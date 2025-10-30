package br.com.unifacol.nasaapod.controller;

import br.com.unifacol.nasaapod.model.ApodDto;
import br.com.unifacol.nasaapod.service.ApodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/apod")
public class ApodController {

    private final ApodService apodService;

    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    @GetMapping
    public ResponseEntity<ApodDto> getApod(@RequestParam(required = false) String date) {
        return ResponseEntity.ok(apodService.getApod(date));
    }
}

