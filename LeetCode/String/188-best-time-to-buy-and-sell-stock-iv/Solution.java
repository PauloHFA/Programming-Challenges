import java.util.*;

/**
 * LeetCode 188 - Best Time to Buy and Sell Stock IV
 * 
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day,
 * and an integer k. Find the maximum profit you can achieve. You may complete at most k transactions.
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 * 
 * Example 1:
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 * 
 * Example 2:
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
 * Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * Total profit = 4 + 3 = 7.
 * 
 * Constraints:
 * - 1 <= k <= 100
 * - 1 <= prices.length <= 1000
 * - 0 <= prices[i] <= 1000
 */
public class Solution {

    // Approach 1: DP with 2D array (Standard)
    // dp[i][j] = max profit with at most i transactions up to day j
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1 || k == 0) return 0;
        
        // If k >= n/2, it's equivalent to unlimited transactions (Problem 122)
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
            }
            return profit;
        }
        
        int[][] dp = new int[k + 1][n];
        
        for (int i = 1; i <= k; i++) {
            int maxDiff = -prices[0];
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i][j-1], prices[j] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[i-1][j] - prices[j]);
            }
        }
        
        return dp[k][n-1];
    }

    // Approach 2: DP with 1D array (Space Optimized)
    public int maxProfit1D(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1 || k == 0) return 0;
        
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
            }
            return profit;
        }
        
        int[] dp = new int[n];
        
        for (int i = 1; i <= k; i++) {
            int maxDiff = -prices[0];
            int prev = 0;
            for (int j = 1; j < n; j++) {
                int temp = dp[j];
                dp[j] = Math.max(dp[j-1], prices[j] + maxDiff);
                maxDiff = Math.max(maxDiff, prev - prices[j]);
                prev = temp;
            }
        }
        
        return dp[n-1];
    }

    // Approach 3: DP with buy/sell arrays (Alternative formulation)
    // buy[i] = max profit after i-th buy
    // sell[i] = max profit after i-th sell
    public int maxProfitBuySell(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1 || k == 0) return 0;
        
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
            }
            return profit;
        }
        
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        Arrays.fill(buy, Integer.MIN_VALUE);
        
        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                buy[i] = Math.max(buy[i], sell[i-1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        
        return sell[k];
    }

    // Approach 4: Recursive with Memoization (Top-Down)
    public int maxProfitMemo(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1 || k == 0) return 0;
        
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
            }
            return profit;
        }
        
        Integer[][][] memo = new Integer[n][k + 1][2];
        return dfs(prices, 0, k, 0, memo);
    }
    
    private int dfs(int[] prices, int day, int k, int holding, Integer[][][] memo) {
        if (day == prices.length || k == 0) return 0;
        if (memo[day][k][holding] != null) return memo[day][k][holding];
        
        int skip = dfs(prices, day + 1, k, holding, memo);
        
        if (holding == 1) {
            // Can sell
            int sell = prices[day] + dfs(prices, day + 1, k - 1, 0, memo);
            return memo[day][k][holding] = Math.max(skip, sell);
        } else {
            // Can buy
            int buy = -prices[day] + dfs(prices, day + 1, k, 1, memo);
            return memo[day][k][holding] = Math.max(skip, buy);
        }
    }

    // Approach 5: Alternative DP formulation (using transaction pairs)
        // This is essentially the same as Approach 3 but with explicit DP table
        public int maxProfitAlt(int k, int[] prices) {
            int n = prices.length;
            if (n <= 1 || k == 0) return 0;
        
            if (k >= n / 2) {
                int profit = 0;
                for (int i = 1; i < n; i++) {
                    if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
                }
                return profit;
            }
        
            // dp[t][d] = max profit with t transactions up to day d
            int[][] dp = new int[k + 1][n];
        
            for (int t = 1; t <= k; t++) {
                int maxDiff = -prices[0];
                for (int d = 1; d < n; d++) {
                    dp[t][d] = Math.max(dp[t][d-1], prices[d] + maxDiff);
                    maxDiff = Math.max(maxDiff, dp[t-1][d] - prices[d]);
                }
            }
        
            return dp[k][n-1];
        }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Best Time to Buy and Sell Stock IV Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, 2, new int[]{2, 4, 1}, 2, "Example 1");
        
        // Test 2: Example 2
        testCase(sol, 2, new int[]{3, 2, 6, 5, 0, 3}, 7, "Example 2");
        
        // Test 3: k = 1 (Problem 121)
        testCase(sol, 1, new int[]{7, 1, 5, 3, 6, 4}, 5, "k=1");
        
        // Test 4: k >= n/2 (unlimited)
        testCase(sol, 100, new int[]{1, 2, 3, 4, 5}, 4, "Unlimited");
        
        // Test 5: Decreasing prices
        testCase(sol, 2, new int[]{5, 4, 3, 2, 1}, 0, "Decreasing");
        
        // Test 6: Single price
        testCase(sol, 2, new int[]{5}, 0, "Single price");
        
        // Test 7: Two prices, profit possible
        testCase(sol, 1, new int[]{1, 2}, 1, "Two prices profit");
        
        // Test 8: Two prices, no profit
        testCase(sol, 1, new int[]{2, 1}, 0, "Two prices no profit");
        
        // Test 9: Multiple transactions
                testCase(sol, 3, new int[]{1, 3, 2, 8, 4, 9}, 13, "Multiple transactions");
        
        // Test 10: k=2, complex
                testCase(sol, 2, new int[]{6, 1, 3, 2, 4, 7}, 7, "Complex k=2");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[][] testK = {{2}, {2}, {1}, {100}, {2}, {2}, {1}, {1}, {3}, {2}};
        int[][] testPrices = {
            {2, 4, 1},
            {3, 2, 6, 5, 0, 3},
            {7, 1, 5, 3, 6, 4},
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {5},
            {1, 2},
            {2, 1},
            {1, 3, 2, 8, 4, 9},
            {6, 1, 3, 2, 4, 7}
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testK.length; i++) {
            int k = testK[i][0];
            int[] prices = testPrices[i];
            
            int r1 = sol.maxProfit(k, prices);
            int r2 = sol.maxProfit1D(k, prices);
            int r3 = sol.maxProfitBuySell(k, prices);
            int r4 = sol.maxProfitMemo(k, prices);
                    int r5 = sol.maxProfitAlt(k, prices);
            
            if (r1 != r2 || r1 != r3 || r1 != r4 || r1 != r5) {
                System.out.println("Mismatch for k=" + k + ", prices=" + Arrays.toString(prices));
                        System.out.printf("  2D: %d, 1D: %d, BuySell: %d, Memo: %d, Alt: %d%n", r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int k, int[] prices, int expected, String name) {
        int r1 = sol.maxProfit(k, prices);
        int r2 = sol.maxProfit1D(k, prices);
        int r3 = sol.maxProfitBuySell(k, prices);
        int r4 = sol.maxProfitMemo(k, prices);
            int r5 = sol.maxProfitAlt(k, prices);
        
        boolean pass = r1 == expected && r2 == expected && r3 == expected && r4 == expected && r5 == expected;
        
        System.out.printf("%s: k=%d, prices=%s -> Expected %d, Got %d %s%n", 
            name, k, Arrays.toString(prices), expected, r1, pass ? "✓" : "✗");
        if (!pass) {
                System.out.printf("  1D: %d, BuySell: %d, Memo: %d, Alt: %d%n", r2, r3, r4, r5);
        }
    }
}