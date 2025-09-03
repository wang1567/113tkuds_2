/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 創建一個 dummy node 指向 head，這樣可以統一處理刪除頭節點的情況
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 使用兩個指針：fast 和 slow
        ListNode fast = dummy;
        ListNode slow = dummy;

        // 第一步：讓 fast 指針先向前移動 n+1 步
        // 為什麼是 n+1？因為我們需要 slow 停在要刪除節點的前一個位置
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // 第二步：同時移動 fast 和 slow 指針，直到 fast 到達鏈表末尾
        // 此時 slow 指針正好指向要刪除節點的前一個節點
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 第三步：刪除目標節點
        // slow.next 就是要刪除的節點，將其從鏈表中移除
        slow.next = slow.next.next;

        // 返回 dummy.next，即新的頭節點
        return dummy.next;
    }
}

/*
 * 解題邏輯與思路詳解：
 * 
 * 【核心思路】：雙指針 + Dummy Node
 * 
 * 1. **問題分析**：
 * - 需要刪除鏈表中倒數第 n 個節點
 * - 單向鏈表無法直接從後往前計算
 * - 關鍵是找到要刪除節點的前一個節點
 * 
 * 2. **雙指針技巧**：
 * - 使用 fast 和 slow 兩個指針
 * - fast 先走 n+1 步，建立兩指針間的距離
 * - 然後兩指針同步前進，直到 fast 到達末尾
 * - 此時 slow 正好在目標節點的前一位
 * 
 * 3. **Dummy Node 的作用**：
 * - 處理刪除頭節點的邊界情況
 * - 統一所有節點的刪除邏輯
 * - 避免特殊判斷
 * 
 * 4. **算法步驟詳解**：
 * ① 創建 dummy 節點指向 head
 * ② fast 指針先移動 n+1 步
 * ③ fast 和 slow 同時移動至 fast 為 null
 * ④ 執行刪除：slow.next = slow.next.next
 * ⑤ 返回 dummy.next（新的頭節點）
 * 
 * 5. **複雜度分析**：
 * - 時間複雜度：O(L)，L 為鏈表長度，只遍歷一次
 * - 空間複雜度：O(1)，只使用常數額外空間
 * 
 * 6. **執行過程示例**：
 * 輸入：[1,2,3,4,5], n=2
 * 
 * 初始狀態：
 * dummy -> 1 -> 2 -> 3 -> 4 -> 5
 * ↑
 * fast,slow
 * 
 * fast 先走 3 步：
 * dummy -> 1 -> 2 -> 3 -> 4 -> 5
 * ↑ ↑
 * slow fast
 * 
 * 同時移動到 fast=null：
 * dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null
 * ↑ ↑
 * slow fast
 * 
 * 刪除 slow.next (節點4)：
 * dummy -> 1 -> 2 -> 3 -> 5
 * 
 * 結果：[1,2,3,5]
 * 
 * 7. **關鍵理解**：
 * - 為什麼 fast 要走 n+1 步？
 * 因為需要 slow 停在待刪除節點的前一位
 * - 雙指針間距始終保持 n+1，確保定位準確
 * - 這種技巧將「倒數第n個」轉化為「兩指針固定間距」問題
 * 
 * 8. **邊界情況處理**：
 * - 刪除頭節點：dummy 節點統一處理
 * - 鏈表只有一個節點：同樣適用
 * - n 等於鏈表長度：刪除頭節點的情況
 * 
 * 這個解法的精妙之處在於一次遍歷就解決問題，避免了先計算長度再定位的兩次遍歷。
 */