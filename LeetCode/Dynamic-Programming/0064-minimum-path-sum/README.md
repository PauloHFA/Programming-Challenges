# 0064 - Minimum Path Sum

## Descrição do Problema

Dada uma grade `m x n` preenchida com números não negativos, encontre um caminho do canto superior esquerdo ao canto inferior direito que **minimize a soma** de todos os números ao longo do caminho.

Você só pode mover **para baixo** ou **para a direita** a qualquer momento.

## Exemplos

### Exemplo 1:
```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: O caminho 1→3→1→1→1 tem soma mínima 7.
```

### Exemplo 2:
```
Input: grid = [[1,2,3],[4,5,6]]
Output: 12
```

## Restrições

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 200`
- `0 <= grid[i][j] <= 200`

## Abordagem

### Programação Dinâmica (Otimizada para O(n) espaço)

**Estado:** `dp[j]` = soma mínima para chegar à célula na linha atual, coluna `j`.

**Transição:**
```
dp[j] = min(dp[j], dp[j-1]) + grid[i][j]
```
(Onde `dp[j]` antes da atualização = soma de cima, `dp[j-1]` = soma da esquerda)

**Inicialização:**
- `dp[0] = grid[0][0]`
- Primeira linha: `dp[j] = dp[j-1] + grid[0][j]`
- Para cada nova linha: `dp[0] += grid[i][0]` (primeira coluna)

### Abordagens Alternativas

1. **2D DP** (O(m×n) espaço): `dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]`
2. **In-place** (O(1) espaço extra): Modifica a grid original diretamente

### Complexidade

- **Tempo:** O(m × n)
- **Espaço:** O(n) otimizado (O(1) in-place, O(m×n) 2D)

## Implementação

A solução está em `Solution.java` com três abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```