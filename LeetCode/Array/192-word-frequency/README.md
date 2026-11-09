# LeetCode 192 - Word Frequency

## Problema

Read from the file `words.txt` and output the word frequency list to stdout.

### Exemplo:
```
words.txt:
the day is sunny the the
the sunny is is

Output:
the 4
is 3
sunny 2
day 1
```

### Notas:
- You may assume the file contains only lowercase letters and space ' ' characters.
- Each word is separated by a single space.
- The output should be sorted by frequency from highest to lowest.
- If two words have the same frequency, sort them alphabetically.

---

## Abordagens

### Abordagem 1: tr + sort + uniq + sort (Padrão)
```bash
tr -s ' ' '\n' < words.txt | sort | uniq -c | sort -k1,1nr -k2,2 | awk '{print $2, $1}'
```
- `tr -s ' ' '\n'`: Substitui espaços por novas linhas (squeeze múltiplos espaços)
- `sort`: Ordena palavras alfabeticamente
- `uniq -c`: Conta ocorrências consecutivas
- `sort -k1,1nr -k2,2`: Ordena por contagem (numérico, reverso) depois por palavra
- `awk '{print $2, $1}'`: Formata saída (palavra contagem)

### Abordagem 2: awk (Single Pass)
```bash
awk '{for(i=1;i<=NF;i++) count[$i]++} END {for(w in count) print w, count[w]}' words.txt | sort -k2,2nr -k1,1
```

### Abordagem 3: Python
```bash
python3 -c "
import sys
from collections import Counter
words = sys.stdin.read().split()
for word, count in Counter(words).most_common():
    print(word, count)
" < words.txt
```

### Abordagem 4: Perl
```bash
perl -lane '$count{$_}++ for @F; END {print "$_ $count{$_}" for sort {$count{$b} <=> $count{$a} || $a cmp $b} keys %count}' words.txt
```

### Abordagem 5: Pure Bash
```bash
declare -A count
while read -r line; do
    for word in $line; do
        ((count[$word]++))
    done
done < words.txt
for word in "${!count[@]}"; do
    echo "$word ${count[$word]}"
done | sort -k2,2nr -k1,1
```

---

## Complexidade

| Abordagem | Tempo | Espaço |
|-----------|-------|--------|
| tr/sort/uniq | O(n log n) | O(n) |
| awk | O(n log n) | O(n) |
| Python | O(n log n) | O(n) |
| Perl | O(n log n) | O(n) |
| Pure Bash | O(n log n) | O(n) |

---

## Implementação

A solução está em `Solution.sh` com 5 abordagens diferentes comentadas. A abordagem padrão (1) é a mais portável e eficiente.