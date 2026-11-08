# 8. String to Integer (atoi)

- **Plataforma:** [LeetCode](https://leetcode.com/problems/string-to-integer-atoi/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Strings
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Implemente a função `myAtoi(string s)`, que converte uma string para um inteiro de 32 bits com sinal (semelhante à função `atoi` do C/C++).

O algoritmo para `myAtoi(string s)` é o seguinte:

1. **Ignorar espaços em branco iniciais** até encontrar o primeiro caractere não-espaço.
2. **Verificar sinal:** Se o próximo caractere for `'+'` ou `'-'`, leia-o. Isso determina se o resultado final é negativo ou positivo. Assuma positivo se nenhum estiver presente.
3. **Ler dígitos:** Leia os próximos caracteres até o próximo caractere não-dígito ou o fim da string. O restante da string é ignorado.
4. **Converter:** Converta esses dígitos em um inteiro. Se nenhum dígito foi lido, o inteiro é `0`.
5. **Clamping (limitar):** Se o inteiro estiver fora do intervalo de 32 bits com sinal `[-2^31, 2^31 - 1]`, limite-o para permanecer no intervalo.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "42"
Saída: 42
```

#### Exemplo 2:
```text
Entrada: s = "   -42"
Saída: -42
```

#### Exemplo 3:
```text
Entrada: s = "4193 with words"
Saída: 4193
```

#### Exemplo 4:
```text
Entrada: s = "words and 987"
Saída: 0
```

#### Exemplo 5:
```text
Entrada: s = "-91283472332"
Saída: -2147483648
```

---

## ⚙️ Restrições

- $0 \le \text{s.length} \le 200$
- `s` consiste de letras inglesas (maiúsculas e minúsculas), dígitos, `' '`, `'+'`, `'-'` e `'.'`

---

## 💡 Abordagem: Máquina de Estados / Parsing Passo a Passo

Implementamos diretamente as regras descritas no problema, processando a string caractere por caractere.

### Algoritmo

1. **Pular espaços em branco iniciais** - avança o índice enquanto for espaço
2. **Verificar sinal opcional** - `'+'` ou `'-'`, define `sign = 1` ou `-1`
3. **Processar dígitos** - enquanto for dígito:
   - Converte char para int: `digit = c - '0'`
   - **Verifica overflow ANTES de multiplicar**: se `result > MAX/10` ou `result == MAX/10 && digit > MAX%10`
   - `result = result * 10 + digit`
4. **Retornar** `result * sign`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Percorremos a string no máximo uma vez.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.