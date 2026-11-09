# LeetCode 136 - Single Number

## Problema

Dado um array não vazio de inteiros `nums`, cada elemento aparece duas vezes, exceto um. Encontre esse único elemento.

Você deve implementar uma solução com complexidade de tempo linear e usar apenas espaço extra constante.

### Exemplos

**Exemplo 1:**
```
Input: nums = [2,2,1]
Output: 1
```

**Exemplo 2:**
```
Input: nums = [4,1,2,1,2]
Output: 4
```

**Exemplo 3:**
```
Input: nums = [1]
Output: 1
```

### Restrições
- 1 <= nums.length <= 3 * 10^4
- -3 * 10^4 <= nums[i] <= 3 * 10^4
- Cada elemento no array aparece duas vezes, exceto um elemento que aparece apenas uma vez.

---

## Abordagens

### Abordagem 1: Manipulação de Bits - XOR (Ótima)

**Intuição:** A operação XOR tem propriedades únicas:
- `a ^ a = 0` (qualquer número XOR com ele mesmo é 0)
- `a ^ 0 = a` (qualquer número XOR com 0 é o próprio número)
- `a ^ b ^ a = b` (XOR é comutativo e associativo)

Como cada elemento aparece duas vezes, exceto um, fazer XOR de todos os elementos cancela os pares e deixa apenas o número único.

**Algoritmo:**
1. Inicialize `result = 0`
2. Para cada `num` em `nums`: `result ^= num`
3. Retorne `result`

**Complexidade:**
- Tempo: O(n) - uma única passagem pelo array
- Espaço: O(1) - apenas uma variável inteira

---

### Abordagem 2: Hash Set

**Intuição:** Use um conjunto para rastrear elementos vistos. Se já estiver no conjunto, remova; caso contrário, adicione. No final, o conjunto contém apenas o elemento único.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) - no pior caso, metade dos elementos no conjunto

---

### Abordagem 3: Matemática (Soma do conjunto * 2 - Soma do array)

**Intuição:** `2 * (soma dos elementos únicos) - (soma de todos os elementos) = elemento único`

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) - para o conjunto de elementos únicos

---

### Abordagem 4: Ordenação

**Intuição:** Ordene o array e encontre o elemento que não tem par adjacente.

**Complexidade:**
- Tempo: O(n log n) - devido à ordenação
- Espaço: O(1) ou O(log n) dependendo do algoritmo de ordenação

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| XOR | O(n) | O(1) | **Ótima** - atende todos os requisitos |
| Hash Set | O(n) | O(n) | Simples, mas usa espaço extra |
| Matemática | O(n) | O(n) | Requer conjunto para elementos únicos |
| Ordenação | O(n log n) | O(1) | Mais lento, modifica array original |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [2,2,1] | 1 | Caso básico |
| [4,1,2,1,2] | 4 | Múltiplos pares |
| [1] | 1 | Único elemento |
| [-1,-1,-2] | -2 | Números negativos |
| [5,1,1,2,2,3,3] | 5 | Único no início |
| [0,0,1] | 1 | Com zero |
| [10,10,20,20,30] | 30 | Números maiores |
| [-5,-5,-10,-10,-15] | -15 | Todos negativos |

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

- [LeetCode 136 - Single Number](https://leetcode.com/problems/single-number/)
- Propriedades do XOR: https://en.wikipedia.org/wiki/Exclusive_or