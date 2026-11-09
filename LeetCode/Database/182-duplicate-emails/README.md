# LeetCode 182 - Duplicate Emails

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

Write a solution to report all the duplicate emails. Return the result table in any order.

### Exemplo:
```
Input: 
Person table:
+----+---------+
| id | email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+

Output:
+---------+
| email   |
+---------+
| a@b.com |
+---------+
```

---

## Abordagens

### Abordagem 1: GROUP BY com HAVING (Padrão)
Agrupa por email e filtra grupos com count > 1.
- **Complexidade**: O(n) tempo

### Abordagem 2: GROUP BY com COUNT(email)
Mesma lógica mas contando explicitamente a coluna email.

### Abordagem 3: Self JOIN
Junta a tabela consigo mesma onde emails são iguais mas IDs diferentes.

### Abordagem 4: Window Function
Usa COUNT(*) OVER (PARTITION BY email) para contar ocorrências por email.

### Abordagem 5: EXISTS com Subquery Correlacionada
Para cada email, verifica se existe outro registro com mesmo email mas ID diferente.

---

## Complexidade

| Abordagem | Tempo | Espaço | Compatibilidade |
|-----------|-------|--------|-----------------|
| GROUP BY HAVING | O(n) | O(n) | Todos |
| Self JOIN | O(n²) pior caso | O(n) | Todos |
| Window Function | O(n log n) | O(n) | PostgreSQL, SQL Server, Oracle, MySQL 8+ |
| EXISTS | O(n²) pior caso | O(1) | Todos |

---

## Implementação

A solução está em `Solution.sql` com 5 abordagens diferentes comentadas.