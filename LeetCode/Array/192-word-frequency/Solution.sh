#!/bin/bash

# LeetCode 192 - Word Frequency
# 
# Read from the file words.txt and output the word frequency list to stdout.
# 
# Example:
# words.txt:
# the day is sunny the the
# the sunny is is
# 
# Output:
# the 4
# is 3
# sunny 2
# day 1
# 
# Note:
# - You may assume the file contains only lowercase letters and space ' ' characters.
# - Each word is separated by a single space.
# - The output should be sorted by frequency from highest to lowest.
# - If two words have the same frequency, sort them alphabetically.

# Approach 1: Using tr, sort, uniq, sort (Standard)
# tr -s ' ' '\n' < words.txt | sort | uniq -c | sort -k1,1nr -k2,2 | awk '{print $2, $1}'

# Approach 2: Using awk (Single pass)
# awk '{for(i=1;i<=NF;i++) count[$i]++} END {for(w in count) print w, count[w]}' words.txt | sort -k2,2nr -k1,1

# Approach 3: Using Python (if available)
# python3 -c "
# import sys
# from collections import Counter
# words = sys.stdin.read().split()
# for word, count in Counter(words).most_common():
#     print(word, count)
# " < words.txt

# Approach 4: Using Perl
# perl -lane '$count{$_}++ for @F; END {print "$_ $count{$_}" for sort {$count{$b} <=> $count{$a} || $a cmp $b} keys %count}' words.txt

# Approach 5: Pure Bash (for educational purposes)
# declare -A count
# while read -r line; do
#     for word in $line; do
#         ((count[$word]++))
#     done
# done < words.txt
# for word in "${!count[@]}"; do
#     echo "$word ${count[$word]}"
# done | sort -k2,2nr -k1,1

# The standard solution (Approach 1) is the most portable and efficient:
tr -s ' ' '\n' < words.txt | sort | uniq -c | sort -k1,1nr -k2,2 | awk '{print $2, $1}'