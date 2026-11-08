import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0015: 3Sum
 * Difficulty: Medium
 * Category: Arrays & Hashing / Two Pointers
 * 
 * Approach: Sort + Two Pointers
 * Time Complexity: O(n^2)
 * Space Complexity: O(1) or O(n) depending on sorting
 */
public class Solution {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate values for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for second element
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for third element
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result1 = solver.threeSum(nums1);
        System.out.println("Test 1: " + result1 + " (Expected: [[-1,-1,2],[-1,0,1]])");

        // Test Case 2
        int[] nums2 = {0, 1, 1};
        List<List<Integer>> result2 = solver.threeSum(nums2);
        System.out.println("Test 2: " + result2 + " (Expected: [])");

        // Test Case 3
        int[] nums3 = {0, 0, 0};
        List<List<Integer>> result3 = solver.threeSum(nums3);
        System.out.println("Test 3: " + result3 + " (Expected: [[0,0,0]])");

        // Test Case 4: Empty array
        int[] nums4 = {};
        List<List<Integer>> result4 = solver.threeSum(nums4);
        System.out.println("Test 4: " + result4 + " (Expected: [])");

        // Test Case 5: Less than 3 elements
        int[] nums5 = {1, 2};
        List<List<Integer>> result5 = solver.threeSum(nums5);
        System.out.println("Test 5: " + result5 + " (Expected: [])");

        // Test Case 6: All zeros
        int[] nums6 = {0, 0, 0, 0};
        List<List<Integer>> result6 = solver.threeSum(nums6);
        System.out.println("Test 6: " + result6 + " (Expected: [[0,0,0]])");

        // Test Case 7: Multiple solutions
        int[] nums7 = {-2, 0, 1, 1, 2};
        List<List<Integer>> result7 = solver.threeSum(nums7);
        System.out.println("Test 7: " + result7 + " (Expected: [[-2,0,2],[-2,1,1]])");

        // Test Case 8: Negative numbers
        int[] nums8 = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
        List<List<Integer>> result8 = solver.threeSum(nums8);
        System.out.println("Test 8: " + result8);

        // Test Case 9: No solution
        int[] nums9 = {1, 2, 3, 4, 5};
        List<List<Integer>> result9 = solver.threeSum(nums9);
        System.out.println("Test 9: " + result9 + " (Expected: [])");

        // Test Case 10: Large array
        int[] nums10 = {-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        List<List<Integer>> result10 = solver.threeSum(nums10);
        System.out.println("Test 10: " + result10);
    }
}