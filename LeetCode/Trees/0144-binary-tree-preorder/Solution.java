import java.util.*;

/**
 * LeetCode 144 - Binary Tree Preorder Traversal
 * 
 * Dado o root de uma árvore binária, retorne a travessia preorder dos valores dos seus nós.
 * Preorder: Root -> Left -> Right
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
     * Tempo: O(n), Espaço: O(h) stack
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorder(node.left, result);
        preorder(node.right, result);
    }

    /**
     * Método 2: Iterativo com Stack
     * Push right first, then left (so left is processed first).
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }

        return result;
    }

    /**
     * Método 3: Morris Traversal (O(1) espaço)
     * Modifica temporariamente a árvore para evitar stack.
     * Tempo: O(n), Espaço: O(1)
     */
    public List<Integer> preorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                result.add(curr.val);
                curr = curr.right;
            } else {
                // Encontra predecessor
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Primeira visita - cria thread
                    result.add(curr.val); // Visita root antes de ir para left
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    // Segunda visita - remove thread
                    predecessor.right = null;
                    curr = curr.right;
                }
            }
        }

        return result;
    }

    /**
     * Método 4: Iterativo com Stack (simulando chamada de função)
     * Mais explícito sobre estado.
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> preorderTraversalExplicit(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 0)); // 0 = não visitado, 1 = left feito, 2 = right feito

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> top = stack.peek();
            TreeNode node = top.getKey();
            int state = top.getValue();

            if (state == 0) {
                // Primeira vez visitando - processa root
                result.add(node.val);
                stack.pop();
                stack.push(new Pair<>(node, 1));
                if (node.left != null) stack.push(new Pair<>(node.left, 0));
            } else if (state == 1) {
                // Left já processado
                stack.pop();
                stack.push(new Pair<>(node, 2));
                if (node.right != null) stack.push(new Pair<>(node.right, 0));
            } else {
                // Ambos filhos processados
                stack.pop();
            }
        }

        return result;
    }

    // Simple Pair class
    private static class Pair<K, V> {
        private final K key;
        private final V value;
        Pair(K key, V value) { this.key = key; this.value = value; }
        K getKey() { return key; }
        V getValue() { return value; }
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

    // Helper para lista para string
    private static String listToString(List<Integer> list) {
        return list.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Binary Tree Preorder Traversal Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, null, 2, 3};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(1, 2, 3);
        List<Integer> r1 = sol.preorderTraversal(root1);
        List<Integer> r1i = sol.preorderTraversalIterative(root1);
        List<Integer> r1m = sol.preorderTraversalMorris(root1);
        List<Integer> r1e = sol.preorderTraversalExplicit(root1);
        boolean pass1 = r1.equals(expected1) && r1i.equals(expected1) && r1m.equals(expected1) && r1e.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  Recursive: " + listToString(r1) + (r1.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listToString(r1i) + (r1i.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Morris:    " + listToString(r1m) + (r1m.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Explicit:  " + listToString(r1e) + (r1e.equals(expected1) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode root2 = null;
        List<Integer> expected2 = new ArrayList<>();
        List<Integer> r2 = sol.preorderTraversal(root2);
        List<Integer> r2i = sol.preorderTraversalIterative(root2);
        List<Integer> r2m = sol.preorderTraversalMorris(root2);
        List<Integer> r2e = sol.preorderTraversalExplicit(root2);
        boolean pass2 = r2.equals(expected2) && r2i.equals(expected2) && r2m.equals(expected2) && r2e.equals(expected2);
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        List<Integer> expected3 = Arrays.asList(1);
        List<Integer> r3 = sol.preorderTraversal(root3);
        List<Integer> r3i = sol.preorderTraversalIterative(root3);
        List<Integer> r3m = sol.preorderTraversalMorris(root3);
        List<Integer> r3e = sol.preorderTraversalExplicit(root3);
        boolean pass3 = r3.equals(expected3) && r3i.equals(expected3) && r3m.equals(expected3) && r3e.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(1, 2, 3, 4);
        List<Integer> r4 = sol.preorderTraversal(root4);
        List<Integer> r4i = sol.preorderTraversalIterative(root4);
        List<Integer> r4m = sol.preorderTraversalMorris(root4);
        List<Integer> r4e = sol.preorderTraversalExplicit(root4);
        boolean pass4 = r4.equals(expected4) && r4i.equals(expected4) && r4m.equals(expected4) && r4e.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(1, 2, 3, 4);
        List<Integer> r5 = sol.preorderTraversal(root5);
        List<Integer> r5i = sol.preorderTraversalIterative(root5);
        List<Integer> r5m = sol.preorderTraversalMorris(root5);
        List<Integer> r5e = sol.preorderTraversalExplicit(root5);
        boolean pass5 = r5.equals(expected5) && r5i.equals(expected5) && r5m.equals(expected5) && r5e.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete tree
        System.out.println("Test 6: Complete tree");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(1, 2, 4, 5, 3, 6, 7);
        List<Integer> r6 = sol.preorderTraversal(root6);
        List<Integer> r6i = sol.preorderTraversalIterative(root6);
        List<Integer> r6m = sol.preorderTraversalMorris(root6);
        List<Integer> r6e = sol.preorderTraversalExplicit(root6);
        boolean pass6 = r6.equals(expected6) && r6i.equals(expected6) && r6m.equals(expected6) && r6e.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + listToString(expected6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: All methods consistency
        System.out.println("Test 7: All methods consistency");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7a = buildTree(arr7);
        TreeNode root7b = buildTree(arr7);
        TreeNode root7c = buildTree(arr7);
        TreeNode root7d = buildTree(arr7);

        List<Integer> r7a = sol.preorderTraversal(root7a);
        List<Integer> r7b = sol.preorderTraversalIterative(root7b);
        List<Integer> r7c = sol.preorderTraversalMorris(root7c);
        List<Integer> r7d = sol.preorderTraversalExplicit(root7d);

        boolean pass7 = r7a.equals(r7b) && r7b.equals(r7c) && r7c.equals(r7d);
        System.out.println("  Recursive: " + listToString(r7a));
        System.out.println("  Iterative: " + listToString(r7b));
        System.out.println("  Morris:    " + listToString(r7c));
        System.out.println("  Explicit:  " + listToString(r7d));
        System.out.println("  All match: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}