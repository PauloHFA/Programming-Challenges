/**
 * LeetCode 160 - Intersection of Two Linked Lists
 * 
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.
 * If the two linked lists have no intersection at all, return null.
 * 
 * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Intersected at '8'
 * 
 * Example 2:
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Intersected at '2'
 * 
 * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: No intersection
 * 
 * Constraints:
 * The number of nodes of listA is in the m.
 * The number of nodes of listB is in the n.
 * 1 <= m, n <= 3 * 10^4
 * 1 <= Node.val <= 10^5
 * 0 <= skipA < m
 * 0 <= skipB < n
 * intersectVal is 0 if listA and listB do not intersect.
 * 
 * Follow up: Could you write a solution that runs in O(m + n) time and uses only O(1) memory?
 */
public class Solution {
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; next = null; }
    }
    
    /**
     * Approach 1: Two Pointers - O(m+n) time, O(1) space
     * 
     * Use two pointers that traverse both lists. When a pointer reaches the end,
     * redirect it to the head of the other list. They will meet at the intersection
     * or both become null.
     * 
     * Time Complexity: O(m + n)
     * Space Complexity: O(1)
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        ListNode pA = headA;
        ListNode pB = headB;
        
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }
        
        return pA;
    }
    
    /**
     * Approach 2: Length Difference - O(m+n) time, O(1) space
     * 
     * Calculate lengths of both lists. Advance the longer list's pointer by the difference.
     * Then move both pointers together until they meet.
     * 
     * Time Complexity: O(m + n)
     * Space Complexity: O(1)
     */
    public ListNode getIntersectionNodeLength(ListNode headA, ListNode headB) {
        int lenA = getLength(headA);
        int lenB = getLength(headB);
        
        ListNode pA = headA;
        ListNode pB = headB;
        
        // Advance the longer list
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) pA = pA.next;
        } else {
            for (int i = 0; i < lenB - lenA; i++) pB = pB.next;
        }
        
        while (pA != pB) {
            pA = pA.next;
            pB = pB.next;
        }
        
        return pA;
    }
    
    private int getLength(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }
    
    /**
     * Approach 3: HashSet - O(m+n) time, O(m) space
     * 
     * Store all nodes of listA in a HashSet, then traverse listB to find first common node.
     * 
     * Time Complexity: O(m + n)
     * Space Complexity: O(m)
     */
    public ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
        java.util.Set<ListNode> set = new java.util.HashSet<>();
        
        ListNode curr = headA;
        while (curr != null) {
            set.add(curr);
            curr = curr.next;
        }
        
        curr = headB;
        while (curr != null) {
            if (set.contains(curr)) return curr;
            curr = curr.next;
        }
        
        return null;
    }
    
    /**
     * Approach 4: Two Pointers with Cycle Detection - O(m+n) time, O(1) space
     * 
     * Connect end of listA to headB, then detect cycle in listA.
     * The cycle entry point is the intersection.
     * Restore the list after detection.
     * 
     * Time Complexity: O(m + n)
     * Space Complexity: O(1)
     */
    public ListNode getIntersectionNodeCycle(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        // Find end of listA and connect to headB
        ListNode tailA = headA;
        while (tailA.next != null) {
            tailA = tailA.next;
        }
        tailA.next = headB;
        
        // Detect cycle in listA
        ListNode slow = headA;
        ListNode fast = headA;
        boolean hasCycle = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        ListNode result = null;
        if (hasCycle) {
            // Find cycle entry
            slow = headA;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            result = slow;
        }
        
        // Restore listA
        tailA.next = null;
        
        return result;
    }
    
    // Test all approaches
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Intersection of Two Linked Lists Tests ===\n");
        
        // Test 1: Example 1 - intersect at 8
        System.out.println("Test 1: listA=[4,1,8,4,5], listB=[5,6,1,8,4,5], intersect at 8");
        ListNode common1 = new ListNode(8);
        common1.next = new ListNode(4);
        common1.next.next = new ListNode(5);
        
        ListNode headA1 = new ListNode(4);
        headA1.next = new ListNode(1);
        headA1.next.next = common1;
        
        ListNode headB1 = new ListNode(5);
        headB1.next = new ListNode(6);
        headB1.next.next = new ListNode(1);
        headB1.next.next.next = common1;
        
        testCase(sol, headA1, headB1, common1, "Test 1");
        
        // Test 2: Example 2 - intersect at 2
        System.out.println("\nTest 2: listA=[1,9,1,2,4], listB=[3,2,4], intersect at 2");
        ListNode common2 = new ListNode(2);
        common2.next = new ListNode(4);
        
        ListNode headA2 = new ListNode(1);
        headA2.next = new ListNode(9);
        headA2.next.next = new ListNode(1);
        headA2.next.next.next = common2;
        
        ListNode headB2 = new ListNode(3);
        headB2.next = common2;
        
        testCase(sol, headA2, headB2, common2, "Test 2");
        
        // Test 3: Example 3 - no intersection
        System.out.println("\nTest 3: listA=[2,6,4], listB=[1,5], no intersection");
        ListNode headA3 = new ListNode(2);
        headA3.next = new ListNode(6);
        headA3.next.next = new ListNode(4);
        
        ListNode headB3 = new ListNode(1);
        headB3.next = new ListNode(5);
        
        testCase(sol, headA3, headB3, null, "Test 3");
        
        // Test 4: Same list
        System.out.println("\nTest 4: Same list (headA == headB)");
        ListNode head4 = new ListNode(1);
        head4.next = new ListNode(2);
        head4.next.next = new ListNode(3);
        testCase(sol, head4, head4, head4, "Test 4");
        
        // Test 5: One list empty
        System.out.println("\nTest 5: One list empty");
        ListNode head5 = new ListNode(1);
        head5.next = new ListNode(2);
        testCase(sol, head5, null, null, "Test 5");
        
        // Test 6: Intersection at head
        System.out.println("\nTest 6: Intersection at head");
        ListNode common6 = new ListNode(1);
        common6.next = new ListNode(2);
        common6.next.next = new ListNode(3);
        
        ListNode headA6 = common6;
        ListNode headB6 = new ListNode(0);
        headB6.next = common6;
        
        testCase(sol, headA6, headB6, common6, "Test 6");
        
        // Test 7: Intersection at tail
        System.out.println("\nTest 7: Intersection at tail (last node)");
        ListNode common7 = new ListNode(99);
        
        ListNode headA7 = new ListNode(1);
        headA7.next = new ListNode(2);
        headA7.next.next = common7;
        
        ListNode headB7 = new ListNode(3);
        headB7.next = new ListNode(4);
        headB7.next.next = new ListNode(5);
        headB7.next.next.next = common7;
        
        testCase(sol, headA7, headB7, common7, "Test 7");
        
        // Test 8: Long lists with intersection
        System.out.println("\nTest 8: Long lists with intersection");
        ListNode common8 = new ListNode(100);
        for (int i = 101; i <= 110; i++) {
            ListNode n = new ListNode(i);
            if (i == 101) common8.next = n;
            else {
                ListNode prev = common8;
                while (prev.next != null) prev = prev.next;
                prev.next = n;
            }
        }
        
        ListNode headA8 = new ListNode(1);
        for (int i = 2; i <= 50; i++) {
            ListNode n = new ListNode(i);
            ListNode prev = headA8;
            while (prev.next != null) prev = prev.next;
            prev.next = n;
        }
        ListNode tailA8 = headA8;
        while (tailA8.next != null) tailA8 = tailA8.next;
        tailA8.next = common8;
        
        ListNode headB8 = new ListNode(1000);
        for (int i = 1001; i <= 1020; i++) {
            ListNode n = new ListNode(i);
            ListNode prev = headB8;
            while (prev.next != null) prev = prev.next;
            prev.next = n;
        }
        ListNode tailB8 = headB8;
        while (tailB8.next != null) tailB8 = tailB8.next;
        tailB8.next = common8;
        
        testCase(sol, headA8, headB8, common8, "Test 8");
        
        // Test 9: Cross verification
        System.out.println("\nTest 9: Cross verification of all approaches");
        crossVerify(sol);
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    static void testCase(Solution sol, ListNode headA, ListNode headB, ListNode expected, String testName) {
        ListNode r1 = sol.getIntersectionNode(headA, headB);
        ListNode r2 = sol.getIntersectionNodeLength(headA, headB);
        ListNode r3 = sol.getIntersectionNodeHashSet(headA, headB);
        ListNode r4 = sol.getIntersectionNodeCycle(headA, headB);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        System.out.println("  TwoPointers: " + (r1 == expected ? "✓" : "✗") + " (" + (r1 != null ? r1.val : "null") + ")");
        System.out.println("  LengthDiff:  " + (r2 == expected ? "✓" : "✗") + " (" + (r2 != null ? r2.val : "null") + ")");
        System.out.println("  HashSet:     " + (r3 == expected ? "✓" : "✗") + " (" + (r3 != null ? r3.val : "null") + ")");
        System.out.println("  Cycle:       " + (r4 == expected ? "✓" : "✗") + " (" + (r4 != null ? r4.val : "null") + ")");
        
        assert match : testName + " failed: expected " + (expected != null ? expected.val : "null") + 
            ", got " + (r1 != null ? r1.val : "null") + ", " + (r2 != null ? r2.val : "null") + 
            ", " + (r3 != null ? r3.val : "null") + ", " + (r4 != null ? r4.val : "null");
    }
    
    static void crossVerify(Solution sol) {
            // Test multiple scenarios - need to create properly intersecting lists
        
            // No intersection
            ListNode a1 = createList(new int[]{1,2,3});
            ListNode b1 = createList(new int[]{4,5,6});
            testCrossVerify(sol, "No intersection", a1, b1, null);
        
            // Intersect at head - same list
            ListNode commonHead = createList(new int[]{1,2,3});
            testCrossVerify(sol, "Intersect at head", commonHead, commonHead, commonHead);
        
            // Intersect at middle
            ListNode commonMid = createList(new int[]{3,4,5,6,7});
            ListNode a2 = createList(new int[]{1,2});
            ListNode tailA2 = a2;
            while (tailA2.next != null) tailA2 = tailA2.next;
            tailA2.next = commonMid;
        
            ListNode b2 = createList(new int[]{8,9});
            ListNode tailB2 = b2;
            while (tailB2.next != null) tailB2 = tailB2.next;
            tailB2.next = commonMid;
        
            testCrossVerify(sol, "Intersect at middle", a2, b2, commonMid);
        
            // Intersect at tail
            ListNode commonTail = createList(new int[]{4});
            ListNode a3 = createList(new int[]{1,2,3});
            ListNode tailA3 = a3;
            while (tailA3.next != null) tailA3 = tailA3.next;
            tailA3.next = commonTail;
        
            ListNode b3 = createList(new int[]{5,6});
            ListNode tailB3 = b3;
            while (tailB3.next != null) tailB3 = tailB3.next;
            tailB3.next = commonTail;
        
            testCrossVerify(sol, "Intersect at tail", a3, b3, commonTail);
        
            System.out.println("  All cross-verifications passed ✓");
        }
    
    static void testCrossVerify(Solution sol, String name, ListNode headA, ListNode headB, ListNode expected) {
        ListNode r1 = sol.getIntersectionNode(headA, headB);
        ListNode r2 = sol.getIntersectionNodeLength(headA, headB);
        ListNode r3 = sol.getIntersectionNodeHashSet(headA, headB);
        ListNode r4 = sol.getIntersectionNodeCycle(headA, headB);
        
        boolean match = (r1 == expected && r2 == expected && r3 == expected && r4 == expected);
        System.out.println("  " + name + ": " + (match ? "MATCH" : "MISMATCH") + 
            " (expected: " + (expected != null ? expected.val : "null") + 
            ", got: " + (r1 != null ? r1.val : "null") + ")");
        assert match : "Cross-verification failed for " + name;
    }
    
    static ListNode createList(int[] vals) {
        if (vals.length == 0) return null;
        ListNode head = new ListNode(vals[0]);
        ListNode curr = head;
        for (int i = 1; i < vals.length; i++) {
            curr.next = new ListNode(vals[i]);
            curr = curr.next;
        }
        return head;
    }
    
    static ListNode createListWithIntersection(int[] prefixA, int[] common, int[] suffix) {
        ListNode head = null;
        ListNode curr = null;
        
        // Build prefix A
        for (int v : prefixA) {
            ListNode n = new ListNode(v);
            if (head == null) head = n;
            else curr.next = n;
            curr = n;
        }
        
        // Build common part
        ListNode commonHead = null;
        ListNode commonCurr = null;
        for (int v : common) {
            ListNode n = new ListNode(v);
            if (commonHead == null) commonHead = n;
            else commonCurr.next = n;
            commonCurr = n;
        }
        
        // Build suffix
        for (int v : suffix) {
            ListNode n = new ListNode(v);
            if (commonHead == null) commonHead = n;
            else commonCurr.next = n;
            commonCurr = n;
        }
        
        if (curr != null) curr.next = commonHead;
        else head = commonHead;
        
        return head;
    }
}