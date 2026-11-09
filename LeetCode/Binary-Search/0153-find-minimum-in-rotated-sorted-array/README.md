# LeetCode 153 - Find Minimum in Rotated Sorted Array

## Problema

Suponha que um array de comprimento n ordenado em ordem crescente seja rotacionado entre 1 e n vezes.
Por exemplo, o array `nums = [0,1,2,4,5,6,7]` pode se tornar:
- `[4,5,6,7,0,1,2]` se foi rotacionado 4 vezes.
- `[0,1,2,4,5,6,7]` se foi rotacionado 7 vezes.

Note que rotacionar um array `[a[0], a[1], ..., a[n-1]]` 1 vez resulta no array `[a[n-1], a[0], a[1], ..., a[n-2]]`.

Dado o array rotacionado ordenado `nums` de elementos únicos, retorne o elemento mínimo deste array.

Você deve escrever um algoritmo que rode em tempo O(log n).

### Exemplos

**Exemplo 1:**
```
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: O array original era [1,2,3,4,5] rotacionado 3 vezes.
```

**Exemplo 2:**
```
Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: O array original era [0,1,2,4,5,6,7] rotacionado 4 vezes.
```

**Exemplo 3:**
```
Input: nums = [11,13,15,17]
Output: 11
Explanation: O array original era [11,13,15,17] rotacionado 4 vezes.
```

### Restrições
- n == nums.length
- 1 <= n <= 5000
- -5000 <= nums[i] <= 5000
- Todos os inteiros de nums são únicos.
- nums é ordenado e rotacionado entre 1 e n vezes.

---

## Abordagens

### Abordagem 1: Binary Search - Padrão O(log n)

**Intuição:** O elemento mínimo é o único onde `nums[i] < nums[i-1]`. Em um array rotacionado ordenado, uma metade é sempre ordenada. Compare `mid` com `right` para decidir qual metade buscar.

**Algoritmo:**
1. `left = 0`, `right = n-1`
2. Enquanto `left < right`:
   - `mid = left + (right - left) / 2`
   - Se `nums[mid] > nums[right]`: mínimo está na metade direita → `left = mid + 1`
   - Senão: mínimo está na metade esquerda (incluindo mid) → `right = mid`
3. Retorne `nums[left]`

**Complexidade:**
- Tempo: O(log n)
- Espaço: O(1)

---

### Abordagem 2: Binary Search - Comparar com Left

**Intuição:** Alternativa: compare `mid` com elemento left.

**Complexidade:**
- Tempo: O(log n)
- Espaço: O(1)

---

### Abordagem 3: Binary Search - Encontrar Índice do Pivô

**Intuição:** Encontre o pivô (ponto de rotação), então o mínimo está no pivô.

**Complexidade:**
- Tempo: O(log n)
- Espaço: O(1)

---

### Abordagem 4: Linear Scan (para verificação)

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Binary Search (Right) | O(log n) | O(1) | **Padrão** - mais limpa |
| Binary Search (Left) | O(log n) | O(1) | Alternativa |
| Binary Search (Pivot) | O(log n) | O(1) | Explícita |
| Linear Scan | O(n) | O(1) | Apenas verificação |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [3,4,5,1,2] | 1 | Exemplo 1 |
| [4,5,6,7,0,1,2] | 0 | Exemplo 2 |
| [11,13,15,17] | 11 | Exemplo 3 - não rotacionado |
| [1] | 1 | Elemento único |
| [2,1] | 1 | Dois elementos |
| [1,2,3,4,5] | 1 | Rotacionado n vezes |
| [5,6,1,2,3,4] | 1 | Mínimo no meio |
| [-5,-4,-3,-2,-1] | -5 | Negativos |
| [1,2,-2,-1,0] | -2 | Rotacionado com negativos |

---

## Implementação

O arquivo `Solution.java` contém todas as 4 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 153 - Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
- Binary Search
- Rotated Sorted Array