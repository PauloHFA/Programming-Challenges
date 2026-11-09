# 0118 - Pascal's Triangle

## Descrição

Dado um inteiro `numRows`, retorne as primeiras `numRows` linhas do triângulo de Pascal.

No triângulo de Pascal, cada número é a soma dos dois números diretamente acima dele.

## Exemplos

**Exemplo 1:**
```
Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
```

**Exemplo 2:**
```
Input: numRows = 1
Output: [[1]]
```

## Restrições

- `0 <= numRows <= 30`

## Abordagem

### Método 1: Iterativo (Principal)
- Constrói cada linha baseada na linha anterior
- Primeiro e último elementos são sempre 1
- Elementos do meio = soma dos dois elementos acima
- **Tempo:** O(n²), **Espaço:** O(n²) para output

### Método 2: Fórmula Combinatória
- Cada elemento é C(row, col) = row! / (col! * (row-col)!)
- Usa relação de recorrência: C(n, k+1) = C(n, k) * (n-k) / (k+1)
- **Tempo:** O(n²), **Espaço:** O(n²)

### Método 3: In-Place com linha anterior
- Similar ao método 1 mas referencia explicitamente a linha anterior
- **Tempo:** O(n²), **Espaço:** O(n²)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Iterativo | O(n²) | O(n²) |
| Combinatório | O(n²) | O(n²) |
| In-Place | O(n²) | O(n²) |

## Implementação

A solução principal usa **Iterativo**.

```java
public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < numRows; i++) {
        List<Integer> row = new ArrayList<>(i + 1);
        row.add(1);
        
        for (int j = 1; j < i; j++) {
            int val = result.get(i - 1).get(j - 1) + result.get(i - 1).get(j);
            row.add(val);
        }
        
        if (i > 0) {
            row.add(1);
        }
        
        result.add(row);
    }
    
    return result;
}
```

## Casos de Teste

- `numRows = 5` → `[[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]`
- `numRows = 1` → `[[1]]`
- `numRows = 0` → `[]`
- `numRows = 6` → inclui `[1,5,10,10,5,1]`
- Soma de cada linha = 2^row