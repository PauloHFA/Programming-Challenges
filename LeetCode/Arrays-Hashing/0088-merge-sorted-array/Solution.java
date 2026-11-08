import java.util.Arrays;

public class Solution {
    
    // Método principal: Merge from end (Three Pointers)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;      // Pointer for nums1's valid elements
        int j = n - 1;      // Pointer for nums2
        int k = m + n - 1;  // Pointer for merged array end
        
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
    
    // Método 2: Copy nums2 then sort (Simple but O((m+n)log(m+n)))
    public void mergeSort(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
    }
    
    // Método 3: Copy nums1 to new array then merge (O(m+n) time, O(m) space)
    public void mergeExtraSpace(int[] nums1, int m, int[] nums2, int n) {
        int[] nums1Copy = Arrays.copyOf(nums1, m);
        int i = 0, j = 0, k = 0;
        
        while (i < m && j < n) {
            if (nums1Copy[i] <= nums2[j]) {
                nums1[k++] = nums1Copy[i++];
            } else {
                nums1[k++] = nums2[j++];
            }
        }
        
        while (i < m) nums1[k++] = nums1Copy[i++];
        while (j < n) nums1[k++] = nums2[j++];
    }
    
    // Método 4: Insertion sort style (O(m*n) - slow but in-place)
    public void mergeInsertion(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            int val = nums2[i];
            int j = m + i - 1;
            
            // Shift elements greater than val to the right
            while (j >= 0 && nums1[j] > val) {
                nums1[j + 1] = nums1[j];
                j--;
            }
            nums1[j + 1] = val;
        }
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Merge Sorted Array Tests ===\n");
        
        // Test 1: Example 1
        test(sol, new int[]{1,2,3,0,0,0}, 3, new int[]{2,5,6}, 3, 
             new int[]{1,2,2,3,5,6}, "Test 1: Example 1");
        
        // Test 2: Example 2
        test(sol, new int[]{1}, 1, new int[]{}, 0, 
             new int[]{1}, "Test 2: Example 2");
        
        // Test 3: Example 3
        test(sol, new int[]{0}, 0, new int[]{1}, 1, 
             new int[]{1}, "Test 3: Example 3");
        
        // Test 4: nums2 all smaller
        test(sol, new int[]{4,5,6,0,0,0}, 3, new int[]{1,2,3}, 3, 
             new int[]{1,2,3,4,5,6}, "Test 4: nums2 all smaller");
        
        // Test 5: nums2 all larger
        test(sol, new int[]{1,2,3,0,0,0}, 3, new int[]{4,5,6}, 3, 
             new int[]{1,2,3,4,5,6}, "Test 5: nums2 all larger");
        
        // Test 6: Interleaved
        test(sol, new int[]{1,3,5,0,0,0}, 3, new int[]{2,4,6}, 3, 
             new int[]{1,2,3,4,5,6}, "Test 6: Interleaved");
        
        // Test 7: Duplicates
        test(sol, new int[]{1,2,2,0,0,0}, 3, new int[]{2,2,3}, 3, 
             new int[]{1,2,2,2,2,3}, "Test 7: Duplicates");
        
        // Test 8: Single element each
        test(sol, new int[]{2,0}, 1, new int[]{1}, 1, 
             new int[]{1,2}, "Test 8: Single element each");
        
        // Test 9: Large m, small n
        test(sol, new int[]{1,2,3,4,5,0}, 5, new int[]{6}, 1, 
             new int[]{1,2,3,4,5,6}, "Test 9: Large m, small n");
        
        // Test 10: Small m, large n
        test(sol, new int[]{5,0,0,0,0,0}, 1, new int[]{1,2,3,4,6}, 5, 
             new int[]{1,2,3,4,5,6}, "Test 10: Small m, large n");
        
        // Test 11: All same
        test(sol, new int[]{1,1,1,0,0,0}, 3, new int[]{1,1,1}, 3, 
             new int[]{1,1,1,1,1,1}, "Test 11: All same");
        
        // Test 12: Negative numbers
        test(sol, new int[]{-3,-2,0,0,0}, 2, new int[]{-1,1,2}, 3, 
             new int[]{-3,-2,-1,1,2}, "Test 12: Negative numbers");
    }
    
    private static void test(Solution sol, int[] nums1, int m, int[] nums2, int n, int[] expected, String name) {
        int[] arr1 = nums1.clone();
        int[] arr2 = nums1.clone();
        int[] arr3 = nums1.clone();
        int[] arr4 = nums1.clone();
        
        sol.merge(arr1, m, nums2, n);
        sol.mergeSort(arr2, m, nums2, n);
        sol.mergeExtraSpace(arr3, m, nums2, n);
        sol.mergeInsertion(arr4, m, nums2, n);
        
        boolean pass1 = Arrays.equals(arr1, expected);
        boolean pass2 = Arrays.equals(arr2, expected);
        boolean pass3 = Arrays.equals(arr3, expected);
        boolean pass4 = Arrays.equals(arr4, expected);
        boolean allMatch = Arrays.equals(arr1, arr2) && Arrays.equals(arr2, arr3) && Arrays.equals(arr3, arr4);
        
        System.out.println(name);
        System.out.println("  nums1: " + Arrays.toString(nums1) + ", m=" + m);
        System.out.println("  nums2: " + Arrays.toString(nums2) + ", n=" + n);
        System.out.println("  Expected: " + Arrays.toString(expected));
        System.out.println("  ThreePtr: " + Arrays.toString(arr1) + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Sort:     " + Arrays.toString(arr2) + (pass2 ? " ✓" : " ✗"));
        System.out.println("  ExtraSp:  " + Arrays.toString(arr3) + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Insert:   " + Arrays.toString(arr4) + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}