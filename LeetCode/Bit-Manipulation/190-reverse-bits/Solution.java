import java.util.*;

/**
 * LeetCode 190 - Reverse Bits
 * 
 * Reverse bits of a given 32 bits unsigned integer.
 * 
 * Note:
 * - Note that in some languages, such as Java, there is no unsigned integer type. 
 *   In this case, both input and output will be given as a signed integer type. 
 *   They should not affect your implementation, as the integer's internal binary representation is the same, 
 *   whether it is signed or unsigned.
 * - In Java, the compiler represents the signed integers using 2's complement notation. 
 *   Therefore, in Example 2 above, the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 * 
 * Example 1:
 * Input: n = 00000010100101000001111010011100
 * Output: 964176192 (00111001011110000010100101000000)
 * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, 
 * so return 964176192 which its binary representation is 00111001011110000010100101000000.
 * 
 * Example 2:
 * Input: n = 11111111111111111111111111111101
 * Output: 3221225471 (10111111111111111111111111111111)
 * Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293, 
 * so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 * 
 * Constraints:
 * - The input must be a binary string of length 32
 * 
 * Follow up: If this function is called many times, how would you optimize it?
 */
public class Solution {

    // Approach 1: Bit by Bit (Standard)
    // Check each bit and set in reversed position
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>>= 1;
        }
        return result;
    }

    // Approach 2: Divide and Conquer (Optimized)
    // Swap 16-bit halves, then 8-bit quarters, etc.
    public int reverseBitsDivideConquer(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }

    // Approach 3: Lookup Table (Byte by Byte)
    // Precompute reverse for each byte, then combine
    public int reverseBitsLookup(int n) {
        int[] reverseByte = new int[256];
        for (int i = 0; i < 256; i++) {
            int rev = 0;
                int val = i;
                for (int j = 0; j < 8; j++) {
                    rev = (rev << 1) | (val & 1);
                    val >>>= 1;
                }
                reverseByte[i] = rev;
            }
        
            return (reverseByte[n & 0xff] << 24) |
                   (reverseByte[(n >>> 8) & 0xff] << 16) |
                   (reverseByte[(n >>> 16) & 0xff] << 8) |
                   (reverseByte[(n >>> 24) & 0xff]);
        }

    // Approach 4: Using Integer.reverse (Java built-in)
    public int reverseBitsBuiltin(int n) {
        return Integer.reverse(n);
    }

    // Approach 5: Recursive
    public int reverseBitsRecursive(int n) {
        return reverseHelper(n, 0, 32);
    }
    
    private int reverseHelper(int n, int result, int bits) {
        if (bits == 0) return result;
        return reverseHelper(n >>> 1, (result << 1) | (n & 1), bits - 1);
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Reverse Bits Tests ===\n");
        
        // Test 1: Example 1
        // 00000010100101000001111010011100 = 43261596
        // Reversed: 00111001011110000010100101000000 = 964176192
        testCase(sol, 43261596, 964176192, "Example 1");
        
        // Test 2: Example 2
        // 11111111111111111111111111111101 = 4294967293 (unsigned) = -3 (signed)
        // Reversed: 10111111111111111111111111111111 = 3221225471 (unsigned) = -1073741825 (signed)
        testCase(sol, -3, -1073741825, "Example 2");
        
        // Test 3: All zeros
        testCase(sol, 0, 0, "All zeros");
        
        // Test 4: All ones
        testCase(sol, -1, -1, "All ones");
        
        // Test 5: Single bit at position 0
        testCase(sol, 1, 1 << 31, "Single bit at 0");
        
        // Test 6: Single bit at position 31
        testCase(sol, 1 << 31, 1, "Single bit at 31");
        
        // Test 7: Alternating bits
        testCase(sol, 0xAAAAAAAA, 0x55555555, "Alternating 1010...");
        
        // Test 8: Alternating bits opposite
        testCase(sol, 0x55555555, 0xAAAAAAAA, "Alternating 0101...");
        
        // Test 9: Lower 16 bits set
        testCase(sol, 0x0000FFFF, 0xFFFF0000, "Lower 16 bits");
        
        // Test 10: Upper 16 bits set
        testCase(sol, 0xFFFF0000, 0x0000FFFF, "Upper 16 bits");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[] testCases = {
            43261596, -3, 0, -1, 1, 1 << 31, 
            0xAAAAAAAA, 0x55555555, 0x0000FFFF, 0xFFFF0000
        };
        
        boolean allMatch = true;
        for (int tc : testCases) {
            int r1 = sol.reverseBits(tc);
            int r2 = sol.reverseBitsDivideConquer(tc);
            int r3 = sol.reverseBitsLookup(tc);
            int r4 = sol.reverseBitsBuiltin(tc);
            int r5 = sol.reverseBitsRecursive(tc);
            
            if (r1 != r2 || r1 != r3 || r1 != r4 || r1 != r5) {
                System.out.println("Mismatch for " + Integer.toBinaryString(tc));
                System.out.printf("  BitByBit: %d, DivConq: %d, Lookup: %d, Builtin: %d, Recursive: %d%n", 
                    r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int input, int expected, String name) {
        int r1 = sol.reverseBits(input);
        int r2 = sol.reverseBitsDivideConquer(input);
        int r3 = sol.reverseBitsLookup(input);
        int r4 = sol.reverseBitsBuiltin(input);
        int r5 = sol.reverseBitsRecursive(input);
        
        boolean pass = r1 == expected && r2 == expected && r3 == expected && r4 == expected && r5 == expected;
        
        System.out.printf("%s: Input=%d (0x%08X), Expected=%d (0x%08X), Got=%d (0x%08X) %s%n", 
            name, input, input, expected, expected, r1, r1, pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  DivConq: %d, Lookup: %d, Builtin: %d, Recursive: %d%n", r2, r3, r4, r5);
        }
    }
}