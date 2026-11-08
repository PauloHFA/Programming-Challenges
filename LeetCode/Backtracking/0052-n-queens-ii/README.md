# 0052 - N-Queens II

## Descrição do Problema

O quebra-cabeça **N-Queens** é o problema de colocar `n` rainhas em um tabuleiro de xadrez `n x n` de forma que **nenhuma duas rainhas se ataquem**.

Dado um inteiro `n`, retorne o **número de soluções distintas** para o quebra-cabeça N-Queens.

## Exemplos

### Exemplo 1:
```
Input: n = 4
Output: 2
Explanation: Existem duas soluções distintas para o quebra-cabeça 4-queens.
```

### Exemplo 2:
```
Input: n = 1
Output: 1
```

## Restrições

- `1 <= n <= 9`

## Abordagem

### Backtracking com Bitmasks (Otimizado)

Esta é a versão otimizada do problema 0051 que conta apenas o número de soluções sem armazenar os tabuleiros.

**Bitmasks:**
- `cols`: bits representam colunas ocupadas (bit i = 1 significa coluna i ocupada)
- `diag1`: bits representam diagonais principais ocupadas (row + col)
- `diag2`: bits representam diagonais secundárias ocupadas (row - col)

**Transições de estado:**
- Ao colocar rainha na coluna `c` na linha `row`:
  - `cols` → `cols | (1 << c)`
  - `diag1` → `(diag1 | (1 << c)) << 1` (desloca para próxima linha)
  - `diag2` → `(diag2 | (1 << c)) >> 1` (desloca para próxima linha)

**Posições disponíveis:**
```java
int available = (~(cols | diag1 | diag2)) & ((1 << n) - 1);
```

**Extrair posição mais à direita:**
```java
int position = available & -available;
available &= available - 1; // Remove o bit usado
```

### Complexidade

- **Tempo:** O(N!) - mesmo do backtracking padrão, mas com constante menor
- **Espaço:** O(N) para pilha de recursão

### Abordagem Alternativa: Arrays Booleanos

Incluída no código para comparação - mais fácil de entender, mesma complexidade assintótica.

## Implementação

A solução está em `Solution.java` com ambas as abordagens (bitmask e arrays) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```