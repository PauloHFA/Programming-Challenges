/**
 * LeetCode 150 - Evaluate Reverse Polish Notation
 * 
 * You are given an array of strings tokens that represents an arithmetic expression in a Reverse Polish Notation.
 * Evaluate the expression. Return an integer that represents the value of the expression.
 * 
 * Note that:
 * - The valid operators are '+', '-', '*', and '/'.
 * - Each operand may be an integer or another expression.
 * - The division between two integers always truncates toward zero.
 * - There will not be any division by zero.
 * - The input represents a valid arithmetic expression in a reverse polish notation.
 * - The answer and all the intermediate calculations can be represented in a 32-bit integer.
 * 
 * Example 1:
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 * 
 * Example 2:
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 * 
 * Example 3:
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 22
 * 
 * Constraints:
 * 1 <= tokens.length <= 10^4
 * tokens[i] is either an operator: "+", "-", "*", "/", or an integer in the range [-200, 200].
 */
public class Solution {
    
    /**
     * Approach 1: Stack - Standard Solution
     * 
     * Iterate through tokens. If operand, push to stack. If operator, pop two operands,
     * apply operation, push result back.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int evalRPN(String[] tokens) {
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor); // Java truncates toward zero
                    break;
                default:
                    stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    /**
     * Approach 2: Array as Stack (Slightly faster)
     * 
     * Use an array as stack to avoid Stack class overhead.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int evalRPNArray(String[] tokens) {
        int[] stack = new int[tokens.length];
        int top = -1;
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack[top - 1] = stack[top - 1] + stack[top];
                    top--;
                    break;
                case "-":
                    int b = stack[top--];
                    int a = stack[top];
                    stack[top] = a - b;
                    break;
                case "*":
                    stack[top - 1] = stack[top - 1] * stack[top];
                    top--;
                    break;
                case "/":
                    int divisor = stack[top--];
                    int dividend = stack[top];
                    stack[top] = dividend / divisor;
                    break;
                default:
                    stack[++top] = Integer.parseInt(token);
            }
        }
        
        return stack[top];
    }
    
    /**
     * Approach 3: Recursive (using index reference)
     * 
     * Process tokens from right to left recursively.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) recursion stack
     */
    public int evalRPNRecursive(String[] tokens) {
        int[] index = {tokens.length - 1};
        return evalRecursive(tokens, index);
    }
    
    private int evalRecursive(String[] tokens, int[] index) {
        String token = tokens[index[0]--];
        
        if (!isOperator(token)) {
            return Integer.parseInt(token);
        }
        
        // Note: order is reversed because we're processing right to left
        int right = evalRecursive(tokens, index);
        int left = evalRecursive(tokens, index);
        
        switch (token) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/": return left / right;
            default: throw new IllegalArgumentException("Invalid operator: " + token);
        }
    }
    
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
    
    /**
     * Approach 4: Using Deque (ArrayDeque)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int evalRPNDeque(String[] tokens) {
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        
        for (String token : tokens) {
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                switch (token) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/": result = a / b; break;
                }
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Evaluate Reverse Polish Notation Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [\"2\",\"1\",\"+\",\"3\",\"*\"] -> Expected: 9");
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        int result1 = sol.evalRPN(tokens1);
        System.out.println("  Stack:    " + result1 + (result1 == 9 ? " ✓" : " ✗"));
        System.out.println("  Array:    " + sol.evalRPNArray(tokens1));
        System.out.println("  Recursive:" + sol.evalRPNRecursive(tokens1));
        System.out.println("  Deque:    " + sol.evalRPNDeque(tokens1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [\"4\",\"13\",\"5\",\"/\",\"+\"] -> Expected: 6");
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        int result2 = sol.evalRPN(tokens2);
        System.out.println("  Stack:    " + result2 + (result2 == 6 ? " ✓" : " ✗"));
        System.out.println("  Array:    " + sol.evalRPNArray(tokens2));
        System.out.println("  Recursive:" + sol.evalRPNRecursive(tokens2));
        System.out.println("  Deque:    " + sol.evalRPNDeque(tokens2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: [\"10\",\"6\",\"9\",\"3\",\"+\",\"-11\",\"*\",\"/\",\"*\",\"17\",\"+\",\"5\",\"+\"] -> Expected: 22");
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        int result3 = sol.evalRPN(tokens3);
        System.out.println("  Stack:    " + result3 + (result3 == 22 ? " ✓" : " ✗"));
        System.out.println("  Array:    " + sol.evalRPNArray(tokens3));
        System.out.println("  Recursive:" + sol.evalRPNRecursive(tokens3));
        System.out.println("  Deque:    " + sol.evalRPNDeque(tokens3));
        
        // Test 4: Single number
        System.out.println("\nTest 4: [\"5\"] -> Expected: 5");
        String[] tokens4 = {"5"};
        int result4 = sol.evalRPN(tokens4);
        System.out.println("  Stack:    " + result4 + (result4 == 5 ? " ✓" : " ✗"));
        
        // Test 5: Negative numbers
        System.out.println("\nTest 5: [\"-2\",\"3\",\"+\"] -> Expected: 1");
        String[] tokens5 = {"-2", "3", "+"};
        int result5 = sol.evalRPN(tokens5);
        System.out.println("  Stack:    " + result5 + (result5 == 1 ? " ✓" : " ✗"));
        
        // Test 6: Division truncation
        System.out.println("\nTest 6: [\"7\",\"-3\",\"/\"] -> Expected: -2 (truncates toward zero)");
        String[] tokens6 = {"7", "-3", "/"};
        int result6 = sol.evalRPN(tokens6);
        System.out.println("  Stack:    " + result6 + (result6 == -2 ? " ✓" : " ✗"));
        
        // Test 7: Complex expression
        System.out.println("\nTest 7: [\"3\",\"4\",\"+\",\"2\",\"*\",\"7\",\"/\"] -> Expected: 2");
        String[] tokens7 = {"3", "4", "+", "2", "*", "7", "/"};
        int result7 = sol.evalRPN(tokens7);
        System.out.println("  Stack:    " + result7 + (result7 == 2 ? " ✓" : " ✗"));
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification of all approaches");
        String[][] testCases = {
            {"2", "1", "+", "3", "*"},
            {"4", "13", "5", "/", "+"},
            {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"},
            {"5"},
            {"-2", "3", "+"},
            {"7", "-3", "/"},
            {"3", "4", "+", "2", "*", "7", "/"},
            {"1", "2", "+", "3", "4", "+", "*"},
            {"100", "200", "+", "2", "/"},
            {"-10", "-20", "*", "5", "/"}
        };
        int[] expected = {9, 6, 22, 5, 1, -2, 2, 21, 150, 40};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.evalRPN(testCases[i]);
            int r2 = sol.evalRPNArray(testCases[i]);
            int r3 = sol.evalRPNRecursive(testCases[i]);
            int r4 = sol.evalRPNDeque(testCases[i]);
            
            boolean m1 = (r1 == expected[i]);
            boolean m2 = (r2 == expected[i]);
            boolean m3 = (r3 == expected[i]);
            boolean m4 = (r4 == expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Stack:" + r1 + " Arr:" + r2 + " Rec:" + r3 + " Deque:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}