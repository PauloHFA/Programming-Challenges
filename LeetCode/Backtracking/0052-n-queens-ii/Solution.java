/**
 * LeetCode 0052: N-Queens II
 * Difficulty: Hard
 * Category: Backtracking
 * 
 * Approach: Backtracking with bit manipulation for optimization
 * Time Complexity: O(N!)
 * Space Complexity: O(N)
 */
public class Solution {

    private int count = 0;
    private int n;

    public int totalNQueens(int n) {
        this.n = n;
        this.count = 0;
        
        // Use bitmasks for columns and diagonals
        // cols: bits represent occupied columns
        // diag1: bits represent occupied main diagonals (row + col)
        // diag2: bits represent occupied anti-diagonals (row - col + n - 1)
        backtrack(0, 0, 0, 0);
        return count;
    }

    private void backtrack(int row, int cols, int diag1, int diag2) {
        if (row == n) {
            count++;
            return;
        }

        // Available positions: bits that are 0 in all three masks
        // We only care about the first n bits
        int available = (~(cols | diag1 | diag2)) & ((1 << n) - 1);

        while (available != 0) {
            // Get the rightmost available position
            int position = available & -available;
            available = available & (available - 1); // Remove the rightmost bit

            backtrack(row + 1, 
                     cols | position, 
                     (diag1 | position) << 1, 
                     (diag2 | position) >> 1);
        }
    }

    // Alternative: Using boolean arrays (easier to understand)
    public int totalNQueensArrays(int n) {
        this.n = n;
        this.count = 0;
        
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];
        
        backtrackArrays(0, cols, diag1, diag2);
        return count;
    }

    private void backtrackArrays(int row, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) {
            count++;
            return;
        }

        for (int col = 0; col < n; col++) {
            int d1 = row + col;
            int d2 = row - col + n - 1;

            if (cols[col] || diag1[d1] || diag2[d2]) {
                continue;
            }

            cols[col] = true;
            diag1[d1] = true;
            diag2[d2] = true;

            backtrackArrays(row + 1, cols, diag1, diag2);

            cols[col] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: n = 4
        int result1 = solver.totalNQueens(4);
        System.out.println("Test 1 (n=4): " + result1 + " (Expected: 2)");

        // Test Case 2: n = 1
        int result2 = solver.totalNQueens(1);
        System.out.println("Test 2 (n=1): " + result2 + " (Expected: 1)");

        // Test Case 3: n = 2
        int result3 = solver.totalNQueens(2);
        System.out.println("Test 3 (n=2): " + result3 + " (Expected: 0)");

        // Test Case 4: n = 3
        int result4 = solver.totalNQueens(3);
        System.out.println("Test 4 (n=3): " + result4 + " (Expected: 0)");

        // Test Case 5: n = 5
        int result5 = solver.totalNQueens(5);
        System.out.println("Test 5 (n=5): " + result5 + " (Expected: 10)");

        // Test Case 6: n = 6
        int result6 = solver.totalNQueens(6);
        System.out.println("Test 6 (n=6): " + result6 + " (Expected: 4)");

        // Test Case 7: n = 7
        int result7 = solver.totalNQueens(7);
        System.out.println("Test 7 (n=7): " + result7 + " (Expected: 40)");

        // Test Case 8: n = 8
        int result8 = solver.totalNQueens(8);
        System.out.println("Test 8 (n=8): " + result8 + " (Expected: 92)");

        // Test Case 9: n = 9
        int result9 = solver.totalNQueens(9);
        System.out.println("Test 9 (n=9): " + result9 + " (Expected: 352)");

        // Verify both approaches match
        System.out.println("\n--- Verification (Bitmask vs Arrays) ---");
        for (int i = 1; i <= 9; i++) {
            int bitmask = solver.totalNQueens(i);
            int arrays = solver.totalNQueensArrays(i);
            System.out.println("n=" + i + " -> Bitmask: " + bitmask + ", Arrays: " + arrays + " (match: " + (bitmask == arrays) + ")");
        }
    }
}