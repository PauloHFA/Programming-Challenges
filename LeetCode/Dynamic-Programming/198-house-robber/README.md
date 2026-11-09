# LeetCode 198 - House Robber

## Problema

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array `nums` representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

### Exemplo 1:
```
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.
```

### Exemplo 2:
```
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1). Total amount you can rob = 2 + 9 + 1 = 12.
```

### Restrições:
- 1 <= nums.length <= 100
- 0 <= nums[i] <= 400

---

## Abordagens

### Abordagem 1: DP com Array (Padrão)
`dp[i]` = dinheiro máximo roubando até a casa i. `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 2: DP com O(1) Espaço (Otimizada)
Só precisamos dos dois valores anteriores.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 3: Recursiva com Memoização (Top-Down)
DFS com memoização decidindo roubar ou pular cada casa.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 4: Duas Variáveis (Formulaçăo Alternativa)
`rob` = max se roubar casa atual, `notRob` = max se não roubar.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 5: Tabulaçăo Explícita com Escolhas
`dp[i][0]` = max até casa i sem roubar i, `dp[i][1]` = max roubando i.
- **Complexidade**: O(n) tempo, O(n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| DP Array | O(n) | O(n) |
| O(1) Espaço | O(n) | O(1) |
| Memoização | O(n) | O(n) |
| Duas Variáveis | O(n) | O(1) |
| Explícita | O(n) | O(n) |

---

## Casos de Teste

1. **[1,2,3,1]** -> 4
2. **[2,7,9,3,1]** -> 12
3. **[5]** -> 5
4. **[2,3]** -> 3
5. **[3,2]** -> 3
6. **[2,2,2,2,2]** -> 6
7. **[1,2,3,4,5]** -> 9
8. **[5,4,3,2,1]** -> 9
9. **[10,1,10,1,10]** -> 30
10. **Array 1..100** -> 2550

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.