package com.example.backend.controller;

import com.example.backend.entity.Plant;
import com.example.backend.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plants")
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPlantByName(@RequestParam(value="name", required=false) String name) {
        if (name != null && !name.isEmpty()) {
            Optional<Plant> existingPlant = plantService.findPlantByName(name);

            if (existingPlant.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(existingPlant.get());
        }

        List<Plant> allPlants = plantService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPlants);
    }
}
