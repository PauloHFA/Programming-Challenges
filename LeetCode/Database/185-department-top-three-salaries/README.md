# LeetCode 185 - Department Top Three Salaries

## Problema

Table: Employee
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
| salary      | int     |
| departmentId| int     |
+-------------+---------+
```
id is the primary key. departmentId is a foreign key referencing Department.id.

Table: Department
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
+-------------+---------+
```
id is the primary key.

Write a solution to find the top three salaries in each department. Return the result table in any order.

### Exemplo:
```
Input: 
Employee table:
+----+-------+--------+--------------+
| id | name  | salary | departmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 85000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
| 7  | Will  | 70000  | 1            |
+----+-------+--------+--------------+

Department table:
+----+-------+
| id | name  |
+----+-------+
| 1  | IT    |
| 2  | Sales |
+----+-------+

Output:
+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Joe      | 85000  |
| IT         | Randy    | 85000  |
| IT         | Janet    | 69000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |
+------------+----------+--------+
```
Note: Top 3 distinct salaries in IT: 90000, 85000, 69000. Both Joe and Randy have 85000.

---

## Abordagens

### Abordagem 1: DENSE_RANK (Padrão para top-N por grupo)
Usa DENSE_RANK() OVER (PARTITION BY departmentId ORDER BY salary DESC) e filtra rank <= 3.
- **Complexidade**: O(n log n) tempo

### Abordagem 2: ROW_NUMBER com salários distintos
Primeiro pega salários distintos por departamento, depois usa ROW_NUMBER.

### Abordagem 3: Subquery Correlacionada (SQL Padrão)
Para cada funcionário, conta quantos salários distintos no seu departamento são maiores. Se < 3, está no top 3.
- **Complexidade**: O(n × m) pior caso

### Abordagem 4: CTE com DENSE_RANK
CTE calcula ranks, depois filtra.

### Abordagem 5: Self JOIN contando salários maiores
Similar à abordagem 3 mas com sintaxe diferente.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| ROW_NUMBER distinct | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Subquery Correlacionada | O(n × m) | O(1) | Todos |
| CTE + DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Self JOIN count | O(n × m) | O(1) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.