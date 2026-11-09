# 0124 - Binary Tree Maximum Path Sum

## Descrição

Um **caminho** em uma árvore binária é uma sequência de nós onde cada par de nós adjacentes na sequência tem uma aresta conectando-os. Um nó pode aparecer no caminho no máximo uma vez. O caminho **não precisa passar pela raiz**. A **soma do caminho** é a soma dos valores dos nós no caminho.
Dado o `root` de uma árvore binária, retorne a **soma máxima** de qualquer caminho não vazio.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3]
Output: 6
Explicação: O caminho ótimo é 2 -> 1 -> 3 com soma 6.
```

**Exemplo 2:**
```
Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explicação: O caminho ótimo é 15 -> 20 -> 7 com soma 42.
```

## Restrições

- O número de nós na árvore está no intervalo `[1, 3 * 10^4]`.
- `-1000 <= Node.val <= 1000`

## Abordagem

### Método 1: DFS Recursivo com Variável Global (Principal)
- Para cada nó, calcula `maxGain`: max soma que pode ser estendida para o pai (apenas um lado)
- Calcula `currentPathSum`: soma do caminho que passa pelo nó (pode usar ambos filhos)
- Atualiza máximo global
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: DFS Recursivo Retornando Par
- Retorna explicitamente `(maxSingle, maxTop)` sem variável global
- `maxSingle`: max path sum que pode ser estendido para cima
- `maxTop`: max path sum na subárvore (pode não ser estendido)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Iterativo Post-order com Dois Stacks
- Usa dois stacks para simular post-order traversal
- Mantém mapa de `maxSingle` por nó
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo Global | O(n) | O(h) |
| DFS Par | O(n) | O(h) |
| Iterativo Post-order | O(n) | O(h) |

## Implementação

A solução principal usa **DFS Recursivo com Variável Global**.

```java
private int maxSum;

public int maxPathSum(TreeNode root) {
    maxSum = Integer.MIN_VALUE;
    maxGain(root);
    return maxSum;
}

private int maxGain(TreeNode node) {
    if (node == null) return 0;

    int leftGain = Math.max(maxGain(node.left), 0);
    int rightGain = Math.max(maxGain(node.right), 0);

    int currentPathSum = node.val + leftGain + rightGain;
    maxSum = Math.max(maxSum, currentPathSum);

    return node.val + Math.max(leftGain, rightGain);
}
```

## Casos de Teste

- `[1,2,3]` → `6` (2->1->3)
- `[-10,9,20,null,null,15,7]` → `42` (15->20->7)
- `[1]` → `1`
- `[-3,-2,-1]` → `-1` (single node -1)
- `[-2,-1,null,-3]` → `-1` (single node -1)
- `[2,-1,-2]` → `2` (root only)
- `[5,4,8,11,null,13,4,7,2,null,null,null,1]` → `48`
- `[1,2,3,4,5,6,7]` → `18` (4->2->1->3->7 or 6->3->7)
- `[-10,9,20]` → `20` (single node 20, path 9->-10->20 = 19)