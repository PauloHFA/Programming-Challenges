# 0053 - Maximum Subarray

## Descrição do Problema

Dado um array de inteiros `nums`, encontre o **subarray contíguo** (contendo pelo menos um número) que tem a maior soma e retorne sua soma.

Um **subarray** é uma parte contígua de um array.

## Exemplos

### Exemplo 1:
```
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: O subarray [4,-1,2,1] tem a maior soma = 6.
```

### Exemplo 2:
```
Input: nums = [1]
Output: 1
```

### Exemplo 3:
```
Input: nums = [5,4,-1,7,8]
Output: 23
```

## Restrições

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

## Abordagem

### Algoritmo de Kadane (O(n) tempo, O(1) espaço)

A ideia principal: **em cada posição, decidimos se estendemos o subarray anterior ou começamos um novo**.

```
currentSum = max(nums[i], currentSum + nums[i])
maxSum = max(maxSum, currentSum)
```

**Intuição:**
- Se `currentSum` é negativo, ele só diminuiria a soma do próximo elemento → melhor começar novo.
- Se `currentSum` é positivo, vale a pena estender.

### Abordagens Alternativas

1. **Divide and Conquer** (O(n log n) tempo, O(log n) espaço):
   - Divide o array ao meio
   - Máximo pode estar: na esquerda, na direita, ou cruzando o meio
   - Recursivamente resolve metades

2. **Programação Dinâmica com Array** (O(n) tempo, O(n) espaço):
   - `dp[i]` = soma máxima de subarray terminando em `i`
   - `dp[i] = max(nums[i], dp[i-1] + nums[i])`

### Complexidade

- **Kadane:** Tempo O(n), Espaço O(1) ⭐ **Ótimo**
- **Divide & Conquer:** Tempo O(n log n), Espaço O(log n)
- **DP Array:** Tempo O(n), Espaço O(n)

## Implementação

A solução está em `Solution.java` com três abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```