$impl = Get-ChildItem LeetCode -Recurse -Filter "Solution.java" | ForEach-Object { [int]($_.FullName -replace '.*(\d{4}).*', '$1') } | Sort-Object
$max = $impl[-1]
$missing = 1..$max | Where-Object { $_ -notin $impl }
"Total implemented: " + $impl.Count
"Max problem: " + $max
"Missing in 1-" + $max + ": " + $missing.Count
$missing -join ", "