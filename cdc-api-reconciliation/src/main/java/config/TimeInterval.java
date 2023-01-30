package config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeInterval {
    OneMinute("1m", 60),
    FiveMinutes("5m", 300),
    FifteenMinutes("15m", 900);

    private final String period;
    private final int timeInterval;
}
