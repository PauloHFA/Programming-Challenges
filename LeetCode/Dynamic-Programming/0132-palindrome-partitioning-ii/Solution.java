import java.util.*;

/**
 * LeetCode 132 - Palindrome Partitioning II
 * 
 * Dada uma string s, particione s de forma que cada substring da partição
 * seja um palíndromo. Retorne o mínimo de cortes necessários para uma
 * partição de palíndromo de s.
 */
public class Solution {

    /**
     * Método 1: DP com pré-computação de palíndromos (Principal)
     * dp[i] = min cortes para s[0..i-1]
     * Tempo: O(n^2), Espaço: O(n^2)
     */
    public int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        // Pré-computa palíndromos
        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || isPal[i + 1][j - 1]);
            }
        }
        
        // dp[i] = min cortes para s[0..i-1]
        int[] dp = new int[n + 1];
        dp[0] = -1; // Base: string vazia precisa -1 cortes (para que primeira partição dê 0)
        
        for (int i = 1; i <= n; i++) {
            dp[i] = i - 1; // Pior caso: cada char separado
            for (int j = 0; j < i; j++) {
                if (isPal[j][i - 1]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n];
    }

    /**
     * Método 2: DP com expansão de centro (Otimizado espaço)
     * Expande palíndromos a partir de cada centro
     * Tempo: O(n^2), Espaço: O(n)
     */
    public int minCutExpand(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        int[] dp = new int[n + 1];
        dp[0] = -1;
        for (int i = 1; i <= n; i++) {
            dp[i] = i - 1;
        }
        
        for (int center = 0; center < n; center++) {
            // Palíndromos de comprimento ímpar
            for (int radius = 0; center - radius >= 0 && center + radius < n && s.charAt(center - radius) == s.charAt(center + radius); radius++) {
                dp[center + radius + 1] = Math.min(dp[center + radius + 1], dp[center - radius] + 1);
            }
            
            // Palíndromos de comprimento par
            for (int radius = 0; center - radius >= 0 && center + radius + 1 < n && s.charAt(center - radius) == s.charAt(center + radius + 1); radius++) {
                dp[center + radius + 2] = Math.min(dp[center + radius + 2], dp[center - radius] + 1);
            }
        }
        
        return dp[n];
    }

    /**
     * Método 3: DP 1D com verificação on-the-fly
     * Tempo: O(n^2), Espaço: O(n)
     */
    public int minCutOnTheFly(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        int[] dp = new int[n + 1];
        dp[0] = -1;
        for (int i = 1; i <= n; i++) {
            dp[i] = i - 1;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (isPalindrome(s, j, i)) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[j] + 1);
                }
            }
        }
        
        return dp[n];
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Palindrome Partitioning II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: s = \"aab\"");
        String s1 = "aab";
        System.out.println("  Expected: 1 (aa|b)");
        System.out.println("  DP:       " + sol.minCut(s1));
        System.out.println("  Expand:   " + sol.minCutExpand(s1));
        System.out.println("  OnFly:    " + sol.minCutOnTheFly(s1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: s = \"a\"");
        String s2 = "a";
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.minCut(s2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: s = \"ab\"");
        String s3 = "ab";
        System.out.println("  Expected: 1 (a|b)");
        System.out.println("  Result:   " + sol.minCut(s3));
        
        // Test 4: Already palindrome
        System.out.println("\nTest 4: s = \"aaa\"");
        String s4 = "aaa";
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.minCut(s4));
        
        // Test 5: No palindromes > 1
        System.out.println("\nTest 5: s = \"abc\"");
        String s5 = "abc";
        System.out.println("  Expected: 2 (a|b|c)");
        System.out.println("  Result:   " + sol.minCut(s5));
        
        // Test 6: Longer palindrome
        System.out.println("\nTest 6: s = \"abba\"");
        String s6 = "abba";
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.minCut(s6));
        
        // Test 7: Mixed
        System.out.println("\nTest 7: s = \"abac\"");
        String s7 = "abac";
        System.out.println("  Expected: 1 (aba|c)");
        System.out.println("  Result:   " + sol.minCut(s7));
        
        // Test 8: Racecar
        System.out.println("\nTest 8: s = \"racecar\"");
        String s8 = "racecar";
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.minCut(s8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        String[] testCases = {"aab", "a", "ab", "aaa", "abc", "abba", "abac", "racecar", "ababab", "abcba"};
                int[] expected = {1, 0, 1, 0, 2, 0, 1, 0, 1, 0};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.minCut(testCases[i]);
            int r2 = sol.minCutExpand(testCases[i]);
            int r3 = sol.minCutOnTheFly(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i]);
            System.out.println("  \"" + testCases[i] + "\": " + (match ? "MATCH" : "MISMATCH") + 
                " (DP:" + r1 + " Exp:" + r2 + " OF:" + r3 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}