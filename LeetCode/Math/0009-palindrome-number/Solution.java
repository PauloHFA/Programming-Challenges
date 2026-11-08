/**
 * LeetCode 0009: Palindrome Number
 * Difficulty: Easy
 * Category: Math
 * 
 * Approach: Revert Half of the Number
 * Time Complexity: O(log10(n))
 * Space Complexity: O(1)
 */
public class Solution {

    public boolean isPalindrome(int x) {
        // Negative numbers are not palindromes
        // Numbers ending with 0 (except 0 itself) are not palindromes
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // When the length is odd, we can get rid of the middle digit by revertedNumber/10
        // For example, when input is 12321, at the end of the loop x = 12, revertedNumber = 123
        return x == revertedNumber || x == revertedNumber / 10;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int x1 = 121;
        boolean result1 = solver.isPalindrome(x1);
        System.out.println("Test 1: " + result1 + " (Expected: true)");

        // Test Case 2
        int x2 = -121;
        boolean result2 = solver.isPalindrome(x2);
        System.out.println("Test 2: " + result2 + " (Expected: false)");

        // Test Case 3
        int x3 = 10;
        boolean result3 = solver.isPalindrome(x3);
        System.out.println("Test 3: " + result3 + " (Expected: false)");

        // Test Case 4: Zero
        int x4 = 0;
        boolean result4 = solver.isPalindrome(x4);
        System.out.println("Test 4: " + result4 + " (Expected: true)");

        // Test Case 5: Single digit
        int x5 = 5;
        boolean result5 = solver.isPalindrome(x5);
        System.out.println("Test 5: " + result5 + " (Expected: true)");

        // Test Case 6: Even length palindrome
        int x6 = 1221;
        boolean result6 = solver.isPalindrome(x6);
        System.out.println("Test 6: " + result6 + " (Expected: true)");

        // Test Case 7: Odd length palindrome
        int x7 = 12321;
        boolean result7 = solver.isPalindrome(x7);
        System.out.println("Test 7: " + result7 + " (Expected: true)");

        // Test Case 8: Not palindrome
        int x8 = 123;
        boolean result8 = solver.isPalindrome(x8);
        System.out.println("Test 8: " + result8 + " (Expected: false)");

        // Test Case 9: Ends with zero
        int x9 = 1001;
        boolean result9 = solver.isPalindrome(x9);
        System.out.println("Test 9: " + result9 + " (Expected: true)");

        // Test Case 10: Large palindrome
        int x10 = 123454321;
        boolean result10 = solver.isPalindrome(x10);
        System.out.println("Test 10: " + result10 + " (Expected: true)");

        // Test Case 11: Negative
        int x11 = -101;
        boolean result11 = solver.isPalindrome(x11);
        System.out.println("Test 11: " + result11 + " (Expected: false)");

        // Test Case 12: Max int palindrome
        int x12 = 2147447412;
        boolean result12 = solver.isPalindrome(x12);
        System.out.println("Test 12: " + result12 + " (Expected: true)");
    }
}