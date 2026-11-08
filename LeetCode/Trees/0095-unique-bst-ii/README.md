# 0095 - Unique Binary Search Trees II

## Descrição

Dado um inteiro `n`, retorne todas as árvores de busca binária (BST) estruturalmente únicas que têm exatamente `n` nós com valores únicos de `1` a `n`. Retorne a resposta em qualquer ordem.

## Exemplos

**Exemplo 1:**
```
Input: n = 3
Output: 5 árvores únicas
```

**Exemplo 2:**
```
Input: n = 1
Output: [[1]]
```

## Restrições

- `1 <= n <= 8`

## Abordagem

### Método 1: Recursão com Memoização (Principal)
- Para cada valor `i` de `start` a `end` como raiz:
  - Gera recursivamente todas as subárvores esquerdas com valores `start..i-1`
  - Gera recursivamente todas as subárvores direitas com valores `i+1..end`
  - Combina cada esquerda com cada direita
- Usa memoização implícita pela estrutura da recursão
- **Tempo:** O(C_n) onde C_n é o n-ésimo número de Catalan, **Espaço:** O(C_n)

### Método 2: DP Bottom-Up
- `dp[start][end]` = lista de árvores com valores `start..end`
- Preenche para intervalos de tamanho crescente
- **Tempo:** O(C_n), **Espaço:** O(C_n)

### Método 3: Recursão Pura
- Mesmo algoritmo sem memoização explícita
- Para n ≤ 5 é viável para verificação
- **Tempo:** O(C_n), **Espaço:** O(C_n)

## Números de Catalan

O número de BSTs únicas com n nós é o n-ésimo número de Catalan:
- C₀ = 1
- C₁ = 1
- C₂ = 2
- C₃ = 5
- C₄ = 14
- C₅ = 42
- C₆ = 132
- C₇ = 429
- C₈ = 1430

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursão + Memo | O(C_n) | O(C_n) |
| DP Bottom-Up | O(C_n) | O(C_n) |
| Recursão Pura | O(C_n) | O(C_n) |

## Implementação

A solução principal usa **Recursão com Memoização**.

```java
public List<TreeNode> generateTrees(int n) {
    if (n == 0) return new ArrayList<>();
    return generate(1, n);
}

private List<TreeNode> generate(int start, int end) {
    List<TreeNode> result = new ArrayList<>();
    
    if (start > end) {
        result.add(null);
        return result;
    }
    
    for (int i = start; i <= end; i++) {
        List<TreeNode> leftTrees = generate(start, i - 1);
        List<TreeNode> rightTrees = generate(i + 1, end);
        
        for (TreeNode left : leftTrees) {
            for (TreeNode right : rightTrees) {
                TreeNode root = new TreeNode(i);
                root.left = cloneTree(left);
                root.right = cloneTree(right);
                result.add(root);
            }
        }
    }
    
    return result;
}
```

## Casos de Teste

- `n = 3` → 5 árvores (Catalan 3)
- `n = 1` → 1 árvore
- `n = 2` → 2 árvores (Catalan 2)
- `n = 4` → 14 árvores (Catalan 4)
- `n = 0` → 0 árvores (empty list)