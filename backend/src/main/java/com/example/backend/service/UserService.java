package com.example.backend.service;

import com.example.backend.entity.user.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
