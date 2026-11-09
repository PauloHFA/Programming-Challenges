import java.util.*;

/**
 * LeetCode 129 - Sum Root to Leaf Numbers
 * 
 * Você recebe o root de uma árvore binária contendo dígitos de 0 a 9 apenas.
 * Cada caminho da raiz até folha representa um número.
 * Retorne a soma total de todos os números da raiz até folha.
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
     * Passa número atual como parâmetro, multiplica por 10 e adiciona valor do nó.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int currentSum) {
        if (node == null) return 0;

        currentSum = currentSum * 10 + node.val;

        // Se é folha, retorna o número formado
        if (node.left == null && node.right == null) {
            return currentSum;
        }

        // Soma dos caminhos esquerdo e direito
        return dfs(node.left, currentSum) + dfs(node.right, currentSum);
    }

    /**
     * Método 2: DFS Iterativo com Stack
     * Usa stack para guardar (nó, número_até_agora).
     * Tempo: O(n), Espaço: O(h)
     */
    public int sumNumbersIterative(TreeNode root) {
        if (root == null) return 0;

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, root.val));
        int totalSum = 0;

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int num = pair.getValue();

            if (node.left == null && node.right == null) {
                totalSum += num;
            }

            if (node.right != null) {
                stack.push(new Pair<>(node.right, num * 10 + node.right.val));
            }
            if (node.left != null) {
                stack.push(new Pair<>(node.left, num * 10 + node.left.val));
            }
        }

        return totalSum;
    }

    /**
     * Método 3: BFS com Queue
     * Usa queue para processar nível a nível.
     * Tempo: O(n), Espaço: O(w)
     */
    public int sumNumbersBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, root.val));
        int totalSum = 0;

        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int num = pair.getValue();

            if (node.left == null && node.right == null) {
                totalSum += num;
            }

            if (node.left != null) {
                queue.offer(new Pair<>(node.left, num * 10 + node.left.val));
            }
            if (node.right != null) {
                queue.offer(new Pair<>(node.right, num * 10 + node.right.val));
            }
        }

        return totalSum;
    }

    /**
     * Método 4: Morris Traversal (O(1) espaço)
     * Complexo para manter estado do número, não implementado.
     */

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

        System.out.println("=== Sum Root to Leaf Numbers Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, 2, 3};
        TreeNode root1 = buildTree(arr1);
        int expected1 = 25; // 12 + 13 = 25
        int r1 = sol.sumNumbers(root1);
        int r1i = sol.sumNumbersIterative(root1);
        int r1b = sol.sumNumbersBFS(root1);
        boolean pass1 = r1 == expected1 && r1i == expected1 && r1b == expected1;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + expected1 + " (12 + 13)");
        System.out.println("  Recursive: " + r1 + (r1 == expected1 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + r1i + (r1i == expected1 ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + r1b + (r1b == expected1 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {4, 9, 0, 5, 1};
        TreeNode root2 = buildTree(arr2);
        int expected2 = 1026; // 495 + 491 + 40 = 1026
        int r2 = sol.sumNumbers(root2);
        int r2i = sol.sumNumbersIterative(root2);
        int r2b = sol.sumNumbersBFS(root2);
        boolean pass2 = r2 == expected2 && r2i == expected2 && r2b == expected2;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + expected2 + " (495 + 491 + 40)");
        System.out.println("  Recursive: " + r2 + (r2 == expected2 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + r2i + (r2i == expected2 ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + r2b + (r2b == expected2 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        int expected3 = 1;
        int r3 = sol.sumNumbers(root3);
        int r3i = sol.sumNumbersIterative(root3);
        int r3b = sol.sumNumbersBFS(root3);
        boolean pass3 = r3 == expected3 && r3i == expected3 && r3b == expected3;
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3};
        TreeNode root4 = buildTree(arr4);
        int expected4 = 123; // 123
        int r4 = sol.sumNumbers(root4);
        int r4i = sol.sumNumbersIterative(root4);
        int r4b = sol.sumNumbersBFS(root4);
        boolean pass4 = r4 == expected4 && r4i == expected4 && r4b == expected4;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + expected4);
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3};
        TreeNode root5 = buildTree(arr5);
        int expected5 = 123; // 123
        int r5 = sol.sumNumbers(root5);
        int r5i = sol.sumNumbersIterative(root5);
        int r5b = sol.sumNumbersBFS(root5);
        boolean pass5 = r5 == expected5 && r5i == expected5 && r5b == expected5;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + expected5);
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: With zeros
        System.out.println("Test 6: With zeros");
        Integer[] arr6 = {1, 0, 2};
        TreeNode root6 = buildTree(arr6);
        int expected6 = 12; // 10 + 12 = 22? Wait: 1->0 = 10, 1->2 = 12, total = 22
        int r6 = sol.sumNumbers(root6);
        int r6i = sol.sumNumbersIterative(root6);
        int r6b = sol.sumNumbersBFS(root6);
        boolean pass6 = r6 == 22 && r6i == 22 && r6b == 22;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: 22 (10 + 12)");
        System.out.println("  Recursive: " + r6 + (r6 == 22 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + r6i + (r6i == 22 ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + r6b + (r6b == 22 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Complete tree
        System.out.println("Test 7: Complete tree");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root7 = buildTree(arr7);
        // Paths: 124, 125, 136, 137 = 124+125+136+137 = 522
        int expected7 = 522;
        int r7 = sol.sumNumbers(root7);
        int r7i = sol.sumNumbersIterative(root7);
        int r7b = sol.sumNumbersBFS(root7);
        boolean pass7 = r7 == expected7 && r7i == expected7 && r7b == expected7;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + expected7 + " (124+125+136+137)");
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: All methods consistency
        System.out.println("Test 8: All methods consistency");
        Integer[] arr8 = {4, 9, 0, 5, 1};
        TreeNode root8a = buildTree(arr8);
        TreeNode root8b = buildTree(arr8);
        TreeNode root8c = buildTree(arr8);

        int r8a = sol.sumNumbers(root8a);
        int r8b = sol.sumNumbersIterative(root8b);
        int r8c = sol.sumNumbersBFS(root8c);

        boolean pass8 = r8a == r8b && r8b == r8c;
        System.out.println("  Recursive: " + r8a);
        System.out.println("  Iterative: " + r8b);
        System.out.println("  BFS:       " + r8c);
        System.out.println("  All match: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}