import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0018: 4Sum
 * Difficulty: Medium
 * Category: Arrays & Hashing / Two Pointers
 * 
 * Approach: Sort + Two Pointers (Generalized k-Sum)
 * Time Complexity: O(n^3)
 * Space Complexity: O(1) or O(n) depending on sorting
 */
public class Solution {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Early termination: smallest possible sum > target
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }

            // Early termination: largest possible sum < target
            if ((long) nums[i] + nums[n - 3] + nums[n - 2] + nums[n - 1] < target) {
                continue;
            }

            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicates for second element
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // Early termination for second element
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }

                if ((long) nums[i] + nums[j] + nums[n - 2] + nums[n - 1] < target) {
                    continue;
                }

                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Skip duplicates for third element
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        // Skip duplicates for fourth element
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }

                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        List<List<Integer>> result1 = solver.fourSum(nums1, target1);
        System.out.println("Test 1: " + result1 + " (Expected: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]])");

        // Test Case 2
        int[] nums2 = {2, 2, 2, 2, 2};
        int target2 = 8;
        List<List<Integer>> result2 = solver.fourSum(nums2, target2);
        System.out.println("Test 2: " + result2 + " (Expected: [[2,2,2,2]])");

        // Test Case 3: No solution
        int[] nums3 = {1, 2, 3, 4, 5};
        int target3 = 100;
        List<List<Integer>> result3 = solver.fourSum(nums3, target3);
        System.out.println("Test 3: " + result3 + " (Expected: [])");

        // Test Case 4: All zeros
        int[] nums4 = {0, 0, 0, 0};
        int target4 = 0;
        List<List<Integer>> result4 = solver.fourSum(nums4, target4);
        System.out.println("Test 4: " + result4 + " (Expected: [[0,0,0,0]])");

        // Test Case 5: Negative target
        int[] nums5 = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target5 = 0;
        List<List<Integer>> result5 = solver.fourSum(nums5, target5);
        System.out.println("Test 5: " + result5);

        // Test Case 6: Large numbers
        int[] nums6 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target6 = -294967296;
        List<List<Integer>> result6 = solver.fourSum(nums6, target6);
        System.out.println("Test 6: " + result6 + " (Expected: [])");

        // Test Case 7: Duplicates
        int[] nums7 = {-1, 0, 1, 2, -1, -4, -2, 3};
        int target7 = 0;
        List<List<Integer>> result7 = solver.fourSum(nums7, target7);
        System.out.println("Test 7: " + result7);

        // Test Case 8: Four elements only
        int[] nums8 = {1, 2, 3, 4};
        int target8 = 10;
        List<List<Integer>> result8 = solver.fourSum(nums8, target8);
        System.out.println("Test 8: " + result8 + " (Expected: [[1,2,3,4]])");

        // Test Case 9: Less than 4 elements
        int[] nums9 = {1, 2, 3};
        int target9 = 6;
        List<List<Integer>> result9 = solver.fourSum(nums9, target9);
        System.out.println("Test 9: " + result9 + " (Expected: [])");

        // Test Case 10: Mixed
        int[] nums10 = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        int target10 = 0;
        List<List<Integer>> result10 = solver.fourSum(nums10, target10);
        System.out.println("Test 10: " + result10.size() + " quadruplets found");
    }
}