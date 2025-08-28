package com.example.todoapi.controller;

import com.example.todoapi.dto.request.TodoRequest;
import com.example.todoapi.dto.response.PaginatedResponse;
import com.example.todoapi.dto.response.TodoResponse;
import com.example.todoapi.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todos", description = "Operations related to todo management")
@SecurityRequirement(name = "bearerAuth") // JWT protected
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Operation(summary = "Create a new todo")
    @ApiResponse(responseCode = "201", description = "Todo created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        TodoResponse response = todoService.createTodo(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing todo")
    @ApiResponse(responseCode = "200", description = "Todo updated")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        TodoResponse response = todoService.updateTodo(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a todo")
    @ApiResponse(responseCode = "204", description = "Todo deleted successfully")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get paginated todos")
    @ApiResponse(responseCode = "200", description = "Todos retrieved successfully")
    @GetMapping
    public ResponseEntity<PaginatedResponse<TodoResponse>> getTodos(
            @Parameter(description = "Page number (default: 1)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int limit,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        PaginatedResponse<TodoResponse> response = todoService.getTodos(userId, page, limit);
        return ResponseEntity.ok(response);
    }
}
