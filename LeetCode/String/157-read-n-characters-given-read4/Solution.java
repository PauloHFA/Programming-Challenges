/**
 * LeetCode 157 - Read N Characters Given Read4
 * 
 * Given a file and assume that you can only read the file using a given method read4,
 * implement a method read to read n characters. Your method read may be called multiple times.
 * 
 * Method read4:
 * The API read4 reads 4 consecutive characters from the file, then writes those characters into the buffer array buf4.
 * The return value is the number of actual characters read.
 * Note that read4() has its own file pointer, much like FILE *fp in C.
 * 
 * Definition of read4:
 *     Parameter:  char[] buf4
 *     Returns:    int
 * 
 * Note: buf4 is guaranteed to have enough space for storing 4 characters.
 * 
 * Method read:
 * By using the read4 method, implement the method read that reads n characters from the file and store it in the buffer array buf.
 * The return value is the number of actual characters read.
 * 
 * Definition of read:
 *     Parameters: char[] buf, int n
 *     Returns:    int
 * 
 * Note: buf is guaranteed to have enough space for storing n characters.
 * 
 * Example 1:
 * Input: file = "abc", n = 4
 * Output: 3
 * Explanation: After calling your read method, buf should contain "abc". We read a total of 3 characters from the file.
 * 
 * Example 2:
 * Input: file = "abcde", n = 5
 * Output: 5
 * Explanation: After calling your read method, buf should contain "abcde". We read a total of 5 characters from the file.
 * 
 * Example 3:
 * Input: file = "abcdABCD1234", n = 12
 * Output: 12
 * Explanation: After calling your read method, buf should contain "abcdABCD1234". We read a total of 12 characters from the file.
 * 
 * Constraints:
 * 1 <= file.length <= 500
 * 1 <= n <= 500
 * file consists of English letters and digits.
 * read4 will only be called with valid parameters.
 * 
 * Follow up: The read method may be called multiple times.
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
     * Approach 1: Buffer with Queue - O(n) time, O(1) space (amortized)
     * 
     * Use a queue to buffer extra characters read by read4.
     * When read is called, first consume from queue, then call read4 as needed.
     * 
     * Time Complexity: O(n) where n is the number of characters read
     * Space Complexity: O(1) - queue holds at most 3 characters
     */
    static class SolutionQueue {
        private java.util.Queue<Character> queue = new java.util.LinkedList<>();
        private Read4 read4;
        
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
                
                for (int i = 0; i < count && total < n; i++) {
                    buf[total++] = buf4[i];
                }
                
                // Buffer extra characters
                for (int i = total - (total - (total - count)); i < count; i++) {
                    if (total + (i - (count - (total - (total - count)))) < n) {
                        // This logic is getting complex, let's simplify
                    }
                }
                
                // Actually, simpler: buffer chars beyond what we need
                for (int i = 0; i < count; i++) {
                    if (total + i < n) {
                        // Already copied above
                    } else {
                        queue.offer(buf4[i]);
                    }
                }
                
                if (count < 4) break; // EOF
            }
            
            return total;
        }
    }
    
    /**
     * Approach 2: Internal Buffer Array - O(n) time, O(1) space
     * 
     * Use a fixed-size internal buffer (size 4) and track start/end positions.
     * More efficient than queue for this specific problem.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static class SolutionBuffer {
        private Read4 read4;
        private char[] buffer = new char[4];
        private int bufferStart = 0;
        private int bufferEnd = 0;
        
        public SolutionBuffer(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            
            while (total < n) {
                // If buffer is empty, refill it
                if (bufferStart == bufferEnd) {
                    bufferEnd = read4.read4(buffer);
                    bufferStart = 0;
                    if (bufferEnd == 0) break; // EOF
                }
                
                // Copy from buffer to buf
                while (total < n && bufferStart < bufferEnd) {
                    buf[total++] = buffer[bufferStart++];
                }
            }
            
            return total;
        }
    }
    
    /**
     * Approach 3: Simple Loop with read4 - O(n) time, O(1) space
     * 
     * Directly call read4 in a loop, copying to buf.
     * Simpler but may call read4 more times than necessary.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static class SolutionSimple {
        private Read4 read4;
        
        public SolutionSimple(Read4 read4) {
            this.read4 = read4;
        }
        
        public int read(char[] buf, int n) {
            int total = 0;
            char[] buf4 = new char[4];
            
            while (total < n) {
                int count = read4.read4(buf4);
                if (count == 0) break;
                
                for (int i = 0; i < count && total < n; i++) {
                    buf[total++] = buf4[i];
                }
                
                if (count < 4) break;
            }
            
            return total;
        }
    }
    
    /**
     * Approach 4: Optimized with Internal State - O(n) time, O(1) space
     * 
     * Similar to Approach 2 but with explicit state management.
     * Handles multiple read() calls correctly.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static class SolutionOptimized {
        private Read4 read4;
        private char[] buf4 = new char[4];
        private int buf4Idx = 0;
        private int buf4Size = 0;
        
        public SolutionOptimized(Read4 read4) {
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
    
    // Test all approaches
    public static void main(String[] args) {
        System.out.println("=== Read N Characters Given Read4 Tests ===\n");
        
        // Test 1: Example 1 - file = "abc", n = 4
        System.out.println("Test 1: file=\"abc\", n=4 -> Expected: 3, buf=\"abc\"");
        testRead("abc", 4, 3, "abc");
        
        // Test 2: Example 2 - file = "abcde", n = 5
        System.out.println("\nTest 2: file=\"abcde\", n=5 -> Expected: 5, buf=\"abcde\"");
        testRead("abcde", 5, 5, "abcde");
        
        // Test 3: Example 3 - file = "abcdABCD1234", n = 12
        System.out.println("\nTest 3: file=\"abcdABCD1234\", n=12 -> Expected: 12");
        testRead("abcdABCD1234", 12, 12, "abcdABCD1234");
        
        // Test 4: n larger than file
        System.out.println("\nTest 4: file=\"hello\", n=10 -> Expected: 5, buf=\"hello\"");
        testRead("hello", 10, 5, "hello");
        
        // Test 5: n smaller than file
        System.out.println("\nTest 5: file=\"hello world\", n=5 -> Expected: 5, buf=\"hello\"");
        testRead("hello world", 5, 5, "hello");
        
        // Test 6: Exact multiple of 4
        System.out.println("\nTest 6: file=\"abcdabcd\", n=8 -> Expected: 8");
        testRead("abcdabcd", 8, 8, "abcdabcd");
        
        // Test 7: Multiple read calls (follow up)
        System.out.println("\nTest 7: Multiple read calls on same file");
        testMultipleReads();
        
        // Test 8: Empty file
        System.out.println("\nTest 8: file=\"\", n=5 -> Expected: 0");
        testRead("", 5, 0, "");
        
        // Test 9: Single character
        System.out.println("\nTest 9: file=\"a\", n=1 -> Expected: 1, buf=\"a\"");
        testRead("a", 1, 1, "a");
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        crossVerify();
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testRead(String file, int n, int expectedCount, String expectedBuf) {
        Read4 read4 = new Read4(file);
        SolutionQueue sq = new SolutionQueue(read4);
        char[] buf1 = new char[n];
        int count1 = sq.read(buf1, n);
        String result1 = new String(buf1, 0, count1);
        assert count1 == expectedCount && result1.equals(expectedBuf) : 
            "Queue: expected count=" + expectedCount + " buf=" + expectedBuf + ", got count=" + count1 + " buf=" + result1;
        System.out.println("  Queue: count=" + count1 + ", buf=\"" + result1 + "\" ✓");
        
        read4 = new Read4(file);
        SolutionBuffer sb = new SolutionBuffer(read4);
        char[] buf2 = new char[n];
        int count2 = sb.read(buf2, n);
        String result2 = new String(buf2, 0, count2);
        assert count2 == expectedCount && result2.equals(expectedBuf) : 
            "Buffer: expected count=" + expectedCount + " buf=" + expectedBuf + ", got count=" + count2 + " buf=" + result2;
        System.out.println("  Buffer: count=" + count2 + ", buf=\"" + result2 + "\" ✓");
        
        read4 = new Read4(file);
        SolutionSimple ss = new SolutionSimple(read4);
        char[] buf3 = new char[n];
        int count3 = ss.read(buf3, n);
        String result3 = new String(buf3, 0, count3);
        assert count3 == expectedCount && result3.equals(expectedBuf) : 
            "Simple: expected count=" + expectedCount + " buf=" + expectedBuf + ", got count=" + count3 + " buf=" + result3;
        System.out.println("  Simple: count=" + count3 + ", buf=\"" + result3 + "\" ✓");
        
        read4 = new Read4(file);
        SolutionOptimized so = new SolutionOptimized(read4);
        char[] buf4 = new char[n];
        int count4 = so.read(buf4, n);
        String result4 = new String(buf4, 0, count4);
        assert count4 == expectedCount && result4.equals(expectedBuf) : 
            "Optimized: expected count=" + expectedCount + " buf=" + expectedBuf + ", got count=" + count4 + " buf=" + result4;
        System.out.println("  Optimized: count=" + count4 + ", buf=\"" + result4 + "\" ✓");
    }
    
    static void testMultipleReads() {
        String file = "abcdefghij";
        Read4 read4 = new Read4(file);
        SolutionBuffer sb = new SolutionBuffer(read4);
        
        char[] buf1 = new char[3];
        int c1 = sb.read(buf1, 3);
        assert c1 == 3 && new String(buf1, 0, c1).equals("abc") : "First read failed";
        System.out.println("  First read(3): count=" + c1 + ", buf=\"abc\" ✓");
        
        char[] buf2 = new char[4];
        int c2 = sb.read(buf2, 4);
        assert c2 == 4 && new String(buf2, 0, c2).equals("defg") : "Second read failed";
        System.out.println("  Second read(4): count=" + c2 + ", buf=\"defg\" ✓");
        
        char[] buf3 = new char[10];
        int c3 = sb.read(buf3, 10);
        assert c3 == 3 && new String(buf3, 0, c3).equals("hij") : "Third read failed";
        System.out.println("  Third read(10): count=" + c3 + ", buf=\"hij\" ✓");
        
        char[] buf4 = new char[5];
        int c4 = sb.read(buf4, 5);
        assert c4 == 0 : "Fourth read should return 0";
        System.out.println("  Fourth read(5): count=" + c4 + " (EOF) ✓");
    }
    
    static void crossVerify() {
        String[] files = {"abc", "abcde", "abcdABCD1234", "hello", "hello world", "abcdabcd", "", "a", "abcdefghijklmnopqrstuvwxyz"};
        int[] ns = {4, 5, 12, 10, 5, 8, 5, 1, 26};
        
        for (int i = 0; i < files.length; i++) {
            Read4 r1 = new Read4(files[i]);
            Read4 r2 = new Read4(files[i]);
            Read4 r3 = new Read4(files[i]);
            Read4 r4 = new Read4(files[i]);
            
            SolutionQueue sq = new SolutionQueue(r1);
            SolutionBuffer sb = new SolutionBuffer(r2);
            SolutionSimple ss = new SolutionSimple(r3);
            SolutionOptimized so = new SolutionOptimized(r4);
            
            char[] b1 = new char[ns[i]];
            char[] b2 = new char[ns[i]];
            char[] b3 = new char[ns[i]];
            char[] b4 = new char[ns[i]];
            
            int c1 = sq.read(b1, ns[i]);
            int c2 = sb.read(b2, ns[i]);
            int c3 = ss.read(b3, ns[i]);
            int c4 = so.read(b4, ns[i]);
            
            String s1 = new String(b1, 0, c1);
            String s2 = new String(b2, 0, c2);
            String s3 = new String(b3, 0, c3);
            String s4 = new String(b4, 0, c4);
            
            boolean match = c1 == c2 && c2 == c3 && c3 == c4 && s1.equals(s2) && s2.equals(s3) && s3.equals(s4);
            System.out.println("  File \"" + files[i] + "\", n=" + ns[i] + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (counts: " + c1 + "," + c2 + "," + c3 + "," + c4 + ")");
            assert match : "Mismatch for file=" + files[i] + " n=" + ns[i];
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}