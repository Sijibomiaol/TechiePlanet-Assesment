# Practice Questions for Oral Defense

## How to Use This
Practice answering these out loud. Time yourself. Aim for 1-2 minute answers.

---

## SECTION 1: CODE WALKTHROUGH (Easy)

### Q1: "Walk me through your project structure"
**Practice saying:**
> "My project follows a standard 3-layer architecture..."

**Include:**
- Controller layer (REST)
- Service layer (business logic)
- Repository layer (data access)
- Supporting: DTOs, entities, config

---

### Q2: "Show me how you handle a POST request to create a student"
**Trace the path:**
1. HTTP POST → StudentController
2. @Valid validates StudentRequest
3. Controller calls StudentService
4. Service creates entity and saves via repository
5. Maps to response DTO
6. Returns 201 Created

---

### Q3: "Explain your Student entity"
**Key points:**
- JPA @Entity annotation
- @Id for primary key
- @Column for fields
- @PrePersist for timestamps
- Lombok reduces boilerplate

---

### Q4: "How does pagination work?"
**Explain:**
- `Pageable` parameter
- `PageRequest.of(page, size, sort)`
- Database uses LIMIT/OFFSET
- Returns `Page<T>` with metadata
- Prevents loading all records

---

### Q5: "Show me your validation"
**Show in StudentRequest:**
```java
@NotBlank(message = "Name is required")
@Min(0) @Max(100)
```
**Explain:**
- Jakarta Bean Validation
- Automatic error messages
- Returns 400 if invalid

---

## SECTION 2: ARCHITECTURE (Medium)

### Q6: "Why did you separate services?"
**Answer:**
- Single Responsibility Principle
- StatisticsService only does math
- StudentService manages students
- Reusability
- Easier testing

---

### Q7: "Explain your layered architecture"
**Draw/describe:**
```
Controller → Service → Repository → Database
    ↓           ↓          ↓
  HTTP      Business    Data
  Layer      Logic     Access
```
**Benefits:**
- Separation of concerns
- Testability
- Maintainability

---

### Q8: "Why use DTOs instead of exposing entities?"
**Three reasons:**
1. **Security**: Don't expose internal structure
2. **Validation**: Validate at API boundary
3. **Flexibility**: API independent of database

---

### Q9: "How would you add caching?"
**Answer:**
```java
@Cacheable("studentReports")
public StudentReportResponse getStudentReport(Long id)
```
- Spring Cache abstraction
- Use Redis for distributed cache
- Cache frequently accessed data
- Invalidate on updates

---

### Q10: "What design patterns did you use?"
**List:**
1. Repository Pattern - data access
2. Service Layer Pattern - business logic
3. DTO Pattern - data transfer
4. Dependency Injection - loose coupling
5. Builder Pattern - object creation

---

## SECTION 3: ALGORITHMS (Medium)

### Q11: "Explain your mean calculation"
**Code:**
```java
scores.stream()
    .mapToDouble(Double::doubleValue)
    .average()
    .orElse(0.0);
```
**Explain:**
- Java Streams
- Convert to primitive double
- Built-in average()
- Handle empty case

---

### Q12: "Explain median calculation"
**Steps:**
1. Sort the list
2. If even count: average two middle values
3. If odd count: take middle value
**Example:** [70, 80, 90, 100] → (80+90)/2 = 85

---

### Q13: "Explain mode calculation"
**Steps:**
1. Create frequency map: {score → count}
2. Find maximum frequency
3. Return score with max frequency
**Example:** [80, 90, 80, 70] → 80 appears twice (mode)

---

### Q14: "What if there are multiple modes?"
**Answer:**
- Current: returns first one found
- Could be enhanced to return List<Double>
- Or return all scores if no clear mode
- Depends on business requirements

---

### Q15: "How do you handle edge cases?"
**Examples:**
- Empty lists → return 0.0
- Null checks → `if (scores == null)`
- Validation → @Min @Max
- Not found → throw exception with 404

---

## SECTION 4: DATABASE (Medium-Hard)

### Q16: "Why denormalized schema?"
**Answer:**
- Fixed 5 subjects
- Simpler queries (no joins)
- Faster reads
- Trade-off: less flexible
**When to normalize:** Dynamic subjects

---

### Q17: "How would you normalize this?"
**Show alternative:**
```sql
students (id, name)
subjects (id, name)
scores (student_id, subject_id, score)
```
**Trade-offs:**
- Pros: Flexible, normalized
- Cons: Requires joins, more complex

---

### Q18: "How do you ensure data integrity?"
**Answer:**
1. NOT NULL constraints (@Column)
2. Validation (@Min @Max)
3. Primary keys (uniqueness)
4. Type safety (DOUBLE PRECISION)
5. Foreign keys (if normalized)

---

### Q19: "What indexes would you add for 1M students?"
**Answer:**
```sql
CREATE INDEX idx_name ON students(name);
CREATE INDEX idx_created_at ON students(created_at);
```
- Name for filtering
- Created_at for sorting
- Primary key already indexed

---

### Q20: "Explain @Transactional"
**Answer:**
- Ensures atomic operations
- If error → rollback
- `readOnly=true` for reads (optimization)
- Prevents partial updates
**Example:** All saves succeed or all fail

---

## SECTION 5: TESTING (Medium)

### Q21: "Why unit tests over integration tests?"
**Answer:**
- **Speed**: Milliseconds vs seconds
- **Isolation**: Test one thing
- **Debugging**: Easy to find bugs
- **Coverage**: Can test edge cases easily
**Pyramid:** Many unit, some integration, few E2E

---

### Q22: "Explain your mocking strategy"
**Answer:**
```java
@Mock
private StudentRepository repository;

@InjectMocks
private StudentService service;
```
- Mock dependencies
- Don't hit real database
- Control return values
- Verify interactions

---

### Q23: "What edge cases did you test?"
**List:**
- Empty lists
- Null values
- Single element
- All same values
- Odd/even counts
- Not found scenarios
- Invalid input

---

### Q24: "How would you test the controller?"
**Answer:**
```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
```
- Use MockMvc
- Test full HTTP flow
- Verify status codes
- Check response JSON

---

## SECTION 6: PERFORMANCE & SCALABILITY (Hard)

### Q25: "How would you handle 1 million students?"
**Answer:**
1. **Database**: Indexes, partitioning
2. **Caching**: Redis for reports
3. **Pagination**: Already have it
4. **Read replicas**: Scale reads
5. **Async**: Background processing
6. **Load balancing**: Multiple instances

---

### Q26: "What's the time complexity of your calculations?"
**Answer:**
- **Mean**: O(n) - one pass
- **Median**: O(n log n) - sorting
- **Mode**: O(n) - one pass with map
- **For 5 scores**: All effectively O(1)

---

### Q27: "How would you optimize report generation?"
**Answer:**
1. **Pre-calculate**: Store mean/median/mode
2. **Async**: Generate in background
3. **Cache**: Store reports in Redis
4. **Database**: Materialized views
5. **Trade-off**: Storage vs compute

---

### Q28: "What if calculations are slow?"
**Answer:**
1. **Profile first**: Find bottleneck
2. **Parallel streams**: `parallelStream()`
3. **Database**: Calculate in SQL
4. **Batch**: Process multiple students together
5. **Cache**: Don't recalculate

---

### Q29: "Explain your pagination strategy"
**Answer:**
- **Offset-based**: LIMIT 10 OFFSET 20
- **Pros**: Simple, jump to any page
- **Cons**: Slow for large offsets
- **Alternative**: Cursor-based pagination
- **When**: For infinite scroll

---

### Q30: "How do you prevent database connection exhaustion?"
**Answer:**
- **HikariCP**: Connection pooling (built-in)
- **Max pool size**: Limit connections
- **Timeouts**: Close idle connections
- **@Transactional**: Ensure connections released
- **Monitor**: Connection usage

---

## SECTION 7: DOCKER & DEPLOYMENT (Medium)

### Q31: "Explain your Dockerfile"
**Key points:**
- Multi-stage build
- Stage 1: Maven builds JAR
- Stage 2: JRE runs JAR
- Benefit: Smaller image (150MB vs 500MB)

---

### Q32: "Why docker-compose?"
**Answer:**
- **Orchestration**: Multiple services
- **Dependencies**: App waits for DB
- **Networking**: Services find each other
- **Volumes**: Data persistence
- **Environment**: Consistent across machines

---

### Q33: "How do you handle database initialization?"
**Answer:**
```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready"]
depends_on:
  condition: service_healthy
```
- Health check ensures DB ready
- App starts only when healthy

---

### Q34: "How would you deploy to production?"
**Answer:**
1. **Container registry**: Push to Docker Hub/ECR
2. **Orchestration**: Kubernetes/ECS
3. **Database**: RDS/Cloud SQL (managed)
4. **Secrets**: Environment variables/Vault
5. **Monitoring**: Prometheus/CloudWatch
6. **Load balancer**: Distribute traffic

---

### Q35: "What's your rollback strategy?"
**Answer:**
1. **Version images**: Tag with git hash
2. **Keep old images**: Don't delete
3. **Database migrations**: Backward compatible
4. **Blue-green deployment**: Two environments
5. **Rollback command**: Deploy old version

---

## SECTION 8: SECURITY (Medium)

### Q36: "How would you secure this API?"
**Answer:**
1. **Authentication**: JWT tokens
2. **Authorization**: Role-based access
3. **HTTPS**: TLS encryption
4. **Rate limiting**: Prevent abuse
5. **Input validation**: Already have it

---

### Q37: "How do you prevent SQL injection?"
**Answer:**
- **JPA/Hibernate**: Uses parameterized queries
- **Never**: String concatenation for SQL
- **Prepared statements**: Under the hood
**Example:**
```java
// JPA does this internally
PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
ps.setLong(1, id);
```

---

### Q38: "What about API rate limiting?"
**Answer:**
- **Spring**: Bucket4j library
- **Nginx**: Rate limit at proxy
- **API Gateway**: AWS API Gateway
**Example:**
```java
@RateLimiter(name = "api")
public ResponseEntity<?> endpoint()
```

---

## BONUS: BEHAVIORAL QUESTIONS

### Q39: "Why did you choose this approach?"
**Framework:**
1. **Context**: Requirements specified...
2. **Options**: I considered A, B, C...
3. **Decision**: I chose B because...
4. **Trade-offs**: Pros are X, cons are Y...
5. **Alternatives**: In production I'd also consider...

---

### Q40: "What would you do differently?"
**Be honest:**
> "If I had more time, I'd:
> - Add integration tests
> - Implement caching
> - Create separate DTOs for create/update
> - Add audit logging
> - Optimize with database views
> 
> But I prioritized core functionality and kept it simple as requested."

---

## PRACTICE DRILL

### 5-Minute Presentation
Practice explaining your entire project in 5 minutes:

**Minute 1:** Overview
- What it does
- Tech stack
- Architecture

**Minute 2:** Code structure
- Layers
- Key classes
- Flow

**Minute 3:** Key features
- Pagination
- Statistics
- Validation

**Minute 4:** Testing & Quality
- Unit tests
- Edge cases
- Design patterns

**Minute 5:** Deployment
- Docker
- How to run
- What you'd improve

---

## CONFIDENCE BUILDERS

### Things You Did Well ✅
- Clean 3-layer architecture
- Proper separation of concerns
- Good validation
- Comprehensive tests
- Docker containerization
- Swagger documentation
- Following best practices

### Be Ready to Say
- "I chose simplicity because..."
- "The trade-off is..."
- "Another approach would be..."
- "In production, I'd add..."
- "I tested for..."

### Don't Worry About
- Not implementing everything
- Having a simple design
- Choosing denormalization
- Not having 100% coverage

**Remember:** They want to see how you think, not perfection!

Good luck! 🚀

