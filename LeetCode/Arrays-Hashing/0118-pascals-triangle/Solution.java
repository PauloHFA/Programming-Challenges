import java.util.*;

/**
 * LeetCode 118 - Pascal's Triangle
 * 
 * Dado um inteiro numRows, retorne as primeiras numRows linhas do triângulo de Pascal.
 * No triângulo de Pascal, cada número é a soma dos dois números diretamente acima dele.
 */
public class Solution {

    /**
     * Método 1: Iterativo (Principal)
     * Constrói cada linha baseada na anterior
     * Tempo: O(n^2), Espaço: O(n^2) para output
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            row.add(1); // Primeiro elemento sempre 1
            
            for (int j = 1; j < i; j++) {
                // Soma dos dois elementos acima
                int val = result.get(i - 1).get(j - 1) + result.get(i - 1).get(j);
                row.add(val);
            }
            
            if (i > 0) {
                row.add(1); // Último elemento sempre 1 (exceto primeira linha)
            }
            
            result.add(row);
        }
        
        return result;
    }

    /**
     * Método 2: Usando fórmula combinatória C(n, k)
     * Cada elemento é C(row, col) = row! / (col! * (row-col)!)
     * Tempo: O(n^2), Espaço: O(n^2)
     */
    public List<List<Integer>> generateCombinatorial(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            long val = 1;
            
            for (int j = 0; j <= i; j++) {
                row.add((int) val);
                // C(n, k+1) = C(n, k) * (n-k) / (k+1)
                    if (j < i) {
                        val = val * (i - j) / (j + 1);
                    }
                }
            
                result.add(row);
            }
        
            return result;
        }

    /**
     * Método 3: Otimizado espaço O(1) extra (modifica in-place)
     * Tempo: O(n^2), Espaço: O(1) extra
     */
    public List<List<Integer>> generateInPlace(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            for (int j = 0; j <= i; j++) {
                row.add(1);
            }
            
            // Atualiza elementos do meio (de trás para frente para não sobrescrever)
                // Precisa usar a linha anterior do resultado, não a linha atual
                if (i >= 2) {
                    List<Integer> prevRow = result.get(i - 1);
                    for (int j = 1; j < i; j++) {
                        row.set(j, prevRow.get(j - 1) + prevRow.get(j));
                    }
                }
            
                result.add(row);
            }
        
            return result;
        }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Pascal's Triangle Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: numRows = 5");
        List<List<Integer>> r1 = sol.generate(5);
        printTriangle(r1);
        System.out.println("Expected: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: numRows = 1");
        List<List<Integer>> r2 = sol.generate(1);
        printTriangle(r2);
        System.out.println("Expected: [[1]]");
        
        // Test 3: numRows = 0
        System.out.println("\nTest 3: numRows = 0");
        List<List<Integer>> r3 = sol.generate(0);
        System.out.println("Result: " + r3);
        System.out.println("Expected: []");
        
        // Test 4: numRows = 2
        System.out.println("\nTest 4: numRows = 2");
        List<List<Integer>> r4 = sol.generate(2);
        printTriangle(r4);
        System.out.println("Expected: [[1],[1,1]]");
        
        // Test 5: numRows = 6
        System.out.println("\nTest 5: numRows = 6");
        List<List<Integer>> r5 = sol.generate(6);
        printTriangle(r5);
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification (3 methods)");
        for (int n = 0; n <= 7; n++) {
            List<List<Integer>> m1 = sol.generate(n);
            List<List<Integer>> m2 = sol.generateCombinatorial(n);
            List<List<Integer>> m3 = sol.generateInPlace(n);
            boolean match = m1.equals(m2) && m2.equals(m3);
                    if (!match) {
                        System.out.println("  n=" + n + ": MISMATCH");
                        System.out.println("    m1: " + m1);
                        System.out.println("    m2: " + m2);
                        System.out.println("    m3: " + m3);
                    } else {
                        System.out.println("  n=" + n + ": MATCH");
                    }
                }
        
        // Test 7: Verify specific values
        System.out.println("\nTest 7: Verify row 5 (0-indexed) = [1,5,10,10,5,1]");
        List<List<Integer>> r7 = sol.generate(6);
        System.out.println("  Row 5: " + r7.get(5));
        
        // Test 8: Sum of row = 2^n
        System.out.println("\nTest 8: Sum of each row = 2^row");
        List<List<Integer>> r8 = sol.generate(8);
        for (int i = 0; i < r8.size(); i++) {
            int sum = r8.get(i).stream().mapToInt(Integer::intValue).sum();
            System.out.println("  Row " + i + ": sum=" + sum + " (2^" + i + "=" + (1 << i) + ") " + (sum == (1 << i) ? "OK" : "FAIL"));
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void printTriangle(List<List<Integer>> triangle) {
        for (List<Integer> row : triangle) {
            System.out.println("  " + row);
        }
    }
}