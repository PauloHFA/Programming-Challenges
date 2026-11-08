/**
 * LeetCode 0014: Longest Common Prefix
 * Difficulty: Easy
 * Category: Strings
 * 
 * Approach: Vertical Scanning
 * Time Complexity: O(S) where S is sum of all characters
 * Space Complexity: O(1)
 */
public class Solution {

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // Find the shortest string length
        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < minLength; i++) {
            char currentChar = strs[0].charAt(i);
            boolean allMatch = true;

            for (int j = 1; j < strs.length; j++) {
                if (strs[j].charAt(i) != currentChar) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                prefix.append(currentChar);
            } else {
                break;
            }
        }

        return prefix.toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String[] strs1 = {"flower", "flow", "flight"};
        String result1 = solver.longestCommonPrefix(strs1);
        System.out.println("Test 1: \"" + result1 + "\" (Expected: \"fl\")");

        // Test Case 2
        String[] strs2 = {"dog", "racecar", "car"};
        String result2 = solver.longestCommonPrefix(strs2);
        System.out.println("Test 2: \"" + result2 + "\" (Expected: \"\")");

        // Test Case 3: Single string
        String[] strs3 = {"hello"};
        String result3 = solver.longestCommonPrefix(strs3);
        System.out.println("Test 3: \"" + result3 + "\" (Expected: \"hello\")");

        // Test Case 4: Empty array
        String[] strs4 = {};
        String result4 = solver.longestCommonPrefix(strs4);
        System.out.println("Test 4: \"" + result4 + "\" (Expected: \"\")");

        // Test Case 5: All same
        String[] strs5 = {"abc", "abc", "abc"};
        String result5 = solver.longestCommonPrefix(strs5);
        System.out.println("Test 5: \"" + result5 + "\" (Expected: \"abc\")");

        // Test Case 6: One empty string
        String[] strs6 = {"", "abc", "ab"};
        String result6 = solver.longestCommonPrefix(strs6);
        System.out.println("Test 6: \"" + result6 + "\" (Expected: \"\")");

        // Test Case 7: Prefix at end
        String[] strs7 = {"ab", "a"};
        String result7 = solver.longestCommonPrefix(strs7);
        System.out.println("Test 7: \"" + result7 + "\" (Expected: \"a\")");

        // Test Case 8: No common prefix
        String[] strs8 = {"abc", "def", "ghi"};
        String result8 = solver.longestCommonPrefix(strs8);
        System.out.println("Test 8: \"" + result8 + "\" (Expected: \"\")");

        // Test Case 9: Long common prefix
        String[] strs9 = {"interspecies", "interstellar", "interstate"};
        String result9 = solver.longestCommonPrefix(strs9);
        System.out.println("Test 9: \"" + result9 + "\" (Expected: \"inters\")");

        // Test Case 10: Case sensitive
        String[] strs10 = {"Flower", "flow", "flight"};
        String result10 = solver.longestCommonPrefix(strs10);
        System.out.println("Test 10: \"" + result10 + "\" (Expected: \"\")");
    }
}