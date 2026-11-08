# 26. Remove Duplicates from Sorted Array

- **Plataforma:** [LeetCode](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Arrays & Hashing / Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` ordenado em **ordem não decrescente**, remova as duplicatas **in-place** de forma que cada elemento único apareça apenas **uma vez**. A ordem relativa dos elementos deve ser mantida a mesma.

Como não é possível alterar o comprimento do array em algumas linguagens, você deve colocar o resultado na **primeira parte** do array `nums`. Mais formalmente, se houver `k` elementos após remover as duplicatas, então os primeiros `k` elementos de `nums` devem conter o resultado final. Não importa o que você deixe além dos primeiros `k` elementos.

Retorne `k` após colocar o resultado final nos primeiros `k` slots de `nums`.

Não aloque espaço extra para outro array. Você deve fazer isso **modificando o array de entrada in-place** com $O(1)$ memória extra.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [1,1,2]
Saída: 2, nums = [1,2,_]
Explicação: Sua função deve retornar k = 2, com os dois primeiros elementos de nums sendo 1 e 2 respectivamente.
Não importa o que você deixe além do k retornado (por isso são sublinhados).
```

#### Exemplo 2:
```text
Entrada: nums = [0,0,1,1,1,2,2,3,3,4]
Saída: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explicação: Sua função deve retornar k = 5, com os cinco primeiros elementos de nums sendo 0, 1, 2, 3 e 4 respectivamente.
```

---

## ⚙️ Restrições

- $1 \le \text{nums.length} \le 3 \times 10^4$
- $-100 \le \text{nums[i]} \le 100$
- `nums` está ordenado em **ordem não decrescente**.

---

## 💡 Abordagem: Dois Ponteiros (Fast and Slow)

Como o array está ordenado, todos os duplicatas estão adjacentes. Usamos dois ponteiros:
- `slow`: aponta para a posição do último elemento único confirmado
- `fast`: percorre o array procurando novos elementos únicos

### Algoritmo

1. Se array vazio, retorna 0
2. Inicializa `slow = 0`
3. Para `fast` de 1 até `n-1`:
   - Se `nums[fast] != nums[slow]`: encontrou novo elemento único
     - Incrementa `slow`
     - `nums[slow] = nums[fast]`
4. Retorna `slow + 1` (comprimento do array sem duplicatas)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos o array uma única vez.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares, modificação in-place.