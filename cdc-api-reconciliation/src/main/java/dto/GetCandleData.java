package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetCandleData {
    Long t;
    double o;
    double h;
    double l;
    double c;
    double v;
}
