# 0117 - Populating Next Right Pointers in Each Node II

## Descrição

Dado uma **árvore binária** (NÃO necessariamente perfeita), preencha cada ponteiro `next` para apontar para o próximo nó à direita. Se não houver próximo nó à direita, o ponteiro `next` deve ser `NULL`. Inicialmente, todos os ponteiros `next` são `NULL`.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3,4,5,null,7]
Output: [1,#,2,3,#,4,5,7,#]
Explicação: 
Nível 0: 1 -> NULL
Nível 1: 2 -> 3 -> NULL
Nível 2: 4 -> 5 -> 7 -> NULL
```

**Exemplo 2:**
```
Input: root = []
Output: []
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 6000]`.
- `-100 <= Node.val <= 100`
- A árvore **NÃO** é necessariamente perfeita

## Abordagem

### Método 1: BFS Level Order (Principal)
- Usa queue para processar nível a nível
- Funciona para qualquer árvore binária
- **Tempo:** O(n), **Espaço:** O(w)

### Método 2: O(1) Espaço - Dummy Head
- Usa dummy node para construir lista do próximo nível
- Não assume árvore perfeita
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: DFS Recursivo com Mapa
- Usa HashMap para guardar último nó de cada nível
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: Iterativo Dois Ponteiros
- Similar ao método 2 mas mais explícito
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| BFS Level Order | O(n) | O(w) |
| O(1) Espaço Dummy | O(n) | O(1) |
| DFS com Mapa | O(n) | O(h) |
| Dois Ponteiros | O(n) | O(1) |

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

- `[1,2,3,4,5,null,7]` → `[[1], [2,3], [4,5,7]]`
- `[]` → `[]`
- `[1]` → `[[1]]`
- `[1,2,null,3,null,4]` → `[[1], [2], [3], [4]]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[[1], [2], [3], [4]]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[[1], [2,3], [4,5,6,7]]` (perfect tree)
- `[1,2,3,null,5,null,7]` → `[[1], [2,3], [5,7]]` (with gaps)