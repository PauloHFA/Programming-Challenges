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
    
    // Método principal: Recursivo
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    // Método 2: Iterativo com Stack (DFS)
    public boolean isSameTreeStack(TreeNode p, TreeNode q) {
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{p, q});
        
        while (!stack.isEmpty()) {
            TreeNode[] pair = stack.pop();
            TreeNode n1 = pair[0], n2 = pair[1];
            
            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null) return false;
            if (n1.val != n2.val) return false;
            
            stack.push(new TreeNode[]{n1.right, n2.right});
            stack.push(new TreeNode[]{n1.left, n2.left});
        }
        
        return true;
    }
    
    // Método 3: Iterativo com Queue (BFS)
    public boolean isSameTreeQueue(TreeNode p, TreeNode q) {
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{p, q});
        
        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode n1 = pair[0], n2 = pair[1];
            
            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null) return false;
            if (n1.val != n2.val) return false;
            
            queue.offer(new TreeNode[]{n1.left, n2.left});
            queue.offer(new TreeNode[]{n1.right, n2.right});
        }
        
        return true;
    }
    
    // Método 4: Serialização e comparação
    public boolean isSameTreeSerialize(TreeNode p, TreeNode q) {
        return serialize(p).equals(serialize(q));
    }
    
    private String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
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
        
        System.out.println("=== Same Tree Tests ===\n");
        
        // Test 1: Example 1 - Same
        test(sol, new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3}, true, "Test 1: Same trees");
        
        // Test 2: Example 2 - Different structure
        test(sol, new Integer[]{1, 2}, new Integer[]{1, null, 2}, false, "Test 2: Different structure");
        
        // Test 3: Example 3 - Different values
        test(sol, new Integer[]{1, 2, 1}, new Integer[]{1, 1, 2}, false, "Test 3: Different values");
        
        // Test 4: Both empty
        test(sol, new Integer[]{}, new Integer[]{}, true, "Test 4: Both empty");
        
        // Test 5: One empty
        test(sol, new Integer[]{1}, new Integer[]{}, false, "Test 5: One empty");
        
        // Test 6: Single node same
        test(sol, new Integer[]{1}, new Integer[]{1}, true, "Test 6: Single node same");
        
        // Test 7: Single node different
        test(sol, new Integer[]{1}, new Integer[]{2}, false, "Test 7: Single node different");
        
        // Test 8: Larger same tree
        test(sol, new Integer[]{1, 2, 3, 4, 5, 6, 7}, new Integer[]{1, 2, 3, 4, 5, 6, 7}, true, "Test 8: Larger same");
        
        // Test 9: Different at leaf
        test(sol, new Integer[]{1, 2, 3, 4, 5, 6, 7}, new Integer[]{1, 2, 3, 4, 5, 6, 8}, false, "Test 9: Different leaf");
        
        // Test 10: Different structure deeper
        test(sol, new Integer[]{1, 2, 3, 4, null, null, 5}, new Integer[]{1, 2, 3, null, 4, null, 5}, false, "Test 10: Different structure deeper");
        
        // Test 11: Negative values
        test(sol, new Integer[]{-1, -2, -3}, new Integer[]{-1, -2, -3}, true, "Test 11: Negative values");
        
        // Test 12: Large values
        test(sol, new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, true, "Test 12: Large values");
    }
    
    private static void test(Solution sol, Integer[] arr1, Integer[] arr2, boolean expected, String name) {
        TreeNode p = createTree(arr1);
        TreeNode q = createTree(arr2);
        
        boolean result1 = sol.isSameTree(p, q);
        boolean result2 = sol.isSameTreeStack(p, q);
        boolean result3 = sol.isSameTreeQueue(p, q);
        boolean result4 = sol.isSameTreeSerialize(p, q);
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        boolean pass3 = result3 == expected;
        boolean pass4 = result4 == expected;
        boolean allMatch = (result1 == result2) && (result2 == result3) && (result3 == result4);
        
        System.out.println(name);
        System.out.println("  p: " + java.util.Arrays.toString(arr1));
        System.out.println("  q: " + java.util.Arrays.toString(arr2));
        System.out.println("  Expected: " + expected);
        System.out.println("  Recursive: " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Stack:     " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  Queue:     " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  Serialize: " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}