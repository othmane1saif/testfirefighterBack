package com.example.test.technique.controller;

import com.example.test.technique.model.FireFigher;
import com.example.test.technique.repository.FireFigherRepository;
import com.example.test.technique.service.FireFigherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/firefigher")
public class FireFigherController {

    private FireFigherService fireFigherService;

    public FireFigherController(FireFigherService fireFigherService) {
        this.fireFigherService = fireFigherService;
    }

    @GetMapping("/activepompier")
    public ResponseEntity<FireFigher> getToDayFire() {
        FireFigher fireFigher = (FireFigher) fireFigherService.getFireFigher().orElse(new FireFigher());
        return ResponseEntity.ok().body(fireFigher);
    }
}
