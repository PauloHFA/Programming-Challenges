# 0098 - Validate Binary Search Tree

## Descrição

Dado o `root` de uma árvore binária, determine se é uma árvore de busca binária (BST) válida.

Uma BST válida é definida como:
- A subárvore esquerda de um nó contém apenas nós com chaves **menores** que a chave do nó.
- A subárvore direita de um nó contém apenas nós com chaves **maiores** que a chave do nó.
- Ambas as subárvores esquerda e direita também devem ser BSTs.

## Exemplos

**Exemplo 1:**
```
Input: root = [2,1,3]
Output: true
```

**Exemplo 2:**
```
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: O nó raiz tem valor 5, mas seu filho direito tem valor 4.
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 10^4]`.
- `-2^31 <= Node.val <= 2^31 - 1`

## Abordagem

### Método 1: Recursão com Limites (Principal)
- Passa intervalo válido `(min, max)` para cada nó recursivamente
- Raiz: `(-∞, +∞)`
- Filho esquerdo: `(min, node.val)`
- Filho direito: `(node.val, max)`
- Usa `Long.MIN_VALUE` e `Long.MAX_VALUE` para lidar com `Integer.MIN/MAX_VALUE`
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Inorder Traversal Iterativo
- BST válida → inorder produz sequência estritamente crescente
- Usa stack explícita, mantém `prev` valor
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Inorder Traversal Recursivo
- Mesma lógica mas recursivo
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: BFS com Limites
- Usa queue com `(node, min, max)`
- Processa nível por nível
- **Tempo:** O(n), **Espaço:** O(w) onde w = largura máxima

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Limites Recursivo | O(n) | O(h) |
| Inorder Iterativo | O(n) | O(h) |
| Inorder Recursivo | O(n) | O(h) |
| BFS Limites | O(n) | O(w) |

## Implementação

A solução principal usa **Recursão com Limites**.

```java
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    
    if (node.val <= min || node.val >= max) return false;
    
    return validate(node.left, min, node.val) && 
           validate(node.right, node.val, max);
}
```

## Casos de Teste

- `[2,1,3]` → `true`
- `[5,1,4,null,null,3,6]` → `false`
- `[1]` → `true`
- `[]` → `true`
- `[3,2,null,1]` → `true` (left skewed)
- `[1,null,2,null,3]` → `true` (right skewed)
- `[2,2,3]` → `false` (left child == parent)
- `[2,1,2]` → `false` (right child == parent)
- `[10,5,15,null,null,6,20]` → `false` (6 < 10 but in right subtree)
- `[0,-1,1]` → `true` (negative values)
- `[Integer.MAX_VALUE]` → `true`
- `[Integer.MIN_VALUE]` → `true`
- `[5,4,6,null,null,3,7]` → `false` (3 < 5 but in right subtree)
- Perfect BST 15 nodes → `true`