/**
 * LeetCode 0008: String to Integer (atoi)
 * Difficulty: Medium
 * Category: Strings
 * 
 * Approach: State Machine / Step-by-step Parsing
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int index = 0;
        int n = s.length();
        int sign = 1;
        int result = 0;

        // Step 1: Skip leading whitespace
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        // Check if all whitespace
        if (index == n) {
            return 0;
        }

        // Step 2: Check sign
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }

        // Step 3: Convert digits
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            // Check overflow before multiplying
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return result * sign;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "42";
        int result1 = solver.myAtoi(s1);
        System.out.println("Test 1: " + result1 + " (Expected: 42)");

        // Test Case 2
        String s2 = "   -42";
        int result2 = solver.myAtoi(s2);
        System.out.println("Test 2: " + result2 + " (Expected: -42)");

        // Test Case 3
        String s3 = "4193 with words";
        int result3 = solver.myAtoi(s3);
        System.out.println("Test 3: " + result3 + " (Expected: 4193)");

        // Test Case 4
        String s4 = "words and 987";
        int result4 = solver.myAtoi(s4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5
        String s5 = "-91283472332";
        int result5 = solver.myAtoi(s5);
        System.out.println("Test 5: " + result5 + " (Expected: -2147483648)");

        // Test Case 6: Leading zeros
        String s6 = "  0000000000012345678";
        int result6 = solver.myAtoi(s6);
        System.out.println("Test 6: " + result6 + " (Expected: 12345678)");

        // Test Case 7: Only sign
        String s7 = "+";
        int result7 = solver.myAtoi(s7);
        System.out.println("Test 7: " + result7 + " (Expected: 0)");

        // Test Case 8: Empty string
        String s8 = "";
        int result8 = solver.myAtoi(s8);
        System.out.println("Test 8: " + result8 + " (Expected: 0)");

        // Test Case 9: Whitespace only
        String s9 = "   ";
        int result9 = solver.myAtoi(s9);
        System.out.println("Test 9: " + result9 + " (Expected: 0)");

        // Test Case 10: Overflow positive
        String s10 = "2147483648";
        int result10 = solver.myAtoi(s10);
        System.out.println("Test 10: " + result10 + " (Expected: 2147483647)");

        // Test Case 11: Overflow negative
        String s11 = "-2147483649";
        int result11 = solver.myAtoi(s11);
        System.out.println("Test 11: " + result11 + " (Expected: -2147483648)");

        // Test Case 12: Multiple signs
        String s12 = "+-12";
        int result12 = solver.myAtoi(s12);
        System.out.println("Test 12: " + result12 + " (Expected: 0)");

        // Test Case 13: Sign followed by non-digit
        String s13 = "-abc";
        int result13 = solver.myAtoi(s13);
        System.out.println("Test 13: " + result13 + " (Expected: 0)");
    }
}