package com.example.backend.entity.wateringSchedule;

import com.example.backend.entity.userPlant.UserPlantResponse;

import java.time.LocalDate;

public record WateringScheduleResponse(int wateringId, LocalDate lastWatering, LocalDate nextWatering, UserPlantResponse userPlantResponse) {
    public static WateringScheduleResponse from(WateringSchedule wateringSchedule) {
        return new WateringScheduleResponse(
                wateringSchedule.getWateringId(),
                wateringSchedule.getLastWatering(),
                wateringSchedule.getNextWatering(),
                UserPlantResponse.from(wateringSchedule.getUserPlant())
        );
    }
}
