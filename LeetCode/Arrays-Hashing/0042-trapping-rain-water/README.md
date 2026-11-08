# 0042 - Trapping Rain Water

## Descrição do Problema

Dado `n` inteiros não negativos representando um mapa de elevação onde a largura de cada barra é 1, calcule quanta água pode ser retida após chover.

## Exemplos

### Exemplo 1:
```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: O mapa de elevação acima (seção preta) retém 6 unidades de água (seção azul).
```

### Exemplo 2:
```
Input: height = [4,2,0,3,2,5]
Output: 9
```

## Restrições

- `n == height.length`
- `1 <= n <= 2 * 10^4`
- `0 <= height[i] <= 10^5`

## Abordagem

### Two Pointers (Ótimo: O(n) tempo, O(1) espaço)

A ideia principal: **a água retida em cada posição depende do menor dos máximos à esquerda e à direita**.

**Algoritmo:**
1. Dois ponteiros: `left = 0`, `right = n-1`
2. Variáveis: `leftMax = 0`, `rightMax = 0`
3. Enquanto `left < right`:
   - Se `height[left] < height[right]`:
     - Se `height[left] >= leftMax`: atualiza `leftMax`
     - Senão: adiciona `leftMax - height[left]` à água
     - `left++`
   - Senão:
     - Se `height[right] >= rightMax`: atualiza `rightMax`
     - Senão: adiciona `rightMax - height[right]` à água
     - `right--`

**Por que funciona:** Processamos o lado com a parede menor, pois a água é limitada pelo menor dos dois máximos. Ao mover o ponteiro do lado menor, garantimos que `leftMax` (ou `rightMax`) é o verdadeiro máximo limitante para aquela posição.

### Abordagens Alternativas

1. **Dynamic Programming** (O(n) tempo, O(n) espaço): Pré-calcula `leftMax[i]` e `rightMax[i]` para cada posição.
2. **Stack** (O(n) tempo, O(n) espaço): Usa pilha monotônica decrescente para encontrar limites.

### Complexidade

- **Tempo:** O(n) - única passagem
- **Espaço:** O(1) - apenas variáveis

## Implementação

A solução está em `Solution.java` com três abordagens (Two Pointers, DP, Stack) e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```