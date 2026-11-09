# LeetCode 138 - Copy List with Random Pointer

## Problema

Uma lista ligada de comprimento n é dada de forma que cada nó contém um ponteiro aleatório adicional, que pode apontar para qualquer nó na lista, ou null.

Construa uma cópia profunda da lista. A cópia profunda deve consistir em exatamente n novos nós, onde cada novo nó tem seu valor definido como o valor do seu nó original correspondente. Tanto o ponteiro next quanto o ponteiro random dos novos nós devem apontar para novos nós na lista copiada de forma que os ponteiros na lista original e na lista copiada representem o mesmo estado da lista. Nenhum dos ponteiros na nova lista deve apontar para nós na lista original.

### Exemplos

**Exemplo 1:**
```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
```

**Exemplo 2:**
```
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]
```

**Exemplo 3:**
```
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]
```

### Restrições
- 0 <= n <= 1000
- -10^4 <= Node.val <= 10^4
- Node.random é null ou aponta para algum nó na lista ligada.

---

## Abordagens

### Abordagem 1: Hash Map - Duas Passagens

**Intuição:** Use um mapa para mapear cada nó original para sua cópia. Na primeira passagem, crie todos os novos nós. Na segunda passagem, configure os ponteiros next e random usando o mapa.

**Algoritmo:**
1. Primeira passagem: Percorra a lista original, crie um novo nó para cada um e armazene no mapa `original -> copy`
2. Segunda passagem: Percorra novamente, para cada nó original, configure `copy.next = map.get(original.next)` e `copy.random = map.get(original.random)`
3. Retorne `map.get(head)`

**Complexidade:**
- Tempo: O(n) - duas passagens lineares
- Espaço: O(n) - para o hash map

---

### Abordagem 2: Nós Intercalados (Espaço O(1))

**Intuição:** Intercale os nós copiados entre os originais: A -> A' -> B -> B' -> C -> C'. Isso permite acessar a cópia de qualquer nó via `original.next` sem espaço extra.

**Algoritmo:**
1. **Intercalar:** Para cada nó original, crie sua cópia e insira logo após ele
2. **Configurar random:** Para cada nó original, se `original.random != null`, então `original.next.random = original.random.next`
3. **Separar:** Desfaça a intercalação, restaurando a lista original e extraindo a cópia

**Complexidade:**
- Tempo: O(n) - três passagens lineares
- Espaço: O(1) extra (não contando a saída)

---

### Abordagem 3: Recursiva com Hash Map

**Intuição:** Use recursão com memoização para lidar com ciclos e ponteiros aleatórios. O mapa evita criar múltiplas cópias do mesmo nó.

**Algoritmo:**
1. Se nó é null, retorne null
2. Se nó já está no mapa, retorne a cópia do mapa
3. Crie novo nó, armazene no mapa
4. Recursivamente configure `copy.next = copyRecursive(node.next)` e `copy.random = copyRecursive(node.random)`
5. Retorne a cópia

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) para hash map + O(n) pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Hash Map (2 passagens) | O(n) | O(n) | Simples, fácil de entender |
| Nós Intercalados | O(n) | O(1) | **Ótima** - espaço constante |
| Recursiva + Hash Map | O(n) | O(n) | Elegante, mas usa pilha de recursão |

---

## Casos de Teste

| Entrada | Descrição |
|---------|-----------|
| [[7,null],[13,0],[11,4],[10,2],[1,0]] | Exemplo 1 - 5 nós |
| [[1,1],[2,1]] | Exemplo 2 - 2 nós, random para o último |
| [[3,null],[3,0],[3,null]] | Exemplo 3 - valores duplicados |
| [] | Lista vazia |
| [[1,0]] | Nó único com random para si mesmo |
| [[42,null]] | Nó único com random null |
| [[1,2],[2,0],[3,1],[4,3],[5,-1]] | 5 nós com vários randoms |

---

## Implementação

O arquivo `Solution.java` contém todas as 3 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações, incluindo verificação de cópia profunda (objetos diferentes, mesmos valores).

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 138 - Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/)
- Deep copy vs shallow copy
- Linked list manipulation techniques