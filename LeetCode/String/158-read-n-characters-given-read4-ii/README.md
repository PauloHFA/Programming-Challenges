# LeetCode 158 - Read N Characters Given Read4 II - Call multiple times

## Problema

Given a file and assume that you can only read the file using a given method read4, implement a method read to read n characters. Your method read may be called multiple times.

This is the follow-up to problem 157 where read() can be called multiple times. The key difference is that we need to maintain state between calls.

### Method read4:
The API read4 reads 4 consecutive characters from the file, then writes those characters into the buffer array buf4. The return value is the number of actual characters read.

### Method read:
By using the read4 method, implement the method read that reads n characters from the file and store it in the buffer array buf. The return value is the number of actual characters read.

### Exemplo 1:
```
Input: file = "abc", queries = [1,2,1]
Output: [1,2,1]
Explanation: 
read(1) -> "a", returns 1
read(2) -> "bc", returns 2
read(1) -> "", returns 0 (EOF)
```

### Exemplo 2:
```
Input: file = "abc", queries = [4,1]
Output: [3,0]
Explanation:
read(4) -> "abc", returns 3
read(1) -> "", returns 0 (EOF)
```

### Restrições:
- 1 <= file.length <= 500
- 1 <= n <= 500
- file consists of English letters and digits.
- read4 will only be called with valid parameters.
- The read method may be called multiple times.

---

## Abordagens

### Abordagem 1: Buffer Interno com Estado (Internal Buffer with State)
Mantém um buffer interno (tamanho 4) e rastreia a posição dentro dele. O estado persiste entre chamadas de read().
- **Complexidade**: O(n) por chamada read, O(1) espaço

### Abordagem 2: Buffer Baseado em Queue (Queue-based Buffer)
Usa uma fila para bufferizar caracteres extras. Mais intuitivo mas com leve overhead.
- **Complexidade**: O(n) por chamada read, O(1) espaço (amortizado - fila guarda no máximo 3 caracteres)

### Abordagem 3: Buffer Circular (Circular Buffer)
Usa abordagem de buffer circular com ponteiros head/tail explícitos.
- **Complexidade**: O(n) por chamada read, O(1) espaço

### Abordagem 4: Buffer com StringBuilder (StringBuilder Buffer)
Usa StringBuilder como buffer interno. Simples e limpo.
- **Complexidade**: O(n) por chamada read, O(1) espaço - StringBuilder guarda no máximo 3 chars

---

## Complexidade

| Abordagem | Tempo (por read) | Espaço |
|-----------|------------------|--------|
| Internal Buffer | O(n) | O(1) |
| Queue Buffer | O(n) | O(1) |
| Circular Buffer | O(n) | O(1) |
| StringBuilder | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: file="abc", queries=[1,2,1] -> [1,2,0] ("a", "bc", "")
2. **Exemplo 2**: file="abc", queries=[4,1] -> [3,0] ("abc", "")
3. **Múltiplas leituras pequenas**: file="hello world", 11 leituras de 1 char
4. **Leitura maior que restante**: file="abc", queries=[2,5] -> [2,1] ("ab", "c")
5. **Leituras exatas**: file="abcdabcd", queries=[4,4] -> [4,4] ("abcd", "abcd")
6. **Arquivo vazio**: file="", queries=[1,2] -> [0,0]
7. **Arquivo de 1 char, múltiplas leituras**: file="a", queries=[1,1,1] -> [1,0,0]
8. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`. Inclui simulação da API read4 para testes locais.