/**
 * LeetCode 0061: Rotate List
 * Difficulty: Medium
 * Category: Linked List
 * 
 * Approach: Find length, connect tail to head, find new tail
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Find length and tail
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Connect tail to head to make it circular
        tail.next = head;

        // Find new tail: (length - k % length) steps from head
        k = k % length;
        int stepsToNewTail = length - k;
        ListNode newTail = head;
        for (int i = 1; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // New head is next of new tail
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    // Helper to create list from array
    private static ListNode createList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    // Helper to convert list to array
    private static int[] toArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1
        ListNode head1 = createList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = solver.rotateRight(head1, 2);
        System.out.println("Test 1: " + java.util.Arrays.toString(toArray(result1)) + " (Expected: [4, 5, 1, 2, 3])");

        // Test Case 2
        ListNode head2 = createList(new int[]{0, 1, 2});
        ListNode result2 = solver.rotateRight(head2, 4);
        System.out.println("Test 2: " + java.util.Arrays.toString(toArray(result2)) + " (Expected: [2, 0, 1])");

        // Test Case 3: k = 0
        ListNode head3 = createList(new int[]{1, 2, 3});
        ListNode result3 = solver.rotateRight(head3, 0);
        System.out.println("Test 3: " + java.util.Arrays.toString(toArray(result3)) + " (Expected: [1, 2, 3])");

        // Test Case 4: k >= length
        ListNode head4 = createList(new int[]{1, 2, 3});
        ListNode result4 = solver.rotateRight(head4, 3);
        System.out.println("Test 4: " + java.util.Arrays.toString(toArray(result4)) + " (Expected: [1, 2, 3])");

        // Test Case 5: Single node
        ListNode head5 = createList(new int[]{1});
        ListNode result5 = solver.rotateRight(head5, 10);
        System.out.println("Test 5: " + java.util.Arrays.toString(toArray(result5)) + " (Expected: [1])");

        // Test Case 6: Empty list
        ListNode result6 = solver.rotateRight(null, 1);
        System.out.println("Test 6: " + (result6 == null ? "null" : java.util.Arrays.toString(toArray(result6))) + " (Expected: null)");

        // Test Case 7: Two nodes, k=1
        ListNode head7 = createList(new int[]{1, 2});
        ListNode result7 = solver.rotateRight(head7, 1);
        System.out.println("Test 7: " + java.util.Arrays.toString(toArray(result7)) + " (Expected: [2, 1])");

        // Test Case 8: Large k
        ListNode head8 = createList(new int[]{1, 2, 3, 4, 5});
        ListNode result8 = solver.rotateRight(head8, 1000000007);
        System.out.println("Test 8: " + java.util.Arrays.toString(toArray(result8)) + " (Expected: [4, 5, 1, 2, 3])");
    }
}