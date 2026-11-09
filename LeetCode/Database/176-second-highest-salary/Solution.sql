-- LeetCode 176 - Second Highest Salary
-- 
-- Table: Employee
-- +-------------+------+
-- | Column Name | Type |
-- +-------------+------+
-- | id          | int  |
-- | salary      | int  |
-- +-------------+------+
-- id is the primary key (column with unique values) for this table.
-- Each row of this table contains information about the salary of an employee.
-- 
-- Write a solution to find the second highest salary from the Employee table.
-- If there is no second highest salary, return null.

-- Approach 1: Subquery with LIMIT/OFFSET (MySQL/PostgreSQL)
SELECT 
    (SELECT DISTINCT salary 
     FROM Employee 
     ORDER BY salary DESC 
     LIMIT 1 OFFSET 1) AS SecondHighestSalary;

-- Approach 2: Using MAX with subquery (Standard SQL)
SELECT MAX(salary) AS SecondHighestSalary
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);

-- Approach 3: Using DENSE_RANK (Window function - PostgreSQL, SQL Server, Oracle)
SELECT DISTINCT salary AS SecondHighestSalary
FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as rnk
    FROM Employee
) t
WHERE rnk = 2;

-- Approach 4: Using ROW_NUMBER (Window function)
SELECT salary AS SecondHighestSalary
FROM (
    SELECT salary, ROW_NUMBER() OVER (ORDER BY salary DESC) as rn
    FROM (SELECT DISTINCT salary FROM Employee) t
) t2
WHERE rn = 2;

-- Approach 5: Self JOIN (Old school)
SELECT MAX(e1.salary) AS SecondHighestSalary
FROM Employee e1
JOIN Employee e2 ON e1.salary < e2.salary;

-- Approach 6: Using IFNULL/COALESCE for explicit NULL handling (MySQL)
SELECT IFNULL(
    (SELECT DISTINCT salary 
     FROM Employee 
     ORDER BY salary DESC 
     LIMIT 1 OFFSET 1), 
    NULL) AS SecondHighestSalary;

-- Test data setup (for local testing)
/*
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    salary INT
);

INSERT INTO Employee (id, salary) VALUES
(1, 100),
(2, 200),
(3, 300);

-- Expected output: 200

-- Test case 2: Only one salary
INSERT INTO Employee (id, salary) VALUES (1, 100);
-- Expected output: NULL

-- Test case 3: Duplicate salaries
INSERT INTO Employee (id, salary) VALUES
(1, 100),
(2, 200),
(3, 200),
(4, 300);
-- Expected output: 200 (distinct second highest)
*/