import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0039: Combination Sum
 * Difficulty: Medium
 * Category: Backtracking
 * 
 * Approach: Backtracking with sorting for optimization
 * Time Complexity: O(N^(T/M)) where N is candidates length, T is target, M is min candidate
 * Space Complexity: O(T/M) for recursion stack
 */
public class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sort for early termination optimization
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Early termination: if candidate > target, no need to continue (since sorted)
            if (candidates[i] > target) {
                break;
            }

            // Include candidates[i] and continue (can reuse same element)
            current.add(candidates[i]);
            backtrack(candidates, target - candidates[i], i, current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] candidates1 = {2, 3, 6, 7};
        int target1 = 7;
        List<List<Integer>> result1 = solver.combinationSum(candidates1, target1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected: [[2,2,3],[7]]");

        // Test Case 2
        int[] candidates2 = {2, 3, 5};
        int target2 = 8;
        List<List<Integer>> result2 = solver.combinationSum(candidates2, target2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Expected: [[2,2,2,2],[2,3,3],[3,5]]");

        // Test Case 3
        int[] candidates3 = {2};
        int target3 = 1;
        List<List<Integer>> result3 = solver.combinationSum(candidates3, target3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: []");

        // Test Case 4: Single candidate equals target
        int[] candidates4 = {5};
        int target4 = 5;
        List<List<Integer>> result4 = solver.combinationSum(candidates4, target4);
        System.out.println("\nTest 4: " + result4);
        System.out.println("Expected: [[5]]");

        // Test Case 5: Multiple combinations
        int[] candidates5 = {2, 4, 6, 8};
        int target5 = 8;
        List<List<Integer>> result5 = solver.combinationSum(candidates5, target5);
        System.out.println("\nTest 5: " + result5);
        System.out.println("Expected: [[2,2,2,2],[2,2,4],[2,6],[4,4],[8]]");

        // Test Case 6: Larger target
        int[] candidates6 = {2, 3, 5};
        int target6 = 10;
        List<List<Integer>> result6 = solver.combinationSum(candidates6, target6);
        System.out.println("\nTest 6: " + result6);
    }
}