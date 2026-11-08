public class Solution {
    
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    // Método principal: Two Dummy Lists
    public ListNode partition(ListNode head, int x) {
        ListNode beforeHead = new ListNode(0);
        ListNode afterHead = new ListNode(0);
        ListNode before = beforeHead;
        ListNode after = afterHead;
        
        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }
        
        after.next = null;
        before.next = afterHead.next;
        
        return beforeHead.next;
    }
    
    // Método 2: In-place (mais complexo, mas O(1) espaço extra)
    public ListNode partitionInPlace(ListNode head, int x) {
        if (head == null || head.next == null) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Encontra o último nó < x na partição "before"
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode lastBefore = dummy;
        
        while (curr != null) {
            if (curr.val < x) {
                if (prev == lastBefore) {
                    // Já está na posição correta
                    lastBefore = curr;
                    prev = curr;
                    curr = curr.next;
                } else {
                    // Move curr para depois de lastBefore
                    prev.next = curr.next;
                    curr.next = lastBefore.next;
                    lastBefore.next = curr;
                    lastBefore = curr;
                    curr = prev.next;
                }
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
        
        return dummy.next;
    }
    
    // Método 3: Coleta valores e reconstrói (simples mas O(n) espaço)
    public ListNode partitionCollect(ListNode head, int x) {
        if (head == null) return null;
        
        java.util.List<Integer> before = new java.util.ArrayList<>();
        java.util.List<Integer> after = new java.util.ArrayList<>();
        
        ListNode curr = head;
        while (curr != null) {
            if (curr.val < x) before.add(curr.val);
            else after.add(curr.val);
            curr = curr.next;
        }
        
        ListNode dummy = new ListNode(0);
        curr = dummy;
        
        for (int val : before) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        for (int val : after) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        
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
    
    // Helper: verifica se partição está correta
    private static boolean isPartitioned(int[] arr, int x) {
        boolean seenGe = false;
        for (int val : arr) {
            if (val >= x) seenGe = true;
            else if (seenGe) return false; // valor < x depois de >= x
        }
        return true;
    }
    
    // Helper: verifica se preserva ordem relativa
    private static boolean preservesOrder(int[] original, int[] partitioned, int x) {
        java.util.List<Integer> origBefore = new java.util.ArrayList<>();
        java.util.List<Integer> origAfter = new java.util.ArrayList<>();
        java.util.List<Integer> partBefore = new java.util.ArrayList<>();
        java.util.List<Integer> partAfter = new java.util.ArrayList<>();
        
        for (int val : original) {
            if (val < x) origBefore.add(val);
            else origAfter.add(val);
        }
        for (int val : partitioned) {
            if (val < x) partBefore.add(val);
            else partAfter.add(val);
        }
        
        return origBefore.equals(partBefore) && origAfter.equals(partAfter);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Partition List Tests ===\n");
        
        // Test 1: Example 1
        int[] arr1 = {1, 4, 3, 2, 5, 2};
        ListNode head1 = createList(arr1);
        ListNode result1 = sol.partition(head1, 3);
        int[] resArr1 = toArray(result1);
        boolean pass1 = isPartitioned(resArr1, 3) && preservesOrder(arr1, resArr1, 3);
        System.out.println("Test 1: [1,4,3,2,5,2], x=3");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr1));
        System.out.println("  Expected partition: <3 then >=3, order preserved");
        System.out.println("  Pass: " + (pass1 ? "✓" : "✗") + "\n");
        
        // Test 2: Example 2
        int[] arr2 = {2, 1};
        ListNode head2 = createList(arr2);
        ListNode result2 = sol.partition(head2, 2);
        int[] resArr2 = toArray(result2);
        boolean pass2 = isPartitioned(resArr2, 2) && preservesOrder(arr2, resArr2, 2);
        System.out.println("Test 2: [2,1], x=2");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr2));
        System.out.println("  Pass: " + (pass2 ? "✓" : "✗") + "\n");
        
        // Test 3: All less than x
        int[] arr3 = {1, 2, 3};
        ListNode head3 = createList(arr3);
        ListNode result3 = sol.partition(head3, 5);
        int[] resArr3 = toArray(result3);
        boolean pass3 = java.util.Arrays.equals(arr3, resArr3);
        System.out.println("Test 3: [1,2,3], x=5 (all < x)");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr3));
        System.out.println("  Pass: " + (pass3 ? "✓" : "✗") + "\n");
        
        // Test 4: All greater than x
        int[] arr4 = {5, 6, 7};
        ListNode head4 = createList(arr4);
        ListNode result4 = sol.partition(head4, 3);
        int[] resArr4 = toArray(result4);
        boolean pass4 = java.util.Arrays.equals(arr4, resArr4);
        System.out.println("Test 4: [5,6,7], x=3 (all >= x)");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr4));
        System.out.println("  Pass: " + (pass4 ? "✓" : "✗") + "\n");
        
        // Test 5: Single element
        int[] arr5 = {1};
        ListNode head5 = createList(arr5);
        ListNode result5 = sol.partition(head5, 2);
        int[] resArr5 = toArray(result5);
        boolean pass5 = java.util.Arrays.equals(arr5, resArr5);
        System.out.println("Test 5: [1], x=2");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr5));
        System.out.println("  Pass: " + (pass5 ? "✓" : "✗") + "\n");
        
        // Test 6: Empty list
        ListNode head6 = null;
        ListNode result6 = sol.partition(head6, 3);
        boolean pass6 = result6 == null;
        System.out.println("Test 6: [], x=3 (empty)");
        System.out.println("  Result: null");
        System.out.println("  Pass: " + (pass6 ? "✓" : "✗") + "\n");
        
        // Test 7: Already partitioned
        int[] arr7 = {1, 2, 3, 4, 5};
        ListNode head7 = createList(arr7);
        ListNode result7 = sol.partition(head7, 3);
        int[] resArr7 = toArray(result7);
        boolean pass7 = isPartitioned(resArr7, 3) && preservesOrder(arr7, resArr7, 3);
        System.out.println("Test 7: [1,2,3,4,5], x=3 (already partitioned)");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr7));
        System.out.println("  Pass: " + (pass7 ? "✓" : "✗") + "\n");
        
        // Test 8: Reverse order
        int[] arr8 = {5, 4, 3, 2, 1};
        ListNode head8 = createList(arr8);
        ListNode result8 = sol.partition(head8, 3);
        int[] resArr8 = toArray(result8);
        boolean pass8 = isPartitioned(resArr8, 3) && preservesOrder(arr8, resArr8, 3);
        System.out.println("Test 8: [5,4,3,2,1], x=3");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr8));
        System.out.println("  Pass: " + (pass8 ? "✓" : "✗") + "\n");
        
        // Test 9: All equal to x
        int[] arr9 = {3, 3, 3};
        ListNode head9 = createList(arr9);
        ListNode result9 = sol.partition(head9, 3);
        int[] resArr9 = toArray(result9);
        boolean pass9 = java.util.Arrays.equals(arr9, resArr9);
        System.out.println("Test 9: [3,3,3], x=3 (all == x)");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr9));
        System.out.println("  Pass: " + (pass9 ? "✓" : "✗") + "\n");
        
        // Test 10: Mixed with duplicates
        int[] arr10 = {3, 1, 2, 3, 4, 2, 1};
        ListNode head10 = createList(arr10);
        ListNode result10 = sol.partition(head10, 3);
        int[] resArr10 = toArray(result10);
        boolean pass10 = isPartitioned(resArr10, 3) && preservesOrder(arr10, resArr10, 3);
        System.out.println("Test 10: [3,1,2,3,4,2,1], x=3");
        System.out.println("  Result: " + java.util.Arrays.toString(resArr10));
        System.out.println("  Pass: " + (pass10 ? "✓" : "✗") + "\n");
        
        // Verify all methods give same result
        System.out.println("=== Cross-method verification ===");
        int[][] testArrays = {arr1, arr2, arr3, arr4, arr5, arr7, arr8, arr9, arr10};
        int[] testX = {3, 2, 5, 3, 2, 3, 3, 3, 3};
        
        for (int i = 0; i < testArrays.length; i++) {
            ListNode h1 = createList(testArrays[i]);
            ListNode h2 = createList(testArrays[i]);
            ListNode h3 = createList(testArrays[i]);
            
            int[] r1 = toArray(sol.partition(h1, testX[i]));
            int[] r2 = toArray(sol.partitionInPlace(h2, testX[i]));
            int[] r3 = toArray(sol.partitionCollect(h3, testX[i]));
            
            boolean match = java.util.Arrays.equals(r1, r2) && java.util.Arrays.equals(r2, r3);
            System.out.println("Test " + (i+1) + " methods match: " + (match ? "✓" : "✗"));
        }
    }
}