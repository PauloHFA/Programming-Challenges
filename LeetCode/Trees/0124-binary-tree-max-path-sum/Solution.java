import java.util.*;

/**
 * LeetCode 124 - Binary Tree Maximum Path Sum
 * 
 * Um caminho em uma árvore binária é uma sequência de nós onde cada par de nós
 * adjacentes na sequência tem uma aresta conectando-os. Um nó pode aparecer
 * no caminho no máximo uma vez. O caminho não precisa passar pela raiz.
 * A soma do caminho é a soma dos valores dos nós no caminho.
 * Dado o root de uma árvore binária, retorne a soma máxima de qualquer caminho não vazio.
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
     * Método 1: DFS Recursivo com variável global (Principal)
     * Para cada nó, calcula max path sum que passa por ele (pode incluir ambos filhos)
     * e max path sum que pode ser estendido para o pai (apenas um lado).
     * Tempo: O(n), Espaço: O(h) stack
     */
    private int maxSum;

    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxGain(root);
        return maxSum;
    }

    private int maxGain(TreeNode node) {
        if (node == null) return 0;

        // Max soma do lado esquerdo (ignora se negativo)
        int leftGain = Math.max(maxGain(node.left), 0);
        // Max soma do lado direito (ignora se negativo)
        int rightGain = Math.max(maxGain(node.right), 0);

        // Soma do caminho que passa por este nó (pode ser o máximo global)
        int currentPathSum = node.val + leftGain + rightGain;

        // Atualiza máximo global
        maxSum = Math.max(maxSum, currentPathSum);

        // Retorna max soma que pode ser estendida para o pai
        return node.val + Math.max(leftGain, rightGain);
    }

    /**
     * Método 2: DFS Recursivo retornando par (maxSingle, maxTop)
     * Retorna ambos valores explicitamente sem variável global.
     * Tempo: O(n), Espaço: O(h)
     */
    public int maxPathSumPair(TreeNode root) {
        return maxPathSumHelper(root).maxTop;
    }

    private Result maxPathSumHelper(TreeNode node) {
        if (node == null) {
            return new Result(0, Integer.MIN_VALUE);
        }

        Result left = maxPathSumHelper(node.left);
        Result right = maxPathSumHelper(node.right);

        // Max path sum que pode ser estendido para cima (single path)
        int maxSingle = Math.max(node.val, node.val + Math.max(left.maxSingle, right.maxSingle));

        // Max path sum que passa por este nó (top path - pode usar ambos filhos)
        int maxTop = Math.max(maxSingle, node.val + left.maxSingle + right.maxSingle);

        // Max top geral considera subárvores
        maxTop = Math.max(maxTop, Math.max(left.maxTop, right.maxTop));

        return new Result(maxSingle, maxTop);
    }

    private static class Result {
        int maxSingle; // Max path sum que pode ser estendido para o pai
        int maxTop;    // Max path sum no subárvore (pode não ser estendido)

        Result(int maxSingle, int maxTop) {
            this.maxSingle = maxSingle;
            this.maxTop = maxTop;
        }
    }

    /**
     * Método 3: Iterativo Post-order com Stack
     * Usa dois stacks para simular post-order.
     * Tempo: O(n), Espaço: O(h)
     */
    public int maxPathSumIterative(TreeNode root) {
        if (root == null) return 0;

        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);

        // Post-order usando dois stacks
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }

        Map<TreeNode, Integer> maxSingleMap = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

        while (!stack2.isEmpty()) {
            TreeNode node = stack2.pop();

            int leftMaxSingle = node.left != null ? maxSingleMap.get(node.left) : 0;
            int rightMaxSingle = node.right != null ? maxSingleMap.get(node.right) : 0;

            leftMaxSingle = Math.max(leftMaxSingle, 0);
            rightMaxSingle = Math.max(rightMaxSingle, 0);

            int currentPathSum = node.val + leftMaxSingle + rightMaxSingle;
            maxSum = Math.max(maxSum, currentPathSum);

            int maxSingle = node.val + Math.max(leftMaxSingle, rightMaxSingle);
            maxSingleMap.put(node, maxSingle);
        }

        return maxSum;
    }

    /**
     * Método 4: Morris Traversal (O(1) espaço) - Complexo para este problema
     * Não implementado devido à complexidade de manter estado de path sum.
     */

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

        System.out.println("=== Binary Tree Maximum Path Sum Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, 2, 3};
        TreeNode root1 = buildTree(arr1);
        int expected1 = 6; // 2 + 1 + 3
        int r1 = sol.maxPathSum(root1);
        int r1p = sol.maxPathSumPair(root1);
        int r1i = sol.maxPathSumIterative(root1);
        boolean pass1 = r1 == expected1 && r1p == expected1 && r1i == expected1;
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + expected1);
        System.out.println("  Recursive: " + r1 + (r1 == expected1 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Pair:      " + r1p + (r1p == expected1 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + r1i + (r1i == expected1 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {-10, 9, 20, null, null, 15, 7};
        TreeNode root2 = buildTree(arr2);
        int expected2 = 42; // 15 + 20 + 7
        int r2 = sol.maxPathSum(root2);
        int r2p = sol.maxPathSumPair(root2);
        int r2i = sol.maxPathSumIterative(root2);
        boolean pass2 = r2 == expected2 && r2p == expected2 && r2i == expected2;
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + expected2);
        System.out.println("  Recursive: " + r2 + (r2 == expected2 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Pair:      " + r2p + (r2p == expected2 ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + r2i + (r2i == expected2 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        int expected3 = 1;
        int r3 = sol.maxPathSum(root3);
        int r3p = sol.maxPathSumPair(root3);
        int r3i = sol.maxPathSumIterative(root3);
        boolean pass3 = r3 == expected3 && r3p == expected3 && r3i == expected3;
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: All negative
        System.out.println("Test 4: All negative");
        Integer[] arr4 = {-3, -2, -1};
        TreeNode root4 = buildTree(arr4);
        int expected4 = -1; // Max single node
        int r4 = sol.maxPathSum(root4);
        int r4p = sol.maxPathSumPair(root4);
        int r4i = sol.maxPathSumIterative(root4);
        boolean pass4 = r4 == expected4 && r4p == expected4 && r4i == expected4;
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + expected4);
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Left skewed negative
        System.out.println("Test 5: Left skewed negative");
        Integer[] arr5 = {-2, -1, null, -3};
        TreeNode root5 = buildTree(arr5);
        int expected5 = -1; // -1 is max single node
        int r5 = sol.maxPathSum(root5);
        int r5p = sol.maxPathSumPair(root5);
        int r5i = sol.maxPathSumIterative(root5);
        boolean pass5 = r5 == expected5 && r5p == expected5 && r5i == expected5;
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + expected5);
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Mixed positive negative
        System.out.println("Test 6: Mixed positive negative");
        Integer[] arr6 = {2, -1, -2};
        TreeNode root6 = buildTree(arr6);
        int expected6 = 2; // Just root
        int r6 = sol.maxPathSum(root6);
        int r6p = sol.maxPathSumPair(root6);
        int r6i = sol.maxPathSumIterative(root6);
        boolean pass6 = r6 == expected6 && r6p == expected6 && r6i == expected6;
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + expected6);
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Path through root with negative children
        System.out.println("Test 7: Path through root with negative children");
        Integer[] arr7 = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
        TreeNode root7 = buildTree(arr7);
        // Max path: 7 + 11 + 4 + 5 + 8 + 13 = 48? Let's check
        // Actually: 7+11+4+5+8+13 = 48, but 7+11+4+5+8+4+1 = 40
        // Best might be 7+11+4+5+8+13 = 48
        int r7 = sol.maxPathSum(root7);
        int r7p = sol.maxPathSumPair(root7);
        int r7i = sol.maxPathSumIterative(root7);
        boolean pass7 = r7 == r7p && r7p == r7i;
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Recursive: " + r7);
        System.out.println("  Pair:      " + r7p);
        System.out.println("  Iterative: " + r7i);
        System.out.println("  All match: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Complete tree
        System.out.println("Test 8: Complete tree");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root8 = buildTree(arr8);
        // Max path: 4+2+5 = 11 or 6+3+7 = 16 or 4+2+1+3+7 = 17
        int r8 = sol.maxPathSum(root8);
        int r8p = sol.maxPathSumPair(root8);
        int r8i = sol.maxPathSumIterative(root8);
        boolean pass8 = r8 == r8p && r8p == r8i;
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Recursive: " + r8);
        System.out.println("  Pair:      " + r8p);
        System.out.println("  Iterative: " + r8i);
        System.out.println("  All match: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Single negative with positive children
                System.out.println("Test 9: Negative root with positive children");
                Integer[] arr9 = {-10, 9, 20};
                TreeNode root9 = buildTree(arr9);
                int expected9 = 20; // Max is 20 (single node), path 9->-10->20 = 19
                int r9 = sol.maxPathSum(root9);
                int r9p = sol.maxPathSumPair(root9);
                int r9i = sol.maxPathSumIterative(root9);
                boolean pass9 = r9 == expected9 && r9p == expected9 && r9i == expected9;
                System.out.println("  Tree: " + arrayToString(arr9));
                System.out.println("  Expected: " + expected9);
                System.out.println("  Result: " + (pass9 ? "✓" : "✗ FAIL"));
                allPassed &= pass9;
                System.out.println();

        // Test 10: All methods consistency
        System.out.println("Test 10: All methods consistency");
        Integer[] arr10 = {-10, 9, 20, null, null, 15, 7};
        TreeNode root10a = buildTree(arr10);
        TreeNode root10b = buildTree(arr10);
        TreeNode root10c = buildTree(arr10);

        int r10a = sol.maxPathSum(root10a);
        int r10b = sol.maxPathSumPair(root10b);
        int r10c = sol.maxPathSumIterative(root10c);

        boolean pass10 = r10a == r10b && r10b == r10c;
        System.out.println("  Recursive: " + r10a);
        System.out.println("  Pair:      " + r10b);
        System.out.println("  Iterative: " + r10c);
        System.out.println("  All match: " + (pass10 ? "✓" : "✗ FAIL"));
        allPassed &= pass10;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}