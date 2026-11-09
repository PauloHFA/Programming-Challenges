/**
 * LeetCode 141 - Linked List Cycle
 * 
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be reached again
 * by continuously following the next pointer. Internally, pos is used to denote the index of the
 * node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * 
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 * 
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 * 
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 * 
 * Constraints:
 * The number of the nodes in the list is in the range [0, 10^4].
 * -10^5 <= Node.val <= 10^5
 * pos is -1 or a valid index in the linked-list.
 * 
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class Solution {
    
    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    
    /**
     * Approach 1: Floyd's Cycle Detection Algorithm (Tortoise and Hare) - O(1) Space
     * 
     * Use two pointers: slow moves 1 step, fast moves 2 steps.
     * If there's a cycle, they will eventually meet.
     * If fast reaches null, there's no cycle.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Hash Set - O(n) Space
     * 
     * Store visited nodes in a HashSet. If we encounter a node already in the set, there's a cycle.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public boolean hasCycleHashSet(ListNode head) {
        java.util.Set<ListNode> visited = new java.util.HashSet<>();
        ListNode curr = head;
        while (curr != null) {
            if (visited.contains(curr)) {
                return true;
            }
            visited.add(curr);
            curr = curr.next;
        }
        return false;
    }
    
    /**
     * Approach 3: Modify List (Mark Visited) - O(1) Space but modifies list
     * 
     * Mark visited nodes by setting a special value or reversing pointers.
     * Not recommended for production as it modifies the input.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public boolean hasCycleModify(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == Integer.MIN_VALUE) {
                return true;
            }
            curr.val = Integer.MIN_VALUE;
            curr = curr.next;
        }
        return false;
    }
    
    /**
     * Approach 4: Brent's Algorithm (Alternative Cycle Detection)
     * 
     * Similar to Floyd's but uses power-of-two steps for the fast pointer.
     * Can be slightly faster in practice.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public boolean hasCycleBrent(ListNode head) {
        if (head == null || head.next == null) return false;
        
        ListNode slow = head;
        ListNode fast = head.next;
        int power = 1;
        int length = 1;
        
        while (fast != null && fast != slow) {
            if (length == power) {
                power *= 2;
                length = 0;
                slow = fast;
            }
            fast = fast.next;
            length++;
        }
        
        return fast == slow;
    }
    
    // Helper methods for testing
    private static ListNode createList(int[] values, int pos) {
        if (values == null || values.length == 0) return null;
        
        ListNode[] nodes = new ListNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new ListNode(values[i]);
        }
        
        for (int i = 0; i < values.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        if (pos >= 0 && pos < values.length) {
            nodes[values.length - 1].next = nodes[pos];
        }
        
        return nodes[0];
    }
    
    private static void printList(ListNode head, int maxNodes) {
        ListNode curr = head;
        int count = 0;
        System.out.print("  List: ");
        while (curr != null && count < maxNodes) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
            count++;
        }
        if (curr != null) {
            System.out.print("... (cycle detected)");
        } else {
            System.out.print("null");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Linked List Cycle Tests ===\n");
        
        // Test 1: Example 1 - Cycle at position 1
        System.out.println("Test 1: [3,2,0,-4], pos = 1 (cycle at index 1)");
        ListNode head1 = createList(new int[]{3, 2, 0, -4}, 1);
        printList(head1, 10);
        boolean expected1 = true;
        boolean result1 = sol.hasCycle(head1);
        System.out.println("  Expected: " + expected1);
        System.out.println("  Floyd:    " + result1 + (result1 == expected1 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head1));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head1));
        
        // Test 2: Example 2 - Cycle at position 0
        System.out.println("\nTest 2: [1,2], pos = 0 (cycle at index 0)");
        ListNode head2 = createList(new int[]{1, 2}, 0);
        printList(head2, 10);
        boolean expected2 = true;
        boolean result2 = sol.hasCycle(head2);
        System.out.println("  Expected: " + expected2);
        System.out.println("  Floyd:    " + result2 + (result2 == expected2 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head2));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head2));
        
        // Test 3: Example 3 - No cycle
        System.out.println("\nTest 3: [1], pos = -1 (no cycle)");
        ListNode head3 = createList(new int[]{1}, -1);
        printList(head3, 10);
        boolean expected3 = false;
        boolean result3 = sol.hasCycle(head3);
        System.out.println("  Expected: " + expected3);
        System.out.println("  Floyd:    " + result3 + (result3 == expected3 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head3));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head3));
        
        // Test 4: Empty list
        System.out.println("\nTest 4: Empty list");
        ListNode head4 = null;
        boolean expected4 = false;
        boolean result4 = sol.hasCycle(head4);
        System.out.println("  Expected: " + expected4);
        System.out.println("  Floyd:    " + result4 + (result4 == expected4 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head4));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head4));
        
        // Test 5: Two nodes, no cycle
        System.out.println("\nTest 5: [1,2], pos = -1 (no cycle)");
        ListNode head5 = createList(new int[]{1, 2}, -1);
        printList(head5, 10);
        boolean expected5 = false;
        boolean result5 = sol.hasCycle(head5);
        System.out.println("  Expected: " + expected5);
        System.out.println("  Floyd:    " + result5 + (result5 == expected5 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head5));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head5));
        
        // Test 6: Single node, self cycle
        System.out.println("\nTest 6: [1], pos = 0 (self cycle)");
        ListNode head6 = createList(new int[]{1}, 0);
        printList(head6, 10);
        boolean expected6 = true;
        boolean result6 = sol.hasCycle(head6);
        System.out.println("  Expected: " + expected6);
        System.out.println("  Floyd:    " + result6 + (result6 == expected6 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head6));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head6));
        
        // Test 7: Long list with cycle at end
        System.out.println("\nTest 7: Long list [1,2,3,4,5,6,7,8,9,10], pos = 5");
        ListNode head7 = createList(new int[]{1,2,3,4,5,6,7,8,9,10}, 5);
        printList(head7, 15);
        boolean expected7 = true;
        boolean result7 = sol.hasCycle(head7);
        System.out.println("  Expected: " + expected7);
        System.out.println("  Floyd:    " + result7 + (result7 == expected7 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head7));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head7));
        
        // Test 8: Long list without cycle
        System.out.println("\nTest 8: Long list [1,2,3,4,5,6,7,8,9,10], pos = -1");
        ListNode head8 = createList(new int[]{1,2,3,4,5,6,7,8,9,10}, -1);
        printList(head8, 15);
        boolean expected8 = false;
        boolean result8 = sol.hasCycle(head8);
        System.out.println("  Expected: " + expected8);
        System.out.println("  Floyd:    " + result8 + (result8 == expected8 ? " ✓" : " ✗"));
        System.out.println("  HashSet:  " + sol.hasCycleHashSet(head8));
        System.out.println("  Brent:    " + sol.hasCycleBrent(head8));
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification of all approaches");
        int[][] testValues = {
            {3, 2, 0, -4},
            {1, 2},
            {1},
            {},
            {1, 2},
            {1},
            {1,2,3,4,5,6,7,8,9,10},
            {1,2,3,4,5,6,7,8,9,10}
        };
        int[] testPos = {1, 0, -1, -1, -1, 0, 5, -1};
        boolean[] expected = {true, true, false, false, false, true, true, false};
        
        boolean allMatch = true;
        for (int i = 0; i < testValues.length; i++) {
            ListNode h = createList(testValues[i], testPos[i]);
            boolean r1 = sol.hasCycle(h);
            boolean r2 = sol.hasCycleHashSet(h);
            boolean r3 = sol.hasCycleBrent(h);
            boolean match = (r1 == expected[i] && r2 == expected[i] && r3 == expected[i]);
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Floyd:" + r1 + " HS:" + r2 + " Brent:" + r3 + " Exp:" + expected[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}