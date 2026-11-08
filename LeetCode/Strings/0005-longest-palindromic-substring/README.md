# 5. Longest Palindromic Substring

- **Plataforma:** [LeetCode](https://leetcode.com/problems/longest-palindromic-substring/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Strings (Two Pointers / Expand Around Center)
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string `s`, retorne a **maior substring palindrômica** em `s`.

Uma string é um palíndromo se ela lê da mesma forma da esquerda para a direita e da direita para a esquerda.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "babad"
Saída: "bab"
Explicação: "aba" também é uma resposta válida.
```

#### Exemplo 2:
```text
Entrada: s = "cbbd"
Saída: "bb"
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 1000$
- `s` consiste apenas de dígitos e letras inglesas.

---

## 💡 Abordagem: Expansão a Partir do Centro (Expand Around Center)

A ideia central é que todo palíndromo tem um **centro** e se expande simetricamente para ambos os lados.

### Tipos de Centro

1. **Centro único (comprimento ímpar):** Ex: `"aba"` → centro no índice 1 (`'b'`)
2. **Centro duplo (comprimento par):** Ex: `"abba"` → centro entre índices 1 e 2

### Algoritmo

Para cada posição `i` na string:
1. Expandir considerando centro único em `i` (ímpar)
2. Expandir considerando centro duplo entre `i` e `i+1` (par)
3. Manter o maior palíndromo encontrado

A função `expandAroundCenter(left, right)` expande enquanto `s[left] == s[right]` e retorna o comprimento do palíndromo encontrado.

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n^2)$ — Para cada um dos $n$ centros possíveis, podemos expandir até $O(n)$ passos no pior caso.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares, sem estruturas de dados adicionais proporcionais à entrada.

> **Nota:** Existe uma solução $O(n)$ usando o **Algoritmo de Manacher**, mas a abordagem de expansão do centro é mais simples e suficiente para as restrições ($n \le 1000$).