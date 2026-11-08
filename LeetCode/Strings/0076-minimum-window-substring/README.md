# 0076 - Minimum Window Substring

## Descrição

Dadas duas strings `s` e `t`, retorne a substring mínima de `s` tal que todos os caracteres de `t` (incluindo duplicatas) estejam incluídos na janela. Se não existir tal substring, retorne a string vazia `""`.

## Exemplos

**Exemplo 1:**
```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: A substring mínima "BANC" contém A, B e C.
```

**Exemplo 2:**
```
Input: s = "a", t = "a"
Output: "a"
```

**Exemplo 3:**
```
Input: s = "a", t = "aa"
Output: ""
Explanation: Não há dois 'a' em s.
```

## Restrições

- `m == s.length`
- `n == t.length`
- `1 <= m, n <= 10^5`
- `s` e `t` consistem de letras maiúsculas e minúsculas do inglês

## Abordagem

### Método 1: Sliding Window com HashMap (Principal)
- Usa dois ponteiros (left, right) para manter uma janela deslizante
- Expande `right` até que a janela contenha todos os caracteres de `t`
- Contrai `left` para encontrar a janela mínima válida
- Mantém contagem de caracteres necessários e formados
- **Tempo:** O(m + n), **Espaço:** O(m + n) no pior caso

### Método 2: Sliding Window com Array (Otimizado para ASCII)
- Usa array de tamanho 128 para caracteres ASCII
- Mais rápido que HashMap para caracteres limitados
- **Tempo:** O(m + n), **Espaço:** O(1) - array fixo

### Método 3: Força Bruta (Apenas para verificação)
- Verifica todas as substrings possíveis
- **Tempo:** O(m² × n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| HashMap | O(m + n) | O(m + n) |
| Array (ASCII) | O(m + n) | O(1) |
| Força Bruta | O(m² × n) | O(1) |

## Implementação

A solução principal usa **Sliding Window com HashMap**.

```java
public String minWindow(String s, String t) {
    if (s == null || t == null || s.length() < t.length()) {
        return "";
    }
    
    Map<Character, Integer> targetCount = new HashMap<>();
    for (char c : t.toCharArray()) {
        targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
    }
    
    int required = targetCount.size();
    int formed = 0;
    Map<Character, Integer> windowCount = new HashMap<>();
    
    int left = 0, right = 0;
    int minLen = Integer.MAX_VALUE;
    int minLeft = 0;
    
    while (right < s.length()) {
        char c = s.charAt(right);
        windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);
        
        if (targetCount.containsKey(c) && windowCount.get(c).equals(targetCount.get(c))) {
            formed++;
        }
        
        while (left <= right && formed == required) {
            c = s.charAt(left);
            
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minLeft = left;
            }
            
            windowCount.put(c, windowCount.get(c) - 1);
            if (targetCount.containsKey(c) && windowCount.get(c) < targetCount.get(c)) {
                formed--;
            }
            
            left++;
        }
        
        right++;
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
}
```

## Casos de Teste

- `"ADOBECODEBANC", "ABC"` → `"BANC"`
- `"a", "a"` → `"a"`
- `"a", "aa"` → `""`
- `"ab", "b"` → `"b"`
- `"aa", "aa"` → `"aa"`
- `"cabwefgewcwaefgcf", "cae"` → `"cwae"`
- `"bba", "ab"` → `"ba"`
- `"aaflslflsldkalskaaa", "aaa"` → `"aaa"`