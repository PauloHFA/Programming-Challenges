import java.util.Arrays;

/**
 * LeetCode 0053: Maximum Subarray
 * Difficulty: Medium
 * Category: Arrays & Hashing / Dynamic Programming
 * 
 * Approach: Kadane's Algorithm (O(n) time, O(1) space)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Either extend the previous subarray or start new at current element
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // Alternative: Divide and Conquer (O(n log n) time, O(log n) space)
    public int maxSubArrayDivideConquer(int[] nums) {
        return maxSubArrayHelper(nums, 0, nums.length - 1);
    }

    private int maxSubArrayHelper(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + (right - left) / 2;

        int leftMax = maxSubArrayHelper(nums, left, mid);
        int rightMax = maxSubArrayHelper(nums, mid + 1, right);
        int crossMax = maxCrossingSum(nums, left, mid, right);

        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private int maxCrossingSum(int[] nums, int left, int mid, int right) {
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }

        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }

        return leftSum + rightSum;
    }

    // Alternative: DP with array (O(n) time, O(n) space)
    public int maxSubArrayDP(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int maxSum = dp[0];

        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            maxSum = Math.max(maxSum, dp[i]);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result1 = solver.maxSubArray(nums1);
        System.out.println("Test 1: " + result1 + " (Expected: 6)");

        // Test Case 2
        int[] nums2 = {1};
        int result2 = solver.maxSubArray(nums2);
        System.out.println("Test 2: " + result2 + " (Expected: 1)");

        // Test Case 3
        int[] nums3 = {5, 4, -1, 7, 8};
        int result3 = solver.maxSubArray(nums3);
        System.out.println("Test 3: " + result3 + " (Expected: 23)");

        // Test Case 4: All negative
        int[] nums4 = {-2, -1, -3, -4};
        int result4 = solver.maxSubArray(nums4);
        System.out.println("Test 4: " + result4 + " (Expected: -1)");

        // Test Case 5: All positive
        int[] nums5 = {1, 2, 3, 4, 5};
        int result5 = solver.maxSubArray(nums5);
        System.out.println("Test 5: " + result5 + " (Expected: 15)");

        // Test Case 6: Single negative
        int[] nums6 = {-1};
        int result6 = solver.maxSubArray(nums6);
        System.out.println("Test 6: " + result6 + " (Expected: -1)");

        // Test Case 7: Mixed with zero
        int[] nums7 = {0, -1, 2, -3, 4};
        int result7 = solver.maxSubArray(nums7);
        System.out.println("Test 7: " + result7 + " (Expected: 4)");

        // Test Case 8: Large array
        int[] nums8 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums8[i] = (i % 2 == 0) ? 1 : -1;
        }
        int result8 = solver.maxSubArray(nums8);
        System.out.println("Test 8: " + result8 + " (Expected: 1)");

        // Verify all approaches match
        System.out.println("\n--- Verification (Kadane vs DivideConquer vs DP) ---");
        int[][] testCases = {nums1, nums2, nums3, nums4, nums5, nums6, nums7};
        for (int[] test : testCases) {
            int kadane = solver.maxSubArray(test.clone());
            int dc = solver.maxSubArrayDivideConquer(test.clone());
            int dp = solver.maxSubArrayDP(test.clone());
            System.out.println("Input: " + Arrays.toString(test) + " -> Kadane: " + kadane + ", DC: " + dc + ", DP: " + dp + " (match: " + (kadane == dc && dc == dp) + ")");
        }
    }
}