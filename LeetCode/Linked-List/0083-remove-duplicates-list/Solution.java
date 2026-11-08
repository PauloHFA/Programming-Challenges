public class Solution {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode curr = this;
            while (curr != null) {
                sb.append(curr.val);
                if (curr.next != null) sb.append(" -> ");
                curr = curr.next;
            }
            return sb.toString();
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                // Skip the duplicate
                curr.next = curr.next.next;
            } else {
                // Move to next node
                curr = curr.next;
            }
        }
        
        return head;
    }

    // Alternative: Using dummy node (for practice)
        public ListNode deleteDuplicatesDummy(ListNode head) {
            ListNode dummy = new ListNode(0, head);
            ListNode curr = dummy;
        
            while (curr.next != null && curr.next.next != null) {
                if (curr.next.val == curr.next.next.val) {
                    // Skip the duplicate
                    curr.next = curr.next.next;
                } else {
                    curr = curr.next;
                }
            }
        
            return dummy.next;
        }

    // Alternative: Recursive
    public ListNode deleteDuplicatesRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        head.next = deleteDuplicatesRecursive(head.next);
        
        if (head.val == head.next.val) {
            return head.next;
        }
        
        return head;
    }

    // Helper to create list from array
    private static ListNode createList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    // Helper to compare lists
    private static boolean listsEqual(ListNode a, ListNode b) {
        while (a != null && b != null) {
            if (a.val != b.val) return false;
            a = a.next;
            b = b.next;
        }
        return a == null && b == null;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases
        int[][] testCases = {
            {1, 1, 2},
            {1, 1, 2, 3, 3},
            {1, 1, 1, 1, 1},
            {1, 2, 3, 4, 5},
            {1, 1},
            {1},
            {},
            {1, 2, 2, 3, 3, 4, 4, 5, 5},
            {1, 1, 2, 2, 3, 3},
        };
        
        int[][] expected = {
            {1, 2},
            {1, 2, 3},
            {1},
            {1, 2, 3, 4, 5},
            {1},
            {1},
            {},
            {1, 2, 3, 4, 5},
            {1, 2, 3},
        };
        
        System.out.println("=== Remove Duplicates from Sorted List Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            ListNode head1 = createList(testCases[i]);
            ListNode head2 = createList(testCases[i]);
            ListNode head3 = createList(testCases[i]);
            ListNode expectedList = createList(expected[i]);
            
            ListNode result1 = solver.deleteDuplicates(head1);
            ListNode result2 = solver.deleteDuplicatesDummy(head2);
            ListNode result3 = solver.deleteDuplicatesRecursive(head3);
            
            boolean match1 = listsEqual(result1, expectedList);
            boolean match2 = listsEqual(result2, expectedList);
            boolean match3 = listsEqual(result3, expectedList);
            
            System.out.println("Test " + (i + 1) + ": " + java.util.Arrays.toString(testCases[i]));
            System.out.println("  Expected: " + (expectedList != null ? expectedList.toString() : "null"));
            System.out.println("  Iterative: " + (result1 != null ? result1.toString() : "null") + (match1 ? " ✓" : " ✗"));
            System.out.println("  Dummy:     " + (result2 != null ? result2.toString() : "null") + (match2 ? " ✓" : " ✗"));
            System.out.println("  Recursive: " + (result3 != null ? result3.toString() : "null") + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}