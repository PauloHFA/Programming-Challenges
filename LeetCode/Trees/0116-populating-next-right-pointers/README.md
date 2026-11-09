# 0116 - Populating Next Right Pointers in Each Node

## Descrição

Você recebe uma **árvore binária perfeita** onde todas as folhas estão no mesmo nível, e cada pai tem dois filhos. A árvore binária tem a seguinte definição:

```java
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
}
```

Preencha cada ponteiro `next` para apontar para o próximo nó à direita. Se não houver próximo nó à direita, o ponteiro `next` deve ser `NULL`. Inicialmente, todos os ponteiros `next` são `NULL`.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explicação: 
Nível 0: 1 -> NULL
Nível 1: 2 -> 3 -> NULL
Nível 2: 4 -> 5 -> 6 -> 7 -> NULL
```

**Exemplo 2:**
```
Input: root = []
Output: []
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 2^12 - 1]`.
- `-1000 <= Node.val <= 1000`
- A árvore é uma **árvore binária perfeita** (todos os níveis completamente preenchidos)

## Abordagem

### Método 1: BFS Level Order (Principal)
- Usa queue para processar nível a nível
- Conecta `next` de cada nó para o próximo na queue
- **Tempo:** O(n), **Espaço:** O(w) onde w é largura máxima

### Método 2: O(1) Espaço - Usando next pointers já estabelecidos
- Como é árvore perfeita, usa `next` do nível pai para conectar filhos
- `head.left.next = head.right` (filhos do mesmo pai)
- `head.right.next = head.next.left` (filhos de pais adjacentes)
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: DFS Recursivo
- Conecta filhos recursivamente: mesmo pai e pais adjacentes
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 4: Iterativo com dummy head
- Usa dummy node para construir lista do próximo nível
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| BFS Level Order | O(n) | O(w) |
| O(1) Espaço | O(n) | O(1) |
| DFS Recursivo | O(n) | O(h) |
| Iterativo Dummy | O(n) | O(1) |

## Implementação

A solução principal usa **BFS Level Order**.

```java
public Node connect(Node root) {
    if (root == null) return null;

    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        Node prev = null;

        for (int i = 0; i < size; i++) {
            Node curr = queue.poll();

            if (prev != null) {
                prev.next = curr;
            }
            prev = curr;

            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
    }

    return root;
}
```

## Casos de Teste

- `[1,2,3,4,5,6,7]` → Níveis: `[[1], [2,3], [4,5,6,7]]`
- `[]` → `[]`
- `[1]` → `[[1]]`
- `[1,2,3]` → `[[1], [2,3]]`
- `[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]` → 4 níveis