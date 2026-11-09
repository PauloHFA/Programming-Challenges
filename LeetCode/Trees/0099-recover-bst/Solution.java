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
    
    // Método principal: Morris Traversal O(1) espaço
    public void recoverTree(TreeNode root) {
        TreeNode first = null, second = null, prev = null;
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                // Processa curr
                if (prev != null && prev.val > curr.val) {
                    if (first == null) first = prev;
                    second = curr;
                }
                prev = curr;
                curr = curr.right;
            } else {
                // Encontra predecessor
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) {
                    pred = pred.right;
                }
                
                if (pred.right == null) {
                    // Cria thread
                    pred.right = curr;
                    curr = curr.left;
                } else {
                    // Remove thread e processa
                    pred.right = null;
                    if (prev != null && prev.val > curr.val) {
                        if (first == null) first = prev;
                        second = curr;
                    }
                    prev = curr;
                    curr = curr.right;
                }
            }
        }
        
        // Troca os valores
        if (first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }
    
    // Método 2: Inorder Traversal com Stack O(h) espaço
    public void recoverTreeStack(TreeNode root) {
        TreeNode first = null, second = null, prev = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            curr = stack.pop();
            
            if (prev != null && prev.val > curr.val) {
                if (first == null) first = prev;
                second = curr;
            }
            prev = curr;
            curr = curr.right;
        }
        
        if (first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }
    
    // Método 3: Inorder Recursivo O(h) espaço
    public void recoverTreeRecursive(TreeNode root) {
        first = second = prev = null;
        inorderRecover(root);
        if (first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }
    
    private TreeNode first, second, prev;
    
    private void inorderRecover(TreeNode node) {
        if (node == null) return;
        
        inorderRecover(node.left);
        
        if (prev != null && prev.val > node.val) {
            if (first == null) first = prev;
            second = node;
        }
        prev = node;
        
        inorderRecover(node.right);
    }
    
    // Método 4: Coleta inorder, ordena, reconstrói O(n) espaço
    public void recoverTreeCollect(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        List<Integer> vals = new ArrayList<>();
        
        inorderCollect(root, nodes, vals);
        
        vals.sort(Integer::compareTo);
        
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).val = vals.get(i);
        }
    }
    
    private void inorderCollect(TreeNode node, List<TreeNode> nodes, List<Integer> vals) {
        if (node == null) return;
        inorderCollect(node.left, nodes, vals);
        nodes.add(node);
        vals.add(node.val);
        inorderCollect(node.right, nodes, vals);
    }
    
    // Helper: cria árvore a partir de array
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
    
    // Helper: verifica se é BST válida
    private boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
    
    // Helper: inorder para verificação
    private List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }
    
    // Helper: copia árvore
    private TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = copyTree(root.left);
        newRoot.right = copyTree(root.right);
        return newRoot;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Recover Binary Search Tree Tests ===\n");
        
        // Test 1: Example 1 - Swapped adjacent
        test(sol, new Integer[]{1, 3, null, null, 2}, "Test 1: [1,3,null,null,2] -> [3,1,null,null,2]");
        
        // Test 2: Example 2 - Swapped non-adjacent
        test(sol, new Integer[]{3, 1, 4, null, null, 2}, "Test 2: [3,1,4,null,null,2] -> [2,1,4,null,null,3]");
        
        // Test 3: Two nodes only
        test(sol, new Integer[]{2, 1}, "Test 3: [2,1] -> [1,2]");
        
        // Test 4: Swapped root and leaf (valid BST with swap)
                // Original BST: [4, 2, 6, 1, 3, 5, 7], swap 4 and 1 -> [1, 2, 6, 4, 3, 5, 7]
                test(sol, new Integer[]{1, 2, 6, 4, 3, 5, 7}, "Test 4: Root and leaf swapped");
        
        // Test 5: Swapped left subtree nodes (valid BST with swap)
                // Original BST: [4, 2, 6, 1, 3, 5, 7], swap 2 and 5 -> [4, 5, 6, 1, 3, 2, 7]
                test(sol, new Integer[]{4, 5, 6, 1, 3, 2, 7}, "Test 5: Left subtree swapped");
        
                // Test 6: Swapped right subtree nodes (valid BST with swap)
                // Original BST: [4, 2, 6, 1, 3, 5, 7], swap 6 and 3 -> [4, 2, 3, 1, 6, 5, 7]
                test(sol, new Integer[]{4, 2, 3, 1, 6, 5, 7}, "Test 6: Right subtree swapped");
        
        // Test 7: Already valid
        test(sol, new Integer[]{2, 1, 3}, "Test 7: Already valid");
        
        // Test 8: Larger tree
        test(sol, new Integer[]{8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15}, "Test 8: Perfect BST (no swap)");
    }
    
    private static void test(Solution sol, Integer[] arr, String name) {
        TreeNode original = createTree(arr);
        
        // Cria cópias para cada método
        TreeNode t1 = copyTreeStatic(original);
        TreeNode t2 = copyTreeStatic(original);
        TreeNode t3 = copyTreeStatic(original);
        TreeNode t4 = copyTreeStatic(original);
        
        sol.recoverTree(t1);
        sol.recoverTreeStack(t2);
        sol.recoverTreeRecursive(t3);
        sol.recoverTreeCollect(t4);
        
        boolean valid1 = sol.isValidBST(t1);
        boolean valid2 = sol.isValidBST(t2);
        boolean valid3 = sol.isValidBST(t3);
        boolean valid4 = sol.isValidBST(t4);
        
        List<Integer> in1 = sol.inorder(t1);
        List<Integer> in2 = sol.inorder(t2);
        List<Integer> in3 = sol.inorder(t3);
        List<Integer> in4 = sol.inorder(t4);
        
        boolean allMatch = in1.equals(in2) && in2.equals(in3) && in3.equals(in4);
        
        System.out.println(name);
        System.out.println("  Morris:     Valid=" + valid1 + ", Inorder=" + in1);
        System.out.println("  Stack:      Valid=" + valid2 + ", Inorder=" + in2);
        System.out.println("  Recursive:  Valid=" + valid3 + ", Inorder=" + in3);
        System.out.println("  Collect:    Valid=" + valid4 + ", Inorder=" + in4);
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
    
    private static TreeNode copyTreeStatic(TreeNode root) {
        if (root == null) return null;
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = copyTreeStatic(root.left);
        newRoot.right = copyTreeStatic(root.right);
        return newRoot;
    }
}