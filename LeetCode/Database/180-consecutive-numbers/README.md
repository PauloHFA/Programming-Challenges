# LeetCode 180 - Consecutive Numbers

## Problema

Table: Logs
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| num         | varchar |
+-------------+---------+
```
id is the primary key for this table. num is a varchar column.

Write a solution to find all numbers that appear at least three times consecutively. Return the result table in any order.

### Exemplo:
```
Input: 
Logs table:
+----+-----+
| id | num |
+----+-----+
| 1  | 1   |
| 2  | 1   |
| 3  | 1   |
| 4  | 2   |
| 5  | 1   |
| 6  | 2   |
| 7  | 2   |
+----+-----+

Output:
+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+
```
Explanation: 1 appears 3 times consecutively (ids 1, 2, 3).

---

## Abordagens

### Abordagem 1: LAG/LEAD Window Functions (SQL Padrão)
Usa LAG para ver o valor anterior e LEAD para ver o próximo. Se ambos forem iguais ao atual, é consecutivo.
- **Complexidade**: O(n log n) tempo

### Abordagem 2: Self JOIN (SQL Padrão - funciona em todo lugar)
Junta a tabela 3 vezes: l1 com l2 (id+1) e l1 com l3 (id+2), verificando se num é igual.
- **Complexidade**: O(n) tempo com índices

### Abordagem 3: Variáveis de Sessão (MySQL específico)
Usa variáveis @prev e @count para contar ocorrências consecutivas inline.

### Abordagem 4: ROW_NUMBER Difference (Window Function)
Agrupa números consecutivos usando a diferença `id - ROW_NUMBER()`. Números consecutivos têm a mesma diferença.
- **Complexidade**: O(n log n) tempo

### Abordagem 5: Três Self JOINs Explícitos
Versão explícita da abordagem 2 com sintaxe de join antiga (vírgula).

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| LAG/LEAD | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Self JOIN | O(n) | O(1) | Todos (com índices) |
| Variáveis MySQL | O(n log n) | O(1) | MySQL apenas |
| ROW_NUMBER diff | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| 3 Self JOINs | O(n) | O(1) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.