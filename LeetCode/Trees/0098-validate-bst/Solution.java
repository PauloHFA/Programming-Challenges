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
    
    // Método principal: Recursão com limites (Long)
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        if (node.val <= min || node.val >= max) return false;
        
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }
    
    // Método 2: Inorder Traversal Iterativo
    public boolean isValidBSTInorder(TreeNode root) {
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        TreeNode curr = root;
        Long prev = null;
        
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            curr = stack.pop();
            
            if (prev != null && curr.val <= prev) return false;
            prev = (long) curr.val;
            
            curr = curr.right;
        }
        
        return true;
    }
    
    // Método 3: Inorder Traversal Recursivo
    public boolean isValidBSTInorderRecursive(TreeNode root) {
        prev = null;
        return inorderValidate(root);
    }
    
    private Long prev = null;
    
    private boolean inorderValidate(TreeNode node) {
        if (node == null) return true;
        
        if (!inorderValidate(node.left)) return false;
        
        if (prev != null && node.val <= prev) return false;
        prev = (long) node.val;
        
        return inorderValidate(node.right);
    }
    
    // Método 4: BFS com limites
    public boolean isValidBSTBFS(TreeNode root) {
        if (root == null) return true;
        
        java.util.Queue<NodeBounds> queue = new java.util.LinkedList<>();
        queue.offer(new NodeBounds(root, Long.MIN_VALUE, Long.MAX_VALUE));
        
        while (!queue.isEmpty()) {
            NodeBounds nb = queue.poll();
            TreeNode node = nb.node;
            long min = nb.min, max = nb.max;
            
            if (node.val <= min || node.val >= max) return false;
            
            if (node.left != null) {
                queue.offer(new NodeBounds(node.left, min, node.val));
            }
            if (node.right != null) {
                queue.offer(new NodeBounds(node.right, node.val, max));
            }
        }
        
        return true;
    }
    
    private static class NodeBounds {
        TreeNode node;
        long min, max;
        NodeBounds(TreeNode node, long min, long max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }
    
    // Helper: cria árvore a partir de array (level order, null = null)
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
        
        System.out.println("=== Validate Binary Search Tree Tests ===\n");
        
        // Test 1: Example 1 - Valid BST
        TreeNode t1 = createTree(new Integer[]{2, 1, 3});
        test(sol, t1, true, "Test 1: [2,1,3] - Valid");
        
        // Test 2: Example 2 - Invalid BST
        TreeNode t2 = createTree(new Integer[]{5, 1, 4, null, null, 3, 6});
        test(sol, t2, false, "Test 2: [5,1,4,null,null,3,6] - Invalid");
        
        // Test 3: Single node
        TreeNode t3 = createTree(new Integer[]{1});
        test(sol, t3, true, "Test 3: [1] - Single node");
        
        // Test 4: Empty tree
        TreeNode t4 = null;
        test(sol, t4, true, "Test 4: [] - Empty");
        
        // Test 5: Left skewed valid
        TreeNode t5 = createTree(new Integer[]{3, 2, null, 1});
        test(sol, t5, true, "Test 5: Left skewed valid");
        
        // Test 6: Right skewed valid
        TreeNode t6 = createTree(new Integer[]{1, null, 2, null, 3});
        test(sol, t6, true, "Test 6: Right skewed valid");
        
        // Test 7: Invalid - left child >= parent
        TreeNode t7 = createTree(new Integer[]{2, 2, 3});
        test(sol, t7, false, "Test 7: Left child == parent");
        
        // Test 8: Invalid - right child <= parent
        TreeNode t8 = createTree(new Integer[]{2, 1, 2});
        test(sol, t8, false, "Test 8: Right child == parent");
        
        // Test 9: Invalid - deeper violation
        TreeNode t9 = createTree(new Integer[]{10, 5, 15, null, null, 6, 20});
        test(sol, t9, false, "Test 9: 6 in right subtree of 10 but < 10");
        
        // Test 10: Valid with negative values
        TreeNode t10 = createTree(new Integer[]{0, -1, 1});
        test(sol, t10, true, "Test 10: Negative values");
        
        // Test 11: Integer boundary values
        TreeNode t11 = createTree(new Integer[]{Integer.MAX_VALUE});
        test(sol, t11, true, "Test 11: Integer.MAX_VALUE");
        
        TreeNode t12 = createTree(new Integer[]{Integer.MIN_VALUE});
        test(sol, t12, true, "Test 12: Integer.MIN_VALUE");
        
        // Test 13: Invalid - left subtree has value > root
        TreeNode t13 = createTree(new Integer[]{5, 4, 6, null, null, 3, 7});
        test(sol, t13, false, "Test 13: 3 in right subtree but < 5");
        
        // Test 14: Complex valid
        TreeNode t14 = createTree(new Integer[]{8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15});
        test(sol, t14, true, "Test 14: Perfect BST");
    }
    
    private static void test(Solution sol, TreeNode root, boolean expected, String name) {
        boolean result1 = sol.isValidBST(root);
        boolean result2 = sol.isValidBSTInorder(root);
        boolean result3 = sol.isValidBSTInorderRecursive(root);
        boolean result4 = sol.isValidBSTBFS(root);
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  Expected: " + expected);
        System.out.println("  Bounds:   " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Inorder:  " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  InorderR: " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  BFS:      " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}