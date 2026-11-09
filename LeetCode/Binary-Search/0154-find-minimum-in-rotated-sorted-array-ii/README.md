# LeetCode 154 - Find Minimum in Rotated Sorted Array II

## Problema

Suponha que um array de comprimento n ordenado em ordem crescente seja rotacionado entre 1 e n vezes.
Por exemplo, o array `nums = [0,1,2,4,5,6,7]` pode se tornar:
- `[4,5,6,7,0,1,2]` se foi rotacionado 4 vezes.
- `[0,1,2,4,5,6,7]` se foi rotacionado 7 vezes.

Note que rotacionar um array `[a[0], a[1], ..., a[n-1]]` 1 vez resulta no array `[a[n-1], a[0], a[1], ..., a[n-2]]`.

Dado o array rotacionado ordenado `nums` que **pode conter duplicatas**, retorne o elemento mínimo deste array.

Você deve escrever um algoritmo que rode em tempo O(log n) em média.

### Exemplos

**Exemplo 1:**
```
Input: nums = [1,3,5]
Output: 1
```

**Exemplo 2:**
```
Input: nums = [2,2,2,0,1]
Output: 0
```

### Restrições
- n == nums.length
- 1 <= n <= 5000
- -5000 <= nums[i] <= 5000
- nums é ordenado e rotacionado entre 1 e n vezes.

**Follow up:** Este problema é similar ao Find Minimum in Rotated Sorted Array, mas nums pode conter duplicatas. Isso afetaria a complexidade de tempo de execução? Como e por quê?

---

## Abordagens

### Abordagem 1: Binary Search com Tratamento de Duplicatas - O(log n) média, O(n) pior

**Intuição:** Quando `nums[mid] == nums[right]`, não podemos determinar qual metade tem o mínimo. Neste caso, decrementamos `right` em 1 (seguro porque `nums[right]` tem um duplicado em `mid`).

**Algoritmo:**
1. `left = 0`, `right = n-1`
2. Enquanto `left < right`:
   - `mid = left + (right - left) / 2`
   - Se `nums[mid] > nums[right]`: mínimo na metade direita → `left = mid + 1`
   - Se `nums[mid] < nums[right]`: mínimo na metade esquerda → `right = mid`
   - Se `nums[mid] == nums[right]`: não dá para saber → `right--`
3. Retorne `nums[left]`

**Complexidade:**
- Tempo: O(log n) média, O(n) pior caso (todas duplicatas)
- Espaço: O(1)

---

### Abordagem 2: Binary Search - Comparar com Left

**Intuição:** Implementação alternativa comparando com elemento left.

**Complexidade:**
- Tempo: O(log n) média, O(n) pior caso
- Espaço: O(1)

---

### Abordagem 3: Binary Search - Encontrar Pivô com Duplicatas

**Intuição:** Encontre explicitamente o ponto de pivô onde a rotação ocorre.

**Complexidade:**
- Tempo: O(log n) média, O(n) pior caso
- Espaço: O(1)

---

### Abordagem 4: Linear Scan (para verificação)

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 5: Binary Search Recursivo

**Complexidade:**
- Tempo: O(log n) média, O(n) pior caso
- Espaço: O(log n) pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Binary Search (Right) | O(log n) médio | O(1) | **Padrão** - mais limpa |
| Binary Search (Left) | O(log n) médio | O(1) | Alternativa |
| Binary Search (Pivot) | O(log n) médio | O(1) | Explícita |
| Linear Scan | O(n) | O(1) | Apenas verificação |
| Recursivo | O(log n) médio | O(log n) | Elegante |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [1,3,5] | 1 | Exemplo 1 |
| [2,2,2,0,1] | 0 | Exemplo 2 |
| [2,2,2,2,2] | 2 | Todas duplicatas |
| [3,3,1,3] | 1 | Duplicatas nas pontas |
| [1,1,2,2,3,3] | 1 | Sem rotação com duplicatas |
| [1] | 1 | Elemento único |
| [2,2] | 2 | Dois elementos duplicados |
| [10,1,10,10,10] | 1 | Rotacionado com duplicatas |
| [-2,-2,-1,-2] | -2 | Negativos com duplicatas |
| [1,1,1,0,1] | 0 | Muitas duplicatas |

---

## Implementação

O arquivo `Solution.java` contém todas as 5 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 154 - Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)
- Binary Search with Duplicates
- Rotated Sorted Array