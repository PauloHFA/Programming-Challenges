# 16. 3Sum Closest

- **Plataforma:** [LeetCode](https://leetcode.com/problems/3sum-closest/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` de comprimento `n` e um inteiro `target`, encontre **três inteiros** em `nums` tais que a soma seja a mais próxima possível de `target`.

Retorne a soma dos três inteiros.

Você pode assumir que cada entrada tem **exatamente uma solução**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [-1,2,1,-4], target = 1
Saída: 2
Explicação: A soma mais próxima do alvo é 2. (-1 + 2 + 1 = 2).
```

#### Exemplo 2:
```text
Entrada: nums = [0,0,0], target = 1
Saída: 0
```

---

## ⚙️ Restrições

- $3 \le \text{nums.length} \le 500$
- $-1000 \le \text{nums}[i] \le 1000$
- $-10^4 \le \text{target} \le 10^4$

---

## 💡 Abordagem: Ordenação + Dois Ponteiros (Sort + Two Pointers)

Similar ao 3Sum, mas em vez de buscar soma zero, buscamos a soma mais próxima do `target`.

### Algoritmo

1. **Ordena** o array
2. Inicializa `closestSum` com a soma dos três primeiros elementos
3. Para cada índice `i` de `0` a `n-3`:
   - Define `left = i + 1`, `right = n - 1`
   - Enquanto `left < right`:
     - `currentSum = nums[i] + nums[left] + nums[right]`
     - Se `|currentSum - target| < |closestSum - target|`, atualiza `closestSum`
     - Se `currentSum < target`: incrementa `left`
     - Se `currentSum > target`: decrementa `right`
     - Se `currentSum == target`: retorna `target` (encontrou exato)
4. Retorna `closestSum`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n^2)$ — Ordenação $O(n \log n)$ + loop externo $O(n)$ com dois ponteiros $O(n)$.
- **Espaço:** $O(1)$ ou $O(n)$ — Dependendo do algoritmo de ordenação.