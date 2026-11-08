/**
 * LeetCode 0019: Remove Nth Node From End of List
 * Difficulty: Medium
 * Category: Linked List
 * 
 * Approach: Two Pointers (Fast and Slow)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        // Move fast pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both pointers until fast reaches the end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the nth node from end
        slow.next = slow.next.next;

        return dummy.next;
    }

    // Helper method to create a linked list from an array
    public static ListNode fromArray(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int v : values) {
            current.next = new ListNode(v);
            current = current.next;
        }
        return dummy.next;
    }

    // Helper method to convert a linked list to a formatted string
    public static String toListString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper method to verify if two linked lists have identical values
    public static boolean isEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        ListNode head1 = fromArray(1, 2, 3, 4, 5);
        int n1 = 2;
        ListNode expected1 = fromArray(1, 2, 3, 5);
        ListNode result1 = solver.removeNthFromEnd(head1, n1);
        System.out.println("Test 1: " + toListString(result1) + " (Expected: [1, 2, 3, 5])");
        if (!isEqual(result1, expected1)) {
            throw new AssertionError("Test 1 failed!");
        }

        // Test Case 2: Remove head
        ListNode head2 = fromArray(1);
        int n2 = 1;
        ListNode expected2 = fromArray();
        ListNode result2 = solver.removeNthFromEnd(head2, n2);
        System.out.println("Test 2: " + toListString(result2) + " (Expected: [])");
        if (!isEqual(result2, expected2)) {
            throw new AssertionError("Test 2 failed!");
        }

        // Test Case 3: Remove last
        ListNode head3 = fromArray(1, 2);
        int n3 = 1;
        ListNode expected3 = fromArray(1);
        ListNode result3 = solver.removeNthFromEnd(head3, n3);
        System.out.println("Test 3: " + toListString(result3) + " (Expected: [1])");
        if (!isEqual(result3, expected3)) {
            throw new AssertionError("Test 3 failed!");
        }

        // Test Case 4: Remove first of two
        ListNode head4 = fromArray(1, 2);
        int n4 = 2;
        ListNode expected4 = fromArray(2);
        ListNode result4 = solver.removeNthFromEnd(head4, n4);
        System.out.println("Test 4: " + toListString(result4) + " (Expected: [2])");
        if (!isEqual(result4, expected4)) {
            throw new AssertionError("Test 4 failed!");
        }

        // Test Case 5: Longer list
        ListNode head5 = fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int n5 = 5;
                ListNode expected5 = fromArray(1, 2, 3, 4, 5, 7, 8, 9, 10);
        ListNode result5 = solver.removeNthFromEnd(head5, n5);
                System.out.println("Test 5: " + toListString(result5) + " (Expected: [1, 2, 3, 4, 5, 7, 8, 9, 10])");
        if (!isEqual(result5, expected5)) {
            throw new AssertionError("Test 5 failed!");
        }

        // Test Case 6: Remove from middle
        ListNode head6 = fromArray(1, 2, 3, 4, 5);
        int n6 = 3;
        ListNode expected6 = fromArray(1, 2, 4, 5);
        ListNode result6 = solver.removeNthFromEnd(head6, n6);
        System.out.println("Test 6: " + toListString(result6) + " (Expected: [1, 2, 4, 5])");
        if (!isEqual(result6, expected6)) {
            throw new AssertionError("Test 6 failed!");
        }

        // Test Case 7: n equals length (remove head)
        ListNode head7 = fromArray(1, 2, 3);
        int n7 = 3;
        ListNode expected7 = fromArray(2, 3);
        ListNode result7 = solver.removeNthFromEnd(head7, n7);
        System.out.println("Test 7: " + toListString(result7) + " (Expected: [2, 3])");
        if (!isEqual(result7, expected7)) {
            throw new AssertionError("Test 7 failed!");
        }

        // Test Case 8: Two nodes, remove first
        ListNode head8 = fromArray(1, 2);
        int n8 = 2;
        ListNode expected8 = fromArray(2);
        ListNode result8 = solver.removeNthFromEnd(head8, n8);
        System.out.println("Test 8: " + toListString(result8) + " (Expected: [2])");
        if (!isEqual(result8, expected8)) {
            throw new AssertionError("Test 8 failed!");
        }

        System.out.println("\nAll test cases passed successfully!");
    }
}