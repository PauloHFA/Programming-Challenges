/**
 * LeetCode 0021: Merge Two Sorted Lists
 * Difficulty: Easy
 * Category: Linked List
 * 
 * Approach: Iterative with Dummy Node
 * Time Complexity: O(n + m)
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

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Attach remaining nodes
        current.next = (list1 != null) ? list1 : list2;

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
        ListNode l1_1 = fromArray(1, 2, 4);
        ListNode l2_1 = fromArray(1, 3, 4);
        ListNode expected1 = fromArray(1, 1, 2, 3, 4, 4);
        ListNode result1 = solver.mergeTwoLists(l1_1, l2_1);
        System.out.println("Test 1: " + toListString(result1) + " (Expected: [1, 1, 2, 3, 4, 4])");
        if (!isEqual(result1, expected1)) throw new AssertionError("Test 1 failed!");

        // Test Case 2: Both empty
        ListNode l1_2 = fromArray();
        ListNode l2_2 = fromArray();
        ListNode expected2 = fromArray();
        ListNode result2 = solver.mergeTwoLists(l1_2, l2_2);
        System.out.println("Test 2: " + toListString(result2) + " (Expected: [])");
        if (!isEqual(result2, expected2)) throw new AssertionError("Test 2 failed!");

        // Test Case 3: One empty
        ListNode l1_3 = fromArray();
        ListNode l2_3 = fromArray(0);
        ListNode expected3 = fromArray(0);
        ListNode result3 = solver.mergeTwoLists(l1_3, l2_3);
        System.out.println("Test 3: " + toListString(result3) + " (Expected: [0])");
        if (!isEqual(result3, expected3)) throw new AssertionError("Test 3 failed!");

        // Test Case 4: One list longer
        ListNode l1_4 = fromArray(1, 3, 5, 7, 9);
        ListNode l2_4 = fromArray(2, 4, 6);
        ListNode expected4 = fromArray(1, 2, 3, 4, 5, 6, 7, 9);
        ListNode result4 = solver.mergeTwoLists(l1_4, l2_4);
        System.out.println("Test 4: " + toListString(result4) + " (Expected: [1, 2, 3, 4, 5, 6, 7, 9])");
        if (!isEqual(result4, expected4)) throw new AssertionError("Test 4 failed!");

        // Test Case 5: All same values
        ListNode l1_5 = fromArray(2, 2, 2);
        ListNode l2_5 = fromArray(2, 2);
        ListNode expected5 = fromArray(2, 2, 2, 2, 2);
        ListNode result5 = solver.mergeTwoLists(l1_5, l2_5);
        System.out.println("Test 5: " + toListString(result5) + " (Expected: [2, 2, 2, 2, 2])");
        if (!isEqual(result5, expected5)) throw new AssertionError("Test 5 failed!");

        // Test Case 6: Negative values
        ListNode l1_6 = fromArray(-10, -5, 0);
        ListNode l2_6 = fromArray(-7, -3, 2);
        ListNode expected6 = fromArray(-10, -7, -5, -3, 0, 2);
        ListNode result6 = solver.mergeTwoLists(l1_6, l2_6);
        System.out.println("Test 6: " + toListString(result6) + " (Expected: [-10, -7, -5, -3, 0, 2])");
        if (!isEqual(result6, expected6)) throw new AssertionError("Test 6 failed!");

        // Test Case 7: Single element each
        ListNode l1_7 = fromArray(5);
        ListNode l2_7 = fromArray(3);
        ListNode expected7 = fromArray(3, 5);
        ListNode result7 = solver.mergeTwoLists(l1_7, l2_7);
        System.out.println("Test 7: " + toListString(result7) + " (Expected: [3, 5])");
        if (!isEqual(result7, expected7)) throw new AssertionError("Test 7 failed!");

        System.out.println("\nAll test cases passed successfully!");
    }
}