# 17. Letter Combinations of a Phone Number

- **Plataforma:** [LeetCode](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Backtracking
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string contendo dígitos de `2` a `9` inclusive, retorne **todas as combinações possíveis de letras** que o número poderia representar. Retorne a resposta em **qualquer ordem**.

Um mapeamento de dígitos para letras (igual aos botões de telefone) é dado abaixo. Note que `1` não mapeia para nenhuma letra.

| Dígito | Letras |
|--------|--------|
| 2      | abc    |
| 3      | def    |
| 4      | ghi    |
| 5      | jkl    |
| 6      | mno    |
| 7      | pqrs   |
| 8      | tuv    |
| 9      | wxyz   |

---

### Exemplos

#### Exemplo 1:
```text
Entrada: digits = "23"
Saída: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

#### Exemplo 2:
```text
Entrada: digits = ""
Saída: []
```

#### Exemplo 3:
```text
Entrada: digits = "2"
Saída: ["a","b","c"]
```

---

## ⚙️ Restrições

- $0 \le \text{digits.length} \le 4$
- `digits[i]` é um dígito no intervalo `['2', '9']`.

---

## 💡 Abordagem: Backtracking (DFS)

Usamos busca em profundidade (DFS) para explorar todas as combinações possíveis. Em cada nível da recursão, escolhemos uma letra para o dígito atual e prosseguimos para o próximo dígito.

### Algoritmo

1. **Caso base:** Se `digits` for vazio, retorna lista vazia
2. **Mapeamento:** Cria mapa estático dígito → letras
3. **Função recursiva `backtrack(index, current)`:**
   - Se `index == digits.length()`: adiciona `current` ao resultado, retorna
   - Obtém as letras para `digits[index]`
   - Para cada letra:
     - Adiciona letra a `current`
     - Chama `backtrack(index + 1, current)`
     - Remove última letra de `current` (backtrack)
4. Inicia com `backtrack(0, "")`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(4^n \times n)$ — Onde $n$ é o comprimento de `digits`. No pior caso (dígitos 7 e 9), cada dígito tem 4 letras. Para cada uma das $4^n$ combinações, gastamos $O(n)$ para construir a string.
- **Espaço:** $O(n)$ — Pilha de recursão de profundidade $n$ (não conta o espaço da saída).