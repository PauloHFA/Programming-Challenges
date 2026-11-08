import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 0006: Zigzag Conversion
 * Difficulty: Medium
 * Category: Strings
 * 
 * Approach: Simulate Zigzag Pattern with Row Tracking
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class Solution {

    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s;
        }

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }

        int currentRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(currentRow).append(c);
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            currentRow += goingDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "PAYPALISHIRING";
        int numRows1 = 3;
        String result1 = solver.convert(s1, numRows1);
        System.out.println("Test 1: \"" + result1 + "\" (Expected: \"PAHNAPLSIIGYIR\")");

        // Test Case 2
        String s2 = "PAYPALISHIRING";
        int numRows2 = 4;
        String result2 = solver.convert(s2, numRows2);
        System.out.println("Test 2: \"" + result2 + "\" (Expected: \"PINALSIGYAHRPI\")");

        // Test Case 3: Single row
        String s3 = "A";
        int numRows3 = 1;
        String result3 = solver.convert(s3, numRows3);
        System.out.println("Test 3: \"" + result3 + "\" (Expected: \"A\")");

        // Test Case 4: Two rows
        String s4 = "ABC";
        int numRows4 = 2;
        String result4 = solver.convert(s4, numRows4);
        System.out.println("Test 4: \"" + result4 + "\" (Expected: \"ACB\")");

        // Test Case 5: More rows than string length
        String s5 = "AB";
        int numRows5 = 3;
        String result5 = solver.convert(s5, numRows5);
        System.out.println("Test 5: \"" + result5 + "\" (Expected: \"AB\")");

        // Test Case 6: Empty string
        String s6 = "";
        int numRows6 = 3;
        String result6 = solver.convert(s6, numRows6);
        System.out.println("Test 6: \"" + result6 + "\" (Expected: \"\")");

        // Test Case 7: Zigzag with 4 rows
        String s7 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int numRows7 = 4;
        String result7 = solver.convert(s7, numRows7);
        System.out.println("Test 7: \"" + result7 + "\"");
    }
}