/**
 * LeetCode 168 - Excel Sheet Column Title
 * 
 * Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
 * 
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * ...
 * 
 * Example 1:
 * Input: columnNumber = 1
 * Output: "A"
 * 
 * Example 2:
 * Input: columnNumber = 28
 * Output: "AB"
 * 
 * Example 3:
 * Input: columnNumber = 701
 * Output: "ZY"
 * 
 * Constraints:
 * 1 <= columnNumber <= 2^31 - 1
 */
public class Solution {
    
    /**
     * Approach 1: Base-26 Conversion - O(log n) time, O(log n) space
     * 
     * Excel column titles are essentially base-26 but with a twist:
     * There's no '0' digit. A=1, B=2, ..., Z=26.
     * So we need to adjust by subtracting 1 before each division.
     * 
     * Time Complexity: O(log_26 n)
     * Space Complexity: O(log_26 n) for result string
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        
        while (columnNumber > 0) {
            columnNumber--; // Adjust for 1-indexed
            char c = (char)('A' + (columnNumber % 26));
            result.append(c);
            columnNumber /= 26;
        }
        
        return result.reverse().toString();
    }
    
    /**
     * Approach 2: Recursive - O(log n) time, O(log n) space
     * 
     * Recursive version of the base-26 conversion.
     * 
     * Time Complexity: O(log_26 n)
     * Space Complexity: O(log_26 n) recursion stack
     */
    public String convertToTitleRecursive(int columnNumber) {
        if (columnNumber <= 0) return "";
        columnNumber--;
        return convertToTitleRecursive(columnNumber / 26) + (char)('A' + (columnNumber % 26));
    }
    
    /**
     * Approach 3: Iterative with StringBuilder (pre-allocated) - O(log n) time, O(log n) space
     * 
     * Pre-calculate length for efficiency.
     * 
     * Time Complexity: O(log_26 n)
     * Space Complexity: O(log_26 n)
     */
    public String convertToTitleOptimized(int columnNumber) {
        // Calculate length first
        int len = 0;
        int temp = columnNumber;
        while (temp > 0) {
            temp = (temp - 1) / 26;
            len++;
        }
        
        char[] chars = new char[len];
        int idx = len - 1;
        
        while (columnNumber > 0) {
            columnNumber--;
            chars[idx--] = (char)('A' + (columnNumber % 26));
            columnNumber /= 26;
        }
        
        return new String(chars);
    }
    
    /**
     * Approach 4: Using Math and Character Array - O(log n) time, O(log n) space
     * 
     * Alternative implementation using character array directly.
     * 
     * Time Complexity: O(log_26 n)
     * Space Complexity: O(log_26 n)
     */
    public String convertToTitleArray(int columnNumber) {
        java.util.List<Character> chars = new java.util.ArrayList<>();
        
        while (columnNumber > 0) {
            columnNumber--;
            chars.add(0, (char)('A' + (columnNumber % 26)));
            columnNumber /= 26;
        }
        
        StringBuilder sb = new StringBuilder(chars.size());
        for (char c : chars) sb.append(c);
        return sb.toString();
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Excel Sheet Column Title Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: columnNumber=1 -> Expected: \"A\"");
        testCase(sol, 1, "A");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: columnNumber=28 -> Expected: \"AB\"");
        testCase(sol, 28, "AB");
        
        // Test 3: Example 3
        System.out.println("\nTest 3: columnNumber=701 -> Expected: \"ZY\"");
        testCase(sol, 701, "ZY");
        
        // Test 4: Single letters
        System.out.println("\nTest 4: columnNumber=26 -> Expected: \"Z\"");
        testCase(sol, 26, "Z");
        
        // Test 5: 27 -> AA
        System.out.println("\nTest 5: columnNumber=27 -> Expected: \"AA\"");
        testCase(sol, 27, "AA");
        
        // Test 6: 52 -> AZ
        System.out.println("\nTest 6: columnNumber=52 -> Expected: \"AZ\"");
        testCase(sol, 52, "AZ");
        
        // Test 7: 53 -> BA
        System.out.println("\nTest 7: columnNumber=53 -> Expected: \"BA\"");
        testCase(sol, 53, "BA");
        
        // Test 8: 702 -> ZZ
        System.out.println("\nTest 8: columnNumber=702 -> Expected: \"ZZ\"");
        testCase(sol, 702, "ZZ");
        
        // Test 9: 703 -> AAA
        System.out.println("\nTest 9: columnNumber=703 -> Expected: \"AAA\"");
        testCase(sol, 703, "AAA");
        
        // Test 10: Large number
        System.out.println("\nTest 10: columnNumber=18278 -> Expected: \"ZZZ\"");
        testCase(sol, 18278, "ZZZ");
        
        // Test 11: Max int
        System.out.println("\nTest 11: columnNumber=2147483647 -> Expected: \"FXSHRXW\"");
        testCase(sol, 2147483647, "FXSHRXW");
        
        // Test 12: Cross verification
        System.out.println("\nTest 12: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int num, String expected) {
        String r1 = sol.convertToTitle(num);
        String r2 = sol.convertToTitleRecursive(num);
        String r3 = sol.convertToTitleOptimized(num);
        String r4 = sol.convertToTitleArray(num);
        
        boolean match = expected.equals(r1) && expected.equals(r2) && expected.equals(r3) && expected.equals(r4);
        
        System.out.println("  Iterative:   " + r1 + (expected.equals(r1) ? " ✓" : " ✗"));
        System.out.println("  Recursive:   " + r2 + (expected.equals(r2) ? " ✓" : " ✗"));
        System.out.println("  Optimized:   " + r3 + (expected.equals(r3) ? " ✓" : " ✗"));
        System.out.println("  Array:       " + r4 + (expected.equals(r4) ? " ✓" : " ✗"));
        
        assert match : "Mismatch for " + num + ": expected " + expected + 
            ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static void crossVerify(Solution sol) {
        int[] testCases = {
            1, 2, 3, 26, 27, 28, 52, 53, 701, 702, 703, 704,
            18278, 18279, 100, 1000, 10000, 100000, 1000000,
            2147483647
        };
        
        for (int num : testCases) {
            String r1 = sol.convertToTitle(num);
            String r2 = sol.convertToTitleRecursive(num);
            String r3 = sol.convertToTitleOptimized(num);
            String r4 = sol.convertToTitleArray(num);
            
            boolean match = r1.equals(r2) && r2.equals(r3) && r3.equals(r4);
            System.out.println("  " + num + ": " + (match ? "MATCH" : "MISMATCH") + " (" + r1 + ")");
            assert match : "Cross-verification failed for " + num;
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}