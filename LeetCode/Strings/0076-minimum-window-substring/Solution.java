import java.util.HashMap;
import java.util.Map;

public class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Count characters in t
        Map<Character, Integer> targetCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
        }
        
        int required = targetCount.size();
        int formed = 0;
        Map<Character, Integer> windowCount = new HashMap<>();
        
        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        while (right < s.length()) {
            char c = s.charAt(right);
            windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);
            
            // Check if this character meets the required count
            if (targetCount.containsKey(c) && windowCount.get(c).equals(targetCount.get(c))) {
                formed++;
            }
            
            // Try to contract the window
            while (left <= right && formed == required) {
                c = s.charAt(left);
                
                // Update minimum window
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                // Remove left character from window
                windowCount.put(c, windowCount.get(c) - 1);
                if (targetCount.containsKey(c) && windowCount.get(c) < targetCount.get(c)) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    // Alternative: Using array for ASCII characters (faster)
    public String minWindowArray(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        int[] targetCount = new int[128];
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        int required = 0;
        for (int count : targetCount) {
            if (count > 0) required++;
        }
        
        int[] windowCount = new int[128];
        int formed = 0;
        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        while (right < s.length()) {
            char c = s.charAt(right);
            windowCount[c]++;
            
            if (targetCount[c] > 0 && windowCount[c] == targetCount[c]) {
                formed++;
            }
            
            while (left <= right && formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                char leftChar = s.charAt(left);
                windowCount[leftChar]--;
                if (targetCount[leftChar] > 0 && windowCount[leftChar] < targetCount[leftChar]) {
                    formed--;
                }
                left++;
            }
            
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    // Alternative: Brute force for verification (only for small inputs)
    public String minWindowBruteForce(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        String result = "";
        int minLen = Integer.MAX_VALUE;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + t.length(); j <= s.length(); j++) {
                String window = s.substring(i, j);
                if (containsAll(window, t)) {
                    if (window.length() < minLen) {
                        minLen = window.length();
                        result = window;
                    }
                    break; // Found minimum for this starting position
                }
            }
        }
        
        return result;
    }
    
    private boolean containsAll(String window, String t) {
        int[] count = new int[128];
        for (char c : window.toCharArray()) count[c]++;
        for (char c : t.toCharArray()) {
            if (--count[c] < 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        String[][] testCases = {
            {"ADOBECODEBANC", "ABC", "BANC"},
            {"a", "a", "a"},
            {"a", "aa", ""},
            {"ab", "b", "b"},
            {"aa", "aa", "aa"},
            {"cabwefgewcwaefgcf", "cae", "cwae"},
            {"bba", "ab", "ba"},
            {"aaflslflsldkalskaaa", "aaa", "aaa"},
        };
        
        System.out.println("=== Minimum Window Substring Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];
            String expected = testCases[i][2];
            
            String result1 = solver.minWindow(s, t);
            String result2 = solver.minWindowArray(s, t);
            String result3 = (s.length() <= 20 && t.length() <= 10) ? solver.minWindowBruteForce(s, t) : "N/A";
            
            boolean match1 = result1.equals(expected);
            boolean match2 = result2.equals(expected);
            boolean match3 = result3.equals("N/A") || result3.equals(expected);
            
            System.out.println("Test " + (i + 1) + ": s=\"" + s + "\", t=\"" + t + "\"");
            System.out.println("  Expected: " + expected);
            System.out.println("  HashMap:  " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  Array:    " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  Brute:    " + result3 + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}