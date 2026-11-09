-- LeetCode 177 - Nth Highest Salary
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
-- Write a solution to find the nth highest salary from the Employee table.
-- If there is no nth highest salary, return null.

-- Approach 1: Using LIMIT/OFFSET with parameter (MySQL/PostgreSQL)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    SET N = N - 1;
    RETURN (
        SELECT DISTINCT salary
        FROM Employee
        ORDER BY salary DESC
        LIMIT 1 OFFSET N
    );
END;

-- Approach 2: Using DENSE_RANK (Window function - PostgreSQL, SQL Server, Oracle)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    RETURN (
        SELECT DISTINCT salary
        FROM (
            SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as rnk
            FROM Employee
        ) t
        WHERE rnk = N
        LIMIT 1
    );
END;

-- Approach 3: Using ROW_NUMBER with distinct (Window function)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    RETURN (
        SELECT salary
        FROM (
            SELECT salary, ROW_NUMBER() OVER (ORDER BY salary DESC) as rn
            FROM (SELECT DISTINCT salary FROM Employee) t
        ) t2
        WHERE rn = N
        LIMIT 1
    );
END;

-- Approach 4: Correlated subquery (Standard SQL - works everywhere)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    RETURN (
        SELECT MAX(salary)
        FROM Employee e1
        WHERE (
            SELECT COUNT(DISTINCT salary)
            FROM Employee e2
            WHERE e2.salary > e1.salary
        ) = N - 1
    );
END;

-- Approach 5: Using variables (MySQL specific)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    DECLARE result INT;
    SET @rownum = 0;
    SELECT salary INTO result
    FROM (
        SELECT DISTINCT salary, @rownum := @rownum + 1 as rn
        FROM Employee
        ORDER BY salary DESC
    ) t
    WHERE rn = N
    LIMIT 1;
    RETURN result;
END;

-- Test data setup (for local testing)
/*
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    salary INT
);

INSERT INTO Employee (id, salary) VALUES
(1, 100),
(2, 200),
(3, 300),
(4, 400),
(5, 500);

-- Test cases:
-- N=1 -> 500 (highest)
-- N=2 -> 400 (second highest)
-- N=3 -> 300 (third highest)
-- N=5 -> 100 (fifth highest)
-- N=6 -> NULL (doesn't exist)

-- With duplicates:
INSERT INTO Employee (id, salary) VALUES
(1, 100),
(2, 200),
(3, 200),
(4, 300);

-- N=1 -> 300
-- N=2 -> 200 (distinct)
-- N=3 -> 100
-- N=4 -> NULL
*/