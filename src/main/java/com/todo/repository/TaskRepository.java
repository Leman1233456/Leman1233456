package com.todo.repository;

import com.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Find all tasks ordered by creation date (newest first)
    List<Task> findAllByOrderByCreatedAtDesc();
    
    // Find all completed tasks
    List<Task> findByCompletedTrue();
    
    // Find all incomplete tasks
    List<Task> findByCompletedFalse();
    
    // Find tasks by title containing the given string (case-insensitive)
    List<Task> findByTitleContainingIgnoreCase(String title);
}