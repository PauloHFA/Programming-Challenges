import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
        result.add(new ArrayList<>(current));
        
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrack(result, current, nums, i + 1);
            current.remove(current.size() - 1);
        }
    }

    // Alternative: Iterative approach
    public List<List<Integer>> subsetsIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        for (int num : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                newSubset.add(num);
                result.add(newSubset);
            }
        }
        
        return result;
    }

    // Alternative: Bit manipulation
    public List<List<Integer>> subsetsBitMask(int[] nums) {
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
            result.add(subset);
        }
        
        return result;
    }

    // Alternative: BFS approach
    public List<List<Integer>> subsetsBFS(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        for (int num : nums) {
            List<List<Integer>> newSubsets = new ArrayList<>();
            for (List<Integer> subset : result) {
                List<Integer> newSubset = new ArrayList<>(subset);
                newSubset.add(num);
                newSubsets.add(newSubset);
            }
            result.addAll(newSubsets);
        }
        
        return result;
    }

    // Helper to compare results (order-independent for both outer and inner lists)
        private boolean listsEqual(List<List<Integer>> a, List<List<Integer>> b) {
            if (a.size() != b.size()) return false;
        
            // Convert to sorted strings for comparison
            java.util.Set<String> setA = new java.util.HashSet<>();
            for (List<Integer> list : a) {
                List<Integer> sorted = new ArrayList<>(list);
                java.util.Collections.sort(sorted);
                setA.add(sorted.toString());
            }
        
            java.util.Set<String> setB = new java.util.HashSet<>();
            for (List<Integer> list : b) {
                List<Integer> sorted = new ArrayList<>(list);
                java.util.Collections.sort(sorted);
                setB.add(sorted.toString());
            }
        
            return setA.equals(setB);
        }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {1, 2, 3},
            {0},
            {1, 2},
            {1, 2, 3, 4},
            {-1, 0, 1},
        };
        
        System.out.println("=== Subsets Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            
            List<List<Integer>> result1 = solver.subsets(nums);
            List<List<Integer>> result2 = solver.subsetsIterative(nums);
            List<List<Integer>> result3 = solver.subsetsBitMask(nums);
            List<List<Integer>> result4 = solver.subsetsBFS(nums);
            
            int expectedCount = 1 << nums.length;
            boolean match12 = solver.listsEqual(result1, result2);
            boolean match13 = solver.listsEqual(result1, result3);
            boolean match14 = solver.listsEqual(result1, result4);
            
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(nums));
            System.out.println("  Count: " + result1.size() + " (expected: " + expectedCount + ")");
            System.out.println("  Backtrack: " + result1);
                System.out.println("  Iterative: " + result2);
                System.out.println("  BitMask:   " + result3);
                System.out.println("  BFS:       " + result4);
                System.out.println("  Iterative match: " + (match12 ? "OK" : "FAIL"));
                System.out.println("  BitMask match:   " + (match13 ? "OK" : "FAIL"));
                System.out.println("  BFS match:       " + (match14 ? "OK" : "FAIL"));
                System.out.println("  All match: " + (match12 && match13 && match14 ? "OK" : "FAIL"));
                System.out.println();
            }
        }
}