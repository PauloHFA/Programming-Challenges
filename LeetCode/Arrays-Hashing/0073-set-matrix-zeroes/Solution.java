import java.util.Arrays;

public class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // Check if first row has zero
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        // Check if first column has zero
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        // Use first row and column as markers
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        // Set zeros based on markers
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Handle first row
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        
        // Handle first column
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // Alternative: Using extra arrays (O(m+n) space)
    public void setZeroesExtraSpace(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean[] rows = new boolean[m];
        boolean[] cols = new boolean[n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rows[i] = true;
                    cols[j] = true;
                }
            }
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rows[i] || cols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // Alternative: Using sets
    public void setZeroesSets(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        java.util.Set<Integer> zeroRows = new java.util.HashSet<>();
        java.util.Set<Integer> zeroCols = new java.util.HashSet<>();
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows.add(i);
                    zeroCols.add(j);
                }
            }
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRows.contains(i) || zeroCols.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // Helper to print matrix
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Helper to deep copy matrix
    private int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    // Helper to compare matrices
    private boolean matricesEqual(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test case 1
        int[][] matrix1 = {{1,1,1},{1,0,1},{1,1,1}};
        int[][] expected1 = {{1,0,1},{0,0,0},{1,0,1}};
        
        // Test case 2
        int[][] matrix2 = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[][] expected2 = {{0,0,0,0},{0,4,5,0},{0,3,1,0}};
        
        // Test case 3: Single row
        int[][] matrix3 = {{1,0,3}};
        int[][] expected3 = {{0,0,0}};
        
        // Test case 4: Single column
        int[][] matrix4 = {{1},{0},{3}};
        int[][] expected4 = {{0},{0},{0}};
        
        // Test case 5: All zeros
        int[][] matrix5 = {{0,0},{0,0}};
        int[][] expected5 = {{0,0},{0,0}};
        
        // Test case 6: No zeros
        int[][] matrix6 = {{1,2},{3,4}};
        int[][] expected6 = {{1,2},{3,4}};
        
        // Test case 7: Zero in first row and column
        int[][] matrix7 = {{0,1,2},{3,4,5},{6,7,8}};
        int[][] expected7 = {{0,0,0},{0,4,5},{0,7,8}};
        
        int[][][] testMatrices = {matrix1, matrix2, matrix3, matrix4, matrix5, matrix6, matrix7};
        int[][][] expectedMatrices = {expected1, expected2, expected3, expected4, expected5, expected6, expected7};
        
        System.out.println("=== Set Matrix Zeroes Tests ===");
        for (int t = 0; t < testMatrices.length; t++) {
            int[][] input = testMatrices[t];
            int[][] expected = expectedMatrices[t];
            
            int[][] copy1 = solver.copyMatrix(input);
            int[][] copy2 = solver.copyMatrix(input);
            int[][] copy3 = solver.copyMatrix(input);
            
            solver.setZeroes(copy1);
            solver.setZeroesExtraSpace(copy2);
            solver.setZeroesSets(copy3);
            
            boolean match1 = solver.matricesEqual(copy1, expected);
            boolean match2 = solver.matricesEqual(copy2, expected);
            boolean match3 = solver.matricesEqual(copy3, expected);
            
            System.out.println("Test " + (t + 1) + ":");
            System.out.println("  Input:");
            solver.printMatrix(input);
            System.out.println("  Expected:");
            solver.printMatrix(expected);
            System.out.println("  O(1) Space: " + (match1 ? "✓" : "✗"));
            System.out.println("  O(m+n) Space: " + (match2 ? "✓" : "✗"));
            System.out.println("  Sets: " + (match3 ? "✓" : "✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}