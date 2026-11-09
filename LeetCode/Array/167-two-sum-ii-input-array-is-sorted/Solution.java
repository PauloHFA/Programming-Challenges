/**
 * LeetCode 167 - Two Sum II - Input Array Is Sorted
 * 
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order,
 * find two numbers such that they add up to a specific target number.
 * Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.
 * 
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.
 * 
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 * 
 * Your solution must use only constant extra space.
 * 
 * Example 1:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * 
 * Example 2:
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore, index1 = 1, index2 = 3. We return [1, 3].
 * 
 * Example 3:
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * 
 * Constraints:
 * 2 <= numbers.length <= 3 * 10^4
 * -1000 <= numbers[i] <= 1000
 * numbers is sorted in non-decreasing order.
 * -1000 <= target <= 1000
 * The tests are generated such that there is exactly one solution.
 */
public class Solution {
    
    /**
     * Approach 1: Two Pointers - O(n) time, O(1) space
     * 
     * Since array is sorted, use two pointers at start and end.
     * If sum > target, move right pointer left.
     * If sum < target, move left pointer right.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1}; // Should not reach here per constraints
    }
    
    /**
     * Approach 2: Binary Search for Each Element - O(n log n) time, O(1) space
     * 
     * For each element, binary search for target - element in the remaining array.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     */
    public int[] twoSumBinarySearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            int left = i + 1;
            int right = numbers.length - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (numbers[mid] == complement) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < complement) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 3: HashMap (for verification) - O(n) time, O(n) space
     * 
     * Standard two-sum with HashMap. Not O(1) space but useful for verification.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int[] twoSumHashMap(int[] numbers, int target) {
        java.util.Map<Integer, Integer> map = new java.util.HashMap<>();
        
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement) + 1, i + 1};
            }
            map.put(numbers[i], i);
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 4: Two Pointers with Duplicate Handling - O(n) time, O(1) space
     * 
     * Enhanced two pointers that skips duplicates for optimization.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int[] twoSumOptimized(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                // Skip duplicates
                int current = numbers[left];
                while (left < right && numbers[left] == current) left++;
            } else {
                // Skip duplicates
                int current = numbers[right];
                while (left < right && numbers[right] == current) right--;
            }
        }
        
        return new int[]{-1, -1};
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Two Sum II Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: numbers=[2,7,11,15], target=9 -> Expected: [1,2]");
        testCase(sol, new int[]{2, 7, 11, 15}, 9, new int[]{1, 2});
        
        // Test 2: Example 2
        System.out.println("\nTest 2: numbers=[2,3,4], target=6 -> Expected: [1,3]");
        testCase(sol, new int[]{2, 3, 4}, 6, new int[]{1, 3});
        
        // Test 3: Example 3
        System.out.println("\nTest 3: numbers=[-1,0], target=-1 -> Expected: [1,2]");
        testCase(sol, new int[]{-1, 0}, -1, new int[]{1, 2});
        
        // Test 4: Negative numbers
        System.out.println("\nTest 4: numbers=[-5,-3,-1,0,2,4], target=-4 -> Expected: [2,3] (-3 + -1)");
        testCase(sol, new int[]{-5, -3, -1, 0, 2, 4}, -4, new int[]{2, 3});
        
        // Test 5: Large array
        System.out.println("\nTest 5: Large array with target at ends");
        int[] large = new int[1000];
        for (int i = 0; i < 1000; i++) large[i] = i * 2;
        testCase(sol, large, 1998, new int[]{1, 1000}); // 0 + 1998
        
        // Test 6: Duplicates in array
        System.out.println("\nTest 6: numbers=[1,2,2,3,4], target=4 -> Expected: [1,4] or [2,3]");
        testCase(sol, new int[]{1, 2, 2, 3, 4}, 4, new int[]{1, 4}); // 1+3=4
        
        // Test 7: Target is sum of middle elements
        System.out.println("\nTest 7: numbers=[1,2,3,4,5,6,7,8,9,10], target=11 -> Expected: [1,10]");
        testCase(sol, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 11, new int[]{1, 10});
        
        // Test 8: Cross verification
        System.out.println("\nTest 8: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, int[] numbers, int target, int[] expected) {
        int[] r1 = sol.twoSum(numbers, target);
        int[] r2 = sol.twoSumBinarySearch(numbers, target);
        int[] r3 = sol.twoSumHashMap(numbers, target);
        int[] r4 = sol.twoSumOptimized(numbers, target);
        
        boolean match = arraysEqual(r1, expected) && arraysEqual(r2, expected) && 
                       arraysEqual(r3, expected) && arraysEqual(r4, expected);
        
        System.out.println("  TwoPointers: " + java.util.Arrays.toString(r1) + (arraysEqual(r1, expected) ? " ✓" : " ✗"));
        System.out.println("  BinarySearch:" + java.util.Arrays.toString(r2) + (arraysEqual(r2, expected) ? " ✓" : " ✗"));
        System.out.println("  HashMap:     " + java.util.Arrays.toString(r3) + (arraysEqual(r3, expected) ? " ✓" : " ✗"));
        System.out.println("  Optimized:   " + java.util.Arrays.toString(r4) + (arraysEqual(r4, expected) ? " ✓" : " ✗"));
        
        assert match : "Mismatch for numbers=" + java.util.Arrays.toString(numbers) + 
            ", target=" + target + ": expected " + java.util.Arrays.toString(expected) + 
            ", got " + java.util.Arrays.toString(r1) + ", " + java.util.Arrays.toString(r2) + 
            ", " + java.util.Arrays.toString(r3) + ", " + java.util.Arrays.toString(r4);
    }
    
    static boolean arraysEqual(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
    
    static void crossVerify(Solution sol) {
        Object[][] testCases = {
                {new int[]{2, 7, 11, 15}, 9},
                {new int[]{2, 3, 4}, 6},
                {new int[]{-1, 0}, -1},
                {new int[]{-5, -3, -1, 0, 2, 4}, -4},
                {new int[]{1, 2, 2, 3, 4}, 4},
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 11},
                {new int[]{0, 0, 3, 4}, 0},
                {new int[]{-3, -2, -1, 0, 1, 2, 3}, 0},
                {new int[]{5, 25, 75}, 100},
                {new int[]{1, 3, 5, 7, 9}, 12},
        };
        
        for (Object[] tc : testCases) {
            int[] numbers = (int[])tc[0];
            int target = (int)tc[1];
            
                int[] r1 = sol.twoSum(numbers, target);
                int[] r2 = sol.twoSumBinarySearch(numbers, target);
                int[] r3 = sol.twoSumHashMap(numbers, target);
                int[] r4 = sol.twoSumOptimized(numbers, target);
            
                // Verify all results are valid (sum to target, indices in range, 1-indexed)
                boolean v1 = isValidResult(numbers, target, r1);
                boolean v2 = isValidResult(numbers, target, r2);
                boolean v3 = isValidResult(numbers, target, r3);
                boolean v4 = isValidResult(numbers, target, r4);
            
                boolean match = v1 && v2 && v3 && v4;
                System.out.println("  " + java.util.Arrays.toString(numbers) + ", target=" + target + ": " + 
                    (match ? "MATCH" : "MISMATCH") + " (r1=" + java.util.Arrays.toString(r1) + 
                    ", r2=" + java.util.Arrays.toString(r2) + 
                    ", r3=" + java.util.Arrays.toString(r3) + 
                    ", r4=" + java.util.Arrays.toString(r4) + ")");
                assert match : "Cross-verification failed for " + java.util.Arrays.toString(numbers);
            }
            System.out.println("  All cross-verifications passed ✓");
        }
    
        static boolean isValidResult(int[] numbers, int target, int[] result) {
            if (result.length != 2) return false;
            if (result[0] < 1 || result[0] > numbers.length) return false;
            if (result[1] < 1 || result[1] > numbers.length) return false;
            if (result[0] >= result[1]) return false; // index1 < index2
            return numbers[result[0] - 1] + numbers[result[1] - 1] == target;
        }
}