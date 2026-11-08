/**
 * LeetCode 0027: Remove Element
 * Difficulty: Easy
 * Category: Arrays & Hashing / Two Pointers
 * 
 * Approach: Two Pointers (Fast and Slow)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int removeElement(int[] nums, int val) {
        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {3, 2, 2, 3};
        int val1 = 3;
        int k1 = solver.removeElement(nums1, val1);
        System.out.println("Test 1: k=" + k1 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums1, k1)) + " (Expected: k=2, [2,2])");

        // Test Case 2
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int val2 = 2;
        int k2 = solver.removeElement(nums2, val2);
        System.out.println("Test 2: k=" + k2 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums2, k2)) + " (Expected: k=5, [0,1,3,0,4])");

        // Test Case 3: Element not in array
        int[] nums3 = {1, 2, 3, 4, 5};
        int val3 = 6;
        int k3 = solver.removeElement(nums3, val3);
        System.out.println("Test 3: k=" + k3 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums3, k3)) + " (Expected: k=5, [1,2,3,4,5])");

        // Test Case 4: All elements are val
        int[] nums4 = {2, 2, 2, 2};
        int val4 = 2;
        int k4 = solver.removeElement(nums4, val4);
        System.out.println("Test 4: k=" + k4 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums4, k4)) + " (Expected: k=0, [])");

        // Test Case 5: Single element, matches
        int[] nums5 = {1};
        int val5 = 1;
        int k5 = solver.removeElement(nums5, val5);
        System.out.println("Test 5: k=" + k5 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums5, k5)) + " (Expected: k=0, [])");

        // Test Case 6: Single element, doesn't match
        int[] nums6 = {1};
        int val6 = 2;
        int k6 = solver.removeElement(nums6, val6);
        System.out.println("Test 6: k=" + k6 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums6, k6)) + " (Expected: k=1, [1])");

        // Test Case 7: Empty array
        int[] nums7 = {};
        int val7 = 1;
        int k7 = solver.removeElement(nums7, val7);
        System.out.println("Test 7: k=" + k7 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums7, k7)) + " (Expected: k=0, [])");

        // Test Case 8: Negative numbers
        int[] nums8 = {-1, -2, -3, -2, -1};
        int val8 = -2;
        int k8 = solver.removeElement(nums8, val8);
        System.out.println("Test 8: k=" + k8 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums8, k8)) + " (Expected: k=3, [-1,-3,-1])");

        // Test Case 9: Val at beginning
        int[] nums9 = {5, 1, 2, 3, 4};
        int val9 = 5;
        int k9 = solver.removeElement(nums9, val9);
        System.out.println("Test 9: k=" + k9 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums9, k9)) + " (Expected: k=4, [1,2,3,4])");

        // Test Case 10: Val at end
        int[] nums10 = {1, 2, 3, 4, 5};
        int val10 = 5;
        int k10 = solver.removeElement(nums10, val10);
        System.out.println("Test 10: k=" + k10 + ", nums=" + java.util.Arrays.toString(java.util.Arrays.copyOf(nums10, k10)) + " (Expected: k=4, [1,2,3,4])");
    }
}