# 22. Generate Parentheses

- **Plataforma:** [LeetCode](https://leetcode.com/problems/generate-parentheses/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Backtracking / Stack
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado `n` pares de parênteses, escreva uma função para **gerar todas as combinações de parênteses bem formados**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: n = 3
Saída: ["((()))","(()())","(())()","()(())","()()()"]
```

#### Exemplo 2:
```text
Entrada: n = 1
Saída: ["()"]
```

---

## ⚙️ Restrições

- $1 \le n \le 8$

---

## 💡 Abordagem: Backtracking (DFS)

A ideia é construir as strings recursivamente, mantendo a contagem de parênteses de abertura (`open`) e fechamento (`close`) usados até o momento.

### Regras de Validade

1. Podemos adicionar `(` se `open < n`
2. Podemos adicionar `)` se `close < open` (não pode fechar mais do que abriu)

### Algoritmo

1. Função `backtrack(current, open, close)`:
   - Se `current.length() == 2 * n`: adiciona ao resultado, retorna
   - Se `open < n`: adiciona `(`, recursão com `open + 1`, remove `(` (backtrack)
   - Se `close < open`: adiciona `)`, recursão com `close + 1`, remove `)` (backtrack)
2. Inicia com `backtrack("", 0, 0)`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\frac{4^n}{\sqrt{n}})$ — O número de combinações válidas é o **número de Catalan** $C_n = \frac{1}{n+1}\binom{2n}{n} \approx \frac{4^n}{n^{3/2}\sqrt{\pi}}$. Para cada combinação, gastamos $O(n)$ para construir a string.
- **Espaço:** $O(n)$ — Pilha de recursão de profundidade máxima $2n$ (não conta o espaço da saída).