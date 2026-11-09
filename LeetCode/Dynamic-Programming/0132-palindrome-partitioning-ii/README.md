# 0132 - Palindrome Partitioning II

## Descrição

Dada uma string `s`, particione `s` de forma que cada substring da partição seja um palíndromo. Retorne o **mínimo de cortes** necessários para uma partição de palíndromo de `s`.

## Exemplos

**Exemplo 1:**
```
Input: s = "aab"
Output: 1
Explicação: A partição ["aa","b"] requer 1 corte.
```

**Exemplo 2:**
```
Input: s = "a"
Output: 0
```

**Exemplo 3:**
```
Input: s = "ab"
Output: 1
```

## Restrições

- `1 <= s.length <= 2000`
- `s` consiste apenas de letras minúsculas inglesas.

## Abordagem

### Método 1: DP com Pré-computação (Principal)
- `dp[i]` = min cortes para `s[0..i-1]`
- Pré-computa tabela `isPal[i][j]`
- `dp[i] = min(dp[j] + 1)` para todo `j` onde `s[j..i-1]` é palíndromo
- **Tempo:** O(n²), **Espaço:** O(n²)

### Método 2: Expansão de Centro (Otimizado Espaço)
- Expande palíndromos a partir de cada centro (ímpar e par)
- Atualiza `dp` diretamente durante expansão
- **Tempo:** O(n²), **Espaço:** O(n)

### Método 3: DP com Verificação On-the-Fly
- Verifica palíndromo durante o DP sem pré-computação
- **Tempo:** O(n²), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP + Pré-computação | O(n²) | O(n²) |
| Expansão Centro | O(n²) | O(n) |
| On-the-Fly | O(n²) | O(n) |

## Implementação

A solução principal usa **DP com Pré-computação**.

```java
public int minCut(String s) {
    int n = s.length();
    boolean[][] isPal = new boolean[n][n];
    
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            isPal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || isPal[i + 1][j - 1]);
        }
    }
    
    int[] dp = new int[n + 1];
    dp[0] = -1;
    for (int i = 1; i <= n; i++) {
        dp[i] = i - 1;
        for (int j = 0; j < i; j++) {
            if (isPal[j][i - 1]) {
                dp[i] = Math.min(dp[i], dp[j] + 1);
            }
        }
    }
    
    return dp[n];
}
```

## Casos de Teste

- `"aab"` → `1` (aa|b)
- `"a"` → `0`
- `"ab"` → `1` (a|b)
- `"aaa"` → `0`
- `"abc"` → `2` (a|b|c)
- `"abba"` → `0`
- `"abac"` → `1` (aba|c)
- `"racecar"` → `0`
- `"ababab"` → `1` (aba|bab)
- `"abcba"` → `0`