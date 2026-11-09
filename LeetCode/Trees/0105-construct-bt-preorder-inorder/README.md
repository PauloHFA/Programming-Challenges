# 0105 - Construct Binary Tree from Preorder and Inorder Traversal

## Descrição

Dado dois arrays de inteiros `preorder` e `inorder` onde `preorder` é a travessia pré-ordem de uma árvore binária e `inorder` é a travessia em ordem da mesma árvore, construa e retorne a árvore binária.

## Exemplos

**Exemplo 1:**
```
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
```

**Exemplo 2:**
```
Input: preorder = [-1], inorder = [-1]
Output: [-1]
```

## Restrições

- `1 <= preorder.length <= 3000`
- `inorder.length == preorder.length`
- `-3000 <= preorder[i], inorder[i] <= 3000`
- `preorder` e `inorder` consistem de valores únicos.
- Cada valor de `inorder` também aparece em `preorder`.
- `preorder` é garantido ser a travessia pré-ordem da árvore.
- `inorder` é garantido ser a travessia em ordem da árvore.

## Abordagem

### Método 1: Recursivo com HashMap (Principal)
- **Preorder:** root, left, right
- **Inorder:** left, root, right
- O primeiro elemento do preorder é a raiz
- Encontra raiz no inorder → elementos à esquerda = subárvore esquerda, à direita = subárvore direita
- Usa HashMap para busca O(1) do índice no inorder
- **Tempo:** O(n), **Espaço:** O(n) HashMap + O(h) stack

### Método 2: Iterativo com Stack
- Simula a recursão usando stack explícita
- Mantém ponteiro no inorder para saber quando voltar
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Recursivo sem HashMap (Busca Linear)
- Mais simples mas O(n²) no pior caso (árvore degenerada)
- **Tempo:** O(n²), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo + HashMap | O(n) | O(n) |
| Iterativo + Stack | O(n) | O(n) |
| Recursivo sem Map | O(n²) | O(h) |

## Implementação

A solução principal usa **Recursivo com HashMap**.

```java
public TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inorderIndex = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        inorderIndex.put(inorder[i], i);
    }
    return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderIndex);
}

private TreeNode build(int[] preorder, int preStart, int preEnd,
                       int[] inorder, int inStart, int inEnd,
                       Map<Integer, Integer> inorderIndex) {
    if (preStart > preEnd || inStart > inEnd) return null;

    int rootVal = preorder[preStart];
    TreeNode root = new TreeNode(rootVal);

    int inRootIndex = inorderIndex.get(rootVal);
    int leftSize = inRootIndex - inStart;

    root.left = build(preorder, preStart + 1, preStart + leftSize,
                      inorder, inStart, inRootIndex - 1, inorderIndex);
    root.right = build(preorder, preStart + leftSize + 1, preEnd,
                       inorder, inRootIndex + 1, inEnd, inorderIndex);

    return root;
}
```

## Casos de Teste

- `[3,9,20,15,7]`, `[9,3,15,20,7]` → árvore com raiz 3, esquerda 9, direita 20(15,7)
- `[-1]`, `[-1]` → nó único -1
- `[1,2,4,5,3]`, `[4,2,5,1,3]` → árvore completa
- `[1,2,3]`, `[1,2,3]` → right skewed
- `[1,2,3]`, `[3,2,1]` → left skewed
- `[1,2,4,8,9,5,3,6,7]`, `[8,4,9,2,5,1,6,3,7]` → árvore maior
- `[-1,-2,-4,-5,-3]`, `[-4,-2,-5,-1,-3]` → valores negativos
- `[1,2]`, `[2,1]` → dois nós esquerda
- `[1,2]`, `[1,2]` → dois nós direita
- `[2,1,3]`, `[1,2,3]` → três nós balanceados