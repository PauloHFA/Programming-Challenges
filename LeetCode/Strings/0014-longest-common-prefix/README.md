# 14. Longest Common Prefix

- **Plataforma:** [LeetCode](https://leetcode.com/problems/longest-common-prefix/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Strings
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Escreva uma função para encontrar a **string de prefixo comum mais longa** entre um array de strings.

Se não houver prefixo comum, retorne uma string vazia `""`.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: strs = ["flower","flow","flight"]
Saída: "fl"
```

#### Exemplo 2:
```text
Entrada: strs = ["dog","racecar","car"]
Saída: ""
Explicação: Não há prefixo comum entre as strings de entrada.
```

---

## ⚙️ Restrições

- $1 \le \text{strs.length} \le 200$
- $0 \le \text{strs[i].length} \le 200$
- `strs[i]` consiste apenas de letras inglesas minúsculas.

---

## 💡 Abordagem: Varredura Vertical (Vertical Scanning)

Comparamos os caracteres **coluna por coluna** (verticalmente) em vez de string por string (horizontalmente). Isso permite parar assim que encontrarmos um descompasso.

### Algoritmo

1. **Casos de borda:** Array vazio ou nulo → retorna `""`
2. Encontra o comprimento da menor string (`minLength`)
3. Para cada posição `i` de `0` a `minLength - 1`:
   - Pega o caractere `strs[0][i]` como referência
   - Verifica se **todas** as strings têm esse caractere na posição `i`
   - Se sim, adiciona ao prefixo
   - Se não, para o loop
4. Retorna o prefixo construído

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(S)$ — Onde $S$ é a soma de todos os caracteres em todas as strings. No pior caso, comparamos todos os caracteres.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares (o StringBuilder do resultado não conta como espaço extra para análise assintótica).

> **Alternativas:** Varredura Horizontal (comparar duas strings por vez), Dividir e Conquistar, Busca Binária no comprimento do prefixo.