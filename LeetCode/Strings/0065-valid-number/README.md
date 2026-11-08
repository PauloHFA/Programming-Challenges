# 0065 - Valid Number

## Descrição do Problema

Dada uma string `s`, retorne `true` se `s` for um **número válido**.

Um **número válido** pode ser dividido nestes componentes (em ordem):

1. Um **número decimal** ou **inteiro**
2. (Opcional) Um `'e'` ou `'E'`, seguido por um **inteiro**

Um **número decimal** pode ser:
- Um ou mais dígitos, seguido por um ponto `.`
- Um ou mais dígitos, seguido por um ponto `.`, seguido por um ou mais dígitos
- Um ponto `.`, seguido por um ou mais dígitos

Um **inteiro** pode ser:
- (Opcional) Um sinal `'+'` ou `'-'`
- Um ou mais dígitos

## Exemplos

### Exemplo 1:
```
Input: s = "0"
Output: true
```

### Exemplo 2:
```
Input: s = "e"
Output: false
```

### Exemplo 3:
```
Input: s = "."
Output: false
```

### Exemplo 4:
```
Input: s = ".1"
Output: true
```

## Restrições

- `1 <= s.length <= 20`
- `s` consiste apenas de letras inglesas (maiúsculas e minúsculas), dígitos (`0-9`), mais `'+'`, `'-'`, `'.'`, `'e'`, `'E'`.

## Abordagem

### Máquina de Estados Finitos (DFA)

A abordagem mais robusta é usar uma **máquina de estados finitos determinística (DFA)**.

**Estados:**
- `0`: Início
- `1`: Sinal antes do número (`+`, `-`)
- `2`: Parte inteira (dígitos antes do ponto)
- `3`: Ponto decimal (sem inteiro antes)
- `4`: Parte decimal (dígitos após o ponto)
- `5`: Expoente (`e`, `E`)
- `6`: Sinal após expoente
- `7`: Parte inteira do expoente

**Estados finais válidos:** `2` (inteiro), `4` (decimal com inteiro), `7` (expoente), `9` (decimal sem inteiro antes), `10` (decimal com inteiro antes)

**Transições baseadas no caractere:**
- Dígito (`0-9`): Avança para estado de número
- Sinal (`+`, `-`): Apenas no início ou após `e`
- Ponto (`.`): Apenas se não visto antes e sem expoente
- Expoente (`e`, `E`): Apenas se número válido antes e não visto antes

### Complexidade

- **Tempo:** O(n) - única passagem pela string
- **Espaço:** O(1) - apenas variáveis de estado

### Abordagem Alternativa: Double.parseDouble

Usar `Double.parseDouble()` com validações extras (rejeitar Infinity, NaN, strings sem dígitos). Incluída no código para comparação.

## Implementação

A solução está em `Solution.java` com DFA e abordagem parseDouble, com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```