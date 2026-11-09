import java.util.*;

/**
 * LeetCode 120 - Triangle
 * 
 * Dado um array triangle, retorne o caminho de soma mínima do topo para a base.
 * Em cada passo, você pode mover para um número adjacente na linha abaixo.
 * Mais formalmente, se você está no índice i na linha atual, pode mover para
 * o índice i ou i + 1 na próxima linha.
 */
public class Solution {

    /**
     * Método 1: DP Bottom-Up In-Place (Principal)
     * Modifica o triangle de baixo para cima
     * Tempo: O(n^2), Espaço: O(1) extra
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) return 0;
        
        // Começa da penúltima linha e sobe
        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> current = triangle.get(i);
            List<Integer> next = triangle.get(i + 1);
            
            for (int j = 0; j < current.size(); j++) {
                current.set(j, current.get(j) + Math.min(next.get(j), next.get(j + 1)));
            }
        }
        
        return triangle.get(0).get(0);
    }

    /**
     * Método 2: DP Bottom-Up com array 1D
     * Usa array separado para não modificar input
     * Tempo: O(n^2), Espaço: O(n)
     */
    public int minimumTotalDP(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) return 0;
        
        int n = triangle.size();
        int[] dp = new int[n];
        
        // Inicializa com última linha
        for (int i = 0; i < n; i++) {
            dp[i] = triangle.get(n - 1).get(i);
        }
        
        // Bottom-up
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        
        return dp[0];
    }

    /**
     * Método 3: DP Top-Down
     * Tempo: O(n^2), Espaço: O(n^2) ou O(n) com otimização
     */
    public int minimumTotalTopDown(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) return 0;
        
        int n = triangle.size();
        int[][] dp = new int[n][n];
        dp[0][0] = triangle.get(0).get(0);
        
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
            }
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }
        
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            min = Math.min(min, dp[n - 1][j]);
        }
        
        return min;
    }

    /**
     * Método 4: Recursivo com Memoização
     * Tempo: O(n^2), Espaço: O(n^2) + O(n) stack
     */
    public int minimumTotalMemo(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) return 0;
        Integer[][] memo = new Integer[triangle.size()][triangle.size()];
        return dfs(triangle, 0, 0, memo);
    }

    private int dfs(List<List<Integer>> triangle, int row, int col, Integer[][] memo) {
        if (row == triangle.size()) return 0;
        if (memo[row][col] != null) return memo[row][col];
        
        int left = dfs(triangle, row + 1, col, memo);
        int right = dfs(triangle, row + 1, col + 1, memo);
        
        memo[row][col] = triangle.get(row).get(col) + Math.min(left, right);
        return memo[row][col];
    }

    // Helper para criar triangle
    private static List<List<Integer>> createTriangle(int[][] arr) {
        List<List<Integer>> triangle = new ArrayList<>();
        for (int[] row : arr) {
            List<Integer> list = new ArrayList<>();
            for (int val : row) list.add(val);
            triangle.add(list);
        }
        return triangle;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Triangle Minimum Path Sum Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [[2],[3,4],[6,5,7],[4,1,8,3]]");
        List<List<Integer>> t1 = createTriangle(new int[][]{
            {2}, {3,4}, {6,5,7}, {4,1,8,3}
        });
        System.out.println("  Expected: 11 (2+3+5+1)");
        System.out.println("  In-Place: " + sol.minimumTotal(t1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [[-10]]");
        List<List<Integer>> t2 = createTriangle(new int[][]{{-10}});
        System.out.println("  Expected: -10");
        System.out.println("  Result:   " + sol.minimumTotal(t2));
        
        // Test 3: Two rows
        System.out.println("\nTest 3: [[1],[2,3]]");
        List<List<Integer>> t3 = createTriangle(new int[][]{{1}, {2,3}});
        System.out.println("  Expected: 3 (1+2)");
        System.out.println("  Result:   " + sol.minimumTotal(t3));
        
        // Test 4: All positive
        System.out.println("\nTest 4: [[1],[2,3],[4,5,6]]");
        List<List<Integer>> t4 = createTriangle(new int[][]{{1}, {2,3}, {4,5,6}});
        System.out.println("  Expected: 7 (1+2+4)");
        System.out.println("  Result:   " + sol.minimumTotal(t4));
        
        // Test 5: Negative values
        System.out.println("\nTest 5: [[-1],[2,3],[1,-1,-3]]");
        List<List<Integer>> t5 = createTriangle(new int[][]{{-1}, {2,3}, {1,-1,-3}});
                System.out.println("  Expected: -1 (-1+3+-3 or -1+2+-1)");
        System.out.println("  Result:   " + sol.minimumTotal(t5));
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification (4 methods)");
        int[][][] testCases = {
            {{2}, {3,4}, {6,5,7}, {4,1,8,3}},
            {{-10}},
            {{1}, {2,3}},
            {{1}, {2,3}, {4,5,6}},
            {{-1}, {2,3}, {1,-1,-3}},
            {{5}, {7,8}, {2,3,4}, {4,5,6,7}, {1,2,3,4,5}}
        };
                int[] expected = {11, -10, 3, 7, -1, 19};
        
        for (int i = 0; i < testCases.length; i++) {
            List<List<Integer>> t = createTriangle(testCases[i]);
            int r1 = sol.minimumTotal(createTriangle(testCases[i]));
            int r2 = sol.minimumTotalDP(createTriangle(testCases[i]));
            int r3 = sol.minimumTotalTopDown(createTriangle(testCases[i]));
            int r4 = sol.minimumTotalMemo(createTriangle(testCases[i]));
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (InPlace:" + r1 + " DP:" + r2 + " TopDown:" + r3 + " Memo:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        // Test 7: Large triangle
        System.out.println("\nTest 7: Large triangle (10 rows)");
        int[][] large = new int[10][];
        for (int i = 0; i < 10; i++) {
            large[i] = new int[i + 1];
            for (int j = 0; j <= i; j++) {
                large[i][j] = (i + j) % 10 + 1;
            }
        }
        List<List<Integer>> t7 = createTriangle(large);
        System.out.println("  Result: " + sol.minimumTotal(t7));
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}