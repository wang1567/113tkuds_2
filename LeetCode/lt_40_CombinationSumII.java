import java.util.*;

public class lt_40_CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sort to handle duplicates
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current)); // Found a valid combination
            return;
        }
        if (target < 0 || start >= candidates.length) {
            return; // Sum exceeds target or out of bounds
        }

        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue; // Skip duplicates to avoid duplicate combinations
            }
            current.add(candidates[i]); // Include current candidate
            backtrack(candidates, target - candidates[i], i + 1, current, result); // Move to next index
            current.remove(current.size() - 1); // Backtrack
        }
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在給定的整數陣列 candidates 中，找到所有和為目標值 target
 * 的唯一組合。每個數字在組合中只能使用一次，且結果不能包含重複組合。與 Combination Sum (題目 39) 不同，這裡每個數字只能使用一次，且
 * candidates 可能包含重複數字。
 * 
 * 我們使用回溯法（Backtracking）結合排序來解決此問題，具體邏輯如下：
 * 
 * 1. **排序陣列**：
 * - 首先對 candidates 陣列進行排序（Arrays.sort），以便在回溯過程中方便處理重複數字，避免生成重複組合。
 * 
 * 2. **初始化結果**：
 * - 創建一個 List<List<Integer>> result 用於存儲所有有效組合。
 * - 調用回溯函數 backtrack，傳入 candidates、target、起始索引 start、當前組合 current 和結果列表 result。
 * 
 * 3. **回溯函數 backtrack**：
 * - **終止條件**：
 * - 如果 target == 0，表示當前組合的和等於目標值，將 current 複製並加入 result。
 * - 如果 target < 0 或 start 超出陣列範圍，終止該分支。
 * - **遍歷候選數字**：
 * - 從索引 start 開始遍歷 candidates。
 * - 為避免重複組合，檢查當前數字 candidates[i] 是否與前一個數字相同（i > start && candidates[i] ==
 * candidates[i - 1]），如果是則跳過。
 * - 將當前數字 candidates[i] 加入 current。
 * - 遞迴調用 backtrack，目標值減去 candidates[i]（target - candidates[i]），並將起始索引設為 i +
 * 1（因為每個數字只能使用一次）。
 * - 回溯：移除 current 中最後添加的數字，恢復狀態以嘗試下一個候選數字。
 * 
 * 4. **處理重複組合**：
 * - 排序後，相同的數字會連續排列。通過檢查 i > start && candidates[i] == candidates[i -
 * 1]，確保在同一層回溯中，相同的數字只被選取一次，從而避免重複組合。
 * - 使用 i + 1 確保每個數字只使用一次，與 Combination Sum (題目 39) 的重複使用不同。
 * 
 * 5. **返回結果**：
 * - 回溯完成後，result 包含所有和為 target 的唯一組合，返回該列表。
 ** 
 * 時間複雜度**：O(2^N)，其中 N 是 candidates 的長度。最壞情況下，回溯樹需要考慮所有可能的組合（每個數字選或不選）。排序的時間為
 * O(N log N)，但在總體複雜度中被 O(2^N) 支配。
 ** 空間複雜度**：O(N)，用於遞迴棧和當前組合 current 的空間，最大深度為 N。
 ** 
 * 示例解釋**：
 * - 對於 candidates = [10,1,2,7,6,1,5], target = 8：
 * - 排序後：candidates = [1,1,2,5,6,7,10]。
 * - 回溯生成組合：[1,1,6]、[1,2,5]、[1,7]、[2,6]。
 * - 跳過重複的 1（僅使用第一個 1 或第二個 1），避免重複組合。
 * - 結果：[[1,1,6], [1,2,5], [1,7], [2,6]]。
 * - 對於 candidates = [2,5,2,1,2], target = 5：
 * - 排序後：candidates = [1,2,2,2,5]。
 * - 生成組合：[1,2,2]、[5]。
 * - 結果：[[1,2,2], [5]]。
 * 
 * 這種方法通過排序和回溯法有效處理重複數字並生成唯一組合，符合題目要求。
 */