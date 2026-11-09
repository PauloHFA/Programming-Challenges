# LeetCode 150 - Evaluate Reverse Polish Notation

## Problema

Você recebe um array de strings `tokens` que representa uma expressão aritmética em Notação Polonesa Reversa (Reverse Polish Notation). Avalie a expressão. Retorne um inteiro que representa o valor da expressão.

Note que:
- Os operadores válidos são '+', '-', '*', e '/'.
- Cada operando pode ser um inteiro ou outra expressão.
- A divisão entre dois inteiros sempre trunca em direção a zero.
- Não haverá divisão por zero.
- A entrada representa uma expressão aritmética válida em notação polonesa reversa.
- A resposta e todos os cálculos intermediários podem ser representados em um inteiro de 32 bits.

### Exemplos

**Exemplo 1:**
```
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
```

**Exemplo 2:**
```
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
```

**Exemplo 3:**
```
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
```

### Restrições
- 1 <= tokens.length <= 10^4
- tokens[i] é um operador: "+", "-", "*", "/", ou um inteiro no intervalo [-200, 200].

---

## Abordagens

### Abordagem 1: Stack - Solução Padrão

**Intuição:** Itere pelos tokens. Se operando, empilhe. Se operador, desempilhe dois operandos, aplique operação, empilhe resultado.

**Algoritmo:**
1. Crie uma stack vazia
2. Para cada token:
   - Se operando: push para stack
   - Se operador: pop dois valores, aplique operação, push resultado
3. Retorne o topo da stack

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 2: Array como Stack (Ligeiramente mais rápido)

**Intuição:** Use um array como stack para evitar overhead da classe Stack.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 3: Recursiva (usando referência de índice)

**Intuição:** Processe tokens da direita para a esquerda recursivamente.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) pilha de recursão

---

### Abordagem 4: Usando Deque (ArrayDeque)

**Intuição:** Use ArrayDeque como stack (mais performático que Stack em Java).

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Stack | O(n) | O(n) | Padrão, legível |
| Array | O(n) | O(n) | **Mais rápido** - sem overhead |
| Recursiva | O(n) | O(n) | Elegante, mas usa pilha |
| ArrayDeque | O(n) | O(n) | **Recomendado** - performance Java |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| ["2","1","+","3","*"] | 9 | Exemplo 1 |
| ["4","13","5","/","+"] | 6 | Exemplo 2 |
| ["10","6","9","3","+","-11","*","/","*","17","+","5","+"] | 22 | Exemplo 3 |
| ["5"] | 5 | Número único |
| ["-2","3","+"] | 1 | Números negativos |
| ["7","-3","/"] | -2 | Divisão trunca para zero |
| ["3","4","+","2","*","7","/"] | 2 | Expressão complexa |

---

## Implementação

O arquivo `Solution.java` contém todas as 4 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 150 - Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/)
- Reverse Polish Notation (Postfix Notation)
- Stack-based Expression Evaluation