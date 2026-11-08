# 0055 - Jump Game

## Descrição do Problema

Você recebe um array de inteiros `nums` indexado em 0. Você está inicialmente posicionado no índice 0.

Cada elemento `nums[i]` representa o **comprimento máximo** de um salto para frente a partir do índice `i`. Em outras palavras, se você está no índice `i`, pode pular para qualquer índice `i + j` onde `0 <= j <= nums[i]` e `i + j < n`.

Retorne `true` se você pode chegar ao último índice, ou `false` caso contrário.

## Exemplos

### Exemplo 1:
```
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Pule 1 passo do índice 0 para 1, depois 3 passos para o último índice.
```

### Exemplo 2:
```
Input: nums = [3,2,1,0,4]
Output: false
Explanation: Você sempre chegará no índice 3 não importa o que faça. Seu comprimento máximo de salto é 0, o que torna impossível chegar ao último índice.
```

## Restrições

- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 10^5`

## Abordagem

### Guloso (Forward) - O(n) tempo, O(1) espaço

Mantemos `farthest` = índice mais longe que podemos alcançar até agora.

**Algoritmo:**
1. Iteramos de `i = 0` até `n-1`
2. Se `i > farthest`: não conseguimos alcançar esta posição → `return false`
3. Atualizamos `farthest = max(farthest, i + nums[i])`
4. Se `farthest >= n-1`: conseguimos alcançar o fim → `return true`
5. Se terminamos o loop: `return true`

### Guloso (Backwards) - O(n) tempo, O(1) espaço

Trabalhamos de trás para frente: `lastPos` = posição mais à esquerda de onde podemos alcançar o fim.

**Algoritmo:**
1. `lastPos = n - 1`
2. Para `i` de `n-2` até `0`:
   - Se `i + nums[i] >= lastPos`: `lastPos = i`
3. Retornar `lastPos == 0`

### Complexidade

- **Tempo:** O(n) - única passagem
- **Espaço:** O(1) - apenas variáveis

### Alternativa: DP (O(n²) tempo, O(n) espaço)

`dp[i]` = true se podemos alcançar índice `i`.
`dp[i] = true` se existe `j < i` tal que `dp[j] && j + nums[j] >= i`.

Incluída no código para verificação.

## Implementação

A solução está em `Solution.java` com três abordagens (forward, backwards, DP) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```