# 0127 - Word Ladder

## Descrição

Dado duas palavras `beginWord` e `endWord`, e uma lista de palavras `wordList`, retorne o número de palavras na sequência de transformação mais curta de `beginWord` para `endWord`, ou 0 se não existir tal sequência.

Cada palavra transformada deve existir na `wordList`.

## Exemplos

**Exemplo 1:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explicação: hit -> hot -> dot -> dog -> cog (5 palavras)
```

**Exemplo 2:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explicação: endWord "cog" não está na wordList.
```

## Restrições

- `1 <= beginWord.length <= 10`
- `endWord.length == beginWord.length`
- `1 <= wordList.length <= 5000`
- `wordList[i].length == beginWord.length`
- `beginWord`, `endWord`, e `wordList[i]` consistem de letras minúsculas inglesas.
- `beginWord != endWord`
- Todas as palavras em `wordList` são únicas.

## Abordagem

### Método 1: BFS Padrão (Principal)
- BFS nível por nível, cada nível = uma transformação
- Remove palavras visitadas do wordSet para evitar ciclos
- **Tempo:** O(N * L²), **Espaço:** O(N * L)

### Método 2: BFS Bidirecional
- BFS simultâneo de beginWord e endWord
- Sempre expande o conjunto menor
- **Tempo:** O(N * L²), **Espaço:** O(N * L) - mais rápido na prática

### Método 3: BFS com Padrões (Wildcard)
- Pré-processa wordList em padrões genéricos (*ot -> [hot, dot, lot])
- **Tempo:** O(N * L²), **Espaço:** O(N * L²)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| BFS Padrão | O(N * L²) | O(N * L) |
| Bidirecional | O(N * L²) | O(N * L) |
| Padrões | O(N * L²) | O(N * L²) |

## Implementação

A solução principal usa **BFS Padrão**.

```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    wordSet.remove(beginWord);
    
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            if (word.equals(endWord)) return level;
            
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String nextWord = new String(chars);
                    if (wordSet.contains(nextWord)) {
                        queue.offer(nextWord);
                        wordSet.remove(nextWord);
                    }
                }
                chars[j] = original;
            }
        }
        level++;
    }
    
    return 0;
}
```

## Casos de Teste

- `hit -> cog, [hot,dot,dog,lot,log,cog]` → `5`
- `hit -> cog, [hot,dot,dog,lot,log]` → `0`
- `a -> c, [a,b,c]` → `2`
- `hit -> hit, [hit]` → `1`
- `hit -> cog, [hot,dot,dog]` → `0`
- `red -> tax, [ted,tex,red,tax,tad,den,rex,pee]` → `4`
- `hot -> dog, [hot,dog,dot]` → `3`