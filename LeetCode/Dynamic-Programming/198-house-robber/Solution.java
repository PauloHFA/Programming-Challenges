import java.util.*;

/**
 * LeetCode 198 - House Robber
 * 
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * 
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 * 
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.
 * 
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1). Total amount you can rob = 2 + 9 + 1 = 12.
 * 
 * Constraints:
 * - 1 <= nums.length <= 100
 * - 0 <= nums[i] <= 400
 */
public class Solution {

    // Approach 1: DP with Array (Standard)
    // dp[i] = max money robbing up to house i
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        
        return dp[n-1];
    }

    // Approach 2: DP with O(1) Space (Optimized)
    // Only need previous two values
    public int robO1Space(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        
        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < n; i++) {
            int curr = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = curr;
        }
        
        return prev1;
    }

    // Approach 3: Recursive with Memoization (Top-Down)
    public int robMemo(int[] nums) {
        int n = nums.length;
        Integer[] memo = new Integer[n];
        return robHelper(nums, n - 1, memo);
    }
    
    private int robHelper(int[] nums, int i, Integer[] memo) {
        if (i < 0) return 0;
        if (memo[i] != null) return memo[i];
        
        int rob = nums[i] + robHelper(nums, i - 2, memo);
        int skip = robHelper(nums, i - 1, memo);
        
        return memo[i] = Math.max(rob, skip);
    }

    // Approach 4: DP with Two Variables (Alternative formulation)
    // rob = max money if we rob current house
    // notRob = max money if we don't rob current house
    public int robTwoVars(int[] nums) {
        int rob = 0, notRob = 0;
        
        for (int num : nums) {
            int newRob = notRob + num;
            int newNotRob = Math.max(rob, notRob);
            rob = newRob;
            notRob = newNotRob;
        }
        
        return Math.max(rob, notRob);
    }

    // Approach 5: Tabulation with explicit choices
    public int robExplicit(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        // dp[i][0] = max money up to house i, not robbing house i
        // dp[i][1] = max money up to house i, robbing house i
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = nums[0];
        
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]);
            dp[i][1] = dp[i-1][0] + nums[i];
        }
        
        return Math.max(dp[n-1][0], dp[n-1][1]);
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== House Robber Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, new int[]{1,2,3,1}, 4, "Example 1");
        
        // Test 2: Example 2
        testCase(sol, new int[]{2,7,9,3,1}, 12, "Example 2");
        
        // Test 3: Single house
        testCase(sol, new int[]{5}, 5, "Single house");
        
        // Test 4: Two houses
        testCase(sol, new int[]{2,3}, 3, "Two houses");
        
        // Test 5: Two houses reverse
        testCase(sol, new int[]{3,2}, 3, "Two houses reverse");
        
        // Test 6: All same
        testCase(sol, new int[]{2,2,2,2,2}, 6, "All same");
        
        // Test 7: Increasing
        testCase(sol, new int[]{1,2,3,4,5}, 9, "Increasing");
        
        // Test 8: Decreasing
        testCase(sol, new int[]{5,4,3,2,1}, 9, "Decreasing");
        
        // Test 9: Alternating high/low
        testCase(sol, new int[]{10,1,10,1,10}, 30, "Alternating");
        
        // Test 10: Large array
        int[] large = new int[100];
        for (int i = 0; i < 100; i++) large[i] = i + 1;
        // Sum of even indices: 1+3+5+...+99 = 50*50 = 2500
        // Sum of odd indices: 2+4+6+...+100 = 50*51 = 2550
        testCase(sol, large, 2550, "Large array");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[][] testCases = {
            {1,2,3,1},
            {2,7,9,3,1},
            {5},
            {2,3},
            {3,2},
            {2,2,2,2,2},
            {1,2,3,4,5},
            {5,4,3,2,1},
            {10,1,10,1,10},
            large
        };
        
        boolean allMatch = true;
        for (int[] tc : testCases) {
            int r1 = sol.rob(tc);
            int r2 = sol.robO1Space(tc);
            int r3 = sol.robMemo(tc);
            int r4 = sol.robTwoVars(tc);
            int r5 = sol.robExplicit(tc);
            
            if (r1 != r2 || r1 != r3 || r1 != r4 || r1 != r5) {
                System.out.println("Mismatch for " + Arrays.toString(tc));
                System.out.printf("  DP: %d, O(1): %d, Memo: %d, TwoVars: %d, Explicit: %d%n", r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int[] nums, int expected, String name) {
        int r1 = sol.rob(nums);
        int r2 = sol.robO1Space(nums);
        int r3 = sol.robMemo(nums);
        int r4 = sol.robTwoVars(nums);
        int r5 = sol.robExplicit(nums);
        
        boolean pass = r1 == expected && r2 == expected && r3 == expected && r4 == expected && r5 == expected;
        
        System.out.printf("%s: %s -> Expected %d, Got %d %s%n", name, Arrays.toString(nums), expected, r1, pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  O(1): %d, Memo: %d, TwoVars: %d, Explicit: %d%n", r2, r3, r4, r5);
        }
    }
}