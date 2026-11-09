# LeetCode 190 - Reverse Bits

## Problema

Reverse bits of a given 32 bits unsigned integer.

Note:
- In Java, there is no unsigned integer type. Both input and output will be given as a signed integer type. They should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
- In Java, the compiler represents the signed integers using 2's complement notation.

### Exemplo 1:
```
Input: n = 00000010100101000001111010011100 (43261596)
Output: 964176192 (00111001011110000010100101000000)
```

### Exemplo 2:
```
Input: n = 11111111111111111111111111111101 (4294967293 unsigned, -3 signed)
Output: 3221225471 (10111111111111111111111111111111 unsigned, -1073741825 signed)
```

### Restrições:
- The input must be a binary string of length 32

### Follow up:
If this function is called many times, how would you optimize it?

---

## Abordagens

### Abordagem 1: Bit a Bit (Padrão)
Verifica cada bit (LSB) e constrói o resultado deslocando para esquerda e adicionando o bit.
- **Complexidade**: O(32) = O(1) tempo, O(1) espaço

### Abordagem 2: Divide and Conquer (Otimizada)
Troca metades de 16 bits, depois quartos de 8 bits, etc. Usa máscaras pré-definidas.
- **Complexidade**: O(1) tempo (5 operações), O(1) espaço

### Abordagem 3: Lookup Table (Byte a Byte)
Pré-computa reversão para cada byte (0-255), depois combina 4 bytes.
- **Complexidade**: O(1) tempo, O(256) espaço para tabela

### Abordagem 4: Integer.reverse (Built-in Java)
Usa método nativo do Java.
- **Complexidade**: O(1) tempo, O(1) espaço

### Abordagem 5: Recursiva
Versão recursiva da abordagem bit a bit.
- **Complexidade**: O(32) tempo, O(32) espaço (call stack)

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Bit a Bit | O(1) | O(1) |
| Divide & Conquer | O(1) | O(1) |
| Lookup Table | O(1) | O(256) |
| Built-in | O(1) | O(1) |
| Recursiva | O(1) | O(1) |

---

## Casos de Teste

1. **43261596** (0x02941E9C) -> 964176192 (0x39782940)
2. **-3** (0xFFFFFFFD) -> -1073741825 (0xBFFFFFFF)
3. **0** -> 0
4. **-1** (0xFFFFFFFF) -> -1
5. **1** -> -2147483648 (0x80000000)
6. **-2147483648** (0x80000000) -> 1
7. **0xAAAAAAAA** -> 0x55555555
8. **0x55555555** -> 0xAAAAAAAA
9. **0x0000FFFF** -> 0xFFFF0000
10. **0xFFFF0000** -> 0x0000FFFF

---

## Implementação

A solução está em `Solution.java` com 5 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.