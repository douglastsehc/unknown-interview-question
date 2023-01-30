package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CandleStickResult {
    private String instrument_name;
    private int depth;
    private String interval;
    private List<GetCandleData> data;
}
