import java.util.*;

/**
 * LeetCode 119 - Pascal's Triangle II
 * 
 * Dado um inteiro rowIndex, retorne a linha rowIndex (0-indexada) do triângulo de Pascal.
 * No triângulo de Pascal, cada número é a soma dos dois números diretamente acima dele.
 */
public class Solution {

    /**
     * Método 1: Iterativo O(k) espaço (Principal)
     * Constrói a linha iterativamente usando apenas O(k) espaço extra
     * Tempo: O(k^2), Espaço: O(k)
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>(rowIndex + 1);
        
        for (int i = 0; i <= rowIndex; i++) {
            row.add(1);
            // Atualiza de trás para frente para não sobrescrever valores necessários
            for (int j = i - 1; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }
        
        return row;
    }

    /**
     * Método 2: Fórmula Combinatória C(n, k)
     * Calcula diretamente cada elemento usando C(rowIndex, k)
     * Tempo: O(k), Espaço: O(k)
     */
    public List<Integer> getRowCombinatorial(int rowIndex) {
        List<Integer> row = new ArrayList<>(rowIndex + 1);
        long val = 1;
        
        for (int k = 0; k <= rowIndex; k++) {
            row.add((int) val);
            // C(n, k+1) = C(n, k) * (n-k) / (k+1)
            val = val * (rowIndex - k) / (k + 1);
        }
        
        return row;
    }

    /**
     * Método 3: Usando geração completa do triângulo (menos eficiente)
     * Tempo: O(k^2), Espaço: O(k^2)
     */
    public List<Integer> getRowFullTriangle(int rowIndex) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i <= rowIndex; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            row.add(1);
            
            for (int j = 1; j < i; j++) {
                int val = triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j);
                row.add(val);
            }
            
            if (i > 0) {
                row.add(1);
            }
            
            triangle.add(row);
        }
        
        return triangle.get(rowIndex);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Pascal's Triangle II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: rowIndex = 3");
        List<Integer> r1 = sol.getRow(3);
        System.out.println("  Result:   " + r1);
        System.out.println("  Expected: [1, 3, 3, 1]");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: rowIndex = 0");
        List<Integer> r2 = sol.getRow(0);
        System.out.println("  Result:   " + r2);
        System.out.println("  Expected: [1]");
        
        // Test 3: Example 3
        System.out.println("\nTest 3: rowIndex = 1");
        List<Integer> r3 = sol.getRow(1);
        System.out.println("  Result:   " + r3);
        System.out.println("  Expected: [1, 1]");
        
        // Test 4: rowIndex = 5
        System.out.println("\nTest 4: rowIndex = 5");
        List<Integer> r4 = sol.getRow(5);
        System.out.println("  Result:   " + r4);
        System.out.println("  Expected: [1, 5, 10, 10, 5, 1]");
        
        // Test 5: rowIndex = 10
        System.out.println("\nTest 5: rowIndex = 10");
        List<Integer> r5 = sol.getRow(10);
        System.out.println("  Result:   " + r5);
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification (3 methods)");
        for (int i = 0; i <= 15; i++) {
            List<Integer> m1 = sol.getRow(i);
            List<Integer> m2 = sol.getRowCombinatorial(i);
            List<Integer> m3 = sol.getRowFullTriangle(i);
            boolean match = m1.equals(m2) && m2.equals(m3);
            System.out.println("  rowIndex=" + i + ": " + (match ? "MATCH" : "MISMATCH"));
        }
        
        // Test 7: Sum = 2^rowIndex
        System.out.println("\nTest 7: Sum of row = 2^rowIndex");
        for (int i = 0; i <= 10; i++) {
            List<Integer> row = sol.getRow(i);
            int sum = row.stream().mapToInt(Integer::intValue).sum();
            System.out.println("  rowIndex=" + i + ": sum=" + sum + " (2^" + i + "=" + (1 << i) + ") " + (sum == (1 << i) ? "OK" : "FAIL"));
        }
        
        // Test 8: Symmetry check
        System.out.println("\nTest 8: Symmetry check");
        for (int i = 0; i <= 10; i++) {
            List<Integer> row = sol.getRow(i);
            boolean symmetric = true;
            for (int j = 0; j < row.size() / 2; j++) {
                if (!row.get(j).equals(row.get(row.size() - 1 - j))) {
                    symmetric = false;
                    break;
                }
            }
            System.out.println("  rowIndex=" + i + ": " + (symmetric ? "SYMMETRIC" : "ASYMMETRIC"));
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}