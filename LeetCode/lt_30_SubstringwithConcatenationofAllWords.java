import java.util.*;

public class lt_30_SubstringwithConcatenationofAllWords {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0)
            return result;

        int wordLen = words[0].length();
        int totalLen = wordLen * words.length;
        if (s.length() < totalLen)
            return result;

        // Create a frequency map for words
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Slide window for each possible starting position within wordLen
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int count = 0;
            Map<String, Integer> seen = new HashMap<>();

            // Slide window by wordLen steps
            for (int j = i; j <= s.length() - wordLen; j += wordLen) {
                String currWord = s.substring(j, j + wordLen);

                // If current word is in wordCount
                if (wordCount.containsKey(currWord)) {
                    seen.put(currWord, seen.getOrDefault(currWord, 0) + 1);
                    count++;

                    // Shrink window while we have excess words
                    while (seen.get(currWord) > wordCount.getOrDefault(currWord, 0)) {
                        String leftWord = s.substring(left, left + wordLen);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        count--;
                        left += wordLen;
                    }

                    // Check if we have a valid concatenation
                    if (count == words.length) {
                        result.add(left);
                        String leftWord = s.substring(left, left + wordLen);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        count--;
                        left += wordLen;
                    }
                } else {
                    // Reset window if current word is not in wordCount
                    seen.clear();
                    count = 0;
                    left = j + wordLen;
                }
            }
        }

        return result;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在字符串 s 中找到所有由 words 陣列中的單詞（任意排列）連續拼接形成的子字符串的起始索引。所有 words 中的單詞長度相同，且 s
 * 和 words[i] 僅包含小寫英文字母。
 * 
 * 我們使用滑動窗口結合哈希表的方法來解決此問題，具體邏輯如下：
 * 
 * 1. **邊界條件檢查**：
 * - 如果 s 或 words 為空，或 words 陣列長度為 0，直接返回空列表。
 * - 計算單詞長度 wordLen（words[0].length）以及拼接字符串的總長度 totalLen（wordLen *
 * words.length）。
 * - 如果 s 的長度小於 totalLen，則不可能包含完整拼接字符串，返回空列表。
 * 
 * 2. **建立單詞頻率映射**：
 * - 使用哈希表 wordCount 記錄 words 中每個單詞的出現次數。
 * 
 * 3. **滑動窗口遍歷**：
 * - 由於拼接字符串必須由連續的單詞組成，且單詞長度固定，我們可以將問題分解為 wordLen 個獨立的滑動窗口問題（每個窗口從索引 0 到
 * wordLen-1 開始）。
 * - 對於每個起始偏移 i（0 到 wordLen-1）：
 * - 使用左指針 left 和計數器 count 來維護當前窗口的狀態。
 * - 使用哈希表 seen 記錄當前窗口中每個單詞的出現次數。
 * - 以 wordLen 為步長，從索引 j = i 開始遍歷 s，提取長度為 wordLen 的子字符串 currWord。
 * 
 * 4. **窗口內處理**：
 * - 如果 currWord 存在於 wordCount 中：
 * - 將 currWord 加入 seen 並遞增 count。
 * - 如果 currWord 在 seen 中的頻率超過 wordCount 中的頻率，則縮小窗口（移動 left），直到該單詞的頻率符合要求。
 * - 如果當前窗口包含的單詞數 count 等於 words.length，則找到一個有效拼接字符串，將 left
 * 加入結果列表，並縮小窗口（移除最左邊的單詞）。
 * - 如果 currWord 不在 wordCount 中：
 * - 清空 seen，重置 count，將 left 移到下一個單詞的起始位置。
 * 
 * 5. **返回結果**：
 * - 遍歷所有可能的窗口後，返回包含所有有效起始索引的列表。
 ** 
 * 時間複雜度**：O(n * m)，其中 n 是 s 的長度，m 是 words 中單詞的長度 wordLen。外層循環執行 wordLen
 * 次，內層循環遍歷 s 的每個可能單詞位置，子字符串比較的時間為 O(m)。
 ** 空間複雜度**：O(k)，其中 k 是 words 中不同單詞的數量，用於存儲 wordCount 和 seen 哈希表。
 * 
 * 這種方法通過滑動窗口和哈希表高效檢查所有可能的拼接字符串，處理了單詞的任意排列，並符合題目要求。
 */