/**
 * LeetCode 142 - Linked List Cycle II
 * 
 * Given the head of a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be reached again
 * by continuously following the next pointer. Internally, pos is used to denote the index of the
 * node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle.
 * Note that pos is not passed as a parameter.
 * 
 * Do not modify the linked list.
 * 
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 * 
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 * 
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
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
     * Approach 1: Floyd's Algorithm (Tortoise and Hare) - O(1) Space
     * 
     * Phase 1: Detect cycle using slow (1 step) and fast (2 steps) pointers.
     * Phase 2: Find cycle start. When they meet, reset one pointer to head.
     * Move both at 1 step. They meet at cycle start.
     * 
     * Proof: Let distance from head to cycle start = a
     * Distance from cycle start to meeting point = b
     * Cycle length = c
     * Slow travels: a + b
     * Fast travels: a + b + k*c (k loops)
     * Since fast = 2 * slow: 2(a + b) = a + b + k*c => a + b = k*c => a = k*c - b
     * So distance from head to cycle start = distance from meeting point to cycle start (going forward)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Phase 1: Detect cycle
        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        if (!hasCycle) return null;
        
        // Phase 2: Find cycle start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    /**
     * Approach 2: Hash Set - O(n) Space
     * 
     * Store visited nodes in a HashSet. First node encountered twice is the cycle start.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public ListNode detectCycleHashSet(ListNode head) {
        java.util.Set<ListNode> visited = new java.util.HashSet<>();
        ListNode curr = head;
        while (curr != null) {
            if (visited.contains(curr)) {
                return curr;
            }
            visited.add(curr);
            curr = curr.next;
        }
        return null;
    }
    
    /**
     * Approach 3: Brent's Algorithm - O(1) Space
     * 
     * Alternative cycle detection that can also find cycle start.
     * Uses power-of-two steps for fast pointer.
         * After detection, calculates cycle length and uses two pointers with gap.
         * 
         * Time Complexity: O(n)
         * Space Complexity: O(1)
         */
        public ListNode detectCycleBrent(ListNode head) {
            if (head == null || head.next == null) return null;
        
            ListNode slow = head;
            ListNode fast = head.next;
            int power = 1;
            int length = 1;
        
            // Phase 1: Detect cycle
            while (fast != null && fast != slow) {
                if (length == power) {
                    power *= 2;
                    length = 0;
                    slow = fast;
                }
                fast = fast.next;
                length++;
            }
        
            if (fast == null) return null; // No cycle
        
            // Phase 2: Calculate cycle length
            int cycleLength = 1;
            fast = fast.next;
            while (fast != slow) {
                fast = fast.next;
                cycleLength++;
        }
        
            // Phase 3: Find cycle start using two pointers with cycleLength gap
            slow = head;
            fast = head;
            for (int i = 0; i < cycleLength; i++) {
                fast = fast.next;
            }
        
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
        
            return slow;
        }
    
    /**
     * Approach 4: Two Pointers with Cycle Length Calculation
     * 
     * First find meeting point, then calculate cycle length,
     * then use two pointers with cycle length gap to find start.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public ListNode detectCycleWithLength(ListNode head) {
        if (head == null || head.next == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Find meeting point
        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        if (!hasCycle) return null;
        
        // Calculate cycle length
        int cycleLength = 1;
        fast = fast.next;
        while (fast != slow) {
            fast = fast.next;
            cycleLength++;
        }
        
        // Find cycle start using two pointers with cycleLength gap
        slow = head;
        fast = head;
        for (int i = 0; i < cycleLength; i++) {
            fast = fast.next;
        }
        
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
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
    
    private static int getCycleStartIndex(ListNode head, ListNode cycleStart) {
        if (cycleStart == null) return -1;
        int index = 0;
        ListNode curr = head;
        while (curr != cycleStart) {
            curr = curr.next;
            index++;
        }
        return index;
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
            System.out.print("... (cycle)");
        } else {
            System.out.print("null");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Linked List Cycle II Tests ===\n");
        
        // Test 1: Example 1 - Cycle at position 1
        System.out.println("Test 1: [3,2,0,-4], pos = 1 (cycle starts at index 1, value 2)");
        ListNode head1 = createList(new int[]{3, 2, 0, -4}, 1);
        printList(head1, 10);
        ListNode result1 = sol.detectCycle(head1);
        int expectedIdx1 = 1;
        int actualIdx1 = getCycleStartIndex(head1, result1);
        System.out.println("  Expected index: " + expectedIdx1 + " (value 2)");
        System.out.println("  Floyd:          index " + actualIdx1 + (actualIdx1 == expectedIdx1 ? " ✓" : " ✗") + 
            (result1 != null ? " val=" + result1.val : ""));
        System.out.println("  HashSet:        index " + getCycleStartIndex(head1, sol.detectCycleHashSet(head1)));
        System.out.println("  Brent:          index " + getCycleStartIndex(head1, sol.detectCycleBrent(head1)));
        System.out.println("  WithLength:     index " + getCycleStartIndex(head1, sol.detectCycleWithLength(head1)));
        
        // Test 2: Example 2 - Cycle at position 0
        System.out.println("\nTest 2: [1,2], pos = 0 (cycle starts at index 0, value 1)");
        ListNode head2 = createList(new int[]{1, 2}, 0);
        printList(head2, 10);
        ListNode result2 = sol.detectCycle(head2);
        int expectedIdx2 = 0;
        int actualIdx2 = getCycleStartIndex(head2, result2);
        System.out.println("  Expected index: " + expectedIdx2 + " (value 1)");
        System.out.println("  Floyd:          index " + actualIdx2 + (actualIdx2 == expectedIdx2 ? " ✓" : " ✗") + 
            (result2 != null ? " val=" + result2.val : ""));
        System.out.println("  HashSet:        index " + getCycleStartIndex(head2, sol.detectCycleHashSet(head2)));
        System.out.println("  Brent:          index " + getCycleStartIndex(head2, sol.detectCycleBrent(head2)));
        System.out.println("  WithLength:     index " + getCycleStartIndex(head2, sol.detectCycleWithLength(head2)));
        
        // Test 3: Example 3 - No cycle
        System.out.println("\nTest 3: [1], pos = -1 (no cycle)");
        ListNode head3 = createList(new int[]{1}, -1);
        printList(head3, 10);
        ListNode result3 = sol.detectCycle(head3);
        System.out.println("  Expected: null");
        System.out.println("  Floyd:    " + (result3 == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  HashSet:  " + (sol.detectCycleHashSet(head3) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  Brent:    " + (sol.detectCycleBrent(head3) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  WithLength:" + (sol.detectCycleWithLength(head3) == null ? "null ✓" : "FAIL ✗"));
        
        // Test 4: Empty list
        System.out.println("\nTest 4: Empty list");
        ListNode head4 = null;
        ListNode result4 = sol.detectCycle(head4);
        System.out.println("  Expected: null");
        System.out.println("  Floyd:    " + (result4 == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  HashSet:  " + (sol.detectCycleHashSet(head4) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  Brent:    " + (sol.detectCycleBrent(head4) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  WithLength:" + (sol.detectCycleWithLength(head4) == null ? "null ✓" : "FAIL ✗"));
        
        // Test 5: Two nodes, no cycle
        System.out.println("\nTest 5: [1,2], pos = -1 (no cycle)");
        ListNode head5 = createList(new int[]{1, 2}, -1);
        printList(head5, 10);
        ListNode result5 = sol.detectCycle(head5);
        System.out.println("  Expected: null");
        System.out.println("  Floyd:    " + (result5 == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  HashSet:  " + (sol.detectCycleHashSet(head5) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  Brent:    " + (sol.detectCycleBrent(head5) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  WithLength:" + (sol.detectCycleWithLength(head5) == null ? "null ✓" : "FAIL ✗"));
        
        // Test 6: Single node, self cycle
        System.out.println("\nTest 6: [1], pos = 0 (self cycle at index 0)");
        ListNode head6 = createList(new int[]{1}, 0);
        printList(head6, 10);
        ListNode result6 = sol.detectCycle(head6);
        int expectedIdx6 = 0;
        int actualIdx6 = getCycleStartIndex(head6, result6);
        System.out.println("  Expected index: " + expectedIdx6 + " (value 1)");
        System.out.println("  Floyd:          index " + actualIdx6 + (actualIdx6 == expectedIdx6 ? " ✓" : " ✗") + 
            (result6 != null ? " val=" + result6.val : ""));
        System.out.println("  HashSet:        index " + getCycleStartIndex(head6, sol.detectCycleHashSet(head6)));
        System.out.println("  Brent:          index " + getCycleStartIndex(head6, sol.detectCycleBrent(head6)));
        System.out.println("  WithLength:     index " + getCycleStartIndex(head6, sol.detectCycleWithLength(head6)));
        
        // Test 7: Long list with cycle at middle
        System.out.println("\nTest 7: [1,2,3,4,5,6,7,8,9,10], pos = 5 (cycle at index 5, value 6)");
        ListNode head7 = createList(new int[]{1,2,3,4,5,6,7,8,9,10}, 5);
        printList(head7, 15);
        ListNode result7 = sol.detectCycle(head7);
        int expectedIdx7 = 5;
        int actualIdx7 = getCycleStartIndex(head7, result7);
        System.out.println("  Expected index: " + expectedIdx7 + " (value 6)");
        System.out.println("  Floyd:          index " + actualIdx7 + (actualIdx7 == expectedIdx7 ? " ✓" : " ✗") + 
            (result7 != null ? " val=" + result7.val : ""));
        System.out.println("  HashSet:        index " + getCycleStartIndex(head7, sol.detectCycleHashSet(head7)));
        System.out.println("  Brent:          index " + getCycleStartIndex(head7, sol.detectCycleBrent(head7)));
        System.out.println("  WithLength:     index " + getCycleStartIndex(head7, sol.detectCycleWithLength(head7)));
        
        // Test 8: Long list without cycle
        System.out.println("\nTest 8: [1,2,3,4,5,6,7,8,9,10], pos = -1 (no cycle)");
        ListNode head8 = createList(new int[]{1,2,3,4,5,6,7,8,9,10}, -1);
        printList(head8, 15);
        ListNode result8 = sol.detectCycle(head8);
        System.out.println("  Expected: null");
        System.out.println("  Floyd:    " + (result8 == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  HashSet:  " + (sol.detectCycleHashSet(head8) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  Brent:    " + (sol.detectCycleBrent(head8) == null ? "null ✓" : "FAIL ✗"));
        System.out.println("  WithLength:" + (sol.detectCycleWithLength(head8) == null ? "null ✓" : "FAIL ✗"));
        
        // Test 9: Cycle at last node (tail points to last)
        System.out.println("\nTest 9: [1,2,3], pos = 2 (cycle at last node, index 2)");
        ListNode head9 = createList(new int[]{1, 2, 3}, 2);
        printList(head9, 10);
        ListNode result9 = sol.detectCycle(head9);
        int expectedIdx9 = 2;
        int actualIdx9 = getCycleStartIndex(head9, result9);
        System.out.println("  Expected index: " + expectedIdx9 + " (value 3)");
        System.out.println("  Floyd:          index " + actualIdx9 + (actualIdx9 == expectedIdx9 ? " ✓" : " ✗") + 
            (result9 != null ? " val=" + result9.val : ""));
        System.out.println("  HashSet:        index " + getCycleStartIndex(head9, sol.detectCycleHashSet(head9)));
        System.out.println("  Brent:          index " + getCycleStartIndex(head9, sol.detectCycleBrent(head9)));
        System.out.println("  WithLength:     index " + getCycleStartIndex(head9, sol.detectCycleWithLength(head9)));
        
        // Test 10: Cross verification
        System.out.println("\nTest 10: Cross verification of all approaches");
        int[][] testValues = {
            {3, 2, 0, -4},
            {1, 2},
            {1},
            {},
            {1, 2},
            {1},
            {1,2,3,4,5,6,7,8,9,10},
            {1,2,3,4,5,6,7,8,9,10},
            {1, 2, 3}
        };
        int[] testPos = {1, 0, -1, -1, -1, 0, 5, -1, 2};
        int[] expectedIdx = {1, 0, -1, -1, -1, 0, 5, -1, 2};
        
        boolean allMatch = true;
        for (int i = 0; i < testValues.length; i++) {
            ListNode h = createList(testValues[i], testPos[i]);
            ListNode r1 = sol.detectCycle(h);
            ListNode r2 = sol.detectCycleHashSet(h);
            ListNode r3 = sol.detectCycleBrent(h);
            ListNode r4 = sol.detectCycleWithLength(h);
            
            int idx1 = getCycleStartIndex(h, r1);
            int idx2 = getCycleStartIndex(h, r2);
            int idx3 = getCycleStartIndex(h, r3);
            int idx4 = getCycleStartIndex(h, r4);
            
            boolean match = (idx1 == expectedIdx[i] && idx2 == expectedIdx[i] && idx3 == expectedIdx[i] && idx4 == expectedIdx[i]);
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Floyd:" + idx1 + " HS:" + idx2 + " Brent:" + idx3 + " Len:" + idx4 + " Exp:" + expectedIdx[i] + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}