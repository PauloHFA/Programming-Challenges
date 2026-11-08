/**
 * LeetCode 0034: Find First and Last Position of Element in Sorted Array
 * Difficulty: Medium
 * Category: Arrays & Hashing / Binary Search
 * 
 * Approach: Binary Search (Two passes - lower bound and upper bound)
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int[] searchRange(int[] nums, int target) {
        int first = findFirst(nums, target);
        if (first == -1) {
            return new int[]{-1, -1};
        }
        int last = findLast(nums, target);
        return new int[]{first, last};
    }

    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching left
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                result = mid;
                left = mid + 1; // Continue searching right
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int target1 = 8;
        int[] result1 = solver.searchRange(nums1, target1);
        System.out.println("Test 1: [" + result1[0] + ", " + result1[1] + "] (Expected: [3, 4])");

        // Test Case 2
        int[] nums2 = {5, 7, 7, 8, 8, 10};
        int target2 = 6;
        int[] result2 = solver.searchRange(nums2, target2);
        System.out.println("Test 2: [" + result2[0] + ", " + result2[1] + "] (Expected: [-1, -1])");

        // Test Case 3
        int[] nums3 = {};
        int target3 = 0;
        int[] result3 = solver.searchRange(nums3, target3);
        System.out.println("Test 3: [" + result3[0] + ", " + result3[1] + "] (Expected: [-1, -1])");

        // Test Case 4: Single element match
        int[] nums4 = {1};
        int target4 = 1;
        int[] result4 = solver.searchRange(nums4, target4);
        System.out.println("Test 4: [" + result4[0] + ", " + result4[1] + "] (Expected: [0, 0])");

        // Test Case 5: Single element no match
        int[] nums5 = {1};
        int target5 = 0;
        int[] result5 = solver.searchRange(nums5, target5);
        System.out.println("Test 5: [" + result5[0] + ", " + result5[1] + "] (Expected: [-1, -1])");

        // Test Case 6: All same elements
        int[] nums6 = {2, 2, 2, 2, 2};
        int target6 = 2;
        int[] result6 = solver.searchRange(nums6, target6);
        System.out.println("Test 6: [" + result6[0] + ", " + result6[1] + "] (Expected: [0, 4])");

        // Test Case 7: Target at beginning
        int[] nums7 = {1, 2, 3, 4, 5};
        int target7 = 1;
        int[] result7 = solver.searchRange(nums7, target7);
        System.out.println("Test 7: [" + result7[0] + ", " + result7[1] + "] (Expected: [0, 0])");

        // Test Case 8: Target at end
        int[] nums8 = {1, 2, 3, 4, 5};
        int target8 = 5;
        int[] result8 = solver.searchRange(nums8, target8);
        System.out.println("Test 8: [" + result8[0] + ", " + result8[1] + "] (Expected: [4, 4])");

        // Test Case 9: Multiple occurrences
        int[] nums9 = {1, 2, 2, 2, 3, 4, 5};
        int target9 = 2;
        int[] result9 = solver.searchRange(nums9, target9);
        System.out.println("Test 9: [" + result9[0] + ", " + result9[1] + "] (Expected: [1, 3])");

        // Test Case 10: Negative numbers
        int[] nums10 = {-5, -3, -3, -1, 0, 2};
        int target10 = -3;
        int[] result10 = solver.searchRange(nums10, target10);
        System.out.println("Test 10: [" + result10[0] + ", " + result10[1] + "] (Expected: [1, 2])");
    }
}