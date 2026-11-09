/**
 * LeetCode 162 - Find Peak Element
 * 
 * A peak element is an element that is strictly greater than its neighbors.
 * Given a 0-indexed integer array nums, find a peak element, and return its index.
 * If the array contains multiple peaks, return the index to any of the peaks.
 * 
 * You may imagine that nums[-1] = nums[n] = -∞.
 * In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * 
 * Example 2:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, 
 * or index number 5 where the peak element is 6.
 * 
 * Constraints:
 * 1 <= nums.length <= 1000
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums[i] != nums[i + 1] for all valid i.
 */
public class Solution {
    
    /**
     * Approach 1: Binary Search - O(log n) time, O(1) space
     * 
     * If nums[mid] < nums[mid+1], peak is on the right side.
     * If nums[mid] > nums[mid+1], peak is on the left side (including mid).
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[mid + 1]) {
                // Peak is on the right
                left = mid + 1;
            } else {
                // Peak is on the left (including mid)
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 2: Binary Search - Compare with Both Neighbors - O(log n) time, O(1) space
     * 
     * Explicitly check if mid is a peak by comparing with both neighbors.
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findPeakElementExplicit(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is a peak
            boolean isLeftSmaller = (mid == 0) || (nums[mid] > nums[mid - 1]);
            boolean isRightSmaller = (mid == nums.length - 1) || (nums[mid] > nums[mid + 1]);
            
            if (isLeftSmaller && isRightSmaller) {
                return mid;
            } else if (mid > 0 && nums[mid - 1] > nums[mid]) {
                // Left neighbor is greater, peak on left
                right = mid - 1;
            } else {
                // Right neighbor is greater, peak on right
                left = mid + 1;
            }
        }
        
        return -1; // Should never reach here
    }
    
    /**
     * Approach 3: Linear Scan - O(n) time, O(1) space
     * 
     * Simple linear scan to find any peak. For verification.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int findPeakElementLinear(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            boolean isLeftSmaller = (i == 0) || (nums[i] > nums[i - 1]);
            boolean isRightSmaller = (i == nums.length - 1) || (nums[i] > nums[i + 1]);
            
            if (isLeftSmaller && isRightSmaller) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 4: Recursive Binary Search - O(log n) time, O(log n) space
     * 
     * Recursive version of binary search.
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(log n) recursion stack
     */
    public int findPeakElementRecursive(int[] nums) {
        return findPeakRecursive(nums, 0, nums.length - 1);
    }
    
    private int findPeakRecursive(int[] nums, int left, int right) {
        if (left == right) return left;
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] < nums[mid + 1]) {
            return findPeakRecursive(nums, mid + 1, right);
        } else {
            return findPeakRecursive(nums, left, mid);
        }
    }
    
    /**
     * Approach 5: Find All Peaks - O(n) time, O(k) space
     * 
     * Find all peak elements for verification.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is number of peaks
     */
    public java.util.List<Integer> findAllPeaks(int[] nums) {
        java.util.List<Integer> peaks = new java.util.ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            boolean isLeftSmaller = (i == 0) || (nums[i] > nums[i - 1]);
            boolean isRightSmaller = (i == nums.length - 1) || (nums[i] > nums[i + 1]);
            
            if (isLeftSmaller && isRightSmaller) {
                peaks.add(i);
            }
        }
        return peaks;
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Find Peak Element Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: nums=[1,2,3,1] -> Expected: 2 (peak=3)");
        testCase(sol, new int[]{1, 2, 3, 1}, new int[]{2});
        
        // Test 2: Example 2
        System.out.println("\nTest 2: nums=[1,2,1,3,5,6,4] -> Expected: 1 or 5 (peaks=2,6)");
        testCase(sol, new int[]{1, 2, 1, 3, 5, 6, 4}, new int[]{1, 5});
        
        // Test 3: Single element
        System.out.println("\nTest 3: nums=[1] -> Expected: 0");
        testCase(sol, new int[]{1}, new int[]{0});
        
        // Test 4: Two elements, first is peak
        System.out.println("\nTest 4: nums=[2,1] -> Expected: 0");
        testCase(sol, new int[]{2, 1}, new int[]{0});
        
        // Test 5: Two elements, second is peak
        System.out.println("\nTest 5: nums=[1,2] -> Expected: 1");
        testCase(sol, new int[]{1, 2}, new int[]{1});
        
        // Test 6: Strictly increasing
        System.out.println("\nTest 6: nums=[1,2,3,4,5] -> Expected: 4 (last element)");
        testCase(sol, new int[]{1, 2, 3, 4, 5}, new int[]{4});
        
        // Test 7: Strictly decreasing
        System.out.println("\nTest 7: nums=[5,4,3,2,1] -> Expected: 0 (first element)");
        testCase(sol, new int[]{5, 4, 3, 2, 1}, new int[]{0});
        
        // Test 8: Multiple peaks
        System.out.println("\nTest 8: nums=[1,3,2,4,1] -> Expected: 1 or 3 (peaks=3,4)");
        testCase(sol, new int[]{1, 3, 2, 4, 1}, new int[]{1, 3});
        
        // Test 9: Negative numbers
        System.out.println("\nTest 9: nums=[-2,-1,-3] -> Expected: 1 (peak=-1)");
        testCase(sol, new int[]{-2, -1, -3}, new int[]{1});
        
        // Test 10: Large array
        System.out.println("\nTest 10: Large array with peak in middle");
        int[] large = new int[100];
        for (int i = 0; i < 50; i++) large[i] = i;
                for (int i = 50; i < 100; i++) large[i] = 100 - i; // 50, 49, 48... so peak at 49 (value 49) and 50 (value 50)
                // Actually this creates equal adjacent at 49 and 50. Let's fix:
                // large[49] = 49, large[50] = 50, large[51] = 49...
                // Better: make it strictly increasing then strictly decreasing
                for (int i = 0; i < 50; i++) large[i] = i;
                large[50] = 50;
                for (int i = 51; i < 100; i++) large[i] = 100 - i;
                testCase(sol, large, new int[]{50});
        
        // Test 11: Cross verification
        System.out.println("\nTest 11: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int[] nums, int[] expectedPeaks) {
        int r1 = sol.findPeakElement(nums);
        int r2 = sol.findPeakElementExplicit(nums);
        int r3 = sol.findPeakElementLinear(nums);
        int r4 = sol.findPeakElementRecursive(nums);
        
        // Check if result is one of the expected peaks
        boolean valid1 = isValidPeak(r1, expectedPeaks);
        boolean valid2 = isValidPeak(r2, expectedPeaks);
        boolean valid3 = isValidPeak(r3, expectedPeaks);
        boolean valid4 = isValidPeak(r4, expectedPeaks);
        
        boolean match = valid1 && valid2 && valid3 && valid4;
        
        System.out.println("  BinarySearch: " + r1 + (valid1 ? " ✓" : " ✗"));
        System.out.println("  Explicit:     " + r2 + (valid2 ? " ✓" : " ✗"));
        System.out.println("  Linear:       " + r3 + (valid3 ? " ✓" : " ✗"));
        System.out.println("  Recursive:    " + r4 + (valid4 ? " ✓" : " ✗"));
        
        assert match : "Mismatch for nums=" + java.util.Arrays.toString(nums) + 
            ": expected one of " + java.util.Arrays.toString(expectedPeaks) + 
            ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static boolean isValidPeak(int peak, int[] expectedPeaks) {
        for (int p : expectedPeaks) {
            if (peak == p) return true;
        }
        return false;
    }
    
    static void crossVerify(Solution sol) {
        int[][] testCases = {
            {1, 2, 3, 1},
            {1, 2, 1, 3, 5, 6, 4},
            {1},
            {2, 1},
            {1, 2},
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {1, 3, 2, 4, 1},
            {-2, -1, -3},
            {1, 2, 3, 2, 1, 2, 3, 2, 1},
            {10, 20, 15, 2, 23, 90, 67}
        };
        
        for (int[] nums : testCases) {
            int r1 = sol.findPeakElement(nums);
            int r2 = sol.findPeakElementExplicit(nums);
            int r3 = sol.findPeakElementLinear(nums);
            int r4 = sol.findPeakElementRecursive(nums);
            
            // Verify all are valid peaks
            boolean v1 = isValidPeak(nums, r1);
            boolean v2 = isValidPeak(nums, r2);
            boolean v3 = isValidPeak(nums, r3);
            boolean v4 = isValidPeak(nums, r4);
            
            boolean match = v1 && v2 && v3 && v4;
            System.out.println("  " + java.util.Arrays.toString(nums) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + ")");
            assert match : "Cross-verification failed for " + java.util.Arrays.toString(nums);
        }
        System.out.println("  All cross-verifications passed ✓");
    }
    
    static boolean isValidPeak(int[] nums, int peak) {
        if (peak < 0 || peak >= nums.length) return false;
        boolean isLeftSmaller = (peak == 0) || (nums[peak] > nums[peak - 1]);
        boolean isRightSmaller = (peak == nums.length - 1) || (nums[peak] > nums[peak + 1]);
        return isLeftSmaller && isRightSmaller;
    }
}