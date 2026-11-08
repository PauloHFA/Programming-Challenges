import java.util.Arrays;

/**
 * LeetCode 0063: Unique Paths II
 * Difficulty: Medium
 * Category: Dynamic Programming
 * 
 * Approach: DP with obstacle handling
 * Time Complexity: O(m * n)
 * Space Complexity: O(n) optimized
 */
public class Solution {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        // If start or end has obstacle, no path
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        int[] dp = new int[n];
        dp[0] = 1; // Start position

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0; // Obstacle blocks path
                } else if (j > 0) {
                    dp[j] += dp[j - 1]; // Add paths from left
                }
                // dp[j] already contains paths from above (previous row)
            }
        }

        return dp[n - 1];
    }

    // Alternative: 2D DP
    public int uniquePathsWithObstacles2D(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        // First column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i - 1][0] == 1) ? 1 : 0;
        }

        // First row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j - 1] == 1) ? 1 : 0;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] grid1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        int result1 = solver.uniquePathsWithObstacles(grid1);
        System.out.println("Test 1: " + result1 + " (Expected: 2)");

        // Test Case 2
        int[][] grid2 = {{0, 1}, {0, 0}};
        int result2 = solver.uniquePathsWithObstacles(grid2);
        System.out.println("Test 2: " + result2 + " (Expected: 1)");

        // Test Case 3: Obstacle at start
        int[][] grid3 = {{1, 0}, {0, 0}};
        int result3 = solver.uniquePathsWithObstacles(grid3);
        System.out.println("Test 3: " + result3 + " (Expected: 0)");

        // Test Case 4: Obstacle at end
        int[][] grid4 = {{0, 0}, {0, 1}};
        int result4 = solver.uniquePathsWithObstacles(grid4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: No obstacles (same as unique paths)
        int[][] grid5 = {{0, 0, 0}, {0, 0, 0}};
        int result5 = solver.uniquePathsWithObstacles(grid5);
        System.out.println("Test 5: " + result5 + " (Expected: 3)");

        // Test Case 6: Single cell, no obstacle
        int[][] grid6 = {{0}};
        int result6 = solver.uniquePathsWithObstacles(grid6);
        System.out.println("Test 6: " + result6 + " (Expected: 1)");

        // Test Case 7: Single cell, obstacle
        int[][] grid7 = {{1}};
        int result7 = solver.uniquePathsWithObstacles(grid7);
        System.out.println("Test 7: " + result7 + " (Expected: 0)");

        // Test Case 8: Row with obstacle
        int[][] grid8 = {{0, 0, 1, 0}};
        int result8 = solver.uniquePathsWithObstacles(grid8);
        System.out.println("Test 8: " + result8 + " (Expected: 0)");

        // Test Case 9: Column with obstacle
        int[][] grid9 = {{0}, {0}, {1}, {0}};
        int result9 = solver.uniquePathsWithObstacles(grid9);
        System.out.println("Test 9: " + result9 + " (Expected: 0)");

        // Test Case 10: Complex
        int[][] grid10 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0}
        };
        int result10 = solver.uniquePathsWithObstacles(grid10);
        System.out.println("Test 10: " + result10 + " (Expected: 4)");

        // Verify both approaches match
        System.out.println("\n--- Verification (1D vs 2D) ---");
        int[][][] testCases = {grid1, grid2, grid3, grid4, grid5, grid6, grid7, grid8, grid9, grid10};
        for (int[][] test : testCases) {
            int dp1 = solver.uniquePathsWithObstacles(deepCopy(test));
            int dp2 = solver.uniquePathsWithObstacles2D(deepCopy(test));
            System.out.println("Match: " + (dp1 == dp2));
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