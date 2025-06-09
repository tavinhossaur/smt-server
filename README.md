# Sistema de Monitoramento de Tutores (SMT) - Backend

Backend application for the SMT (Sistema de Monitoramento de Tutores) mobile app, built with Spring Boot 3.5.0.

## Overview

SMT Backend is a RESTful API service that manages tutors/professors monitoring system. It provides endpoints for managing classrooms, courses, disciplines, events, professors, and users with authentication and authorization features.

## Technologies

- Java 21
- Spring Boot 3.5.0
- Spring Security
- MongoDB
- JWT Authentication
- Maven

## Features

- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (Admin/User)
  - Secure password handling

- **Entity Management**
  - Classrooms
  - Courses
  - Disciplines
  - Events
  - Professors
  - Users

- **User Features**
  - Profile management
  - Password updates
  - Profile photo management
  - Favorites system

- **Dashboard**
  - Professor schedules
  - Classroom availability
  - Course information

## Project Structure

```bash
src/main/java/com/ifsp/tavinho/smt_backend/
├── domain/
│   ├── dtos/ # Data Transfer Objects
│   ├── entities/ # Domain Entities
│   ├── repositories/ # MongoDB Repositories
│   └── usecases/ # Business Logic
├── infra/
│   ├── configurations/ # Spring Configurations
│   ├── controllers/ # REST Controllers
│   ├── exceptions/ # Exception Handling
│   ├── interfaces/ # Common Interfaces
│   ├── middlewares/ # Security Filters
│   ├── routes/ # API Routes
│   └── services/ # Core Services
└── shared/
    ├── errors/ # Error Handling
    ├── responses/ # API Response Models
    └── utils/ # Utility Classes
```

## API Routes

- `/api/v1/admin/*` - Administrative endpoints
- `/api/v1/profile/*` - User profile management
- `/api/v1/dashboard/*` - Public dashboard data
- `/api/v1/login` - Authentication endpoint
- `/api/v1/register` - User registration (will not be on final release)

## Setup

### Prerequisites

- Java 21
- MongoDB
- Maven

### Configuration

- Copy the example properties file:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

- Update the following properties in `application.properties`:

- `spring.data.mongodb.uri`: Your MongoDB connection string
- `security.jwt.secret-key`: A secure random string for JWT signing
- `security.jwt.expiration-time`: JWT token expiration time in milliseconds
- `system.user.default-password`: Default password for new users

### Running the Application

```bash
./mvnw spring-boot:run
```

## Security

This application uses JWT for authentication. To access protected endpoints:

1. Get a token through the login endpoint
2. Include the token in the Authorization header: `Bearer <token>`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
