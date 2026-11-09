# 0134 - Gas Station

## Descrição

Existem `n` postos de gasolina em um circuito circular. Você tem um carro com tanque de gasolina ilimitado e custa `cost[i]` de gasolina para ir do posto `i` para o próximo posto `(i+1)`. Você começa com tanque vazio em um dos postos.

Dado dois arrays inteiros `gas` e `cost`, retorne o índice do posto de partida se você puder viajar ao redor do circuito uma vez no sentido horário, caso contrário retorne `-1`. Se existe solução, é garantido que é única.

## Exemplos

**Exemplo 1:**
```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explicação: Começa no posto 3, tanque = 4 -> 8 -> 7 -> 6 -> 5 -> 0 (completa)
```

**Exemplo 2:**
```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explicação: Total gas = 9, total cost = 10. Impossível completar.
```

## Restrições

- `n == gas.length == cost.length`
- `1 <= n <= 10^5`
- `0 <= gas[i], cost[i] <= 10^4`

## Abordagem

### Método 1: Greedy One Pass (Principal)
- Se `total gas >= total cost`, existe solução única
- Mantém `currTank` (tanque atual), se fica negativo, reinicia do próximo posto
- `start` é o candidato a posto inicial
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Two Pass
- Primeiro verifica se `total gas >= total cost`
- Depois encontra o `start` com mesma lógica
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Força Bruta (Referência)
- Tenta cada posto como início
- **Tempo:** O(n²), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Greedy One Pass | O(n) | O(1) |
| Two Pass | O(n) | O(1) |
| Brute Force | O(n²) | O(1) |

## Implementação

A solução principal usa **Greedy One Pass**.

```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalTank = 0;
    int currTank = 0;
    int start = 0;
    
    for (int i = 0; i < gas.length; i++) {
        int diff = gas[i] - cost[i];
        totalTank += diff;
        currTank += diff;
        
        if (currTank < 0) {
            start = i + 1;
            currTank = 0;
        }
    }
    
    return totalTank >= 0 ? start : -1;
}
```

## Casos de Teste

- `[1,2,3,4,5], [3,4,5,1,2]` → `3`
- `[2,3,4], [3,4,3]` → `-1`
- `[5], [4]` → `0`
- `[3], [4]` → `-1`
- `[2,2,2], [2,2,2]` → `0`
- `[5,1,2,3,4], [4,4,1,5,1]` → `4`