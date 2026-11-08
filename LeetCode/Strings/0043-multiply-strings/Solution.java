/**
 * LeetCode 0043: Multiply Strings
 * Difficulty: Medium
 * Category: Strings / Math
 * 
 * Approach: Grade-school multiplication (digit by digit)
 * Time Complexity: O(m * n) where m, n are lengths of num1, num2
 * Space Complexity: O(m + n) for result array
 */
public class Solution {

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int m = num1.length();
        int n = num2.length();
        int[] result = new int[m + n];

        // Multiply each digit from right to left
        for (int i = m - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0; j--) {
                int digit2 = num2.charAt(j) - '0';
                int product = digit1 * digit2;
                
                // Position in result array
                int p1 = i + j;
                int p2 = i + j + 1;
                
                // Add to existing value at p2
                int sum = product + result[p2];
                
                result[p2] = sum % 10;
                result[p1] += sum / 10;
            }
        }

        // Convert result array to string, skipping leading zeros
        StringBuilder sb = new StringBuilder();
        for (int num : result) {
            if (!(sb.isEmpty() && num == 0)) {
                sb.append(num);
            }
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String result1 = solver.multiply("2", "3");
        System.out.println("Test 1: " + result1 + " (Expected: 6)");

        // Test Case 2
        String result2 = solver.multiply("123", "456");
        System.out.println("Test 2: " + result2 + " (Expected: 56088)");

        // Test Case 3: Zero
        String result3 = solver.multiply("0", "12345");
        System.out.println("Test 3: " + result3 + " (Expected: 0)");

        // Test Case 4: Zero
        String result4 = solver.multiply("12345", "0");
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Large numbers
        String result5 = solver.multiply("999", "999");
        System.out.println("Test 5: " + result5 + " (Expected: 998001)");

        // Test Case 6: Single digit
        String result6 = solver.multiply("9", "9");
        System.out.println("Test 6: " + result6 + " (Expected: 81)");

        // Test Case 7: Different lengths
        String result7 = solver.multiply("123456789", "987654321");
        System.out.println("Test 7: " + result7 + " (Expected: 121932631112635269)");

        // Test Case 8: Leading zeros in input (should not happen per constraints, but handle)
        String result8 = solver.multiply("0012", "003");
        System.out.println("Test 8: " + result8 + " (Expected: 36)");

        // Test Case 9: Very large
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb1.append('9');
            sb2.append('9');
        }
        String result9 = solver.multiply(sb1.toString(), sb2.toString());
        System.out.println("Test 9: Length = " + result9.length() + " (Expected: 200)");
        System.out.println("  First 20: " + result9.substring(0, 20));
        System.out.println("  Last 20: " + result9.substring(result9.length() - 20));
    }
}