# 0085 - Maximal Rectangle

## Descrição

Dada uma matriz binária `matrix` preenchida com '0's e '1's, encontre o maior retângulo contendo apenas '1's e retorne sua área.

## Exemplos

**Exemplo 1:**
```
Input: matrix = [
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6
Explanation: O maior retângulo de 1's tem área 6 (linhas 1-2, colunas 2-4).
```

**Exemplo 2:**
```
Input: matrix = [["0"]]
Output: 0
```

**Exemplo 3:**
```
Input: matrix = [["1"]]
Output: 1
```

## Restrições

- `rows == matrix.length`
- `cols == matrix[i].length`
- `1 <= rows, cols <= 200`
- `matrix[i][j]` é '0' ou '1'

## Abordagem

### Método 1: Monotonic Stack por Linha (Principal)
- Transforma cada linha em um histograma de alturas consecutivas de '1's
- Reutiliza a solução do **0084 - Largest Rectangle in Histogram** para cada linha
- **Tempo:** O(rows × cols), **Espaço:** O(cols)

### Método 2: DP com Limites Esquerdo/Direito
- Mantém três arrays por coluna: `height`, `left`, `right`
- `height[j]`: altura consecutiva de '1's até a linha atual
- `left[j]`: limite esquerdo mais distante onde altura >= height[j]
- `right[j]`: limite direito mais próximo onde altura >= height[j]
- **Tempo:** O(rows × cols), **Espaço:** O(cols)

### Método 3: Stack Variant (Otimizado)
- Similar ao método 1 mas com array de altura reutilizado e sentinela
- **Tempo:** O(rows × cols), **Espaço:** O(cols)

### Método 4: Força Bruta (Verificação)
- Verifica todos os retângulos possíveis
- **Tempo:** O(rows² × cols²), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Monotonic Stack | O(rows × cols) | O(cols) |
| DP Limites | O(rows × cols) | O(cols) |
| Stack Variant | O(rows × cols) | O(cols) |
| Força Bruta | O(rows² × cols²) | O(1) |

## Implementação

A solução principal usa **Monotonic Stack por Linha**, reutilizando a lógica do problema 0084.

```java
public int maximalRectangle(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return 0;
    }
    
    int rows = matrix.length;
    int cols = matrix[0].length;
    int[] heights = new int[cols];
    int maxArea = 0;
    
    for (int i = 0; i < rows; i++) {
        // Atualiza heights para a linha atual
        for (int j = 0; j < cols; j++) {
            if (matrix[i][j] == '1') {
                heights[j] += 1;
            } else {
                heights[j] = 0;
            }
        }
        
        // Calcula maior retângulo no histograma atual
        maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    
    return maxArea;
}

private int largestRectangleArea(int[] heights) {
    // Implementação do 0084 - Monotonic Stack
    int n = heights.length;
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0;
    
    for (int i = 0; i <= n; i++) {
        int currHeight = (i == n) ? 0 : heights[i];
        
        while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        stack.push(i);
    }
    
    return maxArea;
}
```

## Casos de Teste

- Exemplo 1 (4×5) → `6`
- `[["0"]]` → `0`
- `[["1"]]` → `1`
- 3×3 all ones → `9`
- Single row `["1","0","1","1","1","0","1"]` → `3`
- Single column → `2`
- Checkerboard 3×4 → `1`
- L-shape 3×3 → `3`
- Two separate rectangles → `6`
- Bottom rectangle 4×4 → `8`
- Empty matrix → `0`
- Empty row → `0`