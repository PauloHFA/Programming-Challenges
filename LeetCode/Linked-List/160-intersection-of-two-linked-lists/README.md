# LeetCode 160 - Intersection of Two Linked Lists

## Problema

Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.

### Exemplo 1:
```
Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
Output: Intersected at '8'
```

### Exemplo 2:
```
Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Intersected at '2'
```

### Exemplo 3:
```
Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: No intersection
```

### Restrições:
- The number of nodes of listA is in the m.
- The number of nodes of listB is in the n.
- 1 <= m, n <= 3 * 10^4
- 1 <= Node.val <= 10^5
- 0 <= skipA < m
- 0 <= skipB < n
- intersectVal is 0 if listA and listB do not intersect.

### Follow up: Could you write a solution that runs in O(m + n) time and uses only O(1) memory?

---

## Abordagens

### Abordagem 1: Dois Ponteiros (Two Pointers)
Usa dois ponteiros que percorrem ambas as listas. Quando um ponteiro chega ao fim, redireciona para a cabeça da outra lista. Eles se encontram na interseção ou ambos se tornam null.
- **Complexidade**: O(m + n) tempo, O(1) espaço

### Abordagem 2: Diferença de Comprimento (Length Difference)
Calcula comprimentos de ambas as listas. Avança o ponteiro da lista mais longa pela diferença. Depois move ambos juntos até se encontrarem.
- **Complexidade**: O(m + n) tempo, O(1) espaço

### Abordagem 3: HashSet
Armazena todos os nós da listaA em um HashSet, depois percorre listaB para encontrar o primeiro nó comum.
- **Complexidade**: O(m + n) tempo, O(m) espaço

### Abordagem 4: Detecção de Ciclo (Cycle Detection)
Conecta o fim da listaA à cabeça da listaB, depois detecta ciclo na listaA. O ponto de entrada do ciclo é a interseção. Restaura a lista após detecção.
- **Complexidade**: O(m + n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Two Pointers | O(m + n) | O(1) |
| Length Difference | O(m + n) | O(1) |
| HashSet | O(m + n) | O(m) |
| Cycle Detection | O(m + n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: listA=[4,1,8,4,5], listB=[5,6,1,8,4,5], intersect at 8
2. **Exemplo 2**: listA=[1,9,1,2,4], listB=[3,2,4], intersect at 2
3. **Exemplo 3**: listA=[2,6,4], listB=[1,5], no intersection
3. **Mesma lista**: headA == headB
4. **Uma lista vazia**: headB = null
5. **Interseção na cabeça**: headA é o nó de interseção
6. **Interseção na cauda**: último nó é compartilhado
7. **Listas longas**: 50+ nós com interseção
8. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.