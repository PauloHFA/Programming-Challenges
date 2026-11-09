# LeetCode 188 - Best Time to Buy and Sell Stock IV

## Problema

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the ith day, and an integer `k`. Find the maximum profit you can achieve. You may complete at most `k` transactions.

Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

### Exemplo 1:
```
Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
```

### Exemplo 2:
```
Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Total profit = 4 + 3 = 7.
```

### Restrições:
- 1 <= k <= 100
- 1 <= prices.length <= 1000
- 0 <= prices[i] <= 1000

---

## Abordagens

### Abordagem 1: DP 2D (Padrão)
`dp[i][j]` = lucro máximo com no máximo i transações até o dia j. Otimização com `maxDiff` para evitar loop interno.
- **Complexidade**: O(k×n) tempo, O(k×n) espaço

### Abordagem 2: DP 1D (Espaço Otimizado)
Como só precisamos da linha anterior, usamos array 1D.
- **Complexidade**: O(k×n) tempo, O(n) espaço

### Abordagem 3: Arrays Buy/Sell (Formulaçăo Alternativa)
`buy[i]` = lucro máximo após i-ésima compra, `sell[i]` = lucro máximo após i-ésima venda.
- **Complexidade**: O(k×n) tempo, O(k) espaço

### Abordagem 4: Recursivo com Memoização (Top-Down)
DFS com estado (dia, transações restantes, segurando ação).
- **Complexidade**: O(k×n) tempo, O(k×n) espaço

### Abordagem 5: Formulaçăo DP Alternativa
Mesma lógica da Abordagem 1 mas com tabela DP explícita `dp[t][d]`.
- **Complexidade**: O(k×n) tempo, O(k×n) espaço

### Otimização Importante
Se `k >= n/2`, o problema equivale a transações ilimitadas (Problema 122) - soma todos os aumentos.

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| DP 2D | O(k×n) | O(k×n) |
| DP 1D | O(k×n) | O(n) |
| Buy/Sell | O(k×n) | O(k) |
| Memoização | O(k×n) | O(k×n) |
| DP Alternativa | O(k×n) | O(k×n) |

---

## Casos de Teste

1. **k=2, [2,4,1]** -> 2
2. **k=2, [3,2,6,5,0,3]** -> 7
3. **k=1, [7,1,5,3,6,4]** -> 5 (Problema 121)
4. **k=100, [1,2,3,4,5]** -> 4 (Ilimitado)
5. **k=2, [5,4,3,2,1]** -> 0 (Decrescente)
5. **k=2, [5]** -> 0 (Preço único)
6. **k=1, [1,2]** -> 1
7. **k=1, [2,1]** -> 0
8. **k=3, [1,3,2,8,4,9]** -> 13
9. **k=2, [6,1,3,2,4,7]** -> 7

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.