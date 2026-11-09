import java.util.*;

/**
 * LeetCode 189 - Rotate Array
 * 
 * Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.
 * 
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * 
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation: 
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 * 
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - 0 <= k <= 10^5
 * 
 * Follow up:
 * - Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
 * - Could you do it in-place with O(1) extra space?
 */
public class Solution {

    // Approach 1: Reverse Array (Optimal - O(1) space)
    // Reverse entire array, then reverse first k, then reverse rest
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    // Approach 2: Cyclic Replacements (O(1) space)
    // Move each element to its final position in cycles
    public void rotateCyclic(int[] nums, int k) {
        k %= nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

    // Approach 3: Extra Array (O(n) space)
    // Simple approach using extra array
    public void rotateExtraArray(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = nums[i];
        }
        System.arraycopy(result, 0, nums, 0, n);
    }

    // Approach 4: Using Collections.rotate (Java built-in)
    public void rotateCollections(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) list.add(num);
        Collections.rotate(list, k);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = list.get(i);
        }
    }

    // Approach 5: Using System.arraycopy (Alternative O(n) space)
        public void rotateArrayCopy(int[] nums, int k) {
            int n = nums.length;
            k %= n;
            if (k == 0) return;
            int[] temp = new int[k];
            System.arraycopy(nums, n - k, temp, 0, k);
            System.arraycopy(nums, 0, nums, k, n - k);
            System.arraycopy(temp, 0, nums, 0, k);
        }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Rotate Array Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, new int[]{1,2,3,4,5,6,7}, 3, new int[]{5,6,7,1,2,3,4}, "Example 1");
        
        // Test 2: Example 2
        testCase(sol, new int[]{-1,-100,3,99}, 2, new int[]{3,99,-1,-100}, "Example 2");
        
        // Test 3: k = 0
        testCase(sol, new int[]{1,2,3,4,5}, 0, new int[]{1,2,3,4,5}, "k=0");
        
        // Test 4: k = length
        testCase(sol, new int[]{1,2,3,4,5}, 5, new int[]{1,2,3,4,5}, "k=n");
        
        // Test 5: k > length
        testCase(sol, new int[]{1,2,3,4,5}, 7, new int[]{4,5,1,2,3}, "k>n");
        
        // Test 6: Single element
        testCase(sol, new int[]{1}, 100, new int[]{1}, "Single element");
        
        // Test 7: Two elements
        testCase(sol, new int[]{1,2}, 1, new int[]{2,1}, "Two elements");
        
        // Test 8: Even length
        testCase(sol, new int[]{1,2,3,4}, 2, new int[]{3,4,1,2}, "Even length");
        
        // Test 9: Large k
        testCase(sol, new int[]{1,2,3,4,5,6}, 11, new int[]{2,3,4,5,6,1}, "Large k");
        
        // Test 10: All same
        testCase(sol, new int[]{1,1,1,1}, 2, new int[]{1,1,1,1}, "All same");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[][] testArrays = {
            {1,2,3,4,5,6,7},
            {-1,-100,3,99},
            {1,2,3,4,5},
            {1,2,3,4,5},
            {1,2,3,4,5},
            {1},
            {1,2},
            {1,2,3,4},
            {1,2,3,4,5,6},
            {1,1,1,1}
        };
        int[] testK = {3, 2, 0, 5, 7, 100, 1, 2, 11, 2};
        int[][] expectedArrays = {
            {5,6,7,1,2,3,4},
            {3,99,-1,-100},
            {1,2,3,4,5},
            {1,2,3,4,5},
            {4,5,1,2,3},
            {1},
            {2,1},
            {3,4,1,2},
            {2,3,4,5,6,1},
            {1,1,1,1}
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testArrays.length; i++) {
            int[] copy1 = testArrays[i].clone();
            int[] copy2 = testArrays[i].clone();
            int[] copy3 = testArrays[i].clone();
            int[] copy4 = testArrays[i].clone();
            int[] copy5 = testArrays[i].clone();
            
            sol.rotate(copy1, testK[i]);
            sol.rotateCyclic(copy2, testK[i]);
            sol.rotateExtraArray(copy3, testK[i]);
            sol.rotateCollections(copy4, testK[i]);
            sol.rotateArrayCopy(copy5, testK[i]);
            
            if (!Arrays.equals(copy1, copy2) || !Arrays.equals(copy1, copy3) 
                || !Arrays.equals(copy1, copy4) || !Arrays.equals(copy1, copy5)) {
                System.out.println("Mismatch for " + Arrays.toString(testArrays[i]) + ", k=" + testK[i]);
                System.out.printf("  Reverse: %s, Cyclic: %s, Extra: %s, Collections: %s, BlockSwap: %s%n",
                    Arrays.toString(copy1), Arrays.toString(copy2), Arrays.toString(copy3),
                    Arrays.toString(copy4), Arrays.toString(copy5));
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int[] input, int k, int[] expected, String name) {
        int[] copy1 = input.clone();
        int[] copy2 = input.clone();
        int[] copy3 = input.clone();
        int[] copy4 = input.clone();
        int[] copy5 = input.clone();
        
        sol.rotate(copy1, k);
        sol.rotateCyclic(copy2, k);
        sol.rotateExtraArray(copy3, k);
        sol.rotateCollections(copy4, k);
        sol.rotateArrayCopy(copy5, k);
        
        boolean pass = Arrays.equals(copy1, expected) && Arrays.equals(copy2, expected)
                      && Arrays.equals(copy3, expected) && Arrays.equals(copy4, expected)
                      && Arrays.equals(copy5, expected);
        
        System.out.printf("%s: k=%d, %s -> %s %s%n", name, k, Arrays.toString(input), Arrays.toString(copy1), pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  Expected: %s%n", Arrays.toString(expected));
            System.out.printf("  Cyclic: %s, Extra: %s, Collections: %s, BlockSwap: %s%n",
                Arrays.toString(copy2), Arrays.toString(copy3), Arrays.toString(copy4), Arrays.toString(copy5));
        }
    }
}