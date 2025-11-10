package br.com.unifacol.nasaapod.service;

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
import java.util.concurrent.TimeoutException;

@Service
public class ApodService {

    private static final Logger logger = LoggerFactory.getLogger(ApodService.class);

    private final WebClient webClient;

    @Value("${nasa.api.key}")
    private String apiKey;

    public ApodService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ApodDto getApod(String date) {
        String targetDate = date != null ? date : LocalDate.now().toString();

        try {
            logger.info("Fetching APOD for date: {}", targetDate);

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/planetary/apod")
                            .queryParam("api_key", apiKey)
                            .queryParam("date", targetDate)
                            .queryParam("thumbs", true)
                            .build())
                    .retrieve()
                    .bodyToMono(ApodDto.class)
                    .timeout(Duration.ofSeconds(25)) // Reduced timeout to 10 seconds
                    .onErrorResume(TimeoutException.class, e -> {
                        logger.warn("Request timeout for date {} after 10 seconds", targetDate);
                        return Mono.just(createFallbackApod(targetDate,
                                "Request timeout - NASA API is not responding quickly"));
                    })
                    .onErrorResume(WebClientResponseException.class, e -> {
                        logger.warn("NASA API error {} for date {}: {}",
                                e.getStatusCode(), targetDate, e.getStatusText());
                        return Mono.just(createFallbackApod(targetDate,
                                "NASA API error - " + e.getStatusCode()));
                    })
                    .onErrorResume(Exception.class, e -> {
                        logger.warn("Network error for date {}: {}", targetDate, e.getMessage());
                        return Mono.just(createFallbackApod(targetDate,
                                "Network connection issue"));
                    })
                    .block(Duration.ofSeconds(30)); // Slightly longer block timeout

        } catch (Exception e) {
            logger.error("Unexpected error for date {}: {}", targetDate, e.getMessage());
            return createFallbackApod(targetDate, "Unexpected error occurred");
        }
    }

    private ApodDto createFallbackApod(String date, String errorMessage) {
        ApodDto fallback = new ApodDto();
        fallback.setDate(date);
        fallback.setTitle("Astronomy Picture of the Day");
        fallback.setExplanation("We're unable to load the astronomy picture for " + date + ". " +
                errorMessage + ". " +
                "This is usually a temporary issue with NASA's API. " +
                "Please try again in a few minutes.");
        fallback.setUrl("");
        fallback.setHdurl("");
        fallback.setMedia_type("image");
        return fallback;
    }
}