/**
 * LeetCode 0028: Find the Index of the First Occurrence in a String
 * Difficulty: Easy
 * Category: Strings / Two Pointers
 * 
 * Approach: Built-in indexOf (or KMP for O(n+m))
 * Time Complexity: O(n * m) for naive, O(n + m) for KMP
 * Space Complexity: O(1) or O(m) for KMP
 */
public class Solution {

    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        return haystack.indexOf(needle);
    }

    // Alternative: KMP Algorithm for O(n + m) time
    public int strStrKMP(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }

        int n = haystack.length();
        int m = needle.length();

        // Build LPS (Longest Prefix Suffix) array
        int[] lps = new int[m];
        buildLPS(needle, lps);

        int i = 0; // index for haystack
        int j = 0; // index for needle

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                return i - j;
            } else if (i < n && haystack.charAt(i) != needle.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1;
    }

    private void buildLPS(String pattern, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String haystack1 = "sadbutsad";
        String needle1 = "sad";
        int result1 = solver.strStr(haystack1, needle1);
        System.out.println("Test 1: " + result1 + " (Expected: 0)");

        // Test Case 2
        String haystack2 = "leetcode";
        String needle2 = "leeto";
        int result2 = solver.strStr(haystack2, needle2);
        System.out.println("Test 2: " + result2 + " (Expected: -1)");

        // Test Case 3: Needle at end
        String haystack3 = "hello";
        String needle3 = "lo";
        int result3 = solver.strStr(haystack3, needle3);
        System.out.println("Test 3: " + result3 + " (Expected: 3)");

        // Test Case 4: Needle equals haystack
        String haystack4 = "abc";
        String needle4 = "abc";
        int result4 = solver.strStr(haystack4, needle4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Empty needle
        String haystack5 = "abc";
        String needle5 = "";
        int result5 = solver.strStr(haystack5, needle5);
        System.out.println("Test 5: " + result5 + " (Expected: 0)");

        // Test Case 6: Single character match
        String haystack6 = "a";
        String needle6 = "a";
        int result6 = solver.strStr(haystack6, needle6);
        System.out.println("Test 6: " + result6 + " (Expected: 0)");

        // Test Case 7: Single character no match
        String haystack7 = "a";
        String needle7 = "b";
        int result7 = solver.strStr(haystack7, needle7);
        System.out.println("Test 7: " + result7 + " (Expected: -1)");

        // Test Case 8: Multiple occurrences
        String haystack8 = "mississippi";
        String needle8 = "issip";
        int result8 = solver.strStr(haystack8, needle8);
        System.out.println("Test 8: " + result8 + " (Expected: 4)");

        // Test Case 9: Needle longer than haystack
        String haystack9 = "abc";
        String needle9 = "abcd";
        int result9 = solver.strStr(haystack9, needle9);
        System.out.println("Test 9: " + result9 + " (Expected: -1)");

        // Test Case 10: Overlapping pattern
        String haystack10 = "aaaaa";
        String needle10 = "bba";
        int result10 = solver.strStr(haystack10, needle10);
        System.out.println("Test 10: " + result10 + " (Expected: -1)");

        // Test KMP implementation
        System.out.println("\n--- KMP Implementation Tests ---");
        System.out.println("Test 1 KMP: " + solver.strStrKMP(haystack1, needle1) + " (Expected: 0)");
        System.out.println("Test 2 KMP: " + solver.strStrKMP(haystack2, needle2) + " (Expected: -1)");
        System.out.println("Test 8 KMP: " + solver.strStrKMP(haystack8, needle8) + " (Expected: 4)");
    }
}