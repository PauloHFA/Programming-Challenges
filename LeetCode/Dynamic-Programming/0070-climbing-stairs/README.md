# 0070 - Climbing Stairs

## Descrição

Você está subindo uma escada. Leva `n` degraus para chegar ao topo.

Cada vez, você pode subir 1 ou 2 degraus. De quantas maneiras distintas você pode subir ao topo?

## Exemplos

**Exemplo 1:**
```
Input: n = 2
Output: 2
Explanation: Existem duas maneiras de subir ao topo.
1. 1 degrau + 1 degrau
2. 2 degraus
```

**Exemplo 2:**
```
Input: n = 3
Output: 3
Explanation: Existem três maneiras de subir ao topo.
1. 1 degrau + 1 degrau + 1 degrau
2. 1 degrau + 2 degraus
3. 2 degraus + 1 degrau
```

## Restrições

- `1 <= n <= 45`

## Abordagem

Este problema é essencialmente a sequência de Fibonacci:
- `dp[n] = dp[n-1] + dp[n-2]`
- Casos base: `dp[1] = 1`, `dp[2] = 2`

### Método 1: DP com O(1) Espaço (Principal)
- Mantém apenas os dois valores anteriores
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: DP Array (O(n) Espaço)
- Array completo para visualização
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Exponenciação de Matriz (O(log n))
- Usa a matriz de Fibonacci `[[1,1],[1,0]]`
- **Tempo:** O(log n), **Espaço:** O(1)

### Método 4: Fórmula de Binet (Forma Fechada)
- Fórmula direta usando número áureo
- **Tempo:** O(1), **Espaço:** O(1)
- Pode ter problemas de precisão para n grande

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| O(1) DP | O(n) | O(1) |
| DP Array | O(n) | O(n) |
| Matriz | O(log n) | O(1) |
| Binet | O(1) | O(1) |

## Implementação

A solução principal usa **DP com O(1) espaço**.

```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    
    int prev2 = 1; // dp[i-2]
    int prev1 = 2; // dp[i-1]
    
    for (int i = 3; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

## Casos de Teste

- `n = 1` → `1`
- `n = 2` → `2`
- `n = 3` → `3`
- `n = 4` → `5`
- `n = 5` → `8`
- `n = 6` → `13`
- `n = 7` → `21`
- `n = 8` → `34`
- `n = 9` → `55`
- `n = 10` → `89`
- `n = 20` → `10946`
- `n = 30` → `1346269`
- `n = 40` → `165580141`
- `n = 45` → `1836311903`