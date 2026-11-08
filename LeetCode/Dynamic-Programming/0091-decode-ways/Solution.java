public class Solution {
    
    // Método principal: DP Bottom-Up O(1) espaço
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        
        int n = s.length();
        int prev2 = 1;  // dp[i-2]
        int prev1 = 1;  // dp[i-1]
        
        for (int i = 1; i <= n; i++) {
            int curr = 0;
            
            // Single digit
            if (s.charAt(i - 1) != '0') {
                curr += prev1;
            }
            
            // Two digits
            if (i >= 2) {
                int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
                if (twoDigit >= 10 && twoDigit <= 26) {
                    curr += prev2;
                }
            }
            
            prev2 = prev1;
            prev1 = curr;
            
            // Early exit if no valid decoding
            if (curr == 0) return 0;
        }
        
        return prev1;
    }
    
    // Método 2: DP Array (mais claro)
    public int numDecodingsDP(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            // Single digit
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }
            
            // Two digits
            int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
    
    // Método 3: Recursion com Memoização
    public int numDecodingsMemo(String s) {
        if (s == null || s.length() == 0) return 0;
        Integer[] memo = new Integer[s.length()];
        return decodeMemo(s, 0, memo);
    }
    
    private int decodeMemo(String s, int index, Integer[] memo) {
        if (index == s.length()) return 1;
        if (s.charAt(index) == '0') return 0;
        if (memo[index] != null) return memo[index];
        
        int ways = decodeMemo(s, index + 1, memo);
        
        if (index + 1 < s.length()) {
            int twoDigit = (s.charAt(index) - '0') * 10 + (s.charAt(index + 1) - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                ways += decodeMemo(s, index + 2, memo);
            }
        }
        
        memo[index] = ways;
        return ways;
    }
    
    // Método 4: Recursion pura (exponencial, apenas para verificação pequena)
    public int numDecodingsRecursive(String s) {
        if (s == null || s.length() == 0) return 0;
        return decodeRecursive(s, 0);
    }
    
    private int decodeRecursive(String s, int index) {
        if (index == s.length()) return 1;
        if (s.charAt(index) == '0') return 0;
        
        int ways = decodeRecursive(s, index + 1);
        
        if (index + 1 < s.length()) {
            int twoDigit = (s.charAt(index) - '0') * 10 + (s.charAt(index + 1) - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                ways += decodeRecursive(s, index + 2);
            }
        }
        
        return ways;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Decode Ways Tests ===\n");
        
        // Test 1: Example 1
        test(sol, "12", 2, "Test 1: '12' -> 'AB' (1,2) or 'L' (12)");
        
        // Test 2: Example 2
        test(sol, "226", 3, "Test 2: '226' -> 'BBF', 'BZ', 'VF'");
        
        // Test 3: Example 3
        test(sol, "06", 0, "Test 3: '06' -> invalid (leading zero)");
        
        // Test 4: Single digit
        test(sol, "1", 1, "Test 4: '1'");
        test(sol, "9", 1, "Test 5: '9'");
        
        // Test 5: Two digits valid
        test(sol, "10", 1, "Test 6: '10' -> 'J'");
        test(sol, "11", 2, "Test 7: '11' -> 'AA', 'K'");
        test(sol, "26", 2, "Test 8: '26' -> 'BF', 'Z'");
        test(sol, "27", 1, "Test 9: '27' -> 'BG' only");
        
        // Test 6: With zeros
        test(sol, "100", 0, "Test 10: '100' -> invalid");
        test(sol, "101", 1, "Test 11: '101' -> 'JA'");
        test(sol, "110", 1, "Test 12: '110' -> 'KJ'");
        
        // Test 7: Longer strings
        test(sol, "111", 3, "Test 13: '111' -> 'AAA', 'AK', 'KA'");
        test(sol, "1111", 5, "Test 14: '1111' (Fibonacci)");
        test(sol, "1234", 3, "Test 15: '1234'");
        
        // Test 8: All same
        test(sol, "1111111111", 89, "Test 16: Ten 1's (Fibonacci 11)");
        
        // Test 9: Edge cases
        test(sol, "0", 0, "Test 17: '0'");
        test(sol, "30", 0, "Test 18: '30' (invalid two-digit)");
        test(sol, "40", 0, "Test 19: '40' (invalid two-digit)");
        
        // Test 10: Complex
        test(sol, "2611055971756562", 4, "Test 20: Complex case");
    }
    
    private static void test(Solution sol, String s, int expected, String name) {
        int result1 = sol.numDecodings(s);
        int result2 = sol.numDecodingsDP(s);
        int result3 = sol.numDecodingsMemo(s);
        int result4 = (s.length() <= 15) ? sol.numDecodingsRecursive(s) : result1;
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  Input: '" + s + "'");
        System.out.println("  Expected: " + expected);
        System.out.println("  O(1) DP:  " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  DP Array: " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Memo:     " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Recursive:" + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}