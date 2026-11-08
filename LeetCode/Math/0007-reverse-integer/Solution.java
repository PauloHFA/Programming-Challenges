/**
 * LeetCode 0007: Reverse Integer
 * Difficulty: Medium
 * Category: Math
 * 
 * Approach: Pop and Push Digits with Overflow Check
 * Time Complexity: O(log(x)) - number of digits in x
 * Space Complexity: O(1)
 */
public class Solution {

    public int reverse(int x) {
        int reversed = 0;

        while (x != 0) {
            int pop = x % 10;
            x /= 10;

            // Check for overflow before actually multiplying
            if (reversed > Integer.MAX_VALUE / 10 || (reversed == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (reversed < Integer.MIN_VALUE / 10 || (reversed == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            reversed = reversed * 10 + pop;
        }

        return reversed;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int x1 = 123;
        int result1 = solver.reverse(x1);
        System.out.println("Test 1: " + result1 + " (Expected: 321)");

        // Test Case 2
        int x2 = -123;
        int result2 = solver.reverse(x2);
        System.out.println("Test 2: " + result2 + " (Expected: -321)");

        // Test Case 3
        int x3 = 120;
        int result3 = solver.reverse(x3);
        System.out.println("Test 3: " + result3 + " (Expected: 21)");

        // Test Case 4: Zero
        int x4 = 0;
        int result4 = solver.reverse(x4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Overflow positive
        int x5 = 1534236469;
        int result5 = solver.reverse(x5);
        System.out.println("Test 5: " + result5 + " (Expected: 0 - overflow)");

        // Test Case 6: Overflow negative
        int x6 = -2147483648;
        int result6 = solver.reverse(x6);
        System.out.println("Test 6: " + result6 + " (Expected: 0 - overflow)");

        // Test Case 7: Single digit
        int x7 = 5;
        int result7 = solver.reverse(x7);
        System.out.println("Test 7: " + result7 + " (Expected: 5)");

        // Test Case 8: Negative single digit
        int x8 = -5;
        int result8 = solver.reverse(x8);
        System.out.println("Test 8: " + result8 + " (Expected: -5)");

        // Test Case 9: Trailing zeros
        int x9 = 1000;
        int result9 = solver.reverse(x9);
        System.out.println("Test 9: " + result9 + " (Expected: 1)");

        // Test Case 10: Max int boundary
        int x10 = 2147483647;
        int result10 = solver.reverse(x10);
        System.out.println("Test 10: " + result10 + " (Expected: 0 - overflow)");
    }
}