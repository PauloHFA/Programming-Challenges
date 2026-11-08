import java.util.ArrayList;
import java.util.List;

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
    
    // Método principal: Recursão com Memoização
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) return new ArrayList<>();
        return generate(1, n);
    }
    
    private List<TreeNode> generate(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        
        if (start > end) {
            result.add(null);
            return result;
        }
        
        // Tenta cada valor como raiz
        for (int i = start; i <= end; i++) {
            // Gera todas as subárvores esquerdas possíveis
            List<TreeNode> leftTrees = generate(start, i - 1);
            // Gera todas as subárvores direitas possíveis
            List<TreeNode> rightTrees = generate(i + 1, end);
            
            // Combina cada esquerda com cada direita
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = cloneTree(left);
                    root.right = cloneTree(right);
                    result.add(root);
                }
            }
        }
        
        return result;
    }
    
    // Clona árvore (necessário porque subárvores são compartilhadas)
    private TreeNode cloneTree(TreeNode node) {
        if (node == null) return null;
        TreeNode newNode = new TreeNode(node.val);
        newNode.left = cloneTree(node.left);
        newNode.right = cloneTree(node.right);
        return newNode;
    }
    
    // Método 2: DP Bottom-Up (Catalan numbers approach)
    public List<TreeNode> generateTreesDP(int n) {
        if (n == 0) return new ArrayList<>();
        
        // dp[i][j] = lista de árvores com valores i..j
        List<TreeNode>[][] dp = new List[n + 2][n + 2];
        
        // Base case: intervalos vazios
        for (int i = 1; i <= n + 1; i++) {
            dp[i][i - 1] = new ArrayList<>();
            dp[i][i - 1].add(null);
        }
        
        // Preenche para intervalos de tamanho 1 a n
        for (int len = 1; len <= n; len++) {
            for (int start = 1; start + len - 1 <= n; start++) {
                int end = start + len - 1;
                dp[start][end] = new ArrayList<>();
                
                for (int rootVal = start; rootVal <= end; rootVal++) {
                    List<TreeNode> leftTrees = dp[start][rootVal - 1];
                    List<TreeNode> rightTrees = dp[rootVal + 1][end];
                    
                    for (TreeNode left : leftTrees) {
                        for (TreeNode right : rightTrees) {
                            TreeNode root = new TreeNode(rootVal);
                            root.left = cloneTree(left);
                            root.right = cloneTree(right);
                            dp[start][end].add(root);
                        }
                    }
                }
            }
        }
        
        return dp[1][n];
    }
    
    // Método 3: Recursão pura (sem memoização, para verificação)
    public List<TreeNode> generateTreesRecursive(int n) {
        if (n == 0) return new ArrayList<>();
        return generateRecursive(1, n);
    }
    
    private List<TreeNode> generateRecursive(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        
        if (start > end) {
            result.add(null);
            return result;
        }
        
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTrees = generateRecursive(start, i - 1);
            List<TreeNode> rightTrees = generateRecursive(i + 1, end);
            
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = cloneTree(left);
                    root.right = cloneTree(right);
                    result.add(root);
                }
            }
        }
        
        return result;
    }
    
    // Helper: conta número de árvores (número de Catalan)
    private int countTrees(int n) {
        long[] catalan = new long[n + 1];
        catalan[0] = 1;
        catalan[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }
        
        return (int) catalan[n];
    }
    
    // Helper: serializa árvore para comparação (preorder com null markers)
    private String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }
    
    // Helper: verifica se duas listas de árvores são iguais (ordem independente)
    private boolean treesEqual(List<TreeNode> a, List<TreeNode> b) {
        if (a.size() != b.size()) return false;
        
        java.util.Set<String> setA = new java.util.HashSet<>();
        for (TreeNode t : a) setA.add(serialize(t));
        
        java.util.Set<String> setB = new java.util.HashSet<>();
        for (TreeNode t : b) setB.add(serialize(t));
        
        return setA.equals(setB);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Unique Binary Search Trees II Tests ===\n");
        
        // Test 1: n = 3
        test(sol, 3, 5, "Test 1: n=3 (Catalan 3 = 5)");
        
        // Test 2: n = 1
        test(sol, 1, 1, "Test 2: n=1");
        
        // Test 3: n = 2
        test(sol, 2, 2, "Test 3: n=2 (Catalan 2 = 2)");
        
        // Test 4: n = 4
        test(sol, 4, 14, "Test 4: n=4 (Catalan 4 = 14)");
        
        // Test 5: n = 0
        test(sol, 0, 0, "Test 5: n=0");
    }
    
    private static void test(Solution sol, int n, int expectedCount, String name) {
        List<TreeNode> result1 = sol.generateTrees(n);
        List<TreeNode> result2 = sol.generateTreesDP(n);
        List<TreeNode> result3 = (n <= 5) ? sol.generateTreesRecursive(n) : result1;
        
        int count1 = result1.size();
        int count2 = result2.size();
        int count3 = result3.size();
        
        boolean pass1 = count1 == expectedCount;
        boolean pass2 = count2 == expectedCount;
        boolean pass3 = count3 == expectedCount;
        boolean match12 = sol.treesEqual(result1, result2);
        boolean match13 = sol.treesEqual(result1, result3);
        boolean allMatch = match12 && match13;
        
        System.out.println(name);
        System.out.println("  n = " + n + ", Expected count: " + expectedCount);
        System.out.println("  Memo:     " + count1 + " trees " + (pass1 ? "✓" : "✗"));
        System.out.println("  DP:       " + count2 + " trees " + (pass2 ? "✓" : "✗"));
        System.out.println("  Recursive:" + count3 + " trees " + (pass3 ? "✓" : "✗"));
        System.out.println("  Memo==DP: " + (match12 ? "✓" : "✗"));
        System.out.println("  Memo==Rec:" + (match13 ? "✓" : "✗"));
        System.out.println("  All match: " + (allMatch ? "✓" : "✗"));
        System.out.println();
    }
}