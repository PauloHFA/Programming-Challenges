# 3. Longest Substring Without Repeating Characters

- **Plataforma:** [LeetCode](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Sliding Window
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string `s`, encontre o comprimento da **maior substring sem caracteres repetidos**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "abcabcbb"
Saída: 3
Explicação: A resposta é "abc", com comprimento 3.
```

#### Exemplo 2:
```text
Entrada: s = "bbbbb"
Saída: 1
Explicação: A resposta é "b", com comprimento 1.
```

#### Exemplo 3:
```text
Entrada: s = "pwwkew"
Saída: 3
Explicação: A resposta é "wke", com comprimento 3.
Note que a resposta deve ser uma substring, "pwke" é uma subsequência e não uma substring.
```

---

## ⚙️ Restrições

- $0 \le \text{s.length} \le 5 \times 10^4$
- `s` consiste de caracteres ASCII (letras inglesas, dígitos, símbolos e espaços).

---

## 💡 Abordagem: Janela Deslizante (Sliding Window) com Hash Map

A ideia central é manter uma **janela deslizante** `[left, right]` que representa a substring atual sem caracteres repetidos.

1. **Hash Map (`charIndexMap`):** Armazena o índice mais recente de cada caractere visto.
2. **Ponteiro `right`:** Expande a janela para a direita, adicionando novos caracteres.
3. **Ponteiro `left`:** Contrai a janela pela esquerda quando um caractere repetido é encontrado dentro da janela atual.
   - Se `s[right]` já existe no mapa **e** seu índice anterior $\ge$ `left`, significa que o caractere está dentro da janela atual.
   - Movemos `left` para `charIndexMap.get(s[right]) + 1` para pular o caractere duplicado anterior.
4. **Atualização:** Em cada iteração, atualizamos o índice do caractere atual no mapa e calculamos o comprimento máximo: `maxLength = max(maxLength, right - left + 1)`.

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a string uma única vez com o ponteiro `right`. Cada caractere é processado no máximo duas vezes (uma ao entrar na janela, outra ao sair quando `left` avança). Operações no `HashMap` são $O(1)$ em média.
- **Espaço:** $O(\min(m, n))$ — O `HashMap` armazena no máximo $m$ caracteres únicos (tamanho do conjunto de caracteres, ex: 128 para ASCII) ou $n$ caracteres (se todos forem únicos), o que for menor.