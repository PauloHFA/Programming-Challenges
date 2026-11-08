# 0047 - Permutations II

## Descrição do Problema

Dada uma coleção de números `nums` que **podem conter duplicatas**, retorne todas as permutações possíveis **únicas** em qualquer ordem.

## Exemplos

### Exemplo 1:
```
Input: nums = [1,1,2]
Output: [[1,1,2],[1,2,1],[2,1,1]]
```

### Exemplo 2:
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

## Restrições

- `1 <= nums.length <= 8`
- `-10 <= nums[i] <= 10`

## Abordagem

### Backtracking com Ordenação e Pulo de Duplicatas

A diferença chave do **Permutations I** é que o array pode ter **duplicatas**.

**Estratégia para evitar permutações duplicadas:**
1. **Ordenamos** o array primeiro
2. Usamos array `used[]` para rastrear elementos já na permutação atual
3. No loop, pulamos se:
   - `used[i]` é true (já usado)
   - `i > 0 && nums[i] == nums[i-1] && !used[i-1]` (duplicata no mesmo nível)

**Por que `!used[i-1]`?**
- Se `used[i-1]` é true: o elemento anterior **foi usado** na permutação atual (está em um nível mais profundo da recursão), então esta é uma posição diferente → **usamos**.
- Se `used[i-1]` é false: o elemento anterior **não foi usado** neste nível (já voltamos do backtrack), então usar este criaria duplicata → **pulamos**.

### Complexidade

- **Tempo:** O(n! × n) - n! permutações únicas no pior caso (sem duplicatas)
- **Espaço:** O(n) para pilha de recursão + array `used`

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```