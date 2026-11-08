import java.util.Stack;

/**
 * LeetCode 0032: Longest Valid Parentheses
 * Difficulty: Hard
 * Category: Strings / Stack / Dynamic Programming
 * 
 * Approach: Stack
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class Solution {

    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Base index for valid substring calculation
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i); // New base index
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }

    // Alternative: Dynamic Programming approach
    public int longestValidParenthesesDP(String s) {
        int n = s.length();
        int[] dp = new int[n];
        int maxLen = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // Case: "()"
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    // Case: "(())" or "()(())"
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0);
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "(()";
        int result1 = solver.longestValidParentheses(s1);
        System.out.println("Test 1: " + result1 + " (Expected: 2)");

        // Test Case 2
        String s2 = ")()())";
        int result2 = solver.longestValidParentheses(s2);
        System.out.println("Test 2: " + result2 + " (Expected: 4)");

        // Test Case 3
        String s3 = "";
        int result3 = solver.longestValidParentheses(s3);
        System.out.println("Test 3: " + result3 + " (Expected: 0)");

        // Test Case 4: All valid
        String s4 = "()()()";
        int result4 = solver.longestValidParentheses(s4);
        System.out.println("Test 4: " + result4 + " (Expected: 6)");

        // Test Case 5: Nested
        String s5 = "((()))";
        int result5 = solver.longestValidParentheses(s5);
        System.out.println("Test 5: " + result5 + " (Expected: 6)");

        // Test Case 6: Mixed
        String s6 = "()(()";
        int result6 = solver.longestValidParentheses(s6);
        System.out.println("Test 6: " + result6 + " (Expected: 2)");

        // Test Case 7: Starts with closing
        String s7 = ")()())";
        int result7 = solver.longestValidParentheses(s7);
        System.out.println("Test 7: " + result7 + " (Expected: 4)");

        // Test Case 8: Complex
        String s8 = "(()())";
        int result8 = solver.longestValidParentheses(s8);
        System.out.println("Test 8: " + result8 + " (Expected: 6)");

        // Test Case 9: Single pair
        String s9 = "()";
        int result9 = solver.longestValidParentheses(s9);
        System.out.println("Test 9: " + result9 + " (Expected: 2)");

        // Test Case 10: No valid
        String s10 = "((((";
        int result10 = solver.longestValidParentheses(s10);
        System.out.println("Test 10: " + result10 + " (Expected: 0)");

        // Test DP approach
        System.out.println("\n--- DP Approach Tests ---");
        System.out.println("Test 1 DP: " + solver.longestValidParenthesesDP(s1) + " (Expected: 2)");
        System.out.println("Test 2 DP: " + solver.longestValidParenthesesDP(s2) + " (Expected: 4)");
        System.out.println("Test 6 DP: " + solver.longestValidParenthesesDP(s6) + " (Expected: 2)");
    }
}