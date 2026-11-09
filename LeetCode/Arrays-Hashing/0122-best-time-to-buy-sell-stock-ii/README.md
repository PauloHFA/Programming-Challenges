# 0122 - Best Time to Buy and Sell Stock II

## Descrição

Você recebe um array `prices` onde `prices[i]` é o preço de uma ação no dia `i`.

Em cada dia, você pode decidir comprar e/ou vender a ação. Você só pode manter **no máximo uma ação por vez**. No entanto, você pode comprar e vender no mesmo dia.

Encontre e retorne o lucro máximo que você pode alcançar.

## Exemplos

**Exemplo 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 7
Explicação: Compre no dia 2 (preço = 1) e venda no dia 3 (preço = 5), lucro = 4.
           Compre no dia 4 (preço = 3) e venda no dia 5 (preço = 6), lucro = 3.
           Total = 7.
```

**Exemplo 2:**
```
Input: prices = [1,2,3,4,5]
Output: 4
Explicação: Compre no dia 1 (preço = 1) e venda no dia 5 (preço = 5), lucro = 4.
           Ou some todas as diferenças positivas consecutivas.
```

**Exemplo 3:**
```
Input: prices = [7,6,4,3,1]
Output: 0
```

## Restrições

- `1 <= prices.length <= 3 * 10^4`
- `0 <= prices[i] <= 10^4`

## Abordagem

### Método 1: Greedy - Soma de Subidas (Principal)
- Soma todos os lucros positivos entre dias consecutivos
- Equivalente a comprar em cada vale e vender em cada pico
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Peak Valley Approach
- Encontra explicitamente vales (mínimos locais) e picos (máximos locais)
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: DP com Estados
- `hold` = max profit com ação na mão
- `cash` = max profit sem ação
- Permite múltiplas transações
- **Tempo:** O(n), **Espaço:** O(1)

### Método 4: One Pass Explícito
- Mantém preço de compra atual, acumula lucro quando sobe
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Greedy | O(n) | O(1) |
| Peak Valley | O(n) | O(1) |
| DP | O(n) | O(1) |
| One Pass | O(n) | O(1) |

## Implementação

A solução principal usa **Greedy - Soma de Subidas**.

```java
public int maxProfit(int[] prices) {
    int maxProfit = 0;
    
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            maxProfit += prices[i] - prices[i - 1];
        }
    }
    
    return maxProfit;
}
```

## Casos de Teste

- `[7,1,5,3,6,4]` → `7`
- `[1,2,3,4,5]` → `4`
- `[7,6,4,3,1]` → `0`
- `[5]` → `0`
- `[1,2]` → `1`
- `[2,1]` → `0`
- `[1,3,2,4,3,5]` → `6`
- `[5,5,5,5]` → `0`
- `[3,2,6,5,0,3]` → `7`
- `[2,4,1,5,3,6]` → `9`