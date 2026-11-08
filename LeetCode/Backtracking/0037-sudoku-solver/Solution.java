import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 0037: Sudoku Solver
 * Difficulty: Hard
 * Category: Backtracking
 * 
 * Approach: Backtracking with Hash Sets for validation
 * Time Complexity: O(9^(N*N)) worst case, but much faster with constraints
 * Space Complexity: O(N*N) for recursion stack and sets
 */
public class Solution {

    private Set<Character>[] rows;
    private Set<Character>[] cols;
    private Set<Character>[] boxes;
    private char[][] board;

    public void solveSudoku(char[][] board) {
        this.board = board;
        this.rows = new HashSet[9];
        this.cols = new HashSet[9];
        this.boxes = new HashSet[9];

        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        // Initialize sets with existing numbers
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.') {
                    rows[i].add(c);
                    cols[j].add(c);
                    boxes[(i / 3) * 3 + (j / 3)].add(c);
                }
            }
        }

        backtrack(0, 0);
    }

    private boolean backtrack(int row, int col) {
        // If we've reached the end of the board, we're done
        if (row == 9) {
            return true;
        }

        // Calculate next position
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;

        // If cell is already filled, move to next
        if (board[row][col] != '.') {
            return backtrack(nextRow, nextCol);
        }

        // Try digits 1-9
        for (char d = '1'; d <= '9'; d++) {
            int boxIndex = (row / 3) * 3 + (col / 3);

            // Check if digit is valid
            if (!rows[row].contains(d) && !cols[col].contains(d) && !boxes[boxIndex].contains(d)) {
                // Place digit
                board[row][col] = d;
                rows[row].add(d);
                cols[col].add(d);
                boxes[boxIndex].add(d);

                // Recurse
                if (backtrack(nextRow, nextCol)) {
                    return true;
                }

                // Backtrack
                board[row][col] = '.';
                rows[row].remove(d);
                cols[col].remove(d);
                boxes[boxIndex].remove(d);
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: Standard Sudoku puzzle
        char[][] board1 = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        
        System.out.println("Test 1 - Before solving:");
        printBoard(board1);
        solver.solveSudoku(board1);
        System.out.println("Test 1 - After solving:");
        printBoard(board1);
        System.out.println("Valid: " + isValid(board1));

        // Test Case 2: Another puzzle
        char[][] board2 = {
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'}
        };
        
        System.out.println("\nTest 2 - Empty board:");
        solver.solveSudoku(board2);
        printBoard(board2);
        System.out.println("Valid: " + isValid(board2));

        // Test Case 3: Nearly complete puzzle
        char[][] board3 = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','.'}
        };
        
        System.out.println("\nTest 3 - Nearly complete:");
        printBoard(board3);
        solver.solveSudoku(board3);
        System.out.println("After solving:");
        printBoard(board3);
        System.out.println("Valid: " + isValid(board3));
    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValid(char[][] board) {
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];

        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') return false;
                
                if (rows[i].contains(c) || cols[j].contains(c) || boxes[(i/3)*3 + j/3].contains(c)) {
                    return false;
                }
                rows[i].add(c);
                cols[j].add(c);
                boxes[(i/3)*3 + j/3].add(c);
            }
        }
        return true;
    }
}