public class lt_38_CountandSay {
    public String countAndSay(int n) {
        if (n == 1)
            return "1"; // Base case

        // Get the (n-1)th sequence and apply RLE
        String prev = countAndSay(n - 1);
        StringBuilder result = new StringBuilder();

        // Apply run-length encoding
        int count = 1;
        char current = prev.charAt(0);

        for (int i = 1; i < prev.length(); i++) {
            if (prev.charAt(i) == current) {
                count++; // Increment count for same character
            } else {
                result.append(count).append(current); // Append count and character
                current = prev.charAt(i); // Update to new character
                count = 1; // Reset count
            }
        }

        // Append the last group
        result.append(count).append(current);

        return result.toString();
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求生成「Count and Say」序列的第 n 項。該序列的定義是遞迴的：
 * - 第 1 項為 "1"。
 * - 第 n 項是對第 n-1 項進行跑長編碼（Run-Length Encoding, RLE）的結果。
 * 跑長編碼是指將連續相同字符替換為「該字符的重複次數 + 該字符」，例如 "3322251" 編碼為 "23321511"。
 * 
 * 具體邏輯如下：
 * 
 * 1. **基礎情況**：
 * - 如果 n = 1，直接返回 "1"，因為這是序列的起始項。
 * 
 * 2. **遞迴生成前一項**：
 * - 對於 n > 1，遞迴調用 countAndSay(n - 1) 獲取第 n-1 項的字符串。
 * 
 * 3. **應用跑長編碼**：
 * - 使用 StringBuilder 來構建結果字符串，以提高效率。
 * - 遍歷第 n-1 項的字符串：
 * - 記錄當前字符（current）和其連續出現的次數（count）。
 * - 當遇到不同字符時，將當前的 count 和 current 追加到結果中，然後更新 current 為新字符並重置 count 為 1。
 * - 遍歷結束後，追加最後一組的 count 和 current。
 * 
 * 4. **返回結果**：
 * - 將 StringBuilder 轉為字符串返回。
 ** 
 * 時間複雜度**：O(2^n)，因為序列長度隨 n 增長呈指數級（每項長度大約是前一項的 1.5-2 倍），且我們需要遍歷前一項的字符串來生成當前項。
 ** 空間複雜度**：O(2^n)，用於存儲遞迴調用棧和結果字符串。
 ** 
 * 示例解釋**：
 * - n = 1：返回 "1"。
 * - n = 2：對 "1" 進行 RLE，得到 "11"（1 個 1）。
 * - n = 3：對 "11" 進行 RLE，得到 "21"（2 個 1）。
 * - n = 4：對 "21" 進行 RLE，得到 "1211"（1 個 2，1 個 1）。
 * 
 * 這種方法通過遞迴和跑長編碼正確生成第 n 項序列，符合題目要求。
 */