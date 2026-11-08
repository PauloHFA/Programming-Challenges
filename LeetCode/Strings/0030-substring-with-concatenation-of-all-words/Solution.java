import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 0030: Substring with Concatenation of All Words
 * Difficulty: Hard
 * Category: Strings / Sliding Window
 * 
 * Approach: Sliding Window with Hash Map
 * Time Complexity: O(n * m * k) where n = s.length, m = words.length, k = word length
 * Space Complexity: O(m * k)
 */
public class Solution {

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0 || words[0].isEmpty()) {
            return result;
        }

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;
        int n = s.length();

        if (n < totalLen) {
            return result;
        }

        // Build word frequency map
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        // Try each possible starting offset (0 to wordLen-1)
        for (int offset = 0; offset < wordLen; offset++) {
            int left = offset;
            int count = 0;
            Map<String, Integer> windowFreq = new HashMap<>();

            for (int right = offset; right + wordLen <= n; right += wordLen) {
                String word = s.substring(right, right + wordLen);

                if (wordFreq.containsKey(word)) {
                    windowFreq.put(word, windowFreq.getOrDefault(word, 0) + 1);
                    count++;

                    // If we have too many of this word, shrink from left
                    while (windowFreq.get(word) > wordFreq.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowFreq.put(leftWord, windowFreq.get(leftWord) - 1);
                        count--;
                        left += wordLen;
                    }

                    // If we found all words
                    if (count == wordCount) {
                        result.add(left);
                    }
                } else {
                    // Reset window
                    windowFreq.clear();
                    count = 0;
                    left = right + wordLen;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "barfoothefoobarman";
        String[] words1 = {"foo", "bar"};
        List<Integer> result1 = solver.findSubstring(s1, words1);
        System.out.println("Test 1: " + result1 + " (Expected: [0, 9])");

        // Test Case 2
        String s2 = "wordgoodgoodgoodbestword";
                String[] words2 = {"word", "good", "best", "good"};
        List<Integer> result2 = solver.findSubstring(s2, words2);
        System.out.println("Test 2: " + result2 + " (Expected: [8])");

        // Test Case 3
        String s3 = "barfoofoobarthefoobarman";
        String[] words3 = {"bar", "foo", "the"};
        List<Integer> result3 = solver.findSubstring(s3, words3);
        System.out.println("Test 3: " + result3 + " (Expected: [6, 9, 12])");

        // Test Case 4: Empty words
        String s4 = "abc";
        String[] words4 = {};
        List<Integer> result4 = solver.findSubstring(s4, words4);
        System.out.println("Test 4: " + result4 + " (Expected: [])");

        // Test Case 5: Single word
        String s5 = "abc";
        String[] words5 = {"abc"};
        List<Integer> result5 = solver.findSubstring(s5, words5);
        System.out.println("Test 5: " + result5 + " (Expected: [0])");

        // Test Case 6: Word not in string
        String s6 = "abcdef";
        String[] words6 = {"gh"};
        List<Integer> result6 = solver.findSubstring(s6, words6);
        System.out.println("Test 6: " + result6 + " (Expected: [])");

        // Test Case 7: Overlapping words
        String s7 = "aaaaaa";
        String[] words7 = {"aa", "aa"};
        List<Integer> result7 = solver.findSubstring(s7, words7);
        System.out.println("Test 7: " + result7 + " (Expected: [0, 1, 2])");

        // Test Case 8: Multiple same words
        String s8 = "wordgoodgoodgoodbestword";
        String[] words8 = {"word", "good", "best", "good"};
        List<Integer> result8 = solver.findSubstring(s8, words8);
        System.out.println("Test 8: " + result8 + " (Expected: [8])");
    }
}