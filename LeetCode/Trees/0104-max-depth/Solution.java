import java.util.*;

/**
 * LeetCode 104 - Maximum Depth of Binary Tree
 * 
 * Dado o root de uma árvore binária, retorne sua profundidade máxima.
 * A profundidade máxima de uma árvore binária é o número de nós ao longo
 * do caminho mais longo desde o nó raiz até o nó folha mais distante.
 */
public class Solution {

    // Simple Pair class for iterative DFS
    private static class Pair<K, V> {
        private final K key;
        private final V value;
        Pair(K key, V value) { this.key = key; this.value = value; }
        K getKey() { return key; }
        V getValue() { return value; }
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
     * Método 1: DFS Recursivo (Principal)
     * Profundidade = 1 + max(profundidade esquerda, profundidade direita)
     * Tempo: O(n), Espaço: O(h) stack de recursão
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * Método 2: BFS Iterativo (Level Order)
     * Conta quantos níveis existem na árvore
     * Tempo: O(n), Espaço: O(w) largura máxima
     */
    public int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            depth++;
        }

        return depth;
    }

    /**
     * Método 3: DFS Iterativo com Stack
     * Usa stack para simular recursão, guarda (nó, profundidade)
     * Tempo: O(n), Espaço: O(h)
     */
    public int maxDepthIterative(TreeNode root) {
        if (root == null) return 0;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        int maxDepth = 0;

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();
            maxDepth = Math.max(maxDepth, depth);

            if (node.right != null) stack.push(new Pair<>(node.right, depth + 1));
            if (node.left != null) stack.push(new Pair<>(node.left, depth + 1));
        }

        return maxDepth;
    }

    /**
     * Método 4: Morris Traversal (O(1) espaço)
     * Modifica temporariamente a árvore para evitar stack/queue
     * Tempo: O(n), Espaço: O(1)
     */
    public int maxDepthMorris(TreeNode root) {
        int maxDepth = 0;
        int currentDepth = 0;
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
                current = current.right;
            } else {
                // Encontra predecessor
                TreeNode predecessor = current.left;
                int steps = 1;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                    steps++;
                }

                if (predecessor.right == null) {
                    // Cria thread
                    predecessor.right = current;
                    currentDepth++;
                    maxDepth = Math.max(maxDepth, currentDepth);
                    current = current.left;
                } else {
                    // Remove thread
                    predecessor.right = null;
                    currentDepth -= steps;
                    current = current.right;
                }
            }
        }

        return maxDepth;
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

        System.out.println("=== Maximum Depth of Binary Tree Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(arr1);
        int expected1 = 3;
        int r1 = sol.maxDepth(root1);
        int r1b = sol.maxDepthBFS(root1);
        int r1i = sol.maxDepthIterative(root1);
        int r1m = sol.maxDepthMorris(root1);
        boolean pass1 = r1 == expected1 && r1b == expected1 && r1i == expected1 && r1m == expected1;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + expected1);
        System.out.println("  Recursive: " + r1 + (r1 == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r1b + (r1b == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r1i + (r1i == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r1m + (r1m == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {1, null, 2};
        TreeNode root2 = buildTree(arr2);
        int expected2 = 2;
        int r2 = sol.maxDepth(root2);
        int r2b = sol.maxDepthBFS(root2);
        int r2i = sol.maxDepthIterative(root2);
        int r2m = sol.maxDepthMorris(root2);
        boolean pass2 = r2 == expected2 && r2b == expected2 && r2i == expected2 && r2m == expected2;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + expected2);
        System.out.println("  Recursive: " + r2 + (r2 == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r2b + (r2b == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r2i + (r2i == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r2m + (r2m == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty tree
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        int expected3 = 0;
        int r3 = sol.maxDepth(root3);
        int r3b = sol.maxDepthBFS(root3);
        int r3i = sol.maxDepthIterative(root3);
        int r3m = sol.maxDepthMorris(root3);
        boolean pass3 = r3 == expected3 && r3b == expected3 && r3i == expected3 && r3m == expected3;
        System.out.println("  Expected: " + expected3);
        System.out.println("  Recursive: " + r3 + (r3 == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r3b + (r3b == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r3i + (r3i == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r3m + (r3m == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node
        System.out.println("Test 4: Single node");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        int expected4 = 1;
        int r4 = sol.maxDepth(root4);
        int r4b = sol.maxDepthBFS(root4);
        int r4i = sol.maxDepthIterative(root4);
        int r4m = sol.maxDepthMorris(root4);
        boolean pass4 = r4 == expected4 && r4b == expected4 && r4i == expected4 && r4m == expected4;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + expected4);
        System.out.println("  Recursive: " + r4 + (r4 == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r4b + (r4b == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r4i + (r4i == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r4m + (r4m == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        Integer[] arr5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        int expected5 = 4;
        int r5 = sol.maxDepth(root5);
        int r5b = sol.maxDepthBFS(root5);
        int r5i = sol.maxDepthIterative(root5);
        int r5m = sol.maxDepthMorris(root5);
        boolean pass5 = r5 == expected5 && r5b == expected5 && r5i == expected5 && r5m == expected5;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + expected5);
        System.out.println("  Recursive: " + r5 + (r5 == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r5b + (r5b == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r5i + (r5i == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r5m + (r5m == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Right skewed
        System.out.println("Test 6: Right skewed");
        Integer[] arr6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        int expected6 = 4;
        int r6 = sol.maxDepth(root6);
        int r6b = sol.maxDepthBFS(root6);
        int r6i = sol.maxDepthIterative(root6);
        int r6m = sol.maxDepthMorris(root6);
        boolean pass6 = r6 == expected6 && r6b == expected6 && r6i == expected6 && r6m == expected6;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + expected6);
        System.out.println("  Recursive: " + r6 + (r6 == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r6b + (r6b == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r6i + (r6i == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r6m + (r6m == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Complete tree
        System.out.println("Test 7: Complete tree");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7 = buildTree(arr7);
        int expected7 = 3;
        int r7 = sol.maxDepth(root7);
        int r7b = sol.maxDepthBFS(root7);
        int r7i = sol.maxDepthIterative(root7);
        int r7m = sol.maxDepthMorris(root7);
        boolean pass7 = r7 == expected7 && r7b == expected7 && r7i == expected7 && r7m == expected7;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + expected7);
        System.out.println("  Recursive: " + r7 + (r7 == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r7b + (r7b == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r7i + (r7i == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r7m + (r7m == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Perfect tree height 3
        System.out.println("Test 8: Perfect tree height 3");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root8 = buildTree(arr8);
        int expected8 = 4;
        int r8 = sol.maxDepth(root8);
        int r8b = sol.maxDepthBFS(root8);
        int r8i = sol.maxDepthIterative(root8);
        int r8m = sol.maxDepthMorris(root8);
        boolean pass8 = r8 == expected8 && r8b == expected8 && r8i == expected8 && r8m == expected8;
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Expected: " + expected8);
        System.out.println("  Recursive: " + r8 + (r8 == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r8b + (r8b == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r8i + (r8i == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r8m + (r8m == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Mixed tree
        System.out.println("Test 9: Mixed tree");
        Integer[] arr9 = {1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8};
        TreeNode root9 = buildTree(arr9);
        int expected9 = 4;
        int r9 = sol.maxDepth(root9);
        int r9b = sol.maxDepthBFS(root9);
        int r9i = sol.maxDepthIterative(root9);
        int r9m = sol.maxDepthMorris(root9);
        boolean pass9 = r9 == expected9 && r9b == expected9 && r9i == expected9 && r9m == expected9;
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Expected: " + expected9);
        System.out.println("  Recursive: " + r9 + (r9 == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r9b + (r9b == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r9i + (r9i == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r9m + (r9m == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes left
        System.out.println("Test 10: Two nodes left");
        Integer[] arr10 = {1, 2};
        TreeNode root10 = buildTree(arr10);
        int expected10 = 2;
        int r10 = sol.maxDepth(root10);
        int r10b = sol.maxDepthBFS(root10);
        int r10i = sol.maxDepthIterative(root10);
        int r10m = sol.maxDepthMorris(root10);
        boolean pass10 = r10 == expected10 && r10b == expected10 && r10i == expected10 && r10m == expected10;
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Expected: " + expected10);
        System.out.println("  Recursive: " + r10 + (r10 == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r10b + (r10b == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r10i + (r10i == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r10m + (r10m == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Two nodes right
        System.out.println("Test 11: Two nodes right");
        Integer[] arr11 = {1, null, 2};
        TreeNode root11 = buildTree(arr11);
        int expected11 = 2;
        int r11 = sol.maxDepth(root11);
        int r11b = sol.maxDepthBFS(root11);
        int r11i = sol.maxDepthIterative(root11);
        int r11m = sol.maxDepthMorris(root11);
        boolean pass11 = r11 == expected11 && r11b == expected11 && r11i == expected11 && r11m == expected11;
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Expected: " + expected11);
        System.out.println("  Recursive: " + r11 + (r11 == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r11b + (r11b == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r11i + (r11i == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r11m + (r11m == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        // Test 12: Negative values
        System.out.println("Test 12: Negative values");
        Integer[] arr12 = {-1, -2, -3, -4, -5};
        TreeNode root12 = buildTree(arr12);
        int expected12 = 3;
        int r12 = sol.maxDepth(root12);
        int r12b = sol.maxDepthBFS(root12);
        int r12i = sol.maxDepthIterative(root12);
        int r12m = sol.maxDepthMorris(root12);
        boolean pass12 = r12 == expected12 && r12b == expected12 && r12i == expected12 && r12m == expected12;
        System.out.println("  Tree: " + arrayToString(arr12));
        System.out.println("  Expected: " + expected12);
        System.out.println("  Recursive: " + r12 + (r12 == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r12b + (r12b == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r12i + (r12i == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r12m + (r12m == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass12 ? "?" : "? FAIL"));
        allPassed &= pass12;
        System.out.println();

        // Test 13: Unbalanced tree
        System.out.println("Test 13: Unbalanced tree");
        Integer[] arr13 = {1, 2, 3, 4, 5, null, null, 6, 7};
        TreeNode root13 = buildTree(arr13);
        int expected13 = 4;
        int r13 = sol.maxDepth(root13);
        int r13b = sol.maxDepthBFS(root13);
        int r13i = sol.maxDepthIterative(root13);
        int r13m = sol.maxDepthMorris(root13);
        boolean pass13 = r13 == expected13 && r13b == expected13 && r13i == expected13 && r13m == expected13;
        System.out.println("  Tree: " + arrayToString(arr13));
        System.out.println("  Expected: " + expected13);
        System.out.println("  Recursive: " + r13 + (r13 == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r13b + (r13b == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r13i + (r13i == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  Morris:    " + r13m + (r13m == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass13 ? "?" : "? FAIL"));
        allPassed &= pass13;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}