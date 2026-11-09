-- LeetCode 183 - Customers Who Never Order
-- 
-- Table: Customers
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | name        | varchar |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- Each row of this table indicates the ID and name of a customer.
-- 
-- Table: Orders
-- +-------------+------+
-- | Column Name | Type |
-- +-------------+------+
-- | id          | int  |
-- | customerId  | int  |
-- +-------------+------+
-- id is the primary key (column with unique values) for this table.
-- customerId is a foreign key (reference column) of the ID from the Customers table.
-- Each row of this table indicates the ID of an order and the ID of the customer who ordered it.
-- 
-- Write a solution to find all customers who never order anything.
-- Return the result table in any order.

-- Approach 1: LEFT JOIN with NULL check (Standard solution)
SELECT c.name AS Customers
FROM Customers c
LEFT JOIN Orders o ON c.id = o.customerId
WHERE o.id IS NULL;

-- Approach 2: NOT IN subquery
SELECT name AS Customers
FROM Customers
WHERE id NOT IN (SELECT customerId FROM Orders);

-- Approach 3: NOT EXISTS subquery (Better for NULL handling)
SELECT name AS Customers
FROM Customers c
WHERE NOT EXISTS (SELECT 1 FROM Orders o WHERE o.customerId = c.id);

-- Approach 4: EXCEPT / MINUS (Set operation)
SELECT name FROM Customers
EXCEPT
SELECT c.name FROM Customers c JOIN Orders o ON c.id = o.customerId;

-- Approach 5: Using COUNT with GROUP BY
SELECT c.name AS Customers
FROM Customers c
LEFT JOIN Orders o ON c.id = o.customerId
GROUP BY c.id, c.name
HAVING COUNT(o.id) = 0;

-- Test data setup (for local testing)
/*
CREATE TABLE Customers (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE Orders (
    id INT PRIMARY KEY,
    customerId INT
);

INSERT INTO Customers (id, name) VALUES
(1, 'Joe'),
(2, 'Henry'),
(3, 'Sam'),
(4, 'Max');

INSERT INTO Orders (id, customerId) VALUES
(1, 3),
(2, 1);

-- Expected output: Henry, Max (customers 2 and 4 never ordered)
*/