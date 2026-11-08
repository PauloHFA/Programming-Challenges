# 0050 - Pow(x, n)

## Descrição do Problema

Implemente `pow(x, n)`, que calcula `x` elevado à potência `n` (ou seja, `x^n`).

## Exemplos

### Exemplo 1:
```
Input: x = 2.00000, n = 10
Output: 1024.00000
```

### Exemplo 2:
```
Input: x = 2.10000, n = 3
Output: 9.26100
```

### Exemplo 3:
```
Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2^-2 = 1/2^2 = 1/4 = 0.25
```

## Restrições

- `-100.0 < x < 100.0`
- `-2^31 <= n <= 2^31 - 1`
- `n` é um inteiro.
- `-10^4 <= x^n <= 10^4`

## Abordagem

### Exponenciação Binária (Fast Power)

A abordagem ingênua de multiplicar `x` por si mesmo `n` vezes leva O(n) tempo. A exponenciação binária reduz para **O(log n)**.

**Ideia principal:**
- `x^n = (x^(n/2))^2` se n é par
- `x^n = x * (x^(n/2))^2` se n é ímpar

**Recursivo:**
```
fastPow(x, n):
    if n == 0: return 1
    half = fastPow(x, n/2)
    if n % 2 == 0: return half * half
    else: return half * half * x
```

**Iterativo (O(1) espaço):**
- Mantemos `result` (acumulador) e `currentProduct` (x^(2^i))
- Para cada bit de n (da direita para esquerda):
  - Se bit é 1: multiplicamos `result` por `currentProduct`
  - Elevamos `currentProduct` ao quadrado
  - Deslocamos n para direita

**Tratamento de n negativo:**
- Se n < 0: x = 1/x, n = -n
- Usamos `long` para evitar overflow com `Integer.MIN_VALUE`

### Complexidade

- **Tempo:** O(log n)
- **Espaço:** O(log n) recursivo, O(1) iterativo

## Implementação

A solução está em `Solution.java` com ambas as abordagens (recursiva e iterativa) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```