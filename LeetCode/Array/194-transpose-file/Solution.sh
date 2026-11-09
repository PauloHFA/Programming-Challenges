#!/bin/bash

# LeetCode 194 - Transpose File
# 
# Given a text file file.txt, transpose its content.
# 
# You may assume that each row has the same number of columns and each field is separated by the ' ' character.
# 
# Example:
# file.txt:
# name age
# alice 21
# ryan 30
# 
# Output:
# name alice ryan
# age 21 30

# Approach 1: awk (Standard)
# awk '{for(i=1;i<=NF;i++) a[NR,i]=$i} END {for(i=1;i<=NF;i++) {for(j=1;j<=NR;j++) printf "%s%s", a[j,i], (j==NR?ORS:OFS)}}' file.txt

# Approach 2: Using paste and cut (Alternative)
# Not straightforward for variable columns

# Approach 3: Python (if available)
# python3 -c "
# import sys
# lines = [line.strip().split() for line in sys.stdin]
# for row in zip(*lines):
#     print(' '.join(row))
# " < file.txt

# Approach 4: Pure Bash (Educational)
# mapfile -t lines < file.txt
# declare -A matrix
# max_col=0
# for i in "${!lines[@]}"; do
#     read -ra fields <<< "${lines[i]}"
#     for j in "${!fields[@]}"; do
#         matrix[$i,$j]=${fields[j]}
#     done
#     if [ ${#fields[@]} -gt $max_col ]; then max_col=${#fields[@]}; fi
# done
# for ((j=0; j<max_col; j++)); do
#     for ((i=0; i<${#lines[@]}; i++)); do
#         printf "%s " "${matrix[$i,$j]}"
#     done
#     echo
# done

# Approach 5: Using rs (reshape) command if available
# rs -c' ' -C' ' < file.txt

# The standard solution (Approach 1):
awk '{for(i=1;i<=NF;i++) a[NR,i]=$i} END {for(i=1;i<=NF;i++) {for(j=1;j<=NR;j++) printf "%s%s", a[j,i], (j==NR?ORS:OFS)}}' file.txt