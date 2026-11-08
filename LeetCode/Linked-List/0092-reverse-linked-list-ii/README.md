# 0092 - Reverse Linked List II

## Descrição

Dado o `head` de uma lista ligada simples e dois inteiros `left` e `right` onde `left <= right`, reverta os nós da lista da posição `left` à posição `right`, e retorne a lista revertida.

## Exemplos

**Exemplo 1:**
```
Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]
```

**Exemplo 2:**
```
Input: head = [5], left = 1, right = 1
Output: [5]
```

## Restrições

- O número de nós na lista é `n`.
- `1 <= n <= 500`
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n`

## Abordagem

### Método 1: One Pass - Iterativo (Principal)
- Usa dummy node para simplificar edge cases
- Encontra o nó antes de `left` (`prev`)
- Usa técnica de "head insertion" para reverter in-place:
  - `start` = primeiro nó da sublista a reverter
  - `then` = nó que será movido para frente de `start`
  - Repete `right - left` vezes
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Recursivo
- Se `left == 1`, reverte os primeiros `right` nós
- Caso contrário, recursa em `head.next` com `left-1, right-1`
- Usa variável `successor` para conectar o final da parte revertida
- **Tempo:** O(n), **Espaço:** O(n) stack

### Método 3: Array (Coleta valores)
- Coleta todos os valores em array
- Reverte subarray `[left-1, right-1]`
- Reconstrói lista com novos valores
- **Tempo:** O(n), **Espaço:** O(n)

### Método 4: Stack
- Empilha nós de `left` a `right`
- Desempilha reconectando
- **Tempo:** O(n), **Espaço:** O(right-left+1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| One Pass | O(n) | O(1) |
| Recursivo | O(n) | O(n) |
| Array | O(n) | O(n) |
| Stack | O(n) | O(k) |

## Implementação

A solução principal usa **One Pass Iterativo**.

```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null || left == right) return head;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    
    // Move prev para o nó antes de left
    for (int i = 1; i < left; i++) {
        prev = prev.next;
    }
    
    ListNode start = prev.next;
    ListNode then = start.next;
    
    // Reverte a sublista entre left e right
    for (int i = 0; i < right - left; i++) {
        start.next = then.next;
        then.next = prev.next;
        prev.next = then;
        then = start.next;
    }
    
    return dummy.next;
}
```

## Casos de Teste

- `[1,2,3,4,5], left=2, right=4` → `[1,4,3,2,5]`
- `[5], left=1, right=1` → `[5]`
- `[1,2,3,4,5], left=1, right=5` → `[5,4,3,2,1]` (lista inteira)
- `[1,2,3,4,5], left=1, right=3` → `[3,2,1,4,5]` (do head)
- `[1,2,3,4,5], left=3, right=5` → `[1,2,5,4,3]` (até tail)
- `[1,2,3,4], left=2, right=3` → `[1,3,2,4]` (dois do meio)
- `[1,2,3], left=2, right=2` → `[1,2,3]` (single element)
- `[1,2], left=1, right=2` → `[2,1]` (dois elementos)
- `[1,2,3], left=1, right=2` → `[2,1,3]`
- `[1,2,3], left=2, right=3` → `[1,3,2]`
- `[1,2,3,4,5,6,7,8,9], left=3, right=7` → `[1,2,7,6,5,4,3,8,9]`