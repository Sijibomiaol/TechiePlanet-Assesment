-- QUESTION 1: Second largest salary queries

-- Query 1
SELECT DISTINCT(salary) 
FROM emp 
ORDER BY salary DESC 
LIMIT 1 OFFSET 1;

-- Query 2
SELECT MAX(salary) 
FROM emp 
WHERE salary < (SELECT MAX(salary) FROM emp);

-- Query 3
SELECT salary 
FROM (SELECT DISTINCT salary FROM emp ORDER BY salary DESC LIMIT 2) AS emp 
ORDER BY salary 
LIMIT 1;

-- Query 4
SELECT DISTINCT salary 
FROM (SELECT salary FROM emp ORDER BY salary DESC LIMIT 2) AS emp 
ORDER BY salary 
LIMIT 1;


-- QUESTION 2: Country where games took place each year

SELECT games.yr AS year, city.country 
FROM games 
JOIN city ON games.city = city.name 
ORDER BY games.yr;


-- QUESTION 3: LEFT and RIGHT JOIN

-- LEFT JOIN: Returns all records from the LEFT table (games), and matched records from the RIGHT table (city).
-- If no match, NULL values are returned for city table columns.
SELECT games.yr, games.city, city.country
FROM games
LEFT JOIN city ON games.city = city.name;

-- RIGHT JOIN: Returns all records from the RIGHT table (city), and matched records from the LEFT table (games).
-- If no match, NULL values are returned for games table columns.
SELECT games.yr, games.city, city.country
FROM games
RIGHT JOIN city ON games.city = city.name;


-- QUESTION 4: Average session duration for users with more than one session

SELECT userId, AVG(duration) AS AverageDuration
FROM sessions
GROUP BY userId
HAVING COUNT(*) > 1;
