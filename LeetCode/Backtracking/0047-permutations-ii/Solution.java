import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0047: Permutations II
 * Difficulty: Medium
 * Category: Backtracking
 * 
 * Approach: Backtracking with sorting and duplicate skipping
 * Time Complexity: O(n! * n)
 * Space Complexity: O(n) for recursion stack
 */
public class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Sort to handle duplicates
        boolean[] used = new boolean[nums.length];
        backtrack(nums, new ArrayList<>(), used, result);
        return result;
    }

    private void backtrack(int[] nums, List<Integer> current, boolean[] used, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // Skip used elements
            if (used[i]) continue;
            
            // Skip duplicates: if same as previous and previous wasn't used in this position
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;
            current.add(nums[i]);
            backtrack(nums, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 1, 2};
        List<List<Integer>> result1 = solver.permuteUnique(nums1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected: [[1,1,2],[1,2,1],[2,1,1]]");
        System.out.println("Count: " + result1.size() + " (Expected: 3)");

        // Test Case 2
        int[] nums2 = {1, 2, 3};
        List<List<Integer>> result2 = solver.permuteUnique(nums2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Count: " + result2.size() + " (Expected: 6)");

        // Test Case 3: All duplicates
        int[] nums3 = {1, 1, 1};
        List<List<Integer>> result3 = solver.permuteUnique(nums3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: [[1,1,1]]");
        System.out.println("Count: " + result3.size() + " (Expected: 1)");

        // Test Case 4: Two pairs of duplicates
        int[] nums4 = {1, 1, 2, 2};
        List<List<Integer>> result4 = solver.permuteUnique(nums4);
        System.out.println("\nTest 4: Count = " + result4.size() + " (Expected: 6)");

        // Test Case 5: Single element
        int[] nums5 = {1};
        List<List<Integer>> result5 = solver.permuteUnique(nums5);
        System.out.println("\nTest 5: " + result5);
        System.out.println("Count: " + result5.size() + " (Expected: 1)");

        // Test Case 6: Three duplicates
        int[] nums6 = {1, 1, 1, 2};
        List<List<Integer>> result6 = solver.permuteUnique(nums6);
        System.out.println("\nTest 6: Count = " + result6.size() + " (Expected: 4)");
    }
}