# 29. Divide Two Integers

- **Plataforma:** [LeetCode](https://leetcode.com/problems/divide-two-integers/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Math / Bit Manipulation
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Dados dois inteiros `dividend` e `divisor`, divida dois inteiros **sem usar multiplicação, divisão e operador mod**.

Retorne o quociente após dividir `dividend` por `divisor`.

A divisão de inteiros deve truncar para zero, o que significa perder a parte fracionária. Por exemplo, `8.345` seria truncado para `8`, e `-2.7335` seria truncado para `-2`.

**Nota:** Assuma que estamos lidando com um ambiente que só pode armazenar inteiros dentro do intervalo de 32 bits com sinal: $[-2^{31}, 2^{31} - 1]$. Para este problema, se o quociente for **estritamente maior que $2^{31} - 1$**, retorne $2^{31} - 1$, e se o quociente for **estritamente menor que $-2^{31}$**, retorne $-2^{31}$.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: dividend = 10, divisor = 3
Saída: 3
Explicação: 10/3 = 3.33333.. que é truncado para 3.
```

#### Exemplo 2:
```text
Entrada: dividend = 7, divisor = -3
Saída: -2
Explicação: 7/-3 = -2.33333.. que é truncado para -2.
```

---

## ⚙️ Restrições

- $-2^{31} \le \text{dividend}, \text{divisor} \le 2^{31} - 1$
- $\text{divisor} \neq 0$

---

## 💡 Abordagem: Manipulação de Bits (Busca Exponencial)

Como não podemos usar multiplicação/divisão, usamos **deslocamento de bits** (shift) que equivale a multiplicar/dividir por 2.

A ideia é subtrair o divisor do dividendo repetidamente, mas de forma otimizada: dobramos o divisor (shift left) até que seja o maior múltiplo possível que ainda caiba no dividendo restante.

### Algoritmo

1. **Trata overflow:** `Integer.MIN_VALUE / -1` → retorna `Integer.MAX_VALUE`
2. Converte para `long` e usa valores absolutos
3. Enquanto `dividend >= divisor`:
   - `temp = divisor`, `multiple = 1`
   - Enquanto `dividend >= (temp << 1)`: dobra `temp` e `multiple`
   - Subtrai `temp` do `dividend`, adiciona `multiple` ao resultado
4. Aplica sinal baseado nos operandos originais

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\log n)$ — O loop interno dobra o valor a cada iteração (busca exponencial).
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.