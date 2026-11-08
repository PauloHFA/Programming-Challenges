# 0073 - Set Matrix Zeroes

## Descrição

Dada uma matriz `m x n` de inteiros, se um elemento for 0, defina toda a sua linha e coluna como 0. Faça isso in-place.

## Exemplos

**Exemplo 1:**
```
Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
```

**Exemplo 2:**
```
Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
```

## Restrições

- `m == matrix.length`
- `n == matrix[0].length`
- `1 <= m, n <= 200`
- `-2^31 <= matrix[i][j] <= 2^31 - 1`

## Abordagem

### Método 1: O(1) Espaço (Principal) - Usa primeira linha/coluna como marcadores
1. Verifica se a primeira linha e primeira coluna têm zeros
2. Usa `matrix[i][0]` e `matrix[0][j]` como marcadores para linhas/colunas que devem ser zeradas
3. Processa o interior da matriz (i>=1, j>=1)
4. Aplica zeros baseado nos marcadores
5. Trata primeira linha e coluna separadamente
- **Tempo:** O(m × n), **Espaço:** O(1)

### Método 2: O(m+n) Espaço - Arrays auxiliares
- Usa dois arrays booleanos para marcar linhas e colunas
- **Tempo:** O(m × n), **Espaço:** O(m + n)

### Método 3: Sets
- Usa HashSet para armazenar índices de linhas/colunas com zeros
- **Tempo:** O(m × n), **Espaço:** O(m + n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| O(1) Marcadores | O(m × n) | O(1) |
| Arrays Auxiliares | O(m × n) | O(m + n) |
| Sets | O(m × n) | O(m + n) |

## Implementação

A solução principal usa **O(1) espaço** com a primeira linha e coluna como marcadores.

```java
public void setZeroes(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    
    boolean firstRowZero = false;
    boolean firstColZero = false;
    
    // Check if first row has zero
    for (int j = 0; j < n; j++) {
        if (matrix[0][j] == 0) {
            firstRowZero = true;
            break;
        }
    }
    
    // Check if first column has zero
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] == 0) {
            firstColZero = true;
            break;
        }
    }
    
    // Use first row and column as markers
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;
                matrix[0][j] = 0;
            }
        }
    }
    
    // Set zeros based on markers
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                matrix[i][j] = 0;
            }
        }
    }
    
    // Handle first row
    if (firstRowZero) {
        for (int j = 0; j < n; j++) {
            matrix[0][j] = 0;
        }
    }
    
    // Handle first column
    if (firstColZero) {
        for (int i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
    }
}
```

## Casos de Teste

- `[[1,1,1],[1,0,1],[1,1,1]]` → `[[1,0,1],[0,0,0],[1,0,1]]`
- `[[0,1,2,0],[3,4,5,2],[1,3,1,5]]` → `[[0,0,0,0],[0,4,5,0],[0,3,1,0]]`
- `[[1,0,3]]` → `[[0,0,0]]` (linha única)
- `[[1],[0],[3]]` → `[[0],[0],[0]]` (coluna única)
- `[[0,0],[0,0]]` → `[[0,0],[0,0]]` (todos zeros)
- `[[1,2],[3,4]]` → `[[1,2],[3,4]]` (sem zeros)
- `[[0,1,2],[3,4,5],[6,7,8]]` → `[[0,0,0],[0,4,5],[0,7,8]]` (zero no canto)