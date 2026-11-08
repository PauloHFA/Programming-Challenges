import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0051: N-Queens
 * Difficulty: Hard
 * Category: Backtracking
 * 
 * Approach: Backtracking with sets for columns and diagonals
 * Time Complexity: O(N!)
 * Space Complexity: O(N)
 */
public class Solution {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        
        // Sets to track occupied columns and diagonals
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // row + col (main diagonal)
        boolean[] diag2 = new boolean[2 * n - 1]; // row - col + n - 1 (anti-diagonal)
        
        backtrack(board, 0, cols, diag1, diag2, result);
        return result;
    }

    private void backtrack(char[][] board, int row, boolean[] cols, boolean[] diag1, boolean[] diag2, List<List<String>> result) {
        int n = board.length;
        if (row == n) {
            // Found a valid solution
            List<String> solution = new ArrayList<>();
            for (char[] r : board) {
                solution.add(new String(r));
            }
            result.add(solution);
            return;
        }

        for (int col = 0; col < n; col++) {
            int d1 = row + col;
            int d2 = row - col + n - 1;

            if (cols[col] || diag1[d1] || diag2[d2]) {
                continue;
            }

            // Place queen
            board[row][col] = 'Q';
            cols[col] = true;
            diag1[d1] = true;
            diag2[d2] = true;

            backtrack(board, row + 1, cols, diag1, diag2, result);

            // Backtrack
            board[row][col] = '.';
            cols[col] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: n = 4
        List<List<String>> result1 = solver.solveNQueens(4);
        System.out.println("Test 1 (n=4): " + result1.size() + " solutions");
        for (List<String> sol : result1) {
            for (String row : sol) {
                System.out.println(row);
            }
            System.out.println("---");
        }
        System.out.println("Expected: 2 solutions");

        // Test Case 2: n = 1
        List<List<String>> result2 = solver.solveNQueens(1);
        System.out.println("\nTest 2 (n=1): " + result2.size() + " solutions");
        for (List<String> sol : result2) {
            for (String row : sol) {
                System.out.println(row);
            }
        }
        System.out.println("Expected: 1 solution");

        // Test Case 3: n = 2 (no solution)
        List<List<String>> result3 = solver.solveNQueens(2);
        System.out.println("\nTest 3 (n=2): " + result3.size() + " solutions");
        System.out.println("Expected: 0 solutions");

        // Test Case 4: n = 3 (no solution)
        List<List<String>> result4 = solver.solveNQueens(3);
        System.out.println("\nTest 4 (n=3): " + result4.size() + " solutions");
        System.out.println("Expected: 0 solutions");

        // Test Case 5: n = 5
        List<List<String>> result5 = solver.solveNQueens(5);
        System.out.println("\nTest 5 (n=5): " + result5.size() + " solutions");
        System.out.println("Expected: 10 solutions");

        // Test Case 6: n = 6
        List<List<String>> result6 = solver.solveNQueens(6);
        System.out.println("\nTest 6 (n=6): " + result6.size() + " solutions");
        System.out.println("Expected: 4 solutions");

        // Test Case 7: n = 7
        List<List<String>> result7 = solver.solveNQueens(7);
        System.out.println("\nTest 7 (n=7): " + result7.size() + " solutions");
        System.out.println("Expected: 40 solutions");

        // Test Case 8: n = 8
        List<List<String>> result8 = solver.solveNQueens(8);
        System.out.println("\nTest 8 (n=8): " + result8.size() + " solutions");
        System.out.println("Expected: 92 solutions");
    }
}