import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LeetCode 0023: Merge k Sorted Lists
 * Difficulty: Hard
 * Category: Linked List / Heap (Priority Queue)
 * 
 * Approach: Min Heap (Priority Queue)
 * Time Complexity: O(N log k) where N is total nodes, k is number of lists
 * Space Complexity: O(k) for the heap
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

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Min heap to store the smallest node from each list
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        // Add the head of each non-null list to the heap
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            current.next = smallest;
            current = current.next;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
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
        ListNode[] lists1 = {
            fromArray(1, 4, 5),
            fromArray(1, 3, 4),
            fromArray(2, 6)
        };
        ListNode expected1 = fromArray(1, 1, 2, 3, 4, 4, 5, 6);
        ListNode result1 = solver.mergeKLists(lists1);
        System.out.println("Test 1: " + toListString(result1) + " (Expected: [1, 1, 2, 3, 4, 4, 5, 6])");
        if (!isEqual(result1, expected1)) throw new AssertionError("Test 1 failed!");

        // Test Case 2: Empty array
        ListNode[] lists2 = {};
        ListNode result2 = solver.mergeKLists(lists2);
        System.out.println("Test 2: " + toListString(result2) + " (Expected: [])");
        if (result2 != null) throw new AssertionError("Test 2 failed!");

        // Test Case 3: Array with one empty list
        ListNode[] lists3 = { fromArray() };
        ListNode result3 = solver.mergeKLists(lists3);
        System.out.println("Test 3: " + toListString(result3) + " (Expected: [])");
        if (result3 != null) throw new AssertionError("Test 3 failed!");

        // Test Case 4: Single list
        ListNode[] lists4 = { fromArray(1, 2, 3) };
        ListNode expected4 = fromArray(1, 2, 3);
        ListNode result4 = solver.mergeKLists(lists4);
        System.out.println("Test 4: " + toListString(result4) + " (Expected: [1, 2, 3])");
        if (!isEqual(result4, expected4)) throw new AssertionError("Test 4 failed!");

        // Test Case 5: Multiple lists with duplicates
        ListNode[] lists5 = {
            fromArray(1, 1, 2),
            fromArray(1, 2, 2),
            fromArray(2, 3)
        };
        ListNode expected5 = fromArray(1, 1, 1, 2, 2, 2, 2, 3);
        ListNode result5 = solver.mergeKLists(lists5);
        System.out.println("Test 5: " + toListString(result5) + " (Expected: [1, 1, 1, 2, 2, 2, 2, 3])");
        if (!isEqual(result5, expected5)) throw new AssertionError("Test 5 failed!");

        // Test Case 6: Negative values
        ListNode[] lists6 = {
            fromArray(-5, -3, 0),
            fromArray(-4, -2, 1),
            fromArray(-1, 2)
        };
        ListNode expected6 = fromArray(-5, -4, -3, -2, -1, 0, 1, 2);
        ListNode result6 = solver.mergeKLists(lists6);
        System.out.println("Test 6: " + toListString(result6) + " (Expected: [-5, -4, -3, -2, -1, 0, 1, 2])");
        if (!isEqual(result6, expected6)) throw new AssertionError("Test 6 failed!");

        // Test Case 7: Some null lists
        ListNode[] lists7 = {
            fromArray(1, 3),
            null,
            fromArray(2, 4),
            null,
            fromArray(5)
        };
        ListNode expected7 = fromArray(1, 2, 3, 4, 5);
        ListNode result7 = solver.mergeKLists(lists7);
        System.out.println("Test 7: " + toListString(result7) + " (Expected: [1, 2, 3, 4, 5])");
        if (!isEqual(result7, expected7)) throw new AssertionError("Test 7 failed!");

        System.out.println("\nAll test cases passed successfully!");
    }
}