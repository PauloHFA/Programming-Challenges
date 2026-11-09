# 0109 - Convert Sorted List to Binary Search Tree

## Descrição

Dado o `head` de uma lista ligada simplesmente onde os elementos estão ordenados em ordem crescente, converta-a em uma árvore binária de busca balanceada em altura.

## Exemplos

**Exemplo 1:**
```
Input: head = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
```

**Exemplo 2:**
```
Input: head = [1,3]
Output: [3,1]
```

## Restrições

- O número de nós na lista está no intervalo `[0, 2 * 10^4]`.
- `-10^5 <= Node.val <= 10^5`

## Abordagem

### Método 1: Converter para Array + Recursivo (Principal)
- Primeiro converte lista para array O(n)
- Depois usa mesma abordagem do problema 0108 (meio como raiz)
- **Tempo:** O(n), **Espaço:** O(n) array + O(log n) stack

### Método 2: Fast/Slow Pointers In-Place
- Usa ponteiros rápido/lento para encontrar meio da lista
- Recursivamente constrói esquerda, cria raiz, depois direita
- **Tempo:** O(n log n), **Espaço:** O(log n) stack

### Método 3: Inorder Simulation (O(n) tempo)
- Simula inorder traversal: constrói esquerda, consome nó da lista, constrói direita
- Precisa saber tamanho da lista primeiro
- **Tempo:** O(n), **Espaço:** O(log n) stack

### Método 4: Bottom-up Iterativo
- Constrói nível por nível de baixo para cima
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Array + Recursivo | O(n) | O(n) |
| Fast/Slow In-Place | O(n log n) | O(log n) |
| Inorder Simulation | O(n) | O(log n) |
| Bottom-up | O(n) | O(n) |

## Implementação

A solução principal usa **Converter para Array + Recursivo**.

```java
public TreeNode sortedListToBST(ListNode head) {
    List<Integer> values = new ArrayList<>();
    while (head != null) {
        values.add(head.val);
        head = head.next;
    }
    int[] nums = new int[values.size()];
    for (int i = 0; i < values.size(); i++) {
        nums[i] = values.get(i);
    }
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

- `[-10,-3,0,5,9]` → BST balanceado
- `[1,3]` → BST balanceado
- `[1]` → nó único
- `[1,2]` → dois nós
- `[1,2,3]` → três nós
- `[1,2,3,4]` → quatro nós
- `[1,2,3,4,5]` → cinco nós
- `[1,2,3,4,5,6,7]` → sete nós (perfect tree)
- `[-5,-4,-3,-2,-1]` → valores negativos
- `[1..15]` → 15 elementos
- `[]` → lista vazia (retorna null)