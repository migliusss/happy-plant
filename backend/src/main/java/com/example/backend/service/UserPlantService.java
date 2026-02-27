package com.example.backend.service;

import com.example.backend.entity.plant.Plant;
import com.example.backend.entity.user.User;
import com.example.backend.entity.userPlant.UserPlant;
import com.example.backend.repository.UserPlantRepository;
import org.springframework.stereotype.Service;
import java.util.List;
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

    public List<UserPlant> findByUserId(Integer userId) {
        return userPlantRepository.findByUser_UserId(userId);
    }

    public UserPlant save(String userPlantName, User user, Plant plant) {
        UserPlant newUserPlant = UserPlant.newUserPlant(userPlantName, user, plant);

        userPlantRepository.save(newUserPlant);

        return newUserPlant;
    }

    public void delete(Integer userPlantId) {
        userPlantRepository.deleteById(userPlantId);
    }
}
