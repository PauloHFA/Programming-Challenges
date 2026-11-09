/**
 * LeetCode 159 - Longest Substring with At Most Two Distinct Characters
 * 
 * Given a string s, return the length of the longest substring that contains at most two distinct characters.
 * 
 * Example 1:
 * Input: s = "eceba"
 * Output: 3
 * Explanation: The substring is "ece" which its length is 3.
 * 
 * Example 2:
 * Input: s = "ccaabbb"
 * Output: 5
 * Explanation: The substring is "aabbb" which its length is 5.
 * 
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of English letters.
 */
public class Solution {
    
    /**
     * Approach 1: Sliding Window with HashMap - O(n) time, O(1) space
     * 
     * Use a sliding window with a HashMap to track character counts.
     * Expand right pointer, when more than 2 distinct chars, shrink left.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) - at most 3 entries in map
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() <= 2) return s.length();
        
        java.util.Map<Character, Integer> count = new java.util.HashMap<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            count.put(c, count.getOrDefault(c, 0) + 1);
            
            while (count.size() > 2) {
                char leftChar = s.charAt(left);
                count.put(leftChar, count.get(leftChar) - 1);
                if (count.get(leftChar) == 0) {
                    count.remove(leftChar);
                }
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    /**
     * Approach 2: Sliding Window with Array - O(n) time, O(1) space
     * 
     * Since s consists of English letters, use array of size 128 (ASCII) or 256.
     * More efficient than HashMap for this constrained character set.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) - fixed size array
     */
    public int lengthOfLongestSubstringTwoDistinctArray(String s) {
        if (s.length() <= 2) return s.length();
        
        int[] count = new int[128];
        int distinct = 0;
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (count[c] == 0) distinct++;
            count[c]++;
            
            while (distinct > 2) {
                char leftChar = s.charAt(left);
                count[leftChar]--;
                if (count[leftChar] == 0) distinct--;
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    /**
     * Approach 3: Sliding Window with Last Positions - O(n) time, O(1) space
     * 
     * Track the last position of each character. When we have 3 distinct chars,
     * move left to min(lastPos) + 1.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int lengthOfLongestSubstringTwoDistinctLastPos(String s) {
        if (s.length() <= 2) return s.length();
        
        java.util.Map<Character, Integer> lastPos = new java.util.HashMap<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            lastPos.put(c, right);
            
            if (lastPos.size() > 2) {
                // Find the character with the smallest last position
                int minPos = s.length();
                char charToRemove = 0;
                for (java.util.Map.Entry<Character, Integer> entry : lastPos.entrySet()) {
                    if (entry.getValue() < minPos) {
                        minPos = entry.getValue();
                        charToRemove = entry.getKey();
                    }
                }
                lastPos.remove(charToRemove);
                left = minPos + 1;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    /**
         * Approach 4: Two Pointers with Character Tracking - O(n) time, O(1) space
         * 
         * Track the two most recent distinct characters and their last positions.
         * When a third distinct character appears, move left to min(lastPos) + 1.
         * 
         * Time Complexity: O(n)
         * Space Complexity: O(1)
         */
        public int lengthOfLongestSubstringTwoDistinctOptimized(String s) {
            if (s.length() <= 2) return s.length();
        
            // Track last positions of the two characters in current window
            char char1 = s.charAt(0);
            char char2 = 0;
            int lastPos1 = 0;
            int lastPos2 = -1;
            int left = 0;
            int maxLen = 1;
        
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
            
                if (c == char1) {
                    lastPos1 = i;
                } else if (char2 == 0 || c == char2) {
                    if (char2 == 0) {
                        char2 = c;
                    }
                    lastPos2 = i;
                } else {
                    // Third distinct character
                    // Move left to after the earlier last position
                    if (lastPos1 < lastPos2) {
                        left = lastPos1 + 1;
                        char1 = char2;
                        lastPos1 = lastPos2;
                    } else {
                        left = lastPos2 + 1;
                    }
                    char2 = c;
                    lastPos2 = i;
                }
            
                maxLen = Math.max(maxLen, i - left + 1);
            }
        
            return maxLen;
        }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Longest Substring with At Most Two Distinct Characters Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: s=\"eceba\" -> Expected: 3 (\"ece\")");
        testCase(sol, "eceba", 3);
        
        // Test 2: Example 2
        System.out.println("\nTest 2: s=\"ccaabbb\" -> Expected: 5 (\"aabbb\")");
        testCase(sol, "ccaabbb", 5);
        
        // Test 3: All same character
        System.out.println("\nTest 3: s=\"aaaaa\" -> Expected: 5");
        testCase(sol, "aaaaa", 5);
        
        // Test 4: Two characters alternating
        System.out.println("\nTest 4: s=\"ababab\" -> Expected: 6");
        testCase(sol, "ababab", 6);
        
        // Test 5: Three distinct characters
        System.out.println("\nTest 5: s=\"abc\" -> Expected: 2");
        testCase(sol, "abc", 2);
        
        // Test 6: Single character
        System.out.println("\nTest 6: s=\"a\" -> Expected: 1");
        testCase(sol, "a", 1);
        
        // Test 7: Two characters
        System.out.println("\nTest 7: s=\"ab\" -> Expected: 2");
        testCase(sol, "ab", 2);
        
        // Test 8: Complex case
        System.out.println("\nTest 8: s=\"abaccc\" -> Expected: 4 (\"accc\")");
        testCase(sol, "abaccc", 4);
        
        // Test 9: Another complex case
        System.out.println("\nTest 9: s=\"aabbcc\" -> Expected: 4 (\"aabb\" or \"bbcc\")");
        testCase(sol, "aabbcc", 4);
        
        // Test 10: Long string
        System.out.println("\nTest 10: s=\"abcabcabc\" -> Expected: 2");
        testCase(sol, "abcabcabc", 2);
        
        // Test 11: Cross verification
        System.out.println("\nTest 11: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, String s, int expected) {
        int r1 = sol.lengthOfLongestSubstringTwoDistinct(s);
        int r2 = sol.lengthOfLongestSubstringTwoDistinctArray(s);
        int r3 = sol.lengthOfLongestSubstringTwoDistinctLastPos(s);
        int r4 = sol.lengthOfLongestSubstringTwoDistinctOptimized(s);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        System.out.println("  HashMap: " + r1 + (r1 == expected ? " ✓" : " ✗"));
        System.out.println("  Array:   " + r2 + (r2 == expected ? " ✓" : " ✗"));
        System.out.println("  LastPos: " + r3 + (r3 == expected ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + r4 + (r4 == expected ? " ✓" : " ✗"));
        
        assert match : "Mismatch for s=" + s + ": expected " + expected + ", got " + r1 + "," + r2 + "," + r3 + "," + r4;
    }
    
    static void crossVerify(Solution sol) {
        String[] testCases = {
            "eceba", "ccaabbb", "aaaaa", "ababab", "abc", "a", "ab", 
            "abaccc", "aabbcc", "abcabcabc", "abbaac", "abcbbbbcccbdddadacb"
        };
        
        for (String s : testCases) {
            int r1 = sol.lengthOfLongestSubstringTwoDistinct(s);
            int r2 = sol.lengthOfLongestSubstringTwoDistinctArray(s);
            int r3 = sol.lengthOfLongestSubstringTwoDistinctLastPos(s);
            int r4 = sol.lengthOfLongestSubstringTwoDistinctOptimized(s);
            
            boolean match = (r1 == r2 && r2 == r3 && r3 == r4);
            System.out.println("  s=\"" + s + "\": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + ")");
            assert match : "Cross-verification failed for s=" + s;
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}