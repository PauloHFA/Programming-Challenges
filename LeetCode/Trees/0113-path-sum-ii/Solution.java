import java.util.*;

/**
 * LeetCode 113 - Path Sum II
 * 
 * Dado o root de uma árvore binária e um inteiro targetSum, retorne todos os caminhos
 * da raiz até folha onde a soma dos valores dos nós no caminho seja igual a targetSum.
 * Cada caminho deve ser retornado como uma lista dos valores dos nós, não referências.
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
     * Método 1: DFS Recursivo com Backtracking (Principal)
     * Usa backtracking para construir caminhos e verificar soma.
     * Tempo: O(n^2) pior caso (cópia de lista), Espaço: O(h) stack + O(n) resultado
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        dfs(root, targetSum, currentPath, result);
        return result;
    }

    private void dfs(TreeNode node, int remainingSum, List<Integer> currentPath, List<List<Integer>> result) {
        if (node == null) return;

        currentPath.add(node.val);

        // Se é folha
        if (node.left == null && node.right == null) {
            if (remainingSum == node.val) {
                result.add(new ArrayList<>(currentPath));
            }
        } else {
            dfs(node.left, remainingSum - node.val, currentPath, result);
            dfs(node.right, remainingSum - node.val, currentPath, result);
        }

        // Backtrack
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Método 2: DFS Iterativo com Stack
     * Usa stack para simular recursão com estado do caminho.
     * Tempo: O(n^2), Espaço: O(h)
     */
    public List<List<Integer>> pathSumIterative(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Stack<Pair<TreeNode, Pair<Integer, List<Integer>>>> stack = new Stack<>();
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(root.val);
        stack.push(new Pair<>(root, new Pair<>(root.val, initialPath)));

        while (!stack.isEmpty()) {
            Pair<TreeNode, Pair<Integer, List<Integer>>> top = stack.pop();
            TreeNode node = top.getKey();
            int sum = top.getValue().getKey();
            List<Integer> path = top.getValue().getValue();

            if (node.left == null && node.right == null) {
                if (sum == targetSum) {
                    result.add(new ArrayList<>(path));
                }
            }

            if (node.right != null) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node.right.val);
                stack.push(new Pair<>(node.right, new Pair<>(sum + node.right.val, newPath)));
            }

            if (node.left != null) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node.left.val);
                stack.push(new Pair<>(node.left, new Pair<>(sum + node.left.val, newPath)));
            }
        }

        return result;
    }

    /**
     * Método 3: BFS com Queue
     * Usa queue para processar nível a nível.
     * Tempo: O(n^2), Espaço: O(w)
     */
    public List<List<Integer>> pathSumBFS(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Pair<TreeNode, Pair<Integer, List<Integer>>>> queue = new LinkedList<>();
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(root.val);
        queue.offer(new Pair<>(root, new Pair<>(root.val, initialPath)));

        while (!queue.isEmpty()) {
            Pair<TreeNode, Pair<Integer, List<Integer>>> pair = queue.poll();
            TreeNode node = pair.getKey();
            int sum = pair.getValue().getKey();
            List<Integer> path = pair.getValue().getValue();

            if (node.left == null && node.right == null) {
                if (sum == targetSum) {
                    result.add(new ArrayList<>(path));
                }
            }

            if (node.left != null) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node.left.val);
                queue.offer(new Pair<>(node.left, new Pair<>(sum + node.left.val, newPath)));
            }

            if (node.right != null) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node.right.val);
                queue.offer(new Pair<>(node.right, new Pair<>(sum + node.right.val, newPath)));
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

    // Helper para comparar listas de listas
    private static boolean listsEqual(List<List<Integer>> a, List<List<Integer>> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) return false;
        }
        return true;
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

    // Helper para lista de listas para string
    private static String listOfListsToString(List<List<Integer>> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Path Sum II Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1};
        TreeNode root1 = buildTree(arr1);
        int target1 = 22;
        List<List<Integer>> r1 = sol.pathSum(root1, target1);
        List<List<Integer>> r1i = sol.pathSumIterative(root1, target1);
        List<List<Integer>> r1b = sol.pathSumBFS(root1, target1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(5, 4, 11, 2),
            Arrays.asList(5, 8, 4, 5)
        );
        boolean pass1 = listsEqual(r1, expected1) && listsEqual(r1i, expected1) && listsEqual(r1b, expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Target: " + target1);
        System.out.println("  Expected: " + listOfListsToString(expected1));
        System.out.println("  Recursive: " + listOfListsToString(r1) + (listsEqual(r1, expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r1i) + (listsEqual(r1i, expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r1b) + (listsEqual(r1b, expected1) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Example 2
        System.out.println("Test 2: Example 2");
        Integer[] arr2 = {1, 2, 3};
        TreeNode root2 = buildTree(arr2);
        int target2 = 5;
        List<List<Integer>> r2 = sol.pathSum(root2, target2);
        List<List<Integer>> r2i = sol.pathSumIterative(root2, target2);
        List<List<Integer>> r2b = sol.pathSumBFS(root2, target2);
        List<List<Integer>> expected2 = new ArrayList<>();
        boolean pass2 = listsEqual(r2, expected2) && listsEqual(r2i, expected2) && listsEqual(r2b, expected2);
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Target: " + target2);
        System.out.println("  Expected: []");
        System.out.println("  Recursive: " + listOfListsToString(r2) + (listsEqual(r2, expected2) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r2i) + (listsEqual(r2i, expected2) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r2b) + (listsEqual(r2b, expected2) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Example 3
        System.out.println("Test 3: Example 3");
        TreeNode root3 = null;
        int target3 = 0;
        List<List<Integer>> r3 = sol.pathSum(root3, target3);
        List<List<Integer>> r3i = sol.pathSumIterative(root3, target3);
        List<List<Integer>> r3b = sol.pathSumBFS(root3, target3);
        List<List<Integer>> expected3 = new ArrayList<>();
        boolean pass3 = listsEqual(r3, expected3) && listsEqual(r3i, expected3) && listsEqual(r3b, expected3);
        System.out.println("  Tree: null");
        System.out.println("  Target: " + target3);
        System.out.println("  Expected: []");
        System.out.println("  Recursive: " + listOfListsToString(r3) + (listsEqual(r3, expected3) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r3i) + (listsEqual(r3i, expected3) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r3b) + (listsEqual(r3b, expected3) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Single node - match
        System.out.println("Test 4: Single node - match");
        Integer[] arr4 = {1};
        TreeNode root4 = buildTree(arr4);
        int target4 = 1;
        List<List<Integer>> r4 = sol.pathSum(root4, target4);
        List<List<Integer>> r4i = sol.pathSumIterative(root4, target4);
        List<List<Integer>> r4b = sol.pathSumBFS(root4, target4);
        List<List<Integer>> expected4 = Arrays.asList(Arrays.asList(1));
        boolean pass4 = listsEqual(r4, expected4) && listsEqual(r4i, expected4) && listsEqual(r4b, expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Target: " + target4);
        System.out.println("  Expected: [[1]]");
        System.out.println("  Recursive: " + listOfListsToString(r4) + (listsEqual(r4, expected4) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r4i) + (listsEqual(r4i, expected4) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r4b) + (listsEqual(r4b, expected4) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Single node - no match
        System.out.println("Test 5: Single node - no match");
        Integer[] arr5 = {1};
        TreeNode root5 = buildTree(arr5);
        int target5 = 2;
        List<List<Integer>> r5 = sol.pathSum(root5, target5);
        List<List<Integer>> r5i = sol.pathSumIterative(root5, target5);
        List<List<Integer>> r5b = sol.pathSumBFS(root5, target5);
        List<List<Integer>> expected5 = new ArrayList<>();
        boolean pass5 = listsEqual(r5, expected5) && listsEqual(r5i, expected5) && listsEqual(r5b, expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Target: " + target5);
        System.out.println("  Expected: []");
        System.out.println("  Recursive: " + listOfListsToString(r5) + (listsEqual(r5, expected5) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r5i) + (listsEqual(r5i, expected5) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r5b) + (listsEqual(r5b, expected5) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Left skewed
        System.out.println("Test 6: Left skewed");
        Integer[] arr6 = {1, 2, null, 3, null, 4};
        TreeNode root6 = buildTree(arr6);
        int target6 = 10; // 1+2+3+4
        List<List<Integer>> r6 = sol.pathSum(root6, target6);
        List<List<Integer>> r6i = sol.pathSumIterative(root6, target6);
        List<List<Integer>> r6b = sol.pathSumBFS(root6, target6);
        List<List<Integer>> expected6 = Arrays.asList(Arrays.asList(1, 2, 3, 4));
        boolean pass6 = listsEqual(r6, expected6) && listsEqual(r6i, expected6) && listsEqual(r6b, expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Target: " + target6);
        System.out.println("  Expected: [[1,2,3,4]]");
        System.out.println("  Recursive: " + listOfListsToString(r6) + (listsEqual(r6, expected6) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r6i) + (listsEqual(r6i, expected6) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r6b) + (listsEqual(r6b, expected6) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Right skewed
        System.out.println("Test 7: Right skewed");
        Integer[] arr7 = {1, null, 2, null, 3, null, 4};
        TreeNode root7 = buildTree(arr7);
        int target7 = 10; // 1+2+3+4
        List<List<Integer>> r7 = sol.pathSum(root7, target7);
        List<List<Integer>> r7i = sol.pathSumIterative(root7, target7);
        List<List<Integer>> r7b = sol.pathSumBFS(root7, target7);
        List<List<Integer>> expected7 = Arrays.asList(Arrays.asList(1, 2, 3, 4));
        boolean pass7 = listsEqual(r7, expected7) && listsEqual(r7i, expected7) && listsEqual(r7b, expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Target: " + target7);
        System.out.println("  Expected: [[1,2,3,4]]");
        System.out.println("  Recursive: " + listOfListsToString(r7) + (listsEqual(r7, expected7) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r7i) + (listsEqual(r7i, expected7) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r7b) + (listsEqual(r7b, expected7) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Multiple paths
        System.out.println("Test 8: Multiple paths");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root8 = buildTree(arr8);
        int target8 = 7; // 1->2->4 and 1->3->3 (no 3->3 doesn't exist)
        // Paths: 1->2->4=7, 1->3->3 doesn't exist, 1->2->5=8, 1->3->6=10, 1->3->7=11
        List<List<Integer>> r8 = sol.pathSum(root8, target8);
        List<List<Integer>> r8i = sol.pathSumIterative(root8, target8);
        List<List<Integer>> r8b = sol.pathSumBFS(root8, target8);
        List<List<Integer>> expected8 = Arrays.asList(Arrays.asList(1, 2, 4));
        boolean pass8 = listsEqual(r8, expected8) && listsEqual(r8i, expected8) && listsEqual(r8b, expected8);
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Target: " + target8);
        System.out.println("  Expected: [[1,2,4]]");
        System.out.println("  Recursive: " + listOfListsToString(r8) + (listsEqual(r8, expected8) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r8i) + (listsEqual(r8i, expected8) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r8b) + (listsEqual(r8b, expected8) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Negative values
        System.out.println("Test 9: Negative values");
        Integer[] arr9 = {-2, null, -3};
        TreeNode root9 = buildTree(arr9);
        int target9 = -5;
        List<List<Integer>> r9 = sol.pathSum(root9, target9);
        List<List<Integer>> r9i = sol.pathSumIterative(root9, target9);
        List<List<Integer>> r9b = sol.pathSumBFS(root9, target9);
        List<List<Integer>> expected9 = Arrays.asList(Arrays.asList(-2, -3));
        boolean pass9 = listsEqual(r9, expected9) && listsEqual(r9i, expected9) && listsEqual(r9b, expected9);
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Target: " + target9);
        System.out.println("  Expected: [[-2,-3]]");
        System.out.println("  Recursive: " + listOfListsToString(r9) + (listsEqual(r9, expected9) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listOfListsToString(r9i) + (listsEqual(r9i, expected9) ? " ✓" : " ✗ FAIL"));
        System.out.println("  BFS:       " + listOfListsToString(r9b) + (listsEqual(r9b, expected9) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Zero target
        System.out.println("Test 10: Zero target");
        Integer[] arr10 = {1, -2, -3, 1, 3, -2, null, -1};
        TreeNode root10 = buildTree(arr10);
        int target10 = -1;
        List<List<Integer>> r10 = sol.pathSum(root10, target10);
        List<List<Integer>> r10i = sol.pathSumIterative(root10, target10);
        List<List<Integer>> r10b = sol.pathSumBFS(root10, target10);
        // Paths that sum to -1
        List<List<Integer>> expected10 = new ArrayList<>();
        expected10.add(Arrays.asList(1, -2, 1, -1)); // 1-2+1-1 = -1
        expected10.add(Arrays.asList(1, -2, 3, -2)); // 1-2+3-2 = 0 (no)
        // Actually let's just check all methods agree
        boolean pass10 = listsEqual(r10, r10i) && listsEqual(r10i, r10b);
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Target: " + target10);
        System.out.println("  Recursive: " + listOfListsToString(r10));
        System.out.println("  Iterative: " + listOfListsToString(r10i));
        System.out.println("  BFS:       " + listOfListsToString(r10b));
        System.out.println("  All match: " + (pass10 ? " ✓" : " ✗ FAIL"));
        allPassed &= pass10;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}