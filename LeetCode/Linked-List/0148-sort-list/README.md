# LeetCode 148 - Sort List

## Problema

Dado o `head` de uma lista ligada, retorne a lista após ordená-la em ordem crescente.

### Exemplos

**Exemplo 1:**
```
Input: head = [4,2,1,3]
Output: [1,2,3,4]
```

**Exemplo 2:**
```
Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]
```

**Exemplo 3:**
```
Input: head = []
Output: []
```

### Restrições
- O número de nós na lista está no intervalo [0, 5 * 10^4].
- -10^5 <= Node.val <= 10^5

**Follow up:** Você pode ordenar a lista ligada em tempo O(n log n) e memória O(1)?

---

## Abordagens

### Abordagem 1: Merge Sort (Top-Down) - O(n log n) Tempo, O(log n) Espaço

**Intuição:** Divida recursivamente a lista ao meio, ordene cada metade, depois mescle. Use ponteiros fast/slow para encontrar o meio.

**Algoritmo:**
1. Caso base: lista vazia ou com 1 nó
2. Encontre o meio da lista (slow/fast pointers)
3. Divida a lista em duas metades
4. Ordene recursivamente ambas as metades
5. Mescle as duas metades ordenadas

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(log n) pilha de recursão

---

### Abordagem 2: Merge Sort (Bottom-Up) - O(n log n) Tempo, O(1) Espaço

**Intuição:** Merge sort iterativo usando sublistas de tamanho crescente (1, 2, 4, 8...). Alcança complexidade de espaço O(1).

**Algoritmo:**
1. Obtenha o comprimento da lista
2. Para size = 1, 2, 4, 8... < length:
   - Percorra a lista, dividindo em sublistas de tamanho 'size'
   - Mescle pares de sublistas adjacentes
   - Reconecte as sublistas mescladas

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(1) ✓ (atende follow-up)

---

### Abordagem 3: Converter para Array, Ordenar, Reconstruir - O(n log n) Tempo, O(n) Espaço

**Intuição:** Abordagem simples: extraia valores, ordene array, reconstrua lista.

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(n)

---

### Abordagem 4: Quick Sort em Lista Ligada - O(n log n) Médio, O(n²) Pior

**Intuição:** Particione em torno de pivô, ordene recursivamente as partições.

**Complexidade:**
- Tempo: O(n log n) médio, O(n²) pior caso
- Espaço: O(log n) médio pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Merge Sort Top-Down | O(n log n) | O(log n) | Padrão, fácil de implementar |
| Merge Sort Bottom-Up | O(n log n) | O(1) | **Ótima** - atende follow-up |
| Array + Sort | O(n log n) | O(n) | Simples, mas usa espaço extra |
| Quick Sort | O(n log n) médio | O(log n) | Não estável, pior caso O(n²) |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [4,2,1,3] | [1,2,3,4] | Exemplo 1 |
| [-1,5,3,4,0] | [-1,0,3,4,5] | Exemplo 2 |
| [] | [] | Exemplo 3 - lista vazia |
| [1] | [1] | Elemento único |
| [1,2,3,4,5] | [1,2,3,4,5] | Já ordenado |
| [5,4,3,2,1] | [1,2,3,4,5] | Ordenado reverso |
| [3,1,2,1,3,2] | [1,1,2,2,3,3] | Com duplicatas |
| [0,-1,-2,-3] | [-3,-2,-1,0] | Negativos |

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

- [LeetCode 148 - Sort List](https://leetcode.com/problems/sort-list/)
- Merge Sort on Linked List
- Bottom-Up Merge Sort