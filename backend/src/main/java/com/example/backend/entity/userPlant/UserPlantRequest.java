package com.example.backend.entity.userPlant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserPlantRequest(
        @NotBlank(message = "Field 'userPlantName' cannot be blank.")
        @Size(min = 2, max = 100, message = "Field 'userPlantName' must be between 2 and 100 characters.")
        String userPlantName,
        @NotNull(message = "Field 'userId' cannot be NULL.")
        Integer userId,
        @NotNull(message = "Field 'plantId' cannot be NULL.")
        Integer plantId) {}
