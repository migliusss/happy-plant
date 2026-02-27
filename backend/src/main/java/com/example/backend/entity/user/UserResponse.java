package com.example.backend.entity.user;

public record UserResponse(int userId, String username) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getUserId(), user.getUsername());
    }
}
