package config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoEndPoint {
    GET_TRADES("v2/public/get-trades"),
    GET_CANDLESTICK("v2/public/get-candlestick");

    private final String apiEndPointPath;
}
