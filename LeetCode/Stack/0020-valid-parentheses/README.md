# 20. Valid Parentheses

- **Plataforma:** [LeetCode](https://leetcode.com/problems/valid-parentheses/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Stack
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string `s` contendo apenas os caracteres `'('`, `')'`, `'{'`, `'}'`, `'['` e `']'`, determine se a string de entrada é **válida**.

Uma string de entrada é válida se:

1. Parênteses de abertura devem ser fechados pelo mesmo tipo de parênteses.
2. Parênteses de abertura devem ser fechados na ordem correta.
3. Todo parêntese de fechamento tem um parêntese de abertura correspondente do mesmo tipo.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "()"
Saída: true
```

#### Exemplo 2:
```text
Entrada: s = "()[]{}"
Saída: true
```

#### Exemplo 3:
```text
Entrada: s = "(]"
Saída: false
```

#### Exemplo 4:
```text
Entrada: s = "([])"
Saída: true
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 10^4$
- `s` consiste apenas de parênteses `()[]{}`.

---

## 💡 Abordagem: Pilha (Stack)

A estrutura de dados **pilha (LIFO - Last In, First Out)** é ideal para este problema porque o último parêntese de abertura deve ser o primeiro a ser fechado.

### Algoritmo

1. Cria uma pilha vazia e um mapa `fechamento -> abertura`
2. Para cada caractere `c` na string:
   - Se `c` é de **abertura** (`(`, `[`, `{`): empilha (`push`)
   - Se `c` é de **fechamento** (`)`, `]`, `}`):
     - Se pilha vazia → inválido (fechamento sem abertura)
     - Desempilha (`pop`) e verifica se corresponde ao mapa
     - Se não corresponder → inválido
3. No final, a pilha deve estar **vazia** (todas as aberturas foram fechadas)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a string uma única vez.
- **Espaço:** $O(n)$ — No pior caso (todos parênteses de abertura), a pilha armazena $n$ elementos.