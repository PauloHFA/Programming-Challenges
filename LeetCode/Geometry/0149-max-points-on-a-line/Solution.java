/**
 * LeetCode 149 - Max Points on a Line
 * 
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane,
 * return the maximum number of points that lie on the same straight line.
 * 
 * Example 1:
 * Input: points = [[1,1],[2,2],[3,3]]
 * Output: 3
 * 
 * Example 2:
 * Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 * 
 * Constraints:
 * 1 <= points.length <= 300
 * points[i].length == 2
 * -10^4 <= xi, yi <= 10^4
 * All the points are unique.
 */
public class Solution {
    
    /**
     * Approach 1: HashMap with Slope (Double) - O(n^2) Time
     * 
     * For each point, calculate slopes to all other points.
     * Use HashMap to count points with same slope.
     * Handle vertical lines (infinite slope) and duplicate points.
     * 
     * Time Complexity: O(n^2)
     * Space Complexity: O(n)
     */
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        
        int maxPoints = 0;
        
        for (int i = 0; i < n; i++) {
            // Map slope -> count
            java.util.Map<Double, Integer> slopeCount = new java.util.HashMap<>();
            int duplicate = 0;
            int vertical = 0;
            int currentMax = 0;
            
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                
                if (dx == 0 && dy == 0) {
                    // Duplicate point
                    duplicate++;
                } else if (dx == 0) {
                    // Vertical line
                    vertical++;
                    currentMax = Math.max(currentMax, vertical);
                } else {
                    // Calculate slope as double
                    double slope = (double) dy / dx;
                    // Normalize -0.0 to 0.0
                    if (slope == -0.0) slope = 0.0;
                    
                    int count = slopeCount.getOrDefault(slope, 0) + 1;
                    slopeCount.put(slope, count);
                    currentMax = Math.max(currentMax, count);
                }
            }
            
            // Add 1 for the base point itself, plus duplicates
            maxPoints = Math.max(maxPoints, currentMax + duplicate + 1);
        }
        
        return maxPoints;
    }
    
    /**
     * Approach 2: HashMap with Normalized Slope (Fraction) - O(n^2) Time
     * 
     * Use GCD to normalize slope as a fraction (dy/dx) to avoid floating point precision issues.
     * Represent slope as "dy/dx" string or pair.
     * 
     * Time Complexity: O(n^2 * log(max coordinate))
     * Space Complexity: O(n)
     */
    public int maxPointsFraction(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        
        int maxPoints = 0;
        
        for (int i = 0; i < n; i++) {
            java.util.Map<String, Integer> slopeCount = new java.util.HashMap<>();
            int duplicate = 0;
            int currentMax = 0;
            
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                
                if (dx == 0 && dy == 0) {
                    duplicate++;
                    continue;
                }
                
                // Normalize using GCD
                int gcd = gcd(dx, dy);
                dx /= gcd;
                dy /= gcd;
                
                // Ensure consistent sign (denominator positive)
                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                }
                
                String key = dy + "/" + dx;
                int count = slopeCount.getOrDefault(key, 0) + 1;
                slopeCount.put(key, count);
                currentMax = Math.max(currentMax, count);
            }
            
            maxPoints = Math.max(maxPoints, currentMax + duplicate + 1);
        }
        
        return maxPoints;
    }
    
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a == 0 ? 1 : a;
    }
    
    /**
     * Approach 3: Using Pair class for slope (cleaner)
     * 
     * Time Complexity: O(n^2 * log(max coordinate))
     * Space Complexity: O(n)
     */
    public int maxPointsPair(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        
        int maxPoints = 0;
        
        for (int i = 0; i < n; i++) {
            java.util.Map<Slope, Integer> slopeCount = new java.util.HashMap<>();
            int duplicate = 0;
            int currentMax = 0;
            
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                
                if (dx == 0 && dy == 0) {
                    duplicate++;
                    continue;
                }
                
                Slope slope = new Slope(dx, dy);
                int count = slopeCount.getOrDefault(slope, 0) + 1;
                slopeCount.put(slope, count);
                currentMax = Math.max(currentMax, count);
            }
            
            maxPoints = Math.max(maxPoints, currentMax + duplicate + 1);
        }
        
        return maxPoints;
    }
    
    static class Slope {
        int dx, dy;
        
        Slope(int dx, int dy) {
            int gcd = gcd(dx, dy);
            this.dx = dx / gcd;
            this.dy = dy / gcd;
            
            // Normalize sign
            if (this.dx < 0) {
                this.dx = -this.dx;
                this.dy = -this.dy;
            } else if (this.dx == 0) {
                // Vertical line: dy should be 1
                this.dy = 1;
            }
        }
        
        private int gcd(int a, int b) {
            a = Math.abs(a);
            b = Math.abs(b);
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a == 0 ? 1 : a;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slope slope = (Slope) o;
            return dx == slope.dx && dy == slope.dy;
        }
        
        @Override
        public int hashCode() {
            return 31 * dx + dy;
        }
    }
    
    /**
     * Approach 4: Brute Force with Line Equation (for verification)
     * 
     * Check all pairs of points to define lines, then count points on each line.
     * 
     * Time Complexity: O(n^3)
     * Space Complexity: O(n^2)
     */
    public int maxPointsBruteForce(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        
        int maxPoints = 0;
        
        // For each pair of points, define a line and count points on it
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int count = 2; // points i and j
                
                // Line through points[i] and points[j]: (y - y1)(x2 - x1) = (y2 - y1)(x - x1)
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    int x3 = points[k][0], y3 = points[k][1];
                    
                    // Check if point k lies on line: (y3 - y1) * (x2 - x1) == (y2 - y1) * (x3 - x1)
                    long left = (long)(y3 - y1) * (x2 - x1);
                    long right = (long)(y2 - y1) * (x3 - x1);
                    
                    if (left == right) count++;
                }
                
                maxPoints = Math.max(maxPoints, count);
            }
        }
        
        return maxPoints;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Max Points on a Line Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [[1,1],[2,2],[3,3]] -> Expected: 3");
        int[][] points1 = {{1,1}, {2,2}, {3,3}};
        int result1 = sol.maxPoints(points1);
        System.out.println("  Double:     " + result1 + (result1 == 3 ? " ✓" : " ✗"));
        System.out.println("  Fraction:   " + sol.maxPointsFraction(points1));
        System.out.println("  Pair:       " + sol.maxPointsPair(points1));
        System.out.println("  BruteForce: " + sol.maxPointsBruteForce(points1));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]] -> Expected: 4");
        int[][] points2 = {{1,1}, {3,2}, {5,3}, {4,1}, {2,3}, {1,4}};
        int result2 = sol.maxPoints(points2);
        System.out.println("  Double:     " + result2 + (result2 == 4 ? " ✓" : " ✗"));
        System.out.println("  Fraction:   " + sol.maxPointsFraction(points2));
        System.out.println("  Pair:       " + sol.maxPointsPair(points2));
        System.out.println("  BruteForce: " + sol.maxPointsBruteForce(points2));
        
        // Test 3: Two points
        System.out.println("\nTest 3: [[0,0],[1,1]] -> Expected: 2");
        int[][] points3 = {{0,0}, {1,1}};
        int result3 = sol.maxPoints(points3);
        System.out.println("  Double:     " + result3 + (result3 == 2 ? " ✓" : " ✗"));
        
        // Test 4: Single point
        System.out.println("\nTest 4: [[0,0]] -> Expected: 1");
        int[][] points4 = {{0,0}};
        int result4 = sol.maxPoints(points4);
        System.out.println("  Double:     " + result4 + (result4 == 1 ? " ✓" : " ✗"));
        
        // Test 5: Vertical line
        System.out.println("\nTest 5: [[0,0],[0,1],[0,2],[0,3]] -> Expected: 4");
        int[][] points5 = {{0,0}, {0,1}, {0,2}, {0,3}};
        int result5 = sol.maxPoints(points5);
        System.out.println("  Double:     " + result5 + (result5 == 4 ? " ✓" : " ✗"));
        System.out.println("  Fraction:   " + sol.maxPointsFraction(points5));
        System.out.println("  Pair:       " + sol.maxPointsPair(points5));
        
        // Test 6: Horizontal line
        System.out.println("\nTest 6: [[0,0],[1,0],[2,0],[3,0]] -> Expected: 4");
        int[][] points6 = {{0,0}, {1,0}, {2,0}, {3,0}};
        int result6 = sol.maxPoints(points6);
        System.out.println("  Double:     " + result6 + (result6 == 4 ? " ✓" : " ✗"));
        
        // Test 7: Duplicate points
        System.out.println("\nTest 7: [[0,0],[0,0],[1,1]] -> Expected: 3");
        int[][] points7 = {{0,0}, {0,0}, {1,1}};
        int result7 = sol.maxPoints(points7);
        System.out.println("  Double:     " + result7 + (result7 == 3 ? " ✓" : " ✗"));
        
        // Test 8: No three collinear
        System.out.println("\nTest 8: [[0,0],[1,2],[2,1],[3,3]] -> Expected: 2");
        int[][] points8 = {{0,0}, {1,2}, {2,1}, {3,3}};
        int result8 = sol.maxPoints(points8);
        System.out.println("  Double:     " + result8 + (result8 == 2 ? " ✓" : " ✗"));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification of all approaches");
        int[][][] testCases = {
            {{1,1}, {2,2}, {3,3}},
            {{1,1}, {3,2}, {5,3}, {4,1}, {2,3}, {1,4}},
            {{0,0}, {1,1}},
            {{0,0}},
            {{0,0}, {0,1}, {0,2}, {0,3}},
            {{0,0}, {1,0}, {2,0}, {3,0}},
            {{0,0}, {0,0}, {1,1}},
            {{0,0}, {1,2}, {2,1}, {3,3}},
            {{1,1}, {2,2}, {3,3}, {4,4}, {5,5}},
            {{0,0}, {1,1}, {2,2}, {3,4}}
        };
        int[] expected = {3, 4, 2, 1, 4, 4, 3, 2, 5, 3};
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            int r1 = sol.maxPoints(testCases[i]);
            int r2 = sol.maxPointsFraction(testCases[i]);
            int r3 = sol.maxPointsPair(testCases[i]);
            int r4 = sol.maxPointsBruteForce(testCases[i]);
            
            boolean m1 = (r1 == expected[i]);
            boolean m2 = (r2 == expected[i]);
            boolean m3 = (r3 == expected[i]);
            boolean m4 = (r4 == expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Dbl:" + r1 + " Frac:" + r2 + " Pair:" + r3 + " BF:" + r4 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}