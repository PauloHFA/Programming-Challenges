# 9. Palindrome Number

- **Plataforma:** [LeetCode](https://leetcode.com/problems/palindrome-number/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Math
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um inteiro `x`, retorne `true` se `x` for um **palíndromo**, e `false` caso contrário.

Um inteiro é um palíndromo quando lê da mesma forma da esquerda para a direita e da direita para a esquerda.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: x = 121
Saída: true
```

#### Exemplo 2:
```text
Entrada: x = -121
Saída: false
Explicação: Da esquerda para a direita, lê -121. Da direita para a esquerda, lê 121-. Portanto, não é um palíndromo.
```

#### Exemplo 3:
```text
Entrada: x = 10
Saída: false
Explicação: Lê 01 da direita para a esquerda. Portanto, não é um palíndromo.
```

---

## ⚙️ Restrições

- $-2^{31} \le x \le 2^{31} - 1$

---

## 💡 Abordagem: Reverter Metade do Número

Em vez de reverter o número inteiro (o que pode causar overflow), revertemos apenas **metade** dos dígitos e comparamos com a outra metade.

### Algoritmo

1. **Casos de borda:**
   - Números negativos não são palíndromos
   - Números terminados em 0 (exceto o próprio 0) não são palíndromos
2. **Reverter metade:**
   - Enquanto `x > revertedNumber`:
     - `revertedNumber = revertedNumber * 10 + x % 10`
     - `x /= 10`
3. **Comparação:**
   - Comprimento par: `x == revertedNumber`
   - Comprimento ímpar: `x == revertedNumber / 10` (ignora o dígito do meio)

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log_{10}(n))$ — Número de dígitos em `x` (dividimos por 10 a cada iteração).
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.