# 0067 - Add Binary

## Descrição

Dadas duas strings binárias `a` e `b`, retorne a soma delas como uma string binária.

## Exemplos

**Exemplo 1:**
```
Input: a = "11", b = "1"
Output: "100"
```

**Exemplo 2:**
```
Input: a = "1010", b = "1011"
Output: "10101"
```

## Restrições

- `1 <= a.length, b.length <= 10^4`
- `a` e `b` consistem apenas de caracteres '0' ou '1'
- Cada string não contém zeros à esquerda, exceto para o próprio zero

## Abordagem

### Método 1: Adição Binária Manual (Otimizado)
- Percorre ambas as strings do final para o início
- Mantém um carry
- Adiciona bit a bit: `sum = bitA + bitB + carry`
- Resultado bit = `sum % 2`, novo carry = `sum / 2`
- **Tempo:** O(max(n, m)), **Espaço:** O(max(n, m))

### Método 2: BigInteger
- Converte strings binárias para BigInteger base 2
- Soma e converte de volta para base 2
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Padding Manual
- Preenche a string menor com zeros à esquerda
- Processa bit a bit
- **Tempo:** O(max(n, m)), **Espaço:** O(max(n, m))

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Manual (Principal) | O(max(n, m)) | O(max(n, m)) |
| BigInteger | O(n) | O(n) |
| Padding | O(max(n, m)) | O(max(n, m)) |

## Implementação

A solução principal usa o **Método 1** por ser eficiente e não depender de bibliotecas externas.

```java
public String addBinary(String a, String b) {
    StringBuilder result = new StringBuilder();
    int i = a.length() - 1;
    int j = b.length() - 1;
    int carry = 0;
    
    while (i >= 0 || j >= 0 || carry > 0) {
        int sum = carry;
        if (i >= 0) sum += a.charAt(i--) - '0';
        if (j >= 0) sum += b.charAt(j--) - '0';
        
        result.append(sum % 2);
        carry = sum / 2;
    }
    
    return result.reverse().toString();
}
```

## Casos de Teste

- `"11" + "1"` → `"100"`
- `"1010" + "1011"` → `"10101"`
- `"0" + "0"` → `"0"`
- `"1" + "1"` → `"10"`
- `"1111" + "1111"` → `"11110"`
- `"101010" + "101010"` → `"1010100"`
- `"1" + "111"` → `"1000"`
- `"111" + "1"` → `"1000"`