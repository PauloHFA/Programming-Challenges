# ☕ Programming Challenges

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange.svg" alt="Java 21" />
  <img src="https://img.shields.io/badge/LeetCode-Solutions-FFA116.svg?logo=leetcode&logoColor=white" alt="LeetCode" />
  <img src="https://img.shields.io/badge/HackerRank-Solutions-2EC866.svg?logo=hackerrank&logoColor=white" alt="HackerRank" />
  <img src="https://img.shields.io/badge/Beecrowd-Solutions-3498DB.svg" alt="Beecrowd" />
  <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License" />
</p>

Repositório dedicado à resolução, análise e documentação de desafios de programação e algoritmos de alto nível das principais plataformas globais (**LeetCode**, **HackerRank**, **Beecrowd**), com implementações em **Java 21**, análise de complexidade assintótica de Tempo e Espaço ($O$), e casos de teste.

---

## 📂 Estrutura do Repositório

```text
Programming-Challenges/
├── LeetCode/
│   ├── Arrays-Hashing/
│   │   └── 0001-two-sum/
│   ├── Two-Pointers/
│   ├── Stack/
│   ├── Sliding-Window/
│   ├── Linked-List/
│   │   └── 0002-add-two-numbers/
│   ├── Trees/
│   └── Graphs/
├── HackerRank/
│   └── Algorithms/
├── Beecrowd/
│   └── Paradigms/
└── README.md
```

---

## 📊 Progresso de Resolução

### 🟡 LeetCode

| # | Problema | Categoria | Dificuldade | Tempo | Espaço | Solução |
| :-: | :--- | :--- | :-: | :-: | :-: | :-: |
| 0001 | [Two Sum](https://leetcode.com/problems/two-sum/) | Arrays & Hashing | Easy | $O(n)$ | $O(n)$ | [Java](./LeetCode/Arrays-Hashing/0001-two-sum/) |
| 0002 | [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) | Linked List | Medium | $O(\max(N, M))$ | $O(\max(N, M))$ | [Java](./LeetCode/Linked-List/0002-add-two-numbers/) |
| 0003 | [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | Sliding Window | Medium | $O(n)$ | $O(\min(m, n))$ | [Java](./LeetCode/Sliding-Window/0003-longest-substring-without-repeating-characters/) |
| 0004 | [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) | Arrays & Hashing (Binary Search) | Hard | $O(\log(\min(m, n)))$ | $O(1)$ | [Java](./LeetCode/Arrays-Hashing/0004-median-of-two-sorted-arrays/) |
| 0005 | [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) | Strings (Two Pointers) | Medium | $O(n^2)$ | $O(1)$ | [Java](./LeetCode/Strings/0005-longest-palindromic-substring/) |
| 0006 | [Zigzag Conversion](https://leetcode.com/problems/zigzag-conversion/) | Strings | Medium | $O(n)$ | $O(n)$ | [Java](./LeetCode/Strings/0006-zigzag-conversion/) |
| 0007 | [Reverse Integer](https://leetcode.com/problems/reverse-integer/) | Math | Medium | $O(\log x)$ | $O(1)$ | [Java](./LeetCode/Math/0007-reverse-integer/) |
| 0008 | [String to Integer (atoi)](https://leetcode.com/problems/string-to-integer-atoi/) | Strings | Medium | $O(n)$ | $O(1)$ | [Java](./LeetCode/Strings/0008-string-to-integer-atoi/) |
| 0009 | [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Math | Easy | $O(\log n)$ | $O(1)$ | [Java](./LeetCode/Math/0009-palindrome-number/) |
| 0010 | [Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/) | Dynamic Programming | Hard | $O(m \times n)$ | $O(m \times n)$ | [Java](./LeetCode/Dynamic-Programming/0010-regular-expression-matching/) |

---

## 🚀 Como Executar os Códigos Localmente

Cada desafio possui sua própria classe auto-contida com método `main` demonstrando casos de teste.

### Compilação e Execução via Terminal:

```bash
# Exemplo: Two Sum
javac LeetCode/Arrays-Hashing/0001-two-sum/Solution.java
java -cp LeetCode/Arrays-Hashing/0001-two-sum Solution

# Exemplo: Add Two Numbers
javac LeetCode/Linked-List/0002-add-two-numbers/Solution.java
java -cp LeetCode/Linked-List/0002-add-two-numbers Solution
```

---

## 📝 Licença

Este projeto está sob a licença [MIT](./LICENSE).
