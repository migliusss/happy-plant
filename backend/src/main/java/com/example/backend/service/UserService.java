package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
