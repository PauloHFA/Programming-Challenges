/**
 * LeetCode 0011: Container With Most Water
 * Difficulty: Medium
 * Category: Two Pointers
 * 
 * Approach: Two Pointers (Greedy)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int currentArea = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, currentArea);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int result1 = solver.maxArea(height1);
        System.out.println("Test 1: " + result1 + " (Expected: 49)");

        // Test Case 2
        int[] height2 = {1, 1};
        int result2 = solver.maxArea(height2);
        System.out.println("Test 2: " + result2 + " (Expected: 1)");

        // Test Case 3
        int[] height3 = {4, 3, 2, 1, 4};
        int result3 = solver.maxArea(height3);
        System.out.println("Test 3: " + result3 + " (Expected: 16)");

        // Test Case 4
        int[] height4 = {1, 2, 1};
        int result4 = solver.maxArea(height4);
        System.out.println("Test 4: " + result4 + " (Expected: 2)");

        // Test Case 5: Increasing heights
        int[] height5 = {1, 2, 3, 4, 5};
        int result5 = solver.maxArea(height5);
        System.out.println("Test 5: " + result5 + " (Expected: 6)");

        // Test Case 6: Decreasing heights
        int[] height6 = {5, 4, 3, 2, 1};
        int result6 = solver.maxArea(height6);
        System.out.println("Test 6: " + result6 + " (Expected: 6)");

        // Test Case 7: All same height
        int[] height7 = {5, 5, 5, 5, 5};
        int result7 = solver.maxArea(height7);
        System.out.println("Test 7: " + result7 + " (Expected: 20)");

        // Test Case 8: Two elements
        int[] height8 = {2, 3};
        int result8 = solver.maxArea(height8);
        System.out.println("Test 8: " + result8 + " (Expected: 2)");

        // Test Case 9: Large array
        int[] height9 = {1, 3, 2, 5, 25, 24, 5};
        int result9 = solver.maxArea(height9);
        System.out.println("Test 9: " + result9 + " (Expected: 24)");

        // Test Case 10: Peak in middle
        int[] height10 = {2, 3, 10, 5, 7, 8, 9};
        int result10 = solver.maxArea(height10);
        System.out.println("Test 10: " + result10 + " (Expected: 36)");
    }
}