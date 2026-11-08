import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 0022: Generate Parentheses
 * Difficulty: Medium
 * Category: Backtracking / Stack
 * 
 * Approach: Backtracking (DFS)
 * Time Complexity: O(4^n / sqrt(n)) - Catalan number
 * Space Complexity: O(n) for recursion stack
 */
public class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }

        if (open < max) {
            current.append('(');
            backtrack(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1);
        }

        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int n1 = 3;
        List<String> result1 = solver.generateParenthesis(n1);
        System.out.println("Test 1 (n=3): " + result1);
        System.out.println("  Count: " + result1.size() + " (Expected: 5)");

        // Test Case 2
        int n2 = 1;
        List<String> result2 = solver.generateParenthesis(n2);
        System.out.println("Test 2 (n=1): " + result2);
        System.out.println("  Count: " + result2.size() + " (Expected: 1)");

        // Test Case 3
        int n3 = 2;
        List<String> result3 = solver.generateParenthesis(n3);
        System.out.println("Test 3 (n=2): " + result3);
        System.out.println("  Count: " + result3.size() + " (Expected: 2)");

        // Test Case 4
        int n4 = 4;
        List<String> result4 = solver.generateParenthesis(n4);
        System.out.println("Test 4 (n=4): " + result4.size() + " combinations (Expected: 14)");

        // Test Case 5
        int n5 = 0;
        List<String> result5 = solver.generateParenthesis(n5);
        System.out.println("Test 5 (n=0): " + result5 + " (Expected: [])");
    }
}