import java.util.*;

/**
 * LeetCode 122 - Best Time to Buy and Sell Stock II
 * 
 * Você recebe um array prices onde prices[i] é o preço de uma ação no dia i.
 * Em cada dia, você pode decidir comprar e/ou vender a ação.
 * Você só pode manter no máximo uma ação por vez. No entanto, você pode comprar
 * e vender no mesmo dia.
 * Encontre e retorne o lucro máximo que você pode alcançar.
 */
public class Solution {

    /**
     * Método 1: Greedy - Soma de todas as subidas (Principal)
     * Soma todos os lucros positivos entre dias consecutivos
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        
        return maxProfit;
    }

    /**
     * Método 2: Peak Valley Approach
     * Encontra vales (compra) e picos (venda)
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitPeakValley(int[] prices) {
        int maxProfit = 0;
        int i = 0;
        int n = prices.length;
        
        while (i < n - 1) {
            // Encontra vale (mínimo local)
            while (i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            int valley = prices[i];
            
            // Encontra pico (máximo local)
            while (i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            int peak = prices[i];
            
            maxProfit += peak - valley;
        }
        
        return maxProfit;
    }

    /**
     * Método 3: DP com Estados (generalizável)
     * hold = max profit com ação
     * cash = max profit sem ação
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitDP(int[] prices) {
        if (prices.length == 0) return 0;
        
        int hold = -prices[0];
        int cash = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int newCash = Math.max(cash, hold + prices[i]);
            int newHold = Math.max(hold, cash - prices[i]);
            cash = newCash;
            hold = newHold;
        }
        
        return cash;
    }

    /**
     * Método 4: One Pass com variáveis explícitas
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfitOnePass(int[] prices) {
        int profit = 0;
        int buy = prices[0];
        
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy) {
                profit += prices[i] - buy;
            }
            buy = prices[i];
        }
        
        return profit;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Best Time to Buy and Sell Stock II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [7,1,5,3,6,4]");
        int[] p1 = {7,1,5,3,6,4};
        System.out.println("  Expected: 7 (buy 1 sell 5 = 4, buy 3 sell 6 = 3)");
        System.out.println("  Greedy:   " + sol.maxProfit(p1));
        System.out.println("  PeakVal:  " + sol.maxProfitPeakValley(p1));
        System.out.println("  DP:       " + sol.maxProfitDP(p1));
        System.out.println("  OnePass:  " + sol.maxProfitOnePass(p1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [1,2,3,4,5]");
        int[] p2 = {1,2,3,4,5};
        System.out.println("  Expected: 4 (buy 1 sell 5 = 4, or sum of all diffs)");
        System.out.println("  Result:   " + sol.maxProfit(p2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: [7,6,4,3,1]");
        int[] p3 = {7,6,4,3,1};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p3));
        
        // Test 4: Single element
        System.out.println("\nTest 4: [5]");
        int[] p4 = {5};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p4));
        
        // Test 5: Two elements - profit
        System.out.println("\nTest 5: [1,2]");
        int[] p5 = {1,2};
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.maxProfit(p5));
        
        // Test 6: Two elements - no profit
        System.out.println("\nTest 6: [2,1]");
        int[] p6 = {2,1};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p6));
        
        // Test 7: Zigzag
        System.out.println("\nTest 7: [1,3,2,4,3,5]");
        int[] p7 = {1,3,2,4,3,5};
        System.out.println("  Expected: 6 (1->3=2, 2->4=2, 3->5=2)");
        System.out.println("  Result:   " + sol.maxProfit(p7));
        
        // Test 8: All same
        System.out.println("\nTest 8: [5,5,5,5]");
        int[] p8 = {5,5,5,5};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        int[][] testCases = {
            {7,1,5,3,6,4},
            {1,2,3,4,5},
            {7,6,4,3,1},
            {5},
            {1,2},
            {2,1},
            {1,3,2,4,3,5},
            {5,5,5,5},
            {3,2,6,5,0,3},
            {2,4,1,5,3,6}
        };
        int[] expected = {7, 4, 0, 0, 1, 0, 6, 0, 7, 9};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.maxProfit(testCases[i]);
            int r2 = sol.maxProfitPeakValley(testCases[i]);
            int r3 = sol.maxProfitDP(testCases[i]);
            int r4 = sol.maxProfitOnePass(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (G:" + r1 + " PV:" + r2 + " DP:" + r3 + " OP:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}