# 0115 - Distinct Subsequences

## Descrição

Dadas duas strings `s` e `t`, retorne o número de subsequências distintas de `s` que são iguais a `t`.

Uma subsequência de uma string é uma nova string formada a partir da string original eliminando alguns (podem ser nenhum) caracteres sem alterar a ordem relativa dos caracteres restantes.

## Exemplos

**Exemplo 1:**
```
Input: s = "rabbbit", t = "rabbit"
Output: 3
Explicação:
Como mostrado abaixo, existem 3 maneiras de gerar "rabbit" a partir de "rabbbit".
rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
```

**Exemplo 2:**
```
Input: s = "babgbag", t = "bag"
Output: 5
```

## Restrições

- `1 <= s.length, t.length <= 1000`
- `s` e `t` consistem de letras minúsculas inglesas.

## Abordagem

### Método 1: DP 2D (Principal)
- `dp[i][j]` = número de subsequências distintas de `s[0..i-1]` que igualam `t[0..j-1]`
- Se `s[i-1] == t[j-1]`: `dp[i][j] = dp[i-1][j-1] + dp[i-1][j]` (usa o char ou não)
- Senão: `dp[i][j] = dp[i-1][j]` (não pode usar o char)
- **Tempo:** O(m*n), **Espaço:** O(m*n)

### Método 2: DP 1D Otimizado
- Usa apenas uma linha `dp[j]`, iterando `j` de trás para frente
- **Tempo:** O(m*n), **Espaço:** O(n)

### Método 3: DP com Memoização (Top-down)
- Recursivo com cache `memo[i][j]`
- **Tempo:** O(m*n), **Espaço:** O(m*n) + O(m+n) stack

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP 2D | O(m*n) | O(m*n) |
| DP 1D | O(m*n) | O(n) |
| Memoização | O(m*n) | O(m*n) |

## Implementação

A solução principal usa **DP 2D**.

```java
public int numDistinct(String s, String t) {
    int m = s.length(), n = t.length();
    long[][] dp = new long[m + 1][n + 1];
    
    for (int i = 0; i <= m; i++) {
        dp[i][0] = 1;
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (s.charAt(i - 1) == t.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            } else {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    
    return (int) dp[m][n];
}
```

## Casos de Teste

- `"rabbbit", "rabbit"` → `3`
- `"babgbag", "bag"` → `5`
- `"abc", ""` → `1`
- `"", "a"` → `0`
- `"abc", "abc"` → `1`
- `"abc", "def"` → `0`
- `"aaa", "aa"` → `3`
- `"aaaaaaaaaa", "aaaa"` → `210`