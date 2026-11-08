import java.util.Arrays;

public class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        
        // Start from the last digit
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        
        // If all digits were 9, we need a new array with leading 1
        int[] result = new int[n + 1];
        result[0] = 1;
        return result;
    }

    // Alternative implementation using carry
    public int[] plusOneCarry(int[] digits) {
        int n = digits.length;
        int carry = 1;
        
        for (int i = n - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            digits[i] = sum % 10;
            carry = sum / 10;
        }
        
        if (carry > 0) {
            int[] result = new int[n + 1];
            result[0] = carry;
            System.arraycopy(digits, 0, result, 1, n);
            return result;
        }
        
        return digits;
    }

    // Alternative: Convert to BigInteger (for very large numbers)
    public int[] plusOneBigInteger(int[] digits) {
        java.math.BigInteger num = java.math.BigInteger.ZERO;
        for (int d : digits) {
            num = num.multiply(java.math.BigInteger.TEN).add(java.math.BigInteger.valueOf(d));
        }
        num = num.add(java.math.BigInteger.ONE);
        
        String str = num.toString();
        int[] result = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            result[i] = str.charAt(i) - '0';
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {1, 2, 3},        // Expected: [1, 2, 4]
            {4, 3, 2, 1},     // Expected: [4, 3, 2, 2]
            {9},              // Expected: [1, 0]
            {9, 9, 9},        // Expected: [1, 0, 0, 0]
            {1, 9, 9},        // Expected: [2, 0, 0]
            {0},              // Expected: [1]
            {8, 9, 9, 9},     // Expected: [9, 0, 0, 0]
            {1, 2, 9},        // Expected: [1, 3, 0]
        };
        
        int[][] expected = {
            {1, 2, 4},
            {4, 3, 2, 2},
            {1, 0},
            {1, 0, 0, 0},
            {2, 0, 0},
            {1},
            {9, 0, 0, 0},
            {1, 3, 0}
        };
        
        System.out.println("=== Plus One Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i].clone();
            int[] result1 = solver.plusOne(input.clone());
            int[] result2 = solver.plusOneCarry(input.clone());
            int[] result3 = solver.plusOneBigInteger(input.clone());
            
            boolean match1 = Arrays.equals(result1, expected[i]);
            boolean match2 = Arrays.equals(result2, expected[i]);
            boolean match3 = Arrays.equals(result3, expected[i]);
            
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(testCases[i]));
            System.out.println("  Expected: " + Arrays.toString(expected[i]));
            System.out.println("  Method 1: " + Arrays.toString(result1) + (match1 ? " ✓" : " ✗"));
            System.out.println("  Method 2: " + Arrays.toString(result2) + (match2 ? " ✓" : " ✗"));
            System.out.println("  Method 3: " + Arrays.toString(result3) + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Edge cases
        System.out.println("=== Edge Cases ===");
        int[] single9 = {9};
        System.out.println("Single 9: " + Arrays.toString(solver.plusOne(single9)));
        
        int[] all9s = {9, 9, 9, 9, 9};
        System.out.println("All 9s: " + Arrays.toString(solver.plusOne(all9s)));
        
        int[] large = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        System.out.println("Large: " + Arrays.toString(solver.plusOne(large)));
    }
}