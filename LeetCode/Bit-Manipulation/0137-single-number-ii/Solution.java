/**
 * LeetCode 137 - Single Number II
 * 
 * Given an integer array nums where every element appears three times except for one,
 * which appears exactly once. Find the single element and return it.
 * 
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 * 
 * Example 1:
 * Input: nums = [2,2,3,2]
 * Output: 3
 * 
 * Example 2:
 * Input: nums = [0,1,0,1,0,1,99]
 * Output: 99
 * 
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Each element in nums appears exactly three times except for one element which appears once.
 */
public class Solution {
    
    /**
     * Approach 1: Bit Manipulation - Count bits at each position
     * 
     * For each bit position (0-31), count how many numbers have that bit set.
     * Since all numbers appear 3 times except one, the count % 3 gives the bit
     * of the single number at that position.
     * 
     * Time Complexity: O(32 * n) = O(n)
     * Space Complexity: O(1)
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            for (int num : nums) {
                // Check if i-th bit is set
                if ((num & (1 << i)) != 0) {
                    sum++;
                }
            }
            // If sum % 3 != 0, this bit is set in the single number
            if (sum % 3 != 0) {
                result |= (1 << i);
            }
        }
        return result;
    }
    
    /**
     * Approach 2: Bit Manipulation - Finite State Machine (2-bit counter)
     * 
     * Use two variables to represent a 2-bit counter for each bit position:
     * - ones: bits that have appeared 1 time (mod 3)
     * - twos: bits that have appeared 2 times (mod 3)
     * 
     * State transitions for each bit:
     * 00 -> 01 -> 10 -> 00 (for bits appearing 3 times)
     * 00 -> 01 (for the single number's bits)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int singleNumberFSM(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            // Update 'twos' first with bits that have appeared twice
            twos |= ones & num;
            // Update 'ones' with XOR (toggle bits appearing first time)
            ones ^= num;
            // Common bits between ones and twos are bits that appeared 3 times
            int common = ones & twos;
            // Remove bits that appeared 3 times from both
            ones &= ~common;
            twos &= ~common;
        }
        return ones;
    }
    
    /**
     * Approach 3: Hash Map / Frequency Count
     * 
     * Count frequency of each number using a HashMap.
     * Return the number with frequency 1.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int singleNumberHashMap(int[] nums) {
        java.util.Map<Integer, Integer> freq = new java.util.HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        for (java.util.Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return -1; // Should never reach here
    }
    
    /**
     * Approach 4: Mathematical (3 * sum of unique - sum of all) / 2
     * 
     * 3 * (sum of unique elements) - (sum of all elements) = 2 * single element
     * single element = (3 * sumUnique - sumAll) / 2
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the set
     */
    public int singleNumberMath(int[] nums) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        long sumAll = 0;
        long sumUnique = 0;
        for (int num : nums) {
            sumAll += num;
            if (set.add(num)) {
                sumUnique += num;
            }
        }
        return (int)((3 * sumUnique - sumAll) / 2);
    }
    
    /**
     * Approach 5: Sorting
     * 
     * Sort the array and find the element that doesn't appear in a group of 3.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) or O(log n)
     */
    public int singleNumberSort(int[] nums) {
        java.util.Arrays.sort(nums);
        for (int i = 0; i < nums.length; i += 3) {
            if (i == nums.length - 1 || nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Single Number II Tests ===\n");
        
        // Test 1: Basic case
        System.out.println("Test 1: nums = [2,2,3,2]");
        int[] nums1 = {2, 2, 3, 2};
        int expected1 = 3;
        int result1 = sol.singleNumber(nums1);
        System.out.println("  Expected: " + expected1);
        System.out.println("  BitCount: " + result1 + (result1 == expected1 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums1));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums1));
        System.out.println("  Math:     " + sol.singleNumberMath(nums1));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums1));
        
        // Test 2: Multiple triples
        System.out.println("\nTest 2: nums = [0,1,0,1,0,1,99]");
        int[] nums2 = {0, 1, 0, 1, 0, 1, 99};
        int expected2 = 99;
        int result2 = sol.singleNumber(nums2);
        System.out.println("  Expected: " + expected2);
        System.out.println("  BitCount: " + result2 + (result2 == expected2 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums2));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums2));
        System.out.println("  Math:     " + sol.singleNumberMath(nums2));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums2));
        
        // Test 3: Single element
        System.out.println("\nTest 3: nums = [1]");
        int[] nums3 = {1};
        int expected3 = 1;
        int result3 = sol.singleNumber(nums3);
        System.out.println("  Expected: " + expected3);
        System.out.println("  BitCount: " + result3 + (result3 == expected3 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums3));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums3));
        System.out.println("  Math:     " + sol.singleNumberMath(nums3));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums3));
        
        // Test 4: Negative numbers
        System.out.println("\nTest 4: nums = [-2,-2,1,1,-3,1,-3,-3,-4,-2]");
        int[] nums4 = {-2, -2, 1, 1, -3, 1, -3, -3, -4, -2};
        int expected4 = -4;
        int result4 = sol.singleNumber(nums4);
        System.out.println("  Expected: " + expected4);
        System.out.println("  BitCount: " + result4 + (result4 == expected4 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums4));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums4));
        System.out.println("  Math:     " + sol.singleNumberMath(nums4));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums4));
        
        // Test 5: Single at beginning
        System.out.println("\nTest 5: nums = [5,5,5,1,1,1,2,2,2,3]");
        int[] nums5 = {5, 5, 5, 1, 1, 1, 2, 2, 2, 3};
        int expected5 = 3;
        int result5 = sol.singleNumber(nums5);
        System.out.println("  Expected: " + expected5);
        System.out.println("  BitCount: " + result5 + (result5 == expected5 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums5));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums5));
        System.out.println("  Math:     " + sol.singleNumberMath(nums5));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums5));
        
        // Test 6: Single in middle
        System.out.println("\nTest 6: nums = [1,1,1,2,3,3,3]");
        int[] nums6 = {1, 1, 1, 2, 3, 3, 3};
        int expected6 = 2;
        int result6 = sol.singleNumber(nums6);
        System.out.println("  Expected: " + expected6);
        System.out.println("  BitCount: " + result6 + (result6 == expected6 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums6));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums6));
        System.out.println("  Math:     " + sol.singleNumberMath(nums6));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums6));
        
        // Test 7: Large numbers
        System.out.println("\nTest 7: nums = [2147483647,2147483647,2147483647,-2147483648]");
        int[] nums7 = {2147483647, 2147483647, 2147483647, -2147483648};
        int expected7 = -2147483648;
        int result7 = sol.singleNumber(nums7);
        System.out.println("  Expected: " + expected7);
        System.out.println("  BitCount: " + result7 + (result7 == expected7 ? " ✓" : " ✗"));
        System.out.println("  FSM:      " + sol.singleNumberFSM(nums7));
        System.out.println("  HashMap:  " + sol.singleNumberHashMap(nums7));
        System.out.println("  Math:     " + sol.singleNumberMath(nums7));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums7));
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification of all approaches");
        int[][] testCases = {
            {2, 2, 3, 2},
            {0, 1, 0, 1, 0, 1, 99},
            {1},
            {-2, -2, 1, 1, -3, 1, -3, -3, -4, -2},
            {5, 5, 5, 1, 1, 1, 2, 2, 2, 3},
            {1, 1, 1, 2, 3, 3, 3},
            {2147483647, 2147483647, 2147483647, -2147483648},
            {-1, -1, -1, -2},
            {10, 10, 10, 20, 20, 20, 30}
        };
        int[] expected = {3, 99, 1, -4, 3, 2, -2147483648, -2, 30};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.singleNumber(testCases[i]);
            int r2 = sol.singleNumberFSM(testCases[i]);
            int r3 = sol.singleNumberHashMap(testCases[i]);
            int r4 = sol.singleNumberMath(testCases[i]);
            int r5 = sol.singleNumberSort(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i] && r5 == expected[i]);
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (BC:" + r1 + " FSM:" + r2 + " HM:" + r3 + " Math:" + r4 + " Sort:" + r5 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}