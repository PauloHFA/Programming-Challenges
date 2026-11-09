/**
 * LeetCode 164 - Maximum Gap
 * 
 * Given an integer array nums, return the maximum difference between two successive elements in its sorted form.
 * If the array contains less than two elements, return 0.
 * 
 * You must write an algorithm that runs in linear time and uses linear extra space.
 * 
 * Example 1:
 * Input: nums = [3,6,9,1]
 * Output: 3
 * Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
 * 
 * Example 2:
 * Input: nums = [10]
 * Output: 0
 * Explanation: The array contains less than 2 elements, therefore return 0.
 * 
 * Constraints:
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
public class Solution {
    
    /**
     * Approach 1: Bucket Sort (Pigeonhole Principle) - O(n) time, O(n) space
     * 
     * Use bucket sort based on the pigeonhole principle. The maximum gap must be at least
     * (max - min) / (n - 1). Create n-1 buckets, each of size bucketSize = ceil((max-min)/(n-1)).
     * The maximum gap will be between buckets, not within buckets.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int maximumGap(int[] nums) {
        if (nums.length < 2) return 0;
        
        int minVal = nums[0];
        int maxVal = nums[0];
        for (int num : nums) {
            minVal = Math.min(minVal, num);
            maxVal = Math.max(maxVal, num);
        }
        
        if (minVal == maxVal) return 0;
        
        int n = nums.length;
        int bucketSize = Math.max(1, (maxVal - minVal) / (n - 1));
        int bucketCount = (maxVal - minVal) / bucketSize + 1;
        
        int[] bucketMin = new int[bucketCount];
        int[] bucketMax = new int[bucketCount];
        boolean[] bucketUsed = new boolean[bucketCount];
        
        java.util.Arrays.fill(bucketMin, Integer.MAX_VALUE);
        java.util.Arrays.fill(bucketMax, Integer.MIN_VALUE);
        
        for (int num : nums) {
            int idx = (num - minVal) / bucketSize;
            bucketMin[idx] = Math.min(bucketMin[idx], num);
            bucketMax[idx] = Math.max(bucketMax[idx], num);
            bucketUsed[idx] = true;
        }
        
        int maxGap = 0;
        int prevMax = minVal;
        
        for (int i = 0; i < bucketCount; i++) {
            if (!bucketUsed[i]) continue;
            maxGap = Math.max(maxGap, bucketMin[i] - prevMax);
            prevMax = bucketMax[i];
        }
        
        return maxGap;
    }
    
    /**
     * Approach 2: Radix Sort - O(n) time, O(n) space
     * 
     * Use LSD (Least Significant Digit) radix sort for integers.
     * Then find max gap in sorted array.
     * 
     * Time Complexity: O(n * k) where k is number of digits (constant for 32-bit int)
     * Space Complexity: O(n)
     */
    public int maximumGapRadix(int[] nums) {
        if (nums.length < 2) return 0;
        
        int[] sorted = radixSort(nums);
        
        int maxGap = 0;
        for (int i = 1; i < sorted.length; i++) {
            maxGap = Math.max(maxGap, sorted[i] - sorted[i - 1]);
        }
        
        return maxGap;
    }
    
    private int[] radixSort(int[] nums) {
        int[] output = new int[nums.length];
        int[] arr = nums.clone();
        
        // Find max to know number of digits
        int max = arr[0];
        for (int num : arr) max = Math.max(max, num);
        
        // LSD Radix Sort
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, output, exp);
            int[] temp = arr;
            arr = output;
            output = temp;
        }
        
        return arr;
    }
    
    private void countingSortByDigit(int[] arr, int[] output, int exp) {
        int[] count = new int[10];
        
        for (int num : arr) {
            count[(num / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
    }
    
    /**
     * Approach 3: Built-in Sort (for verification) - O(n log n) time, O(1) space
     * 
     * Use Arrays.sort for verification.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) or O(log n) depending on sort implementation
     */
    public int maximumGapSort(int[] nums) {
        if (nums.length < 2) return 0;
        
        int[] sorted = nums.clone();
        java.util.Arrays.sort(sorted);
        
        int maxGap = 0;
        for (int i = 1; i < sorted.length; i++) {
            maxGap = Math.max(maxGap, sorted[i] - sorted[i - 1]);
        }
        
        return maxGap;
    }
    
    /**
     * Approach 4: TreeSet (for verification) - O(n log n) time, O(n) space
     * 
     * Use TreeSet to maintain sorted order.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public int maximumGapTreeSet(int[] nums) {
        if (nums.length < 2) return 0;
        
        java.util.TreeSet<Integer> set = new java.util.TreeSet<>();
        for (int num : nums) set.add(num);
        
        int maxGap = 0;
        Integer prev = null;
        for (Integer curr : set) {
            if (prev != null) {
                maxGap = Math.max(maxGap, curr - prev);
            }
            prev = curr;
        }
        
        return maxGap;
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Maximum Gap Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: nums=[3,6,9,1] -> Expected: 3");
        testCase(sol, new int[]{3, 6, 9, 1}, 3);
        
        // Test 2: Example 2
        System.out.println("\nTest 2: nums=[10] -> Expected: 0");
        testCase(sol, new int[]{10}, 0);
        
        // Test 3: Two elements
        System.out.println("\nTest 3: nums=[1,100] -> Expected: 99");
        testCase(sol, new int[]{1, 100}, 99);
        
        // Test 4: All same
        System.out.println("\nTest 4: nums=[5,5,5,5] -> Expected: 0");
        testCase(sol, new int[]{5, 5, 5, 5}, 0);
        
        // Test 5: Already sorted
        System.out.println("\nTest 5: nums=[1,2,3,4,5] -> Expected: 1");
        testCase(sol, new int[]{1, 2, 3, 4, 5}, 1);
        
        // Test 6: Reverse sorted
        System.out.println("\nTest 6: nums=[5,4,3,2,1] -> Expected: 1");
        testCase(sol, new int[]{5, 4, 3, 2, 1}, 1);
        
        // Test 7: Large gap in middle
                System.out.println("\nTest 7: nums=[1,10,20,30] -> Expected: 10 (gaps: 9,10,10)");
                testCase(sol, new int[]{1, 10, 20, 30}, 10);
        
        // Test 8: Multiple large gaps
        System.out.println("\nTest 8: nums=[1,3,100,102,200] -> Expected: 98");
        testCase(sol, new int[]{1, 3, 100, 102, 200}, 98);
        
        // Test 9: Large numbers
        System.out.println("\nTest 9: nums=[1000000000, 0, 500000000] -> Expected: 500000000");
        testCase(sol, new int[]{1000000000, 0, 500000000}, 500000000);
        
        // Test 10: Random array
        System.out.println("\nTest 10: Random array");
        int[] random = new int[1000];
        java.util.Random rand = new java.util.Random(42);
        for (int i = 0; i < random.length; i++) {
            random[i] = rand.nextInt(1000000);
        }
        testCase(sol, random, -1); // -1 means just verify consistency
        
        // Test 11: Cross verification
        System.out.println("\nTest 11: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int[] nums, int expected) {
        int r1 = sol.maximumGap(nums);
        int r2 = sol.maximumGapRadix(nums);
        int r3 = sol.maximumGapSort(nums);
        int r4 = sol.maximumGapTreeSet(nums);
        
        boolean match;
        if (expected >= 0) {
            match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        } else {
            match = (r1 == r2 && r2 == r3 && r3 == r4);
        }
        
        System.out.println("  Bucket:   " + r1 + (expected >= 0 ? (r1 == expected ? " ✓" : " ✗") : ""));
        System.out.println("  Radix:    " + r2 + (expected >= 0 ? (r2 == expected ? " ✓" : " ✗") : ""));
        System.out.println("  Sort:     " + r3 + (expected >= 0 ? (r3 == expected ? " ✓" : " ✗") : ""));
        System.out.println("  TreeSet:  " + r4 + (expected >= 0 ? (r4 == expected ? " ✓" : " ✗") : ""));
        
        assert match : "Mismatch for nums=" + java.util.Arrays.toString(nums) + 
            ": expected " + expected + ", got " + r1 + ", " + r2 + ", " + r3 + ", " + r4;
    }
    
    static void crossVerify(Solution sol) {
        int[][] testCases = {
            {3, 6, 9, 1},
            {10},
            {1, 100},
            {5, 5, 5, 5},
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {1, 10, 20, 30},
            {1, 3, 100, 102, 200},
            {1000000000, 0, 500000000},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {100, 1, 1000, 10, 10000},
        };
        
        for (int[] nums : testCases) {
            int r1 = sol.maximumGap(nums);
            int r2 = sol.maximumGapRadix(nums);
            int r3 = sol.maximumGapSort(nums);
            int r4 = sol.maximumGapTreeSet(nums);
            
            boolean match = (r1 == r2 && r2 == r3 && r3 == r4);
            System.out.println("  " + java.util.Arrays.toString(nums) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (" + r1 + "," + r2 + "," + r3 + "," + r4 + ")");
            assert match : "Cross-verification failed for " + java.util.Arrays.toString(nums);
        }
        System.out.println("  All cross-verifications passed ✓");
    }
}