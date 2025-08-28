package com.example.todoapi.controller;

import com.example.todoapi.dto.request.LoginRequest;
import com.example.todoapi.dto.request.RegisterRequest;
import com.example.todoapi.dto.response.AuthResponse;
import com.example.todoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "Authentication endpoints")
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns an authentication token"
    )
    @ApiResponse(responseCode = "200", description = "Registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates user credentials and returns a JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid username or password")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
