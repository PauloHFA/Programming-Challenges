# LeetCode 194 - Transpose File

## Problema

Given a text file `file.txt`, transpose its content.

You may assume that each row has the same number of columns and each field is separated by the ' ' character.

### Exemplo:
```
file.txt:
name age
alice 21
ryan 30

Output:
name alice ryan
age 21 30
```

---

## Abordagens

### Abordagem 1: awk (Padrão)
```bash
awk '{for(i=1;i<=NF;i++) a[NR,i]=$i} END {for(i=1;i<=NF;i++) {for(j=1;j<=NR;j++) printf "%s%s", a[j,i], (j==NR?ORS:OFS)}}' file.txt
```
- `NR`: Número do registro (linha) atual
- `NF`: Número de campos na linha atual
- `a[NR,i]`: Armazena campo i da linha NR
- `END`: Processa após ler todo arquivo
- `ORS`: Output Record Separator (newline)
- `OFS`: Output Field Separator (space)

### Abordagem 2: Python
```bash
python3 -c "
import sys
lines = [line.strip().split() for line in sys.stdin]
for row in zip(*lines):
    print(' '.join(row))
" < file.txt
```

### Abordagem 3: Pure Bash
```bash
mapfile -t lines < file.txt
declare -A matrix
max_col=0
for i in "${!lines[@]}"; do
    read -ra fields <<< "${lines[i]}"
    for j in "${!fields[@]}"; do
        matrix[$i,$j]=${fields[j]}
    done
    if [ ${#fields[@]} -gt $max_col ]; then max_col=${#fields[@]}; fi
done
for ((j=0; j<max_col; j++)); do
    for ((i=0; i<${#lines[@]}; i++)); do
        printf "%s " "${matrix[$i,$j]}"
    done
    echo
done
```

### Abordagem 4: rs (reshape) command
```bash
rs -c' ' -C' ' < file.txt
```
(Disponível em BSD/macOS, não padrão no Linux)

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| awk | O(n×m) | O(n×m) |
| Python | O(n×m) | O(n×m) |
| Pure Bash | O(n×m) | O(n×m) |
| rs | O(n×m) | O(n×m) |

---

## Implementação

A solução está em `Solution.sh` com 4 abordagens diferentes comentadas. A abordagem padrão (1) com `awk` é a mais portável e eficiente.