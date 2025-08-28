package com.example.todoapi.repository;

import com.example.todoapi.entity.Todo;
import com.example.todoapi.projection.TodoProjection;
import com.example.todoapi.projection.TodoSummaryProjection;
import com.example.todoapi.dto.projection.TodoProjectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {


    // Interface-based projections
    Page<TodoProjection> findProjectionByUserId(Long userId, Pageable pageable);

    List<TodoSummaryProjection> findSummaryByUserId(Long userId);

    List<TodoSummaryProjection> findSummaryByUserIdAndCompleted(Long userId, Boolean completed);

    @Query("SELECT t FROM Todo t JOIN FETCH t.user WHERE t.user.id = :userId ORDER BY t.createdAt DESC")
    List<TodoProjection> findProjectionWithUserByUserId(@Param("userId") Long userId);

    // Class-based projection with JPQL
    @Query("SELECT new com.example.todoapi.dto.projection.TodoProjectionDTO(" +
            "t.id, t.title, t.description, t.completed, t.createdAt, u.name, u.email) " +
            "FROM Todo t JOIN t.user u WHERE t.user.id = :userId ORDER BY t.createdAt DESC")
    List<TodoProjectionDTO> findTodoProjectionsByUserId(@Param("userId") Long userId);

    // Dynamic projection
    <T> List<T> findByUserId(Long userId, Class<T> type);

    <T> Page<T> findByUserId(Long userId, Pageable pageable, Class<T> type);

    // Traditional entity methods (kept for backward compatibility)
    Page<Todo> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.user.id = :userId AND t.id = :todoId")
    Optional<Todo> findByIdAndUserId(@Param("todoId") Long todoId, @Param("userId") Long userId);

    // Count methods
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.user.id = :userId AND t.completed = :completed")
    Long countByUserIdAndCompleted(@Param("userId") Long userId, @Param("completed") Boolean completed);
}
