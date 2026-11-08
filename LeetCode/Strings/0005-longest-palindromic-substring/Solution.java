/**
 * LeetCode 0005: Longest Palindromic Substring
 * Difficulty: Medium
 * Category: Strings (Two Pointers / Expand Around Center)
 * 
 * Approach: Expand Around Center
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class Solution {

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int start = 0;
        int maxLength = 1;

        for (int i = 0; i < s.length(); i++) {
            // Odd length palindrome (center at i)
            int len1 = expandAroundCenter(s, i, i);
            // Even length palindrome (center between i and i+1)
            int len2 = expandAroundCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > maxLength) {
                maxLength = len;
                start = i - (len - 1) / 2;
            }
        }

        return s.substring(start, start + maxLength);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "babad";
        String result1 = solver.longestPalindrome(s1);
        System.out.println("Test 1: \"" + result1 + "\" (Expected: \"bab\" or \"aba\")");

        // Test Case 2
        String s2 = "cbbd";
        String result2 = solver.longestPalindrome(s2);
        System.out.println("Test 2: \"" + result2 + "\" (Expected: \"bb\")");

        // Test Case 3: Single character
        String s3 = "a";
        String result3 = solver.longestPalindrome(s3);
        System.out.println("Test 3: \"" + result3 + "\" (Expected: \"a\")");

        // Test Case 4: Two same characters
        String s4 = "aa";
        String result4 = solver.longestPalindrome(s4);
        System.out.println("Test 4: \"" + result4 + "\" (Expected: \"aa\")");

        // Test Case 5: Two different characters
        String s5 = "ab";
        String result5 = solver.longestPalindrome(s5);
        System.out.println("Test 5: \"" + result5 + "\" (Expected: \"a\" or \"b\")");

        // Test Case 6: All same characters
        String s6 = "aaaa";
        String result6 = solver.longestPalindrome(s6);
        System.out.println("Test 6: \"" + result6 + "\" (Expected: \"aaaa\")");

        // Test Case 7: Palindrome in the middle
        String s7 = "abcba";
        String result7 = solver.longestPalindrome(s7);
        System.out.println("Test 7: \"" + result7 + "\" (Expected: \"abcba\")");

        // Test Case 8: Even length palindrome
        String s8 = "abba";
        String result8 = solver.longestPalindrome(s8);
        System.out.println("Test 8: \"" + result8 + "\" (Expected: \"abba\")");

        // Test Case 9: Complex case
        String s9 = "ac";
        String result9 = solver.longestPalindrome(s9);
        System.out.println("Test 9: \"" + result9 + "\" (Expected: \"a\" or \"c\")");

        // Test Case 10: Longer string
        String s10 = "forgeeksskeegfor";
        String result10 = solver.longestPalindrome(s10);
        System.out.println("Test 10: \"" + result10 + "\" (Expected: \"geeksskeeg\")");
    }
}