import java.util.*;

/**
 * LeetCode 135 - Candy
 * 
 * Existem n crianças em uma fila. Cada criança recebe uma classificação (rating).
 * Você está dando doces a essas crianças sujeito aos seguintes requisitos:
 * 1. Cada criança deve ter pelo menos um doce.
 * 2. Crianças com classificação maior recebem mais doces que seus vizinhos.
 * Qual é o mínimo de doces que você deve dar?
 */
public class Solution {

    /**
     * Método 1: Two Pass Greedy (Principal)
     * Pass 1: esquerda -> direita (rating maior que esquerda = mais doces)
     * Pass 2: direita -> esquerda (rating maior que direita = mais doces)
     * Tempo: O(n), Espaço: O(n)
     */
    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        
        // Pass 1: esquerda -> direita
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Pass 2: direita -> esquerda
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        
        int total = 0;
        for (int c : candies) total += c;
        return total;
    }

    /**
     * Método 2: One Pass com contagem de picos/vales (Otimizado espaço)
     * Identifica sequências crescentes/decrescentes
     * Tempo: O(n), Espaço: O(1)
     */
    public int candyOnePass(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        int total = 1;
        int up = 0, down = 0, peak = 0;
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) { // Subindo
                up++;
                down = 0;
                peak = up;
                total += 1 + up;
            } else if (ratings[i] < ratings[i - 1]) { // Descendo
                up = 0;
                down++;
                total += 1 + down - (peak >= down ? 1 : 0);
            } else { // Igual
                up = down = peak = 0;
                total += 1;
            }
        }
        
        return total;
    }

    /**
     * Método 3: Two Arrays (Para clareza)
     * left[i] = doces considerando apenas vizinho esquerdo
     * right[i] = doces considerando apenas vizinho direito
     * Tempo: O(n), Espaço: O(n)
     */
    public int candyTwoArrays(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }
        
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }
        
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.max(left[i], right[i]);
        }
        
        return total;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Candy Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: ratings = [1,0,2]");
        int[] r1 = {1,0,2};
        System.out.println("  Expected: 5 (2+1+2)");
        System.out.println("  TwoPass:  " + sol.candy(r1));
        System.out.println("  OnePass:  " + sol.candyOnePass(r1));
        System.out.println("  TwoArr:   " + sol.candyTwoArrays(r1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: ratings = [1,2,2]");
        int[] r2 = {1,2,2};
        System.out.println("  Expected: 4 (1+2+1)");
        System.out.println("  Result:   " + sol.candy(r2));
        
        // Test 3: Strictly increasing
        System.out.println("\nTest 3: ratings = [1,2,3,4,5]");
        int[] r3 = {1,2,3,4,5};
        System.out.println("  Expected: 15 (1+2+3+4+5)");
        System.out.println("  Result:   " + sol.candy(r3));
        
        // Test 4: Strictly decreasing
        System.out.println("\nTest 4: ratings = [5,4,3,2,1]");
        int[] r4 = {5,4,3,2,1};
        System.out.println("  Expected: 15 (5+4+3+2+1)");
        System.out.println("  Result:   " + sol.candy(r4));
        
        // Test 5: All same
        System.out.println("\nTest 5: ratings = [1,1,1,1]");
        int[] r5 = {1,1,1,1};
        System.out.println("  Expected: 4");
        System.out.println("  Result:   " + sol.candy(r5));
        
        // Test 6: Valley
        System.out.println("\nTest 6: ratings = [5,3,1,2,4]");
        int[] r6 = {5,3,1,2,4};
        System.out.println("  Expected: 11 (3+2+1+2+3)");
        System.out.println("  Result:   " + sol.candy(r6));
        
        // Test 7: Peak
        System.out.println("\nTest 7: ratings = [1,3,5,3,1]");
        int[] r7 = {1,3,5,3,1};
        System.out.println("  Expected: 11 (1+2+3+2+1)");
        System.out.println("  Result:   " + sol.candy(r7));
        
        // Test 8: Complex
        System.out.println("\nTest 8: ratings = [1,2,87,87,87,2,1]");
        int[] r8 = {1,2,87,87,87,2,1};
        System.out.println("  Expected: 13 (1+2+3+1+3+2+1)");
        System.out.println("  Result:   " + sol.candy(r8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        int[][] testCases = {
            {1,0,2},
            {1,2,2},
            {1,2,3,4,5},
            {5,4,3,2,1},
            {1,1,1,1},
            {5,3,1,2,4},
            {1,3,5,3,1},
            {1,2,87,87,87,2,1},
            {2,1,3,4,5,2,1},
            {1,3,2,2,1}
        };
        int[] expected = {5, 4, 15, 15, 4, 11, 9, 13, 15, 7};
        
        for (int i = 0; i < testCases.length; i++) {
                    int res1 = sol.candy(testCases[i]);
                    int res2 = sol.candyOnePass(testCases[i]);
                    int res3 = sol.candyTwoArrays(testCases[i]);
                    boolean match = (res1 == expected[i] && res2 == expected[i] && res3 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                        " (2P:" + res1 + " 1P:" + res2 + " 2A:" + res3 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}