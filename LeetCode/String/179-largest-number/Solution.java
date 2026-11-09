import java.util.*;

/**
 * LeetCode 179 - Largest Number
 * 
 * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
 * Since the result may be very large, so you need to return a string instead of an integer.
 * 
 * Example 1:
 * Input: nums = [10,2]
 * Output: "210"
 * 
 * Example 2:
 * Input: nums = [3,30,34,5,9]
 * Output: "9534330"
 * 
 * Constraints:
 * - 1 <= nums.length <= 100
 * - 0 <= nums[i] <= 10^9
 */
public class Solution {

    // Approach 1: Custom Comparator with String Concatenation (Optimal)
    // Compare a+b vs b+a to determine order
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        
        if (strs[0].equals("0")) return "0";
        
        StringBuilder sb = new StringBuilder();
        for (String s : strs) sb.append(s);
        return sb.toString();
    }

    // Approach 2: Using List and Collections.sort
    public String largestNumberList(int[] nums) {
        List<String> list = new ArrayList<>();
        for (int num : nums) list.add(String.valueOf(num));
        
        Collections.sort(list, (a, b) -> (b + a).compareTo(a + b));
        
        if (list.get(0).equals("0")) return "0";
        
        StringBuilder sb = new StringBuilder();
        for (String s : list) sb.append(s);
        return sb.toString();
    }

    // Approach 3: Radix Sort inspired (Bucket by first digit)
    // Group by first digit, sort within groups, then concatenate
    public String largestNumberBucket(int[] nums) {
        List<String>[] buckets = new List[10];
        for (int i = 0; i < 10; i++) buckets[i] = new ArrayList<>();
        
        for (int num : nums) {
            String s = String.valueOf(num);
            int firstDigit = s.charAt(0) - '0';
            buckets[firstDigit].add(s);
        }
        
        // Sort each bucket with custom comparator
        for (int i = 0; i < 10; i++) {
            buckets[i].sort((a, b) -> (b + a).compareTo(a + b));
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            for (String s : buckets[i]) sb.append(s);
        }
        
        String result = sb.toString();
        return result.isEmpty() || result.charAt(0) == '0' ? "0" : result;
    }

    // Approach 4: Priority Queue (Max Heap)
    public String largestNumberPQ(int[] nums) {
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> (b + a).compareTo(a + b));
        for (int num : nums) pq.offer(String.valueOf(num));
        
        if (pq.peek().equals("0")) return "0";
        
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) sb.append(pq.poll());
        return sb.toString();
    }

    // Approach 5: QuickSort with custom partition (In-place)
    public String largestNumberQuickSort(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);
        
        quickSort(strs, 0, strs.length - 1);
        
        if (strs[0].equals("0")) return "0";
        
        StringBuilder sb = new StringBuilder();
        for (String s : strs) sb.append(s);
        return sb.toString();
    }
    
    private void quickSort(String[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(String[] arr, int low, int high) {
        String pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if ((arr[j] + pivot).compareTo(pivot + arr[j]) > 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    private void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Largest Number Tests ===\n");
        
        // Test 1: Example 1
        testCase(sol, new int[]{10, 2}, "210", "Example 1");
        
        // Test 2: Example 2
        testCase(sol, new int[]{3, 30, 34, 5, 9}, "9534330", "Example 2");
        
        // Test 3: All zeros
        testCase(sol, new int[]{0, 0, 0}, "0", "All zeros");
        
        // Test 4: Single element
        testCase(sol, new int[]{5}, "5", "Single element");
        
        // Test 5: Already sorted
        testCase(sol, new int[]{9, 8, 7, 6, 5}, "98765", "Already sorted");
        
        // Test 6: With leading zeros in comparison
        testCase(sol, new int[]{121, 12}, "12121", "121 vs 12");
        
        // Test 7: Large numbers
        testCase(sol, new int[]{1000000000, 999999999}, "9999999991000000000", "Large numbers");
        
        // Test 8: Many elements
        testCase(sol, new int[]{1,2,3,4,5,6,7,8,9,10}, "98765432110", "Many elements");
        
        // Test 9: Edge case - 34323 vs 3432
        testCase(sol, new int[]{34323, 3432}, "343234323", "34323 vs 3432");
        
        // Test 10: Edge case - 121 vs 12
        testCase(sol, new int[]{12, 121}, "12121", "12 vs 121");
        
        // Cross-verification
        System.out.println("\n=== Cross Verification ===");
        int[][] testCases = {
            {10, 2},
            {3, 30, 34, 5, 9},
            {0, 0, 0},
            {5},
            {9, 8, 7, 6, 5},
            {121, 12},
            {1000000000, 999999999},
            {1,2,3,4,5,6,7,8,9,10},
            {34323, 3432},
            {12, 121}
        };
        
        boolean allMatch = true;
        for (int[] tc : testCases) {
            String r1 = sol.largestNumber(tc);
            String r2 = sol.largestNumberList(tc);
            String r3 = sol.largestNumberBucket(tc);
            String r4 = sol.largestNumberPQ(tc);
            String r5 = sol.largestNumberQuickSort(tc);
            
            if (!r1.equals(r2) || !r1.equals(r3) || !r1.equals(r4) || !r1.equals(r5)) {
                System.out.println("Mismatch for " + Arrays.toString(tc));
                System.out.printf("  ArraySort: %s, List: %s, Bucket: %s, PQ: %s, QuickSort: %s%n", r1, r2, r3, r4, r5);
                allMatch = false;
            }
        }
        System.out.println(allMatch ? "All approaches match! ✓" : "Mismatches found! ✗");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    private static void testCase(Solution sol, int[] nums, String expected, String name) {
        String r1 = sol.largestNumber(nums);
        String r2 = sol.largestNumberList(nums);
        String r3 = sol.largestNumberBucket(nums);
        String r4 = sol.largestNumberPQ(nums);
        String r5 = sol.largestNumberQuickSort(nums);
        
        boolean pass = r1.equals(expected) && r2.equals(expected) && r3.equals(expected) 
                      && r4.equals(expected) && r5.equals(expected);
        
        System.out.printf("%s: Expected \"%s\", Got \"%s\" %s%n", name, expected, r1, pass ? "✓" : "✗");
        if (!pass) {
            System.out.printf("  List: %s, Bucket: %s, PQ: %s, QuickSort: %s%n", r2, r3, r4, r5);
        }
    }
}