package com.example.backend.entity.plant;

public record PlantResponse(
        int plantId,
        String name,
        String latinName,
        String light,
        String water,
        String fertilize,
        String notes) {
    public static PlantResponse from(Plant response) {
        return new PlantResponse(
                response.getPlantId(),
                response.getName(),
                response.getLatinName(),
                response.getLight(),
                response.getWater(),
                response.getFertilize(),
                response.getNotes()
        );
    }
}
