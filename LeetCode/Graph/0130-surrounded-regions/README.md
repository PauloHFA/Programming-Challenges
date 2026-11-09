# 0130 - Surrounded Regions

## Descrição

Dado um tabuleiro `m x n` contendo `'X'` e `'O'`, capture todas as regiões cercadas por `'X'`. Uma região é capturada virando todos os `'O'` em `'X'` nessa região cercada. Regiões na borda não são capturadas.

## Exemplos

**Exemplo 1:**
```
Input: board = [
  ["X","X","X","X"],
  ["X","O","O","X"],
  ["X","X","O","X"],
  ["X","O","X","X"]
]
Output: [
  ["X","X","X","X"],
  ["X","X","X","X"],
  ["X","X","X","X"],
  ["X","O","X","X"]
]
Explicação: O 'O' em (3,1) está na borda, então não é capturado.
```

**Exemplo 2:**
```
Input: board = [["X"]]
Output: [["X"]]
```

## Restrições

- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 200`
- `board[i][j]` é `'X'` ou `'O'`.

## Abordagem

### Método 1: DFS a partir das bordas (Principal)
- Inicia DFS de todos os 'O' nas 4 bordas
- Marca 'O' conectados à borda com caractere temporário '#'
- Após DFS, converte: 'O' → 'X' (capturados), '#' → 'O' (salvos)
- **Tempo:** O(m*n), **Espaço:** O(m*n) stack

### Método 2: BFS a partir das bordas
- Similar ao DFS mas usa queue
- **Tempo:** O(m*n), **Espaço:** O(m*n)

### Método 3: Union Find
- Une 'O' adjacentes
- Conecta 'O' de borda a nó virtual (dummy)
- 'O' conectados ao dummy não são capturados
- **Tempo:** O(m*n * α(m*n)), **Espaço:** O(m*n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS | O(m*n) | O(m*n) |
| BFS | O(m*n) | O(m*n) |
| Union Find | O(m*n α) | O(m*n) |

## Implementação

A solução principal usa **DFS a partir das bordas**.

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;
    
    int m = board.length, n = board[0].length;
    
    // DFS das bordas
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') dfs(board, i, 0);
        if (board[i][n-1] == 'O') dfs(board, i, n-1);
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') dfs(board, 0, j);
        if (board[m-1][j] == 'O') dfs(board, m-1, j);
    }
    
    // Converte
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X';
            else if (board[i][j] == '#') board[i][j] = 'O';
        }
    }
}

private void dfs(char[][] board, int i, int j) {
    if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') return;
    board[i][j] = '#';
    dfs(board, i+1, j);
    dfs(board, i-1, j);
    dfs(board, i, j+1);
    dfs(board, i, j-1);
}
```

## Casos de Teste

- Exemplo 1: O em (3,1) permanece, outros viram X
- Tabuleiro 1x1 com X
- Tabuleiro 3x3 todo O (todos conectados à borda)
- Centro cercado por X