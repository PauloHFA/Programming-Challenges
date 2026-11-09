public class Solution {
    
    // Método principal: DP 2D Bottom-Up
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        
        if (m + n != s3.length()) return false;
        
        // dp[i][j] = s1[0..i-1] e s2[0..j-1] podem formar s3[0..i+j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // Primeira linha: só usa s2
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }
        
        // Primeira coluna: só usa s1
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        
        // Preenche o resto
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c3 = s3.charAt(i + j - 1);
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == c3) ||
                           (dp[i][j - 1] && s2.charAt(j - 1) == c3);
            }
        }
        
        return dp[m][n];
    }
    
    // Método 2: DP 1D (otimização de espaço)
    public boolean isInterleave1D(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        
        if (m + n != s3.length()) return false;
        
        // Garante que s1 é o menor para otimizar espaço
        if (m > n) {
            return isInterleave1D(s2, s1, s3);
        }
        
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        // Inicializa primeira linha
        for (int j = 1; j <= n; j++) {
            dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }
        
        for (int i = 1; i <= m; i++) {
            // Atualiza dp[0] para nova linha
            dp[0] = dp[0] && s1.charAt(i - 1) == s3.charAt(i - 1);
            
            for (int j = 1; j <= n; j++) {
                char c3 = s3.charAt(i + j - 1);
                dp[j] = (dp[j] && s1.charAt(i - 1) == c3) ||
                        (dp[j - 1] && s2.charAt(j - 1) == c3);
            }
        }
        
        return dp[n];
    }
    
    // Método 3: Recursão com Memoização
    public boolean isInterleaveMemo(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        
        if (m + n != s3.length()) return false;
        
        Integer[][] memo = new Integer[m + 1][n + 1];
        return interleaveMemo(s1, 0, s2, 0, s3, 0, memo);
    }
    
    private boolean interleaveMemo(String s1, int i, String s2, int j, String s3, int k, Integer[][] memo) {
        if (memo[i][j] != null) return memo[i][j] == 1;
        
        boolean result;
        if (i == s1.length() && j == s2.length()) {
            result = true;
        } else if (i == s1.length()) {
            result = s2.substring(j).equals(s3.substring(k));
        } else if (j == s2.length()) {
            result = s1.substring(i).equals(s3.substring(k));
        } else {
            result = (s1.charAt(i) == s3.charAt(k) && interleaveMemo(s1, i + 1, s2, j, s3, k + 1, memo)) ||
                     (s2.charAt(j) == s3.charAt(k) && interleaveMemo(s1, i, s2, j + 1, s3, k + 1, memo));
        }
        
        memo[i][j] = result ? 1 : 0;
        return result;
    }
    
    // Método 4: BFS/Queue (para verificação)
    public boolean isInterleaveBFS(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        
        if (m + n != s3.length()) return false;
        
        boolean[][] visited = new boolean[m + 1][n + 1];
        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0], j = curr[1];
            int k = i + j;
            
            if (i == m && j == n) return true;
            
            if (i < m && !visited[i + 1][j] && s1.charAt(i) == s3.charAt(k)) {
                visited[i + 1][j] = true;
                queue.offer(new int[]{i + 1, j});
            }
            
            if (j < n && !visited[i][j + 1] && s2.charAt(j) == s3.charAt(k)) {
                visited[i][j + 1] = true;
                queue.offer(new int[]{i, j + 1});
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Interleaving String Tests ===\n");
        
        // Test 1: Example 1
        test(sol, "aabcc", "dbbca", "aadbbcbcac", true, "Test 1: Example 1");
        
        // Test 2: Example 2
        test(sol, "aabcc", "dbbca", "aadbbbaccc", false, "Test 2: Example 2");
        
        // Test 3: Example 3
        test(sol, "", "", "", true, "Test 3: Empty strings");
        
        // Test 4: Simple cases
        test(sol, "a", "b", "ab", true, "Test 4: a + b = ab");
        test(sol, "a", "b", "ba", true, "Test 5: a + b = ba");
        test(sol, "a", "", "a", true, "Test 6: a + empty = a");
        test(sol, "", "a", "a", true, "Test 7: empty + a = a");
        
        // Test 5: No interleave possible
                test(sol, "abc", "def", "abdefc", true, "Test 8: Valid interleave (ab+def+c)");
        test(sol, "abc", "def", "abcdfe", false, "Test 9: Wrong order");
        
        // Test 6: All same chars
        test(sol, "aaa", "aaa", "aaaaaa", true, "Test 10: All same");
        test(sol, "aaa", "bbb", "aaabbb", true, "Test 11: aaa + bbb");
        
        // Test 7: Overlapping choices
        test(sol, "aab", "aac", "aaabac", true, "Test 12: Overlapping");
                test(sol, "aab", "aac", "aaaabc", true, "Test 13: Overlapping valid");
        
        // Test 8: Longer strings
        test(sol, "abcdefghij", "klmnopqrst", "aklbcmndoe pfqgrhsi tj", false, "Test 14: Longer");
        
        // Test 9: Edge case - single char mismatch
        test(sol, "a", "b", "c", false, "Test 15: Mismatch");
    }
    
    private static void test(Solution sol, String s1, String s2, String s3, boolean expected, String name) {
        boolean result1 = sol.isInterleave(s1, s2, s3);
        boolean result2 = sol.isInterleave1D(s1, s2, s3);
        boolean result3 = sol.isInterleaveMemo(s1, s2, s3);
        boolean result4 = sol.isInterleaveBFS(s1, s2, s3);
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  s1: '" + s1 + "', s2: '" + s2 + "', s3: '" + s3 + "'");
        System.out.println("  Expected: " + expected);
        System.out.println("  2D DP:    " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  1D DP:    " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Memo:     " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  BFS:      " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}