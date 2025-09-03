import java.util.*;

public class lt_17_LetterCombinationsofaPhoneNumber {
    // 建立數字到字母的映射（模擬電話按鍵）
    private static final String[] PHONE_MAP = {
            "", // 0
            "", // 1 (不對應任何字母)
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs", // 7
            "tuv", // 8
            "wxyz" // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // 邊界情況：空字串
        if (digits == null || digits.length() == 0) {
            return result;
        }

        // 使用回溯算法生成所有可能的組合
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    /**
     * 回溯函數
     * 
     * @param result  存儲最終結果的列表
     * @param current 當前正在構建的字符串
     * @param digits  輸入的數字字串
     * @param index   當前處理到第幾個數字
     */
    private void backtrack(List<String> result, StringBuilder current, String digits, int index) {
        // 遞歸終止條件：已經處理完所有數字
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // 獲取當前數字對應的字母組合
        char digit = digits.charAt(index);
        String letters = PHONE_MAP[digit - '0']; // 將字符轉換為數字索引

        // 遍歷當前數字對應的每個字母
        for (char letter : letters.toCharArray()) {
            // 選擇：將當前字母加入組合
            current.append(letter);

            // 遞歸：處理下一個數字
            backtrack(result, current, digits, index + 1);

            // 撤銷：移除剛加入的字母（回溯的關鍵步驟）
            current.deleteCharAt(current.length() - 1);
        }
    }

    // 替代解法1：迭代方法（BFS思想）
    public List<String> letterCombinationsIterative(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        result.add(""); // 初始化為包含空字串的列表

        // 逐個處理每個數字
        for (char digit : digits.toCharArray()) {
            String letters = PHONE_MAP[digit - '0'];
            List<String> temp = new ArrayList<>();

            // 對當前結果中的每個字串，與新數字的每個字母組合
            for (String combination : result) {
                for (char letter : letters.toCharArray()) {
                    temp.add(combination + letter);
                }
            }

            result = temp; // 更新結果列表
        }

        return result;
    }

    // 替代解法2：使用Queue實現BFS
    public List<String> letterCombinationsBFS(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(""); // 從空字串開始

        for (char digit : digits.toCharArray()) {
            String letters = PHONE_MAP[digit - '0'];
            int size = queue.size(); // 當前層的節點數量

            // 處理當前層的所有節點
            for (int i = 0; i < size; i++) {
                String current = queue.poll();

                // 為每個字母創建新的組合
                for (char letter : letters.toCharArray()) {
                    queue.offer(current + letter);
                }
            }
        }

        return new ArrayList<>(queue);
    }
}

/*
 * 解題邏輯與思路說明：
 * 
 * 1. 問題理解：
 * - 給定數字字串，返回所有可能的字母組合
 * - 每個數字對應電話按鍵上的字母
 * - 需要生成笛卡爾積（Cartesian Product）
 * - 例如：'23' → '2'對應'abc'，'3'對應'def' → 所有組合為9個
 * 
 * 2. 主要解法：遞歸回溯（DFS）
 * 核心思想：
 * - 這是典型的組合生成問題，適合用回溯算法
 * - 每一步選擇當前數字對應的一個字母
 * - 遞歸地處理剩餘數字
 * - 當處理完所有數字時，得到一個完整組合
 * 
 * 回溯三要素：
 * a) 選擇：從當前數字對應的字母中選一個
 * b) 約束：必須按數字順序選擇字母
 * c) 目標：生成長度等於digits長度的字串
 * 
 * 3. 算法步驟（遞歸回溯）：
 * Step 1: 建立數字到字母的映射表
 * Step 2: 處理邊界情況（空輸入）
 * Step 3: 調用回溯函數：
 * - 參數：結果列表、當前字串、輸入數字、當前索引
 * - 終止條件：索引到達數字串末尾
 * - 遞歸過程：
 * a) 獲取當前數字對應的字母
 * b) 遍歷每個字母
 * c) 選擇字母（加入當前字串）
 * d) 遞歸處理下一個數字
 * e) 撤銷選擇（移除字母）
 * 
 * 4. 舉例說明 digits = "23"：
 * 
 * 初始: current = "", index = 0
 * 
 * index=0, digit='2', letters="abc":
 * ├─ 選擇'a': current="a", 遞歸(index=1)
 * │ index=1, digit='3', letters="def":
 * │ ├─ 選擇'd': current="ad", 遞歸(index=2) → 添加"ad"
 * │ ├─ 選擇'e': current="ae", 遞歸(index=2) → 添加"ae"
 * │ └─ 選擇'f': current="af", 遞歸(index=2) → 添加"af"
 * ├─ 選擇'b': current="b", 遞歸(index=1)
 * │ [類似上面，生成"bd","be","bf"]
 * └─ 選擇'c': current="c", 遞歸(index=1)
 * [類似上面，生成"cd","ce","cf"]
 * 
 * 5. 替代解法分析：
 * 
 * a) 迭代方法（類似BFS）：
 * - 從空字串開始，逐個處理每個數字
 * - 對於每個新數字，將現有結果與該數字的字母組合
 * - 時間複雜度相同，但更直觀理解
 * 
 * b) BFS with Queue：
 * - 使用隊列模擬BFS過程
 * - 每一層代表處理一個數字
 * - 適合理解組合生成的層次結構
 * 
 * 6. 時間與空間複雜度：
 * - 時間複雜度：O(3^m × 4^n)
 * m: 對應3個字母的數字個數(2,3,4,5,6,8)
 * n: 對應4個字母的數字個數(7,9)
 * 最壞情況：O(4^k)，其中k是數字個數
 * - 空間複雜度：O(3^m × 4^n)
 * 主要是存儲結果的空間
 * 遞歸調用棧深度：O(k)
 * 
 * 7. 關鍵設計決策：
 * 
 * a) 數據結構選擇：
 * - 使用數組存儲映射關係（訪問O(1)）
 * - StringBuilder進行字串構建（避免重複創建）
 * 
 * b) 算法選擇：
 * - 回溯法適合組合生成問題
 * - 能夠系統性地遍歷所有可能性
 * 
 * 8. 邊界情況處理：
 * - 空字串輸入：返回空列表
 * - 單個數字：返回對應字母列表
 * - 包含'0'或'1'：題目約束不會出現
 * 
 * 9. 優化技巧：
 * - 使用StringBuilder而非String拼接
 * - 預先分配結果列表大小（如果已知）
 * - 可以考慮使用字符數組代替StringBuilder
 * 
 * 10. 實際應用：
 * - 密碼破解（T9輸入法逆向）
 * - 單詞遊戲生成
 * - 電話號碼與文本的雙向轉換
 * - 組合優化問題的模板應用
 * 
 * 11. 擴展思考：
 * - 如果要求字典序輸出，當前算法已滿足
 * - 如果要限制組合數量，可以添加剪枝條件
 * - 可以擴展到更複雜的映射關係
 * 
 * 這道題是回溯算法的經典應用，展示了如何系統性地生成所有可能的組合，
 * 是理解遞歸和回溯思想的絕佳例子。
 */