import java.util.Arrays;

/**
 * LeetCode 0031: Next Permutation
 * Difficulty: Medium
 * Category: Two Pointers / Array
 * 
 * Approach: Two Pointers (Find pivot, swap, reverse suffix)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // Step 1: Find the first decreasing element from the right (pivot)
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: Find the smallest element greater than nums[i] from the right
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            // Step 3: Swap pivot with this element
            swap(nums, i, j);
        }

        // Step 4: Reverse the suffix (from i+1 to end)
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 2, 3};
        solver.nextPermutation(nums1);
        System.out.println("Test 1: " + Arrays.toString(nums1) + " (Expected: [1, 3, 2])");

        // Test Case 2
        int[] nums2 = {3, 2, 1};
        solver.nextPermutation(nums2);
        System.out.println("Test 2: " + Arrays.toString(nums2) + " (Expected: [1, 2, 3])");

        // Test Case 3
        int[] nums3 = {1, 1, 5};
        solver.nextPermutation(nums3);
        System.out.println("Test 3: " + Arrays.toString(nums3) + " (Expected: [1, 5, 1])");

        // Test Case 4: Single element
        int[] nums4 = {1};
        solver.nextPermutation(nums4);
        System.out.println("Test 4: " + Arrays.toString(nums4) + " (Expected: [1])");

        // Test Case 5: Two elements
        int[] nums5 = {1, 2};
        solver.nextPermutation(nums5);
        System.out.println("Test 5: " + Arrays.toString(nums5) + " (Expected: [2, 1])");

        // Test Case 6: Two elements descending
        int[] nums6 = {2, 1};
        solver.nextPermutation(nums6);
        System.out.println("Test 6: " + Arrays.toString(nums6) + " (Expected: [1, 2])");

        // Test Case 7: With duplicates
        int[] nums7 = {1, 3, 2};
        solver.nextPermutation(nums7);
        System.out.println("Test 7: " + Arrays.toString(nums7) + " (Expected: [2, 1, 3])");

        // Test Case 8: All same
        int[] nums8 = {2, 2, 2};
        solver.nextPermutation(nums8);
        System.out.println("Test 8: " + Arrays.toString(nums8) + " (Expected: [2, 2, 2])");

        // Test Case 9: Complex case
        int[] nums9 = {1, 2, 3, 4};
        solver.nextPermutation(nums9);
        System.out.println("Test 9: " + Arrays.toString(nums9) + " (Expected: [1, 2, 4, 3])");

        // Test Case 10: Another complex case
        int[] nums10 = {1, 5, 8, 4, 7, 6, 5, 3, 1};
        solver.nextPermutation(nums10);
        System.out.println("Test 10: " + Arrays.toString(nums10) + " (Expected: [1, 5, 8, 5, 1, 3, 4, 6, 7])");
    }
}