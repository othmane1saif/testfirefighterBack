package com.example.test.technique.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@ToString
@Builder
public class FireFigher {
    @Id
    private Long id;
    private String name;
}
