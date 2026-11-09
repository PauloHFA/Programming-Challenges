# LeetCode 178 - Rank Scores

## Problema

Table: Scores
```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| score       | decimal |
+-------------+---------+
```
id is the primary key for this table. Score is a floating point value with two decimal places.

Write a solution to find the rank of the scores. The ranking should be calculated according to the following rules:
- The scores should be ranked from the highest to the lowest.
- If there is a tie between two scores, both should have the same ranking.
- After a tie, the next ranking number should be the next consecutive integer value.
- In other words, there should be no "holes" between ranks.

Return the result table ordered by score in descending order.

### Exemplo:
```
Input: 
Scores table:
+----+-------+
| id | score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+

Output:
+-------+------+
| score | rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+
```

---

## Abordagens

### Abordagem 1: DENSE_RANK (Window Function - Padrão)
DENSE_RANK atribui o mesmo rank para valores iguais e não pula números (sem "buracos").
- **Complexidade**: O(n log n) tempo

### Abordagem 2: ROW_NUMBER com subquery
Usa ROW_NUMBER em scores distintos, depois junta com a tabela original.

### Abordagem 3: Subquery correlacionada (SQL Padrão)
Para cada score, conta quantos scores distintos são maiores ou iguais.
- **Complexidade**: O(n²) tempo, funciona em qualquer SQL

### Abordagem 4: Variáveis de sessão (MySQL específico)
Usa variáveis @rank e @prev para calcular rank inline.

### Abordagem 5: Self JOIN com COUNT
Junta a tabela consigo mesma onde score da direita >= score da esquerda, agrupa e conta distintos.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| DENSE_RANK | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| ROW_NUMBER | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| Subquery correlacionada | O(n²) | O(1) | Todos (SQL padrão) |
| Variáveis MySQL | O(n log n) | O(1) | MySQL apenas |
| Self JOIN | O(n²) | O(n) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.