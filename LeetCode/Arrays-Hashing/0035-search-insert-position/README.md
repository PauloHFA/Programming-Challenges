# 35. Search Insert Position

- **Plataforma:** [LeetCode](https://leetcode.com/problems/search-insert-position/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Arrays & Hashing / Binary Search
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` ordenado em ordem crescente (sem duplicatas) e um valor alvo `target`, retorne o índice se o alvo for encontrado. Se não, retorne o índice onde ele seria inserido para manter a ordem.

Você deve escrever um algoritmo com complexidade de tempo **O(log n)**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [1,3,5,6], target = 5
Saída: 2
```

#### Exemplo 2:
```text
Entrada: nums = [1,3,5,6], target = 2
Saída: 1
```

#### Exemplo 3:
```text
Entrada: nums = [1,3,5,6], target = 7
Saída: 4
```

#### Exemplo 4:
```text
Entrada: nums = [1,3,5,6], target = 0
Saída: 0
```

---

## ⚙️ Restrições

- $1 \le \text{nums.length} \le 10^4$
- $-10^4 \le \text{nums[i]} \le 10^4$
- `nums` contém valores **distintos** ordenados em **ordem crescente**.
- $-10^4 \le \text{target} \le 10^4$

---

## 💡 Abordagem: Busca Binária

Busca binária padrão. Se o target for encontrado, retorna o índice. Se não for encontrado, o ponteiro `left` naturalmente aponta para a posição de inserção correta.

### Algoritmo

1. Inicializa `left = 0`, `right = n - 1`
2. Enquanto `left <= right`:
   - `mid = left + (right - left) / 2`
   - Se `nums[mid] == target`: retorna `mid`
   - Se `nums[mid] < target`: `left = mid + 1`
   - Senão: `right = mid - 1`
3. Retorna `left` (posição de inserção)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log n)$ — Busca binária.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.