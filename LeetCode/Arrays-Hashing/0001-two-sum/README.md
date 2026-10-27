# 1. Two Sum

- **Plataforma:** [LeetCode](https://leetcode.com/problems/two-sum/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Arrays & Hashing
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um array de inteiros `nums` e um número inteiro `target`, retorne os *índices dos dois números de modo que a soma deles seja igual a `target`*.

Você pode assumir que cada entrada tem **exatamente uma solução**, e não pode usar o mesmo elemento duas vezes.

A resposta pode ser retornada em qualquer ordem.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [2,7,11,15], target = 9
Saída: [0,1]
Explicação: Como nums[0] + nums[1] == 9, retornamos [0, 1].
```

#### Exemplo 2:
```text
Entrada: nums = [3,2,4], target = 6
Saída: [1,2]
```

#### Exemplo 3:
```text
Entrada: nums = [3,3], target = 6
Saída: [0,1]
```

---

## ⚙️ Restrições

- $2 \le \text{nums.length} \le 10^4$
- $-10^9 \le \text{nums}[i] \le 10^9$
- $-10^9 \le \text{target} \le 10^9$
- **Apenas uma solução válida existe.**

---

## 💡 Abordagem: Tabela Hash de Passagem Única (One-Pass Hash Map)

A solução ingênua com dois loops aninhados tem complexidade $O(n^2)$. 

Podemos otimizar para tempo linear $O(n)$ utilizando uma **Tabela Hash** (`HashMap`):
1. Conforme iteramos pelo array, calculamos o complemento necessário:
   $$\text{complemento} = \text{target} - \text{nums}[i]$$
2. Verificamos em tempo médio $O(1)$ se o `complemento` já foi visto anteriormente no mapa:
   - Se já existe, encontramos o par: retornamos `[map.get(complemento), i]`.
   - Caso contrário, salvamos o valor atual e seu índice no mapa: `map.put(nums[i], i)`.

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos o array contendo $n$ elementos exatamente uma vez. Cada busca e inserção no `HashMap` custa em média $O(1)$.
- **Espaço:** $O(n)$ — No pior caso, armazenamos até $n$ elementos na tabela hash.
