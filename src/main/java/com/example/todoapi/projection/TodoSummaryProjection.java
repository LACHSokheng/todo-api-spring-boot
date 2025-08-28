package com.example.todoapi.projection;

import java.time.LocalDateTime;

public interface TodoSummaryProjection {
    Long getId();
    String getTitle();
    Boolean getCompleted();
    LocalDateTime getCreatedAt();
}
