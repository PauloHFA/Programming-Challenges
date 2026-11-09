# 0111 - Minimum Depth of Binary Tree

## Descrição

Dado o `root` de uma árvore binária, encontre sua profundidade mínima.
A profundidade mínima é o número de nós ao longo do caminho mais curto desde o nó raiz até o nó folha mais próximo.
Nota: Um nó folha é um nó sem filhos.

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: 2
```

**Exemplo 2:**
```
Input: root = [2,null,3,null,4,null,5,null,6]
Output: 5
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 10^5]`.
- `-1000 <= Node.val <= 1000`

## Abordagem

### Método 1: DFS Recursivo (Principal)
- Caso base: nó null retorna 0
- Se é folha (sem filhos), retorna 1
- Se só tem filho esquerdo, deve ir por ele (não é folha)
- Se só tem filho direito, deve ir por ele
- Se tem ambos, pega o mínimo dos dois
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: BFS Iterativo (Level Order)
- Para assim que encontra a primeira folha
- Mais eficiente para árvores desbalanceadas (encontra folha cedo)
- **Tempo:** O(n) pior caso, **Espaço:** O(w) largura máxima

### Método 3: DFS Iterativo com Stack
- Usa stack para simular recursão, guarda (nó, profundidade)
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(n) | O(h) |
| BFS | O(n) | O(w) |
| DFS Iterativo | O(n) | O(h) |

## Implementação

A solução principal usa **DFS Recursivo**.

```java
public int minDepth(TreeNode root) {
    if (root == null) return 0;

    if (root.left == null && root.right == null) return 1;

    if (root.right == null) return 1 + minDepth(root.left);

    if (root.left == null) return 1 + minDepth(root.right);

    return 1 + Math.min(minDepth(root.left), minDepth(root.right));
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `2`
- `[2,null,3,null,4,null,5,null,6]` → `5`
- `[]` → `0`
- `[1]` → `1`
- `[1,2,null,3,null,4]` → `4` (left skewed)
- `[1,null,2,null,3,null,4]` → `4` (right skewed)
- `[1,2,3,4,5,6,7]` → `3` (complete)
- Perfect tree height 3 → `4`
- `[1,2,3,4,null,5,6,7,null,null,null,null,8]` → `3`
- `[1,2]` → `2`
- `[1,null,2]` → `2`
- `[-1,-2,-3,-4,-5]` → `2` (path: -1 -> -3)
- `[1,2,null,null,3]` → `3` (root -> left -> right)
- `[1,null,2,3]` → `3` (root -> right -> left)