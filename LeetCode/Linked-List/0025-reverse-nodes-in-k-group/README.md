# 25. Reverse Nodes in k-Group

- **Plataforma:** [LeetCode](https://leetcode.com/problems/reverse-nodes-in-k-group/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Linked List
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado o `head` de uma lista encadeada, inverta os nós da lista **k de cada vez** e retorne a lista modificada.

`k` é um número inteiro positivo e é menor ou igual ao comprimento da lista encadeada. Se o número de nós não for múltiplo de `k`, os nós restantes no final devem permanecer como estão.

Você não pode alterar os valores nos nós, apenas os próprios nós podem ser alterados.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: head = [1,2,3,4,5], k = 2
Saída: [2,1,4,3,5]
```

#### Exemplo 2:
```text
Entrada: head = [1,2,3,4,5], k = 3
Saída: [3,2,1,4,5]
```

---

## ⚙️ Restrições

- O número de nós na lista é `n`.
- $1 \le k \le n \le 5000$
- $0 \le \text{Node.val} \le 1000$

---

## 💡 Abordagem: Iterativo com Inversão de Grupos

A ideia é processar a lista em grupos de `k` nós. Para cada grupo:
1. Verifica se existem `k` nós restantes
2. Inverte o grupo
3. Conecta o grupo anterior ao grupo invertido
4. Avança para o próximo grupo

### Algoritmo

1. Cria nó sentinela `dummy`, `dummy.next = head`, `prevGroupEnd = dummy`
2. Loop infinito:
   - Encontra o k-ésimo nó a partir de `prevGroupEnd` (`getKthNode`)
   - Se não existir k-ésimo nó: break (não há grupo completo)
   - `nextGroupStart = kthNode.next`
   - Inverte os `k` nós entre `prevGroupEnd.next` e `kthNode`:
     - `prev = nextGroupStart`, `current = prevGroupEnd.next`
     - Enquanto `current != nextGroupStart`: inverte ponteiros
   - Conecta: `prevGroupEnd.next = kthNode`
   - Atualiza `prevGroupEnd` para o fim do grupo invertido (que era o início)
3. Retorna `dummy.next`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Cada nó é visitado um número constante de vezes.
- **Espaço:** $O(1)$ — Apenas ponteiros auxiliares.