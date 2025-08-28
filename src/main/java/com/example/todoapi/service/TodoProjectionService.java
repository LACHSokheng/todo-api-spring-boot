package com.example.todoapi.service;

import com.example.todoapi.dto.projection.TodoProjectionDTO;
import com.example.todoapi.projection.TodoProjection;
import com.example.todoapi.projection.TodoSummaryProjection;
import com.example.todoapi.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoProjectionService {

    @Autowired
    private TodoRepository todoRepository;


    public Page<TodoProjection> getTodosProjectionPaginated(Long userId, Pageable pageable) {
        return todoRepository.findProjectionByUserId(userId, pageable);
    }

    public List<TodoProjection> getTodosWithUser(Long userId) {
        return todoRepository.findProjectionWithUserByUserId(userId);
    }

    public List<TodoSummaryProjection> getTodosSummary(Long userId) {
        return todoRepository.findSummaryByUserId(userId);
    }

    public List<TodoSummaryProjection> getTodosByStatus(Long userId, Boolean completed) {
        return todoRepository.findSummaryByUserIdAndCompleted(userId, completed);
    }

    public List<TodoProjectionDTO> getTodosWithUserInfo(Long userId) {
        return todoRepository.findTodoProjectionsByUserId(userId);
    }

    public <T> List<T> getTodosDynamic(Long userId, Class<T> projectionType) {
        return todoRepository.findByUserId(userId, projectionType);
    }

    public <T> Page<T> getTodosDynamicPaginated(Long userId, Pageable pageable, Class<T> projectionType) {
        return todoRepository.findByUserId(userId, pageable, projectionType);
    }

    public TodoStats getTodoStats(Long userId) {
        Long totalTodos = todoRepository.countByUserId(userId);
        Long completedTodos = todoRepository.countByUserIdAndCompleted(userId, true);
        Long pendingTodos = todoRepository.countByUserIdAndCompleted(userId, false);

        return new TodoStats(totalTodos, completedTodos, pendingTodos);
    }

    public static class TodoStats {
        private Long total;
        private Long completed;
        private Long pending;

        public TodoStats(Long total, Long completed, Long pending) {
            this.total = total;
            this.completed = completed;
            this.pending = pending;
        }

        public Long getTotal() { return total; }
        public Long getCompleted() { return completed; }
        public Long getPending() { return pending; }
    }
}
