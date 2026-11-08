# 0069 - Sqrt(x)

## Descrição

Dado um inteiro não negativo `x`, calcule e retorne a raiz quadrada de `x`.

Como o tipo de retorno é um inteiro, os dígitos decimais são truncados e apenas a parte inteira do resultado é retornada.

**Nota:** Você não pode usar nenhuma função de expoente ou operador embutido, como `pow(x, 0.5)` ou `x ** 0.5`.

## Exemplos

**Exemplo 1:**
```
Input: x = 4
Output: 2
```

**Exemplo 2:**
```
Input: x = 8
Output: 2
Explanation: A raiz quadrada de 8 é 2.82842..., e como a parte decimal é truncada, 2 é retornado.
```

## Restrições

- `0 <= x <= 2^31 - 1`

## Abordagem

### Método 1: Busca Binária (Principal)
- Busca no intervalo [1, x/2] (para x >= 2)
- Compara `mid * mid` com x (usando long para evitar overflow)
- **Tempo:** O(log x), **Espaço:** O(1)

### Método 2: Método de Newton
- Fórmula iterativa: `r = (r + x/r) / 2`
- Convergência quadrática (muito rápida)
- **Tempo:** O(log log x), **Espaço:** O(1)

### Método 3: Math.sqrt (Referência)
- Usa a biblioteca padrão para verificação
- **Tempo:** O(1), **Espaço:** O(1)

### Método 4: Manipulação de Bits
- Algoritmo de raiz quadrada inteira baseado em bits
- **Tempo:** O(log x), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Busca Binária | O(log x) | O(1) |
| Newton | O(log log x) | O(1) |
| Math.sqrt | O(1) | O(1) |
| Bits | O(log x) | O(1) |

## Implementação

A solução principal usa **Busca Binária** por ser intuitiva e eficiente.

```java
public int mySqrt(int x) {
    if (x < 2) return x;
    
    int left = 1, right = x / 2;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        long square = (long) mid * mid;
        
        if (square == x) {
            return mid;
        } else if (square < x) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return right;
}
```

## Casos de Teste

- `0` → `0`
- `1` → `1`
- `2` → `1`
- `3` → `1`
- `4` → `2`
- `8` → `2`
- `9` → `3`
- `15` → `3`
- `16` → `4`
- `17` → `4`
- `24` → `4`
- `25` → `5`
- `26` → `5`
- `100` → `10`
- `101` → `10`
- `2147395599` → `46339` (próximo ao limite)
- `2147483647` → `46340` (Integer.MAX_VALUE)