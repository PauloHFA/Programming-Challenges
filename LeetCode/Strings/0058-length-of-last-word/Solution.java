/**
 * LeetCode 0058: Length of Last Word
 * Difficulty: Easy
 * Category: Strings
 * 
 * Approach: Trim and find last space
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int lengthOfLastWord(String s) {
        // Trim trailing spaces
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') {
            end--;
        }
        
        if (end < 0) {
            return 0;
        }
        
        // Find start of last word
        int start = end;
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }
        
        return end - start;
    }

    // Alternative: Using split (simpler but creates array)
    public int lengthOfLastWordSplit(String s) {
        String[] words = s.trim().split("\\s+");
        return words.length > 0 ? words[words.length - 1].length() : 0;
    }

    // Alternative: Single pass from end
    public int lengthOfLastWordSinglePass(String s) {
        int length = 0;
        int i = s.length() - 1;
        
        // Skip trailing spaces
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        
        // Count last word
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }
        
        return length;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "Hello World";
        int result1 = solver.lengthOfLastWord(s1);
        System.out.println("Test 1: " + result1 + " (Expected: 5)");

        // Test Case 2
        String s2 = "   fly me   to   the moon  ";
        int result2 = solver.lengthOfLastWord(s2);
        System.out.println("Test 2: " + result2 + " (Expected: 4)");

        // Test Case 3
        String s3 = "luffy is still joyboy";
        int result3 = solver.lengthOfLastWord(s3);
        System.out.println("Test 3: " + result3 + " (Expected: 6)");

        // Test Case 4: Single word
        String s4 = "Hello";
        int result4 = solver.lengthOfLastWord(s4);
        System.out.println("Test 4: " + result4 + " (Expected: 5)");

        // Test Case 5: Trailing spaces
        String s5 = "Hello   ";
        int result5 = solver.lengthOfLastWord(s5);
        System.out.println("Test 5: " + result5 + " (Expected: 5)");

        // Test Case 6: Leading spaces
        String s6 = "   Hello";
        int result6 = solver.lengthOfLastWord(s6);
        System.out.println("Test 6: " + result6 + " (Expected: 5)");

        // Test Case 7: Only spaces
        String s7 = "    ";
        int result7 = solver.lengthOfLastWord(s7);
        System.out.println("Test 7: " + result7 + " (Expected: 0)");

        // Test Case 8: Empty string
        String s8 = "";
        int result8 = solver.lengthOfLastWord(s8);
        System.out.println("Test 8: " + result8 + " (Expected: 0)");

        // Test Case 9: Multiple spaces between words
        String s9 = "a  b  c  ";
        int result9 = solver.lengthOfLastWord(s9);
        System.out.println("Test 9: " + result9 + " (Expected: 1)");

        // Verify all approaches match
        System.out.println("\n--- Verification (Main vs Split vs SinglePass) ---");
        String[] testCases = {s1, s2, s3, s4, s5, s6, s7, s8, s9};
        for (String test : testCases) {
            int main = solver.lengthOfLastWord(test);
            int split = solver.lengthOfLastWordSplit(test);
            int single = solver.lengthOfLastWordSinglePass(test);
            System.out.println("Input: \"" + test + "\" -> Main: " + main + ", Split: " + split + ", Single: " + single + " (match: " + (main == split && split == single) + ")");
        }
    }
}