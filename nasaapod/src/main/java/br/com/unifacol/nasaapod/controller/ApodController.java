package br.com.unifacol.nasaapod.controller;

import br.com.unifacol.nasaapod.service.ApodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/apod")
public class ApodController {

    private final ApodService apodService;

    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    @GetMapping
    public ResponseEntity<?> getApod(@RequestParam(required = false) String date) {
        return ResponseEntity.ok(apodService.getApod(date));
    }
}
