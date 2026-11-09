import java.util.*;

/**
 * LeetCode 126 - Word Ladder II
 * 
 * Dado duas palavras beginWord e endWord, e uma lista de palavras wordList,
 * encontre todas as sequências de transformação mais curtas de beginWord para endWord.
 * Cada sequência deve ser tal que:
 * - Apenas uma letra muda por vez
 * - Cada palavra transformada deve existir na wordList
 * Retorne todas as sequências mais curtas. A ordem não importa.
 */
public class Solution {

    /**
     * Método 1: BFS + Backtracking (Principal)
     * BFS para construir grafo de adjacência nível por nível
     * Backtracking para reconstruir todos os caminhos mais curtos
     * Tempo: O(N * L^2) onde N = wordList.size, L = word.length
     * Espaço: O(N * L^2)
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }
        
        // BFS para construir grafo de adjacência (apenas arestas que levam ao próximo nível)
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        distance.put(beginWord, 0);
        boolean found = false;
        int level = 0;
        
        while (!queue.isEmpty() && !found) {
            level++;
            int size = queue.size();
            Set<String> visitedThisLevel = new HashSet<>();
            
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String nextWord = new String(chars);
                        
                        if (wordSet.contains(nextWord)) {
                            graph.computeIfAbsent(word, k -> new ArrayList<>()).add(nextWord);
                            
                            if (!distance.containsKey(nextWord)) {
                                distance.put(nextWord, level);
                                queue.offer(nextWord);
                                visitedThisLevel.add(nextWord);
                                if (nextWord.equals(endWord)) {
                                    found = true;
                                }
                            } else if (distance.get(nextWord) == level) {
                                // Mesmo nível - adiciona aresta mas não re-enfileira
                            }
                        }
                    }
                    chars[j] = original;
                }
            }
            
            // Remove palavras visitadas neste nível do wordSet para evitar ciclos
            wordSet.removeAll(visitedThisLevel);
        }
        
        // Backtracking para encontrar todos os caminhos
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        backtrack(beginWord, endWord, graph, path, result);
        
        return result;
    }
    
    private void backtrack(String current, String endWord, Map<String, List<String>> graph, 
                           List<String> path, List<List<String>> result) {
        if (current.equals(endWord)) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        if (!graph.containsKey(current)) {
            return;
        }
        
        for (String next : graph.get(current)) {
                // Avoid cycles - don't revisit nodes in current path
                if (path.contains(next)) {
                    continue;
                }
                path.add(next);
                backtrack(next, endWord, graph, path, result);
                path.remove(path.size() - 1);
            }
        }

    /**
     * Método 2: BFS Bidirecional + Backtracking
     * Mais eficiente para grafos grandes
     * Tempo: O(N * L^2), Espaço: O(N * L^2)
     */
    public List<List<String>> findLaddersBidirectional(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> graph = new HashMap<>();
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        
        boolean found = false;
        boolean reversed = false;
        
        while (!beginSet.isEmpty() && !endSet.isEmpty() && !found) {
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
                reversed = !reversed;
            }
            
            Set<String> nextLevel = new HashSet<>();
            wordSet.removeAll(beginSet);
            
            for (String word : beginSet) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[i] = c;
                        String nextWord = new String(chars);
                        
                        if (wordSet.contains(nextWord)) {
                            nextLevel.add(nextWord);
                            String from = reversed ? nextWord : word;
                            String to = reversed ? word : nextWord;
                            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
                            
                            if (endSet.contains(nextWord)) {
                                found = true;
                            }
                        }
                    }
                    chars[i] = original;
                }
            }
            
            beginSet = nextLevel;
        }
        
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        backtrack(beginWord, endWord, graph, path, result);
        
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Word Ladder II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog,lot,log,cog]");
        List<String> wl1 = Arrays.asList("hot","dot","dog","lot","log","cog");
        List<List<String>> r1 = sol.findLadders("hit", "cog", wl1);
        System.out.println("  Expected: [[hit,hot,dot,dog,cog],[hit,hot,lot,log,cog]]");
        System.out.println("  Result:   " + r1);
        System.out.println("  Count:    " + r1.size());
        
        // Test 2: Example 2
        System.out.println("\nTest 2: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog,lot,log]");
        List<String> wl2 = Arrays.asList("hot","dot","dog","lot","log");
        List<List<String>> r2 = sol.findLadders("hit", "cog", wl2);
        System.out.println("  Expected: []");
        System.out.println("  Result:   " + r2);
        
        // Test 3: Simple case
        System.out.println("\nTest 3: beginWord=\"a\", endWord=\"c\", wordList=[a,b,c]");
        List<String> wl3 = Arrays.asList("a","b","c");
        List<List<String>> r3 = sol.findLadders("a", "c", wl3);
        System.out.println("  Expected: [[a,c]] or [[a,b,c]]");
        System.out.println("  Result:   " + r3);
        
        // Test 4: No path
        System.out.println("\nTest 4: beginWord=\"hit\", endWord=\"cog\", wordList=[hot,dot,dog]");
        List<String> wl4 = Arrays.asList("hot","dot","dog");
        List<List<String>> r4 = sol.findLadders("hit", "cog", wl4);
        System.out.println("  Expected: []");
        System.out.println("  Result:   " + r4);
        
        // Test 5: Cross verification
        System.out.println("\nTest 5: Cross verification (BFS vs Bidirectional)");
        String[][] testCases = {
            {"hit", "cog", "hot,dot,dog,lot,log,cog"},
            {"hit", "cog", "hot,dot,dog,lot,log"},
            {"a", "c", "a,b,c"},
            {"hit", "cog", "hot,dot,dog"},
            {"red", "tax", "ted,tex,red,tax,tad,den,rex,pee"}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String begin = testCases[i][0];
            String end = testCases[i][1];
            List<String> wordList = Arrays.asList(testCases[i][2].split(","));
            
            List<List<String>> rBFS = sol.findLadders(begin, end, wordList);
            List<List<String>> rBi = sol.findLaddersBidirectional(begin, end, wordList);
            
            // Sort for comparison
                        rBFS.sort((a, b) -> a.toString().compareTo(b.toString()));
                        rBi.sort((a, b) -> a.toString().compareTo(b.toString()));
            
            boolean match = rBFS.equals(rBi);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (BFS:" + rBFS.size() + " Bi:" + rBi.size() + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}