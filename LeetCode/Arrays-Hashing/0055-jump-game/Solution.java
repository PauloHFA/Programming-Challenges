import java.util.Arrays;

/**
 * LeetCode 0055: Jump Game
 * Difficulty: Medium
 * Category: Arrays & Hashing / Greedy
 * 
 * Approach: Greedy (O(n) time, O(1) space)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int farthest = 0;

        for (int i = 0; i < n; i++) {
            // If we can't reach this position, return false
            if (i > farthest) {
                return false;
            }
            // Update farthest reachable position
            farthest = Math.max(farthest, i + nums[i]);
            
            // Early exit if we can reach the end
            if (farthest >= n - 1) {
                return true;
            }
        }

        return true;
    }

    // Alternative: DP (O(n^2) time, O(n) space) - for verification
    public boolean canJumpDP(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && j + nums[j] >= i) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n - 1];
    }

    // Alternative: Backwards Greedy
    public boolean canJumpBackwards(int[] nums) {
        int lastPos = nums.length - 1;
        
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        
        return lastPos == 0;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {2, 3, 1, 1, 4};
        boolean result1 = solver.canJump(nums1);
        System.out.println("Test 1: " + result1 + " (Expected: true)");

        // Test Case 2
        int[] nums2 = {3, 2, 1, 0, 4};
        boolean result2 = solver.canJump(nums2);
        System.out.println("Test 2: " + result2 + " (Expected: false)");

        // Test Case 3: Single element
        int[] nums3 = {0};
        boolean result3 = solver.canJump(nums3);
        System.out.println("Test 3: " + result3 + " (Expected: true)");

        // Test Case 4: Can reach end immediately
        int[] nums4 = {5, 0, 0, 0, 0};
        boolean result4 = solver.canJump(nums4);
        System.out.println("Test 4: " + result4 + " (Expected: true)");

        // Test Case 5: Stuck at zero
        int[] nums5 = {1, 0, 1, 0};
        boolean result5 = solver.canJump(nums5);
        System.out.println("Test 5: " + result5 + " (Expected: false)");

        // Test Case 6: All ones
        int[] nums6 = {1, 1, 1, 1, 1};
        boolean result6 = solver.canJump(nums6);
        System.out.println("Test 6: " + result6 + " (Expected: true)");

        // Test Case 7: Large jump at start
        int[] nums7 = {10, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        boolean result7 = solver.canJump(nums7);
        System.out.println("Test 7: " + result7 + " (Expected: true)");

        // Test Case 8: Zero at start but length 1
        int[] nums8 = {0, 1};
        boolean result8 = solver.canJump(nums8);
        System.out.println("Test 8: " + result8 + " (Expected: false)");

        // Test Case 9: Complex
        int[] nums9 = {2, 0, 6, 9, 8, 4, 5, 0, 8, 9, 1, 2, 9, 6, 8, 8, 0, 6, 3, 1, 2, 2, 1, 2, 6, 5, 3, 1, 2, 2, 6, 4, 2, 4, 3, 0, 0, 0, 3, 8, 2, 4, 0, 1, 2, 0, 1, 4, 6, 5, 8, 0, 7, 9, 3, 4, 6, 6, 5, 8, 9, 3, 4, 3, 7, 0, 4, 9, 0, 9, 8, 4, 3, 0, 7, 7, 1, 9, 1, 9, 4, 9, 0, 1, 9, 5, 7, 7, 1, 5, 8, 2, 8, 2, 6, 8, 2, 2, 7, 5, 1, 7, 9, 6};
        boolean result9 = solver.canJump(nums9);
        System.out.println("Test 9: " + result9 + " (Expected: false)");

        // Test Case 10: Another complex
        int[] nums10 = {2, 0, 0};
        boolean result10 = solver.canJump(nums10);
        System.out.println("Test 10: " + result10 + " (Expected: true)");

        // Verify all approaches match
        System.out.println("\n--- Verification (Forward vs Backwards vs DP) ---");
        int[][] testCases = {nums1, nums2, nums3, nums4, nums5, nums6, nums7, nums8, nums9, nums10};
        for (int[] test : testCases) {
            boolean forward = solver.canJump(test.clone());
            boolean backward = solver.canJumpBackwards(test.clone());
            boolean dp = solver.canJumpDP(test.clone());
            System.out.println("Input: " + Arrays.toString(test) + " -> Forward: " + forward + ", Backward: " + backward + ", DP: " + dp + " (match: " + (forward == backward && backward == dp) + ")");
        }
    }
}