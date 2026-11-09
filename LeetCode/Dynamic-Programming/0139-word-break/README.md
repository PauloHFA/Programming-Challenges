# LeetCode 139 - Word Break

## Problema

Dada uma string `s` e um dicionário de strings `wordDict`, retorne `true` se `s` puder ser segmentada em uma sequência separada por espaços de uma ou mais palavras do dicionário.

Note que a mesma palavra no dicionário pode ser reutilizada múltiplas vezes na segmentação.

### Exemplos

**Exemplo 1:**
```
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Retorne true porque "leetcode" pode ser segmentada como "leet code".
```

**Exemplo 2:**
```
Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Retorne true porque "applepenapple" pode ser segmentada como "apple pen apple".
```

**Exemplo 3:**
```
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false
```

### Restrições
- 1 <= s.length <= 300
- 1 <= wordDict.length <= 1000
- 1 <= wordDict[i].length <= 20
- s e wordDict[i] consistem apenas de letras minúsculas inglesas.
- Todas as strings de wordDict são únicas.

---

## Abordagens

### Abordagem 1: Programação Dinâmica - Bottom Up

**Intuição:** `dp[i] = true` se `s[0..i-1]` pode ser segmentada usando palavras do wordDict.
- `dp[0] = true` (string vazia)
- Para cada i de 1 a n:
  - Para cada j de 0 a i-1:
    - Se `dp[j] && wordDict.contains(s.substring(j, i))`: `dp[i] = true`

**Complexidade:**
- Tempo: O(n² * m) onde n = s.length, m = comprimento médio da palavra
- Espaço: O(n)

---

### Abordagem 2: DP com Otimização de Comprimento Máximo

**Intuição:** Verifique apenas substrings até o comprimento máximo da palavra no dicionário.

**Complexidade:**
- Tempo: O(n * maxLen * m) onde maxLen = comprimento máximo da palavra
- Espaço: O(n)

---

### Abordagem 3: BFS (Busca em Largura)

**Intuição:** Trate índices como nós. Comece do índice 0, tente alcançar o índice n. Use uma fila para explorar índices alcançáveis.

**Complexidade:**
- Tempo: O(n² * m)
- Espaço: O(n) para array visited + fila

---

### Abordagem 4: Recursiva com Memoização (Top-Down DP)

**Intuição:** Use recursão com memoização para evitar recálculos.

**Complexidade:**
- Tempo: O(n² * m)
- Espaço: O(n) para memo + O(n) pilha de recursão

---

### Abordagem 5: Trie + DP (para dicionários muito grandes)

**Intuição:** Construa uma Trie do wordDict, depois use DP com travessia da Trie.

**Complexidade:**
- Tempo: O(n * maxLen) para DP + O(total chars no dict) para Trie
- Espaço: O(n + total chars no dict)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| DP Bottom-Up | O(n² * m) | O(n) | Padrão, fácil de entender |
| DP Otimizado | O(n * maxLen * m) | O(n) | **Mais rápido** na prática |
| BFS | O(n² * m) | O(n) | Bom para encontrar caminho |
| Memoização | O(n² * m) | O(n) | Top-down, intuitivo |
| Trie + DP | O(n * maxLen) | O(n + dict) | Melhor para dicionários grandes |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| "leetcode", ["leet","code"] | true | Exemplo 1 |
| "applepenapple", ["apple","pen"] | true | Exemplo 2 - reutilização |
| "catsandog", ["cats","dog","sand","and","cat"] | false | Exemplo 3 |
| "a", ["a"] | true | Caractere único |
| "aaaaaaa", ["aaaa","aaa"] | true | Palavras repetidas |
| Long string with 'b' at end | false | Caso de borda longo |

---

## Implementação

O arquivo `Solution.java` contém todas as 5 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 139 - Word Break](https://leetcode.com/problems/word-break/)
- Dynamic Programming patterns
- Trie data structure