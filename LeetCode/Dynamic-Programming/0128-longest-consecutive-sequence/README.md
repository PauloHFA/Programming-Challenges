# 0128 - Longest Consecutive Sequence

## Descrição

Dado um array não ordenado de inteiros `nums`, retorne o comprimento da sequência de elementos consecutivos mais longa.

Você deve escrever um algoritmo que execute em tempo **O(n)**.

## Exemplos

**Exemplo 1:**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explicação: A sequência consecutiva mais longa é [1, 2, 3, 4]. Logo, o comprimento é 4.
```

**Exemplo 2:**
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explicação: A sequência consecutiva mais longa é [0, 1, 2, 3, 4, 5, 6, 7, 8]. Logo, o comprimento é 9.
```

## Restrições

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## Abordagem

### Método 1: HashSet (Principal)
- Adiciona todos os números a um HashSet
- Para cada número, verifica se é início de sequência (`num-1` não existe)
- Se for início, expande para frente contando o streak
- **Tempo:** O(n), **Espaço:** O(n)

### Método 2: Union Find (Disjoint Set)
- Une números consecutivos em conjuntos
- Mantém tamanho de cada conjunto
- **Tempo:** O(n * α(n)), **Espaço:** O(n)

### Método 3: HashMap com Limites
- Armazena apenas os limites (início e fim) de cada sequência
- Atualiza limites em O(1) ao encontrar novo número
- **Tempo:** O(n), **Espaço:** O(n)

### Método 4: Sorting (Referência)
- Ordena o array e conta sequências
- **Tempo:** O(n log n), **Espaço:** O(1) - não atende requisito O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| HashSet | O(n) | O(n) |
| Union Find | O(n α(n)) | O(n) |
| HashMap Limites | O(n) | O(n) |
| Sorting | O(n log n) | O(1) |

## Implementação

A solução principal usa **HashSet**.

```java
public int longestConsecutive(int[] nums) {
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
        numSet.add(num);
    }
    
    int longest = 0;
    
    for (int num : numSet) {
        if (!numSet.contains(num - 1)) {
            int currentNum = num;
            int streak = 1;
            
            while (numSet.contains(currentNum + 1)) {
                currentNum++;
                streak++;
            }
            
            longest = Math.max(longest, streak);
        }
    }
    
    return longest;
}
```

## Casos de Teste

- `[100,4,200,1,3,2]` → `4`
- `[0,3,7,2,5,8,4,6,0,1]` → `9`
- `[]` → `0`
- `[5]` → `1`
- `[1,1,1,1]` → `1`
- `[-1,-2,-3,0,1]` → `5`
- `[1,2,3,10,11,12]` → `3`
- `[9,1,4,7,3,-1,0,5,8,-1,6]` → `7`
- `[1,3,5,7,9]` → `1`
- `[10,9,8,7,6,5,4,3,2,1]` → `10`