# LeetCode 170 - Two Sum III - Data Structure Design

## Problema

Design a data structure that accepts a stream of integers and checks if it has a pair of integers that sum up to a particular value.

Implement the TwoSum class:
- `TwoSum()` Initializes the TwoSum object, with an empty array initially.
- `void add(int number)` Adds number to the data structure.
- `boolean find(int value)` Returns true if there exists any pair of numbers whose sum is equal to value, otherwise, it returns false.

### Exemplo 1:
```
Input
["TwoSum", "add", "add", "add", "find", "find"]
[[], [1], [3], [5], [4], [7]]
Output
[null, null, null, null, true, false]

Explanation
TwoSum twoSum = new TwoSum();
twoSum.add(1);   // [] -> [1]
twoSum.add(3);   // [1] -> [1,3]
twoSum.add(5);   // [1,3] -> [1,3,5]
twoSum.find(4);  // 1 + 3 = 4, return true
twoSum.find(7);  // No pair sums to 7, return false
```

### Restrições:
- -10^5 <= number <= 10^5
- -2^31 <= value <= 2^31 - 1
- At most 10^4 calls will be made to add and find.

---

## Abordagens

### Abordagem 1: HashMap com Contagens
Armazena contagens de cada número. Para find, itera pelas chaves e verifica se o complemento existe. Trata caso onde número == complemento (precisa count >= 2).
- **Complexidade**: add O(1), find O(n), espaço O(n)

### Abordagem 2: ArrayList + HashSet para Find
Armazena números em ArrayList. Para find, usa HashSet para verificar complementos.
- **Complexidade**: add O(1), find O(n), espaço O(n)

### Abordagem 3: Array Ordenado + Binary Search
Mantém array ordenado. Add usa insertion sort. Find usa dois ponteiros.
- **Complexidade**: add O(n), find O(n), espaço O(n)

### Abordagem 4: HashSet de Todas as Somas Possíveis
Pré-computa todas as somas possíveis ao adicionar. Troca espaço por find mais rápido.
- **Complexidade**: add O(n), find O(1), espaço O(n²) pior caso

### Abordagem 5: Dois HashMaps (Números e Somas)
Similar à Abordagem 4 mas com contagens para lidar com duplicatas corretamente.
- **Complexidade**: add O(n), find O(1), espaço O(n²) pior caso

---

## Complexidade

| Abordagem | Add | Find | Espaço |
|-----------|-----|------|--------|
| HashMap | O(1) | O(n) | O(n) |
| ArrayList | O(1) | O(n) | O(n) |
| Sorted Array | O(n) | O(n) | O(n) |
| Precompute Sums | O(n) | O(1) | O(n²) |
| Optimized | O(n) | O(1) | O(n²) |

---

## Casos de Teste

1. **Exemplo 1**: add(1), add(3), add(5), find(4)=true, find(7)=false
2. **Duplicatas**: add(2), add(2), find(4)=true
3. **Números negativos**: add(-1), add(1), find(0)=true
4. **Números grandes**: add(100000), add(-100000), find(0)=true
5. **Muitos adds**: 100 números, find(101)=true
6. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.