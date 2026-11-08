import java.util.Arrays;

/**
 * LeetCode 0059: Spiral Matrix II
 * Difficulty: Medium
 * Category: Arrays & Hashing / Matrix
 * 
 * Approach: Boundary Traversal (Layer by Layer) - Fill instead of read
 * Time Complexity: O(n^2)
 * Space Complexity: O(1) excluding output
 */
public class Solution {

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        
        int num = 1;
        int target = n * n;
        
        while (num <= target) {
            // Fill top row (left to right)
            for (int col = left; col <= right && num <= target; col++) {
                matrix[top][col] = num++;
            }
            top++;
            
            // Fill right column (top to bottom)
            for (int row = top; row <= bottom && num <= target; row++) {
                matrix[row][right] = num++;
            }
            right--;
            
            // Fill bottom row (right to left)
            for (int col = right; col >= left && num <= target; col--) {
                matrix[bottom][col] = num++;
            }
            bottom--;
            
            // Fill left column (bottom to top)
            for (int row = bottom; row >= top && num <= target; row--) {
                matrix[row][left] = num++;
            }
            left++;
        }
        
        return matrix;
    }

    // Alternative: Direction-based approach
    public int[][] generateMatrixDirection(int n) {
        int[][] matrix = new int[n][n];
        
        // Directions: right, down, left, up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        
        int r = 0, c = 0, di = 0;
        
        for (int num = 1; num <= n * n; num++) {
            matrix[r][c] = num;
            
            int nextR = r + dr[di];
            int nextC = c + dc[di];
            
            // Check if next position is valid (in bounds and not filled)
            if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n || matrix[nextR][nextC] != 0) {
                // Change direction
                di = (di + 1) % 4;
                nextR = r + dr[di];
                nextC = c + dc[di];
            }
            
            r = nextR;
            c = nextC;
        }
        
        return matrix;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: n = 3
        int[][] result1 = solver.generateMatrix(3);
        System.out.println("Test 1 (n=3):");
        printMatrix(result1);
        System.out.println("Expected: [[1,2,3],[8,9,4],[7,6,5]]");

        // Test Case 2: n = 1
        int[][] result2 = solver.generateMatrix(1);
        System.out.println("\nTest 2 (n=1):");
        printMatrix(result2);
        System.out.println("Expected: [[1]]");

        // Test Case 3: n = 2
        int[][] result3 = solver.generateMatrix(2);
        System.out.println("\nTest 3 (n=2):");
        printMatrix(result3);
        System.out.println("Expected: [[1,2],[4,3]]");

        // Test Case 4: n = 4
        int[][] result4 = solver.generateMatrix(4);
        System.out.println("\nTest 4 (n=4):");
        printMatrix(result4);
        System.out.println("Expected: [[1,2,3,4],[12,13,14,5],[11,16,15,6],[10,9,8,7]]");

        // Test Case 5: n = 5
        int[][] result5 = solver.generateMatrix(5);
        System.out.println("\nTest 5 (n=5):");
        printMatrix(result5);

        // Verify both approaches match
        System.out.println("\n--- Verification (Boundary vs Direction) ---");
        for (int n = 1; n <= 6; n++) {
            int[][] boundary = solver.generateMatrix(n);
            int[][] direction = solver.generateMatrixDirection(n);
            System.out.println("n=" + n + " Match: " + Arrays.deepEquals(boundary, direction));
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}