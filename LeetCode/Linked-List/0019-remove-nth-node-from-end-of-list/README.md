# 19. Remove Nth Node From End of List

- **Plataforma:** [LeetCode](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Linked List
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado o `head` de uma lista encadeada, remova o **n-ésimo nó a partir do final** da lista e retorne seu head.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: head = [1,2,3,4,5], n = 2
Saída: [1,2,3,5]
```

#### Exemplo 2:
```text
Entrada: head = [1], n = 1
Saída: []
```

#### Exemplo 3:
```text
Entrada: head = [1,2], n = 1
Saída: [1]
```

---

## ⚙️ Restrições

- O número de nós na lista é `sz`.
- $1 \le sz \le 30$
- $0 \le \text{Node.val} \le 100$
- $1 \le n \le sz$

---

## 💡 Abordagem: Dois Ponteiros (Fast and Slow)

A ideia é usar dois ponteiros com uma distância de `n+1` entre eles. Quando o ponteiro rápido chegar ao final, o ponteiro lento estará no nó **anterior** ao que deve ser removido.

### Algoritmo

1. Cria um nó **sentinela (dummy)** apontando para o head (facilita remoção do head)
2. Inicializa `fast` e `slow` no dummy
3. Move `fast` `n+1` passos à frente
4. Move ambos `fast` e `slow` até `fast` chegar ao final (`null`)
5. Agora `slow` está no nó anterior ao alvo: `slow.next = slow.next.next`
6. Retorna `dummy.next`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a lista uma única vez.
- **Espaço:** $O(1)$ — Apenas ponteiros auxiliares.