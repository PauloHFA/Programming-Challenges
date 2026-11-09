# 0133 - Clone Graph

## Descrição

Dada uma referência de um nó em um grafo não direcionado conectado, retorne uma **cópia profunda (deep copy)** do grafo.

Cada nó no grafo contém um valor (`int`) e uma lista (`List[Node]`) de seus vizinhos.

## Exemplos

**Exemplo 1:**
```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explicação: Grafo com 4 nós formando um ciclo.
```

**Exemplo 2:**
```
Input: adjList = [[]]
Output: [[]]
Explicação: Grafo com 1 nó sem vizinhos.
```

**Exemplo 3:**
```
Input: adjList = []
Output: []
Explicação: Grafo vazio.
```

## Restrições

- Número de nós no grafo está no intervalo `[0, 100]`.
- `1 <= Node.val <= 100`
- `Node.val` é único para cada nó.
- Não há arestas paralelas nem auto-loops.
- O grafo é conectado.

## Abordagem

### Método 1: DFS Recursivo com HashMap (Principal)
- Usa mapa `cloned` para rastrear nós originais → cópias
- Se nó já clonado, retorna a cópia existente
- Senão cria nova cópia, adiciona ao mapa, e clona recursivamente vizinhos
- **Tempo:** O(V + E), **Espaço:** O(V)

### Método 2: BFS Iterativo com Queue
- Processa nós nível por nível
- Cria cópia quando encontra nó novo
- Conecta vizinhos usando o mapa
- **Tempo:** O(V + E), **Espaço:** O(V)

### Método 3: DFS Iterativo com Stack
- Similar ao BFS mas usa stack (LIFO)
- **Tempo:** O(V + E), **Espaço:** O(V)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| DFS Recursivo | O(V + E) | O(V) |
| BFS | O(V + E) | O(V) |
| DFS Stack | O(V + E) | O(V) |

## Implementação

A solução principal usa **DFS Recursivo com HashMap**.

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    
    Map<Node, Node> cloned = new HashMap<>();
    return dfs(node, cloned);
}

private Node dfs(Node node, Map<Node, Node> cloned) {
    if (cloned.containsKey(node)) {
        return cloned.get(node);
    }
    
    Node copy = new Node(node.val);
    cloned.put(node, copy);
    
    for (Node neighbor : node.neighbors) {
        copy.neighbors.add(dfs(neighbor, cloned));
    }
    
    return copy;
}
```

## Casos de Teste

- `[[2,4],[1,3],[2,4],[1,3]]` → Cópia idêntica
- `[[]]` → Nó único
- `[]` → null
- `1-2-3-4` (linha)
- Estrela (1 conectado a 2,3,4,5)