package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetTradeData {
    Long dateTime;
    Long d;
    String s;
    double p;
    double q;
    Long t;
    String i;
}
