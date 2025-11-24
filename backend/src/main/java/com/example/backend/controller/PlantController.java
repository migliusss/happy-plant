package com.example.backend.controller;

import com.example.backend.entity.Plant;
import com.example.backend.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("Plants")
@RequestMapping("/api/v1/plants")
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Plant> get() {
        return plantService.findAll();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Plant> getPlantByName(@PathVariable String name) {
        if (name != null && !name.isEmpty()) {
            Optional<Plant> existingPlant = plantService.findPlantByName(name);

            if (existingPlant.isEmpty()) {
                return List.of();
            }

            List<Plant> plant = new ArrayList<>();

            plant.add(existingPlant.get());

            return plant;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' is empty.");
    }
}
