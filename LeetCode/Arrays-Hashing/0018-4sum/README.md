# 18. 4Sum

- **Plataforma:** [LeetCode](https://leetcode.com/problems/4sum/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Arrays & Hashing / Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` de comprimento `n` e um inteiro `target`, encontre **todos os quadruplets `[nums[a], nums[b], nums[c], nums[d]]`** tais que:

- $0 \le a, b, c, d < n$
- $a, b, c, d$ são **distintos**
- $nums[a] + nums[b] + nums[c] + nums[d] == target$

A solução não deve conter quadruplets duplicados.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [1,0,-1,0,-2,2], target = 0
Saída: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

#### Exemplo 2:
```text
Entrada: nums = [2,2,2,2,2], target = 8
Saída: [[2,2,2,2]]
```

---

## ⚙️ Restrições

- $1 \le \text{nums.length} \le 200$
- $-10^9 \le \text{nums}[i] \le 10^9$
- $-10^9 \le \text{target} \le 10^9$

---

## 💡 Abordagem: Ordenação + Dois Ponteiros (Generalização do k-Sum)

Estendemos a abordagem do 3Sum para 4Sum fixando dois elementos e usando dois ponteiros para os outros dois.

### Algoritmo

1. **Ordena** o array
2. Loop externo `i` de `0` a `n-4`:
   - Pula duplicatas do primeiro elemento
   - **Terminação antecipada:** se a menor soma possível > target, break
   - **Terminação antecipada:** se a maior soma possível < target, continue
3. Loop interno `j` de `i+1` a `n-3`:
   - Pula duplicatas do segundo elemento
   - Terminação antecipada similar
4. Dois ponteiros `left = j+1`, `right = n-1`:
   - Calcula soma dos 4 elementos (usa `long` para evitar overflow)
   - Se soma == target: adiciona, pula duplicatas, move ambos
   - Se soma < target: incrementa `left`
   - Se soma > target: decrementa `right`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n^3)$ — Dois loops aninhados $O(n^2)$ + dois ponteiros $O(n)$.
- **Espaço:** $O(1)$ ou $O(n)$ — Dependendo do algoritmo de ordenação.