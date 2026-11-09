# LeetCode 195 - Tenth Line

## Problema

Given a text file `file.txt`, print just the 10th line of the file.

### Exemplo:
```
file.txt:
Line 1
Line 2
Line 3
Line 4
Line 5
Line 6
Line 7
Line 8
Line 9
Line 10

Output:
Line 10
```

### Notas:
1. If the file contains less than 10 lines, what should you output?
2. There's at least three different solutions. Try to explore all of them.

---

## Abordagens

### Abordagem 1: sed (Padrão)
```bash
sed -n '10p' file.txt
```
- `-n`: Suprime saída automática
- `10p`: Imprime apenas a linha 10

### Abordagem 2: awk
```bash
awk 'NR==10' file.txt
```
- `NR`: Número do registro (linha) atual

### Abordagem 3: head + tail
```bash
head -n 10 file.txt | tail -n 1
```
- `head -n 10`: Pega primeiras 10 linhas
- `tail -n 1`: Pega última linha das 10

### Abordagem 4: Pure Bash
```bash
i=0
while IFS= read -r line; do
    ((i++))
    if [ $i -eq 10 ]; then
        echo "$line"
        break
    fi
done < file.txt
```

### Abordagem 5: perl
```bash
perl -ne 'print if $. == 10' file.txt
```
- `$.`: Número da linha atual em perl

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| sed | O(n) | O(1) |
| awk | O(n) | O(1) |
| head + tail | O(n) | O(1) |
| Pure Bash | O(n) | O(1) |
| perl | O(n) | O(1) |

---

## Implementação

A solução está em `Solution.sh` com 5 abordagens diferentes comentadas. A abordagem padrão (1) com `sed -n '10p'` é a mais concisa e padrão.