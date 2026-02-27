package com.example.backend.entity.userPlant;

import com.example.backend.entity.plant.PlantResponse;
import com.example.backend.entity.user.UserResponse;

public record UserPlantResponse(int userPlantId, String userPlantName, UserResponse userResponse, PlantResponse plantResponse) {
    public static UserPlantResponse from(UserPlant userPlant) {
        return new UserPlantResponse(
                userPlant.getUserPlantId(),
                userPlant.getUserPlantName(),
                UserResponse.from(userPlant.getUser()),
                PlantResponse.from(userPlant.getPlant())
        );
    }
}
