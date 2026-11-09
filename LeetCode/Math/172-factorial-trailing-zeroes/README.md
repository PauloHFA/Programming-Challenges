# LeetCode 172 - Factorial Trailing Zeroes

## Problema

Given an integer `n`, return the number of trailing zeroes in `n!`.

Note that `n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1`.

### Exemplo 1:
```
Input: n = 3
Output: 0
Explanation: 3! = 6, no trailing zero.
```

### Exemplo 2:
```
Input: n = 5
Output: 1
Explanation: 5! = 120, one trailing zero.
```

### Exemplo 3:
```
Input: n = 0
Output: 0
```

### Restrições:
- 0 <= n <= 10^4

### Follow up:
Could you write a solution that works in logarithmic time complexity?

---

## Abordagens

### Abordagem 1: Contar Fatores de 5 (Ótima - O(log n))
Cada zero final vem de um fator 10 = 2 × 5. Como há mais fatores 2 que 5 no fatorial, basta contar quantos fatores 5 existem. Para cada múltiplo de 5, adiciona 1; para cada múltiplo de 25, adiciona mais 1; para 125, mais 1, etc.
- **Complexidade**: O(log₅ n) tempo, O(1) espaço

### Abordagem 2: Versão Recursiva
Implementação recursiva da mesma lógica: `trailingZeroes(n) = n/5 + trailingZeroes(n/5)`.
- **Complexidade**: O(log₅ n) tempo, O(log₅ n) espaço (call stack)

### Abordagem 3: Loop Explícito com Potências de 5
Itera explicitamente pelas potências de 5 (5, 25, 125, 625, ...) somando n / powerOf5.
- **Complexidade**: O(log₅ n) tempo, O(1) espaço

### Abordagem 4: Força Bruta (apenas verificação)
Calcula o fatorial real e conta zeros. Funciona apenas para n ≤ 20 (overflow de long).
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 5: Contar Fatores 2 e 5 Separadamente (educacional)
Conta explicitamente quantos fatores 2 e 5 existem em todos os números de 1 a n. O mínimo dos dois é a resposta.
- **Complexidade**: O(n log n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Fatores de 5 | O(log n) | O(1) |
| Recursiva | O(log n) | O(log n) |
| Potências explícitas | O(log n) | O(1) |
| Força bruta | O(n) | O(1) |
| Contar 2 e 5 | O(n log n) | O(1) |

---

## Casos de Teste

1. **n=0** -> 0
2. **n=1** -> 0
3. **n=3** -> 0
4. **n=4** -> 0
5. **n=5** -> 1 (5! = 120)
6. **n=6** -> 1
7. **n=10** -> 2 (10! = 3628800)
8. **n=15** -> 3
9. **n=20** -> 4
10. **n=25** -> 6 (25 contribui com dois 5s)
11. **n=30** -> 7
12. **n=100** -> 24
13. **n=1000** -> 249
14. **n=10000** -> 2499

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.