package com.example.backend.repository;

import com.example.backend.entity.WateringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WateringScheduleRepository extends JpaRepository<WateringSchedule, Integer> {
    Optional<WateringSchedule> findByUserPlantUserPlantId(Integer userPlantId);
}
