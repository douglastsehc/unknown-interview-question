package config;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {
        private final String cryptoApiUrl = "https://api.crypto.com/";

        public WebClient cryptoWebClient() {
                return WebClient
                        .builder()
                        .baseUrl(cryptoApiUrl)
                        .build();
        }
}
