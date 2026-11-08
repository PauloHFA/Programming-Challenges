/**
 * LeetCode 0050: Pow(x, n)
 * Difficulty: Medium
 * Category: Math / Binary Exponentiation
 * 
 * Approach: Fast Power (Binary Exponentiation)
 * Time Complexity: O(log n)
 * Space Complexity: O(log n) recursive, O(1) iterative
 */
public class Solution {

    // Recursive approach
    public double myPow(double x, int n) {
        long N = n; // Use long to handle Integer.MIN_VALUE
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return fastPow(x, N);
    }

    private double fastPow(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        double half = fastPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    // Iterative approach (O(1) space)
    public double myPowIterative(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        double result = 1.0;
        double currentProduct = x;
        
        while (N > 0) {
            if (N % 2 == 1) {
                result *= currentProduct;
            }
            currentProduct *= currentProduct;
            N /= 2;
        }
        
        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        double result1 = solver.myPow(2.0, 10);
        System.out.println("Test 1: " + result1 + " (Expected: 1024.0)");

        // Test Case 2
        double result2 = solver.myPow(2.1, 3);
        System.out.println("Test 2: " + result2 + " (Expected: 9.261)");

        // Test Case 3
        double result3 = solver.myPow(2.0, -2);
        System.out.println("Test 3: " + result3 + " (Expected: 0.25)");

        // Test Case 4: n = 0
        double result4 = solver.myPow(5.0, 0);
        System.out.println("Test 4: " + result4 + " (Expected: 1.0)");

        // Test Case 5: x = 1
        double result5 = solver.myPow(1.0, 100);
        System.out.println("Test 5: " + result5 + " (Expected: 1.0)");

        // Test Case 6: x = -1
        double result6 = solver.myPow(-1.0, 3);
        System.out.println("Test 6: " + result6 + " (Expected: -1.0)");

        double result7 = solver.myPow(-1.0, 2);
        System.out.println("Test 7: " + result7 + " (Expected: 1.0)");

        // Test Case 8: Large exponent
        double result8 = solver.myPow(2.0, 31);
        System.out.println("Test 8: " + result8 + " (Expected: 2147483648.0)");

        // Test Case 9: Integer.MIN_VALUE
        double result9 = solver.myPow(2.0, -2147483648);
        System.out.println("Test 9: " + result9 + " (Expected: very small)");

        // Test Case 10: Fractional base
        double result10 = solver.myPow(0.5, 3);
        System.out.println("Test 10: " + result10 + " (Expected: 0.125)");

        // Verify both approaches match
        System.out.println("\n--- Verification (Recursive vs Iterative) ---");
        double[][] testCases = {
            {2.0, 10},
            {2.1, 3},
            {2.0, -2},
            {5.0, 0},
            {1.0, 100},
            {-1.0, 3},
            {-1.0, 2},
            {2.0, 31},
            {2.0, -2147483648},
            {0.5, 3}
        };
        
        for (double[] test : testCases) {
            double rec = solver.myPow(test[0], (int)test[1]);
            double iter = solver.myPowIterative(test[0], (int)test[1]);
            System.out.println("x=" + test[0] + ", n=" + (int)test[1] + " -> Rec: " + rec + ", Iter: " + iter + " (match: " + (Math.abs(rec - iter) < 1e-10) + ")");
        }
    }
}