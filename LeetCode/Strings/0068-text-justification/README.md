# 0068 - Text Justification

## Descrição

Dado um array de strings `words` e uma largura `maxWidth`, formate o texto de forma que cada linha tenha exatamente `maxWidth` caracteres e seja totalmente justificada (alinhada à esquerda e à direita).

Você deve empacotar as palavras de forma gulosa; ou seja, coloque o máximo de palavras possível em cada linha. Preencha espaços extras `' '` quando necessário para que cada linha tenha exatamente `maxWidth` caracteres.

Espaços extras entre as palavras devem ser distribuídos o mais uniformemente possível. Se o número de espaços em uma linha não dividir uniformemente entre as palavras, os espaços vazios à esquerda receberão mais espaços do que os espaços à direita.

Para a última linha de texto, ela deve ser alinhada à esquerda e nenhum espaço extra é inserido entre as palavras.

## Exemplos

**Exemplo 1:**
```
Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
```

**Exemplo 2:**
```
Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
```

**Exemplo 3:**
```
Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]
```

## Restrições

- `1 <= words.length <= 300`
- `1 <= words[i].length <= 20`
- `words[i]` consiste apenas de letras e símbolos de pontuação em inglês.
- `1 <= maxWidth <= 100`
- `words[i].length <= maxWidth`

## Abordagem

### Algoritmo Guloso com Distribuição de Espaços

1. **Agrupar palavras por linha**: Percorre as palavras adicionando o máximo possível que caiba na largura máxima
2. **Calcular espaços necessários**: `maxWidth - soma dos comprimentos das palavras`
3. **Distribuir espaços**:
   - **Última linha ou linha com uma palavra**: Alinhamento à esquerda (espaços no final)
   - **Linhas normais**: Distribuição uniforme de espaços entre as palavras
     - `spacesPerGap = totalSpaces / (wordCount - 1)`
     - `extraSpaces = totalSpaces % (wordCount - 1)` (distribuídos da esquerda para direita)

### Complexidade

- **Tempo:** O(n * maxWidth) onde n é o número de palavras
- **Espaço:** O(n * maxWidth) para o resultado

## Implementação

```java
public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> result = new ArrayList<>();
    int n = words.length;
    int i = 0;
    
    while (i < n) {
        // Find words that fit in current line
        int lineLen = words[i].length();
        int j = i + 1;
        
        while (j < n && lineLen + 1 + words[j].length() <= maxWidth) {
            lineLen += 1 + words[j].length();
            j++;
        }
        
        // Build the line
        int wordCount = j - i;
        int totalWordLen = 0;
        for (int k = i; k < j; k++) {
            totalWordLen += words[k].length();
        }
        
        StringBuilder line = new StringBuilder();
        
        // Last line or single word: left justified
        if (j == n || wordCount == 1) {
            for (int k = i; k < j; k++) {
                line.append(words[k]);
                if (k < j - 1) line.append(" ");
            }
            while (line.length() < maxWidth) {
                line.append(" ");
            }
        } else {
            // Fully justified
            int totalSpaces = maxWidth - totalWordLen;
            int spacesPerGap = totalSpaces / (wordCount - 1);
            int extraSpaces = totalSpaces % (wordCount - 1);
            
            for (int k = i; k < j; k++) {
                line.append(words[k]);
                if (k < j - 1) {
                    int spaces = spacesPerGap + (k - i < extraSpaces ? 1 : 0);
                    for (int s = 0; s < spaces; s++) {
                        line.append(" ");
                    }
                }
            }
        }
        
        result.add(line.toString());
        i = j;
    }
    
    return result;
}
```

## Casos de Teste

- Exemplo 1 do LeetCode
- Exemplo 2 do LeetCode  
- Exemplo 3 do LeetCode
- Verificação de que ambos os métodos produzem resultados idênticos