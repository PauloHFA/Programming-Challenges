# 0058 - Length of Last Word

## Descrição do Problema

Dada uma string `s` consistindo de palavras e espaços, retorne o comprimento da **última palavra** na string.

Uma **palavra** é uma substring máxima consistindo apenas de caracteres não-espaço.

## Exemplos

### Exemplo 1:
```
Input: s = "Hello World"
Output: 5
Explanation: A última palavra é "World" com comprimento 5.
```

### Exemplo 2:
```
Input: s = "   fly me   to   the moon  "
Output: 4
Explanation: A última palavra é "moon" com comprimento 4.
```

### Exemplo 3:
```
Input: s = "luffy is still joyboy"
Output: 6
Explanation: A última palavra é "joyboy" com comprimento 6.
```

## Restrições

- `1 <= s.length <= 10^4`
- `s` consiste apenas de letras inglesas e espaços `' '`.
- Haverá pelo menos uma palavra em `s`.

## Abordagem

### Percorrer do Final (O(n) tempo, O(1) espaço)

A abordagem mais eficiente:

1. **Pular espaços finais**: Começar do final da string e mover para trás enquanto for espaço
2. **Contar última palavra**: Continuar movendo para trás enquanto não for espaço, contando caracteres

**Algoritmo:**
```java
int length = 0;
int i = s.length() - 1;

// Skip trailing spaces
while (i >= 0 && s.charAt(i) == ' ') {
    i--;
}

// Count last word
while (i >= 0 && s.charAt(i) != ' ') {
    length++;
    i--;
}

return length;
```

### Abordagens Alternativas

1. **Trim + Split**: `s.trim().split("\\s+")` - mais simples mas cria array
2. **Índices explícitos**: Encontrar `end` (último não-espaço) e `start` (primeiro espaço antes de `end`)

### Complexidade

- **Tempo:** O(n) - única passagem do final
- **Espaço:** O(1) - apenas variáveis

## Implementação

A solução está em `Solution.java` com três abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```