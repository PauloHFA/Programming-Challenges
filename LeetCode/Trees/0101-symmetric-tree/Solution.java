import java.util.Queue;
import java.util.LinkedList;
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
    
    // Método principal: Recursivo (compara espelhos)
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }
    
    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;
        
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
    
    // Método 2: Iterativo com Queue (BFS)
    public boolean isSymmetricQueue(TreeNode root) {
        if (root == null) return true;
        
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{root.left, root.right});
        
        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode left = pair[0], right = pair[1];
            
            if (left == null && right == null) continue;
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;
            
            queue.offer(new TreeNode[]{left.left, right.right});
            queue.offer(new TreeNode[]{left.right, right.left});
        }
        
        return true;
    }
    
    // Método 3: Iterativo com Stack (DFS)
    public boolean isSymmetricStack(TreeNode root) {
        if (root == null) return true;
        
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{root.left, root.right});
        
        while (!stack.isEmpty()) {
            TreeNode[] pair = stack.pop();
            TreeNode left = pair[0], right = pair[1];
            
            if (left == null && right == null) continue;
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;
            
            stack.push(new TreeNode[]{left.left, right.right});
            stack.push(new TreeNode[]{left.right, right.left});
        }
        
        return true;
    }
    
    // Método 4: Serialização simétrica
    public boolean isSymmetricSerialize(TreeNode root) {
        if (root == null) return true;
        return serializeLeft(root.left).equals(serializeRight(root.right));
    }
    
    private String serializeLeft(TreeNode node) {
        if (node == null) return "#";
        return node.val + "," + serializeLeft(node.left) + "," + serializeLeft(node.right);
    }
    
    private String serializeRight(TreeNode node) {
        if (node == null) return "#";
        return node.val + "," + serializeRight(node.right) + "," + serializeRight(node.left);
    }
    
    // Helper: cria árvore a partir de array
    private static TreeNode createTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null) return null;
        
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
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Symmetric Tree Tests ===\n");
        
        // Test 1: Example 1 - Symmetric
        test(sol, new Integer[]{1, 2, 2, 3, 4, 4, 3}, true, "Test 1: Symmetric");
        
        // Test 2: Example 2 - Not symmetric
        test(sol, new Integer[]{1, 2, 2, null, 3, null, 3}, false, "Test 2: Not symmetric");
        
        // Test 3: Single node
        test(sol, new Integer[]{1}, true, "Test 3: Single node");
        
        // Test 4: Empty
        test(sol, new Integer[]{}, true, "Test 4: Empty");
        
        // Test 5: Two nodes symmetric
        test(sol, new Integer[]{1, 2, 2}, true, "Test 5: Two nodes symmetric");
        
        // Test 6: Two nodes not symmetric
        test(sol, new Integer[]{1, 2, 3}, false, "Test 6: Two nodes not symmetric");
        
        // Test 7: Left only
        test(sol, new Integer[]{1, 2}, false, "Test 7: Left only");
        
        // Test 8: Right only
        test(sol, new Integer[]{1, null, 2}, false, "Test 8: Right only");
        
        // Test 9: Deeper symmetric (perfect tree of height 3)
                test(sol, new Integer[]{1, 2, 2, 3, 4, 4, 3, 5, 6, 7, 8, 8, 7, 6, 5}, true, "Test 9: Deeper symmetric");
        
        // Test 10: Deeper not symmetric
                test(sol, new Integer[]{1, 2, 2, 3, 4, 4, 3, 5, 6, 7, 8, 8, 7, 6, 9}, false, "Test 10: Deeper not symmetric");
        
        // Test 11: All same values
        test(sol, new Integer[]{1, 1, 1, 1, 1, 1, 1}, true, "Test 11: All same values");
        
        // Test 12: Negative values
        test(sol, new Integer[]{-1, -2, -2, -3, -4, -4, -3}, true, "Test 12: Negative values");
        
        // Test 13: Large tree symmetric
        test(sol, new Integer[]{5, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2}, true, "Test 13: Large symmetric");
    }
    
    private static void test(Solution sol, Integer[] arr, boolean expected, String name) {
        TreeNode root = createTree(arr);
        
        boolean result1 = sol.isSymmetric(root);
        boolean result2 = sol.isSymmetricQueue(root);
        boolean result3 = sol.isSymmetricStack(root);
        boolean result4 = sol.isSymmetricSerialize(root);
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  Tree: " + java.util.Arrays.toString(arr));
        System.out.println("  Expected: " + expected);
        System.out.println("  Recursive: " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Queue:     " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Stack:     " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Serialize: " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}