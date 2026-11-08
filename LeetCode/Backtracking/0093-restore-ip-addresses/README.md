# 0093 - Restore IP Addresses

## Descrição

Dada uma string `s` contendo apenas dígitos, retorne todos os endereços IP válidos que podem ser obtidos inserindo **três pontos** em `s`. Você não pode reordenar ou remover dígitos.

Um endereço IP válido consiste em exatamente **quatro inteiros** (cada um entre 0 e 255) separados por pontos. Cada inteiro não pode ter **zeros à esquerda** (exceto o próprio 0).

## Exemplos

**Exemplo 1:**
```
Input: s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
```

**Exemplo 2:**
```
Input: s = "0000"
Output: ["0.0.0.0"]
```

**Exemplo 3:**
```
Input: s = "1111"
Output: ["1.1.1.1"]
```

## Restrições

- `1 <= s.length <= 20` (na prática, 4 a 12 para ter solução)
- `s` consiste apenas de dígitos

## Abordagem

### Método 1: Backtracking (Principal)
- Tenta colocar 3 pontos recursivamente
- Em cada passo, tenta segmentos de tamanho 1, 2, 3
- Valida: sem leading zeros (exceto "0"), valor <= 255
- Para quando tem 4 segmentos e usou todos os caracteres
- **Tempo:** O(1) - limitado (máx 3^4 = 81 tentativas), **Espaço:** O(1) para resultado

### Método 2: Iterativo com 3 Loops
- Três loops aninhados para posições dos 3 pontos
- i, j, k são índices de fim dos 3 primeiros segmentos
- Verifica os 4 segmentos formados
- **Tempo:** O(1), **Espaço:** O(1)

### Método 3: Bitmask
- Escolhe 3 posições entre n-1 possíveis para os pontos
- Itera sobre todas as combinações de 3 bits em n-1 posições
- **Tempo:** O(1), **Espaço:** O(1)

### Método 4: Backtracking com Set
- Mesmo backtracking mas usa Set para evitar duplicatas
- **Tempo:** O(1), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Backtracking | O(1)* | O(1)* |
| Iterativo | O(1)* | O(1)* |
| Bitmask | O(1)* | O(1)* |
| Backtrack + Set | O(1)* | O(1)* |

*Limitado por constantes: máx 3^4 = 81 caminhos, C(11,3) = 165 combinações

## Implementação

A solução principal usa **Backtracking**.

```java
public List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<>();
    if (s == null || s.length() < 4 || s.length() > 12) return result;
    backtrack(s, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(String s, int start, List<String> segments, List<String> result) {
    if (segments.size() == 4) {
        if (start == s.length()) {
            result.add(String.join(".", segments));
        }
        return;
    }
    
    for (int len = 1; len <= 3; len++) {
        if (start + len > s.length()) break;
        
        String segment = s.substring(start, start + len);
        if (isValidSegment(segment)) {
            segments.add(segment);
            backtrack(s, start + len, segments, result);
            segments.remove(segments.size() - 1);
        }
    }
}

private boolean isValidSegment(String segment) {
    if (segment.length() > 1 && segment.charAt(0) == '0') return false;
    int val = Integer.parseInt(segment);
    return val <= 255;
}
```

## Casos de Teste

- `"25525511135"` → `["255.255.11.135","255.255.111.35"]`
- `"0000"` → `["0.0.0.0"]`
- `"1111"` → `["1.1.1.1"]`
- `"010010"` → `["0.10.0.10","0.100.1.0"]` (leading zeros invalid)
- `"1234"` → `["1.2.3.4"]`
- `"123456789012"` → `[]` (length 12, no valid)
- `"1010"` → `["1.0.1.0","10.1.0","10.10"]`
- `"255255255255"` → `["255.255.255.255"]`
- `"123"` → `[]` (too short)
- `"1234567890123"` → `[]` (too long)
- `"101023"` → `["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]`