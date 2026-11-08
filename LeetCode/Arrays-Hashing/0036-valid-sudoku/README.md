# 0036 - Valid Sudoku

## Descrição do Problema

Determine se um tabuleiro de Sudoku 9x9 é válido. Apenas as células preenchidas precisam ser validadas de acordo com as seguintes regras:

1. Cada linha deve conter os dígitos 1-9 sem repetição.
2. Cada coluna deve conter os dígitos 1-9 sem repetição.
3. Cada um dos nove sub-quadrados 3x3 deve conter os dígitos 1-9 sem repetição.

**Nota:**
- Um tabuleiro de Sudoku (parcialmente preenchido) pode ser válido, mas não necessariamente solucionável.
- Apenas as células preenchidas precisam ser validadas.

## Exemplos

### Exemplo 1:
```
Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: true
```

### Exemplo 2:
```
Input: board = 
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: false
Explanation: O mesmo que o Exemplo 1, exceto que o 5 no canto superior esquerdo foi modificado para 8. Como há dois 8's na primeira linha, é inválido.
```

## Restrições

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` é um dígito 1-9 ou '.'

## Abordagem

### Hash Sets para Linhas, Colunas e Caixas

A ideia principal é usar três arrays de HashSets para rastrear os dígitos já vistos:
- `rows[9]`: Conjuntos para cada linha
- `cols[9]`: Conjuntos para cada coluna
- `boxes[9]`: Conjuntos para cada sub-quadrado 3x3

Para cada célula preenchida (não '.'), verificamos se o dígito já existe no conjunto correspondente da linha, coluna ou caixa. Se existir, o tabuleiro é inválido.

O índice da caixa 3x3 pode ser calculado como: `(row / 3) * 3 + (col / 3)`

### Complexidade

- **Tempo:** O(1) - O tabuleiro é sempre 9x9 (81 células), então é tempo constante
- **Espaço:** O(1) - Os conjuntos têm tamanho máximo fixo (9 elementos cada, 27 conjuntos total)

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```