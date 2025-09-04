public class lt_31_NextPermutation {
    public void nextPermutation(int[] nums) {
        // Find the first pair of adjacent elements where nums[i] < nums[i+1] from right
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // If such a pair is found, find the smallest element on the right side that is
        // greater than nums[i]
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            // Swap the two elements
            swap(nums, i, j);
        }

        // Reverse the subarray to the right of i
        reverse(nums, i + 1, nums.length - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start++, end--);
        }
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求找到整數陣列 nums
 * 的下一個字典序更大的排列。如果不存在這樣的排列，則將陣列重新排列為最小的字典序（即升序）。操作必須在原地完成，且僅使用常數額外空間。
 * 
 * 我們使用標準的「下一個排列」算法，該算法基於字典序排列的性質，具體邏輯如下：
 * 
 * 1. **找到第一個遞減對**：
 * - 從陣列右端開始，向左尋找第一對相鄰元素 nums[i] < nums[i+1]。
 * - 如果找不到這樣的對（即陣列是遞減的，如 [3,2,1]），則整個陣列是最大的排列，需跳到步驟 3。
 * - 否則，i 指向需要調整的關鍵位置，表示 nums[i] 是需要被替換的元素。
 * 
 * 2. **找到右側最小的更大元素並交換**：
 * - 如果找到了 i（即 i >= 0），則從右端開始，找到第一個比 nums[i] 大的元素 nums[j]（即 nums[j] > nums[i]）。
 * - 交換 nums[i] 和 nums[j]，這樣可以得到一個比當前排列稍大的排列。
 * - 因為右側的子陣列（從 i+1 到末尾）是遞減的，nums[j] 是右側大於 nums[i] 的最小元素。
 * 
 * 3. **反轉右側子陣列**：
 * - 交換後，nums[i+1] 到末尾的子陣列仍然是遞減的（因為原始右側是遞減的，且交換後保持此性質）。
 * - 將這個子陣列反轉（從 i+1 到末尾），使其變為升序，從而保證得到的是最小的下一個排列。
 * - 如果 i < 0（即整個陣列遞減），則直接反轉整個陣列，得到最小的排列（升序）。
 ** 
 * 時間複雜度**：O(n)，其中 n 是陣列長度。尋找遞減對、尋找右側最小更大元素以及反轉子陣列的時間均為 O(n)。
 ** 空間複雜度**：O(1)，僅使用常數級別的額外空間，符合題目要求。
 ** 
 * 示例解釋**：
 * - 對於 [1,2,3]：
 * - 找到 i=1（因為 nums[1]=2 < nums[2]=3）。
 * - 找到 j=2（因為 nums[2]=3 > nums[1]=2）。
 * - 交換 nums[1] 和 nums[2]，得到 [1,3,2]。
 * - 右側子陣列（從 i+1=2 到末尾）已是最小，無需反轉，結果為 [1,3,2]。
 * - 對於 [3,2,1]：
 * - 找不到 i（陣列遞減），i=-1。
 * - 直接反轉整個陣列，得到 [1,2,3]。
 * 
 * 這種方法高效且符合題目要求，能正確生成下一個字典序排列。
 */