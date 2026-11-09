# 0120 - Triangle

## Descrição

Dado um array `triangle`, retorne o caminho de soma mínima do topo para a base.

Em cada passo, você pode mover para um número adjacente na linha abaixo. Mais formalmente, se você está no índice `i` na linha atual, pode mover para o índice `i` ou `i + 1` na próxima linha.

## Exemplos

**Exemplo 1:**
```
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explicação: O caminho de soma mínima é 2 + 3 + 5 + 1 = 11.
```

**Exemplo 2:**
```
Input: triangle = [[-10]]
Output: -10
```

## Restrições

- `1 <= triangle.length <= 200`
- `triangle[0].length == 1`
- `triangle[i].length == triangle[i - 1].length + 1`
- `-10^4 <= triangle[i][j] <= 10^4`

## Abordagem

### Método 1: DP Bottom-Up In-Place (Principal)
- Modifica o triangle de baixo para cima
- Cada célula recebe seu valor + mínimo dos dois filhos
- **Tempo:** O(n²), **Espaço:** O(1) extra

### Método 2: DP Bottom-Up com array 1D
- Usa array separado para não modificar input
- **Tempo:** O(n²), **Espaço:** O(n)

### Método 3: DP Top-Down
- Constrói tabela dp de cima para baixo
- **Tempo:** O(n²), **Espaço:** O(n²)

### Método 4: Recursivo com Memoização
- Top-down com cache
- **Tempo:** O(n²), **Espaço:** O(n²) + O(n) stack

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Bottom-Up In-Place | O(n²) | O(1) |
| Bottom-Up 1D | O(n²) | O(n) |
| Top-Down | O(n²) | O(n²) |
| Memoização | O(n²) | O(n²) |

## Implementação

A solução principal usa **DP Bottom-Up In-Place**.

```java
public int minimumTotal(List<List<Integer>> triangle) {
    for (int i = triangle.size() - 2; i >= 0; i--) {
        List<Integer> current = triangle.get(i);
        List<Integer> next = triangle.get(i + 1);
        
        for (int j = 0; j < current.size(); j++) {
            current.set(j, current.get(j) + Math.min(next.get(j), next.get(j + 1)));
        }
    }
    
    return triangle.get(0).get(0);
}
```

## Casos de Teste

- `[[2],[3,4],[6,5,7],[4,1,8,3]]` → `11`
- `[[-10]]` → `-10`
- `[[1],[2,3]]` → `3`
- `[[1],[2,3],[4,5,6]]` → `7`
- `[[-1],[2,3],[1,-1,-3]]` → `-1`