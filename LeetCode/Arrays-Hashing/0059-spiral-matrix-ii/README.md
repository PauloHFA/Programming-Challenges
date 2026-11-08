# 0059 - Spiral Matrix II

## Descrição do Problema

Dado um inteiro positivo `n`, gere uma matriz `n x n` preenchida com elementos de `1` a `n^2` em **ordem espiral**.

## Exemplos

### Exemplo 1:
```
Input: n = 3
Output: [[1,2,3],[8,9,4],[7,6,5]]
```

### Exemplo 2:
```
Input: n = 1
Output: [[1]]
```

## Restrições

- `1 <= n <= 20`

## Abordagem

### Travessia por Limites (Layer by Layer) - Preenchimento

Similar ao problema 0054 (Spiral Matrix), mas em vez de ler, **preenchemos** a matriz.

**Algoritmo:**
1. Inicializar matriz `n x n` com zeros
2. Manter quatro limites: `top`, `bottom`, `left`, `right`
3. Contador `num = 1`, alvo `target = n * n`
4. Enquanto `num <= target`:
   - Preencher linha superior (esquerda → direita), `top++`
   - Preencher coluna direita (topo → base), `right--`
   - Preencher linha inferior (direita → esquerda), `bottom--`
   - Preencher coluna esquerda (base → topo), `left++`
5. Verificar `num <= target` em cada loop para evitar sobrescrita

**Exemplo visual (n=3):**
```
Início: matriz 3x3 zeros, num=1, target=9

top=0:    [1, 2, 3]          top=1
right=2:           [4]        right=1
bottom=2:       [6, 5]        bottom=1
left=0:    [8]                left=1
top=1:          [9]           num=10 > target, fim

Resultado:
[1, 2, 3]
[8, 9, 4]
[7, 6, 5]
```

### Abordagem Alternativa: Baseada em Direções

Simula movimento com vetores de direção (direita, baixo, esquerda, cima), mudando direção quando atinge borda ou célula já preenchida.

### Complexidade

- **Tempo:** O(n²) - preenchemos cada célula exatamente uma vez
- **Espaço:** O(1) excluindo saída

## Implementação

A solução está em `Solution.java` com duas abordagens (limites e direções) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```