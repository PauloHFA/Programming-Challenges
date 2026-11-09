# LeetCode 186 - Reverse String

## Problema

Write a function that reverses a string. The input string is given as an array of characters `s`. You must do this by modifying the input array in-place with O(1) extra memory.

### Exemplo 1:
```
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
```

### Exemplo 2:
```
Input: s = ["H","a","n","n","a","h"]
Output: ["h","a","n","n","a","H"]
```

### Restrições:
- 1 <= s.length <= 10^5
- s[i] is a printable ascii character.

---

## Abordagens

### Abordagem 1: Dois Ponteiros (Ótima - In-place)
Usa dois ponteiros (início e fim) e troca os caracteres até se encontrarem no meio.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 2: Recursiva
Versão recursiva da abordagem de dois ponteiros.
- **Complexidade**: O(n) tempo, O(n) espaço (call stack)

### Abordagem 3: Collections.reverse (para List)
Converte para List, usa Collections.reverse, converte de volta.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 4: StringBuilder (Referência - não in-place)
Usa StringBuilder.reverse() e copia de volta.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 5: XOR Swap (In-place, sem variável temporária)
Troca valores usando operação XOR bit a bit.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Dois Ponteiros | O(n) | O(1) |
| Recursiva | O(n) | O(n) |
| Collections.reverse | O(n) | O(n) |
| StringBuilder | O(n) | O(n) |
| XOR Swap | O(n) | O(1) |

---

## Casos de Teste

1. **["h","e","l","l","o"]** -> ["o","l","l","e","h"]
2. **["H","a","n","n","a","h"]** -> ["h","a","n","n","a","H"]
3. **["a"]** -> ["a"]
4. **["a","b"]** -> ["b","a"]
5. **["a","b","c","d"]** -> ["d","c","b","a"]
6. **["a","b","c","d","e"]** -> ["e","d","c","b","a"]
7. **["!","@","#","$"]** -> ["$","#","@","!"]
8. **Array grande (1000 chars)** -> Reverso correto

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.