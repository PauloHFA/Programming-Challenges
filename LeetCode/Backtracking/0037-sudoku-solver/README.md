# 0037 - Sudoku Solver

## Descrição do Problema

Escreva um programa para resolver um tabuleiro de Sudoku preenchendo as células vazias.

Uma solução de Sudoku deve satisfazer **todas as seguintes regras**:

1. Cada um dos dígitos 1-9 deve ocorrer exatamente uma vez em cada linha.
2. Cada um dos dígitos 1-9 deve ocorrer exatamente uma vez em cada coluna.
3. Cada um dos dígitos 1-9 deve ocorrer exatamente uma vez em cada um dos 9 sub-quadrados 3x3 da grade.

O caractere `'.'` indica células vazias.

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

Output: 
[["5","3","4","6","7","8","9","1","2"]
,["6","7","2","1","9","5","3","4","8"]
,["1","9","8","3","4","2","5","6","7"]
,["8","5","9","7","6","1","4","2","3"]
,["4","2","6","8","5","3","7","9","1"]
,["7","1","3","9","2","4","8","5","6"]
,["9","6","1","5","3","7","2","8","4"]
,["2","8","7","4","1","9","6","3","5"]]
```

## Restrições

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` é um dígito ou `'.'`
- É garantido que o tabuleiro de entrada tem apenas uma solução.

## Abordagem

### Backtracking com Hash Sets

A abordagem clássica para resolver Sudoku é o **backtracking** (retrocesso):

1. **Pré-processamento**: Inicializamos três arrays de HashSets para rastrear dígitos já usados em cada linha, coluna e caixa 3x3.

2. **Busca recursiva**: Percorremos o tabuleiro célula por célula:
   - Se a célula já está preenchida, avançamos para a próxima.
   - Se está vazia, tentamos dígitos de '1' a '9':
     - Verificamos se o dígito é válido (não está na linha, coluna ou caixa correspondente).
     - Se válido, colocamos o dígito e atualizamos os conjuntos.
     - Recursivamente tentamos resolver o resto do tabuleiro.
     - Se a recursão retorna `true`, encontramos a solução.
     - Caso contrário, fazemos **backtracking**: removemos o dígito e tentamos o próximo.

3. **Condição de parada**: Quando `row == 9`, todas as células foram preenchidas com sucesso.

### Otimizações

- **Hash Sets O(1)**: Verificação de validade em tempo constante.
- **Ordem de preenchimento**: Preenchemos da esquerda para direita, cima para baixo.
- **Podas**: Só tentamos dígitos válidos, evitando ramificações inválidas.

### Complexidade

- **Tempo:** O(9^(N*N)) no pior caso, mas na prática muito mais rápido devido às restrições do Sudoku. Para tabuleiros 9x9 padrão, resolve em milissegundos.
- **Espaço:** O(N*N) para a pilha de recursão (profundidade máxima 81) + O(N*N) para os conjuntos.

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```