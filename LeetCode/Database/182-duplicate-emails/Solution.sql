-- LeetCode 182 - Duplicate Emails
-- 
-- Table: Person
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | email       | varchar |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- Each row of this table contains an email. The emails will not contain uppercase letters.
-- 
-- Write a solution to report all the duplicate emails.
-- Return the result table in any order.

-- Approach 1: GROUP BY with HAVING (Standard solution)
SELECT email
FROM Person
GROUP BY email
HAVING COUNT(*) > 1;

-- Approach 2: Using COUNT(*) > 1 with explicit column
SELECT email
FROM Person
GROUP BY email
HAVING COUNT(email) > 1;

-- Approach 3: Self JOIN (Alternative)
SELECT DISTINCT p1.email
FROM Person p1
JOIN Person p2 ON p1.email = p2.email AND p1.id <> p2.id;

-- Approach 4: Using window function (Modern SQL)
SELECT DISTINCT email
FROM (
    SELECT email, COUNT(*) OVER (PARTITION BY email) as cnt
    FROM Person
) t
WHERE cnt > 1;

-- Approach 5: Using EXISTS (Correlated subquery)
SELECT DISTINCT email
FROM Person p1
WHERE EXISTS (
    SELECT 1 FROM Person p2 
    WHERE p2.email = p1.email AND p2.id <> p1.id
);

-- Test data setup (for local testing)
/*
CREATE TABLE Person (
    id INT PRIMARY KEY,
    email VARCHAR(100)
);

INSERT INTO Person (id, email) VALUES
(1, 'a@b.com'),
(2, 'c@d.com'),
(3, 'a@b.com');

-- Expected output: a@b.com
*/