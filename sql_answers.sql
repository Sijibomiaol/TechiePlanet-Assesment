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

-- LEFT JOIN
SELECT customers.customer_id, customers.customer_name, orders.order_id, orders.order_date
FROM customers
LEFT JOIN orders ON customers.customer_id = orders.customer_id;

-- RIGHT JOIN
SELECT customers.customer_id, customers.customer_name, orders.order_id, orders.order_date
FROM customers
RIGHT JOIN orders ON customers.customer_id = orders.customer_id;
