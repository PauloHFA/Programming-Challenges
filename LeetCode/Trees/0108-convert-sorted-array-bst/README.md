# 0108 - Convert Sorted Array to Binary Search Tree

## Descrição

Dado um array de inteiros `nums` onde os elementos estão ordenados em ordem crescente, converta-o em uma árvore binária de busca balanceada em altura.

## Exemplos

**Exemplo 1:**
```
Input: nums = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
Explicação: [0,-10,5,null,-3,null,9] também é aceito.
```

**Exemplo 2:**
```
Input: nums = [1,3]
Output: [3,1]
Explicação: [1,null,3] e [3,1] são ambos BSTs balanceados em altura.
```

## Restrições

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` está ordenado em ordem **estritamente crescente**.

## Abordagem

### Método 1: Recursivo - Meio como raiz (Principal)
- Escolhe o elemento do meio como raiz para garantir balanceamento
- Recursivamente constrói subárvore esquerda com metade esquerda
- Recursivamente constrói subárvore direita com metade direita
- **Tempo:** O(n), **Espaço:** O(log n) stack (árvore balanceada)

### Método 2: Iterativo com Stack
- Simula a recursão usando stack explícita
- Cada entrada na stack: (nó pai, left, right, isLeftChild, nodeRef)
- **Tempo:** O(n), **Espaço:** O(log n)

### Método 3: Recursivo com teto (mid = (left + right + 1) / 2)
- Escolhe meio superior quando número par de elementos
- Também produz árvore balanceada
- **Tempo:** O(n), **Espaço:** O(log n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo (piso) | O(n) | O(log n) |
| Iterativo | O(n) | O(log n) |
| Recursivo (teto) | O(n) | O(log n) |

## Implementação

A solução principal usa **Recursivo com meio como raiz**.

```java
public TreeNode sortedArrayToBST(int[] nums) {
    return build(nums, 0, nums.length - 1);
}

private TreeNode build(int[] nums, int left, int right) {
    if (left > right) return null;

    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);

    root.left = build(nums, left, mid - 1);
    root.right = build(nums, mid + 1, right);

    return root;
}
```

## Casos de Teste

- `[-10,-3,0,5,9]` → BST balanceado (raiz 0 ou 5)
- `[1,3]` → BST balanceado (raiz 1 ou 3)
- `[1]` → nó único
- `[1,2]` → dois nós
- `[1,2,3]` → três nós (raiz 2)
- `[1,2,3,4]` → quatro nós
- `[1,2,3,4,5]` → cinco nós
- `[1,2,3,4,5,6,7]` → sete nós (perfect tree)
- `[-5,-4,-3,-2,-1]` → valores negativos
- `[1..15]` → 15 elementos
- `[]` → array vazio (retorna null)