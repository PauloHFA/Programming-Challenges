import java.util.*;

/**
 * LeetCode 117 - Populating Next Right Pointers in Each Node II
 * 
 * Dado uma árvore binária (NÃO necessariamente perfeita), preencha cada ponteiro next
 * para apontar para o próximo nó à direita. Se não houver próximo nó à direita,
 * o ponteiro next deve ser NULL.
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
     * Usa queue para processar nível a nível, funciona para qualquer árvore binária.
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
        }

        return root;
    }

    /**
     * Método 2: O(1) Espaço - Usando next pointers e dummy head
     * Não assume árvore perfeita. Usa dummy para construir próximo nível.
     * Tempo: O(n), Espaço: O(1)
     */
    public Node connectO1Space(Node root) {
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

    /**
     * Método 3: DFS Recursivo com mapa de nível
     * Usa mapa para guardar último nó de cada nível.
     * Tempo: O(n), Espaço: O(h)
     */
    public Node connectDFS(Node root) {
        Map<Integer, Node> lastNodeAtLevel = new HashMap<>();
        dfs(root, 0, lastNodeAtLevel);
        return root;
    }

    private void dfs(Node node, int level, Map<Integer, Node> lastNodeAtLevel) {
        if (node == null) return;

        if (lastNodeAtLevel.containsKey(level)) {
            lastNodeAtLevel.get(level).next = node;
        }
        lastNodeAtLevel.put(level, node);

        dfs(node.left, level + 1, lastNodeAtLevel);
        dfs(node.right, level + 1, lastNodeAtLevel);
    }

    /**
     * Método 4: Iterativo com dois ponteiros (current level, next level)
     * Similar ao método 2 mas mais explícito.
     * Tempo: O(n), Espaço: O(1)
     */
    public Node connectTwoPointers(Node root) {
        if (root == null) return null;

        Node currLevel = root;

        while (currLevel != null) {
            Node dummy = new Node(0);
            Node tail = dummy;

            // Percorre nível atual
            Node curr = currLevel;
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

            currLevel = dummy.next;
        }

        return root;
    }

    // Helper para construir árvore a partir de array (level order com null)
    private static Node buildTree(Integer[] arr) {
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
            
            // Encontra início do próximo nível
            Node nextLevelStart = null;
            Node temp = levelStart;
            while (temp != null && nextLevelStart == null) {
                if (temp.left != null) nextLevelStart = temp.left;
                else if (temp.right != null) nextLevelStart = temp.right;
                temp = temp.next;
            }
            levelStart = nextLevelStart;
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

        System.out.println("=== Populating Next Right Pointers II Tests ===\n");

        // Test 1: Example 1 - Non-perfect tree
        System.out.println("Test 1: Non-perfect tree");
        Integer[] arr1 = {1, 2, 3, 4, 5, null, 7};
        Node root1 = buildTree(arr1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3),
            Arrays.asList(4, 5, 7)
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
        Node root3 = buildTree(arr3);
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(1));
        sol.connect(root3);
        List<List<Integer>> result3 = getLevelsWithNext(root3);
        boolean pass3 = result3.equals(expected3);
        System.out.println("  Tree: " + arrayToString(arr3));
        System.out.println("  Result: " + (pass3 ? "✓" : "✗ FAIL"));
        allPassed &= pass3;
        System.out.println();

        // Test 4: Left skewed
        System.out.println("Test 4: Left skewed");
        Integer[] arr4 = {1, 2, null, 3, null, 4};
        Node root4 = buildTree(arr4);
        List<List<Integer>> expected4 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3),
            Arrays.asList(4)
        );
        sol.connect(root4);
        List<List<Integer>> result4 = getLevelsWithNext(root4);
        boolean pass4 = result4.equals(expected4);
        System.out.println("  Tree: " + arrayToString(arr4));
        System.out.println("  Result: " + (pass4 ? "✓" : "✗ FAIL"));
        allPassed &= pass4;
        System.out.println();

        // Test 5: Right skewed
        System.out.println("Test 5: Right skewed");
        Integer[] arr5 = {1, null, 2, null, 3, null, 4};
        Node root5 = buildTree(arr5);
        List<List<Integer>> expected5 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3),
            Arrays.asList(4)
        );
        sol.connect(root5);
        List<List<Integer>> result5 = getLevelsWithNext(root5);
        boolean pass5 = result5.equals(expected5);
        System.out.println("  Tree: " + arrayToString(arr5));
        System.out.println("  Result: " + (pass5 ? "✓" : "✗ FAIL"));
        allPassed &= pass5;
        System.out.println();

        // Test 6: Test O(1) space method
        System.out.println("Test 6: O(1) Space method");
        Integer[] arr6 = {1, 2, 3, 4, 5, null, 7};
        Node root6 = buildTree(arr6);
        sol.connectO1Space(root6);
        List<List<Integer>> result6 = getLevelsWithNext(root6);
        boolean pass6 = result6.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr6));
        System.out.println("  Result: " + (pass6 ? "✓" : "✗ FAIL"));
        allPassed &= pass6;
        System.out.println();

        // Test 7: Test DFS method
        System.out.println("Test 7: DFS method");
        Integer[] arr7 = {1, 2, 3, 4, 5, null, 7};
        Node root7 = buildTree(arr7);
        sol.connectDFS(root7);
        List<List<Integer>> result7 = getLevelsWithNext(root7);
        boolean pass7 = result7.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr7));
        System.out.println("  Result: " + (pass7 ? "✓" : "✗ FAIL"));
        allPassed &= pass7;
        System.out.println();

        // Test 8: Test Two Pointers method
        System.out.println("Test 8: Two Pointers method");
        Integer[] arr8 = {1, 2, 3, 4, 5, null, 7};
        Node root8 = buildTree(arr8);
        sol.connectTwoPointers(root8);
        List<List<Integer>> result8 = getLevelsWithNext(root8);
        boolean pass8 = result8.equals(expected1);
        System.out.println("  Tree: " + arrayToString(arr8));
        System.out.println("  Result: " + (pass8 ? "✓" : "✗ FAIL"));
        allPassed &= pass8;
        System.out.println();

        // Test 9: All methods consistency
        System.out.println("Test 9: All methods consistency");
        Integer[] arr9 = {1, 2, 3, 4, 5, null, 7};
        Node root9a = buildTree(arr9);
        Node root9b = buildTree(arr9);
        Node root9c = buildTree(arr9);
        Node root9d = buildTree(arr9);

        sol.connect(root9a);
        sol.connectO1Space(root9b);
        sol.connectDFS(root9c);
        sol.connectTwoPointers(root9d);

        List<List<Integer>> r9a = getLevelsWithNext(root9a);
        List<List<Integer>> r9b = getLevelsWithNext(root9b);
        List<List<Integer>> r9c = getLevelsWithNext(root9c);
        List<List<Integer>> r9d = getLevelsWithNext(root9d);

        boolean pass9 = r9a.equals(r9b) && r9b.equals(r9c) && r9c.equals(r9d);
        System.out.println("  BFS:       " + levelsToString(r9a));
        System.out.println("  O(1)Space: " + levelsToString(r9b));
        System.out.println("  DFS:       " + levelsToString(r9c));
        System.out.println("  TwoPtrs:   " + levelsToString(r9d));
        System.out.println("  All match: " + (pass9 ? "✓" : "✗ FAIL"));
        allPassed &= pass9;
        System.out.println();

        // Test 10: Perfect tree (should also work)
        System.out.println("Test 10: Perfect tree");
        Integer[] arr10 = {1, 2, 3, 4, 5, 6, 7};
        Node root10 = buildTree(arr10);
        List<List<Integer>> expected10 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3),
            Arrays.asList(4, 5, 6, 7)
        );
        sol.connect(root10);
        List<List<Integer>> result10 = getLevelsWithNext(root10);
        boolean pass10 = result10.equals(expected10);
        System.out.println("  Tree: " + arrayToString(arr10));
        System.out.println("  Result: " + (pass10 ? "✓" : "✗ FAIL"));
        allPassed &= pass10;
        System.out.println();

        // Test 11: Tree with gaps
        System.out.println("Test 11: Tree with gaps");
        Integer[] arr11 = {1, 2, 3, null, 5, null, 7};
        Node root11 = buildTree(arr11);
        List<List<Integer>> expected11 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2, 3),
            Arrays.asList(5, 7)
        );
        sol.connect(root11);
        List<List<Integer>> result11 = getLevelsWithNext(root11);
        boolean pass11 = result11.equals(expected11);
        System.out.println("  Tree: " + arrayToString(arr11));
        System.out.println("  Result: " + (pass11 ? "✓" : "✗ FAIL"));
        allPassed &= pass11;
        System.out.println();

        System.out.println("=== " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗") + " ===");
    }
}