# LeetCode 175 - Combine Two Tables

## Problema

Table: Person
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| personId    | int     |
| lastName    | varchar |
| firstName   | varchar |
+-------------+---------+
```
personId is the primary key for this table.

Table: Address
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| addressId   | int     |
| personId    | int     |
| city        | varchar |
| state       | varchar |
+-------------+---------+
```
addressId is the primary key for this table.

Write a solution to report the first name, last name, city, and state of each person in the Person table. If the address of a personId is not present in the Address table, report null instead. Return the result table in any order.

### Exemplo:
```
Input: 
Person table:
+----------+----------+-----------+
| personId | lastName | firstName |
+----------+----------+-----------+
| 1        | Wang     | Allen     |
| 2        | Alice    | Bob       |
+----------+----------+-----------+

Address table:
+-----------+----------+---------------+------------+
| addressId | personId | city          | state      |
+-----------+----------+---------------+------------+
| 1         | 2        | New York      | NY         |
+-----------+----------+---------------+------------+

Output:
+-----------+----------+---------------+----------+
| firstName | lastName | city          | state    |
+-----------+----------+---------------+----------+
| Allen     | Wang     | NULL          | NULL     |
| Bob       | Alice    | New York      | NY       |
+-----------+----------+---------------+----------+
```

---

## Abordagens

### Abordagem 1: LEFT JOIN (Padrão)
Usa LEFT JOIN para manter todas as linhas da tabela Person e trazer dados da Address quando existir correspondência.
- **Complexidade**: O(n + m) onde n = linhas Person, m = linhas Address

### Abordagem 2: LEFT JOIN com colunas explícitas
Mesma lógica da abordagem 1 mas listando colunas explicitamente.

### Abordagem 3: Subquery
Usa subqueries correlacionadas para buscar city e state. Menos eficiente em alguns bancos.

### Abordagem 4: COALESCE para NULL explícito
Usa COALESCE para deixar explícito o tratamento de NULL (embora LEFT JOIN já retorne NULL por padrão).

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| LEFT JOIN | O(n + m) | O(n) |
| Subquery | O(n × m) | O(n) |

---

## Implementação

A solução está em `Solution.sql` com 4 abordagens diferentes comentadas.