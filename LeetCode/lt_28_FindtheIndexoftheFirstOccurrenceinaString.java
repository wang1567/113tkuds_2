public class lt_28_FindtheIndexoftheFirstOccurrenceinaString {
    public int strStr(String haystack, String needle) {
        // Handle edge cases
        if (needle.isEmpty())
            return 0;
        if (needle.length() > haystack.length())
            return -1;

        // Iterate through possible starting positions in haystack
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            // Check if substring starting at i matches needle
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }

        return -1;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在給定的字符串 haystack 中，找到字符串 needle 第一次出現的起始索引。如果 needle 不在 haystack 中，則返回
 * -1。
 * 
 * 我們使用簡單的滑動窗口方法來解決此問題，具體邏輯如下：
 * 
 * 1. **邊界條件檢查**：
 * - 如果 needle 為空字符串，根據題目慣例，返回 0。
 * - 如果 needle 的長度大於 haystack 的長度，則 needle 不可能出現在 haystack 中，返回 -1。
 * 
 * 2. **滑動窗口遍歷**：
 * - 遍歷 haystack 的每個可能起始位置 i，從 0 到 haystack.length() - needle.length()。
 * - 對於每個位置 i，檢查從 i 開始、長度為 needle.length() 的子字符串是否等於 needle。
 * - 如果找到匹配，返回當前的索引 i。
 * 
 * 3. **無匹配情況**：
 * - 如果遍歷完成後仍未找到匹配，返回 -1。
 ** 
 * 時間複雜度**：O(n * m)，其中 n 是 haystack 的長度，m 是 needle 的長度。因為對於每個可能的起始位置，我們需要比較長度為 m
 * 的子字符串。
 ** 空間複雜度**：O(1)，因為我們只使用了常數級別的額外空間（不計 substring 方法內部實現的臨時空間）。
 * 
 * 這種方法簡單直觀，適用於題目給定的約束條件。雖然 KMP 算法可以將時間複雜度優化到 O(n + m)，但考慮到題目規模（字符串長度最多為
 * 10^4）以及實現的簡單性，這裡選擇了直觀的滑動窗口方法。
 */