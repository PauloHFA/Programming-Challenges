import java.util.*;

/**
 * LeetCode 171 - Excel Sheet Column Number
 * 
 * Given a string columnTitle that represents the column title as appears in an Excel sheet,
 * return its corresponding column number.
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
 * Constraints:
 * - 1 <= columnTitle.length <= 7
 * - columnTitle consists only of uppercase English letters.
 * - columnTitle is in the range ["A", "FXSHRXW"].
 */
public class Solution {

    // Approach 1: Iterative - Base 26 conversion
    public int titleToNumber(String columnTitle) {
        int result = 0;
        for (char c : columnTitle.toCharArray()) {
            result = result * 26 + (c - 'A' + 1);
        }
        return result;
    }

    // Approach 2: Recursive
    public int titleToNumberRecursive(String columnTitle) {
        if (columnTitle.isEmpty()) return 0;
        return titleToNumberRecursive(columnTitle.substring(0, columnTitle.length() - 1)) * 26 
               + (columnTitle.charAt(columnTitle.length() - 1) - 'A' + 1);
    }

    // Approach 3: Using Math.pow (less efficient but clear)
    public int titleToNumberPow(String columnTitle) {
        int result = 0;
        int n = columnTitle.length();
        for (int i = 0; i < n; i++) {
            int val = columnTitle.charAt(i) - 'A' + 1;
            result += val * Math.pow(26, n - i - 1);
        }
        return result;
    }

    // Approach 4: Reverse iteration
    public int titleToNumberReverse(String columnTitle) {
        int result = 0;
        int multiplier = 1;
        for (int i = columnTitle.length() - 1; i >= 0; i--) {
            result += (columnTitle.charAt(i) - 'A' + 1) * multiplier;
            multiplier *= 26;
        }
        return result;
    }

    // Approach 5: Using char array and explicit loop
    public int titleToNumberArray(String columnTitle) {
        char[] chars = columnTitle.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length; i++) {
            result = result * 26 + (chars[i] - 'A' + 1);
        }
        return result;
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        String[] testCases = {
            "A", "B", "Z", "AA", "AB", "AZ", "BA", "ZZ", "AAA", "FXSHRXW"
        };
        int[] expected = {
            1, 2, 26, 27, 28, 52, 53, 702, 703, 2147483647
        };

        System.out.println("=== Excel Sheet Column Number Tests ===\n");

        for (int t = 0; t < testCases.length; t++) {
            String input = testCases[t];
            int exp = expected[t];
            
            int r1 = sol.titleToNumber(input);
            int r2 = sol.titleToNumberRecursive(input);
            int r3 = sol.titleToNumberPow(input);
            int r4 = sol.titleToNumberReverse(input);
            int r5 = sol.titleToNumberArray(input);

            boolean pass = r1 == exp && r2 == exp && r3 == exp && r4 == exp && r5 == exp;
            
            System.out.printf("Test %d: \"%s\" -> Expected: %d, Got: %d %s%n", 
                t + 1, input, exp, r1, pass ? "✓" : "✗");
            
            if (!pass) {
                System.out.printf("  Recursive: %d, Pow: %d, Reverse: %d, Array: %d%n", r2, r3, r4, r5);
            }
        }

        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        boolean allMatch = true;
        for (String input : testCases) {
            int r1 = sol.titleToNumber(input);
            int r2 = sol.titleToNumberRecursive(input);
            int r3 = sol.titleToNumberPow(input);
            int r4 = sol.titleToNumberReverse(input);
            int r5 = sol.titleToNumberArray(input);
            
            if (r1 != r2 || r1 != r3 || r1 != r4 || r1 != r5) {
                System.out.printf("Mismatch for \"%s\": %d, %d, %d, %d, %d%n", input, r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");

        // Edge cases
        System.out.println("\n=== Edge Cases ===");
        System.out.println("A = " + sol.titleToNumber("A") + " (expected 1)");
        System.out.println("Z = " + sol.titleToNumber("Z") + " (expected 26)");
        System.out.println("AA = " + sol.titleToNumber("AA") + " (expected 27)");
        System.out.println("AZ = " + sol.titleToNumber("AZ") + " (expected 52)");
        System.out.println("BA = " + sol.titleToNumber("BA") + " (expected 53)");
        System.out.println("ZZ = " + sol.titleToNumber("ZZ") + " (expected 702)");
        System.out.println("AAA = " + sol.titleToNumber("AAA") + " (expected 703)");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}