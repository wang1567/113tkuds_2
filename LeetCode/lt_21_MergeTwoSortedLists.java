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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 創建 dummy 節點，簡化邊界條件處理
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 同時遍歷兩個鏈表，比較節點值
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                // list1 的值較小或相等，連接 list1 的節點
                current.next = list1;
                list1 = list1.next;
            } else {
                // list2 的值較小，連接 list2 的節點
                current.next = list2;
                list2 = list2.next;
            }
            // 移動結果鏈表的指針
            current = current.next;
        }

        // 處理剩餘節點
        // 由於兩個鏈表都已排序，剩餘的節點可以直接連接
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        // 返回合併後鏈表的頭節點（跳過 dummy 節點）
        return dummy.next;
    }
}

/*
 * 解題邏輯與思路詳解：
 * 
 * 【核心思路】：雙指針 + 歸併排序思想
 * 
 * 1. **問題分析**：
 * - 兩個已排序的鏈表需要合併成一個排序鏈表
 * - 要求通過拼接原始節點，而不是創建新節點
 * - 本質上是歸併排序中的"合併"步驟
 * 
 * 2. **算法思路**：
 * - 使用雙指針分別指向兩個鏈表的當前節點
 * - 比較兩個指針指向節點的值，選擇較小的節點
 * - 將選中的節點連接到結果鏈表，並移動相應指針
 * - 重複直到其中一個鏈表遍歷完成
 * - 將剩餘鏈表直接連接到結果鏈表末尾
 * 
 * 3. **Dummy 節點的作用**：
 * - 簡化邊界條件處理，避免對空鏈表的特殊判斷
 * - 提供一個統一的起始點，無需考慮第一個節點的特殊情況
 * - 最終返回 dummy.next 作為合併鏈表的頭節點
 * 
 * 4. **詳細執行步驟**：
 * Step 1: 創建 dummy 節點和 current 指針
 * Step 2: 當兩個鏈表都不為空時：
 * a) 比較 list1.val 和 list2.val
 * b) 選擇較小值的節點連接到結果鏈表
 * c) 移動相應的鏈表指針和 current 指針
 * Step 3: 將剩餘的非空鏈表連接到結果鏈表
 * Step 4: 返回 dummy.next
 * 
 * 5. **執行過程示例**：
 * 
 * 輸入: list1 = [1,2,4], list2 = [1,3,4]
 * 
 * 初始狀態:
 * dummy -> null
 * ↑
 * current
 * list1 -> 1 -> 2 -> 4 -> null
 * list2 -> 1 -> 3 -> 4 -> null
 * 
 * 第1步: 比較 1 <= 1，選擇 list1
 * dummy -> 1 -> 2 -> 4
 * ↑
 * current
 * list1 -> 2 -> 4 -> null
 * list2 -> 1 -> 3 -> 4 -> null
 * 
 * 第2步: 比較 2 > 1，選擇 list2
 * dummy -> 1 -> 1 -> 3 -> 4
 * ↑
 * current
 * list1 -> 2 -> 4 -> null
 * list2 -> 3 -> 4 -> null
 * 
 * 第3步: 比較 2 < 3，選擇 list1
 * dummy -> 1 -> 1 -> 2 -> 4
 * ↑
 * current
 * list1 -> 4 -> null
 * list2 -> 3 -> 4 -> null
 * 
 * 第4步: 比較 4 > 3，選擇 list2
 * dummy -> 1 -> 1 -> 2 -> 3 -> 4
 * ↑
 * current
 * list1 -> 4 -> null
 * list2 -> 4 -> null
 * 
 * 第5步: 比較 4 <= 4，選擇 list1
 * dummy -> 1 -> 1 -> 2 -> 3 -> 4 -> null
 * ↑
 * current
 * list1 -> null
 * list2 -> 4 -> null
 * 
 * 第6步: list1 為空，直接連接 list2
 * dummy -> 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> null
 * 
 * 結果: [1,1,2,3,4,4]
 * 
 * 6. **邊界情況處理**：
 * - 兩個鏈表都為空：返回 dummy.next = null
 * - 其中一個鏈表為空：直接連接非空鏈表
 * - 兩個鏈表長度不同：剩餘節點直接連接
 * 
 * 7. **複雜度分析**：
 * - 時間複雜度：O(m + n)，其中 m 和 n 分別是兩個鏈表的長度
 * 需要遍歷兩個鏈表的所有節點
 * - 空間複雜度：O(1)，只使用了常數個額外指針變量
 * 沒有創建新節點，只是重新鏈接原有節點
 * 
 * 8. **算法優勢**：
 * - 原地操作：不需要額外空間創建新節點
 * - 時間最優：一次遍歷完成合併
 * - 邏輯清晰：利用已排序特性，貪心選擇最小元素
 * 
 * 9. **關鍵理解點**：
 * - 歸併思想：利用兩個已排序序列的性質
 * - 貪心策略：每次選擇當前最小的元素
 * - 指針操作：通過修改 next 指針重新組織鏈表結構
 * - 剩餘處理：已排序鏈表的剩餘部分無需逐個比較
 * 
 * 10. **擴展思考**：
 * - 這個算法可以擴展到合併 k 個有序鏈表
 * - 是歸併排序算法在鏈表上的經典應用
 * - 體現了"分治"思想中的"合併"步驟
 * 
 * 這道題完美展示了如何利用數據結構的特性（已排序）來設計高效算法，
 * 是理解歸併排序和鏈表操作的絕佳例題。
 */