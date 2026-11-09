/**
 * LeetCode 169 - Majority Element
 * 
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 * 
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 * 
 * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 * 
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 * 
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 */
public class Solution {
    
    /**
     * Approach 1: Boyer-Moore Voting Algorithm - O(n) time, O(1) space
     * 
     * The majority element appears more than n/2 times, so it will survive
     * the cancellation process where we pair different elements.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        return candidate;
    }
    
    /**
     * Approach 2: HashMap Counting - O(n) time, O(n) space
     * 
     * Count frequency of each element using HashMap.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int majorityElementHashMap(int[] nums) {
        java.util.Map<Integer, Integer> count = new java.util.HashMap<>();
        int n = nums.length;
        
        for (int num : nums) {
            int c = count.getOrDefault(num, 0) + 1;
            if (c > n / 2) return num;
            count.put(num, c);
        }
        
        return -1; // Should not reach here
    }
    
    /**
     * Approach 3: Sorting - O(n log n) time, O(1) or O(log n) space
     * 
     * After sorting, the majority element must be at index n/2.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) or O(log n) depending on sort
     */
    public int majorityElementSort(int[] nums) {
        java.util.Arrays.sort(nums);
        return nums[nums.length / 2];
    }
    
    /**
     * Approach 4: Randomization - O(n) expected time, O(1) space
     * 
     * Randomly pick an element and verify if it's the majority.
     * Since majority > n/2, probability of picking it is > 1/2.
     * 
     * Time Complexity: O(n) expected
     * Space Complexity: O(1)
     */
    public int majorityElementRandom(int[] nums) {
        java.util.Random rand = new java.util.Random();
        int n = nums.length;
        
        while (true) {
            int candidate = nums[rand.nextInt(n)];
            int count = 0;
            for (int num : nums) {
                if (num == candidate) count++;
            }
            if (count > n / 2) return candidate;
        }
    }
    
    /**
     * Approach 5: Divide and Conquer - O(n log n) time, O(log n) space
     * 
     * Recursively find majority in left and right halves, then combine.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n) recursion stack
     */
    public int majorityElementDivideConquer(int[] nums) {
        return majorityElementRec(nums, 0, nums.length - 1);
    }
    
    private int majorityElementRec(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        
        int mid = left + (right - left) / 2;
        int leftMaj = majorityElementRec(nums, left, mid);
        int rightMaj = majorityElementRec(nums, mid + 1, right);
        
        if (leftMaj == rightMaj) return leftMaj;
        
        int leftCount = countInRange(nums, leftMaj, left, right);
        int rightCount = countInRange(nums, rightMaj, left, right);
        
        return leftCount > rightCount ? leftMaj : rightMaj;
    }
    
    private int countInRange(int[] nums, int target, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (nums[i] == target) count++;
        }
        return count;
    }
    
    /**
     * Approach 6: Bit Manipulation - O(n) time, O(1) space
     * 
     * Count bits at each position across all numbers.
     * The majority element's bits will dominate.
     * 
     * Time Complexity: O(32 * n) = O(n)
     * Space Complexity: O(1)
     */
    public int majorityElementBit(int[] nums) {
        int[] bitCounts = new int[32];
        
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                if ((num & (1 << i)) != 0) {
                    bitCounts[i]++;
                }
            }
        }
        
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if (bitCounts[i] > nums.length / 2) {
                result |= (1 << i);
            }
        }
        
        return result;
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Majority Element Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: nums=[3,2,3] -> Expected: 3");
        testCase(sol, new int[]{3, 2, 3}, 3);
        
        // Test 2: Example 2
        System.out.println("\nTest 2: nums=[2,2,1,1,1,2,2] -> Expected: 2");
        testCase(sol, new int[]{2, 2, 1, 1, 1, 2, 2}, 2);
        
        // Test 3: Single element
        System.out.println("\nTest 3: nums=[1] -> Expected: 1");
        testCase(sol, new int[]{1}, 1);
        
        // Test 4: Two elements same
        System.out.println("\nTest 4: nums=[5,5] -> Expected: 5");
        testCase(sol, new int[]{5, 5}, 5);
        
        // Test 5: Negative numbers
        System.out.println("\nTest 5: nums=[-1,-1,2] -> Expected: -1");
        testCase(sol, new int[]{-1, -1, 2}, -1);
        
        // Test 6: Large array
        System.out.println("\nTest 6: Large array with majority");
        int[] large = new int[10001];
        for (int i = 0; i < 5001; i++) large[i] = 42;
        for (int i = 5001; i < 10001; i++) large[i] = i;
        testCase(sol, large, 42);
        
        // Test 7: All same
        System.out.println("\nTest 7: nums=[7,7,7,7,7] -> Expected: 7");
        testCase(sol, new int[]{7, 7, 7, 7, 7}, 7);
        
        // Test 8: Majority at beginning
        System.out.println("\nTest 8: nums=[1,1,1,2,3] -> Expected: 1");
        testCase(sol, new int[]{1, 1, 1, 2, 3}, 1);
        
        // Test 9: Majority at end
        System.out.println("\nTest 9: nums=[2,3,1,1,1] -> Expected: 1");
        testCase(sol, new int[]{2, 3, 1, 1, 1}, 1);
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int[] nums, int expected) {
        int r1 = sol.majorityElement(nums);
        int r2 = sol.majorityElementHashMap(nums);
        int r3 = sol.majorityElementSort(nums);
        int r4 = sol.majorityElementRandom(nums);
        int r5 = sol.majorityElementDivideConquer(nums);
        int r6 = sol.majorityElementBit(nums);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && 
                        r4 == expected && r5 == expected && r6 == expected);
        
        System.out.println("  Boyer-Moore: " + r1 + (r1 == expected ? " ✓" : " ✗"));
        System.out.println("  HashMap:     " + r2 + (r2 == expected ? " ✓" : " ✗"));
        System.out.println("  Sort:        " + r3 + (r3 == expected ? " ✓" : " ✗"));
        System.out.println("  Random:      " + r4 + (r4 == expected ? " ✓" : " ✗"));
        System.out.println("  DivideConq:  " + r5 + (r5 == expected ? " ✓" : " ✗"));
        System.out.println("  Bit:         " + r6 + (r6 == expected ? " ✓" : " ✗"));
        
        assert match : "Mismatch for nums=" + java.util.Arrays.toString(nums) + 
            ": expected " + expected + ", got " + r1 + ", " + r2 + ", " + r3 + 
            ", " + r4 + ", " + r5 + ", " + r6;
    }
    
    static void crossVerify(Solution sol) {
        int[][] testCases = {
            {3, 2, 3},
            {2, 2, 1, 1, 1, 2, 2},
            {1},
            {5, 5},
            {-1, -1, 2},
            {7, 7, 7, 7, 7},
            {1, 1, 1, 2, 3},
            {2, 3, 1, 1, 1},
            {1, 2, 3, 4, 5, 5, 5, 5, 5},
            {0, 0, 0, 1, 2},
            {-5, -5, -5, 10, 20},
            {100, 100, 100, 100, 1, 2, 3},
        };
        
        for (int[] nums : testCases) {
            int r1 = sol.majorityElement(nums);
            int r2 = sol.majorityElementHashMap(nums);
            int r3 = sol.majorityElementSort(nums);
            int r4 = sol.majorityElementRandom(nums);
            int r5 = sol.majorityElementDivideConquer(nums);
            int r6 = sol.majorityElementBit(nums);
            
            boolean match = (r1 == r2 && r2 == r3 && r3 == r4 && r4 == r5 && r5 == r6);
            System.out.println("  " + java.util.Arrays.toString(nums) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + "," + r5 + "," + r6 + ")");
            assert match : "Cross-verification failed for " + java.util.Arrays.toString(nums);
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}