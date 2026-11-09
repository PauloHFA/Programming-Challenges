# 0125 - Valid Palindrome

## Descrição

Uma frase é um palíndromo se, após converter todas as letras maiúsculas em minúsculas e remover todos os caracteres não alfanuméricos, ela lê da mesma forma para frente e para trás. Caracteres alfanuméricos incluem letras e números.

Dada uma string `s`, retorne `true` se for um palíndromo, ou `false` caso contrário.

## Exemplos

**Exemplo 1:**
```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explicação: "amanaplanacanalpanama" é um palíndromo.
```

**Exemplo 2:**
```
Input: s = "race a car"
Output: false
Explicação: "raceacar" não é um palíndromo.
```

**Exemplo 3:**
```
Input: s = " "
Output: true
Explicação: String vazia após remover não alfanuméricos é palíndromo.
```

## Restrições

- `1 <= s.length <= 2 * 10^5`
- `s` consiste apenas de caracteres ASCII imprimíveis.

## Abordagem

### Método 1: Two Pointers (Principal)
- Dois ponteiros nas extremidades, movendo para o centro
- Pula caracteres não alfanuméricos
- Compara caracteres em minúsculas
- **Tempo:** O(n), **Espaço:** O(1)

### Método 2: StringBuilder + Reverse
- Filtra e normaliza a string
- Compara com seu reverso
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Regex + StringBuilder
- Usa regex `[^a-zA-Z0-9]` para remover não alfanuméricos
- **Tempo:** O(n), **Espaço:** O(n)

### Método 4: Two Pointers com Array
- Converte para array de chars primeiro
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Two Pointers | O(n) | O(1) |
| StringBuilder | O(n) | O(n) |
| Regex | O(n) | O(n) |
| Array | O(n) | O(n) |

## Implementação

A solução principal usa **Two Pointers**.

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }
        
        if (left < right) {
            char cl = Character.toLowerCase(s.charAt(left));
            char cr = Character.toLowerCase(s.charAt(right));
            if (cl != cr) {
                return false;
            }
            left++;
            right--;
        }
    }
    
    return true;
}
```

## Casos de Teste

- `"A man, a plan, a canal: Panama"` → `true`
- `"race a car"` → `false`
- `" "` → `true`
- `"a"` → `true`
- `"0P"` → `false`
- `",."` → `true`
- `"1a2"` → `false`
- `"Was it a car or a cat I saw"` → `true`
- `"No lemon, no melon"` → `true`
- `"Madam, I'm Adam"` → `true`