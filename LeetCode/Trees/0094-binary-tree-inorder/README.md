# 0094 - Binary Tree Inorder Traversal

## Descrição

Dado o `root` de uma árvore binária, retorne a travessia **inorder** dos valores de seus nós.

Inorder: esquerda → raiz → direita

## Exemplos

**Exemplo 1:**
```
Input: root = [1,null,2,3]
Output: [1,3,2]
```

**Exemplo 2:**
```
Input: root = []
Output: []
```

**Exemplo 3:**
```
Input: root = [1]
Output: [1]
```

## Restrições

- O número de nós na árvore está no intervalo `[0, 100]`.
- `-100 <= Node.val <= 100`

## Abordagem

### Método 1: Iterativo com Stack (Principal)
- Simula a recursão usando stack explícita
- Vai para o nó mais à esquerda empilhando o caminho
- Desempilha, processa, vai para direita
- **Tempo:** O(n), **Espaço:** O(h) onde h = altura da árvore

### Método 2: Recursivo
- Implementação direta da definição
- **Tempo:** O(n), **Espaço:** O(h) stack de recursão

### Método 3: Morris Traversal (O(1) Espaço)
- Usa "threads" temporários para evitar stack
- Modifica a árvore temporariamente, restaura depois
- **Tempo:** O(n), **Espaço:** O(1)

### Método 4: Stack com Estado
- Simula recursão com estado explícito (0=left, 1=self, 2=right)
- Push na ordem reversa: right, self, left
- **Tempo:** O(n), **Espaço:** O(h)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Stack Iterativo | O(n) | O(h) |
| Recursivo | O(n) | O(h) |
| Morris | O(n) | O(1) |
| Stack Estado | O(n) | O(h) |

## Implementação

A solução principal usa **Iterativo com Stack**.

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    
    while (curr != null || !stack.isEmpty()) {
        // Vai para o nó mais à esquerda
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        
        // Processa o nó
        curr = stack.pop();
        result.add(curr.val);
        
        // Vai para a subárvore direita
        curr = curr.right;
    }
    
    return result;
}
```

## Casos de Teste

- `[1,null,2,3]` → `[1,3,2]`
- `[]` → `[]`
- `[1]` → `[1]`
- Complete tree `[1,2,3,4,5,6,7]` → `[4,2,5,1,6,3,7]`
- Left skewed `[1,2,null,3,null,4]` → `[4,3,2,1]`
- Right skewed `[1,null,2,null,3,null,4]` → `[1,2,3,4]`
- Only left `[4,3,null,2,null,1]` → `[1,2,3,4]`
- Two nodes left `[2,1]` → `[1,2]`
- Two nodes right `[1,null,2]` → `[1,2]`