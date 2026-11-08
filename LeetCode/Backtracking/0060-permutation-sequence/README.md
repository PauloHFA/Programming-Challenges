# 0060 - Permutation Sequence

## Descrição do Problema

O conjunto `[1, 2, 3, ..., n]` contém um total de `n!` permutações únicas.

Listando e rotulando todas as permutações em ordem, obtemos a seguinte sequência para `n = 3`:

```
"123"
"132"
"213"
"231"
"312"
"321"
```

Dado `n` e `k`, retorne a `k`-ésima sequência de permutação.

## Exemplos

### Exemplo 1:
```
Input: n = 3, k = 3
Output: "213"
```

### Exemplo 2:
```
Input: n = 4, k = 9
Output: "2314"
```

### Exemplo 3:
```
Input: n = 3, k = 1
Output: "123"
```

## Restrições

- `1 <= n <= 9`
- `1 <= k <= n!`

## Abordagem

### Sistema Fatorial (Matemático) - O(n²) tempo

A ideia principal: **podemos determinar cada dígito diretamente sem gerar todas as permutações**.

Para `n` números, há `n!` permutações. O primeiro dígito divide as permutações em `n` grupos de `(n-1)!` cada.

**Algoritmo:**
1. Pré-calcular fatoriais: `factorial[i] = i!`
2. Lista de números disponíveis: `[1, 2, ..., n]`
3. Converter `k` para 0-indexed: `k--`
4. Para cada posição `i` de `n` até `1`:
   - `index = k / factorial[i-1]` → qual número escolher
   - Adicionar `numbers[index]` ao resultado
   - Remover `numbers[index]` da lista
   - `k = k % factorial[i-1]` → resto para próxima posição

**Exemplo (n=3, k=3):**
```
factorial = [1, 1, 2, 6]
numbers = [1, 2, 3]
k = 2 (0-indexed)

i=3: index = 2/2 = 1 → pick numbers[1]=2, numbers=[1,3], k=2%2=0
i=2: index = 0/1 = 0 → pick numbers[0]=1, numbers=[3], k=0%1=0
i=1: index = 0/1 = 0 → pick numbers[0]=3, numbers=[], k=0

Resultado: "213"
```

### Complexidade

- **Tempo:** O(n²) - remoção de ArrayList é O(n), feita n vezes
- **Espaço:** O(n) para lista de números e fatoriais

### Abordagem Alternativa: Backtracking

Incluída no código para verificação (apenas para n pequeno).

## Implementação

A solução está em `Solution.java` com abordagem matemática e backtracking para verificação, com testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```