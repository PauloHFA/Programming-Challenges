import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 0060: Permutation Sequence
 * Difficulty: Hard
 * Category: Backtracking / Math
 * 
 * Approach: Mathematical (Factorial Number System) - O(n^2) time
 * Time Complexity: O(n^2)
 * Space Complexity: O(n)
 */
public class Solution {

    public String getPermutation(int n, int k) {
        // Precompute factorials
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        // List of available numbers
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // Convert k to 0-indexed
        k--;

        StringBuilder result = new StringBuilder();

        for (int i = n; i >= 1; i--) {
            int index = k / factorial[i - 1];
            result.append(numbers.remove(index));
            k %= factorial[i - 1];
        }

        return result.toString();
    }

    // Alternative: Backtracking (for verification, only works for small n)
    public String getPermutationBacktrack(int n, int k) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }
        
        StringBuilder result = new StringBuilder();
        backtrack(numbers, k, result);
        return result.toString();
    }

    private void backtrack(List<Integer> numbers, int k, StringBuilder result) {
        if (numbers.isEmpty()) {
            return;
        }
        
        int n = numbers.size();
        int factorial = 1;
        for (int i = 2; i < n; i++) {
            factorial *= i;
        }
        
        int index = (k - 1) / factorial;
        result.append(numbers.remove(index));
        k = (k - 1) % factorial + 1;
        
        backtrack(numbers, k, result);
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String result1 = solver.getPermutation(3, 3);
        System.out.println("Test 1 (n=3, k=3): " + result1 + " (Expected: 213)");

        // Test Case 2
        String result2 = solver.getPermutation(4, 9);
        System.out.println("Test 2 (n=4, k=9): " + result2 + " (Expected: 2314)");

        // Test Case 3
        String result3 = solver.getPermutation(3, 1);
        System.out.println("Test 3 (n=3, k=1): " + result3 + " (Expected: 123)");

        // Test Case 4
        String result4 = solver.getPermutation(3, 6);
        System.out.println("Test 4 (n=3, k=6): " + result4 + " (Expected: 321)");

        // Test Case 5
        String result5 = solver.getPermutation(4, 1);
        System.out.println("Test 5 (n=4, k=1): " + result5 + " (Expected: 1234)");

        // Test Case 6
        String result6 = solver.getPermutation(4, 24);
        System.out.println("Test 6 (n=4, k=24): " + result6 + " (Expected: 4321)");

        // Test Case 7: n=1
        String result7 = solver.getPermutation(1, 1);
        System.out.println("Test 7 (n=1, k=1): " + result7 + " (Expected: 1)");

        // Test Case 8: n=5, middle (k=60 is the last of 32xxx block)
        String result8 = solver.getPermutation(5, 60);
                System.out.println("Test 8 (n=5, k=60): " + result8 + " (Expected: 32541)");

        // Test Case 9: n=9, large k
        String result9 = solver.getPermutation(9, 100000);
        System.out.println("Test 9 (n=9, k=100000): " + result9);

        // Verify mathematical matches backtrack for small n
        System.out.println("\n--- Verification (Math vs Backtrack) ---");
        for (int n = 1; n <= 6; n++) {
            int total = 1;
            for (int i = 2; i <= n; i++) total *= i;
            for (int k = 1; k <= total; k++) {
                String math = solver.getPermutation(n, k);
                String backtrack = solver.getPermutationBacktrack(n, k);
                if (!math.equals(backtrack)) {
                    System.out.println("MISMATCH n=" + n + " k=" + k + ": math=" + math + ", backtrack=" + backtrack);
                }
            }
            System.out.println("n=" + n + " all " + total + " permutations match!");
        }
    }
}