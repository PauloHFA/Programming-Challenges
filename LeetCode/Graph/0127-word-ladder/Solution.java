import java.util.*;

/**
 * LeetCode 127 - Word Ladder
 * 
 * Dado duas palavras beginWord e endWord, e uma lista de palavras wordList,
 * retorne o número de palavras na sequência de transformação mais curta de
 * beginWord para endWord, ou 0 se não existir tal sequência.
 * Cada palavra transformada deve existir na wordList.
 */
public class Solution {

    /**
     * Método 1: BFS Padrão (Principal)
     * BFS nível por nível, cada nível = uma transformação
     * Tempo: O(N * L^2), Espaço: O(N * L)
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        wordSet.remove(beginWord); // Evita revisitar
        
        int level = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                
                if (word.equals(endWord)) {
                    return level;
                }
                
                char[] chars = word.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String nextWord = new String(chars);
                        
                        if (wordSet.contains(nextWord)) {
                            queue.offer(nextWord);
                            wordSet.remove(nextWord);
                        }
                    }
                    chars[j] = original;
                }
            }
            level++;
        }
        
        return 0;
    }

    /**
     * Método 2: BFS Bidirecional
     * BFS simultâneo de beginWord e endWord
     * Tempo: O(N * L^2), Espaço: O(N * L) - mais rápido na prática
     */
    public int ladderLengthBidirectional(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
            // Edge case: beginWord == endWord
            if (beginWord.equals(endWord)) {
                return 1;
            }
        
            Set<String> beginSet = new HashSet<>();
            Set<String> endSet = new HashSet<>();
            beginSet.add(beginWord);
            endSet.add(endWord);
        
            int level = 1;
        
            while (!beginSet.isEmpty() && !endSet.isEmpty()) {
                // Sempre expande o conjunto menor
                if (beginSet.size() > endSet.size()) {
                    Set<String> temp = beginSet;
                    beginSet = endSet;
                    endSet = temp;
                }
            
                Set<String> nextLevel = new HashSet<>();
            
                for (String word : beginSet) {
                    char[] chars = word.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char original = chars[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == original) continue;
                            chars[i] = c;
                            String nextWord = new String(chars);
                        
                            if (endSet.contains(nextWord)) {
                                return level + 1;
                        }
                        
                            if (wordSet.contains(nextWord)) {
                                nextLevel.add(nextWord);
                                wordSet.remove(nextWord);
                            }
                        }
                        chars[i] = original;
                    }
                }
            
                beginSet = nextLevel;
                level++;
            }
        
            return 0;
        }

    /**
     * Método 3: BFS com Padrões (Wildcard)
     * Pré-processa wordList em padrões genéricos
     * Tempo: O(N * L^2), Espaço: O(N * L^2)
     */
    public int ladderLengthPattern(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
        // Build pattern map: *ot -> [hot, dot, lot]
        Map<String, List<String>> patternMap = new HashMap<>();
        for (String word : wordSet) {
            for (int i = 0; i < word.length(); i++) {
                String pattern = word.substring(0, i) + "*" + word.substring(i + 1);
                patternMap.computeIfAbsent(pattern, k -> new ArrayList<>()).add(word);
            }
        }
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        
        int level = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                
                if (word.equals(endWord)) {
                    return level;
                }
                
                for (int j = 0; j < word.length(); j++) {
                    String pattern = word.substring(0, j) + "*" + word.substring(j + 1);
                    if (patternMap.containsKey(pattern)) {
                        for (String nextWord : patternMap.get(pattern)) {
                            if (!visited.contains(nextWord)) {
                                visited.add(nextWord);
                                queue.offer(nextWord);
                            }
                        }
                    }
                }
            }
            level++;
        }
        
        return 0;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Word Ladder Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog,lot,log,cog]");
        List<String> wl1 = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println("  Expected: 5 (hit->hot->dot->dog->cog)");
        System.out.println("  BFS:      " + sol.ladderLength("hit", "cog", wl1));
        System.out.println("  BiBFS:    " + sol.ladderLengthBidirectional("hit", "cog", wl1));
        System.out.println("  Pattern:  " + sol.ladderLengthPattern("hit", "cog", wl1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog,lot,log]");
        List<String> wl2 = Arrays.asList("hot","dot","dog","lot","log");
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.ladderLength("hit", "cog", wl2));
        
        // Test 3: Direct transformation
        System.out.println("\nTest 3: beginWord=\"a\", endWord=\"c\", wordList=[a,b,c]");
        List<String> wl3 = Arrays.asList("a","b","c");
        System.out.println("  Expected: 2 (a->c)");
        System.out.println("  Result:   " + sol.ladderLength("a", "c", wl3));
        
        // Test 4: Same word
        System.out.println("\nTest 4: beginWord=\"hit\", endWord=\"hit\", wordList=[hit]");
        List<String> wl4 = Arrays.asList("hit");
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.ladderLength("hit", "hit", wl4));
        
        // Test 5: No path
        System.out.println("\nTest 5: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog]");
        List<String> wl5 = Arrays.asList("hot","dot","dog");
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.ladderLength("hit", "cog", wl5));
        
        // Test 6: Longer path
        System.out.println("\nTest 6: beginWord=\"red\", endWord=\"tax\", wordList=[ted,tex,red,tax,tad,den,rex,pee]");
        List<String> wl6 = Arrays.asList("ted","tex","red","tax","tad","den","rex","pee");
        System.out.println("  Expected: 4 (red->ted->tad->tax or red->rex->tex->tax)");
        System.out.println("  Result:   " + sol.ladderLength("red", "tax", wl6));
        
        // Test 7: Cross verification
        System.out.println("\nTest 7: Cross verification");
        String[][] testCases = {
            {"hit", "cog", "hot,dot,dog,lot,log,cog"},
            {"hit", "cog", "hot,dot,dog,lot,log"},
            {"a", "c", "a,b,c"},
            {"hit", "hit", "hit"},
            {"hit", "cog", "hot,dot,dog"},
            {"red", "tax", "ted,tex,red,tax,tad,den,rex,pee"},
            {"hot", "dog", "hot,dog,dot"}
        };
        int[] expected = {5, 0, 2, 1, 0, 4, 3};
        
        for (int i = 0; i < testCases.length; i++) {
            String begin = testCases[i][0];
            String end = testCases[i][1];
            List<String> wordList = Arrays.asList(testCases[i][2].split(","));
            
            int r1 = sol.ladderLength(begin, end, wordList);
            int r2 = sol.ladderLengthBidirectional(begin, end, wordList);
            int r3 = sol.ladderLengthPattern(begin, end, wordList);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (BFS:" + r1 + " Bi:" + r2 + " Pat:" + r3 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}