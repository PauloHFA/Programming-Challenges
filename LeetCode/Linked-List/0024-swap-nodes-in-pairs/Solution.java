/**
 * LeetCode 0024: Swap Nodes in Pairs
 * Difficulty: Medium
 * Category: Linked List
 * 
 * Approach: Iterative with Dummy Node
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

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            // Swap
            first.next = second.next;
            second.next = first;
            prev.next = second;

            // Move prev to the next pair
            prev = first;
        }

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
        ListNode head1 = fromArray(1, 2, 3, 4);
        ListNode expected1 = fromArray(2, 1, 4, 3);
        ListNode result1 = solver.swapPairs(head1);
        System.out.println("Test 1: " + toListString(result1) + " (Expected: [2, 1, 4, 3])");
        if (!isEqual(result1, expected1)) throw new AssertionError("Test 1 failed!");

        // Test Case 2: Empty list
        ListNode head2 = fromArray();
        ListNode expected2 = fromArray();
        ListNode result2 = solver.swapPairs(head2);
        System.out.println("Test 2: " + toListString(result2) + " (Expected: [])");
        if (!isEqual(result2, expected2)) throw new AssertionError("Test 2 failed!");

        // Test Case 3: Single node
        ListNode head3 = fromArray(1);
        ListNode expected3 = fromArray(1);
        ListNode result3 = solver.swapPairs(head3);
        System.out.println("Test 3: " + toListString(result3) + " (Expected: [1])");
        if (!isEqual(result3, expected3)) throw new AssertionError("Test 3 failed!");

        // Test Case 4: Two nodes
        ListNode head4 = fromArray(1, 2);
        ListNode expected4 = fromArray(2, 1);
        ListNode result4 = solver.swapPairs(head4);
        System.out.println("Test 4: " + toListString(result4) + " (Expected: [2, 1])");
        if (!isEqual(result4, expected4)) throw new AssertionError("Test 4 failed!");

        // Test Case 5: Odd number of nodes
        ListNode head5 = fromArray(1, 2, 3, 4, 5);
        ListNode expected5 = fromArray(2, 1, 4, 3, 5);
        ListNode result5 = solver.swapPairs(head5);
        System.out.println("Test 5: " + toListString(result5) + " (Expected: [2, 1, 4, 3, 5])");
        if (!isEqual(result5, expected5)) throw new AssertionError("Test 5 failed!");

        // Test Case 6: Three nodes
        ListNode head6 = fromArray(1, 2, 3);
        ListNode expected6 = fromArray(2, 1, 3);
        ListNode result6 = solver.swapPairs(head6);
        System.out.println("Test 6: " + toListString(result6) + " (Expected: [2, 1, 3])");
        if (!isEqual(result6, expected6)) throw new AssertionError("Test 6 failed!");

        // Test Case 7: Longer list
        ListNode head7 = fromArray(1, 2, 3, 4, 5, 6, 7, 8);
        ListNode expected7 = fromArray(2, 1, 4, 3, 6, 5, 8, 7);
        ListNode result7 = solver.swapPairs(head7);
        System.out.println("Test 7: " + toListString(result7) + " (Expected: [2, 1, 4, 3, 6, 5, 8, 7])");
        if (!isEqual(result7, expected7)) throw new AssertionError("Test 7 failed!");

        System.out.println("\nAll test cases passed successfully!");
    }
}