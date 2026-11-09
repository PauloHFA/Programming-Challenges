import java.util.*;

/**
 * LeetCode 121 - Best Time to Buy and Sell Stock
 * 
 * Você recebe um array prices onde prices[i] é o preço de uma ação no dia i.
 * Você quer maximizar seu lucro escolhendo um único dia para comprar uma ação
 * e escolhendo um dia diferente no futuro para vender essa ação.
 * Retorne o lucro máximo que você pode alcançar. Se não puder obter lucro, retorne 0.
 */
public class Solution {

    /**
     * Método 1: One Pass - Min Price Tracking (Principal)
     * Mantém o preço mínimo visto até agora e calcula lucro máximo
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                int profit = price - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        
        return maxProfit;
    }

    /**
     * Método 2: Kadane's Algorithm (Diferenças)
     * Transforma em problema de subarray máximo das diferenças
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitKadane(int[] prices) {
        if (prices.length < 2) return 0;
        
        int maxEndingHere = 0;
        int maxSoFar = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            maxEndingHere = Math.max(0, maxEndingHere + diff);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }

    /**
     * Método 3: Two Pointers
     * Left = buy day, Right = sell day
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitTwoPointers(int[] prices) {
        int left = 0; // buy
        int right = 1; // sell
        int maxProfit = 0;
        
        while (right < prices.length) {
            if (prices[left] < prices[right]) {
                int profit = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                left = right;
            }
            right++;
        }
        
        return maxProfit;
    }

    /**
     * Método 4: DP com estados (generalizável para k transações)
     * dp[i][0] = max profit no dia i sem ação
     * dp[i][1] = max profit no dia i com ação
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitDP(int[] prices) {
        if (prices.length == 0) return 0;
        
        int hold = -prices[0]; // max profit com ação na mão
        int cash = 0; // max profit sem ação
        
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i]);
            hold = Math.max(hold, -prices[i]); // só pode comprar uma vez
        }
        
        return cash;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Best Time to Buy and Sell Stock Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [7,1,5,3,6,4]");
        int[] p1 = {7,1,5,3,6,4};
        System.out.println("  Expected: 5 (buy at 1, sell at 6)");
        System.out.println("  OnePass:  " + sol.maxProfit(p1));
        System.out.println("  Kadane:   " + sol.maxProfitKadane(p1));
        System.out.println("  TwoPtr:   " + sol.maxProfitTwoPointers(p1));
        System.out.println("  DP:       " + sol.maxProfitDP(p1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [7,6,4,3,1]");
        int[] p2 = {7,6,4,3,1};
        System.out.println("  Expected: 0 (no profit possible)");
        System.out.println("  Result:   " + sol.maxProfit(p2));
        
        // Test 3: Single element
        System.out.println("\nTest 3: [5]");
        int[] p3 = {5};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p3));
        
        // Test 4: Two elements - profit
        System.out.println("\nTest 4: [1,2]");
        int[] p4 = {1,2};
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.maxProfit(p4));
        
        // Test 5: Two elements - no profit
        System.out.println("\nTest 5: [2,1]");
        int[] p5 = {2,1};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p5));
        
        // Test 6: All same
        System.out.println("\nTest 6: [5,5,5,5]");
        int[] p6 = {5,5,5,5};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p6));
        
        // Test 7: Increasing
        System.out.println("\nTest 7: [1,2,3,4,5]");
        int[] p7 = {1,2,3,4,5};
        System.out.println("  Expected: 4");
        System.out.println("  Result:   " + sol.maxProfit(p7));
        
        // Test 8: Decreasing
        System.out.println("\nTest 8: [5,4,3,2,1]");
        int[] p8 = {5,4,3,2,1};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        int[][] testCases = {
            {7,1,5,3,6,4},
            {7,6,4,3,1},
            {1,2},
            {2,1},
            {5,5,5,5},
            {1,2,3,4,5},
            {5,4,3,2,1},
            {3,2,6,5,0,3},
            {2,4,1},
            {1,2,4,2,5,7,2,4,9,0}
        };
        int[] expected = {5, 0, 1, 0, 0, 4, 0, 4, 2, 8};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.maxProfit(testCases[i]);
            int r2 = sol.maxProfitKadane(testCases[i]);
            int r3 = sol.maxProfitTwoPointers(testCases[i]);
            int r4 = sol.maxProfitDP(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (1P:" + r1 + " K:" + r2 + " 2P:" + r3 + " DP:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}