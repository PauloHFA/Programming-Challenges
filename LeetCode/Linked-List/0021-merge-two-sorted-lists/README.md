# 21. Merge Two Sorted Lists

- **Plataforma:** [LeetCode](https://leetcode.com/problems/merge-two-sorted-lists/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Linked List
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Você recebe os heads de duas listas encadeadas ordenadas `list1` e `list2`.

Faça a **mesclagem** das duas listas em uma **única lista ordenada**. A lista deve ser feita unindo os nós das duas primeiras listas.

Retorne o head da lista encadeada mesclada.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: list1 = [1,2,4], list2 = [1,3,4]
Saída: [1,1,2,3,4,4]
```

#### Exemplo 2:
```text
Entrada: list1 = [], list2 = []
Saída: []
```

#### Exemplo 3:
```text
Entrada: list1 = [], list2 = [0]
Saída: [0]
```

---

## ⚙️ Restrições

- O número de nós em ambas as listas está no intervalo `[0, 50]`.
- `-100 <= Node.val <= 100`
- Ambas `list1` e `list2` estão ordenadas em **ordem não decrescente**.

---

## 💡 Abordagem: Iterativo com Nó Sentinela (Dummy Node)

Usamos um nó sentinela (`dummy`) para simplificar a manipulação do head da lista resultante. Mantemos um ponteiro `current` que sempre aponta para o último nó da lista mesclada.

### Algoritmo

1. Cria `dummy = new ListNode(0)` e `current = dummy`
2. Enquanto ambas as listas não forem nulas:
   - Compara `list1.val` e `list2.val`
   - Anexa o menor a `current.next`
   - Avança o ponteiro da lista de onde veio o nó
   - Avança `current`
3. Anexa o restante da lista não nula (se houver)
4. Retorna `dummy.next`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n + m)$ — Onde $n$ e $m$ são os comprimentos das listas. Visitamos cada nó no máximo uma vez.
- **Espaço:** $O(1)$ — Apenas ponteiros auxiliares (não criamos novos nós, apenas rearranjamos os existentes).