import java.util.*;

/**
 * LeetCode 123 - Best Time to Buy and Sell Stock III
 * 
 * Você recebe um array prices onde prices[i] é o preço de uma ação no dia i.
 * Encontre o lucro máximo que você pode alcançar. Você pode completar no máximo
 * duas transações.
 * Nota: Você não pode participar de múltiplas transações simultaneamente
 * (você deve vender a ação antes de comprar novamente).
 */
public class Solution {

    /**
     * Método 1: DP com 4 variáveis (Principal)
     * buy1 = max profit após 1ª compra
     * sell1 = max profit após 1ª venda
     * buy2 = max profit após 2ª compra
     * sell2 = max profit após 2ª venda
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxProfit(int[] prices) {
        int buy1 = Integer.MIN_VALUE, sell1 = 0;
        int buy2 = Integer.MIN_VALUE, sell2 = 0;
        
        for (int price : prices) {
            buy1 = Math.max(buy1, -price);           // 1ª compra: gasta dinheiro
            sell1 = Math.max(sell1, buy1 + price);   // 1ª venda: ganha dinheiro
            buy2 = Math.max(buy2, sell1 - price);    // 2ª compra: usa lucro da 1ª venda
            sell2 = Math.max(sell2, buy2 + price);   // 2ª venda: ganha dinheiro
        }
        
        return sell2;
    }

    /**
     * Método 2: DP Array 3D (k=2 transações)
     * dp[i][k][0] = max profit no dia i com k transações, sem ação
     * dp[i][k][1] = max profit no dia i com k transações, com ação
     * Tempo: O(n*k), Espaço: O(k)
     */
    public int maxProfitDP(int[] prices) {
        if (prices.length == 0) return 0;
        int k = 2;
        
        int[][] dp = new int[k + 1][2];
        for (int j = 1; j <= k; j++) {
            dp[j][1] = Integer.MIN_VALUE;
        }
        
        for (int price : prices) {
            for (int j = 1; j <= k; j++) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + price);
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - price);
            }
        }
        
        return dp[k][0];
    }

    /**
     * Método 3: Divide and Conquer - Left/Right arrays
     * left[i] = max profit em prices[0..i] com 1 transação
     * right[i] = max profit em prices[i..n-1] com 1 transação
     * Tempo: O(n), Espaço: O(n)
     */
    public int maxProfitDivideConquer(int[] prices) {
        if (prices.length == 0) return 0;
        int n = prices.length;
        
        // Left to right: max profit with 1 transaction
        int[] left = new int[n];
        int minPrice = prices[0];
        for (int i = 1; i < n; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            left[i] = Math.max(left[i - 1], prices[i] - minPrice);
        }
        
        // Right to left: max profit with 1 transaction
        int[] right = new int[n];
        int maxPrice = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxPrice = Math.max(maxPrice, prices[i]);
            right[i] = Math.max(right[i + 1], maxPrice - prices[i]);
        }
        
        // Combine
        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            maxProfit = Math.max(maxProfit, left[i] + right[i]);
        }
        
        return maxProfit;
    }

    /**
     * Método 4: DP 2D completo (para referência)
     * dp[i][j] = max profit até dia i com j transações
     * Tempo: O(n*k), Espaço: O(n*k)
     */
    public int maxProfitDP2D(int[] prices) {
        if (prices.length == 0) return 0;
        int n = prices.length;
        int k = 2;
        
        int[][] dp = new int[k + 1][n];
        
        for (int j = 1; j <= k; j++) {
            int maxDiff = -prices[0];
            for (int i = 1; i < n; i++) {
                dp[j][i] = Math.max(dp[j][i - 1], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[j - 1][i] - prices[i]);
            }
        }
        
        return dp[k][n - 1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Best Time to Buy and Sell Stock III Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [3,3,5,0,0,3,1,4]");
        int[] p1 = {3,3,5,0,0,3,1,4};
        System.out.println("  Expected: 6 (buy 0 sell 3 = 3, buy 1 sell 4 = 3)");
        System.out.println("  4Vars:    " + sol.maxProfit(p1));
        System.out.println("  DP:       " + sol.maxProfitDP(p1));
        System.out.println("  DivConq:  " + sol.maxProfitDivideConquer(p1));
        System.out.println("  DP2D:     " + sol.maxProfitDP2D(p1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [1,2,3,4,5]");
        int[] p2 = {1,2,3,4,5};
        System.out.println("  Expected: 4 (buy 1 sell 5 = 4, only 1 transaction needed)");
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
        
        // Test 5: Two elements
        System.out.println("\nTest 5: [1,2]");
        int[] p5 = {1,2};
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.maxProfit(p5));
        
        // Test 6: Two transactions better than one
        System.out.println("\nTest 6: [1,3,2,4]");
        int[] p6 = {1,3,2,4};
        System.out.println("  Expected: 4 (1->3=2, 2->4=2)");
        System.out.println("  Result:   " + sol.maxProfit(p6));
        
        // Test 7: Zigzag
        System.out.println("\nTest 7: [3,2,6,5,0,3]");
        int[] p7 = {3,2,6,5,0,3};
        System.out.println("  Expected: 7 (2->6=4, 0->3=3)");
        System.out.println("  Result:   " + sol.maxProfit(p7));
        
        // Test 8: All same
        System.out.println("\nTest 8: [5,5,5,5]");
        int[] p8 = {5,5,5,5};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.maxProfit(p8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        int[][] testCases = {
            {3,3,5,0,0,3,1,4},
            {1,2,3,4,5},
            {7,6,4,3,1},
            {5},
            {1,2},
            {1,3,2,4},
            {3,2,6,5,0,3},
            {5,5,5,5},
            {2,4,1,5,3,6},
            {1,2,4,2,5,7,2,4,9,0}
        };
        int[] expected = {6, 4, 0, 0, 1, 4, 7, 0, 7, 13};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.maxProfit(testCases[i]);
            int r2 = sol.maxProfitDP(testCases[i]);
            int r3 = sol.maxProfitDivideConquer(testCases[i]);
            int r4 = sol.maxProfitDP2D(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (4V:" + r1 + " DP:" + r2 + " DC:" + r3 + " 2D:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}