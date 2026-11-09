-- LeetCode 185 - Department Top Three Salaries
-- 
-- Table: Employee
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | name        | varchar |
-- | salary      | int     |
-- | departmentId| int     |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- departmentId is a foreign key (reference column) of the ID from the Department table.
-- Each row of this table indicates the ID, name, salary, and department of an employee.
-- 
-- Table: Department
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | name        | varchar |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- Each row of this table indicates the ID and name of a department.
-- 
-- Write a solution to find the top three salaries in each department.
-- Return the result table in any order.

-- Approach 1: Using DENSE_RANK (Standard solution for top-N per group)
SELECT Department, Employee, Salary
FROM (
    SELECT 
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        DENSE_RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) as rnk
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
) t
WHERE rnk <= 3;

-- Approach 2: Using ROW_NUMBER with distinct salaries
SELECT Department, Employee, Salary
FROM (
    SELECT 
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        ROW_NUMBER() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) as rn
    FROM (
        SELECT DISTINCT departmentId, salary FROM Employee
    ) ds
    JOIN Employee e ON ds.departmentId = e.departmentId AND ds.salary = e.salary
    JOIN Department d ON e.departmentId = d.id
) t
WHERE rn <= 3;

-- Approach 3: Correlated Subquery (Standard SQL - no window functions)
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
WHERE (
    SELECT COUNT(DISTINCT salary)
    FROM Employee e2
    WHERE e2.departmentId = e.departmentId AND e2.salary > e.salary
) < 3;

-- Approach 4: Using CTE with DENSE_RANK
WITH RankedSalaries AS (
    SELECT 
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        DENSE_RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) as rnk
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
)
SELECT Department, Employee, Salary
FROM RankedSalaries
WHERE rnk <= 3;

-- Approach 5: Self JOIN counting higher salaries (Alternative standard SQL)
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
WHERE 3 > (
    SELECT COUNT(DISTINCT e2.salary)
    FROM Employee e2
    WHERE e2.departmentId = e.departmentId AND e2.salary > e.salary
);

-- Test data setup (for local testing)
/*
CREATE TABLE Department (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary INT,
    departmentId INT
);

INSERT INTO Department (id, name) VALUES
(1, 'IT'),
(2, 'Sales');

INSERT INTO Employee (id, name, salary, departmentId) VALUES
(1, 'Joe', 85000, 1),
(2, 'Henry', 80000, 2),
(3, 'Sam', 60000, 2),
(4, 'Max', 90000, 1),
(5, 'Janet', 69000, 1),
(6, 'Randy', 85000, 1),
(7, 'Will', 70000, 1);

-- Expected output:
-- IT: Max (90000), Joe (85000), Randy (85000), Janet (69000) -> top 3 distinct: 90000, 85000, 69000
-- Sales: Henry (80000), Sam (60000) -> top 3 distinct: 80000, 60000
*/