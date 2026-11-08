public class Solution {
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        if (word.length() > m * n) {
            return false;
        }
        
        // Optimization: check if board has enough characters
        int[] boardCount = new int[256];
        for (char[] row : board) {
            for (char c : row) {
                boardCount[c]++;
            }
        }
        for (char c : word.toCharArray()) {
            if (--boardCount[c] < 0) {
                return false;
            }
        }
        
        // Optimization: reverse word if last char appears less frequently
        char firstChar = word.charAt(0);
        char lastChar = word.charAt(word.length() - 1);
        if (boardCount[lastChar] < boardCount[firstChar]) {
            word = new StringBuilder(word).reverse().toString();
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        if (index == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // Mark as visited
        char temp = board[i][j];
        board[i][j] = '#';
        
        // Explore 4 directions
        if (dfs(board, word, i + 1, j, index + 1) ||
            dfs(board, word, i - 1, j, index + 1) ||
            dfs(board, word, i, j + 1, index + 1) ||
            dfs(board, word, i, j - 1, index + 1)) {
            board[i][j] = temp;
            return true;
        }
        
        // Restore and backtrack
        board[i][j] = temp;
        return false;
    }

    // Alternative: Using visited array
    public boolean existVisited(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfsVisited(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean dfsVisited(char[][] board, String word, int i, int j, int index, boolean[][] visited) {
        if (index == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            visited[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }
        
        visited[i][j] = true;
        
        if (dfsVisited(board, word, i + 1, j, index + 1, visited) ||
            dfsVisited(board, word, i - 1, j, index + 1, visited) ||
            dfsVisited(board, word, i, j + 1, index + 1, visited) ||
            dfsVisited(board, word, i, j - 1, index + 1, visited)) {
            return true;
        }
        
        visited[i][j] = false;
        return false;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        
        // Test cases from LeetCode
        char[][] board1 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        
        char[][] board2 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        
        char[][] board3 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        
        char[][] board4 = {{'a'}};
        char[][] board5 = {{'a','b'}};
        char[][] board6 = {{'A','B','C','E'},{'S','F','E','S'},{'A','D','E','E'}};
        
        Object[][] testCases = {
            {board1, "ABCCED", true},
            {board2, "SEE", true},
            {board3, "ABCB", false},
            {board4, "a", true},
            {board4, "b", false},
            {board5, "ba", true},
            {board5, "ab", true},
            {board6, "ABCESEEEFS", true},
        };
        
        System.out.println("=== Word Search Tests ===");
        for (int i = 0; i < testCases.length; i++) {
            char[][] board = (char[][]) testCases[i][0];
            String word = (String) testCases[i][1];
            boolean expected = (boolean) testCases[i][2];
            
            // Deep copy boards for each method
            char[][] boardCopy1 = deepCopy(board);
            char[][] boardCopy2 = deepCopy(board);
            
            boolean result1 = solver.exist(boardCopy1, word);
            boolean result2 = solver.existVisited(boardCopy2, word);
            
            boolean match1 = result1 == expected;
            boolean match2 = result2 == expected;
            
            System.out.println("Test " + (i + 1) + ": word = \"" + word + "\"");
            System.out.println("  Expected: " + expected);
            System.out.println("  In-place:  " + result1 + (match1 ? " ✓" : " ✗"));
            System.out.println("  Visited:   " + result2 + (match2 ? " ✓" : " ✗"));
            System.out.println("  All match: " + (match1 && match2 ? "✓" : "✗"));
            System.out.println();
        }
    }
    
    private static char[][] deepCopy(char[][] board) {
        char[][] copy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
}