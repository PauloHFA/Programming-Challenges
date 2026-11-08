public class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // DP with O(n) space
        int[] dp = new int[n + 1];
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }
        
        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] = i;
            
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = 1 + Math.min(prev, Math.min(dp[j - 1], dp[j]));
                }
                prev = temp;
            }
        }
        
        return dp[n];
    }

    // Alternative: Full 2D DP array
    public int minDistance2D(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        dp[i - 1][j - 1], // replace
                        Math.min(dp[i - 1][j], dp[i][j - 1]) // delete, insert
                    );
                }
            }
        }
        
        return dp[m][n];
    }

    // Alternative: Recursive with memoization
    public int minDistanceMemo(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            java.util.Arrays.fill(row, -1);
        }
        return dfs(word1, word2, 0, 0, memo);
    }
    
    private int dfs(String word1, String word2, int i, int j, int[][] memo) {
        if (i == word1.length()) return word2.length() - j;
        if (j == word2.length()) return word1.length() - i;
        if (memo[i][j] != -1) return memo[i][j];
        
        if (word1.charAt(i) == word2.charAt(j)) {
            memo[i][j] = dfs(word1, word2, i + 1, j + 1, memo);
        } else {
            int replace = dfs(word1, word2, i + 1, j + 1, memo);
            int delete = dfs(word1, word2, i + 1, j, memo);
            int insert = dfs(word1, word2, i, j + 1, memo);
            memo[i][j] = 1 + Math.min(replace, Math.min(delete, insert));
        }
        
        return memo[i][j];
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        String[][] testCases = {
            {"horse", "ros", "3"},
            {"intention", "execution", "5"},
            {"", "", "0"},
            {"a", "", "1"},
            {"", "a", "1"},
            {"a", "b", "1"},
            {"ab", "ab", "0"},
            {"abc", "def", "3"},
            {"kitten", "sitting", "3"},
            {"saturday", "sunday", "3"},
        };
        
        System.out.println("=== Edit Distance Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            String word1 = testCases[i][0];
            String word2 = testCases[i][1];
            int expected = Integer.parseInt(testCases[i][2]);
            
            int result1 = solver.minDistance(word1, word2);
            int result2 = solver.minDistance2D(word1, word2);
            int result3 = solver.minDistanceMemo(word1, word2);
            
            boolean match1 = result1 == expected;
            boolean match2 = result2 == expected;
            boolean match3 = result3 == expected;
            
            System.out.println("Test " + (i + 1) + ": \"" + word1 + "\" -> \"" + word2 + "\"");
            System.out.println("  Expected: " + expected);
            System.out.println("  O(n) DP:  " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  2D DP:    " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Memo:     " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}