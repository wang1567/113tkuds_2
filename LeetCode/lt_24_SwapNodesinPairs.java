class Main {

}

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

   // 方法1：迭代法（推薦）- 更直觀，空間複雜度更優
   public ListNode swapPairs(ListNode head) {
      // 創建 dummy 節點，簡化邊界條件處理
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      ListNode prev = dummy;

      // 當存在至少兩個節點可以交換時繼續
      while (prev.next != null && prev.next.next != null) {
         // 標識要交換的兩個節點
         ListNode first = prev.next; // 第一個節點
         ListNode second = prev.next.next; // 第二個節點

         // 執行交換操作
         // 步驟1：prev 指向 second
         prev.next = second;

         // 步驟2：first 指向 second 的下一個節點
         first.next = second.next;

         // 步驟3：second 指向 first，完成交換
         second.next = first;

         // 移動 prev 到下一對節點的前面
         // 現在 first 在 second 的後面，所以 prev 移動到 first
         prev = first;
      }

      return dummy.next;
   }

   // 方法2：遞歸法 - 更優雅，但使用額外棧空間
   public ListNode swapPairsRecursive(ListNode head) {
      // 基礎情況：沒有節點或只有一個節點
      if (head == null || head.next == null) {
         return head;
      }

      // 保存第二個節點
      ListNode second = head.next;

      // 遞歸處理剩餘部分，並將結果連接到第一個節點
      head.next = swapPairsRecursive(second.next);

      // 將第二個節點指向第一個節點，完成交換
      second.next = head;

      // 返回新的頭節點（原來的第二個節點）
      return second;
   }
}

/*
 * 解題邏輯與思路詳解：
 * 
 * 【核心思路】：鏈表指針操作 - 通過改變 next 指針來重新組織節點連接
 * 
 * 1. **問題分析**：
 * - 需要交換相鄰的兩個節點，不能修改節點的值
 * - 只能通過改變節點間的連接關係來實現交換
 * - 需要處理奇數個節點的情況（最後一個節點不交換）
 * 
 * 2. **迭代法詳細分析**：
 * 
 * 核心思想：
 * - 維護一個 prev 指針，指向當前要交換的一對節點的前面
 * - 對於每一對節點，執行標準的交換操作
 * - 重複直到沒有完整的節點對可以交換
 * 
 * 交換操作的三個步驟：
 * ```
 * 交換前：prev -> first -> second -> next
 * 
 * 步驟1：prev.next = second
 * prev -> second
 * first -> second -> next
 * 
 * 步驟2：first.next = second.next
 * prev -> second
 * first -> next
 * second -> next
 * 
 * 步驟3：second.next = first
 * prev -> second -> first -> next
 * 
 * 交換完成！
 * ```
 * 
 * 3. **執行過程示例**（輸入：[1,2,3,4]）：
 * 
 * 初始狀態：
 * ```
 * dummy -> 1 -> 2 -> 3 -> 4 -> null
 * ↑
 * prev
 * ```
 * 
 * 第一輪交換 (1,2)：
 * ```
 * 交換前：dummy -> 1 -> 2 -> 3 -> 4
 * ↑ ↑ ↑
 * prev first second
 * 
 * 交換後：dummy -> 2 -> 1 -> 3 -> 4
 * prev指向2, first指向1, prev移動到1
 * ```
 * 
 * 第二輪交換 (3,4)：
 * ```
 * 交換前：dummy -> 2 -> 1 -> 3 -> 4
 * ↑ ↑ ↑
 * prev first second
 * 
 * 交換後：dummy -> 2 -> 1 -> 4 -> 3
 * ```
 * 
 * 最終結果：[2,1,4,3]
 * 
 * 4. **遞歸法詳細分析**：
 * 
 * 核心思想：
 * - 將問題分解：交換前兩個節點 + 遞歸處理剩餘部分
 * - 基礎情況：沒有節點或只有一個節點時直接返回
 * - 遞歸關係：處理當前一對，剩餘部分交給遞歸
 * 
 * 執行過程（輸入：[1,2,3,4]）：
 * ```
 * swapPairs([1,2,3,4])
 * ├─ head=1, second=2
 * ├─ head.next = swapPairs([3,4])
 * │ ├─ head=3, second=4
 * │ ├─ head.next = swapPairs(null) = null
 * │ ├─ second.next = head → 4->3->null
 * │ └─ return 4
 * ├─ 現在：1->4->3->null, second=2
 * ├─ second.next = head → 2->1->4->3->null
 * └─ return 2
 * 
 * 最終結果：[2,1,4,3]
 * ```
 * 
 * 5. **為什麼需要 dummy 節點**：
 * - 處理頭節點被交換的情況
 * - 統一交換操作，無需特殊處理第一對節點
 * - 簡化代碼邏輯，避免複雜的邊界判斷
 * 
 * 6. **邊界情況處理**：
 * - 空鏈表：dummy.next = null，返回 null
 * - 單個節點：while 循環條件不滿足，直接返回原節點
 * - 奇數個節點：最後一個節點不滿足交換條件，保持不變
 * 
 * 7. **兩種方法比較**：
 * 
 * 【迭代法】：
 * - 時間複雜度：O(n)，遍歷一次鏈表
 * - 空間複雜度：O(1)，只使用常數個指針變量
 * - 優勢：空間效率高，邏輯清晰
 * - 劣勢：指針操作較複雜，容易出錯
 * 
 * 【遞歸法】：
 * - 時間複雜度：O(n)，每個節點處理一次
 * - 空間複雜度：O(n)，遞歸調用棧深度
 * - 優勢：代碼簡潔優雅，思路清晰
 * - 劣勢：額外的棧空間開銷
 * 
 * 8. **指針操作的關鍵點**：
 * - 交換前要保存必要的引用，避免丟失節點
 * - 交換操作的順序很重要，順序錯誤會導致鏈表斷裂
 * - 使用 dummy 節點統一處理邏輯
 * 
 * 9. **常見錯誤**：
 * - 忘記移動 prev 指針，導致無限循環
 * - 交換操作順序錯誤，導致鏈表結構破壞
 * - 沒有正確處理邊界情況
 * 
 * 10. **算法設計思想**：
 * - 模式識別：鏈表的"成對操作"問題
 * - 狀態維護：保持對當前處理位置的跟蹤
 * - 操作原子化：將複雜操作分解為簡單的步驟
 * 
 * 11. **擴展思考**：
 * - 可以推廣到"每k個節點一組進行反轉"（第25題）
 * - 體現了迭代與遞歸兩種思維模式的差異
 * - 是理解鏈表操作的重要練習
 * 
 * 12. **核心理解**：
 * - 鏈表操作的本質：通過改變指針來重新組織數據結構
 * - 邊界條件處理：dummy 節點是簡化鏈表問題的常用技巧
 * - 算法選擇：根據空間要求選擇迭代或遞歸
 * 
 * 這道題完美展示了鏈表指針操作的精髓：
 * 1. 通過改變連接關係來重組數據結構
 * 2. 邊界條件的優雅處理
 * 3. 迭代與遞歸兩種解題思維的對比
 * 
 * 掌握這道題對理解更複雜的鏈表操作很有幫助！
 */