# LeetCode 151 - Reverse Words in a String

## Problema

Dada uma string de entrada `s`, inverta a ordem das palavras.

Uma palavra é definida como uma sequência de caracteres não-espaço. As palavras em `s` serão separadas por pelo menos um espaço. Retorne uma string das palavras em ordem inversa concatenadas por um único espaço.

Note que `s` pode conter espaços no início ou no final ou múltiplos espaços entre duas palavras. A string retornada deve ter apenas um único espaço separando as palavras. Não inclua espaços extras.

### Exemplos

**Exemplo 1:**
```
Input: s = "the sky is blue"
Output: "blue is sky the"
```

**Exemplo 2:**
```
Input: s = "  hello world  "
Output: "world hello"
Explanation: Sua string invertida não deve conter espaços no início ou no final.
```

**Exemplo 3:**
```
Input: s = "a good   example"
Output: "example good a"
Explanation: Você precisa reduzir múltiplos espaços entre duas palavras a um único espaço na string invertida.
```

### Restrições
- 1 <= s.length <= 10^4
- s contém letras inglesas (maiúsculas e minúsculas), dígitos, e espaços ' '.
- Há pelo menos uma palavra em s.

**Follow-up:** Se o tipo de dado string for mutável na sua linguagem, você pode resolver in-place com O(1) espaço extra?

---

## Abordagens

### Abordagem 1: Split e Reverse - Simples e Limpa

**Intuição:** Divida por espaços em branco, inverta o array, junte com espaço único.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 2: StringBuilder com Parsing Manual - O(n) Tempo, O(n) Espaço

**Intuição:** Parseie palavras manualmente do fim para o início, anexe ao StringBuilder.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

### Abordagem 3: Two-Pass In-Place (para char array mutável) - O(n) Tempo, O(1) Espaço

**Intuição:** 
1. Inverta toda a string
2. Inverta cada palavra
3. Limpe espaços (remova leading, trailing, múltiplos)

**Nota:** Strings em Java são imutáveis, então usa char array.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1) extra (excluindo array de entrada)

---

### Abordagem 4: Usando Deque

**Intuição:** Parseie palavras e adicione no início do deque.

**Complexidade:**
- Tempo: O(n)
- Espaço: O(n)

---

## Comparação de Abordagens

| Abordagem | Tempo | Espaço | Notas |
|-----------|-------|--------|-------|
| Split + Reverse | O(n) | O(n) | **Mais simples** - legível |
| Manual Parsing | O(n) | O(n) | Sem regex, eficiente |
| In-Place (char[]) | O(n) | O(1) | **Ótima** - atende follow-up |
| Deque | O(n) | O(n) | Elegante, usa estrutura de dados |

---

## Casos de Teste

| Entrada | Saída Esperada | Descrição |
|---------|----------------|-----------|
| "the sky is blue" | "blue is sky the" | Exemplo 1 |
| "  hello world  " | "world hello" | Exemplo 2 - espaços leading/trailing |
| "a good   example" | "example good a" | Exemplo 3 - múltiplos espaços |
| "hello" | "hello" | Palavra única |
| "  a   b  c   " | "c b a" | Múltiplos espaços |
| "   leading and trailing   " | "trailing and leading" | Leading/trailing |

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

- [LeetCode 151 - Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/)
- String Manipulation
- Two Pointers Technique