public class lt_36_ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        // Use HashSet to track digits in each row, column, and 3x3 box
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char digit = board[i][j];
                if (digit != '.') {
                    // Check row, column, and 3x3 box for duplicates
                    String rowKey = "row" + i + digit;
                    String colKey = "col" + j + digit;
                    String boxKey = "box" + (i / 3) + (j / 3) + digit;

                    if (!seen.add(rowKey) || !seen.add(colKey) || !seen.add(boxKey)) {
                        return false; // Duplicate found
                    }
                }
            }
        }

        return true;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求判斷一個 9x9 的數獨棋盤是否有效，僅需檢查已填入的格子是否滿足以下規則：
 * 1. 每行包含數字 1-9，且無重複。
 * 2. 每列包含數字 1-9，且無重複。
 * 3. 每個 3x3 子方格包含數字 1-9，且無重複。
 * 
 * 我們使用 HashSet 來追蹤每個數字在行、列和 3x3 子方格中的出現情況，具體邏輯如下：
 * 
 * 1. **初始化 HashSet**：
 * - 創建一個 HashSet（seen）來存儲已見過的數字的標識。
 * - 每個標識用字符串表示，格式為：
 * - 行：row{i}{digit}（例如，row05 表示第 0 行數字 5）
 * - 列：col{j}{digit}（例如，col05 表示第 0 列數字 5）
 * - 子方格：box{i/3}{j/3}{digit}（例如，box005 表示第 0 個 3x3 子方格的數字 5）
 * 
 * 2. **遍歷棋盤**：
 * - 遍歷 9x9 棋盤的每個格子 (i, j)。
 * - 如果格子不為 '.'（表示有數字），則：
 * - 構造該數字在行、列和子方格的唯一標識。
 * - 嘗試將這三個標識加入 seen：
 * - 如果任一標識無法加入（即已存在），說明該數字在對應的行、列或子方格中重複，返回 false。
 * - 否則，將標識加入 seen。
 * 
 * 3. **計算子方格索引**：
 * - 每個 3x3 子方格的索引由 (i/3, j/3) 確定。例如，格子 (4,4) 屬於第 (1,1) 個子方格（因為 4/3 = 1）。
 * - 這確保每個格子被正確映射到其所在的 3x3 子方格。
 * 
 * 4. **返回結果**：
 * - 如果遍歷完成沒有發現重複數字，則返回 true，表示棋盤有效。
 ** 
 * 時間複雜度**：O(1)，因為棋盤固定為 9x9，遍歷所有格子需要 81 次操作，HashSet 的插入和檢查操作為 O(1)。
 ** 空間複雜度**：O(1)，因為 HashSet 最多存儲 81 個標識（每個格子最多一個數字，且棋盤大小固定）。
 ** 
 * 示例解釋**：
 * - 對於 Example 1：
 * - 遍歷所有非 '.' 格子，檢查每個數字的行、列和子方格，無重複，返回 true。
 * - 對於 Example 2：
 * - 在左上角 3x3 子方格中，數字 8 出現兩次（board[0][0]=8 和 board[3][0]=8），box008 無法再次加入
 * seen，返回 false。
 * 
 * 這種方法使用 HashSet 高效檢查重複，簡單且符合題目要求。
 */