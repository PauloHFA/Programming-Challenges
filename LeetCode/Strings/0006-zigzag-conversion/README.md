# 6. Zigzag Conversion

- **Plataforma:** [LeetCode](https://leetcode.com/problems/zigzag-conversion/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Strings
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

A string `"PAYPALISHIRING"` é escrita em um padrão zigzag em um determinado número de linhas assim:

```text
P   A   H   N
A P L S I I G
Y   I   R
```

E então lida linha por linha: `"PAHNAPLSIIGYIR"`

Escreva o código que pegará uma string e fará essa conversão dado um número de linhas:

```java
string convert(string s, int numRows);
```

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "PAYPALISHIRING", numRows = 3
Saída: "PAHNAPLSIIGYIR"
```

#### Exemplo 2:
```text
Entrada: s = "PAYPALISHIRING", numRows = 4
Saída: "PINALSIGYAHRPI"
Explicação:
P     I    N
A   L S  I G
Y A   H R
P     I
```

#### Exemplo 3:
```text
Entrada: s = "A", numRows = 1
Saída: "A"
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 1000$
- `s` consiste de letras inglesas (maiúsculas e minúsculas), `','` e `'.'`
- $1 \le \text{numRows} \le 1000$

---

## 💡 Abordagem: Simulação do Padrão Zigzag com Rastreamento de Linhas

A ideia é simular o movimento "zigzag" mantendo um array de `StringBuilder` para cada linha.

### Algoritmo

1. **Casos base:** Se `numRows == 1` ou `numRows >= s.length()`, retorna a string original.
2. **Inicialização:** Cria uma lista de `StringBuilder` para cada linha.
3. **Simulação:**
   - Mantém `currentRow` (linha atual) e `goingDown` (direção do movimento)
   - Para cada caractere, adiciona à linha correspondente
   - Inverte a direção quando atinge a primeira (`0`) ou última (`numRows - 1`) linha
4. **Concatenação:** Junta todas as linhas em ordem.

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a string uma única vez.
- **Espaço:** $O(n)$ — Armazenamos todos os caracteres nos `StringBuilder` das linhas.