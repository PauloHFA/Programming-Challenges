public class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;
        
        // Binary search approach
        int left = 1, right = x / 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            if (square == x) {
                return mid;
            } else if (square < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return right;
    }

    // Alternative: Newton's method
    public int mySqrtNewton(int x) {
        if (x < 2) return x;
        
        long r = x;
        while (r * r > x) {
            r = (r + x / r) / 2;
        }
        return (int) r;
    }

    // Alternative: Using Math.sqrt (for reference)
    public int mySqrtMath(int x) {
        return (int) Math.sqrt(x);
    }

    // Alternative: Bit manipulation (for integers) - corrected
    public int mySqrtBit(int x) {
        if (x < 2) return x;
        
        int result = 0;
            // Start with the highest power of 4 <= x
            int bit = 1 << 30; // 2^30 = 1073741824
        
        while (bit > x) {
            bit >>= 2;
        }
        
        while (bit != 0) {
            if (x >= result + bit) {
                x -= result + bit;
                result = (result >> 1) + bit;
            } else {
                result >>= 1;
            }
            bit >>= 2;
        }
        
        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[] testCases = {0, 1, 2, 3, 4, 8, 9, 15, 16, 17, 24, 25, 26, 100, 101, 2147395599, 2147483647};
        int[] expected = {0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 10, 10, 46339, 46340};
        
        System.out.println("=== Sqrt(x) Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int x = testCases[i];
            int exp = expected[i];
            
            int result1 = solver.mySqrt(x);
            int result2 = solver.mySqrtNewton(x);
            int result3 = solver.mySqrtMath(x);
            int result4 = solver.mySqrtBit(x);
            
            boolean match1 = result1 == exp;
            boolean match2 = result2 == exp;
            boolean match3 = result3 == exp;
            boolean match4 = result4 == exp;
            
            System.out.println("Test " + (i + 1) + ": x = " + x);
            System.out.println("  Expected: " + exp);
            System.out.println("  Binary:   " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  Newton:   " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Math:     " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  Bit:      " + result4 + (match4 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 && match4 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Performance test
        System.out.println("=== Performance Test (100000 iterations) ===");
        long start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            solver.mySqrt(2147483647);
        }
        long binaryTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            solver.mySqrtNewton(2147483647);
        }
        long newtonTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            solver.mySqrtMath(2147483647);
        }
        long mathTime = System.nanoTime() - start;
        
        System.out.println("Binary Search: " + (binaryTime / 1_000_000) + " ms");
        System.out.println("Newton's Method: " + (newtonTime / 1_000_000) + " ms");
        System.out.println("Math.sqrt: " + (mathTime / 1_000_000) + " ms");
    }
}