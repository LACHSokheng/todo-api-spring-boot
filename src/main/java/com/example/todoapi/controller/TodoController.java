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

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request,
                                                   HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        TodoResponse response = todoService.createTodo(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id,
                                                   @Valid @RequestBody TodoRequest request,
                                                   HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        TodoResponse response = todoService.updateTodo(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        PaginatedResponse<TodoResponse> response = todoService.getTodos(userId, page, limit);
        return ResponseEntity.ok(response);
    }

}