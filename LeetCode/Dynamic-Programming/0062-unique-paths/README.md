# 0062 - Unique Paths

## Descrição do Problema

Um robô está localizado no canto superior esquerdo de uma grade `m x n` (marcado como 'Início' no diagrama abaixo).

O robô só pode se mover **para baixo** ou **para a direita** a qualquer momento. O robô está tentando chegar ao canto inferior direito da grade (marcado como 'Fim').

Quantos caminhos únicos existem?

## Exemplos

### Exemplo 1:
```
Input: m = 3, n = 7
Output: 28
```

### Exemplo 2:
```
Input: m = 3, n = 2
Output: 3
Explanation:
Do canto superior esquerdo, há um total de 3 maneiras de chegar ao canto inferior direito:
1. Direita -> Direita -> Baixo
2. Direita -> Baixo -> Direita
3. Baixo -> Direita -> Direita
```

## Restrições

- `1 <= m, n <= 100`
- A resposta será menor ou igual a `2 * 10^9`.

## Abordagem

### Programação Dinâmica (Otimizada para O(n) espaço)

**Estado:** `dp[j]` = número de caminhos para chegar à célula na linha atual, coluna `j`.

**Transição:** Para chegar à célula `(i, j)`, podemos vir de cima `(i-1, j)` ou da esquerda `(i, j-1)`:
```
dp[j] = dp[j] + dp[j-1]
```
(Onde `dp[j]` antes da atualização representa o valor de cima, e `dp[j-1]` representa o da esquerda)

**Inicialização:** Primeira linha é toda 1s (só pode ir para a direita).

### Abordagens Alternativas

1. **2D DP** (O(m×n) espaço): Mais intuitivo, `dp[i][j] = dp[i-1][j] + dp[i][j-1]`
2. **Matemática (Combinatória)** (O(min(m,n)) tempo): 
   - Total de passos = (m-1) + (n-1) = m+n-2
   - Escolher (m-1) movimentos para baixo: C(m+n-2, m-1)

### Complexidade

- **DP 1D:** Tempo O(m×n), Espaço O(n) ⭐ **Recomendado**
- **DP 2D:** Tempo O(m×n), Espaço O(m×n)
- **Matemática:** Tempo O(min(m,n)), Espaço O(1)

## Implementação

A solução está em `Solution.java` com três abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```