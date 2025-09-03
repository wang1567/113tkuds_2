import java.util.*;

public class lt_18_4Sum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        // 邊界情況檢查
        if (nums == null || nums.length < 4) {
            return result;
        }

        // 關鍵步驟：排序，為雙指針和去重做準備
        Arrays.sort(nums);
        int n = nums.length;

        // 第一層迴圈：固定第一個數字
        for (int i = 0; i < n - 3; i++) {
            // 跳過重複的第一個數字
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 優化1：如果當前最小可能的和都大於target，直接結束
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }

            // 優化2：如果當前最大可能的和都小於target，跳過這個i
            if ((long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3] < target) {
                continue;
            }

            // 第二層迴圈：固定第二個數字
            for (int j = i + 1; j < n - 2; j++) {
                // 跳過重複的第二個數字
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // 優化3：基於前兩個數的剪枝
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }

                if ((long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2] < target) {
                    continue;
                }

                // 使用雙指針尋找剩餘兩個數字
                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    // 注意：使用long避免整數溢出
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        // 找到一個有效的四元組
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

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
                    } else if (sum < target) {
                        // 和太小，移動左指針增大和
                        left++;
                    } else {
                        // 和太大，移動右指針減小和
                        right--;
                    }
                }
            }
        }

        return result;
    }

    // 通用的kSum解法（可以處理任意k值）
    public List<List<Integer>> fourSumGeneral(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 0, 4);
    }

    /**
     * 通用kSum遞歸解法
     * 
     * @param nums   已排序的數組
     * @param target 目標和
     * @param start  開始搜索的索引
     * @param k      需要找的數字個數
     * @return 所有滿足條件的k元組
     */
    private List<List<Integer>> kSum(int[] nums, long target, int start, int k) {
        List<List<Integer>> result = new ArrayList<>();

        // 邊界檢查
        if (start == nums.length || nums[start] * k > target || nums[nums.length - 1] * k < target) {
            return result;
        }

        // 基礎情況：k = 2，使用雙指針
        if (k == 2) {
            return twoSum(nums, target, start);
        }

        // 遞歸情況：固定一個數，尋找剩餘k-1個數
        for (int i = start; i < nums.length; i++) {
            // 跳過重複元素
            if (i == start || nums[i - 1] != nums[i]) {
                for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
                    List<Integer> quadruplet = new ArrayList<>();
                    quadruplet.add(nums[i]);
                    quadruplet.addAll(subset);
                    result.add(quadruplet);
                }
            }
        }

        return result;
    }

    /**
     * 雙指針解決twoSum問題
     */
    private List<List<Integer>> twoSum(int[] nums, long target, int start) {
        List<List<Integer>> result = new ArrayList<>();
        int left = start;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum < target || (left > start && nums[left] == nums[left - 1])) {
                left++;
            } else if (sum > target || (right < nums.length - 1 && nums[right] == nums[right + 1])) {
                right--;
            } else {
                result.add(Arrays.asList(nums[left], nums[right]));
                left++;
                right--;
            }
        }

        return result;
    }
}

/*
 * 解題邏輯與思路說明：
 * 
 * 1. 問題理解：
 * - 在數組中找到所有和等於target的四元組
 * - 四個數的索引必須不同
 * - 結果不能包含重複的四元組
 * - 這是3Sum問題的擴展版本
 * 
 * 2. 核心策略：排序 + 固定兩個數 + 雙指針
 * - 延續3Sum的思路，再增加一層固定
 * - 外兩層迴圈固定前兩個數
 * - 內層使用雙指針尋找後兩個數
 * - 通過排序和跳重來避免重複結果
 * 
 * 3. 算法步驟詳解：
 * Step 1: 對數組進行排序
 * Step 2: 第一層迴圈固定第一個數nums[i] (i = 0 to n-4)
 * Step 3: 第二層迴圈固定第二個數nums[j] (j = i+1 to n-3)
 * Step 4: 使用雙指針left=j+1, right=n-1尋找後兩個數
 * Step 5: 計算四數之和sum = nums[i] + nums[j] + nums[left] + nums[right]
 * Step 6: 根據sum與target的關係調整指針
 * Step 7: 在各層都進行去重處理
 * Step 8: 收集所有滿足條件的四元組
 * 
 * 4. 關鍵優化策略：
 * 
 * a) 提前剪枝：
 * - 如果最小可能和 > target，直接break
 * - 如果最大可能和 < target，跳過當前數字
 * 
 * b) 整數溢出保護：
 * - 使用long類型計算和，避免溢出
 * - 題目約束nums[i]和target可能很大
 * 
 * c) 多層去重：
 * - 第一層：跳過相同的nums[i]
 * - 第二層：跳過相同的nums[j]
 * - 雙指針：跳過相同的nums[left]和nums[right]
 * 
 * 5. 舉例說明 nums = [1,0,-1,0,-2,2], target = 0：
 * 排序後：[-2,-1,0,0,1,2]
 * 
 * i=0, nums[i]=-2:
 * ├─ j=1, nums[j]=-1:
 * │ left=2(0), right=5(2), sum=-2-1+0+2=-1 < 0, left++
 * │ left=3(0), right=5(2), sum=-2-1+0+2=-1 < 0, left++
 * │ left=4(1), right=5(2), sum=-2-1+1+2=0 ✓ 找到[-2,-1,1,2]
 * ├─ j=2, nums[j]=0:
 * │ left=3(0), right=5(2), sum=-2+0+0+2=0 ✓ 找到[-2,0,0,2]
 * │ [繼續搜索...]
 * 
 * i=1, nums[i]=-1:
 * ├─ j=2, nums[j]=0:
 * │ left=3(0), right=5(2), sum=-1+0+0+2=1 > 0, right--
 * │ left=3(0), right=4(1), sum=-1+0+0+1=0 ✓ 找到[-1,0,0,1]
 * 
 * 6. 時間與空間複雜度：
 * - 時間複雜度：O(n³)
 * 排序：O(n log n)
 * 外兩層迴圈：O(n²)
 * 內層雙指針：O(n)
 * 總計：O(n³)
 * - 空間複雜度：O(1) 或 O(log n)
 * 不計結果存儲：O(1)
 * 排序可能需要 O(log n) 空間
 * 
 * 7. 通用kSum解法說明：
 * - 使用遞歸的思想：kSum → 固定一個數 + (k-1)Sum
 * - 遞歸終止條件：k=2時使用雙指針解決
 * - 可以輕鬆擴展到5Sum, 6Sum等問題
 * - 時間複雜度：O(n^(k-1))
 * 
 * 8. 與3Sum的關係：
 * - 4Sum是3Sum的自然擴展
 * - 增加了一層外部迴圈固定
 * - 去重邏輯更加複雜
 * - 需要更多的剪枝優化
 * 
 * 9. 邊界情況處理：
 * - 數組長度小於4：返回空結果
 * - 所有數字相同：檢查4*num是否等於target
 * - 存在大量重複：通過跳重邏輯處理
 * - 整數溢出：使用long類型計算
 * 
 * 10. 進階優化技巧：
 * - 預計算所有可能的兩數和（空間換時間）
 * - 使用HashMap存儲兩數和及其索引對
 * - 但要小心處理索引重複問題
 * 
 * 11. 實際應用場景：
 * - 投資組合：找到四個投資標的組合達到目標收益
 * - 遊戲平衡：四個屬性值的平衡組合
 * - 資源配置：四種資源的最優分配
 * - 數學問題：四個數的特殊關係
 * 
 * 12. 擴展思考：
 * - 可以推廣到任意kSum問題
 * - 如果允許重複使用數字，算法需要調整
 * - 如果要求索引順序，需要記錄原始索引
 * - 可以考慮使用分治或動態規劃的思路
 * 
 * 這道題展示了如何將二維問題（2Sum）擴展到多維問題（kSum），
 * 是理解降維思想和遞歸設計的好例子。通過層層固定的方式，
 * 將高維問題逐步降解為低維問題，是算法設計中的重要思想。
 */