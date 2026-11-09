# 0173 - Binary Search Tree Iterator

## Descrição

Implemente a classe `BSTIterator` que representa um iterador sobre a travessia **in-order** de uma árvore de busca binária (BST):
- `BSTIterator(TreeNode root)` Inicializa o iterador com o root da BST.
- `boolean hasNext()` Retorna `true` se existe um número à direita do ponteiro atual.
- `int next()` Move o ponteiro para a direita e retorna o número.
O ponteiro inicial está antes do menor elemento.
Assume-se que `next()` será chamado apenas quando `hasNext()` for `true`.

## Exemplos

**Exemplo 1:**
```
Input: ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
       [[[7,3,15,null,null,9,20]], [], [], [], [], [], [], [], [], []]
Output: [null, 3, 7, true, 9, true, 15, true, 20, false]
```

## Restrições

- O número de nós na árvore está no intervalo `[1, 10^5]`.
- `0 <= Node.val <= 10^6`
- No máximo `10^5` chamadas para `hasNext` e `next`.
- `next()` será chamado apenas quando `hasNext()` for `true`.

## Abordagem

### Método 1: Stack Controlado (Principal)
- Mantém stack com caminho para o próximo menor elemento
- Construtor: empurra todos os left children
- `next()`: pop, empurra left children do right child
- **Tempo:** O(1) amortizado, **Espaço:** O(h)

### Método 2: Lista Pré-computada
- Faz in-order traversal completo no construtor
- Guarda em ArrayList
- **Tempo construtor:** O(n), **Espaço:** O(n)
- `next()` e `hasNext()` em O(1) real

## Complexidade

| Método | Construtor | next() | hasNext() | Espaço |
|--------|------------|--------|-----------|--------|
| Stack Controlado | O(h) | O(1) amort. | O(1) | O(h) |
| Lista Pré-computada | O(n) | O(1) | O(1) | O(n) |

## Implementação

A solução principal usa **Stack Controlado**.

```java
static class BSTIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        pushAllLeft(root);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        TreeNode node = stack.pop();
        pushAllLeft(node.right);
        return node.val;
    }

    private void pushAllLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}
```

## Casos de Teste

- `[7,3,15,null,null,9,20]` → `[3,7,9,15,20]`
- `[]` → `[]` (hasNext = false)
- `[1]` → `[1]`
- `[4,3,null,2,null,1]` → `[1,2,3,4]` (left skewed)
- `[1,null,2,null,3,null,4]` → `[1,2,3,4]` (right skewed)
- `[4,2,6,1,3,5,7]` → `[1,2,3,4,5,6,7]` (complete BST)