package com.example.todoapi.dto.projection;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TodoProjectionDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private String userName;
    private String userEmail;

    // Constructor for JPQL queries
    public TodoProjectionDTO(Long id, String title, String description, Boolean completed,
                             LocalDateTime createdAt, String userName, String userEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}