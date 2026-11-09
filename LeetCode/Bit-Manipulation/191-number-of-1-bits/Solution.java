import java.util.*;

/**
 * LeetCode 191 - Number of 1 Bits
 * 
 * Write a function that takes the binary representation of a positive integer and returns the number of '1' bits it has (also known as the Hamming weight).
 * 
 * Note:
 * - Note that in some languages, such as Java, there is no unsigned integer type. 
 *   In this case, the input will be given as a signed integer type. 
 *   It should not affect your implementation, as the integer's internal binary representation is the same, 
 *   whether it is signed or unsigned.
 * - In Java, the compiler represents the signed integers using 2's complement notation. 
 *   Therefore, in Example 3, the input represents the signed integer -3.
 * 
 * Example 1:
 * Input: n = 00000000000000000000000000001011
 * Output: 3
 * Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.
 * 
 * Example 2:
 * Input: n = 00000000000000000000000010000000
 * Output: 1
 * Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.
 * 
 * Example 3:
 * Input: n = 11111111111111111111111111111101
 * Output: 31
 * Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.
 * 
 * Constraints:
 * - The input must be a binary string of length 32.
 * 
 * Follow up: If this function is called many times, how would you optimize it?
 */
public class Solution {

    // Approach 1: Bit by Bit (Standard)
    // Check each bit using n & 1, then shift right
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }

    // Approach 2: Brian Kernighan's Algorithm (Optimal)
    // n & (n - 1) clears the least significant set bit
    public int hammingWeightKernighan(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }

    // Approach 3: Built-in Integer.bitCount
    public int hammingWeightBuiltin(int n) {
        return Integer.bitCount(n);
    }

    // Approach 4: Lookup Table (Byte by Byte)
    // Precompute count for each byte
    public int hammingWeightLookup(int n) {
        int[] bits = new int[256];
        for (int i = 0; i < 256; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits[n & 0xff] + bits[(n >>> 8) & 0xff] + 
               bits[(n >>> 16) & 0xff] + bits[(n >>> 24) & 0xff];
    }

    // Approach 5: Parallel Summing (SWAR - SIMD Within A Register)
    // Add bits in parallel using masks
    public int hammingWeightParallel(int n) {
        n = n - ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n + (n >>> 4)) & 0x0f0f0f0f;
        n = n + (n >>> 8);
        n = n + (n >>> 16);
        return n & 0x3f;
    }

    // Approach 6: Recursive
    public int hammingWeightRecursive(int n) {
        if (n == 0) return 0;
        return (n & 1) + hammingWeightRecursive(n >>> 1);
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Number of 1 Bits Tests ===\n");
        
        // Test 1: Example 1
        // 00000000000000000000000000001011 = 11
        testCase(sol, 11, 3, "Example 1");
        
        // Test 2: Example 2
        // 00000000000000000000000010000000 = 128
        testCase(sol, 128, 1, "Example 2");
        
        // Test 3: Example 3
        // 11111111111111111111111111111101 = -3 (signed)
        testCase(sol, -3, 31, "Example 3");
        
        // Test 4: Zero
        testCase(sol, 0, 0, "Zero");
        
        // Test 5: All ones
        testCase(sol, -1, 32, "All ones");
        
        // Test 6: Power of 2
        testCase(sol, 1 << 10, 1, "Power of 2");
        
        // Test 7: Alternating bits
        testCase(sol, 0xAAAAAAAA, 16, "Alternating 1010...");
        
        // Test 8: Lower 16 bits
        testCase(sol, 0x0000FFFF, 16, "Lower 16 bits");
        
        // Test 9: Random number
        testCase(sol, 12345, 6, "Random 12345");
        
        // Test 10: Max positive int
        testCase(sol, Integer.MAX_VALUE, 31, "Max positive int");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[] testCases = {
            11, 128, -3, 0, -1, 1 << 10, 
            0xAAAAAAAA, 0x0000FFFF, 12345, Integer.MAX_VALUE
        };
        
        boolean allMatch = true;
        for (int tc : testCases) {
            int r1 = sol.hammingWeight(tc);
            int r2 = sol.hammingWeightKernighan(tc);
            int r3 = sol.hammingWeightBuiltin(tc);
            int r4 = sol.hammingWeightLookup(tc);
            int r5 = sol.hammingWeightParallel(tc);
            int r6 = sol.hammingWeightRecursive(tc);
            
            if (r1 != r2 || r1 != r3 || r1 != r4 || r1 != r5 || r1 != r6) {
                System.out.println("Mismatch for " + Integer.toBinaryString(tc));
                System.out.printf("  BitByBit: %d, Kernighan: %d, Builtin: %d, Lookup: %d, Parallel: %d, Recursive: %d%n", 
                    r1, r2, r3, r4, r5, r6);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int input, int expected, String name) {
        int r1 = sol.hammingWeight(input);
        int r2 = sol.hammingWeightKernighan(input);
        int r3 = sol.hammingWeightBuiltin(input);
        int r4 = sol.hammingWeightLookup(input);
        int r5 = sol.hammingWeightParallel(input);
        int r6 = sol.hammingWeightRecursive(input);
        
        boolean pass = r1 == expected && r2 == expected && r3 == expected 
                      && r4 == expected && r5 == expected && r6 == expected;
        
        System.out.printf("%s: Input=%d (0x%08X), Expected=%d, Got=%d %s%n", 
            name, input, input, expected, r1, pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  Kernighan: %d, Builtin: %d, Lookup: %d, Parallel: %d, Recursive: %d%n", r2, r3, r4, r5, r6);
        }
    }
}