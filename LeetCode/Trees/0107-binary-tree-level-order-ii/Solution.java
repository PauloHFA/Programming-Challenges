import java.util.*;

/**
 * LeetCode 107 - Binary Tree Level Order Traversal II
 * 
 * Dado o root de uma árvore binária, retorne a travessia em ordem de nível
 * dos valores de seus nós de baixo para cima (bottom-up). (ou seja, da esquerda
 * para a direita, nível por nível, da folha para a raiz).
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
     * Método 1: BFS com Queue + Reverse (Principal)
     * Faz BFS normal (top-down) e inverte o resultado no final.
     * Tempo: O(n), Espaço: O(w) queue + O(n) resultado
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
        }

        // Inverte para bottom-up
        Collections.reverse(result);
        return result;
    }

    /**
     * Método 2: BFS com Deque (adiciona no início)
     * Usa Deque para adicionar cada nível no início, evitando reverse final.
     * Tempo: O(n), Espaço: O(w) queue + O(n) resultado
     */
    public List<List<Integer>> levelOrderBottomDeque(TreeNode root) {
        Deque<List<Integer>> deque = new LinkedList<>();
        if (root == null) return new ArrayList<>(deque);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            deque.addFirst(level); // Adiciona no início
        }

        return new ArrayList<>(deque);
    }

    /**
     * Método 3: DFS Recursivo com nível
     * Passa nível atual, garante que lista do nível existe, adiciona valor.
     * No final, inverte o resultado.
     * Tempo: O(n), Espaço: O(h) stack + O(n) resultado
     */
    public List<List<Integer>> levelOrderBottomDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        Collections.reverse(result);
        return result;
    }

    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;

        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        result.get(level).add(node.val);
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }

    /**
     * Método 4: Duas Queues
     * Mantém queue atual e próxima, adiciona nível no início do resultado.
     * Tempo: O(n), Espaço: O(w)
     */
    public List<List<Integer>> levelOrderBottomTwoQueues(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> current = new LinkedList<>();
        Queue<TreeNode> next = new LinkedList<>();
        current.offer(root);

        while (!current.isEmpty()) {
            List<Integer> level = new ArrayList<>();

            while (!current.isEmpty()) {
                TreeNode node = current.poll();
                level.add(node.val);

                if (node.left != null) next.offer(node.left);
                if (node.right != null) next.offer(node.right);
            }

            result.add(0, level); // Adiciona no início
            Queue<TreeNode> temp = current;
            current = next;
            next = temp;
        }

        return result;
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

    // Helper para converter lista de listas para string
    private static String listToString(List<List<Integer>> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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

        System.out.println("=== Binary Tree Level Order Traversal II Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(arr1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(15, 7),
            Arrays.asList(9, 20),
            Arrays.asList(3)
        );
        List<List<Integer>> r1 = sol.levelOrderBottom(root1);
        List<List<Integer>> r1d = sol.levelOrderBottomDeque(root1);
        List<List<Integer>> r1dfs = sol.levelOrderBottomDFS(root1);
        List<List<Integer>> r1tq = sol.levelOrderBottomTwoQueues(root1);
        boolean pass1 = r1.equals(expected1) && r1d.equals(expected1) && r1dfs.equals(expected1) && r1tq.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  Queue+Rev: " + listToString(r1) + (r1.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r1d) + (r1d.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r1dfs) + (r1dfs.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r1tq) + (r1tq.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Single node
        System.out.println("Test 2: Single node");
        Integer[] arr2 = {1};
        TreeNode root2 = buildTree(arr2);
        List<List<Integer>> expected2 = Arrays.asList(Arrays.asList(1));
        List<List<Integer>> r2 = sol.levelOrderBottom(root2);
        List<List<Integer>> r2d = sol.levelOrderBottomDeque(root2);
        List<List<Integer>> r2dfs = sol.levelOrderBottomDFS(root2);
        List<List<Integer>> r2tq = sol.levelOrderBottomTwoQueues(root2);
        boolean pass2 = r2.equals(expected2) && r2d.equals(expected2) && r2dfs.equals(expected2) && r2tq.equals(expected2);
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + listToString(expected2));
        System.out.println("  Queue+Rev: " + listToString(r2) + (r2.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r2d) + (r2d.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r2dfs) + (r2dfs.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r2tq) + (r2tq.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        List<List<Integer>> expected3 = new ArrayList<>();
        List<List<Integer>> r3 = sol.levelOrderBottom(root3);
        List<List<Integer>> r3d = sol.levelOrderBottomDeque(root3);
        List<List<Integer>> r3dfs = sol.levelOrderBottomDFS(root3);
        List<List<Integer>> r3tq = sol.levelOrderBottomTwoQueues(root3);
        boolean pass3 = r3.equals(expected3) && r3d.equals(expected3) && r3dfs.equals(expected3) && r3tq.equals(expected3);
        System.out.println("  Expected: " + listToString(expected3));
        System.out.println("  Queue+Rev: " + listToString(r3) + (r3.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r3d) + (r3d.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r3dfs) + (r3dfs.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r3tq) + (r3tq.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(arr4);
        List<List<Integer>> expected4 = Arrays.asList(
            Arrays.asList(4),
            Arrays.asList(3),
            Arrays.asList(2),
            Arrays.asList(1)
        );
        List<List<Integer>> r4 = sol.levelOrderBottom(root4);
        List<List<Integer>> r4d = sol.levelOrderBottomDeque(root4);
        List<List<Integer>> r4dfs = sol.levelOrderBottomDFS(root4);
        List<List<Integer>> r4tq = sol.levelOrderBottomTwoQueues(root4);
        boolean pass4 = r4.equals(expected4) && r4d.equals(expected4) && r4dfs.equals(expected4) && r4tq.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + listToString(expected4));
        System.out.println("  Queue+Rev: " + listToString(r4) + (r4.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r4d) + (r4d.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r4dfs) + (r4dfs.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r4tq) + (r4tq.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<List<Integer>> expected5 = Arrays.asList(
            Arrays.asList(4),
            Arrays.asList(3),
            Arrays.asList(2),
            Arrays.asList(1)
        );
        List<List<Integer>> r5 = sol.levelOrderBottom(root5);
        List<List<Integer>> r5d = sol.levelOrderBottomDeque(root5);
        List<List<Integer>> r5dfs = sol.levelOrderBottomDFS(root5);
        List<List<Integer>> r5tq = sol.levelOrderBottomTwoQueues(root5);
        boolean pass5 = r5.equals(expected5) && r5d.equals(expected5) && r5dfs.equals(expected5) && r5tq.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + listToString(expected5));
        System.out.println("  Queue+Rev: " + listToString(r5) + (r5.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r5d) + (r5d.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r5dfs) + (r5dfs.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r5tq) + (r5tq.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete tree
        System.out.println("Test 6: Complete tree");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = buildTree(arr6);
        List<List<Integer>> expected6 = Arrays.asList(
            Arrays.asList(4, 5, 6, 7),
            Arrays.asList(2, 3),
            Arrays.asList(1)
        );
        List<List<Integer>> r6 = sol.levelOrderBottom(root6);
        List<List<Integer>> r6d = sol.levelOrderBottomDeque(root6);
        List<List<Integer>> r6dfs = sol.levelOrderBottomDFS(root6);
        List<List<Integer>> r6tq = sol.levelOrderBottomTwoQueues(root6);
        boolean pass6 = r6.equals(expected6) && r6d.equals(expected6) && r6dfs.equals(expected6) && r6tq.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + listToString(expected6));
        System.out.println("  Queue+Rev: " + listToString(r6) + (r6.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r6d) + (r6d.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r6dfs) + (r6dfs.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r6tq) + (r6tq.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Perfect tree height 3
        System.out.println("Test 7: Perfect tree height 3");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root7 = buildTree(arr7);
        List<List<Integer>> expected7 = Arrays.asList(
            Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15),
            Arrays.asList(4, 5, 6, 7),
            Arrays.asList(2, 3),
            Arrays.asList(1)
        );
        List<List<Integer>> r7 = sol.levelOrderBottom(root7);
        List<List<Integer>> r7d = sol.levelOrderBottomDeque(root7);
        List<List<Integer>> r7dfs = sol.levelOrderBottomDFS(root7);
        List<List<Integer>> r7tq = sol.levelOrderBottomTwoQueues(root7);
        boolean pass7 = r7.equals(expected7) && r7d.equals(expected7) && r7dfs.equals(expected7) && r7tq.equals(expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + listToString(expected7));
        System.out.println("  Queue+Rev: " + listToString(r7) + (r7.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r7d) + (r7d.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r7dfs) + (r7dfs.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r7tq) + (r7tq.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Mixed tree
        System.out.println("Test 8: Mixed tree");
        Integer[] arr8 = {1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8};
        TreeNode root8 = buildTree(arr8);
        List<List<Integer>> expected8 = Arrays.asList(
            Arrays.asList(7, 8),
            Arrays.asList(4, 5, 6),
            Arrays.asList(2, 3),
            Arrays.asList(1)
        );
        List<List<Integer>> r8 = sol.levelOrderBottom(root8);
        List<List<Integer>> r8d = sol.levelOrderBottomDeque(root8);
        List<List<Integer>> r8dfs = sol.levelOrderBottomDFS(root8);
        List<List<Integer>> r8tq = sol.levelOrderBottomTwoQueues(root8);
        boolean pass8 = r8.equals(expected8) && r8d.equals(expected8) && r8dfs.equals(expected8) && r8tq.equals(expected8);
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Expected: " + listToString(expected8));
        System.out.println("  Queue+Rev: " + listToString(r8) + (r8.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r8d) + (r8d.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r8dfs) + (r8dfs.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r8tq) + (r8tq.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Two nodes left
        System.out.println("Test 9: Two nodes left");
        Integer[] arr9 = {1, 2};
        TreeNode root9 = buildTree(arr9);
        List<List<Integer>> expected9 = Arrays.asList(
            Arrays.asList(2),
            Arrays.asList(1)
        );
        List<List<Integer>> r9 = sol.levelOrderBottom(root9);
        List<List<Integer>> r9d = sol.levelOrderBottomDeque(root9);
        List<List<Integer>> r9dfs = sol.levelOrderBottomDFS(root9);
        List<List<Integer>> r9tq = sol.levelOrderBottomTwoQueues(root9);
        boolean pass9 = r9.equals(expected9) && r9d.equals(expected9) && r9dfs.equals(expected9) && r9tq.equals(expected9);
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Expected: " + listToString(expected9));
        System.out.println("  Queue+Rev: " + listToString(r9) + (r9.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r9d) + (r9d.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r9dfs) + (r9dfs.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r9tq) + (r9tq.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes right
        System.out.println("Test 10: Two nodes right");
        Integer[] arr10 = {1, null, 2};
        TreeNode root10 = buildTree(arr10);
        List<List<Integer>> expected10 = Arrays.asList(
            Arrays.asList(2),
            Arrays.asList(1)
        );
        List<List<Integer>> r10 = sol.levelOrderBottom(root10);
        List<List<Integer>> r10d = sol.levelOrderBottomDeque(root10);
        List<List<Integer>> r10dfs = sol.levelOrderBottomDFS(root10);
        List<List<Integer>> r10tq = sol.levelOrderBottomTwoQueues(root10);
        boolean pass10 = r10.equals(expected10) && r10d.equals(expected10) && r10dfs.equals(expected10) && r10tq.equals(expected10);
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Expected: " + listToString(expected10));
        System.out.println("  Queue+Rev: " + listToString(r10) + (r10.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r10d) + (r10d.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r10dfs) + (r10dfs.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r10tq) + (r10tq.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Negative values
        System.out.println("Test 11: Negative values");
        Integer[] arr11 = {-1, -2, -3, -4, -5};
        TreeNode root11 = buildTree(arr11);
        List<List<Integer>> expected11 = Arrays.asList(
            Arrays.asList(-4, -5),
            Arrays.asList(-2, -3),
            Arrays.asList(-1)
        );
        List<List<Integer>> r11 = sol.levelOrderBottom(root11);
        List<List<Integer>> r11d = sol.levelOrderBottomDeque(root11);
        List<List<Integer>> r11dfs = sol.levelOrderBottomDFS(root11);
        List<List<Integer>> r11tq = sol.levelOrderBottomTwoQueues(root11);
        boolean pass11 = r11.equals(expected11) && r11d.equals(expected11) && r11dfs.equals(expected11) && r11tq.equals(expected11);
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Expected: " + listToString(expected11));
        System.out.println("  Queue+Rev: " + listToString(r11) + (r11.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:     " + listToString(r11d) + (r11d.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:       " + listToString(r11dfs) + (r11dfs.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  2Queues:   " + listToString(r11tq) + (r11tq.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}