package com.example.backend.repository;

import com.example.backend.entity.wateringSchedule.WateringSchedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WateringScheduleRepository extends JpaRepository<WateringSchedule, Integer> {
    List<WateringSchedule> findByUserPlant_UserPlantId(int userPlantId, Sort sort);
}
