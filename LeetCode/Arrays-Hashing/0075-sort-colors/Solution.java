import java.util.Arrays;

public class Solution {
    public void sortColors(int[] nums) {
        // Dutch National Flag algorithm - Three-way partitioning
        int low = 0;      // boundary for 0s (nums[0..low-1] = 0)
        int mid = 0;      // current element (nums[low..mid-1] = 1)
        int high = nums.length - 1; // boundary for 2s (nums[high+1..n-1] = 2)
        
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else { // nums[mid] == 2
                swap(nums, mid, high);
                high--;
            }
        }
    }

    // Alternative: Counting sort (two passes)
    public void sortColorsCounting(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        
        for (int num : nums) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }
        
        int i = 0;
        while (count0-- > 0) nums[i++] = 0;
        while (count1-- > 0) nums[i++] = 1;
        while (count2-- > 0) nums[i++] = 2;
    }

    // Alternative: Two-pass with two pointers
    public void sortColorsTwoPass(int[] nums) {
        // First pass: move all 0s to front
        int zeroPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, zeroPos, i);
                zeroPos++;
            }
        }
        
        // Second pass: move all 1s after 0s
        int onePos = zeroPos;
        for (int i = zeroPos; i < nums.length; i++) {
            if (nums[i] == 1) {
                swap(nums, onePos, i);
                onePos++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Helper to check if sorted correctly
    private boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {2, 0, 2, 1, 1, 0},
            {2, 0, 1},
            {0},
            {1},
            {2},
            {0, 0, 0},
            {1, 1, 1},
            {2, 2, 2},
            {2, 1, 0},
            {0, 1, 2},
            {1, 0, 2, 1, 0, 2},
            {2, 2, 1, 1, 0, 0},
        };
        
        int[][] expected = {
            {0, 0, 1, 1, 2, 2},
            {0, 1, 2},
            {0},
            {1},
            {2},
            {0, 0, 0},
            {1, 1, 1},
            {2, 2, 2},
            {0, 1, 2},
            {0, 1, 2},
            {0, 0, 1, 1, 2, 2},
            {0, 0, 1, 1, 2, 2},
        };
        
        System.out.println("=== Sort Colors Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i].clone();
            int[] exp = expected[i];
            
            int[] copy1 = input.clone();
            int[] copy2 = input.clone();
            int[] copy3 = input.clone();
            
            solver.sortColors(copy1);
            solver.sortColorsCounting(copy2);
            solver.sortColorsTwoPass(copy3);
            
            boolean match1 = Arrays.equals(copy1, exp);
            boolean match2 = Arrays.equals(copy2, exp);
            boolean match3 = Arrays.equals(copy3, exp);
            
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(input));
            System.out.println("  Expected: " + Arrays.toString(exp));
            System.out.println("  Dutch Flag: " + Arrays.toString(copy1) + (match1 ? " ✓" : " ✗"));
            System.out.println("  Counting:   " + Arrays.toString(copy2) + (match2 ? " ✓" : " ✗"));
            System.out.println("  Two Pass:   " + Arrays.toString(copy3) + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Edge cases
        System.out.println("=== Edge Cases ===");
        int[] empty = {};
        solver.sortColors(empty);
        System.out.println("Empty array: " + Arrays.toString(empty));
        
        int[] large = new int[1000];
        for (int i = 0; i < 1000; i++) {
            large[i] = (int) (Math.random() * 3);
        }
        int[] largeCopy = large.clone();
        solver.sortColors(large);
        System.out.println("Large array sorted: " + solver.isSorted(large));
        System.out.println("Counting sort match: " + Arrays.equals(large, largeCopy));
    }
}