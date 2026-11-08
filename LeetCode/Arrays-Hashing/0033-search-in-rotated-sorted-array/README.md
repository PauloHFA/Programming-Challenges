# 33. Search in Rotated Sorted Array

- **Plataforma:** [LeetCode](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Arrays & Hashing / Binary Search
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Existe um array de inteiros `nums` ordenado em ordem crescente (com valores **distintos**).

Antes de ser passado para sua função, `nums` é **rotacionado** em um índice pivô desconhecido `k` (`0 <= k < nums.length`) de forma que o array resultante seja `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]` (indexado em 0). Por exemplo, `[0,1,2,4,5,6,7]` pode ser rotacionado no índice 3 e se tornar `[4,5,6,7,0,1,2]`.

Dado o array `nums` **após** a rotação e um inteiro `target`, retorne o índice de `target` se ele estiver em `nums`, ou `-1` se não estiver.

Você deve escrever um algoritmo com complexidade de tempo de execução **O(log n)**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [4,5,6,7,0,1,2], target = 0
Saída: 4
```

#### Exemplo 2:
```text
Entrada: nums = [4,5,6,7,0,1,2], target = 3
Saída: -1
```

#### Exemplo 3:
```text
Entrada: nums = [1], target = 0
Saída: -1
```

---

## ⚙️ Restrições

- $1 \le \text{nums.length} \le 5000$
- $-10^4 \le \text{nums[i]} \le 10^4$
- Todos os valores de `nums` são **únicos**.
- `nums` é um array ordenado que foi rotacionado.
- $-10^4 \le \text{target} \le 10^4$

---

## 💡 Abordagem: Busca Binária Modificada

A chave é que **pelo menos um lado do meio (mid) está sempre ordenado**. Podemos determinar qual lado está ordenado e decidir se o target está nesse lado.

### Algoritmo

1. Inicializa `left = 0`, `right = n - 1`
2. Enquanto `left <= right`:
   - `mid = left + (right - left) / 2`
   - Se `nums[mid] == target`: retorna `mid`
   - **Se lado esquerdo ordenado** (`nums[left] <= nums[mid]`):
     - Se `nums[left] <= target < nums[mid]`: target está no lado esquerdo → `right = mid - 1`
     - Senão: target está no lado direito → `left = mid + 1`
   - **Senão (lado direito ordenado)**:
     - Se `nums[mid] < target <= nums[right]`: target está no lado direito → `left = mid + 1`
     - Senão: target está no lado esquerdo → `right = mid - 1`
3. Retorna `-1` (não encontrado)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log n)$ — Busca binária padrão.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.