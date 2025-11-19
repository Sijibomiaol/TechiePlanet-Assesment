# Quick Cheat Sheet - Before the Interview

## 🎯 One-Sentence Summary
> "A layered Spring Boot REST API that manages student scores across 5 subjects, calculates statistics (mean, median, mode), supports pagination/filtering, uses PostgreSQL, includes unit tests, and runs in Docker."

---

## 📁 File Structure (Know This!)

```
Controller → Service → Repository → Database
   ↓            ↓          ↓
 HTTP      Business    Data
 Layer      Logic     Access
```

**Key Files:**
- `StudentController.java` - REST endpoints
- `StudentService.java` - Business logic
- `StatisticsService.java` - Calculations
- `StudentRepository.java` - Database
- `Student.java` - Entity
- `StudentRequest/Response.java` - DTOs

---

## 🔄 Request Flow (Memorize This!)

```
1. POST /api/students
   ↓
2. StudentController receives JSON
   ↓
3. @Valid validates StudentRequest
   ↓
4. StudentService.createStudent()
   ↓
5. StudentRepository.save()
   ↓
6. PostgreSQL saves
   ↓
7. Return StudentResponse (201 Created)
```

---

## 📊 Statistics Explained (Simple!)

**Mean (Average):**
```
(85 + 90 + 78 + 88 + 92) ÷ 5 = 86.6
```

**Median (Middle):**
```
Sorted: [78, 85, 88, 90, 92]
Middle: 88
```

**Mode (Most Common):**
```
[80, 90, 80, 70, 80] → 80 appears most
```

---

## 🎨 Design Patterns (Know These 5!)

1. **Repository** - StudentRepository (data access)
2. **Service Layer** - StudentService (business logic)
3. **DTO** - StudentRequest/Response (API contracts)
4. **Dependency Injection** - @RequiredArgsConstructor
5. **Builder** - .builder().name("John").build()

---

## 🗄️ Database Design

**Why One Table?**
- ✅ Simple queries (no joins)
- ✅ Fixed 5 subjects
- ✅ Fast reads
- ❌ Can't add subjects easily

**Normalized Alternative (if asked):**
```sql
students (id, name)
subjects (id, name)  
scores (student_id, subject_id, score)
```
> "I chose denormalized because requirements specified 5 fixed subjects"

---

## ✅ Validation

```java
@NotBlank - Name required
@Min(0) @Max(100) - Score range
@Valid - Triggers validation
GlobalExceptionHandler - Returns 400 errors
```

---

## 📄 Pagination

**Why?**
> "Prevents loading 10,000 students into memory. Database uses LIMIT/OFFSET."

**How?**
```java
PageRequest.of(page=0, size=10, sort)
→ SELECT * FROM students LIMIT 10 OFFSET 0;
```

---

## 🧪 Testing

**What:**
- StatisticsServiceTest - Tests calculations
- StudentServiceTest - Tests business logic
- Uses Mockito to mock database

**Why Mocking?**
> "Don't hit real database. Tests run fast. Isolated testing."

```java
@Mock - Fake object
@InjectMocks - Inject fakes
when(...).thenReturn(...) - Control behavior
verify(...) - Check it was called
```

---

## 🐳 Docker

**Dockerfile:**
```
Stage 1: Maven builds JAR (500MB)
Stage 2: JRE runs JAR (150MB)
Result: Smaller image!
```

**Docker Compose:**
```yaml
PostgreSQL + App
Healthcheck ensures DB ready
Volumes for persistence
One command: docker-compose up
```

---

## 🚀 Trade-offs You Made

| Choice | Pro | Con | Alternative |
|--------|-----|-----|-------------|
| Denormalized | Fast reads | Rigid | Normalize with joins |
| Calculate on-demand | Always fresh | Slightly slower | Pre-calculate & store |
| Monolithic | Simple | Can't scale parts | Microservices |
| Pagination | Memory efficient | Extra code | Load everything (bad!) |

---

## 💡 If Asked "What Would You Add?"

1. **Caching** - Redis for reports
2. **Authentication** - JWT tokens
3. **Audit Log** - Track changes
4. **Versioning** - Optimistic locking
5. **Integration Tests** - Full flow testing
6. **Indexes** - On name column
7. **Async** - Background report generation

> "But I kept it simple as requested to focus on core functionality"

---

## 🎤 Opening Statement (Memorize!)

> "Hi! I built a Spring Boot REST API for managing student scores. It uses a 3-layer architecture:
> 
> - **Controller layer** handles HTTP requests with validation
> - **Service layer** contains business logic including mean, median, and mode calculations  
> - **Repository layer** talks to PostgreSQL
> 
> Key features include pagination for handling large datasets, comprehensive validation, unit tests with Mockito, and Docker containerization. The design prioritizes simplicity while following Spring Boot best practices."

---

## ❌ Don't Say

- "I don't know"
- "I just copied it"
- "This is the only way"
- "I didn't test it"

## ✅ Do Say

- "I chose X because..."
- "The trade-off is..."
- "Another approach would be..."
- "I tested these edge cases..."
- "In production I'd add..."

---

## 🔥 Most Likely Questions

1. **"Walk me through a request"** → Follow the flow above
2. **"Why this architecture?"** → Separation of concerns, testability
3. **"Explain pagination"** → Prevents loading everything, uses LIMIT
4. **"Why DTOs?"** → Security, validation, flexibility
5. **"How do you test?"** → Unit tests with Mockito
6. **"Database design?"** → Denormalized for simplicity
7. **"What would you improve?"** → Caching, auth, more tests
8. **"Explain Docker"** → Multi-stage build, compose orchestration

---

## 🧠 Before You Start

**Deep breath!**

**Remember:**
- You understand this code
- You made deliberate choices
- Simple is not bad
- They want to see how you think
- Be honest about trade-offs

**You got this! 🚀**

---

## 📝 Last Minute Checklist

- [ ] Can explain request flow
- [ ] Know what each layer does
- [ ] Understand mean/median/mode
- [ ] Can explain pagination
- [ ] Know why you used DTOs
- [ ] Can describe testing approach
- [ ] Understand Docker setup
- [ ] Ready to discuss trade-offs
- [ ] Have alternatives in mind
- [ ] Confident about choices

**Time to shine! 💪**

