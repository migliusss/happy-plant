package com.example.backend.controller;

import com.example.backend.entity.Plant;
import com.example.backend.entity.User;
import com.example.backend.entity.UserPlant;
import com.example.backend.service.PlantService;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/userPlants")
public class UserPlantController {
    private final UserPlantService userPlantService;
    private final UserService userService;
    private final PlantService plantService;

    public UserPlantController(UserPlantService userPlantService, UserService userService, PlantService plantService) {
        this.userPlantService = userPlantService;
        this.userService = userService;
        this.plantService = plantService;
    }

    @GetMapping("/get")
    public ResponseEntity<UserPlant> findByUserUserId(@RequestParam(value="userId") Integer userId) {
        Optional<UserPlant> userPlant = userPlantService.findByUserUserId(userId);

        if (userPlant.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userPlant.get());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserPlant(@RequestBody UserPlant userPlant) {
        // Make sure User exists in the database.
        Integer userId = userPlant.getUser().getUserId();

        User user;

        Optional<User> existingUsing = userService.findUserById(userId);

        if (existingUsing.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with User ID " + userId + " not found.");
        }

        user = existingUsing.get();

        // Make sure Plant exists in the database.
        Integer plantId = userPlant.getPlant().getPlantId();

        Plant plant;

        Optional<Plant> existingPlant = plantService.findPlantById(plantId);

        if (existingPlant.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Plant with Plant ID " + plantId + " not found.");
        }

        plant = existingPlant.get();

        // Set UserPlant Object with User and Plant to be saved in database.
        userPlant.setUser(user);
        userPlant.setPlant(plant);

        UserPlant createdUserPlant = userPlantService.save(userPlant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUserPlant);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserPlant(@RequestParam(value="userPlantId") Integer userPlantId) {
        Optional<UserPlant> existingUserPlant = userPlantService.findById(userPlantId);

        if (existingUserPlant.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        userPlantService.delete(userPlantId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
