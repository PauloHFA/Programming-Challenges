#!/bin/bash

# LeetCode 195 - Tenth Line
# 
# Given a text file file.txt, print just the 10th line of the file.
# 
# Example:
# file.txt:
# Line 1
# Line 2
# Line 3
# Line 4
# Line 5
# Line 6
# Line 7
# Line 8
# Line 9
# Line 10
# 
# Output:
# Line 10
# 
# Note:
# 1. If the file contains less than 10 lines, what should you output?
# 2. There's at least three different solutions. Try to explore all of them.

# Approach 1: sed (Standard)
# sed -n '10p' file.txt

# Approach 2: awk
# awk 'NR==10' file.txt

# Approach 3: head + tail
# head -n 10 file.txt | tail -n 1

# Approach 4: Pure Bash
# i=0
# while IFS= read -r line; do
#     ((i++))
#     if [ $i -eq 10 ]; then
#         echo "$line"
#         break
#     fi
# done < file.txt

# Approach 5: perl
# perl -ne 'print if $. == 10' file.txt

# The standard solution (Approach 1):
sed -n '10p' file.txt