import java.util.Arrays;

/**
 * LeetCode 0062: Unique Paths
 * Difficulty: Medium
 * Category: Dynamic Programming
 * 
 * Approach: DP with 2D array (can be optimized to 1D)
 * Time Complexity: O(m * n)
 * Space Complexity: O(n) optimized, O(m * n) standard
 */
public class Solution {

    public int uniquePaths(int m, int n) {
        // Use 1D DP array (space optimized)
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // First row is all 1s

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }

        return dp[n - 1];
    }

    // Alternative: 2D DP (easier to understand)
    public int uniquePaths2D(int m, int n) {
        int[][] dp = new int[m][n];
        
        // First row and first column are all 1s
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    // Alternative: Mathematical (Combinatorics) - O(min(m,n)) time
    public int uniquePathsMath(int m, int n) {
        // Total steps = (m-1) + (n-1) = m+n-2
        // Choose (m-1) down moves (or (n-1) right moves)
        // C(m+n-2, m-1) = (m+n-2)! / ((m-1)! * (n-1)!)
        
        int total = m + n - 2;
        int choose = Math.min(m - 1, n - 1);
        
        long result = 1;
        for (int i = 1; i <= choose; i++) {
            result = result * (total - choose + i) / i;
        }
        
        return (int) result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int result1 = solver.uniquePaths(3, 7);
        System.out.println("Test 1 (m=3, n=7): " + result1 + " (Expected: 28)");

        // Test Case 2
        int result2 = solver.uniquePaths(3, 2);
        System.out.println("Test 2 (m=3, n=2): " + result2 + " (Expected: 3)");

        // Test Case 3: 1x1
        int result3 = solver.uniquePaths(1, 1);
        System.out.println("Test 3 (m=1, n=1): " + result3 + " (Expected: 1)");

        // Test Case 4: 1xn
        int result4 = solver.uniquePaths(1, 5);
        System.out.println("Test 4 (m=1, n=5): " + result4 + " (Expected: 1)");

        // Test Case 5: mx1
        int result5 = solver.uniquePaths(5, 1);
        System.out.println("Test 5 (m=5, n=1): " + result5 + " (Expected: 1)");

        // Test Case 6: 7x3 (same as 3x7)
        int result6 = solver.uniquePaths(7, 3);
        System.out.println("Test 6 (m=7, n=3): " + result6 + " (Expected: 28)");

        // Test Case 7: Larger
        int result7 = solver.uniquePaths(10, 10);
        System.out.println("Test 7 (m=10, n=10): " + result7 + " (Expected: 48620)");

        // Test Case 8: 23x12 (near constraint limits)
        int result8 = solver.uniquePaths(23, 12);
        System.out.println("Test 8 (m=23, n=12): " + result8);

        // Verify all approaches match
        System.out.println("\n--- Verification (1D vs 2D vs Math) ---");
        int[][] testCases = {{3, 7}, {3, 2}, {1, 1}, {1, 5}, {5, 1}, {7, 3}, {10, 10}, {23, 12}};
        for (int[] test : testCases) {
            int dp1 = solver.uniquePaths(test[0], test[1]);
            int dp2 = solver.uniquePaths2D(test[0], test[1]);
            int math = solver.uniquePathsMath(test[0], test[1]);
            System.out.println("m=" + test[0] + ", n=" + test[1] + " -> 1D: " + dp1 + ", 2D: " + dp2 + ", Math: " + math + " (match: " + (dp1 == dp2 && dp2 == math) + ")");
        }
    }
}