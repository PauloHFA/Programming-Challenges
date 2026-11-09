# 0101 - Symmetric Tree

## Descrição

Dado o `root` de uma árvore binária, verifique se ela é um espelho de si mesma (ou seja, simétrica em torno de seu centro).

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,2,3,4,4,3]
Output: true
```

**Exemplo 2:**
```
Input: root = [1,2,2,null,3,null,3]
Output: false
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 1000]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: Recursivo (Principal)
- Compara subárvore esquerda com subárvore direita como espelhos
- `isMirror(left, right)`:
  - Ambos null = true
  - Um null = false
  - Valores diferentes = false
  - Recursa: `left.left` com `right.right` E `left.right` com `right.left`
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo com Queue (BFS)
- Usa queue com pares de nós que devem ser espelhos
- Processa nível por nível
- **Tempo:** O(n), **Espaço:** O(w) largura máxima

### Método 3: Iterativo com Stack (DFS)
- Usa stack com pares de nós
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: Serialização Simétrica
- Serializa esquerda em preorder normal (root, left, right)
- Serializa direita em preorder espelhado (root, right, left)
- Compara strings
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo | O(n) | O(h) |
| Queue (BFS) | O(n) | O(w) |
| Stack (DFS) | O(n) | O(h) |
| Serialização | O(n) | O(n) |

## Implementação

A solução principal usa **Recursivo**.

```java
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return isMirror(root.left, root.right);
}

private boolean isMirror(TreeNode left, TreeNode right) {
    if (left == null && right == null) return true;
    if (left == null || right == null) return false;
    if (left.val != right.val) return false;
    
    return isMirror(left.left, right.right) && isMirror(left.right, right.left);
}
```

## Casos de Teste

- `[1,2,2,3,4,4,3]` → `true`
- `[1,2,2,null,3,null,3]` → `false`
- `[1]` → `true`
- `[]` → `true`
- `[1,2,2]` → `true`
- `[1,2,3]` → `false`
- `[1,2]` → `false`
- `[1,null,2]` → `false`
- `[1,2,2,3,4,4,3,5,6,6,5,7,8,8,7]` → `true`
- `[1,2,2,3,4,4,3,5,6,7,5,7,8,8,7]` → `false`
- `[1,1,1,1,1,1,1]` → `true`
- `[-1,-2,-2,-3,-4,-4,-3]` → `true`
- `[5,4,4,3,3,3,3,2,2,2,2,2,2,2,2]` → `true`