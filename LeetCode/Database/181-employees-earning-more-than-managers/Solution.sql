-- LeetCode 181 - Employees Earning More Than Their Managers
-- 
-- Table: Employee
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | name        | varchar |
-- | salary      | int     |
-- | managerId   | int     |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- Each row of this table indicates the ID of an employee, their name, salary, and the ID of their manager.
-- 
-- Write a solution to find the employees who earn more than their managers.
-- Return the result table in any order.

-- Approach 1: Self JOIN (Standard solution)
SELECT e1.name AS Employee
FROM Employee e1
JOIN Employee e2 ON e1.managerId = e2.id
WHERE e1.salary > e2.salary;

-- Approach 2: Self JOIN with explicit columns
SELECT e.name AS Employee
FROM Employee e
INNER JOIN Employee m ON e.managerId = m.id
WHERE e.salary > m.salary;

-- Approach 3: Using subquery (Alternative)
SELECT name AS Employee
FROM Employee e
WHERE salary > (
    SELECT salary 
    FROM Employee m 
    WHERE m.id = e.managerId
);

-- Approach 4: Using EXISTS (Alternative)
SELECT name AS Employee
FROM Employee e
WHERE EXISTS (
    SELECT 1 
    FROM Employee m 
    WHERE m.id = e.managerId 
    AND e.salary > m.salary
);

-- Approach 5: Using CTE (Common Table Expression)
WITH ManagerSalaries AS (
    SELECT id, salary FROM Employee
)
SELECT e.name AS Employee
FROM Employee e
JOIN ManagerSalaries m ON e.managerId = m.id
WHERE e.salary > m.salary;

-- Test data setup (for local testing)
/*
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary INT,
    managerId INT
);

INSERT INTO Employee (id, name, salary, managerId) VALUES
(1, 'Joe', 70000, 3),
(2, 'Henry', 80000, 4),
(3, 'Sam', 60000, NULL),
(4, 'Max', 90000, NULL);

-- Expected output: Joe (70000 > 60000), Henry (80000 > 90000? No, 80000 < 90000)
-- Wait: Joe's manager is Sam (id=3, salary=60000), Joe earns 70000 > 60000 ✓
-- Henry's manager is Max (id=4, salary=90000), Henry earns 80000 < 90000 ✗
-- Expected: Joe
*/