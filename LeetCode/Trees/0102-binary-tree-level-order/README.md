# 0102 - Binary Tree Level Order Traversal

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia em ordem de nível (level order) dos valores de seus nós. (ou seja, da esquerda para a direita, nível por nível).

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
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

### Método 1: BFS com Queue (Principal)
- Usa queue para processar nível por nível
- Em cada iteração, processa todos os nós do nível atual (tamanho conhecido)
- Adiciona filhos à queue para próximo nível
- **Tempo:** O(n), **Espaço:** O(w) onde w = largura máxima

### Método 2: BFS com Delimitador (null)
- Usa `null` como marcador de fim de nível
- Quando encontra `null`, finaliza nível atual
- **Tempo:** O(n), **Espaço:** O(w)

### Método 3: DFS Recursivo com Nível
- Passa nível atual como parâmetro
- Adiciona nó ao nível correspondente no resultado
- **Tempo:** O(n), **Espaço:** O(h) stack + O(w) resultado

### Método 4: Duas Queues
- Mantém queue atual e próxima
- Troca quando atual fica vazia
- **Tempo:** O(n), **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Queue (BFS) | O(n) | O(w) |
| Delimitador | O(n) | O(w) |
| DFS Recursivo | O(n) | O(h) |
| Duas Queues | O(n) | O(w) |

## Implementação

A solução principal usa **BFS com Queue**.

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> level = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(level);
    }
    
    return result;
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `[[3],[9,20],[15,7]]`
- `[1]` → `[[1]]`
- `[]` → `[]`
- `[1,2,null,3,null,4]` → `[[1],[2],[3],[4]]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[[1],[2],[3],[4]]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[[1],[2,3],[4,5,6,7]]` (complete)
- Perfect tree height 3 → 4 levels
- `[1,2]` → `[[1],[2]]`
- `[1,null,2]` → `[[1],[2]]`
- `[-1,-2,-3,-4,-5]` → `[[-1],[-2,-3],[-4,-5]]`