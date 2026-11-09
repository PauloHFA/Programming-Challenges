# 0112 - Path Sum

## Descrição

Dado o `root` de uma árvore binária e um inteiro `targetSum`, retorne `true` se a árvore tem um caminho da raiz até folha tal que somando todos os valores ao longo do caminho seja igual a `targetSum`.
Um nó folha é um nó sem filhos.

## Exemplos

**Exemplo 1:**
```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
Output: true
Explicação: O caminho 5 -> 4 -> 11 -> 2 soma 22.
```

**Exemplo 2:**
```
Input: root = [1,2,3], targetSum = 5
Output: false
```

**Exemplo 3:**
```
Input: root = [], targetSum = 0
Output: false
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 5000]`.
- `-1000 <= Node.val <= 1000`
- `-1000 <= targetSum <= 1000`

## Abordagem

### Método 1: DFS Recursivo (Principal)
- Subtrai valor do nó do targetSum a cada passo
- Se chega em folha e targetSum == valor do nó, encontrou caminho
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: DFS Recursivo com Soma Acumulada
- Passa soma atual como parâmetro
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: BFS Iterativo com Queue
- Usa queue para guardar (nó, soma_até_agora)
- **Tempo:** O(n), **Espaço:** O(w)

### Método 4: DFS Iterativo com Stack
- Usa stack para simular recursão
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(n) | O(h) |
| DFS Acumulado | O(n) | O(h) |
| BFS | O(n) | O(w) |
| DFS Iterativo | O(n) | O(h) |

## Implementação

A solução principal usa **DFS Recursivo**.

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;

    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }

    int remaining = targetSum - root.val;
    return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
}
```

## Casos de Teste

- `[5,4,8,11,null,13,4,7,2,null,null,null,1]`, target=22 → `true` (5->4->11->2)
- `[1,2,3]`, target=5 → `false`
- `[]`, target=0 → `false`
- `[1]`, target=1 → `true`
- `[1]`, target=2 → `false`
- `[1,2,null,3,null,4]`, target=10 → `true` (left skewed)
- `[1,null,2,null,3,null,4]`, target=10 → `true` (right skewed)
- `[1,2,3,4,5,6,7]`, target=7 → `true` (1->2->4)
- `[1,2,3,4,5,6,7]`, target=100 → `false`
- `[1,2]`, target=3 → `true`
- `[1,null,2]`, target=3 → `true`
- `[-2,null,-3]`, target=-5 → `true`
- `[1,2,3]`, target=3 → `true` (1->2=3 and 2 is leaf)
- `[1,-2,-3,1,3,-2,null,-1]`, target=-1 → `true`