public class Solution {
    
    // Método principal: DP Bottom-Up (Catalan Numbers)
    public int numTrees(int n) {
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - 1 - j];
            }
        }
        
        return (int) dp[n];
    }
    
    // Método 2: Fórmula direta do número de Catalan
    // C_n = (2n)! / ((n+1)! * n!) = C(2n, n) / (n+1)
    public int numTreesCatalanFormula(int n) {
        long result = 1;
        for (int i = 0; i < n; i++) {
            result = result * (2 * n - i) / (i + 1);
        }
        return (int) (result / (n + 1));
    }
    
    // Método 3: Recursão com Memoização
    public int numTreesMemo(int n) {
        Integer[] memo = new Integer[n + 1];
        return catalanMemo(n, memo);
    }
    
    private int catalanMemo(int n, Integer[] memo) {
        if (n <= 1) return 1;
        if (memo[n] != null) return memo[n];
        
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (long) catalanMemo(i, memo) * catalanMemo(n - 1 - i, memo);
        }
        
        memo[n] = (int) sum;
        return memo[n];
    }
    
    // Método 4: Recursão pura (exponencial, apenas para n pequeno)
    public int numTreesRecursive(int n) {
        if (n <= 1) return 1;
        
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += numTreesRecursive(i) * numTreesRecursive(n - 1 - i);
        }
        return sum;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Unique Binary Search Trees Tests ===\n");
        
        // Test 1: n = 3
        test(sol, 3, 5, "Test 1: n=3");
        
        // Test 2: n = 1
        test(sol, 1, 1, "Test 2: n=1");
        
        // Test 3: n = 2
        test(sol, 2, 2, "Test 3: n=2");
        
        // Test 4: n = 4
        test(sol, 4, 14, "Test 4: n=4");
        
        // Test 5: n = 5
        test(sol, 5, 42, "Test 5: n=5");
        
        // Test 6: n = 6
        test(sol, 6, 132, "Test 6: n=6");
        
        // Test 7: n = 7
        test(sol, 7, 429, "Test 7: n=7");
        
        // Test 8: n = 8
        test(sol, 8, 1430, "Test 8: n=8");
        
        // Test 9: n = 19 (max constraint)
        test(sol, 19, 1767263190, "Test 9: n=19");
    }
    
    private static void test(Solution sol, int n, int expected, String name) {
        int result1 = sol.numTrees(n);
        int result2 = sol.numTreesCatalanFormula(n);
        int result3 = sol.numTreesMemo(n);
        int result4 = (n <= 10) ? sol.numTreesRecursive(n) : result1;
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  n = " + n + ", Expected: " + expected);
        System.out.println("  DP:       " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Formula:  " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Memo:     " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Recursive:" + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}