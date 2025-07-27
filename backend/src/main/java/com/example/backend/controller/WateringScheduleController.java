package com.example.backend.controller;

import com.example.backend.entity.UserPlant;
import com.example.backend.entity.WateringSchedule;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.WateringScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/wateringSchedule")
public class WateringScheduleController {
    private final WateringScheduleService wateringScheduleService;
    private final UserPlantService userPlantService;

    public WateringScheduleController(WateringScheduleService wateringScheduleService, UserPlantService userPlantService) {
        this.wateringScheduleService = wateringScheduleService;
        this.userPlantService = userPlantService;
    }

    @GetMapping("/get")
    public ResponseEntity<WateringSchedule> get(@RequestParam(value="userPlantId") Integer userPlantId) {
        Optional<WateringSchedule> existingWateringSchedule = wateringScheduleService.findByUserPlantId(userPlantId);

        if (existingWateringSchedule.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(existingWateringSchedule.get());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody WateringSchedule wateringSchedule) {
        // Make sure User Plant exists in the database.
        Integer userPlantId = wateringSchedule.getUserPlant().getUserPlantId();

        UserPlant userPlant;

        Optional<UserPlant> existingUserPlant = userPlantService.findById(userPlantId);

        if (existingUserPlant.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User Plant with User Plant ID " + userPlantId + " not found.");
        }

        userPlant = existingUserPlant.get();

        wateringSchedule.setUserPlant(userPlant);

        // Set Next Watering date for a User Plant. Default to 7 days.
        LocalDate nextWatering = wateringSchedule.getLastWatering().plusDays(7);
        wateringSchedule.setNextWatering(nextWatering);

        WateringSchedule createdWateringSchedule = wateringScheduleService.save(wateringSchedule);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdWateringSchedule);
    }
}