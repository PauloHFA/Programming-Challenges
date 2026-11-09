/**
 * LeetCode 148 - Sort List
 * 
 * Given the head of a linked list, return the list after sorting it in ascending order.
 * 
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * 
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 * 
 * Example 3:
 * Input: head = []
 * Output: []
 * 
 * Constraints:
 * The number of nodes in the list is in the range [0, 5 * 10^4].
 * -10^5 <= Node.val <= 10^5
 * 
 * Follow up: Can you sort the linked list in O(n log n) time and O(1) memory?
 */
public class Solution {
    
    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    /**
     * Approach 1: Merge Sort (Top-Down) - O(n log n) Time, O(log n) Space
     * 
     * Recursively split the list in half, sort each half, then merge.
     * Uses fast/slow pointers to find middle.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n) recursion stack
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Split the list into two halves
        ListNode mid = getMiddle(head);
        ListNode right = mid.next;
        mid.next = null;
        
        // Recursively sort both halves
        ListNode left = sortList(head);
        right = sortList(right);
        
        // Merge sorted halves
        return merge(left, right);
    }
    
    private ListNode getMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
    
    /**
     * Approach 2: Merge Sort (Bottom-Up) - O(n log n) Time, O(1) Space
     * 
     * Iterative merge sort using sublists of increasing size (1, 2, 4, 8...).
     * Achieves O(1) space complexity.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     */
    public ListNode sortListBottomUp(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Get length of list
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Merge sublists of size 1, 2, 4, 8...
        for (int size = 1; size < length; size *= 2) {
            ListNode prev = dummy;
            curr = dummy.next;
            
            while (curr != null) {
                // Get first sublist of size 'size'
                ListNode left = curr;
                ListNode right = split(left, size);
                curr = split(right, size);
                
                // Merge left and right
                ListNode merged = merge(left, right);
                
                // Attach merged list to previous
                prev.next = merged;
                
                // Move prev to end of merged list
                while (prev.next != null) {
                    prev = prev.next;
                }
            }
        }
        
        return dummy.next;
    }
    
    // Split list after 'size' nodes, return head of second part
    private ListNode split(ListNode head, int size) {
        if (head == null) return null;
        for (int i = 1; i < size && head.next != null; i++) {
            head = head.next;
        }
        ListNode second = head.next;
        head.next = null;
        return second;
    }
    
    /**
     * Approach 3: Convert to Array, Sort, Rebuild - O(n log n) Time, O(n) Space
     * 
     * Simple approach: extract values, sort array, rebuild list.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public ListNode sortListArray(ListNode head) {
        if (head == null) return null;
        
        java.util.List<Integer> values = new java.util.ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }
        
        java.util.Collections.sort(values);
        
        ListNode dummy = new ListNode(0);
        curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 4: Quick Sort on Linked List - O(n log n) Average, O(n^2) Worst
     * 
     * Partition around pivot, recursively sort partitions.
     * 
     * Time Complexity: O(n log n) average, O(n^2) worst
     * Space Complexity: O(log n) average recursion stack
     */
    public ListNode sortListQuickSort(ListNode head) {
        if (head == null || head.next == null) return head;
        return quickSort(head, null);
    }
    
    private ListNode quickSort(ListNode head, ListNode end) {
        if (head == end || head.next == end) return head;
        
        // Partition
        ListNode pivot = head;
        ListNode dummyLess = new ListNode(0);
        ListNode dummyEqual = new ListNode(0);
        ListNode dummyGreater = new ListNode(0);
        ListNode less = dummyLess, equal = dummyEqual, greater = dummyGreater;
        
        ListNode curr = head;
        while (curr != end) {
            if (curr.val < pivot.val) {
                less.next = curr;
                less = less.next;
            } else if (curr.val == pivot.val) {
                equal.next = curr;
                equal = equal.next;
            } else {
                greater.next = curr;
                greater = greater.next;
            }
            curr = curr.next;
        }
        
        less.next = null;
        equal.next = null;
        greater.next = null;
        
        // Recursively sort less and greater
        ListNode sortedLess = quickSort(dummyLess.next, null);
        ListNode sortedGreater = quickSort(dummyGreater.next, null);
        
        // Concatenate: sortedLess + equal + sortedGreater
        ListNode result = concatenate(sortedLess, dummyEqual.next);
        result = concatenate(result, sortedGreater);
        
        return result;
    }
    
    private ListNode concatenate(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        ListNode curr = l1;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = l2;
        return l1;
    }
    
    // Helper methods for testing
    private static ListNode createList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode head = new ListNode(values[0]);
        ListNode curr = head;
        for (int i = 1; i < values.length; i++) {
            curr.next = new ListNode(values[i]);
            curr = curr.next;
        }
        return head;
    }
    
    private static int[] listToArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        ListNode curr = head;
        int count = 0;
        while (curr != null && count < 10000) {
            list.add(curr.val);
            curr = curr.next;
            count++;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
    
    private static boolean arraysEqual(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
    
    private static boolean isSorted(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val > curr.next.val) return false;
            curr = curr.next;
        }
        return true;
    }
    
    private static void printList(ListNode head) {
        ListNode curr = head;
        int count = 0;
        System.out.print("  ");
        while (curr != null && count < 20) {
            System.out.print(curr.val);
            if (curr.next != null) System.out.print(" -> ");
            curr = curr.next;
            count++;
        }
        if (curr != null) System.out.print(" -> ...");
        System.out.println();
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Sort List Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [4,2,1,3] -> Expected: [1,2,3,4]");
        ListNode head1 = createList(new int[]{4, 2, 1, 3});
        printList(head1);
        ListNode result1 = sol.sortList(head1);
        int[] arr1 = listToArray(result1);
        int[] expected1 = {1, 2, 3, 4};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr1));
        System.out.println("  TopDown:  " + (arraysEqual(arr1, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [-1,5,3,4,0] -> Expected: [-1,0,3,4,5]");
        ListNode head2 = createList(new int[]{-1, 5, 3, 4, 0});
        printList(head2);
        ListNode result2 = sol.sortList(head2);
        int[] arr2 = listToArray(result2);
        int[] expected2 = {-1, 0, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr2));
        System.out.println("  TopDown:  " + (arraysEqual(arr2, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 3: Example 3 - Empty list
        System.out.println("\nTest 3: [] -> Expected: []");
        ListNode head3 = null;
        ListNode result3 = sol.sortList(head3);
        System.out.println("  Result:   " + (result3 == null ? "null" : "not null"));
        System.out.println("  TopDown:  " + (result3 == null ? "PASS ✓" : "FAIL ✗"));
        
        // Test 4: Single element
        System.out.println("\nTest 4: [1] -> Expected: [1]");
        ListNode head4 = createList(new int[]{1});
        printList(head4);
        ListNode result4 = sol.sortList(head4);
        int[] arr4 = listToArray(result4);
        int[] expected4 = {1};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr4));
        System.out.println("  TopDown:  " + (arraysEqual(arr4, expected4) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 5: Already sorted
        System.out.println("\nTest 5: [1,2,3,4,5] (already sorted)");
        ListNode head5 = createList(new int[]{1, 2, 3, 4, 5});
        printList(head5);
        ListNode result5 = sol.sortList(head5);
        int[] arr5 = listToArray(result5);
        int[] expected5 = {1, 2, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr5));
        System.out.println("  TopDown:  " + (arraysEqual(arr5, expected5) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 6: Reverse sorted
        System.out.println("\nTest 6: [5,4,3,2,1] (reverse sorted)");
        ListNode head6 = createList(new int[]{5, 4, 3, 2, 1});
        printList(head6);
        ListNode result6 = sol.sortList(head6);
        int[] arr6 = listToArray(result6);
        int[] expected6 = {1, 2, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr6));
        System.out.println("  TopDown:  " + (arraysEqual(arr6, expected6) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 7: Duplicates
        System.out.println("\nTest 7: [3,1,2,1,3,2] (with duplicates)");
        ListNode head7 = createList(new int[]{3, 1, 2, 1, 3, 2});
        printList(head7);
        ListNode result7 = sol.sortList(head7);
        int[] arr7 = listToArray(result7);
        int[] expected7 = {1, 1, 2, 2, 3, 3};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr7));
        System.out.println("  TopDown:  " + (arraysEqual(arr7, expected7) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 8: Large random list
        System.out.println("\nTest 8: Large list (100 elements)");
        int[] large = new int[100];
        java.util.Random rand = new java.util.Random(42);
        for (int i = 0; i < 100; i++) large[i] = rand.nextInt(1000);
        ListNode head8 = createList(large);
        ListNode result8 = sol.sortList(head8);
        System.out.println("  Sorted:   " + (isSorted(result8) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 9: Cross verification of all approaches
        System.out.println("\nTest 9: Cross verification of all approaches");
        int[][] testCases = {
            {4, 2, 1, 3},
            {-1, 5, 3, 4, 0},
            {},
            {1},
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {3, 1, 2, 1, 3, 2},
            {0, -1, -2, -3}
        };
        int[][] expected = {
            {1, 2, 3, 4},
            {-1, 0, 3, 4, 5},
            {},
            {1},
            {1, 2, 3, 4, 5},
            {1, 2, 3, 4, 5},
            {1, 1, 2, 2, 3, 3},
            {-3, -2, -1, 0}
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            // Approach 1: Top-Down Merge Sort
            ListNode h1 = createList(testCases[i]);
            ListNode r1 = sol.sortList(h1);
            int[] a1 = listToArray(r1);
            
            // Approach 2: Bottom-Up Merge Sort
            ListNode h2 = createList(testCases[i]);
            ListNode r2 = sol.sortListBottomUp(h2);
            int[] a2 = listToArray(r2);
            
            // Approach 3: Array Sort
            ListNode h3 = createList(testCases[i]);
            ListNode r3 = sol.sortListArray(h3);
            int[] a3 = listToArray(r3);
            
            // Approach 4: Quick Sort
            ListNode h4 = createList(testCases[i]);
            ListNode r4 = sol.sortListQuickSort(h4);
            int[] a4 = listToArray(r4);
            
            boolean m1 = arraysEqual(a1, expected[i]);
            boolean m2 = arraysEqual(a2, expected[i]);
            boolean m3 = arraysEqual(a3, expected[i]);
            boolean m4 = arraysEqual(a4, expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (TD:" + m1 + " BU:" + m2 + " Arr:" + m3 + " QS:" + m4 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}