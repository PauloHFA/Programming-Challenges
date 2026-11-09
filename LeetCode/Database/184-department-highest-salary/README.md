# LeetCode 184 - Department Highest Salary

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

Write a solution to find employees who have the highest salary in each of the departments. Return the result table in any order.

### Exemplo:
```
Input: 
Employee table:
+----+-------+--------+--------------+
| id | name  | salary | departmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 70000  | 1            |
| 2  | Jim   | 90000  | 1            |
| 3  | Henry | 80000  | 2            |
| 4  | Sam   | 60000  | 2            |
| 5  | Max   | 90000  | 1            |
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
| IT         | Jim      | 90000  |
| IT         | Max      | 90000  |
| Sales      | Henry    | 80000  |
+------------+----------+--------+
```

---

## Abordagens

### Abordagem 1: MAX com JOIN (Padrão)
Encontra o salário máximo por departamento e junta com Employee.
- **Complexidade**: O(n + m) tempo

### Abordagem 2: Window Function RANK
Usa RANK() OVER (PARTITION BY departmentId ORDER BY salary DESC) e filtra rank = 1.
- **Complexidade**: O(n log n) tempo

### Abordagem 3: DENSE_RANK (Trata empates corretamente)
Similar ao RANK mas DENSE_RANK não pula números. Para este problema, ambos funcionam igual para rank=1.
- **Complexidade**: O(n log n) tempo

### Abordagem 4: Subquery Correlacionada
Para cada funcionário, verifica se seu salário é igual ao MAX do seu departamento.
- **Complexidade**: O(n × m) pior caso

### Abordagem 5: CTE com MAX
CTE calcula salários máximos por departamento, depois junta com Employee e Department.
- **Complexidade**: O(n + m) tempo

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| MAX + JOIN | O(n + m) | O(m) | Todos |
| RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Subquery Correlacionada | O(n × m) | O(1) | Todos |
| CTE + MAX | O(n + m) | O(m) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.