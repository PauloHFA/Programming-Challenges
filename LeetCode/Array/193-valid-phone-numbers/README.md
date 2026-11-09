# LeetCode 193 - Valid Phone Numbers

## Problema

Given a text file `file.txt` that contains list of phone numbers (one per line), write a one-liner bash script to print all valid phone numbers.

You may assume that a valid phone number must appear in one of the following two formats:
- `(xxx) xxx-xxxx` 
- `xxx-xxx-xxxx` (x means a digit)

### Exemplo:
```
file.txt:
987-123-4567
123 456 7890
(123) 456-7890

Output:
987-123-4567
(123) 456-7890
```

---

## Abordagens

### Abordagem 1: grep com Extended Regex (Padrão)
```bash
grep -E '^(\([0-9]{3}\) [0-9]{3}-[0-9]{4}|[0-9]{3}-[0-9]{3}-[0-9]{4})$' file.txt
```
- `-E`: Usa extended regular expressions
- `^...$`: Match linha inteira
- `\([0-9]{3}\) [0-9]{3}-[0-9]{4}`: Formato (xxx) xxx-xxxx
- `[0-9]{3}-[0-9]{3}-[0-9]{4}`: Formato xxx-xxx-xxxx
- `|`: OR entre os dois formatos

### Abordagem 2: grep com Basic Regex (Portável)
```bash
grep '^([0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}\|([0-9]\{3\}) [0-9]\{3\}-[0-9]\{4\})$' file.txt
```

### Abordagem 3: awk
```bash
awk '/^(\([0-9]{3}\) [0-9]{3}-[0-9]{4}|[0-9]{3}-[0-9]{3}-[0-9]{4})$/' file.txt
```

### Abordagem 4: sed
```bash
sed -n '/^(\([0-9]\{3\}\) [0-9]\{3\}-[0-9]\{4\}\|[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\})$/p' file.txt
```

### Abordagem 5: perl
```bash
perl -ne 'print if /^(\(\d{3}\) \d{3}-\d{4}|\d{3}-\d{3}-\d{4})$/' file.txt
```

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| grep -E | O(n) | O(1) |
| grep basic | O(n) | O(1) |
| awk | O(n) | O(1) |
| sed | O(n) | O(1) |
| perl | O(n) | O(1) |

---

## Implementação

A solução está em `Solution.sh` com 5 abordagens diferentes comentadas. A abordagem padrão (1) com `grep -E` é a mais legível e padrão.