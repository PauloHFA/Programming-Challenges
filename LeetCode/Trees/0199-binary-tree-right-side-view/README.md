# 0199 - Binary Tree Right Side View

## Descrição

Dado o `root` de uma árvore binária, imagine-se parado no lado direito dela.
Retorne os valores dos nós que você pode ver ordenados de cima para baixo.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]
Explicação:
   1            <---
  /   \
 2     3         <---
  \     \
   5     4       <---
```

**Exemplo 2:**
```
Input: root = [1,null,3]
Output: [1,3]
```

**Exemplo 3:**
```
Input: root = []
Output: []
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 100]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: BFS Level Order (Principal)
- Processa nível a nível, pega o último elemento de cada nível
- **Tempo:** O(n), **Espaço:** O(w)

### Método 2: DFS Recursivo (Right First)
- Visita right child primeiro
- Adiciona nó quando atinge nova profundidade (primeira vez = rightmost)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: DFS Iterativo com Stack
- Similar ao recursivo mas com stack explícito
- Push left first so right is processed first (LIFO)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: BFS Right First
- Adiciona right child primeiro na queue
- Pega primeiro elemento de cada nível
- **Tempo:** O(n), **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| BFS Level Order | O(n) | O(w) |
| DFS Right First | O(n) | O(h) |
| DFS Iterativo | O(n) | O(h) |
| BFS Right First | O(n) | O(w) |

## Implementação

A solução principal usa **BFS Level Order**.

```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();

            if (i == size - 1) {
                result.add(node.val);
            }

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    return result;
}
```

## Casos de Teste

- `[1,2,3,null,5,null,4]` → `[1,3,4]`
- `[1,null,3]` → `[1,3]`
- `[]` → `[]`
- `[1]` → `[1]`
- `[1,2,null,3,null,4]` → `[1,2,3,4]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[1,2,3,4]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[1,3,7]` (complete tree)