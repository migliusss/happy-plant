package com.example.backend.controller;

import com.example.backend.entity.Plant;
import com.example.backend.entity.User;
import com.example.backend.entity.UserPlant;
import com.example.backend.service.PlantService;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("User Plants")
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
    @ResponseStatus(HttpStatus.OK)
    public List<UserPlant> get(@PathVariable Integer userId) {
        if (userId != null) {
            Optional<UserPlant> existingUserPlant = userPlantService.findByUserUserId(userId);

            if (existingUserPlant.isEmpty()) {
                return List.of();
            }

            List<UserPlant> userPlant = new ArrayList<>();

            userPlant.add(existingUserPlant.get());

            return userPlant;
        }

        return List.of();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserPlant create(String plantName, Integer userId, Integer plantId) {
        User user = null;
        Plant plant = null;

        if (userId != null) {
            Optional<User> existingUser = userService.findUserById(userId);

            if (existingUser.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with id " + userId + " not found.");
            }

            user = existingUser.get();
        }

        if (plantId != null) {
            Optional<Plant> existingPlant = plantService.findPlantById(plantId);

            if (existingPlant.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Plant with id " + plantId + " not found.");
            }

            plant = existingPlant.get();
        }

        UserPlant userPlant = new UserPlant();

        if (plantName != null && !plantName.isEmpty()) {
            userPlant.setUserPlantName(plantName);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'plantName' is empty.");
        }

        userPlant.setUser(user);
        userPlant.setPlant(plant);

        return userPlantService.save(userPlant);
    }

    @DeleteMapping("/{userPlantId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer userPlantId) {
        userPlantService.delete(userPlantId);
    }
}
