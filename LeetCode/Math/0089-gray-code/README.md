# 0089 - Gray Code

## Descrição

Um **Gray code** de n bits é uma sequência de 2^n inteiros onde:
1. Cada inteiro está no intervalo [0, 2^n - 1]
2. O primeiro inteiro é 0
3. Cada par de inteiros adjacentes difere em **exatamente 1 bit**
4. O primeiro e o último inteiros também diferem em exatamente 1 bit (propriedade circular)

Dado um inteiro `n`, retorne qualquer sequência válida de Gray code de n bits.

## Exemplos

**Exemplo 1:**
```
Input: n = 2
Output: [0,1,3,2]
Explanation: 
00 -> 01 -> 11 -> 10
0  -> 1  -> 3  -> 2
```

**Exemplo 2:**
```
Input: n = 1
Output: [0,1]
```

## Restrições

- `1 <= n <= 16`

## Abordagem

### Método 1: Fórmula Matemática (Principal)
- Gray code de i = `i ^ (i >> 1)`
- Propriedade: i-ésimo Gray code = i XOR (i deslocado 1 bit à direita)
- **Tempo:** O(2^n), **Espaço:** O(2^n)

### Método 2: Reflexão Recursiva (Construção Clássica)
- n=1: [0, 1]
- n=k: pega sequência de k-1, espelha, adiciona prefixo 0 na primeira metade e 1 na segunda
- **Tempo:** O(2^n), **Espaço:** O(2^n)

### Método 3: Backtracking
- Busca em profundidade no hipercubo n-dimensional
- Encontra caminho hamiltoniano
- **Tempo:** O(2^n) na prática, **Espaço:** O(2^n)

### Método 4: Iterativo com LSB
- `gray[i] = gray[i-1] ^ (i & -i)` (lowest set bit)
- Derivação da fórmula principal
- **Tempo:** O(2^n), **Espaço:** O(2^n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Fórmula | O(2^n) | O(2^n) |
| Reflexão | O(2^n) | O(2^n) |
| Backtracking | O(2^n) | O(2^n) |
| Iterativo LSB | O(2^n) | O(2^n) |

## Implementação

A solução principal usa a **Fórmula Matemática**.

```java
public List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    int total = 1 << n;  // 2^n
    
    for (int i = 0; i < total; i++) {
        result.add(i ^ (i >> 1));
    }
    
    return result;
}
```

## Casos de Teste

- `n = 2` → `[0,1,3,2]` (00, 01, 11, 10)
- `n = 1` → `[0,1]` (0, 1)
- `n = 3` → `[0,1,3,2,6,7,5,4]`
- `n = 4` → 16 elementos
- `n = 0` → `[0]` (edge case)
- `n = 5` → 32 elementos

Todos os métodos produzem sequências válidas de Gray code (verificadas pela propriedade de diferença de 1 bit entre adjacentes e circular).