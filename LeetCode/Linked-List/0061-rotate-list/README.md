# 0061 - Rotate List

## Descrição do Problema

Dado o `head` de uma lista encadeada, rotacione a lista para a direita em `k` lugares.

## Exemplos

### Exemplo 1:
```
Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]
```

### Exemplo 2:
```
Input: head = [0,1,2], k = 4
Output: [2,0,1]
```

## Restrições

- O número de nós na lista está no intervalo `[0, 500]`.
- `-100 <= Node.val <= 100`
- `0 <= k <= 2 * 10^9`

## Abordagem

### Encontrar Comprimento + Conectar Cauda à Cabeça

A ideia principal: **rotacionar uma lista é equivalente a mover o final para o início**.

**Algoritmo:**
1. Se lista vazia, único nó, ou k=0: retornar head
2. Encontrar comprimento `n` e o nó `tail` (último nó)
3. Conectar `tail.next = head` (torna circular)
4. Calcular `k = k % n` (rotações completas não mudam nada)
5. Encontrar novo tail: andar `n - k` passos a partir do head
6. Novo head = `newTail.next`
7. Quebrar círculo: `newTail.next = null`
8. Retornar novo head

**Exemplo visual (1→2→3→4→5, k=2):**
```
Original:  1 → 2 → 3 → 4 → 5 → null
Circular:  1 → 2 → 3 → 4 → 5 → 1 (tail.next = head)
n=5, k=2%5=2, steps=5-2=3
newTail = nó 3 (3 passos do head)
newHead = 4
Quebrar:   3 → null
Resultado: 4 → 5 → 1 → 2 → 3 → null
```

### Complexidade

- **Tempo:** O(n) - duas passagens (encontrar comprimento + encontrar novo tail)
- **Espaço:** O(1) - apenas ponteiros

## Implementação

A solução está em `Solution.java` com classe `ListNode` interna e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```