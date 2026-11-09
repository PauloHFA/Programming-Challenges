/**
 * LeetCode 140 - Word Break II
 * 
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence
 * where each word is a valid dictionary word. Return all such possible sentences in any order.
 * 
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 * 
 * Example 1:
 * Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * Output: ["cats and dog","cat sand dog"]
 * 
 * Example 2:
 * Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * 
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: []
 * 
 * Constraints:
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 */
public class Solution {
    
    /**
     * Approach 1: Backtracking with Memoization (DP + DFS)
     * 
     * First use DP to check if a solution exists (pruning).
     * Then use DFS with memoization to build all sentences.
     * memo[i] = list of sentences that can be formed from s[i:]
     * 
     * Time Complexity: O(n^2 * 2^n) in worst case (exponential due to output size)
     * Space Complexity: O(n^2) for memo + O(n) recursion stack
     */
    public java.util.List<String> wordBreak(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int n = s.length();
        
        // First check if solution exists using DP
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
        if (!dp[n]) return new java.util.ArrayList<>();
        
        // Memoization: index -> list of sentences from that index
        java.util.Map<Integer, java.util.List<String>> memo = new java.util.HashMap<>();
        return backtrack(s, 0, wordSet, memo);
    }
    
    private java.util.List<String> backtrack(String s, int start, java.util.Set<String> wordSet, 
                                              java.util.Map<Integer, java.util.List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        java.util.List<String> result = new java.util.ArrayList<>();
        if (start == s.length()) {
            result.add(""); // Empty string marks valid end
            return result;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (wordSet.contains(word)) {
                java.util.List<String> subSentences = backtrack(s, end, wordSet, memo);
                for (String sub : subSentences) {
                    if (sub.isEmpty()) {
                        result.add(word);
                    } else {
                        result.add(word + " " + sub);
                    }
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
    
    /**
     * Approach 2: Pure Backtracking (without DP pruning)
     * 
     * Simpler but may explore dead ends.
     * 
     * Time Complexity: O(n^2 * 2^n) worst case
     * Space Complexity: O(n) recursion stack + output
     */
    public java.util.List<String> wordBreakPureBacktrack(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        java.util.List<String> result = new java.util.ArrayList<>();
        backtrackPure(s, 0, wordSet, new StringBuilder(), result);
        return result;
    }
    
    private void backtrackPure(String s, int start, java.util.Set<String> wordSet, 
                                StringBuilder path, java.util.List<String> result) {
        if (start == s.length()) {
            result.add(path.toString());
            return;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (wordSet.contains(word)) {
                int len = path.length();
                if (len > 0) path.append(" ");
                path.append(word);
                backtrackPure(s, end, wordSet, path, result);
                path.setLength(len);
            }
        }
    }
    
    /**
     * Approach 3: DP Building Sentences Bottom-Up
     * 
     * dp[i] = list of sentences for s[0..i-1]
     * Build from left to right.
     * 
     * Time Complexity: O(n^2 * 2^n) worst case
     * Space Complexity: O(n^2 * 2^n) for storing all sentences
     */
    public java.util.List<String> wordBreakDP(String s, java.util.List<String> wordDict) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordDict);
        int n = s.length();
        
        // dp[i] = list of sentences for s[0..i-1]
        java.util.List<String>[] dp = new java.util.List[n + 1];
        dp[0] = new java.util.ArrayList<>();
        dp[0].add(""); // Empty sentence for empty string
        
        for (int i = 1; i <= n; i++) {
            dp[i] = new java.util.ArrayList<>();
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (wordSet.contains(word) && !dp[j].isEmpty()) {
                    for (String sentence : dp[j]) {
                        if (sentence.isEmpty()) {
                            dp[i].add(word);
                        } else {
                            dp[i].add(sentence + " " + word);
                        }
                    }
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 4: Trie + Backtracking with Memoization
     * 
     * Use Trie for faster prefix matching.
     * 
     * Time Complexity: O(n * maxLen * 2^n) worst case
     * Space Complexity: O(n^2) for memo + Trie space
     */
    public java.util.List<String> wordBreakTrie(String s, java.util.List<String> wordDict) {
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
            node.word = word;
        }
        
        java.util.Map<Integer, java.util.List<String>> memo = new java.util.HashMap<>();
        return backtrackTrie(s, 0, root, memo);
    }
    
    private java.util.List<String> backtrackTrie(String s, int start, TrieNode root, 
                                                  java.util.Map<Integer, java.util.List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        java.util.List<String> result = new java.util.ArrayList<>();
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        TrieNode node = root;
        for (int end = start; end < s.length(); end++) {
            char c = s.charAt(end);
            if (node.children[c - 'a'] == null) break;
            node = node.children[c - 'a'];
            if (node.isWord) {
                java.util.List<String> subSentences = backtrackTrie(s, end + 1, root, memo);
                for (String sub : subSentences) {
                    if (sub.isEmpty()) {
                        result.add(node.word);
                    } else {
                        result.add(node.word + " " + sub);
                    }
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
    
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;
        String word = null;
    }
    
    // Helper to sort and compare lists
    private static boolean listsEqual(java.util.List<String> a, java.util.List<String> b) {
        if (a.size() != b.size()) return false;
        java.util.List<String> sortedA = new java.util.ArrayList<>(a);
        java.util.List<String> sortedB = new java.util.ArrayList<>(b);
        java.util.Collections.sort(sortedA);
        java.util.Collections.sort(sortedB);
        return sortedA.equals(sortedB);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Word Break II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: s = \"catsanddog\", wordDict = [\"cat\",\"cats\",\"and\",\"sand\",\"dog\"]");
        String s1 = "catsanddog";
        java.util.List<String> dict1 = java.util.Arrays.asList("cat", "cats", "and", "sand", "dog");
        java.util.List<String> expected1 = java.util.Arrays.asList("cats and dog", "cat sand dog");
        java.util.List<String> result1 = sol.wordBreak(s1, dict1);
        System.out.println("  Expected: " + expected1);
        System.out.println("  Result:   " + result1);
        System.out.println("  Memoized: " + (listsEqual(result1, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result1b = sol.wordBreakPureBacktrack(s1, dict1);
        System.out.println("  Pure BT:  " + (listsEqual(result1b, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result1c = sol.wordBreakDP(s1, dict1);
        System.out.println("  DP:       " + (listsEqual(result1c, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result1d = sol.wordBreakTrie(s1, dict1);
        System.out.println("  Trie:     " + (listsEqual(result1d, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: s = \"pineapplepenapple\", wordDict = [\"apple\",\"pen\",\"applepen\",\"pine\",\"pineapple\"]");
        String s2 = "pineapplepenapple";
        java.util.List<String> dict2 = java.util.Arrays.asList("apple", "pen", "applepen", "pine", "pineapple");
        java.util.List<String> expected2 = java.util.Arrays.asList(
            "pine apple pen apple", "pineapple pen apple", "pine applepen apple"
        );
        java.util.List<String> result2 = sol.wordBreak(s2, dict2);
        System.out.println("  Expected: " + expected2);
        System.out.println("  Result:   " + result2);
        System.out.println("  Memoized: " + (listsEqual(result2, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result2b = sol.wordBreakPureBacktrack(s2, dict2);
        System.out.println("  Pure BT:  " + (listsEqual(result2b, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result2c = sol.wordBreakDP(s2, dict2);
        System.out.println("  DP:       " + (listsEqual(result2c, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result2d = sol.wordBreakTrie(s2, dict2);
        System.out.println("  Trie:     " + (listsEqual(result2d, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 3: Example 3 - No solution
        System.out.println("\nTest 3: s = \"catsandog\", wordDict = [\"cats\",\"dog\",\"sand\",\"and\",\"cat\"]");
        String s3 = "catsandog";
        java.util.List<String> dict3 = java.util.Arrays.asList("cats", "dog", "sand", "and", "cat");
        java.util.List<String> expected3 = new java.util.ArrayList<>();
        java.util.List<String> result3 = sol.wordBreak(s3, dict3);
        System.out.println("  Expected: " + expected3);
        System.out.println("  Result:   " + result3);
        System.out.println("  Memoized: " + (listsEqual(result3, expected3) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result3b = sol.wordBreakPureBacktrack(s3, dict3);
        System.out.println("  Pure BT:  " + (listsEqual(result3b, expected3) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result3c = sol.wordBreakDP(s3, dict3);
        System.out.println("  DP:       " + (listsEqual(result3c, expected3) ? "PASS ✓" : "FAIL ✗"));
        
        java.util.List<String> result3d = sol.wordBreakTrie(s3, dict3);
        System.out.println("  Trie:     " + (listsEqual(result3d, expected3) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 4: Single word
        System.out.println("\nTest 4: s = \"leetcode\", wordDict = [\"leet\",\"code\"]");
        String s4 = "leetcode";
        java.util.List<String> dict4 = java.util.Arrays.asList("leet", "code");
        java.util.List<String> expected4 = java.util.Arrays.asList("leet code");
        java.util.List<String> result4 = sol.wordBreak(s4, dict4);
        System.out.println("  Expected: " + expected4);
        System.out.println("  Result:   " + result4);
        System.out.println("  Memoized: " + (listsEqual(result4, expected4) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 5: Single character
        System.out.println("\nTest 5: s = \"a\", wordDict = [\"a\"]");
        String s5 = "a";
        java.util.List<String> dict5 = java.util.Arrays.asList("a");
        java.util.List<String> expected5 = java.util.Arrays.asList("a");
        java.util.List<String> result5 = sol.wordBreak(s5, dict5);
        System.out.println("  Expected: " + expected5);
        System.out.println("  Result:   " + result5);
        System.out.println("  Memoized: " + (listsEqual(result5, expected5) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 6: Multiple same words
        System.out.println("\nTest 6: s = \"aaaa\", wordDict = [\"a\",\"aa\"]");
        String s6 = "aaaa";
        java.util.List<String> dict6 = java.util.Arrays.asList("a", "aa");
        java.util.List<String> expected6 = java.util.Arrays.asList(
            "a a a a", "a a aa", "a aa a", "aa a a", "aa aa"
        );
        java.util.List<String> result6 = sol.wordBreak(s6, dict6);
        System.out.println("  Expected: " + expected6);
        System.out.println("  Result:   " + result6);
        System.out.println("  Memoized: " + (listsEqual(result6, expected6) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 7: Cross verification
        System.out.println("\nTest 7: Cross verification of all approaches");
        String[] testStrings = {
            "catsanddog",
            "pineapplepenapple",
            "catsandog",
            "leetcode",
            "a",
            "aaaa"
        };
        java.util.List<String>[] testDicts = new java.util.List[] {
            java.util.Arrays.asList("cat", "cats", "and", "sand", "dog"),
            java.util.Arrays.asList("apple", "pen", "applepen", "pine", "pineapple"),
            java.util.Arrays.asList("cats", "dog", "sand", "and", "cat"),
            java.util.Arrays.asList("leet", "code"),
            java.util.Arrays.asList("a"),
            java.util.Arrays.asList("a", "aa")
        };
        java.util.List<String>[] expected = new java.util.List[] {
            java.util.Arrays.asList("cats and dog", "cat sand dog"),
            java.util.Arrays.asList("pine apple pen apple", "pineapple pen apple", "pine applepen apple"),
            new java.util.ArrayList<>(),
            java.util.Arrays.asList("leet code"),
            java.util.Arrays.asList("a"),
            java.util.Arrays.asList("a a a a", "a a aa", "a aa a", "aa a a", "aa aa")
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testStrings.length; i++) {
            java.util.List<String> r1 = sol.wordBreak(testStrings[i], testDicts[i]);
            java.util.List<String> r2 = sol.wordBreakPureBacktrack(testStrings[i], testDicts[i]);
            java.util.List<String> r3 = sol.wordBreakDP(testStrings[i], testDicts[i]);
            java.util.List<String> r4 = sol.wordBreakTrie(testStrings[i], testDicts[i]);
            
            boolean m1 = listsEqual(r1, expected[i]);
            boolean m2 = listsEqual(r2, expected[i]);
            boolean m3 = listsEqual(r3, expected[i]);
            boolean m4 = listsEqual(r4, expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Memo:" + m1 + " Pure:" + m2 + " DP:" + m3 + " Trie:" + m4 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}