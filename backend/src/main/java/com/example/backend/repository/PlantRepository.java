package com.example.backend.repository;

import com.example.backend.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    Optional<Plant> findByName(String name);
}
