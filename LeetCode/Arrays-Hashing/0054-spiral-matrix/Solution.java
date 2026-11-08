import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0054: Spiral Matrix
 * Difficulty: Medium
 * Category: Arrays & Hashing / Matrix
 * 
 * Approach: Boundary Traversal (Layer by Layer)
 * Time Complexity: O(m * n)
 * Space Complexity: O(1) excluding output
 */
public class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        
        int top = 0;
        int bottom = m - 1;
        int left = 0;
        int right = n - 1;

        while (top <= bottom && left <= right) {
            // Traverse top row (left to right)
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;

            // Traverse right column (top to bottom)
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;

            // Traverse bottom row (right to left) - if still valid
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // Traverse left column (bottom to top) - if still valid
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }

        return result;
    }

    // Alternative: Direction-based approach
    public List<Integer> spiralOrderDirection(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // Directions: right, down, left, up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        
        int r = 0, c = 0, di = 0;
        
        for (int i = 0; i < m * n; i++) {
            result.add(matrix[r][c]);
            visited[r][c] = true;
            
            int nextR = r + dr[di];
            int nextC = c + dc[di];
            
            // Check if next position is valid
            if (nextR < 0 || nextR >= m || nextC < 0 || nextC >= n || visited[nextR][nextC]) {
                // Change direction
                di = (di + 1) % 4;
                nextR = r + dr[di];
                nextC = c + dc[di];
            }
            
            r = nextR;
            c = nextC;
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        List<Integer> result1 = solver.spiralOrder(matrix1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]");

        // Test Case 2
        int[][] matrix2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        List<Integer> result2 = solver.spiralOrder(matrix2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]");

        // Test Case 3: Single row
        int[][] matrix3 = {{1, 2, 3, 4, 5}};
        List<Integer> result3 = solver.spiralOrder(matrix3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: [1, 2, 3, 4, 5]");

        // Test Case 4: Single column
        int[][] matrix4 = {{1}, {2}, {3}, {4}, {5}};
        List<Integer> result4 = solver.spiralOrder(matrix4);
        System.out.println("\nTest 4: " + result4);
        System.out.println("Expected: [1, 2, 3, 4, 5]");

        // Test Case 5: Single element
        int[][] matrix5 = {{1}};
        List<Integer> result5 = solver.spiralOrder(matrix5);
        System.out.println("\nTest 5: " + result5);
        System.out.println("Expected: [1]");

        // Test Case 6: 2x2
        int[][] matrix6 = {{1, 2}, {3, 4}};
        List<Integer> result6 = solver.spiralOrder(matrix6);
        System.out.println("\nTest 6: " + result6);
        System.out.println("Expected: [1, 2, 4, 3]");

        // Test Case 7: 2x3
        int[][] matrix7 = {{1, 2, 3}, {4, 5, 6}};
        List<Integer> result7 = solver.spiralOrder(matrix7);
        System.out.println("\nTest 7: " + result7);
        System.out.println("Expected: [1, 2, 3, 6, 5, 4]");

        // Test Case 8: 3x2
        int[][] matrix8 = {{1, 2}, {3, 4}, {5, 6}};
        List<Integer> result8 = solver.spiralOrder(matrix8);
        System.out.println("\nTest 8: " + result8);
        System.out.println("Expected: [1, 2, 4, 6, 5, 3]");

        // Verify both approaches match
        System.out.println("\n--- Verification (Boundary vs Direction) ---");
        int[][][] testCases = {matrix1, matrix2, matrix3, matrix4, matrix5, matrix6, matrix7, matrix8};
        for (int[][] test : testCases) {
            List<Integer> boundary = solver.spiralOrder(deepCopy(test));
            List<Integer> direction = solver.spiralOrderDirection(deepCopy(test));
            System.out.println("Match: " + boundary.equals(direction));
        }
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}