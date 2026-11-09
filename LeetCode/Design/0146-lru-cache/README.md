# LeetCode 146 - LRU Cache

## Problema

Projete uma estrutura de dados que siga as restrições de um cache Least Recently Used (LRU).

Implemente a classe LRUCache:
- `LRUCache(int capacity)` Inicializa o cache LRU com tamanho positivo capacity.
- `int get(int key)` Retorna o valor da chave se a chave existir, caso contrário retorna -1.
- `void put(int key, int value)` Atualiza o valor da chave se a chave existir. Caso contrário, adiciona o par chave-valor ao cache. Se o número de chaves exceder a capacity a partir desta operação, remova a chave menos recentemente usada.

As funções get e put devem rodar em complexidade de tempo média O(1).

### Exemplo

```
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
```

### Restrições
- 1 <= capacity <= 3000
- 0 <= key <= 10^4
- 0 <= value <= 10^5
- No máximo 2 * 10^5 chamadas serão feitas para get e put.

---

## Abordagens

### Abordagem 1: HashMap + Lista Duplamente Ligada (Solução Padrão O(1))

**Intuição:** Use um HashMap para busca O(1) por chave e uma lista duplamente ligada para inserção/remoção O(1) e manutenção da ordem de acesso. Mais recentemente usado na cabeça, menos recentemente usado na cauda.

**Estrutura:**
- `HashMap<Integer, Node>`: chave -> nó
- Lista duplamente ligada com sentinelas head (MRU) e tail (LRU)

**Operações:**
- `get(key)`: Se existe, move para head e retorna valor
- `put(key, value)`: Se existe, atualiza e move para head. Se não, cria novo, adiciona no head. Se exceder capacity, remove tail.prev (LRU)

**Complexidade:**
- Tempo: O(1) para get e put
- Espaço: O(capacity)

---

### Abordagem 2: LinkedHashMap (Java Built-in)

**Intuição:** Use LinkedHashMap com `accessOrder=true` que mantém ordem de inserção/acesso. Sobrescreva `removeEldestEntry` para implementar remoção LRU.

**Complexidade:**
- Tempo: O(1) para get e put
- Espaço: O(capacity)

---

### Abordagem 3: Baseada em Array (educacional, para capacity pequeno)

**Intuição:** Use arrays para simular o cache. Não é O(1) mas simples de entender.

**Complexidade:**
- Tempo: O(n) para get e put
- Espaço: O(capacity)

---

## Comparação de Abordagens

| Abordagem | Tempo (get/put) | Espaço | Notas |
|-----------|-----------------|--------|-------|
| HashMap + DLL | O(1) | O(capacity) | **Padrão** - implementação manual |
| LinkedHashMap | O(1) | O(capacity) | **Prática** - usa biblioteca Java |
| Array | O(n) | O(capacity) | Educacional, não atende requisito O(1) |

---

## Casos de Teste

| Operações | Saída Esperada | Descrição |
|-----------|----------------|-----------|
| Exemplo LeetCode | [null,null,null,1,null,-1,null,-1,3,4] | Exemplo principal |
| Básicas | get/put simples | Operações básicas |
| Atualizar chave | Valor atualizado | Put em chave existente |
| Capacity 1 | Evicção imediata | Caso extremo |

---

## Implementação

O arquivo `Solution.java` contém todas as 3 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 146 - LRU Cache](https://leetcode.com/problems/lru-cache/)
- LRU Cache Design Pattern
- LinkedHashMap in Java