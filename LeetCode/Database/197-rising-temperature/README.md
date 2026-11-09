# LeetCode 197 - Rising Temperature

## Problema

Table: Weather
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| recordDate    | date    |
| temperature   | int     |
+---------------+---------+
```
id is the primary key for this table. This table contains information about the temperature on a certain day.

Write a solution to find all dates' id with higher temperature compared to its previous dates (yesterday). Return the result table in any order.

### Exemplo:
```
Input: 
Weather table:
+----+------------+-------------+
| id | recordDate | temperature |
+----+------------+-------------+
| 1  | 2015-01-01 | 10          |
| 2  | 2015-01-02 | 25          |
| 3  | 2015-01-03 | 20          |
| 4  | 2015-01-04 | 30          |
+----+------------+-------------+

Output:
+----+
| id |
+----+
| 2  |
| 4  |
+----+
```
Explanation: 
- Day 2 (25) > Day 1 (10) ✓
- Day 3 (20) < Day 2 (25) ✗
- Day 4 (30) > Day 3 (20) ✓

---

## Abordagens

### Abordagem 1: Self JOIN com DATE_ADD (Padrão)
```sql
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)
WHERE w1.temperature > w2.temperature;
```
Junta a tabela consigo mesma onde a data de w1 é exatamente 1 dia após w2.

### Abordagem 2: Self JOIN com DATEDIFF
```sql
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON DATEDIFF(w1.recordDate, w2.recordDate) = 1
WHERE w1.temperature > w2.temperature;
```
Usa DATEDIFF para verificar diferença de 1 dia.

### Abordagem 3: LAG Window Function (SQL Moderno)
```sql
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
```
Usa LAG para acessar temperatura e data do dia anterior.

### Abordagem 4: Subquery Correlacionada
```sql
SELECT w1.id
FROM Weather w1
WHERE w1.temperature > (
    SELECT w2.temperature 
    FROM Weather w2 
    WHERE w2.recordDate = DATE_ADD(w1.recordDate, INTERVAL -1 DAY)
);
```
Para cada dia, busca temperatura do dia anterior via subquery.

### Abordagem 5: CTE com LAG
```sql
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
```
Versão com CTE da abordagem 3.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| Self JOIN DATE_ADD | O(n log n) | O(n) | MySQL, PostgreSQL |
| Self JOIN DATEDIFF | O(n log n) | O(n) | MySQL, PostgreSQL, SQL Server |
| LAG | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Subquery Correlacionada | O(n²) | O(1) | Todos |
| CTE + LAG | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.