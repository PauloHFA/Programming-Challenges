# LeetCode 183 - Customers Who Never Order

## Problema

Table: Customers
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
+-------------+---------+
```
id is the primary key for this table.

Table: Orders
```
+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| customerId  | int  |
+-------------+------+
```
id is the primary key for this table. customerId is a foreign key referencing Customers.id.

Write a solution to find all customers who never order anything. Return the result table in any order.

### Exemplo:
```
Input: 
Customers table:
+----+-------+
| id | name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+

Orders table:
+----+------------+
| id | customerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+

Output:
+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+
```

---

## Abordagens

### Abordagem 1: LEFT JOIN com NULL check (Padrão)
Faz LEFT JOIN de Customers com Orders e filtra onde Orders.id IS NULL.
- **Complexidade**: O(n + m) tempo

### Abordagem 2: NOT IN Subquery
Seleciona customers cujo id NÃO está na lista de customerIds de Orders.
- **Complexidade**: O(n × m) pior caso, cuidado com NULLs

### Abordagem 3: NOT EXISTS Subquery (Melhor para NULLs)
Usa NOT EXISTS com subquery correlacionada. Mais seguro que NOT IN quando há NULLs.
- **Complexidade**: O(n × m) pior caso, mas geralmente otimizado

### Abordagem 4: EXCEPT / MINUS (Operação de Conjunto)
Remove da lista de todos os customers aqueles que têm pedidos.
- **Complexidade**: O(n log n + m log m) tempo

### Abordagem 5: COUNT com GROUP BY
LEFT JOIN + GROUP BY + HAVING COUNT = 0.
- **Complexidade**: O(n + m) tempo

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| LEFT JOIN NULL | O(n + m) | O(n) | Todos |
| NOT IN | O(n × m) | O(m) | Todos |
| NOT EXISTS | O(n × m) | O(1) | Todos |
| EXCEPT | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle |
| COUNT GROUP BY | O(n + m) | O(n) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.