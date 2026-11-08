# 0044 - Wildcard Matching

## Descrição do Problema

Dada uma string de entrada `s` e um padrão `p`, implemente correspondência de curinga com suporte para `'?'` e `'*'`:

- `'?'` corresponde a **qualquer caractere único**.
- `'*'` corresponde a **qualquer sequência de caracteres** (incluindo a sequência vazia).

A correspondência deve cobrir a **string de entrada inteira** (não parcial).

## Exemplos

### Exemplo 1:
```
Input: s = "aa", p = "a"
Output: false
Explanation: "a" não corresponde a "aa" inteira.
```

### Exemplo 2:
```
Input: s = "aa", p = "*"
Output: true
Explanation: "*" corresponde a qualquer sequência.
```

### Exemplo 3:
```
Input: s = "cb", p = "?a"
Output: false
Explanation: "?" corresponde a "c", mas "a" não corresponde a "b".
```

### Exemplo 4:
```
Input: s = "adceb", p = "*a*b"
Output: true
Explanation: O primeiro "*" corresponde a "dce", o segundo corresponde a vazio.
```

### Exemplo 5:
```
Input: s = "acdcb", p = "a*c?b"
Output: false
```

## Restrições

- `0 <= s.length, p.length <= 2000`
- `s` contém apenas letras minúsculas inglesas.
- `p` contém apenas letras minúsculas inglesas, `'?'` ou `'*'`.

## Abordagem

### 1. Programação Dinâmica (DP) - O(m×n) tempo, O(m×n) espaço

`dp[i][j] = true` se `s[0..i-1]` corresponde a `p[0..j-1]`.

**Transições:**
- Se `p[j-1] == '*'`: `dp[i][j] = dp[i][j-1] || dp[i-1][j]`
  - `dp[i][j-1]`: `*` corresponde a sequência vazia
  - `dp[i-1][j]`: `*` corresponde a mais um caractere
- Se `p[j-1] == '?'` ou `s[i-1] == p[j-1]`: `dp[i][j] = dp[i-1][j-1]`
- Caso contrário: `dp[i][j] = false`

**Inicialização:**
- `dp[0][0] = true` (strings vazias correspondem)
- `dp[0][j] = dp[0][j-1]` se `p[j-1] == '*'` (padrão só com * corresponde a string vazia)

### 2. Two Pointers (Guloso) - O(m×n) tempo, O(1) espaço

Mais eficiente em espaço. Mantemos:
- `sIdx`, `pIdx`: ponteiros atuais
- `starIdx`: posição do último `*` no padrão
- `sTmpIdx`: posição em `s` quando `*` foi encontrado

**Algoritmo:**
1. Se caracteres correspondem (ou `?`), avançamos ambos.
2. Se `*`, registramos posição e avançamos apenas padrão.
3. Se não correspondem e houve `*` anterior: fazemos backtracking - `*` absorve mais um caractere.
4. Se não correspondem e não houve `*`: falha.
5. No final, pulamos `*` restantes no padrão.

### Complexidade

- **DP:** Tempo O(m×n), Espaço O(m×n)
- **Two Pointers:** Tempo O(m×n) pior caso, Espaço O(1)

## Implementação

A solução está em `Solution.java` com ambas as abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```