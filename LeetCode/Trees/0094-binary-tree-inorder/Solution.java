import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    
    // Método principal: Iterativo com Stack
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // Vai para o nó mais à esquerda
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // Processa o nó
            curr = stack.pop();
            result.add(curr.val);
            
            // Vai para a subárvore direita
            curr = curr.right;
        }
        
        return result;
    }
    
    // Método 2: Recursivo
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }
    
    private void inorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderRecursive(node.left, result);
        result.add(node.val);
        inorderRecursive(node.right, result);
    }
    
    // Método 3: Morris Traversal (O(1) espaço)
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                result.add(curr.val);
                curr = curr.right;
            } else {
                // Encontra predecessor in-order
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) {
                    pred = pred.right;
                }
                
                if (pred.right == null) {
                    // Cria thread temporário
                    pred.right = curr;
                    curr = curr.left;
                } else {
                    // Remove thread e processa
                    pred.right = null;
                    result.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        
        return result;
    }
    
    // Método 4: Stack com estado (simula recursão)
    public List<Integer> inorderTraversalStackState(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, 0));  // 0 = visit left, 1 = visit self, 2 = visit right
        
        while (!stack.isEmpty()) {
            Pair pair = stack.pop();
            TreeNode node = pair.node;
            int state = pair.state;
            
            if (node == null) continue;
            
            if (state == 0) {
                // Push right, self, left (reverse order for stack)
                stack.push(new Pair(node.right, 0));
                stack.push(new Pair(node, 1));
                stack.push(new Pair(node.left, 0));
            } else if (state == 1) {
                result.add(node.val);
            }
        }
        
        return result;
    }
    
    private static class Pair {
        TreeNode node;
        int state;
        Pair(TreeNode node, int state) {
            this.node = node;
            this.state = state;
        }
    }
    
    // Helper: cria árvore a partir de array (level order, null = -1)
    private static TreeNode createTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null) return null;
        
        TreeNode root = new TreeNode(arr[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
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
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Binary Tree Inorder Traversal Tests ===\n");
        
        // Test 1: Example 1
        TreeNode t1 = createTree(new Integer[]{1, null, 2, 3});
        test(sol, t1, new int[]{1, 3, 2}, "Test 1: [1,null,2,3]");
        
        // Test 2: Example 2
        TreeNode t2 = null;
        test(sol, t2, new int[]{}, "Test 2: []");
        
        // Test 3: Example 3
        TreeNode t3 = createTree(new Integer[]{1});
        test(sol, t3, new int[]{1}, "Test 3: [1]");
        
        // Test 4: Complete tree
        TreeNode t4 = createTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        test(sol, t4, new int[]{4, 2, 5, 1, 6, 3, 7}, "Test 4: Complete tree");
        
        // Test 5: Left skewed
        TreeNode t5 = createTree(new Integer[]{1, 2, null, 3, null, 4});
        test(sol, t5, new int[]{4, 3, 2, 1}, "Test 5: Left skewed");
        
        // Test 6: Right skewed
        TreeNode t6 = createTree(new Integer[]{1, null, 2, null, 3, null, 4});
        test(sol, t6, new int[]{1, 2, 3, 4}, "Test 6: Right skewed");
        
        // Test 7: Only left children
        TreeNode t7 = createTree(new Integer[]{4, 3, null, 2, null, 1});
        test(sol, t7, new int[]{1, 2, 3, 4}, "Test 7: Only left");
        
        // Test 8: Only right children
        TreeNode t8 = createTree(new Integer[]{1, null, 2, null, 3, null, 4});
        test(sol, t8, new int[]{1, 2, 3, 4}, "Test 8: Only right");
        
        // Test 9: Two nodes left
        TreeNode t9 = createTree(new Integer[]{2, 1});
        test(sol, t9, new int[]{1, 2}, "Test 9: Two nodes left");
        
        // Test 10: Two nodes right
        TreeNode t10 = createTree(new Integer[]{1, null, 2});
        test(sol, t10, new int[]{1, 2}, "Test 10: Two nodes right");
    }
    
    private static void test(Solution sol, TreeNode root, int[] expected, String name) {
        List<Integer> result1 = sol.inorderTraversal(root);
        List<Integer> result2 = sol.inorderTraversalRecursive(root);
        List<Integer> result3 = sol.inorderTraversalMorris(root);
        List<Integer> result4 = sol.inorderTraversalStackState(root);
        
        boolean pass1 = listEquals(result1, expected);
        boolean pass2 = listEquals(result2, expected);
        boolean pass3 = listEquals(result3, expected);
        boolean pass4 = listEquals(result4, expected);
        boolean allMatch = listEquals(result1, result2) && listEquals(result2, result3) && listEquals(result3, result4);
        
        System.out.println(name);
        System.out.println("  Expected: " + java.util.Arrays.toString(expected));
        System.out.println("  Stack:    " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Recursive:" + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Morris:   " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  StackSt:  " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
    
    private static boolean listEquals(List<Integer> list, int[] arr) {
        if (list.size() != arr.length) return false;
        for (int i = 0; i < arr.length; i++) {
            if (list.get(i) != arr[i]) return false;
        }
        return true;
    }
    
    private static boolean listEquals(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) return false;
        }
        return true;
    }
}