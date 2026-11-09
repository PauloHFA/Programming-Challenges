# 0110 - Balanced Binary Tree

## Descrição

Dado o `root` de uma árvore binária, determine se ela é balanceada em altura.
Uma árvore binária é balanceada em altura se para cada nó, a diferença de altura entre as subárvores esquerda e direita não é maior que 1.

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: true
```

**Exemplo 2:**
```
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false
```

**Exemplo 3:**
```
Input: root = []
Output: true
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 5000]`.
- `-10^4 <= Node.val <= 10^4`

## Abordagem

### Método 1: DFS Recursivo com Early Exit (Principal)
- Retorna -1 se subárvore não for balanceada, caso contrário retorna altura
- Para assim que encontra desbalanceamento (short-circuit)
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: DFS Recursivo com Pair
- Retorna par contendo altura e se é balanceado
- Mais explícito que o método 1
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Iterativo com Stack (Post-order)
- Usa stack para simular pós-ordem, calcula alturas bottom-up
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: BFS Level Order
- Verifica balanceamento em cada nó visitado
- Calcula altura de cada subárvore separadamente (ineficiente)
- **Tempo:** O(n²) pior caso, **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Early Exit | O(n) | O(h) |
| Pair | O(n) | O(h) |
| Iterativo | O(n) | O(h) |
| BFS | O(n²) | O(w) |

## Implementação

A solução principal usa **DFS Recursivo com Early Exit**.

```java
public boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}

private int checkHeight(TreeNode node) {
    if (node == null) return 0;

    int leftHeight = checkHeight(node.left);
    if (leftHeight == -1) return -1;

    int rightHeight = checkHeight(node.right);
    if (rightHeight == -1) return -1;

    if (Math.abs(leftHeight - rightHeight) > 1) return -1;

    return Math.max(leftHeight, rightHeight) + 1;
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `true` (balanced)
- `[1,2,2,3,3,null,null,4,4]` → `false` (not balanced)
- `[]` → `true` (empty)
- `[1]` → `true` (single node)
- `[1,2,null,3,null,4]` → `false` (left skewed)
- `[1,null,2,null,3,null,4]` → `false` (right skewed)
- `[1,2,3,4,5,6,7]` → `true` (complete)
- Perfect tree height 3 → `true`
- `[1,2,3,4,null,5,6,7,null,null,null,null,8]` → `false` (not balanced - left subtree of node 2 has height 3, right has height 1)
- `[1,2]` → `true`
- `[1,null,2]` → `true`
- `[-1,-2,-3,-4,-5]` → `true`
- `[1,2,3,4,5,null,null,6,7]` → `false` (unbalanced at root)
- Perfect tree height 5 (31 nodes) → `true`