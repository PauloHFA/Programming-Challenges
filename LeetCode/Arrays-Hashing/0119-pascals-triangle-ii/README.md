# 0119 - Pascal's Triangle II

## Descrição

Dado um inteiro `rowIndex`, retorne a linha `rowIndex` (0-indexada) do triângulo de Pascal.

No triângulo de Pascal, cada número é a soma dos dois números diretamente acima dele.

## Exemplos

**Exemplo 1:**
```
Input: rowIndex = 3
Output: [1,3,3,1]
```

**Exemplo 2:**
```
Input: rowIndex = 0
Output: [1]
```

**Exemplo 3:**
```
Input: rowIndex = 1
Output: [1,1]
```

## Restrições

- `0 <= rowIndex <= 33`

## Abordagem

### Método 1: Iterativo O(k) espaço (Principal)
- Constrói a linha iterativamente usando apenas O(k) espaço extra
- Atualiza de trás para frente para não sobrescrever valores necessários
- **Tempo:** O(k²), **Espaço:** O(k)

### Método 2: Fórmula Combinatória C(n, k)
- Calcula diretamente cada elemento usando C(rowIndex, k)
- Usa relação de recorrência: C(n, k+1) = C(n, k) * (n-k) / (k+1)
- **Tempo:** O(k), **Espaço:** O(k)

### Método 3: Triângulo Completo
- Gera todo o triângulo até rowIndex
- **Tempo:** O(k²), **Espaço:** O(k²)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Iterativo O(k) | O(k²) | O(k) |
| Combinatório | O(k) | O(k) |
| Triângulo Completo | O(k²) | O(k²) |

## Implementação

A solução principal usa **Iterativo O(k) espaço**.

```java
public List<Integer> getRow(int rowIndex) {
    List<Integer> row = new ArrayList<>(rowIndex + 1);
    
    for (int i = 0; i <= rowIndex; i++) {
        row.add(1);
        for (int j = i - 1; j > 0; j--) {
            row.set(j, row.get(j) + row.get(j - 1));
        }
    }
    
    return row;
}
```

## Casos de Teste

- `rowIndex = 3` → `[1, 3, 3, 1]`
- `rowIndex = 0` → `[1]`
- `rowIndex = 1` → `[1, 1]`
- `rowIndex = 5` → `[1, 5, 10, 10, 5, 1]`
- `rowIndex = 10` → `[1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1]`
- Soma da linha = 2^rowIndex
- Linha é simétrica