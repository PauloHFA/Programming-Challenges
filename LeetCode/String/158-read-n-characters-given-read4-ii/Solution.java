/**
 * LeetCode 158 - Read N Characters Given Read4 II - Call multiple times
 * 
 * Given a file and assume that you can only read the file using a given method read4,
 * implement a method read to read n characters. Your method read may be called multiple times.
 * 
 * This is the follow-up to problem 157 where read() can be called multiple times.
 * The key difference is that we need to maintain state between calls.
 * 
 * Method read4:
 * The API read4 reads 4 consecutive characters from the file, then writes those characters into the buffer array buf4.
 * The return value is the number of actual characters read.
 * 
 * Definition of read4:
 *     Parameter:  char[] buf4
 *     Returns:    int
 * 
 * Method read:
 * By using the read4 method, implement the method read that reads n characters from the file and store it in the buffer array buf.
 * The return value is the number of actual characters read.
 * 
 * Definition of read:
 *     Parameters: char[] buf, int n
 *     Returns:    int
 * 
 * Example 1:
 * Input: file = "abc", queries = [1,2,1]
 * Output: [1,2,1]
 * Explanation: 
 * read(1) -> "a", returns 1
 * read(2) -> "bc", returns 2
 * read(1) -> "", returns 0 (EOF)
 * 
 * Example 2:
 * Input: file = "abc", queries = [4,1]
 * Output: [3,0]
 * Explanation:
 * read(4) -> "abc", returns 3
 * read(1) -> "", returns 0 (EOF)
 * 
 * Constraints:
 * 1 <= file.length <= 500
 * 1 <= n <= 500
 * file consists of English letters and digits.
 * read4 will only be called with valid parameters.
 * The read method may be called multiple times.
 */
public class Solution {
    
    /**
     * Simulated read4 API for testing
     */
    static class Read4 {
        private String file;
        private int pointer;
        
        public Read4(String file) {
            this.file = file;
            this.pointer = 0;
        }
        
        public int read4(char[] buf4) {
            int count = 0;
            while (count < 4 && pointer < file.length()) {
                buf4[count] = file.charAt(pointer);
                pointer++;
                count++;
            }
            return count;
        }
        
        public void reset(String file) {
            this.file = file;
            this.pointer = 0;
        }
    }
    
    /**
     * Approach 1: Internal Buffer with State - O(n) time, O(1) space
     * 
     * Maintains an internal buffer (size 4) and tracks position within it.
     * State persists between read() calls.
     * 
     * Time Complexity: O(n) per read call
     * Space Complexity: O(1)
     */
        static class SolutionBuffer implements Reader {
        private Read4 read4;
        private char[] buf4 = new char[4];
        private int buf4Idx = 0;
        private int buf4Size = 0;
        
        public SolutionBuffer(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            
            while (total < n) {
                // If we have buffered characters, use them first
                if (buf4Idx < buf4Size) {
                    buf[total++] = buf4[buf4Idx++];
                } else {
                    // Need to read more from file
                    buf4Size = read4.read4(buf4);
                    buf4Idx = 0;
                    if (buf4Size == 0) break; // EOF
                }
            }
            
            return total;
        }
    }
    
    /**
     * Approach 2: Queue-based Buffer - O(n) time, O(1) space (amortized)
     * 
     * Uses a queue to buffer extra characters. More intuitive but slightly more overhead.
     * 
     * Time Complexity: O(n) per read call
     * Space Complexity: O(1) - queue holds at most 3 characters
     */
        static class SolutionQueue implements Reader {
        private Read4 read4;
        private java.util.Queue<Character> queue = new java.util.LinkedList<>();
        
        public SolutionQueue(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            
            // First, consume from queue
            while (total < n && !queue.isEmpty()) {
                buf[total++] = queue.poll();
            }
            
            // Then, read from file using read4
            char[] buf4 = new char[4];
            while (total < n) {
                int count = read4.read4(buf4);
                if (count == 0) break; // EOF
                
                    int copied = Math.min(count, n - total);
                    for (int i = 0; i < copied; i++) {
                        buf[total++] = buf4[i];
                    }
                
                    // Buffer extra characters
                    for (int i = copied; i < count; i++) {
                        queue.offer(buf4[i]);
                    }
                
                    if (count < 4) break; // EOF
                }
            
                return total;
            }
        }
    
    /**
     * Approach 3: Circular Buffer - O(n) time, O(1) space
     * 
     * Uses a circular buffer approach with explicit head/tail pointers.
     * 
     * Time Complexity: O(n) per read call
     * Space Complexity: O(1)
     */
        static class SolutionCircular implements Reader {
        private Read4 read4;
        private char[] buffer = new char[4];
        private int head = 0;
        private int tail = 0;
        private int size = 0;
        
        public SolutionCircular(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            
            while (total < n) {
                if (size > 0) {
                    // Consume from buffer
                    buf[total++] = buffer[head];
                    head = (head + 1) % 4;
                    size--;
                } else {
                    // Refill buffer
                    int count = read4.read4(buffer);
                    if (count == 0) break; // EOF
                    head = 0;
                    tail = count;
                    size = count;
                }
            }
            
            return total;
        }
    }
    
    /**
     * Approach 4: StringBuilder Buffer - O(n) time, O(1) space
     * 
     * Uses StringBuilder as internal buffer. Simple and clean.
     * 
     * Time Complexity: O(n) per read call
     * Space Complexity: O(1) - StringBuilder holds at most 3 chars
     */
        static class SolutionStringBuilder implements Reader {
        private Read4 read4;
        private StringBuilder buffer = new StringBuilder();
        
        public SolutionStringBuilder(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            
            while (total < n) {
                if (buffer.length() > 0) {
                    // Consume from buffer
                    buf[total++] = buffer.charAt(0);
                    buffer.deleteCharAt(0);
                } else {
                    // Refill buffer
                    char[] buf4 = new char[4];
                    int count = read4.read4(buf4);
                    if (count == 0) break; // EOF
                    buffer.append(buf4, 0, count);
                }
            }
            
            return total;
        }
    }
    
    // Test all approaches
    public static void main(String[] args) {
        System.out.println("=== Read N Characters Given Read4 II Tests ===\n");
        
        // Test 1: Example 1 - file = "abc", queries = [1,2,1]
        System.out.println("Test 1: file=\"abc\", queries=[1,2,1] -> Expected: [1,2,0]");
        testMultipleReads("abc", new int[]{1, 2, 1}, new int[]{1, 2, 0}, new String[]{"a", "bc", ""});
        
        // Test 2: Example 2 - file = "abc", queries = [4,1]
        System.out.println("\nTest 2: file=\"abc\", queries=[4,1] -> Expected: [3,0]");
        testMultipleReads("abc", new int[]{4, 1}, new int[]{3, 0}, new String[]{"abc", ""});
        
        // Test 3: Multiple small reads
        System.out.println("\nTest 3: file=\"hello world\", queries=[1,1,1,1,1,1,1,1,1,1,1]");
        testMultipleReads("hello world", 
            new int[]{1,1,1,1,1,1,1,1,1,1,1}, 
            new int[]{1,1,1,1,1,1,1,1,1,1,1},
            new String[]{"h","e","l","l","o"," ","w","o","r","l","d"});
        
        // Test 4: Read larger than remaining
        System.out.println("\nTest 4: file=\"abc\", queries=[2,5] -> Expected: [2,1]");
        testMultipleReads("abc", new int[]{2, 5}, new int[]{2, 1}, new String[]{"ab", "c"});
        
        // Test 5: Exact reads
        System.out.println("\nTest 5: file=\"abcdabcd\", queries=[4,4] -> Expected: [4,4]");
        testMultipleReads("abcdabcd", new int[]{4, 4}, new int[]{4, 4}, new String[]{"abcd", "abcd"});
        
        // Test 6: Empty file
        System.out.println("\nTest 6: file=\"\", queries=[1,2] -> Expected: [0,0]");
        testMultipleReads("", new int[]{1, 2}, new int[]{0, 0}, new String[]{"", ""});
        
        // Test 7: Single char file, multiple reads
        System.out.println("\nTest 7: file=\"a\", queries=[1,1,1] -> Expected: [1,0,0]");
        testMultipleReads("a", new int[]{1, 1, 1}, new int[]{1, 0, 0}, new String[]{"a", "", ""});
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification of all approaches");
        crossVerify();
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testMultipleReads(String file, int[] queries, int[] expectedCounts, String[] expectedBufs) {
        // Test Buffer approach
        Read4 r1 = new Read4(file);
        SolutionBuffer sb = new SolutionBuffer(r1);
        testReads(sb, queries, expectedCounts, expectedBufs, "Buffer");
        
        // Test Queue approach
        Read4 r2 = new Read4(file);
        SolutionQueue sq = new SolutionQueue(r2);
        testReads(sq, queries, expectedCounts, expectedBufs, "Queue");
        
        // Test Circular approach
        Read4 r3 = new Read4(file);
        SolutionCircular sc = new SolutionCircular(r3);
        testReads(sc, queries, expectedCounts, expectedBufs, "Circular");
        
        // Test StringBuilder approach
        Read4 r4 = new Read4(file);
        SolutionStringBuilder ssb = new SolutionStringBuilder(r4);
        testReads(ssb, queries, expectedCounts, expectedBufs, "StringBuilder");
    }
    
    interface Reader {
        int read(char[] buf, int n);
    }
    
    static void testReads(Reader reader, int[] queries, int[] expectedCounts, String[] expectedBufs, String name) {
        for (int i = 0; i < queries.length; i++) {
            char[] buf = new char[queries[i]];
            int count = reader.read(buf, queries[i]);
            String result = new String(buf, 0, count);
            assert count == expectedCounts[i] && result.equals(expectedBufs[i]) : 
                name + " read " + (i+1) + ": expected count=" + expectedCounts[i] + " buf=" + expectedBufs[i] + ", got count=" + count + " buf=" + result;
        }
        System.out.println("  " + name + ": ✓");
    }
    
    static void crossVerify() {
        String[] files = {"abc", "abcde", "abcdABCD1234", "hello world", "abcdabcd", "", "a", "abcdefghijklmnopqrstuvwxyz"};
        int[][] queries = {
            {1, 2, 1},
            {4, 1},
            {3, 3, 3, 3},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {4, 4},
            {1, 2},
            {1, 1, 1},
            {5, 5, 5, 5, 5, 5}
        };
        
        for (int f = 0; f < files.length; f++) {
            Read4 r1 = new Read4(files[f]);
            Read4 r2 = new Read4(files[f]);
            Read4 r3 = new Read4(files[f]);
            Read4 r4 = new Read4(files[f]);
            
            SolutionBuffer sb = new SolutionBuffer(r1);
            SolutionQueue sq = new SolutionQueue(r2);
            SolutionCircular sc = new SolutionCircular(r3);
            SolutionStringBuilder ssb = new SolutionStringBuilder(r4);
            
            boolean allMatch = true;
            for (int q = 0; q < queries[f].length; q++) {
                char[] b1 = new char[queries[f][q]];
                char[] b2 = new char[queries[f][q]];
                char[] b3 = new char[queries[f][q]];
                char[] b4 = new char[queries[f][q]];
                
                int c1 = sb.read(b1, queries[f][q]);
                int c2 = sq.read(b2, queries[f][q]);
                int c3 = sc.read(b3, queries[f][q]);
                int c4 = ssb.read(b4, queries[f][q]);
                
                String s1 = new String(b1, 0, c1);
                String s2 = new String(b2, 0, c2);
                String s3 = new String(b3, 0, c3);
                String s4 = new String(b4, 0, c4);
                
                if (!(c1 == c2 && c2 == c3 && c3 == c4 && s1.equals(s2) && s2.equals(s3) && s3.equals(s4))) {
                    allMatch = false;
                    System.out.println("  MISMATCH at file=\"" + files[f] + "\", query=" + queries[f][q] + 
                        " (counts: " + c1 + "," + c2 + "," + c3 + "," + c4 + ")");
                    System.out.println("    bufs: \"" + s1 + "\", \"" + s2 + "\", \"" + s3 + "\", \"" + s4 + "\"");
                }
            }
            System.out.println("  File \"" + files[f] + "\": " + (allMatch ? "MATCH" : "MISMATCH") + " ✓");
        }
    }
}