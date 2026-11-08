# 13. Roman to Integer

- **Plataforma:** [LeetCode](https://leetcode.com/problems/roman-to-integer/)
- **Dificuldade:** Fácil (Easy)
- **Categoria:** Math
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dado um numeral romano, converta-o para um inteiro.

Os numerais romanos são representados por sete símbolos diferentes:

| Símbolo | Valor |
|---------|-------|
| I       | 1     |
| V       | 5     |
| X       | 10    |
| L       | 50    |
| C       | 100   |
| D       | 500   |
| M       | 1000  |

Existem seis instâncias onde a subtração é usada:
- `I` pode ser colocado antes de `V` (5) e `X` (10) para fazer 4 e 9.
- `X` pode ser colocado antes de `L` (50) e `C` (100) para fazer 40 e 90.
- `C` pode ser colocado antes de `D` (500) e `M` (1000) para fazer 400 e 900.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "III"
Saída: 3
```

#### Exemplo 2:
```text
Entrada: s = "LVIII"
Saída: 58
Explicação: L = 50, V = 5, III = 3.
```

#### Exemplo 3:
```text
Entrada: s = "MCMXCIV"
Saída: 1994
Explicação: M = 1000, CM = 900, XC = 90, IV = 4.
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 15$
- `s` contém apenas os caracteres `('I', 'V', 'X', 'L', 'C', 'D', 'M')`.
- É garantido que `s` é um numeral romano válido no intervalo `[1, 3999]`.

---

## 💡 Abordagem: Passagem Única com Olhada Adiante (Lookahead)

A ideia é percorrer a string da esquerda para a direita. Se o valor do caractere atual for **menor** que o do próximo caractere, significa que estamos em um caso de subtração (ex: IV, IX, XL, etc.), então **subtraímos** o valor atual. Caso contrário, **somamos**.

### Algoritmo

1. Cria um mapa com os valores de cada símbolo romano
2. Inicializa `total = 0`
3. Para cada caractere no índice `i`:
   - Obtém `currentValue = valor(s[i])`
   - Se `i + 1 < n` e `currentValue < valor(s[i+1])`:
     - `total -= currentValue` (caso de subtração)
   - Senão:
     - `total += currentValue`
4. Retorna `total`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a string uma única vez.
- **Espaço:** $O(1)$ — O mapa tem tamanho fixo (7 entradas).