package com.todolist.service;

import com.todolist.dto.TaskCreateRequest;
import com.todolist.dto.TaskUpdateRequest;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new task
     */
    public Task createTask(TaskCreateRequest request) {
        Task task = new Task(request.getTitle(), request.getDescription());
        return taskRepository.save(task);
    }

    /**
     * Get all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderByCreatedAtDesc();
    }

    /**
     * Get task by ID
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    /**
     * Update task
     */
    public Task updateTask(Long id, TaskUpdateRequest request) {
        Task existingTask = getTaskById(id);

        // Update only non-null fields
        if (request.getTitle() != null) {
            existingTask.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            existingTask.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            existingTask.setCompleted(request.getCompleted());
        }

        return taskRepository.save(existingTask);
    }

    /**
     * Delete task
     */
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    /**
     * Get tasks by completion status
     */
    public List<Task> getTasksByCompletionStatus(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    /**
     * Search tasks by title
     */
    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Mark task as completed
     */
    public Task markTaskAsCompleted(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    /**
     * Mark task as incomplete
     */
    public Task markTaskAsIncomplete(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(false);
        return taskRepository.save(task);
    }
}