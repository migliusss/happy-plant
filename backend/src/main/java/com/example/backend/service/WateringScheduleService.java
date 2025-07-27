package com.example.backend.service;

import com.example.backend.entity.WateringSchedule;
import com.example.backend.repository.WateringScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WateringScheduleService {
    private final WateringScheduleRepository wateringScheduleRepository;

    public WateringScheduleService(WateringScheduleRepository wateringScheduleRepository) {
        this.wateringScheduleRepository = wateringScheduleRepository;
    }

    public Optional<WateringSchedule> findByUserPlantId(Integer userPlantId) {
        return wateringScheduleRepository.findByUserPlantUserPlantId(userPlantId);
    }

    public WateringSchedule save(WateringSchedule wateringSchedule) {
        return wateringScheduleRepository.save(wateringSchedule);
    }
}
