package com.example.backend.service;

import com.example.backend.entity.UserPlant;
import com.example.backend.repository.UserPlantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPlantService {
    private final UserPlantRepository userPlantRepository;

    public UserPlantService(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
    }

    public Optional<UserPlant> findById(Integer id) {
        return userPlantRepository.findById(id);
    }

    public Optional<UserPlant> findByUserUserId(Integer userId) {
        return userPlantRepository.findByUserUserId(userId);
    }

    public UserPlant save(UserPlant userPlant) {
        return userPlantRepository.save(userPlant);
    }

    public void delete(Integer userPlantId) {
        userPlantRepository.deleteById(userPlantId);
    }
}
