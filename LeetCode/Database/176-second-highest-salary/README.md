# LeetCode 176 - Second Highest Salary

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

Write a solution to find the second highest salary from the Employee table. If there is no second highest salary, return null.

### Exemplo 1:
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

Output:
+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
```

### Exemplo 2:
```
Input: 
Employee table:
+----+--------+
| id | salary |
+----+--------+
| 1  | 100    |
+----+--------+

Output:
+---------------------+
| SecondHighestSalary |
+---------------------+
| NULL                |
+---------------------+
```

---

## Abordagens

### Abordagem 1: Subquery com LIMIT/OFFSET (MySQL/PostgreSQL)
Ordena salários distintos em ordem decrescente e pega o segundo (OFFSET 1 LIMIT 1).
- **Complexidade**: O(n log n) tempo

### Abordagem 2: MAX com subquery (SQL Padrão)
Encontra o máximo salário que é menor que o salário máximo geral.
- **Complexidade**: O(n) tempo

### Abordagem 3: DENSE_RANK (Window Function)
Usa DENSE_RANK para rankear salários distintos e filtra rank = 2.
- **Complexidade**: O(n log n) tempo

### Abordagem 4: ROW_NUMBER (Window Function)
Similar ao DENSE_RANK mas com ROW_NUMBER após remover duplicatas.

### Abordagem 5: Self JOIN
Junta a tabela consigo mesma onde salário da esquerda < salário da direita, pega o máximo da esquerda.
- **Complexidade**: O(n²) tempo

### Abordagem 6: IFNULL/COALESCE para NULL explícito
Wrapper na abordagem 1 para garantir retorno NULL explícito.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| LIMIT/OFFSET | O(n log n) | O(n) | MySQL, PostgreSQL |
| MAX + subquery | O(n) | O(1) | Todos (SQL padrão) |
| DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle |
| ROW_NUMBER | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle |
| Self JOIN | O(n²) | O(1) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 6 abordagens diferentes comentadas.