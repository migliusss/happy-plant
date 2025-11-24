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

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserByName(@PathVariable String name) {
        if (name != null && !name.isEmpty()) {
            Optional<User> existingUser = userService.findUserByName(name);

            if (existingUser.isEmpty()) {
                return List.of();
            }

            List<User> user = new ArrayList<>();

            user.add(existingUser.get());

            return user;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' is empty.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(String name) {
        if (name != null && !name.isEmpty()) {
            User user = new User();

            user.setName(name);

            return userService.save(user);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' is empty.");
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, String name) {
        Optional<User> existingUser = userService.findUserById(id);

        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
        }

        User user = existingUser.get();

        user.setName(name);

        return userService.save(user);
    }
}