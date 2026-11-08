/**
 * LeetCode 0029: Divide Two Integers
 * Difficulty: Medium
 * Category: Math / Bit Manipulation
 * 
 * Approach: Bit Manipulation (Exponential Search)
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int divide(int dividend, int divisor) {
        // Handle overflow case: Integer.MIN_VALUE / -1 = Integer.MAX_VALUE + 1 (overflow)
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Convert to long to handle Integer.MIN_VALUE
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);
        long result = 0;

        // Exponential search: subtract largest possible multiple of divisor
        while (a >= b) {
            long temp = b;
            long multiple = 1;

            // Double temp and multiple until temp > a
            while (a >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            a -= temp;
            result += multiple;
        }

        // Apply sign
        boolean negative = (dividend < 0) ^ (divisor < 0);
        return negative ? (int) -result : (int) result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int dividend1 = 10;
        int divisor1 = 3;
        int result1 = solver.divide(dividend1, divisor1);
        System.out.println("Test 1: " + result1 + " (Expected: 3)");

        // Test Case 2
        int dividend2 = 7;
        int divisor2 = -3;
        int result2 = solver.divide(dividend2, divisor2);
        System.out.println("Test 2: " + result2 + " (Expected: -2)");

        // Test Case 3: Overflow case
        int dividend3 = Integer.MIN_VALUE;
        int divisor3 = -1;
        int result3 = solver.divide(dividend3, divisor3);
        System.out.println("Test 3: " + result3 + " (Expected: " + Integer.MAX_VALUE + ")");

        // Test Case 4: Division by 1
        int dividend4 = 5;
        int divisor4 = 1;
        int result4 = solver.divide(dividend4, divisor4);
        System.out.println("Test 4: " + result4 + " (Expected: 5)");

        // Test Case 5: Division by -1
        int dividend5 = 5;
        int divisor5 = -1;
        int result5 = solver.divide(dividend5, divisor5);
        System.out.println("Test 5: " + result5 + " (Expected: -5)");

        // Test Case 6: Zero dividend
        int dividend6 = 0;
        int divisor6 = 5;
        int result6 = solver.divide(dividend6, divisor6);
        System.out.println("Test 6: " + result6 + " (Expected: 0)");

        // Test Case 7: Negative dividend, positive divisor
        int dividend7 = -10;
        int divisor7 = 3;
        int result7 = solver.divide(dividend7, divisor7);
        System.out.println("Test 7: " + result7 + " (Expected: -3)");

        // Test Case 8: Both negative
        int dividend8 = -10;
        int divisor8 = -3;
        int result8 = solver.divide(dividend8, divisor8);
        System.out.println("Test 8: " + result8 + " (Expected: 3)");

        // Test Case 9: Large numbers
        int dividend9 = 2147483647;
        int divisor9 = 2;
        int result9 = solver.divide(dividend9, divisor9);
        System.out.println("Test 9: " + result9 + " (Expected: 1073741823)");

        // Test Case 10: Divisor larger than dividend
        int dividend10 = 3;
        int divisor10 = 5;
        int result10 = solver.divide(dividend10, divisor10);
        System.out.println("Test 10: " + result10 + " (Expected: 0)");

        // Test Case 11: Integer.MIN_VALUE / 2
        int dividend11 = Integer.MIN_VALUE;
        int divisor11 = 2;
        int result11 = solver.divide(dividend11, divisor11);
        System.out.println("Test 11: " + result11 + " (Expected: -1073741824)");

        // Test Case 12: Integer.MIN_VALUE / -2
        int dividend12 = Integer.MIN_VALUE;
        int divisor12 = -2;
        int result12 = solver.divide(dividend12, divisor12);
        System.out.println("Test 12: " + result12 + " (Expected: 1073741824)");
    }
}