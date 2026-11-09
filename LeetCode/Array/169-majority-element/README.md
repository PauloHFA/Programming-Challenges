# LeetCode 169 - Majority Element

## Problema

Given an array nums of size n, return the majority element. The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.

### Exemplo 1:
```
Input: nums = [3,2,3]
Output: 3
```

### Exemplo 2:
```
Input: nums = [2,2,1,1,1,2,2]
Output: 2
```

### Restrições:
- n == nums.length
- 1 <= n <= 5 * 10^4
- -10^9 <= nums[i] <= 10^9

### Follow-up: Could you solve the problem in linear time and in O(1) space?

---

## Abordagens

### Abordagem 1: Boyer-Moore Voting Algorithm
O elemento majoritário aparece mais que n/2 vezes, então ele sobrevive ao processo de cancelamento onde emparelhamos elementos diferentes.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 2: HashMap Counting
Conta frequência de cada elemento usando HashMap.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 3: Sorting
Após ordenar, o elemento majoritário deve estar no índice n/2.
- **Complexidade**: O(n log n) tempo, O(1) ou O(log n) espaço

### Abordagem 4: Randomization
Escolhe aleatoriamente um elemento e verifica se é o majoritário. Como maioria > n/2, probabilidade > 1/2.
- **Complexidade**: O(n) tempo esperado, O(1) espaço

### Abordagem 5: Divide and Conquer
Recursivamente encontra maioria nas metades esquerda e direita, depois combina.
- **Complexidade**: O(n log n) tempo, O(log n) espaço (stack de recursão)

### Abordagem 6: Bit Manipulation
Conta bits em cada posição através de todos os números. Os bits do elemento majoritário dominarão.
- **Complexidade**: O(32 * n) = O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Boyer-Moore | O(n) | O(1) |
| HashMap | O(n) | O(n) |
| Sort | O(n log n) | O(1) |
| Random | O(n) esperado | O(1) |
| Divide & Conquer | O(n log n) | O(log n) |
| Bit Manipulation | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: nums=[3,2,3] -> 3
2. **Exemplo 2**: nums=[2,2,1,1,1,2,2] -> 2
3. **Elemento único**: nums=[1] -> 1
4. **Dois iguais**: nums=[5,5] -> 5
5. **Números negativos**: nums=[-1,-1,2] -> -1
6. **Array grande**: 10001 elementos com maioria 42
7. **Todos iguais**: nums=[7,7,7,7,7] -> 7
8. **Maioria no início**: nums=[1,1,1,2,3] -> 1
9. **Maioria no fim**: nums=[2,3,1,1,1] -> 1
10. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 6 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.