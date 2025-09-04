import java.util.Stack;

public class lt_32_LongestValidParentheses {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2)
            return 0;

        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Initialize with -1 as a reference point

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i); // Push index of opening parenthesis
            } else {
                stack.pop(); // Pop for closing parenthesis

                if (stack.isEmpty()) {
                    stack.push(i); // Reset reference point if stack is empty
                } else {
                    // Calculate length of valid substring
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在給定的僅包含 '(' 和 ')' 的字符串 s 中，找到最長的有效括號子字符串的長度。有效括號子字符串是指括號正確匹配的連續子字符串。
 * 
 * 我們使用棧（Stack）來解決此問題，通過記錄括號的索引來計算有效括號子字符串的長度。具體邏輯如下：
 * 
 * 1. **邊界條件檢查**：
 * - 如果字符串 s 為空或長度小於 2，直接返回 0，因為有效括號至少需要一對括號。
 * 
 * 2. **初始化棧**：
 * - 使用一個棧來存儲括號的索引，初始時推入 -1 作為參考點，方便計算有效括號的長度。
 * 
 * 3. **遍歷字符串**：
 * - 對於每個字符 s[i]：
 * - 如果是 '('，將其索引 i 推入棧中，表示遇到一個開括號，等待匹配。
 * - 如果是 ')'：
 * - 先彈出棧頂元素（可能是 '(' 的索引或參考點）。
 * - 如果彈出後棧為空，說明當前的 ')' 沒有匹配的 '('，將當前索引 i 作為新的參考點推入棧中。
 * - 如果棧不為空，當前 ')' 與棧頂索引之前的 '(' 形成有效匹配，計算當前有效子字符串的長度（i - stack.peek()），並更新
 * maxLen。
 * 
 * 4. **計算有效長度**：
 * - 每次遇到 ')' 並且棧不為空時，計算當前索引 i 與棧頂索引之間的距離，這表示一個有效括號子字符串的長度。
 * - 使用 maxLen 記錄所有有效子字符串中的最大長度。
 * 
 * 5. **返回結果**：
 * - 遍歷結束後，返回 maxLen，即最長有效括號子字符串的長度。
 ** 
 * 時間複雜度**：O(n)，其中 n 是字符串 s 的長度，因為我們只遍歷字符串一次，且棧的操作（推入和彈出）是 O(1)。
 ** 空間複雜度**：O(n)，用於存儲棧中的索引，最壞情況下棧可能存儲所有開括號的索引。
 ** 
 * 示例解釋**：
 * - 對於 s = ")()())"：
 * - 初始化：stack = [-1]
 * - i=0, ')': 彈出 -1，棧空，推入 0，stack = [0]
 * - i=1, '(': 推入 1，stack = [0, 1]
 * - i=2, ')': 彈出 1，計算長度 2-0=2，maxLen=2，stack = [0]
 * - i=3, '(': 推入 3，stack = [0, 3]
 * - i=4, ')': 彈出 3，計算長度 4-0=4，maxLen=4，stack = [0]
 * - i=5, ')': 彈出 0，棧空，推入 5，stack = [5]
 * - 最終返回 maxLen = 4（對應 "()()"）。
 * 
 * 這種方法通過棧高效地追蹤括號匹配，並正確計算最長有效括號子字符串的長度，符合題目要求。
 */