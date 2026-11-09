import java.util.*;

/**
 * LeetCode 116 - Populating Next Right Pointers in Each Node
 * 
 * Você recebe uma árvore binária perfeita onde todas as folhas estão no mesmo nível,
 * e cada pai tem dois filhos. A árvore binária tem a seguinte definição:
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * Preencha cada ponteiro next para apontar para o próximo nó à direita.
 * Se não houver próximo nó à direita, o ponteiro next deve ser NULL.
 * Inicialmente, todos os ponteiros next são NULL.
 */
public class Solution {

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
        
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * Método 1: BFS Level Order (Principal)
     * Usa queue para processar nível a nível, conectando next pointers.
     * Tempo: O(n), Espaço: O(w) onde w é largura máxima
     */
    public Node connect(Node root) {
        if (root == null) return null;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            Node prev = null;

            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();

                if (prev != null) {
                    prev.next = curr;
                }
                prev = curr;

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            // Último nó do nível já tem next = null por padrão
        }

        return root;
    }

    /**
     * Método 2: O(1) Espaço - Usando next pointers já estabelecidos
     * Como é árvore perfeita, podemos usar next do nível pai para conectar filhos.
     * Tempo: O(n), Espaço: O(1)
     */
    public Node connectO1Space(Node root) {
        if (root == null) return null;

        Node leftmost = root;

        // Enquanto houver nível abaixo
        while (leftmost.left != null) {
            Node head = leftmost;

            while (head != null) {
                // Conecta left -> right
                head.left.next = head.right;

                // Conecta right -> next.left (se next existe)
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                head = head.next;
            }

            // Move para próximo nível
            leftmost = leftmost.left;
        }

        return root;
    }

    /**
     * Método 3: DFS Recursivo
     * Conecta filhos recursivamente.
     * Tempo: O(n), Espaço: O(h) stack
     */
    public Node connectDFS(Node root) {
        if (root == null) return null;
        connectChildren(root.left, root.right);
        return root;
    }

    private void connectChildren(Node node1, Node node2) {
        if (node1 == null || node2 == null) return;

        node1.next = node2;

        // Conecta filhos do mesmo pai
        connectChildren(node1.left, node1.right);
        connectChildren(node2.left, node2.right);

        // Conecta filhos de pais adjacentes
        connectChildren(node1.right, node2.left);
    }

    /**
     * Método 4: Iterativo com ponteiro para nível atual
     * Similar ao método 2 mas mais explícito.
     * Tempo: O(n), Espaço: O(1)
     */
    public Node connectIterative(Node root) {
        if (root == null) return null;

        Node levelStart = root;

        while (levelStart != null) {
            Node curr = levelStart;
            Node dummy = new Node(0); // Dummy head para próximo nível
            Node tail = dummy;

            while (curr != null) {
                if (curr.left != null) {
                    tail.next = curr.left;
                    tail = tail.next;
                }
                if (curr.right != null) {
                    tail.next = curr.right;
                    tail = tail.next;
                }
                curr = curr.next;
            }

            levelStart = dummy.next;
        }

        return root;
    }

    // Helper para construir árvore perfeita a partir de array (level order)
    private static Node buildPerfectTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) return null;

        Node root = new Node(arr[0]);
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < arr.length) {
            Node node = queue.poll();

            if (i < arr.length && arr[i] != null) {
                node.left = new Node(arr[i]);
                queue.offer(node.left);
            }
            i++;

            if (i < arr.length && arr[i] != null) {
                node.right = new Node(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }

    // Helper para verificar next pointers nível a nível
    private static List<List<Integer>> getLevelsWithNext(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Node levelStart = root;
        while (levelStart != null) {
            List<Integer> level = new ArrayList<>();
            Node curr = levelStart;
            while (curr != null) {
                level.add(curr.val);
                curr = curr.next;
            }
            result.add(level);
            levelStart = levelStart.left; // Árvore perfeita
        }
        return result;
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

    // Helper para níveis para string
    private static String levelsToString(List<List<Integer>> levels) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < levels.size(); i++) {
            sb.append(levels.get(i));
            if (i < levels.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        boolean allPassed = true;

        System.out.println("=== Populating Next Right Pointers Tests ===\n");

        // Test 1: Example 1 - Perfect tree with 3 levels
        System.out.println("Test 1: Perfect tree 3 levels");
        Integer[] arr1 = {1, 2, 3, 4, 5, 6, 7};
        Node root1 = buildPerfectTree(arr1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3),
            Arrays.asList(4, 5, 6, 7)
        );
        sol.connect(root1);
        List<List<Integer>> result1 = getLevelsWithNext(root1);
        boolean pass1 = result1.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr1));
        System.out.println("  Expected: " + levelsToString(expected1));
        System.out.println("  Actual:   " + levelsToString(result1));
        System.out.println("  Result: " + (pass1 ? "✓" : "✗ FAIL"));
        allPassed &= pass1;
        System.out.println();

        // Test 2: Empty tree
        System.out.println("Test 2: Empty tree");
        Node root2 = null;
        sol.connect(root2);
        boolean pass2 = root2 == null;
        System.out.println("  Tree: null");
        System.out.println("  Result: " + (pass2 ? "✓" : "✗ FAIL"));
        allPassed &= pass2;
        System.out.println();

        // Test 3: Single node
        System.out.println("Test 3: Single node");
        Integer[] arr3 = {1};
        Node root3 = buildPerfectTree(arr3);
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(1));
        sol.connect(root3);
        List<List<Integer>> result3 = getLevelsWithNext(root3);
        boolean pass3 = result3.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Two levels
        System.out.println("Test 4: Two levels");
        Integer[] arr4 = {1, 2, 3};
        Node root4 = buildPerfectTree(arr4);
        List<List<Integer>> expected4 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3)
        );
        sol.connect(root4);
        List<List<Integer>> result4 = getLevelsWithNext(root4);
        boolean pass4 = result4.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Test O(1) space method
        System.out.println("Test 5: O(1) Space method");
        Integer[] arr5 = {1, 2, 3, 4, 5, 6, 7};
        Node root5 = buildPerfectTree(arr5);
        sol.connectO1Space(root5);
        List<List<Integer>> result5 = getLevelsWithNext(root5);
        boolean pass5 = result5.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Test DFS method
        System.out.println("Test 6: DFS method");
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        Node root6 = buildPerfectTree(arr6);
        sol.connectDFS(root6);
        List<List<Integer>> result6 = getLevelsWithNext(root6);
        boolean pass6 = result6.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Test Iterative method
        System.out.println("Test 7: Iterative method");
        Integer[] arr7 = {1, 2, 3, 4, 5, 6, 7};
        Node root7 = buildPerfectTree(arr7);
        sol.connectIterative(root7);
        List<List<Integer>> result7 = getLevelsWithNext(root7);
        boolean pass7 = result7.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: All methods consistency
        System.out.println("Test 8: All methods consistency");
        Integer[] arr8 = {1, 2, 3, 4, 5, 6, 7};
        Node root8a = buildPerfectTree(arr8);
        Node root8b = buildPerfectTree(arr8);
        Node root8c = buildPerfectTree(arr8);
        Node root8d = buildPerfectTree(arr8);

        sol.connect(root8a);
        sol.connectO1Space(root8b);
        sol.connectDFS(root8c);
        sol.connectIterative(root8d);

        List<List<Integer>> r8a = getLevelsWithNext(root8a);
        List<List<Integer>> r8b = getLevelsWithNext(root8b);
        List<List<Integer>> r8c = getLevelsWithNext(root8c);
        List<List<Integer>> r8d = getLevelsWithNext(root8d);

        boolean pass8 = r8a.equals(r8b) && r8b.equals(r8c) && r8c.equals(r8d);
        System.out.println("  BFS:       " + levelsToString(r8a));
        System.out.println("  O(1)Space: " + levelsToString(r8b));
        System.out.println("  DFS:       " + levelsToString(r8c));
        System.out.println("  Iterative: " + levelsToString(r8d));
        System.out.println("  All match: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: Larger perfect tree (4 levels)
        System.out.println("Test 9: Perfect tree 4 levels");
        Integer[] arr9 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Node root9 = buildPerfectTree(arr9);
        List<List<Integer>> expected9 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3),
            Arrays.asList(4, 5, 6, 7),
            Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15)
        );
        sol.connect(root9);
        List<List<Integer>> result9 = getLevelsWithNext(root9);
        boolean pass9 = result9.equals(expected9);
        System.out.println("  Tree: " + arrayToString(arr9));
        System.out.println("  Result: " + (pass9 ? "✓" : "✗ FAIL"));
        allPassed &= pass9;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}