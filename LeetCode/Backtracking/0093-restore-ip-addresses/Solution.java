import java.util.ArrayList;
import java.util.List;

public class Solution {
    
    // Método principal: Backtracking
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() < 4 || s.length() > 12) return result;
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> segments, List<String> result) {
        // Se temos 4 segmentos
        if (segments.size() == 4) {
            // Verifica se usamos todos os caracteres
            if (start == s.length()) {
                result.add(String.join(".", segments));
            }
            return;
        }
        
        // Tenta segmentos de tamanho 1, 2, 3
        for (int len = 1; len <= 3; len++) {
            if (start + len > s.length()) break;
            
            String segment = s.substring(start, start + len);
            
            // Valida segmento
            if (isValidSegment(segment)) {
                segments.add(segment);
                backtrack(s, start + len, segments, result);
                segments.remove(segments.size() - 1);
            }
        }
    }
    
    private boolean isValidSegment(String segment) {
        // Não pode ter leading zeros (exceto "0")
        if (segment.length() > 1 && segment.charAt(0) == '0') return false;
        
        // Valor deve ser <= 255
        int val = Integer.parseInt(segment);
        return val <= 255;
    }
    
    // Método 2: Iterativo com 3 loops aninhados (para 4 segmentos)
    public List<String> restoreIpAddressesIterative(String s) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        if (n < 4 || n > 12) return result;
        
        // i, j, k são os índices de fim dos 3 primeiros segmentos
        for (int i = 1; i <= 3 && i < n - 2; i++) {
            for (int j = i + 1; j <= i + 3 && j < n - 1; j++) {
                for (int k = j + 1; k <= j + 3 && k < n; k++) {
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k);
                    
                    if (isValidSegment(s1) && isValidSegment(s2) && 
                        isValidSegment(s3) && isValidSegment(s4)) {
                        result.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        
        return result;
    }
    
    // Método 3: Bitmask (gera todas as posições de 3 pontos)
    public List<String> restoreIpAddressesBitmask(String s) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        if (n < 4 || n > 12) return result;
        
        // Precisa colocar 3 pontos em n-1 posições possíveis
        // Escolhe 3 posições entre n-1
        for (int mask = 0; mask < (1 << (n - 1)); mask++) {
            if (Integer.bitCount(mask) != 3) continue;
            
            List<Integer> dots = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                if ((mask & (1 << i)) != 0) {
                    dots.add(i + 1);  // posição após o caractere i
                }
            }
            
            if (dots.size() != 3) continue;
            
            String s1 = s.substring(0, dots.get(0));
            String s2 = s.substring(dots.get(0), dots.get(1));
            String s3 = s.substring(dots.get(1), dots.get(2));
            String s4 = s.substring(dots.get(2));
            
            if (isValidSegment(s1) && isValidSegment(s2) && 
                isValidSegment(s3) && isValidSegment(s4)) {
                result.add(s1 + "." + s2 + "." + s3 + "." + s4);
            }
        }
        
        return result;
    }
    
    // Método 4: Usa Set para evitar duplicatas (se houver)
    public List<String> restoreIpAddressesSet(String s) {
        java.util.Set<String> set = new java.util.LinkedHashSet<>();
        if (s == null || s.length() < 4 || s.length() > 12) return new ArrayList<>(set);
        
        backtrackSet(s, 0, new ArrayList<>(), set);
        return new ArrayList<>(set);
    }
    
    private void backtrackSet(String s, int start, List<String> segments, java.util.Set<String> result) {
        if (segments.size() == 4) {
            if (start == s.length()) {
                result.add(String.join(".", segments));
            }
            return;
        }
        
        for (int len = 1; len <= 3; len++) {
            if (start + len > s.length()) break;
            
            String segment = s.substring(start, start + len);
            if (isValidSegment(segment)) {
                segments.add(segment);
                backtrackSet(s, start + len, segments, result);
                segments.remove(segments.size() - 1);
            }
        }
    }
    
    // Helper: ordena e compara listas
    private boolean listsEqual(List<String> a, List<String> b) {
        if (a.size() != b.size()) return false;
        java.util.List<String> sortedA = new java.util.ArrayList<>(a);
        java.util.List<String> sortedB = new java.util.ArrayList<>(b);
        sortedA.sort(String::compareTo);
        sortedB.sort(String::compareTo);
        return sortedA.equals(sortedB);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Restore IP Addresses Tests ===\n");
        
        // Test 1: Example 1
        test(sol, "25525511135", 
             new String[]{"255.255.11.135", "255.255.111.35"}, "Test 1: Example 1");
        
        // Test 2: Example 2
        test(sol, "0000", 
             new String[]{"0.0.0.0"}, "Test 2: Example 2");
        
        // Test 3: Example 3
        test(sol, "1111", 
             new String[]{"1.1.1.1"}, "Test 3: Example 3");
        
        // Test 4: Leading zeros invalid
        test(sol, "010010", 
             new String[]{"0.10.0.10", "0.100.1.0"}, "Test 4: Leading zeros");
        
        // Test 5: All valid single digits
        test(sol, "1234", 
             new String[]{"1.2.3.4"}, "Test 5: 1234");
        
        // Test 6: Max length 12
        test(sol, "123456789012", 
             new String[]{}, "Test 6: Length 12 (no valid)");
        
        // Test 7: Length 4
        test(sol, "1010", 
                     new String[]{"1.0.1.0"}, "Test 7: 1010");
        
        // Test 8: With 255 boundary
        test(sol, "255255255255", 
             new String[]{"255.255.255.255"}, "Test 8: All 255");
        
        // Test 9: Invalid - too short
        test(sol, "123", 
             new String[]{}, "Test 9: Too short");
        
        // Test 10: Invalid - too long
        test(sol, "1234567890123", 
             new String[]{}, "Test 10: Too long");
        
        // Test 11: Mixed
        test(sol, "101023", 
             new String[]{"1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"}, "Test 11: 101023");
    }
    
    private static void test(Solution sol, String s, String[] expected, String name) {
        List<String> result1 = sol.restoreIpAddresses(s);
        List<String> result2 = sol.restoreIpAddressesIterative(s);
        List<String> result3 = sol.restoreIpAddressesBitmask(s);
        List<String> result4 = sol.restoreIpAddressesSet(s);
        
        java.util.List<String> expList = java.util.Arrays.asList(expected);
        
        boolean pass1 = sol.listsEqual(result1, expList);
        boolean pass2 = sol.listsEqual(result2, expList);
        boolean pass3 = sol.listsEqual(result3, expList);
        boolean pass4 = sol.listsEqual(result4, expList);
        boolean allMatch = sol.listsEqual(result1, result2) && sol.listsEqual(result2, result3) && sol.listsEqual(result3, result4);
        
        System.out.println(name);
        System.out.println("  Input: '" + s + "'");
        System.out.println("  Expected: " + expList);
        System.out.println("  Backtrack: " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Iterative: " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Bitmask:   " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Set:       " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}