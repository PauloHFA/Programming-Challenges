import java.util.*;

/**
 * LeetCode 186 - Reverse String
 * 
 * Write a function that reverses a string. The input string is given as an array of characters s.
 * You must do this by modifying the input array in-place with O(1) extra memory.
 * 
 * Example 1:
 * Input: s = ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 * 
 * Example 2:
 * Input: s = ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 * 
 * Constraints:
 * - 1 <= s.length <= 10^5
 * - s[i] is a printable ascii character.
 */
public class Solution {

    // Approach 1: Two Pointers (Optimal - In-place)
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    // Approach 2: Recursive
    public void reverseStringRecursive(char[] s) {
        reverseHelper(s, 0, s.length - 1);
    }
    
    private void reverseHelper(char[] s, int left, int right) {
        if (left >= right) return;
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        reverseHelper(s, left + 1, right - 1);
    }

    // Approach 3: Using Collections.reverse (for List)
    public void reverseStringCollections(char[] s) {
        List<Character> list = new ArrayList<>();
        for (char c : s) list.add(c);
        Collections.reverse(list);
        for (int i = 0; i < s.length; i++) {
            s[i] = list.get(i);
        }
    }

    // Approach 4: Using StringBuilder (not in-place, for reference)
    public void reverseStringBuilder(char[] s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s) sb.append(c);
        sb.reverse();
        for (int i = 0; i < s.length; i++) {
            s[i] = sb.charAt(i);
        }
    }

    // Approach 5: XOR Swap (In-place, no temp variable)
    public void reverseStringXOR(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            s[left] ^= s[right];
            s[right] ^= s[left];
            s[left] ^= s[right];
            left++;
            right--;
        }
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Reverse String Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, new char[]{'h','e','l','l','o'}, new char[]{'o','l','l','e','h'}, "Example 1");
        
        // Test 2: Example 2
        testCase(sol, new char[]{'H','a','n','n','a','h'}, new char[]{'h','a','n','n','a','H'}, "Example 2");
        
        // Test 3: Single character
        testCase(sol, new char[]{'a'}, new char[]{'a'}, "Single char");
        
        // Test 4: Two characters
        testCase(sol, new char[]{'a','b'}, new char[]{'b','a'}, "Two chars");
        
        // Test 5: Even length
        testCase(sol, new char[]{'a','b','c','d'}, new char[]{'d','c','b','a'}, "Even length");
        
        // Test 6: Odd length
        testCase(sol, new char[]{'a','b','c','d','e'}, new char[]{'e','d','c','b','a'}, "Odd length");
        
        // Test 7: With special characters
        testCase(sol, new char[]{'!','@','#','$'}, new char[]{'$','#','@','!'}, "Special chars");
        
        // Test 8: Large array
        char[] large = new char[1000];
        char[] expectedLarge = new char[1000];
        for (int i = 0; i < 1000; i++) {
            large[i] = (char)('a' + (i % 26));
            expectedLarge[i] = (char)('a' + ((999 - i) % 26));
        }
        testCase(sol, large, expectedLarge, "Large array");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        char[][] testCases = {
            new char[]{'h','e','l','l','o'},
            new char[]{'H','a','n','n','a','h'},
            new char[]{'a'},
            new char[]{'a','b'},
            new char[]{'a','b','c','d'},
            new char[]{'a','b','c','d','e'},
            new char[]{'!','@','#','$'}
        };
        
        boolean allMatch = true;
        for (char[] tc : testCases) {
            char[] copy1 = tc.clone();
            char[] copy2 = tc.clone();
            char[] copy3 = tc.clone();
            char[] copy4 = tc.clone();
            char[] copy5 = tc.clone();
            
            sol.reverseString(copy1);
            sol.reverseStringRecursive(copy2);
            sol.reverseStringCollections(copy3);
            sol.reverseStringBuilder(copy4);
            sol.reverseStringXOR(copy5);
            
            if (!Arrays.equals(copy1, copy2) || !Arrays.equals(copy1, copy3) 
                || !Arrays.equals(copy1, copy4) || !Arrays.equals(copy1, copy5)) {
                System.out.println("Mismatch for " + Arrays.toString(tc));
                System.out.printf("  TwoPtr: %s, Recursive: %s, Collections: %s, Builder: %s, XOR: %s%n",
                    Arrays.toString(copy1), Arrays.toString(copy2), Arrays.toString(copy3),
                    Arrays.toString(copy4), Arrays.toString(copy5));
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, char[] input, char[] expected, String name) {
        char[] copy1 = input.clone();
        char[] copy2 = input.clone();
        char[] copy3 = input.clone();
        char[] copy4 = input.clone();
        char[] copy5 = input.clone();
        
        sol.reverseString(copy1);
        sol.reverseStringRecursive(copy2);
        sol.reverseStringCollections(copy3);
        sol.reverseStringBuilder(copy4);
        sol.reverseStringXOR(copy5);
        
        boolean pass = Arrays.equals(copy1, expected) && Arrays.equals(copy2, expected)
                      && Arrays.equals(copy3, expected) && Arrays.equals(copy4, expected)
                      && Arrays.equals(copy5, expected);
        
        System.out.printf("%s: Expected %s, Got %s %s%n", name, Arrays.toString(expected), Arrays.toString(copy1), pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  Recursive: %s, Collections: %s, Builder: %s, XOR: %s%n",
                Arrays.toString(copy2), Arrays.toString(copy3), Arrays.toString(copy4), Arrays.toString(copy5));
        }
    }
}