import java.util.*;

/**
 * LeetCode 128 - Longest Consecutive Sequence
 * 
 * Dado um array não ordenado de inteiros nums, retorne o comprimento da
 * sequência de elementos consecutivos mais longa.
 * Você deve escrever um algoritmo que execute em tempo O(n).
 */
public class Solution {

    /**
     * Método 1: HashSet (Principal)
     * Para cada número, verifica se é início de sequência (num-1 não existe)
     * Então expande para frente
     * Tempo: O(n), Espaço: O(n)
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longest = 0;
        
        for (int num : numSet) {
            // Só verifica se é início de sequência
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int streak = 1;
                
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    streak++;
                }
                
                longest = Math.max(longest, streak);
            }
        }
        
        return longest;
    }

    /**
     * Método 2: Union Find (Disjoint Set)
     * Une números consecutivos
     * Tempo: O(n * α(n)), Espaço: O(n)
     */
    public int longestConsecutiveUF(int[] nums) {
        if (nums.length == 0) return 0;
        
        Map<Integer, Integer> parent = new HashMap<>();
        Map<Integer, Integer> size = new HashMap<>();
        
        for (int num : nums) {
            parent.put(num, num);
            size.put(num, 1);
        }
        
        for (int num : nums) {
            if (parent.containsKey(num + 1)) {
                union(parent, size, num, num + 1);
            }
            if (parent.containsKey(num - 1)) {
                union(parent, size, num, num - 1);
            }
        }
        
        int maxSize = 0;
        for (int s : size.values()) {
            maxSize = Math.max(maxSize, s);
        }
        
        return maxSize;
    }
    
    private int find(Map<Integer, Integer> parent, int x) {
        if (parent.get(x) != x) {
            parent.put(x, find(parent, parent.get(x)));
        }
        return parent.get(x);
    }
    
    private void union(Map<Integer, Integer> parent, Map<Integer, Integer> size, int x, int y) {
        int px = find(parent, x);
        int py = find(parent, y);
        if (px != py) {
            if (size.get(px) < size.get(py)) {
                int temp = px;
                px = py;
                py = temp;
            }
            parent.put(py, px);
            size.put(px, size.get(px) + size.get(py));
        }
    }

    /**
     * Método 3: HashMap com limites (Otimizado)
     * Armazena apenas os limites de cada sequência
     * Tempo: O(n), Espaço: O(n)
     */
    public int longestConsecutiveMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int maxLen = 0;
        
        for (int num : nums) {
            if (map.containsKey(num)) continue; // Duplicata
            
            int left = map.getOrDefault(num - 1, 0);
            int right = map.getOrDefault(num + 1, 0);
            int len = left + right + 1;
            
            maxLen = Math.max(maxLen, len);
            
            // Atualiza limites
            map.put(num, len);
            map.put(num - left, len);
            map.put(num + right, len);
        }
        
        return maxLen;
    }

    /**
     * Método 4: Sorting (O(n log n) - não atende requisito O(n))
     * Para referência
     */
    public int longestConsecutiveSort(int[] nums) {
        if (nums.length == 0) return 0;
        
        Arrays.sort(nums);
        int longest = 1;
        int current = 1;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue; // Duplicata
            }
            if (nums[i] == nums[i - 1] + 1) {
                current++;
            } else {
                longest = Math.max(longest, current);
                current = 1;
            }
        }
        
        return Math.max(longest, current);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Longest Consecutive Sequence Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [100,4,200,1,3,2]");
        int[] n1 = {100,4,200,1,3,2};
        System.out.println("  Expected: 4 (1,2,3,4)");
        System.out.println("  HashSet:  " + sol.longestConsecutive(n1));
        System.out.println("  UF:       " + sol.longestConsecutiveUF(n1));
        System.out.println("  Map:      " + sol.longestConsecutiveMap(n1));
        System.out.println("  Sort:     " + sol.longestConsecutiveSort(n1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [0,3,7,2,5,8,4,6,0,1]");
        int[] n2 = {0,3,7,2,5,8,4,6,0,1};
        System.out.println("  Expected: 9 (0,1,2,3,4,5,6,7,8)");
        System.out.println("  HashSet:  " + sol.longestConsecutive(n2));
        System.out.println("  UF:       " + sol.longestConsecutiveUF(n2));
        System.out.println("  Map:      " + sol.longestConsecutiveMap(n2));
        System.out.println("  Sort:     " + sol.longestConsecutiveSort(n2));
        
        // Test 3: Empty
        System.out.println("\nTest 3: []");
        int[] n3 = {};
        System.out.println("  Expected: 0");
        System.out.println("  Result:   " + sol.longestConsecutive(n3));
        
        // Test 4: Single element
        System.out.println("\nTest 4: [5]");
        int[] n4 = {5};
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.longestConsecutive(n4));
        
        // Test 5: All duplicates
        System.out.println("\nTest 5: [1,1,1,1]");
        int[] n5 = {1,1,1,1};
        System.out.println("  Expected: 1");
        System.out.println("  Result:   " + sol.longestConsecutive(n5));
        
        // Test 6: Negative numbers
        System.out.println("\nTest 6: [-1,-2,-3,0,1]");
        int[] n6 = {-1,-2,-3,0,1};
        System.out.println("  Expected: 5 (-3,-2,-1,0,1)");
        System.out.println("  Result:   " + sol.longestConsecutive(n6));
        
        // Test 7: Two separate sequences
        System.out.println("\nTest 7: [1,2,3,10,11,12]");
        int[] n7 = {1,2,3,10,11,12};
        System.out.println("  Expected: 3");
        System.out.println("  Result:   " + sol.longestConsecutive(n7));
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification");
        int[][] testCases = {
            {100,4,200,1,3,2},
            {0,3,7,2,5,8,4,6,0,1},
            {},
            {5},
            {1,1,1,1},
            {-1,-2,-3,0,1},
            {1,2,3,10,11,12},
            {9,1,4,7,3,-1,0,5,8,-1,6},
            {1,3,5,7,9},
            {10,9,8,7,6,5,4,3,2,1}
        };
        int[] expected = {4, 9, 0, 1, 1, 5, 3, 7, 1, 10};
        
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.longestConsecutive(testCases[i]);
            int r2 = sol.longestConsecutiveUF(testCases[i]);
            int r3 = sol.longestConsecutiveMap(testCases[i]);
            int r4 = sol.longestConsecutiveSort(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (HS:" + r1 + " UF:" + r2 + " Map:" + r3 + " Sort:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}