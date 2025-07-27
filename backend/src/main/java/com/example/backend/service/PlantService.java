package com.example.backend.service;

import com.example.backend.entity.Plant;
import com.example.backend.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public Optional<Plant> findPlantById(Integer plantId) {
        return plantRepository.findById(plantId);
    }

    public Optional<Plant> findPlantByName(String name) {
        return plantRepository.findPlantByName(name);
    }
}
