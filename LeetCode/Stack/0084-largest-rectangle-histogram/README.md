# 0084 - Largest Rectangle in Histogram

## Descrição

Dado um array de inteiros `heights` representando as alturas das barras de um histograma onde a largura de cada barra é 1, retorne a área do maior retângulo no histograma.

## Exemplos

**Exemplo 1:**
```
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: O maior retângulo tem área 10 (barras de altura 5 e 6, largura 2).
```

**Exemplo 2:**
```
Input: heights = [2,4]
Output: 4
```

## Restrições

- `1 <= heights.length <= 10^5`
- `0 <= heights[i] <= 10^4`

## Abordagem

### Método 1: Monotonic Stack (Principal) - One Pass
- Mantém uma pilha de índices com alturas crescentes
- Quando encontra altura menor, calcula área para barras na pilha
- Adiciona altura 0 no final para esvaziar a pilha
- **Tempo:** O(n), **Espaço:** O(n)

### Método 2: Two Pass com Limites
- Primeira passada: encontra limite esquerdo para cada barra
- Segunda passada: encontra limite direito para cada barra
- Calcula área usando limites
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Divide and Conquer
- Encontra barra mínima, calcula área, recursa nos lados
- **Tempo:** O(n log n) médio, O(n²) pior caso, **Espaço:** O(log n)

### Método 4: Força Bruta (Verificação)
- Verifica todos os intervalos possíveis
- **Tempo:** O(n²), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Monotonic Stack | O(n) | O(n) |
| Two Pass | O(n) | O(n) |
| Divide & Conquer | O(n log n) | O(log n) |
| Força Bruta | O(n²) | O(1) |

## Implementação

A solução principal usa **Monotonic Stack (One Pass)**.

```java
public int largestRectangleArea(int[] heights) {
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

- `[2,1,5,6,2,3]` → `10`
- `[2,4]` → `4`
- `[1]` → `1`
- `[2,1,2]` → `3`
- `[5,4,3,2,1]` → `9`
- `[1,2,3,4,5]` → `9`
- `[2,2,2,2]` → `8`
- `[1,3,2,1,2]` → `5`
- `[6,2,5,4,5,1,6]` → `12`