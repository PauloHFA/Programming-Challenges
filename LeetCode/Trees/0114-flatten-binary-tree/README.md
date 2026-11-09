# 0114 - Flatten Binary Tree to Linked List

## Descrição

Dado o `root` de uma árvore binária, achate a árvore em uma "lista encadeada":
A "lista encadeada" deve usar a mesma classe `TreeNode` onde o ponteiro `right` aponta para o próximo nó na lista e o ponteiro `left` é sempre `null`.
A "lista encadeada" deve estar na mesma ordem que uma travessia pre-order da árvore binária.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]
Explicação: A árvore achatada em pre-order: 1 -> 2 -> 3 -> 4 -> 5 -> 6
```

**Exemplo 2:**
```
Input: root = []
Output: []
```

**Exemplo 3:**
```
Input: root = [0]
Output: [0]
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 2000]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: Recursivo - Post-order Reverso (Principal)
- Processa right, depois left, depois root
- Mantém ponteiro para último nó visitado (usando array para simular referência)
- Reconecta: right = prev, left = null, prev = root
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo com Stack
- Usa stack para simular pre-order reverso (root, right, left)
- Mantém ponteiro prev para conectar nós
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Morris Traversal (O(1) espaço)
- Para cada nó com left child, encontra predecessor (mais à direita da subárvore esquerda)
- Conecta predecessor.right = curr.right
- Move curr.left para curr.right, curr.left = null
- **Tempo:** O(n), **Espaço:** O(1)

### Método 4: Recursivo com retorno do último nó
- Retorna o último nó da lista achatada
- Evita variável global
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo Post-order | O(n) | O(h) |
| Iterativo Stack | O(n) | O(h) |
| Morris Traversal | O(n) | O(1) |
| Recursivo Retorno | O(n) | O(h) |

## Implementação

A solução principal usa **Recursivo Post-order Reverso**.

```java
public void flatten(TreeNode root) {
    flattenHelper(root, new TreeNode[1]);
}

private void flattenHelper(TreeNode node, TreeNode[] prev) {
    if (node == null) return;
    
    flattenHelper(node.right, prev);
    flattenHelper(node.left, prev);
    
    node.right = prev[0];
    node.left = null;
    prev[0] = node;
}
```

## Casos de Teste

- `[1,2,5,3,4,null,6]` → `[1,2,3,4,5,6]` (pre-order)
- `[]` → `[]`
- `[0]` → `[0]`
- `[1,2,null,3,null,4]` → `[1,2,3,4]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[1,2,3,4]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[1,2,4,5,3,6,7]` (complete tree)