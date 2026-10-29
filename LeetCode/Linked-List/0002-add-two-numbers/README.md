# 2. Add Two Numbers

- **Plataforma:** [LeetCode](https://leetcode.com/problems/add-two-numbers/)
- **Dificuldade:** Médio (Medium)
- **Categoria:** Linked List
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Você recebe duas listas encadeadas não vazias representando dois números inteiros não negativos. Os dígitos são armazenados em **ordem inversa**, e cada um de seus nós contém um único dígito. Adicione os dois números e retorne a soma como uma lista encadeada.

Você pode assumir que os dois números não contêm nenhum zero à esquerda, exceto o próprio número 0.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: l1 = [2,4,3], l2 = [5,6,4]
Saída: [7,0,8]
Explicação: 342 + 465 = 807.
```

#### Exemplo 2:
```text
Entrada: l1 = [0], l2 = [0]
Saída: [0]
```

#### Exemplo 3:
```text
Entrada: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Saída: [8,9,9,9,0,0,0,1]
```

---

## ⚙️ Restrições

- O número de nós em cada lista encadeada está no intervalo $[1, 100]$.
- $0 \le \text{Node.val} \le 9$
- É garantido que a lista representa um número que não possui zeros à esquerda (exceto o próprio número 0).

---

## 💡 Abordagem: Simulação de Adição Elementar com Carry

Como os dígitos estão armazenados na ordem inversa (o dígito menos significativo está na cabeça da lista), podemos simular a operação de adição matemática coluna por coluna, da esquerda para a direita, propagando o "vai-um" (*carry*):

1. **Nó Sentinela (*Dummy Head*):** Inicializamos um nó sentinela para simplificar a manipulação dos ponteiros e a construção da lista resultante.
2. **Iteração Conjunta:** Mantemos um ponteiro de iteração e percorremos as listas enquanto houver elementos em $l_1$, em $l_2$ ou enquanto restar um valor residual de $carry$:
   - Somamos os valores presentes nos nós atuais (se existirem) ao $carry$.
   - O novo dígito a ser adicionado à lista resultante é $\text{soma} \pmod{10}$.
   - O novo $carry$ para a próxima posição é $\lfloor \text{soma} / 10 \rfloor$.
   - Avançamos os ponteiros de $l_1$ e $l_2$ conforme aplicável.
3. **Retorno:** Retornamos `dummyHead.next`, que representa o início da lista somada resultante.

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(\max(N, M))$ — Sendo $N$ e $M$ os comprimentos das listas $l_1$ e $l_2$, respectivamente. O laço executa no máximo $\max(N, M) + 1$ iterações.
- **Espaço:** $O(\max(N, M))$ — O comprimento da nova lista encadeada retornada é de no máximo $\max(N, M) + 1$ nós. O espaço auxiliar adicional (além da lista retornada) é $O(1)$.
