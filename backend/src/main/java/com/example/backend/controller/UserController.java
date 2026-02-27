package com.example.backend.controller;

import com.example.backend.entity.user.User;
import com.example.backend.entity.user.UserResponse;
import com.example.backend.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Validated
@RestController("Users")
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @PathVariable
            @NotBlank(message = "Field 'username' cannot be empty :/")
            String username
    ) {
        Optional<User> response;
        try {
            response = userService.findByUsername(username);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fetching user by name from DB failed :( Error: " + e.getMessage());
        }

        return response
                .map(u -> ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}