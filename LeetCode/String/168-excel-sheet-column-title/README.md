# LeetCode 168 - Excel Sheet Column Title

## Problema

Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.

For example:
A -> 1
B -> 2
C -> 3
...
Z -> 26
AA -> 27
AB -> 28
...

### Exemplo 1:
```
Input: columnNumber = 1
Output: "A"
```

### Exemplo 2:
```
Input: columnNumber = 28
Output: "AB"
```

### Exemplo 3:
```
Input: columnNumber = 701
Output: "ZY"
```

### Restrições:
- 1 <= columnNumber <= 2^31 - 1

---

## Abordagens

### Abordagem 1: Conversão Base-26
Títulos de colunas do Excel são essencialmente base-26 mas com uma diferença: não há dígito '0'. A=1, B=2, ..., Z=26. Precisamos ajustar subtraindo 1 antes de cada divisão.
- **Complexidade**: O(log_26 n) tempo, O(log_26 n) espaço

### Abordagem 2: Recursiva
Versão recursiva da conversão base-26.
- **Complexidade**: O(log_26 n) tempo, O(log_26 n) espaço (stack de recursão)

### Abordagem 3: Iterativa Otimizada
Pré-calcula o comprimento para eficiência.
- **Complexidade**: O(log_26 n) tempo, O(log_26 n) espaço

### Abordagem 4: Array de Caracteres
Implementação alternativa usando array de caracteres diretamente.
- **Complexidade**: O(log_26 n) tempo, O(log_26 n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Iterative | O(log_26 n) | O(log_26 n) |
| Recursive | O(log_26 n) | O(log_26 n) |
| Optimized | O(log_26 n) | O(log_26 n) |
| Array | O(log_26 n) | O(log_26 n) |

---

## Casos de Teste

1. **Exemplo 1**: columnNumber=1 -> "A"
2. **Exemplo 2**: columnNumber=28 -> "AB"
3. **Exemplo 3**: columnNumber=701 -> "ZY"
4. **Última letra**: columnNumber=26 -> "Z"
5. **Primeiro duplo**: columnNumber=27 -> "AA"
6. **AZ**: columnNumber=52 -> "AZ"
7. **BA**: columnNumber=53 -> "BA"
8. **ZZ**: columnNumber=702 -> "ZZ"
9. **AAA**: columnNumber=703 -> "AAA"
10. **ZZZ**: columnNumber=18278 -> "ZZZ"
11. **Max int**: columnNumber=2147483647 -> "FXSHRXW"
12. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.