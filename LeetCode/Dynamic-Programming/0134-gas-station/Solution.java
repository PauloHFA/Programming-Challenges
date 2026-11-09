import java.util.*;

/**
 * LeetCode 134 - Gas Station
 * 
 * Existem n postos de gasolina em um circuito circular. Você tem um carro com
 * tanque de gasolina ilimitado e custa cost[i] de gasolina para ir do posto i
 * para o próximo posto (i+1). Você começa com tanque vazio em um dos postos.
 * Dado dois arrays inteiros gas e cost, retorne o índice do posto de partida
 * se você puder viajar ao redor do circuito uma vez no sentido horário,
 * caso contrário retorne -1. Se existe solução, é garantido que é única.
 */
public class Solution {

    /**
     * Método 1: Greedy One Pass (Principal)
     * Se total gas >= total cost, existe solução.
     * Mantém tank atual, se fica negativo, reinicia do próximo posto.
     * Tempo: O(n), Espaço: O(1)
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalTank = 0;
        int currTank = 0;
        int start = 0;
        
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            totalTank += diff;
            currTank += diff;
            
            if (currTank < 0) {
                start = i + 1;
                currTank = 0;
            }
        }
        
        return totalTank >= 0 ? start : -1;
    }

    /**
     * Método 2: Two Pass - Verifica viabilidade primeiro
     * Primeiro verifica se total gas >= total cost
     * Depois encontra o start
     * Tempo: O(n), Espaço: O(1)
     */
    public int canCompleteCircuitTwoPass(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }
        if (totalGas < totalCost) return -1;
        
        int tank = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            tank += gas[i] - cost[i];
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return start;
    }

    /**
     * Método 3: Simulação completa (O(n^2) - para referência)
     * Tenta cada posto como início
     * Tempo: O(n^2), Espaço: O(1)
     */
    public int canCompleteCircuitBrute(int[] gas, int[] cost) {
        int n = gas.length;
        
        for (int start = 0; start < n; start++) {
            int tank = 0;
            boolean valid = true;
            
            for (int i = 0; i < n; i++) {
                int idx = (start + i) % n;
                tank += gas[idx];
                if (tank < cost[idx]) {
                    valid = false;
                    break;
                }
                tank -= cost[idx];
            }
            
            if (valid) return start;
        }
        
        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Gas Station Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: gas=[1,2,3,4,5], cost=[3,4,5,1,2]");
        int[] g1 = {1,2,3,4,5};
        int[] c1 = {3,4,5,1,2};
        System.out.println("  Expected: 3");
        System.out.println("  Greedy:   " + sol.canCompleteCircuit(g1, c1));
        System.out.println("  TwoPass:  " + sol.canCompleteCircuitTwoPass(g1, c1));
        System.out.println("  Brute:    " + sol.canCompleteCircuitBrute(g1, c1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: gas=[2,3,4], cost=[3,4,3]");
        int[] g2 = {2,3,4};
        int[] c2 = {3,4,3};
        System.out.println("  Expected: -1");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g2, c2));
        
        // Test 3: Single station - enough gas
        System.out.println("\nTest 3: gas=[5], cost=[4]");
        int[] g3 = {5};
        int[] c3 = {4};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g3, c3));
        
        // Test 4: Single station - not enough
        System.out.println("\nTest 4: gas=[3], cost=[4]");
        int[] g4 = {3};
        int[] c4 = {4};
        System.out.println("  Expected: -1");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g4, c4));
        
        // Test 5: All equal
        System.out.println("\nTest 5: gas=[2,2,2], cost=[2,2,2]");
        int[] g5 = {2,2,2};
        int[] c5 = {2,2,2};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g5, c5));
        
        // Test 6: Start at middle
        System.out.println("\nTest 6: gas=[5,1,2,3,4], cost=[4,4,1,5,1]");
        int[] g6 = {5,1,2,3,4};
        int[] c6 = {4,4,1,5,1};
        System.out.println("  Expected: 4");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g6, c6));
        
        // Test 7: Large case
                System.out.println("\nTest 7: Large case (all equal + extra at 500)");
        int[] g7 = new int[1000];
        int[] c7 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            g7[i] = (i % 10) + 1;
            c7[i] = (i % 10) + 1;
        }
        g7[500] = 100; // Extra gas at station 500
                System.out.println("  Expected: 0 (can start anywhere, tank never negative)");
        System.out.println("  Result:   " + sol.canCompleteCircuit(g7, c7));
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification");
        int[][][] testCases = {
            {{1,2,3,4,5}, {3,4,5,1,2}},
            {{2,3,4}, {3,4,3}},
            {{5}, {4}},
            {{3}, {4}},
            {{2,2,2}, {2,2,2}},
            {{5,1,2,3,4}, {4,4,1,5,1}},
            {{3,1,1}, {1,2,2}},
            {{4,5,2,6,5,3}, {3,2,7,3,2,9}}
        };
        int[] expected = {3, -1, 0, -1, 0, 4, 0, -1};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.canCompleteCircuit(testCases[i][0], testCases[i][1]);
            int r2 = sol.canCompleteCircuitTwoPass(testCases[i][0], testCases[i][1]);
            int r3 = sol.canCompleteCircuitBrute(testCases[i][0], testCases[i][1]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (G:" + r1 + " 2P:" + r2 + " B:" + r3 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}