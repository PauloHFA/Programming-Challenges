# 0063 - Unique Paths II

## Descrição do Problema

Um robô está localizado no canto superior esquerdo de uma grade `m x n`. O robô só pode se mover **para baixo** ou **para a direita**.

Agora, a grade pode conter **obstáculos** (marcados como `1`). Espaços livres são marcados como `0`.

Quantos caminhos únicos existem do canto superior esquerdo ao canto inferior direito?

## Exemplos

### Exemplo 1:
```
Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2
Explanation: Há um obstáculo no centro. Os 2 caminhos válidos contornam o obstáculo.
```

### Exemplo 2:
```
Input: obstacleGrid = [[0,1],[0,0]]
Output: 1
```

## Restrições

- `m == obstacleGrid.length`
- `n == obstacleGrid[i].length`
- `1 <= m, n <= 100`
- `obstacleGrid[i][j]` é `0` ou `1`.

## Abordagem

### DP com Tratamento de Obstáculos (O(n) espaço)

Similar ao problema 0062, mas com verificação de obstáculos:

**Estado:** `dp[j]` = caminhos para célula na linha atual, coluna `j`.

**Transição:**
- Se `obstacleGrid[i][j] == 1`: `dp[j] = 0` (obstáculo bloqueia)
- Senão se `j > 0`: `dp[j] += dp[j-1]` (somar caminhos da esquerda)
- `dp[j]` já contém caminhos de cima (linha anterior)

**Inicialização:** `dp[0] = 1` se não houver obstáculo no início.

### Complexidade

- **Tempo:** O(m × n)
- **Espaço:** O(n) otimizado (ou O(m × n) para 2D)

## Implementação

A solução está em `Solution.java` com duas abordagens (1D e 2D) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```