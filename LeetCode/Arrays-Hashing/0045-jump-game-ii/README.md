# 0045 - Jump Game II

## Descrição do Problema

Você recebe um array de inteiros `nums` indexado em 0. Você está inicialmente posicionado no índice 0.

Cada elemento `nums[i]` representa o **comprimento máximo** de um salto para frente a partir do índice `i`. Em outras palavras, se você está no índice `i`, pode pular para qualquer índice `i + j` onde `0 <= j <= nums[i]` e `i + j < n`.

Retorne o **número mínimo de saltos** para chegar ao último índice `nums[n - 1]`. Os casos de teste são gerados de forma que você possa chegar ao último índice.

## Exemplos

### Exemplo 1:
```
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: O mínimo de saltos para chegar ao último índice é 2. Pule 1 passo do índice 0 para 1, depois 3 passos para o último índice.
```

### Exemplo 2:
```
Input: nums = [2,3,0,1,4]
Output: 2
```

## Restrições

- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 1000`
- É garantido que você pode chegar ao último índice.

## Abordagem

### Guloso (BFS Implícito) - O(n) tempo, O(1) espaço

A ideia principal: **processamos o array em "camadas" de saltos**.

Mantemos:
- `currentEnd`: o fim do alcance do salto atual
- `farthest`: o índice mais longe que podemos alcançar do alcance atual
- `jumps`: contador de saltos

**Algoritmo:**
1. Iteramos de `i = 0` até `n-2` (não precisamos pular do último índice).
2. Atualizamos `farthest = max(farthest, i + nums[i])`.
3. Quando `i == currentEnd`, significa que terminamos o alcance do salto atual:
   - Incrementamos `jumps`
   - Atualizamos `currentEnd = farthest`
   - Se `currentEnd >= n-1`, podemos parar (chegamos ao fim).

**Por que funciona:** É essencialmente uma BFS nível por nível. Cada "salto" expande o alcance para o máximo possível a partir de todas as posições no alcance anterior.

### Complexidade

- **Tempo:** O(n) - única passagem
- **Espaço:** O(1) - apenas variáveis

### Alternativa: DP (O(n²) tempo, O(n) espaço)

`dp[i]` = saltos mínimos para chegar ao índice `i`.
`dp[i] = min(dp[j] + 1)` para todo `j < i` onde `j + nums[j] >= i`.

Incluída no código para verificação.

## Implementação

A solução está em `Solution.java` com ambas as abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```