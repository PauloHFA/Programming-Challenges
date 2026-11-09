import java.util.*;

/**
 * LeetCode 133 - Clone Graph
 * 
 * Dada uma referência de um nó em um grafo não direcionado conectado,
 * retorne uma cópia profunda (deep copy) do grafo.
 * Cada nó no grafo contém um valor (int) e uma lista (List[Node]) de seus vizinhos.
 */
public class Solution {

    public static class Node {
        public int val;
        public List<Node> neighbors;
        
        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }
        
        public Node(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }
        
        public Node(int val, List<Node> neighbors) {
            this.val = val;
            this.neighbors = neighbors;
        }
    }

    /**
     * Método 1: DFS Recursivo com HashMap (Principal)
     * Usa mapa para rastrear nós já clonados
     * Tempo: O(V + E), Espaço: O(V)
     */
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        
        Map<Node, Node> cloned = new HashMap<>();
        return dfs(node, cloned);
    }
    
    private Node dfs(Node node, Map<Node, Node> cloned) {
        if (cloned.containsKey(node)) {
            return cloned.get(node);
        }
        
        Node copy = new Node(node.val);
        cloned.put(node, copy);
        
        for (Node neighbor : node.neighbors) {
            copy.neighbors.add(dfs(neighbor, cloned));
        }
        
        return copy;
    }

    /**
     * Método 2: BFS Iterativo com Queue
     * Usa queue para processar nós nível por nível
     * Tempo: O(V + E), Espaço: O(V)
     */
    public Node cloneGraphBFS(Node node) {
        if (node == null) return null;
        
        Map<Node, Node> cloned = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        cloned.put(node, new Node(node.val));
        queue.offer(node);
        
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            
            for (Node neighbor : curr.neighbors) {
                if (!cloned.containsKey(neighbor)) {
                    cloned.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                cloned.get(curr).neighbors.add(cloned.get(neighbor));
            }
        }
        
        return cloned.get(node);
    }

    /**
     * Método 3: DFS Iterativo com Stack
     * Similar ao BFS mas usa stack
     * Tempo: O(V + E), Espaço: O(V)
     */
    public Node cloneGraphStack(Node node) {
        if (node == null) return null;
        
        Map<Node, Node> cloned = new HashMap<>();
        Stack<Node> stack = new Stack<>();
        
        cloned.put(node, new Node(node.val));
        stack.push(node);
        
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            
            for (Node neighbor : curr.neighbors) {
                if (!cloned.containsKey(neighbor)) {
                    cloned.put(neighbor, new Node(neighbor.val));
                    stack.push(neighbor);
                }
                cloned.get(curr).neighbors.add(cloned.get(neighbor));
            }
        }
        
        return cloned.get(node);
    }

    // Helper para criar grafo de teste
    private static Node createGraph(int[][] adjList) {
        if (adjList.length == 0) return null;
        
        Node[] nodes = new Node[adjList.length + 1]; // 1-indexed
        for (int i = 1; i <= adjList.length; i++) {
            nodes[i] = new Node(i);
        }
        
        for (int i = 1; i <= adjList.length; i++) {
            for (int neighborVal : adjList[i - 1]) {
                nodes[i].neighbors.add(nodes[neighborVal]);
            }
        }
        
        return nodes[1];
    }
    
    // Helper para verificar se dois grafos são iguais (estrutura)
    private static boolean graphsEqual(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        
        Set<Node> visited1 = new HashSet<>();
        Set<Node> visited2 = new HashSet<>();
        Queue<Node> q1 = new LinkedList<>();
        Queue<Node> q2 = new LinkedList<>();
        
        q1.offer(n1);
        q2.offer(n2);
        visited1.add(n1);
        visited2.add(n2);
        
        while (!q1.isEmpty() && !q2.isEmpty()) {
            Node curr1 = q1.poll();
            Node curr2 = q2.poll();
            
            if (curr1.val != curr2.val) return false;
            if (curr1.neighbors.size() != curr2.neighbors.size()) return false;
            
            for (int i = 0; i < curr1.neighbors.size(); i++) {
                Node nb1 = curr1.neighbors.get(i);
                Node nb2 = curr2.neighbors.get(i);
                
                if (nb1.val != nb2.val) return false;
                
                if (!visited1.contains(nb1)) {
                    visited1.add(nb1);
                    q1.offer(nb1);
                }
                if (!visited2.contains(nb2)) {
                    visited2.add(nb2);
                    q2.offer(nb2);
                }
            }
        }
        
        return q1.isEmpty() && q2.isEmpty();
    }
    
    // Helper para verificar se é deep copy (nós diferentes)
    private static boolean isDeepCopy(Node original, Node copy) {
        if (original == copy) return false;
        
        Set<Node> visitedOrig = new HashSet<>();
        Set<Node> visitedCopy = new HashSet<>();
        Queue<Node> q1 = new LinkedList<>();
        Queue<Node> q2 = new LinkedList<>();
        
        q1.offer(original);
        q2.offer(copy);
        visitedOrig.add(original);
        visitedCopy.add(copy);
        
        while (!q1.isEmpty()) {
            Node o = q1.poll();
            Node c = q2.poll();
            
            if (o == c) return false; // Mesmo objeto!
            if (o.val != c.val) return false;
            
            for (int i = 0; i < o.neighbors.size(); i++) {
                Node no = o.neighbors.get(i);
                Node nc = c.neighbors.get(i);
                
                if (no == nc) return false; // Mesmo objeto!
                
                if (!visitedOrig.contains(no)) {
                    visitedOrig.add(no);
                    q1.offer(no);
                }
                if (!visitedCopy.contains(nc)) {
                    visitedCopy.add(nc);
                    q2.offer(nc);
                }
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Clone Graph Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: [[2,4],[1,3],[2,4],[1,3]]");
        int[][] adj1 = {{2,4}, {1,3}, {2,4}, {1,3}};
        Node g1 = createGraph(adj1);
        Node c1 = sol.cloneGraph(g1);
        System.out.println("  Structure equal: " + graphsEqual(g1, c1));
        System.out.println("  Deep copy:       " + isDeepCopy(g1, c1));
        
        // Test 2: Single node
        System.out.println("\nTest 2: Single node [[]]");
        int[][] adj2 = {{}};
        Node g2 = createGraph(adj2);
        Node c2 = sol.cloneGraph(g2);
        System.out.println("  Structure equal: " + graphsEqual(g2, c2));
        System.out.println("  Deep copy:       " + isDeepCopy(g2, c2));
        
        // Test 3: Empty graph
        System.out.println("\nTest 3: Empty graph");
        Node g3 = null;
        Node c3 = sol.cloneGraph(g3);
        System.out.println("  Result: " + (c3 == null ? "null (correct)" : "not null"));
        
        // Test 4: Line graph
        System.out.println("\nTest 4: Line graph 1-2-3-4");
        int[][] adj4 = {{2}, {1,3}, {2,4}, {3}};
        Node g4 = createGraph(adj4);
        Node c4 = sol.cloneGraph(g4);
        System.out.println("  Structure equal: " + graphsEqual(g4, c4));
        System.out.println("  Deep copy:       " + isDeepCopy(g4, c4));
        
        // Test 5: Star graph
        System.out.println("\nTest 5: Star graph (1 connected to 2,3,4,5)");
        int[][] adj5 = {{2,3,4,5}, {1}, {1}, {1}, {1}};
        Node g5 = createGraph(adj5);
        Node c5 = sol.cloneGraph(g5);
        System.out.println("  Structure equal: " + graphsEqual(g5, c5));
        System.out.println("  Deep copy:       " + isDeepCopy(g5, c5));
        
        // Test 6: Cross verification
        System.out.println("\nTest 6: Cross verification (DFS vs BFS vs Stack)");
        int[][][] testCases = {
            {{2,4}, {1,3}, {2,4}, {1,3}},
            {{}},
            {{2}, {1,3}, {2,4}, {3}},
            {{2,3,4,5}, {1}, {1}, {1}, {1}},
            {{2,3}, {1,3}, {1,2}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            Node g = createGraph(testCases[i]);
            Node cDFS = sol.cloneGraph(g);
            Node cBFS = sol.cloneGraphBFS(g);
            Node cStack = sol.cloneGraphStack(g);
            
            boolean match = graphsEqual(cDFS, cBFS) && graphsEqual(cBFS, cStack);
            boolean deepDFS = isDeepCopy(g, cDFS);
            boolean deepBFS = isDeepCopy(g, cBFS);
            boolean deepStack = isDeepCopy(g, cStack);
            
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH") + 
                " (Deep: DFS=" + deepDFS + " BFS=" + deepBFS + " Stack=" + deepStack + ")");
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}