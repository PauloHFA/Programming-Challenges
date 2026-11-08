import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    
    // Método principal: Backtracking com skip de duplicatas
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);  // Importante: ordena para agrupar duplicatas
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicatas: só usa o primeiro de uma sequência de valores iguais
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // Método 2: Iterativo (baseado no método de subconjuntos padrão)
    public List<List<Integer>> subsetsWithDupIterative(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        int start = 0, end = 0;
        
        for (int i = 0; i < nums.length; i++) {
            start = 0;
            // Se é duplicata, só adiciona aos subconjuntos criados na iteração anterior
            if (i > 0 && nums[i] == nums[i - 1]) {
                start = end;
            }
            end = result.size();
            
            for (int j = start; j < end; j++) {
                List<Integer> subset = new ArrayList<>(result.get(j));
                subset.add(nums[i]);
                result.add(subset);
            }
        }
        
        return result;
    }
    
    // Método 3: Bit manipulation com filtro (para verificação)
    public List<List<Integer>> subsetsWithDupBitmask(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        int total = 1 << n;
        
        for (int mask = 0; mask < total; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            // Verifica se já existe (remove duplicatas)
            if (!containsSubset(result, subset)) {
                result.add(subset);
            }
        }
        
        return result;
    }
    
    private boolean containsSubset(List<List<Integer>> list, List<Integer> subset) {
        for (List<Integer> existing : list) {
            if (existing.size() == subset.size()) {
                boolean match = true;
                for (int i = 0; i < subset.size(); i++) {
                    if (!existing.get(i).equals(subset.get(i))) {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
    }
    
    // Método 4: Usa Set para remover duplicatas (simples mas menos eficiente)
    public List<List<Integer>> subsetsWithDupSet(int[] nums) {
        Arrays.sort(nums);
        java.util.Set<List<Integer>> set = new java.util.LinkedHashSet<>();
        int n = nums.length;
        int total = 1 << n;
        
        for (int mask = 0; mask < total; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            set.add(subset);
        }
        
        return new ArrayList<>(set);
    }
    
    // Helper: compara duas listas de subconjuntos (ordem independente)
    private boolean subsetsEqual(List<List<Integer>> a, List<List<Integer>> b) {
        if (a.size() != b.size()) return false;
        
        // Normaliza: ordena cada subconjunto e depois a lista de subconjuntos
        List<List<Integer>> normA = normalize(a);
        List<List<Integer>> normB = normalize(b);
        
        return normA.equals(normB);
    }
    
    private List<List<Integer>> normalize(List<List<Integer>> list) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> subset : list) {
            List<Integer> sorted = new ArrayList<>(subset);
            sorted.sort(Integer::compareTo);
            result.add(sorted);
        }
        result.sort((a, b) -> {
            int minSize = Math.min(a.size(), b.size());
            for (int i = 0; i < minSize; i++) {
                int cmp = a.get(i).compareTo(b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });
        return result;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Subsets II Tests ===\n");
        
        // Test 1: Example 1
        test(sol, new int[]{1,2,2}, "Test 1: [1,2,2]");
        
        // Test 2: Example 2
        test(sol, new int[]{0}, "Test 2: [0]");
        
        // Test 3: All duplicates
        test(sol, new int[]{1,1,1}, "Test 3: [1,1,1]");
        
        // Test 4: No duplicates
        test(sol, new int[]{1,2,3}, "Test 4: [1,2,3]");
        
        // Test 5: Two pairs
        test(sol, new int[]{1,1,2,2}, "Test 5: [1,1,2,2]");
        
        // Test 6: Mixed
        test(sol, new int[]{4,4,4,1,4}, "Test 6: [4,4,4,1,4]");
        
        // Test 7: Empty (not in constraints but good to check)
        // Constraints say 1 <= nums.length
        
        // Test 8: Single duplicate
        test(sol, new int[]{1,1}, "Test 8: [1,1]");
        
        // Test 9: Three distinct, one duplicate
        test(sol, new int[]{1,2,3,3}, "Test 9: [1,2,3,3]");
        
        // Test 10: Larger
        test(sol, new int[]{1,2,2,3,3,3}, "Test 10: [1,2,2,3,3,3]");
    }
    
    private static void test(Solution sol, int[] nums, String name) {
        List<List<Integer>> result1 = sol.subsetsWithDup(nums);
        List<List<Integer>> result2 = sol.subsetsWithDupIterative(nums);
        List<List<Integer>> result3 = (nums.length <= 10) ? sol.subsetsWithDupBitmask(nums) : result1;
        List<List<Integer>> result4 = (nums.length <= 10) ? sol.subsetsWithDupSet(nums) : result1;
        
        boolean match12 = sol.subsetsEqual(result1, result2);
        boolean match13 = sol.subsetsEqual(result1, result3);
        boolean match14 = sol.subsetsEqual(result1, result4);
        boolean allMatch = match12 && match13 && match14;
        
        System.out.println(name);
        System.out.println("  Input: " + Arrays.toString(nums));
        System.out.println("  Backtrack: " + result1.size() + " subsets");
        System.out.println("  Iterative: " + result2.size() + " subsets " + (match12 ? "✓" : "✗"));
        System.out.println("  Bitmask:   " + result3.size() + " subsets " + (match13 ? "✓" : "✗"));
        System.out.println("  Set:       " + result4.size() + " subsets " + (match14 ? "✓" : "✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        
        // Print subsets for small cases
        if (nums.length <= 4) {
            System.out.println("  Subsets: " + result1);
        }
        System.out.println();
    }
}