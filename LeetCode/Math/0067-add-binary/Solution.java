public class Solution {
    public String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            
            result.append(sum % 2);
            carry = sum / 2;
        }
        
        return result.reverse().toString();
    }

    // Alternative: Using BigInteger
    public String addBinaryBigInteger(String a, String b) {
        java.math.BigInteger numA = new java.math.BigInteger(a, 2);
        java.math.BigInteger numB = new java.math.BigInteger(b, 2);
        java.math.BigInteger sum = numA.add(numB);
        return sum.toString(2);
    }

    // Alternative: Using built-in binary addition (for learning)
    public String addBinaryManual(String a, String b) {
        // Pad shorter string with leading zeros
        int maxLen = Math.max(a.length(), b.length());
        a = String.format("%" + maxLen + "s", a).replace(' ', '0');
        b = String.format("%" + maxLen + "s", b).replace(' ', '0');
        
        StringBuilder result = new StringBuilder();
        int carry = 0;
        
        for (int i = maxLen - 1; i >= 0; i--) {
            int bitA = a.charAt(i) - '0';
            int bitB = b.charAt(i) - '0';
            int sum = bitA + bitB + carry;
            
            result.append(sum % 2);
            carry = sum / 2;
        }
        
        if (carry > 0) {
            result.append(carry);
        }
        
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        String[][] testCases = {
            {"11", "1", "100"},
            {"1010", "1011", "10101"},
            {"0", "0", "0"},
            {"1", "1", "10"},
            {"1111", "1111", "11110"},
            {"101010", "101010", "1010100"},
            {"1", "111", "1000"},
            {"111", "1", "1000"},
        };
        
        System.out.println("=== Add Binary Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            String a = testCases[i][0];
            String b = testCases[i][1];
            String expected = testCases[i][2];
            
            String result1 = solver.addBinary(a, b);
            String result2 = solver.addBinaryBigInteger(a, b);
            String result3 = solver.addBinaryManual(a, b);
            
            boolean match1 = result1.equals(expected);
            boolean match2 = result2.equals(expected);
            boolean match3 = result3.equals(expected);
            
            System.out.println("Test " + (i + 1) + ": " + a + " + " + b);
            System.out.println("  Expected: " + expected);
            System.out.println("  Method 1: " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  Method 2: " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Method 3: " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Edge cases
        System.out.println("=== Edge Cases ===");
        System.out.println("Empty strings: " + solver.addBinary("", ""));
        System.out.println("Large: " + solver.addBinary("1".repeat(100), "1".repeat(100)));
    }
}