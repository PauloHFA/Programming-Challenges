-- LeetCode 175 - Combine Two Tables
-- 
-- Table: Person
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | personId    | int     |
-- | lastName    | varchar |
-- | firstName   | varchar |
-- +-------------+---------+
-- personId is the primary key (column with unique values) for this table.
-- This table contains information about the ID of some persons and their first and last names.
-- 
-- Table: Address
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | addressId   | int     |
-- | personId    | int     |
-- | city        | varchar |
-- | state       | varchar |
-- +-------------+---------+
-- addressId is the primary key (column with unique values) for this table.
-- Each row of this table contains information about the city and state of one person with ID = personId.
-- 
-- Write a solution to report the first name, last name, city, and state of each person in the Person table.
-- If the address of a personId is not present in the Address table, report null instead.
-- Return the result table in any order.

-- Approach 1: LEFT JOIN (Standard solution)
SELECT p.firstName, p.lastName, a.city, a.state
FROM Person p
LEFT JOIN Address a ON p.personId = a.personId;

-- Approach 2: LEFT JOIN with explicit columns
SELECT 
    p.firstName,
    p.lastName,
    a.city,
    a.state
FROM Person p
LEFT JOIN Address a 
    ON p.personId = a.personId;

-- Approach 3: Using subquery (Alternative)
SELECT 
    p.firstName,
    p.lastName,
    (SELECT a.city FROM Address a WHERE a.personId = p.personId) AS city,
    (SELECT a.state FROM Address a WHERE a.personId = p.personId) AS state
FROM Person p;

-- Approach 4: Using COALESCE for explicit NULL handling
SELECT 
    p.firstName,
    p.lastName,
    COALESCE(a.city, NULL) AS city,
    COALESCE(a.state, NULL) AS state
FROM Person p
LEFT JOIN Address a ON p.personId = a.personId;

-- Test data setup (for local testing)
/*
CREATE TABLE Person (
    personId INT PRIMARY KEY,
    lastName VARCHAR(50),
    firstName VARCHAR(50)
);

CREATE TABLE Address (
    addressId INT PRIMARY KEY,
    personId INT,
    city VARCHAR(50),
    state VARCHAR(50)
);

INSERT INTO Person (personId, lastName, firstName) VALUES
(1, 'Wang', 'Allen'),
(2, 'Alice', 'Bob');

INSERT INTO Address (addressId, personId, city, state) VALUES
(1, 2, 'New York', 'NY');

-- Expected output:
-- firstName | lastName | city      | state
-- Allen     | Wang     | NULL      | NULL
-- Bob       | Alice    | New York  | NY
*/