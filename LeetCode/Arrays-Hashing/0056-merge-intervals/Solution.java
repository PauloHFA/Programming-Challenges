import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * LeetCode 0056: Merge Intervals
 * Difficulty: Medium
 * Category: Arrays & Hashing / Sorting
 * 
 * Approach: Sort by start time, then merge overlapping intervals
 * Time Complexity: O(n log n)
 * Space Complexity: O(n) for output (or O(1) excluding output)
 */
public class Solution {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort intervals by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0];
        merged.add(current);

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            
            // If current interval overlaps with the last merged interval
            if (interval[0] <= current[1]) {
                // Merge: update the end to the maximum
                current[1] = Math.max(current[1], interval[1]);
            } else {
                // No overlap: start a new interval
                current = interval;
                merged.add(current);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    // Alternative: Using a separate result array (more explicit)
    public int[][] mergeExplicit(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> result = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= end) {
                // Overlap: extend the end
                end = Math.max(end, intervals[i][1]);
            } else {
                // No overlap: add current interval and start new
                result.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }

        // Add the last interval
        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] result1 = solver.merge(intervals1);
        System.out.println("Test 1: " + Arrays.deepToString(result1));
        System.out.println("Expected: [[1, 6], [8, 10], [15, 18]]");

        // Test Case 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        int[][] result2 = solver.merge(intervals2);
        System.out.println("\nTest 2: " + Arrays.deepToString(result2));
        System.out.println("Expected: [[1, 5]]");

        // Test Case 3: No overlap
        int[][] intervals3 = {{1, 2}, {3, 4}, {5, 6}};
        int[][] result3 = solver.merge(intervals3);
        System.out.println("\nTest 3: " + Arrays.deepToString(result3));
        System.out.println("Expected: [[1, 2], [3, 4], [5, 6]]");

        // Test Case 4: All overlap
        int[][] intervals4 = {{1, 5}, {2, 3}, {3, 4}, {4, 6}};
        int[][] result4 = solver.merge(intervals4);
        System.out.println("\nTest 4: " + Arrays.deepToString(result4));
        System.out.println("Expected: [[1, 6]]");

        // Test Case 5: Single interval
        int[][] intervals5 = {{1, 4}};
        int[][] result5 = solver.merge(intervals5);
        System.out.println("\nTest 5: " + Arrays.deepToString(result5));
        System.out.println("Expected: [[1, 4]]");

        // Test Case 6: Empty
        int[][] intervals6 = {};
        int[][] result6 = solver.merge(intervals6);
        System.out.println("\nTest 6: " + Arrays.deepToString(result6));
        System.out.println("Expected: []");

        // Test Case 7: Unsorted input
        int[][] intervals7 = {{8, 10}, {1, 3}, {15, 18}, {2, 6}};
        int[][] result7 = solver.merge(intervals7);
        System.out.println("\nTest 7: " + Arrays.deepToString(result7));
        System.out.println("Expected: [[1, 6], [8, 10], [15, 18]]");

        // Test Case 8: Contained intervals
        int[][] intervals8 = {{1, 10}, {2, 3}, {4, 5}, {6, 7}};
        int[][] result8 = solver.merge(intervals8);
        System.out.println("\nTest 8: " + Arrays.deepToString(result8));
        System.out.println("Expected: [[1, 10]]");

        // Verify both approaches match
        System.out.println("\n--- Verification (Merge vs MergeExplicit) ---");
        int[][][] testCases = {intervals1, intervals2, intervals3, intervals4, intervals5, intervals6, intervals7, intervals8};
        for (int[][] test : testCases) {
            int[][] r1 = solver.merge(deepCopy(test));
            int[][] r2 = solver.mergeExplicit(deepCopy(test));
            System.out.println("Match: " + Arrays.deepEquals(r1, r2));
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