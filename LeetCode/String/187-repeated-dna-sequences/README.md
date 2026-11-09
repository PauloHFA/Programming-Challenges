# LeetCode 187 - Repeated DNA Sequences

## Problema

The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'. For example, "ACGAATTCCG" is a DNA sequence. When studying DNA, it is useful to identify repeated sequences within the DNA.

Given a string `s` that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule. You may return the answer in any order.

### Exemplo 1:
```
Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
Output: ["AAAAACCCCC","CCCCCAAAAA"]
```

### Exemplo 2:
```
Input: s = "AAAAAAAAAAAAA"
Output: ["AAAAAAAAAA"]
```

### Restrições:
- 1 <= s.length <= 10^5
- s[i] is either 'A', 'C', 'G', or 'T'.

---

## Abordagens

### Abordagem 1: HashSet + HashSet (Ótima)
Usa um set para sequências vistas e outro para repetidas. Se `add()` retorna false, já foi vista.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 2: HashMap com Contagem
Conta ocorrências de cada sequência de 10 letras.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 3: Bit Manipulation (Rolling Hash)
Codifica A=00, C=01, G=10, T=11. 10 letras = 20 bits cabem em int. Usa rolling hash.
- **Complexidade**: O(n) tempo, O(1) espaço (array fixo de 2^20)

### Abordagem 4: Sliding Window com StringBuilder
Mantém janela deslizante de 10 chars com StringBuilder.
- **Complexidade**: O(n) tempo, O(n) espaço

### Abordagem 5: Array para Bit Encoding (Mais Rápido)
Similar à abordagem 3 mas usa array primitivo em vez de HashMap/HashSet.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| HashSet + HashSet | O(n) | O(n) |
| HashMap | O(n) | O(n) |
| Bit Manipulation | O(n) | O(1) |
| StringBuilder | O(n) | O(n) |
| Array Bit Encoding | O(n) | O(1) |

---

## Casos de Teste

1. **"AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"** -> ["AAAAACCCCC","CCCCCAAAAA"]
2. **"AAAAAAAAAAAAA"** -> ["AAAAAAAAAA"]
3. **"ACGTACGTAC"** -> []
4. **"AAAAAAAAAA"** -> [] (aparece apenas uma vez)
5. **"AAAAA"** -> [] (menos que 10)
6. **"AAAAAAAAAAAAAAAAAAAA"** -> ["AAAAAAAAAA"]
7. **"AAAAAAAAAAA"** -> ["AAAAAAAAAA"] (overlapping)
8. **"ACGAATTCCGACGAATTCCG"** -> ["ACGAATTCCG"]

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.