/**
 * LeetCode 0033: Search in Rotated Sorted Array
 * Difficulty: Medium
 * Category: Arrays & Hashing / Binary Search
 * 
 * Approach: Modified Binary Search
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Determine which side is sorted
            if (nums[left] <= nums[mid]) {
                // Left side is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right side is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        int result1 = solver.search(nums1, target1);
        System.out.println("Test 1: " + result1 + " (Expected: 4)");

        // Test Case 2
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int target2 = 3;
        int result2 = solver.search(nums2, target2);
        System.out.println("Test 2: " + result2 + " (Expected: -1)");

        // Test Case 3
        int[] nums3 = {1};
        int target3 = 0;
        int result3 = solver.search(nums3, target3);
        System.out.println("Test 3: " + result3 + " (Expected: -1)");

        // Test Case 4: Not rotated
        int[] nums4 = {1, 2, 3, 4, 5};
        int target4 = 3;
        int result4 = solver.search(nums4, target4);
        System.out.println("Test 4: " + result4 + " (Expected: 2)");

        // Test Case 5: Rotated at pivot
        int[] nums5 = {3, 1};
        int target5 = 1;
        int result5 = solver.search(nums5, target5);
        System.out.println("Test 5: " + result5 + " (Expected: 1)");

        // Test Case 6: Target at mid
        int[] nums6 = {5, 1, 3};
        int target6 = 3;
        int result6 = solver.search(nums6, target6);
        System.out.println("Test 6: " + result6 + " (Expected: 2)");

        // Test Case 7: Target at left
        int[] nums7 = {4, 5, 6, 7, 0, 1, 2};
        int target7 = 4;
        int result7 = solver.search(nums7, target7);
        System.out.println("Test 7: " + result7 + " (Expected: 0)");

        // Test Case 8: Target at right
        int[] nums8 = {4, 5, 6, 7, 0, 1, 2};
        int target8 = 2;
        int result8 = solver.search(nums8, target8);
        System.out.println("Test 8: " + result8 + " (Expected: 6)");

        // Test Case 9: Negative numbers
        int[] nums9 = {-5, -3, -1, 0, 1, 2, 3};
        int target9 = -3;
        int result9 = solver.search(nums9, target9);
        System.out.println("Test 9: " + result9 + " (Expected: 1)");

        // Test Case 10: Rotated with negatives
        int[] nums10 = {3, 4, 5, -5, -3, -1, 0, 1, 2};
        int target10 = -1;
        int result10 = solver.search(nums10, target10);
        System.out.println("Test 10: " + result10 + " (Expected: 5)");
    }
}