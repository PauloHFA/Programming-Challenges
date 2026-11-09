# 0123 - Best Time to Buy and Sell Stock III

## Descrição

Você recebe um array `prices` onde `prices[i]` é o preço de uma ação no dia `i`.

Encontre o lucro máximo que você pode alcançar. Você pode completar **no máximo duas transações**.

Nota: Você não pode participar de múltiplas transações simultaneamente (você deve vender a ação antes de comprar novamente).

## Exemplos

**Exemplo 1:**
```
Input: prices = [3,3,5,0,0,3,1,4]
Output: 6
Explicação: Compre no dia 4 (preço = 0) e venda no dia 6 (preço = 3), lucro = 3.
           Compre no dia 7 (preço = 1) e venda no dia 8 (preço = 4), lucro = 3.
           Total = 6.
```

**Exemplo 2:**
```
Input: prices = [1,2,3,4,5]
Output: 4
Explicação: Compre no dia 1 (preço = 1) e venda no dia 5 (preço = 5), lucro = 4.
           Apenas 1 transação é necessária.
```

**Exemplo 3:**
```
Input: prices = [7,6,4,3,1]
Output: 0
```

## Restrições

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^5`

## Abordagem

### Método 1: DP com 4 Variáveis (Principal)
- `buy1` = max profit após 1ª compra (negativo = gasto)
- `sell1` = max profit após 1ª venda
- `buy2` = max profit após 2ª compra (usa lucro da 1ª venda)
- `sell2` = max profit após 2ª venda (resposta final)
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: DP Array 3D (k=2)
- `dp[k][0]` = max profit com k transações, sem ação
- `dp[k][1]` = max profit com k transações, com ação
- **Tempo:** O(n*k), **Espaço:** O(k)

### Método 3: Divide and Conquer
- `left[i]` = max profit em prices[0..i] com 1 transação
- `right[i]` = max profit em prices[i..n-1] com 1 transação
- Combina: max(left[i] + right[i])
- **Tempo:** O(n), **Espaço:** O(n)

### Método 4: DP 2D Completo
- `dp[j][i]` = max profit até dia i com j transações
- Otimização com `maxDiff`
- **Tempo:** O(n*k), **Espaço:** O(n*k)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| 4 Variáveis | O(n) | O(1) |
| DP Array | O(n) | O(1) |
| Divide & Conquer | O(n) | O(n) |
| DP 2D | O(n) | O(n) |

## Implementação

A solução principal usa **DP com 4 Variáveis**.

```java
public int maxProfit(int[] prices) {
    int buy1 = Integer.MIN_VALUE, sell1 = 0;
    int buy2 = Integer.MIN_VALUE, sell2 = 0;
    
    for (int price : prices) {
        buy1 = Math.max(buy1, -price);
        sell1 = Math.max(sell1, buy1 + price);
        buy2 = Math.max(buy2, sell1 - price);
        sell2 = Math.max(sell2, buy2 + price);
    }
    
    return sell2;
}
```

## Casos de Teste

- `[3,3,5,0,0,3,1,4]` → `6`
- `[1,2,3,4,5]` → `4`
- `[7,6,4,3,1]` → `0`
- `[5]` → `0`
- `[1,2]` → `1`
- `[1,3,2,4]` → `4`
- `[3,2,6,5,0,3]` → `7`
- `[5,5,5,5]` → `0`
- `[2,4,1,5,3,6]` → `7`
- `[1,2,4,2,5,7,2,4,9,0]` → `13`