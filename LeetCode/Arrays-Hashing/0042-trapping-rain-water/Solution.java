import java.util.Arrays;

/**
 * LeetCode 0042: Trapping Rain Water
 * Difficulty: Hard
 * Category: Arrays & Hashing / Two Pointers
 * 
 * Approach: Two Pointers (optimal O(n) time, O(1) space)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }

    // Alternative: Dynamic Programming (O(n) time, O(n) space)
    public int trapDP(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return water;
    }

    // Alternative: Stack (O(n) time, O(n) space)
    public int trapStack(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        java.util.Stack<Integer> stack = new java.util.Stack<>();
        int water = 0;

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                water += distance * boundedHeight;
            }
            stack.push(i);
        }

        return water;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int result1 = solver.trap(height1);
        System.out.println("Test 1: " + result1 + " (Expected: 6)");

        // Test Case 2
        int[] height2 = {4, 2, 0, 3, 2, 5};
        int result2 = solver.trap(height2);
        System.out.println("Test 2: " + result2 + " (Expected: 9)");

        // Test Case 3: Empty
        int[] height3 = {};
        int result3 = solver.trap(height3);
        System.out.println("Test 3: " + result3 + " (Expected: 0)");

        // Test Case 4: Single element
        int[] height4 = {5};
        int result4 = solver.trap(height4);
        System.out.println("Test 4: " + result4 + " (Expected: 0)");

        // Test Case 5: Increasing
        int[] height5 = {1, 2, 3, 4, 5};
        int result5 = solver.trap(height5);
        System.out.println("Test 5: " + result5 + " (Expected: 0)");

        // Test Case 6: Decreasing
        int[] height6 = {5, 4, 3, 2, 1};
        int result6 = solver.trap(height6);
        System.out.println("Test 6: " + result6 + " (Expected: 0)");

        // Test Case 7: Valley
        int[] height7 = {5, 1, 5};
        int result7 = solver.trap(height7);
        System.out.println("Test 7: " + result7 + " (Expected: 4)");

        // Test Case 8: Multiple valleys
        int[] height8 = {5, 2, 1, 2, 5};
        int result8 = solver.trap(height8);
        System.out.println("Test 8: " + result8 + " (Expected: 10)");

        // Test Case 9: Flat
        int[] height9 = {3, 3, 3, 3};
        int result9 = solver.trap(height9);
        System.out.println("Test 9: " + result9 + " (Expected: 0)");

        // Test Case 10: Large
        int[] height10 = new int[1000];
        for (int i = 0; i < 500; i++) {
            height10[i] = i;
            height10[999 - i] = i;
        }
        int result10 = solver.trap(height10);
        System.out.println("Test 10: " + result10);

        // Verify all approaches match
        System.out.println("\n--- Verification ---");
        int[][] testCases = {height1, height2, height3, height4, height5, height6, height7, height8, height9};
        for (int[] test : testCases) {
            int twoPtr = solver.trap(test.clone());
            int dp = solver.trapDP(test.clone());
            int stack = solver.trapStack(test.clone());
            System.out.println("Input: " + Arrays.toString(test) + " -> TwoPtr: " + twoPtr + ", DP: " + dp + ", Stack: " + stack + " (match: " + (twoPtr == dp && dp == stack) + ")");
        }
    }
}