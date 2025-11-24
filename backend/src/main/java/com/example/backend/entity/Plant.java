package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plants")
@Getter
@Setter
// Construct the object either way i.e.,
// public Plant() — no args
// public Plant(String name, int age) — all args
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @NotEmpty
    @Column(name = "plant_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer plantId;

    // Cannot be null, and the trimmed length must be greater than zero.
    @NotBlank
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "latin_name", length = 100)
    private String latinName;

    @Column(name = "light", length = 50)
    private String light;

    @Column(name = "water", length = 50)
    private String water;

    @Column(name = "fertilize", length = 50)
    private String fertilize;

    @Column(name = "notes", length = 200)
    private String notes;
}
