# 0072 - Edit Distance

## Descrição

Dadas duas strings `word1` e `word2`, retorne o número mínimo de operações necessárias para converter `word1` em `word2`.

Você tem as seguintes três operações permitidas em uma palavra:
- Inserir um caractere
- Deletar um caractere
- Substituir um caractere

## Exemplos

**Exemplo 1:**
```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (substituir 'h' por 'r')
rorse -> rose (remover 'r')
rose -> ros (remover 'e')
```

**Exemplo 2:**
```
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation:
intention -> inention (remover 't')
inention -> enention (substituir 'i' por 'e')
enention -> exention (substituir 'n' por 'x')
exention -> exection (substituir 'n' por 'c')
exection -> execution (inserir 'u')
```

## Restrições

- `0 <= word1.length, word2.length <= 500`
- `word1` e `word2` consistem de letras minúsculas do inglês

## Abordagem

### Programação Dinâmica

Seja `dp[i][j]` a distância de edição entre `word1[0..i-1]` e `word2[0..j-1]`.

**Transição:**
- Se `word1[i-1] == word2[j-1]`: `dp[i][j] = dp[i-1][j-1]` (sem operação)
- Senão: `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`
  - `dp[i-1][j-1]`: substituir
  - `dp[i-1][j]`: deletar
  - `dp[i][j-1]`: inserir

**Casos base:**
- `dp[i][0] = i` (deletar todos os caracteres de word1)
- `dp[0][j] = j` (inserir todos os caracteres de word2)

### Otimização de Espaço

Como `dp[i][j]` depende apenas da linha anterior e da célula anterior, podemos usar apenas um array 1D de tamanho `n+1`.

### Métodos Implementados

1. **O(n) Espaço (Principal)** - Array 1D otimizado
2. **2D DP Array** - Para visualização e debug
3. **Memoização Recursiva** - Top-down com cache

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| O(n) DP | O(m × n) | O(n) |
| 2D DP | O(m × n) | O(m × n) |
| Memoização | O(m × n) | O(m × n) |

## Implementação

A solução principal usa **DP com O(n) espaço**.

```java
public int minDistance(String word1, String word2) {
    int m = word1.length();
    int n = word2.length();
    
    int[] dp = new int[n + 1];
    for (int j = 0; j <= n; j++) {
        dp[j] = j;
    }
    
    for (int i = 1; i <= m; i++) {
        int prev = dp[0];
        dp[0] = i;
        
        for (int j = 1; j <= n; j++) {
            int temp = dp[j];
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                dp[j] = prev;
            } else {
                dp[j] = 1 + Math.min(prev, Math.min(dp[j - 1], dp[j]));
            }
            prev = temp;
        }
    }
    
    return dp[n];
}
```

## Casos de Teste

- `"horse"` → `"ros"` = 3
- `"intention"` → `"execution"` = 5
- `""` → `""` = 0
- `"a"` → `""` = 1
- `""` → `"a"` = 1
- `"a"` → `"b"` = 1
- `"ab"` → `"ab"` = 0
- `"abc"` → `"def"` = 3
- `"kitten"` → `"sitting"` = 3
- `"saturday"` → `"sunday"` = 3