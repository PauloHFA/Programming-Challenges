# LeetCode 189 - Rotate Array

## Problema

Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

### Exemplo 1:
```
Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
```

### Exemplo 2:
```
Input: nums = [-1,-100,3,99], k = 2
Output: [3,99,-1,-100]
```

### Restrições:
- 1 <= nums.length <= 10^5
- -2^31 <= nums[i] <= 2^31 - 1
- 0 <= k <= 10^5

### Follow up:
- Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
- Could you do it in-place with O(1) extra space?

---

## Abordagens

### Abordagem 1: Reverse Array (Ótima - O(1) espaço)
Inverte o array inteiro, depois inverte os primeiros k elementos, depois inverte o resto.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 2: Cyclic Replacements (O(1) espaço)
Move cada elemento para sua posição final em ciclos.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 3: Array Extra (O(n) espaço)
Usa array auxiliar para colocar elementos nas posições corretas.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 4: Collections.rotate (Built-in Java)
Converte para List, usa Collections.rotate, converte de volta.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 5: System.arraycopy (O(k) espaço)
Copia últimos k elementos para temp, desloca o resto, copia temp para início.
- **Complexidade**: O(n) tempo, O(k) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Reverse | O(n) | O(1) |
| Cyclic | O(n) | O(1) |
| Extra Array | O(n) | O(n) |
| Collections.rotate | O(n) | O(n) |
| arraycopy | O(n) | O(k) |

---

## Casos de Teste

1. **[1,2,3,4,5,6,7], k=3** -> [5,6,7,1,2,3,4]
2. **[-1,-100,3,99], k=2** -> [3,99,-1,-100]
3. **[1,2,3,4,5], k=0** -> [1,2,3,4,5]
4. **[1,2,3,4,5], k=5** -> [1,2,3,4,5]
5. **[1,2,3,4,5], k=7** -> [4,5,1,2,3]
6. **[1], k=100** -> [1]
7. **[1,2], k=1** -> [2,1]
8. **[1,2,3,4], k=2** -> [3,4,1,2]
9. **[1,2,3,4,5,6], k=11** -> [2,3,4,5,6,1]
10. **[1,1,1,1], k=2** -> [1,1,1,1]

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.