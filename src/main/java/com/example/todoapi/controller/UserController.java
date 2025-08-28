package com.example.todoapi.controller;

import com.example.todoapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Users", description = "Example in-memory users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Simulated in-memory database
    private final List<User> userList = new ArrayList<>();

    @Operation(summary = "Add user (demo")
    // POST: Add user
    @PostMapping
    public String addUser(@RequestBody User user) {
        userList.add(user);
        return "User added successfully";
    }

    @Operation(summary = "Get all users (demo)")
    // GET: Return all users
    @GetMapping
    public List<User> getAllUsers() {
        return userList;
    }
}
