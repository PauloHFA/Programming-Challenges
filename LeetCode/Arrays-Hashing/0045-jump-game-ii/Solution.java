import java.util.Arrays;

/**
 * LeetCode 0045: Jump Game II
 * Difficulty: Medium
 * Category: Arrays & Hashing / Greedy
 * 
 * Approach: Greedy BFS (O(n) time, O(1) space)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int jump(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        // We don't need to jump from the last position
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // When we reach the end of current jump range
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                
                // Early exit if we can reach the end
                if (currentEnd >= n - 1) {
                    break;
                }
            }
        }

        return jumps;
    }

    // Alternative: DP (O(n^2) time, O(n) space) - for verification
    public int jumpDP(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i && dp[j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[] nums1 = {2, 3, 1, 1, 4};
        int result1 = solver.jump(nums1);
        System.out.println("Test 1: " + result1 + " (Expected: 2)");

        // Test Case 2
        int[] nums2 = {2, 3, 0, 1, 4};
        int result2 = solver.jump(nums2);
        System.out.println("Test 2: " + result2 + " (Expected: 2)");

        // Test Case 3: Single element
        int[] nums3 = {0};
        int result3 = solver.jump(nums3);
        System.out.println("Test 3: " + result3 + " (Expected: 0)");

        // Test Case 4: Two elements
        int[] nums4 = {1, 2};
        int result4 = solver.jump(nums4);
        System.out.println("Test 4: " + result4 + " (Expected: 1)");

        // Test Case 5: Already at end
        int[] nums5 = {1, 1, 1, 1};
        int result5 = solver.jump(nums5);
        System.out.println("Test 5: " + result5 + " (Expected: 3)");

        // Test Case 6: Large jumps (can reach index 5 from index 0, need 2 jumps to reach end)
                int[] nums6 = {5, 4, 3, 2, 1, 1, 0};
        int result6 = solver.jump(nums6);
                System.out.println("Test 6: " + result6 + " (Expected: 2)");

        // Test Case 7: Zigzag
        int[] nums7 = {1, 2, 3, 4, 5};
        int result7 = solver.jump(nums7);
        System.out.println("Test 7: " + result7 + " (Expected: 3)");

                // Test Case 8: Minimum jumps (valid - can reach end)
                int[] nums8 = {3, 2, 1, 1, 4};
        int result8 = solver.jump(nums8);
        System.out.println("Test 8: " + result8 + " (Expected: 2)");

        // Test Case 9: All same
        int[] nums9 = {2, 2, 2, 2, 2};
        int result9 = solver.jump(nums9);
        System.out.println("Test 9: " + result9 + " (Expected: 2)");

        // Test Case 10: Large array
        int[] nums10 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums10[i] = 1;
        }
        int result10 = solver.jump(nums10);
        System.out.println("Test 10: " + result10 + " (Expected: 999)");

        // Verify greedy matches DP
        System.out.println("\n--- Verification (Greedy vs DP) ---");
                int[][] testCases = {
                    {2, 3, 1, 1, 4},
                    {2, 3, 0, 1, 4},
                    {0},
                    {1, 2},
                    {1, 1, 1, 1},
                    {5, 4, 3, 2, 1, 1, 0},
                    {1, 2, 3, 4, 5},
                    {3, 2, 1, 1, 4},
                    {2, 2, 2, 2, 2}
                };
                for (int[] test : testCases) {
                    int greedy = solver.jump(test.clone());
                    int dp = solver.jumpDP(test.clone());
                    System.out.println("Input: " + Arrays.toString(test) + " -> Greedy: " + greedy + ", DP: " + dp + " (match: " + (greedy == dp) + ")");
                }
    }
}