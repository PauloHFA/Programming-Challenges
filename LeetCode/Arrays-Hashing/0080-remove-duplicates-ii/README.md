# 0080 - Remove Duplicates from Sorted Array II

## Descrição

Dado um array de inteiros `nums` ordenado em ordem não decrescente, remova as duplicatas in-place de forma que cada elemento único apareça no máximo **duas vezes**. A ordem relativa dos elementos deve ser mantida.

Como é impossível alterar o comprimento do array em algumas linguagens, você deve colocar o resultado na primeira parte do array `nums`. Mais formalmente, se houver `k` elementos após remover as duplicatas, então os primeiros `k` elementos de `nums` devem conter o resultado final. Não importa o que você deixe além dos primeiros `k` elementos.

Retorne `k` após colocar o resultado final nos primeiros `k` slots de `nums`.

Não aloque espaço extra para outro array. Você deve fazer isso modificando o array de entrada in-place com O(1) de memória extra.

## Exemplos

**Exemplo 1:**
```
Input: nums = [1,1,1,2,2,3]
Output: 5, nums = [1,1,2,2,3,_]
Explanation: A função retorna k = 5, com os primeiros 5 elementos de nums sendo 1, 1, 2, 2, 3.
```

**Exemplo 2:**
```
Input: nums = [0,0,1,1,1,1,2,3,3]
Output: 7, nums = [0,0,1,1,2,3,3,_,_]
Explanation: A função retorna k = 7, com os primeiros 7 elementos de nums sendo 0, 0, 1, 1, 2, 3, 3.
```

## Restrições

- `1 <= nums.length <= 3 * 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` está ordenado em ordem não decrescente.

## Abordagem

### Método 1: Two Pointers (Principal) - O(1) espaço
- `slow` aponta para a posição onde colocar o próximo elemento válido
- `fast` percorre o array
- Permite no máximo 2 duplicatas: verifica se `nums[fast] != nums[slow-2]`
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Generalizado para k duplicatas
- Mesmo princípio, mas parametrizado para k duplicatas
- **Tempo:** O(n), **Espaço:** O(1)

### Método 3: Contador de duplicatas
- Mantém contador de ocorrências consecutivas
- **Tempo:** O(n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Two Pointers | O(n) | O(1) |
| Generalizado k | O(n) | O(1) |
| Contador | O(n) | O(1) |

## Implementação

A solução principal usa **Two Pointers**.

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) {
        return nums.length;
    }
    
    int slow = 2;
    
    for (int fast = 2; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow - 2]) {
            nums[slow] = nums[fast];
            slow++;
        }
    }
    
    return slow;
}
```

## Casos de Teste

- `[1,1,1,2,2,3]` → `5, [1,1,2,2,3]`
- `[0,0,1,1,1,1,2,3,3]` → `7, [0,0,1,1,2,3,3]`
- `[1,1,1,1,1]` → `2, [1,1]`
- `[1,2,3,4,5]` → `5, [1,2,3,4,5]`
- `[1,1]` → `2, [1,1]`
- `[1]` → `1, [1]`
- `[]` → `0, []`
- `[1,1,1,2,2,2,3,3,3]` → `6, [1,1,2,2,3,3]`
- `[0,0,0,0,0]` → `2, [0,0]`
- `[-1,-1,-1,0,0,1,1,1]` → `6, [-1,-1,0,0,1,1]`