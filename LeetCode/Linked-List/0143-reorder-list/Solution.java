/**
 * LeetCode 143 - Reorder List
 * 
 * You are given the head of a singly linked-list. The list can be represented as:
 * L0 → L1 → … → Ln - 1 → Ln
 * 
 * Reorder the list to be on the following form:
 * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
 * 
 * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
 * 
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [1,4,2,3]
 * 
 * Example 2:
 * Input: head = [1,2,3,4,5]
 * Output: [1,5,2,4,3]
 * 
 * Constraints:
 * The number of nodes in the list is in the range [1, 5 * 10^4].
 * 1 <= Node.val <= 1000
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
     * Approach 1: Find Middle + Reverse Second Half + Merge - O(1) Space
     * 
     * 1. Find middle of list using slow/fast pointers
     * 2. Reverse the second half
     * 3. Merge first half and reversed second half alternately
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        
        // Step 1: Find middle (slow will be at middle for odd, left-middle for even)
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse second half
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null; // Break the list
        
        // Step 3: Merge two halves alternately
        mergeLists(head, secondHalf);
    }
    
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    private void mergeLists(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            ListNode l1Next = l1.next;
            ListNode l2Next = l2.next;
            
            l1.next = l2;
            if (l1Next == null) break; // Odd length case
            
            l2.next = l1Next;
            l1 = l1Next;
            l2 = l2Next;
        }
    }
    
    /**
     * Approach 2: Using Array/Deque - O(n) Space
     * 
     * Store all nodes in an array, then rebuild using two pointers.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public void reorderListArray(ListNode head) {
        if (head == null || head.next == null) return;
        
        java.util.List<ListNode> nodes = new java.util.ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            nodes.add(curr);
            curr = curr.next;
        }
        
        int left = 0;
        int right = nodes.size() - 1;
        ListNode dummy = new ListNode(0);
        curr = dummy;
        
        while (left < right) {
            curr.next = nodes.get(left++);
            curr = curr.next;
            curr.next = nodes.get(right--);
            curr = curr.next;
        }
        
        if (left == right) {
            curr.next = nodes.get(left);
            curr = curr.next;
        }
        curr.next = null;
    }
    
    /**
     * Approach 3: Recursive - O(n) Space (call stack)
     * 
     * Use recursion to reach end, then reorder on the way back.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) recursion stack
     */
    public void reorderListRecursive(ListNode head) {
        if (head == null || head.next == null) return;
        reorderRecursiveHelper(head, head);
    }
    
    // Returns the node to connect next
    private ListNode reorderRecursiveHelper(ListNode front, ListNode back) {
        if (back == null) return front;
        
        ListNode nextFront = reorderRecursiveHelper(front, back.next);
        
        // If we've already processed all nodes, stop
        if (nextFront == null) return null;
        
        // If front and back meet or cross, we're done
        if (nextFront == back || nextFront.next == back) {
            back.next = null;
            return null;
        }
        
        // Insert back after front
        ListNode frontNext = nextFront.next;
        nextFront.next = back;
        back.next = frontNext;
        
        return frontNext;
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
        while (curr != null && count < 1000) { // Prevent infinite loop
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
        
        System.out.println("=== Reorder List Tests ===\n");
        
        // Test 1: Example 1 - Even length
        System.out.println("Test 1: [1,2,3,4] -> Expected: [1,4,2,3]");
        ListNode head1 = createList(new int[]{1, 2, 3, 4});
        printList(head1);
        sol.reorderList(head1);
        int[] result1 = listToArray(head1);
        int[] expected1 = {1, 4, 2, 3};
        System.out.println("  Result:   " + java.util.Arrays.toString(result1));
        System.out.println("  Approach1:" + (arraysEqual(result1, expected1) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 2: Example 2 - Odd length
        System.out.println("\nTest 2: [1,2,3,4,5] -> Expected: [1,5,2,4,3]");
        ListNode head2 = createList(new int[]{1, 2, 3, 4, 5});
        printList(head2);
        sol.reorderList(head2);
        int[] result2 = listToArray(head2);
        int[] expected2 = {1, 5, 2, 4, 3};
        System.out.println("  Result:   " + java.util.Arrays.toString(result2));
        System.out.println("  Approach1:" + (arraysEqual(result2, expected2) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 3: Two nodes
        System.out.println("\nTest 3: [1,2] -> Expected: [1,2]");
        ListNode head3 = createList(new int[]{1, 2});
        printList(head3);
        sol.reorderList(head3);
        int[] result3 = listToArray(head3);
        int[] expected3 = {1, 2};
        System.out.println("  Result:   " + java.util.Arrays.toString(result3));
        System.out.println("  Approach1:" + (arraysEqual(result3, expected3) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 4: Single node
        System.out.println("\nTest 4: [1] -> Expected: [1]");
        ListNode head4 = createList(new int[]{1});
        printList(head4);
        sol.reorderList(head4);
        int[] result4 = listToArray(head4);
        int[] expected4 = {1};
        System.out.println("  Result:   " + java.util.Arrays.toString(result4));
        System.out.println("  Approach1:" + (arraysEqual(result4, expected4) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 5: Three nodes
        System.out.println("\nTest 5: [1,2,3] -> Expected: [1,3,2]");
        ListNode head5 = createList(new int[]{1, 2, 3});
        printList(head5);
        sol.reorderList(head5);
        int[] result5 = listToArray(head5);
        int[] expected5 = {1, 3, 2};
        System.out.println("  Result:   " + java.util.Arrays.toString(result5));
        System.out.println("  Approach1:" + (arraysEqual(result5, expected5) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 6: Six nodes
        System.out.println("\nTest 6: [1,2,3,4,5,6] -> Expected: [1,6,2,5,3,4]");
        ListNode head6 = createList(new int[]{1, 2, 3, 4, 5, 6});
        printList(head6);
        sol.reorderList(head6);
        int[] result6 = listToArray(head6);
        int[] expected6 = {1, 6, 2, 5, 3, 4};
        System.out.println("  Result:   " + java.util.Arrays.toString(result6));
        System.out.println("  Approach1:" + (arraysEqual(result6, expected6) ? " PASS ✓" : " FAIL ✗"));
        
        // Test 7: Cross verification with all approaches
        System.out.println("\nTest 7: Cross verification of all approaches");
        int[][] testCases = {
            {1, 2, 3, 4},
            {1, 2, 3, 4, 5},
            {1, 2},
            {1},
            {1, 2, 3},
            {1, 2, 3, 4, 5, 6},
            {1, 2, 3, 4, 5, 6, 7}
        };
        int[][] expected = {
            {1, 4, 2, 3},
            {1, 5, 2, 4, 3},
            {1, 2},
            {1},
            {1, 3, 2},
            {1, 6, 2, 5, 3, 4},
            {1, 7, 2, 6, 3, 5, 4}
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            // Approach 1: Find middle + reverse + merge
            ListNode h1 = createList(testCases[i]);
            sol.reorderList(h1);
            int[] r1 = listToArray(h1);
            
            // Approach 2: Array
            ListNode h2 = createList(testCases[i]);
            sol.reorderListArray(h2);
            int[] r2 = listToArray(h2);
            
            // Approach 3: Recursive
            ListNode h3 = createList(testCases[i]);
            sol.reorderListRecursive(h3);
            int[] r3 = listToArray(h3);
            
            boolean m1 = arraysEqual(r1, expected[i]);
            boolean m2 = arraysEqual(r2, expected[i]);
            boolean m3 = arraysEqual(r3, expected[i]);
            boolean match = m1 && m2 && m3;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (A1:" + m1 + " A2:" + m2 + " A3:" + m3 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}