# 0087 - Scramble String

## Descrição

Dadas duas strings `s1` e `s2` de mesmo comprimento, determine se `s2` é uma **string embaralhada** (scrambled string) de `s1`.

Uma string embaralhada é formada recursivamente:
1. Se a string tem comprimento 1, para.
2. Divide a string em duas substrings não vazias em um índice aleatório.
3. **Opcionalmente** troca as duas substrings.
4. Aplica recursivamente o passo 1-3 em cada substring.

## Exemplos

**Exemplo 1:**
```
Input: s1 = "great", s2 = "rgeat"
Output: true
Explanation: 
great -> gr/eat (split)
gr -> g/r (split, swap) -> rg
eat -> e/at (split)
at -> a/t (split, swap) -> ta
rg + e + ta = rgeat
```

**Exemplo 2:**
```
Input: s1 = "abcde", s2 = "caebd"
Output: false
```

**Exemplo 3:**
```
Input: s1 = "a", s2 = "a"
Output: true
```

## Restrições

- `s1.length == s2.length`
- `1 <= s1.length <= 30`
- `s1` e `s2` consistem de letras minúsculas inglesas

## Abordagem

### Método 1: DP 3D Bottom-Up (Principal)
- `dp[i][j][len]` = substring `s1[i..i+len-1]` é scramble de `s2[j..j+len-1]`
- Base case: `len = 1`, compara caracteres
- Transição: para cada ponto de divisão `k`, verifica:
  - **Sem troca**: `dp[i][j][k] && dp[i+k][j+k][len-k]`
  - **Com troca**: `dp[i][j+len-k][k] && dp[i+k][j][len-k]`
- **Tempo:** O(n⁴), **Espaço:** O(n³)

### Método 2: Recursion com Memoização (Top-down)
- Mesma lógica mas recursivo com cache
- Poda: verifica frequência de caracteres antes de recursão
- **Tempo:** O(n⁴) com poda, **Espaço:** O(n³) para memo

### Método 3: Recursion Pura
- Exponencial, apenas para verificação em strings pequenas (≤ 8)
- **Tempo:** O(2ⁿ), **Espaço:** O(n) stack

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP 3D Bottom-Up | O(n⁴) | O(n³) |
| Memoização | O(n⁴)* | O(n³) |
| Recursão Pura | O(2ⁿ) | O(n) |

*Com poda de frequência de caracteres

## Implementação

A solução principal usa **DP 3D Bottom-Up**.

```java
public boolean isScramble(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    if (s1.equals(s2)) return true;
    
    int n = s1.length();
    boolean[][][] dp = new boolean[n][n][n + 1];
    
    // Base case: len = 1
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            dp[i][j][1] = (s1.charAt(i) == s2.charAt(j));
        }
    }
    
    // Build up for len = 2 to n
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i + len <= n; i++) {
            for (int j = 0; j + len <= n; j++) {
                for (int k = 1; k < len; k++) {
                    // No swap
                    if (dp[i][j][k] && dp[i + k][j + k][len - k]) {
                        dp[i][j][len] = true;
                        break;
                    }
                    // Swap
                    if (dp[i][j + len - k][k] && dp[i + k][j][len - k]) {
                        dp[i][j][len] = true;
                        break;
                    }
                }
            }
        }
    }
    
    return dp[0][0][n];
}
```

## Casos de Teste

- `"great", "rgeat"` → `true`
- `"abcde", "caebd"` → `false`
- `"a", "a"` → `true`
- `"abc", "abc"` → `true`
- `"a", "b"` → `false`
- `"ab", "ba"` → `true`
- `"abc", "bca"` → `true`
- `"abc", "acb"` → `true`
- `"abc", "def"` → `false`
- `"great", "rgtae"` → `true`
- `"abcd", "bdac"` → `false`
- `"aaaa", "aaaa"` → `true`
- Rotation test → `true`