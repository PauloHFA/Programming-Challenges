# 0054 - Spiral Matrix

## Descrição do Problema

Dada uma matriz `m x n` `matrix`, retorne todos os elementos da matriz em **ordem espiral**.

## Exemplos

### Exemplo 1:
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

### Exemplo 2:
```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

## Restrições

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 10`
- `-100 <= matrix[i][j] <= 100`

## Abordagem

### Travessia por Limites (Layer by Layer)

Mantemos quatro limites: `top`, `bottom`, `left`, `right`.

**Algoritmo:**
1. Percorrer linha superior (esquerda → direita), depois `top++`
2. Percorrer coluna direita (topo → base), depois `right--`
3. Se `top <= bottom`: percorrer linha inferior (direita → esquerda), depois `bottom--`
4. Se `left <= right`: percorrer coluna esquerda (base → topo), depois `left++`
5. Repetir enquanto `top <= bottom && left <= right`

**Exemplo visual (3x3):**
```
Limites iniciais: top=0, bottom=2, left=0, right=2

Passo 1: top row    → 1, 2, 3          top=1
Passo 2: right col  → 6, 9             right=1
Passo 3: bottom row → 8, 7             bottom=1
Passo 4: left col   → 4                left=1
Passo 5: top row    → 5                top=2 (loop ends)
```

### Abordagem Alternativa: Baseada em Direções

Simula o movimento com vetores de direção (direita, baixo, esquerda, cima) e matriz `visited`.

### Complexidade

- **Tempo:** O(m × n) - visitamos cada elemento exatamente uma vez
- **Espaço:** O(1) excluindo saída (ou O(m × n) para abordagem com visited)

## Implementação

A solução está em `Solution.java` com duas abordagens (limites e direções) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```