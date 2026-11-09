/**
 * LeetCode 154 - Find Minimum in Rotated Sorted Array II
 * 
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * For example, the array nums = [0,1,2,4,5,6,7] might become:
 * - [4,5,6,7,0,1,2] if it was rotated 4 times.
 * - [0,1,2,4,5,6,7] if it was rotated 7 times.
 * 
 * Notice that rotating an array [a[0], a[1], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
 * 
 * You must write an algorithm that runs in O(log n) time on average.
 * 
 * Example 1:
 * Input: nums = [1,3,5]
 * Output: 1
 * 
 * Example 2:
 * Input: nums = [2,2,2,0,1]
 * Output: 0
 * 
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums is sorted and rotated between 1 and n times.
 * 
 * Follow up: This problem is similar to Find Minimum in Rotated Sorted Array, but nums may contain duplicates.
 * Would this affect the runtime complexity? How and why?
 */
public class Solution {
    
    /**
     * Approach 1: Binary Search with Duplicate Handling - O(log n) average, O(n) worst
     * 
     * When nums[mid] == nums[right], we can't determine which half has the minimum.
     * In this case, we decrement right by 1 (safe because nums[right] has a duplicate at mid).
     * 
     * Time Complexity: O(log n) average, O(n) worst case (all duplicates)
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
            } else if (nums[mid] < nums[right]) {
                // Minimum is in left half (including mid)
                right = mid;
            } else {
                // nums[mid] == nums[right], can't determine, reduce right
                right--;
            }
        }
        
        return nums[left];
    }
    
    /**
         * Approach 2: Binary Search - Compare with Left
         * 
         * Alternative implementation comparing with left element.
         * 
         * Time Complexity: O(log n) average, O(n) worst case
         * Space Complexity: O(1)
         */
        public int findMinLeft(int[] nums) {
            int left = 0;
            int right = nums.length - 1;
        
            while (left < right) {
                int mid = left + (right - left) / 2;
            
                if (nums[mid] > nums[right]) {
                    left = mid + 1;
                } else if (nums[mid] < nums[right]) {
                    right = mid;
                } else {
                    // nums[mid] == nums[right], can't determine, reduce right
                    right--;
                }
            }
        
            return nums[left];
        }
    
    /**
     * Approach 3: Binary Search - Find Pivot with Duplicates
     * 
     * Explicitly find the pivot point where rotation occurs.
     * 
     * Time Complexity: O(log n) average, O(n) worst case
     * Space Complexity: O(1)
     */
    public int findMinPivot(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[right]) {
                // Right is sorted, pivot in left
                right = mid;
            } else if (nums[mid] > nums[right]) {
                // Left is sorted, pivot in right
                left = mid + 1;
            } else {
                // nums[mid] == nums[right]
                right--;
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
    
    /**
     * Approach 5: Recursive Binary Search
     * 
     * Time Complexity: O(log n) average, O(n) worst case
     * Space Complexity: O(log n) recursion stack
     */
    public int findMinRecursive(int[] nums) {
        return findMinRecursiveHelper(nums, 0, nums.length - 1);
    }
    
    private int findMinRecursiveHelper(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        if (left + 1 == right) return Math.min(nums[left], nums[right]);
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[right]) {
            return findMinRecursiveHelper(nums, mid + 1, right);
        } else if (nums[mid] < nums[right]) {
            return findMinRecursiveHelper(nums, left, mid);
        } else {
            // nums[mid] == nums[right]
            return Math.min(
                findMinRecursiveHelper(nums, left, mid),
                findMinRecursiveHelper(nums, mid + 1, right)
            );
        }
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Find Minimum in Rotated Sorted Array II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [1,3,5] -> Expected: 1");
        int[] nums1 = {1, 3, 5};
        int result1 = sol.findMin(nums1);
        System.out.println("  Standard: " + result1 + (result1 == 1 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums1));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums1));
        System.out.println("  Linear:   " + sol.findMinLinear(nums1));
        System.out.println("  Recursive:" + sol.findMinRecursive(nums1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [2,2,2,0,1] -> Expected: 0");
        int[] nums2 = {2, 2, 2, 0, 1};
        int result2 = sol.findMin(nums2);
        System.out.println("  Standard: " + result2 + (result2 == 0 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums2));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums2));
        System.out.println("  Linear:   " + sol.findMinLinear(nums2));
        System.out.println("  Recursive:" + sol.findMinRecursive(nums2));
        
        // Test 3: All duplicates
        System.out.println("\nTest 3: [2,2,2,2,2] -> Expected: 2");
        int[] nums3 = {2, 2, 2, 2, 2};
        int result3 = sol.findMin(nums3);
        System.out.println("  Standard: " + result3 + (result3 == 2 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums3));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums3));
        System.out.println("  Linear:   " + sol.findMinLinear(nums3));
        System.out.println("  Recursive:" + sol.findMinRecursive(nums3));
        
        // Test 4: Duplicates at ends
        System.out.println("\nTest 4: [3,3,1,3] -> Expected: 1");
        int[] nums4 = {3, 3, 1, 3};
        int result4 = sol.findMin(nums4);
        System.out.println("  Standard: " + result4 + (result4 == 1 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums4));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums4));
        System.out.println("  Linear:   " + sol.findMinLinear(nums4));
        System.out.println("  Recursive:" + sol.findMinRecursive(nums4));
        
        // Test 5: No rotation with duplicates
        System.out.println("\nTest 5: [1,1,2,2,3,3] -> Expected: 1");
        int[] nums5 = {1, 1, 2, 2, 3, 3};
        int result5 = sol.findMin(nums5);
        System.out.println("  Standard: " + result5 + (result5 == 1 ? " ✓" : " ✗"));
        
        // Test 6: Single element
        System.out.println("\nTest 6: [1] -> Expected: 1");
        int[] nums6 = {1};
        int result6 = sol.findMin(nums6);
        System.out.println("  Standard: " + result6 + (result6 == 1 ? " ✓" : " ✗"));
        
        // Test 7: Two elements with duplicates
        System.out.println("\nTest 7: [2,2] -> Expected: 2");
        int[] nums7 = {2, 2};
        int result7 = sol.findMin(nums7);
        System.out.println("  Standard: " + result7 + (result7 == 2 ? " ✓" : " ✗"));
        
        // Test 8: Rotated with duplicates
        System.out.println("\nTest 8: [10,1,10,10,10] -> Expected: 1");
        int[] nums8 = {10, 1, 10, 10, 10};
        int result8 = sol.findMin(nums8);
        System.out.println("  Standard: " + result8 + (result8 == 1 ? " ✓" : " ✗"));
        System.out.println("  Left:     " + sol.findMinLeft(nums8));
        System.out.println("  Pivot:    " + sol.findMinPivot(nums8));
        System.out.println("  Linear:   " + sol.findMinLinear(nums8));
        System.out.println("  Recursive:" + sol.findMinRecursive(nums8));
        
        // Test 9: Negative with duplicates
        System.out.println("\nTest 9: [-2,-2,-1,-2] -> Expected: -2");
        int[] nums9 = {-2, -2, -1, -2};
        int result9 = sol.findMin(nums9);
        System.out.println("  Standard: " + result9 + (result9 == -2 ? " ✓" : " ✗"));
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        int[][] testCases = {
            {1, 3, 5},
            {2, 2, 2, 0, 1},
            {2, 2, 2, 2, 2},
            {3, 3, 1, 3},
            {1, 1, 2, 2, 3, 3},
            {1},
            {2, 2},
            {10, 1, 10, 10, 10},
            {-2, -2, -1, -2},
            {1, 1, 1, 0, 1}
        };
        int[] expected = {1, 0, 2, 1, 1, 1, 2, 1, -2, 0};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.findMin(testCases[i]);
            int r2 = sol.findMinLeft(testCases[i]);
            int r3 = sol.findMinPivot(testCases[i]);
            int r4 = sol.findMinLinear(testCases[i]);
            int r5 = sol.findMinRecursive(testCases[i]);
            
            boolean m1 = (r1 == expected[i]);
            boolean m2 = (r2 == expected[i]);
            boolean m3 = (r3 == expected[i]);
            boolean m4 = (r4 == expected[i]);
            boolean m5 = (r5 == expected[i]);
            boolean match = m1 && m2 && m3 && m4 && m5;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Std:" + r1 + " Left:" + r2 + " Piv:" + r3 + " Lin:" + r4 + " Rec:" + r5 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}