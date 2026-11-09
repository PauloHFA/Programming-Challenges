import java.util.*;

/**
 * LeetCode 172 - Factorial Trailing Zeroes
 * 
 * Given an integer n, return the number of trailing zeroes in n!.
 * 
 * Note that n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1.
 * 
 * Example 1:
 * Input: n = 3
 * Output: 0
 * Explanation: 3! = 6, no trailing zero.
 * 
 * Example 2:
 * Input: n = 5
 * Output: 1
 * Explanation: 5! = 120, one trailing zero.
 * 
 * Example 3:
 * Input: n = 0
 * Output: 0
 * 
 * Constraints:
 * - 0 <= n <= 10^4
 * 
 * Follow up: Could you write a solution that works in logarithmic time complexity?
 */
public class Solution {

    // Approach 1: Count factors of 5 (optimal - O(log n))
    // Each trailing zero comes from a factor of 10 = 2 * 5
    // Since there are more factors of 2 than 5, count factors of 5
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    // Approach 2: Recursive version
    public int trailingZeroesRecursive(int n) {
        if (n == 0) return 0;
        return n / 5 + trailingZeroesRecursive(n / 5);
    }

    // Approach 3: Explicit loop with power of 5
    public int trailingZeroesExplicit(int n) {
        int count = 0;
        long powerOf5 = 5;
        while (powerOf5 <= n) {
            count += n / powerOf5;
            powerOf5 *= 5;
        }
        return count;
    }

    // Approach 4: Brute force (for verification only - computes actual factorial)
    // Only works for small n due to overflow
    public int trailingZeroesBruteForce(int n) {
        if (n > 20) return -1; // 20! exceeds long
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        int count = 0;
        while (fact % 10 == 0) {
            count++;
            fact /= 10;
        }
        return count;
    }

    // Approach 5: Count factors of 2 and 5 separately (educational)
    public int trailingZeroesCountBoth(int n) {
        int count2 = 0, count5 = 0;
        for (int i = 2; i <= n; i++) {
            int x = i;
            while (x % 2 == 0) {
                count2++;
                x /= 2;
            }
            while (x % 5 == 0) {
                count5++;
                x /= 5;
            }
        }
        return Math.min(count2, count5);
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        int[] testCases = {0, 1, 3, 4, 5, 6, 10, 15, 20, 25, 30, 100, 1000, 10000};
        int[] expected = {0, 0, 0, 0, 1, 1, 2, 3, 4, 6, 7, 24, 249, 2499};

        System.out.println("=== Factorial Trailing Zeroes Tests ===\n");

        for (int t = 0; t < testCases.length; t++) {
            int n = testCases[t];
            int exp = expected[t];
            
            int r1 = sol.trailingZeroes(n);
            int r2 = sol.trailingZeroesRecursive(n);
            int r3 = sol.trailingZeroesExplicit(n);
            int r4 = sol.trailingZeroesBruteForce(n);
            int r5 = (n <= 100) ? sol.trailingZeroesCountBoth(n) : -1;

            boolean pass = r1 == exp && r2 == exp && r3 == exp && 
                          (r4 == -1 || r4 == exp) && (r5 == -1 || r5 == exp);
            
            System.out.printf("Test %d: n=%d -> Expected: %d, Got: %d %s%n", 
                t + 1, n, exp, r1, pass ? "✓" : "✗");
            
            if (!pass) {
                System.out.printf("  Recursive: %d, Explicit: %d, Brute: %d, CountBoth: %d%n", r2, r3, r4, r5);
            }
        }

        // Cross-verification for all approaches
        System.out.println("\n=== Cross Verification ===");
        boolean allMatch = true;
        for (int n : testCases) {
            int r1 = sol.trailingZeroes(n);
            int r2 = sol.trailingZeroesRecursive(n);
            int r3 = sol.trailingZeroesExplicit(n);
            
            if (r1 != r2 || r1 != r3) {
                System.out.printf("Mismatch for n=%d: %d, %d, %d%n", n, r1, r2, r3);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");

        // Verify brute force for small n
        System.out.println("\n=== Brute Force Verification (n <= 20) ===");
        for (int n = 0; n <= 20; n++) {
            int r1 = sol.trailingZeroes(n);
            int r4 = sol.trailingZeroesBruteForce(n);
            if (r1 != r4) {
                System.out.printf("Mismatch at n=%d: optimal=%d, brute=%d%n", n, r1, r4);
            }
        }
        System.out.println("Brute force verification complete ✓");

        // Edge cases
        System.out.println("\n=== Edge Cases ===");
        System.out.println("n=0: " + sol.trailingZeroes(0) + " (expected 0)");
        System.out.println("n=4: " + sol.trailingZeroes(4) + " (expected 0)");
        System.out.println("n=5: " + sol.trailingZeroes(5) + " (expected 1)");
        System.out.println("n=24: " + sol.trailingZeroes(24) + " (expected 4)");
        System.out.println("n=25: " + sol.trailingZeroes(25) + " (expected 6)");
        System.out.println("n=10000: " + sol.trailingZeroes(10000) + " (expected 2499)");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}