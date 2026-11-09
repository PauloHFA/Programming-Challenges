# LeetCode 152 - Maximum Product Subarray

## Problema

Dado um array de inteiros `nums`, encontre um subarray que tenha o maior produto e retorne o produto.

Os casos de teste são gerados de forma que a resposta caiba em um inteiro de 32 bits.

### Exemplos

**Exemplo 1:**
```
Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] tem o maior produto 6.
```

**Exemplo 2:**
```
Input: nums = [-2,0,-1]
Output: 0
Explanation: O resultado não pode ser 2, porque [-2,-1] não é um subarray.
```

### Restrições
- 1 <= nums.length <= 2 * 10^4
- -10 <= nums[i] <= 10
- O produto de qualquer prefixo ou sufixo de nums cabe em um inteiro de 32 bits.

---

## Abordagens

### Abordagem 1: Programação Dinâmica - Rastrear Max e Min

**Intuição:** Como números negativos podem inverter max para min e vice-versa, rastreamos tanto o produto máximo quanto o mínimo terminando em cada posição.

**Algoritmo:**
- `maxEndingHere = max(nums[i], maxEndingHere * nums[i], minEndingHere * nums[i])`
- `minEndingHere = min(nums[i], maxEndingHere * nums[i], minEndingHere * nums[i])`
- Quando `num < 0`, max e min trocam de lugar

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 2: DP com Variáveis Temporárias Explícitas

**Intuição:** Versão mais clara usando variáveis temporárias para evitar confusão com a troca.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 3: Produtos Prefixo/Sufixo (Alternativa)

**Intuição:** O subarray de produto máximo é ou um produto de prefixo ou sufixo (ou o array todo). Funciona porque zeros resetam o produto.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

---

### Abordagem 4: Dividir e Conquistar

**Intuição:** Encontre recursivamente o max produto na metade esquerda, metade direita, e cruzando o meio.

**Complexidade:**
- Tempo: O(n log n)
- Espaço: O(log n) pilha de recursão

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| DP (Max/Min) | O(n) | O(1) | **Ótima** - padrão |
| DP Explícita | O(n) | O(1) | Mais clara |
| Prefixo/Sufixo | O(n) | O(1) | Alternativa elegante |
| Dividir e Conquistar | O(n log n) | O(log n) | Educacional |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [2,3,-2,4] | 6 | Exemplo 1 |
| [-2,0,-1] | 0 | Exemplo 2 |
| [1,2,3,4,5] | 120 | Todos positivos |
| [-2,-3,-4,-5] | 120 | Todos negativos (par) |
| [-2,-3,-4] | 12 | Todos negativos (ímpar) |
| [0,2,3,-2,4,0,-1] | 6 | Com zeros |
| [5] | 5 | Elemento único |
| [-5] | -5 | Negativo único |
| [-2,3,-4] | 24 | Misturado |
| [2,-5,-2,-4,3] | 240 | Caso complexo |

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

- [LeetCode 152 - Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
- Dynamic Programming
- Kadane's Algorithm variant