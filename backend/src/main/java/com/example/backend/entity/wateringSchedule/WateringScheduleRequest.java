package com.example.backend.entity.wateringSchedule;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record WateringScheduleRequest(
        @NotNull(message = "Field 'userPlantId' cannot be NULL.")
        int userPlantId,
        @NotNull(message = "Field 'lastWatering' cannot be NULL.")
        LocalDate lastWatering,
        LocalDate nextWatering) {
}
