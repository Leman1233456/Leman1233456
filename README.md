# To-Do List Backend Service

A Spring Boot REST API for managing tasks with JWT authentication. This service provides a complete backend solution for a To-Do list application.

## Features

- ✅ **Create Tasks**: Add new tasks with title and description
- ✅ **Get All Tasks**: Retrieve all tasks with pagination
- ✅ **Get Single Task**: View a specific task by ID
- ✅ **Update Tasks**: Edit existing task details
- ✅ **Delete Tasks**: Remove tasks from the system
- ✅ **JWT Authentication**: Secure API with token-based authentication
- ✅ **Task Status**: Mark tasks as completed/incomplete
- ✅ **Search Functionality**: Find tasks by title
- ✅ **H2 Database**: In-memory database for development

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** with JWT
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd todo-backend
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access H2 Database Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:tododb`
- Username: `sa`
- Password: `password`

## API Documentation

### Authentication

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer"
}
```

### Task Operations

**Note**: All task endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### 1. Create a Task
```http
POST /api/tasks
Content-Type: application/json

{
  "title": "Complete project",
  "description": "Finish the Spring Boot project",
  "completed": false
}
```

#### 2. Get All Tasks
```http
GET /api/tasks
```

#### 3. Get a Single Task
```http
GET /api/tasks/{id}
```

#### 4. Update a Task
```http
PUT /api/tasks/{id}
Content-Type: application/json

{
  "title": "Updated task title",
  "description": "Updated description",
  "completed": true
}
```

#### 5. Delete a Task
```http
DELETE /api/tasks/{id}
```

#### 6. Get Completed Tasks
```http
GET /api/tasks/completed
```

#### 7. Get Incomplete Tasks
```http
GET /api/tasks/incomplete
```

#### 8. Search Tasks by Title
```http
GET /api/tasks/search?title=project
```

## API Response Format

### Success Response
```json
{
  "id": 1,
  "title": "Complete project",
  "description": "Finish the Spring Boot project",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Error Response
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: 999"
}
```

## Testing the API

### Using curl

1. **Login to get JWT token:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

2. **Create a task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"title":"Test task","description":"This is a test task"}'
```

3. **Get all tasks:**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Using Postman

1. Import the following collection or create requests manually
2. Set the base URL to `http://localhost:8080`
3. For protected endpoints, add the JWT token in the Authorization header

## Project Structure

```
src/
├── main/
│   ├── java/com/todo/
│   │   ├── TodoApplication.java          # Main application class
│   │   ├── controller/
│   │   │   ├── AuthController.java       # Authentication endpoints
│   │   │   └── TaskController.java       # Task CRUD endpoints
│   │   ├── dto/
│   │   │   ├── TaskRequest.java          # Request DTO
│   │   │   └── TaskResponse.java         # Response DTO
│   │   ├── model/
│   │   │   └── Task.java                 # Task entity
│   │   ├── repository/
│   │   │   └── TaskRepository.java       # Data access layer
│   │   ├── security/
│   │   │   ├── JwtAuthenticationFilter.java  # JWT filter
│   │   │   ├── JwtTokenProvider.java         # JWT utilities
│   │   │   └── SecurityConfig.java           # Security configuration
│   │   └── service/
│   │       └── TaskService.java          # Business logic
│   └── resources/
│       └── application.properties        # Configuration
└── test/                                # Test files
```

## Configuration

### Database
- **Development**: H2 in-memory database
- **Production**: Configure your preferred database in `application.properties`

### Security
- Default username: `admin`
- Default password: `admin123`
- JWT secret key is configurable in `application.properties`

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
```

### Running with Custom Profile
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```

## Deployment

1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR file:
```bash
java -jar target/todo-backend-1.0.0.jar
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For questions or issues, please create an issue in the repository.