package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUserByName(@RequestParam(value="name") String name) {
        // Retrieve the User from the database.
        Optional<User> existingUser = userService.findUserByName(name);

        // If the User is not found, return a 404 (not found) status code.
        if (existingUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        // Return the User with a 200 (OK) status code.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(existingUser.get());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Save the User to the database.
        User createdUser = userService.save(user);

        // Return the created User with a 201 (created) status code.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam(value="userId") Integer userId, @RequestBody User user) {
        // Retrieve the User from the database.
        Optional<User> existingUser = userService.findUserById(userId);

        // If the User is not found, return a 404 (not found) status code.
        if (existingUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        // Update the User.
        user.setUserId(userId);
        User updatedUser = userService.save(user);

        // Return the updated User with a 200 (OK) status code.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUser);

    }
}