import java.util.*;

/**
 * LeetCode 130 - Surrounded Regions
 * 
 * Dado um tabuleiro m x n contendo 'X' e 'O', capture todas as regiões
 * cercadas por 'X'. Uma região é capturada virando todos os 'O' em 'X'
 * nessa região cercada.
 * Regiões na borda não são capturadas.
 */
public class Solution {

    /**
     * Método 1: DFS a partir das bordas (Principal)
     * Marca 'O' conectados à borda como temporários, depois converte
     * Tempo: O(m*n), Espaço: O(m*n) stack
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        
        int m = board.length;
        int n = board[0].length;
        
        // DFS a partir das bordas
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs(board, i, 0);
            if (board[i][n - 1] == 'O') dfs(board, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfs(board, 0, j);
            if (board[m - 1][j] == 'O') dfs(board, m - 1, j);
        }
        
        // Converte: 'O' -> 'X' (capturados), '#' -> 'O' (salvos)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '#') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    private void dfs(char[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;
        
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') {
            return;
        }
        
        board[i][j] = '#'; // Marca como visitado/salvo
        
        dfs(board, i + 1, j);
        dfs(board, i - 1, j);
        dfs(board, i, j + 1);
        dfs(board, i, j - 1);
    }

    /**
     * Método 2: BFS a partir das bordas
     * Usa queue em vez de recursão
     * Tempo: O(m*n), Espaço: O(m*n)
     */
    public void solveBFS(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        
        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Adiciona 'O' das bordas à queue
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                board[i][0] = '#';
                queue.offer(new int[]{i, 0});
            }
            if (board[i][n - 1] == 'O') {
                board[i][n - 1] = '#';
                queue.offer(new int[]{i, n - 1});
            }
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                board[0][j] = '#';
                queue.offer(new int[]{0, j});
            }
            if (board[m - 1][j] == 'O') {
                board[m - 1][j] = '#';
                queue.offer(new int[]{m - 1, j});
            }
        }
        
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && board[nx][ny] == 'O') {
                    board[nx][ny] = '#';
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        
        // Converte
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }

    /**
     * Método 3: Union Find
     * Une 'O' conectados, conecta borda a nó virtual
     * Tempo: O(m*n * α(m*n)), Espaço: O(m*n)
     */
    public void solveUF(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        
        int m = board.length;
        int n = board[0].length;
        int dummy = m * n; // Nó virtual para borda
        
        int[] parent = new int[m * n + 1];
        for (int i = 0; i <= m * n; i++) {
            parent[i] = i;
        }
        
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    int idx = i * n + j;
                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        union(parent, idx, dummy);
                    } else {
                        for (int[] dir : dirs) {
                            int ni = i + dir[0];
                            int nj = j + dir[1];
                            if (ni >= 0 && ni < m && nj >= 0 && nj < n && board[ni][nj] == 'O') {
                                union(parent, idx, ni * n + nj);
                            }
                        }
                    }
                }
            }
        }
        
        // Converte
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    int idx = i * n + j;
                    if (find(parent, idx) == find(parent, dummy)) {
                        board[i][j] = 'O'; // Conectado à borda
                    } else {
                        board[i][j] = 'X'; // Capturado
                    }
                }
            }
        }
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
    
    private void union(int[] parent, int x, int y) {
        int px = find(parent, x);
        int py = find(parent, y);
        if (px != py) {
            parent[px] = py;
        }
    }

    // Helper para imprimir board
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println("  " + Arrays.toString(row));
        }
    }
    
    // Helper para copiar board
    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        System.out.println("=== Surrounded Regions Tests ===\n");
        
        // Test 1: Example 1
        System.out.println("Test 1: Example 1");
        char[][] b1 = {
            {'X','X','X','X'},
            {'X','O','O','X'},
            {'X','X','O','X'},
            {'X','O','X','X'}
        };
        System.out.println("Before:");
        printBoard(b1);
            char[][] b1copy = copyBoard(b1);
            sol.solve(b1copy);
            System.out.println("After DFS:");
            printBoard(b1copy);
            System.out.println("Expected: O at (3,1) remains, others become X");
        
        // Test 2: Example 2
        System.out.println("\nTest 2: Single X");
        char[][] b2 = {{'X'}};
            char[][] b2copy = copyBoard(b2);
            sol.solve(b2copy);
            printBoard(b2copy);
        
            // Test 3: All O
            System.out.println("\nTest 3: All O (3x3)");
            char[][] b3 = {
            {'O','O','O'},
                {'O','O','O'},
                {'O','O','O'}
            };
            char[][] b3copy = copyBoard(b3);
            sol.solve(b3copy);
            printBoard(b3copy);
            System.out.println("Expected: All O remain (all connected to border)");
        
            // Test 4: Surrounded center
            System.out.println("\nTest 4: Surrounded center");
            char[][] b4 = {
                {'X','X','X','X','X'},
            {'X','O','O','O','X'},
                {'X','O','X','O','X'},
                {'X','O','O','O','X'},
                {'X','X','X','X','X'}
            };
            char[][] b4copy = copyBoard(b4);
            sol.solve(b4copy);
            printBoard(b4copy);
            System.out.println("Expected: Center X remains, ring of O becomes X");
        
        // Test 5: Cross verification
        System.out.println("\nTest 5: Cross verification (DFS vs BFS vs UF)");
        char[][][] testCases = {
            {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}},
            {{'X'}},
            {{'O','O','O'},{'O','O','O'},{'O','O','O'}},
            {{'X','X','X','X','X'},{'X','O','O','O','X'},{'X','O','X','O','X'},{'X','O','O','O','X'},{'X','X','X','X','X'}},
            {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            char[][] d1 = copyBoard(testCases[i]);
            char[][] d2 = copyBoard(testCases[i]);
            char[][] d3 = copyBoard(testCases[i]);
            
            sol.solve(d1);
            sol.solveBFS(d2);
            sol.solveUF(d3);
            
            boolean match = Arrays.deepEquals(d1, d2) && Arrays.deepEquals(d2, d3);
            System.out.println("  Test " + (i+1) + ": " + (match ? "MATCH" : "MISMATCH"));
            if (!match) {
                System.out.println("    DFS:  " + Arrays.deepToString(d1));
                System.out.println("    BFS:  " + Arrays.deepToString(d2));
                System.out.println("    UF:   " + Arrays.deepToString(d3));
            }
        }
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}