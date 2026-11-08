# 0079 - Word Search

## Descrição

Dado um tabuleiro `m x n` de caracteres e uma string `word`, retorne `true` se `word` existir no tabuleiro.

A palavra pode ser construída a partir de letras de células adjacentes sequencialmente, onde células adjacentes são horizontal ou verticalmente vizinhas. A mesma célula não pode ser usada mais de uma vez.

## Exemplos

**Exemplo 1:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
```

**Exemplo 2:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true
```

**Exemplo 3:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false
```

## Restrições

- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 6`
- `1 <= word.length <= 15`
- `board` e `word` consistem apenas de letras maiúsculas e minúsculas do inglês

## Abordagem

### Método 1: DFS com Modificação In-Place (Principal)
- Para cada célula que corresponde ao primeiro caractere, inicia DFS
- Marca células visitadas modificando o tabuleiro (ex: '#')
- Explora 4 direções: cima, baixo, esquerda, direita
- Backtrack: restaura o caractere original
- **Otimizações:**
  - Verificação prévia de frequência de caracteres
  - Inverte a palavra se o último caractere for mais raro
- **Tempo:** O(m * n * 4^L) onde L = word.length, **Espaço:** O(L) para pilha de recursão

### Método 2: DFS com Array Visited
- Usa array booleano separado para rastrear visitados
- Não modifica o tabuleiro original
- **Tempo:** O(m * n * 4^L), **Espaço:** O(m * n + L)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| In-Place | O(m * n * 4^L) | O(L) |
| Visited Array | O(m * n * 4^L) | O(m * n + L) |

## Implementação

A solução principal usa **DFS com modificação in-place** e otimizações.

```java
public boolean exist(char[][] board, String word) {
    int m = board.length;
    int n = board[0].length;
    
    // Optimization: frequency check
    int[] boardCount = new int[256];
    for (char[] row : board) {
        for (char c : row) boardCount[c]++;
    }
    for (char c : word.toCharArray()) {
        if (--boardCount[c] < 0) return false;
    }
    
    // Optimization: reverse if last char is rarer
    if (boardCount[word.charAt(word.length()-1)] < boardCount[word.charAt(0)]) {
        word = new StringBuilder(word).reverse().toString();
    }
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == word.charAt(0)) {
                if (dfs(board, word, i, j, 0)) return true;
            }
        }
    }
    return false;
}

private boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (index == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(index)) {
        return false;
    }
    
    char temp = board[i][j];
    board[i][j] = '#';
    
    if (dfs(board, word, i+1, j, index+1) ||
        dfs(board, word, i-1, j, index+1) ||
        dfs(board, word, i, j+1, index+1) ||
        dfs(board, word, i, j-1, index+1)) {
        board[i][j] = temp;
        return true;
    }
    
    board[i][j] = temp;
    return false;
}
```

## Casos de Teste

- `[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], "ABCCED"` → `true`
- `[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], "SEE"` → `true`
- `[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], "ABCB"` → `false`
- `[["a"]], "a"` → `true`
- `[["a"]], "b"` → `false`
- `[["a","b"]], "ba"` → `true`
- `[["a","b"]], "ab"` → `true`
- `[["A","B","C","E"],["S","F","E","S"],["A","D","E","E"]], "ABCESEEEFS"` → `true`