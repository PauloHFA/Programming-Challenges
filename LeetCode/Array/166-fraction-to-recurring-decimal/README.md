# LeetCode 166 - Fraction to Recurring Decimal

## Problema

Given two integers representing the numerator and denominator of a fraction, return the fraction in string format. If the fractional part is repeating, enclose the repeating part in parentheses.

### Exemplo 1:
```
Input: numerator = 1, denominator = 2
Output: "0.5"
```

### Exemplo 2:
```
Input: numerator = 2, denominator = 1
Output: "2"
```

### Exemplo 3:
```
Input: numerator = 4, denominator = 333
Output: "0.(012)"
```

### Restrições:
- -2^31 <= numerator, denominator <= 2^31 - 1
- denominator != 0

---

## Abordagens

### Abordagem 1: Long Division com HashMap
Simula divisão longa. Rastreia restos e suas posições no resultado. Quando um resto se repete, encontramos um ciclo.
- **Complexidade**: O(denominator) tempo, O(denominator) espaço

### Abordagem 2: Array para Rastreamento de Restos
Usa array em vez de HashMap para acesso mais rápido quando denominador é pequeno.
- **Complexidade**: O(denominator) tempo, O(denominator) espaço

### Abordagem 3: Detectar Comprimento do Ciclo Primeiro
Primeiro detecta se há ciclo e seu comprimento, depois constrói resultado.
- **Complexidade**: O(denominator) tempo, O(denominator) espaço

### Abordagem 4: BigInteger para Precisão Arbitrária
Usa BigInteger para verificação com números muito grandes.
- **Complexidade**: O(n) tempo, O(n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| HashMap | O(denominator) | O(denominator) |
| Array | O(denominator) | O(denominator) |
| Cycle Detect | O(denominator) | O(denominator) |
| BigInteger | O(n) | O(n) |

---

## Casos de Teste

1. **Exemplo 1**: 1/2 -> "0.5"
2. **Exemplo 2**: 2/1 -> "2"
3. **Exemplo 3**: 4/333 -> "0.(012)"
4. **Numerador negativo**: -1/2 -> "-0.5"
5. **Denominador negativo**: 1/-2 -> "-0.5"
6. **Ambos negativos**: -1/-2 -> "0.5"
7. **Repetindo dígito único**: 1/3 -> "0.(3)"
8. **Repetindo múltiplos dígitos**: 1/7 -> "0.(142857)"
9. **Não-repetindo depois repetindo**: 1/6 -> "0.1(6)"
10. **Resultado inteiro**: 4/2 -> "2"
11. **Números grandes**: 2147483647/1 -> "2147483647"
12. **Numerador zero**: 0/5 -> "0"
13. **1/333**: -> "0.(003)"
14. **22/7**: -> "3.(142857)"
15. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.