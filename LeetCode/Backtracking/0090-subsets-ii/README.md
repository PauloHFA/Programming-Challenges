# 0090 - Subsets II

## Descrição

Dado um array de inteiros `nums` que **pode conter duplicatas**, retorne todos os subconjuntos possíveis (o conjunto das partes).

A solução não deve conter subconjuntos duplicados. Retorne a solução em qualquer ordem.

## Exemplos

**Exemplo 1:**
```
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
```

**Exemplo 2:**
```
Input: nums = [0]
Output: [[],[0]]
```

## Restrições

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`

## Abordagem

### Método 1: Backtracking com Skip de Duplicatas (Principal)
- Ordena o array primeiro para agrupar duplicatas
- No backtracking, pula `nums[i]` se `i > start && nums[i] == nums[i-1]`
- Isso garante que só usamos o primeiro elemento de uma sequência de duplicatas em cada nível
- **Tempo:** O(n × 2^n), **Espaço:** O(n) para recursion stack

### Método 2: Iterativo
- Similar ao método iterativo de subconjuntos padrão
- Mantém `start` e `end` para rastrear quais subconjuntos foram adicionados na iteração anterior
- Se elemento atual é duplicata, só estende os subconjuntos criados na iteração anterior
- **Tempo:** O(n × 2^n), **Espaço:** O(n × 2^n)

### Método 3: Bitmask com Filtro
- Gera todos os 2^n subconjuntos via bitmask
- Usa Set ou verificação manual para remover duplicatas
- **Tempo:** O(n × 2^n), **Espaço:** O(n × 2^n)

### Método 4: Bitmask com Set
- Usa `LinkedHashSet` para manter ordem e remover duplicatas automaticamente
- **Tempo:** O(n × 2^n), **Espaço:** O(n × 2^n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Backtracking | O(n × 2^n) | O(n) |
| Iterativo | O(n × 2^n) | O(n × 2^n) |
| Bitmask + Filtro | O(n × 2^n) | O(n × 2^n) |
| Bitmask + Set | O(n × 2^n) | O(n × 2^n) |

## Implementação

A solução principal usa **Backtracking com Skip de Duplicatas**.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);  // Importante: ordena para agrupar duplicatas
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current));
    
    for (int i = start; i < nums.length; i++) {
        // Skip duplicatas: só usa o primeiro de uma sequência de valores iguais
        if (i > start && nums[i] == nums[i - 1]) {
            continue;
        }
        current.add(nums[i]);
        backtrack(nums, i + 1, current, result);
        current.remove(current.size() - 1);
    }
}
```

## Casos de Teste

- `[1,2,2]` → `[[],[1],[1,2],[1,2,2],[2],[2,2]]` (6 subconjuntos)
- `[0]` → `[[],[0]]` (2 subconjuntos)
- `[1,1,1]` → `[[],[1],[1,1],[1,1,1]]` (4 subconjuntos)
- `[1,2,3]` → 8 subconjuntos (sem duplicatas = 2^3)
- `[1,1,2,2]` → 9 subconjuntos
- `[4,4,4,1,4]` → 10 subconjuntos
- `[1,1]` → `[[],[1],[1,1]]` (3 subconjuntos)
- `[1,2,3,3]` → 12 subconjuntos
- `[1,2,2,3,3,3]` → 24 subconjuntos