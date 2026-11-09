/**
 * LeetCode 165 - Compare Version Numbers
 * 
 * Given two version numbers, version1 and version2, compare them.
 * 
 * Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits
 * and may contain leading zeros. Every revision contains at least one character. Revisions are 0-indexed
 * from left to right, with the leftmost revision being revision 0, the next revision being revision 1, and so on.
 * 
 * To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using
 * their integer value ignoring any leading zeros. This means that revisions 1 and 001 are considered equal.
 * If a version number does not specify a revision at an index, then treat the revision as 0.
 * 
 * Return the following:
 * - If version1 < version2, return -1.
 * - If version1 > version2, return 1.
 * - Otherwise, return 0.
 * 
 * Example 1:
 * Input: version1 = "1.01", version2 = "1.001"
 * Output: 0
 * Explanation: Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
 * 
 * Example 2:
 * Input: version1 = "1.0", version2 = "1.0.0"
 * Output: 0
 * Explanation: version1 does not specify revision 2, which means it is treated as "0".
 * 
 * Example 3:
 * Input: version1 = "0.1", version2 = "1.1"
 * Output: -1
 * Explanation: version1's revision 0 is "0", while version2's revision 0 is "1". 0 < 1, so version1 < version2.
 * 
 * Constraints:
 * 1 <= version1.length, version2.length <= 500
 * version1 and version2 only contain digits and '.'.
 * version1 and version2 are valid version numbers.
 * All the given revisions in version1 and version2 can be stored in a 32-bit integer.
 */
public class Solution {
    
    /**
     * Approach 1: Split and Compare - O(n) time, O(n) space
     * 
     * Split both versions by '.', parse each revision as integer, compare pairwise.
     * Handle different lengths by treating missing revisions as 0.
     * 
     * Time Complexity: O(n) where n is max length of version strings
     * Space Complexity: O(n) for split arrays
     */
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        
        int maxLen = Math.max(v1.length, v2.length);
        
        for (int i = 0; i < maxLen; i++) {
            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            
            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
        }
        
        return 0;
    }
    
    /**
     * Approach 2: Two Pointers (No Split) - O(n) time, O(1) space
     * 
     * Parse revisions on-the-fly using two pointers, avoiding split overhead.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int compareVersionTwoPointers(String version1, String version2) {
        int i = 0, j = 0;
        int n1 = version1.length(), n2 = version2.length();
        
        while (i < n1 || j < n2) {
            int num1 = 0;
            while (i < n1 && version1.charAt(i) != '.') {
                num1 = num1 * 10 + (version1.charAt(i) - '0');
                i++;
            }
            
            int num2 = 0;
            while (j < n2 && version2.charAt(j) != '.') {
                num2 = num2 * 10 + (version2.charAt(j) - '0');
                j++;
            }
            
            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
            
            i++; // skip '.'
            j++; // skip '.'
        }
        
        return 0;
    }
    
    /**
     * Approach 3: Using StringTokenizer - O(n) time, O(n) space
     * 
     * Use StringTokenizer for parsing (legacy but functional).
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int compareVersionTokenizer(String version1, String version2) {
        java.util.StringTokenizer st1 = new java.util.StringTokenizer(version1, ".");
        java.util.StringTokenizer st2 = new java.util.StringTokenizer(version2, ".");
        
        while (st1.hasMoreTokens() || st2.hasMoreTokens()) {
            int num1 = st1.hasMoreTokens() ? Integer.parseInt(st1.nextToken()) : 0;
            int num2 = st2.hasMoreTokens() ? Integer.parseInt(st2.nextToken()) : 0;
            
            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
        }
        
        return 0;
    }
    
    /**
     * Approach 4: Stream-based (Java 8+) - O(n) time, O(n) space
     * 
     * Using streams for functional style comparison.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int compareVersionStream(String version1, String version2) {
        java.util.List<Integer> v1 = java.util.Arrays.stream(version1.split("\\."))
            .map(Integer::parseInt)
            .collect(java.util.stream.Collectors.toList());
        
        java.util.List<Integer> v2 = java.util.Arrays.stream(version2.split("\\."))
            .map(Integer::parseInt)
            .collect(java.util.stream.Collectors.toList());
        
        int maxLen = Math.max(v1.size(), v2.size());
        
        for (int i = 0; i < maxLen; i++) {
            int num1 = i < v1.size() ? v1.get(i) : 0;
            int num2 = i < v2.size() ? v2.get(i) : 0;
            
            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
        }
        
        return 0;
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Compare Version Numbers Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: version1=\"1.01\", version2=\"1.001\" -> Expected: 0");
        testCase(sol, "1.01", "1.001", 0);
        
        // Test 2: Example 2
        System.out.println("\nTest 2: version1=\"1.0\", version2=\"1.0.0\" -> Expected: 0");
        testCase(sol, "1.0", "1.0.0", 0);
        
        // Test 3: Example 3
        System.out.println("\nTest 3: version1=\"0.1\", version2=\"1.1\" -> Expected: -1");
        testCase(sol, "0.1", "1.1", -1);
        
        // Test 4: version1 > version2
        System.out.println("\nTest 4: version1=\"1.0.1\", version2=\"1\" -> Expected: 1");
        testCase(sol, "1.0.1", "1", 1);
        
        // Test 5: version1 < version2
        System.out.println("\nTest 5: version1=\"1.0\", version2=\"1.0.1\" -> Expected: -1");
        testCase(sol, "1.0", "1.0.1", -1);
        
        // Test 6: Leading zeros (0001 == 1)
                System.out.println("\nTest 6: version1=\"1.0001\", version2=\"1.1\" -> Expected: 0 (0001 == 1)");
                testCase(sol, "1.0001", "1.1", 0);
        
        // Test 7: Multiple leading zeros
        System.out.println("\nTest 7: version1=\"001.002.003\", version2=\"1.2.3\" -> Expected: 0");
        testCase(sol, "001.002.003", "1.2.3", 0);
        
        // Test 8: Different lengths
        System.out.println("\nTest 8: version1=\"1.2.3.4.5\", version2=\"1.2.3\" -> Expected: 1");
        testCase(sol, "1.2.3.4.5", "1.2.3", 1);
        
        // Test 9: Large numbers
        System.out.println("\nTest 9: version1=\"2147483647\", version2=\"2147483646\" -> Expected: 1");
        testCase(sol, "2147483647", "2147483646", 1);
        
        // Test 10: Equal versions
        System.out.println("\nTest 10: version1=\"1.2.3\", version2=\"1.2.3\" -> Expected: 0");
        testCase(sol, "1.2.3", "1.2.3", 0);
        
        // Test 11: Single digit vs multi-digit
        System.out.println("\nTest 11: version1=\"1.10\", version2=\"1.2\" -> Expected: 1");
        testCase(sol, "1.10", "1.2", 1);
        
        // Test 12: Cross verification
        System.out.println("\nTest 12: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, String v1, String v2, int expected) {
        int r1 = sol.compareVersion(v1, v2);
        int r2 = sol.compareVersionTwoPointers(v1, v2);
        int r3 = sol.compareVersionTokenizer(v1, v2);
        int r4 = sol.compareVersionStream(v1, v2);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        
        System.out.println("  Split:       " + r1 + (r1 == expected ? " ✓" : " ✗"));
        System.out.println("  TwoPointers: " + r2 + (r2 == expected ? " ✓" : " ✗"));
        System.out.println("  Tokenizer:   " + r3 + (r3 == expected ? " ✓" : " ✗"));
        System.out.println("  Stream:      " + r4 + (r4 == expected ? " ✓" : " ✗"));
        
        assert match : "Mismatch for v1=" + v1 + ", v2=" + v2 + 
            ": expected " + expected + ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static void crossVerify(Solution sol) {
        String[][] testCases = {
            {"1.01", "1.001"},
            {"1.0", "1.0.0"},
            {"0.1", "1.1"},
            {"1.0.1", "1"},
            {"1.0", "1.0.1"},
            {"1.0001", "1.1"},
            {"001.002.003", "1.2.3"},
            {"1.2.3.4.5", "1.2.3"},
            {"2147483647", "2147483646"},
            {"1.2.3", "1.2.3"},
            {"1.10", "1.2"},
            {"1.0.0.0.0", "1"},
            {"0.0.0", "0"},
            {"1.0.0.0.1", "1"},
            {"1.2.3.4", "1.2.3.5"},
        };
        int[] expected = {0, 0, -1, 1, -1, 0, 0, 1, 1, 0, 1, 0, 0, 1, -1};
        
        for (int i = 0; i < testCases.length; i++) {
            String v1 = testCases[i][0];
            String v2 = testCases[i][1];
            int exp = expected[i];
            
            int r1 = sol.compareVersion(v1, v2);
            int r2 = sol.compareVersionTwoPointers(v1, v2);
            int r3 = sol.compareVersionTokenizer(v1, v2);
            int r4 = sol.compareVersionStream(v1, v2);
            
            boolean match = (r1 == r2 && r2 == r3 && r3 == r4 && r1 == exp);
            System.out.println("  v1=\"" + v1 + "\", v2=\"" + v2 + "\": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + " exp:" + exp + ")");
            assert match : "Cross-verification failed for v1=" + v1 + ", v2=" + v2;
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}