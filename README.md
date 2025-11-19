# Student Score Management System

A Spring Boot REST API for managing student scores with statistical analysis.

## Quick Start

```bash
# Start application and database
docker-compose up --build

# Access Swagger UI
open http://localhost:8080/swagger-ui.html
```

## Features

- ✅ Manage student scores in 5 subjects (Math, English, Science, History, Geography)
- ✅ Calculate Mean, Median, and Mode
- ✅ Pagination and filtering
- ✅ Data validation (scores 0-100)
- ✅ PostgreSQL database
- ✅ RESTful API with Swagger documentation
- ✅ Docker containerization
- ✅ Unit tests

## API Endpoints

### Create Student
```bash
POST /api/students
{
  "name": "John Doe",
  "mathScore": 85.0,
  "englishScore": 90.0,
  "scienceScore": 78.0,
  "historyScore": 88.0,
  "geographyScore": 92.0
}
```

### Get Reports (Paginated)
```bash
GET /api/students/reports?page=0&size=10&sortBy=name&sortDir=ASC
```

### Filter by Name
```bash
GET /api/students/reports?name=John
```

### Get Single Report
```bash
GET /api/students/reports/{id}
```

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- PostgreSQL 15
- Maven
- Docker
- JUnit 5 & Mockito

## Running Tests

```bash
mvn test
```

## Project Structure

```
src/
├── main/java/com/techieplanet/assessment/
│   ├── config/          # Swagger configuration
│   ├── controller/      # REST endpoints
│   ├── dto/            # Request/Response objects
│   ├── entity/         # Database entities
│   ├── exception/      # Error handling
│   ├── repository/     # Data access
│   └── service/        # Business logic
└── test/               # Unit tests
```

## Design Patterns

- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Dependency Injection
- Builder Pattern

## Documentation

- `ORAL_DEFENSE_PREP.md` - Comprehensive walkthrough and preparation guide
- `PROJECT_SUMMARY.md` - Technical implementation details
- `SQL_ANSWERS.md` - SQL questions and answers
- Swagger UI at `/swagger-ui.html`

## Stopping the Application

```bash
docker-compose down
```

