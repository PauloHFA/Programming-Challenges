import java.util.*;

/**
 * LeetCode 156 - Binary Tree Upside Down
 * 
 * Dado o root de uma árvore binária, vire a árvore de cabeça para baixo e retorne o novo root.
 * A árvore original é transformada de forma que:
 * - O filho esquerdo original se torna o novo root
 * - O root original se torna o filho direito
 * - O filho direito original se torna o filho esquerdo
 * Isso é feito nível por nível.
 * Assume-se que todo nó direito tem um irmão esquerdo (árvore completa à esquerda).
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
     * Método 1: Recursivo (Principal)
     * Processa recursivamente o filho esquerdo até chegar no novo root.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) return root;

        TreeNode newRoot = upsideDownBinaryTree(root.left);

        // Reconfigura ponteiros
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;

        return newRoot;
    }

    /**
     * Método 2: Iterativo
     * Usa três ponteiros para reconfigurar iterativamente.
     * Tempo: O(n), Espaço: O(1)
     */
    public TreeNode upsideDownBinaryTreeIterative(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null;
        TreeNode next = null;
        TreeNode tempRight = null;

        while (curr != null) {
            next = curr.left;

            // Reconfigura
            curr.left = tempRight;
            tempRight = curr.right;
            curr.right = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }

    /**
     * Método 3: Recursivo com retorno de (newRoot, newRightmost)
     * Retorna tanto o novo root quanto o nó mais à direita da nova árvore.
     * Tempo: O(n), Espaço: O(h)
     */
    public TreeNode upsideDownBinaryTreePair(TreeNode root) {
        return upsideDownHelper(root).newRoot;
    }

    private Result upsideDownHelper(TreeNode node) {
        if (node == null || node.left == null) {
            return new Result(node, node);
        }

        Result leftResult = upsideDownHelper(node.left);

        // node.left é o novo root da subárvore
        // leftResult.rightmost é o nó mais à direita da subárvore esquerda virada
        node.left.left = node.right;
        node.left.right = node;
        node.left = null;
        node.right = null;

        return new Result(leftResult.newRoot, node);
    }

    private static class Result {
        TreeNode newRoot;
        TreeNode rightmost;
        Result(TreeNode newRoot, TreeNode rightmost) {
            this.newRoot = newRoot;
            this.rightmost = rightmost;
        }
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

    // Helper para converter árvore em lista level-order para verificação
    private static List<Integer> treeToLevelOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }

        // Remove trailing nulls
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
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

    // Helper para lista para string
    private static String listToString(List<Integer> list) {
        return list.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Binary Tree Upside Down Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, 2, 3, 4, 5};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(4, 5, 2, null, null, 3, 1);
        TreeNode result1 = sol.upsideDownBinaryTree(root1);
        List<Integer> actual1 = treeToLevelOrder(result1);
        boolean pass1 = actual1.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  Actual:   " + listToString(actual1));
        System.out.println("  Result: " + (pass1 ? "✓" : "✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode root2 = null;
        TreeNode result2 = sol.upsideDownBinaryTree(root2);
        boolean pass2 = result2 == null;
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        List<Integer> expected3 = Arrays.asList(1);
        TreeNode result3 = sol.upsideDownBinaryTree(root3);
        List<Integer> actual3 = treeToLevelOrder(result3);
        boolean pass3 = actual3.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Two levels
        System.out.println("Test 4: Two levels");
        Integer[] arr4 = {1, 2, 3};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(2, 3, 1);
        TreeNode result4 = sol.upsideDownBinaryTree(root4);
        List<Integer> actual4 = treeToLevelOrder(result4);
        boolean pass4 = actual4.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + listToString(expected4));
        System.out.println("  Actual:   " + listToString(actual4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        Integer[] arr5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(4, null, 3, null, 2, null, 1);
        TreeNode result5 = sol.upsideDownBinaryTree(root5);
        List<Integer> actual5 = treeToLevelOrder(result5);
        boolean pass5 = actual5.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + listToString(expected5));
        System.out.println("  Actual:   " + listToString(actual5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Test iterative method
        System.out.println("Test 6: Iterative method");
        Integer[] arr6 = {1, 2, 3, 4, 5};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(4, 5, 2, null, null, 3, 1);
        TreeNode result6 = sol.upsideDownBinaryTreeIterative(root6);
        List<Integer> actual6 = treeToLevelOrder(result6);
        boolean pass6 = actual6.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Test pair method
        System.out.println("Test 7: Pair method");
        Integer[] arr7 = {1, 2, 3, 4, 5};
        TreeNode root7 = buildTree(arr7);
        List<Integer> expected7 = Arrays.asList(4, 5, 2, null, null, 3, 1);
        TreeNode result7 = sol.upsideDownBinaryTreePair(root7);
        List<Integer> actual7 = treeToLevelOrder(result7);
        boolean pass7 = actual7.equals(expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: All methods consistency
        System.out.println("Test 8: All methods consistency");
        Integer[] arr8 = {1, 2, 3, 4, 5};
        TreeNode root8a = buildTree(arr8);
        TreeNode root8b = buildTree(arr8);
        TreeNode root8c = buildTree(arr8);

        TreeNode r8a = sol.upsideDownBinaryTree(root8a);
        TreeNode r8b = sol.upsideDownBinaryTreeIterative(root8b);
        TreeNode r8c = sol.upsideDownBinaryTreePair(root8c);

        List<Integer> a8a = treeToLevelOrder(r8a);
        List<Integer> a8b = treeToLevelOrder(r8b);
        List<Integer> a8c = treeToLevelOrder(r8c);

        boolean pass8 = a8a.equals(a8b) && a8b.equals(a8c);
        System.out.println("  Recursive: " + listToString(a8a));
        System.out.println("  Iterative: " + listToString(a8b));
        System.out.println("  Pair:      " + listToString(a8c));
        System.out.println("  All match: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}