import java.util.*;

/**
 * LeetCode 105 - Construct Binary Tree from Preorder and Inorder Traversal
 * 
 * Dado dois arrays de inteiros preorder e inorder onde preorder é a travessia
 * pré-ordem de uma árvore binária e inorder é a travessia em ordem da mesma
 * árvore, construa e retorne a árvore binária.
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
     * Método 1: Recursivo com HashMap (Principal)
     * - Preorder: root, left, right
     * - Inorder: left, root, right
     * O primeiro elemento do preorder é a raiz. Encontra raiz no inorder,
     * elementos à esquerda são subárvore esquerda, à direita são subárvore direita.
     * Usa HashMap para busca O(1) do índice no inorder.
     * Tempo: O(n), Espaço: O(n) HashMap + O(h) stack
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderIndex);
    }

    private TreeNode build(int[] preorder, int preStart, int preEnd,
                           int[] inorder, int inStart, int inEnd,
                           Map<Integer, Integer> inorderIndex) {
        if (preStart > preEnd || inStart > inEnd) return null;

        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        int inRootIndex = inorderIndex.get(rootVal);
        int leftSize = inRootIndex - inStart;

        root.left = build(preorder, preStart + 1, preStart + leftSize,
                          inorder, inStart, inRootIndex - 1, inorderIndex);
        root.right = build(preorder, preStart + leftSize + 1, preEnd,
                           inorder, inRootIndex + 1, inEnd, inorderIndex);

        return root;
    }

    /**
     * Método 2: Iterativo com Stack
     * Simula a recursão usando stack explícita.
     * Tempo: O(n), Espaço: O(n)
     */
    public TreeNode buildTreeIterative(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;

        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int inorderIndex = 0;

        for (int i = 1; i < preorder.length; i++) {
            int preVal = preorder[i];
            TreeNode node = stack.peek();

            if (node.val != inorder[inorderIndex]) {
                // Próximo nó é filho esquerdo
                node.left = new TreeNode(preVal);
                stack.push(node.left);
            } else {
                // Encontrou o nó no inorder, volta para achar pai do direito
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                // Próximo nó é filho direito do último pop
                node.right = new TreeNode(preVal);
                stack.push(node.right);
            }
        }

        return root;
    }

    /**
     * Método 3: Recursivo sem HashMap (busca linear)
     * Mais simples mas O(n²) no pior caso.
     * Tempo: O(n²), Espaço: O(h)
     */
    public TreeNode buildTreeNoMap(int[] preorder, int[] inorder) {
        return buildNoMap(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildNoMap(int[] preorder, int preStart, int preEnd,
                                int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) return null;

        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // Busca linear no inorder
        int inRootIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                inRootIndex = i;
                break;
            }
        }

        int leftSize = inRootIndex - inStart;

        root.left = buildNoMap(preorder, preStart + 1, preStart + leftSize,
                               inorder, inStart, inRootIndex - 1);
        root.right = buildNoMap(preorder, preStart + leftSize + 1, preEnd,
                                inorder, inRootIndex + 1, inEnd);

        return root;
    }

    // Helper para verificar se duas árvores são iguais
    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // Helper para construir árvore esperada manualmente
    private static TreeNode buildExpected1() {
        //       3
        //      / \
        //     9  20
        //       /  \
        //      15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }

    private static TreeNode buildExpected2() {
        //   -1
        TreeNode root = new TreeNode(-1);
        return root;
    }

    private static TreeNode buildExpected3() {
        //     1
        //    / \
        //   2   3
        //  / \
        // 4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        return root;
    }

    private static TreeNode buildExpected4() {
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        return root;
    }

    private static TreeNode buildExpected5() {
        //     1
        //    /
        //   2
        //  /
        // 3
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        return root;
    }

    // Helper para preorder traversal (para debug)
    private static void preorderPrint(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);
        preorderPrint(root.left, result);
        preorderPrint(root.right, result);
    }

    // Helper para inorder traversal (para debug)
    private static void inorderPrint(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorderPrint(root.left, result);
        result.add(root.val);
        inorderPrint(root.right, result);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Construct Binary Tree from Preorder and Inorder Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        int[] pre1 = {3, 9, 20, 15, 7};
        int[] in1 = {9, 3, 15, 20, 7};
        TreeNode expected1 = buildExpected1();
        TreeNode r1 = sol.buildTree(pre1, in1);
        TreeNode r1i = sol.buildTreeIterative(pre1, in1);
        TreeNode r1n = sol.buildTreeNoMap(pre1, in1);
        boolean pass1 = isSameTree(r1, expected1) && isSameTree(r1i, expected1) && isSameTree(r1n, expected1);
        System.out.println("  Preorder: " + Arrays.toString(pre1));
        System.out.println("  Inorder:  " + Arrays.toString(in1));
        System.out.println("  Recursive: " + (isSameTree(r1, expected1) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r1i, expected1) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r1n, expected1) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Single node
        System.out.println("Test 2: Single node");
        int[] pre2 = {-1};
        int[] in2 = {-1};
        TreeNode expected2 = buildExpected2();
        TreeNode r2 = sol.buildTree(pre2, in2);
        TreeNode r2i = sol.buildTreeIterative(pre2, in2);
        TreeNode r2n = sol.buildTreeNoMap(pre2, in2);
        boolean pass2 = isSameTree(r2, expected2) && isSameTree(r2i, expected2) && isSameTree(r2n, expected2);
        System.out.println("  Preorder: " + Arrays.toString(pre2));
        System.out.println("  Inorder:  " + Arrays.toString(in2));
        System.out.println("  Recursive: " + (isSameTree(r2, expected2) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r2i, expected2) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r2n, expected2) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Complete tree
        System.out.println("Test 3: Complete tree");
        int[] pre3 = {1, 2, 4, 5, 3};
        int[] in3 = {4, 2, 5, 1, 3};
        TreeNode expected3 = buildExpected3();
        TreeNode r3 = sol.buildTree(pre3, in3);
        TreeNode r3i = sol.buildTreeIterative(pre3, in3);
        TreeNode r3n = sol.buildTreeNoMap(pre3, in3);
        boolean pass3 = isSameTree(r3, expected3) && isSameTree(r3i, expected3) && isSameTree(r3n, expected3);
        System.out.println("  Preorder: " + Arrays.toString(pre3));
        System.out.println("  Inorder:  " + Arrays.toString(in3));
        System.out.println("  Recursive: " + (isSameTree(r3, expected3) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r3i, expected3) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r3n, expected3) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Right skewed
        System.out.println("Test 4: Right skewed");
        int[] pre4 = {1, 2, 3};
        int[] in4 = {1, 2, 3};
        TreeNode expected4 = buildExpected4();
        TreeNode r4 = sol.buildTree(pre4, in4);
        TreeNode r4i = sol.buildTreeIterative(pre4, in4);
        TreeNode r4n = sol.buildTreeNoMap(pre4, in4);
        boolean pass4 = isSameTree(r4, expected4) && isSameTree(r4i, expected4) && isSameTree(r4n, expected4);
        System.out.println("  Preorder: " + Arrays.toString(pre4));
        System.out.println("  Inorder:  " + Arrays.toString(in4));
        System.out.println("  Recursive: " + (isSameTree(r4, expected4) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r4i, expected4) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r4n, expected4) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        int[] pre5 = {1, 2, 3};
        int[] in5 = {3, 2, 1};
        TreeNode expected5 = buildExpected5();
        TreeNode r5 = sol.buildTree(pre5, in5);
        TreeNode r5i = sol.buildTreeIterative(pre5, in5);
        TreeNode r5n = sol.buildTreeNoMap(pre5, in5);
        boolean pass5 = isSameTree(r5, expected5) && isSameTree(r5i, expected5) && isSameTree(r5n, expected5);
        System.out.println("  Preorder: " + Arrays.toString(pre5));
        System.out.println("  Inorder:  " + Arrays.toString(in5));
        System.out.println("  Recursive: " + (isSameTree(r5, expected5) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r5i, expected5) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r5n, expected5) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Larger tree
        System.out.println("Test 6: Larger tree");
        int[] pre6 = {1, 2, 4, 8, 9, 5, 3, 6, 7};
        int[] in6 = {8, 4, 9, 2, 5, 1, 6, 3, 7};
        TreeNode r6 = sol.buildTree(pre6, in6);
        TreeNode r6i = sol.buildTreeIterative(pre6, in6);
        TreeNode r6n = sol.buildTreeNoMap(pre6, in6);
        // Verify by checking traversals match
        List<Integer> preCheck6 = new ArrayList<>();
        List<Integer> inCheck6 = new ArrayList<>();
        preorderPrint(r6, preCheck6);
        inorderPrint(r6, inCheck6);
        boolean pass6 = preCheck6.equals(Arrays.stream(pre6).boxed().toList()) &&
                        inCheck6.equals(Arrays.stream(in6).boxed().toList()) &&
                        isSameTree(r6, r6i) && isSameTree(r6, r6n);
        System.out.println("  Preorder: " + Arrays.toString(pre6));
        System.out.println("  Inorder:  " + Arrays.toString(in6));
        System.out.println("  Preorder check: " + (preCheck6.equals(Arrays.stream(pre6).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Inorder check:  " + (inCheck6.equals(Arrays.stream(in6).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r6, r6i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r6, r6n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Negative values
        System.out.println("Test 7: Negative values");
        int[] pre7 = {-1, -2, -4, -5, -3};
        int[] in7 = {-4, -2, -5, -1, -3};
        TreeNode r7 = sol.buildTree(pre7, in7);
        TreeNode r7i = sol.buildTreeIterative(pre7, in7);
        TreeNode r7n = sol.buildTreeNoMap(pre7, in7);
        List<Integer> preCheck7 = new ArrayList<>();
        List<Integer> inCheck7 = new ArrayList<>();
        preorderPrint(r7, preCheck7);
        inorderPrint(r7, inCheck7);
        boolean pass7 = preCheck7.equals(Arrays.stream(pre7).boxed().toList()) &&
                        inCheck7.equals(Arrays.stream(in7).boxed().toList()) &&
                        isSameTree(r7, r7i) && isSameTree(r7, r7n);
        System.out.println("  Preorder: " + Arrays.toString(pre7));
        System.out.println("  Inorder:  " + Arrays.toString(in7));
        System.out.println("  Preorder check: " + (preCheck7.equals(Arrays.stream(pre7).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Inorder check:  " + (inCheck7.equals(Arrays.stream(in7).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r7, r7i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r7, r7n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Two nodes left
        System.out.println("Test 8: Two nodes left");
        int[] pre8 = {1, 2};
        int[] in8 = {2, 1};
        TreeNode r8 = sol.buildTree(pre8, in8);
        TreeNode r8i = sol.buildTreeIterative(pre8, in8);
        TreeNode r8n = sol.buildTreeNoMap(pre8, in8);
        List<Integer> preCheck8 = new ArrayList<>();
        List<Integer> inCheck8 = new ArrayList<>();
        preorderPrint(r8, preCheck8);
        inorderPrint(r8, inCheck8);
        boolean pass8 = preCheck8.equals(Arrays.stream(pre8).boxed().toList()) &&
                        inCheck8.equals(Arrays.stream(in8).boxed().toList()) &&
                        isSameTree(r8, r8i) && isSameTree(r8, r8n);
        System.out.println("  Preorder: " + Arrays.toString(pre8));
        System.out.println("  Inorder:  " + Arrays.toString(in8));
        System.out.println("  Preorder check: " + (preCheck8.equals(Arrays.stream(pre8).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Inorder check:  " + (inCheck8.equals(Arrays.stream(in8).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r8, r8i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r8, r8n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Two nodes right
        System.out.println("Test 9: Two nodes right");
        int[] pre9 = {1, 2};
        int[] in9 = {1, 2};
        TreeNode r9 = sol.buildTree(pre9, in9);
        TreeNode r9i = sol.buildTreeIterative(pre9, in9);
        TreeNode r9n = sol.buildTreeNoMap(pre9, in9);
        List<Integer> preCheck9 = new ArrayList<>();
        List<Integer> inCheck9 = new ArrayList<>();
        preorderPrint(r9, preCheck9);
        inorderPrint(r9, inCheck9);
        boolean pass9 = preCheck9.equals(Arrays.stream(pre9).boxed().toList()) &&
                        inCheck9.equals(Arrays.stream(in9).boxed().toList()) &&
                        isSameTree(r9, r9i) && isSameTree(r9, r9n);
        System.out.println("  Preorder: " + Arrays.toString(pre9));
        System.out.println("  Inorder:  " + Arrays.toString(in9));
        System.out.println("  Preorder check: " + (preCheck9.equals(Arrays.stream(pre9).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Inorder check:  " + (inCheck9.equals(Arrays.stream(in9).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r9, r9i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r9, r9n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Three nodes balanced
        System.out.println("Test 10: Three nodes balanced");
        int[] pre10 = {2, 1, 3};
        int[] in10 = {1, 2, 3};
        TreeNode r10 = sol.buildTree(pre10, in10);
        TreeNode r10i = sol.buildTreeIterative(pre10, in10);
        TreeNode r10n = sol.buildTreeNoMap(pre10, in10);
        List<Integer> preCheck10 = new ArrayList<>();
        List<Integer> inCheck10 = new ArrayList<>();
        preorderPrint(r10, preCheck10);
        inorderPrint(r10, inCheck10);
        boolean pass10 = preCheck10.equals(Arrays.stream(pre10).boxed().toList()) &&
                         inCheck10.equals(Arrays.stream(in10).boxed().toList()) &&
                         isSameTree(r10, r10i) && isSameTree(r10, r10n);
        System.out.println("  Preorder: " + Arrays.toString(pre10));
        System.out.println("  Inorder:  " + Arrays.toString(in10));
        System.out.println("  Preorder check: " + (preCheck10.equals(Arrays.stream(pre10).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Inorder check:  " + (inCheck10.equals(Arrays.stream(in10).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r10, r10i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r10, r10n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}