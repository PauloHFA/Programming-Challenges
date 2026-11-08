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
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        ListNode curr = head;
        
        while (curr != null) {
            // Check if current node has duplicates
            if (curr.next != null && curr.val == curr.next.val) {
                int duplicateVal = curr.val;
                // Skip all nodes with this value
                while (curr != null && curr.val == duplicateVal) {
                    curr = curr.next;
                }
                prev.next = curr;
            } else {
                // No duplicates, move prev forward
                prev = curr;
                curr = curr.next;
            }
        }
        
        return dummy.next;
    }

    // Alternative: Using two pointers with explicit duplicate detection
    public ListNode deleteDuplicatesTwoPointers(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        
        while (curr != null) {
            boolean isDuplicate = false;
            
            // Check for duplicates
            while (curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
                isDuplicate = true;
            }
            
            if (isDuplicate) {
                // Skip all duplicates
                prev.next = curr.next;
            } else {
                // No duplicates, move prev
                prev = curr;
            }
            
            curr = curr.next;
        }
        
        return dummy.next;
    }

    // Alternative: Recursive approach
    public ListNode deleteDuplicatesRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        if (head.val == head.next.val) {
            int val = head.val;
            while (head != null && head.val == val) {
                head = head.next;
            }
            return deleteDuplicatesRecursive(head);
        } else {
            head.next = deleteDuplicatesRecursive(head.next);
            return head;
        }
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

    // Helper to convert list to array
    private static int[] toArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
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
            {1, 2, 3, 3, 4, 4, 5},
            {1, 1, 1, 2, 3},
            {1, 1, 1, 2, 2, 3, 3},
            {1, 2, 3, 4, 5},
            {1, 1},
            {1},
            {},
            {1, 1, 2, 2, 3, 3, 4, 4},
            {1, 2, 2, 3, 3, 4, 4, 5, 5},
        };
        
        int[][] expected = {
            {1, 2, 5},
            {2, 3},
            {},
            {1, 2, 3, 4, 5},
            {},
            {1},
            {},
            {},
            {1},
        };
        
        System.out.println("=== Remove Duplicates from Sorted List II Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            ListNode head1 = createList(testCases[i]);
            ListNode head2 = createList(testCases[i]);
            ListNode head3 = createList(testCases[i]);
            ListNode expectedList = createList(expected[i]);
            
            ListNode result1 = solver.deleteDuplicates(head1);
            ListNode result2 = solver.deleteDuplicatesTwoPointers(head2);
            ListNode result3 = solver.deleteDuplicatesRecursive(head3);
            
            boolean match1 = listsEqual(result1, expectedList);
            boolean match2 = listsEqual(result2, expectedList);
            boolean match3 = listsEqual(result3, expectedList);
            
            System.out.println("Test " + (i + 1) + ": " + java.util.Arrays.toString(testCases[i]));
            System.out.println("  Expected: " + (expectedList != null ? expectedList.toString() : "null"));
            System.out.println("  Method 1: " + (result1 != null ? result1.toString() : "null") + (match1 ? " ✓" : " ✗"));
            System.out.println("  Method 2: " + (result2 != null ? result2.toString() : "null") + (match2 ? " ✓" : " ✗"));
            System.out.println("  Method 3: " + (result3 != null ? result3.toString() : "null") + (match3 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 && match3 ? "✓" : "✗"));
            System.out.println();
        }
    }
}