# 12. Integer to Roman

- **Plataforma:** [LeetCode](https://leetcode.com/problems/integer-to-roman/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Math
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Converta um inteiro para um numeral romano.

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
Entrada: num = 3
Saída: "III"
```

#### Exemplo 2:
```text
Entrada: num = 58
Saída: "LVIII"
Explicação: L = 50, V = 5, III = 3.
```

#### Exemplo 3:
```text
Entrada: num = 1994
Saída: "MCMXCIV"
Explicação: M = 1000, CM = 900, XC = 90, IV = 4.
```

---

## ⚙️ Restrições

- $1 \le \text{num} \le 3999$

---

## 💡 Abordagem: Guloso (Greedy) com Mapeamento Valor-Símbolo

A abordagem gulosa funciona porque os numerais romanos são construídos dos maiores valores para os menores, e sempre usamos o maior símbolo possível a cada passo.

### Algoritmo

1. Define arrays paralelos com valores e símbolos em ordem decrescente (incluindo casos de subtração: 900, 400, 90, 40, 9, 4)
2. Para cada valor, enquanto `num >= valor`:
   - Subtrai o valor de `num`
   - Adiciona o símbolo correspondente ao resultado
3. Retorna a string construída

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(1)$ — O loop executa no máximo 15 vezes (número de símbolos), e o comprimento máximo do numeral romano para 3999 é 15 caracteres.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares e o StringBuilder de tamanho fixo.