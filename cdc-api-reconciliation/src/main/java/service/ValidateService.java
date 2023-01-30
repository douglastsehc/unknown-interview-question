package service;

import config.CryptoEndPoint;
import config.TimeInterval;
import dto.GetCandleStickRes;
import dto.GetTradeData;
import dto.GetTradesRes;
import dto.TradeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@NoArgsConstructor
public class ValidateService {
    Map<Long, List<TradeInfo>> tradeInfoMap;
    Long validateTime;
    ApiService apiService;
    String instrumentName;
    TimeInterval timeInterval;

    public ValidateService(ApiService apiService, String instrumentName, TimeInterval timeInterval) {
        this.apiService = apiService;
        this.timeInterval = timeInterval;
        this.instrumentName = instrumentName;
        tradeInfoMap = this.callGetTradesApi();
        this.validateTime = tradeInfoMap.size() >= 2 ? findFullRecordTimeslot(tradeInfoMap) : tradeInfoMap.keySet().stream().findFirst().orElse(null);
    }

    private Map<Long, List<TradeInfo>> callGetTradesApi(){
        List<GetTradesRes> getTradesRes = new LinkedList<>();

        //loop the api call
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                GetTradesRes getTradesResItem = apiService.getApiResponse(CryptoEndPoint.GET_TRADES, GetTradesRes.class, instrumentName, timeInterval);
                getTradesRes.add(getTradesResItem);
            }
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        long delay  = 50;
        long period = 50;
        executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);

        try {
            // make sure the data must have at least one complete candle stick happened
            Thread.sleep(50+1000L * timeInterval.getTimeInterval()*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return processGetTradesApiDataToMap(getTradesRes);

    }

    private Map<Long, List<TradeInfo>> processGetTradesApiDataToMap(List<GetTradesRes> getTradesResList) {
        Map<Long, List<TradeInfo>> processTradeInfoMap = new HashMap<>();
        for(int getTradesResListItem = 0; getTradesResListItem < getTradesResList.size(); getTradesResListItem++) {
            List<GetTradeData> getTradeDataList = getTradesResList.get(getTradesResListItem).getResult().getData();
            for (int getTradeInfo = 0; getTradeInfo < getTradeDataList.size(); getTradeInfo++) {
                processTradeInfoMap = checkIfItemExist(getTradeDataList.get(getTradeInfo), processTradeInfoMap);
            }
        }

        return processTradeInfoMap;
    }

    private Map<Long, List<TradeInfo>> checkIfItemExist(GetTradeData getTradeData, Map<Long, List<TradeInfo>> tradeInfoMap){
        Long candleStickTime = toCandleStickTime(getTradeData.getT(), timeInterval.getTimeInterval());
        TradeInfo tradeInfoItem = TradeInfo.builder().tradeId(getTradeData.getD()).price(getTradeData.getP()).tradeTimeStamp(getTradeData.getT()).build();
        if (tradeInfoMap.containsKey(candleStickTime)) {
            List<TradeInfo> tradeInfoList = tradeInfoMap.get(candleStickTime);
            TradeInfo tradeInfoExistingItem = tradeInfoList.stream().filter(tradeInfoElement -> tradeInfoItem.getTradeId()
                    .equals(tradeInfoElement.getTradeId())).findAny().orElse(null);
            if (tradeInfoExistingItem == null) {
                tradeInfoList.add(tradeInfoItem);
                tradeInfoMap.put(candleStickTime, tradeInfoList);
            }
        } else {
            List<TradeInfo> tradeInfoList = new LinkedList<>();
            tradeInfoList.add(tradeInfoItem);
            tradeInfoMap.put(candleStickTime, tradeInfoList);
        }
        return tradeInfoMap;
    }

    public GetCandleStickRes getCandleStickRes() {
        try {
            // wait 60 second to make sure the candle stick api have the most update record
            Thread.sleep(1000L * timeInterval.getTimeInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return apiService.getApiResponse(CryptoEndPoint.GET_CANDLESTICK, GetCandleStickRes.class, instrumentName, timeInterval);
    }

    public Boolean checkIsValid() {
        GetCandleStickRes getCandleStickRes = getCandleStickRes();
        AtomicReference<Boolean> valid = new AtomicReference<>(true);
        getCandleStickRes.getResult().getData().forEach(candleStickData -> {
            if( this.getTradeInfoMap().containsKey(candleStickData.getT()) && candleStickData.getT().equals(getValidateTime())) {
            List<TradeInfo> tradeInfoList = this.getTradeInfoMap().get(getValidateTime());
                if(!(Double.compare(candleStickData.getC(), getLastTrade(tradeInfoList)) == 0
                && Double.compare(candleStickData.getO(), getFirstTrade(tradeInfoList)) == 0
                && Double.compare(candleStickData.getH(), getHighestTrade(tradeInfoList)) == 0
                && Double.compare(candleStickData.getL(), getLowestTrade(tradeInfoList)) == 0)) {
                    valid.set(false);
            }
        }});
        return valid.get();
    }

    public Boolean checkIsTheCandleStickRecordIsInPreOrCurrentOrPostCandleStickTime() {
        AtomicReference<Double> getHighest = new AtomicReference<>(1.);
        AtomicReference<Double> getLowest = new AtomicReference<>(1.);
        AtomicReference<Double> getOpen = new AtomicReference<>(1.);
        AtomicReference<Double> getClose = new AtomicReference<>(1.);
        AtomicReference<Boolean> valid = new AtomicReference<>(false);
        GetCandleStickRes getCandleStickRes = getCandleStickRes();
        getCandleStickRes.getResult().getData().forEach(candleStickData -> {

            // check if record exist in any other timeslot
            if( this.getTradeInfoMap().containsKey(candleStickData.getT()) ) {
                getTradeInfoMap().forEach((key,tradeInfoList) -> {
                Double getH = getTradeInfoByCandleStickParam(tradeInfoList, candleStickData.getH());
                if(getH != null ) {
                    getHighest.set(candleStickData.getH());
                }
                Double getL = getTradeInfoByCandleStickParam(tradeInfoList, candleStickData.getL());
                if(getL != null ) {
                    getLowest.set(candleStickData.getL());
                }
                Double getO = getTradeInfoByCandleStickParam(tradeInfoList, candleStickData.getO());
                if(getO != null ) {
                    getOpen.set(candleStickData.getO());
                }
                Double getC = getTradeInfoByCandleStickParam(tradeInfoList, candleStickData.getC());
                if(getC != null ) {
                    getClose.set(candleStickData.getC());
                }

                if(Double.compare(candleStickData.getC(), getClose.get()) == 0
                        && Double.compare(candleStickData.getO(), getOpen.get()) == 0
                        && Double.compare(candleStickData.getH(), getHighest.get()) == 0
                        && Double.compare(candleStickData.getL(), getLowest.get()) == 0) {
                    valid.set(true);
                }
                });
            }});
        return valid.get();
    }

    private Double getTradeInfoByCandleStickParam(List<TradeInfo> tradeInfoList, double candleStickParam) {
        TradeInfo tradeInfo = tradeInfoList.stream().filter(item -> item.getPrice().equals(candleStickParam)).findAny().orElse(null);
        return tradeInfo != null ? tradeInfo.getPrice() : null;
    }

    private double getFirstTrade(List<TradeInfo> tradeInfoList){
        TradeInfo tradeInfo = tradeInfoList.stream()
                .min(Comparator.comparing(TradeInfo::getTradeTimeStamp))
                .orElseThrow(NoSuchElementException::new);
        return tradeInfo.getPrice();
    }

    private double getLastTrade(List<TradeInfo> tradeInfoList){
        TradeInfo tradeInfo = tradeInfoList.stream()
                .max(Comparator.comparing(TradeInfo::getTradeTimeStamp))
                .orElseThrow(NoSuchElementException::new);
        return tradeInfo.getPrice();
    }

    private double getHighestTrade(List<TradeInfo> tradeInfoList){
        TradeInfo tradeInfo = tradeInfoList.stream()
                .max(Comparator.comparing(TradeInfo::getPrice))
                .orElseThrow(NoSuchElementException::new);
        return tradeInfo.getPrice();
    }

    private double getLowestTrade(List<TradeInfo> tradeInfoList){
        TradeInfo tradeInfo = tradeInfoList.stream()
                .min(Comparator.comparing(TradeInfo::getPrice))
                .orElseThrow(NoSuchElementException::new);
        return tradeInfo.getPrice();
    }

    private Long findFullRecordTimeslot(Map<Long, List<TradeInfo>> tradeInfoMap) {
        Long smallestTime = 0x7fff_ffff_ffff_ffffL;
        Long secondSmallestTime = 0x7fff_ffff_ffff_fffeL;
        for (Long time : tradeInfoMap.keySet()) {
            if(time < smallestTime) {
                secondSmallestTime = smallestTime;
                smallestTime = time;
            } else if (time < secondSmallestTime) {
                secondSmallestTime = time;
            }
        }
            return secondSmallestTime;
    }

    private Long toCandleStickTime(long tradingTimeStamp, int timeInterval) {
        timeInterval = timeInterval*1000;
        return tradingTimeStamp % timeInterval == 0 ? tradingTimeStamp : tradingTimeStamp + timeInterval - tradingTimeStamp % timeInterval;
    }
}
