import java.util.*;

/**
 * LeetCode 110 - Balanced Binary Tree
 * 
 * Dado o root de uma árvore binária, determine se ela é balanceada em altura.
 * Uma árvore binária é balanceada em altura se para cada nó, a diferença de
 * altura entre as subárvores esquerda e direita não é maior que 1.
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
     * Método 1: DFS Recursivo com Early Exit (Principal)
     * Retorna -1 se subárvore não for balanceada, caso contrário retorna altura.
     * Para assim que encontra desbalanceamento.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Método 2: DFS Recursivo com Pair (altura, balanceado)
     * Retorna par contendo altura e se é balanceado.
     * Tempo: O(n), Espaço: O(h)
     */
    public boolean isBalancedPair(TreeNode root) {
        return checkPair(root).balanced;
    }

    private static class Pair {
        int height;
        boolean balanced;
        Pair(int height, boolean balanced) {
            this.height = height;
            this.balanced = balanced;
        }
    }

    private Pair checkPair(TreeNode node) {
        if (node == null) return new Pair(0, true);

        Pair left = checkPair(node.left);
        if (!left.balanced) return new Pair(0, false);

        Pair right = checkPair(node.right);
        if (!right.balanced) return new Pair(0, false);

        boolean balanced = Math.abs(left.height - right.height) <= 1;
        int height = Math.max(left.height, right.height) + 1;

        return new Pair(height, balanced);
    }

    /**
     * Método 3: Iterativo com Stack (Post-order)
     * Usa stack para simular pós-ordem, calcula alturas bottom-up.
     * Tempo: O(n), Espaço: O(h)
     */
    public boolean isBalancedIterative(TreeNode root) {
        if (root == null) return true;

        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> heights = new HashMap<>();
        TreeNode lastVisited = null;
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && lastVisited != peek.right) {
                    current = peek.right;
                } else {
                    stack.pop();
                    int leftHeight = heights.getOrDefault(peek.left, 0);
                    int rightHeight = heights.getOrDefault(peek.right, 0);

                    if (Math.abs(leftHeight - rightHeight) > 1) {
                        return false;
                    }

                    heights.put(peek, Math.max(leftHeight, rightHeight) + 1);
                    lastVisited = peek;
                }
            }
        }

        return true;
    }

    /**
     * Método 4: BFS Level Order - Verifica cada nível
     * Não é eficiente para este problema mas demonstra abordagem alternativa.
     * Tempo: O(n²) pior caso, Espaço: O(w)
     */
    public boolean isBalancedBFS(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);

            if (Math.abs(leftHeight - rightHeight) > 1) {
                return false;
            }

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        return true;
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Helper para construir árvore a partir de array (level order com null)
    private static TreeNode buildTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < arr.length) {
            TreeNode node = queue.poll();

            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                queue.offer(node.left);
            }
            i++;

            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }

    // Helper para array para string
    private static String arrayToString(Integer[] arr) {
        if (arr == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i] == null ? "null" : arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Balanced Binary Tree Tests ===\n");

        // Test 1: Example 1 - Balanced
        System.out.println("Test 1: Example 1 (Balanced)");
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(arr1);
        boolean r1 = sol.isBalanced(root1);
        boolean r1p = sol.isBalancedPair(root1);
        boolean r1i = sol.isBalancedIterative(root1);
        boolean r1b = sol.isBalancedBFS(root1);
        boolean pass1 = r1 && r1p && r1i && r1b;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r1 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r1p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r1i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r1b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2 - Not Balanced
        System.out.println("Test 2: Example 2 (Not Balanced)");
        Integer[] arr2 = {1, 2, 2, 3, 3, null, null, 4, 4};
        TreeNode root2 = buildTree(arr2);
        boolean r2 = sol.isBalanced(root2);
        boolean r2p = sol.isBalancedPair(root2);
        boolean r2i = sol.isBalancedIterative(root2);
        boolean r2b = sol.isBalancedBFS(root2);
        boolean pass2 = !r2 && !r2p && !r2i && !r2b;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: false");
        System.out.println("  EarlyExit: " + (!r2 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (!r2p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r2i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r2b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty tree
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        boolean r3 = sol.isBalanced(root3);
        boolean r3p = sol.isBalancedPair(root3);
        boolean r3i = sol.isBalancedIterative(root3);
        boolean r3b = sol.isBalancedBFS(root3);
        boolean pass3 = r3 && r3p && r3i && r3b;
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r3 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r3p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r3i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r3b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node
        System.out.println("Test 4: Single node");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        boolean r4 = sol.isBalanced(root4);
        boolean r4p = sol.isBalancedPair(root4);
        boolean r4i = sol.isBalancedIterative(root4);
        boolean r4b = sol.isBalancedBFS(root4);
        boolean pass4 = r4 && r4p && r4i && r4b;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r4 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r4p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r4i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r4b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed (not balanced)
        System.out.println("Test 5: Left skewed (not balanced)");
        Integer[] arr5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        boolean r5 = sol.isBalanced(root5);
        boolean r5p = sol.isBalancedPair(root5);
        boolean r5i = sol.isBalancedIterative(root5);
        boolean r5b = sol.isBalancedBFS(root5);
        boolean pass5 = !r5 && !r5p && !r5i && !r5b;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: false");
        System.out.println("  EarlyExit: " + (!r5 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (!r5p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r5i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r5b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Right skewed (not balanced)
        System.out.println("Test 6: Right skewed (not balanced)");
        Integer[] arr6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        boolean r6 = sol.isBalanced(root6);
        boolean r6p = sol.isBalancedPair(root6);
        boolean r6i = sol.isBalancedIterative(root6);
        boolean r6b = sol.isBalancedBFS(root6);
        boolean pass6 = !r6 && !r6p && !r6i && !r6b;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: false");
        System.out.println("  EarlyExit: " + (!r6 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (!r6p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r6i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r6b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Complete tree (balanced)
        System.out.println("Test 7: Complete tree (balanced)");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7 = buildTree(arr7);
        boolean r7 = sol.isBalanced(root7);
        boolean r7p = sol.isBalancedPair(root7);
        boolean r7i = sol.isBalancedIterative(root7);
        boolean r7b = sol.isBalancedBFS(root7);
        boolean pass7 = r7 && r7p && r7i && r7b;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r7 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r7p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r7i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r7b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Perfect tree height 3 (balanced)
        System.out.println("Test 8: Perfect tree height 3 (balanced)");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root8 = buildTree(arr8);
        boolean r8 = sol.isBalanced(root8);
        boolean r8p = sol.isBalancedPair(root8);
        boolean r8i = sol.isBalancedIterative(root8);
        boolean r8b = sol.isBalancedBFS(root8);
        boolean pass8 = r8 && r8p && r8i && r8b;
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r8 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r8p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r8i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r8b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Mixed tree (NOT balanced - left subtree height 3, right subtree height 2 at node 2)
                System.out.println("Test 9: Mixed tree (not balanced)");
        Integer[] arr9 = {1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8};
        TreeNode root9 = buildTree(arr9);
        boolean r9 = sol.isBalanced(root9);
        boolean r9p = sol.isBalancedPair(root9);
        boolean r9i = sol.isBalancedIterative(root9);
        boolean r9b = sol.isBalancedBFS(root9);
                boolean pass9 = !r9 && !r9p && !r9i && !r9b;
        System.out.println("  Tree: " + arrayToString(arr9));
                System.out.println("  Expected: false");
                System.out.println("  EarlyExit: " + (!r9 ? "?" : "? FAIL"));
                System.out.println("  Pair:      " + (!r9p ? "?" : "? FAIL"));
                System.out.println("  Iterative: " + (!r9i ? "?" : "? FAIL"));
                System.out.println("  BFS:       " + (!r9b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes left (balanced)
        System.out.println("Test 10: Two nodes left (balanced)");
        Integer[] arr10 = {1, 2};
        TreeNode root10 = buildTree(arr10);
        boolean r10 = sol.isBalanced(root10);
        boolean r10p = sol.isBalancedPair(root10);
        boolean r10i = sol.isBalancedIterative(root10);
        boolean r10b = sol.isBalancedBFS(root10);
        boolean pass10 = r10 && r10p && r10i && r10b;
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r10 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r10p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r10i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r10b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Two nodes right (balanced)
        System.out.println("Test 11: Two nodes right (balanced)");
        Integer[] arr11 = {1, null, 2};
        TreeNode root11 = buildTree(arr11);
        boolean r11 = sol.isBalanced(root11);
        boolean r11p = sol.isBalancedPair(root11);
        boolean r11i = sol.isBalancedIterative(root11);
        boolean r11b = sol.isBalancedBFS(root11);
        boolean pass11 = r11 && r11p && r11i && r11b;
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r11 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r11p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r11i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r11b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        // Test 12: Negative values (balanced)
        System.out.println("Test 12: Negative values (balanced)");
        Integer[] arr12 = {-1, -2, -3, -4, -5};
        TreeNode root12 = buildTree(arr12);
        boolean r12 = sol.isBalanced(root12);
        boolean r12p = sol.isBalancedPair(root12);
        boolean r12i = sol.isBalancedIterative(root12);
        boolean r12b = sol.isBalancedBFS(root12);
        boolean pass12 = r12 && r12p && r12i && r12b;
        System.out.println("  Tree: " + arrayToString(arr12));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r12 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r12p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r12i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r12b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass12 ? "?" : "? FAIL"));
        allPassed &= pass12;
        System.out.println();

        // Test 13: Unbalanced at root only
        System.out.println("Test 13: Unbalanced at root only");
        Integer[] arr13 = {1, 2, 3, 4, 5, null, null, 6, 7};
        TreeNode root13 = buildTree(arr13);
        boolean r13 = sol.isBalanced(root13);
        boolean r13p = sol.isBalancedPair(root13);
        boolean r13i = sol.isBalancedIterative(root13);
        boolean r13b = sol.isBalancedBFS(root13);
        boolean pass13 = !r13 && !r13p && !r13i && !r13b;
        System.out.println("  Tree: " + arrayToString(arr13));
        System.out.println("  Expected: false");
        System.out.println("  EarlyExit: " + (!r13 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (!r13p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r13i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r13b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass13 ? "?" : "? FAIL"));
        allPassed &= pass13;
        System.out.println();

        // Test 14: Balanced but deep
        System.out.println("Test 14: Balanced but deep");
        Integer[] arr14 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        TreeNode root14 = buildTree(arr14);
        boolean r14 = sol.isBalanced(root14);
        boolean r14p = sol.isBalancedPair(root14);
        boolean r14i = sol.isBalancedIterative(root14);
        boolean r14b = sol.isBalancedBFS(root14);
        boolean pass14 = r14 && r14p && r14i && r14b;
        System.out.println("  Tree: " + arrayToString(arr14));
        System.out.println("  Expected: true");
        System.out.println("  EarlyExit: " + (r14 ? "?" : "? FAIL"));
        System.out.println("  Pair:      " + (r14p ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r14i ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r14b ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass14 ? "?" : "? FAIL"));
        allPassed &= pass14;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}