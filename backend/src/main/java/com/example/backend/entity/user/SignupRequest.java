package com.example.backend.entity.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "Your 'username' cannot be blank.")
        @Size(min = 2, max = 100, message = "Your 'username' must be between 2 and 100 characters.")
        String username,
        @NotBlank(message = "Your 'password' cannot be blank.")
        String password) {}
