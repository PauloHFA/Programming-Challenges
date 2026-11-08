# 7. Reverse Integer

- **Plataforma:** [LeetCode](https://leetcode.com/problems/reverse-integer/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Math
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um inteiro de 32 bits com sinal `x`, retorne `x` com seus dígitos invertidos. Se inverter `x` causar o valor a ir fora do intervalo de inteiro de 32 bits com sinal `[-2^31, 2^31 - 1]`, retorne `0`.

**Assuma que o ambiente não permite armazenar inteiros de 64 bits (com sinal ou sem sinal).**

---

### Exemplos

#### Exemplo 1:
```text
Entrada: x = 123
Saída: 321
```

#### Exemplo 2:
```text
Entrada: x = -123
Saída: -321
```

#### Exemplo 3:
```text
Entrada: x = 120
Saída: 21
```

---

## ⚙️ Restrições

- $-2^{31} \le x \le 2^{31} - 1$

---

## 💡 Abordagem: Pop e Push de Dígitos com Verificação de Overflow

A ideia é extrair os dígitos do final (pop) e construir o número invertido (push), verificando overflow **antes** que ele ocorra.

### Algoritmo

1. Inicializa `reversed = 0`
2. Enquanto `x != 0`:
   - `pop = x % 10` (último dígito)
   - `x /= 10` (remove o último dígito)
   - **Verificação de overflow** antes de `reversed = reversed * 10 + pop`:
     - Se `reversed > Integer.MAX_VALUE / 10` → overflow
     - Se `reversed == Integer.MAX_VALUE / 10` e `pop > 7` → overflow (pois `MAX_VALUE = 2147483647`)
     - Se `reversed < Integer.MIN_VALUE / 10` → overflow
     - Se `reversed == Integer.MIN_VALUE / 10` e `pop < -8` → overflow (pois `MIN_VALUE = -2147483648`)
   - `reversed = reversed * 10 + pop`
3. Retorna `reversed`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log_{10}(x))$ — Número de dígitos em `x` (no máximo 10 para int 32 bits).
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.