/**
 * LeetCode 136 - Single Number
 * 
 * Given a non-empty array of integers nums, every element appears twice except for one.
 * Find that single one.
 * 
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 * 
 * Example 1:
 * Input: nums = [2,2,1]
 * Output: 1
 * 
 * Example 2:
 * Input: nums = [4,1,2,1,2]
 * Output: 4
 * 
 * Example 3:
 * Input: nums = [1]
 * Output: 1
 * 
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -3 * 10^4 <= nums[i] <= 3 * 10^4
 * Each element in the array appears twice except for one element which appears only once.
 */
public class Solution {
    
    /**
     * Approach 1: Bit Manipulation - XOR
     * 
     * XOR properties:
     * - a ^ a = 0 (any number XOR with itself is 0)
     * - a ^ 0 = a (any number XOR with 0 is the number itself)
     * - a ^ b ^ a = b (XOR is commutative and associative)
     * 
     * Since every element appears twice except one, XORing all elements
     * will cancel out the pairs and leave the single number.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    /**
     * Approach 2: Hash Set
     * 
     * Add elements to a set. If already present, remove it.
     * At the end, the set contains only the single number.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int singleNumberHashSet(int[] nums) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }
        return set.iterator().next();
    }
    
    /**
     * Approach 3: Mathematical (Sum of set * 2 - Sum of array)
     * 
     * 2 * (sum of unique elements) - (sum of all elements) = single element
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the set
     */
    public int singleNumberMath(int[] nums) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        long sumArray = 0;
        long sumSet = 0;
        for (int num : nums) {
            sumArray += num;
            if (set.add(num)) {
                sumSet += num;
            }
        }
        return (int)(2 * sumSet - sumArray);
    }
    
    /**
     * Approach 4: Sorting
     * 
     * Sort the array and find the element that doesn't have a pair.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) or O(log n) depending on sorting algorithm
     */
    public int singleNumberSort(int[] nums) {
        java.util.Arrays.sort(nums);
        for (int i = 0; i < nums.length; i += 2) {
            if (i == nums.length - 1 || nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return -1; // Should never reach here given constraints
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Single Number Tests ===\n");
        
        // Test 1: Basic case
        System.out.println("Test 1: nums = [2,2,1]");
        int[] nums1 = {2, 2, 1};
        int expected1 = 1;
        int result1 = sol.singleNumber(nums1);
        System.out.println("  Expected: " + expected1);
        System.out.println("  XOR:      " + result1 + (result1 == expected1 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums1));
        System.out.println("  Math:     " + sol.singleNumberMath(nums1));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums1));
        
        // Test 2: Multiple pairs
        System.out.println("\nTest 2: nums = [4,1,2,1,2]");
        int[] nums2 = {4, 1, 2, 1, 2};
        int expected2 = 4;
        int result2 = sol.singleNumber(nums2);
        System.out.println("  Expected: " + expected2);
        System.out.println("  XOR:      " + result2 + (result2 == expected2 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums2));
        System.out.println("  Math:     " + sol.singleNumberMath(nums2));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums2));
        
        // Test 3: Single element
        System.out.println("\nTest 3: nums = [1]");
        int[] nums3 = {1};
        int expected3 = 1;
        int result3 = sol.singleNumber(nums3);
        System.out.println("  Expected: " + expected3);
        System.out.println("  XOR:      " + result3 + (result3 == expected3 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums3));
        System.out.println("  Math:     " + sol.singleNumberMath(nums3));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums3));
        
        // Test 4: Negative numbers
        System.out.println("\nTest 4: nums = [-1,-1,-2]");
        int[] nums4 = {-1, -1, -2};
        int expected4 = -2;
        int result4 = sol.singleNumber(nums4);
        System.out.println("  Expected: " + expected4);
        System.out.println("  XOR:      " + result4 + (result4 == expected4 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums4));
        System.out.println("  Math:     " + sol.singleNumberMath(nums4));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums4));
        
        // Test 5: Large array
        System.out.println("\nTest 5: Large array with single at end");
        int[] nums5 = new int[10001];
        for (int i = 0; i < 5000; i++) {
            nums5[i * 2] = i;
            nums5[i * 2 + 1] = i;
        }
        nums5[10000] = 99999;
        int expected5 = 99999;
        int result5 = sol.singleNumber(nums5);
        System.out.println("  Expected: " + expected5);
        System.out.println("  XOR:      " + result5 + (result5 == expected5 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums5));
        System.out.println("  Math:     " + sol.singleNumberMath(nums5));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums5));
        
        // Test 6: Single at beginning
        System.out.println("\nTest 6: nums = [5,1,1,2,2,3,3]");
        int[] nums6 = {5, 1, 1, 2, 2, 3, 3};
        int expected6 = 5;
        int result6 = sol.singleNumber(nums6);
        System.out.println("  Expected: " + expected6);
        System.out.println("  XOR:      " + result6 + (result6 == expected6 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.singleNumberHashSet(nums6));
        System.out.println("  Math:     " + sol.singleNumberMath(nums6));
        System.out.println("  Sort:     " + sol.singleNumberSort(nums6));
        
        // Test 7: Cross verification
        System.out.println("\nTest 7: Cross verification of all approaches");
        int[][] testCases = {
            {2, 2, 1},
            {4, 1, 2, 1, 2},
            {1},
            {-1, -1, -2},
            {5, 1, 1, 2, 2, 3, 3},
            {0, 0, 1},
            {10, 10, 20, 20, 30},
            {-5, -5, -10, -10, -15}
        };
        int[] expected = {1, 4, 1, -2, 5, 1, 30, -15};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.singleNumber(testCases[i]);
            int r2 = sol.singleNumberHashSet(testCases[i]);
            int r3 = sol.singleNumberMath(testCases[i]);
            int r4 = sol.singleNumberSort(testCases[i]);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i] && r4 == expected[i]);
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (XOR:" + r1 + " HS:" + r2 + " Math:" + r3 + " Sort:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}