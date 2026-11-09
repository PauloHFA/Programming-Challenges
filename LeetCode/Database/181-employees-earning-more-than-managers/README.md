# LeetCode 181 - Employees Earning More Than Their Managers

## Problema

Table: Employee
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
| salary      | int     |
| managerId   | int     |
+-------------+---------+
```
id is the primary key for this table. Each row indicates the ID of an employee, their name, salary, and the ID of their manager.

Write a solution to find the employees who earn more than their managers. Return the result table in any order.

### Exemplo:
```
Input: 
Employee table:
+----+-------+--------+-----------+
| id | name  | salary | managerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+

Output:
+----------+
| Employee |
+----------+
| Joe      |
+----------+
```
Explanation: Joe earns 70000 which is more than his manager Sam (60000). Henry earns 80000 which is less than his manager Max (90000).

---

## Abordagens

### Abordagem 1: Self JOIN (Padrão)
Junta a tabela Employee consigo mesma: e1 (funcionário) com e2 (gerente) via managerId = id.
- **Complexidade**: O(n) tempo com índice

### Abordagem 2: Self JOIN com colunas explícitas
Mesma lógica da abordagem 1 com INNER JOIN explícito.

### Abordagem 3: Subquery
Para cada funcionário, busca o salário do gerente via subquery correlacionada.

### Abordagem 4: EXISTS
Usa EXISTS para verificar se existe gerente com salário menor.

### Abordagem 5: CTE (Common Table Expression)
Cria CTE com salários dos gerentes e faz join.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| Self JOIN | O(n) | O(1) | Todos |
| Subquery | O(n²) pior caso | O(1) | Todos |
| EXISTS | O(n²) pior caso | O(1) | Todos |
| CTE | O(n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.