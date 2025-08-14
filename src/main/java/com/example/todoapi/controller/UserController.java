package com.example.todoapi.controller;

import com.example.todoapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Simulated in-memory database
    private final List<User> userList = new ArrayList<>();

    // POST: Add user
    @PostMapping
    public String addUser(@RequestBody User user) {
        userList.add(user);
        return "User added successfully";
    }

    // GET: Return all users
    @GetMapping
    public List<User> getAllUsers() {
        return userList;
    }
}
