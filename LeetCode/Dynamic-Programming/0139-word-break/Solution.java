/**
 * LeetCode 139 - Word Break
 * 
 * Given a string s and a dictionary of strings wordDict, return true if s can be segmented
 * into a space-separated sequence of one or more dictionary words.
 * 
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 * 
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet","code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 * 
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple","pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 * 
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: false
 * 
 * Constraints:
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 */
public class Solution {
    
    /**
     * Approach 1: Dynamic Programming - Bottom Up
     * 
     * dp[i] = true if s[0..i-1] can be segmented using words from wordDict
     * dp[0] = true (empty string)
     * For each i from 1 to n:
     *   For each j from 0 to i-1:
     *     if dp[j] && wordDict.contains(s.substring(j, i)): dp[i] = true
     * 
     * Time Complexity: O(n^2 * m) where n = s.length, m = average word length
     * Space Complexity: O(n)
     */
    public boolean wordBreak(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 2: Dynamic Programming with Max Word Length Optimization
     * 
     * Only check substrings up to max word length in dictionary.
     * 
     * Time Complexity: O(n * maxLen * m) where maxLen = max word length
     * Space Complexity: O(n)
     */
    public boolean wordBreakOptimized(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int maxLen = 0;
        for (String word : wordDict) {
            maxLen = Math.max(maxLen, word.length());
        }
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 1; i <= n; i++) {
            for (int j = Math.max(0, i - maxLen); j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 3: BFS (Breadth-First Search)
     * 
     * Treat indices as nodes. Start from index 0, try to reach index n.
     * Use a queue to explore reachable indices.
     * 
     * Time Complexity: O(n^2 * m)
     * Space Complexity: O(n) for visited array + queue
     */
    public boolean wordBreakBFS(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int n = s.length();
        boolean[] visited = new boolean[n + 1];
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        
        while (!queue.isEmpty()) {
            int start = queue.poll();
            if (start == n) return true;
            
            for (int end = start + 1; end <= n; end++) {
                if (visited[end]) continue;
                if (wordSet.contains(s.substring(start, end))) {
                    queue.offer(end);
                    visited[end] = true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Recursive with Memoization (Top-Down DP)
     * 
     * Time Complexity: O(n^2 * m)
     * Space Complexity: O(n) for memo + O(n) recursion stack
     */
    public boolean wordBreakMemo(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int n = s.length();
        int[] memo = new int[n + 1]; // 0 = unknown, 1 = true, -1 = false
        return wordBreakMemoHelper(s, 0, wordSet, memo);
    }
    
    private boolean wordBreakMemoHelper(String s, int start, java.util.Set<String> wordSet, int[] memo) {
        if (start == s.length()) return true;
        if (memo[start] != 0) return memo[start] == 1;
        
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordSet.contains(s.substring(start, end)) && 
                wordBreakMemoHelper(s, end, wordSet, memo)) {
                memo[start] = 1;
                return true;
            }
        }
        
        memo[start] = -1;
        return false;
    }
    
    /**
     * Approach 5: Trie + DP (for very large dictionaries)
     * 
     * Build a Trie from wordDict, then use DP with Trie traversal.
     * 
     * Time Complexity: O(n * maxLen) for DP + O(total chars in dict) for Trie
     * Space Complexity: O(n + total chars in dict)
     */
    public boolean wordBreakTrie(String s, java.util.List<String> wordDict) {
        TrieNode root = new TrieNode();
        for (String word : wordDict) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.isWord = true;
        }
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 0; i < n; i++) {
            if (!dp[i]) continue;
            TrieNode node = root;
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                if (node.children[c - 'a'] == null) break;
                node = node.children[c - 'a'];
                if (node.isWord) {
                    dp[j + 1] = true;
                }
            }
        }
        
        return dp[n];
    }
    
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Word Break Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: s = \"leetcode\", wordDict = [\"leet\",\"code\"]");
        String s1 = "leetcode";
        java.util.List<String> dict1 = java.util.Arrays.asList("leet", "code");
        boolean expected1 = true;
        boolean result1 = sol.wordBreak(s1, dict1);
        System.out.println("  Expected: " + expected1);
        System.out.println("  DP:       " + result1 + (result1 == expected1 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s1, dict1));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s1, dict1));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s1, dict1));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s1, dict1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: s = \"applepenapple\", wordDict = [\"apple\",\"pen\"]");
        String s2 = "applepenapple";
        java.util.List<String> dict2 = java.util.Arrays.asList("apple", "pen");
        boolean expected2 = true;
        boolean result2 = sol.wordBreak(s2, dict2);
        System.out.println("  Expected: " + expected2);
        System.out.println("  DP:       " + result2 + (result2 == expected2 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s2, dict2));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s2, dict2));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s2, dict2));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s2, dict2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: s = \"catsandog\", wordDict = [\"cats\",\"dog\",\"sand\",\"and\",\"cat\"]");
        String s3 = "catsandog";
        java.util.List<String> dict3 = java.util.Arrays.asList("cats", "dog", "sand", "and", "cat");
        boolean expected3 = false;
        boolean result3 = sol.wordBreak(s3, dict3);
        System.out.println("  Expected: " + expected3);
        System.out.println("  DP:       " + result3 + (result3 == expected3 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s3, dict3));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s3, dict3));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s3, dict3));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s3, dict3));
        
        // Test 4: Single character
        System.out.println("\nTest 4: s = \"a\", wordDict = [\"a\"]");
        String s4 = "a";
        java.util.List<String> dict4 = java.util.Arrays.asList("a");
        boolean expected4 = true;
        boolean result4 = sol.wordBreak(s4, dict4);
        System.out.println("  Expected: " + expected4);
        System.out.println("  DP:       " + result4 + (result4 == expected4 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s4, dict4));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s4, dict4));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s4, dict4));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s4, dict4));
        
        // Test 5: Repeated words
        System.out.println("\nTest 5: s = \"aaaaaaa\", wordDict = [\"aaaa\",\"aaa\"]");
        String s5 = "aaaaaaa";
        java.util.List<String> dict5 = java.util.Arrays.asList("aaaa", "aaa");
        boolean expected5 = true;
        boolean result5 = sol.wordBreak(s5, dict5);
        System.out.println("  Expected: " + expected5);
        System.out.println("  DP:       " + result5 + (result5 == expected5 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s5, dict5));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s5, dict5));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s5, dict5));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s5, dict5));
        
        // Test 6: Not possible
        System.out.println("\nTest 6: s = \"catsandog\", wordDict = [\"cats\",\"dog\",\"sand\",\"and\",\"cat\"]");
        String s6 = "catsandog";
        java.util.List<String> dict6 = java.util.Arrays.asList("cats", "dog", "sand", "and", "cat");
        boolean expected6 = false;
        boolean result6 = sol.wordBreak(s6, dict6);
        System.out.println("  Expected: " + expected6);
        System.out.println("  DP:       " + result6 + (result6 == expected6 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s6, dict6));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s6, dict6));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s6, dict6));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s6, dict6));
        
        // Test 7: Long string with many words
        System.out.println("\nTest 7: Long string");
        String s7 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        java.util.List<String> dict7 = java.util.Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa");
        boolean expected7 = false;
        boolean result7 = sol.wordBreak(s7, dict7);
        System.out.println("  Expected: " + expected7);
        System.out.println("  DP:       " + result7 + (result7 == expected7 ? " ✓" : " ✗"));
        System.out.println("  Optimized:" + sol.wordBreakOptimized(s7, dict7));
        System.out.println("  BFS:      " + sol.wordBreakBFS(s7, dict7));
        System.out.println("  Memo:     " + sol.wordBreakMemo(s7, dict7));
        System.out.println("  Trie:     " + sol.wordBreakTrie(s7, dict7));
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification of all approaches");
        String[] testStrings = {
            "leetcode",
            "applepenapple",
            "catsandog",
            "a",
            "aaaaaaa",
            "catsandog",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"
        };
        java.util.List<String>[] testDicts = new java.util.List[] {
            java.util.Arrays.asList("leet", "code"),
            java.util.Arrays.asList("apple", "pen"),
            java.util.Arrays.asList("cats", "dog", "sand", "and", "cat"),
            java.util.Arrays.asList("a"),
            java.util.Arrays.asList("aaaa", "aaa"),
            java.util.Arrays.asList("cats", "dog", "sand", "and", "cat"),
            java.util.Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")
        };
        boolean[] expected = {true, true, false, true, true, false, false};
        
        boolean allMatch = true;
        for (int i = 0; i < testStrings.length; i++) {
            boolean r1 = sol.wordBreak(testStrings[i], testDicts[i]);
            boolean r2 = sol.wordBreakOptimized(testStrings[i], testDicts[i]);
            boolean r3 = sol.wordBreakBFS(testStrings[i], testDicts[i]);
            boolean r4 = sol.wordBreakMemo(testStrings[i], testDicts[i]);
            boolean r5 = sol.wordBreakTrie(testStrings[i], testDicts[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i] && r5 == expected[i]);
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (DP:" + r1 + " Opt:" + r2 + " BFS:" + r3 + " Memo:" + r4 + " Trie:" + r5 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}