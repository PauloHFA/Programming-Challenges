# 23. Merge k Sorted Lists

- **Plataforma:** [LeetCode](https://leetcode.com/problems/merge-k-sorted-lists/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Linked List / Heap (Priority Queue)
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Você recebe um array de `k` listas encadeadas `lists`, onde cada lista está ordenada em ordem crescente.

Faça a **mesclagem de todas as listas** em uma única lista encadeada ordenada e retorne-a.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: lists = [[1,4,5],[1,3,4],[2,6]]
Saída: [1,1,2,3,4,4,5,6]
Explicação: As listas encadeadas são:
[
  1->4->5,
  1->3->4,
  2->6
]
Mesclando-as em uma lista ordenada:
1->1->2->3->4->4->5->6
```

#### Exemplo 2:
```text
Entrada: lists = []
Saída: []
```

#### Exemplo 3:
```text
Entrada: lists = [[]]
Saída: []
```

---

## ⚙️ Restrições

- $k == \text{lists.length}$
- $0 \le k \le 10^4$
- $0 \le \text{lists[i].length} \le 500$
- $-10^4 \le \text{lists[i][j]} \le 10^4$
- $\text{lists[i]}$ está ordenada em **ordem crescente**.
- A soma de $\text{lists[i].length}$ não excederá $10^4$.

---

## 💡 Abordagem: Min Heap (Priority Queue)

A abordagem ótima usa uma **fila de prioridade (min-heap)** para sempre extrair o menor elemento entre os heads das `k` listas.

### Algoritmo

1. Cria um `PriorityQueue` (min-heap) ordenado por `node.val`
2. Adiciona o head de cada lista não-nula ao heap
3. Cria nó sentinela `dummy` e ponteiro `current`
4. Enquanto heap não estiver vazio:
   - Remove o menor nó (`poll`)
   - Anexa à lista resultado
   - Se o nó removido tem `next`, adiciona `next` ao heap
5. Retorna `dummy.next`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(N \log k)$ — Onde $N$ é o total de nós em todas as listas e $k$ é o número de listas. Cada inserção/remoção no heap custa $O(\log k)$, e fazemos isso $N$ vezes.
- **Espaço:** $O(k)$ — O heap armazena no máximo $k$ elementos (um de cada lista).

> **Alternativa:** Dividir e Conquistar (Merge Sort style) - também $O(N \log k)$ tempo, $O(\log k)$ espaço (recursão).