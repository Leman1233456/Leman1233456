package com.todolist.controller;

import com.todolist.dto.TaskCreateRequest;
import com.todolist.dto.TaskUpdateRequest;
import com.todolist.model.Task;
import com.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // For frontend integration
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create a new task
     * POST /api/tasks
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskCreateRequest request) {
        Task createdTask = taskService.createTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Get all tasks
     * GET /api/tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {
        
        List<Task> tasks;
        
        if (search != null && !search.trim().isEmpty()) {
            tasks = taskService.searchTasksByTitle(search.trim());
        } else if (completed != null) {
            tasks = taskService.getTasksByCompletionStatus(completed);
        } else {
            tasks = taskService.getAllTasks();
        }
        
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get a single task by ID
     * GET /api/tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Update a task
     * PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {
        Task updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Delete a task
     * DELETE /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark task as completed
     * PATCH /api/tasks/{id}/complete
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id) {
        Task completedTask = taskService.markTaskAsCompleted(id);
        return ResponseEntity.ok(completedTask);
    }

    /**
     * Mark task as incomplete
     * PATCH /api/tasks/{id}/incomplete
     */
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<Task> markTaskAsIncomplete(@PathVariable Long id) {
        Task incompleteTask = taskService.markTaskAsIncomplete(id);
        return ResponseEntity.ok(incompleteTask);
    }

    /**
     * Get completed tasks
     * GET /api/tasks/completed
     */
    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasks() {
        List<Task> completedTasks = taskService.getTasksByCompletionStatus(true);
        return ResponseEntity.ok(completedTasks);
    }

    /**
     * Get pending tasks
     * GET /api/tasks/pending
     */
    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks() {
        List<Task> pendingTasks = taskService.getTasksByCompletionStatus(false);
        return ResponseEntity.ok(pendingTasks);
    }
}