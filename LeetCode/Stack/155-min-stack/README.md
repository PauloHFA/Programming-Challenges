# LeetCode 155 - Min Stack

## Problema

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the MinStack class:
- `MinStack()` initializes the stack object.
- `void push(int val)` pushes the element val onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with O(1) time complexity for each function.

### Exemplo 1:
```
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
Output
[null,null,null,null,-3,null,0,-2]

Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

### Restrições:
- -2^31 <= val <= 2^31 - 1
- Methods pop, top and getMin operations will always be called on non-empty stacks.
- At most 3 * 10^4 calls will be made to push, pop, top, and getMin.

---

## Abordagens

### Abordagem 1: Duas Pilhas (Two Stacks)
Mantém duas pilhas: uma para valores e outra para mínimos.
- **Push**: Se o novo valor <= mínimo atual, empilha na pilha de mínimos.
- **Pop**: Se o valor removido == mínimo atual, desempilha da pilha de mínimos.
- **Complexidade**: O(1) tempo, O(n) espaço no pior caso.

### Abordagem 2: Pilha Única com Pares (Pairs)
Armazena pares (valor, mínimo_atual) em uma única pilha.
- Cada elemento sabe o mínimo no momento em que foi empilhado.
- **Complexidade**: O(1) tempo, O(n) espaço.

### Abordagem 3: Codificação de Diferença (Difference Encoding)
Armazena a diferença entre o valor e o mínimo atual.
- Quando `val < min`, armazena `val - min` (negativo) e atualiza `min`.
- Ao desempilhar, se `diff < 0`, restaura o mínimo anterior: `min = min - diff`.
- **Complexidade**: O(1) tempo, O(n) espaço.

### Abordagem 4: Baseado em Array (Array-based)
Usa arrays em vez de Stack para melhor performance.
- **Complexidade**: O(1) tempo, O(n) espaço.

---

## Complexidade

| Abordagem | Push | Pop | Top | GetMin | Espaço |
|-----------|------|-----|-----|--------|--------|
| Two Stacks | O(1) | O(1) | O(1) | O(1) | O(n) |
| Pairs | O(1) | O(1) | O(1) | O(1) | O(n) |
| Difference | O(1) | O(1) | O(1) | O(1) | O(n) |
| Array | O(1) | O(1) | O(1) | O(1) | O(n) |

---

## Casos de Teste

1. **Exemplo do problema**: push(-2), push(0), push(-3), getMin()=-3, pop(), top()=0, getMin()=-2
2. **Push, pop, push, getMin**: push(5), push(3), push(7), getMin()=3, pop(), push(2), getMin()=2
3. **Números negativos**: push(-5), push(-10), push(-3), getMin()=-10
4. **Mínimos duplicados**: push(2), push(2), push(1), push(1), getMin()=1
5. **Valores extremos**: Integer.MAX_VALUE, Integer.MIN_VALUE

---

## Implementação

A solução está em `Solution.java` com 4 abordagens diferentes, todas testadas e verificadas cruzadamente no método `main()`.