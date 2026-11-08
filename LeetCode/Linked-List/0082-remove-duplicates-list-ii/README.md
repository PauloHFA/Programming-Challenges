# 0082 - Remove Duplicates from Sorted List II

## Descrição

Dado o `head` de uma lista ligada ordenada, remova todos os nós que têm números duplicados, deixando apenas números distintos da lista original. Retorne a lista ligada ordenada também.

## Exemplos

**Exemplo 1:**
```
Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]
```

**Exemplo 2:**
```
Input: head = [1,1,1,2,3]
Output: [2,3]
```

## Restrições

- O número de nós na lista está no intervalo `[0, 300]`.
- `-100 <= Node.val <= 100`
- A lista está garantida como ordenada em ordem crescente.

## Abordagem

### Método 1: Dummy Node + Two Pointers (Principal)
- Usa um nó dummy antes do head para lidar com remoção do head
- `prev` aponta para o último nó confirmado como não-duplicado
- `curr` percorre a lista
- Se `curr` tem duplicatas (`curr.val == curr.next.val`), pula todos os nós com esse valor
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Two Pointers com Detecção Explícita
- Similar ao método 1, mas com flag `isDuplicate`
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Recursivo
- Se head tem duplicatas, pula todos e recursa no restante
- Caso contrário, mantém head e recursa no next
- **Tempo:** O(n), **Espaço:** O(n) para pilha de recursão

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Dummy + Two Pointers | O(n) | O(1) |
| Two Pointers Explícito | O(n) | O(1) |
| Recursivo | O(n) | O(n) |

## Implementação

A solução principal usa **Dummy Node + Two Pointers**.

```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode dummy = new ListNode(0, head);
    ListNode prev = dummy;
    ListNode curr = head;
    
    while (curr != null) {
        if (curr.next != null && curr.val == curr.next.val) {
            int duplicateVal = curr.val;
            while (curr != null && curr.val == duplicateVal) {
                curr = curr.next;
            }
            prev.next = curr;
        } else {
            prev = curr;
            curr = curr.next;
        }
    }
    
    return dummy.next;
}
```

## Casos de Teste

- `[1,2,3,3,4,4,5]` → `[1,2,5]`
- `[1,1,1,2,3]` → `[2,3]`
- `[1,1,1,2,2,3,3]` → `[]`
- `[1,2,3,4,5]` → `[1,2,3,4,5]`
- `[1,1]` → `[]`
- `[1]` → `[1]`
- `[]` → `[]`
- `[1,1,2,2,3,3,4,4]` → `[]`
- `[1,2,2,3,3,4,4,5,5]` → `[1]`