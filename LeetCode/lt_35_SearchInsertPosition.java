public class lt_35_SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在一個已排序（升序且無重複值）的整數陣列 nums 中，找到目標值 target 的索引。如果 target
 * 不存在於陣列中，返回它應插入的位置（保持陣列的升序性質）。算法的時間複雜度必須為 O(log n)。
 * 
 * 我們使用二分搜尋法來解決此問題，具體邏輯如下：
 * 
 * 1. **初始化二分搜尋**：
 * - 使用兩個指針 left（指向陣列開頭）和 right（指向陣列結尾）。
 * - 計算中間點 mid = left + (right - left) / 2，避免整數溢出。
 * 
 * 2. **二分搜尋過程**：
 * - 如果 nums[mid] 等於 target，直接返回 mid，因為這是目標值的索引。
 * - 如果 nums[mid] < target，說明 target 應在右半部分，更新 left = mid + 1。
 * - 如果 nums[mid] > target，說明 target 應在左半部分，更新 right = mid - 1。
 * 
 * 3. **處理插入位置**：
 * - 當二分搜尋結束（left > right），left 會停在 target 應該插入的位置。
 * - 這是因為：
 * - 如果 target 存在於陣列中，二分搜尋會直接返回其索引。
 * - 如果 target 不存在，left 最終會指向第一個大於 target 的元素的索引，或者陣列長度（如果 target 大於所有元素）。
 * - 因此，left 就是 target 的插入位置。
 ** 
 * 時間複雜度**：O(log n)，其中 n 是陣列長度。二分搜尋每次將搜尋範圍減半，符合題目要求的時間複雜度。
 ** 空間複雜度**：O(1)，僅使用常數級別的額外空間。
 ** 
 * 示例解釋**：
 * - 對於 nums = [1,3,5,6], target = 5：
 * - mid = 2, nums[mid] = 5，找到 target，返回 2。
 * - 對於 nums = [1,3,5,6], target = 2：
 * - 第一次：mid = 2, nums[mid] = 5 > 2，right = 1。
 * - 第二次：mid = 0, nums[mid] = 1 < 2，left = 1。
 * - 結束時：left = 1，返回 1（插入位置）。
 * - 對於 nums = [1,3,5,6], target = 7：
 * - 最終 left = 4，返回 4（插入到陣列末尾）。
 * 
 * 這種方法利用二分搜尋高效找到目標值或插入位置，簡單且符合題目要求。
 */