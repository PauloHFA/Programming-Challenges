# LeetCode 177 - Nth Highest Salary

## Problema

Table: Employee
```
+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| salary      | int  |
+-------------+------+
```
id is the primary key for this table.

Write a solution to find the nth highest salary from the Employee table. If there is no nth highest salary, return null.

### Exemplo:
```
Input: 
Employee table:
+----+--------+
| id | salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+

N = 2

Output:
+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+
```

---

## Abordagens

### Abordagem 1: LIMIT/OFFSET com parâmetro (MySQL/PostgreSQL)
Usa LIMIT 1 OFFSET N-1 após ordenar salários distintos em ordem decrescente.
- **Complexidade**: O(n log n) tempo

### Abordagem 2: DENSE_RANK (Window Function)
Usa DENSE_RANK para rankear salários distintos e filtra rank = N.
- **Complexidade**: O(n log n) tempo

### Abordagem 3: ROW_NUMBER com distinct
Similar ao DENSE_RANK mas com ROW_NUMBER após remover duplicatas.

### Abordagem 4: Subquery correlacionada (SQL Padrão)
Para cada salário, conta quantos salários distintos são maiores. Se count = N-1, é o N-ésimo.
- **Complexidade**: O(n²) tempo, funciona em qualquer SQL

### Abordagem 5: Variáveis de sessão (MySQL específico)
Usa variável @rownum para numerar linhas após ordenação.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| LIMIT/OFFSET | O(n log n) | O(n) | MySQL, PostgreSQL |
| DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle |
| ROW_NUMBER | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle |
| Subquery correlacionada | O(n²) | O(1) | Todos (SQL padrão) |
| Variáveis MySQL | O(n log n) | O(n) | MySQL apenas |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas. O problema pede uma função `getNthHighestSalary(N INT)`.