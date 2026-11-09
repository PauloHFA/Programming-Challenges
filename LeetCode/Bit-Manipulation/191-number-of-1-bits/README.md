# LeetCode 191 - Number of 1 Bits

## Problema

Write a function that takes the binary representation of a positive integer and returns the number of '1' bits it has (also known as the Hamming weight).

Note:
- In Java, there is no unsigned integer type. The input will be given as a signed integer type.
- In Java, the compiler represents the signed integers using 2's complement notation.

### Exemplo 1:
```
Input: n = 00000000000000000000000000001011 (11)
Output: 3
```

### Exemplo 2:
```
Input: n = 00000000000000000000000010000000 (128)
Output: 1
```

### Exemplo 3:
```
Input: n = 11111111111111111111111111111101 (-3 signed)
Output: 31
```

### Restrições:
- The input must be a binary string of length 32.

### Follow up:
If this function is called many times, how would you optimize it?

---

## Abordagens

### Abordagem 1: Bit a Bit (Padrão)
Verifica cada bit (LSB) usando `n & 1`, depois desloca para direita (`>>>`).
- **Complexidade**: O(32) = O(1) tempo, O(1) espaço

### Abordagem 2: Algoritmo de Brian Kernighan (Ótimo)
`n & (n - 1)` limpa o bit menos significativo definido. Repete até n = 0.
- **Complexidade**: O(k) onde k = número de bits 1, O(1) espaço

### Abordagem 3: Integer.bitCount (Built-in Java)
Usa método nativo do Java.
- **Complexidade**: O(1) tempo, O(1) espaço

### Abordagem 4: Lookup Table (Byte a Byte)
Pré-computa contagem para cada byte (0-255), depois soma 4 bytes.
- **Complexidade**: O(1) tempo, O(256) espaço

### Abordagem 5: Soma Paralela (SWAR)
Soma bits em paralelo usando máscaras (SIMD Within A Register).
- **Complexidade**: O(1) tempo (5 operações), O(1) espaço

### Abordagem 6: Recursiva
Versão recursiva da abordagem bit a bit.
- **Complexidade**: O(32) tempo, O(32) espaço (call stack)

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Bit a Bit | O(1) | O(1) |
| Kernighan | O(k) | O(1) |
| Built-in | O(1) | O(1) |
| Lookup Table | O(1) | O(256) |
| Paralela (SWAR) | O(1) | O(1) |
| Recursiva | O(1) | O(1) |

---

## Casos de Teste

1. **11** (0x0000000B) -> 3
2. **128** (0x00000080) -> 1
3. **-3** (0xFFFFFFFD) -> 31
4. **0** -> 0
5. **-1** (0xFFFFFFFF) -> 32
6. **1024** (1 << 10) -> 1
7. **0xAAAAAAAA** -> 16
8. **0x0000FFFF** -> 16
9. **12345** -> 6
10. **Integer.MAX_VALUE** -> 31

---

## Implementação

A solução está em `Solution.java` com 6 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.