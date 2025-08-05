package com.todoapp.backend.repository;

import com.todoapp.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Find tasks by completion status
    List<Task> findByCompleted(boolean completed);
    
    // Find tasks by title containing text (case insensitive)
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Task> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    // Find tasks by description containing text (case insensitive)
    @Query("SELECT t FROM Task t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Task> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    
    // Find all tasks ordered by creation date (newest first)
    @Query("SELECT t FROM Task t ORDER BY t.createdAt DESC")
    List<Task> findAllOrderByCreatedAtDesc();
}