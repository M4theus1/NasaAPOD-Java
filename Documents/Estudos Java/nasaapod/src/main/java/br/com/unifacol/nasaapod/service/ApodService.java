package br.com.unifacol.nasaapod.service;

import br.com.unifacol.nasaapod.model.ApodDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApodService {

    private final WebClient webClient;

    @Value("${nasa.api.key}")
    private String apiKey;

    public ApodService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ApodDto getApod(String date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/apod")
                        .queryParam("api_key", apiKey)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(ApodDto.class)
                .block();
    }
}
