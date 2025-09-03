import java.util.*;

public class lt_16_3SumClosest {
    public int threeSumClosest(int[] nums, int target) {
        // 排序是關鍵步驟，為雙指針技巧做準備
        Arrays.sort(nums);

        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2]; // 初始化為前三個數的和
        int minDiff = Math.abs(closestSum - target); // 初始化最小差值

        // 外層迴圈：固定第一個數字
        for (int i = 0; i < n - 2; i++) {
            // 跳過重複的第一個數字（可選優化，雖然題目保證有唯一解）
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 使用雙指針尋找最接近target的三數和
            int left = i + 1; // 左指針
            int right = n - 1; // 右指針

            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                int currentDiff = Math.abs(currentSum - target);

                // 如果找到更接近的和，更新結果
                if (currentDiff < minDiff) {
                    minDiff = currentDiff;
                    closestSum = currentSum;
                }

                // 如果恰好等於target，直接返回（最優解）
                if (currentSum == target) {
                    return currentSum;
                }

                // 根據當前和與target的關係調整指針
                if (currentSum < target) {
                    left++; // 和太小，增大和
                } else {
                    right--; // 和太大，減小和
                }
            }

            // 進一步優化：如果當前最小差值已經為0，提前結束
            if (minDiff == 0) {
                break;
            }
        }

        return closestSum;
    }

    // 替代解法：帶更多優化的版本
    public int threeSumClosestOptimized(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < n - 2; i++) {
            // 跳過重複元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = n - 1;

            // 預先計算邊界情況，進行剪枝優化
            int minPossibleSum = nums[i] + nums[left] + nums[left + 1];
            int maxPossibleSum = nums[i] + nums[right - 1] + nums[right];

            // 如果最小可能和都比target大，更新結果並continue
            if (minPossibleSum > target) {
                if (Math.abs(minPossibleSum - target) < Math.abs(closestSum - target)) {
                    closestSum = minPossibleSum;
                }
                continue;
            }

            // 如果最大可能和都比target小，更新結果並continue
            if (maxPossibleSum < target) {
                if (Math.abs(maxPossibleSum - target) < Math.abs(closestSum - target)) {
                    closestSum = maxPossibleSum;
                }
                continue;
            }

            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];

                if (currentSum == target) {
                    return currentSum;
                }

                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }

                if (currentSum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return closestSum;
    }
}

/*
 * 解題邏輯與思路說明：
 * 
 * 1. 問題理解：
 * - 找到三個數的和最接近給定target的組合
 * - 返回這個最接近的和（不是索引或數字本身）
 * - 題目保證有唯一解，不需考慮多個等距離的情況
 * 
 * 2. 核心策略：排序 + 雙指針
 * - 延續3Sum的經典解法思路
 * - 區別：不是尋找等於0的組合，而是尋找最接近target的組合
 * - 需要維護當前找到的最接近的和以及對應的差值
 * 
 * 3. 算法步驟詳解：
 * Step 1: 對數組進行排序
 * Step 2: 初始化closestSum和minDiff
 * Step 3: 外層迴圈固定第一個數nums[i]
 * Step 4: 使用雙指針left和right搜索剩餘兩個數
 * Step 5: 計算當前三數和currentSum
 * Step 6: 如果|currentSum - target| < minDiff，更新結果
 * Step 7: 根據currentSum與target的關係調整指針：
 * - currentSum < target: left++ (需要更大的和)
 * - currentSum > target: right-- (需要更小的和)
 * - currentSum == target: 直接返回（最優解）
 * Step 8: 重複Steps 4-7直到指針相遇
 * Step 9: 重複Steps 3-8直到遍歷所有可能的第一個數
 * 
 * 4. 關鍵優化點：
 * 
 * a) 提前終止：
 * - 如果currentSum == target，直接返回
 * - 如果minDiff已經為0，提前結束外層迴圈
 * 
 * b) 跳過重複：
 * - 跳過相同的第一個數字（雖然題目保證唯一解，但仍可優化）
 * 
 * c) 邊界剪枝（進階優化）：
 * - 計算當前i下的最小和最大可能和
 * - 如果最小可能和 > target 或最大可能和 < target，可以直接處理
 * 
 * 5. 舉例說明 nums = [-1,2,1,-4], target = 1：
 * 排序後：[-4,-1,1,2]
 * 
 * i=0, nums[i]=-4:
 * - left=1(-1), right=3(2), sum=-4-1+2=-3, diff=|(-3)-1|=4
 * - sum < target, left++
 * - left=2(1), right=3(2), sum=-4+1+2=-1, diff=|(-1)-1|=2 (更新closestSum=-1)
 * - sum < target, left++
 * - left=3, right=3, 結束內層迴圈
 * 
 * i=1, nums[i]=-1:
 * - left=2(1), right=3(2), sum=-1+1+2=2, diff=|2-1|=1 (更新closestSum=2)
 * - sum > target, right--
 * - left=2, right=2, 結束內層迴圈
 * 
 * 最終結果：2
 * 
 * 6. 時間與空間複雜度：
 * - 時間複雜度：O(n²)
 * 排序：O(n log n)
 * 外層迴圈：O(n)
 * 內層雙指針：O(n)
 * 總計：O(n²)
 * - 空間複雜度：O(1) 或 O(log n)
 * 不計入排序額外空間：O(1)
 * 排序可能需要 O(log n) 空間
 * 
 * 7. 與3Sum的區別：
 * a) 目標不同：
 * - 3Sum尋找和為0的所有組合
 * - 3Sum Closest尋找最接近target的唯一組合
 * 
 * b) 返回值不同：
 * - 3Sum返回三元組列表
 * - 3Sum Closest返回具體的和值
 * 
 * c) 終止條件不同：
 * - 3Sum需要遍歷所有可能組合
 * - 3Sum Closest找到target就可以直接返回
 * 
 * 8. 邊界情況處理：
 * - 數組長度恰好為3：返回三個數的和
 * - target很大或很小：算法仍然有效
 * - 存在重複數字：跳重優化仍然適用
 * 
 * 9. 進階優化技巧：
 * - 預計算邊界值進行剪枝
 * - 使用更精細的跳重邏輯
 * - 在某些情況下可以提前終止搜索
 * 
 * 10. 實際應用場景：
 * - 投資組合優化：尋找最接近目標收益的投資組合
 * - 資源分配：在有限資源下找到最接近需求的分配方案
 * - 遊戲平衡：尋找最接近平衡點的數值組合
 * - 機器學習：尋找最優參數組合
 */