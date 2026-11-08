/**
 * LeetCode 0035: Search Insert Position
 * Difficulty: Easy
 * Category: Arrays & Hashing / Binary Search
 * 
 * Approach: Binary Search
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int searchInsert(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (nums[mid] >= target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // left is the insertion position (lower bound)
            return left;
        }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 5;
        int result1 = solver.searchInsert(nums1, target1);
        System.out.println("Test 1: " + result1 + " (Expected: 2)");

        // Test Case 2
        int[] nums2 = {1, 3, 5, 6};
        int target2 = 2;
        int result2 = solver.searchInsert(nums2, target2);
        System.out.println("Test 2: " + result2 + " (Expected: 1)");

        // Test Case 3
        int[] nums3 = {1, 3, 5, 6};
        int target3 = 7;
        int result3 = solver.searchInsert(nums3, target3);
        System.out.println("Test 3: " + result3 + " (Expected: 4)");

        // Test Case 4
        int[] nums4 = {1, 3, 5, 6};
        int target4 = 0;
        int result4 = solver.searchInsert(nums4, target4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Single element
        int[] nums5 = {1};
        int target5 = 0;
        int result5 = solver.searchInsert(nums5, target5);
        System.out.println("Test 5: " + result5 + " (Expected: 0)");

        // Test Case 6: Insert at end
        int[] nums6 = {1, 2, 3};
        int target6 = 4;
        int result6 = solver.searchInsert(nums6, target6);
        System.out.println("Test 6: " + result6 + " (Expected: 3)");

        // Test Case 7: Insert at beginning
        int[] nums7 = {2, 3, 4};
        int target7 = 1;
        int result7 = solver.searchInsert(nums7, target7);
        System.out.println("Test 7: " + result7 + " (Expected: 0)");

        // Test Case 8: Empty array
        int[] nums8 = {};
        int target8 = 5;
        int result8 = solver.searchInsert(nums8, target8);
        System.out.println("Test 8: " + result8 + " (Expected: 0)");

        // Test Case 9: Duplicates
        int[] nums9 = {1, 2, 2, 2, 3};
        int target9 = 2;
        int result9 = solver.searchInsert(nums9, target9);
        System.out.println("Test 9: " + result9 + " (Expected: 1)");

        // Test Case 10: Negative numbers
        int[] nums10 = {-5, -3, -1, 0, 2};
        int target10 = -2;
        int result10 = solver.searchInsert(nums10, target10);
        System.out.println("Test 10: " + result10 + " (Expected: 2)");
    }
}