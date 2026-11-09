# LeetCode 161 - One Edit Distance

## Problema

Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.

A string s is said to be one edit distance apart from a string t if you can:
- Insert exactly one character into s to get t
- Delete exactly one character from s to get t
- Replace exactly one character of s to get t

### Exemplo 1:
```
Input: s = "ab", t = "acb"
Output: true
Explanation: We can insert 'c' into s to get t.
```

### Exemplo 2:
```
Input: s = "", t = ""
Output: false
Explanation: We cannot get t from s by only one edit.
```

### Exemplo 3:
```
Input: s = "a", t = ""
Output: true
Explanation: We can delete 'a' from s to get t.
```

### Restrições:
- 0 <= s.length, t.length <= 10^4
- s and t consist of lower-case letters, upper-case letters, and digits.

---

## Abordagens

### Abordagem 1: Dois Ponteiros (Two Pointers)
Compara caracteres de ambas as strings. Quando encontra mismatch, verifica os três casos:
1. Replace: pula ambos os caracteres
2. Insert: pula caractere em t (t é mais longa)
3. Delete: pula caractere em s (s é mais longa)
- **Complexidade**: O(min(m, n)) tempo, O(1) espaço

### Abordagem 2: Dois Ponteiros com Casos Explícitos
Tratamento mais explícito das três operações de edição.
- **Complexidade**: O(min(m, n)) tempo, O(1) espaço

### Abordagem 3: Programação Dinâmica (DP)
Edit distance DP padrão, mas só nos importa se distância == 1.
- **Complexidade**: O(m * n) tempo, O(min(m, n)) espaço

### Abordagem 4: Recursiva com Memoização
Abordagem recursiva com memoização para verificação.
- **Complexidade**: O(m * n) tempo, O(m * n) espaço

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| Two Pointers | O(min(m, n)) | O(1) |
| Explicit | O(min(m, n)) | O(1) |
| DP | O(m * n) | O(min(m, n)) |
| Recursive | O(m * n) | O(m * n) |

---

## Casos de Teste

1. **Exemplo 1**: s="ab", t="acb" -> true (insert 'c')
2. **Exemplo 2**: s="", t="" -> false
3. **Exemplo 3**: s="a", t="" -> true (delete 'a')
4. **Replace**: s="ab", t="ac" -> true
5. **Mesmas strings**: s="abc", t="abc" -> false
6. **Duas edições**: s="abc", t="def" -> false
7. **Diff > 1**: s="a", t="abc" -> false
8. **Insert início**: s="bc", t="abc" -> true
9. **Insert fim**: s="ab", t="abc" -> true
10. **Delete meio**: s="abc", t="ac" -> true
11. **Case sensitive**: s="a", t="A" -> true
12. **Dígitos**: s="123", t="124" -> true
13. **Verificação cruzada**: Todas as abordagens produzem resultados idênticos

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.