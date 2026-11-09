import java.util.*;

/**
 * LeetCode 115 - Distinct Subsequences
 * 
 * Dadas duas strings s e t, retorne o número de subsequências distintas de s que são iguais a t.
 * Uma subsequência de uma string é uma nova string formada a partir da string original
 * eliminando alguns (podem ser nenhum) caracteres sem alterar a ordem relativa dos caracteres restantes.
 */
public class Solution {

    /**
     * Método 1: DP 2D (Principal)
     * dp[i][j] = número de subsequências distintas de s[0..i-1] que igualam t[0..j-1]
     * Tempo: O(m*n), Espaço: O(m*n)
     */
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if (n == 0) return 1;
        if (m == 0) return 0;
        
        long[][] dp = new long[m + 1][n + 1];
        
        // String vazia t é subsequência de qualquer string (1 vez)
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        
        return (int) dp[m][n];
    }

    /**
     * Método 2: DP 1D Otimizado
     * Usa apenas uma linha, iterando j de trás para frente
     * Tempo: O(m*n), Espaço: O(n)
     */
    public int numDistinctOptimized(String s, String t) {
        int m = s.length(), n = t.length();
        if (n == 0) return 1;
        if (m == 0) return 0;
        
        long[] dp = new long[n + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        
        return (int) dp[n];
    }

    /**
     * Método 3: DP com Memoização (Top-down)
     * Tempo: O(m*n), Espaço: O(m*n) + O(m+n) stack
     */
    public int numDistinctMemo(String s, String t) {
        int m = s.length(), n = t.length();
        Integer[][] memo = new Integer[m][n];
        return dfs(s, t, 0, 0, memo);
    }

    private int dfs(String s, String t, int i, int j, Integer[][] memo) {
        if (j == t.length()) return 1;
        if (i == s.length()) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        int result = 0;
        if (s.charAt(i) == t.charAt(j)) {
            result += dfs(s, t, i + 1, j + 1, memo);
        }
        result += dfs(s, t, i + 1, j, memo);
        
        memo[i][j] = result;
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Distinct Subsequences Tests ===\n");
        
        // Test 1: Example 1
        String s1 = "rabbbit", t1 = "rabbit";
        System.out.println("Test 1: s=\"rabbbit\", t=\"rabbit\"");
        System.out.println("  Expected: 3");
        System.out.println("  DP 2D:    " + sol.numDistinct(s1, t1));
        System.out.println("  DP 1D:    " + sol.numDistinctOptimized(s1, t1));
        System.out.println("  Memo:     " + sol.numDistinctMemo(s1, t1));
        
        // Test 2: Example 2
        String s2 = "babgbag", t2 = "bag";
        System.out.println("\nTest 2: s=\"babgbag\", t=\"bag\"");
        System.out.println("  Expected: 5");
        System.out.println("  DP 2D:    " + sol.numDistinct(s2, t2));
        System.out.println("  DP 1D:    " + sol.numDistinctOptimized(s2, t2));
        System.out.println("  Memo:     " + sol.numDistinctMemo(s2, t2));
        
        // Test 3: Empty t
        System.out.println("\nTest 3: s=\"abc\", t=\"\"");
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.numDistinct("abc", ""));
        
        // Test 4: Empty s
        System.out.println("\nTest 4: s=\"\", t=\"a\"");
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.numDistinct("", "a"));
        
        // Test 5: Same strings
        System.out.println("\nTest 5: s=\"abc\", t=\"abc\"");
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.numDistinct("abc", "abc"));
        
        // Test 6: No match
        System.out.println("\nTest 6: s=\"abc\", t=\"def\"");
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.numDistinct("abc", "def"));
        
        // Test 7: All same chars
        System.out.println("\nTest 7: s=\"aaa\", t=\"aa\"");
        System.out.println("  Expected: 3 (C(3,2))");
        System.out.println("  Result:   " + sol.numDistinct("aaa", "aa"));
        
        // Test 8: Large case
        System.out.println("\nTest 8: s=\"aaaaaaaaaa\", t=\"aaaa\"");
        System.out.println("  Expected: 210 (C(10,4))");
        System.out.println("  Result:   " + sol.numDistinct("aaaaaaaaaa", "aaaa"));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        String[] testS = {"rabbbit", "babgbag", "aaa", "abcde", "aaaaa"};
        String[] testT = {"rabbit", "bag", "aa", "ace", "aaa"};
        int[] expected = {3, 5, 3, 1, 10};
        boolean allMatch = true;
        for (int i = 0; i < testS.length; i++) {
            int r1 = sol.numDistinct(testS[i], testT[i]);
            int r2 = sol.numDistinctOptimized(testS[i], testT[i]);
            int r3 = sol.numDistinctMemo(testS[i], testT[i]);
            if (r1 != expected[i] || r2 != expected[i] || r3 != expected[i]) {
                allMatch = false;
                System.out.println("  Mismatch: s=" + testS[i] + ", t=" + testT[i] + 
                    " -> 2D:" + r1 + " 1D:" + r2 + " Memo:" + r3 + " Exp:" + expected[i]);
            }
        }
        System.out.println("  All match: " + allMatch);
        
        // Test 10: Single char
        System.out.println("\nTest 10: s=\"a\", t=\"a\"");
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.numDistinct("a", "a"));
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}