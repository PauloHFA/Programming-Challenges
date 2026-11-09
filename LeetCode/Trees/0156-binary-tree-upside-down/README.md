# 0156 - Binary Tree Upside Down

## Descrição

Dado o `root` de uma árvore binária, vire a árvore de cabeça para baixo e retorne o novo root.
A árvore original é transformada de forma que:
- O filho esquerdo original se torna o novo root
- O root original se torna o filho direito
- O filho direito original se torna o filho esquerdo
Isso é feito nível por nível.
Assume-se que todo nó direito tem um irmão esquerdo (árvore completa à esquerda).

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3,4,5]
Output: [4,5,2,null,null,3,1]
Explicação:
Original:     1           Virada:     4
            /   \                   /   \
           2     3                 5     2
          / \                           / \
         4   5                         3   1
```

**Exemplo 2:**
```
Input: root = []
Output: []
```

**Exemplo 3:**
```
Input: root = [1]
Output: [1]
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 10]`.
- `1 <= Node.val <= 10`
- Todo nó direito tem um irmão esquerdo (árvore completa à esquerda)

## Abordagem

### Método 1: Recursivo (Principal)
- Processa recursivamente o filho esquerdo até chegar no novo root (folha mais à esquerda)
- Reconfigura: left.left = right, left.right = root, root.left = root.right = null
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo
- Usa três ponteiros (curr, prev, tempRight) para reconfigurar iterativamente
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Recursivo com Par
- Retorna tanto o novo root quanto o nó mais à direita
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo | O(n) | O(h) |
| Iterativo | O(n) | O(1) |
| Recursivo Par | O(n) | O(h) |

## Implementação

A solução principal usa **Recursivo**.

```java
public TreeNode upsideDownBinaryTree(TreeNode root) {
    if (root == null || root.left == null) return root;

    TreeNode newRoot = upsideDownBinaryTree(root.left);

    root.left.left = root.right;
    root.left.right = root;
    root.left = null;
    root.right = null;

    return newRoot;
}
```

## Casos de Teste

- `[1,2,3,4,5]` → `[4,5,2,null,null,3,1]`
- `[]` → `[]`
- `[1]` → `[1]`
- `[1,2,3]` → `[2,3,1]`
- `[1,2,null,3,null,4]` → `[4,null,3,null,2,null,1]` (left skewed)