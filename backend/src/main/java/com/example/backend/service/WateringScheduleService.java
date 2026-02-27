package com.example.backend.service;

import com.example.backend.entity.wateringSchedule.WateringSchedule;
import com.example.backend.repository.WateringScheduleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WateringScheduleService {
    private final WateringScheduleRepository wateringScheduleRepository;

    public WateringScheduleService(WateringScheduleRepository wateringScheduleRepository) {
        this.wateringScheduleRepository = wateringScheduleRepository;
    }

    public List<WateringSchedule> findByUserPlantId(int userPlantId) {
        return wateringScheduleRepository.findByUserPlant_UserPlantId(
                userPlantId,
                Sort.by(Sort.Order.desc("lastWatering"))
        );
    }

    public WateringSchedule save(WateringSchedule wateringSchedule) {
        return wateringScheduleRepository.save(wateringSchedule);
    }
}
