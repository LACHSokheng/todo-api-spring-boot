package com.example.todoapi.service;

import com.example.todoapi.dto.request.TodoRequest;
import com.example.todoapi.dto.response.PaginatedResponse;
import com.example.todoapi.dto.response.TodoResponse;
import com.example.todoapi.entity.Todo;
import com.example.todoapi.entity.User;
import com.example.todoapi.exception.ForbiddenException;
import com.example.todoapi.exception.NotFoundException;
import com.example.todoapi.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    public TodoResponse createTodo(TodoRequest request, Long userId) {
        User user = userService.findById(userId);

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);

        return mapToResponse(savedTodo);
    }

    public TodoResponse updateTodo(Long todoId, TodoRequest request, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new NotFoundException("Todo not found"));

        // Verify ownership
        if (!todo.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to update this todo");
        }

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());

        Todo updatedTodo = todoRepository.save(todo);

        return mapToResponse(updatedTodo);
    }

    public void deleteTodo(Long todoId, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new NotFoundException("Todo not found"));

        // Verify ownership
        if (!todo.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to delete this todo");
        }

        todoRepository.delete(todo);
    }

    public PaginatedResponse<TodoResponse> getTodos(Long userId, int page, int limit) {
        // Validate pagination parameters
        if (page < 1) page = 1;
        if (limit < 1) limit = 10;
        if (limit > 100) limit = 100; // Max limit

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        Page<Todo> todoPage = todoRepository.findByUserId(userId, pageable);

        List<TodoResponse> todoResponses = todoPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                todoResponses,
                page,
                limit,
                todoPage.getTotalElements()
        );
    }

    private TodoResponse mapToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCompleted()
        );
    }

}