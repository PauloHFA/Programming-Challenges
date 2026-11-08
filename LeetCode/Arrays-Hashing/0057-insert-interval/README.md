# 0057 - Insert Interval

## Descrição do Problema

Você recebe um array de intervalos não sobrepostos `intervals` onde `intervals[i] = [start_i, end_i]` representam o início e o fim do i-ésimo intervalo e `intervals` está ordenado em ordem crescente por `start_i`. Você também recebe um intervalo `newInterval = [start, end]` que representa o início e o fim de outro intervalo.

Insira `newInterval` em `intervals` de forma que `intervals` ainda esteja ordenado em ordem crescente por `start_i` e `intervals` ainda não tenha intervalos sobrepostos (mescle intervalos sobrepostos se necessário).

Retorne `intervals` após a inserção.

## Exemplos

### Exemplo 1:
```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

### Exemplo 2:
```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: O novo intervalo [4,8] se sobrepõe com [3,5],[6,7],[8,10].
```

## Restrições

- `0 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= start_i <= end_i <= 10^5`
- `intervals` está ordenado por `start_i` em ordem crescente.
- `newInterval.length == 2`
- `0 <= start <= end <= 10^5`

## Abordagem

### Três Fases (O(n) tempo, O(n) espaço)

Como `intervals` já está ordenado, podemos processar em uma única passagem:

**Fase 1: Adicionar intervalos antes do newInterval**
- Enquanto `intervals[i][1] < newInterval[0]`: não há sobreposição, adicionar ao resultado

**Fase 2: Mesclar intervalos sobrepostos**
- Enquanto `intervals[i][0] <= newInterval[1]`: há sobreposição
- Atualizar `start = min(start, intervals[i][0])` e `end = max(end, intervals[i][1])`
- Adicionar intervalo mesclado ao resultado

**Fase 3: Adicionar intervalos restantes**
- Adicionar todos os intervalos restantes

### Complexidade

- **Tempo:** O(n) - única passagem
- **Espaço:** O(n) para saída

### Abordagem Alternativa: Inserir + Mesclar

Inserir o newInterval na posição correta (mantendo ordenação) e depois aplicar a mesma lógica de mesclagem do problema 56. Incluída no código para comparação.

## Implementação

A solução está em `Solution.java` com duas abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```