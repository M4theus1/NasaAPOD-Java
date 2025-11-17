package br.com.unifacol.nasaapod.service;

import br.com.unifacol.nasaapod.exception.InvalidDateException;
import br.com.unifacol.nasaapod.model.ApodDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class ApodService {

    private static final Logger logger = LoggerFactory.getLogger(ApodService.class);

    private final WebClient webClient;

    @Value("${nasa.api.key}")
    private String apiKey;

    private static final LocalDate MIN_DATE = LocalDate.of(1995, 6, 16);

    public ApodService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ApodDto getApod(String date) {

        LocalDate today = LocalDate.now();
        LocalDate chosenDate = date != null ? LocalDate.parse(date) : today;

        if (chosenDate.isBefore(MIN_DATE)) {
            throw new InvalidDateException("A data mínima permitida é 1995-06-16.");
        }

        if (chosenDate.isAfter(today)) {
            throw new InvalidDateException("A data não pode ser maior que a data atual.");
        }

        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/planetary/apod")
                            .queryParam("api_key", apiKey)
                            .queryParam("date", chosenDate)
                            .queryParam("thumbs", true)
                            .build())
                    .retrieve()
                    .bodyToMono(ApodDto.class)
                    .timeout(Duration.ofSeconds(20))
                    .onErrorResume(WebClientResponseException.class, e ->
                            Mono.just(createFallbackApod(chosenDate.toString(),
                                    "Erro da NASA API: " + e.getStatusCode()))
                    )
                    .onErrorResume(Exception.class, e ->
                            Mono.just(createFallbackApod(chosenDate.toString(),
                                    "Erro de rede ou servidor."))
                    )
                    .block();

        } catch (Exception e) {
            return createFallbackApod(chosenDate.toString(), "Erro inesperado.");
        }
    }

    private ApodDto createFallbackApod(String date, String errorMessage) {
        ApodDto apod = new ApodDto();
        apod.setDate(date);
        apod.setTitle("Imagem não disponível");
        apod.setExplanation(errorMessage);
        apod.setUrl("");
        apod.setHdurl("");
        apod.setMedia_type("image");
        return apod;
    }
}
