# 0081 - Search in Rotated Sorted Array II

## Descrição

Dado um array `nums` ordenado em ordem não decrescente (podendo conter duplicatas) e rotado em algum pivô desconhecido, e um inteiro `target`, retorne `true` se `target` estiver em `nums`, ou `false` caso contrário.

## Exemplos

**Exemplo 1:**
```
Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
```

**Exemplo 2:**
```
Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
```

## Restrições

- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` é garantido como rotado em algum pivô
- `-10^4 <= target <= 10^4`

## Abordagem

### Método 1: Busca Binária Modificada (Principal)
- Similar ao problema 0033, mas com duplicatas
- Quando `nums[left] == nums[mid] == nums[right]`, não sabemos qual lado está ordenado
- Nesse caso, encolhe o espaço de busca: `left++`, `right--`
- Caso contrário, determina qual lado está ordenado e busca lá
- **Tempo:** O(log n) médio, O(n) pior caso (muitas duplicatas), **Espaço:** O(1)

### Método 2: Busca Linear (Verificação)
- Para validação dos resultados
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Encontrar Pivô + Busca Binária
- Primeiro encontra o pivô (elemento mínimo)
- Depois faz busca binária com offset do pivô
- **Tempo:** O(log n) médio, O(n) pior caso, **Espaço:** O(1)

## Complexidade

| Método | Tempo (médio) | Tempo (pior) | Espaço |
|--------|---------------|--------------|--------|
| Binária Modificada | O(log n) | O(n) | O(1) |
| Linear | O(n) | O(n) | O(1) |
| Pivô + Binária | O(log n) | O(n) | O(1) |

## Implementação

A solução principal usa **Busca Binária Modificada** lidando com duplicatas.

```java
public boolean search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return true;
        }
        
        // Handle duplicates: can't determine sorted side
        if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
            left++;
            right--;
        }
        // Left side is sorted
        else if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // Right side is sorted
        else {
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return false;
}
```

## Casos de Teste

- `[2,5,6,0,0,1,2], 0` → `true`
- `[2,5,6,0,0,1,2], 3` → `false`
- `[1,0,1,1,1], 0` → `true`
- `[1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1], 2` → `true`
- `[1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1], 0` → `false`
- `[3,1], 1` → `true`
- `[1,3], 3` → `true`
- `[3,5,1], 3` → `true`
- `[1,1,3,1], 3` → `true`
- `[1,3,1,1,1], 3` → `true`