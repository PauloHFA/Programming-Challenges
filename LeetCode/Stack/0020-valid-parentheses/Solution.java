import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * LeetCode 0020: Valid Parentheses
 * Difficulty: Easy
 * Category: Stack
 * 
 * Approach: Stack
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class Solution {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put(')', '(');
        brackets.put(']', '[');
        brackets.put('}', '{');

        for (char c : s.toCharArray()) {
            if (brackets.containsValue(c)) {
                // Opening bracket
                stack.push(c);
            } else if (brackets.containsKey(c)) {
                // Closing bracket
                if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "()";
        boolean result1 = solver.isValid(s1);
        System.out.println("Test 1: " + result1 + " (Expected: true)");

        // Test Case 2
        String s2 = "()[]{}";
        boolean result2 = solver.isValid(s2);
        System.out.println("Test 2: " + result2 + " (Expected: true)");

        // Test Case 3
        String s3 = "(]";
        boolean result3 = solver.isValid(s3);
        System.out.println("Test 3: " + result3 + " (Expected: false)");

        // Test Case 4
        String s4 = "([])";
        boolean result4 = solver.isValid(s4);
        System.out.println("Test 4: " + result4 + " (Expected: true)");

        // Test Case 5: Empty string
        String s5 = "";
        boolean result5 = solver.isValid(s5);
        System.out.println("Test 5: " + result5 + " (Expected: true)");

        // Test Case 6: Single opening
        String s6 = "(";
        boolean result6 = solver.isValid(s6);
        System.out.println("Test 6: " + result6 + " (Expected: false)");

        // Test Case 7: Single closing
        String s7 = ")";
        boolean result7 = solver.isValid(s7);
        System.out.println("Test 7: " + result7 + " (Expected: false)");

        // Test Case 8: Nested
        String s8 = "{[]}";
        boolean result8 = solver.isValid(s8);
        System.out.println("Test 8: " + result8 + " (Expected: true)");

        // Test Case 9: Wrong order
        String s9 = "([)]";
        boolean result9 = solver.isValid(s9);
        System.out.println("Test 9: " + result9 + " (Expected: false)");

        // Test Case 10: Complex valid
        String s10 = "({[]})";
        boolean result10 = solver.isValid(s10);
        System.out.println("Test 10: " + result10 + " (Expected: true)");

        // Test Case 11: Multiple groups
        String s11 = "()[]{}()[]{}";
        boolean result11 = solver.isValid(s11);
        System.out.println("Test 11: " + result11 + " (Expected: true)");

        // Test Case 12: Unmatched opening
        String s12 = "(((";
        boolean result12 = solver.isValid(s12);
        System.out.println("Test 12: " + result12 + " (Expected: false)");
    }
}