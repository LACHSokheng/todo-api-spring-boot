package com.example.todoapi.repository;

import com.example.todoapi.entity.User;
import com.example.todoapi.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u.id as id, u.name as name, u.email as email, " +
            "u.createdAt as createdAt, " +
            "COUNT(t) as todoCount, " +
            "SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END) as completedTodoCount, " +
            "SUM(CASE WHEN t.completed = false THEN 1 ELSE 0 END) as pendingTodoCount " +
            "FROM User u LEFT JOIN u.todos t WHERE u.id = :userId GROUP BY u.id")
    Optional<UserProjection> findUserProjectionById(@Param("userId") Long userId);
}
