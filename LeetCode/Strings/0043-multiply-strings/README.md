# 0043 - Multiply Strings

## Descrição do Problema

Dados dois inteiros não negativos `num1` e `num2` representados como strings, retorne o produto de `num1` e `num2`, também representado como string.

**Nota:** Você não deve usar nenhuma biblioteca built-in de BigInteger ou converter as entradas para inteiros diretamente.

## Exemplos

### Exemplo 1:
```
Input: num1 = "2", num2 = "3"
Output: "6"
```

### Exemplo 2:
```
Input: num1 = "123", num2 = "456"
Output: "56088"
```

## Restrições

- `1 <= num1.length, num2.length <= 200`
- `num1` e `num2` consistem apenas de dígitos.
- Ambos `num1` e `num2` não contêm zeros à esquerda, exceto o próprio número 0.

## Abordagem

### Multiplicação Escolar (Grade-school Multiplication)

Simulamos a multiplicação manual dígito a dígito:

1. **Array de resultado**: O produto de dois números com m e n dígitos tem no máximo m+n dígitos. Criamos array `result[m+n]`.
2. **Multiplicação**: Para cada dígito de `num1` (da direita para esquerda) e cada dígito de `num2` (da direita para esquerda):
   - Calculamos o produto dos dois dígitos
   - A posição no array de resultado: `i + j` (carry) e `i + j + 1` (dígito atual)
   - Somamos ao valor existente e propagamos o carry
3. **Conversão**: Convertemos o array para string, pulando zeros à esquerda.

**Exemplo visual (123 × 456):**
```
      1 2 3
    × 4 5 6
    -------
      7 3 8  (123 × 6)
    6 1 5    (123 × 5, deslocado)
  4 9 2      (123 × 4, deslocado)
  -------
  5 6 0 8 8
```

### Complexidade

- **Tempo:** O(m × n) onde m, n são os comprimentos das strings
- **Espaço:** O(m + n) para o array de resultado

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```