# 0129 - Sum Root to Leaf Numbers

## Descrição

Você recebe o `root` de uma árvore binária contendo dígitos de `0` a `9` apenas.
Cada caminho da raiz até folha representa um número.
Retorne a **soma total** de todos os números da raiz até folha.
Um **nó folha** é um nó sem filhos.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,2,3]
Output: 25
Explicação: 
Caminho 1->2 representa 12
Caminho 1->3 representa 13
Soma = 12 + 13 = 25
```

**Exemplo 2:**
```
Input: root = [4,9,0,5,1]
Output: 1026
Explicação:
Caminho 4->9->5 representa 495
Caminho 4->9->1 representa 491
Caminho 4->0 representa 40
Soma = 495 + 491 + 40 = 1026
```

## Restrições

- O número de nós na árvore está no intervalo `[1, 1000]`.
- `0 <= Node.val <= 9`
- A profundidade da árvore não excederá `10`.

## Abordagem

### Método 1: DFS Recursivo (Principal)
- Passa número atual como parâmetro
- Em cada nó: `currentSum = currentSum * 10 + node.val`
- Se folha, retorna o número formado
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: DFS Iterativo com Stack
- Usa stack para guardar (nó, número_até_agora)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: BFS com Queue
- Usa queue para processar nível a nível
- **Tempo:** O(n), **Espaço:** O(w)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(n) | O(h) |
| DFS Iterativo | O(n) | O(h) |
| BFS | O(n) | O(w) |

## Implementação

A solução principal usa **DFS Recursivo**.

```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int currentSum) {
    if (node == null) return 0;

    currentSum = currentSum * 10 + node.val;

    if (node.left == null && node.right == null) {
        return currentSum;
    }

    return dfs(node.left, currentSum) + dfs(node.right, currentSum);
}
```

## Casos de Teste

- `[1,2,3]` → `25` (12 + 13)
- `[4,9,0,5,1]` → `1026` (495 + 491 + 40)
- `[1]` → `1`
- `[1,2,null,3]` → `123` (left skewed)
- `[1,null,2,null,3]` → `123` (right skewed)
- `[1,0,2]` → `22` (10 + 12)
- `[1,2,3,4,5,6,7]` → `522` (124+125+136+137)