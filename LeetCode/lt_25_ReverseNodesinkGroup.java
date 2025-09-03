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
    public ListNode reverseKGroup(ListNode head, int k) {
        // 若 k=1 或鏈表為空，無需反轉，直接返回原鏈表
        if (k == 1 || head == null) {
            return head;
        }

        // 創建虛擬節點以簡化邊界情況處理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroup = dummy; // 指向當前組之前的節點

        // 計算鏈表總節點數
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        // 處理每組 k 個節點
        while (length >= k) {
            curr = prevGroup.next; // 當前組的起始節點
            ListNode nextGroup = curr.next; // 當前組的第二個節點

            // 反轉當前組的 k-1 條鏈接
            for (int i = 0; i < k - 1; i++) {
                curr.next = nextGroup.next;
                nextGroup.next = prevGroup.next;
                prevGroup.next = nextGroup;
                nextGroup = curr.next;
            }

            // 更新 prevGroup 指向反轉後組的最後一個節點
            prevGroup = curr;
            length -= k;
        }

        return dummy.next;
    }
}

/*
 * 解題說明與邏輯：
 * 
 * 問題理解：
 * - 需要將鏈表每 k 個節點進行反轉
 * - 若剩餘節點數少於 k，保持原樣
 * - 只能改變節點間的連接，不能修改節點值
 * 
 * 解法思路：
 * 1. 處理基礎情況：
 * - 若 k=1 或鏈表為空，無需反轉
 * - 直接返回原頭節點
 * 
 * 2. 使用虛擬節點：
 * - 簡化頭節點的邊界情況處理
 * - 指向實際頭節點
 * 
 * 3. 計算總節點數：
 * - 用於確定可以處理多少組 k 個節點
 * - 避免處理不完整的組
 * 
 * 4. 主要反轉邏輯：
 * - 維護三個指針：
 * prevGroup：指向當前 k 組之前的節點
 * curr：指向當前 k 組的第一個節點
 * nextGroup：指向當前 k 組的第二個節點
 * - 對每組：
 * 通過調整 next 指針反轉 k-1 條鏈接
 * 更新 prevGroup 指向反轉後組的最後節點
 * 減少 k 個節點的計數
 * 
 * 5. 返回結果：
 * - 返回 dummy.next 作為新頭節點
 * 
 * 時間複雜度：O(n)，其中 n 為節點總數
 * - 遍歷一次鏈表以計數節點
 * - 每個節點在反轉過程中只處理一次
 * 
 * 空間複雜度：O(1)
 * - 僅使用常數額外的空間
 * - 所有操作均為原地操作
 * 
 * 範例演練 (k=2, 鏈表=1->2->3->4->5)：
 * 1. 第一組 (1->2)：
 * - 反轉為 2->1
 * - 連接到下一組
 * 2. 第二組 (3->4)：
 * - 反轉為 4->3
 * - 連接到最後節點
 * 3. 最後節點 (5) 保持不變
 * 4. 最終結果：2->1->4->3->5
 */