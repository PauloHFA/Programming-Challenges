/**
 * LeetCode 166 - Fraction to Recurring Decimal
 * 
 * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
 * If the fractional part is repeating, enclose the repeating part in parentheses.
 * 
 * Example 1:
 * Input: numerator = 1, denominator = 2
 * Output: "0.5"
 * 
 * Example 2:
 * Input: numerator = 2, denominator = 1
 * Output: "2"
 * 
 * Example 3:
 * Input: numerator = 4, denominator = 333
 * Output: "0.(012)"
 * 
 * Constraints:
 * -2^31 <= numerator, denominator <= 2^31 - 1
 * denominator != 0
 */
public class Solution {
    
    /**
     * Approach 1: Long Division with HashMap - O(denominator) time, O(denominator) space
     * 
     * Simulate long division. Track remainders and their positions in the result.
     * When a remainder repeats, we found a cycle.
     * 
     * Time Complexity: O(denominator) - at most denominator different remainders
     * Space Complexity: O(denominator) - for remainder map
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        
        // Handle sign
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        // Use long to avoid overflow
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        // Integer part
        result.append(num / den);
        long remainder = num % den;
        
        if (remainder == 0) {
            return result.toString();
        }
        
        // Fractional part
        result.append(".");
        
        // Map remainder to its position in result
        java.util.Map<Long, Integer> remainderPos = new java.util.HashMap<>();
        
        while (remainder != 0) {
            if (remainderPos.containsKey(remainder)) {
                // Repeating decimal found
                int pos = remainderPos.get(remainder);
                result.insert(pos, "(");
                result.append(")");
                break;
            }
            
            remainderPos.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Using Array for Remainder Tracking - O(denominator) time, O(denominator) space
     * 
     * Use array instead of HashMap for faster access when denominator is small.
     * 
     * Time Complexity: O(denominator)
     * Space Complexity: O(denominator)
     */
    public String fractionToDecimalArray(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        result.append(num / den);
        long remainder = num % den;
        
        if (remainder == 0) {
            return result.toString();
        }
        
        result.append(".");
        
        // Use array for remainder positions (index = remainder, value = position)
        // Only works if denominator fits in array size
        if (den <= 100000) {
            int[] pos = new int[(int)den + 1];
            java.util.Arrays.fill(pos, -1);
            
            while (remainder != 0) {
                if (pos[(int)remainder] != -1) {
                    int repeatPos = pos[(int)remainder];
                    result.insert(repeatPos, "(");
                    result.append(")");
                    break;
                }
                
                pos[(int)remainder] = result.length();
                remainder *= 10;
                result.append(remainder / den);
                remainder %= den;
            }
        } else {
            // Fallback to HashMap for large denominators
            java.util.Map<Long, Integer> remainderPos = new java.util.HashMap<>();
            
            while (remainder != 0) {
                if (remainderPos.containsKey(remainder)) {
                    int repeatPos = remainderPos.get(remainder);
                    result.insert(repeatPos, "(");
                    result.append(")");
                    break;
                }
                
                remainderPos.put(remainder, result.length());
                remainder *= 10;
                result.append(remainder / den);
                remainder %= den;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Detect Cycle Length First - O(denominator) time, O(denominator) space
     * 
     * First detect if there's a cycle and its length, then construct result.
     * 
     * Time Complexity: O(denominator)
     * Space Complexity: O(denominator)
     */
    public String fractionToDecimalCycleDetect(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        long integerPart = num / den;
        long remainder = num % den;
        
        result.append(integerPart);
        
        if (remainder == 0) {
            return result.toString();
        }
        
        result.append(".");
        
        // Track remainders and their positions
        java.util.Map<Long, Integer> seen = new java.util.HashMap<>();
        StringBuilder fractional = new StringBuilder();
        
        while (remainder != 0 && !seen.containsKey(remainder)) {
            seen.put(remainder, fractional.length());
            remainder *= 10;
            fractional.append(remainder / den);
            remainder %= den;
        }
        
        if (remainder == 0) {
            result.append(fractional);
        } else {
            int repeatStart = seen.get(remainder);
            result.append(fractional.substring(0, repeatStart));
            result.append("(");
            result.append(fractional.substring(repeatStart));
            result.append(")");
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: BigInteger for Arbitrary Precision (verification) - O(n) time, O(n) space
     * 
     * Use BigInteger for verification with very large numbers.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public String fractionToDecimalBigInt(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        java.math.BigInteger num = java.math.BigInteger.valueOf(numerator);
        java.math.BigInteger den = java.math.BigInteger.valueOf(denominator);
        
        if (num.compareTo(java.math.BigInteger.ZERO) == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        
        if (num.signum() != den.signum()) {
            result.append("-");
            num = num.abs();
            den = den.abs();
        } else {
            num = num.abs();
            den = den.abs();
        }
        
        java.math.BigInteger[] divRem = num.divideAndRemainder(den);
        result.append(divRem[0]);
        
        if (divRem[1].compareTo(java.math.BigInteger.ZERO) == 0) {
            return result.toString();
        }
        
        result.append(".");
        
        java.util.Map<java.math.BigInteger, Integer> seen = new java.util.HashMap<>();
        StringBuilder fractional = new StringBuilder();
        java.math.BigInteger remainder = divRem[1];
        
        while (remainder.compareTo(java.math.BigInteger.ZERO) != 0 && !seen.containsKey(remainder)) {
            seen.put(remainder, fractional.length());
            remainder = remainder.multiply(java.math.BigInteger.TEN);
            java.math.BigInteger[] divRem2 = remainder.divideAndRemainder(den);
            fractional.append(divRem2[0]);
            remainder = divRem2[1];
        }
        
        if (remainder.compareTo(java.math.BigInteger.ZERO) == 0) {
            result.append(fractional);
        } else {
            int repeatStart = seen.get(remainder);
            result.append(fractional.substring(0, repeatStart));
            result.append("(");
            result.append(fractional.substring(repeatStart));
            result.append(")");
        }
        
        return result.toString();
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Fraction to Recurring Decimal Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: 1/2 -> Expected: \"0.5\"");
        testCase(sol, 1, 2, "0.5");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: 2/1 -> Expected: \"2\"");
        testCase(sol, 2, 1, "2");
        
        // Test 3: Example 3
        System.out.println("\nTest 3: 4/333 -> Expected: \"0.(012)\"");
        testCase(sol, 4, 333, "0.(012)");
        
        // Test 4: Negative numerator
        System.out.println("\nTest 4: -1/2 -> Expected: \"-0.5\"");
        testCase(sol, -1, 2, "-0.5");
        
        // Test 5: Negative denominator
        System.out.println("\nTest 5: 1/-2 -> Expected: \"-0.5\"");
        testCase(sol, 1, -2, "-0.5");
        
        // Test 6: Both negative
        System.out.println("\nTest 6: -1/-2 -> Expected: \"0.5\"");
        testCase(sol, -1, -2, "0.5");
        
        // Test 7: Repeating single digit
        System.out.println("\nTest 7: 1/3 -> Expected: \"0.(3)\"");
        testCase(sol, 1, 3, "0.(3)");
        
        // Test 8: Repeating multiple digits
        System.out.println("\nTest 8: 1/7 -> Expected: \"0.(142857)\"");
        testCase(sol, 1, 7, "0.(142857)");
        
        // Test 9: Non-repeating then repeating
        System.out.println("\nTest 9: 1/6 -> Expected: \"0.1(6)\"");
        testCase(sol, 1, 6, "0.1(6)");
        
        // Test 10: Integer result
        System.out.println("\nTest 10: 4/2 -> Expected: \"2\"");
        testCase(sol, 4, 2, "2");
        
        // Test 11: Large numbers
        System.out.println("\nTest 11: 2147483647/1 -> Expected: \"2147483647\"");
        testCase(sol, 2147483647, 1, "2147483647");
        
        // Test 12: Zero numerator
        System.out.println("\nTest 12: 0/5 -> Expected: \"0\"");
        testCase(sol, 0, 5, "0");
        
        // Test 13: 1/333
        System.out.println("\nTest 13: 1/333 -> Expected: \"0.(003)\"");
        testCase(sol, 1, 333, "0.(003)");
        
        // Test 14: 22/7
        System.out.println("\nTest 14: 22/7 -> Expected: \"3.(142857)\"");
        testCase(sol, 22, 7, "3.(142857)");
        
        // Test 15: Cross verification
        System.out.println("\nTest 15: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int num, int den, String expected) {
        String r1 = sol.fractionToDecimal(num, den);
        String r2 = sol.fractionToDecimalArray(num, den);
        String r3 = sol.fractionToDecimalCycleDetect(num, den);
        String r4 = sol.fractionToDecimalBigInt(num, den);
        
        boolean match = expected.equals(r1) && expected.equals(r2) && expected.equals(r3) && expected.equals(r4);
        
        System.out.println("  HashMap:     " + r1 + (expected.equals(r1) ? " ✓" : " ✗"));
        System.out.println("  Array:       " + r2 + (expected.equals(r2) ? " ✓" : " ✗"));
        System.out.println("  CycleDetect: " + r3 + (expected.equals(r3) ? " ✓" : " ✗"));
        System.out.println("  BigInteger:  " + r4 + (expected.equals(r4) ? " ✓" : " ✗"));
        
        assert match : "Mismatch for " + num + "/" + den + 
            ": expected " + expected + ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static void crossVerify(Solution sol) {
        int[][] testCases = {
            {1, 2}, {2, 1}, {4, 333}, {-1, 2}, {1, -2}, {-1, -2},
            {1, 3}, {1, 7}, {1, 6}, {4, 2}, {2147483647, 1}, {0, 5},
            {1, 333}, {22, 7}, {1, 17}, {1, 13}, {1, 11}, {1, 9},
            {5, 2}, {7, 12}, {10, 3}, {100, 3}, {1, 81}
        };
        String[] expected = {
            "0.5", "2", "0.(012)", "-0.5", "-0.5", "0.5",
            "0.(3)", "0.(142857)", "0.1(6)", "2", "2147483647", "0",
            "0.(003)", "3.(142857)", "0.(0588235294117647)", "0.(076923)", "0.(09)", "0.(1)",
            "2.5", "0.58(3)", "3.(3)", "33.(3)", "0.(012345679)"
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int num = testCases[i][0];
            int den = testCases[i][1];
            String exp = expected[i];
            
            String r1 = sol.fractionToDecimal(num, den);
            String r2 = sol.fractionToDecimalArray(num, den);
            String r3 = sol.fractionToDecimalCycleDetect(num, den);
            String r4 = sol.fractionToDecimalBigInt(num, den);
            
            boolean match = exp.equals(r1) && exp.equals(r2) && exp.equals(r3) && exp.equals(r4);
            System.out.println("  " + num + "/" + den + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + ")");
            assert match : "Cross-verification failed for " + num + "/" + den;
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}