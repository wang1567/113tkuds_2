public class lt_37_SudokuSolver {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    // Try digits 1-9 for the empty cell
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; // Place the digit
                            if (solve(board)) {
                                return true; // Solution found
                            }
                            board[i][j] = '.'; // Backtrack
                        }
                    }
                    return false; // No valid digit found
                }
            }
        }
        return true; // All cells filled
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        // Check row
        for (int j = 0; j < 9; j++) {
            if (board[row][j] == c)
                return false;
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == c)
                return false;
        }

        // Check 3x3 sub-box
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == c)
                    return false;
            }
        }

        return true;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求解決一個 9x9 的數獨問題，通過填充空細胞（標記為 '.'）使棋盤滿足以下規則：
 * 1. 每行包含數字 1-9，且無重複。
 * 2. 每列包含數字 1-9，且無重複。
 * 3. 每個 3x3 子方格包含數字 1-9，且無重複。
 * 
 * 我們使用回溯法（Backtracking）來解決此問題，具體邏輯如下：
 * 
 * 1. **主函數 solveSudoku**：
 * - 調用輔助函數 solve 來填充棋盤。
 * 
 * 2. **回溯函數 solve**：
 * - 遍歷 9x9 棋盤，尋找空細胞（board[i][j] == '.'）。
 * - 對於每個空細胞，嘗試填入數字 '1' 到 '9'：
 * - 檢查當前數字 c 是否有效（通過 isValid 函數）。
 * - 如果有效，將數字 c 填入 board[i][j]，並遞迴調用 solve 嘗試填充後續細胞。
 * - 如果遞迴返回 true（表示找到解），則返回 true。
 * - 如果遞迴返回 false（表示當前填充導致無解），則回溯，將 board[i][j] 恢復為 '.'，並嘗試下一個數字。
 * - 如果當前空細胞無法填入任何有效數字，返回 false。
 * - 如果遍歷完所有細胞（即無空細胞），返回 true，表示棋盤已解決。
 * 
 * 3. **有效性檢查函數 isValid**：
 * - 檢查在位置 (row, col) 填入數字 c 是否滿足數獨規則：
 * - 檢查行：遍歷 board[row][j]，確保數字 c 未出現在該行。
 * - 檢查列：遍歷 board[i][col]，確保數字 c 未出現在該列。
 * - 檢查 3x3 子方格：
 * - 計算子方格的左上角索引 (startRow, startCol)，其中 startRow = row - row % 3，startCol = col
 * - col % 3。
 * - 遍歷子方格，確保數字 c 未出現。
 * - 如果行、列和子方格均無重複，返回 true；否則返回 false。
 * 
 * 4. **返回結果**：
 * - solveSudoku 直接修改輸入的 board，無需返回值。題目保證輸入有唯一解，因此回溯法最終會找到正確解。
 ** 
 * 時間複雜度**：O(9^m)，其中 m 是空細胞數量。最壞情況下，每個空細胞嘗試 9
 * 種數字，導致指數級複雜度。但實際上，由於數獨的約束條件，分支會被大量剪枝，效率較高。
 ** 空間複雜度**：O(1)，因為棋盤大小固定為 9x9，遞迴棧的深度最多為 81（空細胞數量），視為常數空間。
 ** 
 * 示例解釋**：
 * - 對於給定輸入棋盤：
 * - 從第一個空細胞開始，嘗試填入數字 1-9，檢查有效性。
 * - 如果填入數字有效，繼續遞迴填充下一個空細胞；否則回溯並嘗試下一個數字。
 * - 最終填充所有空細胞，得到滿足數獨規則的唯一解。
 * 
 * 這種方法利用回溯法系統地嘗試所有可能性，通過有效性檢查剪枝無效分支，高效解決數獨問題，符合題目要求。
 */