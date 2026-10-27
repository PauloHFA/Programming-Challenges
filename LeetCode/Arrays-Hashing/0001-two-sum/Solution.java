import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 0001: Two Sum
 * Difficulty: Easy
 * Category: Arrays & Hashing
 * 
 * Approach: One-Pass Hash Map
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class Solution {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (numToIndex.containsKey(complement)) {
                return new int[] { numToIndex.get(complement), i };
            }

            numToIndex.put(nums[i], i);
        }

        throw new IllegalArgumentException("No two sum solution found");
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = { 2, 7, 11, 15 };
        int target1 = 9;
        int[] result1 = solver.twoSum(nums1, target1);
        System.out.println("Test 1: " + Arrays.toString(result1) + " (Expected: [0, 1])");

        // Test Case 2
        int[] nums2 = { 3, 2, 4 };
        int target2 = 6;
        int[] result2 = solver.twoSum(nums2, target2);
        System.out.println("Test 2: " + Arrays.toString(result2) + " (Expected: [1, 2])");

        // Test Case 3
        int[] nums3 = { 3, 3 };
        int target3 = 6;
        int[] result3 = solver.twoSum(nums3, target3);
        System.out.println("Test 3: " + Arrays.toString(result3) + " (Expected: [0, 1])");
    }
}
