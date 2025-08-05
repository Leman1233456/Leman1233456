package com.todolist.config;

import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if database is empty
        if (taskRepository.count() == 0) {
            // Add sample tasks
            Task task1 = new Task("Java layihəsini tamamla", "Spring Boot istifadə edərək To-Do List backend servisi yarat");
            Task task2 = new Task("API testlərini et", "Bütün REST API endpoint-lərini test et");
            Task task3 = new Task("README yazısını yaz", "Layihə üçün aydın və ətraflı README fayl yarat");
            Task task4 = new Task("Kod review", "Yazılan kodları yoxla və təkmilləşdir");
            task4.setCompleted(true);
            Task task5 = new Task("GitHub-a yüklə", "Layihəni GitHub repository-sinə yüklə");

            taskRepository.save(task1);
            taskRepository.save(task2);
            taskRepository.save(task3);
            taskRepository.save(task4);
            taskRepository.save(task5);

            System.out.println("Sample tasks loaded successfully!");
        }
    }
}