package com.todo.service;

import com.todo.dto.TaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        
        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    // Get all tasks
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get a single task by ID
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return convertToResponse(task);
    }

    // Update a task
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        
        Task updatedTask = taskRepository.save(task);
        return convertToResponse(updatedTask);
    }

    // Delete a task
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Get completed tasks
    public List<TaskResponse> getCompletedTasks() {
        List<Task> tasks = taskRepository.findByCompletedTrue();
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get incomplete tasks
    public List<TaskResponse> getIncompleteTasks() {
        List<Task> tasks = taskRepository.findByCompletedFalse();
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search tasks by title
    public List<TaskResponse> searchTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Convert Task entity to TaskResponse DTO
    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}