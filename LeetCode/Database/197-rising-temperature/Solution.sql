-- LeetCode 197 - Rising Temperature
-- 
-- Table: Weather
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | id            | int     |
-- | recordDate    | date    |
-- | temperature   | int     |
-- +---------------+---------+
-- id is the primary key (column with unique values) for this table.
-- This table contains information about the temperature on a certain day.
-- 
-- Write a solution to find all dates' id with higher temperature compared to its previous dates (yesterday).
-- Return the result table in any order.

-- Approach 1: Self JOIN with DATE_SUB (Standard)
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)
WHERE w1.temperature > w2.temperature;

-- Approach 2: Self JOIN with DATEDIFF (Alternative)
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON DATEDIFF(w1.recordDate, w2.recordDate) = 1
WHERE w1.temperature > w2.temperature;

-- Approach 3: Using LAG Window Function (Modern SQL)
SELECT id
FROM (
    SELECT 
        id,
        temperature,
        LAG(temperature) OVER (ORDER BY recordDate) as prev_temp,
        LAG(recordDate) OVER (ORDER BY recordDate) as prev_date
    FROM Weather
) t
WHERE temperature > prev_temp AND DATEDIFF(recordDate, prev_date) = 1;

-- Approach 4: Correlated Subquery (Standard SQL)
SELECT w1.id
FROM Weather w1
WHERE w1.temperature > (
    SELECT w2.temperature 
    FROM Weather w2 
    WHERE w2.recordDate = DATE_ADD(w1.recordDate, INTERVAL -1 DAY)
);

-- Approach 5: Using CTE with LAG
WITH TempDiff AS (
    SELECT 
        id,
        temperature,
        LAG(temperature) OVER (ORDER BY recordDate) as prev_temp,
        LAG(recordDate) OVER (ORDER BY recordDate) as prev_date
    FROM Weather
)
SELECT id
FROM TempDiff
WHERE temperature > prev_temp AND DATEDIFF(recordDate, prev_date) = 1;

-- Test data setup (for local testing)
/*
CREATE TABLE Weather (
    id INT PRIMARY KEY,
    recordDate DATE,
    temperature INT
);

INSERT INTO Weather (id, recordDate, temperature) VALUES
(1, '2015-01-01', 10),
(2, '2015-01-02', 25),
(3, '2015-01-03', 20),
(4, '2015-01-04', 30);

-- Expected output: 2, 4
-- Day 2 (25) > Day 1 (10) ✓
-- Day 3 (20) < Day 2 (25) ✗
-- Day 4 (30) > Day 3 (20) ✓
*/