import java.util.*;

/**
 * LeetCode 112 - Path Sum
 * 
 * Dado o root de uma árvore binária e um inteiro targetSum, retorne true se
 * a árvore tem um caminho da raiz até folha tal que somando todos os valores
 * ao longo do caminho seja igual a targetSum.
 * Um nó folha é um nó sem filhos.
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
     * Subtrai valor do nó do targetSum a cada passo.
     * Se chega em folha e targetSum == 0, encontrou caminho.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        // Se é folha, verifica se soma bate
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        // Recursivamente verifica subárvores com targetSum reduzido
        int remaining = targetSum - root.val;
        return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
    }

    /**
     * Método 2: DFS Recursivo com Soma Acumulada
     * Passa soma atual como parâmetro.
     * Tempo: O(n), Espaço: O(h)
     */
    public boolean hasPathSumAccum(TreeNode root, int targetSum) {
        return dfsAccum(root, 0, targetSum);
    }

    private boolean dfsAccum(TreeNode node, int currentSum, int targetSum) {
        if (node == null) return false;

        currentSum += node.val;

        // Se é folha
        if (node.left == null && node.right == null) {
            return currentSum == targetSum;
        }

        return dfsAccum(node.left, currentSum, targetSum) || dfsAccum(node.right, currentSum, targetSum);
    }

    /**
     * Método 3: BFS Iterativo com Queue
     * Usa queue para guardar (nó, soma_até_agora).
     * Tempo: O(n), Espaço: O(w)
     */
    public boolean hasPathSumBFS(TreeNode root, int targetSum) {
        if (root == null) return false;

        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, root.val));

        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int sum = pair.getValue();

            // Se é folha
            if (node.left == null && node.right == null) {
                if (sum == targetSum) return true;
            }

            if (node.left != null) queue.offer(new Pair<>(node.left, sum + node.left.val));
            if (node.right != null) queue.offer(new Pair<>(node.right, sum + node.right.val));
        }

        return false;
    }

    /**
     * Método 4: DFS Iterativo com Stack
     * Usa stack para simular recursão.
     * Tempo: O(n), Espaço: O(h)
     */
    public boolean hasPathSumIterative(TreeNode root, int targetSum) {
        if (root == null) return false;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, root.val));

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int sum = pair.getValue();

            // Se é folha
            if (node.left == null && node.right == null) {
                if (sum == targetSum) return true;
            }

            if (node.right != null) stack.push(new Pair<>(node.right, sum + node.right.val));
            if (node.left != null) stack.push(new Pair<>(node.left, sum + node.left.val));
        }

        return false;
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

        System.out.println("=== Path Sum Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
        TreeNode root1 = buildTree(arr1);
        int target1 = 22;
        boolean r1 = sol.hasPathSum(root1, target1);
        boolean r1a = sol.hasPathSumAccum(root1, target1);
        boolean r1b = sol.hasPathSumBFS(root1, target1);
        boolean r1i = sol.hasPathSumIterative(root1, target1);
        boolean pass1 = r1 && r1a && r1b && r1i;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Target: " + target1);
        System.out.println("  Expected: true (5->4->11->2)");
        System.out.println("  Recursive: " + (r1 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r1a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r1b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r1i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {1, 2, 3};
        TreeNode root2 = buildTree(arr2);
        int target2 = 5;
        boolean r2 = sol.hasPathSum(root2, target2);
        boolean r2a = sol.hasPathSumAccum(root2, target2);
        boolean r2b = sol.hasPathSumBFS(root2, target2);
        boolean r2i = sol.hasPathSumIterative(root2, target2);
        boolean pass2 = !r2 && !r2a && !r2b && !r2i;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Target: " + target2);
        System.out.println("  Expected: false");
        System.out.println("  Recursive: " + (!r2 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (!r2a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r2b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r2i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Example 3
        System.out.println("Test 3: Example 3");
        TreeNode root3 = null;
        int target3 = 0;
        boolean r3 = sol.hasPathSum(root3, target3);
        boolean r3a = sol.hasPathSumAccum(root3, target3);
        boolean r3b = sol.hasPathSumBFS(root3, target3);
        boolean r3i = sol.hasPathSumIterative(root3, target3);
        boolean pass3 = !r3 && !r3a && !r3b && !r3i;
        System.out.println("  Tree: null");
        System.out.println("  Target: " + target3);
        System.out.println("  Expected: false");
        System.out.println("  Recursive: " + (!r3 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (!r3a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r3b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r3i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node - match
        System.out.println("Test 4: Single node - match");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        int target4 = 1;
        boolean r4 = sol.hasPathSum(root4, target4);
        boolean r4a = sol.hasPathSumAccum(root4, target4);
        boolean r4b = sol.hasPathSumBFS(root4, target4);
        boolean r4i = sol.hasPathSumIterative(root4, target4);
        boolean pass4 = r4 && r4a && r4b && r4i;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Target: " + target4);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r4 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r4a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r4b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r4i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Single node - no match
        System.out.println("Test 5: Single node - no match");
        Integer[] arr5 = {1};
        TreeNode root5 = buildTree(arr5);
        int target5 = 2;
        boolean r5 = sol.hasPathSum(root5, target5);
        boolean r5a = sol.hasPathSumAccum(root5, target5);
        boolean r5b = sol.hasPathSumBFS(root5, target5);
        boolean r5i = sol.hasPathSumIterative(root5, target5);
        boolean pass5 = !r5 && !r5a && !r5b && !r5i;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Target: " + target5);
        System.out.println("  Expected: false");
        System.out.println("  Recursive: " + (!r5 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (!r5a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r5b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r5i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Left skewed - match
        System.out.println("Test 6: Left skewed - match");
        Integer[] arr6 = {1, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        int target6 = 10; // 1+2+3+4
        boolean r6 = sol.hasPathSum(root6, target6);
        boolean r6a = sol.hasPathSumAccum(root6, target6);
        boolean r6b = sol.hasPathSumBFS(root6, target6);
        boolean r6i = sol.hasPathSumIterative(root6, target6);
        boolean pass6 = r6 && r6a && r6b && r6i;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Target: " + target6);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r6 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r6a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r6b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r6i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Right skewed - match
        System.out.println("Test 7: Right skewed - match");
        Integer[] arr7 = {1, null, 2, null, 3, null, 4};
        TreeNode root7 = buildTree(arr7);
        int target7 = 10; // 1+2+3+4
        boolean r7 = sol.hasPathSum(root7, target7);
        boolean r7a = sol.hasPathSumAccum(root7, target7);
        boolean r7b = sol.hasPathSumBFS(root7, target7);
        boolean r7i = sol.hasPathSumIterative(root7, target7);
        boolean pass7 = r7 && r7a && r7b && r7i;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Target: " + target7);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r7 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r7a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r7b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r7i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Complete tree - match
        System.out.println("Test 8: Complete tree - match");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root8 = buildTree(arr8);
        int target8 = 7; // 1->2->4
        boolean r8 = sol.hasPathSum(root8, target8);
        boolean r8a = sol.hasPathSumAccum(root8, target8);
        boolean r8b = sol.hasPathSumBFS(root8, target8);
        boolean r8i = sol.hasPathSumIterative(root8, target8);
        boolean pass8 = r8 && r8a && r8b && r8i;
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Target: " + target8);
        System.out.println("  Expected: true (1->2->4)");
        System.out.println("  Recursive: " + (r8 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r8a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r8b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r8i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Complete tree - no match
        System.out.println("Test 9: Complete tree - no match");
        Integer[] arr9 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root9 = buildTree(arr9);
        int target9 = 100;
        boolean r9 = sol.hasPathSum(root9, target9);
        boolean r9a = sol.hasPathSumAccum(root9, target9);
        boolean r9b = sol.hasPathSumBFS(root9, target9);
        boolean r9i = sol.hasPathSumIterative(root9, target9);
        boolean pass9 = !r9 && !r9a && !r9b && !r9i;
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Target: " + target9);
        System.out.println("  Expected: false");
        System.out.println("  Recursive: " + (!r9 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (!r9a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (!r9b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (!r9i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes left - match
        System.out.println("Test 10: Two nodes left - match");
        Integer[] arr10 = {1, 2};
        TreeNode root10 = buildTree(arr10);
        int target10 = 3;
        boolean r10 = sol.hasPathSum(root10, target10);
        boolean r10a = sol.hasPathSumAccum(root10, target10);
        boolean r10b = sol.hasPathSumBFS(root10, target10);
        boolean r10i = sol.hasPathSumIterative(root10, target10);
        boolean pass10 = r10 && r10a && r10b && r10i;
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Target: " + target10);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r10 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r10a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r10b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r10i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Two nodes right - match
        System.out.println("Test 11: Two nodes right - match");
        Integer[] arr11 = {1, null, 2};
        TreeNode root11 = buildTree(arr11);
        int target11 = 3;
        boolean r11 = sol.hasPathSum(root11, target11);
        boolean r11a = sol.hasPathSumAccum(root11, target11);
        boolean r11b = sol.hasPathSumBFS(root11, target11);
        boolean r11i = sol.hasPathSumIterative(root11, target11);
        boolean pass11 = r11 && r11a && r11b && r11i;
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Target: " + target11);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r11 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r11a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r11b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r11i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        // Test 12: Negative values
        System.out.println("Test 12: Negative values");
        Integer[] arr12 = {-2, null, -3};
        TreeNode root12 = buildTree(arr12);
        int target12 = -5;
        boolean r12 = sol.hasPathSum(root12, target12);
        boolean r12a = sol.hasPathSumAccum(root12, target12);
        boolean r12b = sol.hasPathSumBFS(root12, target12);
        boolean r12i = sol.hasPathSumIterative(root12, target12);
        boolean pass12 = r12 && r12a && r12b && r12i;
        System.out.println("  Tree: " + arrayToString(arr12));
        System.out.println("  Target: " + target12);
        System.out.println("  Expected: true (-2 + -3)");
        System.out.println("  Recursive: " + (r12 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r12a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r12b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r12i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass12 ? "?" : "? FAIL"));
        allPassed &= pass12;
        System.out.println();

        // Test 13: Path to leaf (should be true - 2 is a leaf in this tree)
                System.out.println("Test 13: Path to leaf (should be true)");
        Integer[] arr13 = {1, 2, 3};
        TreeNode root13 = buildTree(arr13);
                int target13 = 3; // 1+2=3 and 2 IS a leaf
        boolean r13 = sol.hasPathSum(root13, target13);
        boolean r13a = sol.hasPathSumAccum(root13, target13);
        boolean r13b = sol.hasPathSumBFS(root13, target13);
        boolean r13i = sol.hasPathSumIterative(root13, target13);
                boolean pass13 = r13 && r13a && r13b && r13i;
        System.out.println("  Tree: " + arrayToString(arr13));
        System.out.println("  Target: " + target13);
                System.out.println("  Expected: true (1->2=3 and 2 is leaf)");
                System.out.println("  Recursive: " + (r13 ? "?" : "? FAIL"));
                System.out.println("  Accum:     " + (r13a ? "?" : "? FAIL"));
                System.out.println("  BFS:       " + (r13b ? "?" : "? FAIL"));
                System.out.println("  Iterative: " + (r13i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass13 ? "?" : "? FAIL"));
        allPassed &= pass13;
        System.out.println();

        // Test 14: Zero target with negative values
        System.out.println("Test 14: Zero target with negative values");
        Integer[] arr14 = {1, -2, -3, 1, 3, -2, null, -1};
        TreeNode root14 = buildTree(arr14);
        int target14 = -1;
        boolean r14 = sol.hasPathSum(root14, target14);
        boolean r14a = sol.hasPathSumAccum(root14, target14);
        boolean r14b = sol.hasPathSumBFS(root14, target14);
        boolean r14i = sol.hasPathSumIterative(root14, target14);
        boolean pass14 = r14 && r14a && r14b && r14i;
        System.out.println("  Tree: " + arrayToString(arr14));
        System.out.println("  Target: " + target14);
        System.out.println("  Expected: true");
        System.out.println("  Recursive: " + (r14 ? "?" : "? FAIL"));
        System.out.println("  Accum:     " + (r14a ? "?" : "? FAIL"));
        System.out.println("  BFS:       " + (r14b ? "?" : "? FAIL"));
        System.out.println("  Iterative: " + (r14i ? "?" : "? FAIL"));
        System.out.println("  All match: " + (pass14 ? "?" : "? FAIL"));
        allPassed &= pass14;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}