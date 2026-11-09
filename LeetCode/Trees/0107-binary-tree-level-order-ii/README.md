# 0107 - Binary Tree Level Order Traversal II

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia em ordem de nível dos valores de seus nós de baixo para cima (bottom-up). (ou seja, da esquerda para a direita, nível por nível, da folha para a raiz).

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[15,7],[9,20],[3]]
```

**Exemplo 2:**
```
Input: root = [1]
Output: [[1]]
```

**Exemplo 3:**
```
Input: root = []
Output: []
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 2000]`.
- `-1000 <= Node.val <= 1000`

## Abordagem

### Método 1: BFS com Queue + Reverse (Principal)
- Faz BFS normal (top-down) e inverte o resultado no final
- **Tempo:** O(n), **Espaço:** O(w) queue + O(n) resultado

### Método 2: BFS com Deque (adiciona no início)
- Usa Deque para adicionar cada nível no início, evitando reverse final
- **Tempo:** O(n), **Espaço:** O(w) queue + O(n) resultado

### Método 3: DFS Recursivo com nível
- Passa nível atual, garante que lista do nível existe, adiciona valor
- No final, inverte o resultado
- **Tempo:** O(n), **Espaço:** O(h) stack + O(n) resultado

### Método 4: Duas Queues
- Mantém queue atual e próxima, adiciona nível no início do resultado
- **Tempo:** O(n), **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Queue + Reverse | O(n) | O(w) |
| Deque | O(n) | O(w) |
| DFS Recursivo | O(n) | O(h) |
| Duas Queues | O(n) | O(w) |

## Implementação

A solução principal usa **BFS com Queue + Reverse**.

```java
public List<List<Integer>> levelOrderBottom(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> level = new ArrayList<>(levelSize);

        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        result.add(level);
    }

    Collections.reverse(result);
    return result;
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `[[15,7],[9,20],[3]]`
- `[1]` → `[[1]]`
- `[]` → `[]`
- `[1,2,null,3,null,4]` → `[[4],[3],[2],[1]]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[[4],[3],[2],[1]]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[[4,5,6,7],[2,3],[1]]` (complete)
- Perfect tree height 3 → 4 levels bottom-up
- `[1,2,3,4,null,5,6,7,null,null,null,null,8]` → `[[7,8],[4,5,6],[2,3],[1]]`
- `[1,2]` → `[[2],[1]]`
- `[1,null,2]` → `[[2],[1]]`
- `[-1,-2,-3,-4,-5]` → `[[-4,-5],[-2,-3],[-1]]`