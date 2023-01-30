package dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

public class GetTradesRes {
    private int code;
    private String method;
    private TradesResult result;
}
