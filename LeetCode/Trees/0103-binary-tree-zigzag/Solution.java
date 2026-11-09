import java.util.*;

/**
 * LeetCode 103 - Binary Tree Zigzag Level Order Traversal
 * 
 * Dado o root de uma árvore binária, retorne a travessia em ordem de nível zigzag
 * dos valores de seus nós. (ou seja, da esquerda para a direita, depois da direita
 * para a esquerda no próximo nível e alterna entre eles).
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
     * Método 1: BFS com Queue + Flag de direção (Principal)
     * Usa uma flag booleana para alternar a direção a cada nível.
     * Tempo: O(n), Espaço: O(w) onde w = largura máxima
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // Adiciona no início ou fim dependendo da direção
                if (leftToRight) {
                    level.add(node.val);
                } else {
                    level.add(0, node.val); // Adiciona no início para inverter
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight; // Alterna direção
        }

        return result;
    }

    /**
     * Método 2: BFS com Deque (Double-ended Queue)
     * Usa Deque para adicionar no início ou fim eficientemente.
     * Tempo: O(n), Espaço: O(w)
     */
    public List<List<Integer>> zigzagLevelOrderDeque(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        boolean leftToRight = true;

        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node;
                if (leftToRight) {
                    node = deque.pollFirst();
                    if (node.left != null) deque.offerLast(node.left);
                    if (node.right != null) deque.offerLast(node.right);
                } else {
                    node = deque.pollLast();
                    if (node.right != null) deque.offerFirst(node.right);
                    if (node.left != null) deque.offerFirst(node.left);
                }
                level.add(node.val);
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }

    /**
     * Método 3: Duas Stacks
     * Usa duas stacks para alternar a ordem de processamento.
     * Tempo: O(n), Espaço: O(w)
     */
    public List<List<Integer>> zigzagLevelOrderTwoStacks(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> currentLevel = new Stack<>();
        Stack<TreeNode> nextLevel = new Stack<>();
        currentLevel.push(root);
        boolean leftToRight = true;

        while (!currentLevel.isEmpty()) {
            List<Integer> level = new ArrayList<>();

            while (!currentLevel.isEmpty()) {
                TreeNode node = currentLevel.pop();
                level.add(node.val);

                if (leftToRight) {
                    if (node.left != null) nextLevel.push(node.left);
                    if (node.right != null) nextLevel.push(node.right);
                } else {
                    if (node.right != null) nextLevel.push(node.right);
                    if (node.left != null) nextLevel.push(node.left);
                }
            }

            result.add(level);
            leftToRight = !leftToRight;
            Stack<TreeNode> temp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = temp;
        }

        return result;
    }

    /**
     * Método 4: DFS Recursivo com nível
     * Passa o nível atual e adiciona no início ou fim da lista do nível.
     * Tempo: O(n), Espaço: O(h) stack + O(w) resultado
     */
    public List<List<Integer>> zigzagLevelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;

        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        if (level % 2 == 0) {
            result.get(level).add(node.val); // left to right
        } else {
            result.get(level).add(0, node.val); // right to left
        }

        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
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

        System.out.println("=== Binary Tree Zigzag Level Order Traversal Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(arr1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(3),
            Arrays.asList(20, 9),
            Arrays.asList(15, 7)
        );
        List<List<Integer>> r1 = sol.zigzagLevelOrder(root1);
        List<List<Integer>> r1d = sol.zigzagLevelOrderDeque(root1);
        List<List<Integer>> r1s = sol.zigzagLevelOrderTwoStacks(root1);
        List<List<Integer>> r1dfs = sol.zigzagLevelOrderDFS(root1);
        boolean pass1 = r1.equals(expected1) && r1d.equals(expected1) && r1s.equals(expected1) && r1dfs.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  Queue:    " + listToString(r1) + (r1.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r1d) + (r1d.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r1s) + (r1s.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r1dfs) + (r1dfs.equals(expected1) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass1 ? "?" : "? FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Single node
        System.out.println("Test 2: Single node");
        Integer[] arr2 = {1};
        TreeNode root2 = buildTree(arr2);
        List<List<Integer>> expected2 = Arrays.asList(Arrays.asList(1));
        List<List<Integer>> r2 = sol.zigzagLevelOrder(root2);
        List<List<Integer>> r2d = sol.zigzagLevelOrderDeque(root2);
        List<List<Integer>> r2s = sol.zigzagLevelOrderTwoStacks(root2);
        List<List<Integer>> r2dfs = sol.zigzagLevelOrderDFS(root2);
        boolean pass2 = r2.equals(expected2) && r2d.equals(expected2) && r2s.equals(expected2) && r2dfs.equals(expected2);
        System.out.println("  Tree: " + arrayToString(arr2));
        System.out.println("  Expected: " + listToString(expected2));
        System.out.println("  Queue:    " + listToString(r2) + (r2.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r2d) + (r2d.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r2s) + (r2s.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r2dfs) + (r2dfs.equals(expected2) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass2 ? "?" : "? FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Empty
        System.out.println("Test 3: Empty tree");
        TreeNode root3 = null;
        List<List<Integer>> expected3 = new ArrayList<>();
        List<List<Integer>> r3 = sol.zigzagLevelOrder(root3);
        List<List<Integer>> r3d = sol.zigzagLevelOrderDeque(root3);
        List<List<Integer>> r3s = sol.zigzagLevelOrderTwoStacks(root3);
        List<List<Integer>> r3dfs = sol.zigzagLevelOrderDFS(root3);
        boolean pass3 = r3.equals(expected3) && r3d.equals(expected3) && r3s.equals(expected3) && r3dfs.equals(expected3);
        System.out.println("  Expected: " + listToString(expected3));
        System.out.println("  Queue:    " + listToString(r3) + (r3.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r3d) + (r3d.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r3s) + (r3s.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r3dfs) + (r3dfs.equals(expected3) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass3 ? "?" : "? FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(arr4);
        List<List<Integer>> expected4 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3),
            Arrays.asList(4)
        );
        List<List<Integer>> r4 = sol.zigzagLevelOrder(root4);
        List<List<Integer>> r4d = sol.zigzagLevelOrderDeque(root4);
        List<List<Integer>> r4s = sol.zigzagLevelOrderTwoStacks(root4);
        List<List<Integer>> r4dfs = sol.zigzagLevelOrderDFS(root4);
        boolean pass4 = r4.equals(expected4) && r4d.equals(expected4) && r4s.equals(expected4) && r4dfs.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Expected: " + listToString(expected4));
        System.out.println("  Queue:    " + listToString(r4) + (r4.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r4d) + (r4d.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r4s) + (r4s.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r4dfs) + (r4dfs.equals(expected4) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass4 ? "?" : "? FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<List<Integer>> expected5 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3),
            Arrays.asList(4)
        );
        List<List<Integer>> r5 = sol.zigzagLevelOrder(root5);
        List<List<Integer>> r5d = sol.zigzagLevelOrderDeque(root5);
        List<List<Integer>> r5s = sol.zigzagLevelOrderTwoStacks(root5);
        List<List<Integer>> r5dfs = sol.zigzagLevelOrderDFS(root5);
        boolean pass5 = r5.equals(expected5) && r5d.equals(expected5) && r5s.equals(expected5) && r5dfs.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Expected: " + listToString(expected5));
        System.out.println("  Queue:    " + listToString(r5) + (r5.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r5d) + (r5d.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r5s) + (r5s.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r5dfs) + (r5dfs.equals(expected5) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass5 ? "?" : "? FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete tree
        System.out.println("Test 6: Complete tree");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = buildTree(arr6);
        List<List<Integer>> expected6 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(3, 2),
            Arrays.asList(4, 5, 6, 7)
        );
        List<List<Integer>> r6 = sol.zigzagLevelOrder(root6);
        List<List<Integer>> r6d = sol.zigzagLevelOrderDeque(root6);
        List<List<Integer>> r6s = sol.zigzagLevelOrderTwoStacks(root6);
        List<List<Integer>> r6dfs = sol.zigzagLevelOrderDFS(root6);
        boolean pass6 = r6.equals(expected6) && r6d.equals(expected6) && r6s.equals(expected6) && r6dfs.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Expected: " + listToString(expected6));
        System.out.println("  Queue:    " + listToString(r6) + (r6.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r6d) + (r6d.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r6s) + (r6s.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r6dfs) + (r6dfs.equals(expected6) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass6 ? "?" : "? FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Perfect tree height 3
        System.out.println("Test 7: Perfect tree height 3");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root7 = buildTree(arr7);
        List<List<Integer>> expected7 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(3, 2),
            Arrays.asList(4, 5, 6, 7),
            Arrays.asList(15, 14, 13, 12, 11, 10, 9, 8)
        );
        List<List<Integer>> r7 = sol.zigzagLevelOrder(root7);
        List<List<Integer>> r7d = sol.zigzagLevelOrderDeque(root7);
        List<List<Integer>> r7s = sol.zigzagLevelOrderTwoStacks(root7);
        List<List<Integer>> r7dfs = sol.zigzagLevelOrderDFS(root7);
        boolean pass7 = r7.equals(expected7) && r7d.equals(expected7) && r7s.equals(expected7) && r7dfs.equals(expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Expected: " + listToString(expected7));
        System.out.println("  Queue:    " + listToString(r7) + (r7.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r7d) + (r7d.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r7s) + (r7s.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r7dfs) + (r7dfs.equals(expected7) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass7 ? "?" : "? FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Mixed tree
        System.out.println("Test 8: Mixed tree");
        Integer[] arr8 = {1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8};
        TreeNode root8 = buildTree(arr8);
        List<List<Integer>> expected8 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(3, 2),
            Arrays.asList(4, 5, 6),
            Arrays.asList(8, 7)
        );
        List<List<Integer>> r8 = sol.zigzagLevelOrder(root8);
        List<List<Integer>> r8d = sol.zigzagLevelOrderDeque(root8);
        List<List<Integer>> r8s = sol.zigzagLevelOrderTwoStacks(root8);
        List<List<Integer>> r8dfs = sol.zigzagLevelOrderDFS(root8);
        boolean pass8 = r8.equals(expected8) && r8d.equals(expected8) && r8s.equals(expected8) && r8dfs.equals(expected8);
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Expected: " + listToString(expected8));
        System.out.println("  Queue:    " + listToString(r8) + (r8.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r8d) + (r8d.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r8s) + (r8s.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r8dfs) + (r8dfs.equals(expected8) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass8 ? "?" : "? FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Two nodes left
        System.out.println("Test 9: Two nodes left");
        Integer[] arr9 = {1, 2};
        TreeNode root9 = buildTree(arr9);
        List<List<Integer>> expected9 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2)
        );
        List<List<Integer>> r9 = sol.zigzagLevelOrder(root9);
        List<List<Integer>> r9d = sol.zigzagLevelOrderDeque(root9);
        List<List<Integer>> r9s = sol.zigzagLevelOrderTwoStacks(root9);
        List<List<Integer>> r9dfs = sol.zigzagLevelOrderDFS(root9);
        boolean pass9 = r9.equals(expected9) && r9d.equals(expected9) && r9s.equals(expected9) && r9dfs.equals(expected9);
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Expected: " + listToString(expected9));
        System.out.println("  Queue:    " + listToString(r9) + (r9.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r9d) + (r9d.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r9s) + (r9s.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r9dfs) + (r9dfs.equals(expected9) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass9 ? "?" : "? FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Two nodes right
        System.out.println("Test 10: Two nodes right");
        Integer[] arr10 = {1, null, 2};
        TreeNode root10 = buildTree(arr10);
        List<List<Integer>> expected10 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2)
        );
        List<List<Integer>> r10 = sol.zigzagLevelOrder(root10);
        List<List<Integer>> r10d = sol.zigzagLevelOrderDeque(root10);
        List<List<Integer>> r10s = sol.zigzagLevelOrderTwoStacks(root10);
        List<List<Integer>> r10dfs = sol.zigzagLevelOrderDFS(root10);
        boolean pass10 = r10.equals(expected10) && r10d.equals(expected10) && r10s.equals(expected10) && r10dfs.equals(expected10);
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Expected: " + listToString(expected10));
        System.out.println("  Queue:    " + listToString(r10) + (r10.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r10d) + (r10d.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r10s) + (r10s.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r10dfs) + (r10dfs.equals(expected10) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass10 ? "?" : "? FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Negative values
        System.out.println("Test 11: Negative values");
        Integer[] arr11 = {-1, -2, -3, -4, -5};
        TreeNode root11 = buildTree(arr11);
        List<List<Integer>> expected11 = Arrays.asList(
            Arrays.asList(-1),
            Arrays.asList(-3, -2),
            Arrays.asList(-4, -5)
        );
        List<List<Integer>> r11 = sol.zigzagLevelOrder(root11);
        List<List<Integer>> r11d = sol.zigzagLevelOrderDeque(root11);
        List<List<Integer>> r11s = sol.zigzagLevelOrderTwoStacks(root11);
        List<List<Integer>> r11dfs = sol.zigzagLevelOrderDFS(root11);
        boolean pass11 = r11.equals(expected11) && r11d.equals(expected11) && r11s.equals(expected11) && r11dfs.equals(expected11);
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Expected: " + listToString(expected11));
        System.out.println("  Queue:    " + listToString(r11) + (r11.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r11d) + (r11d.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r11s) + (r11s.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r11dfs) + (r11dfs.equals(expected11) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass11 ? "?" : "? FAIL"));
        allPassed &= pass11;
        System.out.println();

        // Test 12: Three levels with zigzag
        System.out.println("Test 12: Three levels zigzag");
        Integer[] arr12 = {1, 2, 3, 4, 5, null, 7};
        TreeNode root12 = buildTree(arr12);
        List<List<Integer>> expected12 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(3, 2),
            Arrays.asList(4, 5, 7)
        );
        List<List<Integer>> r12 = sol.zigzagLevelOrder(root12);
        List<List<Integer>> r12d = sol.zigzagLevelOrderDeque(root12);
        List<List<Integer>> r12s = sol.zigzagLevelOrderTwoStacks(root12);
        List<List<Integer>> r12dfs = sol.zigzagLevelOrderDFS(root12);
        boolean pass12 = r12.equals(expected12) && r12d.equals(expected12) && r12s.equals(expected12) && r12dfs.equals(expected12);
        System.out.println("  Tree: " + arrayToString(arr12));
        System.out.println("  Expected: " + listToString(expected12));
        System.out.println("  Queue:    " + listToString(r12) + (r12.equals(expected12) ? " ?" : " ? FAIL"));
        System.out.println("  Deque:    " + listToString(r12d) + (r12d.equals(expected12) ? " ?" : " ? FAIL"));
        System.out.println("  2Stacks:  " + listToString(r12s) + (r12s.equals(expected12) ? " ?" : " ? FAIL"));
        System.out.println("  DFS:      " + listToString(r12dfs) + (r12dfs.equals(expected12) ? " ?" : " ? FAIL"));
        System.out.println("  All match: " + (pass12 ? "?" : "? FAIL"));
        allPassed &= pass12;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ?" : "SOME TESTS FAILED ?") + " ===");
    }
}