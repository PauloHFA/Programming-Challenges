import java.util.*;

/**
 * LeetCode 109 - Convert Sorted List to Binary Search Tree
 * 
 * Dado o head de uma lista ligada simplesmente onde os elementos estão ordenados
 * em ordem crescente, converta-a em uma árvore binária de busca balanceada em altura.
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Método 1: Converter para Array + Recursivo (Principal)
     * Primeiro converte lista para array, depois usa mesma abordagem do 0108.
     * Tempo: O(n), Espaço: O(n) array + O(log n) stack
     */
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> values = new ArrayList<>();
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }
        int[] nums = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            nums[i] = values.get(i);
        }
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, left, mid - 1);
        root.right = build(nums, mid + 1, right);
        return root;
    }

    /**
     * Método 2: Encontrar meio com Fast/Slow Pointers (In-place)
     * Usa ponteiros rápido/lento para encontrar meio da lista.
     * Recursivamente constrói esquerda, cria raiz, depois direita.
     * Tempo: O(n log n), Espaço: O(log n) stack
     */
    public TreeNode sortedListToBSTInPlace(ListNode head) {
        return buildInPlace(head, null);
    }

    private TreeNode buildInPlace(ListNode left, ListNode right) {
        if (left == right) return null;

        // Encontra meio com fast/slow
        ListNode slow = left;
        ListNode fast = left;
        while (fast != right && fast.next != right) {
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode root = new TreeNode(slow.val);
        root.left = buildInPlace(left, slow);
        root.right = buildInPlace(slow.next, right);
        return root;
    }

    /**
     * Método 3: Inorder Simulation (O(n) tempo, O(log n) espaço)
     * Simula inorder traversal: constrói esquerda, consome nó da lista, constrói direita.
     * Precisa saber tamanho da lista primeiro.
     * Tempo: O(n), Espaço: O(log n) stack
     */
    public TreeNode sortedListToBSTInorder(ListNode head) {
        int size = 0;
        ListNode curr = head;
        while (curr != null) {
            size++;
            curr = curr.next;
        }
        this.head = head;
        return buildInorder(0, size - 1);
    }

    private ListNode head; // referência global para consumir lista em ordem

    private TreeNode buildInorder(int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;

        // 1. Constrói subárvore esquerda
        TreeNode leftChild = buildInorder(left, mid - 1);

        // 2. Cria raiz com valor atual da lista
        TreeNode root = new TreeNode(head.val);
        root.left = leftChild;

        // 3. Avança na lista
        head = head.next;

        // 4. Constrói subárvore direita
        root.right = buildInorder(mid + 1, right);

        return root;
    }


    // Helper para verificar se é BST válido
    private static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
    }

    // Helper para verificar se é balanceado em altura
    private static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private static int checkHeight(TreeNode node) {
        if (node == null) return 0;
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) return -1;
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) return -1;
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Helper para inorder traversal
    private static void inorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    // Helper para criar lista ligada a partir de array
    private static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    // Helper para array para string
    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Convert Sorted List to BST Tests ===\n");

        // Test 1: Example 1
                System.out.println("Test 1: Example 1");
                int[] arr1 = {-10, -3, 0, 5, 9};
                ListNode head1 = createList(arr1);
                TreeNode r1 = sol.sortedListToBST(head1);
                ListNode head1b = createList(arr1);
                TreeNode r1b = sol.sortedListToBSTInPlace(head1b);
                ListNode head1c = createList(arr1);
                TreeNode r1c = sol.sortedListToBSTInorder(head1c);
                List<Integer> in1 = new ArrayList<>();
                List<Integer> in1b = new ArrayList<>();
                List<Integer> in1c = new ArrayList<>();
                inorder(r1, in1);
                inorder(r1b, in1b);
                inorder(r1c, in1c);
                boolean pass1 = isValidBST(r1) && isBalanced(r1) && in1.equals(Arrays.stream(arr1).boxed().toList()) &&
                                isValidBST(r1b) && isBalanced(r1b) && in1b.equals(Arrays.stream(arr1).boxed().toList()) &&
                                isValidBST(r1c) && isBalanced(r1c) && in1c.equals(Arrays.stream(arr1).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr1));
                System.out.println("  Array:     BST=" + isValidBST(r1) + " Balanced=" + isBalanced(r1) + " Inorder=" + (in1.equals(Arrays.stream(arr1).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r1b) + " Balanced=" + isBalanced(r1b) + " Inorder=" + (in1b.equals(Arrays.stream(arr1).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r1c) + " Balanced=" + isBalanced(r1c) + " Inorder=" + (in1c.equals(Arrays.stream(arr1).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
                allPassed &= pass1;
                System.out.println();

        // Test 2: Example 2
                System.out.println("Test 2: Example 2");
                int[] arr2 = {1, 3};
                ListNode head2 = createList(arr2);
                TreeNode r2 = sol.sortedListToBST(head2);
                ListNode head2b = createList(arr2);
                TreeNode r2b = sol.sortedListToBSTInPlace(head2b);
                ListNode head2c = createList(arr2);
                TreeNode r2c = sol.sortedListToBSTInorder(head2c);
                List<Integer> in2 = new ArrayList<>();
                List<Integer> in2b = new ArrayList<>();
                List<Integer> in2c = new ArrayList<>();
                inorder(r2, in2);
                inorder(r2b, in2b);
                inorder(r2c, in2c);
                boolean pass2 = isValidBST(r2) && isBalanced(r2) && in2.equals(Arrays.stream(arr2).boxed().toList()) &&
                                isValidBST(r2b) && isBalanced(r2b) && in2b.equals(Arrays.stream(arr2).boxed().toList()) &&
                                isValidBST(r2c) && isBalanced(r2c) && in2c.equals(Arrays.stream(arr2).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr2));
                System.out.println("  Array:     BST=" + isValidBST(r2) + " Balanced=" + isBalanced(r2) + " Inorder=" + (in2.equals(Arrays.stream(arr2).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r2b) + " Balanced=" + isBalanced(r2b) + " Inorder=" + (in2b.equals(Arrays.stream(arr2).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r2c) + " Balanced=" + isBalanced(r2c) + " Inorder=" + (in2c.equals(Arrays.stream(arr2).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
                allPassed &= pass2;
                System.out.println();

        // Test 3: Single element
                System.out.println("Test 3: Single element");
                int[] arr3 = {1};
                ListNode head3 = createList(arr3);
                TreeNode r3 = sol.sortedListToBST(head3);
                ListNode head3b = createList(arr3);
                TreeNode r3b = sol.sortedListToBSTInPlace(head3b);
                ListNode head3c = createList(arr3);
                TreeNode r3c = sol.sortedListToBSTInorder(head3c);
                List<Integer> in3 = new ArrayList<>();
                List<Integer> in3b = new ArrayList<>();
                List<Integer> in3c = new ArrayList<>();
                inorder(r3, in3);
                inorder(r3b, in3b);
                inorder(r3c, in3c);
                boolean pass3 = isValidBST(r3) && isBalanced(r3) && in3.equals(Arrays.stream(arr3).boxed().toList()) &&
                                isValidBST(r3b) && isBalanced(r3b) && in3b.equals(Arrays.stream(arr3).boxed().toList()) &&
                                isValidBST(r3c) && isBalanced(r3c) && in3c.equals(Arrays.stream(arr3).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr3));
                System.out.println("  Array:     BST=" + isValidBST(r3) + " Balanced=" + isBalanced(r3) + " Inorder=" + (in3.equals(Arrays.stream(arr3).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r3b) + " Balanced=" + isBalanced(r3b) + " Inorder=" + (in3b.equals(Arrays.stream(arr3).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r3c) + " Balanced=" + isBalanced(r3c) + " Inorder=" + (in3c.equals(Arrays.stream(arr3).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
                allPassed &= pass3;
                System.out.println();

        // Test 4: Two elements
                System.out.println("Test 4: Two elements");
                int[] arr4 = {1, 2};
                ListNode head4 = createList(arr4);
                TreeNode r4 = sol.sortedListToBST(head4);
                ListNode head4b = createList(arr4);
                TreeNode r4b = sol.sortedListToBSTInPlace(head4b);
                ListNode head4c = createList(arr4);
                TreeNode r4c = sol.sortedListToBSTInorder(head4c);
                List<Integer> in4 = new ArrayList<>();
                List<Integer> in4b = new ArrayList<>();
                List<Integer> in4c = new ArrayList<>();
                inorder(r4, in4);
                inorder(r4b, in4b);
                inorder(r4c, in4c);
                boolean pass4 = isValidBST(r4) && isBalanced(r4) && in4.equals(Arrays.stream(arr4).boxed().toList()) &&
                                isValidBST(r4b) && isBalanced(r4b) && in4b.equals(Arrays.stream(arr4).boxed().toList()) &&
                                isValidBST(r4c) && isBalanced(r4c) && in4c.equals(Arrays.stream(arr4).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr4));
                System.out.println("  Array:     BST=" + isValidBST(r4) + " Balanced=" + isBalanced(r4) + " Inorder=" + (in4.equals(Arrays.stream(arr4).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r4b) + " Balanced=" + isBalanced(r4b) + " Inorder=" + (in4b.equals(Arrays.stream(arr4).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r4c) + " Balanced=" + isBalanced(r4c) + " Inorder=" + (in4c.equals(Arrays.stream(arr4).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
                allPassed &= pass4;
                System.out.println();

        // Test 5: Three elements
                System.out.println("Test 5: Three elements");
                int[] arr5 = {1, 2, 3};
                ListNode head5 = createList(arr5);
                TreeNode r5 = sol.sortedListToBST(head5);
                ListNode head5b = createList(arr5);
                TreeNode r5b = sol.sortedListToBSTInPlace(head5b);
                ListNode head5c = createList(arr5);
                TreeNode r5c = sol.sortedListToBSTInorder(head5c);
                List<Integer> in5 = new ArrayList<>();
                List<Integer> in5b = new ArrayList<>();
                List<Integer> in5c = new ArrayList<>();
                inorder(r5, in5);
                inorder(r5b, in5b);
                inorder(r5c, in5c);
                boolean pass5 = isValidBST(r5) && isBalanced(r5) && in5.equals(Arrays.stream(arr5).boxed().toList()) &&
                                isValidBST(r5b) && isBalanced(r5b) && in5b.equals(Arrays.stream(arr5).boxed().toList()) &&
                                isValidBST(r5c) && isBalanced(r5c) && in5c.equals(Arrays.stream(arr5).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr5));
                System.out.println("  Array:     BST=" + isValidBST(r5) + " Balanced=" + isBalanced(r5) + " Inorder=" + (in5.equals(Arrays.stream(arr5).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r5b) + " Balanced=" + isBalanced(r5b) + " Inorder=" + (in5b.equals(Arrays.stream(arr5).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r5c) + " Balanced=" + isBalanced(r5c) + " Inorder=" + (in5c.equals(Arrays.stream(arr5).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
                allPassed &= pass5;
                System.out.println();

        // Test 6: Four elements
                System.out.println("Test 6: Four elements");
                int[] arr6 = {1, 2, 3, 4};
                ListNode head6 = createList(arr6);
                TreeNode r6 = sol.sortedListToBST(head6);
                ListNode head6b = createList(arr6);
                TreeNode r6b = sol.sortedListToBSTInPlace(head6b);
                ListNode head6c = createList(arr6);
                TreeNode r6c = sol.sortedListToBSTInorder(head6c);
                List<Integer> in6 = new ArrayList<>();
                List<Integer> in6b = new ArrayList<>();
                List<Integer> in6c = new ArrayList<>();
                inorder(r6, in6);
                inorder(r6b, in6b);
                inorder(r6c, in6c);
                boolean pass6 = isValidBST(r6) && isBalanced(r6) && in6.equals(Arrays.stream(arr6).boxed().toList()) &&
                                isValidBST(r6b) && isBalanced(r6b) && in6b.equals(Arrays.stream(arr6).boxed().toList()) &&
                                isValidBST(r6c) && isBalanced(r6c) && in6c.equals(Arrays.stream(arr6).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr6));
                System.out.println("  Array:     BST=" + isValidBST(r6) + " Balanced=" + isBalanced(r6) + " Inorder=" + (in6.equals(Arrays.stream(arr6).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r6b) + " Balanced=" + isBalanced(r6b) + " Inorder=" + (in6b.equals(Arrays.stream(arr6).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r6c) + " Balanced=" + isBalanced(r6c) + " Inorder=" + (in6c.equals(Arrays.stream(arr6).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
                allPassed &= pass6;
                System.out.println();

        // Test 7: Five elements
                System.out.println("Test 7: Five elements");
                int[] arr7 = {1, 2, 3, 4, 5};
                ListNode head7 = createList(arr7);
                TreeNode r7 = sol.sortedListToBST(head7);
                ListNode head7b = createList(arr7);
                TreeNode r7b = sol.sortedListToBSTInPlace(head7b);
                ListNode head7c = createList(arr7);
                TreeNode r7c = sol.sortedListToBSTInorder(head7c);
                List<Integer> in7 = new ArrayList<>();
                List<Integer> in7b = new ArrayList<>();
                List<Integer> in7c = new ArrayList<>();
                inorder(r7, in7);
                inorder(r7b, in7b);
                inorder(r7c, in7c);
                boolean pass7 = isValidBST(r7) && isBalanced(r7) && in7.equals(Arrays.stream(arr7).boxed().toList()) &&
                                isValidBST(r7b) && isBalanced(r7b) && in7b.equals(Arrays.stream(arr7).boxed().toList()) &&
                                isValidBST(r7c) && isBalanced(r7c) && in7c.equals(Arrays.stream(arr7).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr7));
                System.out.println("  Array:     BST=" + isValidBST(r7) + " Balanced=" + isBalanced(r7) + " Inorder=" + (in7.equals(Arrays.stream(arr7).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r7b) + " Balanced=" + isBalanced(r7b) + " Inorder=" + (in7b.equals(Arrays.stream(arr7).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r7c) + " Balanced=" + isBalanced(r7c) + " Inorder=" + (in7c.equals(Arrays.stream(arr7).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
                allPassed &= pass7;
                System.out.println();

        // Test 8: Seven elements (perfect tree)
                System.out.println("Test 8: Seven elements (perfect)");
                int[] arr8 = {1, 2, 3, 4, 5, 6, 7};
                ListNode head8 = createList(arr8);
                TreeNode r8 = sol.sortedListToBST(head8);
                ListNode head8b = createList(arr8);
                TreeNode r8b = sol.sortedListToBSTInPlace(head8b);
                ListNode head8c = createList(arr8);
                TreeNode r8c = sol.sortedListToBSTInorder(head8c);
                List<Integer> in8 = new ArrayList<>();
                List<Integer> in8b = new ArrayList<>();
                List<Integer> in8c = new ArrayList<>();
                inorder(r8, in8);
                inorder(r8b, in8b);
                inorder(r8c, in8c);
                boolean pass8 = isValidBST(r8) && isBalanced(r8) && in8.equals(Arrays.stream(arr8).boxed().toList()) &&
                                isValidBST(r8b) && isBalanced(r8b) && in8b.equals(Arrays.stream(arr8).boxed().toList()) &&
                                isValidBST(r8c) && isBalanced(r8c) && in8c.equals(Arrays.stream(arr8).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr8));
                System.out.println("  Array:     BST=" + isValidBST(r8) + " Balanced=" + isBalanced(r8) + " Inorder=" + (in8.equals(Arrays.stream(arr8).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r8b) + " Balanced=" + isBalanced(r8b) + " Inorder=" + (in8b.equals(Arrays.stream(arr8).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r8c) + " Balanced=" + isBalanced(r8c) + " Inorder=" + (in8c.equals(Arrays.stream(arr8).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
                allPassed &= pass8;
                System.out.println();

        // Test 9: Negative values
                System.out.println("Test 9: Negative values");
                int[] arr9 = {-5, -4, -3, -2, -1};
                ListNode head9 = createList(arr9);
                TreeNode r9 = sol.sortedListToBST(head9);
                ListNode head9b = createList(arr9);
                TreeNode r9b = sol.sortedListToBSTInPlace(head9b);
                ListNode head9c = createList(arr9);
                TreeNode r9c = sol.sortedListToBSTInorder(head9c);
                List<Integer> in9 = new ArrayList<>();
                List<Integer> in9b = new ArrayList<>();
                List<Integer> in9c = new ArrayList<>();
                inorder(r9, in9);
                inorder(r9b, in9b);
                inorder(r9c, in9c);
                boolean pass9 = isValidBST(r9) && isBalanced(r9) && in9.equals(Arrays.stream(arr9).boxed().toList()) &&
                                isValidBST(r9b) && isBalanced(r9b) && in9b.equals(Arrays.stream(arr9).boxed().toList()) &&
                                isValidBST(r9c) && isBalanced(r9c) && in9c.equals(Arrays.stream(arr9).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr9));
                System.out.println("  Array:     BST=" + isValidBST(r9) + " Balanced=" + isBalanced(r9) + " Inorder=" + (in9.equals(Arrays.stream(arr9).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r9b) + " Balanced=" + isBalanced(r9b) + " Inorder=" + (in9b.equals(Arrays.stream(arr9).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r9c) + " Balanced=" + isBalanced(r9c) + " Inorder=" + (in9c.equals(Arrays.stream(arr9).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
                allPassed &= pass9;
                System.out.println();

        // Test 10: Large list (15 elements)
                System.out.println("Test 10: Large list (15 elements)");
                int[] arr10 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
                ListNode head10 = createList(arr10);
                TreeNode r10 = sol.sortedListToBST(head10);
                ListNode head10b = createList(arr10);
                TreeNode r10b = sol.sortedListToBSTInPlace(head10b);
                ListNode head10c = createList(arr10);
                TreeNode r10c = sol.sortedListToBSTInorder(head10c);
                List<Integer> in10 = new ArrayList<>();
                List<Integer> in10b = new ArrayList<>();
                List<Integer> in10c = new ArrayList<>();
                inorder(r10, in10);
                inorder(r10b, in10b);
                inorder(r10c, in10c);
                boolean pass10 = isValidBST(r10) && isBalanced(r10) && in10.equals(Arrays.stream(arr10).boxed().toList()) &&
                                 isValidBST(r10b) && isBalanced(r10b) && in10b.equals(Arrays.stream(arr10).boxed().toList()) &&
                                 isValidBST(r10c) && isBalanced(r10c) && in10c.equals(Arrays.stream(arr10).boxed().toList());
                System.out.println("  Input: " + arrayToString(arr10));
                System.out.println("  Array:     BST=" + isValidBST(r10) + " Balanced=" + isBalanced(r10) + " Inorder=" + (in10.equals(Arrays.stream(arr10).boxed().toList()) ? "?" : "?"));
                System.out.println("  InPlace:   BST=" + isValidBST(r10b) + " Balanced=" + isBalanced(r10b) + " Inorder=" + (in10b.equals(Arrays.stream(arr10).boxed().toList()) ? "?" : "?"));
                System.out.println("  Inorder:   BST=" + isValidBST(r10c) + " Balanced=" + isBalanced(r10c) + " Inorder=" + (in10c.equals(Arrays.stream(arr10).boxed().toList()) ? "?" : "?"));
                System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
                allPassed &= pass10;
                System.out.println();

                // Test 11: Empty list
                System.out.println("Test 11: Empty list");
                ListNode head11 = null;
                TreeNode r11 = sol.sortedListToBST(head11);
                TreeNode r11b = sol.sortedListToBSTInPlace(head11);
                TreeNode r11c = sol.sortedListToBSTInorder(head11);
                boolean pass11 = r11 == null && r11b == null && r11c == null;
                System.out.println("  Input: null");
                System.out.println("  Array:     " + (r11 == null ? "null ?" : "? FAIL"));
                System.out.println("  InPlace:   " + (r11b == null ? "null ?" : "? FAIL"));
                System.out.println("  Inorder:   " + (r11c == null ? "null ?" : "? FAIL"));
                System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
                allPassed &= pass11;
                System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}