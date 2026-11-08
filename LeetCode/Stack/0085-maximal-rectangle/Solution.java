import java.util.Stack;

public class Solution {
    
    // Método principal: Monotonic Stack baseado no 0084
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            // Atualiza heights para a linha atual
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            
            // Calcula maior retângulo no histograma atual
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        
        return maxArea;
    }
    
    // Reutiliza a solução do 0084 - Largest Rectangle in Histogram
    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];
            
            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            stack.push(i);
        }
        
        return maxArea;
    }
    
    // Método 2: DP com limites esquerdo/direito
    public int maximalRectangleDP(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] height = new int[cols];
        int[] left = new int[cols];
        int[] right = new int[cols];
        
        for (int j = 0; j < cols; j++) {
            right[j] = cols;
        }
        
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            int curLeft = 0, curRight = cols;
            
            // Atualiza height
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                } else {
                    height[j] = 0;
                }
            }
            
            // Atualiza left
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], curLeft);
                } else {
                    left[j] = 0;
                    curLeft = j + 1;
                }
            }
            
            // Atualiza right
            for (int j = cols - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], curRight);
                } else {
                    right[j] = cols;
                    curRight = j;
                }
            }
            
            // Calcula área
            for (int j = 0; j < cols; j++) {
                maxArea = Math.max(maxArea, height[j] * (right[j] - left[j]));
            }
        }
        
        return maxArea;
    }
    
    // Método 3: Força Bruta (verificação)
    public int maximalRectangleBrute(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int maxArea = 0;
        
        for (int r1 = 0; r1 < rows; r1++) {
            for (int c1 = 0; c1 < cols; c1++) {
                if (matrix[r1][c1] == '0') continue;
                
                for (int r2 = r1; r2 < rows; r2++) {
                    for (int c2 = c1; c2 < cols; c2++) {
                        boolean allOnes = true;
                        for (int r = r1; r <= r2 && allOnes; r++) {
                            for (int c = c1; c <= c2; c++) {
                                if (matrix[r][c] == '0') {
                                    allOnes = false;
                                    break;
                                }
                            }
                        }
                        
                        if (allOnes) {
                            int area = (r2 - r1 + 1) * (c2 - c1 + 1);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }
        
        return maxArea;
    }
    
    // Método 4: Histograma por linha com stack (variante)
    public int maximalRectangleStackVariant(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols + 1]; // +1 para sentinela
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            Stack<Integer> stack = new Stack<>();
            
            for (int j = 0; j <= cols; j++) {
                if (j < cols) {
                    heights[j] = (matrix[i][j] == '1') ? heights[j] + 1 : 0;
                } else {
                    heights[j] = 0; // sentinela
                }
                
                while (!stack.isEmpty() && heights[j] < heights[stack.peek()]) {
                    int h = heights[stack.pop()];
                    int w = stack.isEmpty() ? j : j - stack.peek() - 1;
                    maxArea = Math.max(maxArea, h * w);
                }
                
                stack.push(j);
            }
        }
        
        return maxArea;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Maximal Rectangle Tests ===\n");
        
        // Test 1: Example 1
        char[][] matrix1 = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        testMatrix(sol, matrix1, 6, "Test 1");
        
        // Test 2: Example 2
        char[][] matrix2 = {{'0'}};
        testMatrix(sol, matrix2, 0, "Test 2");
        
        // Test 3: Example 3
        char[][] matrix3 = {{'1'}};
        testMatrix(sol, matrix3, 1, "Test 3");
        
        // Test 4: All ones
        char[][] matrix4 = {
            {'1','1','1'},
            {'1','1','1'},
            {'1','1','1'}
        };
        testMatrix(sol, matrix4, 9, "Test 4: All ones 3x3");
        
        // Test 5: Single row
        char[][] matrix5 = {{'1','0','1','1','1','0','1'}};
        testMatrix(sol, matrix5, 3, "Test 5: Single row");
        
        // Test 6: Single column
        char[][] matrix6 = {{'1'},{'1'},{'0'},{'1'},{'1'}};
        testMatrix(sol, matrix6, 2, "Test 6: Single column");
        
        // Test 7: Checkerboard
        char[][] matrix7 = {
            {'1','0','1','0'},
            {'0','1','0','1'},
            {'1','0','1','0'}
        };
        testMatrix(sol, matrix7, 1, "Test 7: Checkerboard");
        
        // Test 8: L-shape
        char[][] matrix8 = {
            {'1','1','1'},
            {'1','0','0'},
            {'1','0','0'}
        };
        testMatrix(sol, matrix8, 3, "Test 8: L-shape");
        
        // Test 9: Two separate rectangles
        char[][] matrix9 = {
            {'1','1','0','1','1'},
            {'1','1','0','1','1'},
            {'0','0','0','0','0'},
            {'1','1','1','1','1'}
        };
                testMatrix(sol, matrix9, 5, "Test 9: Two rectangles");
        
        // Test 10: Large rectangle at bottom
        char[][] matrix10 = {
            {'0','0','0','0'},
            {'0','0','0','0'},
            {'1','1','1','1'},
            {'1','1','1','1'}
        };
        testMatrix(sol, matrix10, 8, "Test 10: Bottom rectangle");
        
        // Test 11: Empty matrix
        char[][] matrix11 = {};
        testMatrix(sol, matrix11, 0, "Test 11: Empty matrix");
        
        // Test 12: Matrix with empty rows
        char[][] matrix12 = {{}};
        testMatrix(sol, matrix12, 0, "Test 12: Empty row");
    }
    
    private static void testMatrix(Solution sol, char[][] matrix, int expected, String testName) {
        int result1 = sol.maximalRectangle(matrix);
        int result2 = sol.maximalRectangleDP(matrix);
        int result3 = sol.maximalRectangleStackVariant(matrix);
        int result4 = (matrix.length <= 5 && matrix.length > 0 && matrix[0].length <= 5) 
            ? sol.maximalRectangleBrute(matrix) : result1;
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(testName + ":");
        System.out.println("  Expected: " + expected);
        System.out.println("  Stack:    " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  DP:       " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  StackVar: " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Brute:    " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}