# 0086 - Partition List

## Descrição

Dado o `head` de uma lista ligada e um valor `x`, particione a lista de forma que todos os nós com valor **menor que** `x` venham antes dos nós com valor **maior ou igual a** `x`.

Você deve **preservar a ordem relativa original** dos nós em cada uma das duas partições.

## Exemplos

**Exemplo 1:**
```
Input: head = [1,4,3,2,5,2], x = 3
Output: [1,2,2,4,3,5]
Explanation: Nós < 3: [1,2,2], Nós >= 3: [4,3,5]. Ordem relativa preservada.
```

**Exemplo 2:**
```
Input: head = [2,1], x = 2
Output: [1,2]
```

## Restrições

- O número de nós na lista está no intervalo `[0, 200]`.
- `-100 <= Node.val <= 100`
- `-200 <= x <= 200`

## Abordagem

### Método 1: Two Dummy Lists (Principal)
- Cria duas listas dummy: `before` (valores < x) e `after` (valores >= x)
- Percorre a lista original uma vez, anexando cada nó à lista apropriada
- Concatena `before` + `after`
- **Tempo:** O(n), **Espaço:** O(1) extra (apenas ponteiros)

### Método 2: In-Place
- Move nós < x para a posição correta mantendo apenas ponteiros
- Mais complexo mas não cria novos nós
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Coleta e Reconstrói
- Coleta valores em dois arrays, depois reconstrói a lista
- Simples mas usa O(n) espaço extra
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Two Dummy Lists | O(n) | O(1) |
| In-Place | O(n) | O(1) |
| Coleta/Reconstrói | O(n) | O(n) |

## Implementação

A solução principal usa **Two Dummy Lists**.

```java
public ListNode partition(ListNode head, int x) {
    ListNode beforeHead = new ListNode(0);
    ListNode afterHead = new ListNode(0);
    ListNode before = beforeHead;
    ListNode after = afterHead;
    
    while (head != null) {
        if (head.val < x) {
            before.next = head;
            before = before.next;
        } else {
            after.next = head;
            after = after.next;
        }
        head = head.next;
    }
    
    after.next = null;        // Importante: evita ciclo
    before.next = afterHead.next;
    
    return beforeHead.next;
}
```

## Casos de Teste

- `[1,4,3,2,5,2], x=3` → `[1,2,2,4,3,5]`
- `[2,1], x=2` → `[1,2]`
- `[1,2,3], x=5` → `[1,2,3]` (todos < x)
- `[5,6,7], x=3` → `[5,6,7]` (todos >= x)
- `[1], x=2` → `[1]`
- `[], x=3` → `[]`
- `[1,2,3,4,5], x=3` → `[1,2,3,4,5]` (já particionado)
- `[5,4,3,2,1], x=3` → `[2,1,5,4,3]`
- `[3,3,3], x=3` → `[3,3,3]` (todos == x)
- `[3,1,2,3,4,2,1], x=3` → `[1,2,2,1,3,3,4]`