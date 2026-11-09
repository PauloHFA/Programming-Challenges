/**
 * LeetCode 151 - Reverse Words in a String
 * 
 * Given an input string s, reverse the order of the words.
 * 
 * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
 * Return a string of the words in reverse order concatenated by a single space.
 * 
 * Note that s may contain leading or trailing spaces or multiple spaces between two words.
 * The returned string should only have a single space separating the words. Do not include any extra spaces.
 * 
 * Example 1:
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 * 
 * Example 2:
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 * 
 * Example 3:
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 * 
 * Constraints:
 * 1 <= s.length <= 10^4
 * s contains English letters (upper-case and lower-case), digits, and spaces ' '.
 * There is at least one word in s.
 * 
 * Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?
 */
public class Solution {
    
    /**
     * Approach 1: Split and Reverse - Simple and Clean
     * 
     * Split by whitespace, reverse the array, join with single space.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public String reverseWords(String s) {
        // Split by one or more spaces
        String[] words = s.trim().split("\\s+");
        
        // Reverse the array
        int left = 0, right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }
        
        // Join with single space
        return String.join(" ", words);
    }
    
    /**
     * Approach 2: StringBuilder with Manual Parsing - O(n) Time, O(n) Space
     * 
     * Parse words manually from end to beginning, append to StringBuilder.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public String reverseWordsManual(String s) {
        StringBuilder result = new StringBuilder();
        int n = s.length();
        int i = n - 1;
        
        while (i >= 0) {
            // Skip trailing spaces
            while (i >= 0 && s.charAt(i) == ' ') i--;
            if (i < 0) break;
            
            // Find start of word
            int j = i;
            while (j >= 0 && s.charAt(j) != ' ') j--;
            
            // Append word
            if (result.length() > 0) result.append(' ');
            result.append(s, j + 1, i + 1);
            
            i = j;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Two-Pass In-Place (for mutable char array) - O(n) Time, O(1) Space
     * 
     * 1. Reverse entire string
     * 2. Reverse each word
     * 3. Clean up spaces
     * 
     * Note: Java Strings are immutable, so this uses char array.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) extra (excluding input array)
     */
    public String reverseWordsInPlace(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        
        // Step 1: Reverse entire array
        reverse(chars, 0, n - 1);
        
        // Step 2: Reverse each word
        int start = 0;
        for (int i = 0; i <= n; i++) {
            if (i == n || chars[i] == ' ') {
                reverse(chars, start, i - 1);
                start = i + 1;
            }
        }
        
        // Step 3: Clean up spaces (remove leading, trailing, multiple)
        return cleanSpaces(chars);
    }
    
    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
    
    private String cleanSpaces(char[] chars) {
        int n = chars.length;
        int i = 0, j = 0;
        
        // Skip leading spaces
        while (j < n && chars[j] == ' ') j++;
        
        while (j < n) {
            // Copy non-space
            while (j < n && chars[j] != ' ') {
                chars[i++] = chars[j++];
            }
            
            // Skip multiple spaces
            while (j < n && chars[j] == ' ') j++;
            
            // Add single space if not at end
            if (j < n) {
                chars[i++] = ' ';
            }
        }
        
        return new String(chars, 0, i);
    }
    
    /**
     * Approach 4: Using Deque
     * 
     * Parse words and add to front of deque.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public String reverseWordsDeque(String s) {
        java.util.Deque<String> deque = new java.util.ArrayDeque<>();
        StringBuilder word = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (word.length() > 0) {
                    deque.addFirst(word.toString());
                    word.setLength(0);
                }
            } else {
                word.append(c);
            }
        }
        
        // Don't forget last word
        if (word.length() > 0) {
            deque.addFirst(word.toString());
        }
        
        return String.join(" ", deque);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Reverse Words in a String Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: \"the sky is blue\" -> Expected: \"blue is sky the\"");
        String s1 = "the sky is blue";
        String result1 = sol.reverseWords(s1);
        System.out.println("  Split:    \"" + result1 + "\" " + (result1.equals("blue is sky the") ? "✓" : "✗"));
        System.out.println("  Manual:   \"" + sol.reverseWordsManual(s1) + "\"");
        System.out.println("  InPlace:  \"" + sol.reverseWordsInPlace(s1) + "\"");
        System.out.println("  Deque:    \"" + sol.reverseWordsDeque(s1) + "\"");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: \"  hello world  \" -> Expected: \"world hello\"");
        String s2 = "  hello world  ";
        String result2 = sol.reverseWords(s2);
        System.out.println("  Split:    \"" + result2 + "\" " + (result2.equals("world hello") ? "✓" : "✗"));
        System.out.println("  Manual:   \"" + sol.reverseWordsManual(s2) + "\"");
        System.out.println("  InPlace:  \"" + sol.reverseWordsInPlace(s2) + "\"");
        System.out.println("  Deque:    \"" + sol.reverseWordsDeque(s2) + "\"");
        
        // Test 3: Example 3
        System.out.println("\nTest 3: \"a good   example\" -> Expected: \"example good a\"");
        String s3 = "a good   example";
        String result3 = sol.reverseWords(s3);
        System.out.println("  Split:    \"" + result3 + "\" " + (result3.equals("example good a") ? "✓" : "✗"));
        System.out.println("  Manual:   \"" + sol.reverseWordsManual(s3) + "\"");
        System.out.println("  InPlace:  \"" + sol.reverseWordsInPlace(s3) + "\"");
        System.out.println("  Deque:    \"" + sol.reverseWordsDeque(s3) + "\"");
        
        // Test 4: Single word
        System.out.println("\nTest 4: \"hello\" -> Expected: \"hello\"");
        String s4 = "hello";
        String result4 = sol.reverseWords(s4);
        System.out.println("  Split:    \"" + result4 + "\" " + (result4.equals("hello") ? "✓" : "✗"));
        
        // Test 5: Multiple spaces
        System.out.println("\nTest 5: \"  a   b  c   \" -> Expected: \"c b a\"");
        String s5 = "  a   b  c   ";
        String result5 = sol.reverseWords(s5);
        System.out.println("  Split:    \"" + result5 + "\" " + (result5.equals("c b a") ? "✓" : "✗"));
        System.out.println("  Manual:   \"" + sol.reverseWordsManual(s5) + "\"");
        System.out.println("  InPlace:  \"" + sol.reverseWordsInPlace(s5) + "\"");
        System.out.println("  Deque:    \"" + sol.reverseWordsDeque(s5) + "\"");
        
        // Test 6: Leading/trailing spaces
        System.out.println("\nTest 6: \"   leading and trailing   \" -> Expected: \"trailing and leading\"");
        String s6 = "   leading and trailing   ";
        String result6 = sol.reverseWords(s6);
        System.out.println("  Split:    \"" + result6 + "\" " + (result6.equals("trailing and leading") ? "✓" : "✗"));
        
        // Test 7: Cross verification
        System.out.println("\nTest 7: Cross verification of all approaches");
        String[] testCases = {
            "the sky is blue",
            "  hello world  ",
            "a good   example",
            "hello",
            "  a   b  c   ",
            "   leading and trailing   ",
            "one two three four five",
            "  multiple    spaces    here  "
        };
        String[] expected = {
            "blue is sky the",
            "world hello",
            "example good a",
            "hello",
            "c b a",
            "trailing and leading",
            "five four three two one",
            "here spaces multiple"
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            String r1 = sol.reverseWords(testCases[i]);
            String r2 = sol.reverseWordsManual(testCases[i]);
            String r3 = sol.reverseWordsInPlace(testCases[i]);
            String r4 = sol.reverseWordsDeque(testCases[i]);
            
            boolean m1 = r1.equals(expected[i]);
            boolean m2 = r2.equals(expected[i]);
            boolean m3 = r3.equals(expected[i]);
            boolean m4 = r4.equals(expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Split:" + m1 + " Man:" + m2 + " IP:" + m3 + " Dq:" + m4 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}