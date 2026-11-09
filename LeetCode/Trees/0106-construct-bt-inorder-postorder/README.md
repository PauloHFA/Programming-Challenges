# 0106 - Construct Binary Tree from Inorder and Postorder Traversal

## Descrição

Dado dois arrays de inteiros `inorder` e `postorder` onde `inorder` é a travessia em ordem de uma árvore binária e `postorder` é a travessia pós-ordem da mesma árvore, construa e retorne a árvore binária.

## Exemplos

**Exemplo 1:**
```
Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
Output: [3,9,20,null,null,15,7]
```

**Exemplo 2:**
```
Input: inorder = [-1], postorder = [-1]
Output: [-1]
```

## Restrições

- `1 <= inorder.length <= 3000`
- `postorder.length == inorder.length`
- `-3000 <= inorder[i], postorder[i] <= 3000`
- `inorder` e `postorder` consistem de valores únicos.
- Cada valor de `inorder` também aparece em `postorder`.
- `inorder` é garantido ser a travessia em ordem da árvore.
- `postorder` é garantido ser a travessia pós-ordem da árvore.

## Abordagem

### Método 1: Recursivo com HashMap (Principal)
- **Inorder:** left, root, right
- **Postorder:** left, right, root
- O último elemento do postorder é a raiz
- Encontra raiz no inorder → elementos à esquerda = subárvore esquerda, à direita = subárvore direita
- Usa HashMap para busca O(1) do índice no inorder
- **Tempo:** O(n), **Espaço:** O(n) HashMap + O(h) stack

### Método 2: Iterativo com Stack
- Similar ao método iterativo do preorder/inorder mas processa postorder de trás para frente
- Mantém ponteiro no inorder (do final para início) para saber quando voltar
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
public TreeNode buildTree(int[] inorder, int[] postorder) {
    Map<Integer, Integer> inorderIndex = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        inorderIndex.put(inorder[i], i);
    }
    return build(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, inorderIndex);
}

private TreeNode build(int[] inorder, int inStart, int inEnd,
                       int[] postorder, int postStart, int postEnd,
                       Map<Integer, Integer> inorderIndex) {
    if (inStart > inEnd || postStart > postEnd) return null;

    int rootVal = postorder[postEnd];
    TreeNode root = new TreeNode(rootVal);

    int inRootIndex = inorderIndex.get(rootVal);
    int leftSize = inRootIndex - inStart;

    root.left = build(inorder, inStart, inRootIndex - 1,
                      postorder, postStart, postStart + leftSize - 1, inorderIndex);
    root.right = build(inorder, inRootIndex + 1, inEnd,
                       postorder, postStart + leftSize, postEnd - 1, inorderIndex);

    return root;
}
```

## Casos de Teste

- `[9,3,15,20,7]`, `[9,15,7,20,3]` → árvore com raiz 3, esquerda 9, direita 20(15,7)
- `[-1]`, `[-1]` → nó único -1
- `[4,2,5,1,3]`, `[4,5,2,3,1]` → árvore completa
- `[1,2,3]`, `[3,2,1]` → right skewed
- `[3,2,1]`, `[3,2,1]` → left skewed
- `[8,4,9,2,5,1,6,3,7]`, `[8,9,4,5,2,6,7,3,1]` → árvore maior
- `[-4,-2,-5,-1,-3]`, `[-4,-5,-2,-3,-1]` → valores negativos
- `[2,1]`, `[2,1]` → dois nós esquerda
- `[1,2]`, `[2,1]` → dois nós direita
- `[1,2,3]`, `[1,3,2]` → três nós balanceados