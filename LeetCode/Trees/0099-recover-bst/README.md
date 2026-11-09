# 0099 - Recover Binary Search Tree

## Descrição

Você recebe o `root` de uma árvore de busca binária (BST), onde os valores de **exatamente dois** nós da árvore foram trocados por engano. Recupere a árvore sem alterar sua estrutura.

## Exemplos

**Exemplo 1:**
```
Input: root = [1,3,null,null,2]
Output: [3,1,null,null,2]
Explanation: 3 não pode ser filho esquerdo de 1 pois 3 > 1. Trocando 1 e 3 corrige a BST.
```

**Exemplo 2:**
```
Input: root = [3,1,4,null,null,2]
Output: [2,1,4,null,null,3]
Explanation: 2 não pode estar na subárvore direita de 3 pois 2 < 3. Trocando 2 e 3 corrige a BST.
```

## Restrições

- O número de nós na árvore está no intervalo `[2, 1000]`.
- `-2^31 <= Node.val <= 2^31 - 1`

## Abordagem

### Método 1: Morris Traversal O(1) Espaço (Principal)
- Inorder traversal sem stack/recursão usando "threads" temporários
- Detecta os dois nós violados durante o traversal:
  - `first`: primeiro nó onde `prev.val > curr.val`
  - `second`: segundo nó onde `prev.val > curr.val` (sempre atualizado)
- Troca os valores no final
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: Inorder com Stack O(h) Espaço
- Inorder iterativo padrão com stack
- Mesma lógica de detecção
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Inorder Recursivo O(h) Espaço
- Recursão simples com variáveis de instância
- **Tempo:** O(n), **Espaço:** O(h)

### Método 4: Coleta, Ordena, Reconstrói O(n) Espaço
- Coleta todos os nós e valores em inorder
- Ordena valores
- Reatribui valores ordenados aos nós
- **Tempo:** O(n log n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Morris | O(n) | O(1) |
| Stack | O(n) | O(h) |
| Recursivo | O(n) | O(h) |
| Coleta/Ordena | O(n log n) | O(n) |

## Implementação

A solução principal usa **Morris Traversal**.

```java
public void recoverTree(TreeNode root) {
    TreeNode first = null, second = null, prev = null;
    TreeNode curr = root;
    
    while (curr != null) {
        if (curr.left == null) {
            if (prev != null && prev.val > curr.val) {
                if (first == null) first = prev;
                second = curr;
            }
            prev = curr;
            curr = curr.right;
        } else {
            TreeNode pred = curr.left;
            while (pred.right != null && pred.right != curr) {
                pred = pred.right;
            }
            
            if (pred.right == null) {
                pred.right = curr;
                curr = curr.left;
            } else {
                pred.right = null;
                if (prev != null && prev.val > curr.val) {
                    if (first == null) first = prev;
                    second = curr;
                }
                prev = curr;
                curr = curr.right;
            }
        }
    }
    
    if (first != null && second != null) {
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
}
```

## Casos de Teste

- `[1,3,null,null,2]` → `[3,1,null,null,2]` (adjacentes)
- `[3,1,4,null,null,2]` → `[2,1,4,null,null,3]` (não adjacentes)
- `[2,1]` → `[1,2]` (dois nós)
- Root e leaf trocados
- Subárvore esquerda trocada
- Subárvore direita trocada
- Já válida (sem troca)
- Perfect BST 15 nós (sem troca)