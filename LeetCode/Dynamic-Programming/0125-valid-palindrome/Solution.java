import java.util.*;

/**
 * LeetCode 125 - Valid Palindrome
 * 
 * Uma frase é um palíndromo se, após converter todas as letras maiúsculas em minúsculas
 * e remover todos os caracteres não alfanuméricos, ela lê da mesma forma para frente
 * e para trás. Caracteres alfanuméricos incluem letras e números.
 * Dada uma string s, retorne true se for um palíndromo, ou false caso contrário.
 */
public class Solution {

    /**
     * Método 1: Two Pointers (Principal)
     * Dois ponteiros nas extremidades, movendo para o centro
     * Tempo: O(n), Espaço: O(1)
     */
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Pula caracteres não alfanuméricos à esquerda
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Pula caracteres não alfanuméricos à direita
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            if (left < right) {
                char cl = Character.toLowerCase(s.charAt(left));
                char cr = Character.toLowerCase(s.charAt(right));
                if (cl != cr) {
                    return false;
                }
                left++;
                right--;
            }
        }
        
        return true;
    }

    /**
     * Método 2: StringBuilder + Reverse
     * Filtra e normaliza, depois compara com reverso
     * Tempo: O(n), Espaço: O(n)
     */
    public boolean isPalindromeSB(String s) {
        StringBuilder filtered = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                filtered.append(Character.toLowerCase(c));
            }
        }
        String str = filtered.toString();
        return str.equals(filtered.reverse().toString());
    }

    /**
     * Método 3: Regex + StringBuilder
     * Usa regex para remover não alfanuméricos
     * Tempo: O(n), Espaço: O(n)
     */
    public boolean isPalindromeRegex(String s) {
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    /**
     * Método 4: Two Pointers com array de chars
     * Converte para array primeiro
     * Tempo: O(n), Espaço: O(n)
     */
    public boolean isPalindromeArray(String s) {
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(chars[left])) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(chars[right])) {
                right--;
            }
            
            if (left < right) {
                if (Character.toLowerCase(chars[left]) != Character.toLowerCase(chars[right])) {
                    return false;
                }
                left++;
                right--;
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Valid Palindrome Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: \"A man, a plan, a canal: Panama\"");
        String s1 = "A man, a plan, a canal: Panama";
        System.out.println("  Expected: true");
        System.out.println("  TwoPtr:   " + sol.isPalindrome(s1));
        System.out.println("  SB:       " + sol.isPalindromeSB(s1));
        System.out.println("  Regex:    " + sol.isPalindromeRegex(s1));
        System.out.println("  Array:    " + sol.isPalindromeArray(s1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: \"race a car\"");
        String s2 = "race a car";
        System.out.println("  Expected: false");
        System.out.println("  Result:   " + sol.isPalindrome(s2));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: \" \"");
        String s3 = " ";
        System.out.println("  Expected: true");
        System.out.println("  Result:   " + sol.isPalindrome(s3));
        
        // Test 4: Single char
        System.out.println("\nTest 4: \"a\"");
        String s4 = "a";
        System.out.println("  Expected: true");
        System.out.println("  Result:   " + sol.isPalindrome(s4));
        
        // Test 5: Numbers
        System.out.println("\nTest 5: \"0P\"");
        String s5 = "0P";
        System.out.println("  Expected: false");
        System.out.println("  Result:   " + sol.isPalindrome(s5));
        
        // Test 6: Only non-alphanumeric
        System.out.println("\nTest 6: \",.\"");
        String s6 = ",.";
        System.out.println("  Expected: true");
        System.out.println("  Result:   " + sol.isPalindrome(s6));
        
        // Test 7: Palindrome with numbers
        System.out.println("\nTest 7: \"1a2\"");
        String s7 = "1a2";
        System.out.println("  Expected: false");
        System.out.println("  Result:   " + sol.isPalindrome(s7));
        
        // Test 8: Long palindrome
        System.out.println("\nTest 8: \"Was it a car or a cat I saw\"");
        String s8 = "Was it a car or a cat I saw";
        System.out.println("  Expected: true");
        System.out.println("  Result:   " + sol.isPalindrome(s8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification");
        String[] testCases = {
            "A man, a plan, a canal: Panama",
            "race a car",
            " ",
            "a",
            "0P",
            ",.",
            "1a2",
            "Was it a car or a cat I saw",
            "No lemon, no melon",
            "Madam, I'm Adam"
        };
        boolean[] expected = {true, false, true, true, false, true, false, true, true, true};
        
        for (int i = 0; i < testCases.length; i++) {
            boolean r1 = sol.isPalindrome(testCases[i]);
            boolean r2 = sol.isPalindromeSB(testCases[i]);
            boolean r3 = sol.isPalindromeRegex(testCases[i]);
            boolean r4 = sol.isPalindromeArray(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (TP:" + r1 + " SB:" + r2 + " RX:" + r3 + " AR:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}