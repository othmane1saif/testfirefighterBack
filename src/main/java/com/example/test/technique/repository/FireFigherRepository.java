package com.example.test.technique.repository;

import com.example.test.technique.model.FireFigher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FireFigherRepository extends JpaRepository<FireFigher, Long> {
    @Query(value = "select * from Fire_figher limit 1", nativeQuery = true)
    Optional<FireFigher> findTop();
}
