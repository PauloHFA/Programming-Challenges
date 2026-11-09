import java.util.ArrayList;
import java.util.List;
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
    
    // Método principal: BFS com Queue
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(level);
        }
        
        return result;
    }
    
    // Método 2: BFS com delimitador de nível (null)
    public List<List<Integer>> levelOrderDelimiter(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(null);  // delimitador de nível
        
        List<Integer> level = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                result.add(new ArrayList<>(level));
                level.clear();
                if (!queue.isEmpty()) {
                    queue.offer(null);
                }
            } else {
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    // Método 3: DFS Recursivo com nível
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;
        
        if (result.size() == level) {
            result.add(new ArrayList<>());
        }
        
        result.get(level).add(node.val);
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }
    
    // Método 4: Duas Queues (atual e próxima)
    public List<List<Integer>> levelOrderTwoQueues(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> current = new LinkedList<>();
        Queue<TreeNode> next = new LinkedList<>();
        current.offer(root);
        
        List<Integer> level = new ArrayList<>();
        
        while (!current.isEmpty()) {
            TreeNode node = current.poll();
            level.add(node.val);
            
            if (node.left != null) next.offer(node.left);
            if (node.right != null) next.offer(node.right);
            
            if (current.isEmpty()) {
                result.add(new ArrayList<>(level));
                level.clear();
                Queue<TreeNode> temp = current;
                current = next;
                next = temp;
            }
        }
        
        return result;
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
    
    // Helper: compara listas de listas
    private boolean listsEqual(List<List<Integer>> a, List<List<Integer>> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Binary Tree Level Order Traversal Tests ===\n");
        
        // Test 1: Example 1
        test(sol, new Integer[]{3, 9, 20, null, null, 15, 7}, 
             new Integer[][]{{3}, {9, 20}, {15, 7}}, "Test 1: Example 1");
        
        // Test 2: Example 2
        test(sol, new Integer[]{1}, 
             new Integer[][]{{1}}, "Test 2: Single node");
        
        // Test 3: Example 3
        test(sol, new Integer[]{}, 
             new Integer[][]{}, "Test 3: Empty");
        
        // Test 4: Left skewed
        test(sol, new Integer[]{1, 2, null, 3, null, 4}, 
             new Integer[][]{{1}, {2}, {3}, {4}}, "Test 4: Left skewed");
        
        // Test 5: Right skewed
        test(sol, new Integer[]{1, null, 2, null, 3, null, 4}, 
             new Integer[][]{{1}, {2}, {3}, {4}}, "Test 5: Right skewed");
        
        // Test 6: Complete tree
        test(sol, new Integer[]{1, 2, 3, 4, 5, 6, 7}, 
             new Integer[][]{{1}, {2, 3}, {4, 5, 6, 7}}, "Test 6: Complete tree");
        
        // Test 7: Perfect tree height 3
        test(sol, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 
             new Integer[][]{{1}, {2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11, 12, 13, 14, 15}}, "Test 7: Perfect tree");
        
        // Test 8: Only left children at each level
        test(sol, new Integer[]{1, 2, 3, 4, null, 5, 6, 7, null, null, null, null, 8}, 
             new Integer[][]{{1}, {2, 3}, {4, 5, 6}, {7, 8}}, "Test 8: Mixed");
        
        // Test 9: Two nodes
        test(sol, new Integer[]{1, 2}, 
             new Integer[][]{{1}, {2}}, "Test 9: Two nodes left");
        
        test(sol, new Integer[]{1, null, 2}, 
             new Integer[][]{{1}, {2}}, "Test 10: Two nodes right");
        
        // Test 11: Negative values
        test(sol, new Integer[]{-1, -2, -3, -4, -5}, 
             new Integer[][]{{-1}, {-2, -3}, {-4, -5}}, "Test 11: Negative values");
    }
    
    private static void test(Solution sol, Integer[] arr, Integer[][] expected, String name) {
        TreeNode root = createTree(arr);
        
        List<List<Integer>> result1 = sol.levelOrder(root);
        List<List<Integer>> result2 = sol.levelOrderDelimiter(root);
        List<List<Integer>> result3 = sol.levelOrderDFS(root);
        List<List<Integer>> result4 = sol.levelOrderTwoQueues(root);
        
        List<List<Integer>> expList = new ArrayList<>();
        for (Integer[] level : expected) {
            expList.add(new ArrayList<>(java.util.Arrays.asList(level)));
        }
        
        boolean pass1 = sol.listsEqual(result1, expList);
        boolean pass2 = sol.listsEqual(result2, expList);
        boolean pass3 = sol.listsEqual(result3, expList);
        boolean pass4 = sol.listsEqual(result4, expList);
        boolean allMatch = sol.listsEqual(result1, result2) && sol.listsEqual(result2, result3) && sol.listsEqual(result3, result4);
        
        System.out.println(name);
        System.out.println("  Tree: " + java.util.Arrays.toString(arr));
        System.out.println("  Expected: " + expList);
        System.out.println("  Queue:      " + result1 + (pass1 ? " ✓" : " ✗"));
        System.out.println("  Delimiter:  " + result2 + (pass2 ? " ✓" : " ✗"));
        System.out.println("  DFS:        " + result3 + (pass3 ? " ✓" : " ✗"));
        System.out.println("  TwoQueues:  " + result4 + (pass4 ? " ✓" : " ✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}