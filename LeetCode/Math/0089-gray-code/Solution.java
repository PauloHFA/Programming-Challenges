import java.util.ArrayList;
import java.util.List;

public class Solution {
    
    // Método principal: Fórmula matemática (i ^ (i >> 1))
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        int total = 1 << n;  // 2^n
        
        for (int i = 0; i < total; i++) {
            result.add(i ^ (i >> 1));
        }
        
        return result;
    }
    
    // Método 2: Reflexão recursiva (construção clássica)
    public List<Integer> grayCodeReflection(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        
        for (int i = 0; i < n; i++) {
            int size = result.size();
            int prefix = 1 << i;  // 2^i
            
            // Adiciona em ordem reversa com prefixo
            for (int j = size - 1; j >= 0; j--) {
                result.add(prefix | result.get(j));
            }
        }
        
        return result;
    }
    
    // Método 3: Backtracking (gera sequência válida)
    public List<Integer> grayCodeBacktrack(int n) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[1 << n];
        
        backtrack(0, n, visited, result);
        return result;
    }
    
    private boolean backtrack(int current, int n, boolean[] visited, List<Integer> result) {
        if (result.size() == (1 << n)) {
            return true;
        }
        
        visited[current] = true;
        result.add(current);
        
        // Tenta todos os vizinhos (diferem em 1 bit)
        for (int i = 0; i < n; i++) {
            int next = current ^ (1 << i);
            if (!visited[next]) {
                if (backtrack(next, n, visited, result)) {
                    return true;
                }
            }
        }
        
        // Backtrack
        visited[current] = false;
        result.remove(result.size() - 1);
        return false;
    }
    
    // Método 4: Iterativo com bit manipulation (variante da fórmula)
    public List<Integer> grayCodeIterative(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        
        for (int i = 1; i < (1 << n); i++) {
            // Gray code: i ^ (i >> 1)
            // Ou: previous ^ (lowest set bit of i)
            int prev = result.get(i - 1);
            int lsb = i & -i;  // Lowest set bit
            result.add(prev ^ lsb);
        }
        
        return result;
    }
    
    // Verifica se uma sequência é Gray code válida
    private boolean isValidGrayCode(List<Integer> code, int n) {
        if (code.size() != (1 << n)) return false;
        
        boolean[] seen = new boolean[1 << n];
        for (int i = 0; i < code.size(); i++) {
            int val = code.get(i);
            if (val < 0 || val >= (1 << n) || seen[val]) return false;
            seen[val] = true;
            
            if (i > 0) {
                int diff = code.get(i) ^ code.get(i - 1);
                // Deve diferir em exatamente 1 bit
                if (diff == 0 || (diff & (diff - 1)) != 0) return false;
            }
        }
        
        // Verifica se primeiro e último também diferem em 1 bit (circular)
        int diff = code.get(0) ^ code.get(code.size() - 1);
        if (diff == 0 || (diff & (diff - 1)) != 0) return false;
        
        return true;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Gray Code Tests ===\n");
        
        // Test 1: n = 2
        test(sol, 2, "Test 1: n=2");
        
        // Test 2: n = 1
        test(sol, 1, "Test 2: n=1");
        
        // Test 3: n = 3
        test(sol, 3, "Test 3: n=3");
        
        // Test 4: n = 4
        test(sol, 4, "Test 4: n=4");
        
        // Test 5: n = 0 (edge case)
        test(sol, 0, "Test 5: n=0");
        
        // Test 6: n = 5
        test(sol, 5, "Test 6: n=5");
    }
    
    private static void test(Solution sol, int n, String name) {
        List<Integer> result1 = sol.grayCode(n);
        List<Integer> result2 = sol.grayCodeReflection(n);
        List<Integer> result3 = (n <= 4) ? sol.grayCodeBacktrack(n) : result1;
        List<Integer> result4 = sol.grayCodeIterative(n);
        
        boolean valid1 = sol.isValidGrayCode(result1, n);
        boolean valid2 = sol.isValidGrayCode(result2, n);
        boolean valid3 = sol.isValidGrayCode(result3, n);
        boolean valid4 = sol.isValidGrayCode(result4, n);
        
        boolean allMatch = result1.equals(result2) && result2.equals(result3) && result3.equals(result4);
        
        System.out.println(name);
        System.out.println("  n = " + n + ", size = " + (1 << n));
        System.out.println("  Formula:      " + result1 + (valid1 ? " ✓" : " ✗"));
        System.out.println("  Reflection:   " + result2 + (valid2 ? " ✓" : " ✗"));
        System.out.println("  Backtrack:    " + result3 + (valid3 ? " ✓" : " ✗"));
        System.out.println("  Iterative:    " + result4 + (valid4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}