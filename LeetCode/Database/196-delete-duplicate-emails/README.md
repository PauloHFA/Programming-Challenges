# LeetCode 196 - Delete Duplicate Emails

## Problema

Table: Person
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| email       | varchar |
+-------------+---------+
```
id is the primary key for this table. Each row contains an email. The emails will not contain uppercase letters.

Write a solution to delete all duplicate emails, keeping only one unique email with the smallest id. For SQL users, please note that you are supposed to write a DELETE statement and not a SELECT one.

### Exemplo:
```
Input: 
Person table:
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+

Output: (after deletion)
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
```

---

## Abordagens

### Abordagem 1: DELETE com JOIN (Padrão)
```sql
DELETE p1 FROM Person p1
INNER JOIN Person p2 
WHERE p1.email = p2.email AND p1.id > p2.id;
```
Junta a tabela consigo mesma e deleta onde email é igual mas id é maior.

### Abordagem 2: DELETE com Subquery
```sql
DELETE FROM Person
WHERE id NOT IN (
    SELECT MIN(id) FROM Person GROUP BY email
);
```
Deleta todos os ids que NÃO são o mínimo para cada email.

### Abordagem 3: DELETE com EXISTS
```sql
DELETE FROM Person p1
WHERE EXISTS (
    SELECT 1 FROM Person p2 
    WHERE p2.email = p1.email AND p2.id < p1.id
);
```
Usa subquery correlacionada com EXISTS.

### Abordagem 4: CTE com ROW_NUMBER (SQL Moderno)
```sql
WITH RankedEmails AS (
    SELECT id, email, ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) as rn
    FROM Person
)
DELETE FROM Person
WHERE id IN (SELECT id FROM RankedEmails WHERE rn > 1);
```
Usa window function para rankear e deleta rank > 1.

### Abordagem 5: MIN com JOIN
```sql
DELETE p1 FROM Person p1
JOIN (
    SELECT email, MIN(id) as min_id FROM Person GROUP BY email
) p2 ON p1.email = p2.email
WHERE p1.id > p2.min_id;
```
Similar à abordagem 1 mas com subquery explícita do MIN.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| DELETE JOIN | O(n log n) | O(n) | MySQL, PostgreSQL |
| NOT IN | O(n log n) | O(n) | Todos |
| EXISTS | O(n log n) | O(1) | Todos |
| CTE + ROW_NUMBER | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| MIN + JOIN | O(n log n) | O(n) | MySQL, PostgreSQL |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.