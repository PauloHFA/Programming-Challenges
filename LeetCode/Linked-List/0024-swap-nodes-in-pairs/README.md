# 24. Swap Nodes in Pairs

- **Plataforma:** [LeetCode](https://leetcode.com/problems/swap-nodes-in-pairs/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Linked List
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma lista encadeada, troque cada dois nós adjacentes e retorne seu head.

Você deve resolver o problema **sem modificar os valores** nos nós (apenas os nós em si podem ser alterados).

---

### Exemplos

#### Exemplo 1:
```text
Entrada: head = [1,2,3,4]
Saída: [2,1,4,3]
```

#### Exemplo 2:
```text
Entrada: head = []
Saída: []
```

#### Exemplo 3:
```text
Entrada: head = [1]
Saída: [1]
```

---

## ⚙️ Restrições

- O número de nós na lista está no intervalo `[0, 100]`.
- $0 \le \text{Node.val} \le 100$

---

## 💡 Abordagem: Iterativo com Nó Sentinela (Dummy Node)

Usamos um nó sentinela para simplificar a troca do head. Mantemos um ponteiro `prev` que sempre aponta para o nó **antes** do par a ser trocado.

### Algoritmo

1. Cria `dummy = new ListNode(0)`, `dummy.next = head`, `prev = dummy`
2. Enquanto `prev.next != null` e `prev.next.next != null` (existe um par):
   - `first = prev.next`
   - `second = prev.next.next`
   - **Troca:**
     - `first.next = second.next`
     - `second.next = first`
     - `prev.next = second`
   - Avança `prev = first` (agora `first` é o segundo do par)
3. Retorna `dummy.next`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a lista uma única vez.
- **Espaço:** $O(1)$ — Apenas ponteiros auxiliares.