/**
 * LeetCode 163 - Missing Ranges
 * 
 * You are given an inclusive range [lower, upper] and a sorted unique integer array nums,
 * where all elements are in the inclusive range.
 * 
 * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
 * 
 * Return the smallest sorted list of ranges that cover every missing number exactly.
 * That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.
 * 
 * Each range [a,b] in the list should be output as:
 * - "a->b" if a != b
 * - "a" if a == b
 * 
 * Example 1:
 * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
 * Output: ["2","4->49","51->74","76->99"]
 * Explanation: The ranges are:
 * [2,2] -> "2"
 * [4,49] -> "4->49"
 * [51,74] -> "51->74"
 * [76,99] -> "76->99"
 * 
 * Example 2:
 * Input: nums = [-1], lower = -1, upper = -1
 * Output: []
 * Explanation: There are no missing ranges since there are no missing numbers.
 * 
 * Constraints:
 * -10^9 <= lower <= upper <= 10^9
 * 0 <= nums.length <= 100
 * lower <= nums[i] <= upper
 * All the values of nums are unique.
 */
public class Solution {
    
    /**
     * Approach 1: Linear Scan - O(n) time, O(1) space (excluding output)
     * 
     * Track the previous number (starting with lower-1). For each number in nums,
     * if there's a gap > 1, add the missing range. Finally check after the last number.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) excluding output
     */
    public java.util.List<String> findMissingRanges(int[] nums, int lower, int upper) {
        java.util.List<String> result = new java.util.ArrayList<>();
        long prev = (long)lower - 1; // Use long to handle Integer.MIN_VALUE
        
        for (int num : nums) {
            if ((long)num - prev > 1) {
                result.add(formatRange(prev + 1, (long)num - 1));
            }
            prev = num;
        }
        
        // Check after the last element
        if ((long)upper - prev > 0) {
            result.add(formatRange(prev + 1, upper));
        }
        
        return result;
    }
    
    private String formatRange(long start, long end) {
        if (start == end) {
            return String.valueOf(start);
        } else {
            return start + "->" + end;
        }
    }
    
    /**
     * Approach 2: Using Long for Boundaries - O(n) time, O(1) space
     * 
     * Add sentinel values at boundaries to simplify logic.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) excluding output
     */
    public java.util.List<String> findMissingRangesSentinel(int[] nums, int lower, int upper) {
        java.util.List<String> result = new java.util.ArrayList<>();
        
        // Create extended array with sentinels
        long[] extended = new long[nums.length + 2];
        extended[0] = (long)lower - 1;
        for (int i = 0; i < nums.length; i++) {
            extended[i + 1] = nums[i];
        }
        extended[extended.length - 1] = (long)upper + 1;
        
        for (int i = 1; i < extended.length; i++) {
            if (extended[i] - extended[i - 1] > 1) {
                result.add(formatRange(extended[i - 1] + 1, extended[i] - 1));
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Two Pointers - O(n) time, O(1) space
     * 
     * Use two pointers to track current range start and end.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) excluding output
     */
    public java.util.List<String> findMissingRangesTwoPointers(int[] nums, int lower, int upper) {
        java.util.List<String> result = new java.util.ArrayList<>();
            long start = (long)lower; // Use long to handle Integer boundaries
        
        for (int num : nums) {
                long n = (long)num;
                if (n > start) {
                    result.add(formatRange(start, n - 1));
                }
                start = n + 1;
            }
        
            if (start <= upper) {
                result.add(formatRange(start, upper));
            }
        
            return result;
        }
    
    /**
     * Approach 4: Stream-based (Java 8+) - O(n) time, O(1) space
     * 
     * Using streams for more functional style.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) excluding output
     */
    public java.util.List<String> findMissingRangesStream(int[] nums, int lower, int upper) {
        java.util.List<Long> extended = new java.util.ArrayList<>();
        extended.add((long)lower - 1);
        for (int num : nums) extended.add((long)num);
        extended.add((long)upper + 1);
        
        return java.util.stream.IntStream.range(1, extended.size())
            .filter(i -> extended.get(i) - extended.get(i - 1) > 1)
            .mapToObj(i -> formatRange(extended.get(i - 1) + 1, extended.get(i) - 1))
            .collect(java.util.stream.Collectors.toList());
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Missing Ranges Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: nums=[0,1,3,50,75], lower=0, upper=99");
        System.out.println("  Expected: [\"2\",\"4->49\",\"51->74\",\"76->99\"]");
        testCase(sol, new int[]{0, 1, 3, 50, 75}, 0, 99, 
            new String[]{"2", "4->49", "51->74", "76->99"});
        
        // Test 2: Example 2
        System.out.println("\nTest 2: nums=[-1], lower=-1, upper=-1");
        System.out.println("  Expected: []");
        testCase(sol, new int[]{-1}, -1, -1, new String[]{});
        
        // Test 3: Empty nums
        System.out.println("\nTest 3: nums=[], lower=1, upper=1");
        System.out.println("  Expected: [\"1\"]");
        testCase(sol, new int[]{}, 1, 1, new String[]{"1"});
        
        // Test 4: Empty nums, range > 1
        System.out.println("\nTest 4: nums=[], lower=1, upper=5");
        System.out.println("  Expected: [\"1->5\"]");
        testCase(sol, new int[]{}, 1, 5, new String[]{"1->5"});
        
        // Test 5: No missing ranges
        System.out.println("\nTest 5: nums=[1,2,3,4,5], lower=1, upper=5");
        System.out.println("  Expected: []");
        testCase(sol, new int[]{1, 2, 3, 4, 5}, 1, 5, new String[]{});
        
        // Test 6: Missing at beginning
        System.out.println("\nTest 6: nums=[3,4,5], lower=1, upper=5");
        System.out.println("  Expected: [\"1->2\"]");
        testCase(sol, new int[]{3, 4, 5}, 1, 5, new String[]{"1->2"});
        
        // Test 7: Missing at end
        System.out.println("\nTest 7: nums=[1,2,3], lower=1, upper=5");
        System.out.println("  Expected: [\"4->5\"]");
        testCase(sol, new int[]{1, 2, 3}, 1, 5, new String[]{"4->5"});
        
        // Test 8: Single missing in middle
        System.out.println("\nTest 8: nums=[1,3], lower=1, upper=3");
        System.out.println("  Expected: [\"2\"]");
        testCase(sol, new int[]{1, 3}, 1, 3, new String[]{"2"});
        
        // Test 9: Negative numbers
        System.out.println("\nTest 9: nums=[-3,-1], lower=-5, upper=0");
        System.out.println("  Expected: [\"-5->-4\",\"-2\",\"0\"]");
        testCase(sol, new int[]{-3, -1}, -5, 0, new String[]{"-5->-4", "-2", "0"});
        
        // Test 10: Large range
        System.out.println("\nTest 10: nums=[0, 100], lower=0, upper=100");
        System.out.println("  Expected: [\"1->99\"]");
        testCase(sol, new int[]{0, 100}, 0, 100, new String[]{"1->99"});
        
        // Test 11: Integer boundaries
        System.out.println("\nTest 11: nums=[-2147483648, 2147483647], lower=-2147483648, upper=2147483647");
        System.out.println("  Expected: [\"-2147483647->2147483646\"]");
        testCase(sol, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}, Integer.MIN_VALUE, Integer.MAX_VALUE, 
            new String[]{"-2147483647->2147483646"});
        
        // Test 12: Cross verification
        System.out.println("\nTest 12: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int[] nums, int lower, int upper, String[] expected) {
        java.util.List<String> r1 = sol.findMissingRanges(nums, lower, upper);
        java.util.List<String> r2 = sol.findMissingRangesSentinel(nums, lower, upper);
        java.util.List<String> r3 = sol.findMissingRangesTwoPointers(nums, lower, upper);
        java.util.List<String> r4 = sol.findMissingRangesStream(nums, lower, upper);
        
        boolean match = listsEqual(r1, expected) && listsEqual(r2, expected) && 
                       listsEqual(r3, expected) && listsEqual(r4, expected);
        
        System.out.println("  Linear:     " + r1 + (listsEqual(r1, expected) ? " ✓" : " ✗"));
        System.out.println("  Sentinel:   " + r2 + (listsEqual(r2, expected) ? " ✓" : " ✗"));
        System.out.println("  TwoPointers:" + r3 + (listsEqual(r3, expected) ? " ✓" : " ✗"));
        System.out.println("  Stream:     " + r4 + (listsEqual(r4, expected) ? " ✓" : " ✗"));
        
        assert match : "Mismatch for nums=" + java.util.Arrays.toString(nums) + 
            ", lower=" + lower + ", upper=" + upper + 
            ": expected " + java.util.Arrays.toString(expected) + 
            ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static boolean listsEqual(java.util.List<String> list, String[] expected) {
        if (list.size() != expected.length) return false;
        for (int i = 0; i < expected.length; i++) {
            if (!list.get(i).equals(expected[i])) return false;
        }
        return true;
    }
    
    static void crossVerify(Solution sol) {
        Object[][] testCases = {
            {new int[]{0, 1, 3, 50, 75}, 0, 99, new String[]{"2", "4->49", "51->74", "76->99"}},
            {new int[]{-1}, -1, -1, new String[]{}},
            {new int[]{}, 1, 1, new String[]{"1"}},
            {new int[]{}, 1, 5, new String[]{"1->5"}},
            {new int[]{1, 2, 3, 4, 5}, 1, 5, new String[]{}},
            {new int[]{3, 4, 5}, 1, 5, new String[]{"1->2"}},
            {new int[]{1, 2, 3}, 1, 5, new String[]{"4->5"}},
            {new int[]{1, 3}, 1, 3, new String[]{"2"}},
            {new int[]{-3, -1}, -5, 0, new String[]{"-5->-4", "-2", "0"}},
            {new int[]{0, 100}, 0, 100, new String[]{"1->99"}},
            {new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}, Integer.MIN_VALUE, Integer.MAX_VALUE, 
                new String[]{"-2147483647->2147483646"}},
            {new int[]{5}, 0, 10, new String[]{"0->4", "6->10"}},
            {new int[]{0, 5, 10}, 0, 10, new String[]{"1->4", "6->9"}},
        };
        
        for (Object[] tc : testCases) {
            int[] nums = (int[])tc[0];
            int lower = (int)tc[1];
            int upper = (int)tc[2];
            String[] expected = (String[])tc[3];
            
            java.util.List<String> r1 = sol.findMissingRanges(nums, lower, upper);
            java.util.List<String> r2 = sol.findMissingRangesSentinel(nums, lower, upper);
            java.util.List<String> r3 = sol.findMissingRangesTwoPointers(nums, lower, upper);
            java.util.List<String> r4 = sol.findMissingRangesStream(nums, lower, upper);
            
            boolean match = listsEqual(r1, expected) && listsEqual(r2, expected) && 
                           listsEqual(r3, expected) && listsEqual(r4, expected);
            
            System.out.println("  nums=" + java.util.Arrays.toString(nums) + ", [" + lower + "," + upper + "]: " + 
                (match ? "MATCH" : "MISMATCH") + " (" + r1 + ")");
            assert match : "Cross-verification failed for nums=" + java.util.Arrays.toString(nums);
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}