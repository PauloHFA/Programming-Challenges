import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 0013: Roman to Integer
 * Difficulty: Easy
 * Category: Math
 * 
 * Approach: Single Pass with Lookahead
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public int romanToInt(String s) {
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        int total = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int currentValue = romanValues.get(s.charAt(i));

            // If next character exists and has greater value, subtract current
            if (i + 1 < n && currentValue < romanValues.get(s.charAt(i + 1))) {
                total -= currentValue;
            } else {
                total += currentValue;
            }
        }

        return total;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String s1 = "III";
        int result1 = solver.romanToInt(s1);
        System.out.println("Test 1: " + result1 + " (Expected: 3)");

        // Test Case 2
        String s2 = "LVIII";
        int result2 = solver.romanToInt(s2);
        System.out.println("Test 2: " + result2 + " (Expected: 58)");

        // Test Case 3
        String s3 = "MCMXCIV";
        int result3 = solver.romanToInt(s3);
        System.out.println("Test 3: " + result3 + " (Expected: 1994)");

        // Test Case 4: IV
        String s4 = "IV";
        int result4 = solver.romanToInt(s4);
        System.out.println("Test 4: " + result4 + " (Expected: 4)");

        // Test Case 5: IX
        String s5 = "IX";
        int result5 = solver.romanToInt(s5);
        System.out.println("Test 5: " + result5 + " (Expected: 9)");

        // Test Case 6: XL
        String s6 = "XL";
        int result6 = solver.romanToInt(s6);
        System.out.println("Test 6: " + result6 + " (Expected: 40)");

        // Test Case 7: XC
        String s7 = "XC";
        int result7 = solver.romanToInt(s7);
        System.out.println("Test 7: " + result7 + " (Expected: 90)");

        // Test Case 8: CD
        String s8 = "CD";
        int result8 = solver.romanToInt(s8);
        System.out.println("Test 8: " + result8 + " (Expected: 400)");

        // Test Case 9: CM
        String s9 = "CM";
        int result9 = solver.romanToInt(s9);
        System.out.println("Test 9: " + result9 + " (Expected: 900)");

        // Test Case 10: MMMCMXCIX (3999)
        String s10 = "MMMCMXCIX";
        int result10 = solver.romanToInt(s10);
        System.out.println("Test 10: " + result10 + " (Expected: 3999)");

        // Test Case 11: MMXXIV (2024)
        String s11 = "MMXXIV";
        int result11 = solver.romanToInt(s11);
        System.out.println("Test 11: " + result11 + " (Expected: 2024)");

        // Test Case 12: M (1000)
        String s12 = "M";
        int result12 = solver.romanToInt(s12);
        System.out.println("Test 12: " + result12 + " (Expected: 1000)");
    }
}