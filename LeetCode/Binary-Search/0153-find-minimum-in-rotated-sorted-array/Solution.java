/**
 * LeetCode 153 - Find Minimum in Rotated Sorted Array
 * 
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * For example, the array nums = [0,1,2,4,5,6,7] might become:
 * - [4,5,6,7,0,1,2] if it was rotated 4 times.
 * - [0,1,2,4,5,6,7] if it was rotated 7 times.
 * 
 * Notice that rotating an array [a[0], a[1], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * Example 1:
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 * 
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] rotated 4 times.
 * 
 * Example 3:
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] rotated 4 times.
 * 
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * All the integers of nums are unique.
 * nums is sorted and rotated between 1 and n times.
 */
public class Solution {
    
    /**
     * Approach 1: Binary Search - Standard O(log n)
     * 
     * The minimum element is the only element where nums[i] < nums[i-1].
     * In a rotated sorted array, one half is always sorted.
     * Compare mid with right to decide which half to search.
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Minimum is in right half
                left = mid + 1;
            } else {
                // Minimum is in left half (including mid)
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 2: Binary Search - Compare with Left
     * 
     * Alternative: compare mid with left element.
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findMinLeft(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // If not rotated
        if (nums[left] < nums[right]) return nums[left];
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is minimum
            if (mid > 0 && nums[mid] < nums[mid - 1]) {
                return nums[mid];
            }
            if (mid < nums.length - 1 && nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            if (nums[mid] >= nums[left]) {
                // Left half is sorted, minimum in right
                left = mid + 1;
            } else {
                // Right half is sorted, minimum in left
                right = mid - 1;
            }
        }
        
        return nums[0];
    }
    
    /**
     * Approach 3: Binary Search - Find Pivot Index
     * 
     * Find the pivot (rotation point), then minimum is at pivot.
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findMinPivot(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[right]) {
                // Right half is sorted, pivot in left
                right = mid;
            } else {
                // Left half is sorted, pivot in right
                left = mid + 1;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 4: Linear Scan (for verification)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int findMinLinear(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return nums[i];
            }
        }
        return nums[0];
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Find Minimum in Rotated Sorted Array Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [3,4,5,1,2] -> Expected: 1");
        int[] nums1 = {3, 4, 5, 1, 2};
        int result1 = sol.findMin(nums1);
        System.out.println("  Standard: " + result1 + (result1 == 1 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums1));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums1));
        System.out.println("  Linear:   " + sol.findMinLinear(nums1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [4,5,6,7,0,1,2] -> Expected: 0");
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int result2 = sol.findMin(nums2);
        System.out.println("  Standard: " + result2 + (result2 == 0 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums2));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums2));
        System.out.println("  Linear:   " + sol.findMinLinear(nums2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: [11,13,15,17] -> Expected: 11");
        int[] nums3 = {11, 13, 15, 17};
        int result3 = sol.findMin(nums3);
        System.out.println("  Standard: " + result3 + (result3 == 11 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums3));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums3));
        System.out.println("  Linear:   " + sol.findMinLinear(nums3));
        
        // Test 4: Single element
        System.out.println("\nTest 4: [1] -> Expected: 1");
        int[] nums4 = {1};
        int result4 = sol.findMin(nums4);
        System.out.println("  Standard: " + result4 + (result4 == 1 ? " ✓" : " ✗"));
        
        // Test 5: Two elements
        System.out.println("\nTest 5: [2,1] -> Expected: 1");
        int[] nums5 = {2, 1};
        int result5 = sol.findMin(nums5);
        System.out.println("  Standard: " + result5 + (result5 == 1 ? " ✓" : " ✗"));
        
        // Test 6: Rotated n times (same as original)
        System.out.println("\nTest 6: [1,2,3,4,5] -> Expected: 1");
        int[] nums6 = {1, 2, 3, 4, 5};
        int result6 = sol.findMin(nums6);
        System.out.println("  Standard: " + result6 + (result6 == 1 ? " ✓" : " ✗"));
        
        // Test 7: Minimum at middle
        System.out.println("\nTest 7: [5,6,1,2,3,4] -> Expected: 1");
        int[] nums7 = {5, 6, 1, 2, 3, 4};
        int result7 = sol.findMin(nums7);
        System.out.println("  Standard: " + result7 + (result7 == 1 ? " ✓" : " ✗"));
        
        // Test 8: Negative numbers
        System.out.println("\nTest 8: [-5,-4,-3,-2,-1] -> Expected: -5");
        int[] nums8 = {-5, -4, -3, -2, -1};
        int result8 = sol.findMin(nums8);
        System.out.println("  Standard: " + result8 + (result8 == -5 ? " ✓" : " ✗"));
        
        // Test 9: Rotated with negatives
        System.out.println("\nTest 9: [-2,-1,0,1,2] rotated -> [1,2,-2,-1,0] -> Expected: -2");
        int[] nums9 = {1, 2, -2, -1, 0};
        int result9 = sol.findMin(nums9);
        System.out.println("  Standard: " + result9 + (result9 == -2 ? " ✓" : " ✗"));
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        int[][] testCases = {
            {3, 4, 5, 1, 2},
            {4, 5, 6, 7, 0, 1, 2},
            {11, 13, 15, 17},
            {1},
            {2, 1},
            {1, 2, 3, 4, 5},
            {5, 6, 1, 2, 3, 4},
            {-5, -4, -3, -2, -1},
            {1, 2, -2, -1, 0},
            {7, 8, 9, 1, 2, 3, 4, 5, 6}
        };
        int[] expected = {1, 0, 11, 1, 1, 1, 1, -5, -2, 1};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.findMin(testCases[i]);
            int r2 = sol.findMinLeft(testCases[i]);
            int r3 = sol.findMinPivot(testCases[i]);
            int r4 = sol.findMinLinear(testCases[i]);
            
            boolean m1 = (r1 == expected[i]);
            boolean m2 = (r2 == expected[i]);
            boolean m3 = (r3 == expected[i]);
            boolean m4 = (r4 == expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Std:" + r1 + " Left:" + r2 + " Piv:" + r3 + " Lin:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}