package service;



import dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ValidateServiceTest {

    Long candleStickTime = 1644072300000L;
    GetCandleStickRes getSingleTradeCandleStickRes = GetCandleStickRes.builder().result(CandleStickResult.builder()
            .data(List.of(GetCandleData.builder()
                    .c(41492.02)
                    .h(41492.02)
                    .l(41492.02)
                    .o(41492.02)
                    .t(candleStickTime)
                    .v(0.000007)
                    .build())).build()).build();

    GetCandleStickRes getMultipleTradeCandleStickRes = GetCandleStickRes.builder().result(CandleStickResult.builder()
            .data(List.of(GetCandleData.builder()
                    .c(41592.02)
                    .h(41592.02)
                    .l(41492.02)
                    .o(41492.02)
                    .t(candleStickTime)
                    .v(0.000007)
                    .build())).build()).build();


    @Spy
    ValidateService validateServiceMock;

    @Test
    public void can_successfully_valid_the_record_with_one_trade(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getSingleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);

        doReturn(getSingleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        doReturn(candleStickTime).when(validateServiceMock).getValidateTime();

        assertEquals(true, validateServiceMock.checkIsValid());
    }

    @Test
    public void can_successfully_valid_the_record_with_custom_complete_data(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build(),
                TradeInfo.builder().tradeId(2L).price(41592.02).tradeTimeStamp(1644072262243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getSingleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);
        doReturn(getMultipleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        doReturn(candleStickTime).when(validateServiceMock).getValidateTime();
        assertEquals(true, validateServiceMock.checkIsValid());
    }

    @Test
    public void can_discover_incomplete_data(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getMultipleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);
        doReturn(getMultipleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();

        doReturn(candleStickTime).when(validateServiceMock).getValidateTime();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        assertEquals(false, validateServiceMock.checkIsValid());
    }

    @Test
    public void can_successfully_valid_one_record_in_candle_stick_time(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getSingleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);

        doReturn(getSingleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();
        //doReturn(candleStickTime).when(validateServiceMock).getValidateTime();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        assertEquals(true, validateServiceMock.checkIsTheCandleStickRecordIsInPreOrCurrentOrPostCandleStickTime());
    }

    @Test
    public void can_successfully_valid_the_record_with_custom_complete_data_in_candle_stick_time(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build(),
                TradeInfo.builder().tradeId(2L).price(41592.02).tradeTimeStamp(1644072262243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getSingleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);

        doReturn(getMultipleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();
        //doReturn(candleStickTime).when(validateServiceMock).getValidateTime();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        assertEquals(true, validateServiceMock.checkIsTheCandleStickRecordIsInPreOrCurrentOrPostCandleStickTime());
    }

    @Test
    public void will_fail_when_incomplete_data_in_candle_stick_time(){
        List<TradeInfo> tradeInfo = List.of(TradeInfo.builder().tradeId(1L).price(41492.02).tradeTimeStamp(1644072261243L).build());
        Map<Long, List<TradeInfo>> tradeInfoMap = new HashMap<>();
        tradeInfoMap.put(getMultipleTradeCandleStickRes.getResult().getData().get(0).getT(), tradeInfo);
        doReturn(getMultipleTradeCandleStickRes).when(validateServiceMock).getCandleStickRes();
        //doReturn(candleStickTime + 60 * 1000).when(validateServiceMock).getValidateTime();
        doReturn(tradeInfoMap).when(validateServiceMock).getTradeInfoMap();
        assertEquals(false, validateServiceMock.checkIsTheCandleStickRecordIsInPreOrCurrentOrPostCandleStickTime());
    }
}
