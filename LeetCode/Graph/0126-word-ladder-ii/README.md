# 0126 - Word Ladder II

## Descrição

Dado duas palavras `beginWord` e `endWord`, e uma lista de palavras `wordList`, encontre todas as sequências de transformação mais curtas de `beginWord` para `endWord`.

Cada sequência deve ser tal que:
- Apenas uma letra muda por vez
- Cada palavra transformada deve existir na `wordList`

Retorne todas as sequências mais curtas. A ordem não importa.

## Exemplos

**Exemplo 1:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
```

**Exemplo 2:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: []
Explicação: endWord "cog" não está na wordList.
```

## Restrições

- `1 <= beginWord.length <= 5`
- `endWord.length == beginWord.length`
- `1 <= wordList.length <= 500`
- `wordList[i].length == beginWord.length`
- `beginWord`, `endWord`, e `wordList[i]` consistem de letras minúsculas inglesas.
- `beginWord != endWord`
- Todas as palavras em `wordList` são únicas.

## Abordagem

### Método 1: BFS + Backtracking (Principal)
- BFS nível por nível para construir grafo de adjacência direcionado
- Apenas arestas que levam ao próximo nível (distância mínima)
- Backtracking DFS para reconstruir todos os caminhos mais curtos
- **Tempo:** O(N * L²), **Espaço:** O(N * L²)

### Método 2: BFS Bidirecional + Backtracking
- BFS simultâneo de beginWord e endWord
- Mais eficiente para grafos grandes
- **Tempo:** O(N * L²), **Espaço:** O(N * L²)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| BFS + Backtracking | O(N * L²) | O(N * L²) |
| Bidirecional | O(N * L²) | O(N * L²) |

## Implementação

A solução principal usa **BFS + Backtracking**.

```java
public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return new ArrayList<>();
    
    Map<String, List<String>> graph = new HashMap<>();
    Map<String, Integer> distance = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    distance.put(beginWord, 0);
    
    // BFS para construir grafo
    while (!queue.isEmpty()) {
        // ... processa nível por nível
    }
    
    // Backtracking
    List<List<String>> result = new ArrayList<>();
    backtrack(beginWord, endWord, graph, new ArrayList<>(Arrays.asList(beginWord)), result);
    return result;
}
```

## Casos de Teste

- `hit -> cog, [hot,dot,dog,lot,log,cog]` → 2 paths
- `hit -> cog, [hot,dot,dog,lot,log]` → 0 paths
- `a -> c, [a,b,c]` → 1 path
- `hit -> cog, [hot,dot,dog]` → 0 paths