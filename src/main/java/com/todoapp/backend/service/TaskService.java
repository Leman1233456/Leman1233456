package com.todoapp.backend.service;

import com.todoapp.backend.dto.TaskDTO;
import com.todoapp.backend.entity.Task;
import com.todoapp.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    /**
     * Create a new task
     */
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }
    
    /**
     * Get all tasks
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAllOrderByCreatedAtDesc();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a single task by ID
     */
    @Transactional(readOnly = true)
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Update an existing task
     */
    public Optional<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(taskDTO.getTitle());
                    existingTask.setDescription(taskDTO.getDescription());
                    existingTask.setCompleted(taskDTO.isCompleted());
                    Task updatedTask = taskRepository.save(existingTask);
                    return convertToDTO(updatedTask);
                });
    }
    
    /**
     * Delete a task by ID
     */
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Get tasks by completion status
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByStatus(boolean completed) {
        List<Task> tasks = taskRepository.findByCompleted(completed);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Search tasks by title
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> searchTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Mark task as completed
     */
    public Optional<TaskDTO> markTaskAsCompleted(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(true);
                    Task updatedTask = taskRepository.save(task);
                    return convertToDTO(updatedTask);
                });
    }
    
    /**
     * Mark task as incomplete
     */
    public Optional<TaskDTO> markTaskAsIncomplete(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(false);
                    Task updatedTask = taskRepository.save(task);
                    return convertToDTO(updatedTask);
                });
    }
    
    /**
     * Convert Task entity to TaskDTO
     */
    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
    
    /**
     * Convert TaskDTO to Task entity
     */
    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        return task;
    }
}