import java.util.Arrays;

public class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        
        // Two pointers: slow points to position to place next valid element
        // fast scans through the array
        int slow = 2;
        
        for (int fast = 2; fast < nums.length; fast++) {
            // Allow at most 2 duplicates: check if current element is different from element at slow-2
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }

    // Alternative: Generalized for k duplicates
    public int removeDuplicatesK(int[] nums, int k) {
        if (nums.length <= k) {
            return nums.length;
        }
        
        int slow = k;
        for (int fast = k; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - k]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }

    // Alternative: Using count variable
    public int removeDuplicatesCount(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        
        int slow = 1;
        int count = 1;
        
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] == nums[fast - 1]) {
                count++;
            } else {
                count = 1;
            }
            
            if (count <= 2) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }

    // Helper to verify result
    private boolean verifyResult(int[] nums, int length) {
        // Check first 'length' elements have at most 2 duplicates
        for (int i = 0; i < length - 2; i++) {
            if (nums[i] == nums[i + 1] && nums[i] == nums[i + 2]) {
                return false;
            }
        }
        // Check array is still sorted
        for (int i = 1; i < length; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {1, 1, 1, 2, 2, 3},
            {0, 0, 1, 1, 1, 1, 2, 3, 3},
            {1, 1, 1, 1, 1},
            {1, 2, 3, 4, 5},
            {1, 1},
            {1},
            {},
            {1, 1, 1, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 0, 0},
            {-1, -1, -1, 0, 0, 1, 1, 1},
        };
        
        int[] expectedLengths = {5, 7, 2, 5, 2, 1, 0, 6, 2, 6};
        int[][] expectedArrays = {
            {1, 1, 2, 2, 3},
            {0, 0, 1, 1, 2, 3, 3},
            {1, 1},
            {1, 2, 3, 4, 5},
            {1, 1},
            {1},
            {},
            {1, 1, 2, 2, 3, 3},
            {0, 0},
            {-1, -1, 0, 0, 1, 1},
        };
        
        System.out.println("=== Remove Duplicates II Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i].clone();
            int expectedLen = expectedLengths[i];
            int[] expectedArr = expectedArrays[i];
            
            int[] copy1 = input.clone();
            int[] copy2 = input.clone();
            int[] copy3 = input.clone();
            
            int len1 = solver.removeDuplicates(copy1);
            int len2 = solver.removeDuplicatesK(copy2, 2);
            int len3 = solver.removeDuplicatesCount(copy3);
            
            boolean match1 = len1 == expectedLen && Arrays.equals(Arrays.copyOf(copy1, len1), expectedArr);
            boolean match2 = len2 == expectedLen && Arrays.equals(Arrays.copyOf(copy2, len2), expectedArr);
            boolean match3 = len3 == expectedLen && Arrays.equals(Arrays.copyOf(copy3, len3), expectedArr);
            
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(input));
            System.out.println("  Expected len: " + expectedLen + ", arr: " + Arrays.toString(expectedArr));
            System.out.println("  Method 1: len=" + len1 + ", arr=" + Arrays.toString(Arrays.copyOf(copy1, len1)) + (match1 ? " ✓" : " ✗"));
            System.out.println("  Method 2: len=" + len2 + ", arr=" + Arrays.toString(Arrays.copyOf(copy2, len2)) + (match2 ? " ✓" : " ✗"));
            System.out.println("  Method 3: len=" + len3 + ", arr=" + Arrays.toString(Arrays.copyOf(copy3, len3)) + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
        
        // Test generalized k=3
        System.out.println("=== Generalized k=3 Test ===");
        int[] testK3 = {1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        int[] copyK3 = testK3.clone();
        int lenK3 = solver.removeDuplicatesK(copyK3, 3);
        System.out.println("Input: " + Arrays.toString(testK3));
        System.out.println("k=3 result: len=" + lenK3 + ", arr=" + Arrays.toString(Arrays.copyOf(copyK3, lenK3)));
                System.out.println("Valid (at most 3): " + solver.verifyResultK(copyK3, lenK3, 3));
            }
    
            // Helper to verify result for k duplicates
                private boolean verifyResultK(int[] nums, int length, int k) {
                    for (int i = 0; i < length - k; i++) {
                        boolean allEqual = true;
                        for (int j = 1; j <= k; j++) {
                            if (nums[i] != nums[i + j]) {
                                allEqual = false;
                                break;
                            }
                        }
                        if (allEqual) return false;
                    }
                    for (int i = 1; i < length; i++) {
                        if (nums[i] < nums[i - 1]) return false;
                    }
                    return true;
                }
            }