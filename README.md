# Student Score Management System

A Spring Boot application for managing student scores across 5 subjects with statistical analysis (Mean, Median, Mode).

## Features

- Add student scores in 5 subjects (Math, English, Science, History, Geography)
- Data validation (scores must be between 0-100)
- Generate reports with statistical analysis:
  - Mean score per student
  - Median score per student
  - Mode score per student
- Pagination and filtering support
- PostgreSQL database
- RESTful API with Swagger/OpenAPI documentation
- Docker containerization

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL 15
- Maven
- Docker & Docker Compose
- Swagger/OpenAPI 3
- Lombok
- JUnit 5 & Mockito

## Prerequisites

- Docker and Docker Compose installed
- Java 17 (for local development)
- Maven 3.9+ (for local development)

## Quick Start with Docker

1. Clone the repository:
```bash
git clone https://github.com/Sijibomiaol/TechiePlanet-Assesment.git
cd TechiePlanet-Assesment
```

2. Start the application with Docker Compose:
```bash
docker-compose up --build
```

This will:
- Build the Spring Boot application
- Start PostgreSQL database
- Start the application on port 8080

3. Access the application:
- API Base URL: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## API Endpoints

### Create Student
```http
POST /api/students
Content-Type: application/json

{
  "name": "John Doe",
  "mathScore": 85.0,
  "englishScore": 90.0,
  "scienceScore": 78.0,
  "historyScore": 88.0,
  "geographyScore": 92.0
}
```

### Get Student Reports (Paginated)
```http
GET /api/students/reports?page=0&size=10&sortBy=name&sortDir=ASC
```

### Filter by Name
```http
GET /api/students/reports?name=John&page=0&size=10
```

### Get Single Student Report
```http
GET /api/students/reports/{id}
```

## Response Example

```json
{
  "id": 1,
  "name": "John Doe",
  "scores": {
    "Math": 85.0,
    "English": 90.0,
    "Science": 78.0,
    "History": 88.0,
    "Geography": 92.0
  },
  "meanScore": 86.6,
  "medianScore": 88.0,
  "modeScore": 85.0
}
```

## Running Tests

```bash
mvn test
```

## Local Development (Without Docker)

1. Install PostgreSQL and create a database named `studentdb`

2. Update `src/main/resources/application.properties` if needed

3. Run the application:
```bash
mvn spring-boot:run
```

## Project Structure

```
src/
├── main/
│   ├── java/com/techieplanet/assessment/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Spring Data Repositories
│   │   └── service/        # Business Logic
│   └── resources/
│       └── application.properties
└── test/                   # Unit Tests

```

## Design Patterns Used

1. **Repository Pattern**: Data access abstraction via Spring Data JPA
2. **Service Layer Pattern**: Business logic separation
3. **DTO Pattern**: Data transfer and validation
4. **Dependency Injection**: Spring's IoC container
5. **Builder Pattern**: Used in DTOs for object creation

## Database Schema

### students table
- id (BIGSERIAL PRIMARY KEY)
- name (VARCHAR NOT NULL)
- math_score (DOUBLE PRECISION)
- english_score (DOUBLE PRECISION)
- science_score (DOUBLE PRECISION)
- history_score (DOUBLE PRECISION)
- geography_score (DOUBLE PRECISION)
- created_at (TIMESTAMP)

## Stopping the Application

```bash
docker-compose down
```

To remove volumes as well:
```bash
docker-compose down -v
```

## License

MIT License