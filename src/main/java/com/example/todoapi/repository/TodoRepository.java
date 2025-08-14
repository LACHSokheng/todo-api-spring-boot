package com.example.todoapi.repository;

import com.example.todoapi.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.user.id = :userId AND t.id = :todoId")
    Optional<Todo> findByIdAndUserId(@Param("todoId") Long todoId, @Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
}