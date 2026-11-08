# 0083 - Remove Duplicates from Sorted List

## Descrição

Dado o `head` de uma lista ligada ordenada, remova todas as duplicatas de forma que cada elemento apareça apenas uma vez. Retorne a lista ligada ordenada também.

## Exemplos

**Exemplo 1:**
```
Input: head = [1,1,2]
Output: [1,2]
```

**Exemplo 2:**
```
Input: head = [1,1,2,3,3]
Output: [1,2,3]
```

## Restrições

- O número de nós na lista está no intervalo `[0, 300]`.
- `-100 <= Node.val <= 100`
- A lista está garantida como ordenada em ordem crescente.

## Abordagem

### Método 1: Iterativo Simples (Principal)
- Percorre a lista com um ponteiro `curr`
- Se `curr.val == curr.next.val`, pula o próximo nó
- Caso contrário, avança `curr`
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Com Dummy Node
- Usa dummy node (embora não seja necessário para este problema)
- Útil como prática para problemas mais complexos
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Recursivo
- Processa recursivamente o resto da lista
- Se head tem mesmo valor que next, retorna next
- **Tempo:** O(n), **Espaço:** O(n) para pilha de recursão

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Iterativo | O(n) | O(1) |
| Dummy Node | O(n) | O(1) |
| Recursivo | O(n) | O(n) |

## Implementação

A solução principal usa **Iterativo Simples**.

```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode curr = head;
    
    while (curr != null && curr.next != null) {
        if (curr.val == curr.next.val) {
            curr.next = curr.next.next;
        } else {
            curr = curr.next;
        }
    }
    
    return head;
}
```

## Casos de Teste

- `[1,1,2]` → `[1,2]`
- `[1,1,2,3,3]` → `[1,2,3]`
- `[1,1,1,1,1]` → `[1]`
- `[1,2,3,4,5]` → `[1,2,3,4,5]`
- `[1,1]` → `[1]`
- `[1]` → `[1]`
- `[]` → `[]`
- `[1,2,2,3,3,4,4,5,5]` → `[1,2,3,4,5]`
- `[1,1,2,2,3,3]` → `[1,2,3]`