import java.util.Arrays;

/**
 * LeetCode 0048: Rotate Image
 * Difficulty: Medium
 * Category: Arrays & Hashing / Matrix
 * 
 * Approach: Transpose + Reverse Rows (In-place)
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class Solution {

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // Step 1: Transpose the matrix (swap matrix[i][j] with matrix[j][i])
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            reverse(matrix[i]);
        }
    }

    private void reverse(int[] row) {
        int left = 0;
        int right = row.length - 1;
        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    // Alternative: Layer by layer rotation
    public void rotateLayer(int[][] matrix) {
        int n = matrix.length;
        
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top
                int top = matrix[first][i];
                
                // left -> top
                matrix[first][i] = matrix[last - offset][first];
                
                // bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // right -> bottom
                matrix[last][last - offset] = matrix[i][last];
                
                // top -> right
                matrix[i][last] = top;
            }
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test 1 - Before:");
        printMatrix(matrix1);
        solver.rotate(matrix1);
        System.out.println("Test 1 - After (90° clockwise):");
        printMatrix(matrix1);
        System.out.println("Expected: [[7,4,1],[8,5,2],[9,6,3]]");

        // Test Case 2
        int[][] matrix2 = {
            {5, 1, 9, 11},
            {2, 4, 8, 10},
            {13, 3, 6, 7},
            {15, 14, 12, 16}
        };
        System.out.println("\nTest 2 - Before:");
        printMatrix(matrix2);
        solver.rotate(matrix2);
        System.out.println("Test 2 - After:");
        printMatrix(matrix2);
        System.out.println("Expected: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]");

        // Test Case 3: 1x1
        int[][] matrix3 = {{1}};
        System.out.println("\nTest 3 - Before:");
        printMatrix(matrix3);
        solver.rotate(matrix3);
        System.out.println("Test 3 - After:");
        printMatrix(matrix3);
        System.out.println("Expected: [[1]]");

        // Test Case 4: 2x2
        int[][] matrix4 = {
            {1, 2},
            {3, 4}
        };
        System.out.println("\nTest 4 - Before:");
        printMatrix(matrix4);
        solver.rotate(matrix4);
        System.out.println("Test 4 - After:");
        printMatrix(matrix4);
        System.out.println("Expected: [[3,1],[4,2]]");

        // Verify both approaches match
        System.out.println("\n--- Verification (Transpose+Reverse vs Layer) ---");
        int[][][] testCases = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}},
            {{1}},
            {{1, 2}, {3, 4}}
        };
        
        for (int[][] test : testCases) {
            int[][] copy1 = deepCopy(test);
            int[][] copy2 = deepCopy(test);
            solver.rotate(copy1);
            solver.rotateLayer(copy2);
            boolean match = Arrays.deepEquals(copy1, copy2);
            System.out.println("Match: " + match);
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
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