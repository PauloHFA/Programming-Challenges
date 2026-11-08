import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 0003: Longest Substring Without Repeating Characters
 * Difficulty: Medium
 * Category: Sliding Window
 * 
 * Approach: Sliding Window with Hash Map
 * Time Complexity: O(n)
 * Space Complexity: O(min(m, n)) where m is the charset size
 */
public class Solution {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }

            charIndexMap.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "abcabcbb";
        int result1 = solver.lengthOfLongestSubstring(s1);
        System.out.println("Test 1: " + result1 + " (Expected: 3) - Explanation: \"abc\"");

        // Test Case 2
        String s2 = "bbbbb";
        int result2 = solver.lengthOfLongestSubstring(s2);
        System.out.println("Test 2: " + result2 + " (Expected: 1) - Explanation: \"b\"");

        // Test Case 3
        String s3 = "pwwkew";
        int result3 = solver.lengthOfLongestSubstring(s3);
        System.out.println("Test 3: " + result3 + " (Expected: 3) - Explanation: \"wke\"");

        // Test Case 4: Empty string
        String s4 = "";
        int result4 = solver.lengthOfLongestSubstring(s4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Single character
        String s5 = " ";
        int result5 = solver.lengthOfLongestSubstring(s5);
        System.out.println("Test 5: " + result5 + " (Expected: 1)");

        // Test Case 6: All unique characters
        String s6 = "abcdef";
        int result6 = solver.lengthOfLongestSubstring(s6);
        System.out.println("Test 6: " + result6 + " (Expected: 6)");

        // Test Case 7: Repeating at the end
        String s7 = "dvdf";
        int result7 = solver.lengthOfLongestSubstring(s7);
        System.out.println("Test 7: " + result7 + " (Expected: 3) - Explanation: \"vdf\"");
    }
}