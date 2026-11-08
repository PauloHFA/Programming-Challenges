import java.util.Arrays;

/**
 * LeetCode 0041: First Missing Positive
 * Difficulty: Hard
 * Category: Arrays & Hashing
 * 
 * Approach: Cyclic Sort / Index Mapping (O(n) time, O(1) space)
 * Time Complexity: O(n)
 * Space Complexity: O(1) - modifies input array in place
 */
public class Solution {

    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // Step 1: Place each number in its correct position
        // Number x should be at index x-1 (if 1 <= x <= n)
        for (int i = 0; i < n; i++) {
            // Keep swapping until the current number is in correct position
            // or it's out of range [1, n] or it's already in correct place
            while (nums[i] >= 1 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        
        // Step 2: Find the first position where number doesn't match index
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        // Step 3: All positions 1..n are filled, answer is n+1
        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Alternative approach using HashSet (O(n) time, O(n) space)
    public int firstMissingPositiveHashSet(int[] nums) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }
        
        for (int i = 1; i <= nums.length + 1; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return nums.length + 1;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {1, 2, 0};
        int result1 = solver.firstMissingPositive(nums1.clone());
        System.out.println("Test 1: " + result1 + " (Expected: 3)");

        // Test Case 2
        int[] nums2 = {3, 4, -1, 1};
        int result2 = solver.firstMissingPositive(nums2.clone());
        System.out.println("Test 2: " + result2 + " (Expected: 2)");

        // Test Case 3
        int[] nums3 = {7, 8, 9, 11, 12};
        int result3 = solver.firstMissingPositive(nums3.clone());
        System.out.println("Test 3: " + result3 + " (Expected: 1)");

        // Test Case 4: Already complete sequence
        int[] nums4 = {1, 2, 3, 4, 5};
        int result4 = solver.firstMissingPositive(nums4.clone());
        System.out.println("Test 4: " + result4 + " (Expected: 6)");

        // Test Case 5: Single element
        int[] nums5 = {1};
        int result5 = solver.firstMissingPositive(nums5.clone());
        System.out.println("Test 5: " + result5 + " (Expected: 2)");

        // Test Case 6: Single element not 1
        int[] nums6 = {2};
        int result6 = solver.firstMissingPositive(nums6.clone());
        System.out.println("Test 6: " + result6 + " (Expected: 1)");

        // Test Case 7: With duplicates
        int[] nums7 = {1, 1, 2, 2, 3, 3};
        int result7 = solver.firstMissingPositive(nums7.clone());
        System.out.println("Test 7: " + result7 + " (Expected: 4)");

        // Test Case 8: Negative numbers only
        int[] nums8 = {-1, -2, -3};
        int result8 = solver.firstMissingPositive(nums8.clone());
        System.out.println("Test 8: " + result8 + " (Expected: 1)");

        // Test Case 9: Large array
        int[] nums9 = new int[100];
        for (int i = 0; i < 100; i++) {
            nums9[i] = i + 2; // 2 to 101, missing 1
        }
        int result9 = solver.firstMissingPositive(nums9.clone());
        System.out.println("Test 9: " + result9 + " (Expected: 1)");

        // Test Case 10: Verify HashSet version matches
        System.out.println("\n--- HashSet Version ---");
        for (int[] test : new int[][]{nums1, nums2, nums3, nums4, nums5, nums6, nums7, nums8}) {
            int cyclic = solver.firstMissingPositive(test.clone());
            int hashSet = solver.firstMissingPositiveHashSet(test.clone());
            System.out.println("Input: " + Arrays.toString(test) + " -> Cyclic: " + cyclic + ", HashSet: " + hashSet + " (match: " + (cyclic == hashSet) + ")");
        }
    }
}