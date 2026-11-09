# LeetCode 137 - Single Number II

## Problema

Dado um array de inteiros `nums` onde cada elemento aparece três vezes, exceto um, que aparece exatamente uma vez. Encontre o elemento único e retorne-o.

Você deve implementar uma solução com complexidade de tempo linear e usar apenas espaço extra constante.

### Exemplos

**Exemplo 1:**
```
Input: nums = [2,2,3,2]
Output: 3
```

**Exemplo 2:**
```
Input: nums = [0,1,0,1,0,1,99]
Output: 99
```

### Restrições
- 1 <= nums.length <= 3 * 10^4
- -2^31 <= nums[i] <= 2^31 - 1
- Cada elemento em nums aparece exatamente três vezes, exceto um elemento que aparece uma vez.

---

## Abordagens

### Abordagem 1: Contagem de Bits (Bit Counting)

**Intuição:** Para cada posição de bit (0-31), conte quantos números têm esse bit definido. Como todos os números aparecem 3 vezes, exceto um, a contagem % 3 dá o bit do número único nessa posição.

**Algoritmo:**
1. Para cada posição de bit i de 0 a 31:
   - Conte quantos números têm o bit i definido
   - Se count % 3 != 0, defina o bit i no resultado
2. Retorne o resultado

**Complexidade:**
- Tempo: O(32 * n) = O(n)
- Espaço: O(1)

---

### Abordagem 2: Máquina de Estados Finitos (FSM) - Contador de 2 bits

**Intuição:** Use duas variáveis para representar um contador de 2 bits para cada posição de bit:
- `ones`: bits que apareceram 1 vez (mod 3)
- `twos`: bits que apareceram 2 vezes (mod 3)

Transições de estado para cada bit:
- 00 -> 01 -> 10 -> 00 (para bits que aparecem 3 vezes)
- 00 -> 01 (para bits do número único)

**Algoritmo:**
1. Inicialize `ones = 0`, `twos = 0`
2. Para cada número:
   - `twos |= ones & num` (bits que apareceram duas vezes)
   - `ones ^= num` (toggle bits que apareceram primeira vez)
   - `common = ones & twos` (bits que apareceram 3 vezes)
   - `ones &= ~common` (remova bits que apareceram 3 vezes)
   - `twos &= ~common`
3. Retorne `ones`

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 3: Hash Map / Contagem de Frequência

**Intuição:** Conte a frequência de cada número usando um HashMap. Retorne o número com frequência 1.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 4: Matemática (3 * soma dos únicos - soma de todos) / 2

**Intuição:** `3 * (soma dos elementos únicos) - (soma de todos os elementos) = 2 * elemento único`
Elemento único = `(3 * somaÚnicos - somaTodos) / 2`

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n) para o conjunto

---

### Abordagem 5: Ordenação

**Intuição:** Ordene o array e encontre o elemento que não aparece em um grupo de 3.

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(1) ou O(log n)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Contagem de Bits | O(n) | O(1) | **Ótima** - atende todos os requisitos |
| FSM (2-bit counter) | O(n) | O(1) | **Ótima** - mais elegante, uma passagem |
| Hash Map | O(n) | O(n) | Simples, mas usa espaço extra |
| Matemática | O(n) | O(n) | Requer conjunto para elementos únicos |
| Ordenação | O(n log n) | O(1) | Mais lento, modifica array original |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [2,2,3,2] | 3 | Caso básico |
| [0,1,0,1,0,1,99] | 99 | Múltiplos triplos |
| [1] | 1 | Único elemento |
| [-2,-2,1,1,-3,1,-3,-3,-4,-2] | -4 | Números negativos |
| [5,5,5,1,1,1,2,2,2,3] | 3 | Único no final |
| [1,1,1,2,3,3,3] | 2 | Único no meio |
| [2147483647,2147483647,2147483647,-2147483648] | -2147483648 | Limites de int |
| [-1,-1,-1,-2] | -2 | Todos negativos |
| [10,10,10,20,20,20,30] | 30 | Números maiores |

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

- [LeetCode 137 - Single Number II](https://leetcode.com/problems/single-number-ii/)
- Bit manipulation techniques
- Finite State Machine for bit counting