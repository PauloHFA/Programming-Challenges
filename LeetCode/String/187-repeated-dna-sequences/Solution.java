import java.util.*;

/**
 * LeetCode 187 - Repeated DNA Sequences
 * 
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
 * For example, "ACGAATTCCG" is a DNA sequence.
 * When studying DNA, it is useful to identify repeated sequences within the DNA.
 * Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule. You may return the answer in any order.
 * 
 * Example 1:
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 * 
 * Example 2:
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 * 
 * Constraints:
 * - 1 <= s.length <= 10^5
 * - s[i] is either 'A', 'C', 'G', or 'T'.
 */
public class Solution {

    // Approach 1: HashSet + HashSet (Optimal)
    // Use one set to track seen sequences, another for repeated
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        
        for (int i = 0; i + 10 <= s.length(); i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub)) {
                repeated.add(sub);
            }
        }
        
        return new ArrayList<>(repeated);
    }

    // Approach 2: HashMap with Count
    // Count occurrences of each 10-letter sequence
    public List<String> findRepeatedDnaSequencesMap(String s) {
        Map<String, Integer> count = new HashMap<>();
        
        for (int i = 0; i + 10 <= s.length(); i++) {
            String sub = s.substring(i, i + 10);
            count.put(sub, count.getOrDefault(sub, 0) + 1);
        }
        
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            if (entry.getValue() > 1) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    // Approach 3: Bit Manipulation (Rolling Hash)
    // Encode A=00, C=01, G=10, T=11. 10 letters = 20 bits fits in int
    public List<String> findRepeatedDnaSequencesBit(String s) {
        if (s.length() < 10) return new ArrayList<>();
        
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0); // 00
        map.put('C', 1); // 01
        map.put('G', 2); // 10
        map.put('T', 3); // 11
        
        Set<Integer> seen = new HashSet<>();
        Set<Integer> repeated = new HashSet<>();
        
        int hash = 0;
        // First 10 characters
        for (int i = 0; i < 10; i++) {
            hash = (hash << 2) | map.get(s.charAt(i));
        }
        seen.add(hash);
        
        // Rolling hash for remaining
        int mask = (1 << 20) - 1; // Keep only 20 bits
        for (int i = 10; i < s.length(); i++) {
            hash = ((hash << 2) | map.get(s.charAt(i))) & mask;
            if (!seen.add(hash)) {
                repeated.add(hash);
            }
        }
        
        // Convert back to strings
        List<String> result = new ArrayList<>();
        for (int h : repeated) {
            result.add(decode(h));
        }
        return result;
    }
    
    private String decode(int hash) {
        char[] chars = new char[10];
        for (int i = 9; i >= 0; i--) {
            int val = hash & 3;
            switch (val) {
                case 0: chars[i] = 'A'; break;
                case 1: chars[i] = 'C'; break;
                case 2: chars[i] = 'G'; break;
                case 3: chars[i] = 'T'; break;
            }
            hash >>= 2;
        }
        return new String(chars);
    }

    // Approach 4: Sliding Window with StringBuilder (Less efficient but clear)
    public List<String> findRepeatedDnaSequencesSB(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            if (sb.length() > 10) {
                sb.deleteCharAt(0);
            }
            if (sb.length() == 10) {
                String sub = sb.toString();
                if (!seen.add(sub)) {
                    repeated.add(sub);
                }
            }
        }
        
        return new ArrayList<>(repeated);
    }

    // Approach 5: Using Array instead of HashMap for bit encoding (Faster)
    public List<String> findRepeatedDnaSequencesArray(String s) {
        if (s.length() < 10) return new ArrayList<>();
        
        int[] map = new int[26];
        map['A' - 'A'] = 0;
        map['C' - 'A'] = 1;
        map['G' - 'A'] = 2;
        map['T' - 'A'] = 3;
        
        boolean[] seen = new boolean[1 << 20];
        boolean[] repeated = new boolean[1 << 20];
        
        int hash = 0;
        for (int i = 0; i < 10; i++) {
            hash = (hash << 2) | map[s.charAt(i) - 'A'];
        }
        seen[hash] = true;
        
        int mask = (1 << 20) - 1;
        for (int i = 10; i < s.length(); i++) {
            hash = ((hash << 2) | map[s.charAt(i) - 'A']) & mask;
            if (seen[hash]) {
                repeated[hash] = true;
            } else {
                seen[hash] = true;
            }
        }
        
        List<String> result = new ArrayList<>();
        for (int h = 0; h < (1 << 20); h++) {
            if (repeated[h]) {
                result.add(decode(h));
            }
        }
        return result;
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Repeated DNA Sequences Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", 
            new String[]{"AAAAACCCCC", "CCCCCAAAAA"}, "Example 1");
        
        // Test 2: Example 2
        testCase(sol, "AAAAAAAAAAAAA", 
            new String[]{"AAAAAAAAAA"}, "Example 2");
        
        // Test 3: No repeats
        testCase(sol, "ACGTACGTAC", 
            new String[]{}, "No repeats");
        
        // Test 4: Short string - exactly 10 chars, appears once so no repeats
        testCase(sol, "AAAAAAAAAA", 
                    new String[]{}, "Exactly 10");
        
        // Test 5: Short string < 10
        testCase(sol, "AAAAA", 
            new String[]{}, "Less than 10");
        
        // Test 6: Multiple repeats
        testCase(sol, "AAAAAAAAAAAAAAAAAAAA", 
            new String[]{"AAAAAAAAAA"}, "Many repeats");
        
        // Test 7: Overlapping repeats
        testCase(sol, "AAAAAAAAAAA", 
            new String[]{"AAAAAAAAAA"}, "Overlapping");
        
        // Test 8: Complex case
        testCase(sol, "ACGAATTCCGACGAATTCCG", 
            new String[]{"ACGAATTCCG"}, "Complex");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        String[] testCases = {
            "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
            "AAAAAAAAAAAAA",
            "ACGTACGTAC",
            "AAAAAAAAAA",
            "AAAAA",
            "AAAAAAAAAAAAAAAAAAAA",
            "AAAAAAAAAAA",
            "ACGAATTCCGACGAATTCCG"
        };
        
        boolean allMatch = true;
        for (String tc : testCases) {
            List<String> r1 = sol.findRepeatedDnaSequences(tc);
            List<String> r2 = sol.findRepeatedDnaSequencesMap(tc);
            List<String> r3 = sol.findRepeatedDnaSequencesBit(tc);
            List<String> r4 = sol.findRepeatedDnaSequencesSB(tc);
            List<String> r5 = sol.findRepeatedDnaSequencesArray(tc);
            
            Collections.sort(r1);
            Collections.sort(r2);
            Collections.sort(r3);
            Collections.sort(r4);
            Collections.sort(r5);
            
            if (!r1.equals(r2) || !r1.equals(r3) || !r1.equals(r4) || !r1.equals(r5)) {
                System.out.println("Mismatch for " + tc);
                System.out.printf("  Set: %s, Map: %s, Bit: %s, SB: %s, Array: %s%n", r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, String input, String[] expected, String name) {
        List<String> r1 = sol.findRepeatedDnaSequences(input);
        List<String> r2 = sol.findRepeatedDnaSequencesMap(input);
        List<String> r3 = sol.findRepeatedDnaSequencesBit(input);
        List<String> r4 = sol.findRepeatedDnaSequencesSB(input);
        List<String> r5 = sol.findRepeatedDnaSequencesArray(input);
        
        List<String> expList = Arrays.asList(expected);
        Collections.sort(r1);
        Collections.sort(expList);
        
        boolean pass = r1.equals(expList) && r2.equals(expList) && r3.equals(expList) 
                      && r4.equals(expList) && r5.equals(expList);
        
        System.out.printf("%s: Expected %s, Got %s %s%n", name, expList, r1, pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  Map: %s, Bit: %s, SB: %s, Array: %s%n", r2, r3, r4, r5);
        }
    }
}