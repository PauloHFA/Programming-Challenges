# 0088 - Merge Sorted Array

## Descrição

Você recebe dois arrays de inteiros `nums1` e `nums2`, ordenados em ordem não decrescente, e dois inteiros `m` e `n`, representando o número de elementos em `nums1` e `nums2` respectivamente.

Faça a fusão (merge) de `nums1` e `nums2` em um único array ordenado em ordem não decrescente.

O array final ordenado não deve ser retornado pela função, mas sim armazenado dentro do array `nums1`. Para acomodar isso, `nums1` tem um comprimento de `m + n`, onde os primeiros `m` elementos denotam os elementos que devem ser fundidos, e os últimos `n` elementos são definidos como `0` e devem ser ignorados. `nums2` tem um comprimento de `n`.

## Exemplos

**Exemplo 1:**
```
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
```

**Exemplo 2:**
```
Input: nums1 = [1], m = 1, nums2 = [], n = 0
Output: [1]
```

**Exemplo 3:**
```
Input: nums1 = [0], m = 0, nums2 = [1], n = 1
Output: [1]
```

## Restrições

- `nums1.length == m + n`
- `nums2.length == n`
- `0 <= m, n <= 200`
- `1 <= m + n <= 200`
- `-10^9 <= nums1[i], nums2[j] <= 10^9`

## Abordagem

### Método 1: Three Pointers from End (Principal)
- Usa três ponteiros: `i` para fim de nums1 válido, `j` para fim de nums2, `k` para fim do array mesclado
- Compara `nums1[i]` e `nums2[j]`, coloca o maior em `nums1[k]`
- Evita sobrescrever elementos de nums1 não processados
- **Tempo:** O(m + n), **Espaço:** O(1)

### Método 2: Copy + Sort
- Copia nums2 para o final de nums1, depois ordena
- Simples mas não aproveita arrays já ordenados
- **Tempo:** O((m+n) log(m+n)), **Espaço:** O(1) ou O(log(m+n)) para sort

### Método 3: Extra Space
- Copia nums1 válido para array auxiliar, depois merge padrão
- **Tempo:** O(m + n), **Espaço:** O(m)

### Método 4: Insertion Style
- Insere cada elemento de nums2 na posição correta em nums1
- **Tempo:** O(m × n), **Espaço:** O(1)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Three Pointers | O(m + n) | O(1) |
| Copy + Sort | O((m+n) log(m+n)) | O(1)* |
| Extra Space | O(m + n) | O(m) |
| Insertion | O(m × n) | O(1) |

*Depende da implementação do sort

## Implementação

A solução principal usa **Three Pointers from End**.

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;      // Pointer for nums1's valid elements
    int j = n - 1;      // Pointer for nums2
    int k = m + n - 1;  // Pointer for merged array end
    
    while (j >= 0) {
        if (i >= 0 && nums1[i] > nums2[j]) {
            nums1[k--] = nums1[i--];
        } else {
            nums1[k--] = nums2[j--];
        }
    }
}
```

## Casos de Teste

- `[1,2,3,0,0,0], m=3, [2,5,6], n=3` → `[1,2,2,3,5,6]`
- `[1], m=1, [], n=0` → `[1]`
- `[0], m=0, [1], n=1` → `[1]`
- `[4,5,6,0,0,0], m=3, [1,2,3], n=3` → `[1,2,3,4,5,6]`
- `[1,2,3,0,0,0], m=3, [4,5,6], n=3` → `[1,2,3,4,5,6]`
- `[1,3,5,0,0,0], m=3, [2,4,6], n=3` → `[1,2,3,4,5,6]`
- `[1,2,2,0,0,0], m=3, [2,2,3], n=3` → `[1,2,2,2,2,3]`
- `[2,0], m=1, [1], n=1` → `[1,2]`
- `[1,2,3,4,5,0], m=5, [6], n=1` → `[1,2,3,4,5,6]`
- `[5,0,0,0,0,0], m=1, [1,2,3,4,6], n=5` → `[1,2,3,4,5,6]`
- `[1,1,1,0,0,0], m=3, [1,1,1], n=3` → `[1,1,1,1,1,1]`
- `[-3,-2,0,0,0], m=2, [-1,1,2], n=3` → `[-3,-2,-1,1,2]`