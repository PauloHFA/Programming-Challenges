$impl = Get-ChildItem LeetCode -Recurse -Filter "Solution.*" | ForEach-Object { [int]($_.FullName -replace '.*(\d{3,4}).*', '$1') } | Sort-Object -Unique
$max = $impl[-1]
$missing = 1..$max | Where-Object { $_ -notin $impl }
"Total implemented: " + $impl.Count
"Max problem: " + $max
"Missing in 1-" + $max + ": " + $missing.Count
$missing -join ", "