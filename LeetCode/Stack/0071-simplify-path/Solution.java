import java.util.Stack;
import java.util.Deque;
import java.util.ArrayDeque;

public class Solution {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");
        
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(part);
            }
        }
        
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, "/" + stack.pop());
        }
        
        return result.toString();
    }

    // Alternative: Using Stack class
    public String simplifyPathStack(String path) {
        Stack<String> stack = new Stack<>();
        String[] parts = path.split("/");
        
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(part);
            }
        }
        
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }

    // Alternative: StringBuilder with manual parsing (no split)
    public String simplifyPathManual(String path) {
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i <= path.length(); i++) {
            char c = (i < path.length()) ? path.charAt(i) : '/';
            
            if (c == '/') {
                String part = current.toString();
                current.setLength(0);
                
                if (part.isEmpty() || part.equals(".")) {
                    continue;
                } else if (part.equals("..")) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    stack.push(part);
                }
            } else {
                current.append(c);
            }
        }
        
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, "/" + stack.pop());
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        String[][] testCases = {
            {"/home/", "/home"},
            {"/../", "/"},
            {"/home//foo/", "/home/foo"},
            {"/a/./b/../../c/", "/c"},
            {"/a/../../b/../c//.//", "/c"},
            {"/a//b////c/d//././/..", "/a/b/c"},
            {"/...", "/..."},
            {"/..hidden", "/..hidden"},
            {"/", "/"},
            {"/.", "/"},
            {"/..", "/"},
            {"/home/user/Documents/../Pictures", "/home/user/Pictures"},
        };
        
        System.out.println("=== Simplify Path Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i][0];
            String expected = testCases[i][1];
            
            String result1 = solver.simplifyPath(input);
            String result2 = solver.simplifyPathStack(input);
            String result3 = solver.simplifyPathManual(input);
            
            boolean match1 = result1.equals(expected);
            boolean match2 = result2.equals(expected);
            boolean match3 = result3.equals(expected);
            
            System.out.println("Test " + (i + 1) + ": " + input);
            System.out.println("  Expected: " + expected);
            System.out.println("  Deque:    " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  Stack:    " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Manual:   " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}