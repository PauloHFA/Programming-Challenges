# LeetCode 140 - Word Break II

## Problema

Dada uma string `s` e um dicionário de strings `wordDict`, adicione espaços em `s` para construir uma frase onde cada palavra é uma palavra válida do dicionário. Retorne todas as frases possíveis em qualquer ordem.

Note que a mesma palavra no dicionário pode ser reutilizada múltiplas vezes na segmentação.

### Exemplos

**Exemplo 1:**
```
Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]
```

**Exemplo 2:**
```
Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
```

**Exemplo 3:**
```
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []
```

### Restrições
- 1 <= s.length <= 20
- 1 <= wordDict.length <= 1000
- 1 <= wordDict[i].length <= 10
- s e wordDict[i] consistem apenas de letras minúsculas inglesas.
- Todas as strings de wordDict são únicas.

---

## Abordagens

### Abordagem 1: Backtracking com Memoização (DP + DFS) - Recomendada

**Intuição:** Primeiro use DP para verificar se uma solução existe (poda). Depois use DFS com memoização para construir todas as frases.
- `memo[i]` = lista de frases que podem ser formadas a partir de `s[i:]`

**Algoritmo:**
1. Use DP bottom-up para verificar se `s` pode ser segmentada (poda precoce)
2. Se não houver solução, retorne lista vazia
3. Use recursão com memoização: para cada índice inicial, tente todas as palavras válidas e combine com resultados do índice final

**Complexidade:**
- Tempo: O(n² * 2^n) no pior caso (exponencial devido ao tamanho da saída)
- Espaço: O(n²) para memo + O(n) pilha de recursão

---

### Abordagem 2: Backtracking Puro (sem poda DP)

**Intuição:** Versão mais simples mas pode explorar caminhos sem saída.

**Complexidade:**
- Tempo: O(n² * 2^n) pior caso
- Espaço: O(n) pilha de recursão + saída

---

### Abordagem 3: DP Construindo Frases Bottom-Up

**Intuição:** `dp[i]` = lista de frases para `s[0..i-1]`. Construa da esquerda para a direita.

**Complexidade:**
- Tempo: O(n² * 2^n) pior caso
- Espaço: O(n² * 2^n) para armazenar todas as frases

---

### Abordagem 4: Trie + Backtracking com Memoização

**Intuição:** Use Trie para correspondência de prefixo mais rápida.

**Complexidade:**
- Tempo: O(n * maxLen * 2^n) pior caso
- Espaço: O(n²) para memo + espaço da Trie

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Memoização (DP+DFS) | O(n² * 2^n) | O(n²) | **Recomendada** - poda + memo |
| Backtracking Puro | O(n² * 2^n) | O(n) | Simples, sem poda |
| DP Bottom-Up | O(n² * 2^n) | O(n² * 2^n) | Usa muito espaço |
| Trie + Memo | O(n * maxLen * 2^n) | O(n² + Trie) | Melhor para dicionários grandes |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| "catsanddog", ["cat","cats","and","sand","dog"] | ["cats and dog","cat sand dog"] | Exemplo 1 |
| "pineapplepenapple", ["apple","pen","applepen","pine","pineapple"] | 3 frases | Exemplo 2 |
| "catsandog", ["cats","dog","sand","and","cat"] | [] | Exemplo 3 - sem solução |
| "leetcode", ["leet","code"] | ["leet code"] | Duas palavras |
| "a", ["a"] | ["a"] | Caractere único |
| "aaaa", ["a","aa"] | 5 frases | Múltiplas combinações |

---

## Implementação

O arquivo `Solution.java` contém todas as 4 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 140 - Word Break II](https://leetcode.com/problems/word-break-ii/)
- Dynamic Programming + Backtracking
- Trie data structure