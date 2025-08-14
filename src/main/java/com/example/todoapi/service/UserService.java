package com.example.todoapi.service;

import com.example.todoapi.dto.request.LoginRequest;
import com.example.todoapi.dto.request.RegisterRequest;
import com.example.todoapi.dto.response.AuthResponse;
import com.example.todoapi.entity.User;
import com.example.todoapi.exception.BadRequestException;
import com.example.todoapi.exception.UnauthorizedException;
import com.example.todoapi.repository.UserRepository;
import com.example.todoapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new UnauthorizedException("Invalid email or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        return new AuthResponse(token);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }
}