# 0048 - Rotate Image

## Descrição do Problema

Você recebe uma matriz `n x n` representando uma imagem. Rotacione a imagem em **90 graus no sentido horário**.

Você deve rotacionar a imagem **in-place**, o que significa que você deve modificar a matriz de entrada diretamente. **Não aloque** outra matriz 2D e faça a rotação.

## Exemplos

### Exemplo 1:
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]
```

### Exemplo 2:
```
Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
```

## Restrições

- `n == matrix.length == matrix[i].length`
- `1 <= n <= 20`
- `-1000 <= matrix[i][j] <= 1000`

## Abordagem

### Transposta + Inversão de Linhas (In-place)

A rotação de 90° no sentido horário pode ser decomposta em duas operações simples:

1. **Transposta**: Trocar `matrix[i][j]` com `matrix[j][i]` para todo `i < j`
   - Isso reflete a matriz sobre a diagonal principal
2. **Inverter cada linha**: Reverter os elementos de cada linha
   - Isso completa a rotação de 90°

**Exemplo visual (3x3):**
```
Original:      Transposta:    Inverter linhas:
1 2 3          1 4 7          7 4 1
4 5 6    ->    2 5 8    ->    8 5 2
7 8 9          3 6 9          9 6 3
```

### Abordagem Alternativa: Camada por Camada

Rotacionamos anel por anel, movendo 4 elementos por vez:
- top → right → bottom → left → top

### Complexidade

- **Tempo:** O(n²) - visitamos cada elemento uma vez
- **Espaço:** O(1) - in-place

## Implementação

A solução está em `Solution.java` com duas abordagens (transposta+reverse e camadas) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```