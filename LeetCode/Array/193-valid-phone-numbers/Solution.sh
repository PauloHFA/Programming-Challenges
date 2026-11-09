#!/bin/bash

# LeetCode 193 - Valid Phone Numbers
# 
# Given a text file file.txt that contains list of phone numbers (one per line), 
# write a one-liner bash script to print all valid phone numbers.
# 
# You may assume that a valid phone number must appear in one of the following two formats: 
# (xxx) xxx-xxxx or xxx-xxx-xxxx. (x means a digit)
# 
# Example:
# file.txt:
# 987-123-4567
# 123 456 7890
# (123) 456-7890
# 
# Output:
# 987-123-4567
# (123) 456-7890

# Approach 1: grep with extended regex (Standard)
# grep -E '^(\([0-9]{3}\) [0-9]{3}-[0-9]{4}|[0-9]{3}-[0-9]{3}-[0-9]{4})$' file.txt

# Approach 2: grep with basic regex (Portable)
# grep '^([0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}\|([0-9]\{3\}) [0-9]\{3\}-[0-9]\{4\})$' file.txt

# Approach 3: awk
# awk '/^(\([0-9]{3}\) [0-9]{3}-[0-9]{4}|[0-9]{3}-[0-9]{3}-[0-9]{4})$/' file.txt

# Approach 4: sed
# sed -n '/^(\([0-9]\{3\}\) [0-9]\{3\}-[0-9]\{4\}\|[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\})$/p' file.txt

# Approach 5: perl
# perl -ne 'print if /^(\(\d{3}\) \d{3}-\d{4}|\d{3}-\d{3}-\d{4})$/' file.txt

# The standard solution (Approach 1):
grep -E '^(\([0-9]{3}\) [0-9]{3}-[0-9]{4}|[0-9]{3}-[0-9]{3}-[0-9]{4})$' file.txt