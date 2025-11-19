# API Guide - Student Score Management System

## Overview

This API allows you to manage student scores across 5 subjects and generate statistical reports.

## Base URL

```
http://localhost:8080/api
```

## Swagger Documentation

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

## Endpoints

### 1. Create Student

**Endpoint:** `POST /api/students`

**Description:** Add a new student with scores in 5 subjects.

**Request Body:**
```json
{
  "name": "John Doe",
  "mathScore": 85.0,
  "englishScore": 90.0,
  "scienceScore": 78.0,
  "historyScore": 88.0,
  "geographyScore": 92.0
}
```

**Validation Rules:**
- All fields are required
- `name` cannot be blank
- All scores must be between 0 and 100

**Success Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "mathScore": 85.0,
  "englishScore": 90.0,
  "scienceScore": 78.0,
  "historyScore": 88.0,
  "geographyScore": 92.0
}
```

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2025-11-19T10:30:00",
  "status": 400,
  "errors": {
    "mathScore": "Math score must be between 0 and 100",
    "name": "Name is required"
  }
}
```

### 2. Get All Student Reports (Paginated)

**Endpoint:** `GET /api/students/reports`

**Description:** Retrieve all student reports with pagination, sorting, and filtering.

**Query Parameters:**
- `name` (optional): Filter by student name (case-insensitive partial match)
- `page` (optional, default: 0): Page number (0-indexed)
- `size` (optional, default: 10): Number of records per page
- `sortBy` (optional, default: "id"): Field to sort by (id, name, mathScore, etc.)
- `sortDir` (optional, default: "ASC"): Sort direction (ASC or DESC)

**Examples:**

Get first page with 10 records:
```
GET /api/students/reports?page=0&size=10
```

Filter by name:
```
GET /api/students/reports?name=John
```

Sort by name descending:
```
GET /api/students/reports?sortBy=name&sortDir=DESC
```

Combined:
```
GET /api/students/reports?name=John&page=0&size=5&sortBy=meanScore&sortDir=DESC
```

**Success Response (200 OK):**
```json
{
  "content": [
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
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1,
  "size": 10,
  "number": 0,
  "empty": false
}
```

### 3. Get Single Student Report

**Endpoint:** `GET /api/students/reports/{id}`

**Description:** Get detailed report for a specific student by ID.

**Path Parameter:**
- `id`: Student ID (Long)

**Example:**
```
GET /api/students/reports/1
```

**Success Response (200 OK):**
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

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2025-11-19T10:30:00",
  "status": 404,
  "message": "Student not found with id: 999"
}
```

## Statistical Calculations

### Mean (Average)
Sum of all 5 scores divided by 5.
```
Mean = (Math + English + Science + History + Geography) / 5
```

### Median (Middle Value)
The middle value when scores are sorted.
- If odd number of scores: middle value
- If even number of scores: average of two middle values

### Mode (Most Frequent)
The score that appears most frequently.
- If all scores are unique, returns the first score
- If multiple scores have same frequency, returns the first one encountered

## Sample cURL Commands

### Create Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "mathScore": 95.0,
    "englishScore": 88.0,
    "scienceScore": 92.0,
    "historyScore": 85.0,
    "geographyScore": 90.0
  }'
```

### Get Reports
```bash
curl -X GET "http://localhost:8080/api/students/reports?page=0&size=10"
```

### Get Single Report
```bash
curl -X GET http://localhost:8080/api/students/reports/1
```

## HTTP Status Codes

- `200 OK`: Request successful
- `201 Created`: Student created successfully
- `400 Bad Request`: Validation error
- `404 Not Found`: Student not found
- `500 Internal Server Error`: Server error

## Testing with Postman

1. Import the OpenAPI spec from: `http://localhost:8080/api-docs`
2. Or use the Swagger UI for interactive testing: `http://localhost:8080/swagger-ui.html`

## Notes

- All timestamps are in ISO-8601 format
- Scores are stored and returned as doubles with decimal precision
- The API uses JSON for both requests and responses
- Pagination is 0-indexed (first page is 0)

