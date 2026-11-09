# LeetCode 164 - Maximum Gap

## Problema

Given an integer array nums, return the maximum difference between two successive elements in its sorted form. If the array contains less than two elements, return 0.

You must write an algorithm that runs in linear time and uses linear extra space.

### Exemplo 1:
```
Input: nums = [3,6,9,1]
Output: 3
Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
```

### Exemplo 2:
```
Input: nums = [10]
Output: 0
Explanation: The array contains less than 2 elements, therefore return 0.
```

### Restrições:
- 1 <= nums.length <= 10^5
- 0 <= nums[i] <= 10^9

---

## Abordagens

### Abordagem 1: Bucket Sort (Princípio da Casa dos Pombos)
O gap máximo deve ser pelo menos (max - min) / (n - 1). Cria n-1 buckets, cada um de tamanho bucketSize = ceil((max-min)/(n-1)). O gap máximo estará entre buckets, não dentro deles.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 2: Radix Sort
Usa LSD (Least Significant Digit) radix sort para inteiros. Depois encontra o gap máximo no array ordenado.
- **Complexidade**: O(n * k) tempo onde k é número de dígitos (constante para int 32-bit), O(n) espaço

### Abordagem 3: Built-in Sort (para verificação)
Usa Arrays.sort para verificação.
- **Complexidade**: O(n log n) tempo, O(1) espaço

### Abordagem 4: TreeSet (para verificação)
Usa TreeSet para manter ordem classificada.
- **Complexidade**: O(n log n) tempo, O(n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Bucket Sort | O(n) | O(n) |
| Radix Sort | O(n) | O(n) |
| Built-in Sort | O(n log n) | O(1) |
| TreeSet | O(n log n) | O(n) |

---

## Casos de Teste

1. **Exemplo 1**: nums=[3,6,9,1] -> 3
2. **Exemplo 2**: nums=[10] -> 0
3. **Dois elementos**: nums=[1,100] -> 99
4. **Todos iguais**: nums=[5,5,5,5] -> 0
5. **Já ordenado**: nums=[1,2,3,4,5] -> 1
6. **Ordem reversa**: nums=[5,4,3,2,1] -> 1
7. **Gap grande no meio**: nums=[1,10,20,30] -> 10 (gaps: 9,10,10)
8. **Múltiplos gaps grandes**: nums=[1,3,100,102,200] -> 98
9. **Números grandes**: nums=[1000000000,0,500000000] -> 500000000
10. **Array aleatório**: 1000 elementos aleatórios
11. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.