package com.example.test.technique.controller;

import com.example.test.technique.events.FireFigherEvent;
import com.example.test.technique.model.FireFigher;
import com.example.test.technique.service.FireFigherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/firefigher")
public class FireFigherController {

    private FireFigherService fireFigherService;
    private FireFigherEvent fireFigherEvent;

    public FireFigherController(FireFigherService fireFigherService, FireFigherEvent fireFigherEvent) {
        this.fireFigherService = fireFigherService;
        this.fireFigherEvent = fireFigherEvent;
    }

    @GetMapping("/activepompier")
    public ResponseEntity<FireFigher> getToDayFire() {
        FireFigher fireFigher = (FireFigher) fireFigherService.getFireFigher().orElse(new FireFigher());
        return ResponseEntity.ok().body(fireFigher);
    }

    @PostMapping("/update_day_fighter")
    public ResponseEntity updateFireFighter() {
        fireFigherEvent.defineFireFigher();
        return ResponseEntity.ok().build();
    }
}
