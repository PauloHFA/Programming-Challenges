# 31. Next Permutation

- **Plataforma:** [LeetCode](https://leetcode.com/problems/next-permutation/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Two Pointers / Array
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Uma **permutação** de um array de inteiros é um arranjo de seus membros em uma sequência ou ordem linear.

A **próxima permutação** de um array de inteiros é a próxima permutação lexicograficamente maior de seus inteiros. Mais formalmente, se todas as permutações do array forem ordenadas em um contêiner de acordo com sua ordem lexicográfica, a próxima permutação desse array é a permutação que o segue no contêiner ordenado. Se tal arranjo não for possível, o array deve ser reorganizado na menor ordem possível (ou seja, ordenado em ordem crescente).

A substituição deve ser **in-place** e usar apenas memória extra constante.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: nums = [1,2,3]
Saída: [1,3,2]
```

#### Exemplo 2:
```text
Entrada: nums = [3,2,1]
Saída: [1,2,3]
```

#### Exemplo 3:
```text
Entrada: nums = [1,1,5]
Saída: [1,5,1]
```

---

## ⚙️ Restrições

- $1 \le \text{nums.length} \le 100$
- $0 \le \text{nums[i]} \le 100$

---

## 💡 Abordagem: Dois Ponteiros (Encontrar pivô, trocar, inverter sufixo)

O algoritmo clássico para próxima permutação lexicográfica:

### Algoritmo

1. **Encontrar o pivô:** Percorre da direita para esquerda para encontrar o primeiro índice `i` tal que `nums[i] < nums[i+1]`. Este é o "pivô" - o ponto onde podemos fazer uma mudança para obter a próxima permutação.
2. **Se não houver pivô (i < 0):** O array está em ordem decrescente (última permutação). Inverte todo o array para obter a primeira permutação.
3. **Encontrar o sucessor:** Percorre da direita para esquerda para encontrar o primeiro elemento `nums[j]` maior que `nums[i]`.
4. **Trocar:** Troca `nums[i]` com `nums[j]`.
5. **Inverter sufixo:** Inverte a subarray de `i+1` até o final (que estava em ordem decrescente, agora fica em ordem crescente - a menor possível).

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Três passagens lineares no array.
- **Espaço:** $O(1)$ — Modificação in-place, apenas variáveis auxiliares.