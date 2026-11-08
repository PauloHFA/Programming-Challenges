/**
 * LeetCode 0065: Valid Number
 * Difficulty: Hard
 * Category: Strings / Finite State Machine
 * 
 * Approach: Finite State Machine (DFA)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public boolean isNumber(String s) {
        // Trim whitespace
        s = s.trim();
        if (s.isEmpty()) return false;

        // State machine states
        // 0: start
        // 1: sign before number
            // 2: integer part (digits before decimal point)
            // 3: decimal point seen (no digits before)
            // 4: decimal part (digits after decimal point)
            // 5: 'e' or 'E' seen
        // 6: sign after e
        // 7: exponent integer part
            // 8: decimal point seen after integer digits (state 2 -> 8 on '.')
        
            int state = 0;
            for (char c : s.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    // Digit
                    if (state <= 2) state = 2;           // 0,1,2 -> 2 (integer part)
                    else if (state == 3 || state == 4) state = 4; // 3,4 -> 4 (decimal part)
                    else if (state == 8) state = 4;      // 8 -> 4 (decimal after integer)
                    else if (state >= 5) state = 7;      // 5,6,7 -> 7 (exponent part)
                    else return false;
                } else if (c == '+' || c == '-') {
                    // Sign
                    if (state == 0) state = 1;           // Start -> sign
                    else if (state == 5) state = 6;      // After e -> sign
                    else return false;
                } else if (c == '.') {
                    // Decimal point
                    if (state <= 2) state = (state == 2) ? 8 : 3; // 0,1 -> 3 (no int before), 2 -> 8 (int before)
                    else return false;
                } else if (c == 'e' || c == 'E') {
                    // Exponent
                    if (state == 2 || state == 4 || state == 8) state = 5; // Valid number before e
                    else return false;
                } else {
                    return false;
                }
            }

            // Valid end states:
            // 2: integer (e.g., "123")
            // 4: decimal with digits after point (e.g., "123.45", ".45")
            // 7: exponent with digits (e.g., "123e10", "123.45e10", ".45e10")
            // 8: decimal point after integer but no digits after (e.g., "123.", "3.")
            return state == 2 || state == 4 || state == 7 || state == 8;
        }

    // Alternative: Using Java's Double.parseDouble with validation
    public boolean isNumberParseDouble(String s) {
        s = s.trim();
        if (s.isEmpty()) return false;
        
        // Check for invalid patterns that parseDouble accepts but shouldn't
        if (s.equals("Infinity") || s.equals("-Infinity") || s.equals("+Infinity") ||
            s.equals("NaN") || s.equals("-NaN") || s.equals("+NaN")) {
            return false;
        }
        
        try {
            Double.parseDouble(s);
            // Additional check: must contain at least one digit
            boolean hasDigit = false;
            for (char c : s.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    hasDigit = true;
                    break;
                }
            }
            return hasDigit;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Valid numbers
        String[] valid = {
            "0", "0.1", "2e10", "-90e3", "3.", ".5", "+3.14", 
                    "2e0", "-.5", "+.8", "46.e3", "005047e+6",
                    " 0.1 ", "2e10", "-90E3", "3.", ".5", "+3.14", "1.", ".1", "3.0"
        };

        // Invalid numbers
        String[] invalid = {
            "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", 
            "95a54e53", ".", ".e1", "e", ".e", " ", "",
                    "1..2", "1.2.3", "1e1e1", "1e+", "1e-",
            "+-5", "12e", "12e+5.4", "12e5.4", "12e5e4"
        };

        System.out.println("=== Valid Numbers ===");
        for (String test : valid) {
            boolean result = solver.isNumber(test);
            System.out.println("\"" + test + "\" -> " + result + (result ? " ✓" : " ✗ EXPECTED TRUE"));
        }

        System.out.println("\n=== Invalid Numbers ===");
        for (String test : invalid) {
            boolean result = solver.isNumber(test);
            System.out.println("\"" + test + "\" -> " + result + (!result ? " ✓" : " ✗ EXPECTED FALSE"));
        }

        // Edge cases
        System.out.println("\n=== Edge Cases ===");
        String[] edgeCases = {
            "0", " 0 ", "0 ", " 0", "-0", "+0", "0.0", ".0", "0.", 
            "1e0", "1E0", "1e+0", "1e-0", ".1", "3.", "3.0",
            "Infinity", "NaN", "inf", "nan"
        };
        for (String test : edgeCases) {
            boolean result = solver.isNumber(test);
            System.out.println("\"" + test + "\" -> " + result);
        }
    }
}