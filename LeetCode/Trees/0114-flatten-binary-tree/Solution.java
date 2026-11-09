import java.util.*;

/**
 * LeetCode 114 - Flatten Binary Tree to Linked List
 * 
 * Dado o root de uma árvore binária, achate a árvore em uma "lista encadeada":
 * A "lista encadeada" deve usar a mesma classe TreeNode onde o ponteiro right
 * aponta para o próximo nó na lista e o ponteiro left é sempre null.
 * A "lista encadeada" deve estar na mesma ordem que uma travessia pre-order da árvore binária.
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
     * Método 1: Recursivo - Post-order Reverso (Principal)
     * Processa right, depois left, depois root. Mantém ponteiro para último nó visitado.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public void flatten(TreeNode root) {
        flattenHelper(root, new TreeNode[1]); // Use array to hold reference
    }

    private void flattenHelper(TreeNode node, TreeNode[] prev) {
        if (node == null) return;
        
        flattenHelper(node.right, prev);
        flattenHelper(node.left, prev);
        
        node.right = prev[0];
        node.left = null;
        prev[0] = node;
    }

    /**
     * Método 2: Iterativo com Stack
     * Usa stack para simular pre-order reverso (root, right, left).
     * Tempo: O(n), Espaço: O(h)
     */
    public void flattenIterative(TreeNode root) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode prev = null;

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();

            if (prev != null) {
                prev.right = curr;
                prev.left = null;
            }

            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);

            prev = curr;
        }
    }

    /**
     * Método 3: Morris Traversal (O(1) espaço)
     * Encontra predecessor in-order do nó direito e conecta.
     * Tempo: O(n), Espaço: O(1)
     */
    public void flattenMorris(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                // Encontra o predecessor (nó mais à direita da subárvore esquerda)
                TreeNode predecessor = curr.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                // Conecta predecessor ao right do curr
                predecessor.right = curr.right;
                // Move left para right
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }

    /**
     * Método 4: Recursivo com retorno do último nó
     * Retorna o último nó da lista achatada para evitar variável global.
     * Tempo: O(n), Espaço: O(h)
     */
    public void flattenRecursiveReturn(TreeNode root) {
        flattenHelperReturn(root);
    }

    private TreeNode flattenHelperReturn(TreeNode node) {
        if (node == null) return null;

        TreeNode leftLast = flattenHelperReturn(node.left);
        TreeNode rightLast = flattenHelperReturn(node.right);

        if (node.left != null) {
            leftLast.right = node.right;
            node.right = node.left;
            node.left = null;
        }

        // Retorna o último nó: rightLast > leftLast > node
        return rightLast != null ? rightLast : (leftLast != null ? leftLast : node);
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

    // Helper para converter árvore achatada em lista para verificação
    private static List<Integer> flattenedToList(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        while (root != null) {
            result.add(root.val);
            root = root.right;
        }
        return result;
    }

    // Helper para pre-order traversal
    private static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
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

    // Verifica se árvore está corretamente achatada
    private static boolean isFlattenedCorrectly(TreeNode root, List<Integer> expectedPreorder) {
        List<Integer> actual = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) return false; // left deve ser null
            actual.add(curr.val);
            curr = curr.right;
        }
        return actual.equals(expectedPreorder);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Flatten Binary Tree to Linked List Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {1, 2, 5, 3, 4, null, 6};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        sol.flatten(root1);
        boolean pass1 = isFlattenedCorrectly(root1, expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected preorder: " + listToString(expected1));
        System.out.println("  Actual flattened: " + listToString(flattenedToList(root1)));
        System.out.println("  Result: " + (pass1 ? "✓" : "✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode root2 = null;
        sol.flatten(root2);
        boolean pass2 = root2 == null;
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        List<Integer> expected3 = Arrays.asList(1);
        sol.flatten(root3);
        boolean pass3 = isFlattenedCorrectly(root3, expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(1, 2, 3, 4);
        sol.flatten(root4);
        boolean pass4 = isFlattenedCorrectly(root4, expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(1, 2, 3, 4);
        sol.flatten(root5);
        boolean pass5 = isFlattenedCorrectly(root5, expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete tree
        System.out.println("Test 6: Complete tree");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(1, 2, 4, 5, 3, 6, 7);
        sol.flatten(root6);
        boolean pass6 = isFlattenedCorrectly(root6, expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Test iterative method
        System.out.println("Test 7: Iterative method");
        Integer[] arr7 = {1, 2, 5, 3, 4, null, 6};
        TreeNode root7 = buildTree(arr7);
        List<Integer> expected7 = Arrays.asList(1, 2, 3, 4, 5, 6);
        sol.flattenIterative(root7);
        boolean pass7 = isFlattenedCorrectly(root7, expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Test Morris method
        System.out.println("Test 8: Morris method");
        Integer[] arr8 = {1, 2, 5, 3, 4, null, 6};
        TreeNode root8 = buildTree(arr8);
        List<Integer> expected8 = Arrays.asList(1, 2, 3, 4, 5, 6);
        sol.flattenMorris(root8);
        boolean pass8 = isFlattenedCorrectly(root8, expected8);
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Result: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Test recursive return method
        System.out.println("Test 9: Recursive return method");
        Integer[] arr9 = {1, 2, 5, 3, 4, null, 6};
        TreeNode root9 = buildTree(arr9);
        List<Integer> expected9 = Arrays.asList(1, 2, 3, 4, 5, 6);
        sol.flattenRecursiveReturn(root9);
        boolean pass9 = isFlattenedCorrectly(root9, expected9);
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Result: " + (pass9 ? "✓" : "✗ FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: All methods produce same result
        System.out.println("Test 10: All methods consistency");
        Integer[] arr10 = {1, 2, 5, 3, 4, null, 6};
        TreeNode root10a = buildTree(arr10);
        TreeNode root10b = buildTree(arr10);
        TreeNode root10c = buildTree(arr10);
        TreeNode root10d = buildTree(arr10);
        
        sol.flatten(root10a);
        sol.flattenIterative(root10b);
        sol.flattenMorris(root10c);
        sol.flattenRecursiveReturn(root10d);
        
        List<Integer> listA = flattenedToList(root10a);
        List<Integer> listB = flattenedToList(root10b);
        List<Integer> listC = flattenedToList(root10c);
        List<Integer> listD = flattenedToList(root10d);
        
        boolean pass10 = listA.equals(listB) && listB.equals(listC) && listC.equals(listD);
        System.out.println("  Recursive: " + listToString(listA));
        System.out.println("  Iterative: " + listToString(listB));
        System.out.println("  Morris:    " + listToString(listC));
        System.out.println("  RecReturn: " + listToString(listD));
        System.out.println("  All match: " + (pass10 ? "✓" : "✗ FAIL"));
        allPassed &= pass10;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}