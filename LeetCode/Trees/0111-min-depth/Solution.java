import java.util.*;

/**
 * LeetCode 111 - Minimum Depth of Binary Tree
 * 
 * Dado o root de uma árvore binária, encontre sua profundidade mínima.
 * A profundidade mínima é o número de nós ao longo do caminho mais curto
 * desde o nó raiz até o nó folha mais próximo.
 * Nota: Um nó folha é um nó sem filhos.
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
     * Método 1: DFS Recursivo (Principal)
     * Caso base: nó null retorna 0
     * Se um filho é null, deve ir pelo outro filho (não é folha)
     * Tempo: O(n), Espaço: O(h) stack
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        // Se é folha
        if (root.left == null && root.right == null) return 1;

        // Se só tem filho esquerdo
        if (root.right == null) return 1 + minDepth(root.left);

        // Se só tem filho direito
        if (root.left == null) return 1 + minDepth(root.right);

        // Tem ambos filhos
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    /**
     * Método 2: BFS Iterativo (Level Order) - Mais eficiente para árvores desbalanceadas
     * Para assim que encontra a primeira folha.
     * Tempo: O(n) pior caso, Espaço: O(w) largura máxima
     */
    public int minDepthBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            depth++;
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // Se é folha, retorna profundidade atual
                if (node.left == null && node.right == null) {
                    return depth;
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return depth;
    }

    /**
     * Método 3: DFS Iterativo com Stack
     * Usa stack para simular recursão, guarda (nó, profundidade).
     * Tempo: O(n), Espaço: O(h)
     */
    public int minDepthIterative(TreeNode root) {
        if (root == null) return 0;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        int minDepth = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();

            // Se é folha, atualiza mínimo
            if (node.left == null && node.right == null) {
                minDepth = Math.min(minDepth, depth);
            }

            if (node.right != null) stack.push(new Pair<>(node.right, depth + 1));
            if (node.left != null) stack.push(new Pair<>(node.left, depth + 1));
        }

        return minDepth;
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

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Minimum Depth of Binary Tree Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(arr1);
        int expected1 = 2;
        int r1 = sol.minDepth(root1);
        int r1b = sol.minDepthBFS(root1);
        int r1i = sol.minDepthIterative(root1);
        boolean pass1 = r1 == expected1 && r1b == expected1 && r1i == expected1;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + expected1);
        System.out.println("  Recursive: " + r1 + (r1 == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r1b + (r1b == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r1i + (r1i == expected1 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {2, null, 3, null, 4, null, 5, null, 6};
        TreeNode root2 = buildTree(arr2);
        int expected2 = 5;
        int r2 = sol.minDepth(root2);
        int r2b = sol.minDepthBFS(root2);
        int r2i = sol.minDepthIterative(root2);
        boolean pass2 = r2 == expected2 && r2b == expected2 && r2i == expected2;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + expected2);
        System.out.println("  Recursive: " + r2 + (r2 == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r2b + (r2b == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r2i + (r2i == expected2 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty tree
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        int expected3 = 0;
        int r3 = sol.minDepth(root3);
        int r3b = sol.minDepthBFS(root3);
        int r3i = sol.minDepthIterative(root3);
        boolean pass3 = r3 == expected3 && r3b == expected3 && r3i == expected3;
        System.out.println("  Expected: " + expected3);
        System.out.println("  Recursive: " + r3 + (r3 == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r3b + (r3b == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r3i + (r3i == expected3 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node
        System.out.println("Test 4: Single node");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        int expected4 = 1;
        int r4 = sol.minDepth(root4);
        int r4b = sol.minDepthBFS(root4);
        int r4i = sol.minDepthIterative(root4);
        boolean pass4 = r4 == expected4 && r4b == expected4 && r4i == expected4;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + expected4);
        System.out.println("  Recursive: " + r4 + (r4 == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r4b + (r4b == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r4i + (r4i == expected4 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed
        System.out.println("Test 5: Left skewed");
        Integer[] arr5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        int expected5 = 4;
        int r5 = sol.minDepth(root5);
        int r5b = sol.minDepthBFS(root5);
        int r5i = sol.minDepthIterative(root5);
        boolean pass5 = r5 == expected5 && r5b == expected5 && r5i == expected5;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + expected5);
        System.out.println("  Recursive: " + r5 + (r5 == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r5b + (r5b == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r5i + (r5i == expected5 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Right skewed
        System.out.println("Test 6: Right skewed");
        Integer[] arr6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        int expected6 = 4;
        int r6 = sol.minDepth(root6);
        int r6b = sol.minDepthBFS(root6);
        int r6i = sol.minDepthIterative(root6);
        boolean pass6 = r6 == expected6 && r6b == expected6 && r6i == expected6;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + expected6);
        System.out.println("  Recursive: " + r6 + (r6 == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r6b + (r6b == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r6i + (r6i == expected6 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Complete tree
        System.out.println("Test 7: Complete tree");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7 = buildTree(arr7);
        int expected7 = 3;
        int r7 = sol.minDepth(root7);
        int r7b = sol.minDepthBFS(root7);
        int r7i = sol.minDepthIterative(root7);
        boolean pass7 = r7 == expected7 && r7b == expected7 && r7i == expected7;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + expected7);
        System.out.println("  Recursive: " + r7 + (r7 == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r7b + (r7b == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r7i + (r7i == expected7 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Perfect tree height 3
        System.out.println("Test 8: Perfect tree height 3");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root8 = buildTree(arr8);
        int expected8 = 4;
        int r8 = sol.minDepth(root8);
        int r8b = sol.minDepthBFS(root8);
        int r8i = sol.minDepthIterative(root8);
        boolean pass8 = r8 == expected8 && r8b == expected8 && r8i == expected8;
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Expected: " + expected8);
        System.out.println("  Recursive: " + r8 + (r8 == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r8b + (r8b == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r8i + (r8i == expected8 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Mixed tree
        System.out.println("Test 9: Mixed tree");
        Integer[] arr9 = {1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8};
        TreeNode root9 = buildTree(arr9);
        int expected9 = 3; // Path: 1 -> 3 -> 6 (or 1 -> 2 -> 4)
        int r9 = sol.minDepth(root9);
        int r9b = sol.minDepthBFS(root9);
        int r9i = sol.minDepthIterative(root9);
        boolean pass9 = r9 == expected9 && r9b == expected9 && r9i == expected9;
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Expected: " + expected9);
        System.out.println("  Recursive: " + r9 + (r9 == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r9b + (r9b == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r9i + (r9i == expected9 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes left
        System.out.println("Test 10: Two nodes left");
        Integer[] arr10 = {1, 2};
        TreeNode root10 = buildTree(arr10);
        int expected10 = 2;
        int r10 = sol.minDepth(root10);
        int r10b = sol.minDepthBFS(root10);
        int r10i = sol.minDepthIterative(root10);
        boolean pass10 = r10 == expected10 && r10b == expected10 && r10i == expected10;
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Expected: " + expected10);
        System.out.println("  Recursive: " + r10 + (r10 == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r10b + (r10b == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r10i + (r10i == expected10 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Two nodes right
        System.out.println("Test 11: Two nodes right");
        Integer[] arr11 = {1, null, 2};
        TreeNode root11 = buildTree(arr11);
        int expected11 = 2;
        int r11 = sol.minDepth(root11);
        int r11b = sol.minDepthBFS(root11);
        int r11i = sol.minDepthIterative(root11);
        boolean pass11 = r11 == expected11 && r11b == expected11 && r11i == expected11;
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Expected: " + expected11);
        System.out.println("  Recursive: " + r11 + (r11 == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r11b + (r11b == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r11i + (r11i == expected11 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        // Test 12: Negative values
        System.out.println("Test 12: Negative values");
        Integer[] arr12 = {-1, -2, -3, -4, -5};
        TreeNode root12 = buildTree(arr12);
                int expected12 = 2; // Path: -1 -> -3 (depth 2)
        int r12 = sol.minDepth(root12);
        int r12b = sol.minDepthBFS(root12);
        int r12i = sol.minDepthIterative(root12);
        boolean pass12 = r12 == expected12 && r12b == expected12 && r12i == expected12;
        System.out.println("  Tree: " + arrayToString(arr12));
        System.out.println("  Expected: " + expected12);
        System.out.println("  Recursive: " + r12 + (r12 == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r12b + (r12b == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r12i + (r12i == expected12 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass12 ? "?" : "? FAIL"));
        allPassed &= pass12;
        System.out.println();

        // Test 13: Root with only left child which has only right child
        System.out.println("Test 13: Root -> left -> right");
        Integer[] arr13 = {1, 2, null, null, 3};
        TreeNode root13 = buildTree(arr13);
        int expected13 = 3;
        int r13 = sol.minDepth(root13);
        int r13b = sol.minDepthBFS(root13);
        int r13i = sol.minDepthIterative(root13);
        boolean pass13 = r13 == expected13 && r13b == expected13 && r13i == expected13;
        System.out.println("  Tree: " + arrayToString(arr13));
        System.out.println("  Expected: " + expected13);
        System.out.println("  Recursive: " + r13 + (r13 == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r13b + (r13b == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r13i + (r13i == expected13 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass13 ? "?" : "? FAIL"));
        allPassed &= pass13;
        System.out.println();

        // Test 14: Root with only right child which has only left child
        System.out.println("Test 14: Root -> right -> left");
        Integer[] arr14 = {1, null, 2, 3};
        TreeNode root14 = buildTree(arr14);
        int expected14 = 3;
        int r14 = sol.minDepth(root14);
        int r14b = sol.minDepthBFS(root14);
        int r14i = sol.minDepthIterative(root14);
        boolean pass14 = r14 == expected14 && r14b == expected14 && r14i == expected14;
        System.out.println("  Tree: " + arrayToString(arr14));
        System.out.println("  Expected: " + expected14);
        System.out.println("  Recursive: " + r14 + (r14 == expected14 ? " ?" : " ? FAIL"));
        System.out.println("  BFS:       " + r14b + (r14b == expected14 ? " ?" : " ? FAIL"));
        System.out.println("  Iterative: " + r14i + (r14i == expected14 ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass14 ? "?" : "? FAIL"));
        allPassed &= pass14;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}