# LeetCode 157 - Read N Characters Given Read4

## Problema

Given a file and assume that you can only read the file using a given method read4, implement a method read to read n characters. Your method read may be called multiple times.

### Method read4:
The API read4 reads 4 consecutive characters from the file, then writes those characters into the buffer array buf4. The return value is the number of actual characters read.

### Method read:
By using the read4 method, implement the method read that reads n characters from the file and store it in the buffer array buf. The return value is the number of actual characters read.

### Exemplo 1:
```
Input: file = "abc", n = 4
Output: 3
Explanation: After calling your read method, buf should contain "abc". We read a total of 3 characters from the file.
```

### Exemplo 2:
```
Input: file = "abcde", n = 5
Output: 5
Explanation: After calling your read method, buf should contain "abcde". We read a total of 5 characters from the file.
```

### Exemplo 3:
```
Input: file = "abcdABCD1234", n = 12
Output: 12
Explanation: After calling your read method, buf should contain "abcdABCD1234". We read a total of 12 characters from the file.
```

### Restrições:
- 1 <= file.length <= 500
- 1 <= n <= 500
- file consists of English letters and digits.
- read4 will only be called with valid parameters.

### Follow up: The read method may be called multiple times.

---

## Abordagens

### Abordagem 1: Buffer com Queue (Queue Buffer)
Usa uma fila para bufferizar caracteres extras lidos pelo read4. Quando read é chamado, primeiro consome da fila, depois chama read4 conforme necessário.
- **Complexidade**: O(n) tempo, O(1) espaço (amortizado - fila guarda no máximo 3 caracteres)

### Abordagem 2: Buffer Interno com Array (Internal Buffer Array)
Usa um buffer interno de tamanho fixo (4) e rastreia posições de início/fim. Mais eficiente que queue para este problema específico.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 3: Loop Simples com read4 (Simple Loop)
Chama read4 diretamente em um loop, copiando para buf. Mais simples mas pode chamar read4 mais vezes que o necessário.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 4: Otimizado com Estado Interno (Optimized with Internal State)
Similar à Abordagem 2 mas com gerenciamento explícito de estado. Lida corretamente com múltiplas chamadas de read().
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Queue Buffer | O(n) | O(1) |
| Internal Buffer | O(n) | O(1) |
| Simple Loop | O(n) | O(1) |
| Optimized | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: file="abc", n=4 -> 3 caracteres ("abc")
2. **Exemplo 2**: file="abcde", n=5 -> 5 caracteres ("abcde")
3. **Exemplo 3**: file="abcdABCD1234", n=12 -> 12 caracteres
4. **n maior que arquivo**: file="hello", n=10 -> 5 caracteres
5. **n menor que arquivo**: file="hello world", n=5 -> 5 caracteres ("hello")
6. **Múltiplo exato de 4**: file="abcdabcd", n=8 -> 8 caracteres
7. **Múltiplas chamadas read()**: Testa follow-up com chamadas sequenciais
8. **Arquivo vazio**: file="", n=5 -> 0 caracteres
9. **Caractere único**: file="a", n=1 -> 1 caractere
10. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`. Inclui simulação da API read4 para testes locais.