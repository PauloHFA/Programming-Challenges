import java.util.Stack;
import java.util.Arrays;

public class Solution {
    public int largestRectangleArea(int[] heights) {
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

    // Alternative: Two passes with left/right boundaries
    public int largestRectangleAreaTwoPass(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        // Find left boundary for each bar
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        
        stack.clear();
        
        // Find right boundary for each bar
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        
        // Calculate max area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int width = right[i] - left[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        
        return maxArea;
    }

    // Alternative: Divide and conquer (for verification)
    public int largestRectangleAreaDivideConquer(int[] heights) {
        return divideConquer(heights, 0, heights.length - 1);
    }
    
    private int divideConquer(int[] heights, int left, int right) {
        if (left > right) return 0;
        if (left == right) return heights[left];
        
        int minIndex = left;
        for (int i = left + 1; i <= right; i++) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }
        
        int area = heights[minIndex] * (right - left + 1);
        int leftArea = divideConquer(heights, left, minIndex - 1);
        int rightArea = divideConquer(heights, minIndex + 1, right);
        
        return Math.max(area, Math.max(leftArea, rightArea));
    }

    // Alternative: Brute force (for small inputs)
    public int largestRectangleAreaBruteForce(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }
        
        return maxArea;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        int[][] testCases = {
            {2, 1, 5, 6, 2, 3},
            {2, 4},
            {1},
            {2, 1, 2},
            {5, 4, 3, 2, 1},
            {1, 2, 3, 4, 5},
            {2, 2, 2, 2},
            {1, 3, 2, 1, 2},
            {6, 2, 5, 4, 5, 1, 6},
        };
        
        int[] expected = {10, 4, 1, 3, 9, 9, 8, 5, 12};
        
        System.out.println("=== Largest Rectangle in Histogram Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[] heights = testCases[i];
            int exp = expected[i];
            
            int result1 = solver.largestRectangleArea(heights);
            int result2 = solver.largestRectangleAreaTwoPass(heights);
            int result3 = (heights.length <= 15) ? solver.largestRectangleAreaDivideConquer(heights) : -1;
            int result4 = (heights.length <= 15) ? solver.largestRectangleAreaBruteForce(heights) : -1;
            
            boolean match1 = result1 == exp;
            boolean match2 = result2 == exp;
            boolean match3 = result3 == -1 || result3 == exp;
            boolean match4 = result4 == -1 || result4 == exp;
            
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(heights));
            System.out.println("  Expected: " + exp);
            System.out.println("  Stack:    " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  TwoPass:  " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  DivConq:  " + (result3 == -1 ? "N/A" : result3) + (match3 ? " ✓" : " ✗"));
            System.out.println("  Brute:    " + (result4 == -1 ? "N/A" : result4) + (match4 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 && match4 ? "✓" : "✗"));
            System.out.println();
        }
    }
}