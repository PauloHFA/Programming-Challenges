# LeetCode 167 - Two Sum II - Input Array Is Sorted

## Problema

Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.

Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.

The tests are generated such that there is exactly one solution. You may not use the same element twice.

Your solution must use only constant extra space.

### Exemplo 1:
```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

### Exemplo 2:
```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore, index1 = 1, index2 = 3. We return [1, 3].
```

### Exemplo 3:
```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

### Restrições:
- 2 <= numbers.length <= 3 * 10^4
- -1000 <= numbers[i] <= 1000
- numbers is sorted in non-decreasing order.
- -1000 <= target <= 1000
- The tests are generated such that there is exactly one solution.

---

## Abordagens

### Abordagem 1: Dois Ponteiros (Two Pointers)
Como o array está ordenado, usa dois ponteiros no início e fim. Se soma > target, move ponteiro direito para esquerda. Se soma < target, move ponteiro esquerdo para direita.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 2: Binary Search para Cada Elemento
Para cada elemento, faz binary search para target - elemento no array restante.
- **Complexidade**: O(n log n) tempo, O(1) espaço

### Abordagem 3: HashMap (para verificação)
Two-sum padrão com HashMap. Não é O(1) espaço mas útil para verificação.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 4: Dois Ponteiros Otimizado
Two pointers aprimorado que pula duplicatas para otimização.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Two Pointers | O(n) | O(1) |
| Binary Search | O(n log n) | O(1) |
| HashMap | O(n) | O(n) |
| Optimized | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: numbers=[2,7,11,15], target=9 -> [1,2]
2. **Exemplo 2**: numbers=[2,3,4], target=6 -> [1,3]
3. **Exemplo 3**: numbers=[-1,0], target=-1 -> [1,2]
4. **Números negativos**: numbers=[-5,-3,-1,0,2,4], target=-4 -> [2,3]
5. **Array grande**: 1000 elementos, target nas pontas
6. **Duplicatas**: numbers=[1,2,2,3,4], target=4 -> [1,4] ou [2,3]
7. **Target no meio**: numbers=[1..10], target=11 -> [1,10]
8. **Verificação cruzada**: Todas as abordagens produzem resultados válidos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.