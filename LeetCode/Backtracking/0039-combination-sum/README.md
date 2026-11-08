# 0039 - Combination Sum

## Descrição do Problema

Dado um array de inteiros **distintos** `candidates` e um inteiro alvo `target`, retorne uma lista de todas as **combinações únicas** de `candidates` onde os números escolhidos somam `target`. Você pode retornar as combinações em qualquer ordem.

O **mesmo** número pode ser escolhido de `candidates` um **número ilimitado de vezes**. Duas combinações são únicas se a frequência de pelo menos um dos números escolhidos for diferente.

Os casos de teste são gerados de forma que o número de combinações únicas que somam `target` seja menor que 150 combinações para a entrada dada.

## Exemplos

### Exemplo 1:
```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 e 3 são candidatos, e 2 + 2 + 3 = 7. Note que 2 pode ser usado múltiplas vezes.
7 é um candidato, e 7 = 7.
Estas são as únicas duas combinações.
```

### Exemplo 2:
```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

### Exemplo 3:
```
Input: candidates = [2], target = 1
Output: []
```

## Restrições

- `1 <= candidates.length <= 30`
- `2 <= candidates[i] <= 40`
- Todos os elementos de `candidates` são **distintos**.
- `1 <= target <= 40`

## Abordagem

### Backtracking com Ordenação

A abordagem padrão para problemas de combinação é o **backtracking**:

1. **Ordenamos** o array `candidates` para permitir poda (early termination).
2. **Função recursiva** `backtrack(candidates, target, start, current, result)`:
   - `target`: soma restante necessária
   - `start`: índice inicial para evitar duplicatas (permutations vs combinations)
   - `current`: combinação atual sendo construída
   - `result`: lista de combinações válidas encontradas
3. **Caso base**: Se `target == 0`, encontramos uma combinação válida → adicionamos ao resultado.
4. **Loop**: Para cada candidato a partir de `start`:
   - Se `candidates[i] > target`: **break** (poda - como está ordenado, os próximos serão maiores)
   - Adicionamos `candidates[i]` à combinação atual
   - Chamamos recursivamente com `target - candidates[i]` e **mesmo índice `i`** (permite reuso)
   - Removemos o último elemento (backtrack)

### Por que `start = i` e não `i + 1`?

Como podemos usar o **mesmo elemento múltiplas vezes**, passamos `i` (não `i + 1`) na chamada recursiva. Isso permite que o mesmo candidato seja escolhido novamente.

### Complexidade

- **Tempo:** O(N^(T/M)) onde N = candidates.length, T = target, M = min(candidates)
- **Espaço:** O(T/M) para a pilha de recursão (profundidade máxima)

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```