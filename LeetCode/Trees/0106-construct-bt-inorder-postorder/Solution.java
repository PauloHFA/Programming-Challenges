import java.util.*;

/**
 * LeetCode 106 - Construct Binary Tree from Inorder and Postorder Traversal
 * 
 * Dado dois arrays de inteiros inorder e postorder onde inorder é a travessia
 * em ordem de uma árvore binária e postorder é a travessia pós-ordem da mesma
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
     * - Inorder: left, root, right
     * - Postorder: left, right, root
     * O último elemento do postorder é a raiz. Encontra raiz no inorder,
     * elementos à esquerda são subárvore esquerda, à direita são subárvore direita.
     * Usa HashMap para busca O(1) do índice no inorder.
     * Tempo: O(n), Espaço: O(n) HashMap + O(h) stack
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return build(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, inorderIndex);
    }

    private TreeNode build(int[] inorder, int inStart, int inEnd,
                           int[] postorder, int postStart, int postEnd,
                           Map<Integer, Integer> inorderIndex) {
        if (inStart > inEnd || postStart > postEnd) return null;

        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        int inRootIndex = inorderIndex.get(rootVal);
        int leftSize = inRootIndex - inStart;

        root.left = build(inorder, inStart, inRootIndex - 1,
                          postorder, postStart, postStart + leftSize - 1, inorderIndex);
        root.right = build(inorder, inRootIndex + 1, inEnd,
                           postorder, postStart + leftSize, postEnd - 1, inorderIndex);

        return root;
    }

    /**
     * Método 2: Iterativo com Stack
     * Similar ao método iterativo do preorder/inorder mas processa postorder de trás para frente.
     * Tempo: O(n), Espaço: O(n)
     */
    public TreeNode buildTreeIterative(int[] inorder, int[] postorder) {
        if (postorder.length == 0) return null;

        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int inorderIndex = inorder.length - 1;

        for (int i = postorder.length - 2; i >= 0; i--) {
            int postVal = postorder[i];
            TreeNode node = stack.peek();

            if (node.val != inorder[inorderIndex]) {
                // Próximo nó é filho direito (processando de trás para frente)
                node.right = new TreeNode(postVal);
                stack.push(node.right);
            } else {
                // Encontrou o nó no inorder, volta para achar pai do esquerdo
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex--;
                }
                // Próximo nó é filho esquerdo do último pop
                node.left = new TreeNode(postVal);
                stack.push(node.left);
            }
        }

        return root;
    }

    /**
     * Método 3: Recursivo sem HashMap (busca linear)
     * Mais simples mas O(n²) no pior caso.
     * Tempo: O(n²), Espaço: O(h)
     */
    public TreeNode buildTreeNoMap(int[] inorder, int[] postorder) {
        return buildNoMap(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildNoMap(int[] inorder, int inStart, int inEnd,
                                int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) return null;

        int rootVal = postorder[postEnd];
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

        root.left = buildNoMap(inorder, inStart, inRootIndex - 1,
                               postorder, postStart, postStart + leftSize - 1);
        root.right = buildNoMap(inorder, inRootIndex + 1, inEnd,
                                postorder, postStart + leftSize, postEnd - 1);

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

    // Helper para inorder traversal (para debug)
    private static void inorderPrint(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorderPrint(root.left, result);
        result.add(root.val);
        inorderPrint(root.right, result);
    }

    // Helper para postorder traversal (para debug)
    private static void postorderPrint(TreeNode root, List<Integer> result) {
        if (root == null) return;
        postorderPrint(root.left, result);
        postorderPrint(root.right, result);
        result.add(root.val);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Construct Binary Tree from Inorder and Postorder Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        int[] in1 = {9, 3, 15, 20, 7};
        int[] post1 = {9, 15, 7, 20, 3};
        TreeNode expected1 = buildExpected1();
        TreeNode r1 = sol.buildTree(in1, post1);
        TreeNode r1i = sol.buildTreeIterative(in1, post1);
        TreeNode r1n = sol.buildTreeNoMap(in1, post1);
        boolean pass1 = isSameTree(r1, expected1) && isSameTree(r1i, expected1) && isSameTree(r1n, expected1);
        System.out.println("  Inorder:   " + Arrays.toString(in1));
        System.out.println("  Postorder: " + Arrays.toString(post1));
        System.out.println("  Recursive: " + (isSameTree(r1, expected1) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r1i, expected1) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r1n, expected1) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Single node
        System.out.println("Test 2: Single node");
        int[] in2 = {-1};
        int[] post2 = {-1};
        TreeNode expected2 = buildExpected2();
        TreeNode r2 = sol.buildTree(in2, post2);
        TreeNode r2i = sol.buildTreeIterative(in2, post2);
        TreeNode r2n = sol.buildTreeNoMap(in2, post2);
        boolean pass2 = isSameTree(r2, expected2) && isSameTree(r2i, expected2) && isSameTree(r2n, expected2);
        System.out.println("  Inorder:   " + Arrays.toString(in2));
        System.out.println("  Postorder: " + Arrays.toString(post2));
        System.out.println("  Recursive: " + (isSameTree(r2, expected2) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r2i, expected2) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r2n, expected2) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Complete tree
        System.out.println("Test 3: Complete tree");
        int[] in3 = {4, 2, 5, 1, 3};
        int[] post3 = {4, 5, 2, 3, 1};
        TreeNode expected3 = buildExpected3();
        TreeNode r3 = sol.buildTree(in3, post3);
        TreeNode r3i = sol.buildTreeIterative(in3, post3);
        TreeNode r3n = sol.buildTreeNoMap(in3, post3);
        boolean pass3 = isSameTree(r3, expected3) && isSameTree(r3i, expected3) && isSameTree(r3n, expected3);
        System.out.println("  Inorder:   " + Arrays.toString(in3));
        System.out.println("  Postorder: " + Arrays.toString(post3));
        System.out.println("  Recursive: " + (isSameTree(r3, expected3) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r3i, expected3) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r3n, expected3) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Right skewed
        System.out.println("Test 4: Right skewed");
        int[] in4 = {1, 2, 3};
        int[] post4 = {3, 2, 1};
        TreeNode expected4 = buildExpected4();
        TreeNode r4 = sol.buildTree(in4, post4);
        TreeNode r4i = sol.buildTreeIterative(in4, post4);
        TreeNode r4n = sol.buildTreeNoMap(in4, post4);
        boolean pass4 = isSameTree(r4, expected4) && isSameTree(r4i, expected4) && isSameTree(r4n, expected4);
        System.out.println("  Inorder:   " + Arrays.toString(in4));
        System.out.println("  Postorder: " + Arrays.toString(post4));
        System.out.println("  Recursive: " + (isSameTree(r4, expected4) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r4i, expected4) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r4n, expected4) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        int[] in5 = {3, 2, 1};
        int[] post5 = {3, 2, 1};
        TreeNode expected5 = buildExpected5();
        TreeNode r5 = sol.buildTree(in5, post5);
        TreeNode r5i = sol.buildTreeIterative(in5, post5);
        TreeNode r5n = sol.buildTreeNoMap(in5, post5);
        boolean pass5 = isSameTree(r5, expected5) && isSameTree(r5i, expected5) && isSameTree(r5n, expected5);
        System.out.println("  Inorder:   " + Arrays.toString(in5));
        System.out.println("  Postorder: " + Arrays.toString(post5));
        System.out.println("  Recursive: " + (isSameTree(r5, expected5) ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (isSameTree(r5i, expected5) ? "?" : "? FAIL"));
        System.out.println("  NoMap:     " + (isSameTree(r5n, expected5) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Larger tree
        System.out.println("Test 6: Larger tree");
        int[] in6 = {8, 4, 9, 2, 5, 1, 6, 3, 7};
        int[] post6 = {8, 9, 4, 5, 2, 6, 7, 3, 1};
        TreeNode r6 = sol.buildTree(in6, post6);
        TreeNode r6i = sol.buildTreeIterative(in6, post6);
        TreeNode r6n = sol.buildTreeNoMap(in6, post6);
        List<Integer> inCheck6 = new ArrayList<>();
        List<Integer> postCheck6 = new ArrayList<>();
        inorderPrint(r6, inCheck6);
        postorderPrint(r6, postCheck6);
        boolean pass6 = inCheck6.equals(Arrays.stream(in6).boxed().toList()) &&
                        postCheck6.equals(Arrays.stream(post6).boxed().toList()) &&
                        isSameTree(r6, r6i) && isSameTree(r6, r6n);
        System.out.println("  Inorder:   " + Arrays.toString(in6));
        System.out.println("  Postorder: " + Arrays.toString(post6));
        System.out.println("  Inorder check:   " + (inCheck6.equals(Arrays.stream(in6).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Postorder check: " + (postCheck6.equals(Arrays.stream(post6).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r6, r6i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r6, r6n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Negative values
        System.out.println("Test 7: Negative values");
        int[] in7 = {-4, -2, -5, -1, -3};
        int[] post7 = {-4, -5, -2, -3, -1};
        TreeNode r7 = sol.buildTree(in7, post7);
        TreeNode r7i = sol.buildTreeIterative(in7, post7);
        TreeNode r7n = sol.buildTreeNoMap(in7, post7);
        List<Integer> inCheck7 = new ArrayList<>();
        List<Integer> postCheck7 = new ArrayList<>();
        inorderPrint(r7, inCheck7);
        postorderPrint(r7, postCheck7);
        boolean pass7 = inCheck7.equals(Arrays.stream(in7).boxed().toList()) &&
                        postCheck7.equals(Arrays.stream(post7).boxed().toList()) &&
                        isSameTree(r7, r7i) && isSameTree(r7, r7n);
        System.out.println("  Inorder:   " + Arrays.toString(in7));
        System.out.println("  Postorder: " + Arrays.toString(post7));
        System.out.println("  Inorder check:   " + (inCheck7.equals(Arrays.stream(in7).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Postorder check: " + (postCheck7.equals(Arrays.stream(post7).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r7, r7i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r7, r7n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Two nodes left
        System.out.println("Test 8: Two nodes left");
        int[] in8 = {2, 1};
        int[] post8 = {2, 1};
        TreeNode r8 = sol.buildTree(in8, post8);
        TreeNode r8i = sol.buildTreeIterative(in8, post8);
        TreeNode r8n = sol.buildTreeNoMap(in8, post8);
        List<Integer> inCheck8 = new ArrayList<>();
        List<Integer> postCheck8 = new ArrayList<>();
        inorderPrint(r8, inCheck8);
        postorderPrint(r8, postCheck8);
        boolean pass8 = inCheck8.equals(Arrays.stream(in8).boxed().toList()) &&
                        postCheck8.equals(Arrays.stream(post8).boxed().toList()) &&
                        isSameTree(r8, r8i) && isSameTree(r8, r8n);
        System.out.println("  Inorder:   " + Arrays.toString(in8));
        System.out.println("  Postorder: " + Arrays.toString(post8));
        System.out.println("  Inorder check:   " + (inCheck8.equals(Arrays.stream(in8).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Postorder check: " + (postCheck8.equals(Arrays.stream(post8).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r8, r8i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r8, r8n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Two nodes right
        System.out.println("Test 9: Two nodes right");
        int[] in9 = {1, 2};
        int[] post9 = {2, 1};
        TreeNode r9 = sol.buildTree(in9, post9);
        TreeNode r9i = sol.buildTreeIterative(in9, post9);
        TreeNode r9n = sol.buildTreeNoMap(in9, post9);
        List<Integer> inCheck9 = new ArrayList<>();
        List<Integer> postCheck9 = new ArrayList<>();
        inorderPrint(r9, inCheck9);
        postorderPrint(r9, postCheck9);
        boolean pass9 = inCheck9.equals(Arrays.stream(in9).boxed().toList()) &&
                        postCheck9.equals(Arrays.stream(post9).boxed().toList()) &&
                        isSameTree(r9, r9i) && isSameTree(r9, r9n);
        System.out.println("  Inorder:   " + Arrays.toString(in9));
        System.out.println("  Postorder: " + Arrays.toString(post9));
        System.out.println("  Inorder check:   " + (inCheck9.equals(Arrays.stream(in9).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Postorder check: " + (postCheck9.equals(Arrays.stream(post9).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r9, r9i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r9, r9n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Three nodes balanced
        System.out.println("Test 10: Three nodes balanced");
        int[] in10 = {1, 2, 3};
        int[] post10 = {1, 3, 2};
        TreeNode r10 = sol.buildTree(in10, post10);
        TreeNode r10i = sol.buildTreeIterative(in10, post10);
        TreeNode r10n = sol.buildTreeNoMap(in10, post10);
        List<Integer> inCheck10 = new ArrayList<>();
        List<Integer> postCheck10 = new ArrayList<>();
        inorderPrint(r10, inCheck10);
        postorderPrint(r10, postCheck10);
        boolean pass10 = inCheck10.equals(Arrays.stream(in10).boxed().toList()) &&
                         postCheck10.equals(Arrays.stream(post10).boxed().toList()) &&
                         isSameTree(r10, r10i) && isSameTree(r10, r10n);
        System.out.println("  Inorder:   " + Arrays.toString(in10));
        System.out.println("  Postorder: " + Arrays.toString(post10));
        System.out.println("  Inorder check:   " + (inCheck10.equals(Arrays.stream(in10).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Postorder check: " + (postCheck10.equals(Arrays.stream(post10).boxed().toList()) ? "?" : "? FAIL"));
        System.out.println("  Recursive=Iterative: " + (isSameTree(r10, r10i) ? "?" : "? FAIL"));
        System.out.println("  Recursive=NoMap:     " + (isSameTree(r10, r10n) ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}