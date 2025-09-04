public class lt_33_SearchinRotatedSortedArray {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                // Check if target is in left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted
            else {
                // Check if target is in right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在一個可能被旋轉的升序整數陣列 nums 中查找目標值 target 的索引，時間複雜度必須為 O(log
 * n)。陣列是升序的，但可能在某個未知索引 k 處被旋轉（例如 [0,1,2,4,5,6,7] 旋轉後可能變為
 * [4,5,6,7,0,1,2]），且所有值唯一。如果 target 存在於陣列中，返回其索引；否則返回 -1。
 * 
 * 我們使用二分搜尋法來解決此問題，具體邏輯如下：
 * 
 * 1. **初始化二分搜尋**：
 * - 使用兩個指針 left（指向陣列開頭）和 right（指向陣列結尾）。
 * - 每次循環計算中間點 mid = left + (right - left) / 2，避免整數溢出。
 * 
 * 2. **檢查中間元素**：
 * - 如果 nums[mid] 等於 target，直接返回 mid。
 * 
 * 3. **判斷哪一半是升序的**：
 * - 由於陣列是旋轉後的升序陣列，mid 將陣列分成兩部分，其中至少有一部分是升序的。
 * - 檢查 nums[left] <= nums[mid]：
 * - 如果成立，則左半部分 [left, mid] 是升序的。
 * - 否則，右半部分 [mid+1, right] 是升序的。
 * 
 * 4. **根據升序部分判斷 target 位置**：
 * - 如果左半部分是升序的：
 * - 檢查 target 是否在左半部分的範圍內（nums[left] <= target < nums[mid]）。
 * - 如果是，則在左半部分繼續搜尋（right = mid - 1）；否則，搜尋右半部分（left = mid + 1）。
 * - 如果右半部分是升序的：
 * - 檢查 target 是否在右半部分的範圍內（nums[mid] < target <= nums[right]）。
 * - 如果是，則在右半部分繼續搜尋（left = mid + 1）；否則，搜尋左半部分（right = mid - 1）。
 * 
 * 5. **未找到 target**：
 * - 如果二分搜尋結束（left > right），則 target 不在陣列中，返回 -1。
 ** 
 * 時間複雜度**：O(log n)，其中 n 是陣列長度。二分搜尋每次將搜尋範圍減半，符合題目要求的時間複雜度。
 ** 空間複雜度**：O(1)，僅使用常數級別的額外空間。
 ** 
 * 示例解釋**：
 * - 對於 nums = [4,5,6,7,0,1,2], target = 0：
 * - 初始：left = 0, right = 6, mid = 3, nums[mid] = 7。
 * - nums[left=4] <= nums[mid=7]，左半部分 [4,5,6,7] 升序，但 target=0 不在
 * [4,7)，搜尋右半部分：left = 4。
 * - mid = 5, nums[mid] = 1, nums[left=0] > nums[mid=1]，右半部分 [1,2] 升序，target=0 在
 * [1,2] 之外，搜尋左半部分：right = 4。
 * - mid = 4, nums[mid] = 0，找到 target，返回 4。
 * 
 * 這種方法利用二分搜尋的特性，通過判斷哪一半是升序的來縮小搜尋範圍，高效且正確地找到目標值的索引。
 */