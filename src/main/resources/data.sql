-- Sample tasks for testing
INSERT INTO tasks (title, description, completed, created_at, updated_at) VALUES 
('Java layihəsini tamamla', 'Spring Boot istifadə edərək To-Do List backend servisi yarat', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('API testlərini et', 'Bütün REST API endpoint-lərini test et', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('README yazısını yaz', 'Layihə üçün aydın və ətraflı README fayl yarat', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kod review', 'Yazılan kodları yoxla və təkmilləşdir', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('GitHub-a yüklə', 'Layihəni GitHub repository-sinə yüklə', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);