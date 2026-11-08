# 11. Container With Most Water

- **Plataforma:** [LeetCode](https://leetcode.com/problems/container-with-most-water/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Two Pointers
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Você recebe um array de inteiros `height` de comprimento `n`. Existem `n` linhas verticais desenhadas de forma que os dois pontos finais da linha `i` são `(i, 0)` e `(i, height[i])`.

Encontre duas linhas que, junto com o eixo x, formem um recipiente que contenha a maior quantidade de água.

Retorne a **quantidade máxima de água** que um recipiente pode armazenar.

**Nota:** Você não pode inclinar o recipiente.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: height = [1,8,6,2,5,4,8,3,7]
Saída: 49
Explicação: As linhas verticais nos índices 1 e 8 formam o recipiente com maior área (altura = 7, largura = 7).
```

#### Exemplo 2:
```text
Entrada: height = [1,1]
Saída: 1
```

---

## ⚙️ Restrições

- $n == \text{height.length}$
- $2 \le n \le 10^5$
- $0 \le \text{height}[i] \le 10^4$

---

## 💡 Abordagem: Dois Ponteiros (Two Pointers - Greedy)

A ideia é usar dois ponteiros, um no início (`left`) e outro no final (`right`), e mover o ponteiro que aponta para a linha **menor**.

### Por que funciona?

A área é determinada pela **linha menor** (altura) multiplicada pela **distância** entre as linhas (largura). Se movermos a linha maior, a largura diminui e a altura não pode aumentar (já é limitada pela linha menor). Ao mover a linha menor, temos a chance de encontrar uma linha mais alta que compense a perda de largura.

### Algoritmo

1. Inicializa `left = 0`, `right = n - 1`, `maxArea = 0`
2. Enquanto `left < right`:
   - Calcula área atual: `min(height[left], height[right]) * (right - left)`
   - Atualiza `maxArea`
   - Move o ponteiro da linha menor: se `height[left] < height[right]`, `left++`, senão `right--`
3. Retorna `maxArea`

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n)$ — Cada ponteiro percorre o array no máximo uma vez.
- **Espaço:** $O(1)$ — Apenas variáveis auxiliares.