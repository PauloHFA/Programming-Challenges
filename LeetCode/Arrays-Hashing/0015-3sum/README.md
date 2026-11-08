# 15. 3Sum

- **Plataforma:** [LeetCode](https://leetcode.com/problems/3sum/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Arrays & Hashing / Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums`, retorne **todos os triplets `[nums[i], nums[j], nums[k]]`** tais que `i != j`, `i != k`, e `j != k`, e `nums[i] + nums[j] + nums[k] == 0`.

A solução não deve conter triplets duplicados.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [-1,0,1,2,-1,-4]
Saída: [[-1,-1,2],[-1,0,1]]
Explicação: 
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
Os triplets distintos são [-1,0,1] e [-1,-1,2].
```

#### Exemplo 2:
```text
Entrada: nums = [0,1,1]
Saída: []
Explicação: Não existem triplets que somem zero.
```

#### Exemplo 3:
```text
Entrada: nums = [0,0,0]
Saída: [[0,0,0]]
Explicação: O único triplet possível soma zero.
```

---

## ⚙️ Restrições

- $3 \le \text{nums.length} \le 3000$
- $-10^5 \le \text{nums}[i] \le 10^5$

---

## 💡 Abordagem: Ordenação + Dois Ponteiros (Sort + Two Pointers)

A abordagem de força bruta $O(n^3)$ é muito lenta. Ordenando o array, podemos fixar um elemento e usar dois ponteiros para encontrar os outros dois em $O(n)$, resultando em $O(n^2)$ total.

### Algoritmo

1. **Ordena** o array
2. Para cada índice `i` de `0` a `n-3`:
   - **Pula duplicatas** do primeiro elemento: se `i > 0 && nums[i] == nums[i-1]`, continua
   - Define `left = i + 1`, `right = n - 1`
   - Enquanto `left < right`:
     - `sum = nums[i] + nums[left] + nums[right]`
     - Se `sum == 0`: adiciona triplet, pula duplicatas de `left` e `right`, move ambos
     - Se `sum < 0`: incrementa `left` (precisa de soma maior)
     - Se `sum > 0`: decrementa `right` (precisa de soma menor)
3. Retorna lista de triplets

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n^2)$ — Ordenação $O(n \log n)$ + loop externo $O(n)$ com dois ponteiros $O(n)$.
- **Espaço:** $O(1)$ ou $O(n)$ — Dependendo do algoritmo de ordenação (Java usa Timsort $O(n)$). O espaço da saída não conta.