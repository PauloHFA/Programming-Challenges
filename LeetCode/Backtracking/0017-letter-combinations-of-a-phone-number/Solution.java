import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 0017: Letter Combinations of a Phone Number
 * Difficulty: Medium
 * Category: Backtracking
 * 
 * Approach: Backtracking (DFS)
 * Time Complexity: O(4^n * n) where n is the length of digits
 * Space Complexity: O(n) for recursion stack
 */
public class Solution {

    private static final Map<Character, String> PHONE_MAP = new HashMap<>();

    static {
        PHONE_MAP.put('2', "abc");
        PHONE_MAP.put('3', "def");
        PHONE_MAP.put('4', "ghi");
        PHONE_MAP.put('5', "jkl");
        PHONE_MAP.put('6', "mno");
        PHONE_MAP.put('7', "pqrs");
        PHONE_MAP.put('8', "tuv");
        PHONE_MAP.put('9', "wxyz");
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current, String digits, int index) {
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        String letters = PHONE_MAP.get(digits.charAt(index));
        for (char letter : letters.toCharArray()) {
            current.append(letter);
            backtrack(result, current, digits, index + 1);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String digits1 = "23";
        List<String> result1 = solver.letterCombinations(digits1);
        System.out.println("Test 1: " + result1 + " (Expected: [ad, ae, af, bd, be, bf, cd, ce, cf])");

        // Test Case 2
        String digits2 = "";
        List<String> result2 = solver.letterCombinations(digits2);
        System.out.println("Test 2: " + result2 + " (Expected: [])");

        // Test Case 3
        String digits3 = "2";
        List<String> result3 = solver.letterCombinations(digits3);
        System.out.println("Test 3: " + result3 + " (Expected: [a, b, c])");

        // Test Case 4: Single digit 7 (4 letters)
        String digits4 = "7";
        List<String> result4 = solver.letterCombinations(digits4);
        System.out.println("Test 4: " + result4 + " (Expected: [p, q, r, s])");

        // Test Case 5: Single digit 9 (4 letters)
        String digits5 = "9";
        List<String> result5 = solver.letterCombinations(digits5);
        System.out.println("Test 5: " + result5 + " (Expected: [w, x, y, z])");

        // Test Case 6: Three digits
        String digits6 = "234";
        List<String> result6 = solver.letterCombinations(digits6);
        System.out.println("Test 6: " + result6.size() + " combinations (Expected: 27)");

        // Test Case 7: Four digits
        String digits7 = "2345";
        List<String> result7 = solver.letterCombinations(digits7);
        System.out.println("Test 7: " + result7.size() + " combinations (Expected: 81)");

        // Test Case 8: With 7 and 9
        String digits8 = "79";
        List<String> result8 = solver.letterCombinations(digits8);
        System.out.println("Test 8: " + result8.size() + " combinations (Expected: 16)");
        System.out.println("  First few: " + result8.subList(0, Math.min(5, result8.size())));

        // Test Case 9: All same digit
        String digits9 = "222";
        List<String> result9 = solver.letterCombinations(digits9);
        System.out.println("Test 9: " + result9.size() + " combinations (Expected: 27)");

        // Test Case 10: Longer sequence
        String digits10 = "23456";
        List<String> result10 = solver.letterCombinations(digits10);
        System.out.println("Test 10: " + result10.size() + " combinations (Expected: 243)");
    }
}