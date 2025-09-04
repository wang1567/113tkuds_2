public class lt_34_FindFirstandLastPositionofElementinSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[] { -1, -1 };
        if (nums == null || nums.length == 0)
            return result;

        // Find the first position
        result[0] = findFirst(nums, target);
        // Find the last position
        result[1] = findLast(nums, target);

        return result;
    }

    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int first = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                first = mid; // Record potential first position
                right = mid - 1; // Continue searching left for earlier occurrence
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return first;
    }

    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int last = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                last = mid; // Record potential last position
                left = mid + 1; // Continue searching right for later occurrence
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return last;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在一個非遞減排序的整數陣列 nums 中，找到給定目標值 target 的起始和結束索引。如果 target 不存在於陣列中，返回 [-1,
 * -1]。算法的時間複雜度必須為 O(log n)。
 * 
 * 我們使用二分搜尋法來分別找到 target 的第一個和最後一個出現位置。具體邏輯如下：
 * 
 * 1. **邊界條件檢查**：
 * - 如果陣列為空或 null，直接返回 [-1, -1]。
 * 
 * 2. **分兩次二分搜尋**：
 * - 問題分解為兩個子問題：找到 target 的第一個位置和最後一個位置。
 * - 定義兩個輔助函數 findFirst 和 findLast，分別用於查找起始和結束索引。
 * 
 * 3. **查找第一個位置 (findFirst)**：
 * - 使用標準二分搜尋，初始化 left = 0 和 right = nums.length - 1。
 * - 計算中間點 mid = left + (right - left) / 2。
 * - 如果 nums[mid] == target：
 * - 記錄 mid 作為潛在的第一位置（first = mid）。
 * - 繼續在左半部分搜尋（right = mid - 1），以檢查是否有更早的 target。
 * - 如果 nums[mid] < target，搜尋右半部分（left = mid + 1）。
 * - 如果 nums[mid] > target，搜尋左半部分（right = mid - 1）。
 * - 最終返回 first（如果未找到，保持 -1）。
 * 
 * 4. **查找最後一個位置 (findLast)**：
 * - 類似於 findFirst，但當 nums[mid] == target 時：
 * - 記錄 mid 作為潛在的最後位置（last = mid）。
 * - 繼續在右半部分搜尋（left = mid + 1），以檢查是否有更晚的 target。
 * - 其他邏輯與 findFirst 相同。
 * - 最終返回 last（如果未找到，保持 -1）。
 * 
 * 5. **返回結果**：
 * - 將 findFirst 和 findLast 的結果存入 result 陣列，返回 [result[0], result[1]]。
 ** 
 * 時間複雜度**：O(log n)，其中 n 是陣列長度。每次二分搜尋將搜尋範圍減半，執行兩次二分搜尋（分別找第一和最後位置），總時間仍為 O(log
 * n)。
 ** 空間複雜度**：O(1)，僅使用常數級別的額外空間（result 陣列和幾個變數）。
 ** 
 * 示例解釋**：
 * - 對於 nums = [5,7,7,8,8,10], target = 8：
 * - findFirst：找到 target=8 時，繼續向左搜尋，最終返回索引 3。
 * - findLast：找到 target=8 時，繼續向右搜尋，最終返回索引 4。
 * - 結果：[3, 4]。
 * - 對於 nums = [5,7,7,8,8,10], target = 6：
 * - findFirst 和 findLast 均返回 -1（未找到 target）。
 * - 結果：[-1, -1]。
 * 
 * 這種方法利用二分搜尋高效找到目標值的範圍，滿足題目要求的時間複雜度並正確處理所有邊界情況。
 */