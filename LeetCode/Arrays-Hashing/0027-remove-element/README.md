# 27. Remove Element

- **Plataforma:** [LeetCode](https://leetcode.com/problems/remove-element/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Arrays & Hashing / Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` e um inteiro `val`, remova todas as ocorrências de `val` em `nums` **in-place**. A ordem dos elementos pode ser alterada. Retorne o número de elementos em `nums` que não são iguais a `val`.

Considere o número de elementos em `nums` que não são iguais a `val` seja `k`. Para ser aceito, você precisa fazer o seguinte:

- Altere o array `nums` de forma que os primeiros `k` elementos de `nums` contenham os elementos que não são iguais a `val`. Os elementos restantes de `nums` não são importantes, assim como o tamanho de `nums`.
- Retorne `k`.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [3,2,2,3], val = 3
Saída: 2, nums = [2,2,_,_]
Explicação: Sua função deve retornar k = 2, com os dois primeiros elementos de nums sendo 2.
Não importa o que você deixe além do k retornado.
```

#### Exemplo 2:
```text
Entrada: nums = [0,1,2,2,3,0,4,2], val = 2
Saída: 5, nums = [0,1,4,0,3,_,_,_]
Explicação: Sua função deve retornar k = 5, com os cinco primeiros elementos de nums sendo 0, 0, 1, 3 e 4.
Note que os cinco elementos podem ser retornados em qualquer ordem.
```

---

## ⚙️ Restrições

- $0 \le \text{nums.length} \le 100$
- $0 \le \text{nums[i]} \le 50$
- $0 \le \text{val} \le 100$

---

## 💡 Abordagem: Dois Ponteiros (Fast and Slow)

Similar ao problema 26, mas aqui removemos um valor específico. O ponteiro `slow` aponta para onde o próximo elemento não-`val` deve ser colocado.

### Algoritmo

1. Inicializa `slow = 0`
2. Para `fast` de 0 até `n-1`:
   - Se `nums[fast] != val`: 
     - `nums[slow] = nums[fast]`
     - Incrementa `slow`
3. Retorna `slow`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos o array uma única vez.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares, modificação in-place.