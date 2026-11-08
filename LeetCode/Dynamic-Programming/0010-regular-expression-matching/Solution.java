/**
 * LeetCode 0010: Regular Expression Matching
 * Difficulty: Hard
 * Category: Dynamic Programming / Strings
 * 
 * Approach: Dynamic Programming (Bottom-Up)
 * Time Complexity: O(m * n) where m = s.length, n = p.length
 * Space Complexity: O(m * n)
 */
public class Solution {

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] = true if s[0..i-1] matches p[0..j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // Initialize first row: empty string s matches pattern p[0..j-1]
        // Only possible if pattern has '*' that can match zero characters
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*' && dp[0][j - 2]) {
                dp[0][j] = true;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                if (pc == '*') {
                    // '*' matches zero of the preceding element
                    dp[i][j] = dp[i][j - 2];

                    // '*' matches one or more of the preceding element
                    char precedingChar = p.charAt(j - 2);
                    if (precedingChar == sc || precedingChar == '.') {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pc == '.' || pc == sc) {
                    // Direct match or '.' matches any single character
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // else dp[i][j] remains false
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "aa";
        String p1 = "a";
        boolean result1 = solver.isMatch(s1, p1);
        System.out.println("Test 1: " + result1 + " (Expected: false)");

        // Test Case 2
        String s2 = "aa";
        String p2 = "a*";
        boolean result2 = solver.isMatch(s2, p2);
        System.out.println("Test 2: " + result2 + " (Expected: true)");

        // Test Case 3
        String s3 = "ab";
        String p3 = ".*";
        boolean result3 = solver.isMatch(s3, p3);
        System.out.println("Test 3: " + result3 + " (Expected: true)");

        // Test Case 4
        String s4 = "aab";
        String p4 = "c*a*b";
        boolean result4 = solver.isMatch(s4, p4);
        System.out.println("Test 4: " + result4 + " (Expected: true)");

        // Test Case 5
        String s5 = "mississippi";
        String p5 = "mis*is*p*.";
        boolean result5 = solver.isMatch(s5, p5);
        System.out.println("Test 5: " + result5 + " (Expected: false)");

        // Test Case 6: Empty string
        String s6 = "";
        String p6 = "a*";
        boolean result6 = solver.isMatch(s6, p6);
        System.out.println("Test 6: " + result6 + " (Expected: true)");

        // Test Case 7: Both empty
        String s7 = "";
        String p7 = "";
        boolean result7 = solver.isMatch(s7, p7);
        System.out.println("Test 7: " + result7 + " (Expected: true)");

        // Test Case 8: Pattern with only *
        String s8 = "a";
        String p8 = ".*";
        boolean result8 = solver.isMatch(s8, p8);
        System.out.println("Test 8: " + result8 + " (Expected: true)");

        // Test Case 9: Multiple *
        String s9 = "aaa";
        String p9 = "a*a";
        boolean result9 = solver.isMatch(s9, p9);
        System.out.println("Test 9: " + result9 + " (Expected: true)");

        // Test Case 10: Complex pattern
        String s10 = "ab";
        String p10 = ".*c";
        boolean result10 = solver.isMatch(s10, p10);
        System.out.println("Test 10: " + result10 + " (Expected: false)");

        // Test Case 11: Dot matching
        String s11 = "abc";
        String p11 = "a.c";
        boolean result11 = solver.isMatch(s11, p11);
        System.out.println("Test 11: " + result11 + " (Expected: true)");

        // Test Case 12: Zero occurrence with *
        String s12 = "a";
        String p12 = "ab*";
        boolean result12 = solver.isMatch(s12, p12);
        System.out.println("Test 12: " + result12 + " (Expected: true)");
    }
}