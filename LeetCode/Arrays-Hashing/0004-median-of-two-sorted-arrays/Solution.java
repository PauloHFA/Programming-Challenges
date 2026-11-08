import java.util.Arrays;

/**
 * LeetCode 0004: Median of Two Sorted Arrays
 * Difficulty: Hard
 * Category: Arrays & Hashing (Binary Search)
 * 
 * Approach: Binary Search on Partition
 * Time Complexity: O(log(min(m, n)))
 * Space Complexity: O(1)
 */
public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array for binary search
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0;
        int right = m;
        int halfLen = (m + n + 1) / 2;

        while (left <= right) {
            int i = (left + right) / 2; // Partition of nums1
            int j = halfLen - i;        // Partition of nums2

            int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                // Correct partition found
                if ((m + n) % 2 == 1) {
                    return Math.max(nums1LeftMax, nums2LeftMax);
                } else {
                    return (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                }
            } else if (nums1LeftMax > nums2RightMin) {
                // Move partition i to the left
                right = i - 1;
            } else {
                // Move partition i to the right
                left = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted or invalid");
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1_1 = {1, 3};
        int[] nums2_1 = {2};
        double result1 = solver.findMedianSortedArrays(nums1_1, nums2_1);
        System.out.println("Test 1: " + result1 + " (Expected: 2.0)");

        // Test Case 2
        int[] nums1_2 = {1, 2};
        int[] nums2_2 = {3, 4};
        double result2 = solver.findMedianSortedArrays(nums1_2, nums2_2);
        System.out.println("Test 2: " + result2 + " (Expected: 2.5)");

        // Test Case 3: One empty array
        int[] nums1_3 = {};
        int[] nums2_3 = {1};
        double result3 = solver.findMedianSortedArrays(nums1_3, nums2_3);
        System.out.println("Test 3: " + result3 + " (Expected: 1.0)");

        // Test Case 4: One empty array with two elements
        int[] nums1_4 = {};
        int[] nums2_4 = {2, 3};
        double result4 = solver.findMedianSortedArrays(nums1_4, nums2_4);
        System.out.println("Test 4: " + result4 + " (Expected: 2.5)");

        // Test Case 5: Arrays with same elements
        int[] nums1_5 = {1, 1};
        int[] nums2_5 = {1, 1};
        double result5 = solver.findMedianSortedArrays(nums1_5, nums2_5);
        System.out.println("Test 5: " + result5 + " (Expected: 1.0)");

        // Test Case 6: Large arrays
        int[] nums1_6 = {1, 2, 3, 4, 5};
        int[] nums2_6 = {6, 7, 8, 9, 10};
        double result6 = solver.findMedianSortedArrays(nums1_6, nums2_6);
        System.out.println("Test 6: " + result6 + " (Expected: 5.5)");

        // Test Case 7: Negative numbers
        int[] nums1_7 = {-5, -3, -1};
        int[] nums2_7 = {-4, -2, 0};
        double result7 = solver.findMedianSortedArrays(nums1_7, nums2_7);
        System.out.println("Test 7: " + result7 + " (Expected: -2.5)");

        // Test Case 8: Single element arrays
        int[] nums1_8 = {2};
        int[] nums2_8 = {1, 3, 4};
        double result8 = solver.findMedianSortedArrays(nums1_8, nums2_8);
        System.out.println("Test 8: " + result8 + " (Expected: 2.5)");
    }
}