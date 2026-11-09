# LeetCode 149 - Max Points on a Line

## Problema

Dado um array de pontos onde `points[i] = [xi, yi]` representa um ponto no plano X-Y, retorne o número máximo de pontos que estão na mesma linha reta.

### Exemplos

**Exemplo 1:**
```
Input: points = [[1,1],[2,2],[3,3]]
Output: 3
```

**Exemplo 2:**
```
Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
Output: 4
```

### Restrições
- 1 <= points.length <= 300
- points[i].length == 2
- -10^4 <= xi, yi <= 10^4
- Todos os pontos são únicos.

---

## Abordagens

### Abordagem 1: HashMap com Inclinação (Double) - O(n²) Tempo

**Intuição:** Para cada ponto, calcule as inclinações para todos os outros pontos. Use HashMap para contar pontos com a mesma inclinação. Trate linhas verticais (inclinação infinita) e pontos duplicados.

**Algoritmo:**
1. Para cada ponto i como base:
   - Calcule inclinação para todos os pontos j > i
   - Use HashMap<Double, Integer> para contar ocorrências de cada inclinação
   - Trate casos especiais: pontos duplicados, linhas verticais
2. O máximo para cada base + 1 (o próprio ponto base) + duplicatas

**Complexidade:**
- Tempo: O(n²)
- Espaço: O(n)

---

### Abordagem 2: HashMap com Inclinação Normalizada (Fração) - O(n²) Tempo

**Intuição:** Use MDC para normalizar a inclinação como uma fração (dy/dx) para evitar problemas de precisão de ponto flutuante. Represente a inclinação como string "dy/dx" ou par.

**Complexidade:**
- Tempo: O(n² * log(max coordenada))
- Espaço: O(n)

---

### Abordagem 3: Usando Classe Pair para Inclinação (mais limpo)

**Intuição:** Encapsule a lógica de normalização em uma classe Slope com equals/hashCode adequados.

**Complexidade:**
- Tempo: O(n² * log(max coordenada))
- Espaço: O(n)

---

### Abordagem 4: Força Bruta com Equação da Linha (para verificação)

**Intuição:** Verifique todos os pares de pontos para definir linhas, depois conte pontos em cada linha.

**Complexidade:**
- Tempo: O(n³)
- Espaço: O(n²)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Double Slope | O(n²) | O(n) | Rápida, mas problemas de precisão |
| Fraction (String) | O(n² log C) | O(n) | **Robusta** - sem problemas de precisão |
| Pair Class | O(n² log C) | O(n) | **Limpa** - orientada a objetos |
| Brute Force | O(n³) | O(n²) | Apenas para verificação |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| [[1,1],[2,2],[3,3]] | 3 | Exemplo 1 - diagonal |
| [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]] | 4 | Exemplo 2 |
| [[0,0],[1,1]] | 2 | Dois pontos |
| [[0,0]] | 1 | Ponto único |
| [[0,0],[0,1],[0,2],[0,3]] | 4 | Linha vertical |
| [[0,0],[1,0],[2,0],[3,0]] | 4 | Linha horizontal |
| [[0,0],[0,0],[1,1]] | 3 | Pontos duplicados |
| [[0,0],[1,2],[2,1],[3,3]] | 2 | Nenhum trio colinear |

---

## Implementação

O arquivo `Solution.java` contém todas as 4 abordagens com uma suite de testes abrangente no método `main()` que verifica a correção de todas as implementações.

### Como executar

```bash
javac Solution.java
java Solution
```

---

## Referências

- [LeetCode 149 - Max Points on a Line](https://leetcode.com/problems/max-points-on-a-line/)
- Computational Geometry
- Hash Map with Slope Normalization