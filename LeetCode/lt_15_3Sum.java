import java.util.*;

public class lt_15_3Sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // 邊界情況檢查
        if (nums == null || nums.length < 3) {
            return result;
        }

        // 關鍵步驟：先對數組進行排序
        // 排序的目的：1) 使用雙指針技巧 2) 方便跳過重複元素
        Arrays.sort(nums);

        // 外層迴圈：固定第一個數字
        for (int i = 0; i < nums.length - 2; i++) {
            // 優化：如果當前數字大於0，後面不可能有和為0的三元組
            // 因為數組已排序，後面的數都更大
            if (nums[i] > 0) {
                break;
            }

            // 跳過重複的第一個數字，避免產生重複的三元組
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 使用雙指針尋找剩餘兩個數字
            int left = i + 1; // 左指針從i+1開始
            int right = nums.length - 1; // 右指針從末尾開始

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    // 找到一個有效的三元組
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 跳過左指針的重複元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    // 跳過右指針的重複元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // 移動雙指針繼續尋找
                    left++;
                    right--;
                } else if (sum < 0) {
                    // 和太小，需要增大，移動左指針
                    left++;
                } else {
                    // 和太大，需要減小，移動右指針
                    right--;
                }
            }
        }

        return result;
    }
}

/*
 * 解題邏輯與思路說明：
 * 
 * 1. 問題理解：
 * - 在數組中找出所有和為0的三元組
 * - 不能包含重複的三元組
 * - 三個數的索引必須不同
 * - 結果的順序不重要
 * 
 * 2. 暴力解法的問題：
 * - 三重迴圈遍歷所有可能的組合：O(n³)
 * - 需要額外的數據結構來去重
 * - 時間複雜度太高，會超時
 * 
 * 3. 優化解法：排序 + 雙指針
 * 核心思想：
 * - 將三數和問題轉化為「固定一個數，用雙指針找另外兩個數」
 * - 排序後可以使用雙指針技巧，將內層兩重迴圈優化為O(n)
 * 
 * 為什麼排序有效？
 * - 排序後可以使用雙指針從兩端向中間收縮
 * - 當和太小時，移動左指針增大和
 * - 當和太大時，移動右指針減小和
 * - 可以線性時間內找到所有解
 * 
 * 4. 算法步驟詳解：
 * Step 1: 對數組進行排序
 * Step 2: 外層迴圈固定第一個數 nums[i]
 * Step 3: 設置左指針 left = i+1，右指針 right = n-1
 * Step 4: 計算三數之和 sum = nums[i] + nums[left] + nums[right]
 * Step 5: 根據sum的值調整指針：
 * - sum == 0: 找到解，記錄結果，移動雙指針
 * - sum < 0: 和太小，left++
 * - sum > 0: 和太大，right--
 * Step 6: 重複Step 4-5直到 left >= right
 * Step 7: 重複Step 2-6直到遍歷完所有可能的第一個數
 * 
 * 5. 去重策略：
 * a) 第一個數去重：跳過與前一個相同的數字
 * b) 找到解後的去重：跳過left和right指向的重複數字
 * c) 為什麼這樣去重有效？排序保證了相同數字相鄰
 * 
 * 6. 舉例說明 nums = [-1,0,1,2,-1,-4]：
 * 排序後：[-4,-1,-1,0,1,2]
 * 
 * i=0, nums[i]=-4:
 * - left=1(-1), right=5(2), sum=-4-1+2=-3 < 0, left++
 * - left=2(-1), right=5(2), sum=-4-1+2=-3 < 0, left++
 * - left=3(0), right=5(2), sum=-4+0+2=-2 < 0, left++
 * - left=4(1), right=5(2), sum=-4+1+2=-1 < 0, left++
 * - left=5, right=5, 結束
 * 
 * i=1, nums[i]=-1:
 * - left=2(-1), right=5(2), sum=-1-1+2=0，找到解[-1,-1,2]
 * - 跳過重複，left++, right--
 * - left=3(0), right=4(1), sum=-1+0+1=0，找到解[-1,0,1]
 * - 繼續...
 * 
 * i=2, nums[i]=-1: 跳過重複
 * 
 * 最終結果：[[-1,-1,2],[-1,0,1]]
 * 
 * 7. 時間與空間複雜度：
 * - 時間複雜度：O(n²)
 * 排序：O(n log n)
 * 外層迴圈：O(n)
 * 內層雙指針：O(n)
 * 總計：O(n log n + n²) = O(n²)
 * - 空間複雜度：O(1) 或 O(log n)
 * 不計結果空間：O(1)
 * 排序可能需要 O(log n) 空間（取決於排序算法）
 * 
 * 8. 關鍵優化點：
 * a) 提前終止：當 nums[i] > 0 時直接break
 * b) 跳過重複：避免產生重複的三元組
 * c) 雙指針：將 O(n²) 的搜索優化為 O(n)
 * 
 * 9. 邊界情況處理：
 * - 數組長度小於3：返回空結果
 * - 所有數字都相同且為0：返回[[0,0,0]]
 * - 沒有有效三元組：返回空結果
 * - 包含大量重複數字：通過跳重邏輯處理
 * 
 * 10. 算法擴展：
 * - 可以推廣到 kSum 問題
 * - 核心思想：固定 k-2 個數，用雙指針找剩餘2個數
 * - 遞歸或迭代都可以實現
 * 
 * 11. 實際應用：
 * - 投資組合優化（尋找平衡的投資組合）
 * - 化學反應平衡
 * - 遊戲中的數值平衡
 * - 數據分析中的相關性查找
 */