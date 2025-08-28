package com.example.todoapi.projection;

import java.time.LocalDateTime;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();
    LocalDateTime getCreatedAt();
    Long getTodoCount();
    Long getCompletedTodoCount();
    Long getPendingTodoCount();
}