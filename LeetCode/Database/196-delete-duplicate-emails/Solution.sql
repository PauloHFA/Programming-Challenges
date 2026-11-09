-- LeetCode 196 - Delete Duplicate Emails
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
-- Write a solution to delete all duplicate emails, keeping only one unique email with the smallest id.
-- For SQL users, please note that you are supposed to write a DELETE statement and not a SELECT one.
-- 
-- Return the result table in any order.

-- Approach 1: DELETE with JOIN (Standard)
DELETE p1 FROM Person p1
INNER JOIN Person p2 
WHERE p1.email = p2.email AND p1.id > p2.id;

-- Approach 2: DELETE with Subquery (Alternative)
DELETE FROM Person
WHERE id NOT IN (
    SELECT MIN(id) FROM Person GROUP BY email
);

-- Approach 3: DELETE with EXISTS (Correlated Subquery)
DELETE FROM Person p1
WHERE EXISTS (
    SELECT 1 FROM Person p2 
    WHERE p2.email = p1.email AND p2.id < p1.id
);

-- Approach 4: Using CTE with ROW_NUMBER (Modern SQL)
WITH RankedEmails AS (
    SELECT id, email, ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) as rn
    FROM Person
)
DELETE FROM Person
WHERE id IN (SELECT id FROM RankedEmails WHERE rn > 1);

-- Approach 5: Using MIN with JOIN (Alternative)
DELETE p1 FROM Person p1
JOIN (
    SELECT email, MIN(id) as min_id FROM Person GROUP BY email
) p2 ON p1.email = p2.email
WHERE p1.id > p2.min_id;

-- Test data setup (for local testing)
/*
CREATE TABLE Person (
    id INT PRIMARY KEY,
    email VARCHAR(100)
);

INSERT INTO Person (id, email) VALUES
(1, 'john@example.com'),
(2, 'bob@example.com'),
(3, 'john@example.com');

-- After deletion, should keep id=1 (john@example.com) and id=2 (bob@example.com)
-- id=3 (john@example.com) should be deleted
*/