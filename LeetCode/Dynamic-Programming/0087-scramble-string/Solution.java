import java.util.HashMap;
import java.util.Map;

public class Solution {
    
    // Método principal: DP 3D (Bottom-up)
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        if (s1.equals(s2)) return true;
        
        int n = s1.length();
        // dp[i][j][len] = s1[i..i+len-1] é scramble de s2[j..j+len-1]
        boolean[][][] dp = new boolean[n][n][n + 1];
        
        // Base case: len = 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = (s1.charAt(i) == s2.charAt(j));
            }
        }
        
        // Build up for len = 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                for (int j = 0; j + len <= n; j++) {
                    // Try all possible split points
                    for (int k = 1; k < len; k++) {
                        // No swap: s1[i..i+k-1] matches s2[j..j+k-1] AND s1[i+k..i+len-1] matches s2[j+k..j+len-1]
                        if (dp[i][j][k] && dp[i + k][j + k][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                        // Swap: s1[i..i+k-1] matches s2[j+len-k..j+len-1] AND s1[i+k..i+len-1] matches s2[j..j+len-k-1]
                        if (dp[i][j + len - k][k] && dp[i + k][j][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return dp[0][0][n];
    }
    
    // Método 2: Recursion com Memoização (Top-down)
    public boolean isScrambleMemo(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        if (s1.equals(s2)) return true;
        
        Map<String, Boolean> memo = new HashMap<>();
        return scrambleMemo(s1, 0, s2, 0, s1.length(), memo);
    }
    
    private boolean scrambleMemo(String s1, int i1, String s2, int i2, int len, Map<String, Boolean> memo) {
        String key = i1 + "," + i2 + "," + len;
        if (memo.containsKey(key)) return memo.get(key);
        
        // Check if substrings are equal
        if (s1.substring(i1, i1 + len).equals(s2.substring(i2, i2 + len))) {
            memo.put(key, true);
            return true;
        }
        
        // Check character frequency (pruning)
        if (!sameChars(s1, i1, s2, i2, len)) {
            memo.put(key, false);
            return false;
        }
        
        // Try all splits
        for (int k = 1; k < len; k++) {
            // No swap
            if (scrambleMemo(s1, i1, s2, i2, k, memo) && 
                scrambleMemo(s1, i1 + k, s2, i2 + k, len - k, memo)) {
                memo.put(key, true);
                return true;
            }
            // Swap
            if (scrambleMemo(s1, i1, s2, i2 + len - k, k, memo) && 
                scrambleMemo(s1, i1 + k, s2, i2, len - k, memo)) {
                memo.put(key, true);
                return true;
            }
        }
        
        memo.put(key, false);
        return false;
    }
    
    // Método 3: Recursion pura (exponencial, apenas para verificação em casos pequenos)
    public boolean isScrambleRecursive(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        if (s1.equals(s2)) return true;
        if (!sameChars(s1, 0, s2, 0, s1.length())) return false;
        
        int n = s1.length();
        for (int k = 1; k < n; k++) {
            // No swap
            if (isScrambleRecursive(s1.substring(0, k), s2.substring(0, k)) &&
                isScrambleRecursive(s1.substring(k), s2.substring(k))) {
                return true;
            }
            // Swap
            if (isScrambleRecursive(s1.substring(0, k), s2.substring(n - k)) &&
                isScrambleRecursive(s1.substring(k), s2.substring(0, n - k))) {
                return true;
            }
        }
        return false;
    }
    
    // Helper: verifica se duas substrings têm os mesmos caracteres
    private boolean sameChars(String s1, int i1, String s2, int i2, int len) {
        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
            count[s1.charAt(i1 + i) - 'a']++;
            count[s2.charAt(i2 + i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Scramble String Tests ===\n");
        
        // Test 1: Example 1
        test(sol, "great", "rgeat", true, "Test 1: great -> rgeat");
        
        // Test 2: Example 2
        test(sol, "abcde", "caebd", false, "Test 2: abcde -> caebd");
        
        // Test 3: Example 3
        test(sol, "a", "a", true, "Test 3: a -> a");
        
        // Test 4: Same string
        test(sol, "abc", "abc", true, "Test 4: Same string");
        
        // Test 5: Single char different
        test(sol, "a", "b", false, "Test 5: a -> b");
        
        // Test 6: Length 2 swap
        test(sol, "ab", "ba", true, "Test 6: ab -> ba");
        
        // Test 7: Length 2 no scramble
        test(sol, "ab", "ab", true, "Test 7: ab -> ab");
        
        // Test 8: Length 3
        test(sol, "abc", "bca", true, "Test 8: abc -> bca");
        
        // Test 9: Length 3 no scramble
        test(sol, "abc", "acb", true, "Test 9: abc -> acb");
        
        // Test 10: Different chars
        test(sol, "abc", "def", false, "Test 10: Different chars");
        
        // Test 11: Longer scramble
        test(sol, "great", "rgtae", true, "Test 11: great -> rgtae");
        
        // Test 12: Not scramble
        test(sol, "abcd", "bdac", false, "Test 12: abcd -> bdac");
        
        // Test 13: All same chars
        test(sol, "aaaa", "aaaa", true, "Test 13: All same");
        
        // Test 14: Complex scramble
        test(sol, "abcdefghijklmnopq", "efghijklmnopqabcd", true, "Test 14: Rotation");
        
        // Test 15: Edge case - empty (not in constraints but good to check)
        // Constraints say length >= 1
    }
    
    private static void test(Solution sol, String s1, String s2, boolean expected, String name) {
        boolean result1 = sol.isScramble(s1, s2);
        boolean result2 = sol.isScrambleMemo(s1, s2);
        boolean result3 = (s1.length() <= 8) ? sol.isScrambleRecursive(s1, s2) : result1;
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3);
        
        System.out.println(name);
        System.out.println("  s1: " + s1 + ", s2: " + s2);
        System.out.println("  Expected: " + expected);
        System.out.println("  DP:       " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Memo:     " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Recursive:" + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}