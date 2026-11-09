# LeetCode 179 - Largest Number

## Problema

Given a list of non-negative integers `nums`, arrange them such that they form the largest number and return it. Since the result may be very large, so you need to return a string instead of an integer.

### Exemplo 1:
```
Input: nums = [10,2]
Output: "210"
```

### Exemplo 2:
```
Input: nums = [3,30,34,5,9]
Output: "9534330"
```

### Restrições:
- 1 <= nums.length <= 100
- 0 <= nums[i] <= 10^9

---

## Abordagens

### Abordagem 1: Comparator Personalizado com Concatenação de Strings (Ótima)
Compara `a+b` vs `b+a` para determinar a ordem. Se `b+a > a+b`, então `b` deve vir antes de `a`.
- **Complexidade**: O(n log n) tempo, O(n) espaço

### Abordagem 2: Usando List e Collections.sort
Mesma lógica da abordagem 1 mas usando List e Collections.sort.

### Abordagem 3: Bucket Sort por Primeiro Dígito
Agrupa números pelo primeiro dígito (0-9), ordena dentro de cada bucket com o comparator personalizado, depois concatena do bucket 9 ao 0.
- **Complexidade**: O(n log n) tempo, O(n) espaço

### Abordagem 4: Priority Queue (Max Heap)
Usa PriorityQueue com o comparator personalizado.
- **Complexidade**: O(n log n) tempo, O(n) espaço

### Abordagem 5: QuickSort In-Place
Implementação manual de QuickSort com partition personalizado usando o mesmo comparator.
- **Complexidade**: O(n log n) tempo médio, O(log n) espaço (stack)

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Comparator + Arrays.sort | O(n log n) | O(n) |
| List + Collections.sort | O(n log n) | O(n) |
| Bucket Sort | O(n log n) | O(n) |
| Priority Queue | O(n log n) | O(n) |
| QuickSort | O(n log n) médio | O(log n) |

---

## Casos de Teste

1. **[10,2]** -> "210"
2. **[3,30,34,5,9]** -> "9534330"
3. **[0,0,0]** -> "0"
4. **[5]** -> "5"
5. **[9,8,7,6,5]** -> "98765"
6. **[121,12]** -> "12121"
7. **[1000000000,999999999]** -> "9999999991000000000"
8. **[1,2,3,4,5,6,7,8,9,10]** -> "98765432110"
9. **[34323,3432]** -> "343234323"
10. **[12,121]** -> "12121"

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.