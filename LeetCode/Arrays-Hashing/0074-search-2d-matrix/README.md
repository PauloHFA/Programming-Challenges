# 0074 - Search a 2D Matrix

## Descrição

Escreva um algoritmo eficiente que busca um valor `target` em uma matriz `m x n`. Esta matriz tem as seguintes propriedades:

- Inteiros em cada linha são ordenados da esquerda para a direita
- O primeiro inteiro de cada linha é maior que o último inteiro da linha anterior

## Exemplos

**Exemplo 1:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true
```

**Exemplo 2:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false
```

## Restrições

- `m == matrix.length`
- `n == matrix[0].length`
- `1 <= m, n <= 100`
- `-10^4 <= matrix[i][j], target <= 10^4`

## Abordagem

### Método 1: Busca Binária 1D (Principal)
- Trata a matriz 2D como um array 1D ordenado de tamanho `m * n`
- Índice 1D `mid` mapeia para 2D: `row = mid / n`, `col = mid % n`
- **Tempo:** O(log(m × n)), **Espaço:** O(1)

### Método 2: Duas Buscas Binárias
- Primeira busca binária encontra a linha correta
- Segunda busca binária procura na linha
- **Tempo:** O(log m + log n) = O(log(m × n)), **Espaço:** O(1)

### Método 3: Busca em Escada (Top-Right)
- Começa no canto superior direito
- Se atual > target: move esquerda
- Se atual < target: move baixo
- **Tempo:** O(m + n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| 1D Binary Search | O(log(m × n)) | O(1) |
| Two Binary Search | O(log m + log n) | O(1) |
| Staircase | O(m + n) | O(1) |

## Implementação

A solução principal usa **Busca Binária 1D**.

```java
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return false;
    }
    
    int m = matrix.length;
    int n = matrix[0].length;
    
    int left = 0;
    int right = m * n - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int midValue = matrix[mid / n][mid % n];
        
        if (midValue == target) {
            return true;
        } else if (midValue < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return false;
}
```

## Casos de Teste

- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 3` → `true`
- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 13` → `false`
- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 60` → `true`
- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 0` → `false`
- `[[1,3,5,7]], 3` → `true` (linha única)
- `[[1,3,5,7]], 2` → `false`
- `[[1],[3],[5],[7]], 5` → `true` (coluna única)
- `[[1],[3],[5],[7]], 4` → `false`
- `[[5]], 5` → `true` (elemento único)
- `[[5]], 3` → `false`
- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 100` → `false`
- `[[1,3,5,7],[10,11,16,20],[23,30,34,60]], -1` → `false`