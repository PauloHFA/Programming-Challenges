# 0038 - Count and Say

## Descrição do Problema

A sequência **Count and Say** é uma sequência de strings de dígitos definida pela fórmula recursiva:

- `countAndSay(1) = "1"`
- `countAndSay(n)` é a forma como você "diz" a string do dígito de `countAndSay(n-1)`, que é então convertida em uma string de dígitos diferente.

Para determinar como você "diz" uma string de dígitos, divida-a no **número mínimo** de substrings tais que cada substring contenha exatamente **um dígito único**. Então, para cada substring, diga o número de dígitos e depois diga o dígito. Finalmente, concatene cada dígito dito.

Por exemplo, a forma de dizer `"3322251"`:
- Dois 3's → "23"
- Três 2's → "32"
- Um 5 → "15"
- Um 1 → "11"
- Resultado: `"23321511"`

## Exemplos

### Exemplo 1:
```
Input: n = 1
Output: "1"
Explanation: Este é o caso base.
```

### Exemplo 2:
```
Input: n = 4
Output: "1211"
Explanation:
countAndSay(1) = "1"
countAndSay(2) = "11" (um 1)
countAndSay(3) = "21" (dois 1's)
countAndSay(4) = "1211" (um 2, um 1)
```

## Restrições

- `1 <= n <= 30`

## Abordagem

### Iterativo (Recomendado)

Construímos a sequência iterativamente de 1 até n:

1. Começamos com `"1"` (caso base n=1).
2. Para cada i de 2 a n:
   - Percorremos a string anterior e contamos grupos consecutivos do mesmo dígito.
   - Para cada grupo, adicionamos a contagem seguida do dígito à nova string.
3. Retornamos a string final.

### Recursivo

Também é possível implementar recursivamente, mas a versão iterativa é mais eficiente em termos de espaço (evita pilha de recursão).

### Complexidade

- **Tempo:** O(2^n) aproximadamente - cada termo aproximadamente dobra de tamanho
- **Espaço:** O(2^n) para armazenar a string de saída

A sequência cresce exponencialmente: 1, 2, 2, 4, 6, 6, 8, 10, 14, 20, ...

## Implementação

A solução está em `Solution.java` com duas versões (recursiva e iterativa) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```