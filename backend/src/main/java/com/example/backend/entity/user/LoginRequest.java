package com.example.backend.entity.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Your 'username' cannot be blank.")
        String username,
        @NotBlank(message = "Your 'password' cannot be blank.")
        String password) {}
