/**
 * LeetCode 147 - Insertion Sort List
 * 
 * Given the head of a singly linked list, sort the list using insertion sort, and return the sorted list's head.
 * 
 * The steps of the insertion sort algorithm:
 * 1. Insertion sort iterates, consuming one input element each repetition and growing a sorted output list.
 * 2. At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list, and inserts it there.
 * 3. It repeats until no input elements remain.
 * 
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * 
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 * 
 * Constraints:
 * The number of nodes in the list is in the range [1, 5000].
 * -5000 <= Node.val <= 5000
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
     * Approach 1: Standard Insertion Sort on Linked List - O(1) Space
     * 
     * Maintain a sorted portion (dummy head) and iterate through unsorted portion.
     * For each node, find its correct position in the sorted portion and insert it.
     * 
     * Time Complexity: O(n^2) worst case, O(n) best case (already sorted)
     * Space Complexity: O(1)
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode dummy = new ListNode(0);
        ListNode curr = head;
        
        while (curr != null) {
            // Save next node before we change pointers
            ListNode next = curr.next;
            
            // Find insertion position in sorted list
            ListNode prev = dummy;
            while (prev.next != null && prev.next.val < curr.val) {
                prev = prev.next;
            }
            
            // Insert curr between prev and prev.next
            curr.next = prev.next;
            prev.next = curr;
            
            // Move to next unsorted node
            curr = next;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 2: Optimized with Tail Pointer
     * 
     * Keep track of the tail of the sorted portion to avoid traversing
     * from the beginning when the current node is larger than all sorted nodes.
     * 
     * Time Complexity: O(n^2) worst case, O(n) best case
     * Space Complexity: O(1)
     */
    public ListNode insertionSortListOptimized(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode sortedTail = head; // Tail of sorted portion
        ListNode curr = head.next;  // First unsorted node
        
        while (curr != null) {
            if (curr.val >= sortedTail.val) {
                // Current node is already in correct position (at end of sorted)
                sortedTail = curr;
                curr = curr.next;
            } else {
                // Need to find insertion position
                ListNode prev = dummy;
                while (prev.next.val < curr.val) {
                    prev = prev.next;
                }
                
                // Remove curr from its current position
                sortedTail.next = curr.next;
                
                // Insert curr after prev
                curr.next = prev.next;
                prev.next = curr;
                
                // Move to next unsorted node
                curr = sortedTail.next;
            }
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 3: Convert to Array, Sort, Rebuild - O(n) Space
     * 
     * Extract values to array, sort array, rebuild linked list.
     * Not true insertion sort but achieves the result.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public ListNode insertionSortListArray(ListNode head) {
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
     * Approach 4: Recursive Insertion Sort
     * 
     * Recursively sort the rest, then insert the head into the sorted list.
     * 
     * Time Complexity: O(n^2)
     * Space Complexity: O(n) recursion stack
     */
    public ListNode insertionSortListRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Sort the rest of the list
        ListNode sortedRest = insertionSortListRecursive(head.next);
        
        // Insert head into sorted rest
        head.next = null; // Detach head
        return insertIntoSorted(sortedRest, head);
    }
    
    private ListNode insertIntoSorted(ListNode sortedHead, ListNode node) {
        ListNode dummy = new ListNode(0);
        dummy.next = sortedHead;
        ListNode prev = dummy;
        
        while (prev.next != null && prev.next.val < node.val) {
            prev = prev.next;
        }
        
        node.next = prev.next;
        prev.next = node;
        
        return dummy.next;
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
        
        System.out.println("=== Insertion Sort List Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [4,2,1,3] -> Expected: [1,2,3,4]");
        ListNode head1 = createList(new int[]{4, 2, 1, 3});
        printList(head1);
        ListNode result1 = sol.insertionSortList(head1);
        int[] arr1 = listToArray(result1);
        int[] expected1 = {1, 2, 3, 4};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr1));
        System.out.println("  Standard: " + (arraysEqual(arr1, expected1) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [-1,5,3,4,0] -> Expected: [-1,0,3,4,5]");
        ListNode head2 = createList(new int[]{-1, 5, 3, 4, 0});
        printList(head2);
        ListNode result2 = sol.insertionSortList(head2);
        int[] arr2 = listToArray(result2);
        int[] expected2 = {-1, 0, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr2));
        System.out.println("  Standard: " + (arraysEqual(arr2, expected2) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 3: Already sorted
        System.out.println("\nTest 3: [1,2,3,4,5] (already sorted)");
        ListNode head3 = createList(new int[]{1, 2, 3, 4, 5});
        printList(head3);
        ListNode result3 = sol.insertionSortList(head3);
        int[] arr3 = listToArray(result3);
        int[] expected3 = {1, 2, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr3));
        System.out.println("  Standard: " + (arraysEqual(arr3, expected3) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 4: Reverse sorted
        System.out.println("\nTest 4: [5,4,3,2,1] (reverse sorted)");
        ListNode head4 = createList(new int[]{5, 4, 3, 2, 1});
        printList(head4);
        ListNode result4 = sol.insertionSortList(head4);
        int[] arr4 = listToArray(result4);
        int[] expected4 = {1, 2, 3, 4, 5};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr4));
        System.out.println("  Standard: " + (arraysEqual(arr4, expected4) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 5: Single element
        System.out.println("\nTest 5: [1] (single element)");
        ListNode head5 = createList(new int[]{1});
        printList(head5);
        ListNode result5 = sol.insertionSortList(head5);
        int[] arr5 = listToArray(result5);
        int[] expected5 = {1};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr5));
        System.out.println("  Standard: " + (arraysEqual(arr5, expected5) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 6: Two elements
        System.out.println("\nTest 6: [2,1] (two elements)");
        ListNode head6 = createList(new int[]{2, 1});
        printList(head6);
        ListNode result6 = sol.insertionSortList(head6);
        int[] arr6 = listToArray(result6);
        int[] expected6 = {1, 2};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr6));
        System.out.println("  Standard: " + (arraysEqual(arr6, expected6) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 7: Duplicates
        System.out.println("\nTest 7: [3,1,2,1,3,2] (with duplicates)");
        ListNode head7 = createList(new int[]{3, 1, 2, 1, 3, 2});
        printList(head7);
        ListNode result7 = sol.insertionSortList(head7);
        int[] arr7 = listToArray(result7);
        int[] expected7 = {1, 1, 2, 2, 3, 3};
        System.out.println("  Result:   " + java.util.Arrays.toString(arr7));
        System.out.println("  Standard: " + (arraysEqual(arr7, expected7) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 8: Cross verification of all approaches
        System.out.println("\nTest 8: Cross verification of all approaches");
        int[][] testCases = {
            {4, 2, 1, 3},
            {-1, 5, 3, 4, 0},
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {1},
            {2, 1},
            {3, 1, 2, 1, 3, 2},
            {0, -1, -2, -3}
        };
        int[][] expected = {
            {1, 2, 3, 4},
            {-1, 0, 3, 4, 5},
            {1, 2, 3, 4, 5},
            {1, 2, 3, 4, 5},
            {1},
            {1, 2},
            {1, 1, 2, 2, 3, 3},
            {-3, -2, -1, 0}
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            // Approach 1: Standard
            ListNode h1 = createList(testCases[i]);
            ListNode r1 = sol.insertionSortList(h1);
            int[] a1 = listToArray(r1);
            
            // Approach 2: Optimized
            ListNode h2 = createList(testCases[i]);
            ListNode r2 = sol.insertionSortListOptimized(h2);
            int[] a2 = listToArray(r2);
            
            // Approach 3: Array
            ListNode h3 = createList(testCases[i]);
            ListNode r3 = sol.insertionSortListArray(h3);
            int[] a3 = listToArray(r3);
            
            // Approach 4: Recursive
            ListNode h4 = createList(testCases[i]);
            ListNode r4 = sol.insertionSortListRecursive(h4);
            int[] a4 = listToArray(r4);
            
            boolean m1 = arraysEqual(a1, expected[i]);
            boolean m2 = arraysEqual(a2, expected[i]);
            boolean m3 = arraysEqual(a3, expected[i]);
            boolean m4 = arraysEqual(a4, expected[i]);
            boolean match = m1 && m2 && m3 && m4;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Std:" + m1 + " Opt:" + m2 + " Arr:" + m3 + " Rec:" + m4 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}