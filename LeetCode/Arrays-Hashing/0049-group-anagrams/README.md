# 0049 - Group Anagrams

## Descrição do Problema

Dado um array de strings `strs`, agrupe os **anagramas** juntos. Você pode retornar a resposta em qualquer ordem.

Um **Anagrama** é uma palavra ou frase formada reorganizando as letras de uma palavra ou frase diferente, tipicamente usando todas as letras originais exatamente uma vez.

## Exemplos

### Exemplo 1:
```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

### Exemplo 2:
```
Input: strs = [""]
Output: [[""]]
```

### Exemplo 3:
```
Input: strs = ["a"]
Output: [["a"]]
```

## Restrições

- `1 <= strs.length <= 10^4`
- `0 <= strs[i].length <= 100`
- `strs[i]` consiste em letras minúsculas inglesas.

## Abordagem

### Hash Map com Chave Ordenada

A ideia principal: **duas strings são anagramas se e somente se suas versões ordenadas são idênticas**.

1. Para cada string, ordenamos seus caracteres para criar uma **chave canônica**.
2. Usamos um `HashMap<String, List<String>>` onde a chave é a string ordenada.
3. Adicionamos a string original à lista correspondente à sua chave.
4. Retornamos todos os valores do mapa.

### Abordagem Alternativa: Contagem de Caracteres (O(n × k))

Em vez de ordenar (O(k log k)), podemos contar a frequência de cada letra (O(k)):
- Array `count[26]` para frequência de 'a' a 'z'
- Convertemos o array em string chave (ex: "#1#0#2#0...")

### Complexidade

- **Ordenação:** Tempo O(n × k log k), Espaço O(n × k)
- **Contagem:** Tempo O(n × k), Espaço O(n × k)

Onde n = número de strings, k = comprimento máximo da string.

## Implementação

A solução está em `Solution.java` com ambas as abordagens e testes abrangentes no método `main()`.

## Como Executar

```bash
javac Solution.java
java Solution
```