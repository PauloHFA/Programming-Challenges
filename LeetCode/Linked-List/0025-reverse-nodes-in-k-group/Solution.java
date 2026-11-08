/**
 * LeetCode 0025: Reverse Nodes in k-Group
 * Difficulty: Hard
 * Category: Linked List
 * 
 * Approach: Iterative with Group Reversal
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

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;

        while (true) {
            // Check if there are k nodes remaining
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if (kthNode == null) {
                break;
            }

            ListNode nextGroupStart = kthNode.next;

            // Reverse the k nodes
            ListNode prev = nextGroupStart;
            ListNode current = prevGroupEnd.next;

            while (current != nextGroupStart) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }

            // Connect previous group to reversed group
            ListNode temp = prevGroupEnd.next; // This will be the new end of reversed group
            prevGroupEnd.next = kthNode;
            prevGroupEnd = temp;
        }

        return dummy.next;
    }

    private ListNode getKthNode(ListNode start, int k) {
        ListNode current = start;
        for (int i = 0; i < k; i++) {
            current = current.next;
            if (current == null) {
                return null;
            }
        }
        return current;
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
        int k1 = 2;
        ListNode expected1 = fromArray(2, 1, 4, 3, 5);
        ListNode result1 = solver.reverseKGroup(head1, k1);
        System.out.println("Test 1 (k=2): " + toListString(result1) + " (Expected: [2, 1, 4, 3, 5])");
        if (!isEqual(result1, expected1)) throw new AssertionError("Test 1 failed!");

        // Test Case 2
        ListNode head2 = fromArray(1, 2, 3, 4, 5);
        int k2 = 3;
        ListNode expected2 = fromArray(3, 2, 1, 4, 5);
        ListNode result2 = solver.reverseKGroup(head2, k2);
        System.out.println("Test 2 (k=3): " + toListString(result2) + " (Expected: [3, 2, 1, 4, 5])");
        if (!isEqual(result2, expected2)) throw new AssertionError("Test 2 failed!");

        // Test Case 3: k = 1 (no change)
        ListNode head3 = fromArray(1, 2, 3);
        int k3 = 1;
        ListNode expected3 = fromArray(1, 2, 3);
        ListNode result3 = solver.reverseKGroup(head3, k3);
        System.out.println("Test 3 (k=1): " + toListString(result3) + " (Expected: [1, 2, 3])");
        if (!isEqual(result3, expected3)) throw new AssertionError("Test 3 failed!");

        // Test Case 4: k equals length
        ListNode head4 = fromArray(1, 2, 3, 4);
        int k4 = 4;
        ListNode expected4 = fromArray(4, 3, 2, 1);
        ListNode result4 = solver.reverseKGroup(head4, k4);
        System.out.println("Test 4 (k=4): " + toListString(result4) + " (Expected: [4, 3, 2, 1])");
        if (!isEqual(result4, expected4)) throw new AssertionError("Test 4 failed!");

        // Test Case 5: k > length (no change)
        ListNode head5 = fromArray(1, 2, 3);
        int k5 = 5;
        ListNode expected5 = fromArray(1, 2, 3);
        ListNode result5 = solver.reverseKGroup(head5, k5);
        System.out.println("Test 5 (k=5): " + toListString(result5) + " (Expected: [1, 2, 3])");
        if (!isEqual(result5, expected5)) throw new AssertionError("Test 5 failed!");

        // Test Case 6: Empty list
        ListNode head6 = fromArray();
        int k6 = 2;
        ListNode expected6 = fromArray();
        ListNode result6 = solver.reverseKGroup(head6, k6);
        System.out.println("Test 6 (empty): " + toListString(result6) + " (Expected: [])");
        if (!isEqual(result6, expected6)) throw new AssertionError("Test 6 failed!");

        // Test Case 7: Single node
        ListNode head7 = fromArray(1);
        int k7 = 2;
        ListNode expected7 = fromArray(1);
        ListNode result7 = solver.reverseKGroup(head7, k7);
        System.out.println("Test 7 (single): " + toListString(result7) + " (Expected: [1])");
        if (!isEqual(result7, expected7)) throw new AssertionError("Test 7 failed!");

        // Test Case 8: Multiple groups
        ListNode head8 = fromArray(1, 2, 3, 4, 5, 6, 7, 8);
        int k8 = 3;
        ListNode expected8 = fromArray(3, 2, 1, 6, 5, 4, 7, 8);
        ListNode result8 = solver.reverseKGroup(head8, k8);
        System.out.println("Test 8 (k=3): " + toListString(result8) + " (Expected: [3, 2, 1, 6, 5, 4, 7, 8])");
        if (!isEqual(result8, expected8)) throw new AssertionError("Test 8 failed!");

        System.out.println("\nAll test cases passed successfully!");
    }
}