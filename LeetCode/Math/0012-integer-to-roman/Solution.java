/**
 * LeetCode 0012: Integer to Roman
 * Difficulty: Medium
 * Category: Math
 * 
 * Approach: Greedy with Value-Symbol Mapping
 * Time Complexity: O(1) - bounded by max roman numeral length
 * Space Complexity: O(1)
 */
public class Solution {

    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                roman.append(symbols[i]);
            }
        }

        return roman.toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int num1 = 3;
        String result1 = solver.intToRoman(num1);
        System.out.println("Test 1: " + result1 + " (Expected: III)");

        // Test Case 2
        int num2 = 58;
        String result2 = solver.intToRoman(num2);
        System.out.println("Test 2: " + result2 + " (Expected: LVIII)");

        // Test Case 3
        int num3 = 1994;
        String result3 = solver.intToRoman(num3);
        System.out.println("Test 3: " + result3 + " (Expected: MCMXCIV)");

        // Test Case 4: 4
        int num4 = 4;
        String result4 = solver.intToRoman(num4);
        System.out.println("Test 4: " + result4 + " (Expected: IV)");

        // Test Case 5: 9
        int num5 = 9;
        String result5 = solver.intToRoman(num5);
        System.out.println("Test 5: " + result5 + " (Expected: IX)");

        // Test Case 6: 40
        int num6 = 40;
        String result6 = solver.intToRoman(num6);
        System.out.println("Test 6: " + result6 + " (Expected: XL)");

        // Test Case 7: 90
        int num7 = 90;
        String result7 = solver.intToRoman(num7);
        System.out.println("Test 7: " + result7 + " (Expected: XC)");

        // Test Case 8: 400
        int num8 = 400;
        String result8 = solver.intToRoman(num8);
        System.out.println("Test 8: " + result8 + " (Expected: CD)");

        // Test Case 9: 900
        int num9 = 900;
        String result9 = solver.intToRoman(num9);
        System.out.println("Test 9: " + result9 + " (Expected: CM)");

        // Test Case 10: Max value 3999
        int num10 = 3999;
        String result10 = solver.intToRoman(num10);
        System.out.println("Test 10: " + result10 + " (Expected: MMMCMXCIX)");

        // Test Case 11: 2024
        int num11 = 2024;
        String result11 = solver.intToRoman(num11);
        System.out.println("Test 11: " + result11 + " (Expected: MMXXIV)");

        // Test Case 12: 1000
        int num12 = 1000;
        String result12 = solver.intToRoman(num12);
        System.out.println("Test 12: " + result12 + " (Expected: M)");
    }
}