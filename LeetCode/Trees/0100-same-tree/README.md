# 0100 - Same Tree

## Descrição

Dadas as raízes de duas árvores binárias `p` e `q`, verifique se elas são iguais.

Duas árvores binárias são consideradas iguais se são estruturalmente idênticas e os nós têm o mesmo valor.

## Exemplos

**Exemplo 1:**
```
Input: p = [1,2,3], q = [1,2,3]
Output: true
```

**Exemplo 2:**
```
Input: p = [1,2], q = [1,null,2]
Output: false
```

**Exemplo 3:**
```
Input: p = [1,2,1], q = [1,1,2]
Output: false
```

## Restrições

- O número de nós em ambas as árvores está no intervalo `[0, 100]`.
- `-10^4 <= Node.val <= 10^4`

## Abordagem

### Método 1: Recursivo (Principal)
- Caso base: ambos null = true, um null = false
- Compara valores, depois recursa em esquerda e direita
- **Tempo:** O(n), **Espaço:** O(h) stack

### Método 2: Iterativo com Stack (DFS)
- Usa stack explícita com pares de nós
- Processa em ordem pré-ordem
- **Tempo:** O(n), **Espaço:** O(h)

### Método 3: Iterativo com Queue (BFS)
- Usa queue com pares de nós
- Processa nível por nível
- **Tempo:** O(n), **Espaço:** O(w) largura máxima

### Método 4: Serialização
- Serializa ambas árvores em string (preorder com marcadores #)
- Compara strings
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Recursivo | O(n) | O(h) |
| Stack (DFS) | O(n) | O(h) |
| Queue (BFS) | O(n) | O(w) |
| Serialização | O(n) | O(n) |

## Implementação

A solução principal usa **Recursivo**.

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    if (p.val != q.val) return false;
    
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

## Casos de Teste

- `[1,2,3], [1,2,3]` → `true`
- `[1,2], [1,null,2]` → `false`
- `[1,2,1], [1,1,2]` → `false`
- `[], []` → `true`
- `[1], []` → `false`
- `[1], [1]` → `true`
- `[1], [2]` → `false`
- `[1,2,3,4,5,6,7], [1,2,3,4,5,6,7]` → `true`
- `[1,2,3,4,5,6,7], [1,2,3,4,5,6,8]` → `false`
- `[1,2,3,4,null,null,5], [1,2,3,null,4,null,5]` → `false`
- `[-1,-2,-3], [-1,-2,-3]` → `true`
- `[Integer.MAX_VALUE, Integer.MIN_VALUE, 0], [Integer.MAX_VALUE, Integer.MIN_VALUE, 0]` → `true`