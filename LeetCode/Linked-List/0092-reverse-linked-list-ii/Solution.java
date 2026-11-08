public class Solution {
    
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    // Método principal: One Pass - Reverse entre left e right
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        // Move prev para o nó antes de left
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        
        // start é o primeiro nó a ser revertido
        ListNode start = prev.next;
        // then é o nó que será movido para frente de start
        ListNode then = start.next;
        
        // Reverte a sublista entre left e right
        for (int i = 0; i < right - left; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }
        
        return dummy.next;
    }
    
    // Método 2: Recursivo
    public ListNode reverseBetweenRecursive(ListNode head, int left, int right) {
        if (left == 1) {
            return reverseN(head, right);
        }
        head.next = reverseBetweenRecursive(head.next, left - 1, right - 1);
        return head;
    }
    
    private ListNode successor = null;
    
    private ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        
        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successor;
        return last;
    }
    
    // Método 3: Coleta valores, reverte array, reconstrói (O(n) espaço)
    public ListNode reverseBetweenArray(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        
        java.util.List<Integer> values = new java.util.ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }
        
        // Reverte subarray
        int l = left - 1, r = right - 1;
        while (l < r) {
            int temp = values.get(l);
            values.set(l, values.get(r));
            values.set(r, temp);
            l++;
            r--;
        }
        
        // Reconstrói lista
        curr = head;
        int idx = 0;
        while (curr != null) {
            curr.val = values.get(idx++);
            curr = curr.next;
        }
        
        return head;
    }
    
    // Método 4: Stack (coleta nós, depois reconecta)
    public ListNode reverseBetweenStack(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        
        java.util.Stack<ListNode> stack = new java.util.Stack<>();
        ListNode curr = prev.next;
        
        for (int i = left; i <= right; i++) {
            stack.push(curr);
            curr = curr.next;
        }
        
        ListNode nextPart = curr;
        
        while (!stack.isEmpty()) {
            prev.next = stack.pop();
            prev = prev.next;
        }
        
        prev.next = nextPart;
        
        return dummy.next;
    }
    
    // Helper: cria lista a partir de array
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
    
    // Helper: converte lista para array
    private static int[] toArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
    
    // Helper: copia lista
    private static ListNode copyList(ListNode head) {
        if (head == null) return null;
        ListNode newHead = new ListNode(head.val);
        ListNode curr = newHead;
        head = head.next;
        while (head != null) {
            curr.next = new ListNode(head.val);
            curr = curr.next;
            head = head.next;
        }
        return newHead;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Reverse Linked List II Tests ===\n");
        
        // Test 1: Example 1
        test(sol, new int[]{1,2,3,4,5}, 2, 4, new int[]{1,4,3,2,5}, "Test 1: Example 1");
        
        // Test 2: Example 2
        test(sol, new int[]{5}, 1, 1, new int[]{5}, "Test 2: Example 2");
        
        // Test 3: Reverse entire list
        test(sol, new int[]{1,2,3,4,5}, 1, 5, new int[]{5,4,3,2,1}, "Test 3: Reverse entire");
        
        // Test 4: Reverse from head
        test(sol, new int[]{1,2,3,4,5}, 1, 3, new int[]{3,2,1,4,5}, "Test 4: Reverse from head");
        
        // Test 5: Reverse to tail
        test(sol, new int[]{1,2,3,4,5}, 3, 5, new int[]{1,2,5,4,3}, "Test 5: Reverse to tail");
        
        // Test 6: Reverse middle two
        test(sol, new int[]{1,2,3,4}, 2, 3, new int[]{1,3,2,4}, "Test 6: Reverse middle two");
        
        // Test 7: Single element (left == right)
        test(sol, new int[]{1,2,3}, 2, 2, new int[]{1,2,3}, "Test 7: Single element");
        
        // Test 8: Two elements, reverse both
        test(sol, new int[]{1,2}, 1, 2, new int[]{2,1}, "Test 8: Two elements");
        
        // Test 9: Three elements, reverse first two
        test(sol, new int[]{1,2,3}, 1, 2, new int[]{2,1,3}, "Test 9: Three elements, first two");
        
        // Test 10: Three elements, reverse last two
        test(sol, new int[]{1,2,3}, 2, 3, new int[]{1,3,2}, "Test 10: Three elements, last two");
        
        // Test 11: Larger list
        test(sol, new int[]{1,2,3,4,5,6,7,8,9}, 3, 7, new int[]{1,2,7,6,5,4,3,8,9}, "Test 11: Larger list");
    }
    
    private static void test(Solution sol, int[] arr, int left, int right, int[] expected, String name) {
        ListNode h1 = createList(arr);
        ListNode h2 = createList(arr);
        ListNode h3 = createList(arr);
        ListNode h4 = createList(arr);
        
        int[] r1 = toArray(sol.reverseBetween(h1, left, right));
        int[] r2 = toArray(sol.reverseBetweenRecursive(h2, left, right));
        int[] r3 = toArray(sol.reverseBetweenArray(h3, left, right));
        int[] r4 = toArray(sol.reverseBetweenStack(h4, left, right));
        
        boolean pass1 = java.util.Arrays.equals(r1, expected);
        boolean pass2 = java.util.Arrays.equals(r2, expected);
        boolean pass3 = java.util.Arrays.equals(r3, expected);
        boolean pass4 = java.util.Arrays.equals(r4, expected);
        boolean allMatch = java.util.Arrays.equals(r1, r2) && java.util.Arrays.equals(r2, r3) && java.util.Arrays.equals(r3, r4);
        
        System.out.println(name);
        System.out.println("  Input: " + java.util.Arrays.toString(arr) + ", left=" + left + ", right=" + right);
        System.out.println("  Expected: " + java.util.Arrays.toString(expected));
        System.out.println("  OnePass:  " + java.util.Arrays.toString(r1) + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Recursive:" + java.util.Arrays.toString(r2) + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Array:    " + java.util.Arrays.toString(r3) + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Stack:    " + java.util.Arrays.toString(r4) + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}