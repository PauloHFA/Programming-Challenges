import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), 1, n, k);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> current, int start, int n, int k) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Pruning: if remaining elements < needed elements, return
        if (n - start + 1 < k - current.size()) {
            return;
        }
        
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(result, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }

    // Alternative: Iterative approach
    public List<List<Integer>> combineIterative(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        
        // Initialize with first combination
        for (int i = 1; i <= k; i++) {
            combination.add(i);
        }
        combination.add(n + 1); // Sentinel
        
        int i = 0;
        while (i < k) {
            result.add(new ArrayList<>(combination.subList(0, k)));
            i = 0;
            while (i < k && combination.get(i) + 1 == combination.get(i + 1)) {
                combination.set(i, i + 1);
                i++;
            }
            if (i < k) {
                combination.set(i, combination.get(i) + 1);
            }
        }
        
        return result;
    }

    // Alternative: Using bit manipulation (for small n)
    public List<List<Integer>> combineBitMask(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        int limit = 1 << n;
        
        for (int mask = 0; mask < limit; mask++) {
            if (Integer.bitCount(mask) == k) {
                List<Integer> combination = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        combination.add(i + 1);
                    }
                }
                result.add(combination);
            }
        }
        
        return result;
    }

    // Helper to compare results (order-independent)
    private boolean listsEqual(List<List<Integer>> a, List<List<Integer>> b) {
        if (a.size() != b.size()) return false;
            for (List<Integer> listA : a) {
                boolean found = false;
                for (List<Integer> listB : b) {
                    if (listA.equals(listB)) {
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
            return true;
        }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {4, 2},
            {1, 1},
            {4, 3},
            {5, 2},
            {3, 3},
            {4, 1},
            {4, 4},
        };
        
        System.out.println("=== Combinations Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i][0];
            int k = testCases[i][1];
            
            List<List<Integer>> result1 = solver.combine(n, k);
            List<List<Integer>> result2 = solver.combineIterative(n, k);
            List<List<Integer>> result3 = (n <= 10) ? solver.combineBitMask(n, k) : new ArrayList<>();
            
            boolean match12 = solver.listsEqual(result1, result2);
            boolean match13 = result3.isEmpty() || solver.listsEqual(result1, result3);
            
            System.out.println("Test " + (i + 1) + ": n = " + n + ", k = " + k);
            System.out.println("  Count: " + result1.size() + " (expected: " + binomialCoefficient(n, k) + ")");
            System.out.println("  Backtrack: " + result1);
            System.out.println("  Iterative: " + result2 + (match12 ? " ✓" : " ✗"));
            System.out.println("  BitMask:   " + (result3.isEmpty() ? "N/A" : result3) + (match13 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match12 && match13 ? "✓" : "✗"));
            System.out.println();
        }
    }
    
    private static int binomialCoefficient(int n, int k) {
        if (k > n - k) k = n - k;
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return (int) result;
    }
}