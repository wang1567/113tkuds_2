public class lt_14_LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        // 邊界情況：空陣列或null
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 以第一個字串作為初始的共同前綴候選
        String prefix = strs[0];

        // 從第二個字串開始，逐一與當前prefix比較
        for (int i = 1; i < strs.length; i++) {
            // 不斷縮短prefix，直到current字串以prefix開頭
            while (!strs[i].startsWith(prefix)) {
                // 移除prefix的最後一個字符
                prefix = prefix.substring(0, prefix.length() - 1);

                // 如果prefix變成空字串，表示沒有共同前綴
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    // 替代解法：垂直掃描法 (Vertical Scanning)
    public String longestCommonPrefixVertical(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 逐個字符位置進行比較
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i);

            // 檢查所有字串在位置i的字符是否相同
            for (int j = 1; j < strs.length; j++) {
                // 如果某個字串長度不夠，或字符不匹配
                if (i >= strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return strs[0].substring(0, i);
                }
            }
        }

        // 如果完整掃描第一個字串都沒有不匹配，返回第一個字串
        return strs[0];
    }

    // 替代解法：分治法 (Divide and Conquer)
    public String longestCommonPrefixDivideConquer(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        return longestCommonPrefixHelper(strs, 0, strs.length - 1);
    }

    private String longestCommonPrefixHelper(String[] strs, int left, int right) {
        // 基礎情況：只有一個字串
        if (left == right) {
            return strs[left];
        }

        int mid = (left + right) / 2;
        String leftPrefix = longestCommonPrefixHelper(strs, left, mid);
        String rightPrefix = longestCommonPrefixHelper(strs, mid + 1, right);

        return commonPrefix(leftPrefix, rightPrefix);
    }

    private String commonPrefix(String left, String right) {
        int minLength = Math.min(left.length(), right.length());
        for (int i = 0; i < minLength; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.substring(0, i);
            }
        }
        return left.substring(0, minLength);
    }
}

/*
 * 解題邏輯與思路說明：
 * 
 * 1. 問題理解：
 * - 在字串陣列中找到所有字串的最長共同前綴
 * - 如果沒有共同前綴，返回空字串
 * - 需要考慮空字串和長度不同的字串
 * 
 * 2. 主要解法：水平掃描法 (Horizontal Scanning)
 * 核心思想：
 * - 以第一個字串作為初始前綴候選
 * - 依序與其他字串比較，不斷縮短前綴
 * - 直到找到所有字串都匹配的最長前綴
 * 
 * 算法步驟：
 * Step 1: 將第一個字串設為初始prefix
 * Step 2: 對每個後續字串，檢查是否以current prefix開頭
 * Step 3: 如果不匹配，縮短prefix（移除最後一個字符）
 * Step 4: 重複Step 2-3直到匹配或prefix變空
 * Step 5: 返回最終的prefix
 * 
 * 3. 算法分析：
 * - 時間複雜度：O(S)，其中S是所有字串字符的總數
 * - 最壞情況：所有字串都相同，需要比較每個字符
 * - 最好情況：第一個字串很短或很快找到不匹配
 * - 空間複雜度：O(1)，只使用常數額外空間
 * 
 * 4. 舉例說明 strs = ["flower","flow","flight"]：
 * - 初始: prefix = "flower"
 * - 比較 "flow": "flow".startsWith("flower") = false
 * - 縮短: prefix = "flowe"
 * - "flow".startsWith("flowe") = false
 * - 縮短: prefix = "flow"
 * - "flow".startsWith("flow") = true ✓
 * - 比較 "flight": "flight".startsWith("flow") = false
 * - 縮短: prefix = "flo"
 * - "flight".startsWith("flo") = false
 * - 縮短: prefix = "fl"
 * - "flight".startsWith("fl") = true ✓
 * - 結果: "fl"
 * 
 * 5. 替代解法比較：
 * 
 * a) 垂直掃描法：
 * - 逐個字符位置比較所有字串
 * - 優勢：遇到不匹配立即停止
 * - 時間複雜度：O(S)
 * - 適合：共同前綴很短的情況
 * 
 * b) 分治法：
 * - 將問題分解為更小的子問題
 * - 遞歸地找到左右兩部分的共同前綴
 * - 時間複雜度：O(S)
 * - 空間複雜度：O(m*log(n))，其中m是字串平均長度
 * - 適合：大量字串且需要並行處理的情況
 * 
 * 6. 邊界情況處理：
 * - 空陣列：返回 ""
 * - 只有一個字串：返回該字串
 * - 包含空字串：最終結果必為 ""
 * - 所有字串相同：返回該字串
 * 
 * 7. 性能優化考量：
 * - 主解法在大多數情況下性能最佳
 * - 垂直掃描在前綴很短時效率更高
 * - 分治法適合非常大的數據集
 * 
 * 8. 關鍵洞察：
 * - 最長共同前綴的長度不會超過最短字串的長度
 * - 一旦某個字串不匹配當前前綴，就需要縮短前綴
 * - startsWith()方法提供了便捷的前綴匹配檢查
 * - 不同的掃描方式適用於不同的數據特徵
 * 
 * 9. 實際應用場景：
 * - 文件路徑處理
 * - URL分析
 * - 自動完成功能
 * - 數據去重和分類
 */