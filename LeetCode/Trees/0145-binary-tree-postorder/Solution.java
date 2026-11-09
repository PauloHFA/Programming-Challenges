import java.util.*;

/**
 * LeetCode 145 - Binary Tree Postorder Traversal
 * 
 * Dado o root de uma árvore binária, retorne a travessia postorder dos valores dos seus nós.
 * Postorder: Left -> Right -> Root
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.val);
    }

    /**
     * Método 2: Iterativo com Stack (Reverse Preorder)
     * Preorder é Root->Left->Right. Reverse Preorder é Root->Right->Left.
     * Reverse de Reverse Preorder = Left->Right->Root = Postorder.
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> postorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(0, node.val); // Add to front (reverse)

            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }

        return result;
    }

    /**
     * Método 3: Iterativo com Stack e Ponteiro Anterior
     * Rastreia último nó visitado para saber quando voltar.
     * Tempo: O(n), Espaço: O(h)
     */
    public List<Integer> postorderTraversalPrev(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        TreeNode prev = null;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            // Se right child existe e não foi visitado
            if (curr.right != null && curr.right != prev) {
                curr = curr.right;
            } else {
                // Visita o nó
                result.add(curr.val);
                prev = curr;
                stack.pop();
                curr = null; // Para não re-entrar no loop left
            }
        }

        return result;
    }

    /**
     * Método 4: Morris Traversal (O(1) espaço) - Postorder
     * Mais complexo, usa threads reversas.
     * Tempo: O(n), Espaço: O(1)
     */
    public List<Integer> postorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        TreeNode curr = dummy;

        while (curr != null) {
            if (curr.left == null) {
                curr = curr.right;
            } else {
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    // Segunda visita - processa caminho reverso de curr.left até predecessor
                    addReversePath(curr.left, predecessor, result);
                    predecessor.right = null;
                    curr = curr.right;
                }
            }
        }

        return result;
    }

    private void addReversePath(TreeNode from, TreeNode to, List<Integer> result) {
        // Reverte caminho de 'from' até 'to'
        TreeNode prev = null;
        TreeNode curr = from;
        while (curr != to) {
            TreeNode next = curr.right;
            curr.right = prev;
            prev = curr;
            curr = next;
        }
        // Inclui 'to'
        to.right = prev;
        prev = to;

        // Adiciona valores e restaura
        curr = prev;
        while (curr != null) {
            result.add(curr.val);
            TreeNode next = curr.right;
            curr.right = null; // Restaura
            curr = next;
        }
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

        System.out.println("=== Binary Tree Postorder Traversal Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, null, 2, 3};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(3, 2, 1);
        List<Integer> r1 = sol.postorderTraversal(root1);
        List<Integer> r1i = sol.postorderTraversalIterative(root1);
        List<Integer> r1p = sol.postorderTraversalPrev(root1);
        List<Integer> r1m = sol.postorderTraversalMorris(root1);
        boolean pass1 = r1.equals(expected1) && r1i.equals(expected1) && r1p.equals(expected1) && r1m.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + listToString(expected1));
        System.out.println("  Recursive: " + listToString(r1) + (r1.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Iterative: " + listToString(r1i) + (r1i.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  PrevPtr:   " + listToString(r1p) + (r1p.equals(expected1) ? " ✓" : " ✗ FAIL"));
        System.out.println("  Morris:    " + listToString(r1m) + (r1m.equals(expected1) ? " ✓" : " ✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode root2 = null;
        List<Integer> expected2 = new ArrayList<>();
        List<Integer> r2 = sol.postorderTraversal(root2);
        List<Integer> r2i = sol.postorderTraversalIterative(root2);
        List<Integer> r2p = sol.postorderTraversalPrev(root2);
        List<Integer> r2m = sol.postorderTraversalMorris(root2);
        boolean pass2 = r2.equals(expected2) && r2i.equals(expected2) && r2p.equals(expected2) && r2m.equals(expected2);
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        List<Integer> expected3 = Arrays.asList(1);
        List<Integer> r3 = sol.postorderTraversal(root3);
        List<Integer> r3i = sol.postorderTraversalIterative(root3);
        List<Integer> r3p = sol.postorderTraversalPrev(root3);
        List<Integer> r3m = sol.postorderTraversalMorris(root3);
        boolean pass3 = r3.equals(expected3) && r3i.equals(expected3) && r3p.equals(expected3) && r3m.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(4, 3, 2, 1);
        List<Integer> r4 = sol.postorderTraversal(root4);
        List<Integer> r4i = sol.postorderTraversalIterative(root4);
        List<Integer> r4p = sol.postorderTraversalPrev(root4);
        List<Integer> r4m = sol.postorderTraversalMorris(root4);
        boolean pass4 = r4.equals(expected4) && r4i.equals(expected4) && r4p.equals(expected4) && r4m.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(4, 3, 2, 1);
        List<Integer> r5 = sol.postorderTraversal(root5);
        List<Integer> r5i = sol.postorderTraversalIterative(root5);
        List<Integer> r5p = sol.postorderTraversalPrev(root5);
        List<Integer> r5m = sol.postorderTraversalMorris(root5);
        boolean pass5 = r5.equals(expected5) && r5i.equals(expected5) && r5p.equals(expected5) && r5m.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete tree
        System.out.println("Test 6: Complete tree");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(4, 5, 2, 6, 7, 3, 1);
        List<Integer> r6 = sol.postorderTraversal(root6);
        List<Integer> r6i = sol.postorderTraversalIterative(root6);
        List<Integer> r6p = sol.postorderTraversalPrev(root6);
        List<Integer> r6m = sol.postorderTraversalMorris(root6);
        boolean pass6 = r6.equals(expected6) && r6i.equals(expected6) && r6p.equals(expected6) && r6m.equals(expected6);
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

        List<Integer> r7a = sol.postorderTraversal(root7a);
        List<Integer> r7b = sol.postorderTraversalIterative(root7b);
        List<Integer> r7c = sol.postorderTraversalPrev(root7c);
        List<Integer> r7d = sol.postorderTraversalMorris(root7d);

        boolean pass7 = r7a.equals(r7b) && r7b.equals(r7c) && r7c.equals(r7d);
        System.out.println("  Recursive: " + listToString(r7a));
        System.out.println("  Iterative: " + listToString(r7b));
        System.out.println("  PrevPtr:   " + listToString(r7c));
        System.out.println("  Morris:    " + listToString(r7d));
        System.out.println("  All match: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}