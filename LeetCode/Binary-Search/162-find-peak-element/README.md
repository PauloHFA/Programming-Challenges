# LeetCode 162 - Find Peak Element

## Problema

A peak element is an element that is strictly greater than its neighbors. Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in O(log n) time.

### Exemplo 1:
```
Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.
```

### Exemplo 2:
```
Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
```

### Restrições:
- 1 <= nums.length <= 1000
- -2^31 <= nums[i] <= 2^31 - 1
- nums[i] != nums[i + 1] for all valid i.

---

## Abordagens

### Abordagem 1: Binary Search
Se nums[mid] < nums[mid+1], o pico está à direita. Se nums[mid] > nums[mid+1], o pico está à esquerda (incluindo mid).
- **Complexidade**: O(log n) tempo, O(1) espaço

### Abordagem 2: Binary Search com Vizinhos Explícitos
Verifica explicitamente se mid é um pico comparando com ambos os vizinhos.
- **Complexidade**: O(log n) tempo, O(1) espaço

### Abordagem 3: Linear Scan
Varredura linear simples para encontrar qualquer pico. Para verificação.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 4: Binary Search Recursivo
Versão recursiva do binary search.
- **Complexidade**: O(log n) tempo, O(log n) espaço (stack de recursão)

### Abordagem 5: Encontrar Todos os Picos
Encontra todos os picos para verificação.
- **Complexidade**: O(n) tempo, O(k) espaço onde k é número de picos

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Binary Search | O(log n) | O(1) |
| Explicit Neighbors | O(log n) | O(1) |
| Linear Scan | O(n) | O(1) |
| Recursive | O(log n) | O(log n) |
| All Peaks | O(n) | O(k) |

---

## Casos de Teste

1. **Exemplo 1**: nums=[1,2,3,1] -> 2 (pico=3)
2. **Exemplo 2**: nums=[1,2,1,3,5,6,4] -> 1 ou 5 (picos=2,6)
3. **Elemento único**: nums=[1] -> 0
4. **Dois elementos, primeiro pico**: nums=[2,1] -> 0
5. **Dois elementos, segundo pico**: nums=[1,2] -> 1
6. **Estritamente crescente**: nums=[1,2,3,4,5] -> 4
7. **Estritamente decrescente**: nums=[5,4,3,2,1] -> 0
8. **Múltiplos picos**: nums=[1,3,2,4,1] -> 1 ou 3
9. **Números negativos**: nums=[-2,-1,-3] -> 1
10. **Array grande**: 100 elementos com pico no meio
11. **Verificação cruzada**: Todas as abordagens produzem picos válidos

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.