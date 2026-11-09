/**
 * LeetCode 152 - Maximum Product Subarray
 * 
 * Given an integer array nums, find a subarray that has the largest product, and return the product.
 * 
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 * 
 * Example 1:
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * 
 * Example 2:
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 * 
 * Constraints:
 * 1 <= nums.length <= 2 * 10^4
 * -10 <= nums[i] <= 10
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 */
public class Solution {
    
    /**
     * Approach 1: Dynamic Programming - Track Max and Min
     * 
     * Since negative numbers can flip max to min and vice versa,
     * we track both max and min product ending at each position.
     * 
     * maxEndingHere = max(nums[i], maxEndingHere * nums[i], minEndingHere * nums[i])
     * minEndingHere = min(nums[i], maxEndingHere * nums[i], minEndingHere * nums[i])
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxEndingHere = nums[0];
        int minEndingHere = nums[0];
        int maxSoFar = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            // When num is negative, max and min swap
            if (num < 0) {
                int temp = maxEndingHere;
                maxEndingHere = minEndingHere;
                minEndingHere = temp;
            }
            
            maxEndingHere = Math.max(num, maxEndingHere * num);
            minEndingHere = Math.min(num, minEndingHere * num);
            
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    /**
     * Approach 2: DP with Explicit Temp Variables (Clearer)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxProductExplicit(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxEndingHere = nums[0];
        int minEndingHere = nums[0];
        int maxSoFar = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            int tempMax = Math.max(num, Math.max(maxEndingHere * num, minEndingHere * num));
            int tempMin = Math.min(num, Math.min(maxEndingHere * num, minEndingHere * num));
            
            maxEndingHere = tempMax;
            minEndingHere = tempMin;
            
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    /**
     * Approach 3: Prefix/Suffix Products (Alternative)
     * 
     * The maximum product subarray is either a prefix or suffix product
     * (or the whole array). This works because zeros reset the product.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxProductPrefixSuffix(int[] nums) {
        int n = nums.length;
        int maxProduct = nums[0];
        
        // Left to right
        int product = 1;
        for (int i = 0; i < n; i++) {
            product *= nums[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) product = 1;
        }
        
        // Right to left
        product = 1;
        for (int i = n - 1; i >= 0; i--) {
            product *= nums[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) product = 1;
        }
        
        return maxProduct;
    }
    
    /**
     * Approach 4: Divide and Conquer
     * 
     * Recursively find max product in left half, right half, and crossing middle.
         * Need to track both max and min for crossing products due to negatives.
         * 
         * Time Complexity: O(n log n)
         * Space Complexity: O(log n) recursion stack
         */
        public int maxProductDivideConquer(int[] nums) {
            return maxProductDC(nums, 0, nums.length - 1).max;
        }
    
        private static class MinMax {
            int max;
            int min;
            MinMax(int max, int min) {
                this.max = max;
                this.min = min;
            }
        }
    
        private MinMax maxProductDC(int[] nums, int left, int right) {
            if (left == right) {
                return new MinMax(nums[left], nums[left]);
            }
        
            int mid = left + (right - left) / 2;
        
            MinMax leftResult = maxProductDC(nums, left, mid);
            MinMax rightResult = maxProductDC(nums, mid + 1, right);
        
            // Max crossing middle - need both max and min from each side
            MinMax crossResult = maxCrossingProduct(nums, left, mid, right);
        
            int overallMax = Math.max(Math.max(leftResult.max, rightResult.max), crossResult.max);
            int overallMin = Math.min(Math.min(leftResult.min, rightResult.min), crossResult.min);
        
            return new MinMax(overallMax, overallMin);
        }
    
        private MinMax maxCrossingProduct(int[] nums, int left, int mid, int right) {
            // Max and min suffix product from left half
            int maxLeftSuffix = Integer.MIN_VALUE;
            int minLeftSuffix = Integer.MAX_VALUE;
            int product = 1;
            for (int i = mid; i >= left; i--) {
                product *= nums[i];
                maxLeftSuffix = Math.max(maxLeftSuffix, product);
                minLeftSuffix = Math.min(minLeftSuffix, product);
            }
        
            // Max and min prefix product from right half
            int maxRightPrefix = Integer.MIN_VALUE;
            int minRightPrefix = Integer.MAX_VALUE;
            product = 1;
            for (int i = mid + 1; i <= right; i++) {
                product *= nums[i];
                maxRightPrefix = Math.max(maxRightPrefix, product);
                minRightPrefix = Math.min(minRightPrefix, product);
            }
        
            // Crossing max can be: maxLeft * maxRight, minLeft * minRight
            int crossMax = Math.max(maxLeftSuffix * maxRightPrefix, minLeftSuffix * minRightPrefix);
            int crossMin = Math.min(maxLeftSuffix * minRightPrefix, minLeftSuffix * maxRightPrefix);
        
            return new MinMax(crossMax, crossMin);
        }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Maximum Product Subarray Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [2,3,-2,4] -> Expected: 6");
        int[] nums1 = {2, 3, -2, 4};
        int result1 = sol.maxProduct(nums1);
        System.out.println("  DP:       " + result1 + (result1 == 6 ? " ✓" : " ✗"));
        System.out.println("  Explicit: " + sol.maxProductExplicit(nums1));
        System.out.println("  Prefix:   " + sol.maxProductPrefixSuffix(nums1));
        System.out.println("  DivConq:  " + sol.maxProductDivideConquer(nums1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [-2,0,-1] -> Expected: 0");
        int[] nums2 = {-2, 0, -1};
        int result2 = sol.maxProduct(nums2);
        System.out.println("  DP:       " + result2 + (result2 == 0 ? " ✓" : " ✗"));
        System.out.println("  Explicit: " + sol.maxProductExplicit(nums2));
        System.out.println("  Prefix:   " + sol.maxProductPrefixSuffix(nums2));
        System.out.println("  DivConq:  " + sol.maxProductDivideConquer(nums2));
        
        // Test 3: All positive
        System.out.println("\nTest 3: [1,2,3,4,5] -> Expected: 120");
        int[] nums3 = {1, 2, 3, 4, 5};
        int result3 = sol.maxProduct(nums3);
        System.out.println("  DP:       " + result3 + (result3 == 120 ? " ✓" : " ✗"));
        
        // Test 4: All negative (even count)
        System.out.println("\nTest 4: [-2,-3,-4,-5] -> Expected: 120");
        int[] nums4 = {-2, -3, -4, -5};
        int result4 = sol.maxProduct(nums4);
        System.out.println("  DP:       " + result4 + (result4 == 120 ? " ✓" : " ✗"));
        
        // Test 5: All negative (odd count)
        System.out.println("\nTest 5: [-2,-3,-4] -> Expected: 12");
        int[] nums5 = {-2, -3, -4};
        int result5 = sol.maxProduct(nums5);
        System.out.println("  DP:       " + result5 + (result5 == 12 ? " ✓" : " ✗"));
        
        // Test 6: With zeros
        System.out.println("\nTest 6: [0,2,3,-2,4,0,-1] -> Expected: 6");
        int[] nums6 = {0, 2, 3, -2, 4, 0, -1};
        int result6 = sol.maxProduct(nums6);
        System.out.println("  DP:       " + result6 + (result6 == 6 ? " ✓" : " ✗"));
        
        // Test 7: Single element
        System.out.println("\nTest 7: [5] -> Expected: 5");
        int[] nums7 = {5};
        int result7 = sol.maxProduct(nums7);
        System.out.println("  DP:       " + result7 + (result7 == 5 ? " ✓" : " ✗"));
        
        // Test 8: Single negative
        System.out.println("\nTest 8: [-5] -> Expected: -5");
        int[] nums8 = {-5};
        int result8 = sol.maxProduct(nums8);
        System.out.println("  DP:       " + result8 + (result8 == -5 ? " ✓" : " ✗"));
        
        // Test 9: Mixed with large numbers
        System.out.println("\nTest 9: [-2,3,-4] -> Expected: 24");
        int[] nums9 = {-2, 3, -4};
        int result9 = sol.maxProduct(nums9);
        System.out.println("  DP:       " + result9 + (result9 == 24 ? " ✓" : " ✗"));
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        int[][] testCases = {
            {2, 3, -2, 4},
            {-2, 0, -1},
            {1, 2, 3, 4, 5},
            {-2, -3, -4, -5},
            {-2, -3, -4},
            {0, 2, 3, -2, 4, 0, -1},
            {5},
            {-5},
            {-2, 3, -4},
            {2, -5, -2, -4, 3}
        };
                int[] expected = {6, 0, 120, 120, 12, 6, 5, -5, 24, 24};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.maxProduct(testCases[i]);
            int r2 = sol.maxProductExplicit(testCases[i]);
            int r3 = sol.maxProductPrefixSuffix(testCases[i]);
            int r4 = sol.maxProductDivideConquer(testCases[i]);
            
            boolean m1 = (r1 == expected[i]);
            boolean m2 = (r2 == expected[i]);
            boolean m3 = (r3 == expected[i]);
            boolean m4 = (r4 == expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (DP:" + r1 + " Exp:" + r2 + " Pre:" + r3 + " DC:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}