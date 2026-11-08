# 0066 - Plus One

## Descrição

Dado um array de inteiros `digits` representando um inteiro não negativo, incremente o inteiro em 1.

Os dígitos são armazenados de forma que o dígito mais significativo esteja no início do array, e cada elemento do array contém um único dígito.

Você pode assumir que o inteiro não contém zeros à esquerda, exceto o próprio número 0.

## Exemplos

**Exemplo 1:**
```
Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: O array representa o inteiro 123. Incrementando em 1 resulta em 124.
```

**Exemplo 2:**
```
Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: O array representa o inteiro 4321. Incrementando em 1 resulta em 4322.
```

**Exemplo 3:**
```
Input: digits = [9]
Output: [1,0]
Explanation: O array representa o inteiro 9. Incrementando em 1 resulta em 10.
```

## Restrições

- `1 <= digits.length <= 100`
- `0 <= digits[i] <= 9`
- `digits` não contém zeros à esquerda.

## Abordagem

### Método 1: Iteração Reversa com Retorno Antecipado (Otimizado)
- Percorre o array do final para o início
- Se o dígito for menor que 9, incrementa e retorna imediatamente
- Se for 9, define como 0 e continua
- Se todos forem 9, cria novo array com 1 no início
- **Tempo:** O(n), **Espaço:** O(1) ou O(n) no pior caso

### Método 2: Usando Carry
- Simula a adição manual com carry
- Mais genérico, funciona para qualquer valor de incremento
- **Tempo:** O(n), **Espaço:** O(1) ou O(n) no pior caso

### Método 3: BigInteger (Para números muito grandes)
- Converte para BigInteger, incrementa, converte de volta
- Útil se o array for muito grande (além do limite de int/long)
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Iteração Reversa | O(n) | O(1)* |
| Carry | O(n) | O(1)* |
| BigInteger | O(n) | O(n) |

*O(1) espaço adicional, exceto no caso onde todos os dígitos são 9 (requer novo array)

## Implementação

A solução principal usa o **Método 1** por ser o mais eficiente e direto para este problema específico.

```java
public int[] plusOne(int[] digits) {
    int n = digits.length;
    
    for (int i = n - 1; i >= 0; i--) {
        if (digits[i] < 9) {
            digits[i]++;
            return digits;
        }
        digits[i] = 0;
    }
    
    int[] result = new int[n + 1];
    result[0] = 1;
    return result;
}
```

## Casos de Teste

- `[1,2,3]` → `[1,2,4]`
- `[4,3,2,1]` → `[4,3,2,2]`
- `[9]` → `[1,0]`
- `[9,9,9]` → `[1,0,0,0]`
- `[1,9,9]` → `[2,0,0]`
- `[0]` → `[1]`
- `[8,9,9,9]` → `[9,0,0,0]`
- `[1,2,9]` → `[1,3,0]`