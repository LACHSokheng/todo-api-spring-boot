package com.example.todoapi.controller;

import com.example.todoapi.dto.projection.TodoProjectionDTO;
import com.example.todoapi.dto.response.PaginatedResponse;
import com.example.todoapi.projection.TodoProjection;
import com.example.todoapi.projection.TodoSummaryProjection;
import com.example.todoapi.service.TodoProjectionService;
import com.example.todoapi.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos/projections")
@Tag(name = "Todo Projections", description = "Optimized todo queries using projections")
@SecurityRequirement(name = "Bearer Authentication")
public class TodoProjectionController {

    @Autowired
    private TodoProjectionService projectionService;

    @Autowired
    private TodoService todoService;


    @Operation(summary = "Get todos with user info (Interface Projection)",
            description = "Fetch todos with nested user information using interface projection")
    @GetMapping("/with-user")
    public ResponseEntity<List<TodoProjection>> getTodosWithUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<TodoProjection> todos = projectionService.getTodosWithUser(userId);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get paginated todos (Interface Projection)",
            description = "Get paginated todos using projection for better performance")
    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponse<TodoProjection>> getTodosPaginated(
            @Parameter(description = "Page number (starts from 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        if (page < 1) page = 1;
        if (size < 1) size = 10;
        if (size > 100) size = 100;

        PaginatedResponse<TodoProjection> todos = todoService.getTodosProjection(userId, page, size);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todo summary (Minimal fields)",
            description = "Get lightweight todo list with only essential fields")
    @GetMapping("/summary")
    public ResponseEntity<List<TodoSummaryProjection>> getTodosSummary(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<TodoSummaryProjection> todos = todoService.getTodosSummary(userId);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todos by completion status",
            description = "Filter todos by completed/pending status")
    @GetMapping("/by-status")
    public ResponseEntity<List<TodoSummaryProjection>> getTodosByStatus(
            @Parameter(description = "Completion status", example = "true")
            @RequestParam Boolean completed,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<TodoSummaryProjection> todos = projectionService.getTodosByStatus(userId, completed);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todos with user info (Class-based Projection)",
            description = "Get todos using DTO projection with user information")
    @GetMapping("/dto")
    public ResponseEntity<List<TodoProjectionDTO>> getTodosDTO(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<TodoProjectionDTO> todos = projectionService.getTodosWithUserInfo(userId);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todo statistics",
            description = "Get aggregated statistics about user's todos")
    @GetMapping("/stats")
    public ResponseEntity<TodoProjectionService.TodoStats> getTodoStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        TodoProjectionService.TodoStats stats = projectionService.getTodoStats(userId);
        return ResponseEntity.ok(stats);
    }
}
