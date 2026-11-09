# LeetCode 141 - Linked List Cycle

## Problema

Dado `head`, o cabeça de uma lista ligada, determine se a lista ligada tem um ciclo nela.

Há um ciclo em uma lista ligada se há algum nó na lista que pode ser alcançado novamente seguindo continuamente o ponteiro `next`. Internamente, `pos` é usado para denotar o índice do nó ao qual o ponteiro `next` do último nó está conectado. Note que `pos` não é passado como parâmetro.

Retorne `true` se há um ciclo na lista ligada. Caso contrário, retorne `false`.

### Exemplos

**Exemplo 1:**
```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: Há um ciclo na lista ligada, onde o último nó conecta ao 1º nó (0-indexado).
```

**Exemplo 2:**
```
Input: head = [1,2], pos = 0
Output: true
Explanation: Há um ciclo na lista ligada, onde o último nó conecta ao 0º nó.
```

**Exemplo 3:**
```
Input: head = [1], pos = -1
Output: false
Explanation: Não há ciclo na lista ligada.
```

### Restrições
- O número de nós na lista está no intervalo [0, 10^4].
- -10^5 <= Node.val <= 10^5
- pos é -1 ou um índice válido na lista ligada.

**Follow up:** Você pode resolver usando O(1) (ou seja, constante) memória?

---

## Abordagens

### Abordagem 1: Algoritmo de Detecção de Ciclo de Floyd (Tartaruga e Lebre) - O(1) Espaço

**Intuição:** Use dois ponteiros: `slow` move 1 passo, `fast` move 2 passos. Se há um ciclo, eles eventualmente se encontrarão. Se `fast` alcançar `null`, não há ciclo.

**Algoritmo:**
1. Inicialize `slow = head`, `fast = head`
2. Enquanto `fast != null && fast.next != null`:
   - `slow = slow.next`
   - `fast = fast.next.next`
   - Se `slow == fast`, retorne `true`
3. Retorne `false`

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1) ✓

---

### Abordagem 2: Hash Set - O(n) Espaço

**Intuição:** Armazene nós visitados em um HashSet. Se encontrarmos um nó já no conjunto, há um ciclo.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 3: Modificar Lista (Marcar Visitados) - O(1) Espaço mas modifica a lista

**Intuição:** Marque nós visitados definindo um valor especial. Não recomendado para produção pois modifica a entrada.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 4: Algoritmo de Brent (Detecção de Ciclo Alternativa)

**Intuição:** Similar ao de Floyd mas usa passos de potência de dois para o ponteiro rápido. Pode ser ligeiramente mais rápido na prática.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Floyd (Tartaruga/Lebre) | O(n) | O(1) | **Ótima** - atende follow-up |
| Hash Set | O(n) | O(n) | Simples, fácil de entender |
| Modificar Lista | O(n) | O(1) | Modifica entrada - não recomendado |
| Brent | O(n) | O(1) | Alternativa a Floyd |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [3,2,0,-4], pos=1 | true | Exemplo 1 |
| [1,2], pos=0 | true | Exemplo 2 |
| [1], pos=-1 | false | Exemplo 3 |
| [] | false | Lista vazia |
| [1,2], pos=-1 | false | Dois nós, sem ciclo |
| [1], pos=0 | true | Nó único, ciclo próprio |
| [1..10], pos=5 | true | Lista longa com ciclo |
| [1..10], pos=-1 | false | Lista longa sem ciclo |

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

- [LeetCode 141 - Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)
- Floyd's Cycle Detection Algorithm
- Brent's Cycle Detection Algorithm