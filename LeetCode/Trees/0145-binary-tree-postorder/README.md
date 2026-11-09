# 0145 - Binary Tree Postorder Traversal

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia **postorder** dos valores dos seus nós.
**Postorder:** Left -> Right -> Root

## Exemplos

**Exemplo 1:**
```
Input: root = [1,null,2,3]
Output: [3,2,1]
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
- Visita left, depois right, depois root
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo com Stack (Reverse Preorder)
- Preorder é Root->Left->Right. Reverse Preorder é Root->Right->Left.
- Reverse de Reverse Preorder = Left->Right->Root = Postorder.
- Adiciona no início da lista (index 0)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Iterativo com Ponteiro Anterior
- Rastreia último nó visitado para saber quando voltar
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: Morris Traversal (O(1) espaço)
- Usa threads reversas para processar caminho inverso
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo | O(n) | O(h) |
| Iterativo Reverse | O(n) | O(h) |
| Iterativo PrevPtr | O(n) | O(h) |
| Morris | O(n) | O(1) |

## Implementação

A solução principal usa **Recursivo**.

```java
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    postorder(root, result);
    return result;
}

private void postorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    postorder(node.left, result);
    postorder(node.right, result);
    result.add(node.val);
}
```

## Casos de Teste

- `[1,null,2,3]` → `[3,2,1]`
- `[]` → `[]`
- `[1]` → `[1]`
- `[1,2,null,3,null,4]` → `[4,3,2,1]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[4,3,2,1]` (right skewed)
- `[1,2,3,4,5,6,7]` → `[4,5,2,6,7,3,1]` (complete tree)