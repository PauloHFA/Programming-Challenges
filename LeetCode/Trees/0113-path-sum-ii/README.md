# 0113 - Path Sum II

## Descrição

Dado o `root` de uma árvore binária e um inteiro `targetSum`, retorne todos os caminhos da raiz até folha onde a soma dos valores dos nós no caminho seja igual a `targetSum`. Cada caminho deve ser retornado como uma lista dos valores dos nós, não referências de nó.
Um nó folha é um nó sem filhos.

## Exemplos

**Exemplo 1:**
```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explicação: Existem dois caminhos cuja soma é 22:
- 5 -> 4 -> 11 -> 2 = 22
- 5 -> 8 -> 4 -> 5 = 22
```

**Exemplo 2:**
```
Input: root = [1,2,3], targetSum = 5
Output: []
```

**Exemplo 3:**
```
Input: root = [], targetSum = 0
Output: []
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 5000]`.
- `-1000 <= Node.val <= 1000`
- `-1000 <= targetSum <= 1000`

## Abordagem

### Método 1: DFS Recursivo com Backtracking (Principal)
- Usa backtracking para construir caminhos incrementalmente
- Quando chega em folha, verifica se soma bate e adiciona cópia do caminho ao resultado
- Remove último elemento ao voltar (backtrack)
- **Tempo:** O(n²) pior caso (cópia de lista), **Espaço:** O(h) stack + O(n) resultado

### Método 2: DFS Iterativo com Stack
- Usa stack para simular recursão mantendo (nó, soma_atual, caminho_atual)
- **Tempo:** O(n²), **Espaço:** O(h)

### Método 3: BFS com Queue
- Usa queue para processar nível a nível mantendo estado do caminho
- **Tempo:** O(n²), **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(n²) | O(h) + O(n) |
| DFS Iterativo | O(n²) | O(h) |
| BFS | O(n²) | O(w) |

## Implementação

A solução principal usa **DFS Recursivo com Backtracking**.

```java
public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPath = new ArrayList<>();
    dfs(root, targetSum, currentPath, result);
    return result;
}

private void dfs(TreeNode node, int remainingSum, List<Integer> currentPath, List<List<Integer>> result) {
    if (node == null) return;

    currentPath.add(node.val);

    if (node.left == null && node.right == null) {
        if (remainingSum == node.val) {
            result.add(new ArrayList<>(currentPath));
        }
    } else {
        dfs(node.left, remainingSum - node.val, currentPath, result);
        dfs(node.right, remainingSum - node.val, currentPath, result);
    }

    currentPath.remove(currentPath.size() - 1); // Backtrack
}
```

## Casos de Teste

- `[5,4,8,11,null,13,4,7,2,null,null,5,1]`, target=22 → `[[5,4,11,2],[5,8,4,5]]`
- `[1,2,3]`, target=5 → `[]`
- `[]`, target=0 → `[]`
- `[1]`, target=1 → `[[1]]`
- `[1]`, target=2 → `[]`
- `[1,2,null,3,null,4]`, target=10 → `[[1,2,3,4]]` (left skewed)
- `[1,null,2,null,3,null,4]`, target=10 → `[[1,2,3,4]]` (right skewed)
- `[1,2,3,4,5,6,7]`, target=7 → `[[1,2,4]]`
- `[-2,null,-3]`, target=-5 → `[[-2,-3]]`
- `[1,-2,-3,1,3,-2,null,-1]`, target=-1 → `[[1,-2,1,-1]]`