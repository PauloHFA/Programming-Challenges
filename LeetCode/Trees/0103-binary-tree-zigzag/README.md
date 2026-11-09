# 0103 - Binary Tree Zigzag Level Order Traversal

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia em ordem de nível zigzag dos valores de seus nós. (ou seja, da esquerda para a direita, depois da direita para a esquerda no próximo nível e alterna entre eles).

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
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

### Método 1: BFS com Queue + Flag (Principal)
- Usa queue padrão e flag booleana `leftToRight`
- Se `leftToRight`: adiciona no final da lista do nível
- Se `!leftToRight`: adiciona no início (index 0) para inverter
- Alterna flag a cada nível
- **Tempo:** O(n), **Espaço:** O(w)

### Método 2: BFS com Deque
- Usa Deque (double-ended queue) para adicionar no início/fim O(1)
- Quando `leftToRight`: pollFirst, offerLast filhos
- Quando `!leftToRight`: pollLast, offerFirst filhos (ordem invertida)
- **Tempo:** O(n), **Espaço:** O(w)

### Método 3: Duas Stacks
- `currentLevel` e `nextLevel` stacks
- Quando `leftToRight`: push left depois right (processa right primeiro)
- Quando `!leftToRight`: push right depois left (processa left primeiro)
- Troca stacks a cada nível
- **Tempo:** O(n), **Espaço:** O(w)

### Método 4: DFS Recursivo
- Passa nível atual como parâmetro
- Nível par: adiciona no final (left-to-right)
- Nível ímpar: adiciona no início (right-to-left)
- **Tempo:** O(n), **Espaço:** O(h) stack + O(w) resultado

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Queue + Flag | O(n) | O(w) |
| Deque | O(n) | O(w) |
| Duas Stacks | O(n) | O(w) |
| DFS Recursivo | O(n) | O(h) |

## Implementação

A solução principal usa **BFS com Queue + Flag**.

```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    boolean leftToRight = true;

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> level = new ArrayList<>(levelSize);

        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();

            if (leftToRight) {
                level.add(node.val);
            } else {
                level.add(0, node.val); // Adiciona no início para inverter
            }

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        result.add(level);
        leftToRight = !leftToRight;
    }

    return result;
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `[[3],[20,9],[15,7]]`
- `[1]` → `[[1]]`
- `[]` → `[]`
- `[1,2,null,3,null,4]` → `[[1],[2],[3],[4]]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[[1],[2],[3],[4]]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[[1],[3,2],[4,5,6,7]]` (complete)
- Perfect tree height 3 → 4 levels alternating
- `[1,2,3,4,null,5,6,7,null,null,null,null,8]` → `[[1],[3,2],[4,5,6],[8,7]]`
- `[1,2]` → `[[1],[2]]`
- `[1,null,2]` → `[[1],[2]]`
- `[-1,-2,-3,-4,-5]` → `[[-1],[-3,-2],[-4,-5]]`
- `[1,2,3,4,5,null,7]` → `[[1],[3,2],[4,5,7]]`