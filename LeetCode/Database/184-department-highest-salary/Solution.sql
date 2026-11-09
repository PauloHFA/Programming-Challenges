-- LeetCode 184 - Department Highest Salary
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
-- Write a solution to find employees who have the highest salary in each of the departments.
-- Return the result table in any order.

-- Approach 1: Using MAX with JOIN (Standard solution)
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
WHERE (e.departmentId, e.salary) IN (
    SELECT departmentId, MAX(salary)
    FROM Employee
    GROUP BY departmentId
);

-- Approach 2: Using Window Function (Modern SQL)
SELECT Department, Employee, Salary
FROM (
    SELECT 
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) as rnk
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
) t
WHERE rnk = 1;

-- Approach 3: Using DENSE_RANK (Handles ties correctly)
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
WHERE rnk = 1;

-- Approach 4: Correlated Subquery (Standard SQL)
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
WHERE e.salary = (
    SELECT MAX(salary)
    FROM Employee e2
    WHERE e2.departmentId = e.departmentId
);

-- Approach 5: Using CTE with MAX
WITH MaxSalaries AS (
    SELECT departmentId, MAX(salary) AS max_salary
    FROM Employee
    GROUP BY departmentId
)
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
JOIN MaxSalaries m ON e.departmentId = m.departmentId AND e.salary = m.max_salary;

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
(1, 'Joe', 70000, 1),
(2, 'Jim', 90000, 1),
(3, 'Henry', 80000, 2),
(4, 'Sam', 60000, 2),
(5, 'Max', 90000, 1);

-- Expected output:
-- IT: Jim (90000), Max (90000) - both have highest salary in IT
-- Sales: Henry (80000)
*/