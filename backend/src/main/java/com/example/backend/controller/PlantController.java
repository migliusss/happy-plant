package com.example.backend.controller;

import com.example.backend.entity.plant.Plant;
import com.example.backend.entity.plant.PlantResponse;
import com.example.backend.service.PlantService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Validated
@RestController("Plants")
@RequestMapping("/api/v1/plants")
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public ResponseEntity<List<PlantResponse>> get() {
        List<PlantResponse> response;
        try {
            response = plantService.findAll()
                    .stream()
                    .map(PlantResponse::from)
                    .toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching plants from DB failed :( Error: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PlantResponse> getPlantByName(
            @PathVariable
            @NotBlank(message = "Field 'plantName' cannot be empty :/")
            String name
    ) {
        Optional<Plant> response;
        try {
            response = plantService.findPlantByName(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching plant by name from DB failed :( Error: " + e.getMessage());
        }

        return response
                .map(p -> ResponseEntity.status(HttpStatus.OK).body(PlantResponse.from(p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
