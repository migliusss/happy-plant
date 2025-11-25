package com.example.backend.repository;

import com.example.backend.entity.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlantRepository extends JpaRepository<UserPlant, Integer> {
    List<UserPlant> findByUserUserId (Integer userId);
}