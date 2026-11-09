# LeetCode 174 - Dungeon Game

## Problema

The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of m x n rooms laid out in a 2D grid. Our valiant knight was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons (represented by negative integers), so the knight loses health upon entering these rooms; other rooms are either empty (0) or contain magic orbs that increase the knight's health (represented by positive integers).

To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.

Return the knight's minimum initial health so that he can rescue the princess.

Note that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.

### Exemplo 1:
```
Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
Output: 7
Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: RIGHT -> RIGHT -> DOWN -> DOWN.
```

### Exemplo 2:
```
Input: dungeon = [[0]]
Output: 1
```

### Restrições:
- m == dungeon.length
- n == dungeon[i].length
- 1 <= m, n <= 200
- -1000 <= dungeon[i][j] <= 1000

---

## Abordagens

### Abordagem 1: DP Bottom-Up (Ótima)
`dp[i][j]` = vida mínima necessária para chegar à princesa a partir da célula (i,j). Trabalha de trás para frente (bottom-right para top-left).
- **Complexidade**: O(m×n) tempo, O(m×n) espaço

### Abordagem 2: DP com Array 1D (Espaço Otimizado)
Como só precisamos da linha abaixo e da célula à direita, podemos usar um array 1D.
- **Complexidade**: O(m×n) tempo, O(n) espaço

### Abordagem 3: DP Top-Down com Memoização
Recursão com memoização começando do topo-esquerdo.
- **Complexidade**: O(m×n) tempo, O(m×n) espaço

### Abordagem 4: Busca Binária + Verificação de Caminho
Busca binária na resposta (vida inicial) + verifica se existe caminho válido com DP forward.
- **Complexidade**: O(m×n log MAX) tempo, O(m×n) espaço

### Abordagem 5: DP com Rastreamento de Caminho
Versão da Abordagem 1 que também reconstrói o caminho ótimo (para debug).
- **Complexidade**: O(m×n) tempo, O(m×n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Bottom-Up | O(m×n) | O(m×n) |
| 1D Array | O(m×n) | O(n) |
| Top-Down | O(m×n) | O(m×n) |
| Binary Search | O(m×n log MAX) | O(m×n) |
| With Path | O(m×n) | O(m×n) |

---

## Casos de Teste

1. **Exemplo 1**: `[[-2,-3,3],[-5,-10,1],[10,30,-5]]` -> 7
2. **Exemplo 2**: `[[0]]` -> 1
3. **Célula única positiva**: `[[5]]` -> 1
4. **Célula única negativa**: `[[-10]]` -> 11
5. **Todos positivos**: `[[1,2,3],[4,5,6]]` -> 1
6. **Todos negativos**: `[[-1,-2],[-3,-4]]` -> 8
7. **Misto**: `[[1,-3,3],[0,-2,0],[-3,-3,-3]]` -> 3
8. **Grid 4x4**: `[[-2,-3,3,-5],[-5,-10,1,2],[10,30,-5,-10],[1,2,3,-5]]` -> 8

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.