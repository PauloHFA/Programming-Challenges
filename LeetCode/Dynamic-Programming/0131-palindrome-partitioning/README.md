# 0131 - Palindrome Partitioning

## Descrição

Dada uma string `s`, particione `s` de forma que cada substring da partição seja um palíndromo. Retorne todas as partições de palíndromo possíveis de `s`.

## Exemplos

**Exemplo 1:**
```
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
```

**Exemplo 2:**
```
Input: s = "a"
Output: [["a"]]
```

## Restrições

- `1 <= s.length <= 16`
- `s` contém apenas letras minúsculas inglesas.

## Abordagem

### Método 1: Backtracking com DP (Principal)
- Pré-computa tabela `isPal[i][j]` = se s[i..j] é palíndromo
- Backtracking: para cada posição inicial, tenta todos os fins válidos
- **Tempo:** O(n * 2^n), **Espaço:** O(n²)

### Método 2: Backtracking On-the-Fly
- Verifica palíndromo durante o backtracking sem pré-computação
- **Tempo:** O(n * 2^n), **Espaço:** O(n)

### Método 3: DP + Memoização
- Memoiza partições a partir de cada índice
- **Tempo:** O(n * 2^n), **Espaço:** O(n * 2^n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Backtracking + DP | O(n * 2^n) | O(n²) |
| On-the-Fly | O(n * 2^n) | O(n) |
| Memoização | O(n * 2^n) | O(n * 2^n) |

## Implementação

A solução principal usa **Backtracking com DP**.

```java
public List<List<String>> partition(String s) {
    int n = s.length();
    boolean[][] isPal = new boolean[n][n];
    
    // Pré-computa palíndromos
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            isPal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || isPal[i + 1][j - 1]);
        }
    }
    
    List<List<String>> result = new ArrayList<>();
    backtrack(s, 0, isPal, new ArrayList<>(), result);
    return result;
}

private void backtrack(String s, int start, boolean[][] isPal, 
                       List<String> path, List<List<String>> result) {
    if (start == s.length()) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int end = start; end < s.length(); end++) {
        if (isPal[start][end]) {
            path.add(s.substring(start, end + 1));
            backtrack(s, end + 1, isPal, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## Casos de Teste

- `"aab"` → `[["a","a","b"],["aa","b"]]`
- `"a"` → `[["a"]]`
- `"aaa"` → `[["a","a","a"],["a","aa"],["aa","a"],["aaa"]]`
- `"abc"` → `[["a","b","c"]]`
- `"abba"` → `[["a","b","b","a"],["a","bb","a"],["abba"]]`
- `"racecar"` → 4 partições