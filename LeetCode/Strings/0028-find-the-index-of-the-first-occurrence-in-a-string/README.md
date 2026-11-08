# 28. Find the Index of the First Occurrence in a String

- **Plataforma:** [LeetCode](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Strings / Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dadas duas strings `needle` e `haystack`, retorne o índice da **primeira ocorrência** de `needle` em `haystack`, ou `-1` se `needle` não for parte de `haystack`.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: haystack = "sadbutsad", needle = "sad"
Saída: 0
Explicação: "sad" ocorre no índice 0 e 6. A primeira ocorrência é no índice 0.
```

#### Exemplo 2:
```text
Entrada: haystack = "leetcode", needle = "leeto"
Saída: -1
Explicação: "leeto" não ocorre em "leetcode".
```

---

## ⚙️ Restrições

- $1 \le \text{haystack.length}, \text{needle.length} \le 10^4$
- `haystack` e `needle` consistem apenas de caracteres ingleses minúsculos.

---

## 💡 Abordagem: Built-in `indexOf` (ou KMP para O(n+m))

A solução mais simples usa o método nativo `String.indexOf()` do Java. Para fins educacionais, também incluí a implementação do algoritmo **KMP (Knuth-Morris-Pratt)** que garante $O(n+m)$ tempo.

### Algoritmo KMP

O KMP pré-processa o padrão (`needle`) para criar um array **LPS (Longest Prefix Suffix)** que indica o maior prefixo próprio que também é sufixo para cada posição. Isso evita comparações desnecessárias quando há mismatch.

1. **Build LPS:** Para cada posição no padrão, calcula o maior prefixo que também é sufixo
2. **Search:** Usa o LPS para pular comparações quando há mismatch

---

## ⏱️ Análise de Complexidade

- **Tempo (Built-in):** $O(n \times m)$ no pior caso (implementação ingênua do Java)
- **Tempo (KMP):** $O(n + m)$ — Linear garantido
- **Espaço:** $O(1)$ para built-in, $O(m)$ para KMP (array LPS)