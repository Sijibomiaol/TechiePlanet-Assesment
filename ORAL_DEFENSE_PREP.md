# Oral Defense Preparation Guide

## Table of Contents
1. [Code Walkthrough](#code-walkthrough)
2. [Architecture Explanation](#architecture-explanation)
3. [Design Choices](#design-choices)
4. [Test Coverage](#test-coverage)
5. [Edge Cases & Performance](#edge-cases--performance)
6. [Data Modeling](#data-modeling)
7. [Practice Questions & Answers](#practice-questions--answers)

---

## CODE WALKTHROUGH

### Starting Point: Main Application Class

**File:** `AssessmentApplication.java`

```java
@SpringBootApplication
public class AssessmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssessmentApplication.class, args);
    }
}
```

**What to Say:**
> "This is our entry point. The `@SpringBootApplication` annotation does three things:
> - `@Configuration`: Makes this a configuration class
> - `@EnableAutoConfiguration`: Tells Spring Boot to auto-configure based on dependencies
> - `@ComponentScan`: Scans for components in this package and sub-packages
>
> When we run this, Spring Boot starts an embedded Tomcat server, initializes our database connection, and sets up all our REST endpoints."

---

### Layer 1: Entity (Database Layer)

**File:** `entity/Student.java`

**What to Say:**
> "I used JPA (Java Persistence API) to map this class to a database table:
> - `@Entity` tells JPA this is a database table
> - `@Table(name = "students")` specifies the table name
> - `@Id` marks the primary key
> - `@GeneratedValue` auto-generates IDs
> - Each field becomes a column in PostgreSQL
>
> I used Lombok's `@Data` to automatically generate getters, setters, toString, equals, and hashCode. This reduces boilerplate code.
>
> The `@PrePersist` method automatically sets the creation timestamp before saving."

**Key Points:**
- This represents our domain model
- It's a simple POJO (Plain Old Java Object) with annotations
- Lombok makes it cleaner

---

### Layer 2: Repository (Data Access Layer)

**File:** `repository/StudentRepository.java`

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
```

**What to Say:**
> "This is the Repository Pattern in action:
> - We extend `JpaRepository<Student, Long>` which gives us CRUD methods for free
> - We don't write SQL - Spring Data JPA generates it from method names
> - `findByNameContainingIgnoreCase` is a custom query method:
>   - `findBy` - tells Spring this is a query
>   - `Name` - field to search
>   - `Containing` - like '%value%' in SQL
>   - `IgnoreCase` - case-insensitive
>
> This interface is never implemented by us - Spring creates the implementation at runtime!"

**Why This is Good:**
- No boilerplate SQL code
- Type-safe queries
- Automatic pagination support
- Easy to test with mocks

---

### Layer 3: Service (Business Logic Layer)

**File:** `service/StatisticsService.java`

**What to Say:**
> "I separated the statistics calculations into its own service for two reasons:
> 1. **Single Responsibility** - it only does math
> 2. **Reusability** - can be used by other services
>
> Let me explain each calculation:

**MEAN (Average):**
```java
public Double calculateMean(List<Double> scores) {
    return scores.stream()
        .mapToDouble(Double::doubleValue)
        .average()
        .orElse(0.0);
}
```
> "I use Java Streams for clean, functional-style code:
> - `stream()` creates a stream from the list
> - `mapToDouble()` converts to primitive doubles (more efficient)
> - `average()` is a built-in terminal operation
> - `orElse(0.0)` handles empty lists safely"

**MEDIAN (Middle Value):**
```java
public Double calculateMedian(List<Double> scores) {
    List<Double> sortedScores = scores.stream()
        .sorted()
        .collect(Collectors.toList());
    
    int size = sortedScores.size();
    if (size % 2 == 0) {
        return (sortedScores.get(size/2 - 1) + sortedScores.get(size/2)) / 2.0;
    } else {
        return sortedScores.get(size/2);
    }
}
```
> "For median, I:
> 1. Sort the scores
> 2. If even count: average the two middle values
> 3. If odd count: take the middle value
> 
> Example: [70, 80, 90, 100] → median is (80+90)/2 = 85"

**MODE (Most Frequent):**
```java
public Double calculateMode(List<Double> scores) {
    Map<Double, Long> frequencyMap = scores.stream()
        .collect(Collectors.groupingBy(score -> score, Collectors.counting()));
    
    long maxFrequency = frequencyMap.values().stream()
        .max(Long::compare)
        .orElse(0L);
    
    return frequencyMap.entrySet().stream()
        .filter(entry -> entry.getValue() == maxFrequency)
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(0.0);
}
```
> "This is more complex:
> 1. `groupingBy` creates a frequency map: {85.0=3, 90.0=2}
> 2. Find the maximum frequency
> 3. Return the score with that frequency
> 
> If all scores appear once, it returns the first one."

---

**File:** `service/StudentService.java`

**What to Say:**
> "This is the main business logic service. It follows these principles:
> - **Dependency Injection**: Uses constructor injection (via `@RequiredArgsConstructor`)
> - **Transaction Management**: `@Transactional` ensures database consistency
> - **DTO Mapping**: Converts between entities and DTOs
>
> Let's look at the main methods:"

**CREATE:**
```java
@Transactional
public StudentResponse createStudent(StudentRequest request) {
    Student student = new Student();
    // Copy properties from request to entity
    student.setName(request.getName());
    // ... set all scores
    
    Student savedStudent = studentRepository.save(student);
    return mapToResponse(savedStudent);
}
```
> "`@Transactional` means if anything fails, the database rolls back. We never expose entities directly - we use DTOs."

**REPORTS:**
```java
public Page<StudentReportResponse> getStudentReports(String name, Pageable pageable) {
    Page<Student> students;
    
    if (name != null && !name.isBlank()) {
        students = studentRepository.findByNameContainingIgnoreCase(name, pageable);
    } else {
        students = studentRepository.findAll(pageable);
    }
    
    return students.map(this::mapToReportResponse);
}
```
> "This supports both filtering and pagination. The `Page` object contains:
> - The actual data
> - Total pages
> - Total elements
> - Current page number
> 
> We transform each Student entity into a StudentReportResponse with statistics."

---

### Layer 4: DTOs (Data Transfer Objects)

**What to Say:**
> "DTOs serve three purposes:
> 1. **Validation** - ensure data is correct before it hits the database
> 2. **Security** - don't expose internal entity structure
> 3. **Flexibility** - API can change without changing database
>
> For example, `StudentRequest` has validation:"

```java
@NotBlank(message = "Name is required")
private String name;

@Min(value = 0, message = "Math score must be between 0 and 100")
@Max(value = 100, message = "Math score must be between 0 and 100")
private Double mathScore;
```

> "If a request violates these rules, Spring automatically returns a 400 error with clear messages."

---

### Layer 5: Controller (REST API Layer)

**File:** `controller/StudentController.java`

**What to Say:**
> "This is where HTTP requests come in. I used REST best practices:
> - POST for creation (returns 201 Created)
> - GET for retrieval (returns 200 OK)
> - Proper error responses
>
> The pagination endpoint shows good API design:"

```java
@GetMapping("/reports")
public ResponseEntity<Page<StudentReportResponse>> getStudentReports(
    @RequestParam(required = false) String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDir
)
```

> "Clients can:
> - Filter: `?name=John`
> - Paginate: `?page=0&size=10`
> - Sort: `?sortBy=name&sortDir=DESC`
> - Combine: `?name=John&page=0&size=5&sortBy=meanScore&sortDir=DESC`
>
> This prevents loading thousands of records at once."

---

### Layer 6: Exception Handling

**File:** `exception/GlobalExceptionHandler.java`

**What to Say:**
> "Instead of try-catch everywhere, I use centralized exception handling:
> - `@RestControllerAdvice` catches exceptions from all controllers
> - Returns consistent error responses
> - Different handlers for different exception types
>
> This makes the API predictable and professional."

---

## ARCHITECTURE EXPLANATION

### 3-Layer Architecture

```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│  (Controllers + DTOs)               │
│  - REST endpoints                   │
│  - Request validation               │
│  - Response formatting              │
└─────────────────────────────────────┘
              ↓↑
┌─────────────────────────────────────┐
│     Business Logic Layer            │
│  (Services)                         │
│  - StudentService                   │
│  - StatisticsService                │
│  - Business rules                   │
└─────────────────────────────────────┘
              ↓↑
┌─────────────────────────────────────┐
│     Data Access Layer               │
│  (Repository + Entity)              │
│  - Database operations              │
│  - JPA/Hibernate                    │
└─────────────────────────────────────┘
              ↓↑
┌─────────────────────────────────────┐
│     PostgreSQL Database             │
└─────────────────────────────────────┘
```

**What to Say:**
> "I followed the standard 3-layer architecture:
> 
> **Layer 1 - Presentation (Controller):**
> - Handles HTTP requests/responses
> - Input validation
> - No business logic here
>
> **Layer 2 - Business Logic (Service):**
> - Where the actual work happens
> - Calculations, transformations
> - Orchestrates data flow
>
> **Layer 3 - Data Access (Repository):**
> - Only talks to database
> - No business logic
> - Returns raw data
>
> **Benefits:**
> - Each layer has one job (Separation of Concerns)
> - Easy to test each layer independently
> - Can swap implementations (e.g., change database) without affecting other layers
> - Multiple controllers can use same service
> - Multiple services can use same repository"

---

## DESIGN CHOICES

### 1. Why Simple Entity Design?

**Question:** "Why did you put all scores in one table instead of normalizing?"

**Answer:**
> "I chose denormalization for this use case because:
> 
> **Pros:**
> - Simpler queries - no joins needed
> - Faster reads - one table lookup
> - Easier to understand and maintain
> - Fixed 5 subjects - no need for flexibility
> - Better performance for reports
>
> **When to Normalize:**
> - If subjects were dynamic (add/remove subjects)
> - If subjects had different properties
> - If we needed subject-level analysis
>
> **Normalized Alternative Would Be:**
> ```sql
> students (id, name)
> subjects (id, name)
> scores (student_id, subject_id, score)
> ```
> But this adds complexity we don't need for 5 fixed subjects."

---

### 2. Why DTOs Instead of Entities?

**Answer:**
> "DTOs (Data Transfer Objects) provide several benefits:
>
> **Security:**
> - Don't expose database IDs or internal structure
> - Can hide sensitive fields
>
> **Validation:**
> - Validate at API boundary
> - Prevent invalid data from reaching database
>
> **Flexibility:**
> - API structure independent of database
> - Can change one without changing the other
>
> **Example:**
> - Entity has `createdAt` timestamp - not in response
> - Response has calculated fields (mean, median) - not in entity
> - Request has different validation than entity"

---

### 3. Why Two Separate Services?

**Answer:**
> "I split StudentService and StatisticsService for:
>
> **Single Responsibility Principle:**
> - StatisticsService: only does calculations
> - StudentService: only manages students
>
> **Reusability:**
> - Other services could use StatisticsService
> - Can test statistics independently
>
> **Maintainability:**
> - If calculation logic changes, only one file to modify
> - Clear separation of concerns"

---

### 4. Why Pagination?

**Answer:**
> "Pagination is critical for performance:
>
> **Without Pagination:**
> ```java
> List<Student> students = repository.findAll(); // Gets EVERYTHING
> ```
> - With 10,000 students, loads all into memory
> - Network transfer of huge JSON
> - Client UI freezes
>
> **With Pagination:**
> ```java
> Page<Student> students = repository.findAll(PageRequest.of(0, 10));
> ```
> - Only fetches 10 records
> - Database uses LIMIT/OFFSET
> - Controlled memory usage
> - Better user experience
>
> **Real-world example:**
> - Google search shows 10 results per page
> - Imagine if it loaded all results at once!"

---

## TEST COVERAGE

### Unit Tests Strategy

**What to Say:**
> "I wrote unit tests for the service layer because that's where the business logic is. I used:
> - **JUnit 5**: Modern testing framework
> - **Mockito**: To mock dependencies
> - **Arrange-Act-Assert**: Clear test structure"

### StatisticsService Tests

**Example Test:**
```java
@Test
void testCalculateMean() {
    List<Double> scores = Arrays.asList(80.0, 90.0, 70.0, 85.0, 95.0);
    Double mean = statisticsService.calculateMean(scores);
    assertEquals(84.0, mean);
}
```

**What to Say:**
> "This tests:
> 1. **Arrange**: Create input data
> 2. **Act**: Call the method
> 3. **Assert**: Verify result
>
> I tested edge cases:
> - Empty list
> - Single value
> - Odd/even number of values
> - All same values"

### StudentService Tests (with Mocking)

**Example:**
```java
@Mock
private StudentRepository studentRepository;

@Mock
private StatisticsService statisticsService;

@InjectMocks
private StudentService studentService;
```

**What to Say:**
> "Mocking is crucial for unit tests:
> - We don't want to hit the real database
> - Tests run faster
> - Tests are isolated
>
> `@Mock` creates fake objects
> `@InjectMocks` injects mocks into the service
>
> Then I tell the mocks what to return:"
```java
when(studentRepository.save(any())).thenReturn(student);
```
> "This means: 'When save is called with any argument, return this student object'
>
> I verify the mock was called:"
```java
verify(studentRepository, times(1)).save(any());
```

**Test Coverage:**
- ✅ Create student
- ✅ Get all reports
- ✅ Get single report
- ✅ Filter by name
- ✅ Student not found scenario
- ✅ All statistical calculations
- ✅ Edge cases (empty, null)

---

## EDGE CASES & PERFORMANCE

### Edge Cases Handled

**1. Empty Data**
```java
if (scores == null || scores.isEmpty()) {
    return 0.0;
}
```
> "Always check for null and empty collections to avoid NullPointerException."

**2. Invalid Scores**
```java
@Min(value = 0, message = "Score must be between 0 and 100")
@Max(value = 100, message = "Score must be between 0 and 100")
```
> "Validation at API level prevents bad data from entering the system."

**3. Not Found**
```java
Student student = studentRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Student not found"));
```
> "Returns proper 404 error instead of crashing."

**4. Pagination Limits**
> "Used default page size of 10 to prevent accidental large queries."

---

### Performance Considerations

**1. Database Queries**
```java
@Transactional(readOnly = true)
public Page<StudentReportResponse> getStudentReports(...) {
```
> "`readOnly = true` tells the database this is just a SELECT, allowing optimizations."

**2. Stream Operations**
```java
scores.stream()
    .mapToDouble(Double::doubleValue)  // Primitive streams are faster
    .average()
```
> "Using primitive streams avoids boxing/unboxing overhead."

**3. Pagination**
```sql
SELECT * FROM students LIMIT 10 OFFSET 0;
```
> "Database only loads 10 records, not all of them."

**4. Indexing**
> "The `id` field is automatically indexed as PRIMARY KEY, making lookups fast."

---

### Performance Trade-offs

**What I Chose:**
- Denormalized schema → Faster reads, slower writes (but we read more)
- Calculated statistics on-demand → No stale data, slightly slower response

**Alternative:**
- Pre-calculate and store mean/median/mode → Faster API but must recalculate on update

**Why My Choice:**
> "For 5 scores, calculating mean/median/mode is trivial (milliseconds). Pre-calculating adds complexity for minimal gain. If we had 100 subjects, I'd reconsider."

---

## DATA MODELING

### Current Schema

```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    math_score DOUBLE PRECISION NOT NULL,
    english_score DOUBLE PRECISION NOT NULL,
    science_score DOUBLE PRECISION NOT NULL,
    history_score DOUBLE PRECISION NOT NULL,
    geography_score DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

### Why This Design?

**What to Say:**
> "I chose a wide table design because:
>
> **Advantages:**
> 1. **Simple queries**: One SELECT gets all data
> 2. **Fast reads**: No joins needed
> 3. **Fixed subjects**: 5 subjects won't change
> 4. **Type safety**: Each score is a typed column
> 5. **Easy constraints**: Can add CHECK constraints per subject
>
> **Disadvantages:**
> 1. **Rigid**: Can't easily add new subjects
> 2. **Repetitive**: Score validation repeated 5 times
> 3. **Wide table**: More columns
>
> **When I'd Normalize:**
> If requirements included:
> - Dynamic subjects (add Biology, remove Geography)
> - Subject metadata (max score varies)
> - Subject-level analysis
> - Thousands of subjects"

---

### Normalized Alternative

```sql
-- Normalized design (if requirements were different)
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE scores (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT REFERENCES students(id),
    subject_id BIGINT REFERENCES subjects(id),
    score DOUBLE PRECISION NOT NULL CHECK (score >= 0 AND score <= 100),
    UNIQUE(student_id, subject_id)
);
```

**Trade-offs:**
> "**Normalized Advantages:**
> - Can add/remove subjects dynamically
> - Better for subject-level reporting
> - Follows normal forms
>
> **Normalized Disadvantages:**
> - Requires JOIN for student report
> - More complex queries
> - Slightly slower for our use case
>
> I chose denormalized because the requirements specified '5 subjects' - implying fixed."

---

### Data Integrity

**Constraints I Used:**
```java
@Column(nullable = false)  // NOT NULL in database
private String name;
```

**Validation:**
```java
@Min(0) @Max(100)  // Application level
```

**What to Say:**
> "I use defense in depth:
> 1. **Application validation**: Catches errors before database
> 2. **NOT NULL constraints**: Database-level safety
> 3. **Data types**: DOUBLE PRECISION ensures valid numbers
> 4. **Primary key**: Ensures unique students"

---

## PRACTICE QUESTIONS & ANSWERS

### Question 1: "Why Spring Boot?"

**Answer:**
> "Spring Boot provides:
> - **Auto-configuration**: Less boilerplate
> - **Embedded server**: No need to deploy to Tomcat separately
> - **Dependency management**: Handles compatible versions
> - **Production-ready**: Built-in health checks, metrics
> - **Large ecosystem**: Integrates with everything
>
> Alternative frameworks:
> - **Quarkus**: Better for microservices, faster startup
> - **Micronaut**: Lower memory footprint
> - **Plain Spring**: More control but more configuration
>
> I chose Spring Boot because it's industry-standard and handles common concerns."

---

### Question 2: "How would you handle 1 million students?"

**Answer:**
> "Several optimizations needed:
>
> **1. Database:**
> - Add indexes on frequently queried columns (name)
> - Use database partitioning by year/grade
> - Connection pooling (already have HikariCP)
>
> **2. Caching:**
> ```java
> @Cacheable("studentReports")
> public StudentReportResponse getStudentReport(Long id) {
> ```
> - Cache frequently accessed reports
> - Use Redis for distributed caching
>
> **3. Pagination:**
> - Already implemented
> - Enforce maximum page size
> - Consider cursor-based pagination for large offsets
>
> **4. Async Processing:**
> - Generate reports asynchronously
> - Use message queues for bulk operations
>
> **5. Database Optimization:**
> - Read replicas for reports
> - Write to master, read from replicas"

---

### Question 3: "What if scores could be updated?"

**Answer:**
> "I'd add:
>
> **1. Update Endpoint:**
> ```java
> @PutMapping("/{id}")
> public StudentResponse updateStudent(@PathVariable Long id, 
>                                      @Valid @RequestBody StudentRequest request)
> ```
>
> **2. Audit Trail:**
> ```java
> @Column
> private LocalDateTime updatedAt;
> 
> @PreUpdate
> protected void onUpdate() {
>     updatedAt = LocalDateTime.now();
> }
> ```
>
> **3. Version Control:**
> ```java
> @Version
> private Long version;  // Optimistic locking
> ```
> Prevents conflicting updates.
>
> **4. Change History:**
> Create `score_history` table to track all changes."

---

### Question 4: "How would you secure this API?"

**Answer:**
> "Multiple layers:
>
> **1. Authentication:**
> ```java
> @EnableWebSecurity
> public class SecurityConfig {
>     // JWT tokens or OAuth2
> }
> ```
>
> **2. Authorization:**
> ```java
> @PreAuthorize("hasRole('ADMIN')")
> public StudentResponse createStudent(...)
> ```
>
> **3. HTTPS:**
> - Enforce TLS in production
> - Redirect HTTP to HTTPS
>
> **4. Rate Limiting:**
> - Prevent abuse
> - Use Spring Cloud Gateway or nginx
>
> **5. Input Validation:**
> - Already have with `@Valid`
> - Prevents SQL injection (JPA does this)
>
> **6. CORS:**
> ```java
> @CrossOrigin(origins = "https://myapp.com")
> ```"

---

### Question 5: "Explain your testing strategy"

**Answer:**
> "Three-level testing pyramid:
>
> **1. Unit Tests (Most):**
> - Test individual methods
> - Mock dependencies
> - Fast, isolated
> - Examples: StatisticsServiceTest, StudentServiceTest
>
> **2. Integration Tests (Some):**
> - Test service + repository + database
> - Use H2 in-memory database
> - ```java
>   @SpringBootTest
>   @AutoConfigureTestDatabase
>   ```
>
> **3. End-to-End Tests (Few):**
> - Test full HTTP flow
> - ```java
>   @SpringBootTest(webEnvironment = RANDOM_PORT)
>   @AutoConfigureMockMvc
>   ```
>
> **Why this pyramid?**
> - Unit tests catch most bugs
> - Run in seconds
> - Easy to debug
> - Integration tests catch configuration issues
> - E2E tests catch UI/API contract issues"

---

### Question 6: "What design patterns did you use?"

**Answer:**
> "**1. Repository Pattern:**
> - Abstracts data access
> - `StudentRepository` interface
>
> **2. Service Layer Pattern:**
> - Business logic separation
> - `StudentService`, `StatisticsService`
>
> **3. DTO Pattern:**
> - API decoupling from entities
> - `StudentRequest`, `StudentResponse`
>
> **4. Dependency Injection:**
> - Constructor injection
> - Loose coupling
>
> **5. Builder Pattern:**
> - ```java
>   StudentResponse.builder()
>       .name("John")
>       .build();
>   ```
>
> **6. Strategy Pattern (could add):**
> - Different calculation strategies
> - ```java
>   interface CalculationStrategy {
>       Double calculate(List<Double> scores);
>   }
>   ```"

---

### Question 7: "How is this different from monolithic architecture?"

**Answer:**
> "**Current (Monolithic):**
> - Single deployable unit
> - All layers in one application
> - Shared database
>
> **Pros:**
> - Simple to develop and deploy
> - No network latency between layers
> - Easy to test
> - Good for small-medium apps
>
> **Cons:**
> - Can't scale parts independently
> - Large codebase over time
> - One bug can crash everything
>
> **Microservices Alternative:**
> ```
> Student Service → Student DB
> Statistics Service → (stateless)
> Report Service → Report DB
> API Gateway → Routes requests
> ```
>
> **When to use Microservices:**
> - Large teams
> - Need independent scaling
> - Different tech stacks per service
>
> **For this application:**
> Monolithic is perfect - simple requirements, single team."

---

## DOCKER EXPLANATION

### Dockerfile

**What to Say:**
> "I used multi-stage build for efficiency:
>
> **Stage 1 - Build:**
> ```dockerfile
> FROM maven:3.9.5-eclipse-temurin-17 AS build
> WORKDIR /app
> COPY pom.xml .
> RUN mvn dependency:go-offline  # Cache dependencies
> COPY src ./src
> RUN mvn clean package -DskipTests
> ```
> - Downloads dependencies first (cached layer)
> - Then compiles code
> - If code changes, dependencies don't re-download
>
> **Stage 2 - Runtime:**
> ```dockerfile
> FROM eclipse-temurin:17-jre-alpine
> COPY --from=build /app/target/*.jar app.jar
> ENTRYPOINT ["java", "-jar", "app.jar"]
> ```
> - Uses JRE (not JDK) - smaller image
> - Alpine Linux - minimal OS
> - Only includes the JAR, not source code
>
> **Benefits:**
> - Build image: ~500MB
> - Runtime image: ~150MB
> - Faster deployments"

### Docker Compose

**What to Say:**
> "docker-compose.yml orchestrates two services:
>
> **PostgreSQL:**
> ```yaml
> postgres:
>   image: postgres:15-alpine
>   environment:
>     POSTGRES_DB: studentdb
>   volumes:
>     - postgres_data:/var/lib/postgresql/data
>   healthcheck:
>     test: ["CMD-SHELL", "pg_isready -U postgres"]
> ```
> - Persistent volume so data survives restarts
> - Health check ensures it's ready before app starts
>
> **Application:**
> ```yaml
> app:
>   depends_on:
>     postgres:
>       condition: service_healthy
>   environment:
>     DB_HOST: postgres
> ```
> - Waits for database to be healthy
> - Uses service name 'postgres' as hostname
>
> **Why this matters:**
> - One command starts everything: `docker-compose up`
> - Reproducible environment
> - Same on dev, test, production"

---

## FINAL TIPS

### How to Present

1. **Start High-Level:**
   > "This is a 3-layer Spring Boot application for managing student scores..."

2. **Drill Down:**
   > "Let me show you how a request flows through the system..."

3. **Show Code:**
   Open files and explain as you go

4. **Be Honest:**
   > "I chose simple design because requirements didn't need complexity. In production with changing requirements, I'd consider..."

5. **Show Trade-offs:**
   > "I chose X over Y because..."

### What They're Looking For

✅ **Understanding** - Not just "it works" but WHY it works
✅ **Trade-offs** - Every decision has pros/cons
✅ **Alternatives** - What else could you have done?
✅ **Scalability** - What if requirements change?
✅ **Testing** - How do you ensure quality?
✅ **Best Practices** - Industry standards

### Red Flags to Avoid

❌ "I don't know why I did it that way"
❌ "I just copied from StackOverflow"
❌ "This is the only way to do it"
❌ "I didn't think about testing"
❌ "Performance doesn't matter"

### Green Flags to Show

✅ "I chose this because..."
✅ "The trade-off is..."
✅ "Another approach would be..."
✅ "I tested these edge cases..."
✅ "For production, I'd also consider..."

---

## QUICK REFERENCE

### Key Files to Know
- `AssessmentApplication.java` - Entry point
- `Student.java` - Entity model
- `StudentRepository.java` - Data access
- `StatisticsService.java` - Calculations
- `StudentService.java` - Business logic
- `StudentController.java` - REST API
- `application.properties` - Configuration
- `docker-compose.yml` - Deployment
- Tests - Quality assurance

### Key Concepts
- **Layers**: Controller → Service → Repository → Database
- **Patterns**: Repository, Service, DTO, DI, Builder
- **Validation**: Bean Validation, NOT NULL constraints
- **Pagination**: Prevents loading all data
- **Statistics**: Mean, Median, Mode calculations
- **Testing**: Unit tests with mocks
- **Docker**: Multi-stage build, compose orchestration

### One-Sentence Summary
> "A simple, layered Spring Boot REST API with PostgreSQL that manages student scores, calculates statistics (mean, median, mode), supports pagination and filtering, includes comprehensive tests, and runs in Docker containers."

Good luck with your oral defense! 🚀

