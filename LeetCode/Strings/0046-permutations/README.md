# 0046 - Permutations

## Descrição do Problema

Dado um array `nums` de inteiros **distintos**, retorne todas as permutações possíveis. Você pode retornar a resposta em qualquer ordem.

## Exemplos

### Exemplo 1:
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

### Exemplo 2:
```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```

### Exemplo 3:
```
Input: nums = [1]
Output: [[1]]
```

## Restrições

- `1 <= nums.length <= 6`
- `-10 <= nums[i] <= 10`
- Todos os inteiros de `nums` são **únicos**.

## Abordagem

### Backtracking com Troca (Swap)

A abordagem clássica para gerar permutações:

1. **Fixamos** um elemento na posição `start` e recursivamente geramos permutações para o resto.
2. Para cada índice `i` de `start` até `n-1`:
   - Trocamos `nums[start]` com `nums[i]`
   - Recursivamente chamamos para `start + 1`
   - Desfazemos a troca (backtrack)

**Por que funciona:** Ao trocar, colocamos cada elemento possível na posição atual e exploramos todas as permutações do sufixo restante.

### Abordagem Alternativa: Array `used`

Mais intuitiva: mantemos um array `used[]` para rastrear quais elementos já estão na permutação atual.

### Complexidade

- **Tempo:** O(n! × n) - n! permutações, cada uma leva O(n) para copiar
- **Espaço:** O(n) para pilha de recursão (excluindo saída)

## Implementação

A solução está em `Solution.java` com duas abordagens (swap e visited) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```