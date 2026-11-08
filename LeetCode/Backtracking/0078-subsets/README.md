# 0078 - Subsets

## Descrição

Dado um array de inteiros `nums` com elementos únicos, retorne todos os subconjuntos possíveis (o conjunto das partes).

A solução não deve conter subconjuntos duplicados. Retorne a solução em qualquer ordem.

## Exemplos

**Exemplo 1:**
```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Exemplo 2:**
```
Input: nums = [0]
Output: [[],[0]]
```

## Restrições

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- Todos os números de `nums` são únicos

## Abordagem

### Método 1: Backtracking (Principal)
- Para cada elemento, decide incluir ou não
- Adiciona o subconjunto atual a cada passo
- **Tempo:** O(n * 2^n), **Espaço:** O(n) para recursão + O(n * 2^n) para resultado

### Método 2: Iterativo
- Começa com conjunto vazio
- Para cada número, adiciona ele a todos os subconjuntos existentes
- **Tempo:** O(n * 2^n), **Espaço:** O(n * 2^n)

### Método 3: Bitmask
- Cada subconjunto corresponde a um número de 0 a 2^n - 1
- Bits indicam quais elementos incluir
- **Tempo:** O(n * 2^n), **Espaço:** O(n * 2^n)

### Método 4: BFS
- Similar ao iterativo, mas mais explícito
- **Tempo:** O(n * 2^n), **Espaço:** O(n * 2^n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Backtracking | O(n * 2^n) | O(n) + resultado |
| Iterativo | O(n * 2^n) | O(n * 2^n) |
| Bitmask | O(n * 2^n) | O(n * 2^n) |
| BFS | O(n * 2^n) | O(n * 2^n) |

## Implementação

A solução principal usa **Backtracking**.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
    result.add(new ArrayList<>(current));
    
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);
        backtrack(result, current, nums, i + 1);
        current.remove(current.size() - 1);
    }
}
```

## Casos de Teste

- `[1,2,3]` → 8 subconjuntos
- `[0]` → 2 subconjuntos
- `[1,2]` → 4 subconjuntos
- `[1,2,3,4]` → 16 subconjuntos
- `[-1,0,1]` → 8 subconjuntos