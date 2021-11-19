package com.example.test.technique.service;

import com.example.test.technique.model.FireFigher;
import com.example.test.technique.repository.FireFigherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireFigherService {
    private FireFigherRepository fireFigherRepository;

    public FireFigherService(FireFigherRepository fireFigherRepository) {
        this.fireFigherRepository = fireFigherRepository;
    }

    public Optional<Object> getFireFigher() {
        List fireFighers = fireFigherRepository.findAll();
        if (fireFighers.isEmpty()) {
            return Optional.empty();
        } else return Optional.ofNullable(fireFighers.get(0));
    }

    public Optional<FireFigher> getFireFighter() {
        return fireFigherRepository.findTop();
    }

    public void deleteById(Long id) {
        fireFigherRepository.deleteById(id);
    }

    public void addTheNewFireFigher(FireFigher fireFigher) {
        fireFigherRepository.save(fireFigher);
    }
}
