package service;

import config.CryptoEndPoint;
import config.TimeInterval;
import config.WebClientConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
public class ApiService {

    WebClient webClient;
    List<String> apple = new LinkedList<>();
    public ApiService(WebClientConfig webClientConfig){
        this.webClient = webClientConfig.cryptoWebClient();
    }

    public <T> T getApiResponse(CryptoEndPoint cryptoEndPointPath, Class<T> responseClass, String instrumentName, TimeInterval timeInterval) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(cryptoEndPointPath.getApiEndPointPath())
                        .queryParam("instrument_name", instrumentName)
                        .queryParam("timeframe", timeInterval.getPeriod()).build())
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }
}
