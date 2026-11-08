# 0041 - First Missing Positive

## Descrição do Problema

Dado um array de inteiros não ordenado `nums`, retorne o menor inteiro positivo ausente.

Você deve implementar um algoritmo que execute em tempo **O(n)** e use espaço extra **O(1)**.

## Exemplos

### Exemplo 1:
```
Input: nums = [1,2,0]
Output: 3
Explanation: Os números 1 e 2 estão presentes, 3 é o menor positivo ausente.
```

### Exemplo 2:
```
Input: nums = [3,4,-1,1]
Output: 2
Explanation: 1 está presente, 2 é o menor positivo ausente.
```

### Exemplo 3:
```
Input: nums = [7,8,9,11,12]
Output: 1
Explanation: O menor inteiro positivo 1 está ausente.
```

## Restrições

- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`

## Abordagem

### Cyclic Sort / Index Mapping (O(n) tempo, O(1) espaço)

A ideia principal: **o menor inteiro positivo ausente deve estar no intervalo [1, n+1]** onde n = nums.length.

- Se o array contém todos os números de 1 a n, a resposta é n+1.
- Caso contrário, a resposta está em [1, n].

**Algoritmo:**
1. **Colocar cada número em sua posição correta**: O número `x` deve estar no índice `x-1` (se `1 <= x <= n`).
2. Fazemos swaps cíclicos até que cada número válido esteja no lugar certo ou não possa ser movido.
3. **Escaneamos o array**: O primeiro índice `i` onde `nums[i] != i+1` indica que `i+1` está ausente.
4. Se todos os índices 0..n-1 estão corretos, a resposta é `n+1`.

**Por que funciona:**
- Números fora do intervalo [1, n] são ignorados (negativos, zeros, > n).
- Duplicatas são tratadas: se `nums[i] == nums[nums[i]-1]`, não fazemos swap (evita loop infinito).
- Cada número é movido no máximo uma vez → O(n) tempo total.

### Complexidade

- **Tempo:** O(n) - cada elemento é processado no máximo uma vez
- **Espaço:** O(1) - modifica o array de entrada in-place

### Alternativa: HashSet (O(n) tempo, O(n) espaço)

Mais simples mas usa espaço extra. Incluída no código para comparação.

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```