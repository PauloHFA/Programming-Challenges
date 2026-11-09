import java.util.*;

/**
 * LeetCode 108 - Convert Sorted Array to Binary Search Tree
 * 
 * Dado um array de inteiros nums onde os elementos estão ordenados em ordem
 * crescente, converta-o em uma árvore binária de busca balanceada em altura.
 */
public class Solution {

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
     * Método 1: Recursivo - Meio como raiz (Principal)
     * Escolhe o elemento do meio como raiz para garantir balanceamento.
     * Recursivamente constrói subárvore esquerda com metade esquerda,
     * subárvore direita com metade direita.
     * Tempo: O(n), Espaço: O(log n) stack (árvore balanceada)
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) return null;

        // Escolhe meio (evita overflow)
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = build(nums, left, mid - 1);
        root.right = build(nums, mid + 1, right);

        return root;
    }

    /**
     * Método 2: Iterativo com Stack
     * Simula a recursão usando stack explícita.
     * Cada entrada na stack: (nó pai, left, right, isLeftChild)
     * Tempo: O(n), Espaço: O(log n)
     */
    public TreeNode sortedArrayToBSTIterative(int[] nums) {
        if (nums.length == 0) return null;

        TreeNode root = new TreeNode(0); // placeholder
        Stack<Object[]> stack = new Stack<>();
        // [parent, left, right, isLeftChild, nodeRef]
        stack.push(new Object[]{null, 0, nums.length - 1, false, root});

        while (!stack.isEmpty()) {
            Object[] frame = stack.pop();
            TreeNode parent = (TreeNode) frame[0];
            int left = (int) frame[1];
            int right = (int) frame[2];
            boolean isLeft = (boolean) frame[3];
            TreeNode node = (TreeNode) frame[4];

            if (left > right) {
                if (parent != null) {
                    if (isLeft) parent.left = null;
                    else parent.right = null;
                }
                continue;
            }

            int mid = left + (right - left) / 2;
            node.val = nums[mid];

            if (parent != null) {
                if (isLeft) parent.left = node;
                else parent.right = node;
            }

            // Push right first (process left first due to stack LIFO)
            TreeNode rightNode = new TreeNode(0);
            stack.push(new Object[]{node, mid + 1, right, false, rightNode});

            TreeNode leftNode = new TreeNode(0);
            stack.push(new Object[]{node, left, mid - 1, true, leftNode});
        }

        return root;
    }

    /**
     * Método 3: Recursivo com mid = (left + right + 1) / 2 (teto)
     * Escolhe meio superior quando número par de elementos.
     * Também produz árvore balanceada.
     * Tempo: O(n), Espaço: O(log n)
     */
    public TreeNode sortedArrayToBSTCeil(int[] nums) {
        return buildCeil(nums, 0, nums.length - 1);
    }

    private TreeNode buildCeil(int[] nums, int left, int right) {
        if (left > right) return null;

        // Teto: (left + right + 1) / 2
        int mid = left + (right - left + 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = buildCeil(nums, left, mid - 1);
        root.right = buildCeil(nums, mid + 1, right);

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

    // Helper para inorder traversal (deve dar array ordenado)
    private static void inorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    // Helper para preorder traversal (para debug)
    private static void preorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
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

        System.out.println("=== Convert Sorted Array to BST Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        int[] nums1 = {-10, -3, 0, 5, 9};
        TreeNode r1 = sol.sortedArrayToBST(nums1);
        TreeNode r1i = sol.sortedArrayToBSTIterative(nums1);
        TreeNode r1c = sol.sortedArrayToBSTCeil(nums1);
        List<Integer> in1 = new ArrayList<>();
        List<Integer> in1i = new ArrayList<>();
        List<Integer> in1c = new ArrayList<>();
        inorder(r1, in1);
        inorder(r1i, in1i);
        inorder(r1c, in1c);
        boolean pass1 = isValidBST(r1) && isBalanced(r1) && in1.equals(Arrays.stream(nums1).boxed().toList()) &&
                        isValidBST(r1i) && isBalanced(r1i) && in1i.equals(Arrays.stream(nums1).boxed().toList()) &&
                        isValidBST(r1c) && isBalanced(r1c) && in1c.equals(Arrays.stream(nums1).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums1));
        System.out.println("  Recursive: BST=" + isValidBST(r1) + " Balanced=" + isBalanced(r1) + " Inorder=" + (in1.equals(Arrays.stream(nums1).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r1i) + " Balanced=" + isBalanced(r1i) + " Inorder=" + (in1i.equals(Arrays.stream(nums1).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r1c) + " Balanced=" + isBalanced(r1c) + " Inorder=" + (in1c.equals(Arrays.stream(nums1).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        int[] nums2 = {1, 3};
        TreeNode r2 = sol.sortedArrayToBST(nums2);
        TreeNode r2i = sol.sortedArrayToBSTIterative(nums2);
        TreeNode r2c = sol.sortedArrayToBSTCeil(nums2);
        List<Integer> in2 = new ArrayList<>();
        List<Integer> in2i = new ArrayList<>();
        List<Integer> in2c = new ArrayList<>();
        inorder(r2, in2);
        inorder(r2i, in2i);
        inorder(r2c, in2c);
        boolean pass2 = isValidBST(r2) && isBalanced(r2) && in2.equals(Arrays.stream(nums2).boxed().toList()) &&
                        isValidBST(r2i) && isBalanced(r2i) && in2i.equals(Arrays.stream(nums2).boxed().toList()) &&
                        isValidBST(r2c) && isBalanced(r2c) && in2c.equals(Arrays.stream(nums2).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums2));
        System.out.println("  Recursive: BST=" + isValidBST(r2) + " Balanced=" + isBalanced(r2) + " Inorder=" + (in2.equals(Arrays.stream(nums2).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r2i) + " Balanced=" + isBalanced(r2i) + " Inorder=" + (in2i.equals(Arrays.stream(nums2).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r2c) + " Balanced=" + isBalanced(r2c) + " Inorder=" + (in2c.equals(Arrays.stream(nums2).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single element
        System.out.println("Test 3: Single element");
        int[] nums3 = {1};
        TreeNode r3 = sol.sortedArrayToBST(nums3);
        TreeNode r3i = sol.sortedArrayToBSTIterative(nums3);
        TreeNode r3c = sol.sortedArrayToBSTCeil(nums3);
        List<Integer> in3 = new ArrayList<>();
        List<Integer> in3i = new ArrayList<>();
        List<Integer> in3c = new ArrayList<>();
        inorder(r3, in3);
        inorder(r3i, in3i);
        inorder(r3c, in3c);
        boolean pass3 = isValidBST(r3) && isBalanced(r3) && in3.equals(Arrays.stream(nums3).boxed().toList()) &&
                        isValidBST(r3i) && isBalanced(r3i) && in3i.equals(Arrays.stream(nums3).boxed().toList()) &&
                        isValidBST(r3c) && isBalanced(r3c) && in3c.equals(Arrays.stream(nums3).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums3));
        System.out.println("  Recursive: BST=" + isValidBST(r3) + " Balanced=" + isBalanced(r3) + " Inorder=" + (in3.equals(Arrays.stream(nums3).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r3i) + " Balanced=" + isBalanced(r3i) + " Inorder=" + (in3i.equals(Arrays.stream(nums3).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r3c) + " Balanced=" + isBalanced(r3c) + " Inorder=" + (in3c.equals(Arrays.stream(nums3).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Two elements
        System.out.println("Test 4: Two elements");
        int[] nums4 = {1, 2};
        TreeNode r4 = sol.sortedArrayToBST(nums4);
        TreeNode r4i = sol.sortedArrayToBSTIterative(nums4);
        TreeNode r4c = sol.sortedArrayToBSTCeil(nums4);
        List<Integer> in4 = new ArrayList<>();
        List<Integer> in4i = new ArrayList<>();
        List<Integer> in4c = new ArrayList<>();
        inorder(r4, in4);
        inorder(r4i, in4i);
        inorder(r4c, in4c);
        boolean pass4 = isValidBST(r4) && isBalanced(r4) && in4.equals(Arrays.stream(nums4).boxed().toList()) &&
                        isValidBST(r4i) && isBalanced(r4i) && in4i.equals(Arrays.stream(nums4).boxed().toList()) &&
                        isValidBST(r4c) && isBalanced(r4c) && in4c.equals(Arrays.stream(nums4).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums4));
        System.out.println("  Recursive: BST=" + isValidBST(r4) + " Balanced=" + isBalanced(r4) + " Inorder=" + (in4.equals(Arrays.stream(nums4).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r4i) + " Balanced=" + isBalanced(r4i) + " Inorder=" + (in4i.equals(Arrays.stream(nums4).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r4c) + " Balanced=" + isBalanced(r4c) + " Inorder=" + (in4c.equals(Arrays.stream(nums4).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Three elements
        System.out.println("Test 5: Three elements");
        int[] nums5 = {1, 2, 3};
        TreeNode r5 = sol.sortedArrayToBST(nums5);
        TreeNode r5i = sol.sortedArrayToBSTIterative(nums5);
        TreeNode r5c = sol.sortedArrayToBSTCeil(nums5);
        List<Integer> in5 = new ArrayList<>();
        List<Integer> in5i = new ArrayList<>();
        List<Integer> in5c = new ArrayList<>();
        inorder(r5, in5);
        inorder(r5i, in5i);
        inorder(r5c, in5c);
        boolean pass5 = isValidBST(r5) && isBalanced(r5) && in5.equals(Arrays.stream(nums5).boxed().toList()) &&
                        isValidBST(r5i) && isBalanced(r5i) && in5i.equals(Arrays.stream(nums5).boxed().toList()) &&
                        isValidBST(r5c) && isBalanced(r5c) && in5c.equals(Arrays.stream(nums5).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums5));
        System.out.println("  Recursive: BST=" + isValidBST(r5) + " Balanced=" + isBalanced(r5) + " Inorder=" + (in5.equals(Arrays.stream(nums5).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r5i) + " Balanced=" + isBalanced(r5i) + " Inorder=" + (in5i.equals(Arrays.stream(nums5).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r5c) + " Balanced=" + isBalanced(r5c) + " Inorder=" + (in5c.equals(Arrays.stream(nums5).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Four elements
        System.out.println("Test 6: Four elements");
        int[] nums6 = {1, 2, 3, 4};
        TreeNode r6 = sol.sortedArrayToBST(nums6);
        TreeNode r6i = sol.sortedArrayToBSTIterative(nums6);
        TreeNode r6c = sol.sortedArrayToBSTCeil(nums6);
        List<Integer> in6 = new ArrayList<>();
        List<Integer> in6i = new ArrayList<>();
        List<Integer> in6c = new ArrayList<>();
        inorder(r6, in6);
        inorder(r6i, in6i);
        inorder(r6c, in6c);
        boolean pass6 = isValidBST(r6) && isBalanced(r6) && in6.equals(Arrays.stream(nums6).boxed().toList()) &&
                        isValidBST(r6i) && isBalanced(r6i) && in6i.equals(Arrays.stream(nums6).boxed().toList()) &&
                        isValidBST(r6c) && isBalanced(r6c) && in6c.equals(Arrays.stream(nums6).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums6));
        System.out.println("  Recursive: BST=" + isValidBST(r6) + " Balanced=" + isBalanced(r6) + " Inorder=" + (in6.equals(Arrays.stream(nums6).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r6i) + " Balanced=" + isBalanced(r6i) + " Inorder=" + (in6i.equals(Arrays.stream(nums6).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r6c) + " Balanced=" + isBalanced(r6c) + " Inorder=" + (in6c.equals(Arrays.stream(nums6).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Five elements
        System.out.println("Test 7: Five elements");
        int[] nums7 = {1, 2, 3, 4, 5};
        TreeNode r7 = sol.sortedArrayToBST(nums7);
        TreeNode r7i = sol.sortedArrayToBSTIterative(nums7);
        TreeNode r7c = sol.sortedArrayToBSTCeil(nums7);
        List<Integer> in7 = new ArrayList<>();
        List<Integer> in7i = new ArrayList<>();
        List<Integer> in7c = new ArrayList<>();
        inorder(r7, in7);
        inorder(r7i, in7i);
        inorder(r7c, in7c);
        boolean pass7 = isValidBST(r7) && isBalanced(r7) && in7.equals(Arrays.stream(nums7).boxed().toList()) &&
                        isValidBST(r7i) && isBalanced(r7i) && in7i.equals(Arrays.stream(nums7).boxed().toList()) &&
                        isValidBST(r7c) && isBalanced(r7c) && in7c.equals(Arrays.stream(nums7).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums7));
        System.out.println("  Recursive: BST=" + isValidBST(r7) + " Balanced=" + isBalanced(r7) + " Inorder=" + (in7.equals(Arrays.stream(nums7).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r7i) + " Balanced=" + isBalanced(r7i) + " Inorder=" + (in7i.equals(Arrays.stream(nums7).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r7c) + " Balanced=" + isBalanced(r7c) + " Inorder=" + (in7c.equals(Arrays.stream(nums7).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Seven elements (perfect tree)
        System.out.println("Test 8: Seven elements (perfect)");
        int[] nums8 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode r8 = sol.sortedArrayToBST(nums8);
        TreeNode r8i = sol.sortedArrayToBSTIterative(nums8);
        TreeNode r8c = sol.sortedArrayToBSTCeil(nums8);
        List<Integer> in8 = new ArrayList<>();
        List<Integer> in8i = new ArrayList<>();
        List<Integer> in8c = new ArrayList<>();
        inorder(r8, in8);
        inorder(r8i, in8i);
        inorder(r8c, in8c);
        boolean pass8 = isValidBST(r8) && isBalanced(r8) && in8.equals(Arrays.stream(nums8).boxed().toList()) &&
                        isValidBST(r8i) && isBalanced(r8i) && in8i.equals(Arrays.stream(nums8).boxed().toList()) &&
                        isValidBST(r8c) && isBalanced(r8c) && in8c.equals(Arrays.stream(nums8).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums8));
        System.out.println("  Recursive: BST=" + isValidBST(r8) + " Balanced=" + isBalanced(r8) + " Inorder=" + (in8.equals(Arrays.stream(nums8).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r8i) + " Balanced=" + isBalanced(r8i) + " Inorder=" + (in8i.equals(Arrays.stream(nums8).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r8c) + " Balanced=" + isBalanced(r8c) + " Inorder=" + (in8c.equals(Arrays.stream(nums8).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Negative values
        System.out.println("Test 9: Negative values");
        int[] nums9 = {-5, -4, -3, -2, -1};
        TreeNode r9 = sol.sortedArrayToBST(nums9);
        TreeNode r9i = sol.sortedArrayToBSTIterative(nums9);
        TreeNode r9c = sol.sortedArrayToBSTCeil(nums9);
        List<Integer> in9 = new ArrayList<>();
        List<Integer> in9i = new ArrayList<>();
        List<Integer> in9c = new ArrayList<>();
        inorder(r9, in9);
        inorder(r9i, in9i);
        inorder(r9c, in9c);
        boolean pass9 = isValidBST(r9) && isBalanced(r9) && in9.equals(Arrays.stream(nums9).boxed().toList()) &&
                        isValidBST(r9i) && isBalanced(r9i) && in9i.equals(Arrays.stream(nums9).boxed().toList()) &&
                        isValidBST(r9c) && isBalanced(r9c) && in9c.equals(Arrays.stream(nums9).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums9));
        System.out.println("  Recursive: BST=" + isValidBST(r9) + " Balanced=" + isBalanced(r9) + " Inorder=" + (in9.equals(Arrays.stream(nums9).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r9i) + " Balanced=" + isBalanced(r9i) + " Inorder=" + (in9i.equals(Arrays.stream(nums9).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r9c) + " Balanced=" + isBalanced(r9c) + " Inorder=" + (in9c.equals(Arrays.stream(nums9).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Large array
        System.out.println("Test 10: Large array (15 elements)");
        int[] nums10 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode r10 = sol.sortedArrayToBST(nums10);
        TreeNode r10i = sol.sortedArrayToBSTIterative(nums10);
        TreeNode r10c = sol.sortedArrayToBSTCeil(nums10);
        List<Integer> in10 = new ArrayList<>();
        List<Integer> in10i = new ArrayList<>();
        List<Integer> in10c = new ArrayList<>();
        inorder(r10, in10);
        inorder(r10i, in10i);
        inorder(r10c, in10c);
        boolean pass10 = isValidBST(r10) && isBalanced(r10) && in10.equals(Arrays.stream(nums10).boxed().toList()) &&
                         isValidBST(r10i) && isBalanced(r10i) && in10i.equals(Arrays.stream(nums10).boxed().toList()) &&
                         isValidBST(r10c) && isBalanced(r10c) && in10c.equals(Arrays.stream(nums10).boxed().toList());
        System.out.println("  Input: " + arrayToString(nums10));
        System.out.println("  Recursive: BST=" + isValidBST(r10) + " Balanced=" + isBalanced(r10) + " Inorder=" + (in10.equals(Arrays.stream(nums10).boxed().toList()) ? "?" : "?"));
        System.out.println("  Iterative: BST=" + isValidBST(r10i) + " Balanced=" + isBalanced(r10i) + " Inorder=" + (in10i.equals(Arrays.stream(nums10).boxed().toList()) ? "?" : "?"));
        System.out.println("  Ceil:      BST=" + isValidBST(r10c) + " Balanced=" + isBalanced(r10c) + " Inorder=" + (in10c.equals(Arrays.stream(nums10).boxed().toList()) ? "?" : "?"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Empty array
        System.out.println("Test 11: Empty array");
        int[] nums11 = {};
        TreeNode r11 = sol.sortedArrayToBST(nums11);
        TreeNode r11i = sol.sortedArrayToBSTIterative(nums11);
        TreeNode r11c = sol.sortedArrayToBSTCeil(nums11);
        boolean pass11 = r11 == null && r11i == null && r11c == null;
        System.out.println("  Input: []");
        System.out.println("  Recursive: " + (r11 == null ? "null ?" : "? FAIL"));
        System.out.println("  Iterative: " + (r11i == null ? "null ?" : "? FAIL"));
        System.out.println("  Ceil:      " + (r11c == null ? "null ?" : "? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}