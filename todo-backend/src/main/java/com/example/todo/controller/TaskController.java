package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // Create a Task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return repository.save(task);
    }

    // Get All Tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    // Get Task by ID
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // Update a Task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    return repository.save(task);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // Delete a Task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(Long id) {
            super("Task with id " + id + " not found");
        }
    }
}