package com.example.todoapi.projection;

import java.time.LocalDateTime;

public interface TodoProjection {
    Long getId();
    String getTitle();
    String getDescription();
    Boolean getCompleted();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    // Nested projection for user info
    UserInfo getUser();

    interface UserInfo {
        Long getId();
        String getName();
        String getEmail();
    }
}
