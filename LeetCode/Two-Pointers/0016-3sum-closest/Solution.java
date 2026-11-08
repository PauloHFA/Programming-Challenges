import java.util.Arrays;

/**
 * LeetCode 0016: 3Sum Closest
 * Difficulty: Medium
 * Category: Two Pointers
 * 
 * Approach: Sort + Two Pointers
 * Time Complexity: O(n^2)
 * Space Complexity: O(1) or O(n) depending on sorting
 */
public class Solution {

    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];

                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }

                if (currentSum < target) {
                    left++;
                } else if (currentSum > target) {
                    right--;
                } else {
                    // Exact match found
                    return target;
                }
            }
        }

        return closestSum;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {-1, 2, 1, -4};
        int target1 = 1;
        int result1 = solver.threeSumClosest(nums1, target1);
        System.out.println("Test 1: " + result1 + " (Expected: 2)");

        // Test Case 2
        int[] nums2 = {0, 0, 0};
        int target2 = 1;
        int result2 = solver.threeSumClosest(nums2, target2);
        System.out.println("Test 2: " + result2 + " (Expected: 0)");

        // Test Case 3
        int[] nums3 = {1, 1, 1, 0};
        int target3 = -100;
        int result3 = solver.threeSumClosest(nums3, target3);
        System.out.println("Test 3: " + result3 + " (Expected: 2)");

        // Test Case 4: Exact match
        int[] nums4 = {1, 2, 3, 4, 5};
        int target4 = 10;
        int result4 = solver.threeSumClosest(nums4, target4);
        System.out.println("Test 4: " + result4 + " (Expected: 10)");

        // Test Case 5: Negative target
        int[] nums5 = {-3, -2, -5, 3, -4};
        int target5 = -1;
        int result5 = solver.threeSumClosest(nums5, target5);
        System.out.println("Test 5: " + result5 + " (Expected: -2)");

        // Test Case 6: All same
        int[] nums6 = {2, 2, 2, 2};
        int target6 = 7;
        int result6 = solver.threeSumClosest(nums6, target6);
        System.out.println("Test 6: " + result6 + " (Expected: 6)");

        // Test Case 7: Large numbers
        int[] nums7 = {1000, 2000, 3000, 4000};
        int target7 = 10000;
        int result7 = solver.threeSumClosest(nums7, target7);
        System.out.println("Test 7: " + result7 + " (Expected: 9000)");

        // Test Case 8: Mixed positive negative
        int[] nums8 = {-10, -20, -30, -40, 10, 20, 30, 40};
        int target8 = 5;
        int result8 = solver.threeSumClosest(nums8, target8);
                System.out.println("Test 8: " + result8 + " (Expected: 10 or 0, both distance 5)");

        // Test Case 9: Three elements only
        int[] nums9 = {1, 2, 3};
        int target9 = 100;
        int result9 = solver.threeSumClosest(nums9, target9);
        System.out.println("Test 9: " + result9 + " (Expected: 6)");

        // Test Case 10: Close to target
        int[] nums10 = {1, 2, 4, 8, 16, 32, 64, 128};
        int target10 = 82;
        int result10 = solver.threeSumClosest(nums10, target10);
        System.out.println("Test 10: " + result10 + " (Expected: 82)");
    }
}