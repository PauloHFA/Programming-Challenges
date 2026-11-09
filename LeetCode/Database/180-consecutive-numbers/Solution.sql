-- LeetCode 180 - Consecutive Numbers
-- 
-- Table: Logs
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | num         | varchar |
-- +-------------+---------+
-- id is the primary key (column with unique values) for this table.
-- num is a varchar column.
-- 
-- Write a solution to find all numbers that appear at least three times consecutively.
-- Return the result table in any order.

-- Approach 1: Using LAG/LEAD window functions (Standard SQL)
SELECT DISTINCT num AS ConsecutiveNums
FROM (
    SELECT 
        num,
        LAG(num) OVER (ORDER BY id) AS prev_num,
        LEAD(num) OVER (ORDER BY id) AS next_num
    FROM Logs
) t
WHERE num = prev_num AND num = next_num;

-- Approach 2: Using self JOIN (Standard SQL - works everywhere)
SELECT DISTINCT l1.num AS ConsecutiveNums
FROM Logs l1
JOIN Logs l2 ON l1.id = l2.id - 1 AND l1.num = l2.num
JOIN Logs l3 ON l1.id = l3.id - 2 AND l1.num = l3.num;

-- Approach 3: Using variables (MySQL specific)
SELECT DISTINCT num AS ConsecutiveNums
FROM (
    SELECT 
        num,
        @count := IF(@prev = num, @count + 1, 1) AS cnt,
        @prev := num
    FROM Logs, (SELECT @prev := NULL, @count := 0) vars
    ORDER BY id
) t
WHERE cnt >= 3;

-- Approach 4: Using ROW_NUMBER difference (Window function)
SELECT DISTINCT num AS ConsecutiveNums
FROM (
    SELECT 
        num,
        id - ROW_NUMBER() OVER (PARTITION BY num ORDER BY id) AS grp
    FROM Logs
) t
GROUP BY num, grp
HAVING COUNT(*) >= 3;

-- Approach 5: Three self JOINs with explicit conditions
SELECT DISTINCT l1.num AS ConsecutiveNums
FROM Logs l1, Logs l2, Logs l3
WHERE l1.id = l2.id - 1 
  AND l2.id = l3.id - 1
  AND l1.num = l2.num 
  AND l2.num = l3.num;

-- Test data setup (for local testing)
/*
CREATE TABLE Logs (
    id INT PRIMARY KEY,
    num VARCHAR(10)
);

INSERT INTO Logs (id, num) VALUES
(1, '1'),
(2, '1'),
(3, '1'),
(4, '2'),
(5, '1'),
(6, '2'),
(7, '2');

-- Expected output: 1 (appears 3 times consecutively at ids 1,2,3)

-- Test case 2: Multiple consecutive groups
INSERT INTO Logs (id, num) VALUES
(1, '1'), (2, '1'), (3, '1'),
(4, '2'), (5, '2'), (6, '2'),
(7, '3'), (8, '3');
-- Expected: 1, 2

-- Test case 3: Non-consecutive
INSERT INTO Logs (id, num) VALUES
(1, '1'), (2, '2'), (3, '1'), (4, '1'), (5, '1');
-- Expected: 1 (at ids 3,4,5)
*/