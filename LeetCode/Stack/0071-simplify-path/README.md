# 0071 - Simplify Path

## Descrição

Dado um caminho absoluto para um arquivo ou diretório em um sistema de arquivos estilo Unix, converta-o para o caminho canônico simplificado.

Em um sistema de arquivos estilo Unix:
- Um ponto `.` refere-se ao diretório atual
- Dois pontos `..` referem-se ao diretório pai
- Múltiplas barras consecutivas `//` são tratadas como uma única barra `/`
- Qualquer outro formato de pontos (como `...`) é tratado como nome de arquivo/diretório

O caminho canônico deve ter o seguinte formato:
- Começa com uma única barra `/`
- Qualquer dois diretórios são separados por uma única barra `/`
- Não termina com barra `/` (exceto o diretório raiz)
- Contém apenas os diretórios no caminho do diretório raiz ao arquivo/diretório alvo (sem `.` ou `..`)

## Exemplos

**Exemplo 1:**
```
Input: path = "/home/"
Output: "/home"
Explanation: Não há barra final no caminho canônico.
```

**Exemplo 2:**
```
Input: path = "/../"
Output: "/"
Explanation: Ir um nível acima do diretório raiz é um no-op.
```

**Exemplo 3:**
```
Input: path = "/home//foo/"
Output: "/home/foo"
Explanation: Múltiplas barras são substituídas por uma única.
```

**Exemplo 4:**
```
Input: path = "/a/./b/../../c/"
Output: "/c"
```

## Restrições

- `1 <= path.length <= 3000`
- `path` consiste de letras inglesas, dígitos, `.`, `/` ou `_`
- `path` é um caminho absoluto Unix válido

## Abordagem

### Método 1: Stack/Deque com Split (Principal)
- Divide o caminho por `/`
- Processa cada parte:
  - Ignora vazios e `.`
  - `..` remove o último diretório da pilha (se não vazia)
  - Outros: adiciona à pilha
- Reconstrói o caminho
- **Tempo:** O(n), **Espaço:** O(n)

### Método 2: Stack Class
- Mesmo algoritmo usando `java.util.Stack`
- **Tempo:** O(n), **Espaço:** O(n)

### Método 3: Parsing Manual (Sem Split)
- Percorre caractere por caractere
- Constrói cada parte manualmente
- **Tempo:** O(n), **Espaço:** O(n)

## Complexidade

| Método | Tempo | Espaço |
|--------|-------|--------|
| Deque + Split | O(n) | O(n) |
| Stack | O(n) | O(n) |
| Manual | O(n) | O(n) |

## Implementação

A solução principal usa **Deque com split**.

```java
public String simplifyPath(String path) {
    Deque<String> stack = new ArrayDeque<>();
    String[] parts = path.split("/");
    
    for (String part : parts) {
        if (part.isEmpty() || part.equals(".")) {
            continue;
        } else if (part.equals("..")) {
            if (!stack.isEmpty()) {
                stack.pop();
            }
        } else {
            stack.push(part);
        }
    }
    
    if (stack.isEmpty()) {
        return "/";
    }
    
    StringBuilder result = new StringBuilder();
    while (!stack.isEmpty()) {
        result.insert(0, "/" + stack.pop());
    }
    
    return result.toString();
}
```

## Casos de Teste

- `"/home/"` → `"/home"`
- `"/../"` → `"/"`
- `"/home//foo/"` → `"/home/foo"`
- `"/a/./b/../../c/"` → `"/c"`
- `"/a/../../b/../c//.//"` → `"/c"`
- `"/a//b////c/d//././/.."` → `"/a/b/c"`
- `"/..."` → `"/..."` (três pontos é nome válido)
- `"/..hidden"` → `"/..hidden"` (nome de arquivo)
- `"/"` → `"/"`
- `"/."` → `"/"`
- `"/.."` → `"/"`
- `"/home/user/Documents/../Pictures"` → `"/home/user/Pictures"`