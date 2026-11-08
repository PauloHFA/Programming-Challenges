import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0040: Combination Sum II
 * Difficulty: Medium
 * Category: Backtracking
 * 
 * Approach: Backtracking with sorting and duplicate skipping
 * Time Complexity: O(2^N) where N is candidates length
 * Space Complexity: O(N) for recursion stack
 */
public class Solution {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sort to handle duplicates
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Skip duplicates: if same as previous and previous wasn't used in this position
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // Early termination
            if (candidates[i] > target) {
                break;
            }

            current.add(candidates[i]);
            // i + 1 because each element can only be used ONCE
            backtrack(candidates, target - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
        int target1 = 8;
        List<List<Integer>> result1 = solver.combinationSum2(candidates1, target1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected: [[1,1,6],[1,2,5],[1,7],[2,6]]");

        // Test Case 2
        int[] candidates2 = {2, 5, 2, 1, 2};
        int target2 = 5;
        List<List<Integer>> result2 = solver.combinationSum2(candidates2, target2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Expected: [[1,2,2],[5]]");

        // Test Case 3: No solution
        int[] candidates3 = {2, 3, 5};
        int target3 = 1;
        List<List<Integer>> result3 = solver.combinationSum2(candidates3, target3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: []");

        // Test Case 4: Single element equals target
        int[] candidates4 = {5};
        int target4 = 5;
        List<List<Integer>> result4 = solver.combinationSum2(candidates4, target4);
        System.out.println("\nTest 4: " + result4);
        System.out.println("Expected: [[5]]");

        // Test Case 5: All duplicates
        int[] candidates5 = {1, 1, 1, 1, 1};
        int target5 = 3;
        List<List<Integer>> result5 = solver.combinationSum2(candidates5, target5);
        System.out.println("\nTest 5: " + result5);
        System.out.println("Expected: [[1,1,1]]");

        // Test Case 6: Multiple duplicates
        int[] candidates6 = {1, 2, 2, 2, 3};
        int target6 = 5;
        List<List<Integer>> result6 = solver.combinationSum2(candidates6, target6);
        System.out.println("\nTest 6: " + result6);
        System.out.println("Expected: [[1,2,2],[2,3]]");
    }
}