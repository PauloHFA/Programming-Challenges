# LeetCode 142 - Linked List Cycle II

## Problema

Dado o `head` de uma lista ligada, retorne o nó onde o ciclo começa. Se não há ciclo, retorne `null`.

Há um ciclo em uma lista ligada se há algum nó na lista que pode ser alcançado novamente seguindo continuamente o ponteiro `next`. Internamente, `pos` é usado para denotar o índice do nó ao qual o ponteiro `next` do último nó está conectado (0-indexado). É -1 se não há ciclo. Note que `pos` não é passado como parâmetro.

Não modifique a lista ligada.

### Exemplos

**Exemplo 1:**
```
Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: Há um ciclo na lista ligada, onde o último nó conecta ao segundo nó.
```

**Exemplo 2:**
```
Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: Há um ciclo na lista ligada, onde o último nó conecta ao primeiro nó.
```

**Exemplo 3:**
```
Input: head = [1], pos = -1
Output: no cycle
Explanation: Não há ciclo na lista ligada.
```

### Restrições
- O número de nós na lista está no intervalo [0, 10^4].
- -10^5 <= Node.val <= 10^5
- pos é -1 ou um índice válido na lista ligada.

**Follow up:** Você pode resolver usando O(1) (ou seja, constante) memória?

---

## Abordagens

### Abordagem 1: Algoritmo de Floyd (Tartaruga e Lebre) - O(1) Espaço

**Intuição:** 
- **Fase 1:** Detectar ciclo usando ponteiros slow (1 passo) e fast (2 passos).
- **Fase 2:** Encontrar início do ciclo. Quando se encontram, reinicie um ponteiro para head. Mova ambos a 1 passo. Eles se encontram no início do ciclo.

**Prova:** Seja distância do head ao início do ciclo = a
Distância do início do ciclo ao ponto de encontro = b
Comprimento do ciclo = c
Slow percorre: a + b
Fast percorre: a + b + k*c (k voltas)
Como fast = 2 * slow: 2(a + b) = a + b + k*c => a + b = k*c => a = k*c - b
Portanto, distância do head ao início do ciclo = distância do ponto de encontro ao início do ciclo (indo para frente).

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1) ✓

---

### Abordagem 2: Hash Set - O(n) Espaço

**Intuição:** Armazene nós visitados em um HashSet. Primeiro nó encontrado duas vezes é o início do ciclo.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 3: Algoritmo de Brent - O(1) Espaço

**Intuição:** Detecção de ciclo alternativa que também encontra início do ciclo. Usa passos de potência de dois para o ponteiro rápido.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 4: Dois Ponteiros com Cálculo do Comprimento do Ciclo

**Intuição:** Primeiro encontre ponto de encontro, depois calcule comprimento do ciclo, então use dois ponteiros com gap do comprimento do ciclo para encontrar início.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Floyd (Tartaruga/Lebre) | O(n) | O(1) | **Ótima** - atende follow-up |
| Hash Set | O(n) | O(n) | Simples, fácil de entender |
| Brent | O(n) | O(1) | Alternativa a Floyd |
| Comprimento do Ciclo | O(n) | O(1) | Mais passos, mas intuitivo |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [3,2,0,-4], pos=1 | índice 1 (valor 2) | Exemplo 1 |
| [1,2], pos=0 | índice 0 (valor 1) | Exemplo 2 |
| [1], pos=-1 | null | Exemplo 3 |
| [] | null | Lista vazia |
| [1,2], pos=-1 | null | Dois nós, sem ciclo |
| [1], pos=0 | índice 0 (valor 1) | Nó único, ciclo próprio |
| [1..10], pos=5 | índice 5 (valor 6) | Lista longa com ciclo |
| [1..10], pos=-1 | null | Lista longa sem ciclo |
| [1,2,3], pos=2 | índice 2 (valor 3) | Ciclo no último nó |

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

- [LeetCode 142 - Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/)
- Floyd's Cycle Detection Algorithm
- Brent's Cycle Detection Algorithm