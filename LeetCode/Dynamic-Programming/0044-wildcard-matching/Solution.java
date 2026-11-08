/**
 * LeetCode 0044: Wildcard Matching
 * Difficulty: Hard
 * Category: Dynamic Programming / Two Pointers
 * 
 * Approach: DP (O(m*n) time, O(m*n) space) and Two Pointers (O(m*n) time, O(1) space)
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n) for DP, O(1) for Two Pointers
 */
public class Solution {

    // DP Approach
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] = true if s[0..i-1] matches p[0..j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // Handle patterns starting with *
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    // * matches empty (dp[i][j-1]) or one more char (dp[i-1][j])
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    // ? matches any single char, or exact match
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        
        return dp[m][n];
    }

    // Two Pointers Approach (Greedy) - O(1) space
    public boolean isMatchTwoPointers(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        int sIdx = 0;
        int pIdx = 0;
        int starIdx = -1;
        int sTmpIdx = -1;
        
        while (sIdx < sLen) {
            // If current characters match, or pattern has '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                sIdx++;
                pIdx++;
            }
            // If pattern has '*', record position and move pattern pointer
            else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                starIdx = pIdx;
                sTmpIdx = sIdx;
                pIdx++;
            }
            // If there was a previous '*', backtrack: let * match one more character
            else if (starIdx != -1) {
                pIdx = starIdx + 1;
                sTmpIdx++;
                sIdx = sTmpIdx;
            }
            // No match and no previous '*'
            else {
                return false;
            }
        }
        
        // Check remaining characters in pattern - all must be '*'
        while (pIdx < pLen && p.charAt(pIdx) == '*') {
            pIdx++;
        }
        
        return pIdx == pLen;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        boolean result1 = solver.isMatch("aa", "a");
        System.out.println("Test 1: " + result1 + " (Expected: false)");

        // Test Case 2
        boolean result2 = solver.isMatch("aa", "*");
        System.out.println("Test 2: " + result2 + " (Expected: true)");

        // Test Case 3
        boolean result3 = solver.isMatch("cb", "?a");
        System.out.println("Test 3: " + result3 + " (Expected: false)");

        // Test Case 4
        boolean result4 = solver.isMatch("adceb", "*a*b");
        System.out.println("Test 4: " + result4 + " (Expected: true)");

        // Test Case 5
        boolean result5 = solver.isMatch("acdcb", "a*c?b");
        System.out.println("Test 5: " + result5 + " (Expected: false)");

        // Test Case 6: Empty string
        boolean result6 = solver.isMatch("", "*");
        System.out.println("Test 6: " + result6 + " (Expected: true)");

        // Test Case 7: Empty pattern
        boolean result7 = solver.isMatch("abc", "");
        System.out.println("Test 7: " + result7 + " (Expected: false)");

        // Test Case 8: Multiple stars
        boolean result8 = solver.isMatch("abc", "a***b***c");
        System.out.println("Test 8: " + result8 + " (Expected: true)");

        // Test Case 9: Star at end
        boolean result9 = solver.isMatch("abc", "ab*");
        System.out.println("Test 9: " + result9 + " (Expected: true)");

        // Test Case 10: Star at beginning
        boolean result10 = solver.isMatch("abc", "*bc");
        System.out.println("Test 10: " + result10 + " (Expected: true)");

        // Test Case 11: Only stars
        boolean result11 = solver.isMatch("abc", "***");
        System.out.println("Test 11: " + result11 + " (Expected: true)");

        // Test Case 12: Question marks
        boolean result12 = solver.isMatch("abc", "???");
        System.out.println("Test 12: " + result12 + " (Expected: true)");

        // Test Case 13: Mismatch length with ?
        boolean result13 = solver.isMatch("abc", "??");
        System.out.println("Test 13: " + result13 + " (Expected: false)");

        // Test Case 14: Complex
        boolean result14 = solver.isMatch("mississippi", "m*ss*ss*pp*");
        System.out.println("Test 14: " + result14 + " (Expected: true)");

        // Test Case 15: Complex no match
        boolean result15 = solver.isMatch("mississippi", "m*ss*ss*pp");
        System.out.println("Test 15: " + result15 + " (Expected: false)");

        // Verify both approaches match
        System.out.println("\n--- Verification (DP vs Two Pointers) ---");
        String[][] testCases = {
            {"aa", "a"},
            {"aa", "*"},
            {"cb", "?a"},
            {"adceb", "*a*b"},
            {"acdcb", "a*c?b"},
            {"", "*"},
            {"abc", ""},
            {"abc", "a***b***c"},
            {"abc", "ab*"},
            {"abc", "*bc"},
            {"abc", "***"},
            {"abc", "???"},
            {"abc", "??"},
            {"mississippi", "m*ss*ss*pp*"},
            {"mississippi", "m*ss*ss*pp"}
        };
        
        for (String[] test : testCases) {
            boolean dp = solver.isMatch(test[0], test[1]);
            boolean twoPtr = solver.isMatchTwoPointers(test[0], test[1]);
            System.out.println("s=\"" + test[0] + "\", p=\"" + test[1] + "\" -> DP: " + dp + ", TwoPtr: " + twoPtr + " (match: " + (dp == twoPtr) + ")");
        }
    }
}