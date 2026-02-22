package com.example.backend.controller;

import com.example.backend.entity.UserPlant;
import com.example.backend.entity.WateringSchedule;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.WateringScheduleService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController("WateringSchedule")
@RequestMapping("/api/v1/wateringSchedule")
public class WateringScheduleController {
    private final WateringScheduleService wateringScheduleService;
    private final UserPlantService userPlantService;

    public WateringScheduleController(
            WateringScheduleService wateringScheduleService, UserPlantService userPlantService) {
        this.wateringScheduleService = wateringScheduleService;
        this.userPlantService = userPlantService;
    }

    @GetMapping("/{userPlantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<WateringSchedule> get(@PathVariable Integer userPlantId) {
        if (userPlantId != null) {
            Optional<WateringSchedule> existingWateringSchedule =
                    wateringScheduleService.findByUserPlantId(userPlantId);

            if (existingWateringSchedule.isEmpty()) {
                return List.of();
            }

            List<WateringSchedule> wateringSchedule = new ArrayList<>();

            wateringSchedule.add(existingWateringSchedule.get());

            return wateringSchedule;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field 'userPlantId' is empty.");
    }

    @PostMapping("/{userPlantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WateringSchedule create(@PathVariable Integer userPlantId, @Nullable LocalDate lastWatering, @Nullable LocalDate nextWatering) {
        Optional<UserPlant> existingUserPlant = userPlantService.findById(userPlantId);

        if (existingUserPlant.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Plant with User Plant ID " + userPlantId + " not found.");
        }

        UserPlant userPlant = existingUserPlant.get();

        WateringSchedule wateringSchedule = new WateringSchedule();

        wateringSchedule.setLastWatering(Objects.requireNonNullElseGet(lastWatering, LocalDate::now));
        wateringSchedule.setNextWatering(
                Objects.requireNonNullElseGet(
                        nextWatering, () -> wateringSchedule.getLastWatering().plusDays(7)));
        wateringSchedule.setUserPlant(userPlant);

        return wateringScheduleService.save(wateringSchedule);
    }
}
