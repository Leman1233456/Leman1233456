package com.todoapp.backend.controller;

import com.todoapp.backend.dto.TaskDTO;
import com.todoapp.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO createdTask = taskService.createTask(taskDTO);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get all tasks
     * GET /api/tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        try {
            List<TaskDTO> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get a single task by ID
     * GET /api/tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long id) {
        try {
            Optional<TaskDTO> task = taskService.getTaskById(id);
            if (task.isPresent()) {
                return new ResponseEntity<>(task.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Update an existing task
     * PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id, 
                                            @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Optional<TaskDTO> updatedTask = taskService.updateTask(id, taskDTO);
            if (updatedTask.isPresent()) {
                return new ResponseEntity<>(updatedTask.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Delete a task
     * DELETE /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") Long id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get tasks by completion status
     * GET /api/tasks/status/{completed}
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable("completed") boolean completed) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByStatus(completed);
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Search tasks by title
     * GET /api/tasks/search?title={title}
     */
    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasksByTitle(@RequestParam("title") String title) {
        try {
            List<TaskDTO> tasks = taskService.searchTasksByTitle(title);
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Mark task as completed
     * PATCH /api/tasks/{id}/complete
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(@PathVariable("id") Long id) {
        try {
            Optional<TaskDTO> updatedTask = taskService.markTaskAsCompleted(id);
            if (updatedTask.isPresent()) {
                return new ResponseEntity<>(updatedTask.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Mark task as incomplete
     * PATCH /api/tasks/{id}/incomplete
     */
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TaskDTO> markTaskAsIncomplete(@PathVariable("id") Long id) {
        try {
            Optional<TaskDTO> updatedTask = taskService.markTaskAsIncomplete(id);
            if (updatedTask.isPresent()) {
                return new ResponseEntity<>(updatedTask.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}