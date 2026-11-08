# 30. Substring with Concatenation of All Words

- **Plataforma:** [LeetCode](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)
- **Dificuldade:** Difícil (Hard)
- **Categoria:** Strings / Sliding Window
- **Linguagem:** Java 21

---

## 📝 Descrição do Problema

Você recebe uma string `s` e um array de strings `words`. Todas as strings em `words` têm o **mesmo comprimento**.

Retorne todos os índices iniciais de substring(s) em `s` que é uma concatenação de **cada palavra em `words` exatamente uma vez**, em qualquer ordem, e sem nenhum caractere intermediário.

Você pode retornar a resposta em **qualquer ordem**.

---

### Exemplos

#### Exemplo 1:
```text
Entrada: s = "barfoothefoobarman", words = ["foo","bar"]
Saída: [0,9]
Explicação: Substrings começando no índice 0 e 9 são "barfoo" e "foobar" respectivamente.
A ordem da saída não importa.
```

#### Exemplo 2:
```text
Entrada: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
Saída: [8]
```

#### Exemplo 3:
```text
Entrada: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
Saída: [6,9,12]
```

---

## ⚙️ Restrições

- $1 \le \text{s.length} \le 10^4$
- $1 \le \text{words.length} \le 5000$
- $1 \le \text{words[i].length} \le 30$
- `s` e `words[i]` consistem de letras inglesas minúsculas.

---

## 💡 Abordagem: Janela Deslizante (Sliding Window) com Hash Map

Como todas as palavras têm o mesmo comprimento, podemos usar uma janela deslizante de tamanho fixo (`wordLen * wordCount`) e verificar se a substring contém exatamente as palavras necessárias.

### Algoritmo

1. Calcula `wordLen`, `wordCount`, `totalLen`
2. Cria mapa de frequência das palavras (`wordFreq`)
3. Para cada offset inicial `0` a `wordLen-1`:
   - Inicializa janela com `left = offset`, `count = 0`, `windowFreq = {}`
   - Move `right` de `offset` até `n - wordLen` em passos de `wordLen`:
     - Extrai palavra atual: `s.substring(right, right + wordLen)`
     - Se palavra está em `wordFreq`:
       - Adiciona a `windowFreq`, incrementa `count`
       - Se frequência excede a permitida: contrai janela pela esquerda
       - Se `count == wordCount`: encontrou substring válida, adiciona `left` ao resultado
     - Senão: reseta janela (`left = right + wordLen`, limpa `windowFreq`, `count = 0`)
4. Retorna lista de índices

---

## ⏱️ Análise de Complexidade

- **Tempo:** $O(n \times m \times k)$ — Onde $n = \text{s.length}$, $m = \text{words.length}$, $k = \text{word length}$. Na prática, $O(n \times k)$ pois cada caractere é processado um número constante de vezes.
- **Espaço:** $O(m \times k)$ — Para armazenar os mapas de frequência.