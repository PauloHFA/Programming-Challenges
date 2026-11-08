# 34. Find First and Last Position of Element in Sorted Array

- **Plataforma:** [LeetCode](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Arrays & Hashing / Binary Search
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` ordenado em ordem não decrescente, encontre as posições **inicial e final** de um dado valor `target`.

Se `target` não for encontrado no array, retorne `[-1, -1]`.

Você deve escrever um algoritmo com complexidade de tempo de execução **O(log n)**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [5,7,7,8,8,10], target = 8
Saída: [3,4]
```

#### Exemplo 2:
```text
Entrada: nums = [5,7,7,8,8,10], target = 6
Saída: [-1,-1]
```

#### Exemplo 3:
```text
Entrada: nums = [], target = 0
Saída: [-1,-1]
```

---

## ⚙️ Restrições

- $0 \le \text{nums.length} \le 10^5$
- $-10^9 \le \text{nums[i]} \le 10^9$
- `nums` é um array **não decrescente**.
- $-10^9 \le \text{target} \le 10^9$

---

## 💡 Abordagem: Busca Binária (Duas passagens - Lower Bound e Upper Bound)

Fazemos duas buscas binárias:
1. **Lower Bound:** Encontra a primeira ocorrência (menor índice onde `nums[i] == target`)
2. **Upper Bound:** Encontra a última ocorrência (maior índice onde `nums[i] == target`)

### Algoritmo

**findFirst (Lower Bound):**
- Busca binária padrão
- Quando `nums[mid] == target`: salva resultado e continua buscando à **esquerda** (`right = mid - 1`)

**findLast (Upper Bound):**
- Busca binária padrão
- Quando `nums[mid] == target`: salva resultado e continua buscando à **direita** (`left = mid + 1`)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log n)$ — Duas buscas binárias.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.