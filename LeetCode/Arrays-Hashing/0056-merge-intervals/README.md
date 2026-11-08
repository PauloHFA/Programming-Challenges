# 0056 - Merge Intervals

## Descrição do Problema

Dado um array de `intervalos` onde `intervalos[i] = [start_i, end_i]`, mescle todos os intervalos sobrepostos e retorne um array dos intervalos não sobrepostos que cobrem todos os intervalos na entrada.

## Exemplos

### Exemplo 1:
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Como os intervalos [1,3] e [2,6] se sobrepõem, mescle-os em [1,6].
```

### Exemplo 2:
```
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervalos [1,4] e [4,5] são considerados sobrepostos.
```

## Restrições

- `1 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= start_i <= end_i <= 10^4`

## Abordagem

### Ordenação + Mesclagem Linear

A ideia principal: **se ordenarmos por tempo de início, intervalos sobrepostos ficarão adjacentes**.

**Algoritmo:**
1. Ordenar `intervals` por `start` (tempo de início)
2. Inicializar `current` com o primeiro intervalo
3. Para cada intervalo seguinte:
   - Se `interval[0] <= current[1]`: **sobrepõe** → mesclar (`current[1] = max(current[1], interval[1])`)
   - Senão: **não sobrepõe** → adicionar `current` ao resultado, `current = interval`
4. Adicionar o último `current` ao resultado

**Por que funciona:** Após ordenar, se o intervalo atual não sobrepõe com o anterior, ele não sobreporá com nenhum anterior (pois todos têm `start` menor ou igual).

### Complexidade

- **Tempo:** O(n log n) - dominado pela ordenação
- **Espaço:** O(n) para saída (ou O(1) excluindo saída)

## Implementação

A solução está em `Solution.java` com duas abordagens (modificando in-place e explícita) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```