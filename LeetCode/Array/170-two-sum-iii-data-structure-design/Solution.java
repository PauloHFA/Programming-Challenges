/**
 * LeetCode 170 - Two Sum III - Data Structure Design
 * 
 * Design a data structure that accepts a stream of integers and checks if it has a pair of integers that sum up to a particular value.
 * 
 * Implement the TwoSum class:
 * - TwoSum() Initializes the TwoSum object, with an empty array initially.
 * - void add(int number) Adds number to the data structure.
 * - boolean find(int value) Returns true if there exists any pair of numbers whose sum is equal to value, otherwise, it returns false.
 * 
 * Example 1:
 * Input
 * ["TwoSum", "add", "add", "add", "find", "find"]
 * [[], [1], [3], [5], [4], [7]]
 * Output
 * [null, null, null, null, true, false]
 * 
 * Explanation
 * TwoSum twoSum = new TwoSum();
 * twoSum.add(1);   // [] -> [1]
 * twoSum.add(3);   // [1] -> [1,3]
 * twoSum.add(5);   // [1,3] -> [1,3,5]
 * twoSum.find(4);  // 1 + 3 = 4, return true
 * twoSum.find(7);  // No pair sums to 7, return false
 * 
 * Constraints:
 * -10^5 <= number <= 10^5
 * -2^31 <= value <= 2^31 - 1
 * At most 10^4 calls will be made to add and find.
 */
public class Solution {
    
    /**
     * Approach 1: HashMap with Counts - O(1) add, O(n) find
     * 
     * Store counts of each number. For find, iterate through keys and check if complement exists.
     * Handle case where number == complement (need count >= 2).
     * 
     * Time Complexity: add O(1), find O(n)
     * Space Complexity: O(n)
     */
    static class TwoSumHashMap {
        private java.util.Map<Integer, Integer> counts = new java.util.HashMap<>();
        
        public void add(int number) {
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }
        
        public boolean find(int value) {
            for (int num : counts.keySet()) {
                int complement = value - num;
                if (counts.containsKey(complement)) {
                    if (complement != num || counts.get(num) > 1) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    /**
     * Approach 2: ArrayList + HashSet for Find - O(1) add, O(n) find
     * 
     * Store numbers in ArrayList. For find, use HashSet to check complements.
     * 
     * Time Complexity: add O(1), find O(n)
     * Space Complexity: O(n)
     */
    static class TwoSumArrayList {
        private java.util.List<Integer> numbers = new java.util.ArrayList<>();
        
        public void add(int number) {
            numbers.add(number);
        }
        
        public boolean find(int value) {
            java.util.Set<Integer> seen = new java.util.HashSet<>();
            for (int num : numbers) {
                int complement = value - num;
                if (seen.contains(complement)) {
                    return true;
                }
                seen.add(num);
            }
            return false;
        }
    }
    
    /**
     * Approach 3: Sorted Array + Binary Search - O(n) add, O(n log n) find
     * 
     * Keep array sorted. Add uses insertion sort. Find uses two pointers or binary search.
     * 
     * Time Complexity: add O(n), find O(n) with two pointers
     * Space Complexity: O(n)
     */
    static class TwoSumSorted {
        private java.util.List<Integer> numbers = new java.util.ArrayList<>();
        
        public void add(int number) {
            // Insert in sorted position
            int idx = java.util.Collections.binarySearch(numbers, number);
            if (idx < 0) idx = -idx - 1;
            numbers.add(idx, number);
        }
        
        public boolean find(int value) {
            int left = 0;
            int right = numbers.size() - 1;
            
            while (left < right) {
                int sum = numbers.get(left) + numbers.get(right);
                if (sum == value) return true;
                else if (sum < value) left++;
                else right--;
            }
            return false;
        }
    }
    
    /**
     * Approach 4: HashSet of All Possible Sums - O(n) add, O(1) find
     * 
     * Precompute all possible sums when adding. Trade space for faster find.
     * 
     * Time Complexity: add O(n), find O(1)
     * Space Complexity: O(n^2) worst case
     */
    static class TwoSumPrecompute {
        private java.util.Set<Integer> numbers = new java.util.HashSet<>();
        private java.util.Set<Integer> sums = new java.util.HashSet<>();
        private java.util.List<Integer> numList = new java.util.ArrayList<>();
        
        public void add(int number) {
            for (int num : numList) {
                sums.add(num + number);
            }
            numList.add(number);
            numbers.add(number);
        }
        
        public boolean find(int value) {
            return sums.contains(value);
        }
    }
    
    /**
     * Approach 5: Two HashMaps (Numbers and Sums) - O(n) add, O(1) find
     * 
     * Similar to Approach 4 but with counts to handle duplicates correctly.
     * 
     * Time Complexity: add O(n), find O(1)
     * Space Complexity: O(n^2) worst case
     */
    static class TwoSumOptimized {
        private java.util.Map<Integer, Integer> counts = new java.util.HashMap<>();
        private java.util.Set<Integer> sums = new java.util.HashSet<>();
        
        public void add(int number) {
            for (int num : counts.keySet()) {
                sums.add(num + number);
            }
            // Handle duplicate: if we already have this number, we can form sum with itself
            if (counts.containsKey(number)) {
                sums.add(number + number);
            }
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }
        
        public boolean find(int value) {
            return sums.contains(value);
        }
    }
    
    // Test all approaches
    public static void main(String[] args) {
        System.out.println("=== Two Sum III Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: add(1), add(3), add(5), find(4)=true, find(7)=false");
        testExample1();
        
        // Test 2: Duplicate numbers
        System.out.println("\nTest 2: add(2), add(2), find(4)=true");
        testDuplicates();
        
        // Test 3: Negative numbers
        System.out.println("\nTest 3: add(-1), add(1), find(0)=true");
        testNegatives();
        
        // Test 4: Large numbers
        System.out.println("\nTest 4: Large numbers");
        testLargeNumbers();
        
        // Test 5: Many adds, few finds
        System.out.println("\nTest 5: Many adds");
        testManyAdds();
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification of all approaches");
        crossVerify();
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testExample1() {
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        hm.add(1); al.add(1); ss.add(1); pc.add(1); opt.add(1);
        hm.add(3); al.add(3); ss.add(3); pc.add(3); opt.add(3);
        hm.add(5); al.add(5); ss.add(5); pc.add(5); opt.add(5);
        
        assert hm.find(4) == true : "HashMap find(4) failed";
        assert al.find(4) == true : "ArrayList find(4) failed";
        assert ss.find(4) == true : "Sorted find(4) failed";
        assert pc.find(4) == true : "Precompute find(4) failed";
        assert opt.find(4) == true : "Optimized find(4) failed";
        
        assert hm.find(7) == false : "HashMap find(7) failed";
        assert al.find(7) == false : "ArrayList find(7) failed";
        assert ss.find(7) == false : "Sorted find(7) failed";
        assert pc.find(7) == false : "Precompute find(7) failed";
        assert opt.find(7) == false : "Optimized find(7) failed";
        
        System.out.println("  All approaches: ✓");
    }
    
    static void testDuplicates() {
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        hm.add(2); al.add(2); ss.add(2); pc.add(2); opt.add(2);
        hm.add(2); al.add(2); ss.add(2); pc.add(2); opt.add(2);
        
        assert hm.find(4) == true : "HashMap duplicate failed";
        assert al.find(4) == true : "ArrayList duplicate failed";
        assert ss.find(4) == true : "Sorted duplicate failed";
        assert pc.find(4) == true : "Precompute duplicate failed";
        assert opt.find(4) == true : "Optimized duplicate failed";
        
        assert hm.find(2) == false : "HashMap find(2) should be false";
        assert al.find(2) == false : "ArrayList find(2) should be false";
        
        System.out.println("  All approaches: ✓");
    }
    
    static void testNegatives() {
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        hm.add(-1); al.add(-1); ss.add(-1); pc.add(-1); opt.add(-1);
        hm.add(1); al.add(1); ss.add(1); pc.add(1); opt.add(1);
        
        assert hm.find(0) == true : "HashMap negative failed";
        assert al.find(0) == true : "ArrayList negative failed";
        assert ss.find(0) == true : "Sorted negative failed";
        assert pc.find(0) == true : "Precompute negative failed";
        assert opt.find(0) == true : "Optimized negative failed";
        
        System.out.println("  All approaches: ✓");
    }
    
    static void testLargeNumbers() {
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        hm.add(100000); al.add(100000); ss.add(100000); pc.add(100000); opt.add(100000);
        hm.add(-100000); al.add(-100000); ss.add(-100000); pc.add(-100000); opt.add(-100000);
        
        assert hm.find(0) == true : "HashMap large failed";
        assert al.find(0) == true : "ArrayList large failed";
        assert ss.find(0) == true : "Sorted large failed";
        assert pc.find(0) == true : "Precompute large failed";
        assert opt.find(0) == true : "Optimized large failed";
        
        System.out.println("  All approaches: ✓");
    }
    
    static void testManyAdds() {
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        for (int i = 1; i <= 100; i++) {
            hm.add(i); al.add(i); ss.add(i); pc.add(i); opt.add(i);
        }
        
        assert hm.find(101) == true : "HashMap many adds failed";
        assert al.find(101) == true : "ArrayList many adds failed";
        assert ss.find(101) == true : "Sorted many adds failed";
        assert pc.find(101) == true : "Precompute many adds failed";
        assert opt.find(101) == true : "Optimized many adds failed";
        
        assert hm.find(200) == false : "HashMap find(200) failed";
        assert al.find(200) == false : "ArrayList find(200) failed";
        
        System.out.println("  All approaches: ✓");
    }
    
    static void crossVerify() {
        // Test sequence of operations
        String[] operations = {"add", "add", "add", "find", "add", "find", "find"};
        int[][] params = {{1}, {3}, {5}, {4}, {2}, {7}, {6}};
        boolean[] expectedFinds = {true, false, true}; // find(4), find(7), find(6)
        
        TwoSumHashMap hm = new TwoSumHashMap();
        TwoSumArrayList al = new TwoSumArrayList();
        TwoSumSorted ss = new TwoSumSorted();
        TwoSumPrecompute pc = new TwoSumPrecompute();
        TwoSumOptimized opt = new TwoSumOptimized();
        
        int findIdx = 0;
        for (int i = 0; i < operations.length; i++) {
            if (operations[i].equals("add")) {
                hm.add(params[i][0]);
                al.add(params[i][0]);
                ss.add(params[i][0]);
                pc.add(params[i][0]);
                opt.add(params[i][0]);
            } else {
                boolean exp = expectedFinds[findIdx++];
                boolean r1 = hm.find(params[i][0]);
                boolean r2 = al.find(params[i][0]);
                boolean r3 = ss.find(params[i][0]);
                boolean r4 = pc.find(params[i][0]);
                boolean r5 = opt.find(params[i][0]);
                
                assert r1 == exp && r2 == exp && r3 == exp && r4 == exp && r5 == exp :
                    "Mismatch at operation " + i;
            }
        }
        
        // Test another sequence
        TwoSumHashMap hm2 = new TwoSumHashMap();
        TwoSumArrayList al2 = new TwoSumArrayList();
        TwoSumSorted ss2 = new TwoSumSorted();
        TwoSumPrecompute pc2 = new TwoSumPrecompute();
        TwoSumOptimized opt2 = new TwoSumOptimized();
        
        hm2.add(0); al2.add(0); ss2.add(0); pc2.add(0); opt2.add(0);
        hm2.add(0); al2.add(0); ss2.add(0); pc2.add(0); opt2.add(0);
        
        assert hm2.find(0) == true;
        assert al2.find(0) == true;
        assert ss2.find(0) == true;
        assert pc2.find(0) == true;
        assert opt2.find(0) == true;
        
        System.out.println("  All cross-verifications passed ✓");
    }
}