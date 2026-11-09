# LeetCode 147 - Insertion Sort List

## Problema

Dado o `head` de uma lista ligada simplesmente, ordene a lista usando insertion sort e retorne o head da lista ordenada.

Os passos do algoritmo insertion sort:
1. O insertion sort itera, consumindo um elemento de entrada a cada repetição e aumentando uma lista de saída ordenada.
2. A cada iteração, o insertion sort remove um elemento dos dados de entrada, encontra a localização a que ele pertence dentro da lista ordenada e o insere lá.
3. Repete até que não reste elementos de entrada.

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

### Restrições
- O número de nós na lista está no intervalo [1, 5000].
- -5000 <= Node.val <= 5000

---

## Abordagens

### Abordagem 1: Insertion Sort Padrão em Lista Ligada - O(1) Espaço

**Intuição:** Mantenha uma porção ordenada (dummy head) e itere pela porção não ordenada. Para cada nó, encontre sua posição correta na porção ordenada e insira-o.

**Algoritmo:**
1. Crie um dummy head para a lista ordenada
2. Para cada nó na lista original:
   - Salve o próximo nó
   - Encontre a posição de inserção na lista ordenada
   - Insira o nó
3. Retorne dummy.next

**Complexidade:**
- Tempo: O(n²) pior caso, O(n) melhor caso (já ordenado)
- Espaço: O(1)

---

### Abordagem 2: Otimizada com Ponteiro Tail

**Intuição:** Mantenha o controle do tail da porção ordenada para evitar percorrer do início quando o nó atual for maior que todos os nós ordenados.

**Complexidade:**
- Tempo: O(n²) pior caso, O(n) melhor caso
- Espaço: O(1)

---

### Abordagem 3: Converter para Array, Ordenar, Reconstruir - O(n) Espaço

**Intuição:** Extraia valores para array, ordene o array, reconstrua a lista ligada. Não é insertion sort verdadeiro mas atinge o resultado.

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(n)

---

### Abordagem 4: Insertion Sort Recursivo

**Intuição:** Ordene recursivamente o resto, depois insira o head na lista ordenada.

**Complexidade:**
- Tempo: O(n²)
- Espaço: O(n) pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Padrão | O(n²) | O(1) | **Padrão** - insertion sort verdadeiro |
| Otimizada (Tail) | O(n²) | O(1) | Mais rápida para listas quase ordenadas |
| Array + Sort | O(n log n) | O(n) | Não é insertion sort verdadeiro |
| Recursiva | O(n²) | O(n) | Elegante, mas usa pilha |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [4,2,1,3] | [1,2,3,4] | Exemplo 1 |
| [-1,5,3,4,0] | [-1,0,3,4,5] | Exemplo 2 |
| [1,2,3,4,5] | [1,2,3,4,5] | Já ordenado |
| [5,4,3,2,1] | [1,2,3,4,5] | Ordenado reverso |
| [1] | [1] | Elemento único |
| [2,1] | [1,2] | Dois elementos |
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

- [LeetCode 147 - Insertion Sort List](https://leetcode.com/problems/insertion-sort-list/)
- Insertion Sort Algorithm
- Linked List Sorting