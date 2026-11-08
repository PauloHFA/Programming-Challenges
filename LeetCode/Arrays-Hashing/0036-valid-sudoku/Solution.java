import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 0036: Valid Sudoku
 * Difficulty: Medium
 * Category: Arrays & Hashing / Matrix
 * 
 * Approach: Hash Sets for Rows, Columns, and Boxes
 * Time Complexity: O(1) - fixed 9x9 board
 * Space Complexity: O(1) - fixed size sets
 */
public class Solution {

    public boolean isValidSudoku(char[][] board) {
        // Use sets to track seen digits in rows, columns, and boxes
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
                if (c == '.') {
                    continue;
                }

                // Check row
                if (rows[i].contains(c)) {
                    return false;
                }
                rows[i].add(c);

                // Check column
                if (cols[j].contains(c)) {
                    return false;
                }
                cols[j].add(c);

                // Check 3x3 box
                int boxIndex = (i / 3) * 3 + (j / 3);
                if (boxes[boxIndex].contains(c)) {
                    return false;
                }
                boxes[boxIndex].add(c);
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: Valid Sudoku
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
        boolean result1 = solver.isValidSudoku(board1);
        System.out.println("Test 1: " + result1 + " (Expected: true)");

        // Test Case 2: Invalid - duplicate in row
        char[][] board2 = {
            {'8','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        boolean result2 = solver.isValidSudoku(board2);
        System.out.println("Test 2: " + result2 + " (Expected: false)");

        // Test Case 3: Invalid - duplicate in column
        char[][] board3 = {
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
        // Modify to have duplicate in column 0
        board3[0][0] = '5';
        board3[3][0] = '5';
        boolean result3 = solver.isValidSudoku(board3);
        System.out.println("Test 3: " + result3 + " (Expected: false)");

        // Test Case 4: Invalid - duplicate in box
        char[][] board4 = {
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
        // Modify to have duplicate in top-left box
        board4[0][0] = '5';
        board4[1][1] = '5';
        boolean result4 = solver.isValidSudoku(board4);
        System.out.println("Test 4: " + result4 + " (Expected: false)");

        // Test Case 5: Empty board (all dots)
        char[][] board5 = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board5[i][j] = '.';
            }
        }
        boolean result5 = solver.isValidSudoku(board5);
        System.out.println("Test 5: " + result5 + " (Expected: true)");

        // Test Case 6: Complete valid Sudoku
        char[][] board6 = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        boolean result6 = solver.isValidSudoku(board6);
        System.out.println("Test 6: " + result6 + " (Expected: true)");
    }
}