import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 0057: Insert Interval
 * Difficulty: Medium
 * Category: Arrays & Hashing
 * 
 * Approach: Three phases - add non-overlapping before, merge overlapping, add non-overlapping after
 * Time Complexity: O(n)
 * Space Complexity: O(n) for output
 */
public class Solution {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1: Add all intervals ending before newInterval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge all intervals overlapping with newInterval
        int start = newInterval[0];
        int end = newInterval[1];
        
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        
        result.add(new int[]{start, end});

        // Phase 3: Add remaining intervals
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    // Alternative: Insert then merge (reuses merge logic from problem 56)
    public int[][] insertThenMerge(int[][] intervals, int[] newInterval) {
        // Create new array with newInterval inserted in correct position
        List<int[]> all = new ArrayList<>();
        boolean inserted = false;
        
        for (int[] interval : intervals) {
            if (!inserted && interval[0] > newInterval[0]) {
                all.add(newInterval);
                inserted = true;
            }
            all.add(interval);
        }
        
        if (!inserted) {
            all.add(newInterval);
        }
        
        // Now merge (same logic as problem 56)
        int[][] merged = new int[all.size()][2];
        for (int j = 0; j < all.size(); j++) {
            merged[j] = all.get(j);
        }
        
        return mergeIntervals(merged);
    }

    private int[][] mergeIntervals(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        result.add(current);
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= current[1]) {
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                current = intervals[i];
                result.add(current);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] intervals1 = {{1, 3}, {6, 9}};
        int[] newInterval1 = {2, 5};
        int[][] result1 = solver.insert(intervals1, newInterval1);
        System.out.println("Test 1: " + Arrays.deepToString(result1));
        System.out.println("Expected: [[1, 5], [6, 9]]");

        // Test Case 2
        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval2 = {4, 8};
        int[][] result2 = solver.insert(intervals2, newInterval2);
        System.out.println("\nTest 2: " + Arrays.deepToString(result2));
        System.out.println("Expected: [[1, 2], [3, 10], [12, 16]]");

        // Test Case 3: Empty intervals
        int[][] intervals3 = {};
        int[] newInterval3 = {5, 7};
        int[][] result3 = solver.insert(intervals3, newInterval3);
        System.out.println("\nTest 3: " + Arrays.deepToString(result3));
        System.out.println("Expected: [[5, 7]]");

        // Test Case 4: New interval before all
        int[][] intervals4 = {{3, 5}, {6, 9}};
        int[] newInterval4 = {1, 2};
        int[][] result4 = solver.insert(intervals4, newInterval4);
        System.out.println("\nTest 4: " + Arrays.deepToString(result4));
        System.out.println("Expected: [[1, 2], [3, 5], [6, 9]]");

        // Test Case 5: New interval after all
        int[][] intervals5 = {{1, 2}, {3, 5}};
        int[] newInterval5 = {6, 9};
        int[][] result5 = solver.insert(intervals5, newInterval5);
        System.out.println("\nTest 5: " + Arrays.deepToString(result5));
        System.out.println("Expected: [[1, 2], [3, 5], [6, 9]]");

        // Test Case 6: New interval covers all
        int[][] intervals6 = {{3, 5}, {6, 9}};
        int[] newInterval6 = {1, 10};
        int[][] result6 = solver.insert(intervals6, newInterval6);
        System.out.println("\nTest 6: " + Arrays.deepToString(result6));
        System.out.println("Expected: [[1, 10]]");

        // Test Case 7: New interval in middle, no merge
        int[][] intervals7 = {{1, 2}, {6, 7}};
        int[] newInterval7 = {3, 5};
        int[][] result7 = solver.insert(intervals7, newInterval7);
        System.out.println("\nTest 7: " + Arrays.deepToString(result7));
        System.out.println("Expected: [[1, 2], [3, 5], [6, 7]]");

        // Test Case 8: Touching intervals (merge)
        int[][] intervals8 = {{1, 2}, {3, 5}};
        int[] newInterval8 = {2, 3};
        int[][] result8 = solver.insert(intervals8, newInterval8);
        System.out.println("\nTest 8: " + Arrays.deepToString(result8));
        System.out.println("Expected: [[1, 5]]");

        // Verify both approaches match
        System.out.println("\n--- Verification (Direct vs InsertThenMerge) ---");
        int[][][] testIntervals = {intervals1, intervals2, intervals3, intervals4, intervals5, intervals6, intervals7, intervals8};
        int[][] testNewIntervals = {newInterval1, newInterval2, newInterval3, newInterval4, newInterval5, newInterval6, newInterval7, newInterval8};
        
        for (int j = 0; j < testIntervals.length; j++) {
            int[][] r1 = solver.insert(deepCopy(testIntervals[j]), testNewIntervals[j].clone());
            int[][] r2 = solver.insertThenMerge(deepCopy(testIntervals[j]), testNewIntervals[j].clone());
            System.out.println("Test " + (j+1) + " Match: " + Arrays.deepEquals(r1, r2));
        }
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) return null;
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}