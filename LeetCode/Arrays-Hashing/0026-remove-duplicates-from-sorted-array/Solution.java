/**
 * LeetCode 0026: Remove Duplicates from Sorted Array
 * Difficulty: Easy
 * Category: Arrays & Hashing / Two Pointers
 * 
 * Approach: Two Pointers (Fast and Slow)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;

        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 1, 2};
        int k1 = solver.removeDuplicates(nums1);
        System.out.println("Test 1: k=" + k1 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums1, k1)) + " (Expected: k=2, [1,2])");

        // Test Case 2
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k2 = solver.removeDuplicates(nums2);
        System.out.println("Test 2: k=" + k2 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums2, k2)) + " (Expected: k=5, [0,1,2,3,4])");

        // Test Case 3: Single element
        int[] nums3 = {1};
        int k3 = solver.removeDuplicates(nums3);
        System.out.println("Test 3: k=" + k3 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums3, k3)) + " (Expected: k=1, [1])");

        // Test Case 4: All duplicates
        int[] nums4 = {2, 2, 2, 2, 2};
        int k4 = solver.removeDuplicates(nums4);
        System.out.println("Test 4: k=" + k4 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums4, k4)) + " (Expected: k=1, [2])");

        // Test Case 5: No duplicates
        int[] nums5 = {1, 2, 3, 4, 5};
        int k5 = solver.removeDuplicates(nums5);
        System.out.println("Test 5: k=" + k5 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums5, k5)) + " (Expected: k=5, [1,2,3,4,5])");

        // Test Case 6: Empty array
        int[] nums6 = {};
        int k6 = solver.removeDuplicates(nums6);
        System.out.println("Test 6: k=" + k6 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums6, k6)) + " (Expected: k=0, [])");

        // Test Case 7: Two elements same
        int[] nums7 = {1, 1};
        int k7 = solver.removeDuplicates(nums7);
        System.out.println("Test 7: k=" + k7 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums7, k7)) + " (Expected: k=1, [1])");

        // Test Case 8: Two elements different
        int[] nums8 = {1, 2};
        int k8 = solver.removeDuplicates(nums8);
        System.out.println("Test 8: k=" + k8 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums8, k8)) + " (Expected: k=2, [1,2])");

        // Test Case 9: Negative numbers
        int[] nums9 = {-3, -3, -2, -1, -1, 0, 0, 1};
        int k9 = solver.removeDuplicates(nums9);
        System.out.println("Test 9: k=" + k9 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums9, k9)) + " (Expected: k=5, [-3,-2,-1,0,1])");

        // Test Case 10: Large array
        int[] nums10 = new int[100];
        for (int i = 0; i < 100; i++) {
            nums10[i] = i / 2; // Each number appears twice
        }
        int k10 = solver.removeDuplicates(nums10);
        System.out.println("Test 10: k=" + k10 + " (Expected: 50)");
    }
}