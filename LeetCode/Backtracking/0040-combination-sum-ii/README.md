# 0040 - Combination Sum II

## Descrição do Problema

Dada uma coleção de números candidatos `candidates` e um número alvo `target`, encontre todas as combinações únicas em `candidates` onde os números candidatos somam `target`.

Cada número em `candidates` pode ser usado **apenas uma vez** na combinação.

**Nota:** O conjunto de soluções não deve conter combinações duplicadas.

## Exemplos

### Exemplo 1:
```
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
```

### Exemplo 2:
```
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]
```

## Restrições

- `1 <= candidates.length <= 100`
- `1 <= candidates[i] <= 50`
- `1 <= target <= 30`

## Abordagem

### Backtracking com Ordenação e Pulo de Duplicatas

A diferença chave do **Combination Sum I** é que:
1. Cada elemento pode ser usado **apenas uma vez** → passamos `i + 1` na recursão
2. O array pode ter **duplicatas** → precisamos pular duplicatas para evitar combinações repetidas

**Estratégia para evitar duplicatas:**
1. **Ordenamos** o array primeiro
2. No loop, se `i > start && candidates[i] == candidates[i-1]`, **pulamos** (continue)
   - `i > start`: garante que estamos pulando apenas duplicatas no mesmo nível de recursão
   - Se `i == start`, é a primeira ocorrência nesse nível, então usamos

### Complexidade

- **Tempo:** O(2^N) onde N = candidates.length (cada elemento: usar ou não usar)
- **Espaço:** O(N) para a pilha de recursão

## Implementação

A solução está em `Solution.java` com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```