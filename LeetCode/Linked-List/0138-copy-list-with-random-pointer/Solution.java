/**
 * LeetCode 138 - Copy List with Random Pointer
 * 
 * A linked list of length n is given such that each node contains an additional random pointer,
 * which could point to any node in the list, or null.
 * 
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
 * where each new node has its value set to the value of its corresponding original node.
 * Both the next and random pointer of the new nodes should point to new nodes in the copied list
 * such that the pointers in the original list and copied list represent the same list state.
 * None of the pointers in the new list should point to nodes in the original list.
 * 
 * Example 1:
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 
 * Example 2:
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * 
 * Example 3:
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * 
 * Constraints:
 * 0 <= n <= 1000
 * -10^4 <= Node.val <= 10^4
 * Node.random is null or points to some node in the linked list.
 */
public class Solution {
    
    // Definition for a Node.
    static class Node {
        int val;
        Node next;
        Node random;
        
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(val).append(",");
            if (random != null) {
                sb.append(random.val);
            } else {
                sb.append("null");
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    /**
     * Approach 1: Hash Map - Two Passes
     * 
     * First pass: Create all new nodes and map original -> copy
     * Second pass: Set next and random pointers using the map
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the hash map
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        
        java.util.Map<Node, Node> map = new java.util.HashMap<>();
        
        // First pass: create all nodes
        Node curr = head;
        while (curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        
        // Second pass: set next and random pointers
        curr = head;
        while (curr != null) {
            Node copy = map.get(curr);
            copy.next = map.get(curr.next);
            copy.random = map.get(curr.random);
            curr = curr.next;
        }
        
        return map.get(head);
    }
    
    /**
     * Approach 2: Interleaved Nodes (O(1) Space)
     * 
     * 1. Interleave copied nodes: A -> A' -> B -> B' -> C -> C'
     * 2. Set random pointers for copied nodes
     * 3. Separate the two lists
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) extra space (not counting output)
     */
    public Node copyRandomListO1Space(Node head) {
        if (head == null) return null;
        
        // Step 1: Interleave copied nodes
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }
        
        // Step 2: Set random pointers for copied nodes
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }
        
        // Step 3: Separate the two lists
        curr = head;
        Node copyHead = head.next;
        Node copyCurr = copyHead;
        
        while (curr != null) {
            curr.next = copyCurr.next;
            curr = curr.next;
            if (curr != null) {
                copyCurr.next = curr.next;
                copyCurr = copyCurr.next;
            }
        }
        
        return copyHead;
    }
    
    /**
     * Approach 3: Recursive with Hash Map
     * 
     * Use recursion with memoization to handle cycles and random pointers.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) for hash map + O(n) recursion stack
     */
    public Node copyRandomListRecursive(Node head) {
        java.util.Map<Node, Node> map = new java.util.HashMap<>();
        return copyRecursive(head, map);
    }
    
    private Node copyRecursive(Node node, java.util.Map<Node, Node> map) {
        if (node == null) return null;
        if (map.containsKey(node)) return map.get(node);
        
        Node copy = new Node(node.val);
        map.put(node, copy);
        copy.next = copyRecursive(node.next, map);
        copy.random = copyRecursive(node.random, map);
        return copy;
    }
    
    // Helper methods for testing
    private static Node createList(int[][] data) {
        if (data == null || data.length == 0) return null;
        
        Node[] nodes = new Node[data.length];
        for (int i = 0; i < data.length; i++) {
            nodes[i] = new Node(data[i][0]);
        }
        
        for (int i = 0; i < data.length; i++) {
            if (i < data.length - 1) {
                nodes[i].next = nodes[i + 1];
            }
            if (data[i][1] != -1) {
                nodes[i].random = nodes[data[i][1]];
            }
        }
        
        return nodes[0];
    }
    
    private static boolean compareLists(Node head1, Node head2) {
        Node curr1 = head1;
        Node curr2 = head2;
        
        while (curr1 != null && curr2 != null) {
            if (curr1.val != curr2.val) return false;
            
            // Check random pointers
            int randVal1 = (curr1.random != null) ? curr1.random.val : -1;
            int randVal2 = (curr2.random != null) ? curr2.random.val : -1;
            if (randVal1 != randVal2) return false;
            
            // Ensure they are different objects (deep copy)
            if (curr1 == curr2) return false;
            if (curr1.random == curr2.random && curr1.random != null) return false;
            
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        
        return curr1 == null && curr2 == null;
    }
    
    private static String listToString(Node head) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node curr = head;
        while (curr != null) {
            sb.append("[").append(curr.val).append(",");
            if (curr.random != null) {
                sb.append(curr.random.val);
            } else {
                sb.append("null");
            }
            sb.append("]");
            if (curr.next != null) sb.append(",");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Copy List with Random Pointer Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [[7,null],[13,0],[11,4],[10,2],[1,0]]");
        int[][] data1 = {{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}};
        Node head1 = createList(data1);
        Node copy1 = sol.copyRandomList(head1);
        System.out.println("  Original: " + listToString(head1));
        System.out.println("  Copy:     " + listToString(copy1));
        System.out.println("  HashMap:  " + (compareLists(head1, copy1) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy1b = sol.copyRandomListO1Space(head1);
        System.out.println("  O(1)Space:" + (compareLists(head1, copy1b) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy1c = sol.copyRandomListRecursive(head1);
        System.out.println("  Recursive:" + (compareLists(head1, copy1c) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 2: Example 2
        System.out.println("\nTest 2: [[1,1],[2,1]]");
        int[][] data2 = {{1, 1}, {2, 1}};
        Node head2 = createList(data2);
        Node copy2 = sol.copyRandomList(head2);
        System.out.println("  Original: " + listToString(head2));
        System.out.println("  Copy:     " + listToString(copy2));
        System.out.println("  HashMap:  " + (compareLists(head2, copy2) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy2b = sol.copyRandomListO1Space(head2);
        System.out.println("  O(1)Space:" + (compareLists(head2, copy2b) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy2c = sol.copyRandomListRecursive(head2);
        System.out.println("  Recursive:" + (compareLists(head2, copy2c) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 3: Example 3
        System.out.println("\nTest 3: [[3,null],[3,0],[3,null]]");
        int[][] data3 = {{3, -1}, {3, 0}, {3, -1}};
        Node head3 = createList(data3);
        Node copy3 = sol.copyRandomList(head3);
        System.out.println("  Original: " + listToString(head3));
        System.out.println("  Copy:     " + listToString(copy3));
        System.out.println("  HashMap:  " + (compareLists(head3, copy3) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy3b = sol.copyRandomListO1Space(head3);
        System.out.println("  O(1)Space:" + (compareLists(head3, copy3b) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy3c = sol.copyRandomListRecursive(head3);
        System.out.println("  Recursive:" + (compareLists(head3, copy3c) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 4: Empty list
        System.out.println("\nTest 4: Empty list");
        Node head4 = null;
        Node copy4 = sol.copyRandomList(head4);
        System.out.println("  HashMap:  " + (copy4 == null ? "PASS ✓" : "FAIL ✗"));
        
        Node copy4b = sol.copyRandomListO1Space(head4);
        System.out.println("  O(1)Space:" + (copy4b == null ? "PASS ✓" : "FAIL ✗"));
        
        Node copy4c = sol.copyRandomListRecursive(head4);
        System.out.println("  Recursive:" + (copy4c == null ? "PASS ✓" : "FAIL ✗"));
        
        // Test 5: Single node with self random
        System.out.println("\nTest 5: Single node with self random");
        Node head5 = new Node(1);
        head5.random = head5;
        Node copy5 = sol.copyRandomList(head5);
        System.out.println("  Original: val=" + head5.val + ", random=" + head5.random.val);
        System.out.println("  Copy:     val=" + copy5.val + ", random=" + copy5.random.val);
        System.out.println("  HashMap:  " + (compareLists(head5, copy5) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy5b = sol.copyRandomListO1Space(head5);
        System.out.println("  O(1)Space:" + (compareLists(head5, copy5b) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy5c = sol.copyRandomListRecursive(head5);
        System.out.println("  Recursive:" + (compareLists(head5, copy5c) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 6: Single node with null random
        System.out.println("\nTest 6: Single node with null random");
        Node head6 = new Node(42);
        head6.random = null;
        Node copy6 = sol.copyRandomList(head6);
        System.out.println("  Original: val=" + head6.val + ", random=null");
        System.out.println("  Copy:     val=" + copy6.val + ", random=" + (copy6.random != null ? copy6.random.val : "null"));
        System.out.println("  HashMap:  " + (compareLists(head6, copy6) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy6b = sol.copyRandomListO1Space(head6);
        System.out.println("  O(1)Space:" + (compareLists(head6, copy6b) ? "PASS ✓" : "FAIL ✗"));
        
        Node copy6c = sol.copyRandomListRecursive(head6);
        System.out.println("  Recursive:" + (compareLists(head6, copy6c) ? "PASS ✓" : "FAIL ✗"));
        
        // Test 7: Cross verification
        System.out.println("\nTest 7: Cross verification of all approaches");
        int[][][] testCases = {
            {{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}},
            {{1, 1}, {2, 1}},
            {{3, -1}, {3, 0}, {3, -1}},
            {{1, 0}},  // single node self random
            {{42, -1}}, // single node null random
            {{1, 2}, {2, 0}, {3, 1}, {4, 3}, {5, -1}} // 5 nodes
        };
        
        boolean allMatch = true;
        for (int i = 0; i < testCases.length; i++) {
            Node h = createList(testCases[i]);
            Node c1 = sol.copyRandomList(h);
            Node c2 = sol.copyRandomListO1Space(h);
            Node c3 = sol.copyRandomListRecursive(h);
            
            boolean m1 = compareLists(h, c1);
            boolean m2 = compareLists(h, c2);
            boolean m3 = compareLists(h, c3);
            boolean match = m1 && m2 && m3;
            if (!match) allMatch = false;
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (HM:" + m1 + " O1:" + m2 + " Rec:" + m3 + ")");
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}