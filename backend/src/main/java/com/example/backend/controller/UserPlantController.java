package com.example.backend.controller;

import com.example.backend.entity.plant.Plant;
import com.example.backend.entity.user.User;
import com.example.backend.entity.userPlant.UserPlant;
import com.example.backend.entity.userPlant.UserPlantRequest;
import com.example.backend.entity.userPlant.UserPlantResponse;
import com.example.backend.service.PlantService;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController("UserPlants")
@RequestMapping("/api/v1/userPlants")
public class UserPlantController {
    private final UserPlantService userPlantService;
    private final UserService userService;
    private final PlantService plantService;

    public UserPlantController(
            UserPlantService userPlantService, UserService userService, PlantService plantService) {
        this.userPlantService = userPlantService;
        this.userService = userService;
        this.plantService = plantService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserPlantResponse>> get(
            @Valid
            @PathVariable
            @NonNull
            Integer userId
    ) {
        List<UserPlantResponse> response;
        try {
            response = userPlantService.findByUserId(userId)
                    .stream()
                    .map(UserPlantResponse::from)
                    .toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fetching from DB failed. Error: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserPlantResponse> create(
            @Valid
            @RequestBody
            UserPlantRequest request) {
        int userId = request.userId();
        int plantId = request.plantId();

        Optional<User> existingUser;
        try {
            existingUser = userService.findById(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching user(" + userId + ") from DB failed :( Error: " + e.getMessage());
        }

        if (existingUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User(" + userId + ") not found :/");

        Optional<Plant> existingPlant;
        try {
            existingPlant = plantService.findPlantById(plantId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching plant(" + plantId + ") from DB failed :( Error: " + e.getMessage());
        }

        if (existingPlant.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Plant(" + plantId + ") not found :/");
        }

        UserPlant newUserPlant;
        try {
            newUserPlant = userPlantService.save(request.userPlantName(), existingUser.get(), existingPlant.get());
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Saving user plant in DB failed :( Error: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(UserPlantResponse.from(newUserPlant));
    }

    @DeleteMapping("/{userPlantId}")
    public ResponseEntity<Void> delete(
            @Valid
            @PathVariable
            @NonNull
            Integer userPlantId
    ) {
        try {
            userPlantService.delete(userPlantId);
        } catch (Exception e) {
            throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Delete in DB failed :( Error: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
