# 0104 - Maximum Depth of Binary Tree

## Descrição

Dado o `root` de uma árvore binária, retorne sua profundidade máxima.
A profundidade máxima de uma árvore binária é o número de nós ao longo do caminho mais longo desde o nó raiz até o nó folha mais distante.

## Exemplos

**Exemplo 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: 3
```

**Exemplo 2:**
```
Input: root = [1,null,2]
Output: 2
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 10^4]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: DFS Recursivo (Principal)
- Profundidade = 1 + max(profundidade esquerda, profundidade direita)
- Caso base: nó null retorna 0
- **Tempo:** O(n), **Espaço:** O(h) stack de recursão (h = altura)

### Método 2: BFS Iterativo (Level Order)
- Conta quantos níveis existem na árvore
- Processa nível por nível com queue
- **Tempo:** O(n), **Espaço:** O(w) largura máxima

### Método 3: DFS Iterativo com Stack
- Usa stack para simular recursão
- Guarda pares (nó, profundidade_atual)
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: Morris Traversal (O(1) espaço)
- Modifica temporariamente a árvore criando threads
- Evita stack/queue completamente
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(n) | O(h) |
| BFS Iterativo | O(n) | O(w) |
| DFS Iterativo | O(n) | O(h) |
| Morris | O(n) | O(1) |

## Implementação

A solução principal usa **DFS Recursivo**.

```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

## Casos de Teste

- `[3,9,20,null,null,15,7]` → `3`
- `[1,null,2]` → `2`
- `[]` → `0`
- `[1]` → `1`
- `[1,2,null,3,null,4]` → `4` (left skewed)
- `[1,null,2,null,3,null,4]` → `4` (right skewed)
- `[1,2,3,4,5,6,7]` → `3` (complete)
- Perfect tree height 3 → `4`
- `[1,2,3,4,null,5,6,7,null,null,null,null,8]` → `4`
- `[1,2]` → `2`
- `[1,null,2]` → `2`
- `[-1,-2,-3,-4,-5]` → `3`
- `[1,2,3,4,5,null,null,6,7]` → `4` (unbalanced)