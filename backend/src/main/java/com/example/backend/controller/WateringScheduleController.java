package com.example.backend.controller;

import com.example.backend.entity.userPlant.UserPlant;
import com.example.backend.entity.wateringSchedule.WateringSchedule;
import com.example.backend.entity.wateringSchedule.WateringScheduleRequest;
import com.example.backend.entity.wateringSchedule.WateringScheduleResponse;
import com.example.backend.service.UserPlantService;
import com.example.backend.service.WateringScheduleService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
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
    public ResponseEntity<List<WateringScheduleResponse>> get(
            @Valid
            @PathVariable
            @NonNull
            Integer userPlantId
    ) {
        List<WateringScheduleResponse> response;
        try {
            response = wateringScheduleService.findByUserPlantId(userPlantId)
                    .stream()
                    .map(WateringScheduleResponse::from)
                    .toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching watering schedule for user plant(" + userPlantId + ") from DB failed :( Error: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<WateringScheduleResponse> create(
            @Valid
            @RequestBody
            WateringScheduleRequest request) {
        Optional<UserPlant> existingUserPlant;
        try {
            existingUserPlant = userPlantService.findById(request.userPlantId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching user plant(" + request.userPlantId() + ") from DB failed :( Error: " + e.getMessage());
        }

        if (existingUserPlant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User plant(" +  request.userPlantId() + ") not found :/");
        }

        LocalDate lastWatering = request.lastWatering();
        LocalDate nextWatering = request.nextWatering();

        if (nextWatering == null) {
            nextWatering = lastWatering.plusDays(7);
        }

        WateringSchedule newWateringSchedule = WateringSchedule.newWateringSchedule(request.lastWatering(), nextWatering, existingUserPlant.get());

        try {
            wateringScheduleService.save(newWateringSchedule);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Saving watering schedule in DB failed :/ Error: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(WateringScheduleResponse.from(newWateringSchedule));
    }
}
