public class lt_27_RemoveElement {
    public int removeElement(int[] nums, int val) {
        int k = 0; // Pointer for position to place non-val elements

        // Iterate through the array
        for (int i = 0; i < nums.length; i++) {
            // If current element is not val, place it at index k
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在給定的整數陣列 nums 中，移除所有等於 val 的元素，並返回不等於 val 的元素數量 k。
 * 移除操作必須在原陣列上進行（in-place），且只需確保前 k 個元素是不等於 val 的元素，後面的元素內容不重要。
 * 
 * 我們使用「雙指針」技術中的快慢指針方法來解決此問題：
 * 1. **慢指針 k**：表示下一個應該放置非 val 元素的位置，同時也記錄不等於 val 的元素數量。
 * 2. **快指針 i**：用於遍歷陣列，檢查每個元素是否等於 val。
 * 
 * 具體邏輯如下：
 * - 初始化 k = 0，表示目前沒有找到非 val 元素。
 * - 遍歷陣列 nums：
 * - 如果當前元素 nums[i] 不等於 val，將其複製到 nums[k] 的位置，並將 k 遞增。
 * - 如果當前元素等於 val，則跳過（不改變 k）。
 * - 最終，k 的值就是不等於 val 的元素數量，且陣列的前 k 個元素已經是不等於 val 的元素。
 ** 
 * 時間複雜度**：O(n)，其中 n 是陣列長度，因為我們只遍歷陣列一次。
 ** 空間複雜度**：O(1)，因為我們在原陣列上操作，沒有使用額外空間。
 * 
 * 這種方法高效且簡單，符合題目要求在原地修改陣列並返回 k 的需求。
 */