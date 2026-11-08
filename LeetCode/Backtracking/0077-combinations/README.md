# 0077 - Combinations

## Descrição

Dados dois inteiros `n` e `k`, retorne todas as combinações possíveis de `k` números escolhidos do intervalo `[1, n]`.

Você pode retornar a resposta em qualquer ordem.

## Exemplos

**Exemplo 1:**
```
Input: n = 4, k = 2
Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
Explanation: Existem 6 = 4!/(2!2!) combinações.
```

**Exemplo 2:**
```
Input: n = 1, k = 1
Output: [[1]]
```

## Restrições

- `1 <= n <= 20`
- `1 <= k <= n`

## Abordagem

### Método 1: Backtracking (Principal)
- Constrói combinações recursivamente
- Poda: se elementos restantes < necessários, retorna
- **Tempo:** O(C(n,k) * k), **Espaço:** O(k) para recursão + O(C(n,k) * k) para resultado

### Método 2: Iterativo
- Gera próxima combinação lexicograficamente
- Usa sentinela para detectar fim
- **Tempo:** O(C(n,k) * k), **Espaço:** O(k)

### Método 3: Bitmask (Para n pequeno)
- Itera sobre todos os 2^n submasks
- Seleciona os com exatamente k bits setados
- **Tempo:** O(n * 2^n), **Espaço:** O(C(n,k) * k)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Backtracking | O(C(n,k) * k) | O(k) + resultado |
| Iterativo | O(C(n,k) * k) | O(k) |
| Bitmask | O(n * 2^n) | O(C(n,k) * k) |

## Implementação

A solução principal usa **Backtracking** com poda.

```java
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), 1, n, k);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current, int start, int n, int k) {
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Pruning
    if (n - start + 1 < k - current.size()) {
        return;
    }
    
    for (int i = start; i <= n; i++) {
        current.add(i);
        backtrack(result, current, i + 1, n, k);
        current.remove(current.size() - 1);
    }
}
```

## Casos de Teste

- `n=4, k=2` → `[[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]` (6 combinações)
- `n=1, k=1` → `[[1]]` (1 combinação)
- `n=4, k=3` → `[[1,2,3],[1,2,4],[1,3,4],[2,3,4]]` (4 combinações)
- `n=5, k=2` → 10 combinações
- `n=3, k=3` → `[[1,2,3]]` (1 combinação)
- `n=4, k=1` → `[[1],[2],[3],[4]]` (4 combinações)
- `n=4, k=4` → `[[1,2,3,4]]` (1 combinação)