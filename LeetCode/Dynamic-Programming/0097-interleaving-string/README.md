# 0097 - Interleaving String

## Descrição

Dadas strings `s1`, `s2` e `s3`, determine se `s3` é formada por uma **intercalação** de `s1` e `s2`.

Uma intercalação de duas strings `s` e `t` é uma configuração onde elas são divididas em substrings não vazias alternadamente:
- `s = s1 + s2 + ... + sn`
- `t = t1 + t2 + ... + tm`
- `|n - m| <= 1`
- A intercalação é `s1 + t1 + s2 + t2 + ...` ou `t1 + s1 + t2 + s2 + ...`

Nota: `a + b` significa concatenação de strings.

## Exemplos

**Exemplo 1:**
```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Explanation: Uma intercalação válida é "aa" (s1) + "dbbc" (s2) + "bc" (s1) + "a" (s2) + "c" (s1)
```

**Exemplo 2:**
```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
```

**Exemplo 3:**
```
Input: s1 = "", s2 = "", s3 = ""
Output: true
```

## Restrições

- `0 <= s1.length, s2.length <= 100`
- `0 <= s3.length <= 200`
- `s1`, `s2`, `s3` consistem de letras minúsculas inglesas

## Abordagem

### Método 1: DP 2D Bottom-Up (Principal)
- `dp[i][j]` = `s1[0..i-1]` e `s2[0..j-1]` podem formar `s3[0..i+j-1]`
- Transição:
  - `dp[i][j] = (dp[i-1][j] && s1[i-1] == s3[i+j-1]) || (dp[i][j-1] && s2[j-1] == s3[i+j-1])`
- Base cases: primeira linha e primeira coluna
- **Tempo:** O(m × n), **Espaço:** O(m × n)

### Método 2: DP 1D (Otimização de Espaço)
- Usa array 1D de tamanho `min(m, n) + 1`
- Atualiza in-place linha por linha
- **Tempo:** O(m × n), **Espaço:** O(min(m, n))

### Método 3: Recursão com Memoização
- Top-down com cache `memo[i][j]`
- **Tempo:** O(m × n), **Espaço:** O(m × n)

### Método 4: BFS
- Explora estados (i, j) como grafo
- **Tempo:** O(m × n), **Espaço:** O(m × n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP 2D | O(m × n) | O(m × n) |
| DP 1D | O(m × n) | O(min(m, n)) |
| Memoização | O(m × n) | O(m × n) |
| BFS | O(m × n) | O(m × n) |

## Implementação

A solução principal usa **DP 2D Bottom-Up**.

```java
public boolean isInterleave(String s1, String s2, String s3) {
    int m = s1.length();
    int n = s2.length();
    
    if (m + n != s3.length()) return false;
    
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    
    for (int j = 1; j <= n; j++) {
        dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
    }
    
    for (int i = 1; i <= m; i++) {
        dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char c3 = s3.charAt(i + j - 1);
            dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == c3) ||
                       (dp[i][j - 1] && s2.charAt(j - 1) == c3);
        }
    }
    
    return dp[m][n];
}
```

## Casos de Teste

- `"aabcc", "dbbca", "aadbbcbcac"` → `true`
- `"aabcc", "dbbca", "aadbbbaccc"` → `false`
- `"", "", ""` → `true`
- `"a", "b", "ab"` → `true`
- `"a", "b", "ba"` → `true`
- `"a", "", "a"` → `true`
- `"abc", "def", "abdefc"` → `false`
- `"aaa", "aaa", "aaaaaa"` → `true`
- `"aab", "aac", "aaabac"` → `true`
- `"aab", "aac", "aaaabc"` → `false`