import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0046: Permutations
 * Difficulty: Medium
 * Category: Backtracking
 * 
 * Approach: Backtracking with swapping
 * Time Complexity: O(n! * n)
 * Space Complexity: O(n) for recursion stack (excluding output)
 */
public class Solution {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result);
        return result;
    }

    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            // Convert array to list and add to result
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            // Swap
            swap(nums, start, i);
            // Recurse
            backtrack(nums, start + 1, result);
            // Backtrack (swap back)
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Alternative: Using visited array (more intuitive)
    public List<List<Integer>> permuteVisited(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrackVisited(nums, new ArrayList<>(), used, result);
        return result;
    }

    private void backtrackVisited(int[] nums, List<Integer> current, boolean[] used, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            current.add(nums[i]);
            backtrackVisited(nums, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 2, 3};
        List<List<Integer>> result1 = solver.permute(nums1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]");
        System.out.println("Count: " + result1.size() + " (Expected: 6)");

        // Test Case 2
        int[] nums2 = {0, 1};
        List<List<Integer>> result2 = solver.permute(nums2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Expected: [[0,1],[1,0]]");
        System.out.println("Count: " + result2.size() + " (Expected: 2)");

        // Test Case 3
        int[] nums3 = {1};
        List<List<Integer>> result3 = solver.permute(nums3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: [[1]]");
        System.out.println("Count: " + result3.size() + " (Expected: 1)");

        // Test Case 4: 4 elements
        int[] nums4 = {1, 2, 3, 4};
        List<List<Integer>> result4 = solver.permute(nums4);
        System.out.println("\nTest 4: Count = " + result4.size() + " (Expected: 24)");

        // Verify both approaches match
        System.out.println("\n--- Verification (Swap vs Visited) ---");
        int[][] testCases = {{1, 2, 3}, {0, 1}, {1}, {1, 2, 3, 4}};
        for (int[] test : testCases) {
            List<List<Integer>> swap = solver.permute(test.clone());
            List<List<Integer>> visited = solver.permuteVisited(test.clone());
            System.out.println("Input: " + Arrays.toString(test) + " -> Swap: " + swap.size() + ", Visited: " + visited.size() + " (match: " + (swap.size() == visited.size()) + ")");
        }
    }
}