# 4. Median of Two Sorted Arrays

- **Plataforma:** [LeetCode](https://leetcode.com/problems/median-of-two-sorted-arrays/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Arrays & Hashing (Binary Search)
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dados dois arrays ordenados `nums1` e `nums2` de tamanho `m` e `n` respectivamente, retorne a **mediana** dos dois arrays ordenados.

A complexidade de tempo geral deve ser $O(\log(m+n))$.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums1 = [1,3], nums2 = [2]
Saída: 2.00000
Explicação: Array mesclado = [1,2,3] e a mediana é 2.
```

#### Exemplo 2:
```text
Entrada: nums1 = [1,2], nums2 = [3,4]
Saída: 2.50000
Explicação: Array mesclado = [1,2,3,4] e a mediana é (2 + 3) / 2 = 2.5.
```

---

## ⚙️ Restrições

- $\text{nums1.length} == m$
- $\text{nums2.length} == n$
- $0 \le m \le 1000$
- $0 \le n \le 1000$
- $1 \le m + n \le 2000$
- $-10^6 \le \text{nums1}[i], \text{nums2}[i] \le 10^6$

---

## 💡 Abordagem: Busca Binária na Partição

A abordagem ingênua de mesclar os arrays leva $O(m+n)$ tempo. Para atingir $O(\log(m+n))$, usamos **busca binária** na partição do menor array.

### Ideia Principal

Queremos particionar os dois arrays de forma que:
1. Todos os elementos à esquerda da partição sejam $\le$ todos os elementos à direita
2. O número de elementos à esquerda seja igual (ou maior em 1) ao número à direita

Seja `i` a partição em `nums1` e `j` a partição em `nums2`, tal que `i + j = (m + n + 1) / 2`.

As condições de partição correta são:
- `nums1[i-1] <= nums2[j]` (max da esquerda de nums1 <= min da direita de nums2)
- `nums2[j-1] <= nums1[i]` (max da esquerda de nums2 <= min da direita de nums1)

### Algoritmo

1. Garantir que `nums1` seja o menor array (trocar se necessário)
2. Busca binária em `nums1` no intervalo `[0, m]`:
   - `i = (left + right) / 2`
   - `j = halfLen - i`
   - Verificar condições de partição
   - Ajustar `left` ou `right` baseado na comparação
3. Quando partição correta encontrada:
   - Se total ímpar: mediana = `max(nums1LeftMax, nums2LeftMax)`
   - Se total par: mediana = `(max(nums1LeftMax, nums2LeftMax) + min(nums1RightMin, nums2RightMin)) / 2.0`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log(\min(m, n)))$ — Busca binária no menor array.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares, sem estruturas de dados adicionais.