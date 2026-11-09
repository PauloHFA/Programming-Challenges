# LeetCode 159 - Longest Substring with At Most Two Distinct Characters

## Problema

Given a string s, return the length of the longest substring that contains at most two distinct characters.

### Exemplo 1:
```
Input: s = "eceba"
Output: 3
Explanation: The substring is "ece" which its length is 3.
```

### Exemplo 2:
```
Input: s = "ccaabbb"
Output: 5
Explanation: The substring is "aabbb" which its length is 5.
```

### Restrições:
- 1 <= s.length <= 10^5
- s consists of English letters.

---

## Abordagens

### Abordagem 1: Sliding Window com HashMap
Usa uma janela deslizante com HashMap para rastrear contagem de caracteres. Expande o ponteiro direito, quando mais de 2 caracteres distintos, encolhe o esquerdo.
- **Complexidade**: O(n) tempo, O(1) espaço (no máximo 3 entradas no map)

### Abordagem 2: Sliding Window com Array
Como s consiste de letras inglesas, usa array de tamanho 128 (ASCII). Mais eficiente que HashMap.
- **Complexidade**: O(n) tempo, O(1) espaço (array de tamanho fixo)

### Abordagem 3: Sliding Window com Últimas Posições
Rastreia a última posição de cada caractere. Quando temos 3 caracteres distintos, move left para min(lastPos) + 1.
- **Complexidade**: O(n) tempo, O(1) espaço

### Abordagem 4: Dois Ponteiros com Rastreamento de Caracteres
Rastreia os dois caracteres distintos mais recentes e suas últimas posições. Otimizado para exatamente 2 caracteres distintos.
- **Complexidade**: O(n) tempo, O(1) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| HashMap | O(n) | O(1) |
| Array | O(n) | O(1) |
| LastPos | O(n) | O(1) |
| Optimized | O(n) | O(1) |

---

## Casos de Teste

1. **Exemplo 1**: s="eceba" -> 3 ("ece")
2. **Exemplo 2**: s="ccaabbb" -> 5 ("aabbb")
3. **Todos iguais**: s="aaaaa" -> 5
4. **Alternados**: s="ababab" -> 6
5. **Três distintos**: s="abc" -> 2
6. **Caractere único**: s="a" -> 1
7. **Dois caracteres**: s="ab" -> 2
8. **Caso complexo**: s="abaccc" -> 4 ("accc")
9. **Outro complexo**: s="aabbcc" -> 4 ("aabb" ou "bbcc")
10. **String longa**: s="abcabcabc" -> 2
11. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.