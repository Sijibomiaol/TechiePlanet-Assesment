# DATABASES AND SQL - ANSWERS

## QUESTION 1

**Given the following data definition, select all queries that return the second largest salary.**

TABLE emp:
- id INTEGER PRIMARY KEY
- name VARCHAR2(30) NOT NULL
- salary NUMBER

Example: Employees A, B, C with salaries $100, $80, $100 respectively. Second highest salary is $80.

### CORRECT QUERIES:

**Option 1: ✅ CORRECT**
```sql
SELECT DISTINCT(salary) FROM emp ORDER BY salary DESC LIMIT 1 OFFSET 1;
```
This gets distinct salaries, orders descending, skips the highest, and returns the second highest.

**Option 2: ✅ CORRECT**
```sql
SELECT MAX(salary) FROM emp WHERE salary < (SELECT MAX(salary) FROM emp);
```
This finds the maximum salary that is less than the overall maximum salary.

**Option 3: ✅ CORRECT**
```sql
SELECT salary FROM (SELECT DISTINCT salary FROM emp ORDER BY salary DESC LIMIT 2) AS emp ORDER BY salary LIMIT 1;
```
Inner query gets top 2 distinct salaries descending. Outer query orders ascending and takes the first (smallest of the two).

### INCORRECT QUERIES:

**Option 4: ❌ INCORRECT**
```sql
SELECT DISTINCT salary FROM (SELECT salary FROM emp ORDER BY salary DESC LIMIT 2) AS emp ORDER BY salary LIMIT 1;
```
This fails when there are duplicate max salaries. Inner query gets top 2 rows (could be 100, 100), then DISTINCT would only return 100, not 80.

**Option 5: ❌ INCORRECT**
```sql
SELECT salary FROM emp ORDER BY salary DESC OFFSET 1 LIMIT 1;
```
This doesn't use DISTINCT, so with salaries 100, 100, 80, it returns the second row (100) instead of the second highest unique salary (80).

---

## QUESTION 2

**Select the country where the games took place each year.**

Assuming a table structure like:
```sql
TABLE olympics (
    year INTEGER,
    country VARCHAR(50),
    city VARCHAR(50)
)
```

**Query:**
```sql
SELECT year, country
FROM olympics
ORDER BY year;
```

Or with more details:
```sql
SELECT year, country, city
FROM olympics
ORDER BY year ASC;
```

---

## QUESTION 3

**Explain how SQL LEFT and RIGHT JOIN statements function in queries.**

### LEFT JOIN (LEFT OUTER JOIN)

Returns all records from the left table, and matched records from the right table. If no match exists, NULL values are returned for right table columns.

**Example Tables:**

**employees**
| id | name    | dept_id |
|----|---------|---------|
| 1  | Alice   | 10      |
| 2  | Bob     | 20      |
| 3  | Charlie | NULL    |

**departments**
| dept_id | dept_name |
|---------|-----------|
| 10      | Sales     |
| 20      | IT        |
| 30      | HR        |

**LEFT JOIN Query:**
```sql
SELECT employees.name, departments.dept_name
FROM employees
LEFT JOIN departments ON employees.dept_id = departments.dept_id;
```

**Result:**
| name    | dept_name |
|---------|-----------|
| Alice   | Sales     |
| Bob     | IT        |
| Charlie | NULL      |

All employees are returned, even Charlie who has no department.

### RIGHT JOIN (RIGHT OUTER JOIN)

Returns all records from the right table, and matched records from the left table. If no match exists, NULL values are returned for left table columns.

**RIGHT JOIN Query:**
```sql
SELECT employees.name, departments.dept_name
FROM employees
RIGHT JOIN departments ON employees.dept_id = departments.dept_id;
```

**Result:**
| name  | dept_name |
|-------|-----------|
| Alice | Sales     |
| Bob   | IT        |
| NULL  | HR        |

All departments are returned, even HR which has no employees.

### Summary:
- **LEFT JOIN**: Keeps all rows from the LEFT table
- **RIGHT JOIN**: Keeps all rows from the RIGHT table

---

## QUESTION 4

**App usage data query: Select userId and average session duration for each user with more than one session.**

### Query:
```sql
SELECT userId, AVG(duration) AS AverageDuration
FROM sessions
GROUP BY userId
HAVING COUNT(*) > 1;
```

### Explanation:
1. `GROUP BY userId` - Groups all sessions by user
2. `AVG(duration)` - Calculates average duration for each user
3. `HAVING COUNT(*) > 1` - Filters to only users with more than one session
4. `AS AverageDuration` - Names the calculated column

### Result with sample data:
```
userId    AverageDuration
------------------------
1         12.00
```

User 1 has sessions with durations 10 and 14, average = (10+14)/2 = 12
User 2 has only 1 session (duration 18), so excluded by HAVING clause.

