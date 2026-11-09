/**
 * LeetCode 161 - One Edit Distance
 * 
 * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
 * 
 * A string s is said to be one edit distance apart from a string t if you can:
 * - Insert exactly one character into s to get t
 * - Delete exactly one character from s to get t
 * - Replace exactly one character of s to get t
 * 
 * Example 1:
 * Input: s = "ab", t = "acb"
 * Output: true
 * Explanation: We can insert 'c' into s to get t.
 * 
 * Example 2:
 * Input: s = "", t = ""
 * Output: false
 * Explanation: We cannot get t from s by only one edit.
 * 
 * Example 3:
 * Input: s = "a", t = ""
 * Output: true
 * Explanation: We can delete 'a' from s to get t.
 * 
 * Constraints:
 * 0 <= s.length, t.length <= 10^4
 * s and t consist of lower-case letters, upper-case letters, and digits.
 */
public class Solution {
    
    /**
     * Approach 1: Two Pointers - O(n) time, O(1) space
     * 
     * Compare characters from both ends. When mismatch found, check the three cases:
     * 1. Replace: skip both characters
     * 2. Insert: skip character in t (t is longer)
     * 3. Delete: skip character in s (s is longer)
     * 
     * Time Complexity: O(min(m, n))
     * Space Complexity: O(1)
     */
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // Ensure s is shorter or equal
        if (m > n) return isOneEditDistance(t, s);
        
        // Length difference > 1 means not one edit distance
        if (n - m > 1) return false;
        
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                // Found mismatch
                if (m == n) {
                    // Replace case: rest must match
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else {
                    // Insert case: s[i:] must match t[i+1:]
                    return s.substring(i).equals(t.substring(i + 1));
                }
            }
        }
        
        // All characters matched, check if t has exactly one extra character
        return n - m == 1;
    }
    
    /**
     * Approach 2: Two Pointers with Explicit Cases - O(n) time, O(1) space
     * 
     * More explicit handling of the three edit operations.
     * 
     * Time Complexity: O(min(m, n))
     * Space Complexity: O(1)
     */
    public boolean isOneEditDistanceExplicit(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        if (Math.abs(m - n) > 1) return false;
        
        int i = 0, j = 0;
        boolean foundDifference = false;
        
        while (i < m && j < n) {
            if (s.charAt(i) != t.charAt(j)) {
                if (foundDifference) return false;
                foundDifference = true;
                
                if (m == n) {
                    // Replace
                    i++;
                    j++;
                } else if (m < n) {
                    // Insert into s (or delete from t)
                    j++;
                } else {
                    // Delete from s (or insert into t)
                    i++;
                }
            } else {
                i++;
                j++;
            }
        }
        
        // Check remaining characters
        if (i < m || j < n) {
            if (foundDifference) return false;
            // Exactly one character remaining
            return (m - i) + (n - j) == 1;
        }
        
        return foundDifference;
    }
    
    /**
     * Approach 3: Dynamic Programming (for verification) - O(m*n) time, O(min(m,n)) space
     * 
     * Standard edit distance DP, but we only care if distance == 1.
     * 
     * Time Complexity: O(m * n)
     * Space Complexity: O(min(m, n))
     */
    public boolean isOneEditDistanceDP(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        if (Math.abs(m - n) > 1) return false;
        
        // Ensure s is shorter for space optimization
        if (m > n) return isOneEditDistanceDP(t, s);
        
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        
        for (int i = 0; i <= m; i++) {
            prev[i] = i;
        }
        
        for (int j = 1; j <= n; j++) {
            curr[0] = j;
            for (int i = 1; i <= m; i++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    curr[i] = prev[i - 1];
                } else {
                    curr[i] = 1 + Math.min(prev[i - 1], Math.min(prev[i], curr[i - 1]));
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev[m] == 1;
    }
    
    /**
     * Approach 4: Recursive with Memoization - O(m*n) time, O(m*n) space
     * 
     * Recursive approach with memoization for verification.
     * 
     * Time Complexity: O(m * n)
     * Space Complexity: O(m * n)
     */
    public boolean isOneEditDistanceRecursive(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        if (Math.abs(m - n) > 1) return false;
        
        Integer[][] memo = new Integer[m + 1][n + 1];
        return editDistance(s, t, 0, 0, memo) == 1;
    }
    
    private int editDistance(String s, String t, int i, int j, Integer[][] memo) {
        if (i == s.length()) return t.length() - j;
        if (j == t.length()) return s.length() - i;
        
        if (memo[i][j] != null) return memo[i][j];
        
        if (s.charAt(i) == t.charAt(j)) {
            memo[i][j] = editDistance(s, t, i + 1, j + 1, memo);
        } else {
            int replace = editDistance(s, t, i + 1, j + 1, memo);
            int insert = editDistance(s, t, i, j + 1, memo);
            int delete = editDistance(s, t, i + 1, j, memo);
            memo[i][j] = 1 + Math.min(replace, Math.min(insert, delete));
        }
        
        return memo[i][j];
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== One Edit Distance Tests ===\n");
        
        // Test 1: Example 1 - insert
        System.out.println("Test 1: s=\"ab\", t=\"acb\" -> Expected: true (insert 'c')");
        testCase(sol, "ab", "acb", true);
        
        // Test 2: Example 2 - empty strings
        System.out.println("\nTest 2: s=\"\", t=\"\" -> Expected: false");
        testCase(sol, "", "", false);
        
        // Test 3: Example 3 - delete
        System.out.println("\nTest 3: s=\"a\", t=\"\" -> Expected: true (delete 'a')");
        testCase(sol, "a", "", true);
        
        // Test 4: Replace
        System.out.println("\nTest 4: s=\"ab\", t=\"ac\" -> Expected: true (replace 'b' with 'c')");
        testCase(sol, "ab", "ac", true);
        
        // Test 5: Same strings
        System.out.println("\nTest 5: s=\"abc\", t=\"abc\" -> Expected: false");
        testCase(sol, "abc", "abc", false);
        
        // Test 6: Two edits needed
        System.out.println("\nTest 6: s=\"abc\", t=\"def\" -> Expected: false");
        testCase(sol, "abc", "def", false);
        
        // Test 7: Length diff > 1
        System.out.println("\nTest 7: s=\"a\", t=\"abc\" -> Expected: false");
        testCase(sol, "a", "abc", false);
        
        // Test 8: Insert at beginning
        System.out.println("\nTest 8: s=\"bc\", t=\"abc\" -> Expected: true (insert 'a')");
        testCase(sol, "bc", "abc", true);
        
        // Test 9: Insert at end
        System.out.println("\nTest 9: s=\"ab\", t=\"abc\" -> Expected: true (insert 'c')");
        testCase(sol, "ab", "abc", true);
        
        // Test 10: Delete from middle
        System.out.println("\nTest 10: s=\"abc\", t=\"ac\" -> Expected: true (delete 'b')");
        testCase(sol, "abc", "ac", true);
        
        // Test 11: Case sensitivity
        System.out.println("\nTest 11: s=\"a\", t=\"A\" -> Expected: true (replace)");
        testCase(sol, "a", "A", true);
        
        // Test 12: Digits
        System.out.println("\nTest 12: s=\"123\", t=\"124\" -> Expected: true");
        testCase(sol, "123", "124", true);
        
        // Test 13: Cross verification
        System.out.println("\nTest 13: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, String s, String t, boolean expected) {
        boolean r1 = sol.isOneEditDistance(s, t);
        boolean r2 = sol.isOneEditDistanceExplicit(s, t);
        boolean r3 = sol.isOneEditDistanceDP(s, t);
        boolean r4 = sol.isOneEditDistanceRecursive(s, t);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        System.out.println("  TwoPointers: " + r1 + (r1 == expected ? " ✓" : " ✗"));
        System.out.println("  Explicit:    " + r2 + (r2 == expected ? " ✓" : " ✗"));
        System.out.println("  DP:          " + r3 + (r3 == expected ? " ✓" : " ✗"));
        System.out.println("  Recursive:   " + r4 + (r4 == expected ? " ✓" : " ✗"));
        
        assert match : "Mismatch for s=" + s + ", t=" + t + ": expected " + expected + 
            ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static void crossVerify(Solution sol) {
        String[][] testCases = {
            {"ab", "acb"}, {"", ""}, {"a", ""}, {"ab", "ac"}, {"abc", "abc"},
            {"abc", "def"}, {"a", "abc"}, {"bc", "abc"}, {"ab", "abc"}, {"abc", "ac"},
                    {"a", "A"}, {"123", "124"}, {"teacher", "teachers"}, {"teacher", "teache"},
            {"teacher", "techer"}, {"teacher", "teachr"}, {"a", "b"}, {"ab", "ba"},
            {"abc", "abcd"}, {"abc", "ab"}, {"abc", "axc"}, {"abc", "axcy"}
        };
        boolean[] expected = {
            true, false, true, true, false,
            false, false, true, true, true,
            true, true, true, true, true, true,
            true, false, true, true, true, false
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];
            boolean exp = expected[i];
            
            boolean r1 = sol.isOneEditDistance(s, t);
            boolean r2 = sol.isOneEditDistanceExplicit(s, t);
            boolean r3 = sol.isOneEditDistanceDP(s, t);
            boolean r4 = sol.isOneEditDistanceRecursive(s, t);
            
            boolean match = (r1 == r2 && r2 == r3 && r3 == r4 && r1 == exp);
            System.out.println("  s=\"" + s + "\", t=\"" + t + "\": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + " exp:" + exp + ")");
            assert match : "Cross-verification failed for s=" + s + ", t=" + t;
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}