public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Treat 2D matrix as 1D sorted array
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = matrix[mid / n][mid % n];
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }

    // Alternative: Two binary searches (first find row, then column)
    public boolean searchMatrixTwoBinary(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Binary search for row
        int top = 0, bottom = m - 1;
        int row = -1;
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            if (matrix[mid][0] <= target && matrix[mid][n - 1] >= target) {
                row = mid;
                break;
            } else if (matrix[mid][0] > target) {
                bottom = mid - 1;
            } else {
                top = mid + 1;
            }
        }
        
        if (row == -1) return false;
        
        // Binary search in row
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }

    // Alternative: Start from top-right corner (staircase search)
    public boolean searchMatrixStaircase(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int row = 0;
        int col = n - 1;
        
        while (row < m && col >= 0) {
            int current = matrix[row][col];
            if (current == target) {
                return true;
            } else if (current < target) {
                row++;
            } else {
                col--;
            }
        }
        
        return false;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test case 1
        int[][] matrix1 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        
        // Test case 2
        int[][] matrix2 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        
        // Test case 3: Single row
        int[][] matrix3 = {{1, 3, 5, 7}};
        
        // Test case 4: Single column
        int[][] matrix4 = {{1}, {3}, {5}, {7}};
        
        // Test case 5: Single element
        int[][] matrix5 = {{5}};
        
        // Test case 6: Target not found
        int[][] matrix6 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        
        Object[][] testCases = {
            {matrix1, 3, true},
            {matrix1, 13, false},
            {matrix2, 60, true},
            {matrix2, 0, false},
            {matrix3, 3, true},
            {matrix3, 2, false},
            {matrix4, 5, true},
            {matrix4, 4, false},
            {matrix5, 5, true},
            {matrix5, 3, false},
            {matrix6, 100, false},
            {matrix6, -1, false},
        };
        
        System.out.println("=== Search 2D Matrix Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[][] matrix = (int[][]) testCases[i][0];
            int target = (int) testCases[i][1];
            boolean expected = (boolean) testCases[i][2];
            
            boolean result1 = solver.searchMatrix(matrix, target);
            boolean result2 = solver.searchMatrixTwoBinary(matrix, target);
            boolean result3 = solver.searchMatrixStaircase(matrix, target);
            
            boolean match1 = result1 == expected;
            boolean match2 = result2 == expected;
            boolean match3 = result3 == expected;
            
            System.out.println("Test " + (i + 1) + ": target = " + target);
            System.out.println("  Expected: " + expected);
            System.out.println("  1D Binary:  " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  2 Binary:   " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Staircase:  " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}