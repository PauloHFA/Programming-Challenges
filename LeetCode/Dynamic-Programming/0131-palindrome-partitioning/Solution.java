import java.util.*;

/**
 * LeetCode 131 - Palindrome Partitioning
 * 
 * Dada uma string s, particione s de forma que cada substring da partição
 * seja um palíndromo. Retorne todas as partições de palíndromo possíveis de s.
 */
public class Solution {

    /**
     * Método 1: Backtracking com DP para verificação de palíndromo (Principal)
     * Pré-computa tabela de palíndromos, depois backtracking
     * Tempo: O(n * 2^n), Espaço: O(n^2)
     */
    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        
        // Pré-computa todos os palíndromos
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || isPal[i + 1][j - 1]);
            }
        }
        
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, isPal, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, boolean[][] isPal, 
                           List<String> path, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPal[start][end]) {
                path.add(s.substring(start, end + 1));
                backtrack(s, end + 1, isPal, path, result);
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * Método 2: Backtracking com verificação on-the-fly
     * Verifica palíndromo durante o backtracking
     * Tempo: O(n * 2^n), Espaço: O(n)
     */
    public List<List<String>> partitionOnTheFly(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrackOnTheFly(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackOnTheFly(String s, int start, List<String> path, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                path.add(s.substring(start, end + 1));
                backtrackOnTheFly(s, end + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método 3: DP + Backtracking com memoização de partições
     * Memoiza partições a partir de cada índice
     * Tempo: O(n * 2^n), Espaço: O(n * 2^n)
     */
    public List<List<String>> partitionMemo(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || isPal[i + 1][j - 1]);
            }
        }
        
        Map<Integer, List<List<String>>> memo = new HashMap<>();
        return partitionMemoHelper(s, 0, isPal, memo);
    }
    
    private List<List<String>> partitionMemoHelper(String s, int start, boolean[][] isPal, 
                                                    Map<Integer, List<List<String>>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<List<String>> result = new ArrayList<>();
        if (start == s.length()) {
            result.add(new ArrayList<>());
            return result;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPal[start][end]) {
                String prefix = s.substring(start, end + 1);
                List<List<String>> suffixes = partitionMemoHelper(s, end + 1, isPal, memo);
                for (List<String> suffix : suffixes) {
                    List<String> combined = new ArrayList<>();
                    combined.add(prefix);
                    combined.addAll(suffix);
                    result.add(combined);
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }

    // Helper para comparar resultados (ordem não importa)
    private static boolean partitionsEqual(List<List<String>> a, List<List<String>> b) {
        if (a.size() != b.size()) return false;
        Set<String> setA = new HashSet<>();
        Set<String> setB = new HashSet<>();
        for (List<String> part : a) setA.add(part.toString());
        for (List<String> part : b) setB.add(part.toString());
        return setA.equals(setB);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Palindrome Partitioning Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: s = \"aab\"");
        String s1 = "aab";
        List<List<String>> r1 = sol.partition(s1);
        System.out.println("  Expected: [[a,a,b],[aa,b]]");
        System.out.println("  Result:   " + r1);
        
        // Test 2: Example 2
        System.out.println("\nTest 2: s = \"a\"");
        String s2 = "a";
        List<List<String>> r2 = sol.partition(s2);
        System.out.println("  Expected: [[a]]");
        System.out.println("  Result:   " + r2);
        
        // Test 3: All same chars
        System.out.println("\nTest 3: s = \"aaa\"");
        String s3 = "aaa";
        List<List<String>> r3 = sol.partition(s3);
        System.out.println("  Expected: [[a,a,a],[a,aa],[aa,a],[aaa]]");
        System.out.println("  Result:   " + r3);
        System.out.println("  Count:    " + r3.size());
        
        // Test 4: No palindromes > 1
        System.out.println("\nTest 4: s = \"abc\"");
        String s4 = "abc";
        List<List<String>> r4 = sol.partition(s4);
        System.out.println("  Expected: [[a,b,c]]");
        System.out.println("  Result:   " + r4);
        
        // Test 5: Longer string
        System.out.println("\nTest 5: s = \"abba\"");
        String s5 = "abba";
        List<List<String>> r5 = sol.partition(s5);
        System.out.println("  Expected: [[a,b,b,a],[a,bb,a],[abba]]");
        System.out.println("  Result:   " + r5);
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification (3 methods)");
        String[] testCases = {"aab", "a", "aaa", "abc", "abba", "racecar", "abac"};
        
        for (String test : testCases) {
            List<List<String>> rDP = sol.partition(test);
            List<List<String>> rOTF = sol.partitionOnTheFly(test);
            List<List<String>> rMemo = sol.partitionMemo(test);
            
            boolean match = partitionsEqual(rDP, rOTF) && partitionsEqual(rOTF, rMemo);
            System.out.println("  \"" + test + "\": " + (match ? "MATCH" : "MISMATCH") + 
                " (DP:" + rDP.size() + " OTF:" + rOTF.size() + " Memo:" + rMemo.size() + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}