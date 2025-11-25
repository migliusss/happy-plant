package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("Users")
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserByUsername(@PathVariable String username) {
        if (username != null && !username.isEmpty()) {
            Optional<User> existingUser = userService.findByUsername(username);

            if (existingUser.isEmpty()) {
                return List.of();
            }

            List<User> user = new ArrayList<>();

            user.add(existingUser.get());

            return user;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' is empty.");
    }
}