import java.util.*;

/**
 * LeetCode 173 - Binary Search Tree Iterator
 * 
 * Implemente a classe BSTIterator que representa um iterador sobre a travessia in-order
 * de uma árvore de busca binária (BST):
 * - BSTIterator(TreeNode root) Inicializa o iterador com o root da BST.
 * - boolean hasNext() Retorna true se existe um número à direita do ponteiro atual.
 * - int next() Move o ponteiro para a direita e retorna o número.
 * O ponteiro inicial está antes do menor elemento.
 * Assume-se que next() será chamado apenas quando hasNext() for true.
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
     * Método 1: Stack Controlado (Principal)
     * Mantém stack com caminho para o próximo menor elemento.
     * next() e hasNext() em O(1) amortizado.
     * Tempo: O(1) amortizado, Espaço: O(h)
     */
    static class BSTIterator {
        private Stack<TreeNode> stack = new Stack<>();

        public BSTIterator(TreeNode root) {
            pushAllLeft(root);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            TreeNode node = stack.pop();
            pushAllLeft(node.right);
            return node.val;
        }

        private void pushAllLeft(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    /**
     * Método 2: Lista Pré-computada
     * Faz in-order traversal completo no construtor e guarda em lista.
     * next() e hasNext() em O(1) real.
     * Tempo construtor: O(n), Espaço: O(n)
     */
    static class BSTIteratorList {
        private List<Integer> values = new ArrayList<>();
        private int index = 0;

        public BSTIteratorList(TreeNode root) {
            inorder(root);
        }

        private void inorder(TreeNode node) {
            if (node == null) return;
            inorder(node.left);
            values.add(node.val);
            inorder(node.right);
        }

        public boolean hasNext() {
            return index < values.size();
        }

        public int next() {
            return values.get(index++);
        }
    }

    /**
     * Método 3: Morris Traversal (O(1) espaço)
     * Usa Morris traversal para iterar sem stack extra.
     * Complexo para implementar como iterador com estado.
     * Não implementado completamente aqui.
     */

    // Helper para construir BST a partir de array (level order com null)
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

    // Helper para coletar todos os elementos via iterador
    private static List<Integer> collectAll(BSTIterator iterator) {
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    private static List<Integer> collectAllList(BSTIteratorList iterator) {
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public static void main(String[] args) {
        boolean allPassed = true;

        System.out.println("=== Binary Search Tree Iterator Tests ===\n");

        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        Integer[] arr1 = {7, 3, 15, null, null, 9, 20};
        TreeNode root1 = buildTree(arr1);
        List<Integer> expected1 = Arrays.asList(3, 7, 9, 15, 20);
        
        BSTIterator iter1 = new BSTIterator(root1);
        List<Integer> result1 = collectAll(iter1);
        boolean pass1 = result1.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + expected1);
        System.out.println("  Actual:   " + result1);
        System.out.println("  Result: " + (pass1 ? "✓" : "✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode root2 = null;
        BSTIterator iter2 = new BSTIterator(root2);
        boolean pass2 = !iter2.hasNext();
        System.out.println("  Tree: null");
        System.out.println("  hasNext: " + iter2.hasNext());
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        TreeNode root3 = buildTree(arr3);
        List<Integer> expected3 = Arrays.asList(1);
        BSTIterator iter3 = new BSTIterator(root3);
        List<Integer> result3 = collectAll(iter3);
        boolean pass3 = result3.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {4, 3, null, 2, null, 1};
        TreeNode root4 = buildTree(arr4);
        List<Integer> expected4 = Arrays.asList(1, 2, 3, 4);
        BSTIterator iter4 = new BSTIterator(root4);
        List<Integer> result4 = collectAll(iter4);
        boolean pass4 = result4.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(arr5);
        List<Integer> expected5 = Arrays.asList(1, 2, 3, 4);
        BSTIterator iter5 = new BSTIterator(root5);
        List<Integer> result5 = collectAll(iter5);
        boolean pass5 = result5.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Complete BST
        System.out.println("Test 6: Complete BST");
        Integer[] arr6 = {4, 2, 6, 1, 3, 5, 7};
        TreeNode root6 = buildTree(arr6);
        List<Integer> expected6 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        BSTIterator iter6 = new BSTIterator(root6);
        List<Integer> result6 = collectAll(iter6);
        boolean pass6 = result6.equals(expected6);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Test List method
        System.out.println("Test 7: List method");
        Integer[] arr7 = {7, 3, 15, null, null, 9, 20};
        TreeNode root7 = buildTree(arr7);
        List<Integer> expected7 = Arrays.asList(3, 7, 9, 15, 20);
        BSTIteratorList iter7 = new BSTIteratorList(root7);
        List<Integer> result7 = collectAllList(iter7);
        boolean pass7 = result7.equals(expected7);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Step by step iteration
        System.out.println("Test 8: Step by step iteration");
        Integer[] arr8 = {7, 3, 15, null, null, 9, 20};
        TreeNode root8 = buildTree(arr8);
        BSTIterator iter8 = new BSTIterator(root8);
        
        boolean pass8 = true;
        pass8 &= iter8.hasNext() && iter8.next() == 3;
        pass8 &= iter8.hasNext() && iter8.next() == 7;
        pass8 &= iter8.hasNext() && iter8.next() == 9;
        pass8 &= iter8.hasNext() && iter8.next() == 15;
        pass8 &= iter8.hasNext() && iter8.next() == 20;
        pass8 &= !iter8.hasNext();
        
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Step by step: 3, 7, 9, 15, 20");
        System.out.println("  Result: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Both methods consistency
        System.out.println("Test 9: Both methods consistency");
        Integer[] arr9 = {7, 3, 15, null, null, 9, 20};
        TreeNode root9a = buildTree(arr9);
        TreeNode root9b = buildTree(arr9);
        
        BSTIterator iter9a = new BSTIterator(root9a);
        BSTIteratorList iter9b = new BSTIteratorList(root9b);
        
        List<Integer> r9a = collectAll(iter9a);
        List<Integer> r9b = collectAllList(iter9b);
        
        boolean pass9 = r9a.equals(r9b);
        System.out.println("  Stack: " + r9a);
        System.out.println("  List:  " + r9b);
        System.out.println("  All match: " + (pass9 ? "✓" : "✗ FAIL"));
        allPassed &= pass9;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}