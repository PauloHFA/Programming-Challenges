# 0091 - Decode Ways

## Descrição

Uma mensagem contendo letras de A-Z pode ser **codificada** em números usando o seguinte mapeamento:

```
'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
```

Para **decodificar** uma mensagem codificada, todos os dígitos devem ser agrupados e mapeados de volta para letras usando o mapeamento inverso (pode haver múltiplas formas). Por exemplo, `"11106"` pode ser decodificado como:
- `"AAJF"` (1, 1, 10, 6)
- `"KJF"` (11, 10, 6)

Note que o agrupamento `(1, 11, 06)` é inválido porque `"06"` não pode ser mapeado para `'F'` (zero à esquerda não permitido).

Dada uma string `s` contendo apenas dígitos, retorne o **número de formas** de decodificá-la.

## Exemplos

**Exemplo 1:**
```
Input: s = "12"
Output: 2
Explanation: "12" pode ser decodificado como "AB" (1,2) ou "L" (12).
```

**Exemplo 2:**
```
Input: s = "226"
Output: 3
Explanation: "226" pode ser decodificado como "BBF" (2,2,6), "BZ" (2,26), ou "VF" (22,6).
```

**Exemplo 3:**
```
Input: s = "06"
Output: 0
Explanation: "06" não pode ser decodificado porque "06" ≠ "6" (zero à esquerda).
```

## Restrições

- `1 <= s.length <= 100`
- `s` contém apenas dígitos e pode conter zeros à esquerda.

## Abordagem

### Método 1: DP Bottom-Up O(1) Espaço (Principal)
- `dp[i]` = número de formas de decodificar `s[0..i-1]`
- Transição:
  - Se `s[i-1] != '0'`: `dp[i] += dp[i-1]` (dígito único)
  - Se `10 <= s[i-2..i-1] <= 26`: `dp[i] += dp[i-2]` (dois dígitos)
- Otimização: só precisa de `prev1` (dp[i-1]) e `prev2` (dp[i-2])
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: DP Array
- Mesma lógica mas mantém array completo
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Recursion com Memoização
- Top-down com cache
- **Tempo:** O(n), **Espaço:** O(n)

### Método 4: Recursion Pura
- Exponencial, apenas para verificação em strings pequenas (≤ 15)
- **Tempo:** O(2^n), **Espaço:** O(n) stack

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DP O(1) | O(n) | O(1) |
| DP Array | O(n) | O(n) |
| Memoização | O(n) | O(n) |
| Recursão Pura | O(2^n) | O(n) |

## Implementação

A solução principal usa **DP Bottom-Up O(1) Espaço**.

```java
public int numDecodings(String s) {
    if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
    
    int n = s.length();
    int prev2 = 1;  // dp[i-2]
    int prev1 = 1;  // dp[i-1]
    
    for (int i = 1; i <= n; i++) {
        int curr = 0;
        
        // Single digit
        if (s.charAt(i - 1) != '0') {
            curr += prev1;
        }
        
        // Two digits
        if (i >= 2) {
            int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                curr += prev2;
            }
        }
        
        prev2 = prev1;
        prev1 = curr;
        
        if (curr == 0) return 0;
    }
    
    return prev1;
}
```

## Casos de Teste

- `"12"` → `2` (AB, L)
- `"226"` → `3` (BBF, BZ, VF)
- `"06"` → `0` (inválido)
- `"1"` → `1`
- `"10"` → `1` (J)
- `"11"` → `2` (AA, K)
- `"26"` → `2` (BF, Z)
- `"27"` → `1` (BG)
- `"100"` → `0` (inválido)
- `"101"` → `1` (JA)
- `"110"` → `1` (KJ)
- `"111"` → `3` (AAA, AK, KA)
- `"1111"` → `5` (Fibonacci)
- `"1234"` → `3`
- `"1111111111"` → `89` (Fibonacci 11)
- `"0"` → `0`
- `"30"` → `0` (inválido)
- `"40"` → `0` (inválido)
- `"2611055971756562"` → `4`