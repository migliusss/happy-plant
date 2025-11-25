package com.example.backend.repository;

import com.example.backend.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByName(String name);
}
