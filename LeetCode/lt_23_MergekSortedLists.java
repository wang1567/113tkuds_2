import java.util.*;

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

    // 方法1：分治法（推薦解法）- 時間複雜度最優
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 使用分治法，兩兩合併
        return divideAndConquer(lists, 0, lists.length - 1);
    }

    /**
     * 分治法核心函數：遞歸合併指定範圍內的鏈表
     * 
     * @param lists 鏈表數組
     * @param start 起始索引
     * @param end   結束索引
     * @return 合併後的鏈表頭節點
     */
    private ListNode divideAndConquer(ListNode[] lists, int start, int end) {
        // 基礎情況1：只有一個鏈表
        if (start == end) {
            return lists[start];
        }

        // 基礎情況2：只有兩個鏈表，直接合併
        if (start + 1 == end) {
            return mergeTwoLists(lists[start], lists[end]);
        }

        // 分治：將問題分解為兩個子問題
        int mid = start + (end - start) / 2;
        ListNode left = divideAndConquer(lists, start, mid);
        ListNode right = divideAndConquer(lists, mid + 1, end);

        // 合併：將兩個子問題的結果合併
        return mergeTwoLists(left, right);
    }

    /**
     * 合併兩個有序鏈表（來自第21題的解法）
     * 
     * @param l1 第一個有序鏈表
     * @param l2 第二個有序鏈表
     * @return 合併後的有序鏈表
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }

        // 連接剩餘節點
        current.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    // 方法2：優先隊列（堆）解法 - 更直觀，適合理解
    public ListNode mergeKListsWithPriorityQueue(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 創建最小堆，按節點值排序
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // 將每個鏈表的頭節點加入堆中
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 不斷從堆中取出最小值節點
        while (!minHeap.isEmpty()) {
            ListNode minNode = minHeap.poll();
            current.next = minNode;
            current = current.next;

            // 如果該節點還有後續節點，將其加入堆
            if (minNode.next != null) {
                minHeap.offer(minNode.next);
            }
        }

        return dummy.next;
    }

    // 方法3：逐一合併法 - 簡單但效率較低
    public ListNode mergeKListsOneByOne(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode result = lists[0];

        // 逐一將每個鏈表與結果合併
        for (int i = 1; i < lists.length; i++) {
            result = mergeTwoLists(result, lists[i]);
        }

        return result;
    }
}

/*
 * 解題邏輯與思路詳解：
 * 
 * 【核心思路】：將問題轉化為"合併兩個有序鏈表"的重複應用
 * 
 * 1. **問題分析**：
 * - 本質上是第21題（合併兩個有序鏈表）的擴展版本
 * - 關鍵在於如何高效地處理多個鏈表的合併
 * - 不同的合併順序會影響時間複雜度
 * 
 * 2. **三種解法比較**：
 * 
 * 【方法1：分治法】（推薦）
 * - 思路：類似歸併排序，兩兩配對合併
 * - 時間複雜度：O(N * log k)，其中 N 是所有節點總數，k 是鏈表個數
 * - 空間複雜度：O(log k)，遞歸棧深度
 * - 優勢：時間複雜度最優，思路清晰
 * 
 * 【方法2：優先隊列（堆）】
 * - 思路：維護一個最小堆，始終取出最小值
 * - 時間複雜度：O(N * log k)
 * - 空間複雜度：O(k)，堆的大小
 * - 優勢：直觀易懂，適合處理動態數據流
 * 
 * 【方法3：逐一合併】
 * - 思路：依次合併每個鏈表
 * - 時間複雜度：O(N * k)，效率最低
 * - 空間複雜度：O(1)
 * - 劣勢：重複計算較多
 * 
 * 3. **分治法詳細分析**：
 * 
 * 核心思想：
 * - 將 k 個鏈表分成兩組
 * - 遞歸處理每組得到兩個合併結果
 * - 最後合併這兩個結果
 * 
 * 執行過程（k=4的情況）：
 * ```
 * 第一輪：[L1, L2, L3, L4]
 * / \
 * [L1, L2] [L3, L4]
 * | |
 * merge(L1,L2) merge(L3,L4)
 * | |
 * M12 M34
 * \ /
 * merge(M12, M34)
 * |
 * 最終結果
 * ```
 * 
 * 4. **優先隊列法詳細分析**：
 * 
 * 核心思想：
 * - 將所有鏈表的頭節點放入最小堆
 * - 每次取出最小值節點，並將其後續節點加入堆
 * - 重複直到堆為空
 * 
 * 執行過程：
 * ```
 * 初始：heap = [1(L1), 1(L2), 2(L3)]
 * 取1(L1)：result=[1], heap=[1(L2), 2(L3), 4(L1)]
 * 取1(L2)：result=[1,1], heap=[2(L3), 4(L1), 3(L2)]
 * 取2(L3)：result=[1,1,2], heap=[3(L2), 4(L1), 6(L3)]
 * ...繼續直到堆空
 * ```
 * 
 * 5. **為什麼分治法最優**：
 * 
 * 時間複雜度分析：
 * - 分治法：每層合併的總工作量是 O(N)，共有 log k 層
 * - 逐一合併：第i次合併處理 i*平均長度 個節點，總計 O(N*k)
 * - 堆方法：每個節點都要進出堆，每次操作 O(log k)
 * 
 * 直觀理解：
 * - 分治法充分利用了"已排序"的特性
 * - 避免了重複比較，每個節點只被處理 log k 次
 * - 符合"分而治之"的經典思想
 * 
 * 6. **複雜度詳細分析**：
 * 
 * 分治法：
 * - 時間：T(k) = 2*T(k/2) + O(N) = O(N * log k)
 * - 空間：遞歸棧深度 O(log k)
 * 
 * 堆方法：
 * - 時間：N個節點 × log k 每次堆操作 = O(N * log k)
 * - 空間：堆大小最多 k = O(k)
 * 
 * 逐一合併：
 * - 時間：第i次合併處理 (i-1)*avg + avg = i*avg 個節點
 * 總計：avg + 2*avg + ... + k*avg = O(k²*avg) = O(N*k)
 * - 空間：O(1)
 * 
 * 7. **邊界情況處理**：
 * - 空數組：直接返回 null
 * - 只有一個鏈表：直接返回該鏈表
 * - 包含空鏈表：在合併函數中自然處理
 * 
 * 8. **實際應用場景**：
 * - 外部排序：合併多個已排序的文件
 * - 數據庫：合併多個有序索引
 * - 分佈式系統：合併多個節點的排序結果
 * 
 * 9. **核心理解**：
 * - 分治思想：大問題分解為小問題
 * - 堆的應用：維護動態最值
 * - 時間複雜度優化：避免不必要的重複計算
 * - 算法選擇：根據具體場景選擇最適合的方法
 * 
 * 10. **擴展思考**：
 * - 如果鏈表數量 k 很大，堆方法更適合（避免深遞歸）
 * - 如果內存受限，逐一合併法更合適
 * - 在實際工程中，可能需要考慮鏈表長度差異很大的情況
 * 
 * 這道題完美展示了算法設計中的核心思想：
 * 1. 問題轉化：將複雜問題轉化為已解決的簡單問題
 * 2. 複雜度分析：不同策略的效率差異巨大
 * 3. 分治思想：是解決大規模問題的重要工具
 * 
 * 掌握這道題的多種解法，對理解算法設計思維很有幫助！
 */