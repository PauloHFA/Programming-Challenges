public class Solution {
        public boolean search(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
        
            while (left <= right) {
                int mid = left + (right - left) / 2;
            
                if (nums[mid] == target) {
                    return true;
                }
            
                // If left, mid, and right are equal, we can't determine which side is sorted
                // Shrink the search space
                if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                    left++;
                    right--;
                }
                // Left side is sorted
                else if (nums[left] <= nums[mid]) {
                    if (nums[left] <= target && target < nums[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                // Right side is sorted
                else {
                    if (nums[mid] < target && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        
            return false;
        }

        // Alternative: Linear search (for verification)
        public boolean searchLinear(int[] nums, int target) {
            for (int num : nums) {
                if (num == target) return true;
            }
            return false;
        }

        public static void main(String[] args) {
            Solution solver = new Solution();
        
            // Test cases from LeetCode
            int[][] testCases = {
                {2, 5, 6, 0, 0, 1, 2},
                {2, 5, 6, 0, 0, 1, 2},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
                {3, 1},
                {1, 3},
                {3, 5, 1},
                {1, 1, 3, 1},
                {1, 3, 1, 1, 1},
            };
        
            int[] targets = {0, 3, 0, 2, 0, 1, 3, 3, 3, 3};
            boolean[] expected = {true, false, true, true, false, true, true, true, true, true};
        
            System.out.println("=== Search in Rotated Sorted Array II Tests ===");
            for (int i = 0; i < testCases.length; i++) {
                int[] nums = testCases[i];
                int target = targets[i];
                boolean exp = expected[i];
            
                boolean result1 = solver.search(nums, target);
                boolean result2 = solver.searchLinear(nums, target);
            
                boolean match1 = result1 == exp;
                boolean match2 = result2 == exp;
            
                System.out.println("Test " + (i + 1) + ": nums = " + java.util.Arrays.toString(nums) + ", target = " + target);
                System.out.println("  Expected: " + exp);
                System.out.println("  Binary:   " + result1 + (match1 ? " ✓" : " ✗"));
                System.out.println("  Linear:   " + result2 + (match2 ? " ✓" : " ✗"));
                System.out.println("  All match: " + (match1 && match2 ? "✓" : "✗"));
                System.out.println();
            }
        }
}