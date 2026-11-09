# 0121 - Best Time to Buy and Sell Stock

## Descrição

Você recebe um array `prices` onde `prices[i]` é o preço de uma ação no dia `i`.

Você quer maximizar seu lucro escolhendo um **único dia** para comprar uma ação e escolhendo um **dia diferente no futuro** para vender essa ação.

Retorne o lucro máximo que você pode alcançar. Se não puder obter lucro, retorne 0.

## Exemplos

**Exemplo 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explicação: Compre no dia 2 (preço = 1) e venda no dia 5 (preço = 6), lucro = 6-1 = 5.
```

**Exemplo 2:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explicação: Nenhuma transação é feita, lucro máximo = 0.
```

## Restrições

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

## Abordagem

### Método 1: One Pass - Min Price Tracking (Principal)
- Mantém o preço mínimo visto até agora
- Calcula lucro potencial a cada passo
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Kadane's Algorithm (Diferenças)
- Transforma em problema de subarray máximo das diferenças consecutivas
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Two Pointers
- Left = dia de compra, Right = dia de venda
- Move left quando preço cai
- **Tempo:** O(n), **Espaço:** O(1)

### Método 4: DP com Estados
- `hold` = max profit com ação na mão
- `cash` = max profit sem ação
- Generalizável para k transações
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| One Pass | O(n) | O(1) |
| Kadane | O(n) | O(1) |
| Two Pointers | O(n) | O(1) |
| DP | O(n) | O(1) |

## Implementação

A solução principal usa **One Pass - Min Price Tracking**.

```java
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    
    for (int price : prices) {
        if (price < minPrice) {
            minPrice = price;
        } else {
            int profit = price - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }
    }
    
    return maxProfit;
}
```

## Casos de Teste

- `[7,1,5,3,6,4]` → `5`
- `[7,6,4,3,1]` → `0`
- `[5]` → `0`
- `[1,2]` → `1`
- `[2,1]` → `0`
- `[5,5,5,5]` → `0`
- `[1,2,3,4,5]` → `4`
- `[5,4,3,2,1]` → `0`
- `[3,2,6,5,0,3]` → `4` (buy at 0, sell at 3? No, buy at 2 sell at 6 = 4)
- `[2,4,1]` → `2`
- `[1,2,4,2,5,7,2,4,9,0]` → `8`