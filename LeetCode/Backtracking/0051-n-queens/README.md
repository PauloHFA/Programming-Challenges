# 0051 - N-Queens

## Descrição do Problema

O quebra-cabeça **N-Queens** é o problema de colocar `n` rainhas em um tabuleiro de xadrez `n x n` de forma que **nenhuma duas rainhas se ataquem**.

Dado um inteiro `n`, retorne todas as soluções distintas para o quebra-cabeça N-Queens. Você pode retornar a resposta em qualquer ordem.

Cada solução contém uma configuração distinta do tabuleiro, onde `'Q'` e `'.'` indicam uma rainha e um espaço vazio, respectivamente.

## Exemplos

### Exemplo 1:
```
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: Existem duas soluções distintas para o quebra-cabeça 4-queens.
```

### Exemplo 2:
```
Input: n = 1
Output: [["Q"]]
```

## Restrições

- `1 <= n <= 9`

## Abordagem

### Backtracking com Conjuntos para Colunas e Diagonais

A abordagem clássica usa backtracking linha por linha:

1. **Representação do tabuleiro**: Matriz `char[][]` de tamanho `n x n`, inicializada com `'.'`.
2. **Rastreamento de ataques**: Usamos três arrays booleanos:
   - `cols[n]`: colunas ocupadas
   - `diag1[2n-1]`: diagonais principais (row + col = constante)
   - `diag2[2n-1]`: diagonais secundárias (row - col + n - 1 = constante)
3. **Recursão**: Para cada linha, tentamos colocar uma rainha em cada coluna válida.
4. **Validação O(1)**: Verificamos se a coluna e diagonais estão livres em tempo constante.
5. **Backtracking**: Se chegamos à linha `n`, encontramos uma solução válida.

**Índices das diagonais:**
- Diagonal principal (↘): `row + col` varia de `0` a `2n-2`
- Diagonal secundária (↙): `row - col` varia de `-(n-1)` a `n-1`, deslocamos por `n-1` para índice não-negativo

### Complexidade

- **Tempo:** O(N!) - no pior caso, exploramos todas as permutações de colunas
- **Espaço:** O(N) para arrays de rastreamento + O(N) para pilha de recursão

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```