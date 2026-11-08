import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 0049: Group Anagrams
 * Difficulty: Medium
 * Category: Arrays & Hashing
 * 
 * Approach: Hash Map with sorted string as key
 * Time Complexity: O(n * k log k) where n is number of strings, k is max length
 * Space Complexity: O(n * k)
 */
public class Solution {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        
        return new ArrayList<>(map.values());
    }

    // Alternative: Character count as key (O(n * k) time)
    public List<List<String>> groupAnagramsCount(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            
            // Convert count array to string key
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#').append(count[i]);
            }
            String key = sb.toString();
            
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result1 = solver.groupAnagrams(strs1);
        System.out.println("Test 1: " + result1);
        System.out.println("Expected groups: [eat,tea,ate], [tan,nat], [bat]");

        // Test Case 2
        String[] strs2 = {""};
        List<List<String>> result2 = solver.groupAnagrams(strs2);
        System.out.println("\nTest 2: " + result2);
        System.out.println("Expected: [[]]");

        // Test Case 3
        String[] strs3 = {"a"};
        List<List<String>> result3 = solver.groupAnagrams(strs3);
        System.out.println("\nTest 3: " + result3);
        System.out.println("Expected: [[a]]");

        // Test Case 4: All anagrams
        String[] strs4 = {"abc", "bca", "cab", "cba", "acb", "bac"};
        List<List<String>> result4 = solver.groupAnagrams(strs4);
        System.out.println("\nTest 4: " + result4);
        System.out.println("Expected: one group with all 6");

        // Test Case 5: No anagrams
        String[] strs5 = {"abc", "def", "ghi"};
        List<List<String>> result5 = solver.groupAnagrams(strs5);
        System.out.println("\nTest 5: " + result5);
        System.out.println("Expected: 3 groups of 1 each");

        // Test Case 6: Mixed case
        String[] strs6 = {"ddddddddddg", "dgggggggggg"};
        List<List<String>> result6 = solver.groupAnagrams(strs6);
        System.out.println("\nTest 6: " + result6);

        // Verify both approaches match
        System.out.println("\n--- Verification (Sort vs Count) ---");
        String[][] testCases = {
            {"eat", "tea", "tan", "ate", "nat", "bat"},
            {""},
            {"a"},
            {"abc", "bca", "cab"},
            {"abc", "def", "ghi"},
            {"ddddddddddg", "dgggggggggg"}
        };
        
        for (String[] test : testCases) {
            List<List<String>> sort = solver.groupAnagrams(test.clone());
            List<List<String>> count = solver.groupAnagramsCount(test.clone());
            
            // Sort groups for comparison
                    for (List<String> group : sort) {
                        group.sort(String::compareTo);
                    }
                    for (List<String> group : count) {
                        group.sort(String::compareTo);
                    }
                    sort.sort((a, b) -> a.get(0).compareTo(b.get(0)));
                    count.sort((a, b) -> a.get(0).compareTo(b.get(0)));
            
                    System.out.println("Input: " + Arrays.toString(test) + " -> Match: " + sort.equals(count));
                }
    }
}