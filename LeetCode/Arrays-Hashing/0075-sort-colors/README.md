# 0075 - Sort Colors

## Descrição

Dado um array `nums` com `n` objetos coloridos de vermelho, branco ou azul, ordene-os in-place de forma que objetos da mesma cor fiquem adjacentes, com as cores na ordem vermelho, branco e azul.

Usaremos os inteiros `0`, `1` e `2` para representar as cores vermelho, branco e azul, respectivamente.

Você deve resolver este problema sem usar a função de ordenação da biblioteca.

## Exemplos

**Exemplo 1:**
```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Exemplo 2:**
```
Input: nums = [2,0,1]
Output: [0,1,2]
```

## Restrições

- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` é `0`, `1` ou `2`

## Abordagem

### Método 1: Dutch National Flag Algorithm (Principal) - One Pass
- Algoritmo de partição de três vias de Dijkstra
- Três ponteiros: `low` (fim dos 0s), `mid` (atual), `high` (início dos 2s)
- Invariante: `[0..low-1]=0`, `[low..mid-1]=1`, `[mid..high]=desconhecido`, `[high+1..n-1]=2`
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Counting Sort (Duas Passagens)
- Conta ocorrências de 0, 1, 2
- Reescreve o array
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Two-Pass Two Pointers
- Primeira passagem: move todos 0s para o início
- Segunda passagem: move todos 1s após os 0s
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço | Passagens |
|--------|-------|--------|-----------|
| Dutch Flag | O(n) | O(1) | 1 |
| Counting | O(n) | O(1) | 2 |
| Two Pass | O(n) | O(1) | 2 |

## Implementação

A solução principal usa o **Algoritmo Dutch National Flag** (partição de três vias).

```java
public void sortColors(int[] nums) {
    int low = 0;
    int mid = 0;
    int high = nums.length - 1;
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            swap(nums, low, mid);
            low++;
            mid++;
        } else if (nums[mid] == 1) {
            mid++;
        } else { // nums[mid] == 2
            swap(nums, mid, high);
            high--;
        }
    }
}
```

## Casos de Teste

- `[2,0,2,1,1,0]` → `[0,0,1,1,2,2]`
- `[2,0,1]` → `[0,1,2]`
- `[0]` → `[0]`
- `[1]` → `[1]`
- `[2]` → `[2]`
- `[0,0,0]` → `[0,0,0]`
- `[1,1,1]` → `[1,1,1]`
- `[2,2,2]` → `[2,2,2]`
- `[2,1,0]` → `[0,1,2]`
- `[0,1,2]` → `[0,1,2]`
- `[1,0,2,1,0,2]` → `[0,0,1,1,2,2]`
- `[2,2,1,1,0,0]` → `[0,0,1,1,2,2]`