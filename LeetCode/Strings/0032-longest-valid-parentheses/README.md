# 32. Longest Valid Parentheses

- **Plataforma:** [LeetCode](https://leetcode.com/problems/longest-valid-parentheses/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Strings / Stack / Dynamic Programming
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string contendo apenas os caracteres `'('` e `')'`, encontre o comprimento da **maior substring válida (bem formada)** de parênteses.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "(()"
Saída: 2
Explicação: A substring válida mais longa é "()".
```

#### Exemplo 2:
```text
Entrada: s = ")()())"
Saída: 4
Explicação: A substring válida mais longa é "()()".
```

#### Exemplo 3:
```text
Entrada: s = ""
Saída: 0
```

---

## ⚙️ Restrições

- $0 \le \text{s.length} \le 3 \times 10^4$
- `s[i]` é `'('` ou `')'`.

---

## 💡 Abordagem: Pilha (Stack)

Usamos uma pilha para rastrear os índices dos parênteses de abertura. A ideia principal é manter um **índice base** no topo da pilha que marca o início de uma substring potencialmente válida.

### Algoritmo (Stack)

1. Inicializa pilha com `-1` (índice base antes do início da string)
2. Para cada caractere no índice `i`:
   - Se `'('`: empilha `i`
   - Se `')'`:
     - Desempilha (remove o par de abertura correspondente)
     - Se pilha vazia: empilha `i` como novo índice base
     - Senão: calcula comprimento válido = `i - stack.peek()`, atualiza `maxLen`
3. Retorna `maxLen`

### Algoritmo (Dynamic Programming - Alternativo)

`dp[i]` = comprimento da substring válida mais longa **terminando em `i`**.

- Se `s[i] == ')'` e `s[i-1] == '('`: `dp[i] = dp[i-2] + 2` (caso "()")
- Se `s[i] == ')'` e `s[i-1] == ')'` e `s[i-dp[i-1]-1] == '('`: `dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]` (caso "(())" ou "()(())")

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Uma passagem pela string.
- **Espaço:** $O(n)$ — Pilha ou array DP.