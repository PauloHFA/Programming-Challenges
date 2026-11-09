# LeetCode 171 - Excel Sheet Column Number

## Problema

Given a string `columnTitle` that represents the column title as appears in an Excel sheet, return its corresponding column number.

For example:
- A -> 1
- B -> 2
- C -> 3
- ...
- Z -> 26
- AA -> 27
- AB -> 28
- ...

### Restrições:
- 1 <= columnTitle.length <= 7
- columnTitle consists only of uppercase English letters.
- columnTitle is in the range ["A", "FXSHRXW"].

---

## Abordagens

### Abordagem 1: Iterativa - Conversão Base 26
Trata a string como um número na base 26 onde A=1, B=2, ..., Z=26. Para cada caractere, multiplica o resultado atual por 26 e adiciona o valor do caractere.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 2: Recursiva
Versão recursiva da conversão base 26. Processa a string da esquerda para a direita recursivamente.
- **Complexidade**: O(n) tempo, O(n) espaço (call stack)

### Abordagem 3: Usando Math.pow
Calcula explicitamente a potência de 26 para cada posição. Menos eficiente mas mais clara matematicamente.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 4: Iteração Reversa
Processa a string da direita para a esquerda, mantendo um multiplicador que cresce em potências de 26.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 5: Array de Caracteres
Similar à abordagem 1 mas usando array de chars explicitamente.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Iterativa | O(n) | O(1) |
| Recursiva | O(n) | O(n) |
| Math.pow | O(n) | O(1) |
| Reversa | O(n) | O(1) |
| Array | O(n) | O(1) |

---

## Casos de Teste

1. **A** -> 1
2. **B** -> 2
3. **Z** -> 26
4. **AA** -> 27
5. **AB** -> 28
6. **AZ** -> 52
7. **BA** -> 53
8. **ZZ** -> 702
9. **AAA** -> 703
10. **FXSHRXW** -> 2147483647 (máximo)

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.