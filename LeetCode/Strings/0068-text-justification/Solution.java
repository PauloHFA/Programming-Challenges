import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int n = words.length;
        int i = 0;
        
        while (i < n) {
            // Find words that fit in current line
            int lineLen = words[i].length();
            int j = i + 1;
            
            while (j < n && lineLen + 1 + words[j].length() <= maxWidth) {
                lineLen += 1 + words[j].length();
                j++;
            }
            
            // Build the line
            int wordCount = j - i;
            int totalWordLen = 0;
            for (int k = i; k < j; k++) {
                totalWordLen += words[k].length();
            }
            
            StringBuilder line = new StringBuilder();
            
            // Last line or single word: left justified
            if (j == n || wordCount == 1) {
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) line.append(" ");
                }
                // Pad with spaces at the end
                while (line.length() < maxWidth) {
                    line.append(" ");
                }
            } else {
                // Fully justified
                int totalSpaces = maxWidth - totalWordLen;
                int spacesPerGap = totalSpaces / (wordCount - 1);
                int extraSpaces = totalSpaces % (wordCount - 1);
                
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int spaces = spacesPerGap + (k - i < extraSpaces ? 1 : 0);
                        for (int s = 0; s < spaces; s++) {
                            line.append(" ");
                        }
                    }
                }
            }
            
            result.add(line.toString());
            i = j;
        }
        
        return result;
    }

    // Alternative: More explicit space distribution
    public List<String> fullJustifyExplicit(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        
        while (i < words.length) {
            int j = i;
            int lineLen = 0;
            
            // Find words for this line
            while (j < words.length && lineLen + words[j].length() + (j - i) <= maxWidth) {
                lineLen += words[j].length();
                j++;
            }
            
            int wordCount = j - i;
            int spacesNeeded = maxWidth - lineLen;
            StringBuilder line = new StringBuilder();
            
            if (j == words.length || wordCount == 1) {
                // Last line or single word - left justify
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) line.append(" ");
                }
                // Pad end
                while (line.length() < maxWidth) line.append(" ");
            } else {
                // Full justify
                int spacesPerGap = spacesNeeded / (wordCount - 1);
                int extraSpaces = spacesNeeded % (wordCount - 1);
                
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int spaces = spacesPerGap + (k - i < extraSpaces ? 1 : 0);
                        line.append(" ".repeat(spaces));
                    }
                }
            }
            
            result.add(line.toString());
            i = j;
        }
        
        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test case 1
        String[] words1 = {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth1 = 16;
        List<String> result1 = solver.fullJustify(words1, maxWidth1);
        System.out.println("=== Test 1 ===");
        for (String line : result1) {
            System.out.println("[" + line + "] len=" + line.length());
        }
        System.out.println();
        
        // Test case 2
        String[] words2 = {"What","must","be","acknowledgment","shall","be"};
        int maxWidth2 = 16;
        List<String> result2 = solver.fullJustify(words2, maxWidth2);
        System.out.println("=== Test 2 ===");
        for (String line : result2) {
            System.out.println("[" + line + "] len=" + line.length());
        }
        System.out.println();
        
        // Test case 3
        String[] words3 = {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
        int maxWidth3 = 20;
        List<String> result3 = solver.fullJustify(words3, maxWidth3);
        System.out.println("=== Test 3 ===");
        for (String line : result3) {
            System.out.println("[" + line + "] len=" + line.length());
        }
        System.out.println();
        
        // Verify both methods produce same results
        System.out.println("=== Verification ===");
        List<String> r1 = solver.fullJustify(words1, maxWidth1);
        List<String> r2 = solver.fullJustifyExplicit(words1, maxWidth1);
        System.out.println("Test 1 match: " + r1.equals(r2));
        
        r1 = solver.fullJustify(words2, maxWidth2);
        r2 = solver.fullJustifyExplicit(words2, maxWidth2);
        System.out.println("Test 2 match: " + r1.equals(r2));
        
        r1 = solver.fullJustify(words3, maxWidth3);
        r2 = solver.fullJustifyExplicit(words3, maxWidth3);
        System.out.println("Test 3 match: " + r1.equals(r2));
    }
}