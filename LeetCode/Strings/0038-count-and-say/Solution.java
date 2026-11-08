/**
 * LeetCode 0038: Count and Say
 * Difficulty: Medium
 * Category: Strings
 * 
 * Approach: Iterative string building
 * Time Complexity: O(2^n) approximately - each term roughly doubles in length
 * Space Complexity: O(2^n) for the output string
 */
public class Solution {

    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String prev = countAndSay(n - 1);
        StringBuilder result = new StringBuilder();
        
        int count = 1;
        char currentChar = prev.charAt(0);
        
        for (int i = 1; i < prev.length(); i++) {
            if (prev.charAt(i) == currentChar) {
                count++;
            } else {
                result.append(count).append(currentChar);
                currentChar = prev.charAt(i);
                count = 1;
            }
        }
        
        // Append the last group
        result.append(count).append(currentChar);
        
        return result.toString();
    }

    // Iterative version (more efficient)
    public String countAndSayIterative(int n) {
        String result = "1";
        
        for (int i = 2; i <= n; i++) {
            StringBuilder next = new StringBuilder();
            int count = 1;
            char currentChar = result.charAt(0);
            
            for (int j = 1; j < result.length(); j++) {
                if (result.charAt(j) == currentChar) {
                    count++;
                } else {
                    next.append(count).append(currentChar);
                    currentChar = result.charAt(j);
                    count = 1;
                }
            }
            
            next.append(count).append(currentChar);
            result = next.toString();
        }
        
        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: n = 1
        String result1 = solver.countAndSay(1);
        System.out.println("Test 1 (n=1): " + result1 + " (Expected: 1)");

        // Test Case 2: n = 2
        String result2 = solver.countAndSay(2);
        System.out.println("Test 2 (n=2): " + result2 + " (Expected: 11)");

        // Test Case 3: n = 3
        String result3 = solver.countAndSay(3);
        System.out.println("Test 3 (n=3): " + result3 + " (Expected: 21)");

        // Test Case 4: n = 4
        String result4 = solver.countAndSay(4);
        System.out.println("Test 4 (n=4): " + result4 + " (Expected: 1211)");

        // Test Case 5: n = 5
        String result5 = solver.countAndSay(5);
        System.out.println("Test 5 (n=5): " + result5 + " (Expected: 111221)");

        // Test Case 6: n = 6
        String result6 = solver.countAndSay(6);
        System.out.println("Test 6 (n=6): " + result6 + " (Expected: 312211)");

        // Test Case 7: n = 7
        String result7 = solver.countAndSay(7);
        System.out.println("Test 7 (n=7): " + result7 + " (Expected: 13112221)");

        // Test Case 8: n = 8
        String result8 = solver.countAndSay(8);
        System.out.println("Test 8 (n=8): " + result8 + " (Expected: 1113213211)");

        // Test iterative version
        System.out.println("\n--- Iterative Version ---");
        for (int i = 1; i <= 8; i++) {
            String iterResult = solver.countAndSayIterative(i);
            String recResult = solver.countAndSay(i);
            System.out.println("n=" + i + ": " + iterResult + " (match: " + iterResult.equals(recResult) + ")");
        }
    }
}