# To-Do List Backend Service

This is a simple To-Do List backend built with Java 17 and Spring Boot. It offers a REST API secured with HTTP Basic authentication and stores data in an in-memory H2 database.

## Requirements

* Java 17+
* Maven 3.9+

## Running the application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

### Default credentials

```
username: admin
password: admin123
```

## API Endpoints

| Method | Endpoint          | Description              |
|--------|-------------------|--------------------------|
| GET    | `/api/tasks`      | Get all tasks            |
| GET    | `/api/tasks/{id}` | Get a task by ID         |
| POST   | `/api/tasks`      | Create a new task        |
| PUT    | `/api/tasks/{id}` | Update an existing task  |
| DELETE | `/api/tasks/{id}` | Delete a task            |

All endpoints require Basic Auth except the H2 console located at `/h2-console`.

### Sample `curl` request

```bash
curl -u admin:admin123 -H "Content-Type: application/json" \
     -d '{"title":"Buy milk","description":"2 liters of milk"}' \
     -X POST http://localhost:8080/api/tasks
```

## Building a runnable JAR

```bash
mvn clean package
java -jar target/todo-backend-0.0.1-SNAPSHOT.jar
```

## Deploying to GitHub

1. Create a new GitHub repository.
2. Initialize your local repository and push:

```bash
git init
git add .
git commit -m "Initial commit: To-Do backend"
git branch -M main
git remote add origin <YOUR_GITHUB_REPO_URL>
git push -u origin main
```

Replace `<YOUR_GITHUB_REPO_URL>` with the link you got from GitHub.

---

Enjoy building! ☕️