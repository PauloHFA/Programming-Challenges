import java.util.Arrays;

/**
 * LeetCode 0064: Minimum Path Sum
 * Difficulty: Medium
 * Category: Dynamic Programming
 * 
 * Approach: DP with 1D array (space optimized)
 * Time Complexity: O(m * n)
 * Space Complexity: O(n) optimized
 */
public class Solution {

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] dp = new int[n];
        dp[0] = grid[0][0];

        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            // Update first column
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }

        return dp[n - 1];
    }

    // Alternative: 2D DP
    public int minPathSum2D(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        // First row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // First column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    // Alternative: In-place modification (O(1) extra space)
    public int minPathSumInPlace(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // First row
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j - 1];
        }

        // First column
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }

        return grid[m - 1][n - 1];
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] grid1 = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        int result1 = solver.minPathSum(grid1);
        System.out.println("Test 1: " + result1 + " (Expected: 7)");

        // Test Case 2
        int[][] grid2 = {{1, 2, 3}, {4, 5, 6}};
        int result2 = solver.minPathSum(grid2);
        System.out.println("Test 2: " + result2 + " (Expected: 12)");

        // Test Case 3: Single cell
        int[][] grid3 = {{5}};
        int result3 = solver.minPathSum(grid3);
        System.out.println("Test 3: " + result3 + " (Expected: 5)");

        // Test Case 4: Single row
        int[][] grid4 = {{1, 2, 3, 4, 5}};
        int result4 = solver.minPathSum(grid4);
        System.out.println("Test 4: " + result4 + " (Expected: 15)");

        // Test Case 5: Single column
        int[][] grid5 = {{1}, {2}, {3}, {4}, {5}};
        int result5 = solver.minPathSum(grid5);
        System.out.println("Test 5: " + result5 + " (Expected: 15)");

        // Test Case 6: 2x2
        int[][] grid6 = {{1, 2}, {3, 4}};
        int result6 = solver.minPathSum(grid6);
        System.out.println("Test 6: " + result6 + " (Expected: 7)");

        // Test Case 7: Larger grid
        int[][] grid7 = {
            {1, 3, 5, 8},
            {4, 2, 1, 7},
            {4, 3, 2, 3}
        };
        int result7 = solver.minPathSum(grid7);
        System.out.println("Test 7: " + result7 + " (Expected: 12)");

        // Verify all approaches match
        System.out.println("\n--- Verification (1D vs 2D vs InPlace) ---");
        int[][][] testCases = {grid1, grid2, grid3, grid4, grid5, grid6, grid7};
        for (int[][] test : testCases) {
            int dp1 = solver.minPathSum(deepCopy(test));
            int dp2 = solver.minPathSum2D(deepCopy(test));
            int inPlace = solver.minPathSumInPlace(deepCopy(test));
            System.out.println("Match: " + (dp1 == dp2 && dp2 == inPlace));
        }
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}