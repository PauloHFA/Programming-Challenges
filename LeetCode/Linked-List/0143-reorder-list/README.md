# LeetCode 143 - Reorder List

## Problema

Você recebe o `head` de uma lista ligada simplesmente. A lista pode ser representada como:
L0 → L1 → … → Ln - 1 → Ln

Reordene a lista para a seguinte forma:
L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …

Você não pode modificar os valores nos nós da lista. Apenas os próprios nós podem ser alterados.

### Exemplos

**Exemplo 1:**
```
Input: head = [1,2,3,4]
Output: [1,4,2,3]
```

**Exemplo 2:**
```
Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]
```

### Restrições
- O número de nós na lista está no intervalo [1, 5 * 10^4].
- 1 <= Node.val <= 1000

---

## Abordagens

### Abordagem 1: Encontrar Meio + Reverter Segunda Metade + Mesclar - O(1) Espaço

**Intuição:**
1. Encontre o meio da lista usando ponteiros slow/fast
2. Reverta a segunda metade
3. Mescle a primeira metade e a segunda metade revertida alternadamente

**Algoritmo:**
1. Use slow (1 passo) e fast (2 passos) para encontrar o meio
2. Reverta a lista a partir do meio
3. Mescle alternadamente: L0 → Ln → L1 → Ln-1 → ...

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1) ✓

---

### Abordagem 2: Usando Array/Deque - O(n) Espaço

**Intuição:** Armazene todos os nós em um array, depois reconstrua usando dois ponteiros (início e fim).

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 3: Recursiva - O(n) Espaço (pilha de chamada)

**Intuição:** Use recursão para chegar ao final, depois reordene no caminho de volta.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Meio + Reverter + Mesclar | O(n) | O(1) | **Ótima** - espaço constante |
| Array/Deque | O(n) | O(n) | Simples, fácil de implementar |
| Recursiva | O(n) | O(n) | Elegante, mas usa pilha |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [1,2,3,4] | [1,4,2,3] | Exemplo 1 - par |
| [1,2,3,4,5] | [1,5,2,4,3] | Exemplo 2 - ímpar |
| [1,2] | [1,2] | Dois nós |
| [1] | [1] | Nó único |
| [1,2,3] | [1,3,2] | Três nós |
| [1,2,3,4,5,6] | [1,6,2,5,3,4] | Seis nós |
| [1,2,3,4,5,6,7] | [1,7,2,6,3,5,4] | Sete nós |

---

## Implementação

O arquivo `Solution.java` contém todas as 3 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 143 - Reorder List](https://leetcode.com/problems/reorder-list/)
- Linked list manipulation
- Two pointers technique