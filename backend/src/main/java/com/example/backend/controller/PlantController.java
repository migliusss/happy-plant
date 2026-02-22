package com.example.backend.controller;

import com.example.backend.entity.Plant;
import com.example.backend.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
            List<Plant> plantList = plantService.findPlantByName(name);

            if (plantList.isEmpty()) {
                return List.of();
            }

            return plantList;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' is empty.");
    }
}
