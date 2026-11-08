# 10. Regular Expression Matching

- **Plataforma:** [LeetCode](https://leetcode.com/problems/regular-expression-matching/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Dynamic Programming / Strings
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dada uma string de entrada `s` e um padrão `p`, implemente correspondência de expressão regular com suporte a `'.'` e `'*'` onde:

- `'.'` corresponde a **qualquer caractere único**.
- `'*'` corresponde a **zero ou mais** do elemento precedente.

A correspondência deve cobrir a **string de entrada inteira** (não parcial).

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "aa", p = "a"
Saída: false
Explicação: "a" não corresponde à string inteira "aa".
```

#### Exemplo 2:
```text
Entrada: s = "aa", p = "a*"
Saída: true
Explicação: '*' significa zero ou mais do elemento precedente 'a'. Portanto, "a*" corresponde a "aa".
```

#### Exemplo 3:
```text
Entrada: s = "ab", p = ".*"
Saída: true
Explicação: ".*" significa "zero ou mais de qualquer caractere".
```

#### Exemplo 4:
```text
Entrada: s = "aab", p = "c*a*b"
Saída: true
Explicação: c pode ser repetido 0 vezes, a pode ser repetido 1 vez. Portanto, corresponde a "aab".
```

#### Exemplo 5:
```text
Entrada: s = "mississippi", p = "mis*is*p*."
Saída: false
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 20$
- $1 \le \text{p.length} \le 20$
- `s` contém apenas letras minúsculas inglesas.
- `p` contém apenas letras minúsculas inglesas, `'.'` e `'*'`.
- É garantido que para cada aparição do caractere `'*'`, haverá um caractere válido precedente.

---

## 💡 Abordagem: Programação Dinâmica (Bottom-Up)

Usamos uma tabela DP onde `dp[i][j]` indica se `s[0..i-1]` corresponde a `p[0..j-1]`.

### Definição do Estado

`dp[i][j] = true` se os primeiros `i` caracteres de `s` correspondem aos primeiros `j` caracteres de `p`.

### Transições

1. **Se `p[j-1] == '*'`:**
   - **Zero ocorrência:** `dp[i][j] = dp[i][j-2]` (ignora o par `char*`)
   - **Uma ou mais ocorrências:** Se o caractere precedente corresponde (`p[j-2] == s[i-1]` ou `p[j-2] == '.'`), então `dp[i][j] = dp[i][j] || dp[i-1][j]`

2. **Se `p[j-1] == '.'` ou `p[j-1] == s[i-1]`:**
   - Correspondência direta: `dp[i][j] = dp[i-1][j-1]`

3. **Caso contrário:** `dp[i][j] = false`

### Inicialização

- `dp[0][0] = true` (string vazia corresponde a padrão vazio)
- Primeira linha: `dp[0][j] = true` apenas se `p[j-1] == '*'` e `dp[0][j-2] == true`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(m \times n)$ — Onde $m = \text{s.length}$ e $n = \text{p.length}$. Preenchemos uma tabela $m \times n$.
- **Espaço:** $O(m \times n)$ — Tabela DP 2D. Pode ser otimizado para $O(n)$ usando apenas duas linhas.