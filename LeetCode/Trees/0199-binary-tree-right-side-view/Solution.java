import java.util.*;

/**
 * LeetCode 199 - Binary Tree Right Side View
 * 
 * Dado o root de uma árvore binária, imagine-se parado no lado direito dela.
 * Retorne os valores dos nós que você pode ver ordenados de cima para baixo.
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
     * Método 1: BFS Level Order (Principal)
     * Processa nível a nível, pega o último elemento de cada nível.
     * Tempo: O(n), Espaço: O(w)
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                // Se é o último nó do nível, adiciona ao resultado
                if (i == size - 1) {
                    result.add(node.val);
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return result;
    }

    /**
     * Método 2: DFS Recursivo (Right First)
     * Visita right child primeiro, adiciona nó quando atinge nova profundidade.
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int depth, List<Integer> result) {
        if (node == null) return;

        // Se é a primeira vez visitando esta profundidade (right-first garante rightmost)
        if (depth == result.size()) {
            result.add(node.val);
        }

        // Visita right primeiro
        dfs(node.right, depth + 1, result);
        dfs(node.left, depth + 1, result);
    }

    /**
     * Método 3: DFS Iterativo com Stack
     * Similar ao recursivo mas com stack explícito.
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> rightSideViewIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 0));

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();

            if (depth == result.size()) {
                result.add(node.val);
            }

            // Push left first so right is processed first (LIFO)
            if (node.left != null) stack.push(new Pair<>(node.left, depth + 1));
            if (node.right != null) stack.push(new Pair<>(node.right, depth + 1));
        }

        return result;
    }

    /**
     * Método 4: BFS com Queue (Right First)
     * Adiciona right child primeiro na queue, pega primeiro de cada nível.
     * Tempo: O(n), Espaço: O(w)
     */
    public List<Integer> rightSideViewBFSRightFirst(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                // Primeiro nó do nível (right-first)
                if (i == 0) {
                    result.add(node.val);
                }

                // Right first
                if (node.right != null) queue.offer(node.right);
                if (node.left != null) queue.offer(node.left);
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

        System.out.println("=== Binary Tree Right Side View Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, 2, 3, null, 5, null, 4};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(1, 3, 4);
        List<Integer> r1 = sol.rightSideView(root1);
        List<Integer> r1d = sol.rightSideViewDFS(root1);
        List<Integer> r1i = sol.rightSideViewIterative(root1);
        List<Integer> r1b = sol.rightSideViewBFSRightFirst(root1);
        boolean pass1 = r1.equals(expected1) && r1d.equals(expected1) && r1i.equals(expected1) && r1b.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  BFS:       " + listToString(r1) + (r1.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  DFS:       " + listToString(r1d) + (r1d.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listToString(r1i) + (r1i.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS-RF:    " + listToString(r1b) + (r1b.equals(expected1) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {1, null, 3};
        TreeNode root2 = buildTree(arr2);
        List<Integer> expected2 = Arrays.asList(1, 3);
        List<Integer> r2 = sol.rightSideView(root2);
        List<Integer> r2d = sol.rightSideViewDFS(root2);
        List<Integer> r2i = sol.rightSideViewIterative(root2);
        List<Integer> r2b = sol.rightSideViewBFSRightFirst(root2);
        boolean pass2 = r2.equals(expected2) && r2d.equals(expected2) && r2i.equals(expected2) && r2b.equals(expected2);
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty tree
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        List<Integer> expected3 = new ArrayList<>();
        List<Integer> r3 = sol.rightSideView(root3);
        List<Integer> r3d = sol.rightSideViewDFS(root3);
        List<Integer> r3i = sol.rightSideViewIterative(root3);
        List<Integer> r3b = sol.rightSideViewBFSRightFirst(root3);
        boolean pass3 = r3.equals(expected3) && r3d.equals(expected3) && r3i.equals(expected3) && r3b.equals(expected3);
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node
        System.out.println("Test 4: Single node");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(1);
        List<Integer> r4 = sol.rightSideView(root4);
        List<Integer> r4d = sol.rightSideViewDFS(root4);
        List<Integer> r4i = sol.rightSideViewIterative(root4);
        List<Integer> r4b = sol.rightSideViewBFSRightFirst(root4);
        boolean pass4 = r4.equals(expected4) && r4d.equals(expected4) && r4i.equals(expected4) && r4b.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        Integer[] arr5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(1, 2, 3, 4);
        List<Integer> r5 = sol.rightSideView(root5);
        List<Integer> r5d = sol.rightSideViewDFS(root5);
        List<Integer> r5i = sol.rightSideViewIterative(root5);
        List<Integer> r5b = sol.rightSideViewBFSRightFirst(root5);
        boolean pass5 = r5.equals(expected5) && r5d.equals(expected5) && r5i.equals(expected5) && r5b.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Right skewed
        System.out.println("Test 6: Right skewed");
        Integer[] arr6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(1, 2, 3, 4);
        List<Integer> r6 = sol.rightSideView(root6);
        List<Integer> r6d = sol.rightSideViewDFS(root6);
        List<Integer> r6i = sol.rightSideViewIterative(root6);
        List<Integer> r6b = sol.rightSideViewBFSRightFirst(root6);
        boolean pass6 = r6.equals(expected6) && r6d.equals(expected6) && r6i.equals(expected6) && r6b.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Complete tree
        System.out.println("Test 7: Complete tree");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7 = buildTree(arr7);
        List<Integer> expected7 = Arrays.asList(1, 3, 7);
        List<Integer> r7 = sol.rightSideView(root7);
        List<Integer> r7d = sol.rightSideViewDFS(root7);
        List<Integer> r7i = sol.rightSideViewIterative(root7);
        List<Integer> r7b = sol.rightSideViewBFSRightFirst(root7);
        boolean pass7 = r7.equals(expected7) && r7d.equals(expected7) && r7i.equals(expected7) && r7b.equals(expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + listToString(expected7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: All methods consistency
        System.out.println("Test 8: All methods consistency");
        Integer[] arr8 = {1, 2, 3, null, 5, null, 4};
        TreeNode root8a = buildTree(arr8);
        TreeNode root8b = buildTree(arr8);
        TreeNode root8c = buildTree(arr8);
        TreeNode root8d = buildTree(arr8);

        List<Integer> r8a = sol.rightSideView(root8a);
        List<Integer> r8b = sol.rightSideViewDFS(root8b);
        List<Integer> r8c = sol.rightSideViewIterative(root8c);
        List<Integer> r8d = sol.rightSideViewBFSRightFirst(root8d);

        boolean pass8 = r8a.equals(r8b) && r8b.equals(r8c) && r8c.equals(r8d);
        System.out.println("  BFS:       " + listToString(r8a));
        System.out.println("  DFS:       " + listToString(r8b));
        System.out.println("  Iterative: " + listToString(r8c));
        System.out.println("  BFS-RF:    " + listToString(r8d));
        System.out.println("  All match: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}