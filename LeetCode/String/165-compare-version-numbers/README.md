# LeetCode 165 - Compare Version Numbers

## Problema

Given two version numbers, version1 and version2, compare them.

Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits and may contain leading zeros. Every revision contains at least one character. Revisions are 0-indexed from left to right.

To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using their integer value ignoring any leading zeros. If a version number does not specify a revision at an index, then treat the revision as 0.

Return:
- If version1 < version2, return -1.
- If version1 > version2, return 1.
- Otherwise, return 0.

### Exemplo 1:
```
Input: version1 = "1.01", version2 = "1.001"
Output: 0
Explanation: Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
```

### Exemplo 2:
```
Input: version1 = "1.0", version2 = "1.0.0"
Output: 0
Explanation: version1 does not specify revision 2, which means it is treated as "0".
```

### Exemplo 3:
```
Input: version1 = "0.1", version2 = "1.1"
Output: -1
Explanation: version1's revision 0 is "0", while version2's revision 0 is "1". 0 < 1, so version1 < version2.
```

### Restrições:
- 1 <= version1.length, version2.length <= 500
- version1 and version2 only contain digits and '.'.
- version1 and version2 are valid version numbers.
- All the given revisions can be stored in a 32-bit integer.

---

## Abordagens

### Abordagem 1: Split e Compare
Divide ambas as versões por '.', parse cada revisão como inteiro, compara pairwise. Trata revisões faltantes como 0.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 2: Dois Ponteiros (Sem Split)
Parse revisões on-the-fly usando dois ponteiros, evitando overhead do split.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 3: StringTokenizer
Usa StringTokenizer para parsing (legado mas funcional).
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 4: Stream-based (Java 8+)
Usando streams para estilo funcional.
- **Complexidade**: O(n) tempo, O(n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Split | O(n) | O(n) |
| Two Pointers | O(n) | O(1) |
| Tokenizer | O(n) | O(n) |
| Stream | O(n) | O(n) |

---

## Casos de Teste

1. **Exemplo 1**: "1.01" vs "1.001" -> 0
2. **Exemplo 2**: "1.0" vs "1.0.0" -> 0
3. **Exemplo 3**: "0.1" vs "1.1" -> -1
4. **version1 > version2**: "1.0.1" vs "1" -> 1
5. **version1 < version2**: "1.0" vs "1.0.1" -> -1
6. **Leading zeros**: "1.0001" vs "1.1" -> 0 (0001 == 1)
7. **Múltiplos leading zeros**: "001.002.003" vs "1.2.3" -> 0
8. **Comprimentos diferentes**: "1.2.3.4.5" vs "1.2.3" -> 1
9. **Números grandes**: "2147483647" vs "2147483646" -> 1
10. **Versões iguais**: "1.2.3" vs "1.2.3" -> 0
11. **Dígito único vs multi-dígito**: "1.10" vs "1.2" -> 1
12. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.