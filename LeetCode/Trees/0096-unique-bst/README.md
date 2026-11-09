# 0096 - Unique Binary Search Trees

## Descrição

Dado um inteiro `n`, retorne o número de árvores de busca binária (BST) estruturalmente únicas que têm exatamente `n` nós com valores únicos de `1` a `n`.

## Exemplos

**Exemplo 1:**
```
Input: n = 3
Output: 5
```

**Exemplo 2:**
```
Input: n = 1
Output: 1
```

## Restrições

- `1 <= n <= 19`

## Abordagem

### Método 1: DP Bottom-Up (Números de Catalan) - Principal
- `dp[i]` = número de BSTs únicas com `i` nós
- `dp[0] = 1` (árvore vazia)
- `dp[1] = 1` (único nó)
- Para `i >= 2`: `dp[i] = Σ(dp[j] * dp[i-1-j])` para `j = 0..i-1`
  - `j` nós na subárvore esquerda, `i-1-j` na direita
- **Tempo:** O(n²), **Espaço:** O(n)

### Método 2: Fórmula Direta do Número de Catalan
- C_n = (2n)! / ((n+1)! * n!) = C(2n, n) / (n+1)
- Calculado iterativamente para evitar overflow
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Recursão com Memoização
- Top-down com cache
- **Tempo:** O(n²), **Espaço:** O(n)

### Método 4: Recursão Pura
- Exponencial, apenas para verificação em n pequeno (≤ 10)
- **Tempo:** O(2^n), **Espaço:** O(n) stack

## Números de Catalan

| n | C_n |
|---|-----|
| 0 | 1 |
| 1 | 1 |
| 2 | 2 |
| 3 | 5 |
| 4 | 14 |
| 5 | 42 |
| 6 | 132 |
| 7 | 429 |
| 8 | 1430 |
| 9 | 4862 |
| 10 | 16796 |
| 19 | 1767263190 |

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP Bottom-Up | O(n²) | O(n) |
| Fórmula Catalan | O(n) | O(1) |
| Memoização | O(n²) | O(n) |
| Recursão Pura | O(2^n) | O(n) |

## Implementação

A solução principal usa **DP Bottom-Up**.

```java
public int numTrees(int n) {
    long[] dp = new long[n + 1];
    dp[0] = 1;
    dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            dp[i] += dp[j] * dp[i - 1 - j];
        }
    }
    
    return (int) dp[n];
}
```

## Casos de Teste

- `n = 3` → `5`
- `n = 1` → `1`
- `n = 2` → `2`
- `n = 4` → `14`
- `n = 5` → `42`
- `n = 6` → `132`
- `n = 7` → `429`
- `n = 8` → `1430`
- `n = 19` → `1767263190` (máximo da constraint)