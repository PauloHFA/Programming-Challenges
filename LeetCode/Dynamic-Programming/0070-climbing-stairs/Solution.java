public class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;
        
        // DP with O(1) space - Fibonacci
        int prev2 = 1; // dp[i-2]
        int prev1 = 2; // dp[i-1]
        
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }

    // Alternative: DP array (O(n) space)
    public int climbStairsDP(int n) {
        if (n <= 2) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }

    // Alternative: Matrix exponentiation (O(log n))
    public int climbStairsMatrix(int n) {
        if (n <= 2) return n;
        
        // [[1, 1], [1, 0]] ^ (n-1) * [1, 1]^T
        long[][] matrix = {{1, 1}, {1, 0}};
        long[][] result = matrixPower(matrix, n - 1);
        return (int) (result[0][0] + result[0][1]);
    }
    
    private long[][] matrixMultiply(long[][] a, long[][] b) {
        return new long[][] {
            {a[0][0] * b[0][0] + a[0][1] * b[1][0], a[0][0] * b[0][1] + a[0][1] * b[1][1]},
            {a[1][0] * b[0][0] + a[1][1] * b[1][0], a[1][0] * b[0][1] + a[1][1] * b[1][1]}
        };
    }
    
    private long[][] matrixPower(long[][] matrix, int n) {
        long[][] result = {{1, 0}, {0, 1}}; // Identity
        long[][] base = matrix;
        
        while (n > 0) {
            if ((n & 1) == 1) {
                result = matrixMultiply(result, base);
            }
            base = matrixMultiply(base, base);
            n >>= 1;
        }
        
        return result;
    }

    // Alternative: Binet's formula (closed form)
    public int climbStairsBinet(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double psi = (1 - sqrt5) / 2;
        return (int) Math.round((Math.pow(phi, n + 1) - Math.pow(psi, n + 1)) / sqrt5);
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[] testCases = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 45};
        int[] expected = {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 10946, 1346269, 165580141, 1836311903};
        
        System.out.println("=== Climbing Stairs Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            int exp = expected[i];
            
            int result1 = solver.climbStairs(n);
            int result2 = solver.climbStairsDP(n);
            int result3 = solver.climbStairsMatrix(n);
            int result4 = solver.climbStairsBinet(n);
            
            boolean match1 = result1 == exp;
            boolean match2 = result2 == exp;
            boolean match3 = result3 == exp;
            boolean match4 = result4 == exp;
            
            System.out.println("Test " + (i + 1) + ": n = " + n);
            System.out.println("  Expected: " + exp);
            System.out.println("  O(1) DP:  " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  DP Array: " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Matrix:   " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  Binet:    " + result4 + (match4 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 && match4 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Performance test
        System.out.println("=== Performance Test (n=45, 10000 iterations) ===");
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solver.climbStairs(45);
        }
        long dpTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solver.climbStairsMatrix(45);
        }
        long matrixTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solver.climbStairsBinet(45);
        }
        long binetTime = System.nanoTime() - start;
        
        System.out.println("O(1) DP: " + (dpTime / 1_000_000) + " ms");
        System.out.println("Matrix:  " + (matrixTime / 1_000_000) + " ms");
        System.out.println("Binet:   " + (binetTime / 1_000_000) + " ms");
    }
}