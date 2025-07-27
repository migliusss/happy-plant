package com.example.backend.repository;

import com.example.backend.entity.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPlantRepository extends JpaRepository<UserPlant, Integer> {
    Optional<UserPlant> findByUserUserId(Integer userId);
}
