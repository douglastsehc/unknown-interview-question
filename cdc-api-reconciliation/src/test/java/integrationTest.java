import config.TimeInterval;
import config.WebClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import service.ApiService;
import service.ValidateService;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class integrationTest {
    ApiService apiService;
    WebClientConfig webClientConfig;
    @BeforeEach
    void initialize(){
        webClientConfig = new WebClientConfig();
        apiService = new ApiService(webClientConfig);
    }
    @ParameterizedTest(name = "With nearly continuous api call data in {1} candle stick chart, after preprocess the data, still not have the same result in one or expected candle stick chart")
     @CsvSource({"BTC_USDT, 1 minute"})
    //@CsvSource({"BTC_USDT, 1 minute", "BTC_USDT, 5 minute", "BTC_USDT, 15 minute"})
    public void will_fail_the_test_even_when_there_is_continuous_api_call_in_different_time_interval(String instrumentName, String interval) {
        TimeInterval timeInterval;
        switch(interval){
            case "1 minute":
                timeInterval = TimeInterval.OneMinute;
                break;
            case "5 minute":
                timeInterval = TimeInterval.FiveMinutes;
                break;
            default:
                timeInterval = TimeInterval.FifteenMinutes;
        }
        ValidateService validateService = new ValidateService(apiService, instrumentName, timeInterval);
        assertEquals(false, validateService.checkIsValid()) ;
    }

    @ParameterizedTest(name = "with nearly continuous api call data in {1} candle stick chart, after preprocess the data will have all of the result from all of the candle stick time match to the expected candle stick chart")
    @CsvSource({"BTC_USDT, 1 minute"})
    //@CsvSource({"BTC_USDT, 1 minute", "BTC_USDT, 5 minute", "BTC_USDT, 15 minute"})
    public void will_pass_the_test_when_there_is_continuous_api_call_in_different_time_interval(String instrumentName, String interval) {
        TimeInterval timeInterval;
        switch(interval){
            case "1 minute":
                timeInterval = TimeInterval.OneMinute;
                break;
            case "5 minute":
                timeInterval = TimeInterval.FiveMinutes;
                break;
            default:
                timeInterval = TimeInterval.FifteenMinutes;
        }
        ValidateService validateService = new ValidateService(apiService, instrumentName, timeInterval);
        assertEquals(true, validateService.checkIsTheCandleStickRecordIsInPreOrCurrentOrPostCandleStickTime()) ;
    }
}
