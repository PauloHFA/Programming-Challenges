# LeetCode 163 - Missing Ranges

## Problema

You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.

A number x is considered missing if x is in the range [lower, upper] and x is not in nums.

Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.

Each range [a,b] in the list should be output as:
- "a->b" if a != b
- "a" if a == b

### Exemplo 1:
```
Input: nums = [0,1,3,50,75], lower = 0, upper = 99
Output: ["2","4->49","51->74","76->99"]
Explanation: The ranges are:
[2,2] -> "2"
[4,49] -> "4->49"
[51,74] -> "51->74"
[76,99] -> "76->99"
```

### Exemplo 2:
```
Input: nums = [-1], lower = -1, upper = -1
Output: []
Explanation: There are no missing ranges since there are no missing numbers.
```

### Restrições:
- -10^9 <= lower <= upper <= 10^9
- 0 <= nums.length <= 100
- lower <= nums[i] <= upper
- All the values of nums are unique.

---

## Abordagens

### Abordagem 1: Linear Scan
Rastreia o número anterior (começando com lower-1). Para cada número em nums, se há gap > 1, adiciona o range faltante. Finalmente verifica após o último número.
- **Complexidade**: O(n) tempo, O(1) espaço (excluindo output)

### Abordagem 2: Sentinelas
Adiciona valores sentinela nas bordas para simplificar a lógica.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 3: Dois Ponteiros
Usa dois ponteiros para rastrear início e fim do range atual.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 4: Stream-based (Java 8+)
Usando streams para estilo mais funcional.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Linear Scan | O(n) | O(1) |
| Sentinel | O(n) | O(1) |
| Two Pointers | O(n) | O(1) |
| Stream | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: nums=[0,1,3,50,75], lower=0, upper=99 -> ["2","4->49","51->74","76->99"]
2. **Exemplo 2**: nums=[-1], lower=-1, upper=-1 -> []
3. **Array vazio**: nums=[], lower=1, upper=1 -> ["1"]
4. **Array vazio, range > 1**: nums=[], lower=1, upper=5 -> ["1->5"]
5. **Sem ranges faltantes**: nums=[1,2,3,4,5], lower=1, upper=5 -> []
6. **Faltando no início**: nums=[3,4,5], lower=1, upper=5 -> ["1->2"]
7. **Faltando no fim**: nums=[1,2,3], lower=1, upper=5 -> ["4->5"]
8. **Único faltando no meio**: nums=[1,3], lower=1, upper=3 -> ["2"]
9. **Números negativos**: nums=[-3,-1], lower=-5, upper=0 -> ["-5->-4","-2","0"]
10. **Range grande**: nums=[0,100], lower=0, upper=100 -> ["1->99"]
11. **Limites Integer**: nums=[MIN,MAX], lower=MIN, upper=MAX -> ["-2147483647->2147483646"]
12. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.