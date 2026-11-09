# 0144 - Binary Tree Preorder Traversal

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia **preorder** dos valores dos seus nós.
**Preorder:** Root -> Left -> Right

## Exemplos

**Exemplo 1:**
```
Input: root = [1,null,2,3]
Output: [1,2,3]
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

- O número de nós na árvore está no intervalo `[0, 100]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: Recursivo (Principal)
- Visita root, depois left, depois right
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo com Stack
- Push right first, then left (so left is processed first)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Morris Traversal (O(1) espaço)
- Usa threads temporárias para evitar stack
- Visita root antes de ir para left
- **Tempo:** O(n), **Espaço:** O(1)

### Método 4: Iterativo Explícito com Estado
- Simula chamada de função com stack de (nó, estado)
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo | O(n) | O(h) |
| Iterativo Stack | O(n) | O(h) |
| Morris | O(n) | O(1) |
| Iterativo Explícito | O(n) | O(h) |

## Implementação

A solução principal usa **Recursivo**.

```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorder(root, result);
    return result;
}

private void preorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    result.add(node.val);
    preorder(node.left, result);
    preorder(node.right, result);
}
```

## Casos de Teste

- `[1,null,2,3]` → `[1,2,3]`
- `[]` → `[]`
- `[1]` → `[1]`
- `[1,2,null,3,null,4]` → `[1,2,3,4]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[1,2,3,4]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[1,2,4,5,3,6,7]` (complete tree)